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
import org.openscience.jmol.app.jmolpanel.console.AppConsole;

public class TestJmol {
    

  /* Code copied from Integrate.java from JMol !
   * Demonstrates a simple way to include an optional console along with the applet.
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
        .openFile("http://chemapps.stolaf.edu/jmol/docs/examples-11/data/zircon.xyz");
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
