/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abinitgui.parser;

import java.util.ArrayList;
import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.function.Function;
import net.sourceforge.jeval.function.FunctionConstants;
import net.sourceforge.jeval.function.FunctionException;
import net.sourceforge.jeval.function.FunctionHelper;
import net.sourceforge.jeval.function.FunctionResult;

/**
 *
 * @author yannick
 */
public class AbinitEvaluator extends Evaluator
{
    public AbinitEvaluator()
    {
        super();
        
        this.putVariable("AUTO_FROM_PSP", "0");
        this.putVariable("SEQUENTIAL","0");
        this.putVariable("FFTW3","0");
        this.putVariable("MPI_IO","0");
        this.putVariable("CUDA","0");
        
        this.putFunction(new SumFunction());
    }
    
    private class SumFunction implements Function
    {

        @Override
        public String getName() {
            return "sum";
        }

        @Override
        public FunctionResult execute(Evaluator evltr, String string) throws FunctionException {
            try{
            ArrayList<Double> listDouble = FunctionHelper.getDoubles(string,',');
            Double d = 0.0;
            for(Double o : listDouble)
            {
                d = d+o.doubleValue();
            }
            //System.exit(-1);
            return new FunctionResult(""+d,FunctionConstants.FUNCTION_RESULT_TYPE_NUMERIC);
            }catch(Exception e)
            {
                e.printStackTrace();
                System.exit(0);
            }
            return null;
        }
        
    }
}
