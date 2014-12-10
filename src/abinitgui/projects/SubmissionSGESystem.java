/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abinitgui.projects;

import abinitgui.core.MainFrame;
import abinitgui.core.RetMSG;
import abinitgui.core.Utils;
import java.util.ArrayList;

/**
 *
 * @author Yannick
 */
public class SubmissionSGESystem extends SubmissionSystem 
{
    public SubmissionSGESystem()
    {
        
    }
    
    public SubmissionSGESystem(Machine machine)
    {
        this.machine = machine;
    }
    
    @Override
    public RemoteJob submit(SubmissionScript script, String rootPath, String simName) {
        
        if(!machine.isConnected())
            getMachine().connection();
        
        RemoteJob rj = new RemoteJob();
        rj.setScript(script);
        rj.setMachineName(machine.getName());
        
        boolean isLocalMachine = (getMachine().getType() == Machine.LOCAL_MACHINE);
        boolean isRemoteGatewayMachine = (getMachine().getType() == Machine.GATEWAY_MACHINE);
        boolean isRemoteAbinitMachine = (getMachine().getType() == Machine.REMOTE_MACHINE);
        
        String PBSfileName = rootPath + Utils.fileSeparator() + simName + ".SGE.sh";
        script.writeToFile(PBSfileName);
        
        String sgeSHFile = rootPath + Utils.fileSeparator() + simName + ".SGE.sh";
        String sgeSHFileR = rootPath + "/" + simName + ".SGE.sh";
        if (isRemoteGatewayMachine
                || isRemoteAbinitMachine) {

            // Envoie du fichier SGE
            getMachine().putFile(sgeSHFile + " " + sgeSHFileR);

            if (Utils.osName().startsWith("Windows")) {
                getMachine().sendCommand("dos2unix " + sgeSHFileR);
            }
        }
        // lancement des commandes d'exécution de la simulation
        RetMSG msg = getMachine().sendCommand("qsub " + sgeSHFileR);
        String retMsg = msg.getRetMSG();
        // Your job 1036608 ("testjob") has been submitted
        if(retMsg.startsWith("Your job"))
        {
            String jobIdS = retMsg.replace("Your job ", "").replace(" has been submitted","").replaceAll("\\(.*\\)", "").replaceAll("\n", "").replaceAll(" ","");
            int id = Integer.parseInt(jobIdS);
            rj.setJobId(id);
            System.out.println("my job id is : "+rj.getJobId());
        }
        else
        {
            MainFrame.printERR("Error : "+retMsg);
        }
        
        return rj;
    }

    @Override
    public ArrayList<RemoteJob> getRemoteJobs() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateStatus(RemoteJob rj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String printInfos(RemoteJob rj) {
        if(rj.getJobId() == -1) 
        {
            return "This job has not been submitted yet !";
        } 
        if(!machine.isConnected())
            getMachine().connection();
        
        RetMSG msg = getMachine().sendCommand("qacct -j "+rj.getJobId());
        return msg.getRetMSG();
    }

    @Override
    public void kill(RemoteJob rj) {
        if(!machine.isConnected())
           getMachine().connection();
        
        getMachine().sendCommand("qdel "+rj.getJobId());
    }
    
}
