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

import abinitgui.core.MainFrame;
import abinitgui.core.RetMSG;

public class RemoteJob 
{
    public final static int READY = 0;
    public final static int RUNNING = 1;
    public final static int COMPLETED = 2;
    public final static int PENDING = 3;
    public final static int UNKNOWN = 4;
    public final static int FAILED = 5;
    public final static int CANCELLED = 6;
    
    protected SubmissionScript script;
    
    protected int status;
    
    protected String machineName;
    
    protected int jobId;
    
    public void updateStatus()
    {
        if(getMachineName() == null)
        {
            MainFrame.printOUT("No machine associated to this job");
            return;
        } 
        
        Machine mach = MainFrame.getMachineDatabase().getMachine(machineName);
        
        if(mach != null)
        {
            mach.getSubmissionSystem().updateStatus(this);
        }
    }
    
    public String getMachineName()
    {
        return machineName;
    }
    
    public void setMachineName(String machineName)
    {
        this.machineName = machineName;
    }
    
    public String toString()
    {
        String s = "";
        if(this.script != null)
        {
            s = s + this.script.getSimName();
        }
        else
        {
            s = s + "Job "+this.getJobId();
        }
        
        s = s + " ("+this.getStatusString()+")";
        return s;
    }

    /**
     * @return the script
     */
    public SubmissionScript getScript() {
        return script;
    }

    /**
     * @param script the script to set
     */
    public void setScript(SubmissionScript script) {
        this.script = script;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }
    
    /**
     * Try to kill the job on the remote machine !
     */
    public void kill() {
        
        if(getMachineName() == null)
        {
            MainFrame.printOUT("No machine associated to this job");
            return;
        } 
        
        Machine mach = MainFrame.getMachineDatabase().getMachine(machineName);
        if(mach != null)
        {
            mach.getSubmissionSystem().kill(this);
        }
        
    }
    
    public String getStatusString() {
        switch(status)
        {
            case PENDING:
                return "Submitted";
            case READY:
                return "Ready";
            case COMPLETED:
                return "Completed";
            case RUNNING:
                return "Running";
            case UNKNOWN:
                return "Unknown";
            case FAILED:
                return "Failed";
            case CANCELLED:
                return "Cancelled";
            default:
                return "Unknown";
        }
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }
    
    /**
     * Print to main output the infos on the job !
     */
    public String printInfos()
    {
        if(getMachineName() == null)
        {
            MainFrame.printOUT("No machine associated to this job");
            return null;
        } 
        
        Machine mach = MainFrame.getMachineDatabase().getMachine(machineName);
        if(mach != null)
        {
            return mach.getSubmissionSystem().printInfos(this);
        }
        return null;
    }

    /**
     * @return the jobId
     */
    public int getJobId() {
        return jobId;
    }

    /**
     * @param jobId the jobId to set
     */
    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public void setStatusString(String stat) 
    {
        String statS = stat.toUpperCase();
        if(statS.contains("RUNNING"))
        {
            setStatus(RemoteJob.RUNNING);
        }
        else if(statS.contains("COMPLETED"))
        {
            setStatus(RemoteJob.COMPLETED);
        }
        else if(statS.contains("FAILED"))
        {
            setStatus(RemoteJob.FAILED);
        }
        else if(statS.contains("CANCELLED"))
        {
            setStatus(RemoteJob.CANCELLED);
        }
        else if(statS.contains("PENDING") || statS.contains("SUBMITTED"))
        {
            setStatus(RemoteJob.PENDING);
        }
        else if(statS.contains("READY"))
        {
            setStatus(RemoteJob.READY);
        }
        else
        {
            setStatus(RemoteJob.UNKNOWN);
        }
    }
    
    
}
