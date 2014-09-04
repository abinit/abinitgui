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

package abinitgui.variables;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.rtf.RTFEditorKit;

public class AbinitInputVars extends JFrame {
    
    private AllInputVars database;
    
    private JTextArea descriptPaneHTML;
    private JTextArea nameHTML;
    private JTextArea sectionHTML;
    private JTextArea categoryHTML;
    private JTextArea defaultValHTML;
    private JTextArea typeHTML;
    private JTextArea mnemoHTML;
    private JTextArea dimensionsHTML;
    
    /**
     * Creates new form AbinitInputVars
     */
    public AbinitInputVars(AllInputVars database) {
        initComponents();
        
        //jMenuItem2.setVisible(false);

        this.database = database;
        
        descriptPaneEdit.setEditable(false);
        descriptPaneEdit.setEditorKit(new HTMLEditorKit());
        descriptPaneEdit.addHyperlinkListener(new HyperlinkListen());
        
        descriptPaneHTML = new JTextArea();
        descriptPaneHTML.setLineWrap(true);
        descriptPaneHTML.setWrapStyleWord(true);
        
        namePane.setEditable(false);
        namePane.setEditorKit(new HTMLEditorKit());
        namePane.addHyperlinkListener(new HyperlinkListen());
        
        nameHTML = new JTextArea();
        nameHTML.setLineWrap(true);
        nameHTML.setWrapStyleWord(true);
        
        sectionPane.setEditable(false);
        sectionPane.setEditorKit(new HTMLEditorKit());
        sectionPane.addHyperlinkListener(new HyperlinkListen());
        
        sectionHTML = new JTextArea();
        sectionHTML.setLineWrap(true);
        sectionHTML.setWrapStyleWord(true);
        
        categoryPane.setEditable(false);
        categoryPane.setEditorKit(new HTMLEditorKit());
        categoryPane.addHyperlinkListener(new HyperlinkListen());
        
        categoryHTML = new JTextArea();
        categoryHTML.setLineWrap(true);
        categoryHTML.setWrapStyleWord(true);
        
        defaultValPane.setEditable(false);
        defaultValPane.setEditorKit(new HTMLEditorKit());
        defaultValPane.addHyperlinkListener(new HyperlinkListen());
        
        defaultValHTML = new JTextArea();
        defaultValHTML.setLineWrap(true);
        defaultValHTML.setWrapStyleWord(true);
        
        typePane.setEditable(false);
        typePane.setEditorKit(new HTMLEditorKit());
        typePane.addHyperlinkListener(new HyperlinkListen());
        
        typeHTML = new JTextArea();
        typeHTML.setLineWrap(true);
        typeHTML.setWrapStyleWord(true);
        
        mnemoPane.setEditable(false);
        mnemoPane.setEditorKit(new HTMLEditorKit());
        mnemoPane.addHyperlinkListener(new HyperlinkListen());
        
        mnemoHTML = new JTextArea();
        mnemoHTML.setLineWrap(true);
        mnemoHTML.setWrapStyleWord(true);
        
        dimensionsPane.setEditable(false);
        dimensionsPane.setEditorKit(new HTMLEditorKit());
        dimensionsPane.addHyperlinkListener(new HyperlinkListen());
        
        dimensionsHTML = new JTextArea();
        dimensionsHTML.setLineWrap(true);
        dimensionsHTML.setWrapStyleWord(true);
        
        showInputVars();
        
        listVars.setSelectedIndex(0);
        
        boolean showButton = true;
        
        jButton1.setVisible(showButton);
        jButton2.setVisible(showButton);
        jButton3.setVisible(showButton);
        jButton4.setVisible(showButton);
        jButton5.setVisible(showButton);
        jButton6.setVisible(showButton);
        jButton7.setVisible(showButton);
        jButton8.setVisible(showButton);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        setVisible(false);
    }
    
    public final void showInputVars()
    {
        String strName[] = database.getListKeys().toArray(new String[0]);
        listVars.setListData(strName);
    }
    
    public String makeLinkVars(String txt, String parent)
    {
        if(txt == null)
        {
            return null;
        }
        txt = txt.replaceAll("\\*\\*([a-zA-Z0-9_*/<>]*)\\*\\*","<b>$1</b>");
        txt = txt.replaceAll("\\/\\/([a-zA-Z0-9_*/<>]*)\\/\\/","<i>$1</i>");
        txt = txt.replaceAll("\\_\\_([a-zA-Z0-9_*/<>]*)\\_\\_","<u>$1</u>");
        return txt.replaceAll("\\[\\[([a-zA-Z0-9_*/<>]*)\\]\\]", "<a href=\"$1\">$1</a>").replaceAll("<<parent>>", parent);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listVars = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        descriptEditorScroll = new javax.swing.JScrollPane();
        descriptPaneEdit = new javax.swing.JEditorPane();
        nameEditorScroll = new javax.swing.JScrollPane();
        namePane = new javax.swing.JEditorPane();
        defaultValEditorScroll = new javax.swing.JScrollPane();
        defaultValPane = new javax.swing.JEditorPane();
        dimensionsEditorScroll = new javax.swing.JScrollPane();
        dimensionsPane = new javax.swing.JEditorPane();
        typeEditorScroll = new javax.swing.JScrollPane();
        typePane = new javax.swing.JEditorPane();
        mnemoEditorScroll = new javax.swing.JScrollPane();
        mnemoPane = new javax.swing.JEditorPane();
        categoryEditorScroll = new javax.swing.JScrollPane();
        categoryPane = new javax.swing.JEditorPane();
        sectionEditorScroll = new javax.swing.JScrollPane();
        sectionPane = new javax.swing.JEditorPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setTitle("Input variables");
        setMinimumSize(new java.awt.Dimension(669, 489));

        listVars.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listVars.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listVarsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(listVars);

        jLabel1.setText("Section : ");

        jLabel2.setText("Name : ");

        jLabel3.setText("Default value :");

        jLabel4.setText("Type :");

        jLabel5.setText("Mnemonics :");

        jLabel6.setText("Category :");

        jLabel7.setText("Description :");

        jLabel8.setText("Dimensions :");

        descriptEditorScroll.setViewportView(descriptPaneEdit);

        nameEditorScroll.setViewportView(namePane);

        defaultValPane.setMinimumSize(new java.awt.Dimension(100, 48));
        defaultValPane.setPreferredSize(new java.awt.Dimension(100, 48));
        defaultValEditorScroll.setViewportView(defaultValPane);

        dimensionsPane.setMinimumSize(new java.awt.Dimension(100, 48));
        dimensionsPane.setPreferredSize(new java.awt.Dimension(100, 48));
        dimensionsEditorScroll.setViewportView(dimensionsPane);

        typeEditorScroll.setViewportView(typePane);

        mnemoEditorScroll.setViewportView(mnemoPane);

        categoryEditorScroll.setViewportView(categoryPane);

        sectionEditorScroll.setViewportView(sectionPane);

        jButton1.setText("Edit");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Edit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Edit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Edit");
        jButton4.setEnabled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Edit");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Edit");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Edit");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Edit");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jMenu3.setText("File");

        jMenuItem1.setText("Open file");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuItem2.setText("Save file");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        jMenuBar2.add(jMenu3);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameEditorScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                            .addComponent(sectionEditorScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(typeEditorScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(mnemoEditorScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(dimensionsEditorScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(defaultValEditorScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(categoryEditorScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addComponent(descriptEditorScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton7)
                    .addComponent(jButton8)
                    .addComponent(jButton6)
                    .addComponent(jButton5)
                    .addComponent(jButton4)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameEditorScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sectionEditorScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(defaultValEditorScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dimensionsEditorScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(typeEditorScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mnemoEditorScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton7)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(categoryEditorScroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton8)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(descriptEditorScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listVarsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listVarsValueChanged
        boolean adjust = evt.getValueIsAdjusting();   
        if(!adjust)
        {
            Object obj = listVars.getSelectedValue();
            if(obj == null)
                return;

            String varName = (String)obj;

            Variable curVar2 = database.getVar(varName);
            
            
            /*setEditablePane(nameEditorScroll, namePane, nameHTML, jButton1, false, varName, varName);*/
            setEditablePane(sectionEditorScroll, sectionPane, sectionHTML, jButton2, false, curVar2.getSection(), varName);
            
            Object o = curVar2.getDefaultval();
            String s = null;
            if(o != null)
            {
                s = o.toString();
            }
            
            setEditablePane(defaultValEditorScroll, defaultValPane, defaultValHTML, jButton3, false, s, varName);
            setEditablePane(dimensionsEditorScroll, dimensionsPane, dimensionsHTML, jButton4, false, fromDimToString(curVar2.getDimensions()), varName);
            setEditablePane(typeEditorScroll, typePane, typeHTML, jButton5, false, curVar2.getVartype(), varName);
            setEditablePane(mnemoEditorScroll, mnemoPane, mnemoHTML, jButton6, false, curVar2.getDefinition(), varName);
            setEditablePane(categoryEditorScroll, categoryPane, categoryHTML, jButton7, false, curVar2.getCategory(), varName);
            setEditablePane(descriptEditorScroll, descriptPaneEdit, descriptPaneHTML, jButton8, false, curVar2.getText(), varName);
        

            namePane.setText("<html><b>"+curVar2.getVarname()+"</b></html>"); /*
            sectionPane.setText("<html>"+curVar2.getSection()+"</html>");
            defaultValPane.setText("<html>"+makeLinkVars(curVar2.getDefaultval()+"",curVar2.getVarname())+"</html>");
            mnemoPane.setText("<html>"+makeLinkVars(curVar2.getDefinition(),curVar2.getVarname())+"</html>");
            typePane.setText("<html>"+makeLinkVars(curVar2.getVartype(),curVar2.getVarname())+"</html>");
            categoryPane.setText("<html>"+makeLinkVars(curVar2.getCategory(),curVar2.getVarname())+"</html>");
            descriptPaneEdit.setText("<html>"+makeLinkVars(curVar2.getText(),curVar2.getVarname())+"</html>");
            dimensionsPane.setText("<html>"+makeLinkVars(fromDimToString(curVar2.getDimensions()),curVar2.getVarname())+"</html>");*/
        }
    }//GEN-LAST:event_listVarsValueChanged

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //This is where a real application would open the file.
            System.out.println("Opening: " + file.getName() + ".");
            
            this.database.loadVars(file.getAbsolutePath());
            this.showInputVars();
        } else {
            System.out.println("Open command cancelled by user.");
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showSaveDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //This is where a real application would open the file.
            System.out.println("Saving into: " + file.getName() + ".");
            this.database.saveVars(file);
        } else {
            System.out.println("Save command cancelled by user.");
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void setEditablePane(JScrollPane scrollPane, JEditorPane editor, JTextArea area, JButton button, boolean editable, String text, String varName)
    {
        if(editable)
        {
            scrollPane.setViewportView(area);
            area.setText(text);
            button.setText("Save");
        }
        else
        {
            scrollPane.setViewportView(editor);
            editor.setText("<html>"+makeLinkVars(text,varName)+"</html>");
            button.setText("Edit");
        }
    }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Object obj = listVars.getSelectedValue();
        if(obj == null)
            return;

        String varName = (String)obj;

        Variable curVar2 = database.getVar(varName);
        
        if(jButton1.getText().equals("Edit"))
        {
            setEditablePane(nameEditorScroll, namePane, nameHTML, jButton1, true, curVar2.getVarname(), varName);
        }
        else if(jButton1.getText().equals("Save"))
        {
            curVar2.setVarname(nameHTML.getText());
            setEditablePane(nameEditorScroll, namePane, nameHTML, jButton1, false, curVar2.getVarname(), curVar2.getVarname());
        }
        
        this.repaint();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Object obj = listVars.getSelectedValue();
        if(obj == null)
            return;

        String varName = (String)obj;

        Variable curVar2 = database.getVar(varName);
        
        if(jButton2.getText().equals("Edit"))
        {
            setEditablePane(sectionEditorScroll, sectionPane, sectionHTML, jButton2, true, curVar2.getSection(), varName);
        }
        else if(jButton2.getText().equals("Save"))
        {
            curVar2.setSection(sectionHTML.getText());
            setEditablePane(sectionEditorScroll, sectionPane, sectionHTML, jButton2, false, curVar2.getSection(), varName);
        }
        
        this.repaint();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Object obj = listVars.getSelectedValue();
        if(obj == null)
            return;

        String varName = (String)obj;

        Variable curVar2 = database.getVar(varName);
        
        if(jButton3.getText().equals("Edit"))
        {
            Object o = curVar2.getDefaultval();
            String s = "";
            if(o != null)
            {
                s = o.toString();
            }
            setEditablePane(defaultValEditorScroll, defaultValPane, defaultValHTML, jButton3, true, s, varName);
        }
        else if(jButton3.getText().equals("Save"))
        {
            curVar2.setDefaultval(defaultValHTML.getText());
        
            Object o = curVar2.getDefaultval();
            String s = "";
            if(o != null)
            {
                s = o.toString();
            }
            setEditablePane(defaultValEditorScroll, defaultValPane, defaultValHTML, jButton3, false, s, varName);
        }
        
        this.repaint();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Object obj = listVars.getSelectedValue();
        if(obj == null)
            return;

        String varName = (String)obj;

        Variable curVar2 = database.getVar(varName);
        
        if(jButton4.getText().equals("Edit"))
        {
            setEditablePane(dimensionsEditorScroll, dimensionsPane, dimensionsHTML, jButton4, true, fromDimToString(curVar2.getDimensions()), varName);
        }
        else if(jButton4.getText().equals("Save"))
        {
            setEditablePane(dimensionsEditorScroll, dimensionsPane, dimensionsHTML, jButton4, false, fromDimToString(curVar2.getDimensions()), varName);
        }
        
        this.repaint();
    }//GEN-LAST:event_jButton4ActionPerformed

    private String fromDimToString(Object o)
    {
        String s = null;
        if(o != null)
        {
            if(o.getClass().isArray())
            {
                s = Arrays.toString((Object[])o);
            }
        }
        return s;
    }
    
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Object obj = listVars.getSelectedValue();
        if(obj == null)
            return;

        String varName = (String)obj;

        Variable curVar2 = database.getVar(varName);
        
        if(jButton5.getText().equals("Edit"))
        {
            setEditablePane(typeEditorScroll, typePane, typeHTML, jButton5, true, curVar2.getVartype(), varName);
        }
        else if(jButton5.getText().equals("Save"))
        {
            curVar2.setVartype(typeHTML.getText());
            setEditablePane(typeEditorScroll, typePane, typeHTML, jButton5, false, curVar2.getVartype(), varName);
        }
        
        this.repaint();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        Object obj = listVars.getSelectedValue();
        if(obj == null)
            return;

        String varName = (String)obj;

        Variable curVar2 = database.getVar(varName);
        
        if(jButton6.getText().equals("Edit"))
        {
            setEditablePane(mnemoEditorScroll, mnemoPane, mnemoHTML, jButton6, true, curVar2.getDefinition(), varName);
        }
        else if(jButton6.getText().equals("Save"))
        {
            curVar2.setDefinition(mnemoHTML.getText());
            setEditablePane(mnemoEditorScroll, mnemoPane, mnemoHTML, jButton6, false, curVar2.getDefinition(), varName);
        }
        
        this.repaint();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        Object obj = listVars.getSelectedValue();
        if(obj == null)
            return;

        String varName = (String)obj;

        Variable curVar2 = database.getVar(varName);
        
        if(jButton7.getText().equals("Edit"))
        {
            setEditablePane(categoryEditorScroll, categoryPane, categoryHTML, jButton7, true, curVar2.getCategory(), varName);
        }
        else if(jButton7.getText().equals("Save"))
        {
            curVar2.setCategory(categoryHTML.getText());
            setEditablePane(categoryEditorScroll, categoryPane, categoryHTML, jButton7, false, curVar2.getCategory(), varName);
        }
        
        this.repaint();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        Object obj = listVars.getSelectedValue();
        if(obj == null)
            return;

        String varName = (String)obj;

        Variable curVar2 = database.getVar(varName);
        
        if(jButton8.getText().equals("Edit"))
        {
            setEditablePane(descriptEditorScroll, descriptPaneEdit, descriptPaneHTML, jButton8, true, curVar2.getText(), varName);
        }
        else if(jButton8.getText().equals("Save"))
        {
            curVar2.setText(descriptPaneHTML.getText());
            setEditablePane(descriptEditorScroll, descriptPaneEdit, descriptPaneHTML, jButton8, false, curVar2.getText(), varName);
        }
        
        this.repaint();
    }//GEN-LAST:event_jButton8ActionPerformed


   
    public class HyperlinkListen implements HyperlinkListener
    {

        @Override
        public void hyperlinkUpdate(HyperlinkEvent e) {
            
            if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
            {
                String a = e.getDescription();
                Variable var = database.getVar(a);
                if(var != null)
                {
                    listVars.setSelectedValue(var.getVarname(), true);
                }
            }
            
        }
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane categoryEditorScroll;
    private javax.swing.JEditorPane categoryPane;
    private javax.swing.JScrollPane defaultValEditorScroll;
    private javax.swing.JEditorPane defaultValPane;
    private javax.swing.JScrollPane descriptEditorScroll;
    private javax.swing.JEditorPane descriptPaneEdit;
    private javax.swing.JScrollPane dimensionsEditorScroll;
    private javax.swing.JEditorPane dimensionsPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList listVars;
    private javax.swing.JScrollPane mnemoEditorScroll;
    private javax.swing.JEditorPane mnemoPane;
    private javax.swing.JScrollPane nameEditorScroll;
    private javax.swing.JEditorPane namePane;
    private javax.swing.JScrollPane sectionEditorScroll;
    private javax.swing.JEditorPane sectionPane;
    private javax.swing.JScrollPane typeEditorScroll;
    private javax.swing.JEditorPane typePane;
    // End of variables declaration//GEN-END:variables

}
