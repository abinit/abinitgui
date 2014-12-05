/*
 Copyright (c) 2009-2014 Flavio Miguel ABREU ARAUJO (flavio.abreuaraujo@uclouvain.be)
                         Yannick GILLET (yannick.gillet@uclouvain.be)

Université catholique de Louvain, Louvain-la-Neuve, Belgium
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

1. Redistributions of source code must retain the above copyright
notice, this list of conditions, and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright
notice, this list of conditions, and the disclaimer that follows
these conditions in the documentation and/or other materials
provided with the distribution.

3. The names of the author may not be used to endorse or promote
products derived from this software without specific prior written
permission.

In addition, we request (but do not require) that you include in the
end-user documentation provided with the redistribution and/or in the
software itself an acknowledgement equivalent to the following:
"This product includes software developed by the
Abinit Project (http://www.abinit.org/)."

THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED.  IN NO EVENT SHALL THE JDOM AUTHORS OR THE PROJECT
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
SUCH DAMAGE.

For more information on the Abinit Project, please see
<http://www.abinit.org/>.
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
        // TODO !
    }
    
    public String getMachineName()
    {
        return machineName;
    }
    
    public void setMachineName(String machineName)
    {
        this.machineName = machineName;
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
        MainFrame.printERR("Nothing to do !");
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
     * Create the submission scripts and submits it to the remote machine
     * @param rootPath Path where the simulation should be run
     * @param simName Simulation name
     */
    public void submit(String rootPath, String simName)
    {
        ;
    }
    
    /**
     * Print to main output the infos on the job !
     */
    public void printInfos()
    {
        if(getMachineName() == null)
        {
            MainFrame.printOUT("No machine associated to this job");
            return;
        } 
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
    
    
}
