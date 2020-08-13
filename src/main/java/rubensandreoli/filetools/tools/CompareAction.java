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
import java.util.Set;

public class CompareAction implements Action<Void>{

    // <editor-fold defaultstate="collapsed" desc=" COMPARATOR FILE "> 
    public class ComparatorFile {

        private String filename;
        private String name;
        private Set<String> words;
        private final String absolutePath;

        public ComparatorFile(File file) {
            absolutePath = file.getAbsolutePath();
            filename = file.getName();
//            words = StringUtils.splitWords(name);
        }

        public boolean deleteFile(){
            File file = new File(absolutePath);
            return (file.exists() && file.delete());
        }

        public String getName() {
            return name;
        }

        public String getFilename() {
            return filename;
        }

        public Set<String> getWords() {
            return words;
        }

        public String getAbsolutePath() {
            return absolutePath;
        }

    }
    // </editor-fold>
    
    
    @Override
    public Void perform() {
        return null;
    }

    @Override
    public void interrupt() {
        
    }
    
}
