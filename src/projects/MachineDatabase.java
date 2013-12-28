/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projects;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author yannick
 */
public class MachineDatabase 
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
    
    public boolean removeMachine(Machine rm)
    {
        return list.remove(rm);
    }
    
    public ArrayList<Machine> getMachineList()
    {
        return list;
    }
    
    public Iterator<Machine> iterator()
    {
        return list.iterator();
    }
}
