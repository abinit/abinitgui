package parser;

import abinitgui.AllInputVars;
import abinitgui.InputVar;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yannick
 */
public class AbinitInput 
{
    private String content = null;
    private double Ha_to_eV = 27.21138386;
    private double ev_to_Ha = 1.0/Ha_to_eV;
    private double kb_to_HaK = 8.617343E-5/Ha_to_eV;
    private double tesla_to_au = 4.254383E-6*0.5;
    private double bohr_to_angstrom=0.52917720859;
    private double angstrom_to_bohr=1.0/bohr_to_angstrom;
    private AllInputVars allInputs = null;
    private HashMap<String,ArrayList> map;
    private HashMap<String,String> mapString;
    private HashMap<String,Double> listOfUnits = null;
    private final String[] units = {"HA","HARTREE","RY","RYDBERG","BOHR","AU", "T","TE", "EV", "ANGSTROM" };
    private final double[] scaling = {1.0,1.0,0.5,0.5,1.0,1.0,tesla_to_au,tesla_to_au,ev_to_Ha,angstrom_to_bohr};
    
    private HashMap<String,HashMap<String,Object>> allDatasets;
    private int ndtset = 0;
    private int udtset[] = new int[]{0,0};
    private ArrayList<String> jdtsets;
    private boolean usedtsets = false;
    private boolean useudtset = false;
    private boolean usejdtset = false;
    
    public AbinitInput()
    {
        allInputs = new AllInputVars(null);
        
        listOfUnits = new HashMap<>();
        
        for(int i = 0; i < units.length; i++)
        {
            listOfUnits.put(units[i],scaling[i]);
        }
    }
    public void readFromFile(String fileName) throws IOException
    {
        content = fileToString(fileName);
        content = content.trim(); // Remove spaces at the beginning and at the end
        content = content.replaceAll(" +"," "); // Replace multiple spaces by 1 space
        
        mapString = tabularize(content, allInputs);
        
        buildDtset(mapString);
    }
    
    @Override
    public String toString()
    {
        return "Abinit Input : "+mapString;
    }
    
    public static void main(String[] args)
    {
        String path = "/home/yannick/abinit/7.3.2-private/tests/";
        
        String[] filesToTest = {"v67mbpt/Input/t22.in","fast/Input/t01.in","v6/Input/t66.in"};
    
        for(String file : filesToTest)
        {
            System.out.println("Parsing file : "+file);
            AbinitInput input = new AbinitInput();
        
            try{
                input.readFromFile(path+file);

            } catch(IOException e)
            {
                e.printStackTrace();
            }
            System.out.println(input);
        }
        

    }
    
    private HashMap<String, String> tabularize(String content, AllInputVars allInputs)
    {
        HashMap<String,String> newMap = new HashMap<>();
        
        String[] splitted = content.split(" ");
        
        String keyword = null;
        StringBuilder sb = new StringBuilder();
        
        for(int i = 0; i < splitted.length; i++)
        {
            String word = splitted[i].toLowerCase();
            
//            System.out.print("Looking for word "+word+"...");
            
            if(isVariable(word))
            {
//                System.out.println("var !");
                if(keyword != null)
                {
                    newMap.put(keyword,sb.toString().trim());
                    sb = new StringBuilder();
                }
                keyword = word;
            }
            else
            {
                sb.append(word+" ");
            }
            
        }
        
        
        return newMap;
    }

    private HashMap<String, ArrayList> tabularizeContent(String content) throws InvalidInputFileException 
    {
        HashMap<String,ArrayList> newmap = new HashMap<>();
        
        String[] splitted = content.split(" ");
        
        String keyword = null;
        ArrayList<Object> listValue = new ArrayList<>();
        
        for(int i = 0; i < splitted.length; i++)
        {
            String word = splitted[i].toUpperCase();
            
            if(isUnit(word))
            {
                double curScaling = listOfUnits.get(word);
                
                // Rescale !
                if(listValue.isEmpty())
                {
                    System.out.println("word = "+word+", keyword = "+keyword+", listValue = "+listValue);
                    throw new InvalidInputFileException("Unit "+word+" without values !");
                }
                
                for(int j = 0; j < listValue.size(); j++)
                {
                    listValue.set(j, curScaling*(Double)listValue.get(j));
                }
                
                newmap.put(keyword, listValue);
                continue;
            }
            else if(isNumber(word))
            {
                listValue.addAll(getAllNumber(word));
                continue;
            }
            else if((keyword != null && keyword.contains("file")))
            {
                listValue.add(word);
                continue;
            }
            else
            {
                if(keyword != null)
                {
                    newmap.put(keyword, listValue);
                }
                keyword = word;
                listValue = new ArrayList<>();
                continue;
            }
        }
        
        return newmap;
    }
    
    // http://stackoverflow.com/questions/7597485/how-to-check-if-a-string-is-a-number
    public boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("(^-?)(\\d+)(.?)(\\d*$)"); // \\'D'?\\-?\\d* [^/]
        Matcher matcher = pattern.matcher(str);
        boolean isSimpleNumber = matcher.matches();
        pattern = Pattern.compile("(^-?)(\\d+)(.?)(\\d*)[DEFdef](-?)(\\d+)(.?)(\\d*$)");
        matcher = pattern.matcher(str);
        boolean isExponential = matcher.matches();
        pattern = Pattern.compile("(^-?)(\\d+)(.?)(\\d*)/(-?)(\\d+)(.?)(\\d*$)");
        matcher = pattern.matcher(str);
        boolean isFraction = matcher.matches();
        return isSimpleNumber || isFraction || isExponential;
    }
    
    private boolean isUnit(String word)
    {
        return listOfUnits.keySet().contains(word);
    }
    
    private boolean isVariable(String word)
    {
        word = word.trim();
        Pattern pattern = Pattern.compile("(^[a-zA-Z_]+)(\\d*$)");
        Matcher matcher = pattern.matcher(word);
        boolean isText = matcher.matches();
        pattern = Pattern.compile("");
        matcher = pattern.matcher(word);
        boolean isTextWithPlus = word.endsWith("+");
        boolean isTextWithPoint = word.endsWith(":");
        String curWord = word.replaceAll("\\d", "").replaceAll("\\+","").replaceAll(":", "");
        boolean isVar = allInputs.getListKeys().contains(curWord);
        if(word.equals("ecut+"))
            System.out.println("word = "+word+" ; curWord = "+curWord+" "+isText+" "+isTextWithPoint+" "+isTextWithPlus);
        return (isText || isTextWithPoint || isTextWithPlus) && isVar ;
    }
    
    // Returns a string from a file where the next line are appended.
    // Remove comments
    public String fileToString(String fileName) throws FileNotFoundException, IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine()) != null)
        {
            // Remove comments from line (starting with # or !)
            if(line.contains("#"))
            {
                if(line.startsWith("#"))
                    continue;
                line = line.split("#")[0];
            }
            
            if(line.contains("!"))
            {
                if(line.startsWith("!"))
                    continue;
                line = line.split("!")[0];
            }
            
            sb.append(line);
            sb.append(" ");
        }
        
        return sb.toString();
    }

    private Collection<? extends Object> getAllNumber(String word) throws InvalidInputFileException 
    {
        ArrayList<Object> values = new ArrayList<>();
        word = word.replaceAll("D","E");
        
        if(word.contains("/"))
        {
            String[] splitted = word.split("/");
            Double d1 = Double.parseDouble(splitted[0]);
            Double d2 = Double.parseDouble(splitted[1]);
            values.add(d1/d2);
        }
        else if(word.contains("*"))
        {
            if(word.startsWith("*"))
            {
                throw new InvalidInputFileException("* starting value is not supported yet");
            }
            
            String[] splitted = word.split("\\*");
            
            Integer nb = Integer.parseInt(splitted[0]);
            Double d = Double.parseDouble(splitted[1]);
            
            while(nb-- > 0)
            {
                values.add(d);
            }
        }
        else
        {
            values.add(Double.parseDouble(word));
        }
        
        return values;
    }

    private void buildDtset(HashMap<String, String> mapString) throws InvalidInputFileException {
        
        // Find ndtset
        jdtsets = new ArrayList<>();
        
        String ndtset_S = mapString.get("ndtset");
        
        if(ndtset_S != null)
        {
            ndtset = Integer.parseInt(ndtset_S);
            usedtsets = true;
        }
        
        String udtset_S = mapString.get("udtset");
        
        if(udtset_S != null)
        {
            useudtset = true;
            String[] split = udtset_S.split(" ");
            if(split.length != 2)
            {
                throw new InvalidInputFileException("Udtset should contain exactly two int");
            }
            udtset[0] = Integer.parseInt(split[0]);
            udtset[1] = Integer.parseInt(split[1]);
            if(isUsedtsets())
            {
                if(getUdtset()[0]*getUdtset()[1] != getNdtset())
                {
                    throw new InvalidInputFileException("Ndtset should be the product of udtsets");
                }
            }
            usedtsets = true;
        }
        
        String jdtset_S = mapString.get("jdtset");
        
        if(jdtset_S == null)
        {
            if(isUseudtset())
            {
                for(int i = 1; i <= getUdtset()[0]; i++)
                {
                    for(int j = 1; j <= getUdtset()[1]; j++)
                    {
                        getJdtsets().add(""+i+""+j);
                    }
                }
                usejdtset = true;
            }
            else if(isUsedtsets())
            {
                for(int i = 1; i <= getNdtset(); i++)
                {
                    getJdtsets().add(""+i);
                }
                usejdtset = true;
            }
            else
            {
                usejdtset = false;
            }
            // Range from 1 to ndtset
        }
        else
        {
            if(isUseudtset())
            {
                throw new InvalidInputFileException("udtset and jdtset cannot be used at the same time !");
            }
            else
            {
                String[] split = jdtset_S.split(" ");
                for(String s : split)
                {
                    getJdtsets().add(s);
                }
                usejdtset = true;
            }
        }
        
        System.out.println("jdtsets = "+getJdtsets());
        
        allDatasets = new HashMap<>();
        
        if(isUsejdtset())
        {
            for(String idtset : getJdtsets())
            {
                HashMap<String,Object> values = readDataSet(mapString,idtset);
                
                getAllDatasets().put(idtset, values);
            }
        }
        else
        {
            HashMap<String,Object> values = readDataSet(mapString,"0");
            
            getAllDatasets().put("0", values);
        }
        
        System.out.println(getAllDatasets());
        
    }

    private HashMap<String, Object> readDataSet(HashMap<String, String> mapString, String idtset) throws InvalidInputFileException {
        
        HashMap<String,Object> curMap = new HashMap<>();
        
        Iterator<String> iter = allInputs.getListKeys().iterator();
        
        if(useudtset)
        {
            throw new UnsupportedOperationException("Udtset not yet supported by the parser ... Sorry for inconvenience");
        }
        
        while(iter.hasNext())
        {
            String name = iter.next();
            InputVar var = allInputs.get(name);
            String value = mapString.get(name);
            if(isUsejdtset())
            {
                // Look for : and + possibilities !
                String startVal = mapString.get(name+":");
                if(startVal != null)
                {
                    String incVal = mapString.get(name+"+");
                    if(incVal  == null)
                    {
                        throw new InvalidInputFileException("Check the increment for variable : "+name);
                    }
                    else
                    {
                        // Compute the value ...
                        value = "s = "+startVal+", i = "+incVal;
                    }
                }
                
                
                String curVal = mapString.get(name+idtset);
                if(curVal != null)
                {
                    // Parse the string to get the value
                    value = curVal;
                }
            }
            if(value != null)
            {
                curMap.put(name, value);
            }
        }
        
        return curMap;
    }

    /**
     * @return the allDatasets
     */
    public HashMap<String,HashMap<String,Object>> getAllDatasets() {
        return allDatasets;
    }

    /**
     * @return the ndtset
     */
    public int getNdtset() {
        return ndtset;
    }

    /**
     * @return the udtset
     */
    public int[] getUdtset() {
        return udtset;
    }

    /**
     * @return the jdtsets
     */
    public ArrayList<String> getJdtsets() {
        return jdtsets;
    }

    /**
     * @return the usedtsets
     */
    public boolean isUsedtsets() {
        return usedtsets;
    }

    /**
     * @return the useudtset
     */
    public boolean isUseudtset() {
        return useudtset;
    }

    /**
     * @return the usejdtset
     */
    public boolean isUsejdtset() {
        return usejdtset;
    }

    private static class InvalidInputFileException extends IOException {

        public InvalidInputFileException(String unit_without_values_) {
            super(unit_without_values_);
        }
    }

}
