/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projects;

import core.Exec;
import core.LocalExec;
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
public class LocalMachine extends Machine
{
    protected LocalExec localExec;
    
    public LocalMachine()
    {
        super();
        this.type = LOCAL_MACHINE;
    }
    
    @Override
    public Exec getExec()
    {
        return localExec;
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
            // Set local localExec as localExec
            this.localExec = mf.getLocalExec();
            isConnected = true;
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
            this.localExec = null;
            isConnected = false;
        }
    }
    
    @Override
    public void putFile(String parameters, MainFrame mf)
    {
        RetMSG retmsg;
        retmsg = localExec.sendCommand("cp " + parameters);
        if (retmsg.getRetCode() == RetMSG.SUCCES) {
            mf.printOUT("Succes: " + retmsg.getCMD() + " => " + Utils.removeEndl(retmsg.getRetMSG()) + ".");
        } else {
            //printERR("Error (RetVal = " + retmsg.getRetCode() + "): " + retmsg.getRetMSG());
            mf.printERR("Error: " + Utils.removeEndl(retmsg.getRetMSG()) + " !");
        }
    }
    
    @Override
    public RetMSG sendCommand(String CMD, MainFrame mf)
    {
        RetMSG retmsg;
        retmsg = localExec.sendCommand(CMD);
        if (retmsg.getRetCode() == RetMSG.SUCCES) {
            mf.printOUT("Succes: " + retmsg.getCMD() + " => " + Utils.removeEndl(retmsg.getRetMSG()) + ".");
        } else {
            //printERR("Error (RetVal = " + retmsg.getRetCode() + "): " + retmsg.getRetMSG());
            mf.printERR("Error: " + Utils.removeEndl(retmsg.getRetMSG()) + " !");
        }
        
        return retmsg;
    }
    
    @Override
    public MySSHTerm newSSHTerm(MainFrame mf)
    {
        mf.printERR("To connect to the local host, please choose the remote option"
                    + " where you specify localhost as hostname !");
        
        return null;
    }
    
    @Override
    public MySFTP newSFTP(MainFrame mf) {
        MySFTP client = null;
        mf.printERR("To connect to the local host, please choose the remote option"
                + " where you specify localhost as hostname !");
        return client;
    }
    
    public void getFile(String parameters, MainFrame mf) {
        RetMSG retmsg;
        retmsg = localExec.sendCommand("cp " + parameters);
        if (retmsg.getRetCode() == RetMSG.SUCCES) {
            mf.printOUT("Succes: " + retmsg.getCMD() + " => " + Utils.removeEndl(retmsg.getRetMSG()) + ".");
        } else {
            //printERR("Error (RetVal = " + retmsg.getRetCode() + "): " + retmsg.getRetMSG());
            mf.printERR("Error: " + Utils.removeEndl(retmsg.getRetMSG()) + " !");
        }
    }
    
    @Override
    public String getOutputFiles(String dir, MainFrame mf) {
        if(Utils.osName().startsWith("Windows"))
        {
            mf.printERR("Not available on Windows");
            return "";
        }
        else
        {
            String CMD = "ls " + dir;
            RetMSG retmsg;
            retmsg = localExec.sendCommand(CMD);
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
}
