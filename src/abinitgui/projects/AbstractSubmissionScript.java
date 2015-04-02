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

public abstract class AbstractSubmissionScript implements SubmissionScript 
{
    
    protected String nbProcs = "1";
    protected String memoryMax = "2000";
    protected String timeLimit = "2:00:00";
    protected String email = "yannick.gillet@uclouvain.be";
    protected String preProcessPart = "";
    protected String postProcessPart = "";
    protected String mpiPath = "mpirun"; // /cvos/shared/apps/openmpi/intel/64/1.3.1/bin/
    protected String abinitPath = "";
    protected String inputPath = "";
    protected String logPath = "";
    protected boolean parallel = false;
    protected String simName = "simulation";
    protected String system = "";
    protected String cdPart = "";

    @Override
    public String getSimName() 
    {
        return simName;
    }
    
    @Override
    public void setSimName(String simName)
    {
        this.simName = simName;
    }
    
    @Override
    public String getNbProcs() {
        return nbProcs;
    }

    @Override
    public void setNbProcs(String nbProcs) {
        this.nbProcs = nbProcs;
    }

    @Override
    public String getMemoryMax() {
        return memoryMax;
    }

    @Override
    public void setMemoryMax(String memory) {
        this.memoryMax = memory;
    }

    @Override
    public String getTimeLimit() {
        return timeLimit;
    }

    @Override
    public void setTimeLimit(String seconds) {
        this.timeLimit = seconds;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPreProcessPart() {
        return preProcessPart;
    }

    @Override
    public void setPreProcessPart(String part) {
        this.preProcessPart = part;
    }

    @Override
    public String getPostProcessPart() {
        return postProcessPart;
    }

    @Override
    public void setPostProcessPart(String part) {
        this.postProcessPart = part;
    }

    @Override
    public String getMPIPath() {
        return mpiPath;
    }

    @Override
    public void setMPIPath(String path) {
        this.mpiPath = path;
    }

    @Override
    public boolean isParallel() {
        return parallel;
    }

    @Override
    public void setParallel(boolean parallel) {
        this.parallel = parallel;
    }

    @Override
    public String getAbinitPath() {
        return abinitPath;
    }

    @Override
    public void setAbinitPath(String path) {
        this.abinitPath = path;
    }

    @Override
    public String getInputPath() {
        return inputPath;
    }

    @Override
    public void setInputPath(String path) {
        this.inputPath = path;
    }

    @Override
    public String getLogPath() {
        return logPath;
    }

    @Override
    public void setLogPath(String path) {
        this.logPath = path;
    }
    
    @Override
    public String getSystem() {
        return system;
    }
    
    @Override
    public void setSystem(String system) {
        this.system = system;
    }
    
    @Override
    public String getCDPart() {
        return cdPart;
    }

    @Override
    public void setCDPart(String part) {
        this.cdPart = part;
    }
    
}
