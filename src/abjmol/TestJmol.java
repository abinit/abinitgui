/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package abjmol;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jmol.adapter.smarter.SmarterJmolAdapter;
import org.jmol.api.JmolViewer;
import org.jmol.util.Logger;
import org.jmol.viewer.Viewer;
import org.openscience.jmol.app.jmolpanel.JmolPanel;
import org.openscience.jmol.app.jmolpanel.console.AppConsole;

/**
 *
 * @author yannick
 */
public class TestJmol {
    

  /* Code copied from Integrate.java from JMol !
   * Demonstrates a simple way to include an optional console along with the applet.
   * 
   */
  public static void main(String[] argv) {
    JFrame frame = new JFrame("Hello");
    frame.addWindowListener(new ApplicationCloser());
    frame.setSize(410, 700);
    Container contentPane = frame.getContentPane();
    JmolPanel jmolPanel = new JmolPanel();
    jmolPanel.setPreferredSize(new Dimension(400, 400));

    // main panel -- Jmol panel on top

    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.add(jmolPanel);
    
    // main panel -- console panel on bottom
    
    JPanel panel2 = new JPanel();
    panel2.setLayout(new BorderLayout());
    panel2.setPreferredSize(new Dimension(400, 300));
    AppConsole console = new AppConsole(jmolPanel.viewer, panel2,
        "History State Clear");
    
    // You can use a different JmolStatusListener or JmolCallbackListener interface
    // if you want to, but AppConsole itself should take care of any console-related callbacks
    jmolPanel.viewer.setJmolCallbackListener(console);
    
    panel.add("South", panel2);
    
    contentPane.add(panel);
    frame.setVisible(true);

    // sample start-up script
    
    String strError = jmolPanel.viewer
        .openFile("http://chemapps.stolaf.edu/jmol/docs/examples-11/data/caffeine.xyz");
    //viewer.openStringInline(strXyzHOH);
    if (strError == null)
      jmolPanel.viewer.evalString(strScript);
    else
      Logger.error(strError);
  }

  final static String strXyzHOH = "3\n" 
    + "water\n" 
    + "O  0.0 0.0 0.0\n"
    + "H  0.76923955 -0.59357141 0.0\n" 
    + "H -0.76923955 -0.59357141 0.0\n";

  final static String strScript = "delay; move 360 0 0 0 0 0 0 0 4;";

  static class ApplicationCloser extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
      System.exit(0);
    }
  }

  static class JmolPanel extends JPanel {

    JmolViewer viewer;
    
    private final Dimension currentSize = new Dimension();
    
    JmolPanel() {
      viewer = JmolViewer.allocateViewer(this, new SmarterJmolAdapter(), 
          null, null, null, null, null);
    }

    @Override
    public void paint(Graphics g) {
      getSize(currentSize);
      viewer.renderScreenImage(g, currentSize.width, currentSize.height);
    }
  }
    
}
