/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package abinitgui.scriptbib;

import abinitgui.core.LocalExec;
import abinitgui.core.MainFrame;
import abinitgui.core.RetMSG;
import abinitgui.core.Utils;
import abinitgui.projects.Machine;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author yannick
 */
public class PostProcPanel extends javax.swing.JPanel {
    
    
    private DefaultListModel scriptModel;
    private final ScriptBib scriptBibs;
    private final ScriptArgTableModel argsModel;
    private final ScriptFileTableModel outModel;
    private final ScriptFileTableModel inModel;
    private Machine currentMachineForScript;

    /**
     * Creates new form PostProcPanel
     */
    public PostProcPanel() {
        initComponents();
        scriptBibs = new ScriptBib();
        outModel = new ScriptFileTableModel(scriptOutTable);
        
        scriptOutTable.setModel(outModel);
        initTableHeader(scriptOutTable, new String[]{"Name", "Remote ?", "Value"},
                new Integer[]{null, null, null});
        scriptOutTable.setDefaultRenderer(ScriptArgument.class,
                new ScriptArgumentRenderer());
        
        inModel = new ScriptFileTableModel(scriptInTable);
        
        scriptInTable.setModel(inModel);
        initTableHeader(scriptInTable, new String[]{"Name", "Remote ?", "Value"},
                new Integer[]{null, null, null});
        scriptInTable.setDefaultRenderer(ScriptArgument.class,
                new ScriptArgumentRenderer());
        
        argsModel = new ScriptArgTableModel(scriptArgTable);

        scriptArgTable.setModel(argsModel);
        initTableHeader(scriptArgTable, new String[]{"Name", "Value"},
                new Integer[]{null, null});
        scriptArgTable.setDefaultRenderer(ScriptArgument.class,
                new ScriptArgumentRenderer());
    }
    
    public void refresh() {

        /**
         * Script section *
         */
        
        if(Utils.exists("listScripts.yml"))
        {
            try{
                scriptBibs.loadFromFile("listScripts.yml");
            } catch(IOException e)
            {
                MainFrame.printERR("Error while IO listScripts.yml : "+e.getMessage());
            }
        } 
        else if(Utils.exists("listScripts.xml"))
        {
            scriptBibs.loadScriptsFromFile("listScripts.xml");
            MainFrame.printOUT("creating listScripts.yml from your obsolete listScripts.xml");
            try{
                scriptBibs.saveToFile("listScripts.yml");
                scriptBibs.loadFromFile("listScripts.yml");
            } catch(IOException e)
            {
                MainFrame.printERR("Error while IO listScripts.yml : "+e.getMessage());
            }
        }
        else {
            MainFrame.printERR("No file listScripts.yml nor listScripts.xml in the directory: you won't"
                    + " be able to use post-processing tools.");
        }

        scriptModel = new DefaultListModel();
        scriptList.setModel(scriptModel);

        showScripts();
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
    
    
    
    public final void refreshMachines()
    {        
        Machine mymach2 = (Machine)(machineCombo1.getSelectedItem());
        
        DefaultComboBoxModel<Machine> model2 = new DefaultComboBoxModel<>();
        
        for(Machine mach : MainFrame.getMachineDatabase())
        {
            model2.addElement(mach);
        }
        
        machineCombo1.setModel(model2);
        machineCombo1.setSelectedItem(mymach2);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scriptScrollPane = new javax.swing.JScrollPane();
        scriptList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        scriptName = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        scriptDescription = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        scriptArgTable = new abinitgui.scriptbib.ScriptArgTable();
        launchScript = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        scriptOutTable = new abinitgui.scriptbib.ScriptArgTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        scriptProgram = new javax.swing.JTextField();
        openOutput = new javax.swing.JButton();
        editScripts = new javax.swing.JButton();
        reloadScripts = new javax.swing.JButton();
        machineCombo1 = new javax.swing.JComboBox();
        machineLabel1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        scriptInTable = new abinitgui.scriptbib.ScriptArgTable();
        jLabel7 = new javax.swing.JLabel();
        remoteCB = new javax.swing.JCheckBox();

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

        machineLabel1.setText("Select machine on which the simulation runs :");

        scriptInTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(scriptInTable);

        jLabel7.setText("Input files :");

        remoteCB.setText("Run the script remotely ?");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scriptScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(editScripts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(reloadScripts, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scriptName, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scriptProgram, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel6)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(launchScript, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(openOutput))
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(machineLabel1)
                                    .addComponent(remoteCB))
                                .addGap(67, 67, 67)
                                .addComponent(machineCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(machineLabel1)
                            .addComponent(machineCombo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(remoteCB)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(scriptName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(scriptProgram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(launchScript)
                            .addComponent(openOutput)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scriptScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editScripts)
                        .addGap(3, 3, 3)
                        .addComponent(reloadScripts)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void scriptListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_scriptListValueChanged
        boolean adjust = evt.getValueIsAdjusting();
        if(!adjust)
        {
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
                remoteCB.setSelected(scr.runRemote);

                scriptDescription.setText(scr.description.replace("\\n", "\n"));

                scriptProgram.setText(scr.program);
                argsModel.resetScripts();
                outModel.resetScripts();
                inModel.resetScripts();

                ArrayList<ScriptArgument> inArgs = scr.listInput;

                int nbIn = inArgs.size();

                for (int i = 0; i < nbIn; i++) {
                    inModel.addScript(inArgs.get(i));
                }

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
        }
    }//GEN-LAST:event_scriptListValueChanged

    private void launchScriptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_launchScriptActionPerformed

        Runnable r = new Runnable() {
            @Override
            public void run() {

                Machine mach = currentMachineForScript;

                if(mach == null)
                {
                    MainFrame.printERR("Please select a machine first");
                    return;
                }

                int index = scriptList.getSelectedIndex();

                if (index == -1) {
                    MainFrame.printERR("Please select a script before running");
                    return;
                }

                Script scr = scriptBibs.getList().get(index);

                if (scr == null) {
                    MainFrame.printERR("Please select a script before running");
                    return;
                }
                
                String rootPath = mach.getSimulationPath();

                String folder = "scripts";
                
                boolean isLocalMachine = (mach.getType() == Machine.LOCAL_MACHINE);
                boolean isRemoteGatewayMachine = (mach.getType() == Machine.GATEWAY_MACHINE);
                boolean isRemoteAbinitMachine = (mach.getType() == Machine.REMOTE_MACHINE);

                boolean isRunRemote = scr.runRemote;
                isRunRemote = remoteCB.isSelected();
                
                String path = mach.getSimulationPath();
                if (path.equals("")) {
                    path = ".";
                }
                
                if(!mach.isConnected())
                {
                    MainFrame.printOUT("Trying to connect ...");
                    mach.connection();
                }
                
                mach.createTree(path);
                if((mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE))
                {
                    MainFrame.getLocalExec().createTree(path);
                }
                
                String inputFile = scr.fileName;

                String program = scr.program;

                // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

                if ((!isRunRemote || isLocalMachine) && Utils.osName().startsWith("Windows")) {
                    MainFrame.printERR("Scripts are not supported for Windows platform yet");
                    launchScript.setEnabled(true);
                }

                // ********************************************************************************************************************************

                String cwd = "";

                String CMD = "pwd";
                
                LocalExec localExec = MainFrame.getLocalExec();

                RetMSG retmsg;

                if(isRunRemote)
                {
                    retmsg = mach.sendCommand(CMD);
                }
                else
                {
                    retmsg = localExec.sendCommand(CMD);
                }
                
                if (retmsg.getRetCode() == RetMSG.SUCCES) {
                    MainFrame.printOUT("PWD: " + retmsg.getRetMSG());
                    cwd = Utils.removeEndl(retmsg.getRetMSG());
                } else {
                    MainFrame.printERR("Error: " + retmsg.getRetMSG() + " !");
                }

                String inputFileName = Utils.getLastToken(inputFile.replace('\\', '/'), "/");

                // Test de l'existance de inputfile
                if (!Utils.exists(inputFile)) {
                    MainFrame.printERR("The file " + inputFile + " doesn't exist!");
                    launchScript.setEnabled(true);
                    return;
                }

                if (!inputFile.equals("")) {
                    // Will do the computation in rootpath/folder
                    String inputFileR = rootPath + "/" + folder + "/" + inputFileName;
                    // Copy script file
                    if(isRunRemote)
                    {
                        mach.putFile(inputFile+" "+inputFileR);
                        if((isRemoteAbinitMachine || isRemoteGatewayMachine) && Utils.osName().startsWith("Windows"))
                        {
                            mach.sendCommand("dos2unix " + inputFileR);
                        }
                    }
                    else
                    {
                        retmsg = localExec.sendCommand("cp "+inputFile+" "+inputFileR);
                        if (retmsg.getRetCode() != RetMSG.SUCCES) {
                            MainFrame.printERR("Error: " + retmsg.getRetMSG() + "!");
                        }
                    }
                    

                    ArrayList<String> allCommand = new ArrayList<>();
                    ArrayList<ScriptArgument> listArgs = scr.listArgs;
                    allCommand.add(program);
                    allCommand.add(inputFileR);

                    String command = program + " " + inputFileR;

                    for (int i = 0; i < listArgs.size(); i++) {
                        String input = (String) scriptArgTable.getValueAt(i, 1);
                        allCommand.add("--" + listArgs.get(i).name );
                        
                        command = command + " --" + listArgs.get(i).name + " \'" + input + "\'";
                        allCommand.add(input);
                        
                    }

                    System.out.println("CWD = "+cwd);

                    ArrayList<ScriptArgument> listOut = scr.listOutput;

                    for (int i = 0; i < listOut.size(); i++) {
                        String outFile = (String) scriptOutTable.getValueAt(i, 2);
                        
                        String outputFN = Utils.getLastToken(outFile.replace('\\', '/'), "/");
                        
                        allCommand.add("--" + listOut.get(i).name);
                        String outFileR = rootPath + "/" + folder + "/" + outputFN;
                        
                        if(isRunRemote)
                        {
                            if((boolean)(scriptOutTable.getValueAt(i,1))) // Remote file
                            {
                                command = command + " --" + listOut.get(i).name + " \'" + outFile + "\'";
                                allCommand.add(outFile);
                            }
                            else
                            {
                                command = command + " --" + listOut.get(i).name + " \'" + outFileR + "\'";
                                allCommand.add(outFileR);
                            }
                        }
                        else
                        {
                            if((boolean)(scriptOutTable.getValueAt(i,1))) // Remote file
                            {
                                command = command + " --" + listOut.get(i).name + " \'" + outFileR + "\'";
                                allCommand.add(outFileR); // If remote file, we should produce it inside outFileR
                            }
                            else
                            {
                                command = command + " --" + listOut.get(i).name + " \'" + outFile + "\'";
                                allCommand.add(outFile);
                            }
                        }
                    }

                    ArrayList<ScriptArgument> listIn = scr.listInput;

                    for (int i = 0; i < listIn.size(); i++) {
                        String inFile = (String) scriptInTable.getValueAt(i, 2);
                        
                        String inputFN = Utils.getLastToken(inFile.replace('\\', '/'), "/");
                        String inFileR = rootPath + "/" + folder + "/" + inputFN;
                        allCommand.add("--" + listIn.get(i).name);
                        if(isRunRemote)
                        {
                            if( (boolean) scriptInTable.getValueAt(i,1) ) // Remote file, give directly correct name !
                            {
                                command = command + " --" + listIn.get(i).name + " \'" + inFile + "\'";
                                allCommand.add(inFile); 
                            }
                            else
                            {
                                mach.putFile(inFile + " " + inFileR);
                                command = command + " --" + listIn.get(i).name + " \'" + inFileR + "\'";
                                allCommand.add(inFileR);
                            }
                        }
                        else
                        {
                            if( (boolean) scriptInTable.getValueAt(i,1) ) // Remote file
                            {
                                mach.getFile(inFile+" "+inFileR);
                                command = command + " --" + listIn.get(i).name + " \'" + inFileR + "\'";
                                allCommand.add(inFileR);
                            }
                            else
                            {
                                command = command + " --" + listIn.get(i).name + " \'" + inFile + "\'";
                                allCommand.add(inFile);
                            }
                        }
                    }

                    String[] arrayCMD = allCommand.toArray(new String[0]);
                    if(isRunRemote)
                    {
                        retmsg = mach.sendCommand(command);
                    }
                    else
                    {
                        retmsg = localExec.sendCommand(arrayCMD);
                    }
                    if (retmsg.getRetCode() == RetMSG.SUCCES) {
                        MainFrame.printOUT("Script output : \n"+retmsg.getRetMSG());
                    } else {
                        MainFrame.printERR("Error: " + retmsg.getRetMSG() + "!");
                    }
                    
                    MainFrame.printDEB(command);
                    
                    for (int i = 0; i < listOut.size(); i++) {
                        String outFile = (String) scriptOutTable.getValueAt(i, 2);
                        
                        String outputFN = Utils.getLastToken(outFile.replace('\\', '/'), "/");
                        
                        allCommand.add("--" + listOut.get(i).name);
                        String outFileR = rootPath + "/" + folder + "/" + outputFN;
                        if(isRunRemote)
                        {
                            if((boolean)scriptOutTable.getValueAt(i,1)) // Remote file
                            {
                                // Since remote, we don't want to get it back ! It is already good !
                            }
                            else
                            {
                                mach.getFile(outFileR + " " + outFile);
                            }
                        }
                        else
                        {
                            if((boolean)scriptOutTable.getValueAt(i,1)) // Remote file
                            {
                                // Since remote, we want to send it to the cluster !
                                mach.putFile(outFileR + " " + outFile);
                            }
                            else
                            {
                                // File is already there !
                            }
                        }
                    }

                }

            }
        };

        Thread t = new Thread(r);
        t.start();
    }//GEN-LAST:event_launchScriptActionPerformed

    private void scriptProgramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scriptProgramActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_scriptProgramActionPerformed

    private void openOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openOutputActionPerformed

        Machine mach = currentMachineForScript;
        if(mach == null)
        {
            MainFrame.printERR("Please select a machine first");
            return;
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
        
        if(!mach.isConnected())
        {
            mach.connection();
        }
        
        String folder = "scripts";

        // Open files
        for (int i = 0; i < listOut.size(); i++) {
            String outFile = (String) scriptOutTable.getValueAt(i, 2);
            
            String outputFN = Utils.getLastToken(outFile.replace('\\', '/'), "/");
            String outFileR = rootPath + "/" + folder + "/" + outputFN;
            
            boolean isRemote = (boolean) scriptOutTable.getValueAt(i,1);
            if(isRemote)
            {
                // Remote file
                mach.getFile(outFile+" "+outFileR);
                outFile = outFileR;
            }
            
            // Now we have outFile locally

            File f = new File(rootPath);

            if (!f.exists()) {
                MainFrame.getLocalExec().createTree(rootPath);
                f = new File(rootPath);
            }

            String abs = "";
            try {
                abs = f.getCanonicalPath();
            } catch (IOException ex) {
                MainFrame.printERR("Unable to get output file canonical path");
                return;
            }

            f = new File(outFile);
            
            MainFrame.printOUT("Trying to open file : " + outFile);
            if (f.exists()) {
                try {
                    if (Desktop.isDesktopSupported()) {
                        if (Utils.osName().contains("Windows")) {
                            if (outFile.endsWith(".txt") || outFile.endsWith(".dbs") || outFile.endsWith(".agr")
                                || outFile.endsWith(".files") || outFile.endsWith(".in") || outFile.endsWith(".out")
                                || outFile.endsWith(".sh")) {
                                MainFrame.editFile(outFile, true);
                            } else {
                                Desktop.getDesktop().open(f);
                            }
                        } else {
                            Desktop.getDesktop().open(f);
                        }
                    } else {
                        MainFrame.printOUT("Not able to open the file with the default editor. Use basic editor instead.");
                        MainFrame.editFile(outFile, true);
                    }
                } catch (IOException ex) {
                    MainFrame.printOUT("Not able to open the file with the default editor. Use editor instead.");
                    MainFrame.editFile(outFile, true);
                }
            } else {
                MainFrame.printERR("Please execute the script before opening the output files.");
            }

        }
    }//GEN-LAST:event_openOutputActionPerformed

    private void editScriptsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editScriptsActionPerformed
        int idScript = scriptList.getSelectedIndex();

        if (idScript == -1) {
            MainFrame.printERR("Please select a script first!");
        } else {
            Script scr = scriptBibs.getList().get(idScript);

            String inputFile = scr.fileName;

            if (Utils.osName().startsWith("Windows")) {
                Utils.unix2dos(new File(inputFile));
            }
            MainFrame.editFile(inputFile, true);
        }
    }//GEN-LAST:event_editScriptsActionPerformed

    private void reloadScriptsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reloadScriptsActionPerformed
        scriptBibs.getList().clear();
        scriptBibs.loadScriptsFromFile("listScripts.xml");
        showScripts();
    }//GEN-LAST:event_reloadScriptsActionPerformed

    private void machineCombo1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_machineCombo1ItemStateChanged

    }//GEN-LAST:event_machineCombo1ItemStateChanged

    private void machineCombo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_machineCombo1ActionPerformed
        Machine mach = (Machine)machineCombo1.getSelectedItem();
        if(mach != null)
        {
            this.currentMachineForScript = mach;
        }
    }//GEN-LAST:event_machineCombo1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton editScripts;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton launchScript;
    private javax.swing.JComboBox machineCombo1;
    private javax.swing.JLabel machineLabel1;
    private javax.swing.JButton openOutput;
    private javax.swing.JButton reloadScripts;
    private javax.swing.JCheckBox remoteCB;
    private javax.swing.JTable scriptArgTable;
    private javax.swing.JTextArea scriptDescription;
    private javax.swing.JTable scriptInTable;
    private javax.swing.JList scriptList;
    private javax.swing.JLabel scriptName;
    private javax.swing.JTable scriptOutTable;
    private javax.swing.JTextField scriptProgram;
    private javax.swing.JScrollPane scriptScrollPane;
    // End of variables declaration//GEN-END:variables
}
