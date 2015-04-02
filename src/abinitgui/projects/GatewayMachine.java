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
