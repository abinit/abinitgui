/*
 AbinitGUI - Created in July 2009
 
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

                        try {
                            MainFrame.pw.close();
                            MainFrame.bw.close();
                            MainFrame.fw.close();
                        } catch (IOException ex) {
                            System.err.println("IOException (windowClosing(...))");
                        }

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
