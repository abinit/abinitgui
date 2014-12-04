/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abinitgui.parser;

import net.sourceforge.jeval.Evaluator;

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
    }
}
