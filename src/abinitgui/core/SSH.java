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

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;

//@SuppressWarnings({"deprecation","unchecked"})
public class SSH {

    private InputStream in;
    private OutputStream out;
    public Channel channel;
    public Session session;
    private String userAndHost = null;
    private String pass = null;
    private String prvkey;
    private boolean useKey;
    private JTextArea outputTA;
    private OutputHandler sshOH;
    private ArrayList cmds = new ArrayList();
    private String retMSG = "";
    private SSHExecOutput sshEO;
    boolean console = false;
    private int sshPort = 22;
    private MainFrame mainFrame;

    public SSH(MainFrame parent, JTextArea outputTA) {
        this(parent, outputTA, false);
    }

    public SSH(MainFrame parent, JTextArea outputTA, boolean console) {
        this.mainFrame = parent;
        this.outputTA = outputTA;
        this.console = console;
    }

    public void setUserAndHost(String userAndHost) {
        this.userAndHost = userAndHost;
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
                String pvrK;
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

    public void setPort(int port) {
        this.sshPort = port;
    }

    /*synchronized*/ public void sendCommand(String cmd) {
        if (console) {
            try {
                cmd += '\n';
                out.write(cmd.getBytes(), 0, cmd.length());
                out.flush();
            } catch (Exception e) {
                MainFrame.printERR(e.getMessage());
            }
        } else {
            if (cmd.startsWith("ls")) {
                cmd += " --color=never";
            }

            if (cmd.startsWith("exec") || cmd.startsWith("EXEC")) {
                // cette commande n'est pas gérée
                outputTA.append("Command [" + cmd + "] not supported by AbinitGUI !\n");
                return;
            }

            if (cmd.equals("vi") || cmd.equals("VI")) {
                // cette commande n'est pas gérée
                outputTA.append("Command [" + cmd + "] not supported by AbinitGUI !\n");
                return;
            }

            if (cmd.equals("vim") || cmd.equals("VIM")) {
                // cette commande n'est pas gérée
                outputTA.append("Command [" + cmd + "] not supported by AninitGUI !\n");
                return;
            }

            if (cmds.add(cmd)) {
                MainFrame.printDEB("Command [" + cmd + "] successfuly registred.");
            } else {
                MainFrame.printERR("Could not register the command: " + cmd);
            }
        }
    }

    public boolean start() {
        try {
            if (mainFrame.DEBUG) {
                JSch.setLogger(new MyLogger());
            }
            JSch jsch = new JSch();

            MyUserInfo ui = new MyUserInfo();

            String user = userAndHost.substring(0, userAndHost.indexOf('@'));
            String host = userAndHost.substring(userAndHost.indexOf('@') + 1);

            if (this.useKey) {
                if (prvkey == null) {
                    return false;
                } else {
                    jsch.addIdentity(prvkey, pass);
                }
            } else {
                ui.setPassword(pass);
            }

            session = jsch.getSession(user, host, sshPort);
            // username and password will be given via UserInfo interface.
            session.setUserInfo(ui);
            session.connect();
            //System.out.println("DEBUG connect SSH");

            channel = session.openChannel("shell");

            //((ChannelShell) channel).setXForwarding(true); // XForwarding

            out = channel.getOutputStream();
            in = channel.getInputStream();

            if (console) {
                sshEO = new SSHExecOutput(in);
            } else {
                sshOH = new OutputHandler();
                sshOH.start();
            }
            channel.connect();

            if (session.isConnected() && channel.isConnected()) {
                return true;
            } else {
                return false;
            }
        } catch (com.jcraft.jsch.JSchException e) {
            String msg = e.getMessage();
            if (e.getCause() != null) {
                String msg2 = msg.substring(0, msg.indexOf(':'));
                String msg3 = e.getCause().getMessage();
                if (msg2.equals("java.net.UnknownHostException")) {
                    MainFrame.printERR("Unknown hostname: " + msg3);
                } else {
                    MainFrame.printERR("Exception: " + msg2);
                }
            } else {
                if (msg.equals("Auth fail")) {
                    MainFrame.printERR("Username or password is wrong !!");
                } else {
                    MainFrame.printERR("JSchException => MSG: " + msg);
                }
            }
            return false;
        } catch (Exception e) {
            MainFrame.printERR(e.getMessage());
            return false;
        }
    }

    public void stop() {
        if (console) {
            if (channel != null && session != null) {
                sendCommand("exit");
            }
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
            if (sshEO != null) {
                //sshEO.stop();
                sshEO.interrupt();
            }
        } else {
            new ClosureWatcher().start();
        }
    }

    public boolean isConnected() {
        if (channel.isConnected()) {
            return session.isConnected();
        } else {
            return false;
        }
    }

    public class OutputHandler extends Thread {

        //private String lastCMD = "";
        @Override
        public void run() {
            String msg;
            byte[] buf = new byte[1024];
            int i;
            try {
                while (true) {
                    do {
                        int b = in.read();
                        //outputTA.append("[" + b + "-" + (char) b + "]\n");
                        if (b == -1) {
                            //OutputTA.append("code de retour [" + b + "]\n");
                        } else if (b == 0) {
                            outputTA.append("[" + b + "] SUCCES\n");
                        } else if (b == 1) {
                            outputTA.append("[" + b + "] ERROR\n");
                        } else if (b == 2) {
                            outputTA.append("[" + b + "] FATAL ERROR\n");
                        }

                        i = in.read(buf, 0, 1024);
                        if (i <= 0) {
                            break;
                        } else {
                            byte[] tmp = new byte[i];
                            System.arraycopy(buf, 0, tmp, 0, i);

                            msg = (char) b + new String(tmp);
                            outputTA.append(msg);
                            outputTA.setCaretPosition(outputTA.getDocument().getLength());

                            if (msg.contains("@") && ((msg.endsWith("> ") || msg.endsWith(">")
                                    || msg.endsWith("# ") || msg.endsWith("#")
                                    || msg.endsWith("$ ") || msg.endsWith("$")))) {
                                // THE PROMPT IS READY !!
                                //printOUT(msg + '\n');
                                String cmd = execNextCMD();
                                if (cmd.startsWith("exit") || cmd.startsWith("EXIT")) {
                                    //Ceci provoque l'arrÃªt contrÃ´lÃ© du thread OutputHandler
                                    throw new Exception("OutputHandler thread is shuting down !!");
                                }
                            } else {
                                if (msg.contains("Password:") || msg.contains("password:")) {
                                    MainFrame.printOUT("msg.contains(\"Password:\") || msg.contains(\"password:\")");
                                    execNextCMD();
                                } else if (msg.endsWith("ETA")) {
                                    // scp command
                                    outputTA.append("\n");
                                    outputTA.setCaretPosition(outputTA.getDocument().getLength());
                                } else {
                                    // Continuer Ã  lire
                                    break;
                                }
                            }

                        }
                    } while (in.available() > 0);
                }
            } catch (Exception e) {
                MainFrame.printERR(e.getMessage());
            }
        }

        private String execNextCMD() {
            String cmd = null;
            do {
                if (!cmds.isEmpty()) {
                    cmd = (String) cmds.get(0);
                    break;
                }
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                }
            } while (true);
            cmds.remove(cmd);
            try {
                cmd += '\n';
                if (cmd != null) {
                    out.write(cmd.getBytes(), 0, cmd.length());
                    out.flush();
                }
            } catch (Exception e) {
                MainFrame.printERR(e.getMessage());
            }
            return cmd;
        }
    }

    public class ClosureWatcher extends Thread {

        @Override
        public void run() {
            sendCommand("exit");
            try {
                sshOH.join();
            } catch (Exception e) {
                MainFrame.printERR("SSH stop(): " + e);
            }

            if (channel != null) {
                channel.disconnect();
            }

            if (session != null) {
                session.disconnect();
            }

            if (sshOH != null) {
                //sshOH.stop();
                sshOH.isInterrupted();
            }
        }
    }

    public class SSHExecOutput extends Thread {

        private InputStream input;

        public SSHExecOutput(InputStream input) {
            this.input = input;
            this.start();
        }

        @Override
        public void run() {
            byte[] buf = new byte[1024];
            int i;
            try {
                while (true) {
                    i = input.read(buf, 0, 1024);
                    if (i <= 0) {
                        break;
                    } else {
                        byte[] tmp = new byte[i];
                        System.arraycopy(buf, 0, tmp, 0, i);
                        String str = new String(tmp);

                        outputTA.append(str.replace("[0;0m", "")
                                .replace("[1;39m", "")
                                .replace("[00m", "")
                                .replace("[01;31m", "")
                                .replace("[01;32m", "")
                                .replace("[01;33m", "")
                                .replace("[01;34m", "")
                                .replace("[0;31;27m", "")
                                .replace("[0;36;27m", "")
                                .replace("[0;31;27m", "")
                                .replace("\033[1;41m", "") //
                                .replace("\033[31m", "") //
                                .replace("\033[1;42m", "") //
                                .replace("\033[1;32m", "") //
                                .replace("\033[1;43m", "") //
                                .replace("\033[1;33m", "") //
                                .replace("\033[1;44m", "") //
                                .replace("\033[1;34m", "") //
                                .replace("\033[1;45m", "") //
                                .replace("\033[1;35m", "") //
                                .replace("\033[1;46m", "") //
                                .replace("\033[1;36m", "") //
                                .replace("\033[1;47m", "") //
                                .replace("\033[1;37m", "") //
                                .replace("\033[0m", "") //
                                .replace("[0;25;27m", "") // sur green
                                .replace("[0;32;27m", "") // sur green
                                .replace("[0;34;27m", "") // sur green
                                .replace("[m", "")
                                .replace("[0m", "")
                                .replace("[K", ""));
                        outputTA.setCaretPosition(outputTA.getDocument().getLength());
                    }
                }
            } catch (Exception e) {
                MainFrame.printERR(e.getMessage());
            }
        }
    }
}
