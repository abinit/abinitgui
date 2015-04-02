/*
 AbinitGUI - Created in 2009
 
 Copyright (c) 2009-2015 Flavio Miguel ABREU ARAUJO (abreuaraujo.flavio@gmail.com)
                         Yannick GILLET (yannick.gillet@hotmail.com)

 Université catholique de Louvain, Louvain-la-Neuve, Belgium
 All rights reserved.

 AbinitGUI is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 AbinitGUI is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with AbinitGUI.  If not, see <http://www.gnu.org/licenses/>.

 For more information on the project, please see
 <http://gui.abinit.org/>.
 */

package abinitgui.scriptbib;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class ScriptArgTable extends JTable
{
    private String curPath = ".";
    
    @Override
    public TableCellEditor getCellEditor(int row, int column)
    {
        ScriptArgument arg = (ScriptArgument)(getModel().getValueAt(row,0));
        
        if(column == 2)
        {
            if(arg.type.toUpperCase().equals("FILE"))
            {
                return new FileEditor();
            }
            else if(arg.type.toUpperCase().equals("REMOTEFILE"))
            {
                // DO nothing for the moment
            }
            else if(arg.type.toUpperCase().equals("BOOLEAN"))
            {
                return new BooleanEditor();
            }
        }
        
        return getDefaultEditor(getModel().getValueAt(row, column).getClass());
    }
    
    private class BooleanEditor extends AbstractCellEditor
        implements TableCellEditor,
        ActionListener
    {
        private JButton but;
        private String status;
        private boolean stat;
        
        public BooleanEditor()
        {
            but = new JButton();
            but.addActionListener(this);
            but.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            but.setBackground(Color.darkGray);
        }
        
        @Override
        public Object getCellEditorValue()
        {
            return status;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            
            status = (String)value;
            stat = (status.equals("True"));
            return but;
        }

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            stat = !stat;
            
            if(stat)
            {
                status = "True";
            }
            else
            {
                status = "False";
            }
            
            fireEditingStopped();
        }
    }
    
    private class FileEditor extends AbstractCellEditor
        implements TableCellEditor,
        ActionListener
    {
        private JButton but;
        private String fileName;
                
        public FileEditor()
        {
            but = new JButton();
            but.addActionListener(this);
            but.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            but.setBackground(Color.darkGray);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table,
                Object value,
                boolean isSelected,
                int row,
                int column) {
            fileName = (String)value;
            //button.setText(currentAtom.getSymbol());
            return but;
        }

        @Override
        public Object getCellEditorValue() {
            return fileName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            
            JFileChooser fc = new JFileChooser(curPath);
            File currDir = new File(".");
            String currPath = currDir.getAbsolutePath();
            String basePath = currPath.replace("\\", "/").replace(".", "");
            
            int returnVal = fc.showOpenDialog(ScriptArgTable.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                curPath = file.getParent();
                //This is where a real application would open the file.
                System.out.println("Opening: " + file.getName() + ".");
                String relPath = file.getAbsolutePath().replace("\\", "/").replace(basePath, "./");
                this.fileName = relPath;
            } else {
                System.out.println("Open command cancelled by user.");
            }
            fireEditingStopped();
        }
    }
}
