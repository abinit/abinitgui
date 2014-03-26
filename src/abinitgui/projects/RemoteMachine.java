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
    public RetMSG sendCommand(String CMD)
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
