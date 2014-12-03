/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
