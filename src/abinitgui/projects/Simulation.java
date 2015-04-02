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

public class Simulation {

    private String name = "default";
    private String inputFileName = "./test3.in";
    private SubmissionScript script;
    public RemoteJob job;

    public Simulation() {
        job = new RemoteJob();
        script = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public void setInputFileName(String fileName) {
        this.inputFileName = fileName;
    }

    @Override
    public String toString() {
        return name;
        //return "Simulation(name = " + name + "; fileName = " + inputFileName + ")";
    }
    
    public void printInfos() {
        job.printInfos();
    }

    public int getStatus() {
        return job.getStatus();
    }

    public RemoteJob getRemoteJob() {
        return job;
    }
    
    public void kill() {
        job.kill();
    }

    public void setRemoteJob(RemoteJob job) {
        this.job = job;
    }

    public void updateStatus() {
        job.updateStatus();
    }
    
    public void createFileTree(Machine mach)
    {
        String path = mach.getSimulationPath();
        if (path.equals("")) {
            path = ".";
        }
        mach.createTree(path);
        if(mach.getType() == Machine.REMOTE_MACHINE || mach.getType() == Machine.GATEWAY_MACHINE)
        {
            MainFrame.getLocalExec().createTree(path);
        }
    }
    
    public boolean submit()
    {
        throw new UnsupportedOperationException("Not yet supported.");
    }
    
    public void downloadLog()
    {
        throw new UnsupportedOperationException("Not yet supported.");
    }
    
    public void downloadOutput()
    {
        throw new UnsupportedOperationException("Not yet supported.");
    }

    /**
     * @return the script
     */
    public SubmissionScript getScript() {
        return script;
    }

    /**
     * @param script the script to set
     */
    public void setScript(SubmissionScript script) {
        this.script = script;
    }
}
