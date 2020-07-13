package rubensandreoli.filetools.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SplitAction implements Action<Boolean>{

    private final String folder;
    private final List<File> files;
    private List<File> filteredFiles;
    private int startIndex, endIndex;
    private volatile boolean interrupted; 
    
    public SplitAction(String folder){
        this.folder = folder;
        files = new LinkedList(Arrays.asList(new File(folder).listFiles()));
        filteredFiles = files;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
    
    public void filterFiles(String regex){
        if(regex == null || regex.isBlank() || regex.equals(".*")){
            filteredFiles = files;
        }else{
            filteredFiles = new ArrayList<>();
            for (File file : files) {
                if(file.getName().matches(regex))
                    filteredFiles.add(file);
            }
        }
    }
    
    public List<File> getFiles(){
        return Collections.unmodifiableList(filteredFiles);
    }
    
    public int getFileCount(){
        return filteredFiles.size();
    }

    public String getFile(int index){
        return files.get(index).getName();
    }
    
    public String getFolder(int index){
        String subfolder = "";
        if(startIndex >= 0 && endIndex > startIndex && index < files.size()){
            String filename = files.get(index).getName();
            if(endIndex < filename.length()){
                subfolder = filename.substring(startIndex, endIndex);
            }
        }
        return subfolder;
    }
    
    private String getFolder(String filename){
        if(startIndex >= 0 && endIndex > startIndex && endIndex < filename.length()){
            return filename.substring(startIndex, endIndex);
        }else{
            return null;
        }
    }

    @Override
    public Boolean perform() {
        Iterator<File> i = filteredFiles.iterator();
        boolean moved = false;
        while(!interrupted && i.hasNext()){
            File file = i.next();
            String folderName = getFolder(file.getName());
            if(folderName == null) continue;
            File subfolder = new File(folder, folderName);
            try{
                if(!subfolder.isDirectory()) subfolder.mkdir();
                if(file.renameTo(new File(subfolder, file.getName()))){
                    files.remove(file);
                    i.remove();
                    moved = true;
                }
            }catch(SecurityException ex){
                System.err.println("ERROR: failed moving file "+file+" -> "+ex.getMessage());
            }
        }
        return moved;
    }

    @Override
    public void interrupt() {
        interrupted = true;
    }
    
}
