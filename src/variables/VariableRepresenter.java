package variables;

import java.util.Map;

import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.constructor.*;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.representer.*;



public class VariableRepresenter extends Representer {
	public VariableRepresenter() {

		this.addClassTag(Variable.class,new Tag("!variable"));
		this.addClassTag(ValueWithUnit.class,new Tag("!valuewithunit"));
		this.addClassTag(Range.class,new Tag("!range"));
		this.representers.put(ValueWithConditions.class, new RepresentConditions());
	}
	
	class RepresentConditions implements Represent
	{

		@Override
		public Node representData(Object data) {
			ValueWithConditions vwc = (ValueWithConditions)data;
			
			return representMapping(new Tag("!valuewithconditions"), vwc.getValues(), false);
		}
		
	}
}