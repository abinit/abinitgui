/*
 Copyright (c) 2009-2013 Flavio Miguel ABREU ARAUJO (flavio.abreuaraujo@uclouvain.be)
                         Yannick GILLET (yannick.gillet@uclouvain.be)

Universit� catholique de Louvain, Louvain-la-Neuve, Belgium
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

package abinitgui;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import jsshterm.JSSHErrorHandler;
import jsshterm.JSSHTerm;

public class MySSHTerm implements Runnable {

    MainFrame parent = null;
    private String host;
    private int port;
    private String username;
    private String password;
    private String userkey;
    private String passphrase;
    private boolean authPWD = true;

    public MySSHTerm(MainFrame mf, String host, int port, String username,
            String password) {
        parent = mf;
        authPWD = true;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }
    
    public MySSHTerm(MainFrame mf, String host, int port, String username,
            String userkey, String passphrase) {
        parent = mf;
        authPWD = false;
        this.host = host;
        this.port = port;
        this.username = username;
        this.userkey = userkey;
        this.passphrase = passphrase;
    }

    @Override
    public void run() {
        JSSHErrorHandler errHandler = new JSSHErrorHandler() {
            @Override
            public void printOUT(String msg) {
                if (parent != null) {
                    parent.printOUT(msg);
                } else {
                    System.out.println(msg);
                }
            }

            @Override
            public void printERR(String msg) {
                if (parent != null) {
                    parent.printERR(msg);
                } else {
                    System.err.println(msg);
                }
            }

            @Override
            public void printDEB(String msg) {
                if (parent != null) {
                    parent.printDEB(msg);
                } else {
                    System.out.println(msg);
                }
            }

            @Override
            public void printGEN(String msg) {
                if (parent != null) {
                    parent.printGEN(msg, Color.BLACK, false, false);
                } else {
                    System.out.println(msg);
                }
            }
        };

        final JSSHTerm dialog;
        
        if(authPWD) {
            dialog = new JSSHTerm(host, port, username, password, errHandler);
        } else {
            dialog = new JSSHTerm(host, port, username, userkey,
                    passphrase, errHandler);
        }
        dialog.setTitle("SSH2 Client driven by AbinitGUI (JSSHTerm)");
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialog.disconnect();
            }
        });
        //dialog.setVisible(true);
    }
}
