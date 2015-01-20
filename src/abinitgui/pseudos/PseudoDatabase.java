/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abinitgui.pseudos;

import abinitgui.scriptbib.Script;
import abinitgui.scriptbib.ScriptConstructor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Yannick
 */
public class PseudoDatabase {
    
    private Object data;
    private HashMap<String,HashMap<String,ArrayList<AbinitPseudo>>> database;
    
    public void from_url(String url)
    {
        try{
            Yaml yaml = new Yaml(new PseudoConstructor());
            this.data = yaml.load(new BufferedReader(new InputStreamReader(new URL(url).openStream())));
            database = (HashMap<String,HashMap<String,ArrayList<AbinitPseudo>>>)(this.data);
        } catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public ArrayList<AbinitPseudo> getListPseudos(String pspType, String atom)
    {
        HashMap<String,ArrayList<AbinitPseudo>> dataPerType = database.get(pspType);
        if(dataPerType != null)
        {
            return dataPerType.get(atom);
        }
        else
        {
            System.out.println("No data for type : "+pspType);
            System.out.println("Types in database : "+database.keySet());
            return null;
        }
    }
    
}
