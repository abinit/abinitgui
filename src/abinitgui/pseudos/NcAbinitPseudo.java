/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abinitgui.pseudos;

/**
 *
 * @author Yannick
 */
public class NcAbinitPseudo extends AbinitPseudo {
    public int l_local;
    public int l_max;
    public double nlcc_radius;
    public String summary;
    
    public String toString()
    {
        return "<html>"+"Z = "+this.Z+"<br />"+"Z_val = "+this.Z_val+"<br />"
                + "l_local = "+l_local+"<br />"+"l_max = "+this.l_max+"<br />"
                + "nlcc_rad = "+nlcc_radius+"<br />";
                
    }
}
