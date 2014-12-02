/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abinitgui.parser;

import abivars.AllInputVars;
import abivars.Variable;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

/**
 *
 * @author yannick
 */
public class AbinitInputMapping 
{
    private HashMap<Integer,AbinitDataset> allDatasets;
    private int ndtset;
    private int[] udtset;
    private boolean usedtsets;
    private boolean useudtset;
    private boolean usejdtset;
    private AllInputVars database;
    private AbinitDataset defaultDataset;
    private ArrayList<Integer> jdtsets;
    private DepTree tree;
    private ArrayList<String> listSpecs;
    
    public AbinitInputMapping()
    {
        allDatasets = new HashMap<>();
        jdtsets = new ArrayList<>();
        defaultDataset = null;
        
        listSpecs = new ArrayList<String>();
        listSpecs.add("AUTO_FROM_PSP");
    }
    
    public void clean()
    {
        allDatasets.clear();
    }
    
    public Object getSpecValue(String spec)
    {
        switch(spec)
        {
            case "AUTO_FROM_PSP":
                return 0;
            default:
                return null;
        }
    }
    
    public AbinitDataset getDataset(int idtset)
    {
        return allDatasets.get(idtset);
    }
    
    public void setDataset(int idtset, AbinitDataset dataset)
    {
        allDatasets.put(idtset, dataset);
    }
    
    /**
     * Return the variable associated to name in the current dataset idtset
     * If the variable "name" is not associated in the current dataset, goes 
     * in the "0" dataset
     * @param name Name of the variable
     * @param dtset Current dataset
     * @return The variable
     */
    public Object valueFromDataset(AbinitDataset dtset, String name,int idtset)
    {
        boolean printDebug = false;
        //if(name.equals("natrd") || name.equals("xred") || name.equals("natom"))
        //{
        //    printDebug = true;
        //}
        Evaluator eval = new AbinitEvaluator();
        if(dtset == null)
        {
            return null;
        }
        AbinitVariable var = dtset.getVariable(name);
        if(var == null)
        {
            return null;
        }
        Object out = var.getValue();
        if(out == null)
        {
            if(printDebug)
              System.out.println("Nothing in value of "+name);
            DepNode node = this.tree.getVar(name);
            if(node != null)
            {
                ArrayList<DepNode> listDeps = node.listDeps;
                for(DepNode dep : listDeps)
                {
                    if(printDebug)
                        System.out.println(" ===== Deps of "+name+" ==== ");
                    DepNode otherNode = dep;
                    if(printDebug)
                      System.out.println("Try to look in dep : "+otherNode.name);
                    Object otherObj = getVariableValue(otherNode.name,idtset);
                    if(otherObj == null)
                    {
                        System.err.println("!!!!!!! null from getVariableValue: "+otherNode.name+" !!!!");
                        return null;
                    }
                    else
                    {
                        eval.putVariable(otherNode.name, otherObj.toString());
                    }
                }
            }
            if(printDebug)
              System.out.println(name+" has no dependencies anymore !");
            try {
                var.evaluateDim(eval);
                var.evaluateValue(eval);
                //System.out.println("evaluated !");
            } catch (EvaluationException ex) {
                Logger.getLogger(AbinitInputMapping.class.getName()).log(Level.SEVERE, null, ex);
            }
            out = var.getValue();
            if(printDebug)
              System.out.println("out value = "+out);
        }
        //System.out.println("The value is now : "+out);
        return out;
    }
    
    /**
     * Return the variable value associated to name in the current dataset idtset
     * If the variable "name" is not associated in the current dataset, goes 
     * in the "0" dataset
     * @param name Name of the variable
     * @param idtset Current dataset
     * @return The variable
     */
    public Object getVariableValue(String name, int idtset)
    {
        if(listSpecs.contains(name))
        {
            return getSpecValue(name);
        }
        Object obj = valueFromDataset(allDatasets.get(idtset),name,idtset);
        if(obj == null)
        {
            obj = valueFromDataset(allDatasets.get(0),name,idtset);
            if(obj == null)
            {
                obj = valueFromDataset(defaultDataset,name,idtset);
            }
        }
        return obj;
    }
    
    /**
     * Return the variable associated to name in the current dataset idtset
     * If the variable "name" is not associated in the current dataset, goes 
     * in the "0" dataset
     * @param name Name of the variable
     * @param idtset Current dataset
     * @return The variable
     */
    public AbinitVariable getVariable(String name, int idtset)
    {
        AbinitVariable var = getDataset(idtset).getVariable(name);
        if(var == null)
        {
            if(allDatasets.keySet().contains(0))
            {
                var = getDataset(0).getVariable(name);
                if(var == null)
                {
                    return defaultDataset.getVariable(name);
                }
            }
            else
            {
                return defaultDataset.getVariable(name);
            }
        }
        return var;
    }

    public ArrayList<Integer> getJdtsets() {
       return jdtsets;
    }
    
    public void addJdtset(int jdtset)
    {
        jdtsets.add(jdtset);
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

    /**
     * @param ndtset the ndtset to set
     */
    public void setNdtset(int ndtset) {
        this.ndtset = ndtset;
    }

    /**
     * @param udtset the udtset to set
     */
    public void setUdtset(int[] udtset) {
        this.udtset = udtset;
    }

    /**
     * @param usedtsets the usedtsets to set
     */
    public void setUsedtsets(boolean usedtsets) {
        this.usedtsets = usedtsets;
    }

    /**
     * @param useudtset the useudtset to set
     */
    public void setUseudtset(boolean useudtset) {
        this.useudtset = useudtset;
    }

    /**
     * @param usejdtset the usejdtset to set
     */
    public void setUsejdtset(boolean usejdtset) {
        this.usejdtset = usejdtset;
    }

    /**
     * @return the database
     */
    public AllInputVars getDatabase() {
        return database;
    }

    /**
     * @param database the database to set
     */
    public void setDatabase(AllInputVars database) {
        this.database = database;
        this.buildDefaultValues();
        this.buildDepTree();
    }
    
    public String createExprForJEval(Object value)
    {
        if(value instanceof String)
        {
            String s = (String)value;
            s = s.replaceAll("\\[\\[","#{").replaceAll("\\]\\]","}");
            return s;
        }
        
        return "0";
    }
    
    public void buildDepTree()
    {
        this.tree = new DepTree();
        for(String varname : database.getListKeys())
        {
            AbinitVariable abivar = defaultDataset.getVariable(varname);
            
            ArrayList<String> listDeps = abivar.lookForDeps();
            
            this.tree.addDep(varname, listDeps);
        }
        try{
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("tree.txt")));
            pw.println(this.tree);
            pw.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        //System.out.println(this.tree);
    }
    
    public void buildDefaultValues()
    {
        defaultDataset = new AbinitDataset();
        for(String varname : database.getListKeys())
        {
            Variable var = database.getVar(varname);
            AbinitVariable abivar = new AbinitVariable();
            abivar.setDocVariable(var);
            abivar.setInputValue(null);
            defaultDataset.setVariable(varname, abivar);
        }
    }
    
    public void evaluateAll() throws EvaluationException
    {
        Evaluator evaluator = new Evaluator();
        
        /**if(isUsejdtset())
        {
            //allDatasets.get(0).evaluateAll(evaluator);
            // Then I should set all the variables in evaluator with their values
            for(int idtset : getJdtsets())
            {
                System.out.println("   -------------------------------   "); 
                System.out.println("Jdtset = "+idtset);
                defaultDataset.evaluateAll(evaluator);
                allDatasets.get(idtset).evaluateAll(evaluator);
            }
        }
        else
        {
            System.out.println("   -------------------------------   "); 
            System.out.println("Jdtset = "+0);
            defaultDataset.evaluateAll(evaluator);
            allDatasets.get(0).evaluateAll(evaluator);
        }**/
    }
}
