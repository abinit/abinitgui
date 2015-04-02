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
import abinitgui.core.LocalExec;
import abinitgui.core.MainFrame;
import abinitgui.core.MySFTP;
import abinitgui.core.MySSHTerm;
import abinitgui.core.RetMSG;
import abinitgui.core.Utils;
import static abinitgui.projects.Machine.LOCAL_MACHINE;

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
    public void connection()
    {
        if(isConnected)
        {
            MainFrame.printERR("You are already connected!");
        }
        else
        {
            // Set local localExec as localExec
            this.localExec = MainFrame.getLocalExec();
            isConnected = true;
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
            this.localExec = null;
            isConnected = false;
        }
    }
    
    @Override
    public void putFile(String parameters)
    {
        RetMSG retmsg;
        retmsg = localExec.sendCommand("cp " + parameters);
        if (retmsg.getRetCode() == RetMSG.SUCCES) {
            MainFrame.printOUT("Succes: " + retmsg.getCMD() + " => " +
                    Utils.removeEndl(retmsg.getRetMSG()) + ".");
        } else {
            //printERR("Error (RetVal = " + retmsg.getRetCode() + "): " + retmsg.getRetMSG());
            MainFrame.printERR("Error: " +
                    Utils.removeEndl(retmsg.getRetMSG()) + " !");
        }
    }
    
    @Override
    public RetMSG sendCommand(String CMD, boolean printMF)
    {
        RetMSG retmsg;
        retmsg = localExec.sendCommand(CMD);
        if(printMF)
        {
            if (retmsg.getRetCode() == RetMSG.SUCCES) {
                MainFrame.printOUT("Succes: " + retmsg.getCMD() + " => " +
                        Utils.removeEndl(retmsg.getRetMSG()) + ".");
            } else {
                //printERR("Error (RetVal = " + retmsg.getRetCode() + "): " + retmsg.getRetMSG());
                MainFrame.printERR("Error: " +
                        Utils.removeEndl(retmsg.getRetMSG()) + " !");
            }
        }
        
        return retmsg;
    }
    
    
    @Override
    public RetMSG sendCommand(String CMD[])
    {
        RetMSG retmsg;
        retmsg = localExec.sendCommand(CMD);
        if (retmsg.getRetCode() == RetMSG.SUCCES) {
            MainFrame.printOUT("Succes: " + retmsg.getCMD() + " => " +
                    Utils.removeEndl(retmsg.getRetMSG()) + ".");
        } else {
            //printERR("Error (RetVal = " + retmsg.getRetCode() + "): " + retmsg.getRetMSG());
            MainFrame.printERR("Error: " +
                    Utils.removeEndl(retmsg.getRetMSG()) + " !");
        }
        
        return retmsg;
    }
    
    @Override
    public MySSHTerm newSSHTerm()
    {
        MainFrame.printERR("To connect to the local host, please choose the"
                + " remote option where you specify localhost as hostname !");
        return null;
    }
    
    @Override
    public MySFTP newSFTP() {
        MySFTP client = null;
        MainFrame.printERR("To connect to the local host, please choose the"
                + " remote option where you specify localhost as hostname !");
        return client;
    }
    
    @Override
    public void getFile(String parameters) {
        RetMSG retmsg;
        retmsg = localExec.sendCommand("cp " + parameters);
        if (retmsg.getRetCode() == RetMSG.SUCCES) {
            MainFrame.printOUT("Succes: " + retmsg.getCMD() + " => " +
                    Utils.removeEndl(retmsg.getRetMSG()) + ".");
        } else {
            //printERR("Error (RetVal = " + retmsg.getRetCode() + "): " + retmsg.getRetMSG());
            MainFrame.printERR("Error: " + Utils.removeEndl(retmsg.getRetMSG()) + " !");
        }
    }
    
    @Override
    public String getOutputFiles(String dir) {
        if(Utils.osName().startsWith("Windows"))
        {
            MainFrame.printERR("Not available on Windows");
            return "";
        }
        else
        {
            String[] CMD = new String[]{"sh","-c","ls " + dir};
            RetMSG retmsg;
            retmsg = localExec.sendCommand(CMD);
            if (retmsg.getRetCode() == RetMSG.SUCCES) {
                MainFrame.printOUT("Succes: " + retmsg.getCMD());
                return Utils.removeEndl(retmsg.getRetMSG());
            } else {
                //printERR("Error (RetVal = " + retmsg.getRetCode() + "): " + retmsg.getRetMSG());
                MainFrame.printERR("Error: " +
                        Utils.removeEndl(retmsg.getRetMSG()) + " !");
                return "";
            }
        }
    }
}
