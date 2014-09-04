/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package abinitgui.variables;

import java.awt.Container;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 *
 * @author yannick
 */
public class Utils {
    

    public static String fileToString(String fileName)
            throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public static final JScrollPane getScrollPane( JComponent component ) {

        Container p = component.getParent();
        if (p instanceof JViewport) {
               Container gp = p.getParent();
               if (gp instanceof JScrollPane) {
                   return (JScrollPane)gp;
               }
        }
        return null;
    }
    
}
