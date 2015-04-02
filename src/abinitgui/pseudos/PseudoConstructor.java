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

import abinitgui.scriptbib.*;
import abinitgui.projects.*;
import abinitgui.mdtb.ClustepSimulation;
import abinitgui.mdtb.TightBindingSimulation;
import abinitgui.pseudos.Atom;
import abinitgui.core.Password;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

public class PseudoConstructor extends Constructor {
    public PseudoConstructor() {
        
        this.addTypeDescription(new TypeDescription(NcAbinitPseudo.class,new Tag("!NcAbinitPseudo")));
        this.addTypeDescription(new TypeDescription(PawAbinitPseudo.class,new Tag("!PawAbinitPseudo")));
    }
}
