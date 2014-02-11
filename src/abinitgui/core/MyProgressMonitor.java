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
