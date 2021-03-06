/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projects;

import core.Atom;
import core.Password;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;
import variables.Range;
import variables.ValueWithConditions;
import variables.ValueWithUnit;
import variables.Variable;	 

/**
 *
 * @author yannick
 */
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
