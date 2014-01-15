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

package projects;

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
import projects.RemoteJob;

public class AbinitSimulation extends Simulation {

    private String name = "default";
    private String inputFileName = "./test3.in";
    private RemoteJob job;
    public static int RUNNING = 1;
    public static int FINISHED = 2;
    public static int READY = 0;
    private int status;
    private ArrayList<Atom> listPseudos;
    private boolean usingExtInputFile = false;

    public AbinitSimulation() {
        status = READY;

        job = new RemoteJob();
        listPseudos = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    
    public boolean isUsingExtInputFile()
    {
        return usingExtInputFile;
    }
    
    public void setUsingExtInputFile(boolean usingExtInputFile)
    {
        this.usingExtInputFile = usingExtInputFile;
    }
    
    public ArrayList<Atom> getListPseudos()
    {
        return listPseudos;
    }
    
    public void setListPseudos(ArrayList<Atom> listPseudos)
    {
        this.listPseudos = listPseudos;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public void setInputFileName(String fileName) {
        this.inputFileName = fileName;
    }

    @Override
    public String toString() {
        return name;
        //return "Simulation(name = " + name + "; fileName = " + inputFileName + ")";
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public RemoteJob getRemoteJob() {
        return job;
    }

    public void setRemoteJob(RemoteJob job) {
        this.job = job;
    }

    void updateStatus() {
        job.updateStatus();
    }
    
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
    
    public boolean submit(MainFrame mf)
    {
        Machine mach = mf.getMachineDatabase().getMachine(job.getMachineName());
        
        if(mach == null)
        {
            mf.printERR("No machine selected for this simulation");
            return false;
        }
        
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        boolean isLocalMachine = (mach.getType() == Machine.LOCAL_MACHINE);
        boolean isRemoteGatewayMachine = (mach.getType() == Machine.GATEWAY_MACHINE);
        boolean isRemoteAbinitMachine = (mach.getType() == Machine.REMOTE_MACHINE);

        String rootPath = mach.getSimulationPath();

        String pathToAbinit = mach.getAbinitPath();

        SubmissionScript script = getRemoteJob().getScript();

        String inputFile;

        if (usingExtInputFile) {
            inputFile = inputFileName;
        } else {
            mf.printERR("Choose an option please ! (use an external"
                    + " inputfile or created a inputfile)");
            return false;
        }

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (isLocalMachine && Utils.osName().startsWith("Windows")) {
            mf.printERR("Please connect to a remote host before submitting a simulation on Windows platform !");
            return false;
        }
        
        if(!mach.isConnected())
        {
            mf.printOUT("Trying to connect ...");
            mach.connection(mf);
        }

//        if ((remoteGatewayRadioButton_isSelected
//                || remoteAbinitRadioButton_isSelected) && remoteExec == null) {
//            mf.printERR("Please connect to a ABINIT host before submitting a simulation !");
//            return false;
//        }

        this.createFileTree(mach, mf);

        String inputFolder = "input";
        String outputFolder = "output";
        String wholedataFolder = "wholedata";
        String pseudopotFolder = "pseudopot";
        String logfilesFolder = "logfiles";

        // *************************************************************

        String cwd = "";

        String CMD = "pwd";

        RetMSG retmsg = mach.sendCommand(CMD, mf);
        if(retmsg.getRetCode() == RetMSG.SUCCES)
        {
            cwd = Utils.removeEndl(retmsg.getRetMSG());
        }
        else
        {
            mf.printERR("Not able to get working directory !");
            return false;
        }
        
//        Exec remoteExec = mach.getExec();
//        if (remoteExec != null) {
//            retmsg = remoteExec.sendCommand(CMD);
//            if (retmsg.getRetCode() == RetMSG.SUCCES) {
//                mf.printOUT("PWD: " + retmsg.getRetMSG());
//                cwd = Utils.removeEndl(retmsg.getRetMSG());
//            } else {
//                //mf.printERR("Error (RetVal = " + retmsg.getRetCode() + "): " + retmsg.getRetMSG());
//                mf.printERR("Error: " + retmsg.getRetMSG() + " !");
//            }
//        } else {
//            mf.printERR("First connect to an abinit host please !");
//        }

        // ********************************************************************************************************************************

        String inputFN = "";

        String sep = Utils.fileSeparator();
        inputFN = Utils.getLastToken(inputFile.replace('\\', '/'), "/");


        // Test de l'existance de inputfile
        if (!Utils.exists(inputFile)) {
            mf.printERR("The file " + inputFile + " doesn't exist !");
            return false;
        }

        String simName = null;
        if (inputFN != null) {
            int idx = inputFN.indexOf('.');
            if (idx > 0 && idx < inputFN.length()) {
                simName = inputFN.substring(0, idx);
            } else {
                simName = inputFN;
            }
        }

        if (!inputFile.equals("")) {
            script.setAbinitPath(pathToAbinit + "abinit");
            script.setInputPath(cwd + "/" + rootPath.replaceFirst("./", "") + "/" + simName + ".files");
            script.setLogPath(cwd + "/"
                    + rootPath.replaceFirst("./", "") + "/" + logfilesFolder + "/" + simName + ".log");

            if (script.getSystem().equals("SGE")) {
                String PBSfileName = rootPath + sep + simName + ".SGE.sh";
                script.writeToFile(PBSfileName);
            } else if (script.getSystem().equals("SLURM")) {
                String PBSfileName = rootPath + sep + simName + ".SLURM.sh";
                script.writeToFile(PBSfileName);
            } else if (script.getSystem().equals("Frontend")) {
                String SHfileName = rootPath + sep + simName + ".sh";
                script.writeToFile(SHfileName);
            }

            // Envoie (copie) du fichier d'input
            String inputFileR = rootPath + "/" + inputFolder + "/" + inputFN;
            mach.putFile(inputFile + " " + inputFileR, mf);

            if (isRemoteGatewayMachine
                    || isRemoteAbinitMachine) {
                if (Utils.osName().startsWith("Windows")) {
                    mach.sendCommand("dos2unix " + inputFileR, mf);
                    // TODO Util.dos2unix(new File(inputFileR)); // Transformer avant d'envoyer le fichier
                }
            }

            // Création du contenu du fichier de configuration (*.files)
            String configFileContent = "";
            configFileContent += cwd + "/" + rootPath.replaceFirst("./", "")
                    + "/" + inputFolder + "/" + inputFN + "\n";
            configFileContent += cwd + "/" + rootPath.replaceFirst("./", "")
                    + "/" + outputFolder + "/" + simName + ".out\n";
            configFileContent += cwd + "/" + rootPath.replaceFirst("./", "")
                    + "/" + wholedataFolder + "/" + simName + "/" + simName + "i\n";
            configFileContent += cwd + "/" + rootPath.replaceFirst("./", "")
                    + "/" + wholedataFolder + "/" + simName + "/" + simName + "o\n";
            configFileContent += cwd + "/" + rootPath.replaceFirst("./", "")
                    + "/" + wholedataFolder + "/" + simName + "/" + simName + "\n";

            if (usingExtInputFile) {
                for(Atom at : listPseudos)
                {
                    mach.putFile(at.getPSPPath() + sep + at.getPSPFileName()
                                    + " " + rootPath + "/" + pseudopotFolder
                                    + "/" + at.getPSPFileName(), mf);
                    configFileContent += cwd + "/" + rootPath.replaceFirst("./", "")
                            + "/" + pseudopotFolder + "/" + at.getPSPFileName() + "\n";
                }
            } else {
                mf.printERR("Creating input files graphically not yet supported");
                return false;
            }

            // Création du fichier de configuration
            try {
                String FILESfileName = rootPath + sep + simName + ".files";
                OutputStreamWriter fw = new OutputStreamWriter(
                        new FileOutputStream(FILESfileName), Utils.getCharset());
                //FileWriter fw = new FileWriter(FILESfileName);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);
                pw.print(configFileContent);
                pw.close();
                bw.close();
                fw.close();
            } catch (IOException e) {
                //mf.printERR(e.getMessage());
                mf.printERR("The configuration file (*.files) could not"
                        + " be created !");
                return false;
            }
            
            if (isRemoteGatewayMachine
                    || isRemoteAbinitMachine) {
                // Envoie du fichier de configuration
                String configFile = rootPath + sep + simName + ".files";
                String configFileR = rootPath + "/" + simName + ".files";

                if (Utils.osName().startsWith("Windows")) {
                    Utils.dos2unix(new File(configFile));
                }

                mach.putFile(configFile + " " + configFileR, mf);

                //if (Utils.osName().startsWith("Windows")) {
                //    sendCommand("dos2unix " + configFileR);
                //}
            }

            // Creation du dossier simName dans wholedataFolder
            Utils.mkdir(rootPath + "/" + wholedataFolder + "/" + simName);
            if (isRemoteGatewayMachine
                    || isRemoteAbinitMachine) {
                // We do it only if machine is remote !
                mach.mkdir(rootPath + "/" + wholedataFolder + "/" + simName, mf);
            }

            if (script.getSystem().equals("SGE")) {
                String sgeSHFile = rootPath + sep + simName + ".SGE.sh";
                String sgeSHFileR = rootPath + "/" + simName + ".SGE.sh";
                if (isRemoteGatewayMachine
                        || isRemoteAbinitMachine) {

                    // Envoie du fichier SGE
                    mach.putFile(sgeSHFile + " " + sgeSHFileR, mf);

                    if (Utils.osName().startsWith("Windows")) {
                        mach.sendCommand("dos2unix " + sgeSHFileR, mf);
                    }
                }
                // lancement des commandes d'exécution de la simulation
                mach.sendCommand("qsub " + sgeSHFileR, mf);
            } else if (script.getSystem().equals("Frontend")) {
                String SHFile = rootPath + sep + simName + ".sh";
                String SHFileR = rootPath + "/" + simName + ".sh";

                if (isRemoteGatewayMachine
                        || isRemoteAbinitMachine) {
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
                String slurmSHFile = rootPath + sep + simName + ".SLURM.sh";
                String slurmSHFileR = rootPath + "/" + simName + ".SLURM.sh";
                if (isRemoteGatewayMachine
                        || isRemoteAbinitMachine) {

                    // Envoie du fichier SGE
                    mach.putFile(slurmSHFile + " " + slurmSHFileR, mf);

                    if (Utils.osName().startsWith("Windows")) {
                        mach.sendCommand("dos2unix " + slurmSHFileR, mf);
                    }
                }
                // lancement des commandes d'exécution de la simulation
                mach.sendCommand("sbatch " + slurmSHFileR, mf);
            }
        } else {
            mf.printERR("Please setup the inputfile textfield !");
            return false;
        }

        if (isLocalMachine) {
            mf.printOUT("The simulation was submitted to the local Abinit machine.");
        } else {
            mf.printOUT("The simulation was submitted to the remote Abinit"
                    + " machine "+mach.getName()+".");
        }
        mf.printOUT("The submission thread ended successfully! (Abinit)");
        return true;
    }
}
