package rubensandreoli.filetools.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableStringConverter;
import rubensandreoli.commons.swing.PathField;
import rubensandreoli.filetools.tools.RegexSearchAction;
import rubensandreoli.filetools.tools.RegexSearchAction.SearchFile;
import rubensandreoli.filetools.tools.RegexSearchAction.SearchFile.Size;

/** 
 * References:
 * https://stackoverflow.com/questions/550329/how-to-open-a-file-with-the-default-associated-program
 * https://stackoverflow.com/questions/5673430/java-jtable-change-cell-color
 * https://stackoverflow.com/questions/5970765/java-detect-ctrlx-key-combination-on-a-jtree
 * 
 */
public class RegexSearchPanel extends  ToolPanel{ //TODO: implement filters
    private static final long serialVersionUID = 1L;

    private RegexSearchAction action;
    private List<SearchFile> files;
    private final Model model = new Model();
    private final TableRowSorter<Model> sorter;

    // <editor-fold defaultstate="collapsed" desc=" MODEL "> 
    private class Model extends AbstractTableModel{
        private static final long serialVersionUID = 1L;

        private String[] headers = new String[]{"Filename", "Size", "Path"};
        
        @Override
        public int getRowCount() {
            return files==null? 0:files.size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public String getColumnName(int column) {
            return headers[column];
        }

        @Override
        public Class<?> getColumnClass(int column) {
            switch(column){
                case 0:
                    return SearchFile.class;
                case 1:
                    return SearchFile.Size.class;
                case 2:
                    return Path.class;
                default:
                    return null;
            }
        } 
        
        @Override
        public Object getValueAt(int row, int column) {
            SearchFile info = files.get(row); //FIX: out of bounds when deleting/moving; remove table first? disable table?
            switch(column){
                case 0:
                    return info;
                case 1:
                    return info.size;
                case 2:
                    return info.path;
                default:
                    return null;
            }
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" RENDERERS "> 
    public static class SizeCellRenderer extends DefaultTableCellRenderer{
        private static final long serialVersionUID = 1L;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return super.getTableCellRendererComponent(table, ((Size)value).text, isSelected, hasFocus, row, column);
        }

    } 
    
    public static class FileCellRenderer extends DefaultTableCellRenderer{
        private static final long serialVersionUID = 1L;

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return super.getTableCellRendererComponent(table, ((SearchFile)value).name, isSelected, hasFocus, row, column);
        }
        
    }
    // </editor-fold>
    
    public RegexSearchPanel() {
        super("Regex Search");
        initComponents();
        tblFiles.setDefaultRenderer(SearchFile.class, new FileCellRenderer());
        tblFiles.setDefaultRenderer(Size.class, new SizeCellRenderer());
        sorter = new TableRowSorter<>((Model)tblFiles.getModel());
        tblFiles.setRowSorter(sorter); //TODO: sort view or model?
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popTable = new javax.swing.JPopupMenu();
        itmSelectAll = new javax.swing.JMenuItem();
        itmDeselectAll = new javax.swing.JMenuItem();
        itmSeparator1 = new javax.swing.JPopupMenu.Separator();
        itmMove = new javax.swing.JMenuItem();
        itmDelete = new javax.swing.JMenuItem();
        itmSeparator2 = new javax.swing.JPopupMenu.Separator();
        mnuSort = new javax.swing.JMenu();
        itmFilename = new javax.swing.JMenuItem();
        itmSize = new javax.swing.JMenuItem();
        itmPath = new javax.swing.JMenuItem();
        btnFolder = new javax.swing.JButton();
        txfFolder = new PathField(PathField.DIRECTORIES_ONLY, 60);
        pnlRegex = new javax.swing.JPanel();
        txfRegex = new javax.swing.JTextField();
        pnlFilters = new javax.swing.JPanel();
        chkFile = new javax.swing.JCheckBox();
        chkFolder = new javax.swing.JCheckBox();
        chkSubfolders = new javax.swing.JCheckBox();
        sclFiles = new javax.swing.JScrollPane();
        tblFiles = new javax.swing.JTable();

        itmSelectAll.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        itmSelectAll.setText("Select All");
        itmSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmSelectAllActionPerformed(evt);
            }
        });
        popTable.add(itmSelectAll);

        itmDeselectAll.setText("Deselect All");
        itmDeselectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmDeselectAllActionPerformed(evt);
            }
        });
        popTable.add(itmDeselectAll);
        popTable.add(itmSeparator1);

        itmMove.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        itmMove.setText("Move to...");
        itmMove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmMoveActionPerformed(evt);
            }
        });
        popTable.add(itmMove);

        itmDelete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        itmDelete.setText("Delete");
        itmDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmDeleteActionPerformed(evt);
            }
        });
        popTable.add(itmDelete);
        popTable.add(itmSeparator2);

        mnuSort.setText("Sort");

        itmFilename.setText("Filename");
        itmFilename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmFilenameActionPerformed(evt);
            }
        });
        mnuSort.add(itmFilename);

        itmSize.setText("Size");
        itmSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmSizeActionPerformed(evt);
            }
        });
        mnuSort.add(itmSize);

        itmPath.setText("Path");
        itmPath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmPathActionPerformed(evt);
            }
        });
        mnuSort.add(itmPath);

        popTable.add(mnuSort);

        btnFolder.setText("Folder");
        btnFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFolderActionPerformed(evt);
            }
        });

        pnlRegex.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(0, -2, 0, 0), javax.swing.BorderFactory.createTitledBorder("Regex")));

        javax.swing.GroupLayout pnlRegexLayout = new javax.swing.GroupLayout(pnlRegex);
        pnlRegex.setLayout(pnlRegexLayout);
        pnlRegexLayout.setHorizontalGroup(
            pnlRegexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegexLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txfRegex, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlRegexLayout.setVerticalGroup(
            pnlRegexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegexLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(txfRegex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4))
        );

        pnlFilters.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, -3), javax.swing.BorderFactory.createTitledBorder("Filters")));

        chkFile.setSelected(true);
        chkFile.setText("Files");
        chkFile.setMargin(new java.awt.Insets(0, 2, 0, 2));

        chkFolder.setSelected(true);
        chkFolder.setText("Folders");
        chkFolder.setMargin(new java.awt.Insets(0, 2, 0, 2));

        chkSubfolders.setSelected(true);
        chkSubfolders.setText("Subfolders");
        chkSubfolders.setToolTipText("");
        chkSubfolders.setMargin(new java.awt.Insets(0, 2, 0, 2));

        javax.swing.GroupLayout pnlFiltersLayout = new javax.swing.GroupLayout(pnlFilters);
        pnlFilters.setLayout(pnlFiltersLayout);
        pnlFiltersLayout.setHorizontalGroup(
            pnlFiltersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFiltersLayout.createSequentialGroup()
                .addComponent(chkSubfolders)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkFolder)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlFiltersLayout.setVerticalGroup(
            pnlFiltersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFiltersLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(pnlFiltersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(chkSubfolders)
                    .addComponent(chkFolder)
                    .addComponent(chkFile))
                .addGap(4, 4, 4))
        );

        tblFiles.setModel(model);
        tblFiles.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblFiles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblFilesMouseClicked(evt);
            }
        });
        tblFiles.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblFilesKeyReleased(evt);
            }
        });
        sclFiles.setViewportView(tblFiles);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sclFiles)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnFolder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txfFolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(pnlRegex, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(pnlFilters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlRegex, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlFilters, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sclFiles, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFolderActionPerformed
        txfFolder.select(this);
    }//GEN-LAST:event_btnFolderActionPerformed

    private void tblFilesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblFilesMouseClicked
        tblFiles.requestFocus();
        if(evt.getButton() == MouseEvent.BUTTON3){
            popTable.show(tblFiles, evt.getX(), evt.getY());
        }else if(evt.getButton() == MouseEvent.BUTTON1 && evt.getClickCount() == 2){
            final SearchFile searchFile = getClicked(evt.getX(), evt.getY());
            try {
                Desktop.getDesktop().open(searchFile.path.toFile());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                    "Error",
                    "Failed oppening file "+searchFile.path,
                    JOptionPane.INFORMATION_MESSAGE
                );
                System.err.println(ex.getMessage()); //FIX: remove debugging
            }
        }
    }//GEN-LAST:event_tblFilesMouseClicked

    private void itmMoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmMoveActionPerformed
        final String destination = PathField.select(this, PathField.DIRECTORIES_ONLY);
        final int selectedCount = tblFiles.getSelectedRowCount();
        if(destination != null && selectedCount > 0){
            if(JOptionPane.showConfirmDialog(
                    this, 
                    "Move "+selectedCount+" selected file(s) to: "+ destination+" ?", 
                    "Move",
                    JOptionPane.OK_CANCEL_OPTION, 
                    JOptionPane.QUESTION_MESSAGE
            ) == JOptionPane.CANCEL_OPTION) return;
            doInBackgroud(() -> action.moveTo(destination, getSelected()), b -> {
                if(b) model.fireTableDataChanged();
            });
        }
    }//GEN-LAST:event_itmMoveActionPerformed

    private void itmDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmDeleteActionPerformed
        final int selectedCount = tblFiles.getSelectedRowCount();
        if(selectedCount > 0){
            if(JOptionPane.showConfirmDialog(
                    this, 
                    "Delete "+selectedCount+" selected file(s) permanently?", 
                    "Delete",
                    JOptionPane.OK_CANCEL_OPTION, 
                    JOptionPane.QUESTION_MESSAGE
            ) == JOptionPane.CANCEL_OPTION) return;
            doInBackgroud(() -> {
                tblFiles.setEnabled(false);
                return action.delete(getSelected());
            }, deleted -> {
                if(deleted) model.fireTableDataChanged();
                tblFiles.setEnabled(true);
            });
        }
    }//GEN-LAST:event_itmDeleteActionPerformed

    private void itmSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmSelectAllActionPerformed
        tblFiles.selectAll();
    }//GEN-LAST:event_itmSelectAllActionPerformed

    private void itmDeselectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmDeselectAllActionPerformed
        tblFiles.clearSelection();
    }//GEN-LAST:event_itmDeselectAllActionPerformed

    private void tblFilesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblFilesKeyReleased
        switch(evt.getKeyCode()){
            case KeyEvent.VK_DELETE:
                itmDeleteActionPerformed(null);
                break;
            case KeyEvent.VK_M:
                if(evt.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK) itmMoveActionPerformed(null);
                break;
            case KeyEvent.VK_SPACE: //FIX: remove debbuging
                getSelected();
                break;
        }
    }//GEN-LAST:event_tblFilesKeyReleased

    private void itmFilenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmFilenameActionPerformed
        sorter.toggleSortOrder(0);
    }//GEN-LAST:event_itmFilenameActionPerformed

    private void itmSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmSizeActionPerformed
        sorter.toggleSortOrder(1);
    }//GEN-LAST:event_itmSizeActionPerformed

    private void itmPathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmPathActionPerformed
        sorter.toggleSortOrder(2);
    }//GEN-LAST:event_itmPathActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFolder;
    private javax.swing.JCheckBox chkFile;
    private javax.swing.JCheckBox chkFolder;
    private javax.swing.JCheckBox chkSubfolders;
    private javax.swing.JMenuItem itmDelete;
    private javax.swing.JMenuItem itmDeselectAll;
    private javax.swing.JMenuItem itmFilename;
    private javax.swing.JMenuItem itmMove;
    private javax.swing.JMenuItem itmPath;
    private javax.swing.JMenuItem itmSelectAll;
    private javax.swing.JPopupMenu.Separator itmSeparator1;
    private javax.swing.JPopupMenu.Separator itmSeparator2;
    private javax.swing.JMenuItem itmSize;
    private javax.swing.JMenu mnuSort;
    private javax.swing.JPanel pnlFilters;
    private javax.swing.JPanel pnlRegex;
    private javax.swing.JPopupMenu popTable;
    private javax.swing.JScrollPane sclFiles;
    private javax.swing.JTable tblFiles;
    private rubensandreoli.commons.swing.PathField txfFolder;
    private javax.swing.JTextField txfRegex;
    // End of variables declaration//GEN-END:variables

    @Override
    public void start() {
        doInBackgroud(() -> {
            action = new RegexSearchAction(txfFolder.getText(), txfRegex.getText());
            action.setFilters(chkSubfolders.isSelected(), chkFile.isSelected(), chkFolder.isSelected());
            return action.perform();
        }, list ->{
            files = list;
            model.fireTableDataChanged();
            if(files.isEmpty()){
                JOptionPane.showMessageDialog(RegexSearchPanel.this, 
                        "No files/folders were found with the regex: "+txfRegex.getText(), 
                        "Nothing Found", 
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });
    }

    @Override
    public void stop() {
        super.stop(false);
        if(action != null){
            action.interrupt();
        }
    }
    
    private SearchFile getClicked(int x, int y){
        int clickedRow = tblFiles.rowAtPoint(new Point(x, y));
        return (SearchFile) tblFiles.getValueAt(clickedRow, 0);
    }
    
    private List<SearchFile> getSelected(){
        List<SearchFile> list = new ArrayList<>();
        for (int i : tblFiles.getSelectedRows()) {
            list.add((SearchFile)tblFiles.getValueAt(i, 0));
        }
        return list;
    }
    
    @Override
    public Integer getMnemonic() {
        return KeyEvent.VK_R;
    }

}
