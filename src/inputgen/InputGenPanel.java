/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package inputgen;

import core.MainFrame;

/**
 * TODO !!!
 * @author yannick
 */
public class InputGenPanel extends javax.swing.JPanel {

    private MainFrame mf;
    
    private GeomDialog geomD;
    private AlCoDialog alcoD;
    private ReReDialog rereD;
    private WaDeDialog wadeD;
    private InOuDialog inouD;
    private TheoDialog theoD;
    
    
    /**
     * Creates new form InputPanel
     */
    public InputGenPanel() {
        initComponents();
    }
    
    public void setMainFrame(MainFrame mf)
    {
        this.mf = mf;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inputFileTabbedPane = new javax.swing.JTabbedPane();
        basicsScrollPane = new javax.swing.JScrollPane();
        basicsPanel = new javax.swing.JPanel();
        geometryButton = new javax.swing.JButton();
        algoAndConvButton = new javax.swing.JButton();
        realAndRecipButton = new javax.swing.JButton();
        wavefuncAndDensButton = new javax.swing.JButton();
        inputOutputButton = new javax.swing.JButton();
        theoryButton = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        otherTextArea = new javax.swing.JTextArea();
        emptyPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        inputFileTabbedPane.setEnabled(false);
        inputFileTabbedPane.setMaximumSize(new java.awt.Dimension(380, 550));
        inputFileTabbedPane.setMinimumSize(new java.awt.Dimension(380, 550));

        basicsScrollPane.setEnabled(false);
        basicsScrollPane.setMaximumSize(new java.awt.Dimension(352, 600));
        basicsScrollPane.setMinimumSize(new java.awt.Dimension(352, 600));
        basicsScrollPane.setPreferredSize(new java.awt.Dimension(352, 600));

        basicsPanel.setEnabled(false);
        basicsPanel.setMaximumSize(new java.awt.Dimension(241, 267));
        basicsPanel.setMinimumSize(new java.awt.Dimension(241, 267));

        geometryButton.setText("Geometry");
        geometryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geometryButtonActionPerformed(evt);
            }
        });

        algoAndConvButton.setText("Algorithm & convergence");
        algoAndConvButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                algoAndConvButtonActionPerformed(evt);
            }
        });

        realAndRecipButton.setText("Real & reciprocal space");
        realAndRecipButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                realAndRecipButtonActionPerformed(evt);
            }
        });

        wavefuncAndDensButton.setText("Wavefunctions & densities");
        wavefuncAndDensButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wavefuncAndDensButtonActionPerformed(evt);
            }
        });

        inputOutputButton.setText("Input / Output");
        inputOutputButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputOutputButtonActionPerformed(evt);
            }
        });

        theoryButton.setText("Theory (DFT)");
        theoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                theoryButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout basicsPanelLayout = new javax.swing.GroupLayout(basicsPanel);
        basicsPanel.setLayout(basicsPanelLayout);
        basicsPanelLayout.setHorizontalGroup(
            basicsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basicsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(basicsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(theoryButton, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addGroup(basicsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(inputOutputButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(wavefuncAndDensButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                        .addComponent(realAndRecipButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(algoAndConvButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(geometryButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        basicsPanelLayout.setVerticalGroup(
            basicsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basicsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(geometryButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(algoAndConvButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(realAndRecipButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wavefuncAndDensButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputOutputButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(theoryButton)
                .addContainerGap())
        );

        basicsScrollPane.setViewportView(basicsPanel);

        inputFileTabbedPane.addTab("Basics", basicsScrollPane);

        otherTextArea.setColumns(20);
        otherTextArea.setRows(5);
        jScrollPane5.setViewportView(otherTextArea);

        inputFileTabbedPane.addTab("For other variables", jScrollPane5);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("<HTML> <center> Select the <b>Use created input file</b><br>radiobutton to create graphicaly an input<br>file to send to an ABINIT host.  </HTML>");

        javax.swing.GroupLayout emptyPanelLayout = new javax.swing.GroupLayout(emptyPanel);
        emptyPanel.setLayout(emptyPanelLayout);
        emptyPanelLayout.setHorizontalGroup(
            emptyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(emptyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                .addContainerGap())
        );
        emptyPanelLayout.setVerticalGroup(
            emptyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(emptyPanelLayout.createSequentialGroup()
                .addGap(133, 133, 133)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        inputFileTabbedPane.addTab("", emptyPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 369, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inputFileTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 296, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inputFileTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void geometryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_geometryButtonActionPerformed
        geomD.setVisible(true);
    }//GEN-LAST:event_geometryButtonActionPerformed

    private void algoAndConvButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_algoAndConvButtonActionPerformed
        alcoD.setVisible(true);
    }//GEN-LAST:event_algoAndConvButtonActionPerformed

    private void realAndRecipButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_realAndRecipButtonActionPerformed
        rereD.setVisible(true);
    }//GEN-LAST:event_realAndRecipButtonActionPerformed

    private void wavefuncAndDensButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wavefuncAndDensButtonActionPerformed
        wadeD.setVisible(true);
    }//GEN-LAST:event_wavefuncAndDensButtonActionPerformed

    private void inputOutputButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputOutputButtonActionPerformed
        inouD.setVisible(true);
    }//GEN-LAST:event_inputOutputButtonActionPerformed

    private void theoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_theoryButtonActionPerformed
        theoD.setVisible(true);
    }//GEN-LAST:event_theoryButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton algoAndConvButton;
    private javax.swing.JPanel basicsPanel;
    private javax.swing.JScrollPane basicsScrollPane;
    private javax.swing.JPanel emptyPanel;
    private javax.swing.JButton geometryButton;
    private javax.swing.JTabbedPane inputFileTabbedPane;
    private javax.swing.JButton inputOutputButton;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextArea otherTextArea;
    private javax.swing.JButton realAndRecipButton;
    private javax.swing.JButton theoryButton;
    private javax.swing.JButton wavefuncAndDensButton;
    // End of variables declaration//GEN-END:variables
}
