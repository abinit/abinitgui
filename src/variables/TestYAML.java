import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import variables.ValueWithConditions;
import variables.ValueWithUnit;
import variables.Variable;
import variables.VariableConstructor;
import variables.VariableRepresenter;



public class TestYAML  {
	
	public static void main(String[] args)
	{
		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		options.setIndent(4);
		options.setPrettyFlow(true);
		
		Representer representer = new VariableRepresenter();
		
		Constructor constructor = new VariableConstructor();
		
		Yaml yaml = new Yaml(representer,options);
		
		Yaml yaml2 = new Yaml(constructor);
		
		try {
//			File f = new File("ABINIT_structvariables.yml");
			File f = new File("tmp_struct.yml");
			
			Object o = null;
			if(f.exists())
			{
				BufferedReader br = new BufferedReader(new FileReader(f));
				
				o = yaml2.load(br);
				
				br.close();
			}
			else
			{
				System.err.println("File "+f+" does not exist !");
			}
			
			String output = yaml.dump(o);
			
			File f2 = new File("ABINIT_structvariables_java.yml");
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f2)));
			
			if(pw.checkError())
			{
				System.err.println("Error while writing");
			}
			
			pw.println(output);

			if(pw.checkError())
			{
				System.err.println("Error while writing");
			}
			
			pw.close();
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

}



