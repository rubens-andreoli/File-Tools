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
