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

package abinitgui.pseudos;

import abinitgui.pseudos.Atom;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class znuclAtomRenderer extends JLabel
        implements TableCellRenderer {

    public znuclAtomRenderer() {
        setBackground(Color.LIGHT_GRAY);
        setHorizontalAlignment(JLabel.CENTER);
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object atom,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        Atom newAtom = (Atom) atom;
        setBackground(Color.LIGHT_GRAY);
        setText(newAtom.getSymbol());
        if (newAtom.getSymbol() != null) {
            table.setValueAt(new Integer(newAtom.getZnucl()), row, 1);
            newAtom.setTypat((Integer) table.getValueAt(row, 2));
        }
        return this;
    }
}
