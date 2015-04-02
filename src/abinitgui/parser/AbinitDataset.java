/*
 AbinitGUI - Created in July 2009
 
 Copyright (c) 2009-2015 Flavio Miguel ABREU ARAUJO (flavio.abreuaraujo@uclouvain.be)
                         Yannick GILLET (yannick.gillet@uclouvain.be)

 Université catholique de Louvain, Louvain-la-Neuve, Belgium
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1. Redistributions of source code must retain the above copyright
 notice, this list of conditions, and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright
 notice, this list of conditions, and the disclaimer that follows
 these conditions in the documentation and/or other materials
 provided with the distribution.

 3. The names of the author may not be used to endorse or promote
 products derived from this software without specific prior written
 permission.

 In addition, we request (but do not require) that you include in the
 end-user documentation provided with the redistribution and/or in the
 software itself an acknowledgement equivalent to the following:
 "This product includes software developed by the
 Abinit Project (http://www.abinit.org/)."

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE JDOM AUTHORS OR THE PROJECT
 CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.

 For more information on the Abinit Project, please see
 <http://www.abinit.org/>.

 For more information on the AbinitGUI Project, please see
 <http://gui.abinit.org/>.
 */
package abinitgui.parser;

import abivars.AllInputVars;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

/**
 *
 * @author yannick
 */
public class AbinitDataset implements Iterable<AbinitVariable>
{
    private HashMap<String,AbinitVariable> allData;
    private DepTree tree;
    
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
        /**LinkedList<String> varsForDim = new LinkedList<>();
        LinkedList<String> varsForVal = new LinkedList<>();
        
        varsForDim.addAll(allData.keySet());
        varsForVal.addAll(allData.keySet());
        
        // Brute force !
        // 1st set going to get all the dependencies !
        LinkedList<String> newVarsForDims = new LinkedList<>(varsForDim);
        for(String varname : newVarsForDims)
        {
            AbinitVariable abivar = allData.get(varname);
            try{
                abivar.evaluateDim(evaluator);
                if(abivar.getDims() != null)
                {
                    varsForDim.remove(varname);
                }
            }
            catch(EvaluationException exc)
            {
                System.err.println("exception encountered : "+exc);
            }
        }

        LinkedList<String> newVarsForVal = new LinkedList<>(varsForVal);
        for(String varname : newVarsForVal)
        {
            AbinitVariable abivar = allData.get(varname);
            try{
                abivar.evaluateValue(evaluator);
                if(abivar.getValue() != null)
                {
                    Object obj = abivar.getValue();
                    varsForVal.remove(varname);
                    // I don't want to set variable here in this new version !
                    //evaluator.putVariable(varname, obj.toString());
                }
            }
            catch(EvaluationException exc)
            {
                System.err.println("exception encoutered : "+exc);
            }
        }
        
        // At this point of the code, each variable has its dependencies:
        // TODO: Extract dependencies from the doc directly !
        this.setTree(new DepTree());
        for(AbinitVariable abivar : allData.values())
        {
            System.out.println("Dependencies of "+abivar.getDocVariable().getVarname()+" : "+abivar.getListDeps());
            getTree().addDep(abivar.getDocVariable().getVarname(), abivar.getListDeps());
        }
        
        System.out.println(getTree().toString());**/
        /**
        int maxnbiter = 10;
        while(maxnbiter > 0 && (varsForDim.size() > 0 || varsForVal.size() > 0))
        {
            newVarsForDims = new LinkedList<>(varsForDim);
            for(String varname : newVarsForDims)
            {
                AbinitVariable abivar = allData.get(varname);
                try{
                    abivar.evaluateDim(evaluator);
                    if(abivar.getDims() != null)
                    {
                        varsForDim.remove(varname);
                    }
                }
                catch(EvaluationException exc)
                {
                    System.err.println("exception encountered : "+exc);
                }
            }
            
            newVarsForVal = new LinkedList<>(varsForVal);
            for(String varname : newVarsForVal)
            {
                AbinitVariable abivar = allData.get(varname);
                try{
                    abivar.evaluateValue(evaluator);
                    if(abivar.getValue() != null)
                    {
                        Object obj = abivar.getValue();
                        varsForVal.remove(varname);
                        evaluator.putVariable(varname, obj.toString());
                    }
                }
                catch(EvaluationException exc)
                {
                    System.err.println("exception encoutered : "+exc);
                }
            }
            
            //System.out.println("evaluator = "+evaluator.getVariables());
            
            maxnbiter--;
        }
        
        if(maxnbiter == 0)
        {
            System.err.println("Number max of iteration reached !");
            System.err.println("varsForDims = "+varsForDim);
            System.err.println("varsForVal = "+varsForVal);
        }**/
    }

    /**
     * @return the tree
     */
    public DepTree getTree() {
        return tree;
    }

    /**
     * @param tree the tree to set
     */
    public void setTree(DepTree tree) {
        this.tree = tree;
    }
}
