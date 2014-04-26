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
    public RetMSG sendCommand(String CMD)
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
