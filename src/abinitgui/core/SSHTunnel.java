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

package abinitgui.core;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import javax.swing.JFileChooser;

public class SSHTunnel {

    private Session session;
    private String gatewayuser;
    private String gatewayhost;
    private int gatewayport;
    private int lport;
    private String rhost;
    private int rport;
    private String prvkey;
    private boolean useKey;
    private String pass;

    public SSHTunnel(String gatewayuser, String gatewayhost,
            int gatewayport, String rhost, int lport, int rport) {
        this.gatewayuser = gatewayuser;
        this.gatewayhost = gatewayhost;
        this.gatewayport = gatewayport;
        this.rhost = rhost;
        this.lport = lport;
        this.rport = rport;
    }

    public void setPrvkeyOrPwdOnly(String prvkey, String pwd, boolean useKey) {
        if (useKey) {
            this.useKey = true;
            this.pass = pwd;
            if (prvkey == null) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Choose your privatekey(ex. ~/.ssh/id_dsa)");
                chooser.setFileHidingEnabled(false);
                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    MainFrame.printOUT("You choose "
                            + chooser.getSelectedFile().getAbsolutePath() + ".");
                    this.prvkey = chooser.getSelectedFile().getAbsolutePath();
                } else {
                    this.prvkey = null;
                }
            } else {
                this.prvkey = prvkey;
            }
        } else {
            this.useKey = false;
            this.pass = pwd;
        }
    }

    public int start() {
        try {
            if (MainFrame.DEBUG) {
                JSch.setLogger(new MyLogger());
            }
            JSch jsch = new JSch();

            MyUserInfo ui = new MyUserInfo();

            if (this.useKey) {
                if (prvkey == null) {
                    return -1;
                } else {
                    jsch.addIdentity(prvkey, pass);
                }
            } else {
                ui.setPassword(pass);
            }

            session = jsch.getSession(gatewayuser, gatewayhost, gatewayport);

            // username and password will be given via UserInfo interface.
            session.setUserInfo(ui);
            session.connect();

            int assinged_port = session.setPortForwardingL(lport, rhost, rport);
            
            MainFrame.printDEB("localhost:" + assinged_port + " -> " + rhost + ":" + rport);

            if (session.isConnected()) {
                return assinged_port;
            } else {
                return -1;
            }
        } catch (Exception e) {
            MainFrame.printERR("Exception: " + e.getMessage());
            return -1;
        }
    }

    public void stop() {
        try {
            if (session != null) {
                session.disconnect();
            }
        } catch (Exception e) {
            MainFrame.printERR("Exception: " + e.getMessage());
        }
    }
}
