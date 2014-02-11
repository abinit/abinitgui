/*
 Copyright (c) 2009-2014 Flavio Miguel ABREU ARAUJO (flavio.abreuaraujo@uclouvain.be)
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
 */

package abinitgui.tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import abinitgui.parser.AbinitInput;

public class TestParser {
    
    public static void testAllInputsInPath(String pathAbinit, String outputFile)
    {
        AbinitInput ai = new AbinitInput();
        
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
        try {
            //pw.print("Reading file : "+name+" ...");

            new AbinitInput().readFromFile(name);
            //pw.println("OK !");
        } catch (Exception ex) {
            ex.printStackTrace();
            pw.println("Parsing file : "+name+" : Failed (Msg = "+ex.getMessage()+")");
            //Logger.getLogger(TestParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public static void main(String[] args)
    {
//        testAllInputsInPath("/home/yannick/abinit/7.5.4-private","test-all.txt");
        
        testOneInputFile("/home/yannick/abinit/7.5.4-private/tests/v3/Input/t76.in","test-v3-76.txt");
    }
    
}
