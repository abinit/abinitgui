/*
 Copyright (c) 2009-2014 Flavio Miguel ABREU ARAUJO (flavio.abreuaraujo@uclouvain.be)
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
 */

package abinitgui.projects;

import abinitgui.mdtb.ClustepSimulation;
import abinitgui.mdtb.TightBindingSimulation;
import abinitgui.core.Atom;
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
