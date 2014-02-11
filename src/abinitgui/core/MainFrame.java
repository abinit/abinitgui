/*
 Copyright (c) 2009-2014 Flavio Miguel ABREU ARAUJO (flavio.abreuaraujo@uclouvain.be)
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

package abinitgui.core;

import abinitgui.inputgen.TheoDialog;
import abinitgui.inputgen.AlCoDialog;
import abinitgui.inputgen.GeomDialog;
import abinitgui.inputgen.WaDeDialog;
import abinitgui.inputgen.InOuDialog;
import abinitgui.inputgen.ReReDialog;
import abinitgui.projects.MachineDialog;
import abinitgui.variables.AllInputVars;
import abinitgui.parser.GUIEditor;
import abinitgui.parser.AbinitInputVars;
import abinitgui.scriptbib.ScriptBib;
import abinitgui.scriptbib.Script;
import abinitgui.scriptbib.ScriptTableModel;
import abinitgui.scriptbib.ScriptArgumentRenderer;
import abinitgui.scriptbib.ScriptArgument;
import abinitgui.scriptbib.ScriptTable;
import abinitgui.projects.SubmissionScript;
import abinitgui.projects.Simulation;
import abinitgui.projects.SubmissionScriptFrame;
import abinitgui.projects.SGEScript;
import abinitgui.projects.RemoteJob;
import abinitgui.mdtb.ClustepDiag;
import abinitgui.mdtb.TightBindingDiag;
import java.awt.Color;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import msgdisp.core.MessageDisplayer;
import abinitgui.projects.JobDialog;
import abinitgui.projects.Machine;
import abinitgui.projects.MachineDatabase;
import abinitgui.projects.MachinePane;
import abinitgui.projects.Project;

public class MainFrame extends javax.swing.JFrame {

    private SSH ssh = null;
    private MyTableModel pspModel = null;
    private MyTableModel pspModel1 = null;
    public static LocalExec localExec;
    public RemoteExec remoteExec;
    private SSHTunnel sshtun;
    private boolean useKey1 = false;
    private boolean useKey2 = false;
    private AboutDialog about;
    private int lport = 0;
    public static MessageDisplayer msgdisp;
    private MessageDisplayer inputFileDisplayer;
    public MessageDisplayer clustepInputFileDisplayer;
    public MessageDisplayer clustepPositionFileDisplayer;
    private GeomDialog geomD;
    private AlCoDialog alcoD;
    private ReReDialog rereD;
    private WaDeDialog wadeD;
    private InOuDialog inouD;
    private TheoDialog theoD;
    private MachineDialog machineD;
    private JobDialog jobD;
    //private VarsHelp varsHelp;
    // Avant la version 6 de ABINIT, il y avait deux exécutables différents
    private String SequAbinit = "abinit";
    private String ParaAbinit = "abinit";
    public String CharSet = "UTF-8";
    private static boolean autoTest;
    public static String Version = "0.6";
    public static String SubVersion = "0.1";
    public static String VerMonth = "Feb";
    public static String VerYear = "2014";
    private String outputFile = "out.log";
    public static OutputStreamWriter fw;
    public static BufferedWriter bw;
    public static PrintWriter pw;
    public static MainFrame mainFrame;
    public static boolean DEBUG = true;
    private ClustepDiag clustepDiag;
    private TightBindingDiag tightBindingDiag;
    private DefaultListModel scriptModel;
    private final ScriptBib scriptBibs;
    private final ScriptTableModel argsModel;
    private final ScriptTableModel outModel;
    private String curPath = "."; // to save current Path !
    private GUIEditor guiEditor;
    private AbinitInputVars abinitInputVars;
    private static Simulation simulation;
    private SubmissionScriptFrame submitScriptFrame;
    private final AllInputVars allInputVars;
    private static MachineDatabase machineDatabase;
    private Machine currentMachine;
    private static Project currentProject;
    private Machine currentMachineForScript;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {

        msgdisp = new MessageDisplayer(this, false);
        msgdisp.setTitle("..:: Global MSG Display ::..");
        
        // ---------------------------------------------------------------------

        mainFrame = this;

        initComponents();

        //JFrame.setDefaultLookAndFeelDecorated(true);
        //JDialog.setDefaultLookAndFeelDecorated(true);

        // -------------- Output stream files ----------------------------------
        try {
            fw = new OutputStreamWriter(new FileOutputStream(outputFile),
                    CharSet);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
        } catch (UnsupportedEncodingException ex) {
            printERR("UnsupportedEncodingException (MainFrame())");
        } catch (FileNotFoundException ex) {
            printERR("FileNotFoundException (MainFrame())");
        }
        // ---------------------------------------------------------------------

        this.setTitle("AbinitGUI (v. " + Version + " " + VerMonth
                + " " + VerYear + ")");

        inputFileDisplayer = new MessageDisplayer(this, false);
        inputFileDisplayer.setTitle("..:: Input file preview ::..");

        clustepInputFileDisplayer = new MessageDisplayer(this, false);
        clustepInputFileDisplayer.setTitle("..:: Clustep input file preview ::..");

        clustepPositionFileDisplayer = new MessageDisplayer(this, false);
        clustepPositionFileDisplayer.setTitle("..:: Clustep position file preview ::..");

        geomD = new GeomDialog(this, false);
        geomD.setTitle("..:: Geometry ::..");

        alcoD = new AlCoDialog(this, false);
        alcoD.setTitle("..:: Algorithm and convergence ::..");

        rereD = new ReReDialog(this, false);
        rereD.setTitle("..:: Real and reciprocal space ::..");

        wadeD = new WaDeDialog(this, false);
        wadeD.setTitle("..:: Wavefunctions and densities ::..");

        inouD = new InOuDialog(this, false);
        inouD.setTitle("..:: Input / Output ::..");

        theoD = new TheoDialog(this, false);
        theoD.setTitle("..:: Theory (DFT) ::..");
        
        machineD = new MachineDialog(this, false);
        machineD.setTitle(" Select machine !");
        
        jobD = new JobDialog(this, false);
        jobD.setTitle(" New input dialog !");

        //varsHelp = new VarsHelp();
        //varsHelp.setTitle("..:: Abinit variables help ::..");

        localExec = new LocalExec();

        about = new AboutDialog(this, true);

        // ---------------------------------------------------------------------
        clustepDiag = new ClustepDiag(this, false);
        clustepDiag.setTitle("..:: Clustep ::..");
        clustepDiag.setVisible(false);

        tightBindingDiag = new TightBindingDiag(this, false);
        tightBindingDiag.setTitle("..:: Tight-Binding ::..");
        tightBindingDiag.setVisible(false);

        // Pour activer ou désactiver le menu Clustep & Tight-Binding
        jMenuClustepAndTB.setVisible(true);
        // ---------------------------------------------------------------------

        //loadConfig("config.xml");

        msgdisp.show();
        msgdisp.setLocationRelativeTo(this);

        // TODO rendre visible
        //mainTabbedPane.setEnabledAt(4, false);

        autoTest = false;

        // ---------------------------------------------------------------------

        /**
         * Script section *
         */

        argsModel = new ScriptTableModel(scriptArgTable);
        scriptArgTable.setModel(argsModel);
        initTableHeader(scriptArgTable, new String[]{"Name", "Value"},
                new Integer[]{null, null});
        scriptArgTable.setDefaultRenderer(ScriptArgument.class,
                new ScriptArgumentRenderer());


        outModel = new ScriptTableModel(scriptOutTable);
        scriptOutTable.setModel(outModel);
        initTableHeader(scriptOutTable, new String[]{"Name", "Value"},
                new Integer[]{null, null});
        scriptOutTable.setDefaultRenderer(ScriptArgument.class,
                new ScriptArgumentRenderer());

        scriptBibs = new ScriptBib();
        
        if(Utils.exists("listScripts.xml"))
        {
            scriptBibs.loadScriptsFromFile("listScripts.xml");
        } else {
            printERR("No file listScripts.xml in the directory: you won't be able to use post-processing tools.");
        }

        scriptModel = new DefaultListModel();
        scriptList.setModel(scriptModel);

        showScripts();

        /**
         * End of script section *
         */

        guiEditor = new GUIEditor();
        allInputVars = new AllInputVars();
        abinitInputVars = new AbinitInputVars(allInputVars);

        submitScriptFrame = new SubmissionScriptFrame();
        jMenuClustepAndTB.setVisible(false);
        
        /**
         * Projects section
         */
        
        machineDatabase = new MachineDatabase();
        try{
            machineDatabase.loadFromFile("machines.yml");
        } catch(IOException e) {
            printERR("Unable to load machine configurations from \"machines.yml\".");
            try{
                machineDatabase.saveToFile("machines.yml");
            } catch(IOException exc) {
                printERR("Error saving new file machines.yml. (IOException: "
                        + exc.getMessage() + ")");
            } catch(Exception exc) {
                printERR("Error saving new file machines.yml. (Exception: "
                        + exc.getMessage() + ")");
            }
        }
        
        // Default values
        RemoteJob remoteJob = new RemoteJob();
        // Default values
        SubmissionScript script = new SGEScript();
        remoteJob.setScript(script);
        simulation = new Simulation();
        simulation.setRemoteJob(remoteJob);
        try{
            currentProject = Project.fromFile("currentProject.yml");
        } catch(IOException e) {
            printERR("Unable to load project file! (IOException: " + e.getMessage() + ")");
            currentProject = new Project("currentProject.yml");
            try{
                currentProject.save();
            } catch(IOException exc) {
                printERR("Error saving new project! (IOException: " + exc.getMessage() + ")");
            } catch(Exception exc) {
                printERR("Error saving new project! (Exception: " + exc.getMessage() + ")");
            }
        }
        
        machinePane1.refresh();
        jobPanel1.refreshProject();
        jobPanel1.refreshMachines();
        
        refreshMachines();
        
        /**
         * End of projects section
         */

    }

    public String getNtypat() {
        return geomD.getNtypat();
    }
    
    public final void refreshMachines()
    {
        Machine mymach = (Machine)(machineCombo.getSelectedItem());
        Machine mymach2 = (Machine)(machineCombo1.getSelectedItem());
        
        DefaultComboBoxModel<Machine> model = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<Machine> model2 = new DefaultComboBoxModel<>();
        
        for(Machine mach : machineDatabase)
        {
            model.addElement(mach);
            model2.addElement(mach);
        }
        
        machineCombo.setModel(model);
        machineCombo.setSelectedItem(mymach);
        
        machineCombo1.setModel(model2);
        machineCombo1.setSelectedItem(mymach2);
        
        jobD.refresh();
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

    public static void printERR(String s) {
        msgdisp.printERR(Utils.removeEndl(s));
        pw.print("ERR>> " + Utils.removeEndl(s) + "\n");
        System.err.println("ERR>> " + Utils.removeEndl(s));
        pw.flush();
    }

    public static void printOUT(String s) {
        msgdisp.printOUT(Utils.removeEndl(s));
        pw.print("OUT>> " + Utils.removeEndl(s) + "\n");
        System.out.println("OUT>> " + Utils.removeEndl(s));
        pw.flush();
    }

    public static void printDEB(String s) {
        msgdisp.printDEB("DEB: " + Utils.removeEndl(s));
        pw.print("DEB>> " + Utils.removeEndl(s) + "\n");
        System.out.println("DEB>> " + Utils.removeEndl(s));
        pw.flush();
    }

    public static void printGEN(String s, Color color, boolean underline, boolean bolt) {
        //msgdisp.printGEN(removeEndl(s), color, underline, bolt);
        msgdisp.printGEN(Utils.removeEndl(s));
        pw.print("GEN>> " + Utils.removeEndl(s) + "\n");
        System.out.println("GEN>> " + Utils.removeEndl(s));
        pw.flush();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    //@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        whereIsAbinitbuttonGroup = new javax.swing.ButtonGroup();
        inputFilebuttonGroup = new javax.swing.ButtonGroup();
        lookAndFeelbuttonGroup = new javax.swing.ButtonGroup();
        abinixbuttonGroup = new javax.swing.ButtonGroup();
        mainTabbedPane = new javax.swing.JTabbedPane();
        machinePane1 = new abinitgui.projects.MachinePane();
        jobPanel1 = new abinitgui.projects.JobPanel();
        postProcPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        scriptScrollPane = new javax.swing.JScrollPane();
        scriptList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        scriptName = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        scriptDescription = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        scriptArgTable = new ScriptTable();
        launchScript = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        scriptOutTable = new ScriptTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        scriptProgram = new javax.swing.JTextField();
        openOutput = new javax.swing.JButton();
        editScripts = new javax.swing.JButton();
        reloadScripts = new javax.swing.JButton();
        machineCombo1 = new javax.swing.JComboBox();
        machineLabel1 = new javax.swing.JLabel();
        connectionToggleButton = new javax.swing.JToggleButton();
        SSH2ClientButton = new javax.swing.JButton();
        SFTPButton = new javax.swing.JButton();
        machineCombo = new javax.swing.JComboBox();
        machineLabel = new javax.swing.JLabel();
        mainMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        viewMenu = new javax.swing.JMenu();
        outputMSGMenuItem = new javax.swing.JMenuItem();
        clearOutMSGMenuItem = new javax.swing.JMenuItem();
        jMenuClustepAndTB = new javax.swing.JMenu();
        jMenuItemClustep = new javax.swing.JMenuItem();
        jMenuItemTB = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AbinitGUI");
        setBackground(new java.awt.Color(245, 242, 239));
        setResizable(false);

        mainTabbedPane.setMaximumSize(new java.awt.Dimension(800, 650));
        mainTabbedPane.setMinimumSize(new java.awt.Dimension(800, 650));
        mainTabbedPane.setPreferredSize(new java.awt.Dimension(800, 650));
        mainTabbedPane.addTab("Machines", machinePane1);
        mainTabbedPane.addTab("Jobs", jobPanel1);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Scripts Library", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 3, 14), java.awt.Color.darkGray)); // NOI18N
        jPanel1.setToolTipText("");

        scriptList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        scriptList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                scriptListValueChanged(evt);
            }
        });
        scriptScrollPane.setViewportView(scriptList);

        jLabel1.setText("Name of the script :");

        scriptName.setText("Name");

        jLabel3.setText("Description : ");

        scriptDescription.setEditable(false);
        scriptDescription.setColumns(20);
        scriptDescription.setLineWrap(true);
        scriptDescription.setRows(5);
        scriptDescription.setText("Description of the script");
        scriptDescription.setWrapStyleWord(true);
        jScrollPane1.setViewportView(scriptDescription);

        jLabel5.setText("Arguments :");

        scriptArgTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(scriptArgTable);

        launchScript.setText("Launch script !");
        launchScript.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                launchScriptActionPerformed(evt);
            }
        });

        scriptOutTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(scriptOutTable);

        jLabel6.setText("Output files :");

        jLabel9.setText("Program :");

        scriptProgram.setEditable(false);
        scriptProgram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scriptProgramActionPerformed(evt);
            }
        });

        openOutput.setText("Open output files !");
        openOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openOutputActionPerformed(evt);
            }
        });

        editScripts.setText("Edit script");
        editScripts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editScriptsActionPerformed(evt);
            }
        });

        reloadScripts.setText("Reload scripts");
        reloadScripts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reloadScriptsActionPerformed(evt);
            }
        });

        machineCombo1.setModel(new DefaultComboBoxModel<Machine>());
        machineCombo1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                machineCombo1ItemStateChanged(evt);
            }
        });
        machineCombo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                machineCombo1ActionPerformed(evt);
            }
        });

        machineLabel1.setText("Select machine on which running the simulation :");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(scriptScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 220, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                        .add(editScripts, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, reloadScripts, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 220, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(machineLabel1)
                        .add(0, 0, Short.MAX_VALUE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(jLabel3)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jScrollPane1))
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jPanel1Layout.createSequentialGroup()
                                        .add(154, 154, 154)
                                        .add(machineCombo1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 135, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(jPanel1Layout.createSequentialGroup()
                                        .add(jLabel1)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(scriptName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 287, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                                .add(0, 0, Short.MAX_VALUE)))
                        .add(12, 12, 12))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jPanel1Layout.createSequentialGroup()
                                        .add(jLabel9)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(scriptProgram, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 211, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                    .add(jLabel6)
                                    .add(jPanel1Layout.createSequentialGroup()
                                        .add(launchScript, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 191, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(openOutput)))
                                .add(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                        .add(jLabel5)
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(scriptScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 366, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(editScripts)
                        .add(3, 3, 3)
                        .add(reloadScripts))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(machineLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(machineCombo1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel1)
                            .add(scriptName))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel3)
                            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(4, 4, 4)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel9)
                            .add(scriptProgram, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel5)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 194, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel6)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(launchScript)
                    .add(openOutput))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout postProcPanelLayout = new org.jdesktop.layout.GroupLayout(postProcPanel);
        postProcPanel.setLayout(postProcPanelLayout);
        postProcPanelLayout.setHorizontalGroup(
            postProcPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(postProcPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        postProcPanelLayout.setVerticalGroup(
            postProcPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(postProcPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainTabbedPane.addTab("Post-processing", postProcPanel);

        connectionToggleButton.setText("Connect");
        connectionToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectionToggleButtonActionPerformed(evt);
            }
        });

        SSH2ClientButton.setText("SSH2 Client");
        SSH2ClientButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SSH2ClientButtonActionPerformed(evt);
            }
        });

        SFTPButton.setText("SFTP Client");
        SFTPButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SFTPButtonActionPerformed(evt);
            }
        });

        machineCombo.setModel(new DefaultComboBoxModel<Machine>());
        machineCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                machineComboItemStateChanged(evt);
            }
        });
        machineCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                machineComboActionPerformed(evt);
            }
        });

        machineLabel.setText("Direct access to:");

        fileMenu.setLabel("File");

        jMenuItem3.setText("Save project");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem3);

        jMenuItem4.setText("Load project");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem4);

        mainMenuBar.add(fileMenu);

        viewMenu.setText("View");

        outputMSGMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        outputMSGMenuItem.setText("Display MSG panel");
        outputMSGMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outputMSGMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(outputMSGMenuItem);

        clearOutMSGMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        clearOutMSGMenuItem.setText("Clear MSG panel");
        clearOutMSGMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearOutMSGMenuItemActionPerformed(evt);
            }
        });
        viewMenu.add(clearOutMSGMenuItem);

        mainMenuBar.add(viewMenu);

        jMenuClustepAndTB.setText("Clustep & TB");

        jMenuItemClustep.setText("Clustep");
        jMenuItemClustep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemClustepActionPerformed(evt);
            }
        });
        jMenuClustepAndTB.add(jMenuItemClustep);

        jMenuItemTB.setText("Tight-Binding");
        jMenuItemTB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTBActionPerformed(evt);
            }
        });
        jMenuClustepAndTB.add(jMenuItemTB);

        mainMenuBar.add(jMenuClustepAndTB);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem1.setText("Input Variables");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        helpMenu.add(jMenuItem1);

        aboutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK));
        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        mainMenuBar.add(helpMenu);

        setJMenuBar(mainMenuBar);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(mainTabbedPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 754, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(layout.createSequentialGroup()
                        .add(machineLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(machineCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(connectionToggleButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 143, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(SSH2ClientButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(SFTPButton)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(mainTabbedPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(connectionToggleButton)
                    .add(SSH2ClientButton)
                    .add(SFTPButton)
                    .add(machineCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(machineLabel))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void stopConnection() {
        if(currentMachine != null)
        {
            currentMachine.stopConnection();
        }
        else
        {
            printERR("Please select a machine!");
        }
    }

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        about.setLocationRelativeTo(this);
        about.setVisible(true);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void outputMSGMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outputMSGMenuItemActionPerformed
        msgdisp.setLocationRelativeTo(this);
        msgdisp.show();
    }//GEN-LAST:event_outputMSGMenuItemActionPerformed

    private void clearOutMSGMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearOutMSGMenuItemActionPerformed
        //outDialog.clear();
        msgdisp.clear();
    }//GEN-LAST:event_clearOutMSGMenuItemActionPerformed

    private void connectionToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectionToggleButtonActionPerformed
        Runnable r = new Runnable() {
            @Override
            public void run() {
                connectionToggleButton.setEnabled(false);
                if (connectionToggleButton.isSelected()) {
                    if(currentMachine != null)
                    {
                        currentMachine.connection();
                        setStateConnect();
                    }
                    else
                    {
                        printERR("Please select a machine first!");
                    }
                } else {
                    stopConnection();
                    setStateConnect();
                    //connectionToggleButton.setText("Connect");
                    printOUT("You are now disconnected!");
                }
                connectionToggleButton.setEnabled(true);
            }
        };

        Thread t = new Thread(r);
        t.start();
}//GEN-LAST:event_connectionToggleButtonActionPerformed

    private void SSH2ClientButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SSH2ClientButtonActionPerformed
        Machine mach = (Machine)machineCombo.getSelectedItem();
        if(mach != null)
        {
            MySSHTerm ssh2 = mach.newSSHTerm();
        }
        else
        {
            printERR("Please select a machine first!");
        }
    }//GEN-LAST:event_SSH2ClientButtonActionPerformed

    private void jMenuItemClustepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemClustepActionPerformed
        clustepDiag.setVisible(true);
    }//GEN-LAST:event_jMenuItemClustepActionPerformed

    private void jMenuItemTBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemTBActionPerformed
        tightBindingDiag.setVisible(true);
    }//GEN-LAST:event_jMenuItemTBActionPerformed

    private void SFTPButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SFTPButtonActionPerformed
        if(currentMachine != null)
        {
            MySFTP client = currentMachine.newSFTP();
        }
        else
        {
            printERR("Please select a machine first!");
        }
    }//GEN-LAST:event_SFTPButtonActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        abinitInputVars.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void machineComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_machineComboItemStateChanged
        
    }//GEN-LAST:event_machineComboItemStateChanged

    private void setStateConnect()
    {
        if(currentMachine != null && currentMachine.isConnected())
        {
            connectionToggleButton.setSelected(true);
            connectionToggleButton.setText("Disconnect");
        }
        else
        {
            connectionToggleButton.setSelected(false);
            connectionToggleButton.setText("Connect"); 
        }
    }
    private void machineComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_machineComboActionPerformed
        Machine mach = (Machine)machineCombo.getSelectedItem();
        if(mach != null)
        {
            this.currentMachine = mach;   
            setStateConnect();
        }
    }//GEN-LAST:event_machineComboActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        if(currentProject != null)
        {
            try {
                currentProject.save();
            } catch (IOException ex) {
                Logger.getLogger(MachinePane.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        JFileChooser fc = new JFileChooser(".");
        fc.setMultiSelectionEnabled(false);
        int retValue = fc.showOpenDialog(this);
        if (retValue == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            currentProject = new Project(file.getPath());
        }
        
        jobD.refresh();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void reloadScriptsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reloadScriptsActionPerformed
        scriptBibs.getList().clear();
        scriptBibs.loadScriptsFromFile("listScripts.xml");
        showScripts();
    }//GEN-LAST:event_reloadScriptsActionPerformed

    private void editScriptsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editScriptsActionPerformed
        int idScript = scriptList.getSelectedIndex();

        if (idScript == -1) {
            printERR("Please select a script first!");
        } else {
            Script scr = scriptBibs.getList().get(idScript);

            String inputFile = scr.fileName;

            if (Utils.osName().startsWith("Windows")) {
                Utils.unix2dos(new File(inputFile));
            }
            editFile(inputFile, true);
        }
    }//GEN-LAST:event_editScriptsActionPerformed

    private void openOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openOutputActionPerformed

        Machine mach = currentMachineForScript;
        if(mach == null)
        {
            printERR("Please select a machine first");
        }
        
        int index = scriptList.getSelectedIndex();
        if (index < 0) {
            return;
        }

        Script scr = scriptBibs.getList().get(index);
        if (scr == null) {
            return;
        }

        ArrayList<ScriptArgument> listOut = scr.listOutput;

        String rootPath;
        if(mach != null) {
            rootPath = mach.getSimulationPath();
        } else {
            // TODO: error report
            rootPath = "";
        }

        // Open files
        for (int i = 0; i < listOut.size(); i++) {
            String outFile = (String) scriptOutTable.getValueAt(i, 1);
            //String outFileR = rootPath + "/" + folder + "/" + outFile;

            File f = new File(rootPath);

            if (!f.exists()) {
                localExec.createTree(rootPath);
                f = new File(rootPath);
            }

            String abs = "";
            try {
                abs = f.getCanonicalPath();
            } catch (IOException ex) {
                printERR("Unable to get output file canonical path");
                return;
            }

            f = new File(outFile);

            printOUT("Trying to open file : " + outFile);
            if (f.exists()) {
                try {
                    if (Desktop.isDesktopSupported()) {
                        if (Utils.osName().contains("Windows")) {
                            if (outFile.endsWith(".txt") || outFile.endsWith(".dbs") || outFile.endsWith(".agr")
                                || outFile.endsWith(".files") || outFile.endsWith(".in") || outFile.endsWith(".out")
                                || outFile.endsWith(".sh")) {
                                editFile(outFile, true);
                            } else {
                                Desktop.getDesktop().open(f);
                            }
                        } else {
                            Desktop.getDesktop().open(f);
                        }
                    } else {
                        printOUT("Not able to open the file with the default editor. Use basic editor instead.");
                        editFile(outFile, true);
                    }
                } catch (IOException ex) {
                    printOUT("Not able to open the file with the default editor. Use editor instead.");
                    editFile(outFile, true);
                }
            } else {
                printERR("Please execute the script before opening the output files.");
            }

        }
    }//GEN-LAST:event_openOutputActionPerformed

    private void scriptProgramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scriptProgramActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_scriptProgramActionPerformed

    private void launchScriptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_launchScriptActionPerformed

        Runnable r = new Runnable() {
            @Override
            public void run() {

                Machine mach = currentMachineForScript;
                
                if(mach == null)
                {
                    printERR("Please select a machine first");
                    return;
                }
                
                String rootPath = mach.getSimulationPath();

                String folder = "scripts";

                String path = mach.getSimulationPath();
                if (path.equals("")) {
                    path = ".";
                }

                createLocalTree(path);

                int index = scriptList.getSelectedIndex();

                if (index == -1) {
                    printERR("Please select a script before running");
                    return;
                }

                Script scr = scriptBibs.getList().get(index);

                if (scr == null) {
                    printERR("Please select a script before running");
                    return;
                }

                String inputFile = scr.fileName;

                String program = scr.program;

                // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

                if (Utils.osName().startsWith("Windows") || Utils.osName().startsWith("MAC")) {
                    printERR("Scripts are not yet supported for Windows platform");
                    launchScript.setEnabled(true);
                    return;
                }

                // ********************************************************************************************************************************

                String cwd = "";

                String CMD = "pwd";

                RetMSG retmsg;

                retmsg = localExec.sendCommand(CMD);
                if (retmsg.getRetCode() == RetMSG.SUCCES) {
                    printOUT("PWD: " + retmsg.getRetMSG());
                    cwd = Utils.removeEndl(retmsg.getRetMSG());
                } else {
                    printERR("Error: " + retmsg.getRetMSG() + " !");
                }

                String inputFileName = Utils.getLastToken(inputFile.replace('\\', '/'), "/");

                // Test de l'existance de inputfile
                if (!Utils.exists(inputFile)) {
                    printERR("The file " + inputFile + " doesn't exist!");
                    launchScript.setEnabled(true);
                    return;
                }

                if (!inputFile.equals("")) {
                    // Will do the computation in rootpath/folder
                    String inputFileR = rootPath + "/" + folder + "/" + inputFileName;
                    retmsg = localExec.sendCommand("cp "+inputFile+" "+inputFileR);
                    if (retmsg.getRetCode() != RetMSG.SUCCES) {
                        printERR("Error: " + retmsg.getRetMSG() + "!");
                    }

                    ArrayList<String> allCommand = new ArrayList<>();
                    ArrayList<ScriptArgument> listArgs = scr.listArgs;
                    allCommand.add(program);
                    allCommand.add(inputFileR);

                    String command = program + " " + inputFileR;

                    for (int i = 0; i < listArgs.size(); i++) {
                        String input = (String) scriptArgTable.getValueAt(i, 1);
                        command = command + " --" + listArgs.get(i).name + " \'" + input + "\'";
                        allCommand.add("--" + listArgs.get(i).name );
                        allCommand.add(input);
                    }

                    System.out.println("CWD = "+cwd);

                    ArrayList<ScriptArgument> listOut = scr.listOutput;

                    for (int i = 0; i < listOut.size(); i++) {
                        String outFile = (String) scriptOutTable.getValueAt(i, 1);
                        //                        String outFileR = rootPath + "/" + folder + "/" + outFile;
                        command = command + " --" + listOut.get(i).name + " \'" + outFile + "\'";
                        allCommand.add("--" + listOut.get(i).name);
                        allCommand.add(outFile);
                    }

                    String[] arrayCMD = allCommand.toArray(new String[0]);
                    retmsg = localExec.sendCommand(arrayCMD);
                    if (retmsg.getRetCode() == RetMSG.SUCCES) {
                        printOUT("Script output : \n"+retmsg.getRetMSG());
                    } else {
                        printERR("Error: " + retmsg.getRetMSG() + "!");
                    }
                    printDEB(command);

                }

            }
        };

        Thread t = new Thread(r);
        t.start();
    }//GEN-LAST:event_launchScriptActionPerformed

    private void scriptListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_scriptListValueChanged
        int index = scriptList.getSelectedIndex();
        if (index < 0) {
            scriptName.setText("Name");
            scriptDescription.setText("Description of the script");
            scriptProgram.setText("");
            argsModel.resetScripts();
            outModel.resetScripts();
        } else {
            Script scr = scriptBibs.getList().get(index);
            if (scr == null) {
                scriptName.setText("Name");
                scriptDescription.setText("Description of the script");
                scriptProgram.setText("");
                argsModel.resetScripts();
                outModel.resetScripts();
                return;
            }

            scriptName.setText(scr.title);

            scriptDescription.setText(scr.description.replace("\\n", "\n"));

            scriptProgram.setText(scr.program);
            argsModel.resetScripts();
            outModel.resetScripts();

            ArrayList<ScriptArgument> listArgs = scr.listArgs;

            int nbArgs = listArgs.size();

            for (int i = 0; i < nbArgs; i++) {
                argsModel.addScript(listArgs.get(i));
            }

            ArrayList<ScriptArgument> outArgs = scr.listOutput;

            int nbOut = outArgs.size();

            for (int i = 0; i < nbOut; i++) {
                outModel.addScript(outArgs.get(i));
            }
        }
    }//GEN-LAST:event_scriptListValueChanged

    private void machineCombo1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_machineCombo1ItemStateChanged

    }//GEN-LAST:event_machineCombo1ItemStateChanged

    private void machineCombo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_machineCombo1ActionPerformed
        Machine mach = (Machine)machineCombo1.getSelectedItem();
        if(mach != null)
        {
            this.currentMachineForScript = mach;
        }
    }//GEN-LAST:event_machineCombo1ActionPerformed

    public static void localCommand(String CMD) /*throws CMDException*/ {
        RetMSG retmsg;
        retmsg = localExec.sendCommand(CMD);
        if (retmsg.getRetCode() == RetMSG.SUCCES) {
            printOUT("Succes: " + retmsg.getCMD() + " => " + Utils.removeEndl(retmsg.getRetMSG()) + ".");
        } else {
            //printERR("Error (RetVal = " + retmsg.getRetCode() + "): " + retmsg.getRetMSG());
            printERR("Error: " + Utils.removeEndl(retmsg.getRetMSG()) + "!");
        }
    }

    public void mkdir(String dir) {
        if (Utils.mkdir(dir)) {
            printOUT("Succes: mkdir " + dir + ".");
        } else {
            if (Utils.exists(dir)) {
                printDEB("The local directory `" + dir + "' exists!");
            } else {
                printERR("Error: mkdir: cannot create directory `" + dir + "'!");
            }
        }
    }

    private void localCopy(String parameters) {
        RetMSG retmsg = localExec.sendCommand("cp " + parameters);
        System.out.println(retmsg.getRetMSG());
        if (retmsg.getRetCode() == RetMSG.SUCCES) {
            printOUT("Succes: " + retmsg.getCMD() + " => " + Utils.removeEndl(retmsg.getRetMSG()) + ".");
        } else {
            //printERR("Error (RetVal = " + retmsg.getRetCode() + "): " + retmsg.getRetMSG());
            printERR("Error: " + Utils.removeEndl(retmsg.getRetMSG()) + "!");
        }
    }

    private void showScripts() {
        scriptList.clearSelection();
        scriptModel.clear();

        ArrayList<Script> list = scriptBibs.getList();
        int nbScripts = list.size();

        String strName[] = new String[nbScripts];

        for (int i = 0; i < nbScripts; i++) {
            Script scr = list.get(i);

            strName[i] = scr.title;
        }

        scriptList.setListData(strName);
    }
    
    public void createLocalTree(String path)
    {
        mkdir(path);
        mkdir(path + "/input");
        mkdir(path + "/output");
        mkdir(path + "/wholedata");
        mkdir(path + "/logfiles");
        mkdir(path + "/pseudopot");
        mkdir(path + "/scripts");
    }
    
    public void sendSIMButton_setEnabled(boolean enab) {
        if (MainFrame.autoTest) {
            // Do nothing in autotest mode
        } else {
            // TODO sendSIMButton.setEnabled(enab);
        }
    }

    public static void editFile(final String fileName, boolean threaded) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                // Tester l'existance du fichier
                if (!Utils.exists(fileName)) {
                    printERR("File " + fileName + " doesn't exist!");
                } else {
                    if (Utils.osName().equals("Linux")) {
                        localCommand("gedit " + fileName);
                    } else if (Utils.osName().equals("Mac OS X")) {
                        localCommand("open -a textedit " + fileName);
                    } else if (Utils.osName().startsWith("Windows")) {
                        Utils.unix2dos(new File(fileName));
                        localCommand("notepad " + fileName);
                    } else {
                        printERR("No text editor has been found!");
                    }
                }
            }
        };

        if (threaded) {
            Thread t = new Thread(r);
            t.start();
        } else {
            r.run();
        }
    }
    
    // For accessing some private variables from outside of abinitgui package
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton SFTPButton;
    javax.swing.JButton SSH2ClientButton;
    javax.swing.ButtonGroup abinixbuttonGroup;
    javax.swing.JMenuItem aboutMenuItem;
    javax.swing.JMenuItem clearOutMSGMenuItem;
    javax.swing.JToggleButton connectionToggleButton;
    javax.swing.JButton editScripts;
    javax.swing.JMenu fileMenu;
    javax.swing.JMenu helpMenu;
    javax.swing.ButtonGroup inputFilebuttonGroup;
    javax.swing.JLabel jLabel1;
    javax.swing.JLabel jLabel3;
    javax.swing.JLabel jLabel5;
    javax.swing.JLabel jLabel6;
    javax.swing.JLabel jLabel9;
    javax.swing.JMenu jMenuClustepAndTB;
    javax.swing.JMenuItem jMenuItem1;
    javax.swing.JMenuItem jMenuItem3;
    javax.swing.JMenuItem jMenuItem4;
    javax.swing.JMenuItem jMenuItemClustep;
    javax.swing.JMenuItem jMenuItemTB;
    javax.swing.JPanel jPanel1;
    javax.swing.JScrollPane jScrollPane1;
    javax.swing.JScrollPane jScrollPane2;
    javax.swing.JScrollPane jScrollPane3;
    abinitgui.projects.JobPanel jobPanel1;
    javax.swing.JButton launchScript;
    javax.swing.ButtonGroup lookAndFeelbuttonGroup;
    javax.swing.JComboBox machineCombo;
    javax.swing.JComboBox machineCombo1;
    javax.swing.JLabel machineLabel;
    javax.swing.JLabel machineLabel1;
    abinitgui.projects.MachinePane machinePane1;
    javax.swing.JMenuBar mainMenuBar;
    javax.swing.JTabbedPane mainTabbedPane;
    javax.swing.JButton openOutput;
    javax.swing.JMenuItem outputMSGMenuItem;
    javax.swing.JPanel postProcPanel;
    javax.swing.JButton reloadScripts;
    javax.swing.JTable scriptArgTable;
    javax.swing.JTextArea scriptDescription;
    javax.swing.JList scriptList;
    javax.swing.JLabel scriptName;
    javax.swing.JTable scriptOutTable;
    javax.swing.JTextField scriptProgram;
    javax.swing.JScrollPane scriptScrollPane;
    javax.swing.JMenu viewMenu;
    javax.swing.ButtonGroup whereIsAbinitbuttonGroup;
    // End of variables declaration//GEN-END:variables

    public void setSimulation(Simulation simulation) {
        printDEB("Is setting simulation " + simulation + ".");
    }

    public static Simulation getSimulation() 
    {
        return simulation;
    }

    public AllInputVars getAllInputVars() 
    {
        return allInputVars;
    }

    public static MachineDatabase getMachineDatabase() 
    {
        return machineDatabase;
    }
    
    public static Project getCurrentProject()
    {
        return currentProject;
    }
    
    public static LocalExec getLocalExec()
    {
        return localExec;
    }
}
