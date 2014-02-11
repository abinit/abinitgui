/*
 Copyright (c) 2009-2014 Flavio Miguel ABREU ARAUJO (flavio.abreuaraujo@uclouvain.be)
                         Yannick GILLET (yannick.gillet@uclouvain.be)

Universit� catholique de Louvain, Louvain-la-Neuve, Belgium
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

import abinitgui.core.XMLConfigReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jdom.Attribute;
import org.jdom.Element;

public class ScriptBib {

    private ArrayList<Script> listOfScripts;

    public ScriptBib() {
        listOfScripts = new ArrayList<>();
    }

    /**
     * Will load the scripts by reading the file "fileName"
     *
     * @param fileName Name of the input file
     * @return True if the treatment was successful
     */
    public boolean loadScriptsFromFile(String fileName) {
        XMLConfigReader conf = new XMLConfigReader(fileName);

        if (conf.getRoot() != null) {
            List l1 = conf.getRoot().getChildren();
            Iterator i1 = l1.iterator();
            while (i1.hasNext()) {
                Element cur1 = (Element) i1.next();
                if (cur1.getName().equals("script")) {
                    List l2 = cur1.getChildren();
                    Iterator i2 = l2.iterator();

                    Script scr = new Script();

                    while (i2.hasNext()) {
                        Element cur2 = (Element) i2.next();
                        String elemName = cur2.getName();

                        if (elemName.equals("input")
                                || elemName.equals("output")
                                || elemName.equals("file")) {
                            // Needs to get children and parse the arguments
                            ScriptArgument arg = new ScriptArgument();

                            List l3 = cur2.getChildren();
                            Iterator i3 = l3.iterator();

                            while (i3.hasNext()) {
                                Element cur3 = (Element) i3.next();
                                String elemName3 = cur3.getName();

                                Attribute attr;
                                List lAttr = cur3.getAttributes();
                                Iterator iAttr = lAttr.iterator();
                                // On s'int�resse qu'a un seul atribut
                                if (iAttr.hasNext()) {
                                    attr = (Attribute) iAttr.next();
                                    String attrValue = attr.getValue();
                                    switch (elemName3) {
                                        case "name":
                                            arg.name = attrValue;
                                            break;
                                        case "help":
                                            arg.help = attrValue;
                                            break;
                                        case "default":
                                            arg.value = attrValue;
                                            break;
                                        case "type":
                                            arg.type = attrValue;
                                            break;
                                    }

                                }
                            }
                            switch (elemName) {
                                case "input":
                                    scr.addArgs(arg);
                                    break;
                                case "output":
                                    scr.addOutput(arg);
                                    break;
                                case "file":
                                    scr.addInput(arg);
                                    break;
                            }
                        } else {
                            Attribute attr;
                            List lAttr = cur2.getAttributes();
                            Iterator iAttr = lAttr.iterator();
                            // On s'int�resse qu'a un seul atribut
                            if (iAttr.hasNext()) {
                                attr = (Attribute) iAttr.next();
                                String attrValue = attr.getValue();
                                switch (elemName) {
                                    case "filename":
                                        scr.setFileName(attrValue);
                                        break;
                                    case "title":
                                        scr.setTitle(attrValue);
                                        break;
                                    case "description":
                                        scr.setDescription(attrValue);
                                        break;
                                    case "program":
                                        scr.setProgram(attrValue);
                                        break;
                                }

                            }
                        }
                    }

                    listOfScripts.add(scr);
                } else {
                    // TODO: What should I do ?
                }
            }
        } else {
            // TODO: What should I do ?
        }
        return false;
    }

    public ArrayList<Script> getList() {
        return listOfScripts;
    }
}
