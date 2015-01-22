/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abinitgui.projects;

import abinitgui.core.MainFrame;
import abinitgui.core.RetMSG;
import abinitgui.core.Utils;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

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
        // Zombie jobs
        RetMSG msg = machine.sendCommand("qstat -u "+login+" -s z -xml",false);
        String retMsg = msg.getRetMSG();
        SAXBuilder sxb = new SAXBuilder();
        try {
            Document document = sxb.build(new StringReader(retMsg));
            Element root = document.getRootElement();
            System.out.println(root);
            Element root2 = root.getChild("queue_info");
            for(Object job : root2.getChildren())
            {
                Element value = (Element)job;
                String jIS = value.getChild("JB_job_number").getValue();
                RemoteJob rj = new RemoteJob();
                rj.setMachineName(machine.getName());
                rj.setJobId(Integer.parseInt(jIS));
                rj.setStatusString("Unknown");
                listJobs.add(rj);
            }
            root2 = root.getChild("job_info");
            for(Object job : root2.getChildren())
            {
                Element value = (Element)job;
                String jIS = value.getChild("JB_job_number").getValue();
                RemoteJob rj = new RemoteJob();
                rj.setMachineName(machine.getName());
                rj.setJobId(Integer.parseInt(jIS));
                rj.setStatusString("Unknown");
                listJobs.add(rj);
            }
            
        } catch (JDOMException | IOException e) {
            MainFrame.printERR(e.getMessage());
        }
        
        msg = machine.sendCommand("qstat -u "+login+" -s r -xml",false);
        retMsg = msg.getRetMSG();
        System.out.println(retMsg);
        try {
            Document document = sxb.build(new StringReader(retMsg));
            Element root = document.getRootElement();
            Element root2 = root.getChild("job_info");
            for(Object job : root2.getChildren())
            {
                Element value = (Element)job;
                String jIS = value.getChild("JB_job_number").getValue();
                RemoteJob rj = new RemoteJob();
                rj.setMachineName(machine.getName());
                rj.setJobId(Integer.parseInt(jIS));
                rj.setStatusString("Running");
                listJobs.add(rj);
            }
            root2 = root.getChild("queue_info");
            for(Object job : root2.getChildren())
            {
                Element value = (Element)job;
                String jIS = value.getChild("JB_job_number").getValue();
                RemoteJob rj = new RemoteJob();
                rj.setMachineName(machine.getName());
                rj.setJobId(Integer.parseInt(jIS));
                rj.setStatusString("Running");
                listJobs.add(rj);
            }
            
        } catch (JDOMException | IOException e) {
            MainFrame.printERR(e.getMessage());
        }
        
        
        
        msg = machine.sendCommand("qstat -u "+login+" -s p -xml",false);
        retMsg = msg.getRetMSG();
        try {
            Document document = sxb.build(new StringReader(retMsg));
            Element root = document.getRootElement();
            System.out.println(root);
            Element root2 = root.getChild("job_info");
            for(Object job : root2.getChildren())
            {
                Element value = (Element)job;
                String jIS = value.getChild("JB_job_number").getValue();
                RemoteJob rj = new RemoteJob();
                rj.setMachineName(machine.getName());
                rj.setJobId(Integer.parseInt(jIS));
                rj.setStatusString("Pending");
                listJobs.add(rj);
            } 
            root2 = root.getChild("queue_info");
            for(Object job : root2.getChildren())
            {
                Element value = (Element)job;
                String jIS = value.getChild("JB_job_number").getValue();
                RemoteJob rj = new RemoteJob();
                rj.setMachineName(machine.getName());
                rj.setJobId(Integer.parseInt(jIS));
                rj.setStatusString("Pending");
                listJobs.add(rj);
            }
            
        } catch (JDOMException | IOException e) {
            MainFrame.printERR(e.getMessage());
        }
        
        return listJobs;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateStatus(RemoteJob rj) {
         if(!machine.isConnected())
            machine.connection();
        
        if(!machine.isConnected())
        {
            MainFrame.printERR("Cannot connect to the machine !");
            return;
        }
        
        String login = null;
        if(machine.getType() == Machine.REMOTE_MACHINE || machine.getType() == Machine.GATEWAY_MACHINE)
        {
            login = machine.getRemoteConnect().getLogin();
        }
        if(login == null)
        {
            MainFrame.printERR("Cannot get login for the machine !");
            return;
        }

        rj.setStatus(RemoteJob.UNKNOWN);
        RetMSG msg = machine.sendCommand("qstat -u "+login+" -r -xml",false);
        String retMsg = msg.getRetMSG();
        SAXBuilder sxb = new SAXBuilder();
        try {
            Document document = sxb.build(new StringReader(retMsg));
            Element root = document.getRootElement();
            System.out.println(root);
            Element root2 = root.getChild("queue_info");
            for(Object job : root2.getChildren())
            {
                Element value = (Element)job;
                String jIS = value.getChild("JB_job_number").getValue();
                if(jIS.equals(""+rj.getJobId()))
                {
                    String status = value.getChild("state").getValue();
                    switch(status)
                    {
                        case "qw":
                            rj.setStatus(RemoteJob.PENDING);
                            break;
                        case "r":
                            rj.setStatus(RemoteJob.RUNNING);
                            break;
                        case "Eqw":
                            rj.setStatus(RemoteJob.FAILED);
                            break;
                    }
                }
            }
            root2 = root.getChild("job_info");
            for(Object job : root2.getChildren())
            {
                Element value = (Element)job;
                String jIS = value.getChild("JB_job_number").getValue();
                if(jIS.equals(""+rj.getJobId()))
                {
                    String status = value.getChild("state").getValue();
                    switch(status)
                    {
                        case "qw":
                            rj.setStatus(RemoteJob.PENDING);
                            break;
                        case "r":
                            rj.setStatus(RemoteJob.RUNNING);
                            break;
                        case "Eqw":
                            rj.setStatus(RemoteJob.FAILED);
                            break;
                    }
                }
            }
            
        } catch (JDOMException | IOException e) {
            MainFrame.printERR(e.getMessage());
        }
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
        
        if(msg.getRetCode() != 0)
        {
            msg = getMachine().sendCommand("qstat -j "+rj.getJobId());
            return msg.getRetMSG();
        }
        return msg.getRetMSG();
    }

    @Override
    public void kill(RemoteJob rj) {
        if(!machine.isConnected())
           getMachine().connection();
        
        getMachine().sendCommand("qdel "+rj.getJobId());
    }
    
}
