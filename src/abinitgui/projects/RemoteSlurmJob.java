/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abinitgui.projects;

import abinitgui.core.MainFrame;
import abinitgui.core.RetMSG;
import abinitgui.core.Utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author yannick
 */
public class RemoteSlurmJob extends RemoteJob
{
    @Override
    public void submit(String rootPath, String simName)
    {
        Machine machine = MainFrame.getMachineDatabase().getMachine(this.getMachineName());
        if(!machine.isConnected())
            machine.connection();
        
        boolean isLocalMachine = (machine.getType() == Machine.LOCAL_MACHINE);
        boolean isRemoteGatewayMachine = (machine.getType() == Machine.GATEWAY_MACHINE);
        boolean isRemoteAbinitMachine = (machine.getType() == Machine.REMOTE_MACHINE);
        
        String PBSfileName = rootPath + Utils.fileSeparator() + simName + ".SLURM.sh";
        script.writeToFile(PBSfileName);
       
        String slurmSHFile = rootPath + Utils.fileSeparator() + simName + ".SLURM.sh";
        String slurmSHFileR = rootPath + "/" + simName + ".SLURM.sh";
        if (isRemoteGatewayMachine
                || isRemoteAbinitMachine) {

            // Envoie du fichier SGE
            machine.putFile(slurmSHFile + " " + slurmSHFileR);

            if (Utils.osName().startsWith("Windows")) {
                machine.sendCommand("dos2unix " + slurmSHFileR);
            }
        }
        // lancement des commandes d'exécution de la simulation
        RetMSG msg = machine.sendCommand("sbatch " + slurmSHFileR);
        String retMsg = msg.getRetMSG();
        if(retMsg.startsWith("Submitted batch job"))
        {
            String jobIdS = retMsg.replace("Submitted batch job ", "").replaceAll("\n", "").replaceAll(" ","");
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
        
        updateStatus();
        MainFrame.printOUT("The job is currently : "+this.getStatusString());
    }
    
    @Override
    public void setScript(SubmissionScript script) {
        if(script instanceof SLURMScript)
        {
            super.setScript(script);
        }
        else
        {
            throw new IllegalArgumentException(""+script);
        }
    }
    
    @Override
    public void updateStatus()
    {
        if(getJobId() == -1) 
        {
            this.status = READY;
        } 
        
        Machine machine = MainFrame.getMachineDatabase().getMachine(this.getMachineName());
        if(!machine.isConnected())
            machine.connection();
        
        RetMSG msg = machine.sendCommand("sacct -j "+this.getJobId()+" -o \"JobId,State\"");

        String retMsg = msg.getRetMSG();

        if(retMsg.contains("COMPLETED"))
        {
            this.status = COMPLETED;
        }
        else if(retMsg.contains("CANCELLED"))
        {
            this.status = CANCELLED;
        }
        else if(retMsg.contains("FAIL"))
        {
            this.status = FAILED;
        }
        else if(retMsg.contains("PENDING"))
        {
            this.status = PENDING;
        }
        else if(retMsg.contains("RUNNING"))
        {
            this.status = RUNNING;
        }
        else
        {
            this.status = UNKNOWN;
        }
        
        if(this.status == UNKNOWN)
        {
            msg = machine.sendCommand("scontrol show job "+this.getJobId());

            retMsg = msg.getRetMSG();
            Pattern state = Pattern.compile("JobState=([A-Z]+)");

            Matcher m = state.matcher(retMsg);
            while (m.find()) {
                String status = m.group().trim();
                if(status.equals("Pending"))
                {
                    this.status = PENDING;
                }
                else if(status.equals("Running"))
                {
                    this.status = RUNNING;
                }
                else if(status.equals("Completed"))
                {
                    this.status = COMPLETED;
                }
            }
        }
    }
    
}
