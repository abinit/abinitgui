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

package abinitgui.tests;

import static abinitgui.core.MainFrame.printERR;
import abinitgui.parser.AbinitDataset;
import abinitgui.parser.AbinitGeometry;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import abinitgui.parser.AbinitInputJEval;
import abinitgui.parser.AbinitInputMapping;
import abinitgui.parser.AbinitVariable;
import abivars.AllInputVars;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.yaml.snakeyaml.error.YAMLException;

public class TestParser {
    
    public static AllInputVars getAllInputVars()
    {
        AllInputVars allInputVars = new AllInputVars();
        try{
            printERR("No abinit_vars.yml found, falling back to internal version");
            File fileOpened = new File(AllInputVars.class.getClassLoader().getResource("/abinitgui/resources/abinit_vars.yml").toURI());
            BufferedReader br = new BufferedReader(new FileReader(fileOpened));
            allInputVars.loadVars(br);
            return allInputVars;
        } catch(YAMLException | FileNotFoundException | URISyntaxException ex2)
        {
            printERR("Internal error, abinit_vars.yml not available in the jar file !!!");
            printERR("BUG !!!");
        }
        
        return null;
    }
    
    
    public static void testAllInputsInPath(String pathAbinit, String outputFile)
    {
        AbinitInputJEval ai = new AbinitInputJEval(getAllInputVars());
        
        String path = pathAbinit+"/tests/";
        
        File pFile = new File(path);
        
        try{
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
        
        
        if(pFile.isDirectory())
        {
            for(File child : pFile.listFiles())
            {
                if(child.isDirectory())
                {
                    String pathChild = child.getAbsolutePath();
                    
                    //System.out.println("pathChild = "+pathChild);
//                    if(!pathChild.contains("fast"))
//                    {
//                        continue;
//                    }
                    
                    File pathInput = new File(pathChild+"/Input/");
                    
                    if(pathInput.exists())
                    {
                    
                        for(File childInput : pathInput.listFiles())
                        {
                            String name = childInput.getAbsolutePath();

                            if(checkIfAbinitInput(name))
                            {
                                System.out.println("Testing : "+name);
                                testOneFile(name, pw);
                            }
                            else
                            {
                                System.out.println("Not testing : "+name);
                            }
                        }
                    }
                }
            }
        }
        
        pw.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void testOneInputFile(String inputFile, String outputFile)
    {
        PrintWriter pw;
        try{
            pw = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
            testOneFile(inputFile, pw);
            pw.close();
        }
        catch(Exception e)
        {
        }
    }
    
    public static boolean checkIfAbinitInput(String name)
    {
        boolean test = false;
        if(!name.endsWith(".in") || name.endsWith("report.in"))
        {
            return false;
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(name));
            String line;
            while((line = br.readLine())!=null)
            {
                if(line.contains("executable") && line.contains("abinit"))
                {
                    test = true;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(TestParser.class.getName()).log(Level.SEVERE, null, ex);
            test = false;
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(TestParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return test;
    }
    
    public static void testOneFile(String name, PrintWriter pw)
    {
        boolean isExc = false;
        AbinitInputJEval ai = new AbinitInputJEval(getAllInputVars());
        AbinitInputMapping mapping = null;
        try {
            //pw.print("Reading file : "+name+" ...");

            mapping = ai.readFromFile(name);
            mapping.evaluateAll();
            AbinitDataset values;
            ArrayList<Integer> listJdtset = mapping.getJdtsets();
            if(mapping.isUsejdtset())
            {
            }
            else
            {
                listJdtset = new ArrayList<>();
                listJdtset.add(0);
            }
            
            for(int jdtset : listJdtset)
            {
                values = mapping.getDataset(jdtset);

                Iterator<AbinitVariable> iter = values.iterator();
                
                ArrayList<HashMap<String,Object>> dataTable = new ArrayList<>();

                while(iter.hasNext())
                {
                    AbinitVariable o = iter.next();

                    Object value = mapping.getVariableValue(o.getDocVariable().getVarname(), jdtset, true, true);

                    HashMap<String,Object> map = new HashMap<>();

                    map.put("name", o.getDocVariable().getVarname());
                    map.put("value", value);

                    dataTable.add(map);
                }
                
                System.out.println(dataTable);
            
            }

            
            /*if(jdtset == null)
            {
                return;
            }*/ // TODO: ? voir plus haut !!
            //pw.println("OK !");
        } catch (Exception ex) {
            System.err.println("Parsing file : "+name+" : Failed (Msg = "+ex.getMessage()+")");
            isExc =true;
            ex.printStackTrace();
            pw.println("Parsing file : "+name+" : Failed (Msg = "+ex.getMessage()+")");
            //Logger.getLogger(TestParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(isExc)
            return;
        
        try{
        
            /*HashMap<String,Object> values;
            if(ai.isUsejdtset())
            {
                values = ai.getAllDatasets().get(ai.getJdtsets().get(0));
            }
            else
            {
                values = ai.getAllDatasets().get(0);
            }*/
            
            /*if(values == null)
            {
                throw new Exception("GetAllDatasets returned null");
            }*/
            
            AbinitGeometry geom = new AbinitGeometry();
            geom.loadData(mapping,0);
            
        } catch(Exception exc) {
            System.err.println("Building geometry from file : "+name+" : Failed (Msg = "+exc.getMessage()+")");
            exc.printStackTrace();
            pw.println("Building geometry from file : "+name+" : Failed (Msg = "+exc.getMessage()+")");
            //Logger.getLogger(TestParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public static void main(String[] args)
    {
        testAllInputsInPath("/home/yannick/abinit/7.11.0-training","test-all.txt");
        
        //testOneInputFile("/home/yannick/abinit/7.11.0-private/tests/v1/Input/t86.in","test-v1-86.txt");
    }
    
}
