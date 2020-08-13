/*
 * Copyright (C) 2020 Rubens A. Andreoli Jr.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package rubensandreoli.filetools.tools;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import rubensandreoli.commons.utils.FileUtils;
import rubensandreoli.filetools.tools.RegexSearchAction.SearchFile;
import rubensandreoli.filetools.tools.support.Size;

/** 
 * References:
 https://stackoverflow.com/questions/3263892/format-file-bytes-as-mb-gb-etc
 */
public class RegexSearchAction implements Action<LinkedList<SearchFile>>{
    
    // <editor-fold defaultstate="collapsed" desc=" STATIC FIELDS "> 
    public static final boolean DEFAULT_INCLUDE_SUBFOLDERS = true;
    public static final boolean DEFAULT_INCLUDE_FILES = false;
    public static final boolean DEFAULT_INCLUDE_FOLDERS = true;
    public static final String DEFAULT_REGEX = ".*";
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" SEARCH FILE "> 
    public static class SearchFile{
        
        public final String name;
        public final Path path;
        public final Size size;

        private SearchFile(String filename, Path path, long size){
            this.name = filename;
            this.path = path;
            this.size = new Size(size);
        }
        
        private SearchFile(String filename, Path path) {
            this.name = filename;
            this.path = path;
            size = null;
        }
        
        public boolean isFolder(){
            return size == null;
        }

        @Override
        public String toString() {
            return path.toString();
        }

    }
    // </editor-fold>
    
    private final Path folder;
    private String regex = DEFAULT_REGEX;
    private boolean subfolders = DEFAULT_INCLUDE_SUBFOLDERS;
    private boolean files = DEFAULT_INCLUDE_FILES;
    private boolean folders = DEFAULT_INCLUDE_FOLDERS;
    
    private LinkedList<SearchFile> found = new LinkedList<>();
    private volatile boolean interrupted; 

    public RegexSearchAction(String folder, String regex) {
        this.folder = Path.of(folder);
        if(regex != null && !regex.isEmpty()){
            this.regex = regex;
        }
    }

    @Override
    public LinkedList<SearchFile> perform() {
        try {
            Files.walkFileTree(folder, new SimpleFileVisitor<Path>(){
                
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if(interrupted) return FileVisitResult.TERMINATE;
                    if(folder.equals(dir)) return FileVisitResult.CONTINUE;
                    if(folders){
                        final String foldername = dir.getFileName().toString();
                        if(foldername.matches(regex)){
                            found.add(new SearchFile(foldername, dir));
                        }
                    }
                    return subfolders? FileVisitResult.CONTINUE:FileVisitResult.SKIP_SUBTREE;
                }
                
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if(interrupted) return FileVisitResult.TERMINATE;
                    if(files){
                        final String filename = file.getFileName().toString();
                        if(filename.matches(regex)){
                            found.add(new SearchFile(filename, file, FileUtils.getFileSize(file.toFile())));
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    System.err.println("Error accessing file: " +file.toAbsolutePath()+" -> "+ exc.getMessage()); //TODO: do what then?
                    return interrupted? FileVisitResult.TERMINATE:FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
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
    
    public boolean moveTo(String folder, List<SearchFile> files){
        boolean changed = false;
        for (SearchFile file : files) {
            try {
                Files.move(file.path, Path.of(folder).resolve(file.name));
                changed = true;
                found.remove(file);
            } catch (IOException ex) {
                System.err.println("ERROR: failed moving file "+file.path+ "\n" + ex.getMessage());
            }
        }
        return changed;
    }
    
    public boolean delete(List<SearchFile> files){
        boolean changed = false;
        for (SearchFile file : files) {
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
    
    public boolean delete(int...indexes){
        if(indexes.length == 0) return false;
        
        boolean deletedOne = iteratePerforming(path -> {
            try {
                Files.delete(path);
                return true;
            } catch (IOException ex) {
                System.err.println("ERROR: failed deleting file "+path+ "\n" + ex.getMessage()); //TODO: add failed in info
                return false;
            }
        }, indexes);

        return deletedOne;
    }

    @Override
    public void interrupt() {
        interrupted = true;
    }

    // <editor-fold defaultstate="collapsed" desc=" SETTERS "> 
    public void setFilters(boolean subfolders, boolean files, boolean folders) {
        this.subfolders = subfolders;
        this.files = files;
        this.folders = folders;
    }
    // </editor-fold>
   
}
