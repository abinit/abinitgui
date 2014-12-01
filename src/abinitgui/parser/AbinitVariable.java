/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abinitgui.parser;

import abivars.Variable;
import java.util.ArrayList;
import java.util.Set;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

/**
 *
 * @author yannick
 */
public class AbinitVariable {
    private Object value;
    private ArrayList<Object> inputValue;
    private Variable docVariable;
    private ArrayList<Integer> dims;

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }
    
    public Object getDim(Object docDim, Evaluator evaluator) throws EvaluationException
    {
        if(docDim instanceof String)
        {
            String dimS = (String)docDim;
            dimS = dimS.replaceAll("\\[\\[","#{").replaceAll("\\]\\]","}");
            return evaluator.getNumberResult(dimS);
        }
        else if(docDim instanceof Number)
        {
            return docDim;
        }
        else if(docDim instanceof ArrayList)
        {
            ArrayList<Object> list = new ArrayList<>();
            ArrayList<Object> dimL = (ArrayList<Object>)docDim;
            for(Object obj : dimL)
            {
                list.add(getDim(obj,evaluator));
            }
            return list;
        }
        else
        {
            System.err.println("Not yet treated : "+docDim);
        }
        
        return null;
    }
    
    public void evaluateDim(Evaluator evaluator) throws EvaluationException 
    {
        Object docDim = docVariable.getDimensions();
        if(docDim instanceof String && ((String)docDim).equals("scalar"))
        {
            this.setDims(new ArrayList<Integer>());
        }
        else
        {
            Object o = getDim(docDim, evaluator);
            if(o instanceof ArrayList)
            {
                this.setDims((ArrayList<Integer>)o);
            }
            else if(o instanceof Integer)
            {
                ArrayList<Integer> obj = new ArrayList<>();
                obj.add((Integer)o);
            }
            else 
            {
                System.err.println("Not yet treated, getDim returned : "+o);
            }
        }
        
        /*System.out.println("=== "+docVariable.getDimensions()+" ===");
        System.out.println("dims = "+this.getDims());*/
    }
    
    public void evaluateValue(Evaluator evaluator) throws EvaluationException
    {
        Object data = getInputValue();
        ArrayList<Object> listValues = new ArrayList<Object>();
        
        if(data == null)
        {
            data = docVariable.getDefaultval();
        }
        
        Object myVal = getDim(data, evaluator);
        if(myVal instanceof Number)
        {
            listValues.add(myVal);
        }
        else if(myVal instanceof Set)
        {
            listValues.addAll((Set)myVal);
        }
        
        String type = docVariable.getVartype();
        // We don't know the size of table
        if(listValues.size() == 1)
        {
            if(type.contains("integer"))
            {
                this.value = (int)(listValues.get(0));
            }
            else if(type.contains("real"))
            {
                this.value =  (listValues.get(0));
            }
        }
        else
        {
            if(type.contains("integer"))
            {
                this.value =  listValues.toArray(new Integer[0]);
            }
            else if(type.contains("real"))
            {
                this.value = listValues.toArray(new Double[0]);
            }
        }
    }

    /**
     * @return the inputValue
     */
    public ArrayList<Object> getInputValue() {
        return inputValue;
    }

    /**
     * @param inputValue the inputValue to set
     */
    public void setInputValue(ArrayList<Object> inputValue) {
        this.inputValue = inputValue;
    }

    /**
     * @return the docVariable
     */
    public Variable getDocVariable() {
        return docVariable;
    }

    /**
     * @param docVariable the docVariable to set
     */
    public void setDocVariable(Variable docVariable) {
        this.docVariable = docVariable;
    }

    /**
     * @return the dims
     */
    public ArrayList<Integer> getDims() {
        return dims;
    }

    /**
     * @param dims the dims to set
     */
    public void setDims(ArrayList<Integer> dims) {
        this.dims = dims;
    }
}
