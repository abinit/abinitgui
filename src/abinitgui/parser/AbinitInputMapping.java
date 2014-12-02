/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abinitgui.parser;

import abivars.AllInputVars;
import abivars.Variable;
import java.util.ArrayList;
import java.util.HashMap;
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
    
    public AbinitInputMapping()
    {
        allDatasets = new HashMap<>();
        jdtsets = new ArrayList<>();
        defaultDataset = null;
    }
    
    public void clean()
    {
        allDatasets.clear();
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
     * @param idtset Current dataset
     * @return The variable
     */
    public AbinitVariable getVariable(String name, int idtset)
    {
        if(name.equals("natom"))
        {
            System.out.println(getDataset(idtset).getVariable(name));
            System.out.println(getDataset(0).getVariable(name));
            System.out.println(defaultDataset.getVariable(name));
        }
        AbinitVariable var = getDataset(idtset).getVariable(name);
        if(var == null)
        {
            return getDataset(0).getVariable(name);
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
        
        // First create a fake dataset with default values ! 
        // We will look in this dataset in case the value is not defined in the
        // input file
        buildDefaultValues();
        
        defaultDataset.evaluateAll(evaluator);
        
        System.out.println("   -------------------------------   "); 
        
        System.out.println("allDatasets = "+allDatasets);
        //allDatasets.get(0).evaluateAll(evaluator);
        // Then I should set all the variables in evaluator with their values
        for(int idtset : getJdtsets())
        {
            allDatasets.get(idtset).evaluateAll(evaluator);
        }
    }
}
