/*
 AbinitGUI - Created in 2009
 
 Copyright (c) 2009-2015 Flavio Miguel ABREU ARAUJO (abreuaraujo.flavio@gmail.com)
                         Yannick GILLET (yannick.gillet@hotmail.com)

 Université catholique de Louvain, Louvain-la-Neuve, Belgium
 All rights reserved.

 AbinitGUI is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 AbinitGUI is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with AbinitGUI.  If not, see <http://www.gnu.org/licenses/>.

 For more information on the project, please see
 <http://gui.abinit.org/>.
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
