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
public class RemoteFrontendJob extends RemoteJob
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
            mach.putFile(SHFile + " " + SHFileR);

            if (Utils.osName().startsWith("Windows")) {
                mach.sendCommand("dos2unix " + SHFileR);
            }
        }
        // lancement des commandes d'exécution de la simulation
        mach.sendCommand("bash "+SHFileR);
    }
    
    @Override
    public void printInfos()
    {
        MainFrame.printERR("Status of frontend jobs is not yet implemented !");
    }
    
    @Override
    public void setScript(SubmissionScript script) {
        if(script instanceof FrontendScript)
        {
            super.setScript(script);
        }
        else
        {
            throw new IllegalArgumentException(""+script);
        }
    }
    
}
