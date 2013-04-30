package json;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestJSon
{
	public static void main(String[] args)
	{
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("test-json.txt"));
            String s = br.readLine();
            JSONArray dtsets = new JSONArray(s);
            int length = dtsets.length();
            System.out.println("length = "+length);
            for(int idtset = 0; idtset < length; idtset++)
            {
                
            JSONObject json = new JSONArray(s).getJSONObject(idtset);
            System.out.println("file = "+s);
            System.out.println("jdtset = "+json.get("jdtset"));
            System.out.println("JSON = "+json);
            }
        } catch (IOException ex) {
            Logger.getLogger(TestJSon.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(TestJSon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
	}
}
