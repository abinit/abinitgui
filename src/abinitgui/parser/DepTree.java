/*
 AbinitGUI - Created in July 2009
 
 Copyright (c) 2009-2015 Flavio Miguel ABREU ARAUJO (flavio.abreuaraujo@uclouvain.be)
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

 For more information on the AbinitGUI Project, please see
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
