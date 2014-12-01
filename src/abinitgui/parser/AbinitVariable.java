/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abinitgui.parser;

import abivars.Variable;
import java.util.ArrayList;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

/**
 *
 * @author yannick
 */
public class AbinitVariable {
    private Object value;
    private ArrayList<String> inputValue;
    private Variable docVariable;

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
    
    public void evaluateValue(Evaluator evaluator) throws EvaluationException
    {
        /*if(this.exprValue != null)
        {
            this.value = evaluator.evaluate(exprValue);
        }*/
        System.out.println("=== "+docVariable.getVarname()+" ===");
        System.out.println("expr = "+inputValue);
        System.out.println("value = "+value);
        // TODO change type of value !
    }

    /**
     * @return the inputValue
     */
    public ArrayList<String> getInputValue() {
        return inputValue;
    }

    /**
     * @param inputValue the inputValue to set
     */
    public void setInputValue(ArrayList<String> inputValue) {
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
}
