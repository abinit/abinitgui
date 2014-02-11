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

package abinitgui.projects;

import abinitgui.core.MainFrame;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SGEScript extends AbstractSubmissionScript {
    
    public SGEScript()
    {
        this.system = "SGE";
        this.parallel = true;
    }
    
    @Override
    public String toString()
    {
        String fileContent = "#!/bin/bash" + "\n"
                + "#" + "\n"
                + "# On old Green node" + "\n"
                + "#$ -l nb=false" + "\n"
                + "#" + "\n"
                + "# Ask for pe=parrallel environment, snode or openmpi" + "\n"
                + "# snode= same node, as the shared memory communication is the fastest" + "\n"
                + "#$ -pe openmpi " + nbProcs + "\n"
                + "# -pe snode8 8" + "\n"
                + "\n"
                + "# keep current working directory" + "\n"
                + "#$ -cwd" + "\n"
                + "\n"
                + "#$ -o SGE_out-$JOB_ID.log" + "\n"
                + "#$ -e SGE_err-$JOB_ID.log" + "\n"
                + "\n"
                + "# give a name to your job" + "\n"
                + "#$ -N " + simName + "\n"
                + "\n"
                + "# keep all the defined variables" + "\n"
                + "#$ -V" + "\n"
                + "#$ -l nb=false" + "\n"
                + "\n"
                + "# not mandatory: highmem=true (hm=true) for 32GB node" + "\n"
                + "# or hm=false for 16GB node" + "\n"
                + "# no hm argument does not take about the kind of node ram (16/32)" + "\n"
                + "# -l hm=true" + "\n"
                + "\n"
                + "# IMPORTANT: You need to specify the mem_free" + "\n"
                + "# h_vmem can also be set but mf is mandatory!" + "\n"
                + "# max 31G if hm=true and max 15G if hm=false" + "\n"
                + "#$ -l mf=" + memoryMax + "\n"
                + "\n"
                + "# Specify the requested time" + "\n"
                + "#$ -l h_rt=" + timeLimit + "\n"
                + "\n"
                + "# To be informed by email (besa= begin,end,stop,abort)" + "\n"
                + "#$ -M " + email + "\n"
                + "#$ -m besa" + "\n"
                //+ "# ---------------------------" + "\n"
                + "\n"
                + preProcessPart
                + "\n"
                + cdPart
                + "\n";
                
        if (parallel) {
            fileContent += "MPI=" + mpiPath + "\n"
                    + "${MPI} -np " + nbProcs + " " + abinitPath + " < "+ inputPath +" >& " + logPath;
        } else {
            fileContent += abinitPath + " < "+ inputPath +" >& " + logPath;
        }
        
        fileContent+= postProcessPart
                    + "\n";
        
        return fileContent;
    }
    
    @Override
    public void writeToFile(String fileName) {
        PrintWriter pw;
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            
            if(pw.checkError())
            {
                throw new IOException("Error with pw.");
            }
            
            // Writing file !
            pw.println(this.toString());
            
            if(pw.checkError())
            {
                throw new IOException("Error with pw.");
            }
            
            pw.println();
            
            pw.close();
            
        } catch (IOException ex) {
            MainFrame.printERR("Error while writing the file. (IOException: "
                    + ex.getMessage() + ")");
        } catch (Exception ex) {
            MainFrame.printERR("Error while writing the file. (Exception: "
                    + ex.getMessage() + ")");
        }
    }   
}
