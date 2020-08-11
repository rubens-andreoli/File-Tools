package rubensandreoli.filetools;

import javax.swing.SwingUtilities;
import rubensandreoli.filetools.gui.ComparatorPanel;
import rubensandreoli.filetools.gui.FileTools;
import rubensandreoli.filetools.gui.FolderSearchPanel;
import rubensandreoli.filetools.gui.RegexSearchPanel;
import rubensandreoli.filetools.gui.SplitterPanel;

public class Launcher {

        public static void main(String args[]) {
            
        //<editor-fold defaultstate="collapsed" desc=" Look and Feel ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {}
        //</editor-fold>

        FileTools view = new FileTools();
        view.addToolPanel(new RegexSearchPanel());
        view.addToolPanel(new FolderSearchPanel());
        view.addToolPanel(new SplitterPanel());
        view.addToolPanel(new ComparatorPanel());
        
        SwingUtilities.invokeLater(() -> view.setVisible(true));

    }
    
}
