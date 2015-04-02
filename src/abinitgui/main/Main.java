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

package abinitgui.main;

import abinitgui.core.MainFrame;
import abinitgui.core.Utils;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Timer;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

    public static OutputStream out;
    public static InputStream in;
    public Object[][] OwnerBuyerArray = null;
    public ArrayList<Object[]> ProductsVector = new ArrayList();
    public File file = null;
    public static MainFrame frame = null;
    public static Timer time1;
    public static int userID;
    public static String userName;
    public static boolean DEBUG = true;

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                boolean laf = true;

                try {
                    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                        if ("Nimbus".equals(info.getName())) {
                            UIManager.setLookAndFeel(info.getClassName());
                            System.out.println("LAF : " + info.getName());
                            laf = false;
                        } else {
                            System.err.println("LAF : " + info.getName());
                        }
                    }

                    if (laf) {
                        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                            if ("GTK+".equals(info.getName())) {
                                UIManager.setLookAndFeel(info.getClassName());
                                laf = false;
                            }
                        }
                    }

                    if (laf) {
                        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                            if ("Mac OS X".equals(info.getName())) {
                                UIManager.setLookAndFeel(info.getClassName());
                                laf = false;
                            }
                        }
                    }

                    if (laf) {
                        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                            if ("Windows".equals(info.getName())) {
                                UIManager.setLookAndFeel(info.getClassName());
                            }
                        }
                    }
                } catch (ClassNotFoundException | InstantiationException |
                        IllegalAccessException | UnsupportedLookAndFeelException e) {
                    // If Nimbus is not available, you can set the GUI to another look and feel.
                }

                frame = new MainFrame();

                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });

                try {
                    String osName = Utils.osName();
                    String javaVersion = Utils.javaVersion();
                    String osArch = Utils.osArch();
                    System.err.println("Java version number: " + javaVersion);
                    
                    switch (osName) {
                        case "Linux":
                            System.err.print("OS name: ");
                            System.err.println(osName);
                            break;
                        case "Windows Vista":
                            System.err.print("OS name: ");
                            System.err.println(osName);
                            break;
                        default:
                            System.err.print("OS name: ");
                            System.err.println(osName);
                            break;
                    }
                    System.err.println("OS arch: " + osArch);
                    //SwingUtilities.updateComponentTreeUI(frame);
                } catch (Exception e) {
                    System.out.println(e);
                }

                if (args.length > 0) {
                    for (int i = 0; i < args.length; i++) {
                        if (args[i].equalsIgnoreCase("AutoTest")) {
                            frame.setVisible(false);
                            //frame.autoTestProc(true);
                        } else {
                            frame.setVisible(true);
                        }
                    }
                } else {
                    frame.setVisible(true);
                }
            }
        });
    }
}
