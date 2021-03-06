/*
 Copyright (c) 2009-2013 Flavio Miguel ABREU ARAUJO (flavio.abreuaraujo@uclouvain.be)
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

package projects;

import core.MainFrame;
import projects.AbstractSubmissionScript;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yannick
 */
public class FrontendScript extends AbstractSubmissionScript
{
    private MainFrame mf;
    
    public FrontendScript()
    {
        this.system = "Frontend";
    }
    
    public FrontendScript(MainFrame mf)
    {
        this();
        this.mf = mf;
    }
    
    @Override
    public String toString()
    {
        String fileContent = "#!/bin/bash" + "\n";
//                + "\n"
//                + preProcessPart
//                + "\n";
                
        if (parallel) {
            fileContent += "mpirun -np " + nbProcs + " " + abinitPath + " < "+ inputPath +" >& " + logPath;
        } else {
            fileContent += abinitPath + " < "+ inputPath +" >& " + logPath;
        }
        
        fileContent+= postProcessPart
                    + "\n";
        
        return fileContent;
    }
    @Override
    public void writeToFile(String fileName) 
    {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            
            if(pw.checkError())
            {
                throw new IOException("Error with pw");
            }
            
            // Writing file !
            pw.println(this.toString());
            
            if(pw.checkError())
            {
                throw new IOException("Error with pw");
            }
            
            pw.println();
            
            pw.close();
            
        } catch (IOException ex) {
            mf.printERR("Error while writing the file");
        }
        
        
    }   
}
