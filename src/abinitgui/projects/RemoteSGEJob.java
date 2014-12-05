/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abinitgui.projects;

import abinitgui.core.MainFrame;
import abinitgui.core.RetMSG;
import abinitgui.core.Utils;

/**
 *
 * @author yannick
 */
public class RemoteSGEJob extends RemoteJob
{
    @Override
    public void submit(String rootPath, String simName)
    {
        Machine mach = MainFrame.getMachineDatabase().getMachine(this.getMachineName());
        if(!mach.isConnected())
            mach.connection();
        
        boolean isLocalMachine = (mach.getType() == Machine.LOCAL_MACHINE);
        boolean isRemoteGatewayMachine = (mach.getType() == Machine.GATEWAY_MACHINE);
        boolean isRemoteAbinitMachine = (mach.getType() == Machine.REMOTE_MACHINE);
        
        String PBSfileName = rootPath + Utils.fileSeparator() + simName + ".SGE.sh";
        script.writeToFile(PBSfileName);
        
        String sgeSHFile = rootPath + Utils.fileSeparator() + simName + ".SGE.sh";
        String sgeSHFileR = rootPath + "/" + simName + ".SGE.sh";
        if (isRemoteGatewayMachine
                || isRemoteAbinitMachine) {

            // Envoie du fichier SGE
            mach.putFile(sgeSHFile + " " + sgeSHFileR);

            if (Utils.osName().startsWith("Windows")) {
                mach.sendCommand("dos2unix " + sgeSHFileR);
            }
        }
        // lancement des commandes d'exécution de la simulation
        RetMSG msg = mach.sendCommand("qsub " + sgeSHFileR);
        String retMsg = msg.getRetMSG();
        // Your job 1036608 ("testjob") has been submitted
        if(retMsg.startsWith("Your job"))
        {
            String jobIdS = retMsg.replace("Your job ", "").replace(" has been submitted","").replaceAll("\\(.*\\)", "").replaceAll("\n", "").replaceAll(" ","");
            int id = Integer.parseInt(jobIdS);
            this.setJobId(id);
            System.out.println("my job id is : "+this.getJobId());
        }
        else
        {
            MainFrame.printERR("Error : "+retMsg);
        }
    }
    
    @Override
    public void printInfos()
    {
        if(getJobId() == -1) 
        {
            MainFrame.printOUT("This job has not been submitted yet !");
            return;
        } 
        
        Machine machine = MainFrame.getMachineDatabase().getMachine(this.getMachineName());
        if(!machine.isConnected())
            machine.connection();
        
        RetMSG msg = machine.sendCommand("qacct -j "+this.getJobId());
        MainFrame.printOUT(msg.getRetMSG());
    }
    
    @Override
    public void setScript(SubmissionScript script) {
        if(script instanceof SGEScript)
        {
            super.setScript(script);
        }
        else
        {
            throw new IllegalArgumentException(""+script);
        }
    }
    
   
    @Override
    public void kill() {
        Machine machine = MainFrame.getMachineDatabase().getMachine(this.getMachineName());
        if(!machine.isConnected())
            machine.connection();
        
        machine.sendCommand("qdel "+this.getJobId());
    }
    
}
