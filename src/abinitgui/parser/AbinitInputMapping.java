/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abinitgui.parser;

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
    
    public AbinitInputMapping()
    {
        allDatasets = new HashMap<>();
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
        AbinitVariable var = getDataset(idtset).getVariable(name);
        if(var == null)
        {
            return getDataset(0).getVariable(name);
        }
        return var;
    }

    public ArrayList<Integer> getJdtsets() {
       return new ArrayList<>(allDatasets.keySet());
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
    
    public void evaluateAll() throws EvaluationException
    {
        Evaluator evaluator = new Evaluator();
        allDatasets.get(0).evaluateAll(evaluator);
        // Then I should set all the variables in evaluator with their values
        for(int idtset : getJdtsets())
        {
            allDatasets.get(idtset).evaluateAll(evaluator);
        }
    }
}
