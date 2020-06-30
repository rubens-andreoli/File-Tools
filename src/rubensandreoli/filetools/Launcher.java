package rubensandreoli.filetools;

import javax.swing.SwingUtilities;
import rubensandreoli.filetools.gui.FileTools;

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

        SwingUtilities.invokeLater(() -> new FileTools().setVisible(true));

    }
    
}
