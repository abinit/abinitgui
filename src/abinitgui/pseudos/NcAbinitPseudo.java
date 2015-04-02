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
