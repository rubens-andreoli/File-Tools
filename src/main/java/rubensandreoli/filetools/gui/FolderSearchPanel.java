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

import java.awt.Desktop;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import rubensandreoli.commons.swing.PathField;
import rubensandreoli.filetools.tools.FolderSearchAction;
import rubensandreoli.filetools.tools.FolderSearchAction.Condition;
import rubensandreoli.filetools.tools.FolderSearchAction.Operator;
import rubensandreoli.filetools.tools.FolderSearchAction.SearchFolder;
import rubensandreoli.filetools.tools.support.Size;

public class FolderSearchPanel extends  ToolPanel{
    private static final long serialVersionUID = 1L;
    
    // <editor-fold defaultstate="collapsed" desc=" STATIC FIELDS "> 
    private static final String[] TABLE_COLUMNS = {"Relative Path", "Files", "Folders", "Size"};
    private static final int[] TABLE_COLUMNS_SIZES = {200, 60, 60, 60};
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" MODEL "> 
    private class Model extends AbstractTableModel{
        private static final long serialVersionUID = 1L;

        private List<SearchFolder> values;
        
        public void setValues(List<SearchFolder> data){
            this.values = data;
        }
        
        @Override
        public String getColumnName(int column) {
            return TABLE_COLUMNS[column];
        }

        @Override
        public int getRowCount() {
            return values == null? 0:values.size();
        }

        @Override
        public int getColumnCount() {
            return TABLE_COLUMNS.length;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch(columnIndex){
                case 0:
                    return SearchFolder.class;
                case 1:
                    return Integer.class;
                case 2:
                    return Integer.class;
                case 3:
                    return Size.class;
                default:
                    return null;
            }
        }
 
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            SearchFolder folder = values.get(rowIndex);
            switch(columnIndex){
                case 0:
                    return folder;
                case 1:
                    return folder.getNumFiles();
                case 2:
                    return folder.getNumSubfolders();
                case 3:
                    return folder.getSize();
                default:
                    return null;
            }
        }
    
    }
    // </editor-fold>
    
    private FolderSearchAction action;
    private Condition condition = Condition.FILES;
    private Operator operator = Operator.GREATER;
    private Model model = new Model();

    public FolderSearchPanel() {
        super("Folder Search");
        initComponents();
        for (int i = 0; i < TABLE_COLUMNS.length; i++) {
            tblFolders.getColumnModel().getColumn(i).setMinWidth(TABLE_COLUMNS_SIZES[i]);
            tblFolders.getColumnModel().getColumn(i).setPreferredWidth(TABLE_COLUMNS_SIZES[i]);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btgCondition = new javax.swing.ButtonGroup();
        btgOperator = new javax.swing.ButtonGroup();
        btnFolder = new javax.swing.JButton();
        txfFolder = new PathField(PathField.DIRECTORIES_ONLY, 60);
        pnlCondition = new javax.swing.JPanel();
        rbtConditionFiles = new javax.swing.JRadioButton();
        rbtConditionFolders = new javax.swing.JRadioButton();
        rbtConditionMBytes = new javax.swing.JRadioButton();
        pnlOperator = new javax.swing.JPanel();
        rbtOperatorEqual = new javax.swing.JRadioButton();
        rbtOperatorLess = new javax.swing.JRadioButton();
        rbtOperatorGreater = new javax.swing.JRadioButton();
        pnlValue = new javax.swing.JPanel();
        txfValue = new rubensandreoli.commons.swing.NumberField();
        sclFolders = new javax.swing.JScrollPane();
        tblFolders = new javax.swing.JTable();

        btnFolder.setText("Folder");
        btnFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFolderActionPerformed(evt);
            }
        });

        pnlCondition.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(0, -2, 0, 0), javax.swing.BorderFactory.createTitledBorder("Condition")));

        btgCondition.add(rbtConditionFiles);
        rbtConditionFiles.setSelected(true);
        rbtConditionFiles.setText("Files");
        rbtConditionFiles.setMargin(new java.awt.Insets(0, 2, 0, 2));
        rbtConditionFiles.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rbtConditionFilesItemStateChanged(evt);
            }
        });

        btgCondition.add(rbtConditionFolders);
        rbtConditionFolders.setText("Folders");
        rbtConditionFolders.setMargin(new java.awt.Insets(0, 2, 0, 2));
        rbtConditionFolders.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rbtConditionFoldersItemStateChanged(evt);
            }
        });

        btgCondition.add(rbtConditionMBytes);
        rbtConditionMBytes.setText("MBytes");
        rbtConditionMBytes.setMargin(new java.awt.Insets(0, 2, 0, 2));
        rbtConditionMBytes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rbtConditionMBytesItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout pnlConditionLayout = new javax.swing.GroupLayout(pnlCondition);
        pnlCondition.setLayout(pnlConditionLayout);
        pnlConditionLayout.setHorizontalGroup(
            pnlConditionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConditionLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(rbtConditionFiles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbtConditionFolders)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbtConditionMBytes)
                .addGap(0, 0, 0))
        );
        pnlConditionLayout.setVerticalGroup(
            pnlConditionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConditionLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(pnlConditionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtConditionFiles)
                    .addComponent(rbtConditionFolders)
                    .addComponent(rbtConditionMBytes))
                .addGap(4, 4, 4))
        );

        pnlOperator.setBorder(javax.swing.BorderFactory.createTitledBorder("Operator"));

        btgOperator.add(rbtOperatorEqual);
        rbtOperatorEqual.setText("Equal");
        rbtOperatorEqual.setMargin(new java.awt.Insets(0, 2, 0, 2));
        rbtOperatorEqual.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rbtOperatorEqualItemStateChanged(evt);
            }
        });

        btgOperator.add(rbtOperatorLess);
        rbtOperatorLess.setText("Less");
        rbtOperatorLess.setMargin(new java.awt.Insets(0, 2, 0, 2));
        rbtOperatorLess.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rbtOperatorLessItemStateChanged(evt);
            }
        });

        btgOperator.add(rbtOperatorGreater);
        rbtOperatorGreater.setSelected(true);
        rbtOperatorGreater.setText("Greater");
        rbtOperatorGreater.setMargin(new java.awt.Insets(0, 2, 0, 2));
        rbtOperatorGreater.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rbtOperatorGreaterItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout pnlOperatorLayout = new javax.swing.GroupLayout(pnlOperator);
        pnlOperator.setLayout(pnlOperatorLayout);
        pnlOperatorLayout.setHorizontalGroup(
            pnlOperatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOperatorLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(rbtOperatorEqual)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbtOperatorLess)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbtOperatorGreater)
                .addGap(0, 0, 0))
        );
        pnlOperatorLayout.setVerticalGroup(
            pnlOperatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOperatorLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(pnlOperatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtOperatorEqual)
                    .addComponent(rbtOperatorLess)
                    .addComponent(rbtOperatorGreater))
                .addGap(4, 4, 4))
        );

        pnlValue.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, -3), javax.swing.BorderFactory.createTitledBorder("Value")));

        txfValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txfValueKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlValueLayout = new javax.swing.GroupLayout(pnlValue);
        pnlValue.setLayout(pnlValueLayout);
        pnlValueLayout.setHorizontalGroup(
            pnlValueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlValueLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txfValue, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlValueLayout.setVerticalGroup(
            pnlValueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlValueLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(txfValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        tblFolders.setModel(model);
        tblFolders.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblFolders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFoldersMouseClicked(evt);
            }
        });
        sclFolders.setViewportView(tblFolders);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlCondition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(pnlOperator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(pnlValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnFolder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txfFolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(sclFolders))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFolder)
                    .addComponent(txfFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(pnlCondition, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlOperator, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sclFolders, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFolderActionPerformed
        txfFolder.select(this);
    }//GEN-LAST:event_btnFolderActionPerformed

    private void tblFoldersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFoldersMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 2){
            final SearchFolder SearchFolfer = getClicked(evt.getX(), evt.getY());
            try {
                Desktop.getDesktop().open(SearchFolfer.path.toFile());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                    "Error",
                    "Failed oppening folder "+SearchFolfer.path,
                    JOptionPane.ERROR_MESSAGE
                );
                System.err.println(ex.getMessage()); //TODO: remove debugging
            }
        }
    }//GEN-LAST:event_tblFoldersMouseClicked

    private void rbtConditionFilesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rbtConditionFilesItemStateChanged
        condition = Condition.FILES;
        filter();
    }//GEN-LAST:event_rbtConditionFilesItemStateChanged

    private void rbtConditionFoldersItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rbtConditionFoldersItemStateChanged
        condition = Condition.FOLDERS;
        filter();
    }//GEN-LAST:event_rbtConditionFoldersItemStateChanged

    private void rbtConditionMBytesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rbtConditionMBytesItemStateChanged
        condition = Condition.MBYTES;
        filter();
    }//GEN-LAST:event_rbtConditionMBytesItemStateChanged

    private void rbtOperatorEqualItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rbtOperatorEqualItemStateChanged
        operator = Operator.EQUAL;
        filter();
    }//GEN-LAST:event_rbtOperatorEqualItemStateChanged

    private void rbtOperatorLessItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rbtOperatorLessItemStateChanged
        operator = Operator.LESS;
        filter();
    }//GEN-LAST:event_rbtOperatorLessItemStateChanged

    private void rbtOperatorGreaterItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rbtOperatorGreaterItemStateChanged
        operator = Operator.GREATER;
        filter();
    }//GEN-LAST:event_rbtOperatorGreaterItemStateChanged

    private void txfValueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txfValueKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) filter();
    }//GEN-LAST:event_txfValueKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btgCondition;
    private javax.swing.ButtonGroup btgOperator;
    private javax.swing.JButton btnFolder;
    private javax.swing.JPanel pnlCondition;
    private javax.swing.JPanel pnlOperator;
    private javax.swing.JPanel pnlValue;
    private javax.swing.JRadioButton rbtConditionFiles;
    private javax.swing.JRadioButton rbtConditionFolders;
    private javax.swing.JRadioButton rbtConditionMBytes;
    private javax.swing.JRadioButton rbtOperatorEqual;
    private javax.swing.JRadioButton rbtOperatorGreater;
    private javax.swing.JRadioButton rbtOperatorLess;
    private javax.swing.JScrollPane sclFolders;
    private javax.swing.JTable tblFolders;
    private rubensandreoli.commons.swing.PathField txfFolder;
    private rubensandreoli.commons.swing.NumberField txfValue;
    // End of variables declaration//GEN-END:variables

    @Override
    public void start() {
        doInBackgroud(() -> {
            action = new FolderSearchAction(txfFolder.getText());
            return action.perform();
        }, n -> {
            filter();
        });
    }

    @Override
    public void stop() {
        super.stop(false);
        if(action != null) action.interrupt();
    }
    
    private void filter() {
        if(action != null){
            model.setValues(action.getFilteredList(condition, operator, txfValue.getInt()));
            model.fireTableDataChanged();
        }
    }

    private SearchFolder getClicked(int x, int y){
        return (SearchFolder) tblFolders.getValueAt(tblFolders.rowAtPoint(new Point(x, y)), 0);
    }
    
    @Override
    public Integer getMnemonic() {
        return KeyEvent.VK_F;
    }

}
