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
                    ex.printStackTrace();
                }
            } 
        };
        worker.execute();
    }
    
    protected void stop(boolean interrupt){
        if(worker != null) worker.cancel(interrupt);
    }

}
