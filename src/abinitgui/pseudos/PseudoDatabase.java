/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abinitgui.pseudos;

import abinitgui.core.MainFrame;
import abinitgui.scriptbib.Script;
import abinitgui.scriptbib.ScriptConstructor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import jsftp.misc.Utils;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Yannick
 */
public class PseudoDatabase {
    
    private HashMap<String,HashMap<String,ArrayList<AbinitPseudo>>> database;
    
    public void fromUrl(String url) throws MalformedURLException, IOException
    {
        Yaml yaml = new Yaml(new PseudoConstructor());
        Object data = yaml.load(new BufferedReader(new InputStreamReader(new URL(url).openStream())));
        database = (HashMap<String,HashMap<String,ArrayList<AbinitPseudo>>>)(data);
    }
    
    public void fromFile(String filename)
    {
        try{
            Yaml yaml = new Yaml(new PseudoConstructor());
            if(Utils.exists(filename))
            {
                Object data = yaml.load(new BufferedReader(new FileReader(filename)));
                database = (HashMap<String,HashMap<String,ArrayList<AbinitPseudo>>>)(data);
            }
            else
            {
                MainFrame.printERR(filename+" does not exist, create an empty one");
                database = new HashMap<>();
                toFile(filename);
            }
        } catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void toFile(String filename)
    {
        try{
            Yaml yaml = new Yaml(new PseudoRepresenter());
            String s = yaml.dump(this.database);
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
            if(pw.checkError())
            {
                throw new IOException("PW");
            }
            pw.println(s);
            if(pw.checkError())
            {
                throw new IOException("PW");
            }
            pw.close();
            
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

    public ArrayList<AbinitPseudo> getOrCreateListPseudos(String pspType, String atom) {
        HashMap<String,ArrayList<AbinitPseudo>> dataPerType = database.get(pspType);
        ArrayList<AbinitPseudo> listPseudos = null;
        if(dataPerType != null)
        {
            listPseudos = dataPerType.get(atom);
            if(listPseudos == null)
            {
                listPseudos = new ArrayList<>();
                dataPerType.put(atom,listPseudos);
            }
        }
        else
        {
            dataPerType = new HashMap<>();
            listPseudos = new ArrayList<>();
            dataPerType.put(atom, listPseudos);
            database.put(pspType, dataPerType);
        }
        
        return listPseudos;
    }
    
}
