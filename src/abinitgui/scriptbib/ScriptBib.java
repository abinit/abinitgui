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

import abinitgui.core.MainFrame;
import abinitgui.core.XMLConfigReader;
import abinitgui.projects.Machine;
import abinitgui.projects.ProjectConstructor;
import abinitgui.projects.ProjectRepresenter;
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

public class ScriptBib {

    private ArrayList<Script> listOfScripts;

    public ScriptBib() {
        listOfScripts = new ArrayList<>();
    }
    
    public void loadFromFile(String fileName) throws IOException {
        try{
            Yaml yaml = new Yaml(new ScriptConstructor());
            Object data = yaml.load(new FileReader(fileName));
            this.listOfScripts = (ArrayList<Script>) data;
        } catch(RuntimeException e)
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

        String txt = yaml.dump(this.listOfScripts);
        System.out.println(txt);

        PrintWriter pw;
        pw = new PrintWriter(new FileWriter(fileName));
        if (pw.checkError()) {
            MainFrame.printERR("Error while writing.");
        }

        pw.println(txt);

        if (pw.checkError()) {
            MainFrame.printERR("Error while writing.");
        }
        pw.close();
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
