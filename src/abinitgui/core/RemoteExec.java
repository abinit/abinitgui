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

//import com.jcraft.jsch.*;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import jsshterm.misc.XDetails;

public class RemoteExec implements Exec {

    private Session session;
    private InputStream in;
    private InputStream err;
    private String ruser;
    private String rhost;
    private int rport;
    private String succesMSG = null;
    private String errorMSG = null;
    private String pass;
    private String prvkey;
    private boolean useKey;

    public RemoteExec(String ruser, String rhost, int rport) {
        this.ruser = ruser;
        this.rhost = rhost;
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

    public boolean start() {
        try {
            if (MainFrame.DEBUG) {
                JSch.setLogger(new MyLogger());
            }
            JSch jsch = new JSch();

            MyUserInfo ui = new MyUserInfo();

            if (this.useKey) {
                if (prvkey == null) {
                    return false;
                } else {
                    jsch.addIdentity(prvkey, pass);
                }
            } else {
                ui.setPassword(pass);
            }

            session = jsch.getSession(ruser, rhost, rport);

            session.setX11Host("127.0.0.1");
            session.setX11Port(6000);

            XDetails xd = new XDetails();
            String X11Cookie = null;
            try {
                X11Cookie = jsshterm.core.Utils.byteArrayToHexString(
                        xd.getX11Cookie());
                session.setX11Cookie(X11Cookie);
            } catch (Exception ex) {
                MainFrame.printDEB("X11 Cookie: " + X11Cookie);
            }

            // username and password will be given via UserInfo interface.
            session.setUserInfo(ui);
            session.connect();

            //System.out.println("DEBUG connect RemoteExec");
            if (session.isConnected()) {
                return true;
            } else {
                return false;
            }
        } catch (JSchException e) {
            MainFrame.printERR("JSchException: " + e.getMessage());
            return false;
        } catch (IOException e) {
            MainFrame.printERR("IOException: " + e.getMessage());
            return false;
        }
    }

    public void stop() {
        try {
            if (session != null) {
                session.disconnect();
            }
        } catch (Exception e) {
            MainFrame.printERR(e.getMessage());
        }
    }

    @Override
    synchronized public RetMSG sendCommand(String CMD) {

        if (CMD.equals("")) {
            return new RetMSG(-2, "Empty command!", CMD);
        }
        ArrayList cmds = new ArrayList();
        cmds.clear();

        StringTokenizer st = new StringTokenizer(CMD);
        while (st.hasMoreTokens()) {
            cmds.add(st.nextToken());
        }

        String cmd = (String) cmds.get(0);
        switch (cmd) {
            case "put":
            case "PUT":
                if (cmds.size() == 2) {
                    String localfile = (String) cmds.get(1);
                    String remotefile = ".";
                    return scpL2R(localfile, remotefile);
                } else if (cmds.size() == 3) {
                    String localfile = (String) cmds.get(1);
                    String remotefile = (String) cmds.get(2);
                    return scpL2R(localfile, remotefile);
                } else {
                    return new RetMSG(-2, "Bad number of parameters !", CMD);
                }
            case "get":
            case "GET":
                if (cmds.size() == 2) {
                    String remotefile = (String) cmds.get(1);
                    String localfile = ".";
                    return scpR2L(remotefile, localfile);
                } else if (cmds.size() == 3) {
                    String remotefile = (String) cmds.get(1);
                    String localfile = (String) cmds.get(2);
                    return scpR2L(remotefile, localfile);
                } else {
                    return new RetMSG(-2, "Bad number of parameters !", CMD);
                }
            default:
                Channel channel;
                try {
                    channel = session.openChannel("exec");
                    channel.setXForwarding(true);
                } catch (JSchException e) {
                    //mainFrame.printERR(e.getMessage());
                    return new RetMSG(-1, e.toString(), CMD);
                }
                ((ChannelExec) channel).setCommand(CMD);
                channel.setInputStream(null);
                try {
                    err = ((ChannelExec) channel).getErrStream();
                    in = channel.getInputStream();
                } catch (IOException e) {
                    //mainFrame.printERR(e.getMessage());
                    return new RetMSG(-1, e.toString(), CMD);
                }
                try {
                    channel.connect();
                } catch (JSchException e) {
                    //mainFrame.printERR(e.getMessage());
                    return new RetMSG(-1, e.toString(), CMD);
                }
                byte[] tmp = new byte[1024];
                try {
                    succesMSG = "";
                    // TODO ceci peut boucler trÃ¨s longtemps en attendant qu'une commande se termine !
                    while (true) {
                        while (true) {
                            int len = in.read(tmp, 0, tmp.length);
                            if (len <= 0) {
                                break;
                            }
                            succesMSG += new String(tmp, 0, len);
                        }
                        if (channel.isClosed()) {
                            //mainFrame.printDEB("Exit-status: " + channel.getExitStatus());
                            int retCode = channel.getExitStatus();
                            if (retCode != 0) {
                                errorMSG = "";
                                while(true) {
                                    int i = err.read(tmp, 0, tmp.length);
                                    if (i < 0) {
                                        break;
                                    }
                                    errorMSG += new String(tmp,0,i);
                                }
                                return new RetMSG(retCode, errorMSG, CMD);
                            }
                            return new RetMSG(retCode, succesMSG, CMD);
                        }
                    }
                } catch (IOException e) {
                    //mainFrame.printERR(e.getMessage());
                    return new RetMSG(-1, e.toString(), CMD);
                }
        }
    }

    public RetMSG scpL2R(String lfile, String rfile) {
        FileInputStream fis = null;
        OutputStream out;
        try {
            int retVal;
            // exec 'scp -t rfile' remotely
            String command = "scp -p -t " + rfile;
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            // get I/O streams for remote scp
            out = channel.getOutputStream();
            in = channel.getInputStream();

            channel.connect();

            long t1 = System.nanoTime();
            
            retVal = checkAck(in);
            if (retVal != 0) {
                return new RetMSG(retVal, errorMSG, "scp " + lfile + " " + rfile);
            }
            // send "C0644 filesize filename", where filename should not include '/'
            long filesize = (new File(lfile)).length();
            command = "C0644 " + filesize + " ";
            if (lfile.lastIndexOf('/') > 0) {
                command += lfile.substring(lfile.lastIndexOf('/') + 1);
            } else {
                command += lfile;
            }
            command += "\n";
            out.write(command.getBytes());
            out.flush();
            
            retVal = checkAck(in);
            if (retVal != 0) {
                return new RetMSG(retVal, errorMSG, "scp " + lfile + " " + rfile);
            }

            // send a content of lfile
            fis = new FileInputStream(lfile);
            byte[] buf = new byte[1024];
            while (true) {
                int len = fis.read(buf, 0, buf.length);
                if (len <= 0) {
                    break;
                }
                out.write(buf, 0, len); //out.flush();
            }
            fis.close();
            fis = null;
            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();

            retVal = checkAck(in);
            if (retVal != 0) {
                return new RetMSG(retVal, errorMSG, "scp " + lfile + " " + rfile);
            }

            out.close();

            long t2 = System.nanoTime();
            MainFrame.printDEB("File " + lfile + " was uploaded in " +
                    Utils.formatTime(((double) (t2 - t1)) / 1000000000.0) + ".");

            channel.disconnect();
            
            return new RetMSG(RetMSG.SUCCES, "", "scp " + lfile + " " + rfile);
        } catch (JSchException | IOException e) {
            MainFrame.printERR(e.getMessage());
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception ee) {
                MainFrame.printERR(ee.getMessage());
            }
            return new RetMSG(-1, e.toString(), "scp " + lfile + " " + rfile);
        }
    }

    public RetMSG scpR2L(String rfile, String lfile) {
        FileOutputStream fos = null;
        try {
            int retVal;
            // exec 'scp -f rfile' remotely
            String command = "scp -f " + rfile;
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            String prefix = null;
            if (new File(lfile).isDirectory()) {
                prefix = lfile + File.separator;
            }

            // get I/O streams for remote scp
            OutputStream out = channel.getOutputStream();
            in = channel.getInputStream();

            channel.connect();

            long t1 = System.nanoTime();

            byte[] buf = new byte[1024];

            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();

            // Variable servant ÃÂ  tester si un fichier a ÃÂ©tÃÂ© reÃÂ§u !
            int test = 0;

            while (true) {
                int c = checkAck(in);

                if (c != 'C') {
                    break;
                } else {
                    test++;
                }

                // read '0644 '
                in.read(buf, 0, 5);

                long filesize = 0L;
                while (true) {
                    if (in.read(buf, 0, 1) < 0) {
                        // error
                        return new RetMSG(-2, "Reading filesize", "scp " +
                                rfile + " " + lfile);
                    }
                    if (buf[0] == ' ') {
                        break;
                    }
                    filesize = filesize * 10L + (long) (buf[0] - '0');
                }

                String file = null;
                for (int i = 0;; i++) {
                    in.read(buf, i, 1);
                    if (buf[i] == (byte) 0x0a) {
                        file = new String(buf, 0, i);
                        break;
                    }
                }

                //mainFrame.printDEB("filesize="+filesize+", file="+file);

                // send '\0'
                buf[0] = 0;
                out.write(buf, 0, 1);
                out.flush();

                // write in local file lfile
                fos = new FileOutputStream(prefix == null ? lfile : prefix + file);
                int foo;
                while (true) {
                    if (buf.length < filesize) {
                        foo = buf.length;
                    } else {
                        foo = (int) filesize;
                    }
                    foo = in.read(buf, 0, foo);
                    if (foo < 0) {
                        // error
                        return new RetMSG(-2, "Error: reading incomming stream",
                                "scp " + rfile + " " + lfile);
                    }
                    fos.write(buf, 0, foo);
                    filesize -= foo;
                    if (filesize == 0L) {
                        break;
                    }
                }
                fos.close();
                fos = null;

                retVal = checkAck(in);
                if (retVal != 0) {
                    return new RetMSG(retVal, errorMSG, "scp " + rfile + " " + lfile);
                }

                // send '\0'
                buf[0] = 0;
                out.write(buf, 0, 1);
                out.flush();
            }

            if (test != 0) {
                long t2 = System.nanoTime();
                MainFrame.printDEB("File " + lfile + " was downloaded in " +
                        Utils.formatTime(((double) (t2 - t1)) / 1000000000.0) + ".");

                channel.disconnect();

                return new RetMSG(RetMSG.SUCCES, "", "scp " + rfile + " " + lfile);
            } else {
                return new RetMSG(-2, "File " + rfile + " doesn't exist", "scp "
                        + rfile + " " + lfile);
            }
        } catch (JSchException | IOException e) {
            //printERR(e.getMessage());
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception ee) {
                MainFrame.printERR("Exception: " + e.getMessage());
            }
            return new RetMSG(-1, e.toString(), "scp " + rfile + " " + lfile);
        }
    }

    int checkAck(InputStream in) throws IOException {
        int b = in.read();
        // b may be 0 for success,
        //          1 for error,
        //          2 for fatal error,
        //          -1
        if (b == 0) {
            return b;
        }
        if (b == -1) {
            return b;
        }

        if (b == 1 || b == 2) {
            StringBuilder sb = new StringBuilder();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            } while (c != '\n');
            if (b == 1) { // error
                errorMSG = sb.toString();
                //printERR(sb.toString());
            }
            if (b == 2) { // fatal error
                errorMSG = sb.toString();
                //printERR(sb.toString());
            }
        }
        return b;
    }

    @Override
    public RetMSG sendCommand(String[] CMD) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }
    
    @Override
    public void mkdir(String dir) {
        String CMD = "mkdir " + dir;
        RetMSG retmsg;
        retmsg = sendCommand(CMD);
        if (retmsg.getRetCode() == RetMSG.SUCCES) {
            MainFrame.printOUT("Succes: " + retmsg.getCMD() + " => " +
                    Utils.removeEndl(retmsg.getRetMSG()) + ".");
        } else {
            if (retmsg.getRetCode() == 1) {
                MainFrame.printDEB("The remote directory `" + dir + "' exists!");
            } else {
                MainFrame.printERR("Error: " +
                        Utils.removeEndl(retmsg.getRetMSG()) + "!");
            }
        }
    }
    
    @Override
    public void createTree(String path)
    {
        mkdir(path);
        mkdir(path + "/input");
        mkdir(path + "/output");
        mkdir(path + "/wholedata");
        mkdir(path + "/logfiles");
        mkdir(path + "/pseudopot");
        mkdir(path + "/scripts");
    }
}
