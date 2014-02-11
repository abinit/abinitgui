/*
 Copyright (c) 2009-2014 Flavio Miguel ABREU ARAUJO (flavio.abreuaraujo@uclouvain.be)
                         Yannick GILLET (yannick.gillet@uclouvain.be)

 Universit� catholique de Louvain, Louvain-la-Neuve, Belgium
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

package abinitgui.inputgen;

import abinitgui.core.Atom;
import abinitgui.core.AtomEditor;
import abinitgui.core.MainFrame;
import abinitgui.core.MyTableModel;
import abinitgui.core.znuclAtomRenderer;
import java.awt.Color;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Enumeration;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

//@SuppressWarnings("serial")
public class Geometry extends JPanel {

    private String previous = "xred"; // Option pour le choix xred, xcart et xangst
    private MyTableModel xyzModel = null;
    private MyTableModel rprimModel = null;
    private MyTableModel znuclModel = null;
    private MyTableModel symrelModel = null;
    private MyTableModel tnonsModel = null;
    // 1 Bohr = 0,5291772108 Angström
    private final double bohr2angst = 0.5291772108;
    private DecimalFormat df_rprim = new DecimalFormat("#0.0000000000000");
    private DecimalFormat df_angdeg = new DecimalFormat("#0.0000000000");

    public Geometry(JFrame parent) {
        initComponents();

        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        df_rprim.setDecimalFormatSymbols(dfs);
        df_angdeg.setDecimalFormatSymbols(dfs);

        xyzModel = new MyTableModel(xyzTable);
        xyzTable.setModel(xyzModel);
        initTableHeader(xyzTable, new String[]{"xred", "yred", "zred"},
                new Integer[]{null, null, null});

        rprimModel = new MyTableModel(rprimTable);
        rprimModel.setNotEditableCol("0");
        rprimTable.setModel(rprimModel);
        initTableHeader(rprimTable, new String[]{"", "x (red.)", "y (red.)", "z (red.)"},
                new Integer[]{18, null, null, null});
        rprimTable.setVisible(false);

        znuclModel = new MyTableModel(znuclTable);
        znuclModel.setNotEditableCol("1-2");
        znuclTable.setModel(znuclModel);
        initTableHeader(znuclTable, new String[]{"Atom", "znucl", "typat", "#"},
                new Integer[]{null, null, null, null});
        znuclTable.setDefaultRenderer(Atom.class,
                new znuclAtomRenderer());
        znuclTable.setDefaultEditor(Atom.class,
                new AtomEditor(parent));

        symrelModel = new MyTableModel(symrelTable);
        symrelTable.setModel(symrelModel);
        initTableHeader(symrelTable, new String[]{"S11", "S12", "S13", "S21",
            "S22", "S23", "S31", "S32", "S33"},
                new Integer[]{null, null, null, null, null, null, null, null, null});
        symrelTable.setVisible(false);

        tnonsModel = new MyTableModel(tnonsTable);
        tnonsTable.setModel(tnonsModel);
        initTableHeader(tnonsTable, new String[]{"a", "b", "c"},
                new Integer[]{null, null, null});
        tnonsTable.setVisible(false);

        rprimdCheckBox.setVisible(false);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    //@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        xyzbuttonGroup = new javax.swing.ButtonGroup();
        znuclTableScrollPane = new javax.swing.JScrollPane();
        znuclTable = new javax.swing.JTable();
        znuclCheckBox = new javax.swing.JCheckBox();
        rprimTableScrollPane = new javax.swing.JScrollPane();
        rprimTable = new javax.swing.JTable();
        angdegCheckBox = new javax.swing.JCheckBox();
        rprimCheckBox = new javax.swing.JCheckBox();
        acellTextField1 = new javax.swing.JTextField();
        acellTextField2 = new javax.swing.JTextField();
        acellUnitsLabel = new javax.swing.JLabel();
        ntypatTextField = new javax.swing.JTextField();
        typatCheckBox = new javax.swing.JCheckBox();
        xangstRadioButton = new javax.swing.JRadioButton();
        xredRadioButton = new javax.swing.JRadioButton();
        xyzTableScrollPane = new javax.swing.JScrollPane();
        xyzTable = new javax.swing.JTable();
        natomTextField = new javax.swing.JTextField();
        xcartRadioButton = new javax.swing.JRadioButton();
        ntypatCheckBox = new javax.swing.JCheckBox();
        natomCheckBox = new javax.swing.JCheckBox();
        acellCheckBox = new javax.swing.JCheckBox();
        rprimdCheckBox = new javax.swing.JCheckBox();
        acellTextField3 = new javax.swing.JTextField();
        shiftkTableScrollPane2 = new javax.swing.JScrollPane();
        tnonsTable = new javax.swing.JTable();
        symrelCheckBox = new javax.swing.JCheckBox();
        tnonsCheckBox = new javax.swing.JCheckBox();
        nsymCheckBox = new javax.swing.JCheckBox();
        nsymTextField = new javax.swing.JTextField();
        shiftkTableScrollPane1 = new javax.swing.JScrollPane();
        symrelTable = new javax.swing.JTable();

        setAutoscrolls(true);

        znuclTableScrollPane.setMaximumSize(new java.awt.Dimension(325, 110));
        znuclTableScrollPane.setMinimumSize(new java.awt.Dimension(325, 110));
        znuclTableScrollPane.setPreferredSize(new java.awt.Dimension(325, 110));

        znuclTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        znuclTable.setEnabled(false);
        znuclTable.setMaximumSize(null);
        znuclTable.setMinimumSize(null);
        znuclTable.setPreferredSize(null);
        znuclTable.setRowSelectionAllowed(false);
        znuclTable.getTableHeader().setReorderingAllowed(false);
        znuclTableScrollPane.setViewportView(znuclTable);

        znuclCheckBox.setForeground(java.awt.Color.blue);
        znuclCheckBox.setText("znucl");
        znuclCheckBox.setEnabled(false);
        znuclCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                znuclCheckBoxActionPerformed(evt);
            }
        });

        rprimTableScrollPane.setMaximumSize(new java.awt.Dimension(325, 70));
        rprimTableScrollPane.setMinimumSize(new java.awt.Dimension(325, 70));
        rprimTableScrollPane.setPreferredSize(new java.awt.Dimension(325, 70));

        rprimTable.setEnabled(false);
        rprimTable.setMaximumSize(null);
        rprimTable.setMinimumSize(null);
        rprimTable.setPreferredSize(null);
        rprimTable.setRowSelectionAllowed(false);
        rprimTable.getTableHeader().setReorderingAllowed(false);
        rprimTableScrollPane.setViewportView(rprimTable);

        angdegCheckBox.setForeground(java.awt.Color.blue);
        angdegCheckBox.setText("angdeg");
        angdegCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                angdegCheckBoxActionPerformed(evt);
            }
        });

        rprimCheckBox.setForeground(java.awt.Color.blue);
        rprimCheckBox.setText("rprim");
        rprimCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rprimCheckBoxActionPerformed(evt);
            }
        });

        acellTextField1.setEnabled(false);

        acellTextField2.setEnabled(false);

        acellUnitsLabel.setText("[Bohr]");
        acellUnitsLabel.setEnabled(false);

        ntypatTextField.setEnabled(false);
        ntypatTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ntypatTextFieldKeyReleased(evt);
            }
        });

        typatCheckBox.setForeground(java.awt.Color.blue);
        typatCheckBox.setText("typat");
        typatCheckBox.setEnabled(false);
        typatCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typatCheckBoxActionPerformed(evt);
            }
        });

        xyzbuttonGroup.add(xangstRadioButton);
        xangstRadioButton.setText("xangst");
        xangstRadioButton.setEnabled(false);
        xangstRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xangstRadioButtonActionPerformed(evt);
            }
        });

        xyzbuttonGroup.add(xredRadioButton);
        xredRadioButton.setForeground(java.awt.Color.red);
        xredRadioButton.setSelected(true);
        xredRadioButton.setText("xred");
        xredRadioButton.setEnabled(false);
        xredRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xredRadioButtonActionPerformed(evt);
            }
        });

        xyzTableScrollPane.setMaximumSize(new java.awt.Dimension(325, 110));
        xyzTableScrollPane.setMinimumSize(new java.awt.Dimension(325, 110));
        xyzTableScrollPane.setPreferredSize(new java.awt.Dimension(325, 110));

        xyzTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        xyzTable.setEnabled(false);
        xyzTable.setMaximumSize(null);
        xyzTable.setMinimumSize(null);
        xyzTable.setPreferredSize(null);
        xyzTable.setRowSelectionAllowed(false);
        xyzTable.getTableHeader().setReorderingAllowed(false);
        xyzTableScrollPane.setViewportView(xyzTable);

        natomTextField.setEnabled(false);
        natomTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                natomTextFieldKeyReleased(evt);
            }
        });

        xyzbuttonGroup.add(xcartRadioButton);
        xcartRadioButton.setText("xcart");
        xcartRadioButton.setEnabled(false);
        xcartRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xcartRadioButtonActionPerformed(evt);
            }
        });

        ntypatCheckBox.setForeground(java.awt.Color.blue);
        ntypatCheckBox.setText("ntypat");
        ntypatCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ntypatCheckBoxActionPerformed(evt);
            }
        });

        natomCheckBox.setForeground(java.awt.Color.blue);
        natomCheckBox.setText("natom");
        natomCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                natomCheckBoxActionPerformed(evt);
            }
        });

        acellCheckBox.setForeground(java.awt.Color.blue);
        acellCheckBox.setText("acell");
        acellCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acellCheckBoxActionPerformed(evt);
            }
        });

        rprimdCheckBox.setForeground(java.awt.Color.blue);
        rprimdCheckBox.setText("rprimd");
        rprimdCheckBox.setEnabled(false);
        rprimdCheckBox.setFocusPainted(false);
        rprimdCheckBox.setFocusable(false);
        rprimdCheckBox.setRequestFocusEnabled(false);
        rprimdCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rprimdCheckBoxActionPerformed(evt);
            }
        });

        acellTextField3.setEnabled(false);

        shiftkTableScrollPane2.setMaximumSize(new java.awt.Dimension(325, 110));
        shiftkTableScrollPane2.setMinimumSize(new java.awt.Dimension(325, 110));
        shiftkTableScrollPane2.setPreferredSize(new java.awt.Dimension(325, 110));

        tnonsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tnonsTable.setEnabled(false);
        tnonsTable.setRowSelectionAllowed(false);
        tnonsTable.getTableHeader().setReorderingAllowed(false);
        shiftkTableScrollPane2.setViewportView(tnonsTable);

        symrelCheckBox.setForeground(java.awt.Color.blue);
        symrelCheckBox.setText("symrel");
        symrelCheckBox.setEnabled(false);
        symrelCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                symrelCheckBoxActionPerformed(evt);
            }
        });

        tnonsCheckBox.setForeground(java.awt.Color.blue);
        tnonsCheckBox.setText("tnons");
        tnonsCheckBox.setEnabled(false);
        tnonsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnonsCheckBoxActionPerformed(evt);
            }
        });

        nsymCheckBox.setForeground(java.awt.Color.blue);
        nsymCheckBox.setText("nsym");
        nsymCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nsymCheckBoxActionPerformed(evt);
            }
        });

        nsymTextField.setEnabled(false);
        nsymTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nsymTextFieldKeyReleased(evt);
            }
        });

        shiftkTableScrollPane1.setMaximumSize(new java.awt.Dimension(325, 110));
        shiftkTableScrollPane1.setMinimumSize(new java.awt.Dimension(325, 110));
        shiftkTableScrollPane1.setPreferredSize(new java.awt.Dimension(325, 110));

        symrelTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        symrelTable.setEnabled(false);
        symrelTable.setRowSelectionAllowed(false);
        symrelTable.getTableHeader().setReorderingAllowed(false);
        shiftkTableScrollPane1.setViewportView(symrelTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rprimCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(angdegCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rprimdCheckBox))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(acellCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(acellTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(acellTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(acellTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(acellUnitsLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(natomCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(natomTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(xredRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(xcartRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(xangstRadioButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ntypatCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ntypatTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(znuclCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(typatCheckBox))
                    .addComponent(rprimTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(xyzTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(znuclTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nsymCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nsymTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(symrelCheckBox))
                    .addComponent(tnonsCheckBox)
                    .addComponent(shiftkTableScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(shiftkTableScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {acellTextField1, acellTextField2, acellTextField3});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nsymCheckBox)
                            .addComponent(nsymTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(symrelCheckBox))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(shiftkTableScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tnonsCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(shiftkTableScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(acellCheckBox)
                            .addComponent(acellTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(acellTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(acellTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(acellUnitsLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rprimCheckBox)
                            .addComponent(angdegCheckBox)
                            .addComponent(rprimdCheckBox))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rprimTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(natomCheckBox)
                            .addComponent(natomTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(xangstRadioButton)
                            .addComponent(xcartRadioButton)
                            .addComponent(xredRadioButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(xyzTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ntypatCheckBox)
                    .addComponent(ntypatTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(znuclCheckBox)
                    .addComponent(typatCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(znuclTableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void znuclCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_znuclCheckBoxActionPerformed
        znuclCheckBox.setSelected(true);
}//GEN-LAST:event_znuclCheckBoxActionPerformed

    private void angdegCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_angdegCheckBoxActionPerformed
        if (angdegCheckBox.isSelected()) {
            Double angdeg1;
            Double angdeg2;
            Double angdeg3;

            Double acell1;
            Double acell2;
            Double acell3;

            if (rprimCheckBox.isSelected()) {
                rprimCheckBox.setSelected(false);
                rprimCheckBoxActionPerformed(evt);

                // On transforme rprim vers angdeg
                int col = rprimTable.getColumnCount();
                int row = rprimTable.getRowCount();
                Double tmp[][] = new Double[row][col - 1];
                if (row > 0) {
                    for (int i = 0; i < row; i++) {
                        for (int j = 1; j < col; j++) {
                            try {
                                tmp[i][j - 1] = (Double) rprimTable.getValueAt(i, j);
                            } catch (Exception e) {
                                angdeg1 = 90.0;
                                angdeg2 = 90.0;
                                angdeg3 = 90.0;
                                MainFrame.printERR("Error in reading rprimTable (angdegCheckBoxActionPerformed) !");
                            }
                        }
                    }

                    Double a1 = tmp[0][0];
                    Double a2 = tmp[0][1];
                    Double a3 = tmp[0][2];
                    Double b1 = tmp[1][0];
                    Double b2 = tmp[1][1];
                    Double b3 = tmp[1][2];
                    Double c1 = tmp[2][0];
                    Double c2 = tmp[2][1];
                    Double c3 = tmp[2][2];

                    angdeg1 = new Double(Math.acos((c1 * b1 + c2 * b2 + c3 * b3)
                            / (Math.sqrt(c1 * c1 + c2 * c2 + c3 * c3)
                            * Math.sqrt(b1 * b1 + b2 * b2 + b3 * b3)))
                            * 180.0 / Math.PI);
                    angdeg2 = new Double(Math.acos((a1 * c1 + a2 * c2 + a3 * c3)
                            / (Math.sqrt(a1 * a1 + a2 * a2 + a3 * a3)
                            * Math.sqrt(c1 * c1 + c2 * c2 + c3 * c3)))
                            * 180.0 / Math.PI);
                    angdeg3 = new Double(Math.acos((a1 * b1 + a2 * b2 + a3 * b3)
                            / (Math.sqrt(a1 * a1 + a2 * a2 + a3 * a3)
                            * Math.sqrt(b1 * b1 + b2 * b2 + b3 * b3)))
                            * 180.0 / Math.PI);

                } else {
                    angdeg1 = 90.0;
                    angdeg2 = 90.0;
                    angdeg3 = 90.0;
                }
            } else if (rprimdCheckBox.isSelected()) {
                rprimdCheckBox.setSelected(false);
                rprimdCheckBoxActionPerformed(evt);

                // On transforme rprimd vers angdeg
                try {
                    acell1 = Double.parseDouble(acellTextField1.getText());
                    acell2 = Double.parseDouble(acellTextField2.getText());
                    acell3 = Double.parseDouble(acellTextField3.getText());
                } catch (Exception e) {
                    acell1 = 1.0;
                    acell2 = 1.0;
                    acell3 = 1.0;
                    MainFrame.printERR("Please setup acell (to avoid calculation errors acell became (1, 1, 1)) !");
                }

                int col = rprimTable.getColumnCount();
                int row = rprimTable.getRowCount();
                Double tmp[][] = new Double[row][col - 1];
                if (row > 0) {
                    for (int i = 0; i < row; i++) {
                        for (int j = 1; j < col; j++) {
                            try {
                                tmp[i][j - 1] = (Double) rprimTable.getValueAt(i, j);
                            } catch (Exception e) {
                                MainFrame.printERR("Error in reading rprimTable (angdegCheckBoxActionPerformed) !");
                                angdeg1 = 90.0;
                                angdeg2 = 90.0;
                                angdeg3 = 90.0;
                            }
                        }
                    }

                    Double a1 = tmp[0][0] / acell1;
                    Double a2 = tmp[0][1] / acell1;
                    Double a3 = tmp[0][2] / acell1;
                    Double b1 = tmp[1][0] / acell2;
                    Double b2 = tmp[1][1] / acell2;
                    Double b3 = tmp[1][2] / acell2;
                    Double c1 = tmp[2][0] / acell3;
                    Double c2 = tmp[2][1] / acell3;
                    Double c3 = tmp[2][2] / acell3;

                    angdeg1 = new Double(Math.acos((c1 * b1 + c2 * b2 + c3 * b3)
                            / (Math.sqrt(c1 * c1 + c2 * c2 + c3 * c3)
                            * Math.sqrt(b1 * b1 + b2 * b2 + b3 * b3)))
                            * 180.0 / Math.PI);
                    angdeg2 = new Double(Math.acos((a1 * c1 + a2 * c2 + a3 * c3)
                            / (Math.sqrt(a1 * a1 + a2 * a2 + a3 * a3)
                            * Math.sqrt(c1 * c1 + c2 * c2 + c3 * c3)))
                            * 180.0 / Math.PI);
                    angdeg3 = new Double(Math.acos((a1 * b1 + a2 * b2 + a3 * b3)
                            / (Math.sqrt(a1 * a1 + a2 * a2 + a3 * a3)
                            * Math.sqrt(b1 * b1 + b2 * b2 + b3 * b3)))
                            * 180.0 / Math.PI);
                } else {
                    angdeg1 = 90.0;
                    angdeg2 = 90.0;
                    angdeg3 = 90.0;
                }
            } else {
                angdeg1 = 90.0;
                angdeg2 = 90.0;
                angdeg3 = 90.0;
            }

            angdegCheckBox.setForeground(Color.red);
            initTableHeader(rprimTable, new String[]{"", "angle [deg.]"},
                    new Integer[]{160, null});

            rprimModel.setData(new Object[][]{
                {"angle between b and c", new Double(df_angdeg.format(angdeg1))},
                {"angle between a and c", new Double(df_angdeg.format(angdeg2))},
                {"angle between a and b", new Double(df_angdeg.format(angdeg3))}});
            rprimTable.setEnabled(true);
            rprimTable.setVisible(true);
        } else {
            angdegCheckBox.setForeground(Color.blue);
            rprimTable.setEnabled(false);
            rprimTable.setVisible(false);
        }
}//GEN-LAST:event_angdegCheckBoxActionPerformed

    private void rprimCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rprimCheckBoxActionPerformed
        if (rprimCheckBox.isSelected()) {
            Object tab[][];
            Double acell1;
            Double acell2;
            Double acell3;

            if (angdegCheckBox.isSelected()) {
                angdegCheckBox.setSelected(false);
                angdegCheckBoxActionPerformed(evt);

                // On transforme angdeg en rprim
                try {
                    acell1 = Double.parseDouble(acellTextField1.getText());
                    acell2 = Double.parseDouble(acellTextField2.getText());
                    acell3 = Double.parseDouble(acellTextField3.getText());
                } catch (Exception e) {
                    acell1 = 1.0;
                    acell2 = 1.0;
                    acell3 = 1.0;
                    MainFrame.printERR("Please setup acell (to avoid calculation errors acell became (1, 1, 1)) !");
                }

                int col = rprimTable.getColumnCount();
                int row = rprimTable.getRowCount();
                Object tmp[][] = new Object[row][col];
                //Double acell[] = new Double[]{acell1, acell2, acell3};
                if (row > 0) {
                    for (int i = 0; i < row; i++) {
                        for (int j = 1; j < col; j++) {
                            try {
                                tmp[i][j] = new Double((Double) rprimTable.getValueAt(i, j));
                            } catch (Exception e) {
                                MainFrame.printERR("Error in reading rprimTable (angdegCheckBoxActionPerformed) !");
                            }
                        }
                    }

                    Double angdeg1 = (Double) tmp[0][1]; // angdeg(1)
                    Double angdeg2 = (Double) tmp[1][1]; // angdeg(2)
                    Double angdeg3 = (Double) tmp[2][1]; // angdeg(3)

                    tab = new Object[3][4];
                    tab[0][0] = "a";
                    tab[1][0] = "b";
                    tab[2][0] = "c";

                    Double rprim11;
                    Double rprim12;
                    Double rprim13;
                    Double rprim21;
                    Double rprim22;
                    Double rprim23;
                    Double rprim31;
                    Double rprim32;
                    Double rprim33;

                    if (angdeg1.equals(angdeg2) && angdeg1.equals(angdeg3) && !angdeg1.equals(90.0)) {
                        Double cosang = Math.cos(Math.PI * angdeg1 / 180.0);
                        Double a2 = 2.0 / 3.0 * (1.0 - cosang);
                        Double aa = Math.sqrt(a2);
                        Double cc = Math.sqrt(1.0 - a2);
                        rprim11 = aa;
                        rprim21 = 0.0;
                        rprim31 = cc;
                        rprim12 = -0.5 * aa;
                        rprim22 = Math.sqrt(3.0) * 0.5 * aa;
                        rprim32 = cc;
                        rprim13 = -0.5 * aa;
                        rprim23 = -Math.sqrt(3.0) * 0.5 * aa;
                        rprim33 = cc;
                    } else {
                        rprim11 = new Double(1.0); // rprim(1,1) OK
                        rprim12 = new Double(Math.cos(Math.PI * angdeg3 / 180.0)); // rprim(1,2) OK
                        rprim13 = new Double(Math.cos(Math.PI * angdeg2 / 180.0)); // rprim(1,3) OK

                        rprim21 = new Double(0.0); // rprim(2,1)
                        rprim22 = new Double(Math.sin(Math.PI * angdeg3 / 180.0)); // rprim(2,2) OK
                        rprim23 = new Double(((Math.cos(Math.PI * angdeg1 / 180.0)
                                - rprim12 * rprim13) / rprim22)); // rprim(2,3) OK

                        rprim31 = new Double(0.0); // rprim(3,1)
                        rprim32 = new Double(0.0); // rprim(3,2)
                        rprim33 = new Double(Math.sqrt(1.0 - Math.pow(rprim13, 2)
                                - Math.pow(rprim23, 2))); // rprim(3,3) OK
                    }

                    tab[0][1] = new Double(df_rprim.format(rprim11));
                    tab[1][1] = new Double(df_rprim.format(rprim12));
                    tab[2][1] = new Double(df_rprim.format(rprim13));

                    tab[0][2] = new Double(df_rprim.format(rprim21));
                    tab[1][2] = new Double(df_rprim.format(rprim22));
                    tab[2][2] = new Double(df_rprim.format(rprim23));

                    tab[0][3] = new Double(df_rprim.format(rprim31));
                    tab[1][3] = new Double(df_rprim.format(rprim32));
                    tab[2][3] = new Double(df_rprim.format(rprim33));
                } else {
                    tab = new Object[][]{
                        {"a", new Double(1.0), new Double(0.0), new Double(0.0)},
                        {"b", new Double(0.0), new Double(1.0), new Double(0.0)},
                        {"c", new Double(0.0), new Double(0.0), new Double(1.0)}};
                }
            } else if (rprimdCheckBox.isSelected()) {
                rprimdCheckBox.setSelected(false);
                rprimdCheckBoxActionPerformed(evt);

                // On transforme rprimd en rprim
                try {
                    acell1 = Double.parseDouble(acellTextField1.getText());
                    acell2 = Double.parseDouble(acellTextField2.getText());
                    acell3 = Double.parseDouble(acellTextField3.getText());
                } catch (Exception e) {
                    acell1 = 1.0;
                    acell2 = 1.0;
                    acell3 = 1.0;
                    MainFrame.printERR("Please setup acell (to avoid calculation errors acell became (1, 1, 1)) !");
                }

                int col = rprimTable.getColumnCount();
                int row = rprimTable.getRowCount();
                Object tmp[][] = new Object[row][col];
                Double acell[] = new Double[]{acell1, acell2, acell3};
                tmp[0][0] = "a";
                tmp[1][0] = "b";
                tmp[2][0] = "c";
                if (row > 0) {
                    for (int i = 0; i < row; i++) {
                        for (int j = 1; j < col; j++) {
                            try {
                                tmp[i][j] = new Double(((Double) rprimTable.getValueAt(i, j)) / acell[i]);
                            } catch (Exception e) {
                                MainFrame.printERR("Error in reading rprimTable (angdegCheckBoxActionPerformed) !");
                            }
                        }
                    }
                    tab = tmp;
                } else {
                    tab = new Object[][]{
                        {"a", new Double(1.0), new Double(0.0), new Double(0.0)},
                        {"b", new Double(0.0), new Double(1.0), new Double(0.0)},
                        {"c", new Double(0.0), new Double(0.0), new Double(1.0)}};
                }
            } else {
                tab = new Object[][]{
                    {"a", new Double(1.0), new Double(0.0), new Double(0.0)},
                    {"b", new Double(0.0), new Double(1.0), new Double(0.0)},
                    {"c", new Double(0.0), new Double(0.0), new Double(1.0)}};
            }

            rprimCheckBox.setForeground(Color.red);
            initTableHeader(rprimTable, new String[]{"", "x (red.)", "y (red.)", "z (red.)"},
                    new Integer[]{18, null, null, null});
            rprimModel.setData(tab);
            rprimTable.setEnabled(true);
            rprimTable.setVisible(true);
        } else {
            rprimCheckBox.setForeground(Color.blue);
            rprimTable.setEnabled(false);
            rprimTable.setVisible(false);
        }
}//GEN-LAST:event_rprimCheckBoxActionPerformed

    private void ntypatTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ntypatTextFieldKeyReleased
        try {
            int ntypat = Integer.parseInt(ntypatTextField.getText());

            if (ntypat > 1000) {
                ntypat = 1000;
                Object strTab[][] = new Object[ntypat][4];
                for (int i = 0; i < ntypat; i++) {
                    strTab[i] = new Object[]{new Atom(), new Integer(0), new Integer(i + 1), new Integer(1)};
                    //strTab[i] = new Object[]{new String(), new Integer(0), new Integer(i+1), new Integer(0)};
                }
                znuclModel.setData(strTab);
                //znuclTable.setModel(znuclModel);
            } else {
                Object strTab[][] = new Object[ntypat][4];
                for (int i = 0; i < ntypat; i++) {
                    strTab[i] = new Object[]{new Atom(), new Integer(0), new Integer(i + 1), new Integer(1)};
                    //strTab[i] = new Object[]{new String(), new Integer(0), new Integer(i+1), new Integer(0)};
                }
                znuclModel.setData(strTab);
                //znuclTable.setModel(znuclModel);
            }
        } catch (Exception e) {
            //printERR(e.getMessage());
            znuclModel.setData(null);
            //znuclTable.setModel(znuclModel);
        }
}//GEN-LAST:event_ntypatTextFieldKeyReleased

    private void typatCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typatCheckBoxActionPerformed
        typatCheckBox.setSelected(true);
}//GEN-LAST:event_typatCheckBoxActionPerformed

    private void xangstRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xangstRadioButtonActionPerformed
        radioButtonHandler(null, false);
}//GEN-LAST:event_xangstRadioButtonActionPerformed

    private void xredRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xredRadioButtonActionPerformed
        radioButtonHandler(null, false);
}//GEN-LAST:event_xredRadioButtonActionPerformed

    private void natomTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_natomTextFieldKeyReleased
        try {
            int natom = Integer.parseInt(natomTextField.getText());

            if (natom > 1000) {
                natom = 1000;
                Object strTab[][] = new Object[natom][3];
                for (int i = 0; i < natom; i++) {
                    strTab[i] = new Object[]{new Double(0.0), new Double(0.0), new Double(0.0)};
                }
                radioButtonHandler(strTab, false);
            } else {
                Object strTab[][] = new Object[natom][3];
                for (int i = 0; i < natom; i++) {
                    strTab[i] = new Object[]{new Double(0.0), new Double(0.0), new Double(0.0)};
                }
                radioButtonHandler(strTab, false);
            }
        } catch (Exception e) {
            //printERR(e.getMessage());
            radioButtonHandler(null, true);
        }
}//GEN-LAST:event_natomTextFieldKeyReleased

    private void xcartRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xcartRadioButtonActionPerformed
        radioButtonHandler(null, false);
}//GEN-LAST:event_xcartRadioButtonActionPerformed

    private void ntypatCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ntypatCheckBoxActionPerformed
        if (ntypatCheckBox.isSelected()) {
            ntypatCheckBox.setForeground(Color.red);
            znuclCheckBox.setForeground(Color.red);
            znuclCheckBox.setEnabled(true);
            znuclCheckBox.setSelected(true);
            typatCheckBox.setForeground(Color.red);
            typatCheckBox.setEnabled(true);
            typatCheckBox.setSelected(true);

            ntypatTextField.setEnabled(true);
            znuclTable.setEnabled(true);
            znuclTable.setVisible(true);
        } else {
            ntypatCheckBox.setForeground(Color.blue);
            //znuclCheckBox.setForeground(Color.red);
            znuclCheckBox.setEnabled(false);
            znuclCheckBox.setSelected(false);
            //typatCheckBox.setForeground(Color.red);
            typatCheckBox.setEnabled(false);
            typatCheckBox.setSelected(false);

            ntypatTextField.setEnabled(false);
            znuclTable.setEnabled(false);
            znuclTable.setVisible(false);
        }
}//GEN-LAST:event_ntypatCheckBoxActionPerformed

    private void natomCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_natomCheckBoxActionPerformed
        if (natomCheckBox.isSelected()) {
            natomCheckBox.setForeground(Color.red);
            natomTextField.setEnabled(true);
            xredRadioButton.setEnabled(true);
            xcartRadioButton.setEnabled(true);
            xangstRadioButton.setEnabled(true);
            xyzTable.setEnabled(true);
            xyzTable.setVisible(true);
        } else {
            natomCheckBox.setForeground(Color.blue);
            natomTextField.setEnabled(false);
            xredRadioButton.setEnabled(false);
            xcartRadioButton.setEnabled(false);
            xangstRadioButton.setEnabled(false);
            xyzTable.setEnabled(false);
            xyzTable.setVisible(false);
        }
}//GEN-LAST:event_natomCheckBoxActionPerformed

    private void acellCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acellCheckBoxActionPerformed
        if (acellCheckBox.isSelected()) {
            acellCheckBox.setForeground(Color.red);
            acellTextField1.setEnabled(true);
            acellTextField2.setEnabled(true);
            acellTextField3.setEnabled(true);
            acellUnitsLabel.setEnabled(true);
        } else {
            acellCheckBox.setForeground(Color.blue);
            acellTextField1.setEnabled(false);
            acellTextField2.setEnabled(false);
            acellTextField3.setEnabled(false);
            acellUnitsLabel.setEnabled(false);
        }
}//GEN-LAST:event_acellCheckBoxActionPerformed

    private void rprimdCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rprimdCheckBoxActionPerformed
        if (rprimdCheckBox.isSelected()) {
            Object tab[][];
            Double acell1;
            Double acell2;
            Double acell3;

            try {
                acell1 = Double.parseDouble(acellTextField1.getText());
                acell2 = Double.parseDouble(acellTextField2.getText());
                acell3 = Double.parseDouble(acellTextField3.getText());
            } catch (Exception e) {
                acell1 = 1.0;
                acell2 = 1.0;
                acell3 = 1.0;
                MainFrame.printERR("Please setup acell (to avoid calculation errors acell became (1, 1, 1)) !");
            }

            if (angdegCheckBox.isSelected()) {
                angdegCheckBox.setSelected(false);
                angdegCheckBoxActionPerformed(evt);

                // On transforme angdeg en rprimd (pas implémeté)
                try {
                    acell1 = Double.parseDouble(acellTextField1.getText());
                    acell2 = Double.parseDouble(acellTextField2.getText());
                    acell3 = Double.parseDouble(acellTextField3.getText());
                } catch (Exception e) {
                    acell1 = 1.0;
                    acell2 = 1.0;
                    acell3 = 1.0;
                    MainFrame.printERR("Please setup acell (to avoid calculation errors acell became (1, 1, 1)) !");
                }

                int col = rprimTable.getColumnCount();
                int row = rprimTable.getRowCount();
                Object tmp[][] = new Object[row][col];
                if (row > 0) {
                    for (int i = 0; i < row; i++) {
                        for (int j = 1; j < col; j++) {
                            try {
                                tmp[i][j] = new Double((Double) rprimTable.getValueAt(i, j));
                            } catch (Exception e) {
                                MainFrame.printERR("Error in reading rprimTable (angdegCheckBoxActionPerformed) !");
                            }
                        }
                    }

                    Double angdeg1 = (Double) tmp[0][1]; // angdeg(1)
                    Double angdeg2 = (Double) tmp[1][1]; // angdeg(2)
                    Double angdeg3 = (Double) tmp[2][1]; // angdeg(3)

                    tab = new Object[3][4];
                    tab[0][0] = "a";
                    tab[1][0] = "b";
                    tab[2][0] = "c";

                    Double rprim11;
                    Double rprim12;
                    Double rprim13;
                    Double rprim21;
                    Double rprim22;
                    Double rprim23;
                    Double rprim31;
                    Double rprim32;
                    Double rprim33;

                    if (angdeg1.equals(angdeg2) && angdeg1.equals(angdeg3) && !angdeg1.equals(90.0)) {
                        Double cosang = Math.cos(Math.PI * angdeg1 / 180.0);
                        Double a2 = 2.0 / 3.0 * (1.0 - cosang);
                        Double aa = Math.sqrt(a2);
                        Double cc = Math.sqrt(1.0 - a2);
                        rprim11 = aa;
                        rprim21 = 0.0;
                        rprim31 = cc;
                        rprim12 = -0.5 * aa;
                        rprim22 = Math.sqrt(3.0) * 0.5 * aa;
                        rprim32 = cc;
                        rprim13 = -0.5 * aa;
                        rprim23 = -Math.sqrt(3.0) * 0.5 * aa;
                        rprim33 = cc;
                    } else {
                        rprim11 = new Double(1.0); // rprim(1,1) OK
                        rprim12 = new Double(Math.cos(Math.PI * angdeg3 / 180.0)); // rprim(1,2) OK
                        rprim13 = new Double(Math.cos(Math.PI * angdeg2 / 180.0)); // rprim(1,3) OK

                        rprim21 = new Double(0.0); // rprim(2,1)
                        rprim22 = new Double(Math.sin(Math.PI * angdeg3 / 180.0)); // rprim(2,2) OK
                        rprim23 = new Double(((Math.cos(Math.PI * angdeg1 / 180.0)
                                - rprim12 * rprim13) / rprim22)); // rprim(2,3) OK

                        rprim31 = new Double(0.0); // rprim(3,1)
                        rprim32 = new Double(0.0); // rprim(3,2)
                        rprim33 = new Double(Math.sqrt(1.0 - Math.pow(rprim13, 2)
                                - Math.pow(rprim23, 2))); // rprim(3,3) OK
                    }

                    // TODO
                    /*tab[0][1] = new Double(df_rprim.format(rprim11 * acell1));
                     tab[1][1] = new Double(df_rprim.format(rprim12 * acell2));
                     tab[2][1] = new Double(df_rprim.format(rprim13 * acell3));

                     tab[0][2] = new Double(df_rprim.format(rprim21 * acell1));
                     tab[1][2] = new Double(df_rprim.format(rprim22 * acell2));
                     tab[2][2] = new Double(df_rprim.format(rprim23 * acell3));

                     tab[0][3] = new Double(df_rprim.format(rprim31 * acell1));
                     tab[1][3] = new Double(df_rprim.format(rprim32 * acell2));
                     tab[2][3] = new Double(df_rprim.format(rprim33 * acell3));*/

                    tab[0][1] = new Double((rprim11 * acell1));
                    tab[1][1] = new Double((rprim12 * acell2));
                    tab[2][1] = new Double((rprim13 * acell3));

                    tab[0][2] = new Double((rprim21 * acell1));
                    tab[1][2] = new Double((rprim22 * acell2));
                    tab[2][2] = new Double((rprim23 * acell3));

                    tab[0][3] = new Double((rprim31 * acell1));
                    tab[1][3] = new Double((rprim32 * acell2));
                    tab[2][3] = new Double((rprim33 * acell3));
                } else {
                    tab = new Object[][]{{"a", new Double(1.0 * acell1), new Double(0.0), new Double(0.0)},
                        {"b", new Double(0.0), new Double(1.0 * acell2), new Double(0.0)},
                        {"c", new Double(0.0), new Double(0.0), new Double(1.0 * acell3)}};
                }
            } else if (rprimCheckBox.isSelected()) {
                rprimCheckBox.setSelected(false);
                rprimCheckBoxActionPerformed(evt);

                // On transforme rprim en rprimd !
                int col = rprimTable.getColumnCount();
                int row = rprimTable.getRowCount();
                Object tmp[][] = new Object[row][col];
                Double acell[] = new Double[]{acell1, acell2, acell3};
                tmp[0][0] = "a";
                tmp[1][0] = "b";
                tmp[2][0] = "c";
                if (row > 0) {
                    for (int i = 0; i < row; i++) {
                        for (int j = 1; j < col; j++) {
                            try {
                                tmp[i][j] = new Double(((Double) rprimTable.getValueAt(i, j)) * acell[i]);
                            } catch (Exception e) {
                                MainFrame.printERR("Error in reading rprimTable (angdegCheckBoxActionPerformed) !");
                            }
                        }
                    }
                    tab = tmp;
                } else {
                    tab = new Object[][]{{"a", new Double(1.0), new Double(0.0), new Double(0.0)},
                        {"b", new Double(0.0), new Double(1.0), new Double(0.0)},
                        {"c", new Double(0.0), new Double(0.0), new Double(1.0)}};
                }
            } else {
                tab = new Object[][]{{"a", new Double(1.0 * acell1), new Double(0.0), new Double(0.0)},
                    {"b", new Double(0.0), new Double(1.0 * acell2), new Double(0.0)},
                    {"c", new Double(0.0), new Double(0.0), new Double(1.0 * acell3)}};
            }

            rprimdCheckBox.setForeground(Color.red);
            initTableHeader(rprimTable, new String[]{"", "x", "y", "z"},
                    new Integer[]{15, null, null, null});
            rprimModel.setData(tab);
            rprimTable.setEnabled(true);
            rprimTable.setVisible(true);
        } else {
            rprimdCheckBox.setForeground(Color.blue);
            rprimTable.setEnabled(false);
            rprimTable.setVisible(false);
        }
}//GEN-LAST:event_rprimdCheckBoxActionPerformed

    private void symrelCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_symrelCheckBoxActionPerformed
        symrelCheckBox.setForeground(Color.red);
        symrelCheckBox.setEnabled(true);
        symrelCheckBox.setSelected(true);
}//GEN-LAST:event_symrelCheckBoxActionPerformed

    private void tnonsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnonsCheckBoxActionPerformed
        tnonsCheckBox.setForeground(Color.red);
        tnonsCheckBox.setEnabled(true);
        tnonsCheckBox.setSelected(true);
}//GEN-LAST:event_tnonsCheckBoxActionPerformed

    private void nsymCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nsymCheckBoxActionPerformed
        if (nsymTextField.getText().equals("0")) {
            if (nsymCheckBox.isSelected()) {
                nsymCheckBox.setForeground(Color.red);
                nsymTextField.setEnabled(true);
                symrelCheckBox.setEnabled(false);
                symrelCheckBox.setSelected(false);
                tnonsCheckBox.setEnabled(false);
                tnonsCheckBox.setSelected(false);
                symrelTable.setEnabled(false);
                symrelTable.setVisible(false);
                tnonsTable.setEnabled(false);
                tnonsTable.setVisible(false);
            } else {
                nsymCheckBox.setForeground(Color.blue);
                nsymTextField.setEnabled(false);
                symrelCheckBox.setEnabled(false);
                symrelCheckBox.setSelected(false);
                tnonsCheckBox.setEnabled(false);
                tnonsCheckBox.setSelected(false);
                symrelTable.setEnabled(false);
                symrelTable.setVisible(false);
                tnonsTable.setEnabled(false);
                tnonsTable.setVisible(false);
            }
        } else {
            if (nsymCheckBox.isSelected()) {
                nsymCheckBox.setForeground(Color.red);
                nsymTextField.setEnabled(true);
                if (nsymTextField.getText().equals("")) {
                    symrelCheckBox.setEnabled(false);
                    symrelCheckBox.setSelected(false);
                    tnonsCheckBox.setEnabled(false);
                    tnonsCheckBox.setSelected(false);
                    symrelTable.setEnabled(false);
                    symrelTable.setVisible(false);
                    tnonsTable.setEnabled(false);
                    tnonsTable.setVisible(false);
                } else {
                    symrelCheckBox.setEnabled(true);
                    symrelCheckBox.setSelected(true);
                    tnonsCheckBox.setEnabled(true);
                    tnonsCheckBox.setSelected(true);
                    symrelTable.setEnabled(true);
                    symrelTable.setVisible(true);
                    tnonsTable.setEnabled(true);
                    tnonsTable.setVisible(true);
                }
            } else {
                nsymCheckBox.setForeground(Color.blue);
                nsymTextField.setEnabled(false);
                symrelCheckBox.setEnabled(false);
                symrelCheckBox.setSelected(false);
                tnonsCheckBox.setEnabled(false);
                tnonsCheckBox.setSelected(false);
                symrelTable.setEnabled(false);
                symrelTable.setVisible(false);
                tnonsTable.setEnabled(false);
                tnonsTable.setVisible(false);
            }
        }
}//GEN-LAST:event_nsymCheckBoxActionPerformed

    private void nsymTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nsymTextFieldKeyReleased
        if (!(nsymTextField.getText().equals("0"))) {

            try {
                int nsym = Integer.parseInt(nsymTextField.getText());

                if (nsym > 400) {
                    nsym = 400;
                    Object strTab[][] = new Object[nsym][3];
                    for (int i = 0; i < nsym; i++) {
                        strTab[i] = new Object[]{new Double(0.0), new Double(0.0), new Double(0.0),
                            new Double(0.0), new Double(0.0), new Double(0.0),
                            new Double(0.0), new Double(0.0), new Double(0.0)};
                    }
                    symrelModel.setData(strTab);
                } else {
                    Object strTab[][] = new Object[nsym][3];
                    for (int i = 0; i < nsym; i++) {
                        strTab[i] = new Object[]{new Double(0.0), new Double(0.0), new Double(0.0),
                            new Double(0.0), new Double(0.0), new Double(0.0),
                            new Double(0.0), new Double(0.0), new Double(0.0)};
                    }
                    symrelModel.setData(strTab);
                }

                if (nsym > 400) {
                    nsym = 400;
                    Object strTab[][] = new Object[nsym][3];
                    for (int i = 0; i < nsym; i++) {
                        strTab[i] = new Object[]{new Double(0.0), new Double(0.0), new Double(0.0)};
                    }
                    tnonsModel.setData(strTab);
                } else {
                    Object strTab[][] = new Object[nsym][3];
                    for (int i = 0; i < nsym; i++) {
                        strTab[i] = new Object[]{new Double(0.0), new Double(0.0), new Double(0.0)};
                    }
                    tnonsModel.setData(strTab);
                }

                if (nsymCheckBox.isSelected()) {
                    nsymCheckBox.setForeground(Color.red);
                    nsymTextField.setEnabled(true);
                    symrelCheckBox.setForeground(Color.red);
                    symrelCheckBox.setEnabled(true);
                    symrelCheckBox.setSelected(true);
                    tnonsCheckBox.setForeground(Color.red);
                    tnonsCheckBox.setEnabled(true);
                    tnonsCheckBox.setSelected(true);
                    symrelTable.setEnabled(true);
                    symrelTable.setVisible(true);
                    tnonsTable.setEnabled(true);
                    tnonsTable.setVisible(true);
                } else {
                    nsymCheckBox.setForeground(Color.blue);
                    nsymTextField.setEnabled(false);
                    symrelCheckBox.setEnabled(false);
                    symrelCheckBox.setSelected(false);
                    tnonsCheckBox.setEnabled(false);
                    tnonsCheckBox.setSelected(false);
                    symrelTable.setEnabled(false);
                    symrelTable.setVisible(false);
                    tnonsTable.setEnabled(false);
                    tnonsTable.setVisible(false);
                }
            } catch (Exception e) {
                //printERR(e.getMessage());
                if (nsymCheckBox.isSelected()) {
                    nsymCheckBox.setForeground(Color.red);
                    nsymTextField.setEnabled(true);
                    symrelCheckBox.setEnabled(false);
                    symrelCheckBox.setSelected(false);
                    tnonsCheckBox.setEnabled(false);
                    tnonsCheckBox.setSelected(false);
                    symrelTable.setEnabled(false);
                    symrelTable.setVisible(false);
                    tnonsTable.setEnabled(false);
                    tnonsTable.setVisible(false);
                } else {
                    nsymCheckBox.setForeground(Color.blue);
                    nsymTextField.setEnabled(false);
                    symrelCheckBox.setEnabled(false);
                    symrelCheckBox.setSelected(false);
                    tnonsCheckBox.setEnabled(false);
                    tnonsCheckBox.setSelected(false);
                    symrelTable.setEnabled(false);
                    symrelTable.setVisible(false);
                    tnonsTable.setEnabled(false);
                    tnonsTable.setVisible(false);
                }
            }
        } else {
            if (nsymCheckBox.isSelected()) {
                nsymCheckBox.setForeground(Color.red);
                nsymTextField.setEnabled(true);
                symrelCheckBox.setSelected(false);
                tnonsCheckBox.setSelected(false);
                symrelTable.setEnabled(false);
                symrelTable.setVisible(false);
                tnonsTable.setEnabled(false);
                tnonsTable.setVisible(false);
            } else {
                nsymCheckBox.setForeground(Color.blue);
                nsymTextField.setEnabled(false);
                symrelCheckBox.setEnabled(false);
                symrelCheckBox.setSelected(false);
                tnonsCheckBox.setEnabled(false);
                tnonsCheckBox.setSelected(false);
                symrelTable.setEnabled(false);
                symrelTable.setVisible(false);
                tnonsTable.setEnabled(false);
                tnonsTable.setVisible(false);
            }
        }
}//GEN-LAST:event_nsymTextFieldKeyReleased

    private void radioButtonHandler(Object[][] data, boolean reset) {
        Enumeration en = xyzbuttonGroup.getElements();
        while (en.hasMoreElements()) {
            JRadioButton jrb = (JRadioButton) en.nextElement();
            //printERR(jrb.isSelected() + " " + jrb.getText());
            if (jrb.isSelected()) {
                Object[][] tmp = null;
                if (data != null) {
                    tmp = data;
                } else {
                    if (reset) {
                        tmp = null;
                    } else {
                        tmp = getTableData(xyzTable);
                    }

                }

                jrb.setForeground(new Color(255, 0, 0));

                double acell1 = 0.0;
                double acell2 = 0.0;
                double acell3 = 0.0;

                try {
                    acell1 = Double.parseDouble(acellTextField1.getText());
                    acell2 = Double.parseDouble(acellTextField2.getText());
                    acell3 = Double.parseDouble(acellTextField3.getText());
                } catch (Exception e) {
                    MainFrame.printERR("Fill acell vector please !");
                }
                switch (jrb.getText()) {
                    case "xred":
                        switch (previous) {
                            case "xred":
                                // ne fait rien comme calcul
                                xyzModel.setHeader(new String[]{"xred", "yred", "zred"});
                                xyzModel.setData(tmp);
                                xyzTable.setModel(xyzModel);
                                break;
                            case "xcart":
                                //tmp = convertxyz(tmp,"xcart -> xred");
                                tmp = xcart2xred(tmp);
                                xyzModel.setHeader(new String[]{"xred", "yred", "zred"});
                                xyzModel.setData(tmp);
                                xyzTable.setModel(xyzModel);
                                break;
                            case "xangst":
                                //tmp = convertxyz(tmp,"xangst -> xred");
                                tmp = xangst2xred(tmp);
                                xyzModel.setHeader(new String[]{"xred", "yred", "zred"});
                                xyzModel.setData(tmp);
                                xyzTable.setModel(xyzModel);
                                break;
                        }

                        previous = "xred";
                    case "xcart":
                        switch (previous) {
                            case "xred":
                                //tmp = convertxyz(tmp,"xred -> xcart");
                                tmp = xred2xcart(tmp);
                                xyzModel.setHeader(new String[]{"xcart", "ycart", "zcart"});
                                xyzModel.setData(tmp);
                                xyzTable.setModel(xyzModel);
                                break;
                            case "xcart":
                                // ne fait rien comme calcul
                                xyzModel.setHeader(new String[]{"xcart", "ycart", "zcart"});
                                xyzModel.setData(tmp);
                                xyzTable.setModel(xyzModel);
                                break;
                            case "xangst":
                                //tmp = convertxyz(tmp,"xangst -> xcart");
                                tmp = xangst2xcart(tmp);
                                xyzModel.setHeader(new String[]{"xcart", "ycart", "zcart"});
                                xyzModel.setData(tmp);
                                xyzTable.setModel(xyzModel);
                                break;
                        }
                        previous = "xcart";
                        break;
                    case "xangst":
                        switch (previous) {
                            case "xred":
                                //tmp = convertxyz(tmp,"xred -> xangst");
                                tmp = xred2xangst(tmp);
                                xyzModel.setHeader(new String[]{"xangst", "yangst", "zangst"});
                                xyzModel.setData(tmp);
                                xyzTable.setModel(xyzModel);
                                break;
                            case "xcart":
                                //tmp = convertxyz(tmp,"xcart -> xangst");
                                tmp = xcart2xangst(tmp);
                                xyzModel.setHeader(new String[]{"xangst", "yangst", "zangst"});
                                xyzModel.setData(tmp);
                                xyzTable.setModel(xyzModel);
                                break;
                            case "xangst":
                                // ne fait rien comme calcul
                                xyzModel.setHeader(new String[]{"xangst", "yangst", "zangst"});
                                xyzModel.setData(tmp);
                                xyzTable.setModel(xyzModel);
                                break;
                        }
                        previous = "xangst";
                        break;
                }
            } else {
                jrb.setForeground(new Color(0, 0, 0));
            }

        }
    }

    private Object[][] getTableData(JTable xyzTable) {
        int col = xyzTable.getColumnCount();
        int row = xyzTable.getRowCount();
        if (row > 0) {
            Object[][] tmp = new Object[row][col];

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    try {
                        tmp[i][j] = xyzTable.getValueAt(i, j);
                        //Double doub = (Double) xyzTable.getValueAt(i, j);
                        //printERR(doub);
                    } catch (Exception e) {
                        MainFrame.printERR(e.getMessage());
                    }

                }
            }

            return tmp;
        } else {
            return null;
        }

    }

    private Object[][] xred2xcart(Object[][] data) {
        if (data != null) {
            Double a1;
            Double a2;
            Double a3;
            try {
                a1 = Double.parseDouble(acellTextField1.getText());
                a2 = Double.parseDouble(acellTextField2.getText());
                a3 = Double.parseDouble(acellTextField3.getText());
            } catch (Exception e) {
                MainFrame.printERR("Please setup acell !");
                return data;
            }

            int col = rprimTable.getColumnCount();
            int row = rprimTable.getRowCount();
            Double rprim[][] = new Double[row][col - 1];
            if (row > 0) {
                for (int i = 0; i < row; i++) {
                    for (int j = 1; j < col; j++) {
                        try {
                            rprim[i][j - 1] = (Double) rprimTable.getValueAt(i, j);
                        } catch (Exception e) {
                            MainFrame.printERR("Please setup rprim or angdeg (problem with getValue from rprimTable) !");
                            return data;
                        }
                    }
                }
            } else {
                MainFrame.printERR("Please setup rprim or angdeg !");
                return data;
            }
            Double r1[];
            Double r2[];
            Double r3[];
            if (rprimCheckBox.isSelected()) {
                r1 = new Double[]{rprim[0][0], rprim[0][1], rprim[0][2]};
                r2 = new Double[]{rprim[1][0], rprim[1][1], rprim[1][2]};
                r3 = new Double[]{rprim[2][0], rprim[2][1], rprim[2][2]};
            } else if (rprimdCheckBox.isSelected()) {
                r1 = new Double[]{rprim[0][0] / a1, rprim[0][1] / a1, rprim[0][2] / a1};
                r2 = new Double[]{rprim[1][0] / a2, rprim[1][1] / a2, rprim[1][2] / a2};
                r3 = new Double[]{rprim[2][0] / a3, rprim[2][1] / a3, rprim[2][2] / a3};
            } else {
                MainFrame.printDEB("ang1 = " + rprim[0][0] + " ang2 = " + rprim[1][0] + " ang3 = " + rprim[2][0]);
                Double angdeg1 = rprim[0][0];
                Double angdeg2 = rprim[1][0];
                Double angdeg3 = rprim[2][0];

                Double rprim11;
                Double rprim12;
                Double rprim13;
                Double rprim21;
                Double rprim22;
                Double rprim23;
                Double rprim31;
                Double rprim32;
                Double rprim33;

                if (angdeg1.equals(angdeg2) && angdeg1.equals(angdeg3) && !angdeg1.equals(90.0)) {
                    Double cosang = Math.cos(Math.PI * angdeg1 / 180.0);
                    Double a2_ = 2.0 / 3.0 * (1.0 - cosang);
                    Double aa = Math.sqrt(a2_);
                    Double cc = Math.sqrt(1.0 - a2_);
                    rprim11 = aa;
                    rprim21 = 0.0;
                    rprim31 = cc;
                    rprim12 = -0.5 * aa;
                    rprim22 = Math.sqrt(3.0) * 0.5 * aa;
                    rprim32 = cc;
                    rprim13 = -0.5 * aa;
                    rprim23 = -Math.sqrt(3.0) * 0.5 * aa;
                    rprim33 = cc;
                } else {
                    rprim11 = new Double(1.0); // rprim(1,1) OK
                    rprim12 = new Double(Math.cos(Math.PI * angdeg3 / 180.0)); // rprim(1,2) OK
                    rprim13 = new Double(Math.cos(Math.PI * angdeg2 / 180.0)); // rprim(1,3) OK

                    rprim21 = new Double(0.0); // rprim(2,1)
                    rprim22 = new Double(Math.sin(Math.PI * angdeg3 / 180.0)); // rprim(2,2) OK
                    rprim23 = new Double(((Math.cos(Math.PI * angdeg1 / 180.0)
                            - rprim12 * rprim13) / rprim22)); // rprim(2,3) OK

                    rprim31 = new Double(0.0); // rprim(3,1)
                    rprim32 = new Double(0.0); // rprim(3,2)
                    rprim33 = new Double(Math.sqrt(1.0 - Math.pow(rprim13, 2)
                            - Math.pow(rprim23, 2))); // rprim(3,3) OK
                }

                r1 = new Double[]{rprim11, rprim21, rprim31};
                r2 = new Double[]{rprim12, rprim22, rprim32};
                r3 = new Double[]{rprim13, rprim23, rprim33};
            }
            Double xred2xcart[][] = new Double[][]{{r1[0] * a1, r1[1] * a1, r1[2] * a1},
                {r2[0] * a2, r2[1] * a2, r2[2] * a2}, {r3[0] * a3, r3[1] * a3, r3[2] * a3}};

            Object result[][] = new Object[data.length][3];

            for (int k = 0; k < data.length; k++) {
                Double X = 0.0;
                Double Y = 0.0;
                Double Z = 0.0;
                for (int i = 0; i < 3; i++) {
                    X += xred2xcart[0][i] * ((Double) data[k][i]);
                    Y += xred2xcart[1][i] * ((Double) data[k][i]);
                    Z += xred2xcart[2][i] * ((Double) data[k][i]);
                }

                // TODO
                result[k] = new Double[]{
                    new Double(df_rprim.format(X)),
                    new Double(df_rprim.format(Y)),
                    new Double(df_rprim.format(Z))};

                //result[k] = new Double[]{new Double(X), new Double(Y), new Double(Z)};
            }
            return result;
        } else {
            return null;
        }
    }

    private Object[][] xred2xangst(Object[][] data) {
        if (data != null) {
            Double a1;
            Double a2;
            Double a3;
            try {
                a1 = Double.parseDouble(acellTextField1.getText());
                a2 = Double.parseDouble(acellTextField2.getText());
                a3 = Double.parseDouble(acellTextField3.getText());
            } catch (Exception e) {
                MainFrame.printERR("Please setup acell !");
                return data;
            }
            int col = rprimTable.getColumnCount();
            int row = rprimTable.getRowCount();
            Double rprim[][] = new Double[row][col - 1];
            if (row > 0) {
                for (int i = 0; i < row; i++) {
                    for (int j = 1; j < col; j++) {
                        try {
                            rprim[i][j - 1] = (Double) rprimTable.getValueAt(i, j);
                        } catch (Exception e) {
                            MainFrame.printERR("Please setup rprim or angdeg (problem with getValue from rprimTable) !");
                            return data;
                        }
                    }
                }
            } else {
                MainFrame.printERR("Please setup rprim or angdeg !");
                return data;
            }
            Double r1[];
            Double r2[];
            Double r3[];
            if (rprimCheckBox.isSelected()) {
                r1 = new Double[]{rprim[0][0], rprim[0][1], rprim[0][2]};
                r2 = new Double[]{rprim[1][0], rprim[1][1], rprim[1][2]};
                r3 = new Double[]{rprim[2][0], rprim[2][1], rprim[2][2]};
            } else if (rprimdCheckBox.isSelected()) {
                r1 = new Double[]{rprim[0][0] / a1, rprim[0][1] / a2, rprim[0][2] / a3};
                r2 = new Double[]{rprim[1][0] / a1, rprim[1][1] / a2, rprim[1][2] / a3};
                r3 = new Double[]{rprim[2][0] / a1, rprim[2][1] / a2, rprim[2][2] / a3};
            } else {
                MainFrame.printDEB("ang1 = " + rprim[0][0] + " ang2 = " + rprim[1][0] + " ang3 = " + rprim[2][0]);
                Double angdeg1 = rprim[0][0];
                Double angdeg2 = rprim[1][0];
                Double angdeg3 = rprim[2][0];

                Double rprim11;
                Double rprim12;
                Double rprim13;
                Double rprim21;
                Double rprim22;
                Double rprim23;
                Double rprim31;
                Double rprim32;
                Double rprim33;

                if (angdeg1.equals(angdeg2) && angdeg1.equals(angdeg3) && !angdeg1.equals(90.0)) {
                    Double cosang = Math.cos(Math.PI * angdeg1 / 180.0);
                    Double a2_ = 2.0 / 3.0 * (1.0 - cosang);
                    Double aa = Math.sqrt(a2_);
                    Double cc = Math.sqrt(1.0 - a2_);
                    rprim11 = aa;
                    rprim21 = 0.0;
                    rprim31 = cc;
                    rprim12 = -0.5 * aa;
                    rprim22 = Math.sqrt(3.0) * 0.5 * aa;
                    rprim32 = cc;
                    rprim13 = -0.5 * aa;
                    rprim23 = -Math.sqrt(3.0) * 0.5 * aa;
                    rprim33 = cc;
                } else {
                    rprim11 = new Double(1.0); // rprim(1,1) OK
                    rprim12 = new Double(Math.cos(Math.PI * angdeg3 / 180.0)); // rprim(1,2) OK
                    rprim13 = new Double(Math.cos(Math.PI * angdeg2 / 180.0)); // rprim(1,3) OK

                    rprim21 = new Double(0.0); // rprim(2,1)
                    rprim22 = new Double(Math.sin(Math.PI * angdeg3 / 180.0)); // rprim(2,2) OK
                    rprim23 = new Double(((Math.cos(Math.PI * angdeg1 / 180.0)
                            - rprim12 * rprim13) / rprim22)); // rprim(2,3) OK

                    rprim31 = new Double(0.0); // rprim(3,1)
                    rprim32 = new Double(0.0); // rprim(3,2)
                    rprim33 = new Double(Math.sqrt(1.0 - Math.pow(rprim13, 2)
                            - Math.pow(rprim23, 2))); // rprim(3,3) OK
                }

                r1 = new Double[]{rprim11, rprim21, rprim31};
                r2 = new Double[]{rprim12, rprim22, rprim32};
                r3 = new Double[]{rprim13, rprim23, rprim33};
            }
            Double xred2xcart[][] = new Double[][]{{r1[0] * a1, r1[1] * a1, r1[2] * a1},
                {r2[0] * a2, r2[1] * a2, r2[2] * a2}, {r3[0] * a3, r3[1] * a3, r3[2] * a3}};

            Object result[][] = new Object[data.length][3];

            for (int k = 0; k < data.length; k++) {
                Double Xa = 0.0;
                Double Ya = 0.0;
                Double Za = 0.0;
                for (int i = 0; i < 3; i++) {
                    Xa += xred2xcart[0][i] * ((Double) data[k][i]);
                    Ya += xred2xcart[1][i] * ((Double) data[k][i]);
                    Za += xred2xcart[2][i] * ((Double) data[k][i]);
                }

                // TODO
                result[k] = new Double[]{
                    new Double(df_rprim.format(Xa * bohr2angst)),
                    new Double(df_rprim.format(Ya * bohr2angst)),
                    new Double(df_rprim.format(Za * bohr2angst))};

                /*result[k] = new Double[]{new Double((Xa * bohr2angst)),
                 new Double((Ya * bohr2angst)), new Double((Za * bohr2angst))};*/
            }
            return result;
        } else {
            return null;
        }
    }

    private Object[][] xcart2xred(Object[][] data) {
        if (data != null) {
            Double a1;
            Double a2;
            Double a3;
            try {
                a1 = Double.parseDouble(acellTextField1.getText());
                a2 = Double.parseDouble(acellTextField2.getText());
                a3 = Double.parseDouble(acellTextField3.getText());
            } catch (Exception e) {
                MainFrame.printERR("Please setup acell !");
                return data;
            }
            int col = rprimTable.getColumnCount();
            int row = rprimTable.getRowCount();
            Double rprim[][] = new Double[row][col - 1];
            if (row > 0) {
                for (int i = 0; i < row; i++) {
                    for (int j = 1; j < col; j++) {
                        try {
                            rprim[i][j - 1] = (Double) rprimTable.getValueAt(i, j);
                        } catch (Exception e) {
                            MainFrame.printERR("Please setup rprim or angdeg (wrong values) !");
                            return data;
                        }
                    }
                }
            } else {
                MainFrame.printERR("Please setup rprim or angdeg !");
                return data;
            }
            Double r1[];
            Double r2[];
            Double r3[];
            if (rprimCheckBox.isSelected()) {
                r1 = new Double[]{rprim[0][0], rprim[0][1], rprim[0][2]};
                r2 = new Double[]{rprim[1][0], rprim[1][1], rprim[1][2]};
                r3 = new Double[]{rprim[2][0], rprim[2][1], rprim[2][2]};
            } else if (rprimdCheckBox.isSelected()) {
                r1 = new Double[]{rprim[0][0] / a1, rprim[0][1] / a2, rprim[0][2] / a3};
                r2 = new Double[]{rprim[1][0] / a1, rprim[1][1] / a2, rprim[1][2] / a3};
                r3 = new Double[]{rprim[2][0] / a1, rprim[2][1] / a2, rprim[2][2] / a3};
            } else {
                MainFrame.printDEB("ang1 = " + rprim[0][0] + " ang2 = " + rprim[1][0] + " ang3 = " + rprim[2][0]);
                Double angdeg1 = rprim[0][0];
                Double angdeg2 = rprim[1][0];
                Double angdeg3 = rprim[2][0];

                Double rprim11;
                Double rprim12;
                Double rprim13;
                Double rprim21;
                Double rprim22;
                Double rprim23;
                Double rprim31;
                Double rprim32;
                Double rprim33;

                if (angdeg1.equals(angdeg2) && angdeg1.equals(angdeg3) && !angdeg1.equals(90.0)) {
                    Double cosang = Math.cos(Math.PI * angdeg1 / 180.0);
                    Double a2_ = 2.0 / 3.0 * (1.0 - cosang);
                    Double aa = Math.sqrt(a2_);
                    Double cc = Math.sqrt(1.0 - a2_);
                    rprim11 = aa;
                    rprim21 = 0.0;
                    rprim31 = cc;
                    rprim12 = -0.5 * aa;
                    rprim22 = Math.sqrt(3.0) * 0.5 * aa;
                    rprim32 = cc;
                    rprim13 = -0.5 * aa;
                    rprim23 = -Math.sqrt(3.0) * 0.5 * aa;
                    rprim33 = cc;
                } else {
                    rprim11 = new Double(1.0); // rprim(1,1) OK
                    rprim12 = new Double(Math.cos(Math.PI * angdeg3 / 180.0)); // rprim(1,2) OK
                    rprim13 = new Double(Math.cos(Math.PI * angdeg2 / 180.0)); // rprim(1,3) OK

                    rprim21 = new Double(0.0); // rprim(2,1)
                    rprim22 = new Double(Math.sin(Math.PI * angdeg3 / 180.0)); // rprim(2,2) OK
                    rprim23 = new Double(((Math.cos(Math.PI * angdeg1 / 180.0)
                            - rprim12 * rprim13) / rprim22)); // rprim(2,3) OK

                    rprim31 = new Double(0.0); // rprim(3,1)
                    rprim32 = new Double(0.0); // rprim(3,2)
                    rprim33 = new Double(Math.sqrt(1.0 - Math.pow(rprim13, 2)
                            - Math.pow(rprim23, 2))); // rprim(3,3) OK
                }

                r1 = new Double[]{rprim11, rprim21, rprim31};
                r2 = new Double[]{rprim12, rprim22, rprim32};
                r3 = new Double[]{rprim13, rprim23, rprim33};
            }
            Double A11 = (r2[1] * a2 * r3[2] * a3 - r2[2] * a2 * r3[1] * a3)
                    / (r1[0] * a1 * r2[1] * a2 * r3[2] * a3 - r1[0] * a1 * r2[2] * a2 * r3[1] * a3
                    - r1[1] * a1 * r2[0] * a2 * r3[2] * a3 + r1[1] * a1 * r2[2] * a2 * r3[0] * a3
                    + r1[2] * a1 * r2[0] * a2 * r3[1] * a3 - r1[2] * a1 * r2[1] * a2 * r3[0] * a3);
            Double A12 = -(r1[1] * a1 * r3[2] * a3 - r1[2] * a1 * r3[1] * a3)
                    / (r1[0] * a1 * r2[1] * a2 * r3[2] * a3 - r1[0] * a1 * r2[2] * a2 * r3[1] * a3
                    - r1[1] * a1 * r2[0] * a2 * r3[2] * a3 + r1[1] * a1 * r2[2] * a2 * r3[0] * a3
                    + r1[2] * a1 * r2[0] * a2 * r3[1] * a3 - r1[2] * a1 * r2[1] * a2 * r3[0] * a3);
            Double A13 = (r1[1] * a1 * r2[2] * a2 - r1[2] * a1 * r2[1] * a2)
                    / (r1[0] * a1 * r2[1] * a2 * r3[2] * a3 - r1[0] * a1 * r2[2] * a2 * r3[1] * a3
                    - r1[1] * a1 * r2[0] * a2 * r3[2] * a3 + r1[1] * a1 * r2[2] * a2 * r3[0] * a3
                    + r1[2] * a1 * r2[0] * a2 * r3[1] * a3 - r1[2] * a1 * r2[1] * a2 * r3[0] * a3);
            Double A21 = -(r2[0] * a2 * r3[2] * a3 - r2[2] * a2 * r3[0] * a3)
                    / (r1[0] * a1 * r2[1] * a2 * r3[2] * a3 - r1[0] * a1 * r2[2] * a2 * r3[1] * a3
                    - r1[1] * a1 * r2[0] * a2 * r3[2] * a3 + r1[1] * a1 * r2[2] * a2 * r3[0] * a3
                    + r1[2] * a1 * r2[0] * a2 * r3[1] * a3 - r1[2] * a1 * r2[1] * a2 * r3[0] * a3);
            Double A22 = (r1[0] * a1 * r3[2] * a3 - r1[2] * a1 * r3[0] * a3)
                    / (r1[0] * a1 * r2[1] * a2 * r3[2] * a3 - r1[0] * a1 * r2[2] * a2 * r3[1] * a3
                    - r1[1] * a1 * r2[0] * a2 * r3[2] * a3 + r1[1] * a1 * r2[2] * a2 * r3[0] * a3
                    + r1[2] * a1 * r2[0] * a2 * r3[1] * a3 - r1[2] * a1 * r2[1] * a2 * r3[0] * a3);
            Double A23 = -(r1[0] * a1 * r2[2] * a2 - r1[2] * a1 * r2[0] * a2)
                    / (r1[0] * a1 * r2[1] * a2 * r3[2] * a3 - r1[0] * a1 * r2[2] * a2 * r3[1] * a3
                    - r1[1] * a1 * r2[0] * a2 * r3[2] * a3 + r1[1] * a1 * r2[2] * a2 * r3[0] * a3
                    + r1[2] * a1 * r2[0] * a2 * r3[1] * a3 - r1[2] * a1 * r2[1] * a2 * r3[0] * a3);
            Double A31 = (r2[0] * a2 * r3[1] * a3 - r2[1] * a2 * r3[0] * a3)
                    / (r1[0] * a1 * r2[1] * a2 * r3[2] * a3 - r1[0] * a1 * r2[2] * a2 * r3[1] * a3
                    - r1[1] * a1 * r2[0] * a2 * r3[2] * a3 + r1[1] * a1 * r2[2] * a2 * r3[0] * a3
                    + r1[2] * a1 * r2[0] * a2 * r3[1] * a3 - r1[2] * a1 * r2[1] * a2 * r3[0] * a3);
            Double A32 = -(r1[0] * a1 * r3[1] * a3 - r1[1] * a1 * r3[0] * a3)
                    / (r1[0] * a1 * r2[1] * a2 * r3[2] * a3 - r1[0] * a1 * r2[2] * a2 * r3[1] * a3
                    - r1[1] * a1 * r2[0] * a2 * r3[2] * a3 + r1[1] * a1 * r2[2] * a2 * r3[0] * a3
                    + r1[2] * a1 * r2[0] * a2 * r3[1] * a3 - r1[2] * a1 * r2[1] * a2 * r3[0] * a3);
            Double A33 = (r1[0] * a1 * r2[1] * a2 - r1[1] * a1 * r2[0] * a2)
                    / (r1[0] * a1 * r2[1] * a2 * r3[2] * a3 - r1[0] * a1 * r2[2] * a2 * r3[1] * a3
                    - r1[1] * a1 * r2[0] * a2 * r3[2] * a3 + r1[1] * a1 * r2[2] * a2 * r3[0] * a3
                    + r1[2] * a1 * r2[0] * a2 * r3[1] * a3 - r1[2] * a1 * r2[1] * a2 * r3[0] * a3);
            Double xcart2xred[][] = new Double[][]{{A11, A12, A13}, {A21, A22, A23}, {A31, A32, A33}};

            Object result[][] = new Object[data.length][3];

            for (int k = 0; k < data.length; k++) {
                Double Xr = 0.0;
                Double Yr = 0.0;
                Double Zr = 0.0;
                for (int i = 0; i < 3; i++) {
                    Xr += xcart2xred[0][i] * ((Double) data[k][i]);
                    Yr += xcart2xred[1][i] * ((Double) data[k][i]);
                    Zr += xcart2xred[2][i] * ((Double) data[k][i]);
                }

                // TODO
                result[k] = new Double[]{
                    new Double(df_rprim.format(Xr)),
                    new Double(df_rprim.format(Yr)),
                    new Double(df_rprim.format(Zr))};
                //result[k] = new Double[]{new Double((Xr)), new Double((Yr)), new Double((Zr))};
            }
            return result;
        } else {
            return null;
        }
    }

    private Object[][] xangst2xred(Object[][] data) {
        if (data != null) {
            Double a1;
            Double a2;
            Double a3;
            try {
                a1 = Double.parseDouble(acellTextField1.getText());
                a2 = Double.parseDouble(acellTextField2.getText());
                a3 = Double.parseDouble(acellTextField3.getText());
            } catch (Exception e) {
                MainFrame.printERR("Please setup acell !");
                return data;
            }
            int col = rprimTable.getColumnCount();
            int row = rprimTable.getRowCount();
            Double rprim[][] = new Double[row][col - 1];
            if (row > 0) {
                for (int i = 0; i < row; i++) {
                    for (int j = 1; j < col; j++) {
                        try {
                            rprim[i][j - 1] = (Double) rprimTable.getValueAt(i, j);
                        } catch (Exception e) {
                            MainFrame.printERR("Please setup rprim or angdeg (wrong values) !");
                            return data;
                        }
                    }
                }
            } else {
                MainFrame.printERR("Please setup rprim or angdeg !");
                return data;
            }
            Double r1[];
            Double r2[];
            Double r3[];
            if (rprimCheckBox.isSelected()) {
                r1 = new Double[]{rprim[0][0], rprim[0][1], rprim[0][2]};
                r2 = new Double[]{rprim[1][0], rprim[1][1], rprim[1][2]};
                r3 = new Double[]{rprim[2][0], rprim[2][1], rprim[2][2]};
            } else if (rprimdCheckBox.isSelected()) {
                r1 = new Double[]{rprim[0][0] / a1, rprim[0][1] / a2, rprim[0][2] / a3};
                r2 = new Double[]{rprim[1][0] / a1, rprim[1][1] / a2, rprim[1][2] / a3};
                r3 = new Double[]{rprim[2][0] / a1, rprim[2][1] / a2, rprim[2][2] / a3};
            } else {
                MainFrame.printDEB("ang1 = " + rprim[0][0] + " ang2 = " + rprim[1][0] + " ang3 = " + rprim[2][0]);
                Double angdeg1 = rprim[0][0];
                Double angdeg2 = rprim[1][0];
                Double angdeg3 = rprim[2][0];

                Double rprim11;
                Double rprim12;
                Double rprim13;
                Double rprim21;
                Double rprim22;
                Double rprim23;
                Double rprim31;
                Double rprim32;
                Double rprim33;

                if (angdeg1.equals(angdeg2) && angdeg1.equals(angdeg3) && !angdeg1.equals(90.0)) {
                    Double cosang = Math.cos(Math.PI * angdeg1 / 180.0);
                    Double a2_ = 2.0 / 3.0 * (1.0 - cosang);
                    Double aa = Math.sqrt(a2_);
                    Double cc = Math.sqrt(1.0 - a2_);
                    rprim11 = aa;
                    rprim21 = 0.0;
                    rprim31 = cc;
                    rprim12 = -0.5 * aa;
                    rprim22 = Math.sqrt(3.0) * 0.5 * aa;
                    rprim32 = cc;
                    rprim13 = -0.5 * aa;
                    rprim23 = -Math.sqrt(3.0) * 0.5 * aa;
                    rprim33 = cc;
                } else {
                    rprim11 = new Double(1.0); // rprim(1,1) OK
                    rprim12 = new Double(Math.cos(Math.PI * angdeg3 / 180.0)); // rprim(1,2) OK
                    rprim13 = new Double(Math.cos(Math.PI * angdeg2 / 180.0)); // rprim(1,3) OK

                    rprim21 = new Double(0.0); // rprim(2,1)
                    rprim22 = new Double(Math.sin(Math.PI * angdeg3 / 180.0)); // rprim(2,2) OK
                    rprim23 = new Double(((Math.cos(Math.PI * angdeg1 / 180.0)
                            - rprim12 * rprim13) / rprim22)); // rprim(2,3) OK

                    rprim31 = new Double(0.0); // rprim(3,1)
                    rprim32 = new Double(0.0); // rprim(3,2)
                    rprim33 = new Double(Math.sqrt(1.0 - Math.pow(rprim13, 2)
                            - Math.pow(rprim23, 2))); // rprim(3,3) OK
                }

                r1 = new Double[]{rprim11, rprim21, rprim31};
                r2 = new Double[]{rprim12, rprim22, rprim32};
                r3 = new Double[]{rprim13, rprim23, rprim33};
            }
            Double A11 = (r2[1] * a2 * r3[2] * a3 - r2[2] * a2 * r3[1] * a3)
                    / (r1[0] * a1 * r2[1] * a2 * r3[2] * a3 - r1[0] * a1 * r2[2] * a2 * r3[1] * a3
                    - r1[1] * a1 * r2[0] * a2 * r3[2] * a3 + r1[1] * a1 * r2[2] * a2 * r3[0] * a3
                    + r1[2] * a1 * r2[0] * a2 * r3[1] * a3 - r1[2] * a1 * r2[1] * a2 * r3[0] * a3);
            Double A12 = -(r1[1] * a1 * r3[2] * a3 - r1[2] * a1 * r3[1] * a3)
                    / (r1[0] * a1 * r2[1] * a2 * r3[2] * a3 - r1[0] * a1 * r2[2] * a2 * r3[1] * a3
                    - r1[1] * a1 * r2[0] * a2 * r3[2] * a3 + r1[1] * a1 * r2[2] * a2 * r3[0] * a3
                    + r1[2] * a1 * r2[0] * a2 * r3[1] * a3 - r1[2] * a1 * r2[1] * a2 * r3[0] * a3);
            Double A13 = (r1[1] * a1 * r2[2] * a2 - r1[2] * a1 * r2[1] * a2)
                    / (r1[0] * a1 * r2[1] * a2 * r3[2] * a3 - r1[0] * a1 * r2[2] * a2 * r3[1] * a3
                    - r1[1] * a1 * r2[0] * a2 * r3[2] * a3 + r1[1] * a1 * r2[2] * a2 * r3[0] * a3
                    + r1[2] * a1 * r2[0] * a2 * r3[1] * a3 - r1[2] * a1 * r2[1] * a2 * r3[0] * a3);
            Double A21 = -(r2[0] * a2 * r3[2] * a3 - r2[2] * a2 * r3[0] * a3)
                    / (r1[0] * a1 * r2[1] * a2 * r3[2] * a3 - r1[0] * a1 * r2[2] * a2 * r3[1] * a3
                    - r1[1] * a1 * r2[0] * a2 * r3[2] * a3 + r1[1] * a1 * r2[2] * a2 * r3[0] * a3
                    + r1[2] * a1 * r2[0] * a2 * r3[1] * a3 - r1[2] * a1 * r2[1] * a2 * r3[0] * a3);
            Double A22 = (r1[0] * a1 * r3[2] * a3 - r1[2] * a1 * r3[0] * a3)
                    / (r1[0] * a1 * r2[1] * a2 * r3[2] * a3 - r1[0] * a1 * r2[2] * a2 * r3[1] * a3
                    - r1[1] * a1 * r2[0] * a2 * r3[2] * a3 + r1[1] * a1 * r2[2] * a2 * r3[0] * a3
                    + r1[2] * a1 * r2[0] * a2 * r3[1] * a3 - r1[2] * a1 * r2[1] * a2 * r3[0] * a3);
            Double A23 = -(r1[0] * a1 * r2[2] * a2 - r1[2] * a1 * r2[0] * a2)
                    / (r1[0] * a1 * r2[1] * a2 * r3[2] * a3 - r1[0] * a1 * r2[2] * a2 * r3[1] * a3
                    - r1[1] * a1 * r2[0] * a2 * r3[2] * a3 + r1[1] * a1 * r2[2] * a2 * r3[0] * a3
                    + r1[2] * a1 * r2[0] * a2 * r3[1] * a3 - r1[2] * a1 * r2[1] * a2 * r3[0] * a3);
            Double A31 = (r2[0] * a2 * r3[1] * a3 - r2[1] * a2 * r3[0] * a3)
                    / (r1[0] * a1 * r2[1] * a2 * r3[2] * a3 - r1[0] * a1 * r2[2] * a2 * r3[1] * a3
                    - r1[1] * a1 * r2[0] * a2 * r3[2] * a3 + r1[1] * a1 * r2[2] * a2 * r3[0] * a3
                    + r1[2] * a1 * r2[0] * a2 * r3[1] * a3 - r1[2] * a1 * r2[1] * a2 * r3[0] * a3);
            Double A32 = -(r1[0] * a1 * r3[1] * a3 - r1[1] * a1 * r3[0] * a3)
                    / (r1[0] * a1 * r2[1] * a2 * r3[2] * a3 - r1[0] * a1 * r2[2] * a2 * r3[1] * a3
                    - r1[1] * a1 * r2[0] * a2 * r3[2] * a3 + r1[1] * a1 * r2[2] * a2 * r3[0] * a3
                    + r1[2] * a1 * r2[0] * a2 * r3[1] * a3 - r1[2] * a1 * r2[1] * a2 * r3[0] * a3);
            Double A33 = (r1[0] * a1 * r2[1] * a2 - r1[1] * a1 * r2[0] * a2)
                    / (r1[0] * a1 * r2[1] * a2 * r3[2] * a3 - r1[0] * a1 * r2[2] * a2 * r3[1] * a3
                    - r1[1] * a1 * r2[0] * a2 * r3[2] * a3 + r1[1] * a1 * r2[2] * a2 * r3[0] * a3
                    + r1[2] * a1 * r2[0] * a2 * r3[1] * a3 - r1[2] * a1 * r2[1] * a2 * r3[0] * a3);
            Double xcart2xred[][] = new Double[][]{{A11, A12, A13}, {A21, A22, A23}, {A31, A32, A33}};

            Object result[][] = new Object[data.length][3];

            for (int k = 0; k < data.length; k++) {
                Double Xr = 0.0;
                Double Yr = 0.0;
                Double Zr = 0.0;
                for (int i = 0; i < 3; i++) {
                    Xr += xcart2xred[0][i] * ((Double) data[k][i]);
                    Yr += xcart2xred[1][i] * ((Double) data[k][i]);
                    Zr += xcart2xred[2][i] * ((Double) data[k][i]);
                }

                // TODO
                result[k] = new Double[]{
                    new Double(df_rprim.format(Xr / bohr2angst)),
                    new Double(df_rprim.format(Yr / bohr2angst)),
                    new Double(df_rprim.format(Zr / bohr2angst))};

                //result[k] = new Double[]{new Double((Xr / bohr2angst)), new Double((Yr / bohr2angst)), new Double((Zr / bohr2angst))};
            }
            return result;
        } else {
            return null;
        }
    }

    private Object[][] xcart2xangst(Object[][] data) {
        if (data != null) {
            Object result[][] = new Object[data.length][3];
            for (int k = 0; k < data.length; k++) {
                Double Xa = ((Double) data[k][0]);
                Double Ya = ((Double) data[k][1]);
                Double Za = ((Double) data[k][2]);

                // TODO
                result[k] = new Double[]{
                    new Double(df_rprim.format(Xa * bohr2angst)),
                    new Double(df_rprim.format(Ya * bohr2angst)),
                    new Double(df_rprim.format(Za * bohr2angst))};

                /*result[k] = new Double[]{new Double((Xa * bohr2angst)),
                 new Double((Ya * bohr2angst)), new Double((Za * bohr2angst))};*/
            }
            return result;
        } else {
            return null;
        }
    }

    private Object[][] xangst2xcart(Object[][] data) {
        if (data != null) {
            Object result[][] = new Object[data.length][3];
            for (int k = 0; k < data.length; k++) {
                Double Xa = ((Double) data[k][0]);
                Double Ya = ((Double) data[k][1]);
                Double Za = ((Double) data[k][2]);

                // TODO
                result[k] = new Double[]{
                    new Double(df_rprim.format(Xa / bohr2angst)),
                    new Double(df_rprim.format(Ya / bohr2angst)),
                    new Double(df_rprim.format(Za / bohr2angst))};

                /*result[k] = new Double[]{new Double((Xa / bohr2angst)),
                 new Double((Ya / bohr2angst)), new Double((Za / bohr2angst))};*/
            }
            return result;
        } else {
            return null;
        }
    }

    public String getData() {
        String file = new String();

        // ACELL ***************************************************************
        if (acellCheckBox.isSelected()) {
            try {
                double acell1, acell2, acell3;
                acell1 = Double.parseDouble(acellTextField1.getText());
                acell2 = Double.parseDouble(acellTextField2.getText());
                acell3 = Double.parseDouble(acellTextField3.getText());
                if (acell1 == acell2 && acell2 == acell3) {
                    file += acellCheckBox.getText() + " 3*" + acell1 + "\n\n";
                } else {
                    file += acellCheckBox.getText() + " " + acell1 + " " + acell2 + " " + acell3 + "\n\n";
                }

            } catch (Exception e) {
                MainFrame.printERR("Please set up ACELL !");
            }

        }
        // NATOM ***************************************************************
        if (natomCheckBox.isSelected()) {
            try {
                int natom;
                natom = Integer.parseInt(natomTextField.getText());
                file += natomCheckBox.getText() + " " + natom + "\n\n";
            } catch (Exception e) {
                MainFrame.printERR("Please set up NATOM !");
            }

        }
        // XRED, XCART, XANGST *************************************************
        if (natomCheckBox.isSelected()) {
            Enumeration en = xyzbuttonGroup.getElements();
            while (en.hasMoreElements()) {
                JRadioButton jrb = (JRadioButton) en.nextElement();
                if (jrb.isSelected()) {
                    int col = xyzTable.getColumnCount();
                    int row = xyzTable.getRowCount();
                    if (row > 0) {
                        int len = jrb.getText().length();
                        file +=
                                jrb.getText();
                        for (int i = 0; i
                                < row; i++) {
                            for (int j = 0; j
                                    < col; j++) {
                                try {
                                    file += " " + xyzTable.getValueAt(i, j);
                                } catch (Exception e) {
                                    MainFrame.printERR("Please set up XYZ !");
                                }
                            }
                            file += "\n";
                            for (int j = 0; j
                                    < len; j++) {
                                file += " ";
                            }
                        }
                        file += "\n";
                    }
                }
            }
        }
        // RPRIM ***************************************************************
        if (rprimCheckBox.isSelected()) {

            int col = rprimTable.getColumnCount();
            int row = rprimTable.getRowCount();
            if (row > 0) {
                file += rprimCheckBox.getText() + " ";
                for (int i = 0; i
                        < row; i++) {
                    for (int j = 1; j
                            < col; j++) {
                        try {
                            file += rprimTable.getValueAt(i, j) + " ";
                        } catch (Exception e) {
                            MainFrame.printERR("Please set up RPRIM !");
                        }
                    }
                    file += "\n      ";
                }
                file += "\n";
            }

        }
        // RPRIMD ***************************************************************
        if (rprimdCheckBox.isSelected()) {

            int col = rprimTable.getColumnCount();
            int row = rprimTable.getRowCount();
            if (row > 0) {
                file += rprimdCheckBox.getText() + " ";
                for (int i = 0; i
                        < row; i++) {
                    for (int j = 1; j
                            < col; j++) {
                        try {
                            file += rprimTable.getValueAt(i, j) + " ";
                        } catch (Exception e) {
                            MainFrame.printERR("Please set up RPRIMD !");
                        }
                    }
                    file += "\n       ";
                }
                file += "\n";
            }

        }
        // ANGDEG ***************************************************************
        if (angdegCheckBox.isSelected()) {

            int col = rprimTable.getColumnCount();
            int row = rprimTable.getRowCount();
            if (row > 0) {
                file += angdegCheckBox.getText() + " ";
                for (int i = 0; i
                        < row; i++) {
                    for (int j = 1; j
                            < col; j++) {
                        try {
                            file += rprimTable.getValueAt(i, j) + " ";
                        } catch (Exception e) {
                            MainFrame.printERR("Please set up ANGDEG !");
                        }
                    }
                }
                file += "\n\n";
            }
        }
        // NTYPAT ***************************************************************
        if (ntypatCheckBox.isSelected()) {
            try {
                int ntypat;
                ntypat =
                        Integer.parseInt(ntypatTextField.getText());
                file +=
                        ntypatCheckBox.getText() + " " + ntypat + "\n\n";
            } catch (Exception e) {
                MainFrame.printERR("Please set up NTYPAT !");
            }

        }
        // ZNUCL, TYPAT ********************************************************
        if (znuclCheckBox.isSelected()) {

            int col = znuclTable.getColumnCount();
            int row = znuclTable.getRowCount();

            String znucl = "";
            String typat = "";

            if (row > 0) {
                znucl += znuclCheckBox.getText();
                typat += typatCheckBox.getText();
                for (int i = 0; i
                        < row; i++) {
                    try {
                        Atom at = (Atom) znuclTable.getValueAt(i, 0);
                        int nat = (Integer) znuclTable.getValueAt(i, 3);
                        int typat_ = (Integer) znuclTable.getValueAt(i, 2);
                        znucl += " " + at.getZnucl();

                        MainFrame.printDEB(at.getPSPPath() + "/" + at.getPSPFileName());

                        for (int k = 0; k
                                < nat; k++) {
                            //typat += " " + at.getTypat();
                            typat += " " + typat_;
                        }

                    } catch (Exception e) {
                        MainFrame.printERR("Please set up ZNUCL/TYPAT !");
                    }

                }
                file += typat + "\n\n";
                file +=
                        znucl + "\n\n";
            }

        }
        // NSYM ****************************************************************
        if (nsymCheckBox.isSelected()) {
            try {
                int nsym;
                nsym = Integer.parseInt(nsymTextField.getText());
                file += nsymCheckBox.getText() + " " + nsym + "\n\n";
            } catch (Exception e) {
                MainFrame.printERR("Please set up NSYM !");
            }

        }
        // SYMREL, TNONS ********************************************************
        if (symrelCheckBox.isSelected()) {
            int col = symrelTable.getColumnCount();
            int row = symrelTable.getRowCount();
            if (row > 0) {
                int len = symrelCheckBox.getText().length();
                file += symrelCheckBox.getText();
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        try {
                            file += " " + symrelTable.getValueAt(i, j);
                        } catch (Exception e) {
                            MainFrame.printERR("Please set up SYMREL !");
                        }
                    }
                    file += "\n";
                    for (int j = 0; j < len; j++) {
                        file += " ";
                    }
                }
                file += "\n";
            }
        }
        if (tnonsCheckBox.isSelected()) {
            int col = tnonsTable.getColumnCount();
            int row = tnonsTable.getRowCount();
            if (row > 0) {
                int len = tnonsCheckBox.getText().length();
                file += tnonsCheckBox.getText();
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        try {
                            file += " " + tnonsTable.getValueAt(i, j);
                        } catch (Exception e) {
                            MainFrame.printERR("Please set up TNONS !");
                        }
                    }
                    file += "\n";
                    for (int j = 0; j < len; j++) {
                        file += " ";
                    }
                }
                file += "\n";
            }
        }
        return file;
    }

    public JTable getZnuclTable() {
        return znuclTable;
    }

    public String getNtypat() {
        if (ntypatCheckBox.isSelected()) {
            return ntypatTextField.getText();
        } else {
            return "0";
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox acellCheckBox;
    private javax.swing.JTextField acellTextField1;
    private javax.swing.JTextField acellTextField2;
    private javax.swing.JTextField acellTextField3;
    private javax.swing.JLabel acellUnitsLabel;
    private javax.swing.JCheckBox angdegCheckBox;
    private javax.swing.JCheckBox natomCheckBox;
    private javax.swing.JTextField natomTextField;
    private javax.swing.JCheckBox nsymCheckBox;
    private javax.swing.JTextField nsymTextField;
    private javax.swing.JCheckBox ntypatCheckBox;
    private javax.swing.JTextField ntypatTextField;
    private javax.swing.JCheckBox rprimCheckBox;
    private javax.swing.JTable rprimTable;
    private javax.swing.JScrollPane rprimTableScrollPane;
    private javax.swing.JCheckBox rprimdCheckBox;
    private javax.swing.JScrollPane shiftkTableScrollPane1;
    private javax.swing.JScrollPane shiftkTableScrollPane2;
    private javax.swing.JCheckBox symrelCheckBox;
    private javax.swing.JTable symrelTable;
    private javax.swing.JCheckBox tnonsCheckBox;
    private javax.swing.JTable tnonsTable;
    private javax.swing.JCheckBox typatCheckBox;
    private javax.swing.JRadioButton xangstRadioButton;
    private javax.swing.JRadioButton xcartRadioButton;
    private javax.swing.JRadioButton xredRadioButton;
    private javax.swing.JTable xyzTable;
    private javax.swing.JScrollPane xyzTableScrollPane;
    private javax.swing.ButtonGroup xyzbuttonGroup;
    private javax.swing.JCheckBox znuclCheckBox;
    private javax.swing.JTable znuclTable;
    private javax.swing.JScrollPane znuclTableScrollPane;
    // End of variables declaration//GEN-END:variables
}
