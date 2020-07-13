package rubensandreoli.filetools.tools;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import rubensandreoli.filetools.tools.SearchAction.FileInfo;

/** References:
 * https://stackoverflow.com/questions/3263892/format-file-size-as-mb-gb-etc
 */
public class SearchAction implements Action<LinkedList<FileInfo>>{

    public void setFilters(boolean subfolders, boolean files, boolean folders) {
        this.subfolders = subfolders;
        this.files = files;
        this.folders = folders;
    }
    
    // <editor-fold defaultstate="collapsed" desc=" FILE INFO "> 
    public static class FileInfo{
        public final String filename;
        public final Path path;
        public final long size;
        public final boolean denied;

        private FileInfo(String filename, Path path, long size, boolean denied) {
            this.filename = filename;
            this.path = path;
            this.size = size;
            this.denied = denied;
        }
        
        private FileInfo(String filename, Path path, long size) {
            this(filename, path, size, false);
        }
        
        private FileInfo(String filename, Path path) {
            this(filename, path, 0, true);
        }
        
        public String getFormattedSize(){
            if(size <= 0) return "0";
            final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
            int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
            return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
        }
    }
    // </editor-fold>
    
    private Path folder;
    private String regex;
    private boolean subfolders = true;
    private boolean files = false;
    private boolean folders = true;
    
    private LinkedList<FileInfo> found = new LinkedList<>();
    private volatile boolean interrupted; 

    public SearchAction(String folder, String regex) {
        this.folder = Path.of(folder);
        if(regex == null || regex.isEmpty()){
            this.regex = ".*";
        }else{
            this.regex = regex;
        }
    }

    @Override
    public LinkedList<FileInfo> perform() {
        try {
            Files.walkFileTree(folder, new SimpleFileVisitor<Path>(){
                
                private long folderSize;
                
                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    System.err.println("Error accessing file: " +file.toAbsolutePath()+" -> "+ exc.getMessage());
                    return interrupted? FileVisitResult.TERMINATE:FileVisitResult.CONTINUE;
                }
                
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if(interrupted) return FileVisitResult.TERMINATE;
                    long size = 0;
                    boolean denied = false;
                    if(folders){ //if not including folders, there is no need to read every file size.
                        try{
                            size = Files.size(file);
                            folderSize+=size;   
                        }catch(IOException ex){
                            denied = true;
                        }
                    }
                    if(files){
                        String filename = file.getFileName().toString();
                        if(filename.matches(regex)){
                            if(!folders){
                                try{
                                    size = Files.size(file); 
                                }catch(IOException ex){
                                    denied = true;
                                }
                            }
                            FileInfo info = new FileInfo(filename, file, size, denied);
                            found.add(info);
                        }
                    }
                    
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if(folder.equals(dir)) return FileVisitResult.CONTINUE;
                    if(interrupted) return FileVisitResult.TERMINATE;
                    return subfolders? FileVisitResult.CONTINUE:FileVisitResult.SKIP_SUBTREE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    if(folder.equals(dir)) return FileVisitResult.CONTINUE;
                    if(folders){
                        String foldername = dir.getFileName().toString();
                        if(foldername.matches(regex)){
                            found.add(new FileInfo(foldername, dir, folderSize));
                            folderSize = 0;
                        }
                    }
                    return interrupted? FileVisitResult.TERMINATE:FileVisitResult.CONTINUE;
                }
                
                
            });
        } catch (IOException ex) {
            System.err.println("ERROR: failed searching folder " + ex.getMessage());
        }
        return found;
    }
    
    private boolean iteratePerforming(Function<Path, Boolean> function, int...indexes){
        boolean changed = false;
        for (int i = indexes.length-1; i >= 0; i--) { //remove from the end to not change the indexes
            if(function.apply(found.get(indexes[i]).path)){
                found.remove(indexes[i]);
                changed = true;
            }
        }
        return changed;
    }
    
    public boolean moveTo(String folder, int...indexes){
        if(indexes.length == 0) return false;
        
        Path destination = Path.of(folder);
        boolean movedOne = iteratePerforming(path -> {
            try {
                Files.move(path, destination.resolve(path.getFileName()));
                return true;
            } catch (IOException ex) {
                System.err.println("ERROR: failed moving file "+path+ "\n" + ex.getMessage());
                return false;
            }
        }, indexes);

        return movedOne;
    }
    
    public boolean moveTo(String folder, List<FileInfo> files){
        boolean changed = false;
        for (FileInfo file : files) {
            try {
                Files.move(file.path, Path.of(folder).resolve(file.filename));
                changed = true;
                found.remove(file);
            } catch (IOException ex) {
                System.err.println("ERROR: failed moving file "+file.path+ "\n" + ex.getMessage());
            }
        }
        return changed;
    }
    
    public boolean remove(List<FileInfo> files){
        boolean changed = false;
        for (FileInfo file : files) {
            try {
                Files.delete(file.path);
                found.remove(file);
                changed = true;
            } catch (IOException ex) {
                System.err.println("ERROR: failed deleting file "+file.path+ "\n" + ex.getMessage());
            }
        }
        return changed; 
    }
    
    public boolean remove(int...indexes){
        if(indexes.length == 0) return false;
        
        boolean removedOne = iteratePerforming(path -> {
            try {
                Files.delete(path);
                return true;
            } catch (IOException ex) {
                System.err.println("ERROR: failed deleting file "+path+ "\n" + ex.getMessage()); //TODO: add failed in info
                return false;
            }
        }, indexes);

        return removedOne;
    }

    @Override
    public void interrupt() {
        interrupted = true;
    }

//    @Override
//    public LinkedList<FileInfo> getResult() {
//        return found;
//    }
   
}
