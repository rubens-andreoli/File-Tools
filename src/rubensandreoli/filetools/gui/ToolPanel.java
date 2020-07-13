package rubensandreoli.filetools.gui;

import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.swing.SwingWorker;

/** References:
 * https://stackoverflow.com/questions/2723397/what-is-pecs-producer-extends-consumer-super
 * https://stackoverflow.com/questions/17390441/forcing-java-generic-parameters-to-be-of-the-same-type
 * https://www.baeldung.com/java-8-functional-interfaces
 */
public abstract class ToolPanel extends javax.swing.JPanel {

    private final String title;
    private SwingWorker worker;

    public ToolPanel(final String title) {
        this.title = title;
    }

    public abstract void start();
    public abstract void stop();
    
    public Integer getMnemonic(){
        return null;
    }
    
    public String getTitle(){
        return title;
    }
    
    protected <T> void doInBackgroud(Supplier<T> action, Consumer<? super T> done){
        worker = new SwingWorker<T, Void>(){
            @Override
            protected T doInBackground() throws Exception {
                return action.get();
            }

            @Override
            protected void done() {
                try {
                    done.accept(get());
                } catch (InterruptedException | ExecutionException ex) {
                    System.err.println("ERROR: worker interrupted " + ex.getMessage());
                }
            } 
        };
        worker.execute();
    }
    
    protected void stop(boolean interrupt){
        if(worker != null) worker.cancel(interrupt);
    }

}
