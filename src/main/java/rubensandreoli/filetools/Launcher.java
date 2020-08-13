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
