/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projects;

import core.Atom;
import core.Password;
import java.util.Map;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;
import variables.Range;
import variables.ValueWithConditions;
import variables.ValueWithUnit;
import variables.Variable;

/**
 *
 * @author yannick
 */
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
        this.addTypeDescription(new TypeDescription(Atom.class, new Tag("!atom")));
        this.yamlConstructors.put(new Tag("!password"), new ConstructPassword());
    }

    private class ConstructPassword extends AbstractConstruct {
        public Object construct(Node node) {
            String val = (String)constructScalar((ScalarNode)node);
            return Password.p_decrypt(val);
        }
    }
}
