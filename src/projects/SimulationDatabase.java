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

import abinitgui.MainFrame;
import abinitgui.XMLConfigReader;
import projects.Simulation;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.jdom.Attribute;
import org.jdom.Element;

/**
 *
 * @author yannick
 */
public class SimulationDatabase {

    private HashMap<String, Simulation> dict;
    private MainFrame mf;

    public SimulationDatabase(MainFrame mf) {
        this.mf = mf;

        dict = new HashMap<String, Simulation>();


    }

    public void updateAllStatus() {
        Iterator<Simulation> iter = getIterator();

        while (iter.hasNext()) {
            Simulation simu = iter.next();

            updateSimulation(simu);
        }
    }

    public void updateSimulation(Simulation simu) {
        // TODO
    }

    public void addSimulation(Simulation simu) {
        Simulation simu2 = dict.get(simu.getName());

        if (simu2 != null) {
            mf.printERR("Another simulation with this name already exists");
            return;
        }

        dict.put(simu.getName(), simu);
    }

    public void removeSimulation(Simulation simu) {
        Simulation simu2 = dict.remove(simu.getName());

        if (simu2 == null) {
            mf.printERR("This simulation does not exist !");
            return;
        }
    }

    public Iterator<Simulation> getIterator() {
        Iterator iter = dict.values().iterator();
        return iter;
    }

    public void loadDatabaseFromFile(String fileName) {
        XMLConfigReader conf = new XMLConfigReader(fileName);

        if (conf.getRoot() != null) {
            List l1 = conf.getRoot().getChildren();
            Iterator i1 = l1.iterator();
            while (i1.hasNext()) {
                Element cur1 = (Element) i1.next();
                if (cur1.getName().equals("simulation")) {
                    List l2 = cur1.getChildren();
                    Iterator i2 = l2.iterator();

                    Simulation simu = new Simulation();

                    while (i2.hasNext()) {
                        Element cur2 = (Element) i2.next();
                        String elemName = cur2.getName();

                        Attribute attr;
                        List lAttr = cur2.getAttributes();
                        Iterator iAttr = lAttr.iterator();
                        // On s'intéresse qu'a un seul atribut
                        if (iAttr.hasNext()) {
                            attr = (Attribute) iAttr.next();
                            String attrValue = attr.getValue();

                            if (elemName.equals("filename")) {
                                simu.setFileName(attrValue);
                            } else if (elemName.equals("name")) {
                                simu.setName(attrValue);
                            } else if (elemName.equals("status")) {
                                if (attrValue.equals("running")) {
                                    simu.setStatus(Simulation.RUNNING);
                                } else if (attrValue.equals("finished")) {
                                    simu.setStatus(Simulation.FINISHED);
                                } else {
                                    simu.setStatus(Simulation.READY);
                                }
                            }

                        }
                    }

                    addSimulation(simu);
                } else {
                    // What should I do ?
                }
            }
        } else {
            // What should I do ?
        }
    }

    public Simulation getSimulation(String name) {
        return dict.get(name);
    }
}
