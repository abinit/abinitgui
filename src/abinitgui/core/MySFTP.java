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

import jsftp.core.JSFTP;

public class MySFTP implements Runnable {

    private String host;
    private int port;
    private String username;
    private String password;
    private String userkey;
    private String passphrase;
    private boolean authPWD = true;

    public MySFTP(String host, int port, String username,
            String password) {
        authPWD = true;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }
    
    public MySFTP(String host, int port, String username,
            String userkey, String passphrase) {
        authPWD = false;
        this.host = host;
        this.port = port;
        this.username = username;
        this.userkey = userkey;
        this.passphrase = passphrase;
    }

    @Override
    public void run() {

        final JSFTP dialog;
        
        if(authPWD) {
            dialog = new JSFTP(host, port, username, password,
                    MainFrame.msgdisp, MainFrame.mainFrame, false);
        } else {
            dialog = new JSFTP(host, port, username, userkey,
                    passphrase, MainFrame.msgdisp, MainFrame.mainFrame, false);
        }
        dialog.setTitle("SFTP Client (JSFTP by F. ABREU ARAUJO)");
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                dialog.disconnect();
            }
        });
        //dialog.setVisible(true);
    }
}
