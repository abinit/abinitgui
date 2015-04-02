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

import java.util.ArrayList;

public abstract class SubmissionSystem 
{
    protected Machine machine;
    
    public abstract RemoteJob submit(SubmissionScript script, String rootPath, String simName);
    
    public abstract ArrayList<RemoteJob> getRemoteJobs();
    
    public abstract void updateStatus(RemoteJob rj);
    
    public abstract String printInfos(RemoteJob rj);
    
    public abstract void kill(RemoteJob rj);
    
    public void setMachine(Machine mach)
    {
        this.machine = mach;
    }
    
    public Machine getMachine()
    {
        return this.machine;
    }
}
