/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MDandTB;

import core.MainFrame;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import projects.Simulation;
import projects.SimulationPanel;

/**
 *
 * @author yannick
 */
public class TightBindingPanel extends SimulationPanel {

    private MainFrame mf;
    
    /**
     * Creates new form TightBindingPanel
     */
    public TightBindingPanel() {
        initComponents();
    }
    
    public void setMainFrame(MainFrame mf)
    {
        this.mf = mf;
    }
    
    @Override
    public void fillFromSimu(Simulation currentSimu) {
        if(currentSimu instanceof TightBindingSimulation)
        {
            TightBindingSimulation simu = (TightBindingSimulation)currentSimu;
            
            if(simu.getTypeGlobal() == TightBindingSimulation.TB_DOSKNEE1AND2)
            {
                useDK1AND2.setSelected(true);
                jRB_dos1.setSelected(simu.getTypeDosknee() == 1);
                jRB_dos2.setSelected(simu.getTypeDosknee() == 2);
                
                if(simu.isUseLANDM())
                {
                    RB_LM.setSelected(true);
                    LTextField.setText(""+simu.getL());
                    MTextField.setText(""+simu.getM());
                }
                else
                {
                    RB_notLM.setSelected(true);
                    if(simu.isGraphite())
                    {
                        jCheckBox_graphite.setSelected(true);
                    }
                    else
                    {
                        jCheckBox_pentahept.setSelected(true);
                    }
                }
            }
            else if(simu.getTypeGlobal() == TightBindingSimulation.TB_DOSKNEE3)
            {
                useDK3.setSelected(true);
                LTextField.setText(""+simu.getL());
                MTextField.setText(""+simu.getM());
            }
            else if(simu.getTypeGlobal() == TightBindingSimulation.TB_BNDSTR)
            {
                useBD.setSelected(true);
                openTBbndstrInputFileTextField.setText(simu.getTbenerFileName());
                if(simu.isUse66())
                {
                    jRB1.setSelected(true);
                }
                else if(simu.isUse110())
                {
                    jRB3.setSelected(true);
                }
                else if(simu.isUse120())
                {
                    jRB2.setSelected(true);
                }
            }
            
            sendTBCheckBox.setSelected(simu.isSendTBSources());
            compileTBCheckBox.setSelected(simu.isCompileTB());
            
        }
        else
        {
            throw new UnsupportedOperationException("Not able to tackle simulation");
        }
    }

    @Override
    public void fillSimu(Simulation currentSimu) {
        
        if(currentSimu instanceof TightBindingSimulation)
        {
            TightBindingSimulation simu = (TightBindingSimulation)currentSimu;
            
            if(useDK1AND2.isSelected())
            {
                simu.setTypeGlobal(TightBindingSimulation.TB_DOSKNEE1AND2);
                if(jRB_dos1.isSelected())
                {
                    simu.setTypeDosknee(1);
                }
                else if(jRB_dos2.isSelected())
                {
                    simu.setTypeDosknee(2);
                }
                
                if(RB_LM.isSelected())
                {
                    simu.setUseLANDM(true);
                    try{
                        simu.setL(Integer.parseInt(LTextField.getText()));
                        simu.setM(Integer.parseInt(MTextField.getText()));
                    }
                    catch(NumberFormatException exc)
                    {
                        mf.printERR("Please provide numbers for L & M");
                    }
                }
                else if(RB_notLM.isSelected())
                {
                    simu.setUseLANDM(false);
                    if(jCheckBox_graphite.isSelected())
                    {
                        simu.setGraphite(true);
                    }
                    else if(jCheckBox_pentahept.isSelected())
                    {
                        simu.setGraphite(false);
                    }
                }
            }
            else if(useDK3.isSelected())
            {
                simu.setTypeGlobal(TightBindingSimulation.TB_DOSKNEE3);
                
                try{
                    simu.setL(Integer.parseInt(LTextField.getText()));
                    simu.setM(Integer.parseInt(MTextField.getText()));
                }
                catch(NumberFormatException e)
                {
                    mf.printERR("Please provide numbers for L & M");
                }
            }
            else if(useBD.isSelected())
            {
                simu.setTypeGlobal(TightBindingSimulation.TB_BNDSTR);
                simu.setTbenerFileName(openTBbndstrInputFileTextField.getText());
                if(jRB1.isSelected())
                {
                    simu.setUse66(true);
                }
                else if(jRB3.isSelected())
                {
                    simu.setUse110(true);
                }
                else if(jRB2.isSelected())
                {
                    simu.setUse120(true);
                }
            }
            
            simu.setSendTBSources(sendTBCheckBox.isSelected());
            simu.setCompileTB(compileTBCheckBox.isSelected());
            
        }
        else
        {
            throw new UnsupportedOperationException("Not able to tackle simulation");
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        jLabel4 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        MTextField = new javax.swing.JTextField();
        MLabel = new javax.swing.JLabel();
        LTextField = new javax.swing.JTextField();
        LLabel = new javax.swing.JLabel();
        RB_LM = new javax.swing.JRadioButton();
        RB_notLM = new javax.swing.JRadioButton();
        jCheckBox_graphite = new javax.swing.JCheckBox();
        jCheckBox_pentahept = new javax.swing.JCheckBox();
        jRB_dos1 = new javax.swing.JRadioButton();
        jRB_dos2 = new javax.swing.JRadioButton();
        jPanel14 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        LLabel2 = new javax.swing.JLabel();
        LTextField2 = new javax.swing.JTextField();
        MLabel2 = new javax.swing.JLabel();
        MTextField2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        openClustepInputFileLabel4 = new javax.swing.JLabel();
        displayClustepInputFileButton3 = new javax.swing.JButton();
        geditClustepInputButton3 = new javax.swing.JButton();
        openClustepInputFileDialogButton3 = new javax.swing.JButton();
        openTBbndstrInputFileTextField = new javax.swing.JTextField();
        jRB1 = new javax.swing.JRadioButton();
        jRB2 = new javax.swing.JRadioButton();
        jRB3 = new javax.swing.JRadioButton();
        compileTBCheckBox = new javax.swing.JCheckBox();
        sendTBCheckBox = new javax.swing.JCheckBox();
        useDK1AND2 = new javax.swing.JRadioButton();
        useDK3 = new javax.swing.JRadioButton();
        useBD = new javax.swing.JRadioButton();

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel4.setText("Density of states (DOS)");

        jPanel20.setBackground(new java.awt.Color(0, 153, 153));

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel3.setText("DOSKNEE");

        MTextField.setText("3");

        MLabel.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        MLabel.setText("M");

        LTextField.setText("3");

        LLabel.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        LLabel.setText("L");

        buttonGroup3.add(RB_LM);
        RB_LM.setSelected(true);
        RB_LM.setText("=>");
        RB_LM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RB_LMActionPerformed(evt);
            }
        });

        buttonGroup3.add(RB_notLM);
        RB_notLM.setText("=>");
        RB_notLM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RB_notLMActionPerformed(evt);
            }
        });

        buttonGroup5.add(jCheckBox_graphite);
        jCheckBox_graphite.setText("graphite");
        jCheckBox_graphite.setContentAreaFilled(false);
        jCheckBox_graphite.setEnabled(false);

        buttonGroup5.add(jCheckBox_pentahept);
        jCheckBox_pentahept.setText("pentahept");
        jCheckBox_pentahept.setEnabled(false);

        buttonGroup2.add(jRB_dos1);
        jRB_dos1.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jRB_dos1.setSelected(true);
        jRB_dos1.setText("1");

        buttonGroup2.add(jRB_dos2);
        jRB_dos2.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jRB_dos2.setText("2");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(RB_LM)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(RB_notLM)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox_graphite)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox_pentahept)))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRB_dos1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRB_dos2)
                .addGap(55, 55, 55))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RB_LM)
                    .addComponent(LLabel)
                    .addComponent(LTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MLabel)
                    .addComponent(MTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RB_notLM)
                    .addComponent(jCheckBox_graphite)
                    .addComponent(jCheckBox_pentahept)))
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jRB_dos1)
                    .addComponent(jRB_dos2)))
        );

        jPanel14.setBackground(new java.awt.Color(0, 102, 204));

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel1.setText("DOSKNEE 3");

        LLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        LLabel2.setText("L");

        LTextField2.setText("3");

        MLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        MLabel2.setText("M");

        MTextField2.setText("3");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(LLabel2)
                .addComponent(LTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(MLabel2)
                .addComponent(MTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel1)
        );

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel2.setText("Band structure (BNDSTR)");

        jPanel7.setBackground(new java.awt.Color(153, 153, 255));

        openClustepInputFileLabel4.setText("Open the \"tbener\" input file");

        displayClustepInputFileButton3.setText("Display");
        displayClustepInputFileButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayClustepInputFileButton3ActionPerformed(evt);
            }
        });

        geditClustepInputButton3.setText("Edit");
        geditClustepInputButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geditClustepInputButton3ActionPerformed(evt);
            }
        });

        openClustepInputFileDialogButton3.setText("...");
        openClustepInputFileDialogButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openClustepInputFileDialogButton3ActionPerformed(evt);
            }
        });

        buttonGroup4.add(jRB1);
        jRB1.setSelected(true);
        jRB1.setText("6,6");

        buttonGroup4.add(jRB2);
        jRB2.setText("12,0");

        buttonGroup4.add(jRB3);
        jRB3.setText("11,0");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(openClustepInputFileDialogButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(openTBbndstrInputFileTextField))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(openClustepInputFileLabel4)
                .addGap(18, 18, 18)
                .addComponent(displayClustepInputFileButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(geditClustepInputButton3)
                .addGap(44, 44, 44)
                .addComponent(jRB1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRB2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRB3)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(openClustepInputFileLabel4)
                    .addComponent(displayClustepInputFileButton3)
                    .addComponent(jRB1)
                    .addComponent(jRB2)
                    .addComponent(jRB3)
                    .addComponent(geditClustepInputButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(openTBbndstrInputFileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(openClustepInputFileDialogButton3)))
        );

        compileTBCheckBox.setText("Compile");

        sendTBCheckBox.setText("Send data and source code");
        sendTBCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendTBCheckBoxActionPerformed(evt);
            }
        });

        buttonGroup1.add(useDK1AND2);
        useDK1AND2.setText("DOSKNEE 1 & 2");

        buttonGroup1.add(useDK3);
        useDK3.setText("DOSKNEE 3");

        buttonGroup1.add(useBD);
        useBD.setText("BNDSTR");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(useDK3)
                            .addComponent(jLabel2)
                            .addComponent(useBD)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(sendTBCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(compileTBCheckBox))
                            .addComponent(jLabel4)
                            .addComponent(useDK1AND2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(useDK1AND2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(useDK3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(useBD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sendTBCheckBox)
                    .addComponent(compileTBCheckBox))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void RB_LMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RB_LMActionPerformed
        LLabel.setEnabled(true);
        MLabel.setEnabled(true);
        LTextField.setEnabled(true);
        MTextField.setEnabled(true);

        jCheckBox_graphite.setEnabled(false);
        jCheckBox_pentahept.setEnabled(false);
        jCheckBox_graphite.setSelected(false);
        jCheckBox_pentahept.setSelected(false);
    }//GEN-LAST:event_RB_LMActionPerformed

    private void RB_notLMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RB_notLMActionPerformed
        LLabel.setEnabled(false);
        MLabel.setEnabled(false);
        LTextField.setEnabled(false);
        MTextField.setEnabled(false);

        jCheckBox_graphite.setEnabled(true);
        jCheckBox_pentahept.setEnabled(true);
        jCheckBox_graphite.setSelected(true);
        jCheckBox_pentahept.setSelected(false);
    }//GEN-LAST:event_RB_notLMActionPerformed

    private void displayClustepInputFileButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayClustepInputFileButton3ActionPerformed
        mf.clustepInputFileDisplayer.setVisible(true);
        // TODO : pour quand ce sera éditable
        //clustepInputFileDisplayer.setEditable(true);

        String fileContent = "";

        try {
            File file = new File(openTBbndstrInputFileTextField.getText());

            FileInputStream fis = new FileInputStream(file);

            // Here BufferedInputStream is added for fast reading.
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);

            while (dis.available() != 0) {
                fileContent += dis.readLine() + "\n";
            }

            // dispose all the resources after using them.
            fis.close();
            bis.close();
            dis.close();

        } catch (FileNotFoundException e) {
            mf.printERR(e.getMessage());
        } catch (IOException e) {
            mf.printERR(e.getMessage());
        }

        mf.clustepInputFileDisplayer.setText(fileContent);
    }//GEN-LAST:event_displayClustepInputFileButton3ActionPerformed

    private void geditClustepInputButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_geditClustepInputButton3ActionPerformed
        Runnable r = new Runnable() {
            @Override
            public void run() {
                String fileName = openTBbndstrInputFileTextField.getText();

                // ****************************************************************************

                mf.editFile(fileName, false);
                // ****************************************************************************
            }
        };

        Thread t = new Thread(r);
        t.start();
    }//GEN-LAST:event_geditClustepInputButton3ActionPerformed

    private void openClustepInputFileDialogButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openClustepInputFileDialogButton3ActionPerformed
        JFileChooser fc = new JFileChooser(".");
        File currDir = new File(".");
        String currPath = currDir.getAbsolutePath();
        String basePath = basePath = currPath.replace("\\", "/").replace(".", "");
            mf.printDEB(basePath);
            fc.setMultiSelectionEnabled(false);

            int retValue = fc.showOpenDialog(this);
            if (retValue == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                String relPath = file.getAbsolutePath().replace("\\", "/").replace(basePath, "./");
                    openTBbndstrInputFileTextField.setText(relPath);
                }
    }//GEN-LAST:event_openClustepInputFileDialogButton3ActionPerformed

    private void sendTBCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendTBCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sendTBCheckBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LLabel;
    private javax.swing.JLabel LLabel2;
    private javax.swing.JTextField LTextField;
    private javax.swing.JTextField LTextField2;
    private javax.swing.JLabel MLabel;
    private javax.swing.JLabel MLabel2;
    private javax.swing.JTextField MTextField;
    private javax.swing.JTextField MTextField2;
    private javax.swing.JRadioButton RB_LM;
    private javax.swing.JRadioButton RB_notLM;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.JCheckBox compileTBCheckBox;
    private javax.swing.JButton displayClustepInputFileButton3;
    private javax.swing.JButton geditClustepInputButton3;
    private javax.swing.JCheckBox jCheckBox_graphite;
    private javax.swing.JCheckBox jCheckBox_pentahept;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JRadioButton jRB1;
    private javax.swing.JRadioButton jRB2;
    private javax.swing.JRadioButton jRB3;
    private javax.swing.JRadioButton jRB_dos1;
    private javax.swing.JRadioButton jRB_dos2;
    private javax.swing.JButton openClustepInputFileDialogButton3;
    private javax.swing.JLabel openClustepInputFileLabel4;
    private javax.swing.JTextField openTBbndstrInputFileTextField;
    private javax.swing.JCheckBox sendTBCheckBox;
    private javax.swing.JRadioButton useBD;
    private javax.swing.JRadioButton useDK1AND2;
    private javax.swing.JRadioButton useDK3;
    // End of variables declaration//GEN-END:variables
}
