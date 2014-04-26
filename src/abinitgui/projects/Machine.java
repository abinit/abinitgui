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

import abinitgui.core.Exec;
import abinitgui.core.MainFrame;
import abinitgui.core.MySFTP;
import abinitgui.core.MySSHTerm;
import abinitgui.core.RetMSG;
import abinitgui.core.SSHTunnel;

/**
 * This class represents a Remote Machine with its configuration.
 */
public abstract class Machine 
{
    // BEANS arguments
    protected ConnectionInfo remoteConnect;
    protected ConnectionInfo gatewayConnect;
    protected int type;
    protected String abinitPath;
    protected String simulationPath;
    protected String name;
    protected SubmissionScript submissionScript;
    
    public final static int LOCAL_MACHINE = 0;
    public final static int REMOTE_MACHINE = 1;
    public final static int GATEWAY_MACHINE = 2;
    
    // Runtime arguments
    protected int lport;
    protected SSHTunnel sshtun;
    protected boolean isConnected;
    
    protected static int last_port;
    
    public Machine()
    {
        this.remoteConnect = null;
        this.gatewayConnect = null;
        this.submissionScript = null;
        this.type = -1;
        lport = 0;
        sshtun = null;
        isConnected = false;
        last_port = 2568;
    }
    
    public ConnectionInfo getRemoteConnect()
    {
        return remoteConnect;
    }
    
    public void setRemoteConnect(ConnectionInfo info)
    {
        this.remoteConnect = info;
    }
    
    public ConnectionInfo getGatewayConnect()
    {
        return gatewayConnect;
    }
    
    public SubmissionScript getSubmissionScript()
    {
        return submissionScript;
    }
    
    public void setSubmissionScript(SubmissionScript script)
    {
        this.submissionScript = script;
    }
    
    public void setGatewayConnect(ConnectionInfo info)
    {
        this.gatewayConnect = info;
    }
    
    public Exec getExec()
    {
        return null;
    }
    
    public int getType() 
    {
        return type;
    }

    public String getAbinitPath() 
    {
        return abinitPath;
    }
    
    public void setAbinitPath(String abinitPath)
    {
        this.abinitPath = abinitPath;
    }

    public String getSimulationPath() 
    {
        return simulationPath;
    }
    
    public void setSimulationPath(String simulationPath)
    {
        this.simulationPath = simulationPath;
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }
    
    @Override
    public String toString()
    {
        return this.name;
    }
    
    public boolean isConnected()
    {
        return isConnected;
    }
    
    public abstract void connection();
    
    public abstract void stopConnection();

    public abstract void putFile(String parameters);
    
    public abstract void getFile(String parameters);
    
    public abstract MySSHTerm newSSHTerm();

    public abstract MySFTP newSFTP();
    
    public abstract RetMSG sendCommand(String CMD);
    
    public abstract RetMSG sendCommand(String CMD[]);
    
    public abstract String getOutputFiles(String path);
    
    public void mkdir(String dir)
    {
        getExec().mkdir(dir);
    }
    
    public void createTree(String path)
    {
        Exec exec = getExec();
        
        if(exec != null)
        {
            getExec().createTree(path);
        }
        else
        {
            MainFrame.printERR("Exec is null!");
        }
    }
}
