/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abinitgui.parser;

import abinitgui.core.Utils;
import abivars.MultipleValue;
import abivars.Range;
import abivars.ValueWithConditions;
import abivars.ValueWithUnit;
import abivars.Variable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;
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
    private ArrayList<String> listDeps;
    
    public AbinitVariable()
    {
        listDeps = new ArrayList<String>();
    }
    
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
    
    public ArrayList<String> getVars(String dimS)
    {
        ArrayList<String> listDeps = new ArrayList<String>();
        String tmpS = dimS;
        int id = tmpS.indexOf("[[");
        while(id != -1)
        {
            tmpS = tmpS.substring(id);
            int end = tmpS.indexOf("]]");
            listDeps.add(tmpS.substring(2,end));
            tmpS = tmpS.substring(end);
            id = tmpS.indexOf("[[");
        }
        return listDeps;
    }
    
    public Object getDim(Object docDim, Evaluator evaluator, boolean isDim) throws EvaluationException
    {
        String type = docVariable.getVartype();
        if(docDim instanceof String)
        {
            String dimS = (String)docDim;
            listDeps.addAll(getVars(dimS));
            dimS = dimS.replaceAll("\\[\\[","#{").replaceAll("\\]\\]","}");
            dimS.replace("0.0d0", "0.0");
            try{
                Number n = evaluator.getNumberResult(dimS);
                //System.out.println("evaluator : "+dimS+", result = "+n);
                if(isDim)
                {
                    return n.intValue();
                }
                else
                {
                    if(type.contains("real"))
                    {
                        return n.doubleValue();
                    }
                    else if(type.contains("integer"))
                    {
                        return n.intValue();
                    }
                }
            }catch(EvaluationException exc)
            {
                System.out.println(evaluator.getVariables());
                System.err.println("Exception encountered for var : "+this.docVariable.getVarname()+", with expression : "+dimS);
                throw exc;
            }
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
                    System.err.println("Problem with Multiple value with non-numeric values : "+docDim);
                }
            }
            else
            {
                return mv;
            }
        }
        else if(docDim instanceof ValueWithConditions)
        {
            // Just fake reading
            return getDim(((ValueWithConditions)docDim).getValues().get("defaultval"), evaluator, isDim);
        }
        else if(docDim == null)
        {
            return null;
        }
        else if(docDim instanceof ValueWithUnit)
        {
            ValueWithUnit vwu = (ValueWithUnit)docDim;
            if(type.contains("integer"))
            {
                System.err.println("Units can only be provided for real numbers !");
                return null;
            }
            String units = vwu.getUnits();
            Double scalingFactor = AbinitInputJEval.listOfUnits.get(units.toUpperCase());
            if(scalingFactor == null)
            {
                System.err.println("Unit not recognized : "+units);
                return null;
            }
            Object o = getDim(vwu.getValue(),evaluator,isDim);
            if(o instanceof Number)
            {
                return scalingFactor * ((Number)o).doubleValue();
            }
            else
            {
                System.err.println("Units should be applied on top of Number value !");
                return null;
            }
        }
        else if(docDim instanceof Range)
        {
            Range ran = (Range)docDim;
            Object startVal = getDim(ran.getStart(),evaluator,isDim);
            Object stopVal = getDim(ran.getStart(),evaluator,isDim);
            if(startVal instanceof Number && stopVal instanceof Number)
            {
                int start = ((Number)startVal).intValue();
                int stop = ((Number)stopVal).intValue();
                ArrayList<Integer> values = new ArrayList<>();
                for(int i = start; i < stop+1; i++)
                {
                    values.add(i);
                }
                return values;
            }
            else
            {
                System.err.println("For a range to be treated, you need start and stop values to be integers :"+ran);
                System.err.println(startVal);
                System.err.println(stopVal);
                return null;
            }
        }
        else
        {
            System.err.println("Class Not yet treated : "+docDim.getClass());
        }
        
        return null;
    }
    
    public void evaluateDim(Evaluator evaluator) throws EvaluationException 
    {
        evaluateDim(evaluator,false);
    }
    
    public void evaluateDim(Evaluator evaluator, boolean strict) throws EvaluationException 
    {
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
    
    public ArrayList<Object> flattenMultiple(Object o, int nbTotDims, Evaluator evaluator) throws EvaluationException
    {
        ArrayList<Object> curList = new ArrayList<>();
        
        if(o instanceof Number)
        {
            curList.add(o);
        }
        else if(o instanceof Set)
        {
            curList.addAll((Set)o);
        }
        else if(o instanceof ArrayList)
        {
            ArrayList<Object> listO = (ArrayList)o;
            for(Object obj : listO)
            {
                curList.addAll(flattenMultiple(obj,nbTotDims,evaluator));
            }
        }
        else if(o instanceof MultipleValue)
        {
            Object out = getDim(((MultipleValue)o).getValue(),evaluator,false);
            for(int i = 0; i < nbTotDims; i++)
            {
                curList.add(out);
            }
        }
        else
        {
            return null;
        }
        return curList;
    }
    
    
    public void evaluateValue(Evaluator evaluator) throws EvaluationException
    {
        evaluateValue(evaluator,false);
    }
    
    public void evaluateValue(Evaluator evaluator, boolean strict) throws EvaluationException
    {
        Object data = getInputValue();
        ArrayList<Object> listValues = new ArrayList<>();
        if(data == null)
        {
            data = docVariable.getDefaultval();
        }
        
        if(data == null)
        {
            this.value = null;
            return;
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
                int size = getAlldims.get(i);
                nbTotDims *= size;
            }
        }
        else
        {
            nbTotDims = -1;
        }
        
        String type = docVariable.getVartype();
        
        ArrayList<Object> currentList2 = flattenMultiple(myVal,nbTotDims,evaluator);
        if(currentList2 == null)
        {
            this.value = null;
            return;
        }
        listValues.addAll(currentList2);
        /*if(myVal instanceof Number)
        {
            listValues.add(myVal);
        }
        else if(myVal instanceof Set)
        {
            listValues.addAll((Set)myVal);
        }
        else if(myVal instanceof ArrayList)
        {
            listValues.addAll(Utils.flatten((ArrayList)myVal));
        }
        else if(myVal instanceof MultipleValue)
        {
            Object out = getDim(((MultipleValue)myVal).getValue(),evaluator,false);
            for(int i = 0; i < nbTotDims; i++)
            {
                listValues.add(out);
            }
        }
        else
        {
            this.value = null;
            return;
        }*/
        
        if(nbTotDims == -1)
        {
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
        else
        {
            int[] dims = new int[getAlldims.size()];
            for(int i = 0; i < getAlldims.size(); i++)
            {
                dims[i] = getAlldims.get(i);
            }
            
            if(nbTotDims != listValues.size())
            {
                String erMsg = "Mismatch for var = "+docVariable.getVarname()+" between doc dim ("+Arrays.toString(dims)+") and input file ("+listValues.size()+")";
                if(strict)
                {
                    throw new EvaluationException(erMsg);
                }
                else
                {
                    System.err.println("Mismatch for var = "+docVariable.getVarname()+" between doc dim ("+Arrays.toString(dims)+") and input file ("+listValues.size()+")");
                }
                if(nbTotDims > listValues.size())
                {
                    System.err.println("Not enough elements in input file !");
                    return;
                }
            }
            
            if(dims.length > 2) // TODO: check if null
            {
                if(type.contains("integer"))
                {
                    this.value = listValues.toArray(new Integer[0]);
                }
                else if(type.contains("real"))
                {
                    this.value = listValues.toArray(new Double[0]);
                }
            }
            else if(dims.length == 1)
            {
                if(type.contains("integer"))
                {
                    Integer[] tab = new Integer[listValues.size()];
                    for(int i = 0; i < tab.length; i++)
                    {
                        tab[i] = ((Number)listValues.get(i)).intValue();
                    }
                    this.value = tab;
                    //this.value = listValues.toArray(new Integer[0]);
                }
                else if(type.contains("real"))
                {
                    Double[] tab = new Double[listValues.size()];
                    for(int i = 0; i < tab.length; i++)
                    {
                        tab[i] = ((Number)listValues.get(i)).doubleValue();
                    }
                    this.value = tab;
                    //this.value = listValues.toArray(new Double[0]);
                }
            }
            else if(dims.length == 2)
            {
                int length1 = dims[0];
                int length2 = dims[1];

                Number[][] tab = null;
                if(type.contains("integer"))
                {
                    tab = new Integer[length1][length2];
                }
                else if(type.contains("real"))
                {
                    tab = new Double[length1][length2];
                }

                int index = 0;
                for(int i = 0; i < length2; i++)
                {
                    for(int k = 0; k < length1; k++)
                    {
                        // Still fortran convention Have to invert the reading sense !
                        tab[k][i] = (Number)listValues.get(index);
                        index++;
                    }
                }
                this.value = tab;
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

    /**
     * @return the listDeps
     */
    public ArrayList<String> getListDeps() {
        return listDeps;
    }

    /**
     * @param listDeps the listDeps to set
     */
    public void setListDeps(ArrayList<String> listDeps) {
        this.listDeps = listDeps;
    }
    
    public ArrayList<String> getDep(Object obj)
    {
        ArrayList<String> list = new ArrayList<>();
        if(obj instanceof String)
        {
            list.addAll(getVars((String)obj));
        }
        else if(obj instanceof ArrayList)
        {
            for(Object inobj : (ArrayList)obj)
            {
                list.addAll(getDep(inobj));
            }
        }
        else if(obj instanceof ValueWithConditions)
        {
            ValueWithConditions vwc = (ValueWithConditions)obj;
            
            for(Entry<? extends Object,Object> entry : vwc.getValues().entrySet())
            {
                list.addAll(getDep(entry.getValue()));
                list.addAll(getDep(entry.getKey()));
            }
        }
        else if(obj instanceof ValueWithUnit)
        {
            ValueWithUnit vwu = (ValueWithUnit)obj;
            
            list.addAll(getDep(vwu.getValue()));
        }
        else if(obj instanceof MultipleValue)
        {
            MultipleValue mv = (MultipleValue)obj;
            list.addAll(getDep(mv.getNumber()));
            list.addAll(getDep(mv.getValue()));
        }
        
        return list;
    }

    public ArrayList<String> lookForDeps() 
    {
        // Dependencies can be in default values
        // Dependencies can be in dimensions
        
        ArrayList<String> listDeps = new ArrayList<>();
        
        listDeps.addAll(getDep(this.docVariable.getDefaultval()));
        listDeps.addAll(getDep(this.docVariable.getDimensions()));
        
        return listDeps;
    }
}
