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
import core.SSHTunnel;
import static projects.Machine.GATEWAY_MACHINE;
import static projects.Machine.LOCAL_MACHINE;
import static projects.Machine.REMOTE_MACHINE;

/**
 *
 * @author yannick
 */
public class GatewayMachine extends RemoteMachine {

    public GatewayMachine()
    {
        super();
        this.type = GATEWAY_MACHINE;
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
            }
            if (sshtun != null) {
                sshtun.stop();
                sshtun = null;
            }
            isConnected = false;
            lport = 0;
        }
    }
    
    public MySSHTerm newSSHTerm(MainFrame mf)
    {
        MySSHTerm ssh2 = null;
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
        
        return ssh2;
        
    }
    
    @Override
    public MySFTP newSFTP(MainFrame mf) 
    {
        MySFTP client = null;
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
        
        return client;
    }
    
}
