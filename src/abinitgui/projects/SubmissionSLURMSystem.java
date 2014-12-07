/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abinitgui.projects;

import abinitgui.core.MainFrame;
import abinitgui.core.RetMSG;
import abinitgui.core.Utils;
import static abinitgui.projects.RemoteJob.CANCELLED;
import static abinitgui.projects.RemoteJob.COMPLETED;
import static abinitgui.projects.RemoteJob.FAILED;
import static abinitgui.projects.RemoteJob.PENDING;
import static abinitgui.projects.RemoteJob.READY;
import static abinitgui.projects.RemoteJob.RUNNING;
import static abinitgui.projects.RemoteJob.UNKNOWN;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Yannick
 */
public class SubmissionSLURMSystem extends SubmissionSystem 
{
    public SubmissionSLURMSystem()
    {
        
    }
    
    public SubmissionSLURMSystem(Machine machine)
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
        
        String PBSfileName = rootPath + Utils.fileSeparator() + simName + ".SLURM.sh";
        script.writeToFile(PBSfileName);
       
        String slurmSHFile = rootPath + Utils.fileSeparator() + simName + ".SLURM.sh";
        String slurmSHFileR = rootPath + "/" + simName + ".SLURM.sh";
        if (isRemoteGatewayMachine
                || isRemoteAbinitMachine) {

            // Envoie du fichier SGE
            getMachine().putFile(slurmSHFile + " " + slurmSHFileR);

            if (Utils.osName().startsWith("Windows")) {
                getMachine().sendCommand("dos2unix " + slurmSHFileR);
            }
        }
        // lancement des commandes d'exécution de la simulation
        RetMSG msg = getMachine().sendCommand("sbatch " + slurmSHFileR);
        String retMsg = msg.getRetMSG();
        if(retMsg.startsWith("Submitted batch job"))
        {
            String jobIdS = retMsg.replace("Submitted batch job ", "").replaceAll("\n", "").replaceAll(" ","");
            int id = Integer.parseInt(jobIdS);
            rj.setJobId(id);
            System.out.println("my job id is : "+rj.getJobId());
        }
        else
        {
            MainFrame.printERR("Error : "+retMsg);
        }
        System.out.println("id = "+rj.getJobId());
        
        return rj;
    }

    @Override
    public ArrayList<RemoteJob> getRemoteJobs() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateStatus(RemoteJob rj) {
        System.out.println("updating Status !");
        int status = UNKNOWN;
        if(rj.getJobId() == -1) 
        {
            status = READY;
        } 
        
        if(!machine.isConnected())
            getMachine().connection();
        
        RetMSG msg = getMachine().sendCommand("sacct -j "+rj.getJobId()+" -o \"JobId,State\"");

        String retMsg = msg.getRetMSG();

        if(retMsg.contains("COMPLETED"))
        {
            status = COMPLETED;
        }
        else if(retMsg.contains("CANCELLED"))
        {
            status = CANCELLED;
        }
        else if(retMsg.contains("FAIL"))
        {
            status = FAILED;
        }
        else if(retMsg.contains("PENDING"))
        {
            status = PENDING;
        }
        else if(retMsg.contains("RUNNING"))
        {
            status = RUNNING;
        }
        else
        {
            status = UNKNOWN;
        }
        
        if(status == UNKNOWN)
        {
            msg = getMachine().sendCommand("scontrol show job "+rj.getJobId());

            retMsg = msg.getRetMSG();
            Pattern state = Pattern.compile("JobState=([A-Z]+)");

            Matcher m = state.matcher(retMsg);
            while (m.find()) {
                String statusS = m.group().trim();
                if(statusS.equals("Pending"))
                {
                    status = PENDING;
                }
                else if(statusS.equals("Running"))
                {
                    status = RUNNING;
                }
                else if(statusS.equals("Completed"))
                {
                    status = COMPLETED;
                }
            }
        }
        
        rj.setStatus(status);
        
        System.out.println("rj.status = "+rj.getStatusString());
    }

    @Override
    public void printInfos(RemoteJob rj) 
    {
        if(rj.getJobId() == -1) 
        {
            MainFrame.printOUT("This job has not been submitted yet !");
            return;
        } 
        
        if(rj.getJobId() == 0)
        {
            MainFrame.printERR("Job ID is 0 !");
            return;
        }
        
        if(!machine.isConnected())
            getMachine().connection();
        
        updateStatus(rj);
        MainFrame.printOUT("The job is currently : "+rj.getStatusString());

    }

    @Override
    public void kill(RemoteJob rj) {
        if(!machine.isConnected())
            getMachine().connection();
        
        getMachine().sendCommand("scancel "+rj.getJobId());
    }
    
}
