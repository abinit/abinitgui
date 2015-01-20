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

package abinitgui.pseudos;

import abinitgui.core.MainFrame;
import abinitgui.core.Utils;
import abinitgui.inputgen.Geometry;
import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

//@SuppressWarnings("serial")
public class AtomEditorOnline extends AbstractCellEditor
        implements TableCellEditor,
        ActionListener {

    Atom currentAtom;
    JButton but;
    MendTabDialogOnline dialog;
    MainFrame mainFrame;
    Geometry geomFrame;
    protected static final String CMD = "setAtom";

    public AtomEditorOnline(JFrame frame) {
        but = new JButton();
        but.setActionCommand(CMD);
        but.addActionListener(this);
        but.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        but.setBackground(Color.darkGray);
        dialog = new MendTabDialogOnline(frame, true, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String ActionCommand = e.getActionCommand();

        //mainFrame.printERR(ActionCommand);

        if (CMD.equals(ActionCommand)) {

            String pathToPSP = MainFrame.getCurrentProject().getPSPPath();
            String pspType = dialog.getPSPSelected();

            Object[][] atomsDB = Atom.getAtomsBD();
            int atomsDBlength = atomsDB.length;

            for (int i = 0; i < atomsDBlength; i++) {
                setAtomButton((String) atomsDB[i][0], pspType, pathToPSP);
            }

            // Montre la fenêtre quand l'utilisateur clique dans la cellule
            dialog.setLocationRelativeTo(mainFrame);
            dialog.setVisible(true);
            // Pour que le rederer réapparaisse
            fireEditingStopped();

        } else {
            switch (ActionCommand) {
                case "OK":
                    break;
                case "cancel":
                    break;
                case "NC_GGA_FHI":
                case "NC_GGA_HGH":
                case "NC_LDA_Core":
                case "NC_LDA_FHI":
                case "NC_LDA_GTH":
                case "NC_LDA_HGH":
                case "NC_LDA_TM":
                case "NC_LDA_Teter":
                case "PAW_LDA_PW":
                case "PAW_GGA_PBE":
                    {
                        String pathToPSP = MainFrame.getCurrentProject().getPSPPath();
                        Object[][] atomsDB = Atom.getAtomsBD();
                        int atomsDBlength = atomsDB.length;
                        for (int i = 0; i < atomsDBlength; i++) {
                            setAtomButton((String) atomsDB[i][0], ActionCommand, pathToPSP);
                        }
                        break;
                    }
                case "UserPSP":
                    dialog.setVisible(false);
                    // User defined pseudopotential: open FileChooser
                    JFileChooser fc = new JFileChooser(".");
                    File currDir = new File(".");
                    String currPath = currDir.getAbsolutePath();
                    String basePath = currPath.replace("\\", "/").replace(".", "");
                    MainFrame.printDEB(basePath);
                    fc.setMultiSelectionEnabled(false);
                    int retValue = fc.showOpenDialog(mainFrame);
                    if (retValue == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        String relPath = file.getAbsolutePath().replace("\\", "/").replace(basePath, "./");
                        MainFrame.printDEB(relPath);

                        int znucl = pspReader(relPath);

                        if (znucl > 0) {
                            currentAtom.setByZNucl(znucl);

                            currentAtom.setPSPType("UserPSP");

                            String relPathtmp = relPath.replace('\\', '/');

                            String pspfile = Utils.getLastToken(relPathtmp, "/");

                            String psppath = relPathtmp.replaceAll("/" + pspfile, "");

                            currentAtom.setPSPPath(psppath);

                            currentAtom.setPSPFileName(pspfile);
                        } else {
                            MainFrame.printERR("Problem in reading the pseudopotential file!");
                        }
                    } else {
                        MainFrame.printERR("You canceled the pseudo-potential file chooser!");
                    }
                    break;
                default:
                    {
                        System.out.println("Action: " + ActionCommand);
                        /*dialog.setVisible(false);
                        String pspType = dialog.getPSPSelected();
                        String pathToPSP = MainFrame.getCurrentProject().getPSPPath();
                        if (!pathToPSP.equals("")) {
                            setAtomParse(currentAtom, ActionCommand, pspType, pathToPSP);
                        } else {
                            MainFrame.printERR("Please setup the path to the pseudopotentials at the config. tab !");
                            currentAtom.setPSPPath("");
                            currentAtom.setPSPFileName("");
                            currentAtom.setName("");
                            currentAtom.setSymbol("");
                            currentAtom.setPSPType("");
                            currentAtom.setTypat(0);
                            currentAtom.setZnucl(0);
                        }
                        break;*/
                    }
            }
        }
    }

    public static void setAtom(Atom atom, String symbol, String pspType, String pathToPSP, String pspfile) {
        atom.setBySymbol(symbol);

        atom.setPSPType(pspType);

        atom.setPSPPath(pathToPSP);

        atom.setPSPFileName(pspfile);

        String file = pathToPSP + "/" + pspfile;
        if (Utils.exists(file)) {
            MainFrame.printDEB("The file " + file + " exists!");
        } else {
            MainFrame.printERR("The file " + file + " doesn't exist!");
        }
    }

    private void setAtomParse(Atom atom, String symbol, String pspType, String pathToPSP) {
        atom.setBySymbol(symbol);

        atom.setPSPType(pspType);
        switch (pspType) {
            case "GGA_FHI":
                {
                    atom.setPSPPath(pathToPSP + "/" + pspType);
                    int znucl = atom.getZnucl();
                    String fileName = "";
                    if (znucl < 10) {
                        fileName += "0";
                    }
                    fileName += znucl + "-" + symbol + ".GGA.fhi";
                    atom.setPSPFileName(fileName);
                    String file = pathToPSP + "/" + pspType + "/" + fileName;
                    if (Utils.exists(file)) {
                        MainFrame.printDEB("The file " + file + " exists!");
                    } else {
                        MainFrame.printERR("The file " + file + " doesn't exist!");
                    }
                    break;
                }
            case "GGA_HGH":
                {
                    atom.setPSPPath(pathToPSP + "/" + pspType);
                    int znucl = atom.getZnucl();
                    String fileName = "";
                    if (znucl < 10) {
                        fileName += "0";
                    }
                    fileName += znucl + symbol.toLowerCase() + ".pbe_hgh";
                    atom.setPSPFileName(fileName);
                    String file = pathToPSP + "/" + pspType + "/" + fileName;
                    if (Utils.exists(file)) {
                        MainFrame.printDEB("The file " + file + " exists!");
                    } else {
                        MainFrame.printERR("The file " + file + " doesn't exist!");
                    }
                    break;
                }
            case "LDA_Core":
                {
                    atom.setPSPPath(pathToPSP + "/" + pspType);
                    int znucl = atom.getZnucl();
                    String fileName = znucl + symbol.toLowerCase() + ".1s_psp.mod";
                    atom.setPSPFileName(fileName);
                    String file = pathToPSP + "/" + pspType + "/" + fileName;
                    if (Utils.exists(file)) {
                        MainFrame.printDEB("The file " + file + " exists!");
                    } else {
                        MainFrame.printERR("The file " + file + " doesn't exist!");
                    }
                    break;
                }
            case "LDA_FHI":
                {
                    atom.setPSPPath(pathToPSP + "/" + pspType);
                    int znucl = atom.getZnucl();
                    String fileName = "";
                    if (znucl < 10) {
                        fileName += "0";
                    }
                    fileName += znucl + "-" + symbol + ".LDA.fhi";
                    atom.setPSPFileName(fileName);
                    String file = pathToPSP + "/" + pspType + "/" + fileName;
                    if (Utils.exists(file)) {
                        MainFrame.printDEB("The file " + file + " exists!");
                    } else {
                        MainFrame.printERR("The file " + file + " doesn't exist!");
                    }
                    break;
                }
            case "LDA_GTH":
                {
                    atom.setPSPPath(pathToPSP + "/" + pspType);
                    int znucl = atom.getZnucl();
                    String fileName = "";
                    if (znucl < 10) {
                        fileName += "0";
                    }
                    fileName += znucl + symbol.toLowerCase() + ".pspgth";
                    atom.setPSPFileName(fileName);
                    String file = pathToPSP + "/" + pspType + "/" + fileName;
                    if (Utils.exists(file)) {
                        MainFrame.printDEB("The file " + file + " exists!");
                    } else {
                        MainFrame.printERR("The file " + file + " doesn't exist!");
                    }
                    break;
                }
            case "LDA_HGH":
                {
                    atom.setPSPPath(pathToPSP + "/" + pspType);
                    int znucl = atom.getZnucl();
                    // TODO elec doit varier suivant l'atome. En plus un même
                    // atome peut avoir différentes valeurs de elec !
                    int elec = 1;
                    String fileName = znucl + symbol.toLowerCase() + "." + elec + ".hgh";
                    atom.setPSPFileName(fileName);
                    break;
                }
            case "LDA_TM":
                {
                    int znucl = atom.getZnucl();
                    String folder = "";
                    if (znucl < 10) {
                        folder += "0";
                    }
                    folder += znucl;
                    symbol = symbol.replaceAll("Rf", "Unq");
                    symbol = symbol.replaceAll("Db", "Unp");
                    symbol = symbol.replaceAll("Sg", "Unh");
                    symbol = symbol.replaceAll("Bh", "Uns");
                    symbol = symbol.replaceAll("Hs", "Uno");
                    symbol = symbol.replaceAll("Mt", "Une");
                    symbol = symbol.replaceAll("Ds", "Uun");
                    symbol = symbol.replaceAll("Rg", "Uuu");
                    symbol = symbol.replaceAll("Cn", "Uub");
                    String fileName = znucl + symbol.toLowerCase() + ".pspnc";
                    atom.setPSPFileName(fileName);
                    atom.setPSPPath(pathToPSP + "/" + pspType + ".psps/" + folder);
                    String file = pathToPSP + "/" + pspType + ".psps/" + folder + "/" + fileName;
                    if (Utils.exists(file)) {
                        MainFrame.printDEB("The file " + file + " exists!");
                    } else {
                        MainFrame.printERR("The file " + file + " doesn't exist!");
                    }
                    break;
                }
            case "LDA_Teter":
                {
                    atom.setPSPPath(pathToPSP + "/" + pspType);
                    int znucl = atom.getZnucl();
                    // TODO str est assez variable et une automatisation est impossible
                    // faire un map avec les éléments et leur str (String) correspondant
                    String str = "";
                    String fileName = znucl + symbol.toLowerCase() + "." + str + ".mod";
                    atom.setPSPFileName(fileName);
                    break;
                }
            default:
                MainFrame.printERR("No pseudopotential type defined!");
                atom.setPSPPath("");
                atom.setPSPFileName("");
                atom.setName("");
                atom.setSymbol("");
                atom.setPSPType(dialog.getPSPSelected());
                atom.setZnucl(0);
                break;
        }
    }
    
    public class ItemListener implements ActionListener
    {
        private AbinitPseudo pseudo;
        private String symbol;
        
        public ItemListener(String symbol, AbinitPseudo pseudo)
        {
            this.pseudo = pseudo;
            this.symbol = symbol;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            currentAtom.setPseudo(pseudo);
            currentAtom.setPSPPath(new File(pseudo.path).getParent());
            currentAtom.setPSPFileName(new File(pseudo.path).getName());
            currentAtom.setBySymbol(symbol);
            currentAtom.setPSPType(dialog.getPSPSelected());
            dialog.setVisible(false);
        }
        
        
    }

    private void setAtomButton(String symbol, String pspType, String pathToPSP) {

        JButton button = dialog.getMendButton(symbol);

        if (button == null) {
            return;
        }
        PseudoDatabase database = MainFrame.getPseudoDatabase();
        
        ArrayList<AbinitPseudo> listPseudos = database.getListPseudos(pspType, symbol);
        
        if(listPseudos == null)
        {
            button.setEnabled(false);
        }
        else
        {
            //Create the popup menu.
            final JPopupMenu popup = new JPopupMenu();
            
            for(final AbinitPseudo pseudo : listPseudos)
            {
                JMenuItem item = new JMenuItem(new File(pseudo.path).getName());
                item.setToolTipText(pseudo.toString());
                item.addActionListener(new ItemListener(symbol, pseudo));
                popup.add(item);
            }

            button.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            });
        
            button.setEnabled(true);
            if(listPseudos.size() == 1)
            {
                button.setBackground(Color.RED);
            }
            else if(listPseudos.size() == 2)
            {
                button.setBackground(Color.GREEN);
            }
            else
            {
                button.setBackground(Color.YELLOW);
            }
        }
        
        
        /*switch (pspType) {
            case "GGA_FHI":
                {
                    int znucl = Atom.getZnuclBySymbol(symbol);
                    String fileName = "";
                    if (znucl < 10) {
                        fileName += "0";
                    }
                    fileName += znucl + "-" + symbol + ".GGA.fhi";
                    String file = pathToPSP + "/" + pspType + "/" + fileName;
                    if (Utils.exists(file)) {
                        button.setBackground(Color.GREEN);
                        button.setEnabled(true);
                    } else {
                        button.setEnabled(false);
                    }
                    break;
                }
            case "GGA_HGH":
                {
                    int znucl = Atom.getZnuclBySymbol(symbol);
                    String fileName = "";
                    if (znucl < 10) {
                        fileName += "0";
                    }
                    fileName += znucl + symbol.toLowerCase() + ".pbe_hgh";
                    String file = pathToPSP + "/" + pspType + "/" + fileName;
                    if (Utils.exists(file)) {
                        button.setBackground(Color.GREEN);
                        button.setEnabled(true);
                    } else {
                        button.setEnabled(false);
                    }
                    break;
                }
            case "LDA_Core":
                {
                    int znucl = Atom.getZnuclBySymbol(symbol);
                    String fileName = znucl + symbol.toLowerCase() + ".1s_psp.mod";
                    String file = pathToPSP + "/" + pspType + "/" + fileName;
                    if (Utils.exists(file)) {
                        button.setBackground(Color.GREEN);
                        button.setEnabled(true);
                    } else {
                        button.setEnabled(false);
                    }
                    break;
                }
            case "LDA_FHI":
                {
                    int znucl = Atom.getZnuclBySymbol(symbol);
                    String fileName = "";
                    if (znucl < 10) {
                        fileName += "0";
                    }
                    fileName += znucl + "-" + symbol + ".LDA.fhi";
                    String file = pathToPSP + "/" + pspType + "/" + fileName;
                    if (Utils.exists(file)) {
                        button.setBackground(Color.GREEN);
                        button.setEnabled(true);
                    } else {
                        button.setEnabled(false);
                    }
                    break;
                }
            case "LDA_GTH":
                {
                    int znucl = Atom.getZnuclBySymbol(symbol);
                    String fileName = "";
                    if (znucl < 10) {
                        fileName += "0";
                    }
                    fileName += znucl + symbol.toLowerCase() + ".pspgth";
                    String file = pathToPSP + "/" + pspType + "/" + fileName;
                    if (Utils.exists(file)) {
                        button.setBackground(Color.GREEN);
                        button.setEnabled(true);
                    } else {
                        button.setEnabled(false);
                    }
                    break;
                }
            case "LDA_HGH":
                {
                    int znucl = Atom.getZnuclBySymbol(symbol);
                    // TODO elec doit varier suivant l'atome. En plus un même
                    // atome peut avoir différentes valeurs de elec !
                    int elec = 1;
                    String file = znucl + symbol.toLowerCase() + "." + elec + ".hgh";
                    if (Utils.exists(file)) {
                        button.setBackground(Color.GREEN);
                        button.setEnabled(true);
                    } else {
                        button.setEnabled(false);
                    }
                    break;
                }
            case "LDA_TM":
                {
                    int znucl = Atom.getZnuclBySymbol(symbol);
                    String folder = "";
                    if (znucl < 10) {
                        folder += "0";
                    }
                    folder += znucl;
                    symbol = symbol.replaceAll("Rf", "Unq");
                    symbol = symbol.replaceAll("Db", "Unp");
                    symbol = symbol.replaceAll("Sg", "Unh");
                    symbol = symbol.replaceAll("Bh", "Uns");
                    symbol = symbol.replaceAll("Hs", "Uno");
                    symbol = symbol.replaceAll("Mt", "Une");
                    symbol = symbol.replaceAll("Ds", "Uun");
                    symbol = symbol.replaceAll("Rg", "Uuu");
                    symbol = symbol.replaceAll("Cn", "Uub");
                    String fileName = znucl + symbol.toLowerCase() + ".pspnc";
                    String file = pathToPSP + "/" + pspType + ".psps/" + folder + "/" + fileName;
                    if (Utils.exists(file)) {
                        button.setBackground(Color.GREEN);
                        button.setEnabled(true);
                    } else {
                        button.setEnabled(false);
                    }
                    break;
                }
            case "LDA_Teter":
                {
                    int znucl = Atom.getZnuclBySymbol(symbol);
                    // TODO str est assez variable et une automatisation est impossible
                    // faire un map avec les éléments et leur str (String) correspondant
                    String str = "";
                    String file = znucl + symbol.toLowerCase() + "." + str + ".mod";
                    if (Utils.exists(file)) {
                        button.setBackground(Color.GREEN);
                        button.setEnabled(true);
                    } else {
                        button.setEnabled(false);
                    }
                    break;
                }
            default:
                MainFrame.printERR("No pseudopotential type defined!");
                break;
        }*/
    }

    private int pspReader(String fileName) {
        double zatom;

        String line1;
        String line2;
        //String line3;

        LineNumberReader reader;

        try {
            // open file stream
            Reader stream = new FileReader(fileName);
            // connect to line reader
            reader = new LineNumberReader(stream);
            // read the first 3 lines
            line1 = reader.readLine();
            line2 = reader.readLine();
            //line3 = reader.readLine();
        } catch (FileNotFoundException ex) {
            MainFrame.printERR("File " + fileName + " doesn't exist!");
            return 0;
        } catch (IOException ex) {
            MainFrame.printERR("Problem while reading the first two lines of file " + fileName + "!");
            return 0;
        }

        MainFrame.printDEB("----------- Line 1 -----------");
        MainFrame.printDEB(line1);

        // le parsing des lignes relues, StringTokenizer est fait pour cela:
        StringTokenizer STline2 = new StringTokenizer(line2, " ,:\t", false);
        //StringTokenizer STline3 = new StringTokenizer(line3, " ,\t", false);

        if (STline2.countTokens() == 6) {
            zatom = Double.parseDouble(STline2.nextToken());
            double zion = Double.parseDouble(STline2.nextToken());
            String pspdat = STline2.nextToken();

            MainFrame.printDEB("----------- Line 2 -----------");
            MainFrame.printDEB("zatom = " + zatom + "\n"
                    + "zion = " + zion + "\n"
                    + "pspdat = " + pspdat);
        } else {
            MainFrame.printERR("Problem in line 2");
            return 0;
        }

        /*if (STline3.countTokens() == 12) {
        int pspcod = Integer.parseInt(STline3.nextToken());
        int pspxc = Integer.parseInt(STline3.nextToken());
        int lmax = Integer.parseInt(STline3.nextToken());
        int lloc = Integer.parseInt(STline3.nextToken());
        int mmax = Integer.parseInt(STline3.nextToken());
        int r2well = Integer.parseInt(STline3.nextToken());

        System.out.println("----------- Line 3 -----------");
        System.out.println("pspcod = " + pspcod + "\n"
        + "pspxc = " + pspxc + "\n"
        + "lmax = " + lmax + "\n"
        + "lloc = " + lloc + "\n"
        + "mmax = " + mmax + "\n"
        + "r2well = " + r2well);
        //System.out.println(STline3.nextToken());
        System.out.println("------------------------------");
        } else {
        System.err.println("Problem in line 3");
         * return 0;
        }*/

        return (int) zatom;
    }

    @Override
    public Object getCellEditorValue() {
        return currentAtom;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table,
            Object value,
            boolean isSelected,
            int row,
            int column) {
        currentAtom = (Atom) value;
        //button.setText(currentAtom.getSymbol());
        return but;
    }
}
