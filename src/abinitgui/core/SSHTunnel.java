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
