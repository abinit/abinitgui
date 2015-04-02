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
import abinitgui.core.RemoteExec;
import abinitgui.core.RetMSG;
import abinitgui.core.Utils;
import static abinitgui.projects.Machine.REMOTE_MACHINE;

public class RemoteMachine extends Machine {
    
    protected RemoteExec remoteExec;
    
    public RemoteMachine()
    {
        super();
        this.type = REMOTE_MACHINE;
    }
    
    @Override
    public Exec getExec()
    {
        return remoteExec;
    }
 
    @Override
    public void connection()
    {
        if(isConnected)
        {
            MainFrame.printERR("You are already connected !");
        }
        else
        {
            String abHostname = remoteConnect.getHost();
            String abLogin = remoteConnect.getLogin();
            if (!abLogin.equals("") && !abHostname.equals("")) {
                MainFrame.printOUT("Connecting to " + abHostname + " as " + abLogin + ".");
                remoteExec = new RemoteExec(abLogin, abHostname, 22);
                String keyFile = remoteConnect.getKeyPath();
                if (keyFile.equals("")) {
                    keyFile = null;
                }
                if(remoteConnect.getPassword() == null)
                    remoteExec.setPrvkeyOrPwdOnly(keyFile, null, remoteConnect.isUseKey());
                else
                    remoteExec.setPrvkeyOrPwdOnly(keyFile, remoteConnect.getPassword().toString(), remoteConnect.isUseKey());
                if (remoteExec.start()) {
                    MainFrame.printOUT("Connected to " + abHostname + " as " + abLogin + ".");
                    //mf.getConnectionToggleButton().setText("Disconnect");
                    isConnected = true;
                } else {
                    MainFrame.printERR("Could not connect to " + abHostname + " as " + abLogin + "!");
                    //mf.getConnectionToggleButton().setSelected(false);
                    stopConnection();
                }
            } else {
                MainFrame.printERR("Please enter the ABINIT hostname AND corresponding login!");
                //mf.getConnectionToggleButton().setSelected(false);
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
                isConnected = false;
            }
        }
    }
    
    @Override
    public void putFile(String parameters)
    {
        RetMSG retmsg;
        retmsg = remoteExec.sendCommand("put " + parameters);
        if (retmsg.getRetCode() == RetMSG.SUCCES) {
            MainFrame.printOUT("Succes: " + retmsg.getCMD() + " => " +
                    Utils.removeEndl(retmsg.getRetMSG()) + ".");
        } else {
            MainFrame.printERR("Error (RetVal = " + retmsg.getRetCode() + "): " + retmsg.getRetMSG());
            MainFrame.printERR("Error: " +
                    Utils.removeEndl(retmsg.getRetMSG()) + " !");
        }
    }
    
    @Override
    public MySSHTerm newSSHTerm()
    {
        MySSHTerm ssh2;
        String host = remoteConnect.getHost();
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
            ssh2 = new MySSHTerm(host, 22, user, keyFile, pass);
        } else {
            ssh2 = new MySSHTerm(host, 22, user, pass);
        }
        Thread thread = new Thread(ssh2);
        thread.start();
        
        return ssh2;
    }
    
    @Override
    public MySFTP newSFTP() {
        MySFTP client;
        String host = remoteConnect.getHost();
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
            client = new MySFTP(host, 22, user, keyFile, pass);
        } else {
            client = new MySFTP(host, 22, user, pass);
        }
        
        Thread thread = new Thread(client);
        thread.start();
        
        return client;
    }
    
    @Override
    public RetMSG sendCommand(String CMD, boolean printMF)
    {
        RetMSG retmsg;
        retmsg = remoteExec.sendCommand(CMD);
        if(printMF)
        {
            if (retmsg.getRetCode() == RetMSG.SUCCES) {
                MainFrame.printOUT("Succes: " + retmsg.getCMD() + " => " +
                        Utils.removeEndl(retmsg.getRetMSG()) + ".");
            } else {
                MainFrame.printERR("Error (Code = " + retmsg.getRetCode() + "): " +
                        Utils.removeEndl(retmsg.getRetMSG()) + " !");
            }
        }
        return retmsg;
    }
    
    
    
    @Override
    public RetMSG sendCommand(String CMD[])
    {
        RetMSG retmsg;
        retmsg = remoteExec.sendCommand(CMD);
        if (retmsg.getRetCode() == RetMSG.SUCCES) {
            MainFrame.printOUT("Succes: " + retmsg.getCMD() + " => " +
                    Utils.removeEndl(retmsg.getRetMSG()) + ".");
        } else {
            MainFrame.printERR("Error (Code = " + retmsg.getRetCode() + "): " +
                    Utils.removeEndl(retmsg.getRetMSG()) + " !");
        }
        return retmsg;
    }
    
    @Override
    public void getFile(String parameters) {
        RetMSG retmsg;
        retmsg = remoteExec.sendCommand("get " + parameters);
        if (retmsg.getRetCode() == RetMSG.SUCCES) {
            MainFrame.printOUT("Succes: " + retmsg.getCMD() + " => " +
                    Utils.removeEndl(retmsg.getRetMSG()) + ".");
        } else {
            MainFrame.printERR("Error (Code = " + retmsg.getRetCode() + "): " +
                    Utils.removeEndl(retmsg.getRetMSG()) + " !");
        }
    }
    
    @Override
    public String getOutputFiles(String dir) {
        String CMD = "ls " + dir;
        RetMSG retmsg;
        retmsg = remoteExec.sendCommand(CMD);
        if (retmsg.getRetCode() == RetMSG.SUCCES) {
            MainFrame.printOUT("Succes: " + retmsg.getCMD());
            return Utils.removeEndl(retmsg.getRetMSG());
        } else {
            MainFrame.printERR("Error (Code = " + retmsg.getRetCode() + "): " +
                    Utils.removeEndl(retmsg.getRetMSG()) + " !");
            return "";
        }
    }
}
