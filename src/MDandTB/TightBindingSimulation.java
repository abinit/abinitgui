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

package MDandTB;

import core.Atom;
import core.Exec;
import core.MainFrame;
import core.RetMSG;
import core.Utils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JButton;
import projects.LocalMachine;
import projects.Machine;
import projects.RemoteJob;
import projects.RemoteJob;
import projects.Simulation;
import projects.SubmissionScript;

public class TightBindingSimulation extends Simulation {

    public final static int TB_DOSKNEE1AND2 = 101, TB_DOSKNEE3 = 102, TB_BNDSTR = 103;
    
    private String name = "default";
    private String tbenerFileName = "tbener";
    private RemoteJob job;
    public static int RUNNING = 1;
    public static int FINISHED = 2;
    public static int READY = 0;
    private int status;
    private boolean sendTBSources;
    private boolean compileTB;
    private boolean graphite;
    private int typeDosknee; // 1 or 2
    private boolean useLANDM;
    private int L, M;
    private int typeGlobal;
    private boolean use66, use110, use120;

    public TightBindingSimulation() {
        status = READY;

        job = new RemoteJob();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getInputFileName() {
        return getTbenerFileName();
    }

    @Override
    public void setInputFileName(String fileName) {
        this.setTbenerFileName(fileName);
    }

    @Override
    public String toString() {
        return name;
        //return "Simulation(name = " + name + "; fileName = " + tbenerFileName + ")";
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }
    
    @Override
    public RemoteJob getRemoteJob() {
        return job;
    }

    @Override
    public void setRemoteJob(RemoteJob job) {
        this.job = job;
    }

    void updateStatus() {
        job.updateStatus();
    }
    
    @Override
    public void createFileTree(Machine mach, MainFrame mf)
    {
        String path = mach.getSimulationPath();
        if (path.equals("")) {
            path = ".";
        }
        mach.createTree(path, mf);
        if(mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE)
        {
            mf.getLocalExec().createTree(path);
        }
        
    }
    
    @Override
    public boolean submit(MainFrame mf)
    {
            
        if(this.typeGlobal == TB_DOSKNEE1AND2)
        {
            Machine mach = mf.getMachineDatabase().getMachine(job.getMachineName());

            if(mach == null)
            {
                mf.printERR("No machine selected for this simulation");
                return false;
            }

            if(!mach.isConnected())
            {
                mach.connection(mf);
            }

            if (mach.getType() == Machine.LOCAL_MACHINE && Utils.osName().startsWith("Windows")) {
                mf.printERR("Please connect to a remote host before submitting a simulation on Windows platform !");
                return false;
            }

            this.createFileTree(mach, mf);

            String rootPath = mach.getSimulationPath();

            SubmissionScript script = getRemoteJob().getScript();

            String TBfolderL = "Tight-Binding";

            String TBrootR = "./Tight-Binding_src/DOS";
            String TBrootL = TBfolderL + "/DOS";

            String cwd = "";

            String CMD = "pwd";

            RetMSG retmsg = mach.sendCommand(CMD, mf);
            if(retmsg.getRetCode() == RetMSG.SUCCES)
            {
                mf.printOUT("PWD: " + retmsg.getRetMSG());
                cwd = Utils.removeEndl(retmsg.getRetMSG());
            }
            else
            {
                mf.printERR("Not able to get working directory !");
                return false;
            }

            // ***************************************************************
            // Creation du dossier Tight-Binding local
            mf.getLocalExec().mkdir(rootPath + "/" + TBfolderL);
            // Creation du dossier DOS dans Tight-Binding local
            mf.getLocalExec().mkdir(rootPath + "/" + TBrootL);
            // ***************************************************************



            // ***************************************************************

            //                String sep = Utils.fileSeparator();

            String simName = name.replace(" ", "_");

            if (sendTBSources) {

                if(mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE)
                {
                    // Envoie du code source pour les calculs TB (remote ou local)
                    mach.putFile("./Tight-Binding_src.tar.gz ./Tight-Binding_src.tar.gz", mf);
                }
                
                // Unzip the compressed file CLUSTEP_src.tar.gz
                mach.sendCommand("tar -zxf ./Tight-Binding_src.tar.gz", mf);

                if (mach.getType() == Machine.GATEWAY_MACHINE || mach.getType() == Machine.REMOTE_MACHINE) {
                    mach.sendCommand("rm -f ./Tight-Binding_src.tar.gz", mf);
                }
            }

            int compile;
            if (compileTB) {
                compile = 1;
            } else {
                compile = 0;
            }

            int option = 1;
            option = typeDosknee;

            //**************

            String L_str = "3";
            String M_str = "3";
            try {
                if (useLANDM) {
                    L_str = "" + L;
                    M_str = "" + M;
                }
                else
                {
                    L_str = "-1";
                    if (graphite) 
                    {
                        M_str = "graphite";
                    }
                    else
                    {
                        M_str = "pentahept";
                    }
                }
            } catch (Exception e) {
                mf.printDEB("L = 5, M = 5 (default taken!)");
                L_str = "5";
                M_str = "5";
            }

            script.setAbinitPath("bash " + "execDOS.sh" + " " + compile + " " + L_str + " " + M_str + " " + option + "");
            script.setInputPath("/dev/null");
            script.setLogPath(simName + ".log");

            script.setPreProcessPart("cd "+TBrootR);

            if (script.getSystem().equals("SGE")) {
                String PBSfileName = rootPath + "/" + TBrootL + "/" + simName + ".SGE.sh";
                script.writeToFile(PBSfileName);
            } else if (script.getSystem().equals("SLURM")) {
                String PBSfileName = rootPath + "/" + TBrootL + "/" + simName + ".SLURM.sh";
                script.writeToFile(PBSfileName);
            } else if (script.getSystem().equals("Frontend")) {
                String SHfileName = rootPath + "/" + TBrootL + "/" + simName + ".sh";
                script.writeToFile(SHfileName);
            }

            if (script.getSystem().equals("SGE")) {
                String sgeSHFile = rootPath + "/" + TBrootL + "/" + simName + ".SGE.sh";
                String sgeSHFileR = rootPath + "/" + TBrootL + "/" + simName + ".SGE.sh";
                if (mach.getType() == Machine.GATEWAY_MACHINE || mach.getType() == Machine.REMOTE_MACHINE) {

                    // Envoie du fichier SGE
                    mach.putFile(sgeSHFile + " " + sgeSHFileR, mf);

                    if (Utils.osName().startsWith("Windows")) {
                        mach.sendCommand("dos2unix " + sgeSHFileR, mf);
                    }
                }
                // lancement des commandes d'exécution de la simulation
                mach.sendCommand("qsub " + sgeSHFileR, mf);
            } else if (script.getSystem().equals("Frontend")) {
                String SHFile = rootPath + "/" + TBrootL + "/" + simName + ".sh";
                String SHFileR = rootPath + "/" + TBrootL + "/" + simName + ".sh";

                if (mach.getType() == Machine.GATEWAY_MACHINE || mach.getType() == Machine.REMOTE_MACHINE) {
                    /*if (Utils.osName().startsWith("Windows")) {
                     Utils.dos2unix(new File(SHFileR));
                     }*/
                    // Envoie du fichier BASH
                    mach.putFile(SHFile + " " + SHFileR, mf);

                    if (Utils.osName().startsWith("Windows")) {
                        mach.sendCommand("dos2unix " + SHFileR, mf);
                    }
                }
                // lancement des commandes d'exécution de la simulation
                mach.sendCommand("bash "+SHFileR, mf);
            } else if (script.getSystem().equals("SLURM")) {
                String slurmSHFile = rootPath + "/" + TBrootL + "/" + simName + ".SLURM.sh";
                String slurmSHFileR = rootPath + "/" + TBrootL + "/" + simName + ".SLURM.sh";
                if (mach.getType() == Machine.GATEWAY_MACHINE || mach.getType() == Machine.REMOTE_MACHINE) {

                    // Envoie du fichier SGE
                    mach.putFile(slurmSHFile + " " + slurmSHFileR, mf);

                    if (Utils.osName().startsWith("Windows")) {
                        mach.sendCommand("dos2unix " + slurmSHFileR, mf);
                    }
                }
                // lancement des commandes d'exécution de la simulation
                mach.sendCommand("sbatch " + slurmSHFileR, mf);
            }

            if (mach.getType() == Machine.LOCAL_MACHINE) {
                mf.printOUT("The simulation was submitted to the local Tight-Binding server.");
            } else {
                mf.printOUT("The simulation was submitted to the remote Tight-Binding server " + mach.getName());
            }
            mf.printDEB("The submission thread ended successfully! (Tight-Binding)");
        }
                
        
        
        /**
         * DOSKNEE 3
         * JButton sendSIM = sendSIMDosknee3Button;

                if (mf.localAbinitRadioButton().isSelected() && Utils.osName().startsWith("Windows")) {
                    mf.printERR("Please connect to a remote Tight-Binding host before submitting a simulation !");
                    sendSIM.setEnabled(true);
                    return;
                }

                if ((mf.remoteGatewayRadioButton().isSelected() || mf.remoteAbinitRadioButton().isSelected()) && mf.remoteExec == null) {
                    mf.printERR("Please connect to a Tight-Binding host before submitting a simulation !");
                    sendSIM.setEnabled(true);
                    return;
                }

                mf.createFiletree();

                String rootPath = mf.mySimulationsTextField().getText();

                String TBfolderL = "Tight-Binding";

                String TBrootR = "./Tight-Binding_src/DOS";
                String TBrootL = TBfolderL + "/DOS";

                // ***************************************************************
                // Creation du dossier Tight-Binding local
                mf.mkdir(rootPath + "/" + TBfolderL);
                // Creation du dossier DOS dans Tight-Binding local
                mf.mkdir(rootPath + "/" + TBrootL);
                // ***************************************************************

                String cwd = "";

                String CMD = "pwd";

                RetMSG retmsg;
                if (mf.remoteGatewayRadioButton().isSelected() || mf.remoteAbinitRadioButton().isSelected()) {
                    if (mf.remoteExec != null) {
                        retmsg = mf.remoteExec.sendCommand(CMD);
                        if (retmsg.getRetCode() == RetMSG.SUCCES) {
                            mf.printOUT("PWD: " + retmsg.getRetMSG());
                            cwd = mf.removeEndl(retmsg.getRetMSG());
                        } else {
                            //printERR("Error (RetVal = " + retmsg.getRetCode() + "): " + retmsg.getRetMSG());
                            mf.printERR("Error: " + retmsg.getRetMSG() + " !");
                        }
                    } else {
                        mf.printERR("First connect to an CLUSTEP host please !");
                    }
                } else if (mf.localAbinitRadioButton().isSelected()) {
                    if (mf.localExec != null) {
                        retmsg = mf.localExec.sendCommand(CMD);
                        if (retmsg.getRetCode() == RetMSG.SUCCES) {
                            mf.printOUT("PWD: " + retmsg.getRetMSG());
                            cwd = mf.removeEndl(retmsg.getRetMSG());
                        } else {
                            //printERR("Error (RetVal = " + retmsg.getRetCode() + "): " + retmsg.getRetMSG());
                            mf.printERR("Error: " + retmsg.getRetMSG() + " !");
                        }
                    }
                } else { // Le choix n'a pas été fait
                    mf.printERR("Choose a destination option please at config. tab !");
                }

                // ***************************************************************

                //                String sep = Utils.fileSeparator();

                String simName = "DOScalc";

                if (sendTBCheckBox.isSelected()) {

                    // Envoie du code source pour les calculs TB (remote ou local)
                    mf.putFile("./Tight-Binding_src.tar.gz ./Tight-Binding_src.tar.gz");

                    // Unzip the compressed file CLUSTEP_src.tar.gz
                    mf.sendCommand("tar -zxf ./Tight-Binding_src.tar.gz");

                    if (mf.remoteGatewayRadioButton().isSelected()
                        || mf.remoteAbinitRadioButton().isSelected()) {
                        mf.sendCommand("rm -f ./Tight-Binding_src.tar.gz");
                    }
                }

                int compile;
                if (compileTBCheckBox.isSelected()) {
                    compile = 1;
                } else {
                    compile = 0;
                }

                //**************

                int L = 3, M = 3;
                String L_str = "3";
                String M_str = "3";
                try {
                    L = Integer.parseInt(LTextField2.getText());
                    M = Integer.parseInt(MTextField2.getText());
                    L_str = "" + L;
                    M_str = "" + M;
                } catch (Exception e) {
                    mf.printDEB("L = 5, M = 5 (default taken!)");
                    L_str = "5";
                    M_str = "5";
                }

                //**************

                int nbProc;
                if (CD.abinitParaTextField().isEnabled()) { // Tjs actif pour cette version du gui
                    try {
                        nbProc = Integer.parseInt(CD.abinitParaTextField().getText());
                    } finally {
                        mf.printDEB("Please set up the number of processors to use ! nbProc was set to 1!");
                        nbProc = 1;
                    }
                } else {
                    nbProc = 1;
                }

                if (CD.needSGECheckBox().isSelected()) {
                    int time, nodes, ram, hdm;
                    String email;
                    try {
                        time = Integer.parseInt(CD.timeTextField().getText());
                        nodes = Integer.parseInt(CD.nodesTextField().getText());
                        ram = Integer.parseInt(CD.ramTextField().getText());
                        hdm = Integer.parseInt(CD.hdmTextField().getText());
                        email = CD.emailTextField().getText();
                    } catch (Exception e) {
                        //printERR("Exception in sendSIMButtonActionPerformed:" + e + "");
                        mf.printERR("The SGE script configurations are probably wrong !");
                        sendSIM.setEnabled(true);
                        return;
                    }
                    // Création du script SGE
                    try {
                        String PBSfileName = rootPath + "/" + TBrootL + "/" + simName + ".SGE.sh";
                        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(PBSfileName), mf.CharSet);
                        //FileWriter fw = new FileWriter(PBSfileName);
                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter pw = new PrintWriter(bw);

                        //*********************************************************************************************
                        String fileContent = "#!/bin/bash" + "\n"
                        + "#" + "\n"
                        + "# On old Green node" + "\n"
                        + "#$ -l nb=false" + "\n"
                        + "#" + "\n"
                        + "# Ask for pe=parrallel environment, snode or openmpi" + "\n"
                        + "# snode= same node, as the shared memory communication is the fastest" + "\n"
                        + "#$ -pe openmpi 1" + "\n"
                        + "# -pe snode8 8" + "\n"
                        + "\n"
                        + "# keep current working directory" + "\n"
                        + "#$ -cwd" + "\n"
                        + "\n"
                        + "#$ -o SGE_out-$JOB_ID.log" + "\n"
                        + "#$ -e SGE_err-$JOB_ID.log" + "\n"
                        + "\n"
                        + "# give a name to your job" + "\n"
                        + "#$ -N " + simName + "\n"
                        + "\n"
                        + "# keep all the defined variables" + "\n"
                        + "#$ -V" + "\n"
                        + "#$ -l nb=false" + "\n"
                        + "\n"
                        + "# not mandatory: highmem=true (hm=true) for 32GB node" + "\n"
                        + "# or hm=false for 16GB node" + "\n"
                        + "# no hm argument does not take about the kind of node ram (16/32)" + "\n"
                        + "# -l hm=true" + "\n"
                        + "\n"
                        + "# IMPORTANT: You need to specify the mem_free" + "\n"
                        + "# h_vmem can also be set but mf is mandatory!" + "\n"
                        + "# max 31G if hm=true and max 15G if hm=false" + "\n"
                        + "#$ -l mf=" + ram + "M" + "\n"
                        + "\n"
                        + "# Specify the requested time" + "\n"
                        + "#$ -l h_rt=" + time + ":00:00" + "\n"
                        + "\n"
                        + "# To be informed by email (besa= begin,end,stop,abort)" + "\n"
                        + "#$ -M " + email + "\n"
                        + "#$ -m besa" + "\n"
                        //+ "# ---------------------------" + "\n"
                        + "\n"
                        + "echo \"Simulation " + simName + " is running!\"" + "\n"
                        + "cd " + TBrootR + "\n"
                        + "bash " + "execDOS.sh" + " " + compile + " " + L_str + " " + M_str + " 3 >& DOS.log";

                        pw.print(fileContent);
                        //*********************************************************************************************

                        pw.println();
                        pw.close();
                        bw.close();
                        fw.close();
                    } catch (IOException e) {
                        //printERR("Exception in sendSIMButtonActionPerformed:" + e + "");
                        mf.printERR("The SGE script could not be created !");
                        sendSIM.setEnabled(true);
                        return;
                    }
                } else {
                    // Création du script BASH
                    try {
                        String BASHfileName = rootPath + "/" + TBrootL + "/" + simName + ".sh";

                        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(BASHfileName), mf.CharSet);
                        //FileWriter fw = new FileWriter(BASHfileName);

                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter pw = new PrintWriter(bw);
                        pw.println("#!/bin/bash");
                        pw.println("echo \"Simulation " + simName + " is running!\"");
                        pw.println("cd " + TBrootR);
                        pw.print("bash " + "execDOS.sh" + " " + compile + " " + L_str + " " + M_str + " 3 >& DOS.log");
                        pw.println();
                        pw.close();
                        bw.close();
                        fw.close();
                    } catch (IOException e) {
                        //printDEB("Exception in sendSIMButtonActionPerformed:" + e + "");
                        mf.printERR("The bash script could not be created !");
                        sendSIM.setEnabled(true);
                        return;
                    }
                }

                if (CD.needSGECheckBox().isSelected()) {
                    if (mf.remoteGatewayRadioButton().isSelected() || mf.remoteAbinitRadioButton().isSelected()) {
                        String sgeSHFile = rootPath + "/" + TBrootL + "/" + simName + ".SGE.sh";
                        String sgeSHFileR = TBrootR + "/" + simName + ".SGE.sh";
                        if (Utils.osName().startsWith("Windows")) {
                            Utils.dos2unix(new File(sgeSHFile));
                        }
                        // Envoie du fichier SGE
                        mf.putFile(sgeSHFile + " " + sgeSHFileR);
                    }
                    // lancement des commandes d'exécution de la simulation
                    mf.sendCommand("qsub " + TBrootR + "/" + simName + ".SGE.sh");
                } else {
                    String SHFile = rootPath + "/" + TBrootL + "/" + simName + ".sh";
                    String SHFileR = TBrootR + "/" + simName + ".sh";
                    if (mf.remoteGatewayRadioButton().isSelected() || mf.remoteAbinitRadioButton().isSelected()) {
                        if (Utils.osName().startsWith("Windows")) {
                            Utils.dos2unix(new File(SHFile));
                        }
                        // Envoie du fichier BASH
                        mf.putFile(SHFile + " " + SHFileR);
                    }
                    // lancement des commandes d'exécution de la simulation
                    mf.sendCommand("bash " + SHFileR);
                }

                if (mf.localAbinitRadioButton().isSelected()) {
                    mf.printOUT("The simulation was submitted to the local Tight-Binding server.");
                } else {
                    mf.printOUT("The simulation was submitted to the remote Tight-Binding server " + mf.hostTextField().getText());
                    if (mf.remoteGatewayRadioButton().isSelected()) {
                        mf.printOUT(" via the gateway " + mf.gatewayHostTextField().getText() + ".");
                    } else {
                        //printOUT(".");
                    }
                }
                mf.printDEB("The submission thread ended successfully! (Tight-Binding)");
                sendSIM.setEnabled(true);
         */
        
        
        /**
         * BNDSTR
         * 
         * JButton sendSIM = sendSIMBNDSTRButton;

                if (mf.localAbinitRadioButton().isSelected() && Utils.osName().startsWith("Windows")) {
                    mf.printERR("Please connect to a remote Tight-Binding host before submitting a simulation !");
                    sendSIM.setEnabled(true);
                    return;
                }

                if ((mf.remoteGatewayRadioButton().isSelected() || mf.remoteAbinitRadioButton().isSelected()) && mf.remoteExec == null) {
                    mf.printERR("Please connect to a Tight-Binding host before submitting a simulation !");
                    sendSIM.setEnabled(true);
                    return;
                }

                mf.createFiletree();

                String rootPath = mf.mySimulationsTextField().getText();

                String TBfolderL = "Tight-Binding";

                String TBrootR = "./Tight-Binding_src/BS";
                String TBrootL = TBfolderL + "/BS";

                // ***************************************************************
                // Creation du dossier Tight-Binding local
                mf.mkdir(rootPath + "/" + TBfolderL);
                // Creation du dossier BS dans Tight-Binding local
                mf.mkdir(rootPath + "/" + TBrootL);
                // ***************************************************************

                String cwd = "";

                String CMD = "pwd";

                RetMSG retmsg;
                if (mf.remoteGatewayRadioButton().isSelected() || mf.remoteAbinitRadioButton().isSelected()) {
                    if (mf.remoteExec != null) {
                        retmsg = mf.remoteExec.sendCommand(CMD);
                        if (retmsg.getRetCode() == RetMSG.SUCCES) {
                            mf.printOUT("PWD: " + retmsg.getRetMSG());
                            cwd = mf.removeEndl(retmsg.getRetMSG());
                        } else {
                            //printERR("Error (RetVal = " + retmsg.getRetCode() + "): " + retmsg.getRetMSG());
                            mf.printERR("Error: " + retmsg.getRetMSG() + " !");
                        }
                    } else {
                        mf.printERR("First connect to an CLUSTEP host please !");
                    }
                } else if (mf.localAbinitRadioButton().isSelected()) {
                    if (mf.localExec != null) {
                        retmsg = mf.localExec.sendCommand(CMD);
                        if (retmsg.getRetCode() == RetMSG.SUCCES) {
                            mf.printOUT("PWD: " + retmsg.getRetMSG());
                            cwd = mf.removeEndl(retmsg.getRetMSG());
                        } else {
                            //printERR("Error (RetVal = " + retmsg.getRetCode() + "): " + retmsg.getRetMSG());
                            mf.printERR("Error: " + retmsg.getRetMSG() + " !");
                        }
                    }
                } else { // Le choix n'a pas été fait
                    mf.printERR("Choose a destination option please at config. tab !");
                }

                // ***************************************************************

                String sep = Utils.fileSeparator();

                String inputFile = openTBbndstrInputFileTextField.getText();

                String inputFileName = Utils.getLastToken(inputFile.replace('\\', '/'), "/");

                // Test de l'existance de inputfile
                if (!Utils.exists(inputFile)) {
                    mf.printERR("The file " + inputFile + " doesn't exist !");
                    sendSIM.setEnabled(true);
                    return;
                }

                String simName = "BScalc";

                if (sendTBCheckBox.isSelected()) {

                    // Envoie du code source pour les calculs TB (remote ou local)
                    mf.putFile("./Tight-Binding_src.tar.gz ./Tight-Binding_src.tar.gz");

                    // Unzip the compressed file CLUSTEP_src.tar.gz
                    mf.sendCommand("tar -zxf ./Tight-Binding_src.tar.gz");

                    if (mf.remoteGatewayRadioButton().isSelected()
                        || mf.remoteAbinitRadioButton().isSelected()) {
                        mf.sendCommand("rm -f ./Tight-Binding_src.tar.gz");
                    }
                }

                int compile;
                if (compileTBCheckBox.isSelected()) {
                    compile = 1;
                } else {
                    compile = 0;
                }

                int option = 1;
                if (jRB1.isSelected()) {
                    option = 1;
                }
                if (jRB2.isSelected()) {
                    option = 2;
                }
                if (jRB3.isSelected()) {
                    option = 3;
                }

                if (!inputFile.equals("")) {
                    int nbProc;
                    if (CD.abinitParaTextField().isEnabled()) { // Tjs actif pour cette version du gui
                        try {
                            nbProc = Integer.parseInt(CD.abinitParaTextField().getText());
                        } finally {
                            mf.printDEB("Please set up the number of processors to use ! nbProc was set to 1!");
                            nbProc = 1;
                        }
                    } else {
                        nbProc = 1;
                    }

                    if (CD.needSGECheckBox().isSelected()) {
                        int time, nodes, ram, hdm;
                        String email;
                        try {
                            time = Integer.parseInt(CD.timeTextField().getText());
                            nodes = Integer.parseInt(CD.nodesTextField().getText());
                            ram = Integer.parseInt(CD.ramTextField().getText());
                            hdm = Integer.parseInt(CD.hdmTextField().getText());
                            email = CD.emailTextField().getText();
                        } catch (Exception e) {
                            //printERR("Exception in sendSIMButtonActionPerformed:" + e + "");
                            mf.printERR("The SGE script configurations are probably wrong !");
                            sendSIM.setEnabled(true);
                            return;
                        }
                        // Création du script SGE
                        try {
                            String PBSfileName = rootPath + "/" + TBrootL + "/" + simName + ".SGE.sh";
                            OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(PBSfileName), mf.CharSet);
                            //FileWriter fw = new FileWriter(PBSfileName);
                            BufferedWriter bw = new BufferedWriter(fw);
                            PrintWriter pw = new PrintWriter(bw);

                            //*********************************************************************************************
                            String fileContent = "#!/bin/bash" + "\n"
                            + "#" + "\n"
                            + "# On old Green node" + "\n"
                            + "#$ -l nb=false" + "\n"
                            + "#" + "\n"
                            + "# Ask for pe=parrallel environment, snode or openmpi" + "\n"
                            + "# snode= same node, as the shared memory communication is the fastest" + "\n"
                            + "#$ -pe openmpi 1" + "\n"
                            + "# -pe snode8 8" + "\n"
                            + "\n"
                            + "# keep current working directory" + "\n"
                            + "#$ -cwd" + "\n"
                            + "\n"
                            + "#$ -o SGE_out-$JOB_ID.log" + "\n"
                            + "#$ -e SGE_err-$JOB_ID.log" + "\n"
                            + "\n"
                            + "# give a name to your job" + "\n"
                            + "#$ -N " + simName + "\n"
                            + "\n"
                            + "# keep all the defined variables" + "\n"
                            + "#$ -V" + "\n"
                            + "#$ -l nb=false" + "\n"
                            + "\n"
                            + "# not mandatory: highmem=true (hm=true) for 32GB node" + "\n"
                            + "# or hm=false for 16GB node" + "\n"
                            + "# no hm argument does not take about the kind of node ram (16/32)" + "\n"
                            + "# -l hm=true" + "\n"
                            + "\n"
                            + "# IMPORTANT: You need to specify the mem_free" + "\n"
                            + "# h_vmem can also be set but mf is mandatory!" + "\n"
                            + "# max 31G if hm=true and max 15G if hm=false" + "\n"
                            + "#$ -l mf=" + ram + "M" + "\n"
                            + "\n"
                            + "# Specify the requested time" + "\n"
                            + "#$ -l h_rt=" + time + ":00:00" + "\n"
                            + "\n"
                            + "# To be informed by email (besa= begin,end,stop,abort)" + "\n"
                            + "#$ -M " + email + "\n"
                            + "#$ -m besa" + "\n"
                            //+ "# ---------------------------" + "\n"
                            + "\n"
                            + "echo \"Simulation " + simName + " is running!\"" + "\n"
                            + "cd " + TBrootR + "\n"
                            + "bash " + "execBS.sh" + " " + compile + " " + inputFileName + " " + option + " >& BS.log";

                            pw.print(fileContent);
                            //*********************************************************************************************

                            pw.println();
                            pw.close();
                            bw.close();
                            fw.close();
                        } catch (IOException e) {
                            //printERR("Exception in sendSIMButtonActionPerformed:" + e + "");
                            mf.printERR("The SGE script could not be created !");
                            sendSIM.setEnabled(true);
                            return;
                        }
                    } else {
                        // Création du script BASH
                        try {
                            String BASHfileName = rootPath + "/" + TBrootL + "/" + simName + ".sh";

                            OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(BASHfileName), mf.CharSet);
                            //FileWriter fw = new FileWriter(BASHfileName);

                            BufferedWriter bw = new BufferedWriter(fw);
                            PrintWriter pw = new PrintWriter(bw);
                            pw.println("#!/bin/bash");
                            pw.println("echo \"Simulation " + simName + " is running!\"");
                            pw.println("cd " + TBrootR);
                            pw.print("bash " + "execBS.sh" + " " + compile + " " + inputFileName + " " + option + " >& BS.log");
                            pw.println();
                            pw.close();
                            bw.close();
                            fw.close();
                        } catch (IOException e) {
                            //printDEB("Exception in sendSIMButtonActionPerformed:" + e + "");
                            mf.printERR("The bash script could not be created !");
                            sendSIM.setEnabled(true);
                            return;
                        }
                    }

                    // Envoie (copie) du fichier d'input *******************************************************************
                    String inputFileR = TBrootR + "/" + inputFileName;
                    if (mf.remoteGatewayRadioButton().isSelected() || mf.remoteAbinitRadioButton().isSelected()) {
                        if (Utils.osName().startsWith("Windows")) {
                            Utils.dos2unix(new File(inputFile));
                        }
                    }
                    mf.putFile(inputFile + " " + inputFileR);

                    if (CD.needSGECheckBox().isSelected()) {
                        if (mf.remoteGatewayRadioButton().isSelected() || mf.remoteAbinitRadioButton().isSelected()) {
                            String sgeSHFile = rootPath + "/" + TBrootL + "/" + simName + ".SGE.sh";
                            String sgeSHFileR = TBrootR + "/" + simName + ".SGE.sh";
                            if (Utils.osName().startsWith("Windows")) {
                                Utils.dos2unix(new File(sgeSHFile));
                            }
                            // Envoie du fichier SGE
                            mf.putFile(sgeSHFile + " " + sgeSHFileR);
                        }
                        // lancement des commandes d'exécution de la simulation
                        mf.sendCommand("qsub " + TBrootR + "/" + simName + ".SGE.sh");
                    } else {
                        String SHFile = rootPath + "/" + TBrootL + "/" + simName + ".sh";
                        String SHFileR = TBrootR + "/" + simName + ".sh";
                        if (mf.remoteGatewayRadioButton().isSelected() || mf.remoteAbinitRadioButton().isSelected()) {
                            if (Utils.osName().startsWith("Windows")) {
                                Utils.dos2unix(new File(SHFile));
                            }
                            // Envoie du fichier BASH
                            mf.putFile(SHFile + " " + SHFileR);
                        }
                        // lancement des commandes d'exécution de la simulation
                        mf.sendCommand("bash " + SHFileR);
                    }
                } else {
                    mf.printERR("Please setup the inputfile textfield !");
                    sendSIM.setEnabled(true);
                    return;
                }

                if (mf.localAbinitRadioButton().isSelected()) {
                    mf.printOUT("The simulation was submitted to the local Tight-Binding server.");
                } else {
                    mf.printOUT("The simulation was submitted to the remote Tight-Binding server " + mf.hostTextField().getText());
                    if (mf.remoteGatewayRadioButton().isSelected()) {
                        mf.printOUT(" via the gateway " + mf.gatewayHostTextField().getText() + ".");
                    } else {
                        //printOUT(".");
                    }
                }
                mf.printDEB("The submission thread ended successfully! (Tight-Binding)");
                sendSIM.setEnabled(true);
         */
        return true;
        
    }

    /**
     * @return the sendTBSources
     */
    public boolean isSendTBSources() {
        return sendTBSources;
    }

    /**
     * @param sendTBSources the sendTBSources to set
     */
    public void setSendTBSources(boolean sendTBSources) {
        this.sendTBSources = sendTBSources;
    }
    
    @Override
    public void downloadLog(MainFrame mf)
    {
        // Use local machine to enable compilation
    
        Machine mach = mf.getMachineDatabase().getMachine(job.getMachineName());
        
        if(mach == null)
        {
            mf.printERR("No machine selected for this simulation");
            return;
        }
        
        if(!mach.isConnected())
        {
            mach.connection(mf);
        }

        String rootPath = mach.getSimulationPath();
        if(rootPath == null || rootPath.isEmpty())
        {
            rootPath = ".";
        }

        String clustepFolder = "clustep";

        String inputFile = "";
        String inputFN = "";

        inputFile = getTbenerFileName();
        inputFN = Utils.getLastToken(inputFile.replace('\\', '/'), "/");

        // Test de l'existance de inputfile
        if (!Utils.exists(inputFile)) {
            mf.printERR("The file " + inputFile + " doesn't exist !");
            return;
        }

        String simName = null;
        if (inputFN != null) {
            if (!inputFN.equals("")) {
                int idx = inputFN.indexOf('-');
                if (idx > 0 && idx < inputFN.length()) {
                    simName = inputFN.substring(0, idx);
                } else {
                    simName = inputFN;
                }
            } else {
                mf.printERR("inputFileName == \"\"");
                return;
            }
        } else {
            mf.printERR("inputFileName == null");
            return;
        }

        if (!inputFile.equals("")) {

            String fileName = rootPath + "/" + clustepFolder + "/"
            + simName + "/" + simName + ".log";

            if (!Utils.exists(fileName)) {
                // Réception (copie) du fichier d'output si celui-ci est distant
                if (mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE) {
                    //                            if (Utils.osName().startsWith("Windows")) {
                        //                                sendCommand("unix2dos " + fileName);
                        //                            }
                    mach.getFile(fileName + " " + fileName, mf);
                    //                            if (Utils.osName().startsWith("Windows")) {
                        //                                sendCommand("dos2unix " + fileName);
                        //                            }
                    if (Utils.osName().startsWith("Windows")) {
                        Utils.unix2dos(new File(fileName));
                    }
                }
            } else {
                mf.printOUT("File " + fileName + " exists in your local filetree!\n"
                    + "Please remove the local file before you download the new file version!");
            }

            // ****************************************************************************
            // Tester l'existance du fichier
            mf.editFile(fileName, false);
            // ****************************************************************************
        } else {
            mf.printERR("Please setup the inputfile textfield !");
            return;
        }
    }
    
    @Override
    public void downloadOutput(MainFrame mf)
    {
        /**
        * Download result 1
        * if ((mf.remoteGatewayRadioButton().isSelected() || mf.remoteAbinitRadioButton().isSelected()) && mf.remoteExec == null) {
                    mf.printERR("Please connect to a Tight-Binding host before doing anything!");
                    //getEvolutionFileButton.setEnabled(true);
                    downloadR1.setEnabled(true);
                    return;
                }

                String rootPath = mf.mySimulationsTextField().getText();

                String TBrootR = "./Tight-Binding_src/DOS";
                String TBrootL = rootPath + "/Tight-Binding/DOS";

                String DOS = "1";
                if (jRB_dos1.isSelected()) {
                    DOS = "1";
                }
                if (jRB_dos2.isSelected()) {
                    DOS = "2";
                }

                //**************

                int L = 3, M = 3;
                String LMParams = "3,3";
                try {
                    if (RB_LM.isSelected()) {
                        L = Integer.parseInt(LTextField.getText());
                        M = Integer.parseInt(MTextField.getText());
                        LMParams = L + "," + M;
                    }
                    if (RB_notLM.isSelected()) {
                        if (jCheckBox_graphite.isSelected()) {
                            LMParams = "graphite";
                        }
                        if (jCheckBox_pentahept.isSelected()) {
                            LMParams = "pentahept";
                        }
                    }
                } catch (Exception e) {
                    mf.printDEB("L = 3, M = 3 (default taken!)");
                    LMParams = "3,3";
                }

                String fileNameR = TBrootR + "/results/DOS" + DOS + "_res-" + LMParams;
                String fileNameL = TBrootL + "/DOS" + DOS + "_res-" + LMParams;

                if (!Utils.exists(fileNameL)) {
                    // Réception (copie) du fichier d'output si celui-ci est distant
                    if (mf.remoteGatewayRadioButton().isSelected() || mf.remoteAbinitRadioButton().isSelected()) {
                        mf.getFile(fileNameR + " " + fileNameL);
                        if (Utils.osName().startsWith("Windows")) {
                            Utils.unix2dos(new File(fileNameL));
                        }
                    }
                } else {
                    mf.printOUT("File " + fileNameL + " exists in your local filetree!\n"
                        + "Please remove the local file before you download the new file version!");
                }

                String logfileNameR = TBrootR + "/DOS.log";
                String logfileNameL = TBrootL + "/DOS.log";

                // Réception (copie) du fichier d'output si celui-ci est distant
                if (mf.remoteGatewayRadioButton().isSelected() || mf.remoteAbinitRadioButton().isSelected()) {
                    mf.getFile(logfileNameR + " " + logfileNameL);
                    if (Utils.osName().startsWith("Windows")) {
                        Utils.unix2dos(new File(fileNameL));
                    }
                }

                // ****************************************************************************
                // Tester l'existance du fichier
                if (!Utils.exists(fileNameL)) {
                    mf.editFile(logfileNameL.replace("/./", "/"), false);
                } else {
                    mf.editFile(fileNameL.replace("/./", "/"), false);
                }
        */
        
        
        /**
         * Download results 2
         * if ((mf.remoteGatewayRadioButton().isSelected() || mf.remoteAbinitRadioButton().isSelected()) && mf.remoteExec == null) {
                    mf.printERR("Please connect to a Tight-Binding host before doing anything!");
                    //getEvolutionFileButton.setEnabled(true);
                    downloadR2.setEnabled(true);
                    return;
                }

                String rootPath = mf.mySimulationsTextField().getText();

                String TBrootR = "./Tight-Binding_src/DOS";
                String TBrootL = rootPath + "/Tight-Binding/DOS";

                //**************

                int L = 3, M = 3;
                String LMParams = "3,3";
                try {
                    L = Integer.parseInt(LTextField2.getText());
                    M = Integer.parseInt(MTextField2.getText());
                    LMParams = L + "," + M;
                } catch (Exception e) {
                    mf.printDEB("L = 3, M = 3 (default taken!)");
                    LMParams = "3,3";
                }

                String fileNameR = TBrootR + "/results/DOS3_res-" + LMParams;
                String fileNameL = TBrootL + "/DOS3_res-" + LMParams;

                if (!Utils.exists(fileNameL)) {
                    // Réception (copie) du fichier d'output si celui-ci est distant
                    if (mf.remoteGatewayRadioButton().isSelected() || mf.remoteAbinitRadioButton().isSelected()) {
                        mf.getFile(fileNameR + " " + fileNameL);
                        if (Utils.osName().startsWith("Windows")) {
                            Utils.unix2dos(new File(fileNameL));
                        }
                    }
                } else {
                    mf.printOUT("File " + fileNameL + " exists in your local filetree!\n"
                        + "Please remove the local file before you download the new file version!");
                }

                String logfileNameR = TBrootR + "/DOS.log";
                String logfileNameL = TBrootL + "/DOS.log";

                // Réception (copie) du fichier d'output si celui-ci est distant
                if (mf.remoteGatewayRadioButton().isSelected() || mf.remoteAbinitRadioButton().isSelected()) {
                    mf.getFile(logfileNameR + " " + logfileNameL);
                    if (Utils.osName().startsWith("Windows")) {
                        Utils.unix2dos(new File(fileNameL));
                    }
                }

                // ****************************************************************************
                // Tester l'existance du fichier
                if (!Utils.exists(fileNameL)) {
                    mf.editFile(logfileNameL.replace("/./", "/"), false);
                } else {
                    mf.editFile(fileNameL.replace("/./", "/"), false);
                }
         */
        
        /**
         * Downloads results 3
         * if ((mf.remoteGatewayRadioButton().isSelected() || mf.remoteAbinitRadioButton().isSelected()) && mf.remoteExec == null) {
                    mf.printERR("Please connect to a Tight-Binding host before doing anything!");
                    //getEvolutionFileButton.setEnabled(true);
                    downloadR3.setEnabled(true);
                    return;
                }

                String rootPath = mf.mySimulationsTextField().getText();

                String TBrootR = "./Tight-Binding_src/BS";
                String TBrootL = rootPath + "/Tight-Binding/BS";

                String LMParams = "6,6";

                if (jRB1.isSelected()) {
                    LMParams = "6,6";
                }
                if (jRB2.isSelected()) {
                    LMParams = "12,0";
                }
                if (jRB3.isSelected()) {
                    LMParams = "11,0";
                }

                String fileNameR = TBrootR + "/results/result-" + LMParams;
                String fileNameL = TBrootL + "/result-" + LMParams;

                if (!Utils.exists(fileNameL)) {
                    // Réception (copie) du fichier d'output si celui-ci est distant
                    if (mf.remoteGatewayRadioButton().isSelected() || mf.remoteAbinitRadioButton().isSelected()) {
                        mf.getFile(fileNameR + " " + fileNameL);
                        if (Utils.osName().startsWith("Windows")) {
                            Utils.unix2dos(new File(fileNameL));
                        }
                    }
                } else {
                    mf.printOUT("File " + fileNameL + " exists in your local filetree!\n"
                        + "Please remove the local file before you download the new file version!");
                }

                String logfileNameR = TBrootR + "/BS.log";
                String logfileNameL = TBrootL + "/BS.log";

                // Réception (copie) du fichier d'output si celui-ci est distant
                if (mf.remoteGatewayRadioButton().isSelected() || mf.remoteAbinitRadioButton().isSelected()) {
                    mf.getFile(logfileNameR + " " + logfileNameL);
                    if (Utils.osName().startsWith("Windows")) {
                        Utils.unix2dos(new File(fileNameL));
                    }
                }

                // ****************************************************************************
                // Tester l'existance du fichier
                if (!Utils.exists(fileNameL)) {
                    mf.editFile(logfileNameL.replace("/./", "/"), false);
                } else {
                    mf.editFile(fileNameL.replace("/./", "/"), false);
                }
         */
        
        
    }

    /**
     * @return the compileTB
     */
    public boolean isCompileTB() {
        return compileTB;
    }

    /**
     * @param compileTB the compileTB to set
     */
    public void setCompileTB(boolean compileTB) {
        this.compileTB = compileTB;
    }

    /**
     * @return the tbenerFileName
     */
    public String getTbenerFileName() {
        return tbenerFileName;
    }

    /**
     * @param tbenerFileName the tbenerFileName to set
     */
    public void setTbenerFileName(String tbenerFileName) {
        this.tbenerFileName = tbenerFileName;
    }

    /**
     * @return the graphite
     */
    public boolean isGraphite() {
        return graphite;
    }

    /**
     * @param graphite the graphite to set
     */
    public void setGraphite(boolean graphite) {
        this.graphite = graphite;
    }

    /**
     * @return the typeDosknee
     */
    public int getTypeDosknee() {
        return typeDosknee;
    }

    /**
     * @param typeDosknee the typeDosknee to set
     */
    public void setTypeDosknee(int typeDosknee) {
        this.typeDosknee = typeDosknee;
    }

    /**
     * @return the useLANDM
     */
    public boolean isUseLANDM() {
        return useLANDM;
    }

    /**
     * @param useLANDM the useLANDM to set
     */
    public void setUseLANDM(boolean useLANDM) {
        this.useLANDM = useLANDM;
    }

    /**
     * @return the L
     */
    public int getL() {
        return L;
    }

    /**
     * @param L the L to set
     */
    public void setL(int L) {
        this.L = L;
    }

    /**
     * @return the M
     */
    public int getM() {
        return M;
    }

    /**
     * @param M the M to set
     */
    public void setM(int M) {
        this.M = M;
    }

    /**
     * @return the typeGlobal
     */
    public int getTypeGlobal() {
        return typeGlobal;
    }

    /**
     * @param typeGlobal the typeGlobal to set
     */
    public void setTypeGlobal(int typeGlobal) {
        this.typeGlobal = typeGlobal;
    }

    /**
     * @return the use66
     */
    public boolean isUse66() {
        return use66;
    }

    /**
     * @param use66 the use66 to set
     */
    public void setUse66(boolean use66) {
        this.use66 = use66;
    }

    /**
     * @return the use110
     */
    public boolean isUse110() {
        return use110;
    }

    /**
     * @param use110 the use110 to set
     */
    public void setUse110(boolean use110) {
        this.use110 = use110;
    }

    /**
     * @return the use120
     */
    public boolean isUse120() {
        return use120;
    }

    /**
     * @param use120 the use120 to set
     */
    public void setUse120(boolean use120) {
        this.use120 = use120;
    }
    
    
}
