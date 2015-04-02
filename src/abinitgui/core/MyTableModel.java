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

package abinitgui.core;

import java.util.Enumeration;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class MyTableModel extends AbstractTableModel {

    private String[] columnNames = null;
    private Object[][] data = null;
    private JTable table = null;
    private String notEditableCol;
    boolean DEBUG = false;

    public MyTableModel(JTable table) {
        super();
        this.table = table;
        notEditableCol = "";
    }

    @Override
    public int getColumnCount() {
        if (data == null) {
            return 0;
        } else {
            return data[0].length;
        }
    }

    @Override
    public int getRowCount() {
        if (data == null) {
            return 0;
        } else {
            return data.length;
        }
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {

        return data[row][col];
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        if (notEditableCol.contains(Integer.toString(col))) {
            return false;
        } else {
            return true;
        }
    }

    public void setNotEditableCol(String notEditableCol) {
        this.notEditableCol = notEditableCol;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        if (DEBUG) {
            if (value == null) {
                MainFrame.printDEB("Setting value at " + row + "," + col +
                        " to " + 0.0 + " (an instance of Double)");
            } else {
                MainFrame.printDEB("Setting value at " + row + "," + col +
                        " to " + value + " (an instance of " + value.getClass() + ")");
            }
        }
        if (value == null) {
            data[row][col] = 0.0;
        } else {
            data[row][col] = value;
        }
        fireTableCellUpdated(row, col);

        if (DEBUG) {
            MainFrame.printDEB("New value of data:");
            printDebugData();
        }
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

    public void setData(Object[][] data) {
        if (data == null) {
            this.data = null;
            this.table.updateUI();
        } else {
            this.data = data;
            this.table.updateUI();
        }
    }

    private void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        String out = "";
        for (int i = 0; i < numRows; i++) {
            out += "    row " + i + ":";
            for (int j = 0; j < numCols; j++) {
                out += "  " + data[i][j].toString();
            }
            out += "\n";
        }
        MainFrame.printDEB(out);
        MainFrame.printDEB("--------------------------");
    }
}
