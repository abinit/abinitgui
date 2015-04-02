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

public interface SubmissionScript 
{
    /*
     * Accessors to fields
     */
    
    public String getSimName();
    
    public void setSimName(String simName);
    
    public String getNbProcs();
    
    public void setNbProcs(String nbProcs);
    
    public String getMemoryMax();
    
    public void setMemoryMax(String memory);

    public String getTimeLimit();
    
    public void setTimeLimit(String time);
    
    public String getEmail();
    
    public void setEmail(String email);
    
    public String getPreProcessPart();
    
    public void setPreProcessPart(String part);
    
    public String getCDPart();
    
    public void setCDPart(String part);
    
    public String getPostProcessPart();
    
    public void setPostProcessPart(String part);
    
    public String getMPIPath();
    
    public void setMPIPath(String path);
    
    public boolean isParallel();
    
    public void setParallel(boolean parallel);
    
    public String getAbinitPath();
    
    public void setAbinitPath(String path);
    
    public String getInputPath();
    
    public void setInputPath(String path);
    
    public String getLogPath();
    
    public void setLogPath(String path);

    public String getSystem();
    
    public void setSystem(String system);
    
    
    /*
     * Actions
     */
    public void writeToFile(String fileName);
}
