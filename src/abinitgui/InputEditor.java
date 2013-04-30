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

package abinitgui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import json.JSONArray;
import json.JSONObject;
import json.TestJSon;

public class InputEditor extends javax.swing.JFrame {

    private MainFrame mf;
    private JSONObject inputObject;
    private String fileName;
    private ArrayList<HashMap<String,Object>> dataTable;
    private DatasetModel model;
    
    /**
     * Creates new form InputEditor
     */
    public InputEditor(MainFrame mf) {
        initComponents();
        this.mf = mf;
        dataTable = new ArrayList<HashMap<String,Object>>();
        
        model = new DatasetModel();
        
        this.jTable1.setModel(model);
        
        jTable1.setDefaultEditor(JSONArray.class,new JSONArrayEditor(mf));
        
        Vector<String> vector = new Vector<String>(mf.getAllInputVars().getListKeys());
        listAllVars.setModel(new DefaultComboBoxModel(vector));
    }

    public void loadFile(String fileName)
    {
        this.fileName = fileName;
        
        mf.printOUT("Loading JSON File "+fileName);
        
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String s = br.readLine();
            inputObject = new JSONObject(s);
            
        refreshDatabase();
            
        } catch (IOException ex) {
            Logger.getLogger(TestJSon.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(TestJSon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void refreshDatabase()
    {
        Iterator<String> iter = inputObject.keys();
        
        dataTable.clear();
        TreeSet<String> listKeys = new TreeSet<String>();
        
        while(iter.hasNext())
        {
            String o = iter.next();
            listKeys.add(o);
        }
        
        iter = listKeys.iterator();

        while(iter.hasNext())
        {
            String name = iter.next();

            HashMap<String,Object> curMap = new HashMap<String,Object>();

            curMap.put("name", name);
            curMap.put("value", inputObject.get(name));

            dataTable.add(curMap);
        }

        model.fireTableDataChanged();
        
        setVisible(true);
    }

    
    
    public class DatasetTable extends JTable
    {
        @Override
        public TableCellEditor getCellEditor(int row, int column)
        {
            return getDefaultEditor(getModel().getValueAt(row, column).getClass());
        }
        
    }
    public class DatasetModel extends DefaultTableModel
    {
        @Override
        public int getColumnCount()
        {
            return 2;
        }
        
        @Override
        public String getColumnName(int col)
        {
            if(col == 0)
            {
                return "name";
            }
            else if(col == 1)
            {
                return "value";
            }
            return null;
        }
        
        @Override
        public Object getValueAt(int row, int column)
        {
            if(column == 0)
            {
                return dataTable.get(row).get("name");
            }
            else if(column == 1)
            {
                return dataTable.get(row).get("value");
            }
            
            return null;
        }
        
        @Override
        public int getRowCount()
        {
            return dataTable.size();
        }
        
        @Override
        public boolean isCellEditable(int row, int column)
        {
            return (column == 1); // Value of the variable
        }
        
        @Override
        public void setValueAt(Object o, int row, int column)
        {
            String name = (String)getValueAt(row,0);
            System.out.println("Object o = "+o);
            inputObject.put(name, o);

            refreshDatabase();
            model.fireTableDataChanged();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = jTable1 = new DatasetTable();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        listAllVars = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Add another input variable :");

        listAllVars.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(173, 173, 173)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(listAllVars, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap(125, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(343, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(listAllVars, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(50, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int index = listAllVars.getSelectedIndex();
        
        if(index == -1)
        {
            mf.printERR("Please first select an input variable name");
            return;
        }
        
        String name = (String)listAllVars.getSelectedItem();
        
        Object o = mf.getAllInputVars().get(name).defaultVal;
        
        inputObject.put(name, o);
        refreshDatabase();
        model.fireTableDataChanged();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox listAllVars;
    // End of variables declaration//GEN-END:variables
}
