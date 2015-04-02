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
                + "# snode= same node, as the shared memory communication is the fastest" + "\n";
        if(isParallel())
        {
            fileContent += 
                    "#$ -pe openmpi " + nbProcs + "\n"
                    + "# -pe snode8 8" + "\n"
                    + "\n";
        }
        else
        {
            fileContent +=
                    "#$ -pe openmpi 1 \n"
                    + "\n";
        }

        fileContent += ""
                + "# keep current working directory" + "\n"
                + "#$ -cwd" + "\n"
                + "\n"
                + "#$ -o SGE_out-$JOB_ID.log" + "\n"
                + "#$ -e SGE_err-$JOB_ID.log" + "\n"
                + "\n"
                + "# give a name to your job" + "\n";
        
        if(simName != null && !simName.isEmpty())
        {
            fileContent += "#$ -N " + simName + "\n";
        }
        else
        {
            fileContent += "#$ -N simulation \n";
        }
        
        fileContent += ""
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
                + "# max 31G if hm=true and max 15G if hm=false" + "\n";
        
        if(memoryMax != null && !memoryMax.isEmpty())
        {
            fileContent += "#$ -l mf=" + memoryMax + "\n";
        }
        
        fileContent += ""
                + "\n"
                + "# Specify the requested time" + "\n";
        if(timeLimit != null && !timeLimit.isEmpty())
        {
            fileContent += "#$ -l h_rt=" + timeLimit + "\n";
        }
        
        fileContent += ""
                + "\n"
                + "# To be informed by email (besa= begin,end,stop,abort)" + "\n";
        
        if(email != null && !email.isEmpty())
        {
            fileContent += "#$ -M " + email + "\n"
                + "#$ -m besa" + "\n";
        }
        
        fileContent += ""
                //+ "# ---------------------------" + "\n"
                + "\n"
                + preProcessPart
                + "\n"
                + cdPart
                + "\n";
                
        if (parallel) {
            fileContent += "MPI=" + mpiPath + "\n"
                    + "${MPI} -np " + nbProcs + " " + abinitPath + " < " +inputPath +" >& " + logPath;
        } else {
            fileContent += abinitPath + " < "+ inputPath +" >& " + logPath;
        }
        
        fileContent += "\n";
        
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
