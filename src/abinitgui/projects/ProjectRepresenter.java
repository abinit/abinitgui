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
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;	 

public class ProjectRepresenter extends Representer {
    	public ProjectRepresenter() {

            this.addClassTag(Machine.class,new Tag("!machine"));
            this.addClassTag(LocalMachine.class,new Tag("!localmachine"));
            this.addClassTag(RemoteMachine.class,new Tag("!remotemachine"));
            this.addClassTag(GatewayMachine.class,new Tag("!gatewaymachine"));
            this.addClassTag(ConnectionInfo.class,new Tag("!connectioninfo"));
            this.addClassTag(SubmissionScript.class, new Tag("!submissionscript"));
            this.addClassTag(SGEScript.class, new Tag("!sgescript"));
            this.addClassTag(SLURMScript.class, new Tag("!slurmscript"));
            this.addClassTag(FrontendScript.class, new Tag("!frontendscript"));
            this.addClassTag(Simulation.class, new Tag("!simulation"));
            this.addClassTag(AbinitSimulation.class, new Tag("!abinitsimulation"));
            this.addClassTag(ClustepSimulation.class, new Tag("!clustepsimulation"));
            this.addClassTag(TightBindingSimulation.class, new Tag("!tightbindingsimulation"));
            this.addClassTag(RemoteJob.class,new Tag("!remotejob"));
            this.addClassTag(SubmissionSystem.class,new Tag("!submissionsystem"));
            this.addClassTag(SubmissionSGESystem.class,new Tag("!submissionsgesystem"));
            this.addClassTag(SubmissionSLURMSystem.class,new Tag("!submissionslurmsystem"));
            this.addClassTag(SubmissionFrontendSystem.class,new Tag("!submissionfrontendsystem"));
        
            this.addClassTag(Atom.class, new Tag("!atom"));
            this.representers.put(Password.class, new RepresentPassword());
	}
	
	class RepresentPassword implements Represent
	{
		@Override
		public Node representData(Object data) {
			Password pwd = (Password)data;
			
			return representScalar(new Tag("!password"), pwd.p_encrypt());
		}
		
	}
}
