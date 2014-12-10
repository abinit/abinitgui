/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abinitgui.projects;

import abinitgui.core.Utils;
import java.util.ArrayList;

/**
 *
 * @author Yannick
 */
public class SubmissionFrontendSystem extends SubmissionSystem
{
    public SubmissionFrontendSystem()
    {
        
    }
    
    public SubmissionFrontendSystem(Machine machine)
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
        
        String SHfileName = rootPath + Utils.fileSeparator() + simName + ".sh";
        script.writeToFile(SHfileName);
        
        String SHFile = rootPath + Utils.fileSeparator() + simName + ".sh";
        String SHFileR = rootPath + "/" + simName + ".sh";

        if (isRemoteGatewayMachine
                || isRemoteAbinitMachine) {
            /*if (Utils.osName().startsWith("Windows")) {
             Utils.dos2unix(new File(SHFileR));
             }*/
            // Envoie du fichier BASH
            getMachine().putFile(SHFile + " " + SHFileR);

            if (Utils.osName().startsWith("Windows")) {
                getMachine().sendCommand("dos2unix " + SHFileR);
            }
        }
        // lancement des commandes d'exécution de la simulation
        getMachine().sendCommand("bash "+SHFileR);
        
        return rj;
    }

    @Override
    public ArrayList<RemoteJob> getRemoteJobs() {
        return null;
    }

    @Override
    public void updateStatus(RemoteJob rj) {
        return;
    }

    @Override
    public String printInfos(RemoteJob rj) {
        return "Frontend job !";
    }

    @Override
    public void kill(RemoteJob rj) {
        return;
    }
    
}
