/*
 Copyright (c) 2009-2014 Flavio Miguel ABREU ARAUJO (flavio.abreuaraujo@uclouvain.be)
                         Yannick GILLET (yannick.gillet@uclouvain.be)

Université catholique de Louvain, Louvain-la-Neuve, Belgium
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

1. Redistributions of source code must retain the above copyright
notice, this list of conditions, and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright
notice, this list of conditions, and the disclaimer that follows
these conditions in the documentation and/or other materials
provided with the distribution.

3. The names of the author may not be used to endorse or promote
products derived from this software without specific prior written
permission.

In addition, we request (but do not require) that you include in the
end-user documentation provided with the redistribution and/or in the
software itself an acknowledgement equivalent to the following:
"This product includes software developed by the
Abinit Project (http://www.abinit.org/)."

THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED.  IN NO EVENT SHALL THE JDOM AUTHORS OR THE PROJECT
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
SUCH DAMAGE.

For more information on the Abinit Project, please see
<http://www.abinit.org/>.
 */

package abinitgui.projects;

import abinitgui.core.MainFrame;
import abinitgui.core.MyTableModel;
import abinitgui.core.pspAtomRenderer;
import abinitgui.core.Atom;
import abinitgui.core.AtomEditor;
import abinitgui.core.Utils;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import msgdisp.core.MessageDisplayer;
import abinitgui.parser.GUIEditor;

public class AbinitInputPanel extends SimulationPanel {

    private MessageDisplayer msgdispInputFile;
    private String curPath = "."; // to save current Path !
    private MyTableModel pspModel = null;
    
    private GUIEditor guiEditor;

    public AbinitInputPanel() {
        initComponents();
        
        msgdispInputFile = new MessageDisplayer(MainFrame.mainFrame, false, null);
        msgdispInputFile.setTitle("..:: Input file preview ::..");
        
        pspModel = new MyTableModel(pspTable);
        pspModel.setNotEditableCol("1-3");
        pspTable.setModel(pspModel);
        initTableHeader(pspTable, new String[]{"Atom", "PSP filename",
            "PSP type", "PSP path"}, new Integer[]{null, null, null, null});
        pspTable.setDefaultRenderer(Atom.class, new pspAtomRenderer());
        pspTable.setDefaultEditor(Atom.class,
                new AtomEditor(MainFrame.mainFrame));
        
        guiEditor = new GUIEditor();
    }

    private void initTableHeader(JTable table, String header[], Integer headerWidths[]) {
        TableColumnModel tcm = new DefaultTableColumnModel();
        for (int i = 0; i < header.length; i++) {
            TableColumn tc = new TableColumn(i);
            tc.setHeaderValue(header[i]);
            tc.setResizable(false);
            if (headerWidths[i] != null) {
                tc.setMinWidth(headerWidths[i]);
                tc.setPreferredWidth(headerWidths[i]);
                tc.setMaxWidth(headerWidths[i]);
            }
            tcm.addColumn(tc);
        }
        table.setColumnModel(tcm);
    }
    
    @Override
    public void fillFromSimu(Simulation currentSimu)
    {
        if(currentSimu instanceof AbinitSimulation)
        {
            AbinitSimulation simu = (AbinitSimulation)currentSimu;
            this.setInputFileName(simu.getInputFileName());
            this.setAtomList(simu.getListPseudos());
        }
        else
        {
            throw new UnsupportedOperationException("Not able to tackle Simulation");
        }
    }
    
    @Override
    public void fillSimu(Simulation currentSimu)
    {
        if(currentSimu instanceof AbinitSimulation)
        {
            AbinitSimulation simu = (AbinitSimulation)currentSimu;
            String fileName = getInputFileName();
            ArrayList<Atom> listPseudos = getAtomList();
            boolean useEXT = getUsingExtInputFile();
            simu.setInputFileName(fileName);
            simu.setListPseudos(listPseudos);
            simu.setUsingExtInputFile(useEXT);
        }
        else
        {
            throw new UnsupportedOperationException("Not able to tackle Simulation");
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

        inputFilePanel = new javax.swing.JPanel();
        useExtIFRadioButton = new javax.swing.JRadioButton();
        openFileTextField = new javax.swing.JTextField();
        openFileDialogButton = new javax.swing.JButton();
        openFileLabel = new javax.swing.JLabel();
        pspTextField = new javax.swing.JTextField();
        pspTableScrollPane = new javax.swing.JScrollPane();
        pspTable = new javax.swing.JTable();
        pspLabel = new javax.swing.JLabel();
        displayFileButton = new javax.swing.JButton();
        geditButton = new javax.swing.JButton();
        testAnalyze1 = new javax.swing.JButton();

        inputFilePanel.setMaximumSize(new java.awt.Dimension(800, 600));
        inputFilePanel.setMinimumSize(new java.awt.Dimension(800, 600));

        useExtIFRadioButton.setForeground(java.awt.Color.red);
        useExtIFRadioButton.setSelected(true);
        useExtIFRadioButton.setText("Use an external input file");
        useExtIFRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useExtIFRadioButtonActionPerformed(evt);
            }
        });

        openFileDialogButton.setText("...");
        openFileDialogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileDialogButtonActionPerformed(evt);
            }
        });

        openFileLabel.setText("Open the ABINIT input file (usualy *.in)");

        pspTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pspTextFieldActionPerformed(evt);
            }
        });
        pspTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pspTextFieldKeyReleased(evt);
            }
        });

        pspTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        pspTable.setRowSelectionAllowed(false);
        pspTable.getTableHeader().setReorderingAllowed(false);
        pspTableScrollPane.setViewportView(pspTable);

        pspLabel.setText("Number of different psps (=ntypat)");

        displayFileButton.setText("Display");
        displayFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayFileButtonActionPerformed(evt);
            }
        });

        geditButton.setText("Edit");
        geditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geditButtonActionPerformed(evt);
            }
        });

        testAnalyze1.setText("Test / Analyze file");
        testAnalyze1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testAnalyze1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout inputFilePanelLayout = new javax.swing.GroupLayout(inputFilePanel);
        inputFilePanel.setLayout(inputFilePanelLayout);
        inputFilePanelLayout.setHorizontalGroup(
            inputFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputFilePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inputFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, inputFilePanelLayout.createSequentialGroup()
                        .addComponent(openFileDialogButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(openFileTextField))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, inputFilePanelLayout.createSequentialGroup()
                        .addComponent(pspLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pspTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(useExtIFRadioButton, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, inputFilePanelLayout.createSequentialGroup()
                        .addComponent(openFileLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(displayFileButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(geditButton))
                    .addComponent(pspTableScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(testAnalyze1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        inputFilePanelLayout.setVerticalGroup(
            inputFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputFilePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(useExtIFRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(openFileLabel)
                    .addComponent(displayFileButton)
                    .addComponent(geditButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(openFileDialogButton)
                    .addComponent(openFileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pspLabel)
                    .addComponent(pspTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pspTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94)
                .addComponent(testAnalyze1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inputFilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inputFilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void useExtIFRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_useExtIFRadioButtonActionPerformed
        useExtIFRadioButton.setForeground(Color.red);        
        useExtIFRadioButton.setSelected(true);

        openFileLabel.setEnabled(true);
        openFileDialogButton.setEnabled(true);
        displayFileButton.setEnabled(true);
        geditButton.setEnabled(true);
        openFileTextField.setEnabled(true);
        pspLabel.setEnabled(true);
        pspTextField.setEnabled(true);
        pspTable.setEnabled(true);
        pspTable.setVisible(true);
        //inputFileDisplayer.setVisible(false);
        msgdispInputFile.hide();
//        inputFileTabbedPane.setEnabled(false);
//
//        inputFileTabbedPane.setSelectedIndex(inputFileTabbedPane.getTabCount() - 1);
    }//GEN-LAST:event_useExtIFRadioButtonActionPerformed

    private void openFileDialogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFileDialogButtonActionPerformed
        JFileChooser fc = new JFileChooser(curPath);
        File currDir = new File(".");
        String currPath = currDir.getAbsolutePath();
        String basePath = currPath.replace("\\", "/").replace(".", "");
            MainFrame.printDEB(basePath);
            fc.setMultiSelectionEnabled(false);
            int retValue = fc.showOpenDialog(this);
            if (retValue == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                curPath = file.getParent();
                MainFrame.printDEB("CurPath = " + curPath);
                String relPath = file.getAbsolutePath().replace("\\", "/").replace(basePath, "./");
                    openFileTextField.setText(relPath);
                }
    }//GEN-LAST:event_openFileDialogButtonActionPerformed

    private void pspTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pspTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pspTextFieldActionPerformed

    private void pspTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pspTextFieldKeyReleased
        try {
            int npsp = Integer.parseInt(pspTextField.getText());

            if (npsp > 1000) {
                npsp = 1000;
                Object strTab[][] = new Object[npsp][4];
                for (int i = 0; i < npsp; i++) {
                    strTab[i] = new Object[]{new Atom(), "", "", ""};
                }
                pspModel.setData(strTab);
                //znuclTable.setModel(znuclModel);
            } else {
                Object strTab[][] = new Object[npsp][4];
                for (int i = 0; i < npsp; i++) {
                    strTab[i] = new Object[]{new Atom(), "", "", ""};
                }
                pspModel.setData(strTab);
                //znuclTable.setModel(znuclModel);
            }
        } catch (Exception e) {
            //printERR(e.getMessage());
            pspModel.setData(null);
            //znuclTable.setModel(znuclModel);
        }
    }//GEN-LAST:event_pspTextFieldKeyReleased

    private void displayFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayFileButtonActionPerformed
        // TODO : pour quand ce sera éditable
        //inputFileDisplayer.setEditable(true);

        String fileContent = "";

        try {
            /*File file = new File(openFileTextField.getText());
            Scanner scanner = new Scanner(file).useDelimiter("\\Z");
            fileContent = scanner.next();
            scanner.close();*/
            
            fileContent = Utils.fileToString(openFileTextField.getText());

        } catch (FileNotFoundException e) {
            MainFrame.printERR(e.getMessage());
        } catch (IOException e) {
            MainFrame.printERR(e.getMessage());
        }

        //inputFileDisplayer.setText(fileContent);
        msgdispInputFile.setText(fileContent);
        msgdispInputFile.show();
    }//GEN-LAST:event_displayFileButtonActionPerformed

    private void geditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_geditButtonActionPerformed
        MainFrame.editFile(openFileTextField.getText(), true);
        // TODO
    }//GEN-LAST:event_geditButtonActionPerformed
    
    private void testAnalyze1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testAnalyze1ActionPerformed
        guiEditor.loadFile(openFileTextField.getText());
    }//GEN-LAST:event_testAnalyze1ActionPerformed

    public String getInputFileName()
    {
        return openFileTextField.getText();
    }
    
    public ArrayList<Atom> getAtomList()
    {
        ArrayList<Atom> list = new ArrayList<>();
        
        int row = pspTable.getRowCount();
        if (row > 0) {
            for (int i = 0; i < row; i++) {
                // L'élément pspTable.getValueAt(i, 0) est de type Atom
                // et toutes les infos du pseudopotentiel sont dedans.
                // J'aurais pu utiliser cet objet pour obtenir tous les champs!
                String symbol = (pspTable.getValueAt(i, 0)).toString();
                String pspfile = (pspTable.getValueAt(i, 1)).toString();
                String psptype = (pspTable.getValueAt(i, 2)).toString();
                String psppath = (pspTable.getValueAt(i, 3)).toString();

                Atom atom = new Atom();
                atom.setBySymbol(symbol);
                atom.setPSPFileName(pspfile);
                atom.setPSPType(psptype);
                atom.setPSPPath(psppath);
                list.add(atom);
            }
        }
        return list;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton displayFileButton;
    private javax.swing.JButton geditButton;
    private javax.swing.JPanel inputFilePanel;
    private javax.swing.JButton openFileDialogButton;
    private javax.swing.JLabel openFileLabel;
    private javax.swing.JTextField openFileTextField;
    private javax.swing.JLabel pspLabel;
    private javax.swing.JTable pspTable;
    private javax.swing.JScrollPane pspTableScrollPane;
    private javax.swing.JTextField pspTextField;
    private javax.swing.JButton testAnalyze1;
    private javax.swing.JRadioButton useExtIFRadioButton;
    // End of variables declaration//GEN-END:variables

    void setInputFileName(String inputFileName) {
        this.openFileTextField.setText(inputFileName);
    }
    
    public boolean getUsingExtInputFile() {
        return this.useExtIFRadioButton.isSelected();
    }

    void setAtomList(ArrayList<Atom> listPseudos) {
     
        int npsp = listPseudos.size();
        if (npsp > 1000) {
            npsp = 1000;
            Object strTab[][] = new Object[npsp][4];
            for (int i = 0; i < npsp; i++) {
                strTab[i] = new Object[]{new Atom(), "", "", ""};
            }
            pspModel.setData(strTab);
            //znuclTable.setModel(znuclModel);
        } else {
            Object strTab[][] = new Object[npsp][4];
            for (int i = 0; i < npsp; i++) {
                strTab[i] = new Object[]{new Atom(), "", "", ""};
            }
            pspModel.setData(strTab);
            //znuclTable.setModel(znuclModel);
        }
        
        pspTextField.setText(""+npsp);
        
        int i = 0;
        for(Atom at : listPseudos)
        {
            String psppath = at.getPSPPath();
            String pspfile = at.getPSPFileName();
            String psptype = at.getPSPType();
            String symbol = at.getSymbol();
            Atom atom = (Atom) pspTable.getValueAt(i++, 0);

            AtomEditor.setAtom(atom, symbol, psptype, psppath, pspfile);
        }
    }
}
