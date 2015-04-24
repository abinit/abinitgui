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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class MachineDatabase implements Iterable<Machine> {

    private HashMap<String, Machine> list;

    public MachineDatabase() {
        list = new HashMap<>();
    }

    public void addMachine(Machine rm) {
        list.put(rm.getName(), rm);
    }

    public void removeMachine(Machine rm) {
        this.removeMachineWithName(rm.getName());
    }

    public Machine getMachine(String name) {
        return list.get(name);
    }

    public void removeMachineWithName(String name) {
        list.remove(name);
    }

    public void setAllNames() {
        ArrayList<Machine> machines = new ArrayList<>();
        for (Machine mach : this) {
            machines.add(mach);
        }

        this.list.clear();

        for (Machine mach : machines) {
            this.addMachine(mach);
        }
    }

    public HashMap<String, Machine> getMachineList() {
        return list;
    }

    @Override
    public Iterator<Machine> iterator() {
        return list.values().iterator();
    }

    public void loadFromFile(String fileName) throws IOException {
        try{
            Yaml yaml = new Yaml(new ProjectConstructor());
            Object data = yaml.load(new FileReader(fileName));
            this.list = (HashMap<String, Machine>) data;
        } catch(RuntimeException e)
        {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public void saveToFile(String fileName) throws IOException {
        setAllNames();

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setIndent(4);
        options.setPrettyFlow(true);
        Yaml yaml = new Yaml(new ProjectRepresenter(), new DumperOptions());

        String txt = yaml.dump(this.list);

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
}
