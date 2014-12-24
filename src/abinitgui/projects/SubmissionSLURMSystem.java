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
        }
        else
        {
            MainFrame.printERR("Error : "+retMsg);
        }
        
        return rj;
    }

    @Override
    public ArrayList<RemoteJob> getRemoteJobs() {
        ArrayList<RemoteJob> listJobs = new ArrayList<>();
        
        
        if(!machine.isConnected())
            machine.connection();
        
        if(!machine.isConnected())
        {
            MainFrame.printERR("Cannot connect to the machine !");
            return null;
        }
        
        String login = null;
        if(machine.getType() == Machine.REMOTE_MACHINE || machine.getType() == Machine.GATEWAY_MACHINE)
        {
            login = machine.getRemoteConnect().getLogin();
        }
        if(login == null)
        {
            MainFrame.printERR("Cannot get login for the machine !");
            return null;
        }
        RetMSG msg = machine.sendCommand("sacct --noheader -P -u "+login+" -o \"JobId,State\"",false);

        String retMsg = msg.getRetMSG();
        
        String[] lines = retMsg.split("\n");
        
        for(String lin : lines)
        {
            String[] tab = lin.split("\\|");
            if(tab.length != 2)
                continue;
            String idS = tab[0];
            String statS = tab[1];
            
            try{
                int id = Integer.parseInt(idS);
                RemoteJob rj = new RemoteJob();
                rj.setMachineName(machine.getName());
                rj.setJobId(id);
                rj.setStatusString(statS);
                listJobs.add(rj);
            } catch(NumberFormatException exc)
            {
                continue;
            }
        }
        
        return listJobs;

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
        
        RetMSG msg = getMachine().sendCommand("sacct --noheader -P -j "+rj.getJobId()+" -o \"JobId,State\"",false);

        String retMsg = msg.getRetMSG();
        String[] lines = retMsg.split("\n");
        for(String lin : lines)
        {
            String[] tab = lin.split("\\|");
            String idS = tab[0];
            String statS = tab[1];
            
            try{
                int id = Integer.parseInt(idS);
                System.out.println("tab = "+idS+", "+statS);
                if(rj.getJobId() == id)
                {
                    rj.setStatusString(statS);
                }
            } catch(NumberFormatException exc)
            {
            }
        }
        
        if(rj.getStatus() == UNKNOWN)
        {
            String command = "scontrol show job "+rj.getJobId();
            msg = getMachine().sendCommand("scontrol show job "+rj.getJobId(),false);

            retMsg = msg.getRetMSG();
            Pattern state = Pattern.compile("JobState=([A-Z]+)");

            Matcher m = state.matcher(retMsg);
            while (m.find()) {
                String statusS = m.group().trim();
                rj.setStatusString(statusS);
            }
        }
        
        System.out.println("rj.status = "+rj.getStatusString());
    }

    @Override
    public String printInfos(RemoteJob rj) 
    {
        if(rj.getJobId() == -1) 
        {
            return "This job has not been submitted yet !";
        } 
        
        if(rj.getJobId() == 0)
        {
            return "Job ID is 0 !";
        }
        
        if(!machine.isConnected())
            getMachine().connection();
        
        updateStatus(rj);
        
        String command = "scontrol show job "+rj.getJobId();
        RetMSG msg = getMachine().sendCommand(command,false);
        
        if(msg.getRetCode() == 0)
        {
            return msg.getRetMSG();
        }
        else
        {
            command = "sacct -j "+rj.getJobId();
            msg = getMachine().sendCommand(command, false);
            
            if(msg.getRetCode() == 0)
            {
                return msg.getRetMSG();
            }
            else
            {
                return "The status of the job is : "+rj.getStatusString();
            }
        }
            
        
    }

    @Override
    public void kill(RemoteJob rj) {
        if(!machine.isConnected())
            getMachine().connection();
        
        getMachine().sendCommand("scancel "+rj.getJobId());
    }
    
}
