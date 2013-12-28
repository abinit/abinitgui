/*
 Copyright (c) 2009-2013 Flavio Miguel ABREU ARAUJO (flavio.abreuaraujo@uclouvain.be)
                         Yannick GILLET (yannick.gillet@uclouvain.be)

Universit� catholique de Louvain, Louvain-la-Neuve, Belgium
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

package projects;

import core.RemoteExec;
import java.io.FileReader;
import java.io.IOException;
import org.yaml.snakeyaml.Yaml;

/**
 * This class represents a Remote Machine with its configuration
 * @author yannick
 */
public class Machine 
{
    private ConnectionInfo remoteConnect;
    private ConnectionInfo gatewayConnect;
    private RemoteExec remoteExec;
    private int type;
    private String abinitPath;
    private String simulationPath;
    
    public final static int LOCAL_MACHINE = 0;
    public final static int REMOTE_MACHINE = 1;
    public final static int GATEWAY_MACHINE = 2;
    
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
    
    public void setGatewayConnect(ConnectionInfo info)
    {
        this.gatewayConnect = info;
    }
    
    public RemoteExec getRemoteExec()
    {
        return remoteExec;
    }
    
    public void setRemoteExec(RemoteExec remoteExec)
    {
        this.remoteExec = remoteExec;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    public void dumpToFile(String fileName)
    {
        Yaml yaml = new Yaml();
        
        yaml.dump(this);
    }
    
    public void loadFromFile(String fileName) throws IOException
    {
        Yaml yaml = new Yaml();
        
        Object o = yaml.load(new FileReader(fileName));
        
        if(o instanceof Machine)
        {
            Machine rm = (Machine)o;
            this.setRemoteConnect(rm.remoteConnect);
            this.setGatewayConnect(rm.gatewayConnect);
            this.setRemoteExec(rm.remoteExec);
        }
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
}
