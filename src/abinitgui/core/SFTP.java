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
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;

public class SFTP {
    private JTextArea outputTA;
    private String userAndHost = null;
    private String pass = null;
    private String prvkey;
    private boolean useKey;
    private ChannelSftp chanSFTP;
    private Session session;
    private int sftpPort = 22;
    private MainFrame mainFrame;

    public SFTP(MainFrame parent, JTextArea outputTA) {
        this.mainFrame = parent;
        this.outputTA = outputTA;
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
                    MainFrame.printDEB("You choose "
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

    public boolean isConnected() {
        if (chanSFTP.isConnected()) {
            return session.isConnected();
        } else {
            return false;
        }
    }

    public void sendCommand(String command) {
        if (!command.equals("")) {
            printText(command + '\n');
        }
        ArrayList<String> cmds = new ArrayList<>();
        cmds.clear();

        StringTokenizer st = new StringTokenizer(command);
        while (st.hasMoreTokens()) {
            cmds.add(st.nextToken());
        }

        String str;
        int level = 0;

        if (cmds.isEmpty()) {
            return;
        }

        String cmd = cmds.get(0);
        cmds.remove(0);

        if (cmd.equals("quit")) {
            chanSFTP.quit();
            // TODO attention !
            return;
        }
        if (cmd.equals("exit")) {
            chanSFTP.exit();
            // TODO attention !
            return;
        }
        if (cmd.equals("rekey")) {
            try {
                session.rekey();
            } catch (Exception e) {
                MainFrame.printERR("sftp - " + cmd + ": error (" + e.getMessage() + ")");
            }
            printPrompt();
            return;
        }
        if (cmd.equals("compression")) {
            if (cmds.isEmpty()) {
                printText("compression level: " + level + '\n');
                printPrompt();
                return;
            }
            try {
                level = Integer.parseInt(cmds.get(0));
                if (level == 0) {
                    session.setConfig("compression.s2c", "none");
                    session.setConfig("compression.c2s", "none");
                } else {
                    session.setConfig("compression.s2c", "zlib@openssh.com,zlib,none");
                    session.setConfig("compression.c2s", "zlib@openssh.com,zlib,none");
                }
                session.rekey();
            } catch (Exception e) {
                MainFrame.printERR(e.getMessage());
            }
            printPrompt();
            return;
        }
        if (cmd.equals("cd") || cmd.equals("lcd")) {
            if (cmds.isEmpty()) {
                printPrompt();
                return;
            }
            String path = cmds.get(0);
//            String path;
//            path = "";
//            for (String s : cmds) {
//                path += s + " ";
//            }
//            mainFrame.printDEB(path);
            if (cmd.equals("cd")) {
                try {
                    chanSFTP.cd(path);
                } catch (SftpException e) {
                    MainFrame.printERR("sftp - " + cmd + ": error (" + e.getMessage() + ")");
                }
            } else {
                try {
                    chanSFTP.lcd(path);
                } catch (SftpException e) {
                    MainFrame.printERR("sftp - " + cmd + ": error (" + e.getMessage() + ")");
                }
            }
            printPrompt();
            return;
        }
        if (cmd.equals("rm") || cmd.equals("rmdir") || cmd.equals("mkdir")) {
            if (cmds.isEmpty()) {
                printPrompt();
                return;
            }
            String path = (String) cmds.get(0);
            switch (cmd) {
                case "rm":
                    try {
                        chanSFTP.rm(path);
                    } catch (SftpException e) {
                        MainFrame.printERR("sftp - " + cmd + ": error (" + e.getMessage() + ")");
                    }
                    break;
                case "rmdir":
                    try {
                        chanSFTP.rmdir(path);
                    } catch (SftpException e) {
                        MainFrame.printERR("sftp - " + cmd + ": error (" + e.getMessage() + ")");
                    }
                    break;
                default:
                    try {
                        chanSFTP.mkdir(path);
                    } catch (SftpException e) {
                        MainFrame.printERR("sftp - " + cmd + ": error (" + e.getMessage() + ")");
                    }
                    break;
            }
            printPrompt();
            return;
        }
        if (cmd.equals("chgrp") || cmd.equals("chown") || cmd.equals("chmod")) {
            if (cmds.size() != 2) {
                printPrompt();
                return;
            }
            String path = (String) cmds.get(1);
            int foo = 0;
            if (cmd.equals("chmod")) {
                byte[] bar;
                bar = ((String) cmds.get(0)).getBytes();
                int k;
                for (int j = 0; j < bar.length; j++) {
                    k = bar[j];
                    if (k < '0' || k > '7') {
                        foo = -1;
                        break;
                    }
                    foo <<= 3;
                    foo |= (k - '0');
                }
                if (foo == -1) {
                    printPrompt();
                    return;
                }
            } else {
                try {
                    foo = Integer.parseInt((String) cmds.get(0));
                } catch (Exception e) {
                    printPrompt();
                    return;
                }
            }
            switch (cmd) {
                case "chgrp":
                    try {
                        chanSFTP.chgrp(foo, path);
                    } catch (SftpException e) {
                        MainFrame.printERR("sftp - " + cmd + ": error (" + e.getMessage() + ")");
                    }
                    break;
                case "chown":
                    try {
                        chanSFTP.chown(foo, path);
                    } catch (SftpException e) {
                        MainFrame.printERR("sftp - " + cmd + ": error (" + e.getMessage() + ")");
                    }
                    break;
                case "chmod":
                    try {
                        chanSFTP.chmod(foo, path);
                    } catch (SftpException e) {
                        MainFrame.printERR("sftp - " + cmd + ": error (" + e.getMessage() + ")");
                    }
                    break;
            }
            printPrompt();
            return;
        }

        if (cmd.equals("pwd") || cmd.equals("lpwd")) {
            str = (cmd.equals("pwd") ? "Remote" : "Local");
            str += " working directory: ";
            if (cmd.equals("pwd")) {
                try {
                    str += chanSFTP.pwd();
                } catch (SftpException e) {
                    MainFrame.printERR("sftp - " + cmd + ": error (" + e.getMessage() + ")");
                }
            } else {
                try {
                    str += chanSFTP.lpwd();
                } catch (Exception e) {
                    MainFrame.printERR("sftp - " + cmd + ": error (" + e.getMessage() + ")");
                }
            }
            printText(str + '\n');
            printPrompt();
            return;
        }

        if (cmd.equals("ls") || cmd.equals("dir")) {
            String path = ".";
            if (!cmds.isEmpty()) {
                path = (String) cmds.get(0); // TODO
            }
            try {
                @SuppressWarnings("UseOfObsoleteCollectionType")
                java.util.Vector vv;
                vv = chanSFTP.ls(path);
                if (vv != null) {
                    for (int ii = 0; ii < vv.size(); ii++) {
                        Object obj = vv.elementAt(ii);
                        if (obj instanceof LsEntry) {
                            printText(((LsEntry) obj).getLongname() + '\n');
                        }
                    }
                }
            } catch (SftpException e) {
                MainFrame.printERR("sftp - " + cmd + ": error (" + e.getMessage() + ")");
            }
            printPrompt();
            return;
        }

        if (cmd.equals("lls") || cmd.equals("ldir")) {
            String path = ".";
            if (!cmds.isEmpty()) {
                path = (String) cmds.get(1); // TODO
            }
            try {
                java.io.File file = new java.io.File(path);
                if (!file.exists()) {
                    printText(path + ": No such file or directory" + '\n');
                    printPrompt();
                    return;
                }
                if (file.isDirectory()) {
                    String[] list = file.list();
                    for (int ii = 0; ii < list.length; ii++) {
                        printText(list[ii] + '\n');
                    }
                    printPrompt();
                    return;
                }
                printText(path + '\n');
            } catch (Exception e) {
                MainFrame.printERR("sftp - " + cmd + ": error (" + e.getMessage() + ")");
            }
            printPrompt();
            return;
        }

        if (cmd.equals("get")
                || cmd.equals("get-resume") || cmd.equals("get-append")
                || cmd.equals("put")
                || cmd.equals("put-resume") || cmd.equals("put-append")) {
            if (cmds.size() != 1 && cmds.size() != 2) {
                MainFrame.printERR("Too much parameters for " + cmd + " command");
                printPrompt();
                return;
            }
            String p1 = (String) cmds.get(0);
            // String p2=p1;
            String p2 = ".";
            if (cmds.size() == 2) {
                p2 = (String) cmds.get(1);
            }
            if (cmd.startsWith("get")) {
                int mode = ChannelSftp.OVERWRITE;
                switch (cmd) {
                    case "get-resume":
                        mode = ChannelSftp.RESUME;
                        break;
                    case "get-append":
                        mode = ChannelSftp.APPEND;
                        break;
                }
                Get getThread = new Get(p1, p2, mode);
                try {
                    getThread.join();
                } catch (InterruptedException e) {
                    MainFrame.printERR("sftp - " + cmd + ": error (" + e.getMessage() + ")");
                }
            } else {
                int mode = ChannelSftp.OVERWRITE;
                switch (cmd) {
                    case "put-resume":
                        mode = ChannelSftp.RESUME;
                        break;
                    case "put-append":
                        mode = ChannelSftp.APPEND;
                        break;
                }
                Put putThread = new Put(p1, p2, mode);
                try {
                    putThread.join();
                } catch (InterruptedException e) {
                    MainFrame.printERR("sftp - " + cmd + ": error (" + e.getMessage() + ")");
                }
            }
            MainFrame.printDEB(cmd);
            printPrompt();
            return;
        }

        if (cmd.equals("ln") || cmd.equals("symlink") || cmd.equals("rename")) {
            if (cmds.size() != 2) {
                printPrompt();
                return;
            }
            String p1 = (String) cmds.get(0);
            String p2 = (String) cmds.get(1);
            if (cmd.equals("rename")) {
                try {
                    chanSFTP.rename(p1, p2);
                } catch (SftpException e) {
                    MainFrame.printERR("sftp - " + cmd + ": error (" + e.getMessage() + ")");
                }
            } else {
                try {
                    chanSFTP.symlink(p1, p2);
                } catch (SftpException e) {
                    MainFrame.printERR("sftp - " + cmd + ": error (" + e.getMessage() + ")");
                }
            }
            printPrompt();
            return;
        }

        if (cmd.equals("stat") || cmd.equals("lstat")) {
            if (cmds.size() != 1) {
                printPrompt();
                return;
            }
            String p1 = (String) cmds.get(0);
            SftpATTRS attrs = null;
            if (cmd.equals("stat")) {
                try {
                    attrs = chanSFTP.stat(p1);
                } catch (SftpException e) {
                    MainFrame.printERR("sftp - " + cmd + ": error (" + e.getMessage() + ")");
                }
            } else {
                try {
                    attrs = chanSFTP.lstat(p1);
                } catch (SftpException e) {
                    MainFrame.printERR("sftp - " + cmd + ": error (" + e.getMessage() + ")");
                }
            }
            if (attrs != null) {
                printText(attrs.toString() + '\n');
            } else {
            }
            printPrompt();
            return;
        }

        if (cmd.equals("readlink")) {
            if (cmds.size() != 1) {
                printPrompt();
                return;
            }
            String p1 = (String) cmds.get(0);
            String filename;
            try {
                filename = chanSFTP.readlink(p1);
                printText(filename + '\n');
            } catch (SftpException e) {
                MainFrame.printERR("sftp - " + cmd + ": error (" + e.getMessage() + ")");
            }
            printPrompt();
            return;
        }

        if (cmd.equals("realpath")) {
            if (cmds.size() != 1) {
                printPrompt();
                return;
            }
            String p1 = (String) cmds.get(0);
            String filename;
            try {
                filename = chanSFTP.realpath(p1);
                printText(filename + '\n');
            } catch (SftpException e) {
                MainFrame.printERR("sftp - " + cmd + ": error (" + e.getMessage() + ")");
            }
            printPrompt();
            return;
        }

        if (cmd.equals("version")) {
            printText("SFTP protocol version " + chanSFTP.version() + '\n');
            printPrompt();
            return;
        }

        if (cmd.equals("help") || cmd.equals("?")) {
            printText(help + '\n');
            printPrompt();
            return;
        }

        printText("unimplemented command: " + cmd + '\n');
        printPrompt();
    }

    private void printPrompt() {
            outputTA.append("sftp> ");
            outputTA.setCaretPosition(outputTA.getDocument().getLength());
    }

    private void printText(String text) {
            outputTA.append(text);
    }

    public ChannelSftp getChannel() {
        return chanSFTP;
    }
    
    public Session getSession() {
        return session;
    }

    public void setPort(int port) {
        this.sftpPort = port;
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

            session = jsch.getSession(user, host, sftpPort);
            // username and password will be given via UserInfo interface.
            session.setUserInfo(ui);
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            chanSFTP = (ChannelSftp) channel;
            printText("sftp> ");
            if (session.isConnected() && chanSFTP.isConnected()) {
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
        try {
            if (chanSFTP != null) {
                chanSFTP.exit();
            }
            if (session != null) {
                session.disconnect();
            }
        } catch (Exception e) {
            MainFrame.printERR("SFTP stop error (" + e.getMessage() + ")");
        }
    }
    
    private static String help =
            "      Available commands:\n"
            + "      * means unimplemented command.\n"
            + "cd path                       Change remote directory to 'path'\n"
            + "lcd path                      Change local directory to 'path'\n"
            + "chgrp grp path                Change group of file 'path' to 'grp'\n"
            + "chmod mode path               Change permissions of file 'path' to 'mode'\n"
            + "chown own path                Change owner of file 'path' to 'own'\n"
            + "help                          Display this help text\n"
            + "get remote-path [local-path]  Download file\n"
            + "get-resume remote-path [local-path]  Resume to download file.\n"
            + "get-append remote-path [local-path]  Append remote file to local file\n"
            + "lls [ls-options [path]]       Display local directory listing\n"
            + "ln oldpath newpath            Symlink remote file\n"
            + "*lmkdir path                  Create local directory\n"
            + "lpwd                          Print local working directory\n"
            + "ls [path]                     Display remote directory listing\n"
            + "*lumask umask                 Set local umask to 'umask'\n"
            + "mkdir path                    Create remote directory\n"
            + "put local-path [remote-path]  Upload file\n"
            + "put-resume local-path [remote-path]  Resume to upload file\n"
            + "put-append local-path [remote-path]  Append local file to remote file.\n"
            + "pwd                           Display remote working directory\n"
            + "stat path                     Display info about path\n"
            + "exit                          Quit sftp\n"
            + "quit                          Quit sftp\n"
            + "rename oldpath newpath        Rename remote file\n"
            + "rmdir path                    Remove remote directory\n"
            + "rm path                       Delete remote file\n"
            + "symlink oldpath newpath       Symlink remote file\n"
            + "readlink path                 Check the target of a symbolic link\n"
            + "realpath path                 Canonicalize the path\n"
            + "rekey                         Key re-exchanging\n"
            + "compression level             Packet compression will be enabled\n"
            + "version                       Show SFTP version\n"
            + "?                             Synonym for help";

    public class Get extends Thread {

        String p1;
        String p2;
        int mode;

        public Get(String p1, String p2, int mode) {
            this.p1 = p1;
            this.p2 = p2;
            this.mode = mode;
            this.start();
        }

        @Override
        public void run() {
            try {
                chanSFTP.get(p1, p2, new MyProgressMonitor(this), mode);
            } catch (SftpException e) {
                MainFrame.printERR("SftpException: " + e.getMessage());
            }
        }
    }

    public class Put extends Thread {

        String p1;
        String p2;
        int mode;

        public Put(String p1, String p2, int mode) {
            this.p1 = p1;
            this.p2 = p2;
            this.mode = mode;
            this.start();
        }

        @Override
        public void run() {
            try {
                chanSFTP.put(p1, p2, new MyProgressMonitor(this), mode);
            } catch (SftpException e) {
                String msg = e.getMessage();
                MainFrame.printERR(msg);
            }
        }
    }
}
