/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abinitgui.parser;

import abivars.MultipleValue;
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
    
    public String toString()
    {
        return docVariable.getVarname()+" = "+value+" (from "+inputValue+")";
    }

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
    
    public Object getDim(Object docDim, Evaluator evaluator, boolean isDim) throws EvaluationException
    {
        String type = docVariable.getVartype();
        if(docDim instanceof String)
        {
            String dimS = (String)docDim;
            dimS = dimS.replaceAll("\\[\\[","#{").replaceAll("\\]\\]","}");
            return (int)evaluator.getNumberResult(dimS);
        }
        else if(docDim instanceof Number)
        {
            if(isDim)
            {
                return ((Number)docDim).intValue();
            }
            else
            {
                if(type.contains("real"))
                {
                    return ((Number)docDim).doubleValue();
                }
                else if(type.contains("integer"))
                {
                    return ((Number)docDim).intValue();
                }
            }
        }
        else if(docDim instanceof ArrayList)
        {
            ArrayList<Object> list = new ArrayList<>();
            ArrayList<Object> dimL = (ArrayList<Object>)docDim;
            for(Object obj : dimL)
            {
                list.add(getDim(obj,evaluator, isDim));
            }
            return list;
        }
        else if(docDim instanceof MultipleValue)
        {
            MultipleValue mv = (MultipleValue)docDim;
            Object obj = mv.getNumber();
            Object obj2 = mv.getValue();
            if(obj != null)
            {
                Object out = getDim(obj, evaluator, isDim);
                Object out2 = getDim(obj2, evaluator, isDim);
                System.out.println("number = "+out);
                System.out.println("value = "+out2);
                if(out instanceof Number && out2 instanceof Number)
                {
                    ArrayList<Number> list = new ArrayList<>();
                    int nb = ((Number)out).intValue();

                    while(nb-- > 0)
                    {
                       list.add((Number)out2);
                    }
                    return list;
                }
                else
                {
                    System.err.println("Not yet treated : "+docDim);
                }
            }
            else
            {
                return mv;
            }
        }
        else
        {
            System.err.println("Not yet treated : "+docDim);
        }
        
        return null;
    }
    
    public void evaluateDim(Evaluator evaluator) throws EvaluationException 
    {
        if(getDocVariable().getVarname().equals("natom"))
        {
            System.out.println("dim for natom ");
            System.out.println(dims);
            System.out.println(value);
            System.out.println(inputValue);
        }
        Object docDim = docVariable.getDimensions();
        if(docDim instanceof String && ((String)docDim).equals("scalar"))
        {
            this.setDims(new ArrayList<Integer>());
        }
        else
        {
            Object o = getDim(docDim, evaluator, true);
            if(o instanceof ArrayList)
            {
                this.setDims((ArrayList<Integer>)o);
            }
            else if(o instanceof Integer)
            {
                ArrayList<Integer> obj = new ArrayList<>();
                obj.add((Integer)o);
                this.setDims(obj);
            }
            else 
            {
                System.err.println(docVariable.getVarname()+" Not yet treated, getDim returned : "+o);
            }
        }
        
        /*System.out.println("=== "+docVariable.getDimensions()+" ===");
        System.out.println("dims = "+this.getDims());*/
    }
    
    public void evaluateValue(Evaluator evaluator) throws EvaluationException
    {
        if(getDocVariable().getVarname().equals("natom"))
        {
            System.out.println("value for natom ");
            System.out.println(dims);
            System.out.println(value);
            System.out.println(inputValue);
        }
        Object data = getInputValue();
        ArrayList<Object> listValues = new ArrayList<Object>();
        
        if(data == null)
        {
            data = docVariable.getDefaultval();
        }
        
        Object myVal = getDim(data, evaluator, false);
        ArrayList<Integer> getAlldims = getDims();
        int nbTotDims = 1;
        if(getAlldims == null)
        {
            return;
        }
        if(getAlldims.size() > 0)
        {
            for(int i = 0; i < getAlldims.size(); i++)
            {
                nbTotDims *= getAlldims.get(i);
            }
        }
        
        String type = docVariable.getVartype();
        
        if(myVal instanceof Number)
        {
            listValues.add(myVal);
        }
        else if(myVal instanceof Set)
        {
            listValues.addAll((Set)myVal);
        }
        else if(myVal instanceof MultipleValue)
        {
            Object out = getDim(((MultipleValue)myVal).getValue(),evaluator,false);
            for(int i = 0; i < nbTotDims; i++)
            {
                listValues.add(out);
            }
        }
        
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
                try{
                    this.value = listValues.toArray(new Double[0]);
                }
                catch(ArrayStoreException exc)
                {
                    System.out.println("ArrayStoreException e");
                    System.out.println(docVariable.getVarname());
                    System.out.println("listValues = "+listValues);
                }
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
