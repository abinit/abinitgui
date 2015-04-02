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

import java.io.InputStream;
import java.util.Arrays;

public class LocalExec implements Exec {

    private Runtime rt;
    private Process proc;
    private LocalExecOutput leErr;
    private LocalExecOutput leOut;
    private String succesMSG = null;
    private String errorMSG = null;
    final private int ERROR_TYPE = 1;
    final private int SUCCES_TYPE = 0;

    public LocalExec() {
        rt = Runtime.getRuntime();
    }

    @Override
    public RetMSG sendCommand(String CMD) {
        try {
            proc = rt.exec(CMD);
        } catch (Exception e) {
            errorMSG += e;
            return new RetMSG(-1, errorMSG, CMD);
        }
        try {
            leErr = new LocalExecOutput(proc.getErrorStream(), ERROR_TYPE);
            leOut = new LocalExecOutput(proc.getInputStream(), SUCCES_TYPE);
            int exitVal = proc.waitFor();
            if (exitVal == RetMSG.SUCCES) {
                leOut.join();
                return new RetMSG(RetMSG.SUCCES, succesMSG, CMD);
            } else {
                leErr.join();
                return new RetMSG(exitVal, errorMSG, CMD);
            }
        } catch (Exception e) {
            errorMSG += e;
            return new RetMSG(-1, errorMSG, CMD);
        }
    }
    
    @Override
    public RetMSG sendCommand(String CMD[]) {
        try {
            proc = rt.exec(CMD);
        } catch (Exception e) {
            errorMSG += e;
            return new RetMSG(-1, errorMSG, Arrays.toString(CMD));
        }
        try {
            leErr = new LocalExecOutput(proc.getErrorStream(), ERROR_TYPE);
            leOut = new LocalExecOutput(proc.getInputStream(), SUCCES_TYPE);
            int exitVal = proc.waitFor();
            if (exitVal == RetMSG.SUCCES) {
                leOut.join();
                return new RetMSG(RetMSG.SUCCES, succesMSG, Arrays.toString(CMD));
            } else {
                leErr.join();
                return new RetMSG(exitVal, errorMSG, Arrays.toString(CMD));
            }
        } catch (Exception e) {
            errorMSG += e;
            return new RetMSG(-1, errorMSG, Arrays.toString(CMD));
        }
    }
    
    @Override
    public void mkdir(String dir) {
        if (Utils.mkdir(dir)) {
            MainFrame.printOUT("Succes: mkdir " + dir + ".");
        } else {
            if (Utils.exists(dir)) {
                MainFrame.printDEB("The local directory '" + dir + "' exists!");
            } else {
                MainFrame.printERR("Error: mkdir: cannot create directory '" + dir + "' !");
            }
        }
    }
    
    @Override
    public void createTree(String path) {
        mkdir(path);
        mkdir(path + "/input");
        mkdir(path + "/output");
        mkdir(path + "/wholedata");
        mkdir(path + "/logfiles");
        mkdir(path + "/pseudopot");
        mkdir(path + "/scripts");
    }

    public class LocalExecOutput extends Thread {

        private InputStream input;
        private int streamType;

        public LocalExecOutput(InputStream input, int streamType) {
            this.input = input;
            this.streamType = streamType;
            this.start();
        }

        @Override
        public void run() {
            if (streamType == ERROR_TYPE) {
                errorMSG = "";
            } else {
                succesMSG = "";
            }
            byte[] buf = new byte[1024];
            int i;
            try {
                while (true) {
                    i = input.read(buf, 0, 1024);
                    if (i <= 0) {
                        break;
                    } else {
                        byte[] tmp = new byte[i];
                        /*for (int k = 0; k < i; k++) {
                            tmp[k] = buf[k];
                        }*/
                        System.arraycopy(buf, 0, tmp, 0, i);
                        
                        if (streamType == ERROR_TYPE) {
                            errorMSG += new String(tmp);
                        } else {
                            succesMSG += new String(tmp);
                        }
                    }
                    if (!(input.available() > 0)) {
                        break;
                    }
                }
            } catch (Exception e) {
                MainFrame.printERR("LocalExec: " + e.getMessage());
            }
        }
    }
}
