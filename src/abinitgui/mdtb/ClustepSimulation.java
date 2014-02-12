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

package abinitgui.mdtb;

import abinitgui.core.MainFrame;
import abinitgui.core.RetMSG;
import abinitgui.core.Utils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import abinitgui.projects.Machine;
import abinitgui.projects.RemoteJob;
import abinitgui.projects.Simulation;
import abinitgui.projects.SubmissionScript;

public class ClustepSimulation extends Simulation {

    private String name = "default";
    private String inputFileName = "./test3.in";
    private RemoteJob job;
    private int status;
    private boolean sendClustepSources;
    private String positionFileName;

    public ClustepSimulation() {
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
        return inputFileName;
    }

    @Override
    public void setInputFileName(String fileName) {
        this.inputFileName = fileName;
    }

    @Override
    public String toString() {
        return name;
        //return "Simulation(name = " + name + "; fileName = " + inputFileName + ")";
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
    public void createFileTree(Machine mach)
    {
        String path = mach.getSimulationPath();
        if (path.equals("")) {
            path = ".";
        }
        mach.createTree(path);
        if(mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE)
        {
            MainFrame.getLocalExec().createTree(path);
        }
        
    }
    
    @Override
    public boolean submit()
    {
        // Use local machine to enable compilation
    
        Machine mach = MainFrame.getMachineDatabase().getMachine(job.getMachineName());
        
        if(mach == null)
        {
            MainFrame.printERR("No machine selected for this simulation");
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

        inputFile = inputFileName;

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        if (isLocalMachine && Utils.osName().startsWith("Windows")) {
            MainFrame.printERR("Please connect to a remote host before"
                    + " submitting a simulation on Windows platform!");
            return false;
        }
        
        if(!mach.isConnected())
        {
            MainFrame.printOUT("Trying to connect ...");
            mach.connection();
        }
        
        String ClustepProgPath;
        if (mach.getType() == Machine.GATEWAY_MACHINE ||
                mach.getType() == Machine.REMOTE_MACHINE) {
            ClustepProgPath = "~/CLUSTEP/clustep0";
        } else {
            ClustepProgPath = "../../../CLUSTEP/clustep0";
        }   

        this.createFileTree(mach);
        
        String clustepFolder = "clustep";

        // *************************************************************

        String cwd;

        String CMD = "pwd";

        RetMSG retmsg = mach.sendCommand(CMD);
        if(retmsg.getRetCode() == RetMSG.SUCCES)
        {
            MainFrame.printOUT("PWD: " + retmsg.getRetMSG());
            cwd = Utils.removeEndl(retmsg.getRetMSG());
        }
        else
        {
            MainFrame.printERR("Not able to get working directory !");
            return false;
        }

        // ********************************************************************************************************************************

        String inputFN;

        String sep = Utils.fileSeparator();
        inputFN = Utils.getLastToken(inputFile.replace('\\', '/'), "/");


        // Test de l'existance de inputfile
        if (!Utils.exists(inputFile)) {
            MainFrame.printERR("The file " + inputFile + " doesn't exist!");
            return false;
        }

        String simName = null;
        if (inputFN != null) {
            int idx = inputFN.indexOf('-');
            if (idx > 0 && idx < inputFN.length()) {
                simName = inputFN.substring(0, idx);
            } else {
                simName = inputFN;
            }
        }

        // Creation du dossier clustepFolder local
        MainFrame.getLocalExec().mkdir(rootPath + "/" + clustepFolder);
        // Creation du dossier simName dans clustepFolder local
        MainFrame.getLocalExec().mkdir(rootPath + "/" + clustepFolder + "/" + simName);

        if (mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE) {
            // Creation du dossier clustepFolder
            mach.mkdir(rootPath + "/" + clustepFolder);
            // Creation du dossier simName dans clustepFolder
            mach.mkdir(rootPath + "/" + clustepFolder + "/" + simName);
        }

        if (isSendClustepSources()) {
            mach.mkdir("./CLUSTEP");
            if(mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE)
            {
                mach.putFile("./CLUSTEP_src.tar.gz ./CLUSTEP_src.tar.gz");
            }

            // Unzip the compressed file CLUSTEP_src.tar.gz
            mach.sendCommand("tar -zxf ./CLUSTEP_src.tar.gz");

            // Compilation de clustep0
            mach.sendCommand("make -C ./CLUSTEP_src/");
            mach.sendCommand("mv ./CLUSTEP_src/clustep0 ./CLUSTEP");
            mach.sendCommand("rm -rf ./CLUSTEP_src/");

            if(mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE)
            {
                mach.sendCommand("rm -f ./CLUSTEP_src.tar.gz");
            }
        }

        if (!inputFile.equals("")) {
            script.setAbinitPath(ClustepProgPath);
            script.setInputPath(cwd + "/" 
                    + rootPath.replaceFirst("./", "") + "/" + clustepFolder + "/" + simName+ "/" + simName + ".files");
            script.setLogPath(cwd + "/"
                    + rootPath.replaceFirst("./", "") + "/" + clustepFolder + "/" + simName+ "/" + simName + ".log");
            script.setCDPart("cd "+cwd+"/" + rootPath.replaceFirst("./", "") + "/" + clustepFolder + "/" + simName);
            switch (script.getSystem()) {
                case "SGE":
                    {
                        String PBSfileName = rootPath + sep + clustepFolder + sep + simName+ sep + simName + ".SGE.sh";
                        script.writeToFile(PBSfileName);
                        break;
                    }
                case "SLURM":
                    {
                        String PBSfileName = rootPath + sep + clustepFolder + sep + simName+ sep + simName + ".SLURM.sh";
                        script.writeToFile(PBSfileName);
                        break;
                    }
                case "Frontend":
                    String SHfileName = rootPath + sep + clustepFolder + sep + simName+ sep + simName + ".sh";
                    script.writeToFile(SHfileName);
                    break;
            }

            // Envoie (copie) du fichier d'input
            String inputFileR = rootPath + "/" + clustepFolder + "/" + simName + "/" + simName + "-input";
            if (mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE) {
                if (Utils.osName().startsWith("Windows")) {
                    Utils.dos2unix(new File(inputFile));
                }
            }
            mach.putFile(inputFile + " " + inputFileR);
            
            // Envoie (copie) du fichier d'input
            String positionFile = getPositionFileName();
            String positionFileR = rootPath + "/" + clustepFolder + "/" + simName + "/" + simName + "-pos";
            if (mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE) {
                if (Utils.osName().startsWith("Windows")) {
                    Utils.dos2unix(new File(positionFile));
                }
            }
            
            mach.putFile(positionFile + " " + positionFileR);

            if (isRemoteGatewayMachine
                    || isRemoteAbinitMachine) {
                if (Utils.osName().startsWith("Windows")) {
                    mach.sendCommand("dos2unix " + inputFileR);
                    // TODO Util.dos2unix(new File(inputFileR)); // Transformer avant d'envoyer le fichier
                }
            }

            // Création du contenu du fichier de configuration (*.files)
            String configFileContent = "";
            configFileContent += simName + "-input\n";
            configFileContent += simName + "-evol.dat\n";
            configFileContent += simName + "-pos\n";
            configFileContent += simName + "-film.xyz\n";

            // Création du fichier de configuration
            OutputStreamWriter fw;
            BufferedWriter bw;
            PrintWriter pw;
            try {
                String FILESfileName = rootPath + sep + clustepFolder + sep + simName+ sep + simName + ".files";
                fw = new OutputStreamWriter(
                        new FileOutputStream(FILESfileName), Utils.getCharset());
                //FileWriter fw = new FileWriter(FILESfileName);
                bw = new BufferedWriter(fw);
                pw = new PrintWriter(bw);
                pw.print(configFileContent);
                pw.close();
                bw.close();
                fw.close();
            } catch (IOException e) {
                //mf.printERR(e.getMessage());
                MainFrame.printERR("The configuration file (*.files) could not"
                        + " be created !");
                return false;
            }
            
            if (isRemoteGatewayMachine
                    || isRemoteAbinitMachine) {
                // Envoie du fichier de configuration
                String configFile  = rootPath + sep + clustepFolder + sep + simName + sep + simName + ".files";
                String configFileR = rootPath + "/" + clustepFolder + "/" + simName + "/" +  simName + ".files";

                if (Utils.osName().startsWith("Windows")) {
                    Utils.dos2unix(new File(configFile));
                }

                mach.putFile(configFile + " " + configFileR);

                //if (Utils.osName().startsWith("Windows")) {
                //    sendCommand("dos2unix " + configFileR);
                //}
            }
            switch (script.getSystem()) {
                case "SGE":
                    String sgeSHFile = rootPath + sep + clustepFolder + sep + simName+ sep + simName + ".SGE.sh";
                    String sgeSHFileR = rootPath + "/" + clustepFolder + "/" + simName+ "/" + simName + ".SGE.sh";
                    if (isRemoteGatewayMachine
                            || isRemoteAbinitMachine) {

                        // Envoie du fichier SGE
                        mach.putFile(sgeSHFile + " " + sgeSHFileR);

                        if (Utils.osName().startsWith("Windows")) {
                            mach.sendCommand("dos2unix " + sgeSHFileR);
                        }
                    }
                    // lancement des commandes d'exécution de la simulation
                    mach.sendCommand("qsub " + sgeSHFileR);
                    break;
                case "Frontend":
                    String SHFile = rootPath + sep + clustepFolder + sep + simName+  sep + simName + ".sh";
                    String SHFileR = rootPath + "/" + clustepFolder + "/" + simName+ "/" + simName + ".sh";
                    if (isRemoteGatewayMachine
                            || isRemoteAbinitMachine) {
                        /*if (Utils.osName().startsWith("Windows")) {
                         Utils.dos2unix(new File(SHFileR));
                         }*/
                        // Envoie du fichier BASH
                        mach.putFile(SHFile + " " + SHFileR);

                        if (Utils.osName().startsWith("Windows")) {
                            mach.sendCommand("dos2unix " + SHFileR);
                        }
                    }
                    // lancement des commandes d'exécution de la simulation
                    mach.sendCommand("bash "+SHFileR);
                    break;
                case "SLURM":
                    String slurmSHFile = rootPath + "/" + clustepFolder + "/" + simName+ sep + simName + ".SLURM.sh";
                    String slurmSHFileR = rootPath + "/" + clustepFolder + "/" + simName+ "/" + simName + ".SLURM.sh";
                    if (isRemoteGatewayMachine
                            || isRemoteAbinitMachine) {

                        // Envoie du fichier SGE
                        mach.putFile(slurmSHFile + " " + slurmSHFileR);

                        if (Utils.osName().startsWith("Windows")) {
                            mach.sendCommand("dos2unix " + slurmSHFileR);
                        }
                    }
                    // lancement des commandes d'exécution de la simulation
                    mach.sendCommand("sbatch " + slurmSHFileR);
                    break;
            }
        } else {
            MainFrame.printERR("Please setup the inputfile textfield !");
            return false;
        }

        if (isLocalMachine) {
            MainFrame.printOUT("The simulation was submitted to the local Abinit machine.");
        } else {
            MainFrame.printOUT("The simulation was submitted to the remote Abinit"
                    + " machine "+mach.getName()+".");
        }
        MainFrame.printOUT("The submission thread ended successfully! (Abinit)");
        return true;
    }

    /**
     * @return the sendClustepSources
     */
    public boolean isSendClustepSources() {
        return sendClustepSources;
    }

    /**
     * @param sendClustepSources the sendClustepSources to set
     */
    public void setSendClustepSources(boolean sendClustepSources) {
        this.sendClustepSources = sendClustepSources;
    }

    /**
     * @return the positionFileName
     */
    public String getPositionFileName() {
        return positionFileName;
    }

    /**
     * @param positionFileName the positionFileName to set
     */
    public void setPositionFileName(String positionFileName) {
        this.positionFileName = positionFileName;
    }
    
    @Override
    public void downloadLog()
    {
        // Use local machine to enable compilation
    
        Machine mach = MainFrame.getMachineDatabase().getMachine(job.getMachineName());
        
        if(mach == null)
        {
            MainFrame.printERR("No machine selected for this simulation");
            return;
        }
        
        if(!mach.isConnected())
        {
            mach.connection();
        }

        String rootPath = mach.getSimulationPath();
        if(rootPath == null || rootPath.isEmpty())
        {
            rootPath = ".";
        }

        String clustepFolder = "clustep";

        String inputFile;
        String inputFN;

        inputFile = inputFileName;
        inputFN = Utils.getLastToken(inputFile.replace('\\', '/'), "/");

        // Test de l'existance de inputfile
        if (!Utils.exists(inputFile)) {
            MainFrame.printERR("The file " + inputFile + " doesn't exist !");
            return;
        }

        String simName;
        if (inputFN != null) {
            if (!inputFN.equals("")) {
                int idx = inputFN.indexOf('-');
                if (idx > 0 && idx < inputFN.length()) {
                    simName = inputFN.substring(0, idx);
                } else {
                    simName = inputFN;
                }
            } else {
                MainFrame.printERR("inputFileName == \"\"");
                return;
            }
        } else {
            MainFrame.printERR("inputFileName == null");
            return;
        }

        if (!inputFile.equals("")) {

            String fileName = rootPath + "/" + clustepFolder + "/"
            + simName + "/" + simName + ".log";

            //if (!Utils.exists(fileName)) {
                // Réception (copie) du fichier d'output si celui-ci est distant
                if (mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE) {
                    //                            if (Utils.osName().startsWith("Windows")) {
                        //                                sendCommand("unix2dos " + fileName);
                        //                            }
                    mach.getFile(fileName + " " + fileName);
                    //                            if (Utils.osName().startsWith("Windows")) {
                        //                                sendCommand("dos2unix " + fileName);
                        //                            }
                    if (Utils.osName().startsWith("Windows")) {
                        Utils.unix2dos(new File(fileName));
                    }
                }
            /*} else {
                MainFrame.printOUT("File " + fileName + " exists in your local filetree!\n"
                    + "Please remove the local file before you download the new file version!");
            }*/

            // ****************************************************************************
            // Tester l'existance du fichier
            MainFrame.editFile(fileName, false);
            // ****************************************************************************
        } else {
            MainFrame.printERR("Please setup the inputfile textfield!");
        }
    }
    
    @Override
    public void downloadOutput()
    {
        Machine mach = MainFrame.getMachineDatabase().getMachine(job.getMachineName());

        if(!mach.isConnected())
        {
            mach.connection();
        }

        String rootPath = mach.getSimulationPath();
        if(rootPath == null || rootPath.isEmpty())
        {
            rootPath = ".";
        }
        String clustepFolder = "clustep";

        String inputFile;
        String inputFN;

        inputFile = inputFileName;
        inputFN = Utils.getLastToken(inputFile.replace('\\', '/'), "/");

        // Test de l'existance de inputfile
        if (!Utils.exists(inputFile)) {
            MainFrame.printERR("The file " + inputFile + " doesn't exist!");
            return;
        }

        String simName;
        if (inputFN != null) {
            if (!inputFN.equals("")) {
                int idx = inputFN.indexOf('-');
                if (idx > 0 && idx < inputFN.length()) {
                    simName = inputFN.substring(0, idx);
                } else {
                    simName = inputFN;
                }
            } else {
                MainFrame.printERR("inputFileName == \"\"");
                return;
            }
        } else {
            MainFrame.printERR("inputFileName == null");
            return;
        }

        if (!inputFile.equals("")) {
            String fileNameEvol = rootPath + "/" + clustepFolder + "/"
            + simName + "/" + simName + "-evol.dat";

            /*if (!Utils.exists(fileNameEvol)) {
                // Réception (copie) du fichier d'output si celui-ci est distant
                if (mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE) {
                    //                            if (Utils.osName().startsWith("Windows")) {
                        //                                sendCommand("unix2dos " + fileName);
                        //                            }
                    mach.getFile(fileNameEvol + " " + fileNameEvol);
                    //                            if (Utils.osName().startsWith("Windows")) {
                        //                                sendCommand("dos2unix " + fileName);
                        //                            }
                    if (Utils.osName().startsWith("Windows")) {
                        Utils.unix2dos(new File(fileNameEvol));
                    }
                }
            /*} else {
                MainFrame.printOUT("File " + fileNameEvol + " exists in your local filetree!\n"
                    + "Please remove the local file before you download the new file version!");
            }*/

            // ****************************************************************************
            // Tester l'existence du fichier
            MainFrame.editFile(fileNameEvol.replace("/./", "/"), false);
            

            String fileNameFilm = rootPath + "/" + clustepFolder + "/"
            + simName + "/" + simName + "-film.xyz";

            //if (!Utils.exists(fileNameFilm)) {
                // Réception (copie) du fichier d'output si celui-ci est distant
                if (mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE) {
                    //                            if (Utils.osName().startsWith("Windows")) {
                        //                                sendCommand("unix2dos " + fileName);
                        //                            }
                    mach.getFile(fileNameFilm + " " + fileNameFilm);
                    //                            if (Utils.osName().startsWith("Windows")) {
                        //                                sendCommand("dos2unix " + fileName);
                        //                            }
                    if (Utils.osName().startsWith("Windows")) {
                        Utils.unix2dos(new File(fileNameFilm));
                    }
                }
            /*} else {
                MainFrame.printOUT("File " + fileNameFilm + " exists in your local filetree!\n"
                    + "Please remove the local file before you download the new file version!");
            }*/

            // ****************************************************************************
            // Tester l'existence du fichier
            MainFrame.editFile(fileNameFilm.replace("/./", "/"), false);

            if (!Utils.exists(fileNameFilm)) {
                MainFrame.printERR("File " + fileNameFilm + " doesn't exist !");
            } else {
                switch (Utils.osName()) {
                    case "Linux":
                        MainFrame.localCommand("java -jar Jmol.jar " + fileNameFilm.replace("/./", "/"));
                        break;
                    case "Mac OS X":
                        MainFrame.localCommand("java -jar Jmol.jar " + fileNameFilm.replace("/./", "/"));
                        break;
                    default:
                        MainFrame.localCommand("java -jar Jmol.jar " + fileNameFilm.replace("/./", "/"));
                        break;
                }
            }
            // ****************************************************************************
        } else {
            MainFrame.printERR("Please setup the inputfile textfield!");
        }
    }
    
    
}
