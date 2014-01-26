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
import projects.LocalMachine;
import projects.Machine;
import projects.RemoteJob;
import projects.RemoteJob;
import projects.Simulation;
import projects.SubmissionScript;

public class ClustepSimulation extends Simulation {

    private String name = "default";
    private String inputFileName = "./test3.in";
    private RemoteJob job;
    public static int RUNNING = 1;
    public static int FINISHED = 2;
    public static int READY = 0;
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
        // Use local machine to enable compilation
    
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

        inputFile = inputFileName;

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
        
        String ClustepProgPath = "";
        if (mach.getType() == Machine.GATEWAY_MACHINE || mach.getType() == Machine.REMOTE_MACHINE) {
            ClustepProgPath = "~/CLUSTEP/clustep0";
        } else {
            ClustepProgPath = "../../../CLUSTEP/clustep0";
        }   

        this.createFileTree(mach, mf);
        
        String clustepFolder = "clustep";

        // *************************************************************

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
            int idx = inputFN.indexOf('-');
            if (idx > 0 && idx < inputFN.length()) {
                simName = inputFN.substring(0, idx);
            } else {
                simName = inputFN;
            }
        }

        // Creation du dossier clustepFolder local
        mf.getLocalExec().mkdir(rootPath + "/" + clustepFolder);
        // Creation du dossier simName dans clustepFolder local
        mf.getLocalExec().mkdir(rootPath + "/" + clustepFolder + "/" + simName);

        if (mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE) {
            // Creation du dossier clustepFolder
            mach.mkdir(rootPath + "/" + clustepFolder, mf);
            // Creation du dossier simName dans clustepFolder
            mach.mkdir(rootPath + "/" + clustepFolder + "/" + simName, mf);
        }

        if (isSendClustepSources()) {
            mach.mkdir("./CLUSTEP", mf);
            if(mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE)
            {
                mach.putFile("./CLUSTEP_src.tar.gz ./CLUSTEP_src.tar.gz", mf);
            }

            // Unzip the compressed file CLUSTEP_src.tar.gz
            mach.sendCommand("tar -zxf ./CLUSTEP_src.tar.gz", mf);

            // Compilation de clustep0
            mach.sendCommand("make -C ./CLUSTEP_src/", mf);
            mach.sendCommand("mv ./CLUSTEP_src/clustep0 ./CLUSTEP", mf);
            mach.sendCommand("rm -rf ./CLUSTEP_src/", mf);

            if(mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE)
            {
                mach.sendCommand("rm -f ./CLUSTEP_src.tar.gz", mf);
            }
        }

        if (!inputFile.equals("")) {
            script.setAbinitPath(ClustepProgPath);
            script.setInputPath(cwd + "/" 
                    + rootPath.replaceFirst("./", "") + "/" + clustepFolder + "/" + simName+ "/" + simName + ".files");
            script.setLogPath(cwd + "/"
                    + rootPath.replaceFirst("./", "") + "/" + clustepFolder + "/" + simName+ "/" + simName + ".log");
            script.setPreProcessPart("cd "+cwd+"/" + rootPath.replaceFirst("./", "") + "/" + clustepFolder + "/" + simName);

            if (script.getSystem().equals("SGE")) {
                String PBSfileName = rootPath + sep + clustepFolder + sep + simName+ sep + simName + ".SGE.sh";
                script.writeToFile(PBSfileName);
            } else if (script.getSystem().equals("SLURM")) {
                String PBSfileName = rootPath + sep + clustepFolder + sep + simName+ sep + simName + ".SLURM.sh";
                script.writeToFile(PBSfileName);
            } else if (script.getSystem().equals("Frontend")) {
                String SHfileName = rootPath + sep + clustepFolder + sep + simName+ sep + simName + ".sh";
                script.writeToFile(SHfileName);
            }

            // Envoie (copie) du fichier d'input
            String inputFileR = rootPath + "/" + clustepFolder + "/" + simName + "/" + simName + "-input";
            if (mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE) {
                if (Utils.osName().startsWith("Windows")) {
                    Utils.dos2unix(new File(inputFile));
                }
            }
            mach.putFile(inputFile + " " + inputFileR, mf);
            
            // Envoie (copie) du fichier d'input
            String positionFile = getPositionFileName();
            String positionFileR = rootPath + "/" + clustepFolder + "/" + simName + "/" + simName + "-pos";
            if (mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE) {
                if (Utils.osName().startsWith("Windows")) {
                    Utils.dos2unix(new File(positionFile));
                }
            }
            
            mach.putFile(positionFile + " " + positionFileR, mf);

            if (isRemoteGatewayMachine
                    || isRemoteAbinitMachine) {
                if (Utils.osName().startsWith("Windows")) {
                    mach.sendCommand("dos2unix " + inputFileR, mf);
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
            try {
                String FILESfileName = rootPath + sep + clustepFolder + sep + simName+ sep + simName + ".files";
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
                String configFile  = rootPath + sep + clustepFolder + sep + simName + sep + simName + ".files";
                String configFileR = rootPath + "/" + clustepFolder + "/" + simName + "/" +  simName + ".files";

                if (Utils.osName().startsWith("Windows")) {
                    Utils.dos2unix(new File(configFile));
                }

                mach.putFile(configFile + " " + configFileR, mf);

                //if (Utils.osName().startsWith("Windows")) {
                //    sendCommand("dos2unix " + configFileR);
                //}
            }

            if (script.getSystem().equals("SGE")) {
                String sgeSHFile = rootPath + sep + clustepFolder + sep + simName+ sep + simName + ".SGE.sh";
                String sgeSHFileR = rootPath + "/" + clustepFolder + "/" + simName+ "/" + simName + ".SGE.sh";
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
                String SHFile = rootPath + sep + clustepFolder + sep + simName+  sep + simName + ".sh";
                String SHFileR = rootPath + "/" + clustepFolder + "/" + simName+ "/" + simName + ".sh";

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
                String slurmSHFile = rootPath + "/" + clustepFolder + "/" + simName+ sep + simName + ".SLURM.sh";
                String slurmSHFileR = rootPath + "/" + clustepFolder + "/" + simName+ "/" + simName + ".SLURM.sh";
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

        inputFile = inputFileName;
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
        Machine mach = mf.getMachineDatabase().getMachine(job.getMachineName());

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

        inputFile = inputFileName;
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
            String fileNameEvol = rootPath + "/" + clustepFolder + "/"
            + simName + "/" + simName + "-evol.dat";

            if (!Utils.exists(fileNameEvol)) {
                // Réception (copie) du fichier d'output si celui-ci est distant
                if (mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE) {
                    //                            if (Utils.osName().startsWith("Windows")) {
                        //                                sendCommand("unix2dos " + fileName);
                        //                            }
                    mach.getFile(fileNameEvol + " " + fileNameEvol, mf);
                    //                            if (Utils.osName().startsWith("Windows")) {
                        //                                sendCommand("dos2unix " + fileName);
                        //                            }
                    if (Utils.osName().startsWith("Windows")) {
                        Utils.unix2dos(new File(fileNameEvol));
                    }
                }
            } else {
                mf.printOUT("File " + fileNameEvol + " exists in your local filetree!\n"
                    + "Please remove the local file before you download the new file version!");
            }

            // ****************************************************************************
            // Tester l'existence du fichier
            mf.editFile(fileNameEvol.replace("/./", "/"), false);
            

            String fileNameFilm = rootPath + "/" + clustepFolder + "/"
            + simName + "/" + simName + "-film.xyz";

            if (!Utils.exists(fileNameFilm)) {
                // Réception (copie) du fichier d'output si celui-ci est distant
                if (mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE) {
                    //                            if (Utils.osName().startsWith("Windows")) {
                        //                                sendCommand("unix2dos " + fileName);
                        //                            }
                    mach.getFile(fileNameFilm + " " + fileNameFilm, mf);
                    //                            if (Utils.osName().startsWith("Windows")) {
                        //                                sendCommand("dos2unix " + fileName);
                        //                            }
                    if (Utils.osName().startsWith("Windows")) {
                        Utils.unix2dos(new File(fileNameFilm));
                    }
                }
            } else {
                mf.printOUT("File " + fileNameFilm + " exists in your local filetree!\n"
                    + "Please remove the local file before you download the new file version!");
            }

            // ****************************************************************************
            // Tester l'existence du fichier
            mf.editFile(fileNameFilm.replace("/./", "/"), false);

            if (!Utils.exists(fileNameFilm)) {
                mf.printERR("File " + fileNameFilm + " doesn't exist !");
                return;
            } else {
                if (Utils.osName().equals("Linux")) {
                    mf.localCommand("java -jar Jmol.jar " + fileNameFilm.replace("/./", "/"));
                } else if (Utils.osName().equals("Mac OS X")) {
                    mf.localCommand("java -jar Jmol.jar " + fileNameFilm.replace("/./", "/"));
                } else {
                    mf.localCommand("java -jar Jmol.jar " + fileNameFilm.replace("/./", "/"));
                }
            }
            // ****************************************************************************
        } else {
            mf.printERR("Please setup the inputfile textfield !");
            return;
        }
    }
    
    
}
