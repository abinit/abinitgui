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
                    // le mot de passe est dÃ©jÃ Â  dans passwd
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
                    // le mot de passe est dÃ©jÃ Â  dans passwd
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
