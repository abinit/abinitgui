/*
 AbinitGUI - Created in 2009
 
 Copyright (c) 2009-2015 Flavio Miguel ABREU ARAUJO (abreuaraujo.flavio@gmail.com)
                         Yannick GILLET (yannick.gillet@hotmail.com)

 Université catholique de Louvain, Louvain-la-Neuve, Belgium
 All rights reserved.

 AbinitGUI is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 AbinitGUI is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with AbinitGUI.  If not, see <http://www.gnu.org/licenses/>.

 For more information on the project, please see
 <http://gui.abinit.org/>.
 */

package abinitgui.tests;

import abivars.VariableConstructor;
import abivars.VariableRepresenter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

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
                        BufferedReader br;
			if(f.exists())
			{
			        br = new BufferedReader(new FileReader(f));
				
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



