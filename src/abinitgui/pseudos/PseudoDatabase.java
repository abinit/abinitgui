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
