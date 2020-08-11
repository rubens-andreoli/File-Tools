package rubensandreoli.filetools.gui;

import java.awt.event.KeyEvent;
import javax.swing.table.AbstractTableModel;
import rubensandreoli.filetools.tools.SplitAction;

public class SplitterPanel extends ToolPanel{
    private static final long serialVersionUID = 1L;

    private SplitAction splitter;
    private final Model model = new Model();

    // <editor-fold defaultstate="collapsed" desc=" MODEL "> 
    private class Model extends AbstractTableModel{
        private static final long serialVersionUID = 1L;

        private String[] headers = new String[]{"Filename", "Subfolder"};
        
        @Override
        public int getRowCount() {
            return splitter==null? 0:splitter.getFileCount();
        }

        @Override
        public int getColumnCount() {
            return 2;
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
                case 1:
                    return String.class;
                default:
                    return null;
            }
        } 
        
        @Override
        public Object getValueAt(int row, int column) {
            switch(column){
                case 0:
                    return splitter.getFile(row);
                case 1:
                    return splitter.getFolder(row);
                default:
                    return splitter.getFiles();
            }
        }
    }
    // </editor-fold>

    public SplitterPanel() {
        super("Split");
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnFolder = new javax.swing.JButton();
        txfFolder = new rubensandreoli.commons.swing.PathField();
        lblIndex = new javax.swing.JLayeredPane();
        spnStart = new javax.swing.JSpinner();
        spnEnd = new javax.swing.JSpinner();
        lblFilter = new javax.swing.JLayeredPane();
        txfRegex = new javax.swing.JTextField();
        btnApply = new javax.swing.JButton();
        pnlFiles = new javax.swing.JPanel();
        sclFiles = new javax.swing.JScrollPane();
        tblFiles = new javax.swing.JTable();

        btnFolder.setText("Folder");
        btnFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFolderActionPerformed(evt);
            }
        });

        lblIndex.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(0, -2, 0, 0), javax.swing.BorderFactory.createTitledBorder("Indexes")));

        spnStart.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnStartStateChanged(evt);
            }
        });

        spnEnd.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnEndStateChanged(evt);
            }
        });

        lblIndex.setLayer(spnStart, javax.swing.JLayeredPane.DEFAULT_LAYER);
        lblIndex.setLayer(spnEnd, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout lblIndexLayout = new javax.swing.GroupLayout(lblIndex);
        lblIndex.setLayout(lblIndexLayout);
        lblIndexLayout.setHorizontalGroup(
            lblIndexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblIndexLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spnEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        lblIndexLayout.setVerticalGroup(
            lblIndexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lblIndexLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(lblIndexLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spnStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        lblFilter.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, -3), javax.swing.BorderFactory.createTitledBorder("Regex filter")));

        txfRegex.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txfRegexKeyTyped(evt);
            }
        });

        btnApply.setText("Apply");
        btnApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApplyActionPerformed(evt);
            }
        });

        lblFilter.setLayer(txfRegex, javax.swing.JLayeredPane.DEFAULT_LAYER);
        lblFilter.setLayer(btnApply, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout lblFilterLayout = new javax.swing.GroupLayout(lblFilter);
        lblFilter.setLayout(lblFilterLayout);
        lblFilterLayout.setHorizontalGroup(
            lblFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lblFilterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txfRegex)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnApply)
                .addContainerGap())
        );
        lblFilterLayout.setVerticalGroup(
            lblFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lblFilterLayout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(txfRegex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4))
            .addComponent(btnApply)
        );

        pnlFiles.setLayout(new java.awt.BorderLayout());

        tblFiles.setModel(model);
        sclFiles.setViewportView(tblFiles);

        pnlFiles.add(sclFiles, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlFiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(lblFilter))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnFolder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txfFolder, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)))
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
                    .addComponent(lblFilter)
                    .addComponent(lblIndex))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFiles, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFolderActionPerformed
        if(txfFolder.select(this)){
            splitter = new SplitAction(txfFolder.getText());
            splitter.setStartIndex((Integer)spnStart.getValue());
            splitter.setEndIndex((Integer)spnEnd.getValue());
            model.fireTableDataChanged();
        }
    }//GEN-LAST:event_btnFolderActionPerformed

    private void spnStartStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnStartStateChanged
        if(splitter != null){
            splitter.setStartIndex((Integer)spnStart.getValue());
            model.fireTableDataChanged();
        }
    }//GEN-LAST:event_spnStartStateChanged

    private void btnApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApplyActionPerformed
        if(splitter != null) {
            splitter.filterFiles(txfRegex.getText());
            model.fireTableDataChanged();
        }
    }//GEN-LAST:event_btnApplyActionPerformed

    private void spnEndStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnEndStateChanged
        if(splitter != null){
            splitter.setEndIndex((Integer)spnEnd.getValue());
            model.fireTableDataChanged();
        }
    }//GEN-LAST:event_spnEndStateChanged

    private void txfRegexKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txfRegexKeyTyped
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            btnApplyActionPerformed(null);
        }
    }//GEN-LAST:event_txfRegexKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApply;
    private javax.swing.JButton btnFolder;
    private javax.swing.JLayeredPane lblFilter;
    private javax.swing.JLayeredPane lblIndex;
    private javax.swing.JPanel pnlFiles;
    private javax.swing.JScrollPane sclFiles;
    private javax.swing.JSpinner spnEnd;
    private javax.swing.JSpinner spnStart;
    private javax.swing.JTable tblFiles;
    private rubensandreoli.commons.swing.PathField txfFolder;
    private javax.swing.JTextField txfRegex;
    // End of variables declaration//GEN-END:variables

    @Override
    public void start() {
        if(splitter != null){
            doInBackgroud(() -> {
                return splitter.perform();
            }, b -> {
                if(b) model.fireTableDataChanged();
            });
        }
    }

    @Override
    public void stop() {
        super.stop(false);
        if(splitter != null){
            splitter.interrupt();
            model.fireTableDataChanged();
        }
    }

    @Override
    public Integer getMnemonic() {
        return KeyEvent.VK_P;
    }

}
