/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package abinitgui.variables;

/**
 *
 * @author yannick
 */
public class Abivars {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AllInputVars aiv = new AllInputVars();
        aiv.loadVars("abinit_vars.yml");
        AbinitInputVars abivars = new AbinitInputVars(aiv);
        abivars.setVisible(true);
    }
    
}
