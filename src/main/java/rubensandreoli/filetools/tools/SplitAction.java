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
        String subfolder = null;
        if(index < files.size()){
            subfolder = getFolder(files.get(index).getName());
        }
        return subfolder!=null? subfolder:"";
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
        boolean moved = false;
        Iterator<File> i = filteredFiles.iterator();
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
