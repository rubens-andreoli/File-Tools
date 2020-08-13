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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.function.BiPredicate;
import rubensandreoli.commons.utils.FileUtils;
import rubensandreoli.filetools.tools.FolderSearchAction.SearchFolder;
import rubensandreoli.filetools.tools.support.Size;

public class FolderSearchAction implements Action<Void> {

    // <editor-fold defaultstate="collapsed" desc=" CONDITIONS & OPERATORS "> 
    public enum Condition{FILES, FOLDERS, MBYTES}
    public static enum Operator{
        EQUAL((d, t) -> d.equals(t)), 
        LESS((d, t) -> d < t), 
        GREATER((d, t) -> d > t);
    
        public final BiPredicate<Long, Long> condition;

        private Operator(BiPredicate<Long, Long> condition) {
            this.condition = condition;
        }
  
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" SEARCH FOLDER "> 
    public class SearchFolder{
    
        public final Path path;
        private List<SearchFolder> subfolders = new ArrayList<>();
        private List<Path> files = new ArrayList<>();
        private long size;

        public SearchFolder(Path path) {
            this.path = path;
        }
        
        private void addFile(Path path){
            files.add(path);
        }
        
        private void addFile(Path path, long size){
            this.addFile(path);
            this.size += size;
        }
        
        private void addFolder(SearchFolder folder){
            this.subfolders.add(folder);
            this.size += folder.size;
        }

        @Override
        public String toString() {
            return "."+path.toString().substring(folder.toString().length());
        }

        public Size getSize() {
            return new Size(size);
        }

        public int getNumSubfolders() {
            return subfolders.size();
        }

        public int getNumFiles() {
            return files.size();
        }
        
    }
    // </editor-fold>
    
    private final Path folder;
    private LinkedList<SearchFolder> found = new LinkedList<>();   
    private volatile boolean interrupted; 
    
    public FolderSearchAction(String folder){
        this.folder = Path.of(folder);
    }
    
    @Override
    public Void perform() {
        try {
            Files.walkFileTree(folder, new SimpleFileVisitor<Path>(){
                
                private Stack<SearchFolder> stack = new Stack<>();
                
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    final SearchFolder current = new SearchFolder(dir);
                    stack.add(current);
                    found.add(current);
                    return interrupted? FileVisitResult.TERMINATE:FileVisitResult.CONTINUE;
                }
                
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    stack.peek().addFile(file, FileUtils.getFileSize(file.toFile()));
                    return interrupted? FileVisitResult.TERMINATE:FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    System.err.println("Error accessing file: " +file.toAbsolutePath()+" -> "+ exc.getMessage());
                    stack.peek().addFile(file); //TODO: add anyway?
                    return interrupted? FileVisitResult.TERMINATE:FileVisitResult.CONTINUE;
                }
                
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    SearchFolder current = stack.pop();
                    if(!stack.isEmpty()) {
                        SearchFolder parent =  stack.peek();
                        parent.addFolder(current);
                    }
                    return interrupted? FileVisitResult.TERMINATE:FileVisitResult.CONTINUE;
                }
                
            });
        } catch (IOException ex) {
            System.err.println("ERROR: failed searching folder " + ex.getMessage());
        }
        return null;
    }

    @Override
    public void interrupt() {
        interrupted = true;
    }
    
    public List<SearchFolder> getFilteredList(Condition c, Operator o, long value){
        List<SearchFolder> filtered = new ArrayList<>();
        if(c == Condition.MBYTES) value *= 1_048_576L;
        for (SearchFolder folder : found) {
            long folderValue = -1;
            switch(c){
                case FILES:
                    folderValue = (long)folder.files.size();
                    break;
                case FOLDERS:
                    folderValue = (long)folder.subfolders.size();
                    break;
                case MBYTES:
                    folderValue = folder.size;
                    break;
            }
            if(o.condition.test(folderValue, value)) filtered.add(folder);
        }
        return filtered;
    }

}
