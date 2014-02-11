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

import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MyUserInfo implements UserInfo, UIKeyboardInteractive {

    private boolean DEBUG = false;

        @Override
        public String getPassword() {
            return passwd;
        }

        public void setPassword(String pwd) {
            passwd = pwd;
        }

        @Override
        public boolean promptYesNo(String str) {
            return true;
        }
        String passwd = null;
        JTextField passwordField = (JTextField) new JPasswordField(20);
        JTextField textField = new JTextField(20);

        @Override
        public String getPassphrase() {
            return passwd;
        }

        @Override
        public boolean promptPassphrase(String message) {
            if (passwd != null) {
                if (passwd.equals("")) {
                    Object[] ob = {passwordField};
                    int result = JOptionPane.showConfirmDialog(null, ob,
                            message, JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        passwd = passwordField.getText();
                        return true;
                    } else {
                        // annulation par l'utilisateur
                        passwd = null;
                        return false;
                    }
                } else {
                    // le mot de passe est déjà  dans passwd
                    return true;
                }
            } else {
                Object[] ob = {passwordField};
                int result = JOptionPane.showConfirmDialog(null, ob,
                        message, JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    passwd = passwordField.getText();
                    return true;
                } else {
                    // annulation par l'utilisateur
                    passwd = null;
                    return false;
                }
            }
        }

        @Override
        public boolean promptPassword(String message) {
            if (passwd != null) {
                if (passwd.equals("")) {
                    Object[] ob = {passwordField};
                    int result = JOptionPane.showConfirmDialog(null, ob,
                            message, JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        passwd = passwordField.getText();
                        return true;
                    } else {
                        // annulation par l'utilisateur
                        passwd = null;
                        return false;
                    }
                } else {
                    // le mot de passe est déjà  dans passwd
                    return true;
                }
            } else {
                Object[] ob = {passwordField};
                int result = JOptionPane.showConfirmDialog(null, ob,
                        message, JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    passwd = passwordField.getText();
                    return true;
                } else {
                    // annulation par l'utilisateur
                    passwd = null;
                    return false;
                }
            }
        }

        @Override
        public void showMessage(String message) {
            JOptionPane.showMessageDialog(null, message);
        }

        @Override
        public String[] promptKeyboardInteractive(String destination,
                String name,
                String instruction,
                String[] prompt,
                boolean[] echo) {
            if (DEBUG) {
                MainFrame.printDEB("destination: " + destination + " - name: " +
                        name + " - instruction: " + instruction + "promp.length: " +
                        prompt.length);
            }

            String str[] = new String[prompt.length];
            for (int i = 0; i < prompt.length; i++) {
                if (!echo[i]) {
                    if (passwd == null) {
                        Object[] ob = {passwordField};
                        int result = JOptionPane.showConfirmDialog(null, ob,
                                "Password for " + destination, JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            passwd = passwordField.getText();
                            str[i] = passwd;
                        } else {
                            passwd = null;
                            str[i] = "";
                        }
                    } else {
                        if (passwd.equals("")) {
                            Object[] ob = {passwordField};
                            int result = JOptionPane.showConfirmDialog(null, ob,
                                    "Password for " + destination, JOptionPane.OK_CANCEL_OPTION);
                            if (result == JOptionPane.OK_OPTION) {
                                passwd = passwordField.getText();
                                str[i] = passwd;
                            } else {
                                passwd = "";
                                str[i] = "";
                            }
                        } else {
                            str[i] = passwd;
                        }
                    }
                } else {
                    Object[] ob = {textField};
                    int result = JOptionPane.showConfirmDialog(null, ob,
                            prompt[i] + " | " + destination, JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        str[i] = textField.getText();
                    } else {
                        str[i] = "";
                    }
                }
            }
            return str;
        }
    }
