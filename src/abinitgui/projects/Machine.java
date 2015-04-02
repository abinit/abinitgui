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
    protected SubmissionSystem submissionSystem;
    protected SubmissionScript submissionScript;
    
    public final static int LOCAL_MACHINE = 0;
    public final static int REMOTE_MACHINE = 1;
    public final static int GATEWAY_MACHINE = 2;
    
    // Runtime arguments
    protected int lport;
    protected SubmissionSystem frontendSystem;
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
        this.frontendSystem = new SubmissionFrontendSystem(this);
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
    
    public RemoteJob submitSimulation(Simulation simulation, String rootPath, String name)
    {
        RemoteJob rj = null;
        if(simulation.getScript().getSystem().equals("Frontend"))
        {
            rj = frontendSystem.submit(simulation.getScript(), rootPath, name);
        }
        else if(simulation.getScript().getSystem().equals(this.getSubmissionScript().getSystem()))
        {
            rj = getSubmissionSystem().submit(simulation.getScript(), rootPath, name);
        }
        else
        {
            MainFrame.printOUT("The submission script can not be submitted to the selected machine");
        }
        return rj;
    }
    
    public abstract void connection();
    
    public abstract void stopConnection();

    public abstract void putFile(String parameters);
    
    public abstract void getFile(String parameters);
    
    public abstract MySSHTerm newSSHTerm();

    public abstract MySFTP newSFTP();
    
    public RetMSG sendCommand(String CMD)
    {
        return sendCommand(CMD, true);
    }
    
    public abstract RetMSG sendCommand(String CMD, boolean printMF);
    
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

    /**
     * @return the submissionSystem
     */
    public SubmissionSystem getSubmissionSystem() {
        return submissionSystem;
    }

    /**
     * @param submissionSystem the submissionSystem to set
     */
    public void setSubmissionSystem(SubmissionSystem submissionSystem) {
        this.submissionSystem = submissionSystem;
    }
}
