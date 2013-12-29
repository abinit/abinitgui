/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projects;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author yannick
 */
public class MachineDatabase implements Iterable<Machine>
{
    private ArrayList<Machine> list;
    
    public MachineDatabase()
    {
        list = new ArrayList<>();
    }
    
    public void addMachine(Machine rm)
    {
        list.add(rm);
    }
    
    public void removeMachine(Machine rm)
    {
        list.remove(rm.getName());
    }
    
    public Machine getMachine(String name)
    {
        for(Machine mach : list)
        {
            if(mach.getName().equals(name))
            {
                return mach;
            }
        }
        return null;
    }
    
    public ArrayList<Machine> getMachineList()
    {
        return list;
    }
    
    public Iterator<Machine> iterator()
    {
        return list.iterator();
    }
    
    public void loadFromFile(String fileName) throws IOException
    {
        Yaml yaml = new Yaml(new ProjectConstructor());
        Object data = yaml.load(new FileReader(fileName));
        this.list = (ArrayList<Machine>)data;
    }
    
    public void saveToFile(String fileName) throws IOException
    {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setIndent(4);
        options.setPrettyFlow(true);
        Yaml yaml = new Yaml(new ProjectRepresenter(), new DumperOptions());
        
        String txt = yaml.dump(this.list);
        System.out.println(txt);
        
        PrintWriter pw = new PrintWriter(new FileWriter(fileName));
        if(pw.checkError())
        {
                System.err.println("Error while writing");
        }

        pw.println(txt);

        if(pw.checkError())
        {
                System.err.println("Error while writing");
        }

        pw.close();
    }
}
