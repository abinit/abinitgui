package variables;

import java.util.Map;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

public class VariableConstructor extends Constructor {
    public VariableConstructor() {

		this.addTypeDescription(new TypeDescription(Variable.class, "!variable"));
		this.addTypeDescription(new TypeDescription(ValueWithUnit.class, "!valuewithunit"));
		this.addTypeDescription(new TypeDescription(Range.class, "!range"));
		this.yamlConstructors.put(new Tag("!valuewithconditions"), new ConstructConditions());
    }

    private class ConstructConditions extends AbstractConstruct {
        public Object construct(Node node) {
        	Map<Object,Object> map = constructMapping((MappingNode)node);
        	
        	return new ValueWithConditions(map);
        }
    }
}