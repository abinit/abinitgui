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
