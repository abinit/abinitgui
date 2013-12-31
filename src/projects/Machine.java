/*
 Copyright (c) 2009-2013 Flavio Miguel ABREU ARAUJO (flavio.abreuaraujo@uclouvain.be)
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

package projects;

import core.MainFrame;
import core.MySFTP;
import core.MySSHTerm;
import core.RemoteExec;
import core.SSHTunnel;
import java.io.FileReader;
import java.io.IOException;
import org.yaml.snakeyaml.Yaml;

/**
 * This class represents a Remote Machine with its configuration
 * @author yannick
 */
public class Machine 
{
    // BEANS arguments
    private ConnectionInfo remoteConnect;
    private ConnectionInfo gatewayConnect;
    private int type;
    private String abinitPath;
    private String simulationPath;
    private String name;
    private SubmissionScript submissionScript;
    
    public final static int LOCAL_MACHINE = 0;
    public final static int REMOTE_MACHINE = 1;
    public final static int GATEWAY_MACHINE = 2;
    
    // Runtime arguments
    private RemoteExec remoteExec;
    private int lport;
    private SSHTunnel sshtun;
    private boolean isConnected;
    
    private static int last_port;
    
    public Machine()
    {
        this.remoteConnect = null;
        this.gatewayConnect = null;
        this.remoteExec = null;
        this.submissionScript = null;
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
    
    public void setGatewayConnect(ConnectionInfo info)
    {
        this.gatewayConnect = info;
    }
    
    public RemoteExec getRemoteExec()
    {
        return remoteExec;
    }
    
    // So that remoteExec is not inside YAML !
//    
//    public void setRemoteExec(RemoteExec remoteExec)
//    {
//        this.remoteExec = remoteExec;
//    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String toString()
    {
        return this.name;
    }
    
    public boolean isConnected()
    {
        return isConnected;
    }
    
    public void connection(MainFrame mf)
    {
        if(isConnected)
        {
            mf.printERR("You are already connected !");
        }
        else
        {
            if (type == GATEWAY_MACHINE) {
                String gwHostname = gatewayConnect.getHost();
                String gwLogin = gatewayConnect.getLogin();
                if (!gwLogin.equals("") && !gwHostname.equals("")) {
                    String abHostname = remoteConnect.getHost();
                    String abLogin = remoteConnect.getLogin();
                    if (!abLogin.equals("") && !abHostname.equals("")) {
                        // Début de la création du tunnel SSH
                        mf.printOUT("Connecting to " + gwHostname + " as " + gwLogin + ".");
                        sshtun = new SSHTunnel(mf, gwLogin, gwHostname, 22, abHostname, last_port++, 22);
                        String keyFile = gatewayConnect.getKeyPath();
                        if (keyFile == null || keyFile.equals("")) {
                            keyFile = null;
                        }
                        if(gatewayConnect.getPassword() == null)
                            sshtun.setPrvkeyOrPwdOnly(keyFile, null, gatewayConnect.isUseKey());
                        else
                            sshtun.setPrvkeyOrPwdOnly(keyFile, gatewayConnect.getPassword().toString(), gatewayConnect.isUseKey());
                        lport = sshtun.start();
                        if (lport > 0 && lport < 65536) {
                            mf.printOUT("Connected to " + gwHostname + " as " + gwLogin + ".");
                            mf.printOUT("Connecting to " + abHostname + " as " + abLogin + ".");
                            remoteExec = new RemoteExec(mf, abLogin, "localhost", lport);
                            keyFile = remoteConnect.getKeyPath();
                            if (keyFile == null || keyFile.equals("")) {
                                keyFile = null;
                            }
                            if(remoteConnect.getPassword() == null)
                                remoteExec.setPrvkeyOrPwdOnly(keyFile, null, remoteConnect.isUseKey());
                            else
                                remoteExec.setPrvkeyOrPwdOnly(keyFile, remoteConnect.getPassword().toString(), remoteConnect.isUseKey());
                            if (remoteExec.start()) {
                                isConnected = true;
                                mf.printOUT("Connected to " + abHostname + " as " + abLogin + ".");
                                // Le tunnel SSH a été créé avec succÃ¨s
                                //mf.getConnectionToggleButton().setText("Disconnect");
                            } else {
                                lport = 0;
                                mf.printERR("Could not connect to " + abHostname + " as " + abLogin + " !");
                                //mf.getConnectionToggleButton().setSelected(false);
                                stopConnection(mf);
                            }
                        } else {
                            lport = 0;
                            mf.printERR("Could not connect to " + gwHostname + " as " + gwLogin + " !");
                            //mf.getConnectionToggleButton().setSelected(false);
                            stopConnection(mf);
                        }
                    } else {
                        mf.printERR("Please enter the ABINIT hostname AND corresponding login !");
                        //mf.getConnectionToggleButton().setSelected(false);
                        stopConnection(mf);
                    }
                } else {
                    mf.printERR("Please enter the gateway hostname AND corresponding login !");
                    //mf.getConnectionToggleButton().setSelected(false);
                    stopConnection(mf);
                }
            } else if (type == REMOTE_MACHINE) {
                String abHostname = remoteConnect.getHost();
                String abLogin = remoteConnect.getLogin();
                if (!abLogin.equals("") && !abHostname.equals("")) {
                    mf.printOUT("Connecting to " + abHostname + " as " + abLogin + ".");
                    remoteExec = new RemoteExec(mf, abLogin, abHostname, 22);
                    String keyFile = remoteConnect.getKeyPath();
                    if (keyFile.equals("")) {
                        keyFile = null;
                    }
                    if(remoteConnect.getPassword() == null)
                        remoteExec.setPrvkeyOrPwdOnly(keyFile, null, remoteConnect.isUseKey());
                    else
                        remoteExec.setPrvkeyOrPwdOnly(keyFile, remoteConnect.getPassword().toString(), remoteConnect.isUseKey());
                    if (remoteExec.start()) {
                        mf.printOUT("Connected to " + abHostname + " as " + abLogin + ".");
                        //mf.getConnectionToggleButton().setText("Disconnect");
                        isConnected = true;
                    } else {
                        mf.printERR("Could not connect to " + abHostname + " as " + abLogin + " !");
                        //mf.getConnectionToggleButton().setSelected(false);
                        stopConnection(mf);
                    }
                } else {
                    mf.printERR("Please enter the ABINIT hostname AND corresponding login !");
                    //mf.getConnectionToggleButton().setSelected(false);
                    stopConnection(mf);
                }
            } else if (type == LOCAL_MACHINE) {
                // Pas besoin en local
            } else { // Le choix n'a pas été fait
                mf.printERR("Choose a destination option please at config. tab !");
            }
        }
    }
    
    public void stopConnection(MainFrame mf)
    {
        if(!isConnected)
        {
            mf.printERR("You are not connected !");
        }
        else
        {
            if (type == GATEWAY_MACHINE) {
                if (remoteExec != null) {
                    remoteExec.stop();
                    remoteExec = null;
                }
                if (sshtun != null) {
                    sshtun.stop();
                    sshtun = null;
                }
                isConnected = false;
                lport = 0;
            } else if (type == REMOTE_MACHINE) {
                if (remoteExec != null) {
                    remoteExec.stop();
                    remoteExec = null;
                    isConnected = false;
                }
            } else if (type == LOCAL_MACHINE) {
                // Pas besoin en local
            } else { // Le choix n'a pas été fait
                mf.printERR("Choose a destination option please at config. tab !");
            }
        }
    }
    
    public MySSHTerm newSSHTerm(MainFrame mf)
    {
        MySSHTerm ssh2 = null;
        if (type == GATEWAY_MACHINE) {;
            if (lport != 0) {
                String host = "localhost";
                String user = remoteConnect.getLogin();
                String keyFile = remoteConnect.getKeyPath();
                String pass = null;
                if(remoteConnect.getPassword() != null)
                {
                    pass = remoteConnect.getPassword().toString();
                }
                else
                {
                    mf.printERR("Please provide a password before connection");
                    return null;
                }
                if (remoteConnect.isUseKey()) {
                    ssh2 = new MySSHTerm(mf, host, lport, user, keyFile, pass);
                } else {
                    ssh2 = new MySSHTerm(mf, host, lport, user, pass);
                }
                Thread thread = new Thread(ssh2);
                thread.start();
            } else {
                mf.printERR("The ssh tunnel is not working, please connect at the config. tab before !");
            }
        } else if (type == REMOTE_MACHINE) {
            String host = remoteConnect.getHost();
            String user = remoteConnect.getLogin();
            String keyFile = remoteConnect.getKeyPath();
            String pass = remoteConnect.getPassword().toString();
            if(remoteConnect.getPassword() != null)
            {
                pass = remoteConnect.getPassword().toString();
            }
            else
            {
                mf.printERR("Please provide a password before connection");
                return null;
            }
            if (remoteConnect.isUseKey()) {
                ssh2 = new MySSHTerm(mf, host, 22, user, keyFile, pass);
            } else {
                ssh2 = new MySSHTerm(mf, host, 22, user, pass);
            }
            Thread thread = new Thread(ssh2);
            thread.start();
        } else if (type == LOCAL_MACHINE) {
            mf.printERR("To connect to the local host, please choose the remote option"
                    + " where you specify localhost as hostname !");
        } else { // Le choix n'a pas été fait
            mf.printERR("Choose a destination option please at config. tab !");
        }
        return ssh2;
    }

    public MySFTP newSFTP(MainFrame mf) {
        MySFTP client = null;
        if (type == GATEWAY_MACHINE) {
            if (lport != 0) {
                String host = "localhost";
                String user = remoteConnect.getLogin();
                String keyFile = remoteConnect.getKeyPath();
                String pass = null;
                if(remoteConnect.getPassword() != null)
                {
                    pass = remoteConnect.getPassword().toString();
                }
                else
                {
                    mf.printERR("Please provide a password !");
                    return null;
                }
                if (remoteConnect.isUseKey()) {
                    client = new MySFTP(mf, host, lport, user, keyFile, pass);
                } else {
                    client = new MySFTP(mf, host, lport, user, pass);
                }
                Thread thread = new Thread(client);
                thread.start();
            } else {
                mf.printERR("The ssh tunnel is not working, please connect at the config. tab before !");
            }
        } else if (type == REMOTE_MACHINE) {
            String host = remoteConnect.getHost();
            String user = remoteConnect.getLogin();
            String keyFile = remoteConnect.getKeyPath();
            String pass = null;
            if(remoteConnect.getPassword() != null)
            {
                pass = remoteConnect.getPassword().toString();
            }
            else
            {
                mf.printERR("Please provide a password !");
                return null;
            }
            if (remoteConnect.isUseKey()) {
                client = new MySFTP(mf, host, 22, user, keyFile, pass);
            } else {
                client = new MySFTP(mf, host, 22, user, pass);
            }
            Thread thread = new Thread(client);
            thread.start();
        } else if (type == LOCAL_MACHINE) {
            mf.printERR("To connect to the local host, please choose the remote option"
                    + " where you specify localhost as hostname !");
        } else { // Le choix n'a pas été fait
            mf.printERR("Choose a destination option please at config. tab !");
        }
        
        return client;
    }
}
