/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abinitgui.tests;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;

/**
 *
 * @author yannick
 */
public class TestJEval {
    
    public static void main(String[] args)
    {
        try {
            Evaluator jeval = new Evaluator();
            
            jeval.putVariable("npsp","5");
            System.out.println(jeval.evaluate("abs(#{npsp}+3)"));
        } catch (EvaluationException ex) {
            Logger.getLogger(TestJEval.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
