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
import abinitgui.core.XMLConfigReader;
import java.io.FileNotFoundException;
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
        dict = new HashMap<>();
        this.fileName = null;
    }

    public HashMap<String, Simulation> getDict() {
        return dict;
    }

    public void setDict(HashMap<String, Simulation> map) {
        this.dict = map;
    }
    
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
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
        try{
            Object data = yaml.load(new FileReader(fileName));
            this.dict = (HashMap<String, Simulation>) data;
        }
        catch(RuntimeException e)
        {
            throw new IOException(e.getMessage());
        }
    }

    public static Project fromFile(String fileName) throws IOException {
        Yaml yaml = new Yaml(new ProjectConstructor());
        try{
            Project p = (Project) (yaml.load(new FileReader(fileName)));
            p.fileName = fileName;
            return p;
        }
        catch (RuntimeException e)
        {
            throw new IOException(e.getMessage());
        }
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
                                            simu.getRemoteJob().setStatus(RemoteJob.RUNNING);
                                            break;
                                        case "finished":
                                            simu.getRemoteJob().setStatus(RemoteJob.COMPLETED);
                                            break;
                                        default:
                                            simu.getRemoteJob().setStatus(RemoteJob.READY);
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
