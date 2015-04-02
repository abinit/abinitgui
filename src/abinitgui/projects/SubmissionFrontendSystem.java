/*
 AbinitGUI - Created in 2009
 
 Copyright (c) 2009-2015 Flavio Miguel ABREU ARAUJO (abreuaraujo.flavio@gmail.com)
                         Yannick GILLET (yannick.gillet@hotmail.com)

 Université catholique de Louvain, Louvain-la-Neuve, Belgium
 All rights reserved.

 AbinitGUI is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 AbinitGUI is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with AbinitGUI.  If not, see <http://www.gnu.org/licenses/>.

 For more information on the project, please see
 <http://gui.abinit.org/>.
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
