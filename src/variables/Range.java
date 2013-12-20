package variables;

import java.io.Serializable;

public class Range implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8259423996131963216L;
	
	private Object start;
	private Object stop;
	
	public Object getStart() {
		return start;
	}
	public void setStart(Object start) {
		this.start = start;
	}
	public Object getStop() {
		return stop;
	}
	public void setStop(Object stop) {
		this.stop = stop;
	}

}
