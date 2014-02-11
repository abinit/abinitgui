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
import abinitgui.core.MySFTP;
import abinitgui.core.MySSHTerm;
import abinitgui.core.RemoteExec;
import abinitgui.core.SSHTunnel;
import static abinitgui.projects.Machine.GATEWAY_MACHINE;

public class GatewayMachine extends RemoteMachine {

    public GatewayMachine()
    {
        super();
        this.type = GATEWAY_MACHINE;
    }
    
    @Override
    public void connection()
    {
        if(isConnected)
        {
            MainFrame.printERR("You are already connected!");
        }
        else
        {
            String gwHostname = gatewayConnect.getHost();
            String gwLogin = gatewayConnect.getLogin();
            if (!gwLogin.equals("") && !gwHostname.equals("")) {
                String abHostname = remoteConnect.getHost();
                String abLogin = remoteConnect.getLogin();
                if (!abLogin.equals("") && !abHostname.equals("")) {
                    // Début de la création du tunnel SSH
                    MainFrame.printOUT("Connecting to " + gwHostname + " as " + gwLogin + ".");
                    sshtun = new SSHTunnel(gwLogin, gwHostname, 22, abHostname, last_port++, 22);
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
                        MainFrame.printOUT("Connected to " + gwHostname + " as " + gwLogin + ".");
                        MainFrame.printOUT("Connecting to " + abHostname + " as " + abLogin + ".");
                        remoteExec = new RemoteExec(abLogin, "localhost", lport);
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
                            MainFrame.printOUT("Connected to " + abHostname + " as " + abLogin + ".");
                            // Le tunnel SSH a été créé avec succÃ¨s
                            //MainFrame.getConnectionToggleButton().setText("Disconnect");
                        } else {
                            lport = 0;
                            MainFrame.printERR("Could not connect to " + abHostname + " as " + abLogin + "!");
                            //MainFrame.getConnectionToggleButton().setSelected(false);
                            stopConnection();
                        }
                    } else {
                        lport = 0;
                        MainFrame.printERR("Could not connect to " + gwHostname + " as " + gwLogin + "!");
                        //MainFrame.getConnectionToggleButton().setSelected(false);
                        stopConnection();
                    }
                } else {
                    MainFrame.printERR("Please enter the ABINIT hostname AND corresponding login!");
                    //MainFrame.getConnectionToggleButton().setSelected(false);
                    stopConnection();
                }
            } else {
                MainFrame.printERR("Please enter the gateway hostname AND corresponding login!");
                //MainFrame.getConnectionToggleButton().setSelected(false);
                stopConnection();
            }
        }
    }
    
    @Override
    public void stopConnection()
    {
        if(!isConnected)
        {
            MainFrame.printERR("You are not connected!");
        }
        else
        {
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
        }
    }
    
    @Override
    public MySSHTerm newSSHTerm()
    {
        MySSHTerm ssh2 = null;
        if (lport != 0) {
            String host = "localhost";
            String user = remoteConnect.getLogin();
            String keyFile = remoteConnect.getKeyPath();
            String pass;
            if(remoteConnect.getPassword() != null)
            {
                pass = remoteConnect.getPassword().toString();
            }
            else
            {
                MainFrame.printERR("Please provide a password before connection!");
                return null;
            }
            if (remoteConnect.isUseKey()) {
                ssh2 = new MySSHTerm(host, lport, user, keyFile, pass);
            } else {
                ssh2 = new MySSHTerm(host, lport, user, pass);
            }
            Thread thread = new Thread(ssh2);
            thread.start();
        } else {
            MainFrame.printERR("The ssh tunnel is not working, please connect"
                    + " at the config. tab before!");
        }
        return ssh2;
    }
    
    @Override
    public MySFTP newSFTP() 
    {
        MySFTP client = null;
        if (lport != 0) {
            String host = "localhost";
            String user = remoteConnect.getLogin();
            String keyFile = remoteConnect.getKeyPath();
            String pass;
            if(remoteConnect.getPassword() != null)
            {
                pass = remoteConnect.getPassword().toString();
            }
            else
            {
                MainFrame.printERR("Please provide a password!");
                return null;
            }
            if (remoteConnect.isUseKey()) {
                client = new MySFTP(host, lport, user, keyFile, pass);
            } else {
                client = new MySFTP(host, lport, user, pass);
            }
            Thread thread = new Thread(client);
            thread.start();
        } else {
            MainFrame.printERR("The ssh tunnel is not working, please connect"
                    + " at the config. tab before!");
        }
        return client;
    }
    
}
