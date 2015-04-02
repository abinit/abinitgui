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

import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class ScriptArgTableModel extends AbstractTableModel {

    private String[] columnNames = null;
    private ArrayList<ScriptArgument> data;
    private JTable table = null;

    public ScriptArgTableModel(JTable table) {
        super();
        this.table = table;
        data = new ArrayList<>();
    }

    @Override
    public int getColumnCount() {
        if (getRowCount() == 0) {
            return 0;
        } else {
            return 2;
        }
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        switch(col)
        {
            case 0:
                return data.get(row);
            case 1:
                return data.get(row).value;
            default:
                return null;
        }
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col == 1;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        switch(col)
        {
            case 0:
                return; // Not possible to modify name
            case 1:
                data.get(row).value = (String)value;
        }
        fireTableCellUpdated(row, col);
    }

    public void setHeader(String[] header) {
        if (header == null) {
            columnNames = null;
        } else {
            columnNames = header;
            TableColumnModel tcm = table.getTableHeader().getColumnModel();
            Enumeration<TableColumn> cols = tcm.getColumns();
            for (int i = 0; cols.hasMoreElements(); i++) {
                TableColumn tc = cols.nextElement();
                tc.setHeaderValue(columnNames[i]);
            }
        }
    }

    public void addScript(ScriptArgument data) {
        this.data.add(data);
        if (table.isEditing())
        {
            table.getCellEditor().cancelCellEditing();
        }   
        this.table.updateUI();
    }
    
    public void resetScripts()
    {
        this.data.clear();
        if (table.isEditing())
        {
            table.getCellEditor().cancelCellEditing();
        }   
        this.table.updateUI();
    }
}
