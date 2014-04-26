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
