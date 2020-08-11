package rubensandreoli.filetools.tools;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import rubensandreoli.filetools.tools.FolderSearchAction.SearchFolder;

public class FolderSearchAction implements Action<LinkedList<SearchFolder>> {

    public enum Condition{FILES, KBYTES, MBYTES}
    public enum Operator{EQUAL, LESS, GREATER}
    
    public static class SearchFolder{
    
    }
    
    private Path folder;
    private int value;
    private Condition condition;
    private Operator operator;
    
    private LinkedList<SearchFolder> found = new LinkedList<>();
    private volatile boolean interrupted; 
    
    public FolderSearchAction(String folder, int value, Condition condition, Operator operator){
        this.folder = Path.of(folder);
        this.value = value;
        this.condition = condition!=null? condition : Condition.FILES;
        this.operator = operator!=null? operator : Operator.EQUAL;
    }
    
    @Override
    public LinkedList<SearchFolder> perform() {
        try {
            Files.walkFileTree(folder, new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    return super.postVisitDirectory(dir, exc); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return super.visitFileFailed(file, exc); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    return super.visitFile(file, attrs); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    return super.preVisitDirectory(dir, attrs); //To change body of generated methods, choose Tools | Templates.
                }
                
            });
        } catch (IOException ex) {
            System.err.println("ERROR: failed searching folder " + ex.getMessage());
        }
        return found;
    }

    @Override
    public void interrupt() {
        interrupted = true;
    }
    
}
