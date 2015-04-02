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
package abinitgui.parser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author yannick
 */
public class DepTree {
    
    public DepNode root = null;
    public HashMap<String,DepNode> hashMap;
    
    public DepTree()
    {
        root = new DepNode("root");
        hashMap = new HashMap<>();
    }
    
    public void addDep(String variable, ArrayList<String> deps)
    {
        for(String other : deps)
        {
            addDep(variable,other);
        }
    }
    
    public DepNode getVar(String variable)
    {
        return hashMap.get(variable);
    }
    
    public void addDep(String variable, String other)
    {
        DepNode varNode = hashMap.get(variable);
        DepNode otherNode = hashMap.get(other);
        if(varNode == null)
        {
            varNode = new DepNode(variable);
            hashMap.put(variable,varNode);
            root.listDeps.add(varNode);
        }
        if(otherNode == null)
        {
            otherNode = new DepNode(other);
            hashMap.put(other,otherNode);
            root.listDeps.add(otherNode);
        }
        if(!varNode.listDeps.contains(otherNode))
        {
            if(root.listDeps.contains(otherNode))
            {
                root.listDeps.remove(otherNode);
            }
            varNode.listDeps.add(otherNode);
        }
    }
    
    public String toString()
    {
        return printNode(root,1);
    }
    
    public String printNode(DepNode node, int level)
    {
        if(node == null)
        {
            return null;
        }
        
        String prepend = "";
        for(int i = 0; i < level; i++)
        {
            prepend += "-";
        }
        String s = node.name+"\n";
        for(DepNode other : node.listDeps)
        {
            s = s + prepend+printNode(other,level+1);
        }
        return s;
    }

    public String getGraphViz() {
        String s = "digraph {\n";
        for(DepNode node : hashMap.values())
        {
            for(DepNode other : node.listDeps)
            {
                s = s + "   "+node.name+"->"+other.name+"\n";
            }
        }
        return s+"}\n";
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
