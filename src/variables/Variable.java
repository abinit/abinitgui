package variables;

import java.io.Serializable;

public class Variable implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 691960467798135403L;
	
	private String vartype;
	private String category;
	private String definition;
	private Object[] dimensions;
	private Object defaultval;
	private String text;
	private String varname;
	private String errordefault;
	private String errordimensions;
	private String errortype;
	private String section;
	private Range range;
	
	public String getVartype() {
		return vartype;
	}
	public void setVartype(String vartype) {
		this.vartype = vartype;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public Object[] getDimensions() {
		return dimensions;
	}
	public void setDimensions(Object[] dimensions) {
		this.dimensions = dimensions;
	}
	public Object getDefaultval() {
		return defaultval;
	}
	public void setDefaultval(Object defaultval) {
		this.defaultval = defaultval;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getVarname() {
		return varname;
	}
	public void setVarname(String varname) {
		this.varname = varname;
	}
	
	public String toString()
	{
		return "Name = "+varname;
	}
	public String getErrordefault() {
		return errordefault;
	}
	public void setErrordefault(String errordefault) {
		this.errordefault = errordefault;
	}
	public String getErrordimensions() {
		return errordimensions;
	}
	public void setErrordimensions(String errordimensions) {
		this.errordimensions = errordimensions;
	}
	public String getErrortype() {
		return errortype;
	}
	public void setErrortype(String errortype) {
		this.errortype = errortype;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public Range getRange() {
		return range;
	}
	public void setRange(Range range) {
		this.range = range;
	}

}
