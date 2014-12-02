/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abinitgui.parser;

import java.util.ArrayList;

/**
 *
 * @author yannick
 */
public class DepNode {
    public String name = null;
    public ArrayList<DepNode> listDeps;
    
    public DepNode(String name)
    {
        this.name = name;
        listDeps = new ArrayList<>();
    }
    
    
}
