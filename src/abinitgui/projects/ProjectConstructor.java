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

package abinitgui.projects;

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

public class ProjectConstructor extends Constructor {
    public ProjectConstructor() {
        
        this.addTypeDescription(new TypeDescription(Machine.class,new Tag("!machine")));
        this.addTypeDescription(new TypeDescription(LocalMachine.class,new Tag("!localmachine")));
        this.addTypeDescription(new TypeDescription(RemoteMachine.class,new Tag("!remotemachine")));
        this.addTypeDescription(new TypeDescription(GatewayMachine.class,new Tag("!gatewaymachine")));
        this.addTypeDescription(new TypeDescription(ConnectionInfo.class,new Tag("!connectioninfo")));
        this.addTypeDescription(new TypeDescription(SubmissionScript.class, new Tag("!submissionscript")));
        this.addTypeDescription(new TypeDescription(SGEScript.class, new Tag("!sgescript")));
        this.addTypeDescription(new TypeDescription(SLURMScript.class, new Tag("!slurmscript")));
        this.addTypeDescription(new TypeDescription(FrontendScript.class, new Tag("!frontendscript")));
        this.addTypeDescription(new TypeDescription(Simulation.class, new Tag("!simulation")));
        this.addTypeDescription(new TypeDescription(AbinitSimulation.class, new Tag("!abinitsimulation")));
        this.addTypeDescription(new TypeDescription(ClustepSimulation.class, new Tag("!clustepsimulation")));
        this.addTypeDescription(new TypeDescription(TightBindingSimulation.class, new Tag("!tightbindingsimulation")));
        this.addTypeDescription(new TypeDescription(Atom.class, new Tag("!atom")));
        this.addTypeDescription(new TypeDescription(RemoteJob.class,new Tag("!remotejob")));
        this.addTypeDescription(new TypeDescription(SubmissionSystem.class, new Tag("!submissionsystem")));
        this.addTypeDescription(new TypeDescription(SubmissionSGESystem.class, new Tag("!submissionsgesystem")));
        this.addTypeDescription(new TypeDescription(SubmissionSLURMSystem.class, new Tag("!submissionslurmsystem")));
        this.addTypeDescription(new TypeDescription(SubmissionFrontendSystem.class, new Tag("!submissionfrontendsystem")));
        this.yamlConstructors.put(new Tag("!password"), new ConstructPassword());
    }

    private class ConstructPassword extends AbstractConstruct {
        @Override
        public Object construct(Node node) {
            String val = (String)constructScalar((ScalarNode)node);
            return Password.p_decrypt(val);
        }
    }
}
