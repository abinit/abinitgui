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

package abinitgui.variables;

import abinitgui.core.MainFrame;
import abinitgui.core.Utils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class AllInputVars
{
    private HashMap<String, Variable> database2;
    private TreeSet<String> listKeys2;

    public AllInputVars()
    {
        database2 = new HashMap<>();
        listKeys2 = new TreeSet<>();
        loadVars();
    }
    
    public final void loadVars()
    {
        Constructor constructor = new VariableConstructor();

        Yaml yaml2 = new Yaml(constructor);

        try {
            String s2 = Utils.fileToString("abinit_vars.yml");
            Object obj2 = yaml2.load(s2);
            listKeys2.clear();
            Iterator<Variable> iter2 = ((ArrayList<Variable>)obj2).iterator();
            while(iter2.hasNext())
            {
                Variable var = iter2.next();
                listKeys2.add(var.getVarname());
                database2.put(var.getVarname(), var);
            }
        } catch (FileNotFoundException ex) {
            MainFrame.printERR("File 'tmp_struct.yml' does not exist."
                    + " (IOException: " + ex.getMessage() + ")\n"
                    + "Help and pre-processing might not be available.");
        } catch (IOException ex) {
            MainFrame.printERR("Error reading file 'tmp_struct.yml'."
                    + " (IOException: " + ex.getMessage() + ")\n"
                    + "Help and pre-processing might not be available.");
        } catch (Exception ex) {
             MainFrame.printERR("Error reading file 'tmp_struct.yml'."
                    + " (IOException: " + ex.getMessage() + ")\n"
                    + "Help and pre-processing might not be available.");
        }

    }

    public TreeSet<String> getListKeys() {
        return listKeys2;
    }
    
    public Variable getVar(String varName) {
        return database2.get(varName);
    }
}
