package variables;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ValueWithConditions implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2959610476532229311L;
	
	private HashMap<String,Object> values;
	
	public ValueWithConditions(Map<Object, Object> map)
	{
		values = new HashMap<>();
		
		for(Object key : map.keySet())
		{
			if(key instanceof String)
			{
				values.put((String)key, map.get(key));
			}
			else
			{
				throw new IllegalArgumentException("Wrong format for map : "+map);
			}
		}
	}

	public Map<? extends Object, Object> getValues() {
		return values;
	}
        
        public String toString() 
        {
            String s = "";
            String attheend = "";
            boolean already = false;
            for(Entry<String,Object> val : values.entrySet())
            {
                if(val.getKey().equals("defaultval"))
                {
                    attheend += "else is "+val.getValue();
                }
                else
                {
                    if(already)
                    {
                        s += "else if ";
                    }
                    else
                    {
                        already = true;
                        s += "if ";
                    }
                    s += val.getKey().replace("<", "&lt;").replace(">","&gt;")+" is "+val.getValue()+"  ";
                }
            }
            return s+", "+attheend;
        }
}
