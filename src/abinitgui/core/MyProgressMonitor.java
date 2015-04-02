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

import com.jcraft.jsch.SftpProgressMonitor;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class MyProgressMonitor implements SftpProgressMonitor {

        long count = 0;
        long prevcount = 0;
        long max = 0;
        final long begtime = System.nanoTime();
        long curtime = 0;
        long prevtime = System.nanoTime();
        JProgressBar pb_;
        JLabel lab_rate_;
        
        SFTPProgressDialog SFTPPD = null;

        public MyProgressMonitor(Thread thread) {
            if(SFTPPD == null) {
                SFTPPD = new SFTPProgressDialog(thread);
            } else {
                SFTPPD.dispose();
                SFTPPD = null;
                SFTPPD = new SFTPProgressDialog(thread);
            }
            this.pb_ = SFTPPD.getProgressBar();
            this.lab_rate_ = SFTPPD.getRateLabel();
        }

        @Override
        public void init(int op, String src, String dest, long max) {
            this.max = max;
            String info = ((op == SftpProgressMonitor.PUT) ? "PUT" : "GET")
                    + ": " + src + " (" + Utils.inByteFormat(max) + ")";
            SFTPPD.setTitle(info);
            count = 0;
            percent = -1;
            pb_.setMaximum((int) max);
            pb_.setValue((int) this.count);
        }
        private long percent = -1;

        @Override
        public boolean count(long count) {
            this.count += count;

            if (percent >= this.count * 100 / max) {
                return true;
            }
            percent = this.count * 100 / max;

            //"Completed " + this.count + "(" + percent + "%) out of " + max + "."
            curtime = System.nanoTime();
            double timeTot = ((double) (curtime - begtime)) / 1000000000.0;
            double time = ((double) (curtime - prevtime)) / 1000000000.0;
            //double speed = ((double) this.count) / time;
            double speed = ((double) (this.count - this.prevcount)) / time;

            String timestr;
            if (timeTot < 1.0) {
                timestr = Math.ceil(timeTot * 1000.0) + " ms";
            } else {
                timestr = Utils.formatDouble(timeTot) + " s";
            }
            
            String rate ="Elapsed time " + timestr + " | Speed " +
                    Utils.inByteFormat(speed) + "/s (" + percent + "%)";
            lab_rate_.setText(rate);
            pb_.setValue((int) this.count);
            
            this.prevtime = this.curtime;
            this.prevcount = this.count;

            return true;
        }

        @Override
        public void end() {
            SFTPPD.dispose(); SFTPPD = null;
        }
    }
