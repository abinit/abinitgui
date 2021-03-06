/*
 Copyright (c) 2009-2013 Flavio Miguel ABREU ARAUJO (flavio.abreuaraujo@uclouvain.be)
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

package parser;

import variables.AllInputVars;
import core.MainFrame;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.text.html.HTMLEditorKit;
import variables.Variable;

public class AbinitInputVars extends javax.swing.JFrame {

    private MainFrame mf;
    private AllInputVars database;
    
    /**
     * Creates new form AbinitInputVars
     */
    public AbinitInputVars(MainFrame mf, AllInputVars database) {
        initComponents();
        
        this.mf = mf;
        this.database = database;
        
        descriptPaneEdit.setEditable(false);
        descriptPaneEdit.setEditorKit(new HTMLEditorKit());
        
        namePane.setEditable(false);
        namePane.setEditorKit(new HTMLEditorKit());
        
        sectionPane.setEditable(false);
        sectionPane.setEditorKit(new HTMLEditorKit());
        
        categoryPane.setEditable(false);
        categoryPane.setEditorKit(new HTMLEditorKit());
        
        defaultValPane.setEditable(false);
        defaultValPane.setEditorKit(new HTMLEditorKit());
        
        typePane.setEditable(false);
        typePane.setEditorKit(new HTMLEditorKit());
        
        mnemoPane.setEditable(false);
        mnemoPane.setEditorKit(new HTMLEditorKit());
        
        dimensionsPane.setEditable(false);
        dimensionsPane.setEditorKit(new HTMLEditorKit());
        
        showInputVars();
        
        listVars.setSelectedIndex(0);
        
        setVisible(false);
    }
    
    public void showInputVars()
    {
        String strName[] = database.getListKeys().toArray(new String[0]);
        listVars.setListData(strName);
    }
    
    public String makeLinkVars(String txt)
    {
        if(txt == null)
        {
            return null;
        }
        return txt.replaceAll("\\[\\[([a-zA-Z0-9_]*)\\]\\]", "<a href=\"$1\">$1</a>");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
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
        jScrollPane4 = new javax.swing.JScrollPane();
        descriptPaneEdit = new javax.swing.JEditorPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        namePane = new javax.swing.JEditorPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        defaultValPane = new javax.swing.JEditorPane();
        jScrollPane9 = new javax.swing.JScrollPane();
        dimensionsPane = new javax.swing.JEditorPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        typePane = new javax.swing.JEditorPane();
        jScrollPane6 = new javax.swing.JScrollPane();
        mnemoPane = new javax.swing.JEditorPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        categoryPane = new javax.swing.JEditorPane();
        jScrollPane8 = new javax.swing.JScrollPane();
        sectionPane = new javax.swing.JEditorPane();

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

        jScrollPane4.setViewportView(descriptPaneEdit);

        jScrollPane2.setViewportView(namePane);

        defaultValPane.setMinimumSize(new java.awt.Dimension(100, 48));
        defaultValPane.setPreferredSize(new java.awt.Dimension(100, 48));
        jScrollPane3.setViewportView(defaultValPane);

        dimensionsPane.setMinimumSize(new java.awt.Dimension(100, 48));
        dimensionsPane.setPreferredSize(new java.awt.Dimension(100, 48));
        jScrollPane9.setViewportView(dimensionsPane);

        jScrollPane5.setViewportView(typePane);

        jScrollPane6.setViewportView(mnemoPane);

        jScrollPane7.setViewportView(categoryPane);

        jScrollPane8.setViewportView(sectionPane);

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
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane9)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE))
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
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listVarsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listVarsValueChanged
        
        Object obj = listVars.getSelectedValue();
        if(obj == null)
            return;
        
        String varName = (String)obj;
        
        Variable curVar2 = database.getVar(varName);
        
        namePane.setText("<html><b>"+curVar2.getVarname()+"</b></html>");
        sectionPane.setText("<html>"+curVar2.getSection()+"</html>");
        defaultValPane.setText("<html>"+makeLinkVars(curVar2.getDefaultval()+"")+"</html>");
        mnemoPane.setText("<html>"+makeLinkVars(curVar2.getDefinition())+"</html>");
        typePane.setText("<html>"+makeLinkVars(curVar2.getVartype())+"</html>");
        categoryPane.setText("<html>"+makeLinkVars(curVar2.getCategory())+"</html>");
        descriptPaneEdit.setText("<html>"+makeLinkVars(curVar2.getText())+"</html>");
        Object o = curVar2.getDimensions();
        String s = null;
        if(o != null)
        {
            if(o.getClass().isArray())
            {
                s = Arrays.toString((Object[])o);
            }
        }
        dimensionsPane.setText("<html>"+makeLinkVars(s)+"</html>");
        
    }//GEN-LAST:event_listVarsValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane categoryPane;
    private javax.swing.JEditorPane defaultValPane;
    private javax.swing.JEditorPane descriptPaneEdit;
    private javax.swing.JEditorPane dimensionsPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JList listVars;
    private javax.swing.JEditorPane mnemoPane;
    private javax.swing.JEditorPane namePane;
    private javax.swing.JEditorPane sectionPane;
    private javax.swing.JEditorPane typePane;
    // End of variables declaration//GEN-END:variables

}
