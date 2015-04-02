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

package abinitgui.scriptbib;

import java.util.ArrayList;

public class Script {

    public String fileName;
    public ArrayList<ScriptArgument> listArgs;
    public ArrayList<ScriptArgument> listOutput;
    public ArrayList<ScriptArgument> listInput;
    public String title;
    public String description;
    public String program;
    public boolean runRemote;
    
    public Script()
    {
        listArgs = new ArrayList<>();
        listOutput = new ArrayList<>();
        listInput = new ArrayList<>();
        runRemote = false;
    }

    void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    void setTitle(String title) 
    {
        this.title = title;
    }

    void setDescription(String description) 
    {
        this.description = description;
    }
    
    void setProgram(String program)
    {
        this.program = program;
    }
    
    void setRunRemote(boolean runRemote)
    {
        this.runRemote = runRemote;
    }

    void addArgs(ScriptArgument arg) 
    {
        listArgs.add(arg);
    }

    void addOutput(ScriptArgument attrValue) 
    {
        listOutput.add(attrValue);
    }

    void addInput(ScriptArgument attrValue) 
    {
        listInput.add(attrValue);
    }
    
    @Override
    public String toString()
    {
        return title+":\n"+"Description : "+description+"\nFileName : "
                +fileName+"\nlistArgs : "+listArgs;
    }
    
}
