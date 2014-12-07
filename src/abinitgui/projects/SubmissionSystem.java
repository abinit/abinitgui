/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abinitgui.projects;

import java.util.ArrayList;

public abstract class SubmissionSystem 
{
    protected Machine machine;
    
    public abstract RemoteJob submit(SubmissionScript script, String rootPath, String simName);
    
    public abstract ArrayList<RemoteJob> getRemoteJobs();
    
    public abstract void updateStatus(RemoteJob rj);
    
    public abstract void printInfos(RemoteJob rj);
    
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
