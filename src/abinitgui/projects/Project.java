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

package abinitgui.projects;

import abinitgui.core.MainFrame;
import abinitgui.core.XMLConfigReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.jdom.Attribute;
import org.jdom.Element;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public final class Project implements Iterable<Simulation> {

    private HashMap<String, Simulation> dict;
    private String fileName;
    private String pspPath;

    public Project() {
    }

    public HashMap<String, Simulation> getDict() {
        return dict;
    }

    public void setDict(HashMap<String, Simulation> map) {
        this.dict = map;
    }

    public Project(String fileName) {
        try {
            this.fileName = fileName;

            dict = new HashMap<>();

            if (fileName != null) {
                loadFromFile(fileName);
            }
        } catch (IOException ex) {
            MainFrame.printERR("Creating empty project!");
        }
    }

    public void updateAllStatus() {
        for (Simulation simu : this) {
            updateSimulation(simu);
        }
    }

    public void updateSimulation(Simulation simu) {
    }

    public void setAllNames() {
        ArrayList<Simulation> simus = new ArrayList<>();
        for (Simulation simu : this) {
            simus.add(simu);
        }

        this.dict.clear();

        for (Simulation simu : simus) {
            this.addSimulation(simu);
        }
    }

    public void addSimulation(Simulation simu) {
        Simulation simu2 = dict.get(simu.getName());

        if (simu2 != null) {
            MainFrame.printERR("Another simulation with this name already exists.");
            return;
        }

        dict.put(simu.getName(), simu);
    }

    public void removeSimulation(Simulation simu) {
        Simulation simu2 = dict.remove(simu.getName());

        if (simu2 == null) {
            MainFrame.printERR("This simulation does not exist!");
        }
    }

    public void save() throws IOException {
        setAllNames();
        this.saveToFile(this.fileName);
    }

    public void loadFromFile(String fileName) throws IOException {
        Yaml yaml = new Yaml(new ProjectConstructor());
        Object data = yaml.load(new FileReader(fileName));
        this.dict = (HashMap<String, Simulation>) data;
    }

    public static Project fromFile(String fileName) throws IOException {
        Yaml yaml = new Yaml(new ProjectConstructor());
        Project p = (Project) (yaml.load(new FileReader(fileName)));
        p.fileName = fileName;
        return p;
    }

    public void saveToFile(String fileName) throws IOException {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setIndent(4);
        options.setPrettyFlow(true);
        Yaml yaml = new Yaml(new ProjectRepresenter(), new DumperOptions());

        String txt = yaml.dump(this);
        System.out.println(txt);

        PrintWriter pw;
        pw = new PrintWriter(new FileWriter(fileName));
        if (pw.checkError()) {
            System.err.println("Error while writing");
        }

        pw.println(txt);

        if (pw.checkError()) {
            System.err.println("Error while writing");
        }
        pw.close();
    }

    public void loadDatabaseFromXMLFile(String fileName) {
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
                            switch (elemName) {
                                case "filename":
                                    simu.setInputFileName(attrValue);
                                    break;
                                case "name":
                                    simu.setName(attrValue);
                                    break;
                                case "status":
                                    switch (attrValue) {
                                        case "running":
                                            simu.setStatus(Simulation.RUNNING);
                                            break;
                                        case "finished":
                                            simu.setStatus(Simulation.FINISHED);
                                            break;
                                        default:
                                            simu.setStatus(Simulation.READY);
                                            break;
                                    }
                                    break;
                            }
                        }
                    }

                    addSimulation(simu);
                } else {
                    // TODO: What should I do ?
                }
            }
        } else {
            // TODO: What should I do ?
        }
    }

    public Simulation getSimulation(String name) {
        return dict.get(name);
    }

    @Override
    public Iterator<Simulation> iterator() {
        return dict.values().iterator();
    }

    public String getPSPPath() {
        return pspPath;
    }

    public void setPSPPath(String pspPath) {
        this.pspPath = pspPath;
    }
}
