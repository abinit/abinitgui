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

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import json.JSONArray;
   
public class JSONArrayEditor extends AbstractCellEditor
                     implements TableCellEditor, ActionListener
{
    private JButton but;
    private JSONArrayDialog dialog;
    private MainFrame mf;
    private Object data;

    public JSONArrayEditor(MainFrame frame)
    {
        but = new JButton();
        but.setActionCommand("cmd");
        but.addActionListener(this);
        but.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        but.setBackground(Color.darkGray);
        dialog = new JSONArrayDialog(frame, this);
        mf = frame;
    }

    public void setData(Object data)
    {
        this.data = data;
        // Pour que le rederer reapparaisse
        fireEditingStopped();
    }

    public Object getData()
    {
        return data;
    }

    @Override
    public Object getCellEditorValue() {
        
        System.out.println("GetCellEditorValue : "+data);
        return data;
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column
            ) 
    {
        System.out.println("GetTableCellEditorComponent : "+but);
        this.data = (JSONArray)value;
        dialog.setArray((JSONArray)value);
        return but;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String ActionCommand = e.getActionCommand();

        // Montre la fenetre quand l'utilisateur clique dans la cellule
        dialog.setLocationRelativeTo(mf);
        dialog.setVisible(true);
    }
}