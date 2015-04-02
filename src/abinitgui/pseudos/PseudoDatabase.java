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
