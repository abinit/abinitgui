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
