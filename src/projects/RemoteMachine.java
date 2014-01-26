/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projects;

import core.Exec;
import core.MainFrame;
import core.MySFTP;
import core.MySSHTerm;
import core.RemoteExec;
import core.RetMSG;
import core.SSHTunnel;
import core.Utils;
import static projects.Machine.GATEWAY_MACHINE;
import static projects.Machine.LOCAL_MACHINE;
import static projects.Machine.REMOTE_MACHINE;

/**
 *
 * @author yannick
 */
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
    public void connection(MainFrame mf)
    {
        if(isConnected)
        {
            mf.printERR("You are already connected !");
        }
        else
        {
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
        }
    }
    
    @Override
    public void stopConnection(MainFrame mf)
    {
        if(!isConnected)
        {
            mf.printERR("You are not connected !");
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
    
    public void putFile(String parameters, MainFrame mf)
    {
        RetMSG retmsg;
        retmsg = remoteExec.sendCommand("put " + parameters);
        if (retmsg.getRetCode() == RetMSG.SUCCES) {
            mf.printOUT("Succes: " + retmsg.getCMD() + " => " + Utils.removeEndl(retmsg.getRetMSG()) + ".");
        } else {
            //printERR("Error (RetVal = " + retmsg.getRetCode() + "): " + retmsg.getRetMSG());
            mf.printERR("Error: " + Utils.removeEndl(retmsg.getRetMSG()) + " !");
        }
    }
    
    @Override
    public MySSHTerm newSSHTerm(MainFrame mf)
    {
        MySSHTerm ssh2 = null;
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
        
        return ssh2;
    }
    
    @Override
    public MySFTP newSFTP(MainFrame mf) {
        MySFTP client = null;
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
        
        return client;
    }
    
    @Override
    public RetMSG sendCommand(String CMD, MainFrame mf)
    {
        RetMSG retmsg;
        retmsg = remoteExec.sendCommand(CMD);
        if (retmsg.getRetCode() == RetMSG.SUCCES) {
            mf.printOUT("Succes: " + retmsg.getCMD() + " => " + Utils.removeEndl(retmsg.getRetMSG()) + ".");
        } else {
            //printERR("Error (RetVal = " + retmsg.getRetCode() + "): " + retmsg.getRetMSG());
            mf.printERR("Error: " + Utils.removeEndl(retmsg.getRetMSG()) + " !");
        }
        
        return retmsg;
    }
    
    @Override
    public void getFile(String parameters, MainFrame mf) {
        RetMSG retmsg;
        retmsg = remoteExec.sendCommand("get " + parameters);
        if (retmsg.getRetCode() == RetMSG.SUCCES) {
            mf.printOUT("Succes: " + retmsg.getCMD() + " => " + Utils.removeEndl(retmsg.getRetMSG()) + ".");
        } else {
            //printERR("Error (RetVal = " + retmsg.getRetCode() + "): " + retmsg.getRetMSG());
            mf.printERR("Error: " + Utils.removeEndl(retmsg.getRetMSG()) + " !");
        }
    }
    
    @Override
    public String getOutputFiles(String dir, MainFrame mf) {
        String CMD = "ls " + dir;
        RetMSG retmsg;
        retmsg = remoteExec.sendCommand(CMD);
        if (retmsg.getRetCode() == RetMSG.SUCCES) {
            mf.printOUT("Succes: " + retmsg.getCMD());
            return Utils.removeEndl(retmsg.getRetMSG());
        } else {
            //printERR("Error (RetVal = " + retmsg.getRetCode() + "): " + retmsg.getRetMSG());
            mf.printERR("Error: " + Utils.removeEndl(retmsg.getRetMSG()) + " !");
            return "";
        }
    }
}
