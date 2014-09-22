/*
 Copyright (c) 2009-2014 Flavio Miguel ABREU ARAUJO (flavio.abreuaraujo@uclouvain.be)
                         Yannick GILLET (yannick.gillet@uclouvain.be)

 Université catholique de Louvain, Louvain-la-Neuve, Belgium
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1. Redistributions of source code must retain the above copyright
 notice, this list of conditions, and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright
 notice, this list of conditions, and the disclaimer that follows
 these conditions in the documentation and/or other materials
 provided with the distribution.

 3. The names of the author may not be used to endorse or promote
 products derived from this software without specific prior written
 permission.

 In addition, we request (but do not require) that you include in the
 end-user documentation provided with the redistribution and/or in the
 software itself an acknowledgement equivalent to the following:
 "This product includes software developed by the
 Abinit Project (http://www.abinit.org/)."

 THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED.  IN NO EVENT SHALL THE JDOM AUTHORS OR THE PROJECT
 CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 SUCH DAMAGE.

 For more information on the Abinit Project, please see
 <http://www.abinit.org/>.
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



