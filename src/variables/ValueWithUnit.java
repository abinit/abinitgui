package variables;

import java.io.Serializable;

public class ValueWithUnit implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1917009023883304327L;
	
	private Object value;
	private String units;
	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	
	public String toString()
	{
		return "Units : "+units;
	}
}
