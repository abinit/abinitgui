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
import abivars.AllInputVars;
import abinitgui.parser.GUIEditor;
import abivars.AbinitInputVars;
import abinitgui.projects.SubmissionScript;
import abinitgui.projects.Simulation;
import abinitgui.projects.SubmissionScriptFrame;
import abinitgui.projects.SGEScript;
import abinitgui.projects.RemoteJob;
import abinitgui.mdtb.ClustepDiag;
import abinitgui.mdtb.TightBindingDiag;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
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
import abinitgui.pseudos.PseudoDatabase;
import javax.swing.JFrame;

public class MainFrame extends JFrame {


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
    public static String Version = "0.7";
    public static String SubVersion = "0.2";
    public static String VerMonth = "April";
    public static String VerYear = "2014";
    private String outputFile = "AbinitGUI.log";
    public static MainFrame mainFrame;
    public static boolean DEBUG = false;
    private ClustepDiag clustepDiag;
    private TightBindingDiag tightBindingDiag;
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
    
    private static PseudoDatabase remotePseudoDatabase;
    private static PseudoDatabase localPseudoDatabase;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {

        
        // Disable SNI support !
        System.setProperty("jsse.enableSNIExtension", "false");
        
        
        msgdisp = new MessageDisplayer(this, false, outputFile);
        msgdisp.setTitle("..:: Global MSG Display ::..");
        
        // ---------------------------------------------------------------------

        mainFrame = this;

        initComponents();

        //JFrame.setDefaultLookAndFeelDecorated(true);
        //JDialog.setDefaultLookAndFeelDecorated(true);

        this.setTitle("AbinitGUI (v. " + Version + " " + VerMonth
                + " " + VerYear + ")");

        inputFileDisplayer = new MessageDisplayer(this, false, null);
        inputFileDisplayer.setTitle("..:: Input file preview ::..");

        clustepInputFileDisplayer = new MessageDisplayer(this, false, null);
        clustepInputFileDisplayer.setTitle("..:: Clustep input file preview ::..");

        clustepPositionFileDisplayer = new MessageDisplayer(this, false, null);
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
        machineD.setTitle("Select machine!");
        
        jobD = new JobDialog(this, false);
        jobD.setTitle("New input dialog!");

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

        guiEditor = new GUIEditor();
        allInputVars = new AllInputVars();
        allInputVars.loadVars("abinit_vars.yml");
        File fileOpened = new File("abinit_vars.yml");
        abinitInputVars = new AbinitInputVars(allInputVars,fileOpened);

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
            if(currentProject == null)
            {
                printERR("Error in the file currentProject.yml. The file will be overwritten on save !");
                currentProject = new Project(null);
                currentProject.setFileName("currentProject.yml");
            }
        } catch(IOException e) {
            printERR("Unable to load project file! (IOException: " + e.getMessage() + ")");
            currentProject = new Project(null);
            currentProject.setFileName("currentProject.yml");
            /*try{
                currentProject.save();
            } catch(IOException exc) {
                printERR("Error saving new project! (IOException: " + exc.getMessage() + ")");
            } catch(Exception exc) {
                printERR("Error saving new project! (Exception: " + exc.getMessage() + ")");
            }*/
        }
        
        machinePane1.refresh();
        jobPanel1.refreshProject();
        jobPanel1.refreshMachines();
        postProcPanel1.refresh();
        postProcPanel1.refreshMachines();
        jobMonitorPanel1.refreshMachines();
        
        refreshMachines();
        
        /**
         * End of projects section
         */
        
        /**
         * Pseudo section
         */
        
        localPseudoDatabase = new PseudoDatabase();
        localPseudoDatabase.fromFile("pseudos.yml");
        
        remotePseudoDatabase = new PseudoDatabase();
        try{
            remotePseudoDatabase.fromUrl("http://gui.abinit.org/PSPS/pseudos.yml");
        }catch(Exception e)
        {
            printERR("Error connecting to gui.abinit.org, will use local database of pseudos");
            remotePseudoDatabase = localPseudoDatabase;
        }
        
        /**
         * End of pseudo section
         */

    }

    public String getNtypat() {
        return geomD.getNtypat();
    }
    
    public final void refreshMachines()
    {
        jobPanel1.refreshMachines();
        
        Machine mymach = (Machine)(machineCombo.getSelectedItem());
        
        DefaultComboBoxModel<Machine> model = new DefaultComboBoxModel<>();
        
        for(Machine mach : machineDatabase)
        {
            model.addElement(mach);
        }
        
        machineCombo.setModel(model);
        machineCombo.setSelectedItem(mymach);
        
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
        if(s != null)
          msgdisp.printERR(Utils.removeEndl(s));
    }

    public static void printOUT(String s) {
        msgdisp.printOUT(Utils.removeEndl(s));
    }

    public static void printDEB(String s) {
        msgdisp.printDEB(Utils.removeEndl(s));
    }

    public static void printGEN(String s, Color color, boolean underline, boolean bolt) {
        msgdisp.printGEN(Utils.removeEndl(s), color, underline, bolt);
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
        postProcPanel1 = new abinitgui.scriptbib.PostProcPanel();
        jobMonitorPanel1 = new abinitgui.projects.JobMonitorPanel();
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
        mainTabbedPane.addTab("Post-processing", postProcPanel1);
        mainTabbedPane.addTab("Job Monitor", jobMonitorPanel1);

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
    javax.swing.JMenu fileMenu;
    javax.swing.JMenu helpMenu;
    javax.swing.ButtonGroup inputFilebuttonGroup;
    javax.swing.JMenu jMenuClustepAndTB;
    javax.swing.JMenuItem jMenuItem1;
    javax.swing.JMenuItem jMenuItem3;
    javax.swing.JMenuItem jMenuItem4;
    javax.swing.JMenuItem jMenuItemClustep;
    javax.swing.JMenuItem jMenuItemTB;
    abinitgui.projects.JobMonitorPanel jobMonitorPanel1;
    abinitgui.projects.JobPanel jobPanel1;
    javax.swing.ButtonGroup lookAndFeelbuttonGroup;
    javax.swing.JComboBox machineCombo;
    javax.swing.JLabel machineLabel;
    abinitgui.projects.MachinePane machinePane1;
    javax.swing.JMenuBar mainMenuBar;
    javax.swing.JTabbedPane mainTabbedPane;
    javax.swing.JMenuItem outputMSGMenuItem;
    abinitgui.scriptbib.PostProcPanel postProcPanel1;
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
    
    public static PseudoDatabase getRemotePseudoDatabase() 
    {
        return remotePseudoDatabase;
    }
    
    public static PseudoDatabase getLocalPseudoDatabase()
    {
        return localPseudoDatabase;
    }
}
