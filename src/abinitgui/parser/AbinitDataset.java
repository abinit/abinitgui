/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abinitgui.parser;

import java.util.HashMap;
import java.util.Iterator;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

/**
 *
 * @author yannick
 */
public class AbinitDataset implements Iterable<AbinitVariable>
{
    private HashMap<String,AbinitVariable> allData;
    
    public AbinitDataset()
    {
        allData = new HashMap<>();
    }
    
    public AbinitVariable getVariable(String name)
    {
        return allData.get(name);
    }
    
    public void setVariable(String name, AbinitVariable variable)
    {
        allData.put(name, variable);
    }
    
    public Iterator<AbinitVariable> iterator()
    {
        return allData.values().iterator();
    }
    
    public void evaluateAll(Evaluator evaluator) throws EvaluationException
    {
        for(AbinitVariable var : this)
        {
            var.evaluateValue(evaluator);
        }
    }
}
