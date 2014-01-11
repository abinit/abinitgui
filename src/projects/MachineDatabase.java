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
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author yannick
 */
public class MachineDatabase implements Iterable<Machine>
{
    private HashMap<String,Machine> list;
    
    public MachineDatabase()
    {
        list = new HashMap<>();
    }
    
    public void addMachine(Machine rm)
    {
        list.put(rm.getName(),rm);
    }
    
    public void removeMachine(Machine rm)
    {
        this.removeMachineWithName(rm.getName());
    }
    
    public Machine getMachine(String name)
    {
        return list.get(name);
    }
    
    public void removeMachineWithName(String name)
    {
        list.remove(name);
    }
    
    public void setAllNames()
    {
        for(Map.Entry<String,Machine> entry : this.list.entrySet())
        {
            String key = entry.getKey();
            Machine mach = entry.getValue();
            String name = mach.getName();
            System.out.println("Key = "+key+", machine = "+name);
            if(mach == null)
            {
                
            }
            else if(!entry.getKey().equals(entry.getValue().getName()))
            {
                list.remove(entry.getKey());
                list.put(entry.getValue().getName(), entry.getValue());
            }
        }
    }
    
    public HashMap<String,Machine> getMachineList()
    {
        return list;
    }
    
    public Iterator<Machine> iterator()
    {
        return list.values().iterator();
    }
    
    public void loadFromFile(String fileName) throws IOException
    {
        Yaml yaml = new Yaml(new ProjectConstructor());
        Object data = yaml.load(new FileReader(fileName));
        this.list = (HashMap<String,Machine>)data;
    }
    
    public void saveToFile(String fileName) throws IOException
    {
        setAllNames();
        
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
