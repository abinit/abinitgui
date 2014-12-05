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

package abinitgui.projects;

import abinitgui.core.MainFrame;
import java.awt.Color;
import java.awt.Component;
import java.util.Iterator;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class ProjectFrame extends javax.swing.JDialog {

    //private MainFrame mainFrame;
    private Project project;
    DefaultMutableTreeNode top;

    public ProjectFrame() {

        initComponents();

        top = new DefaultMutableTreeNode("Current project");

        TreeModel model = new DefaultTreeModel(top);
        tasksTree.setModel(model);

        tasksTree.setCellRenderer(new ProjectTreeRenderer());

        //project = new Project();

        //project.loadDatabaseFromXMLFile("currentProject.xml");

        //createNodes(top);

        setLocationRelativeTo(MainFrame.mainFrame);
        setLocation(800, 0);
    }
    
    public void setProject(Project project)
    {
        this.project = project;
        createNodes(top);
    }

    public final void createNodes(DefaultMutableTreeNode top) {
        DefaultMutableTreeNode book;

        Iterator<Simulation> iter = project.iterator();

        while (iter.hasNext()) {
            Simulation simu = iter.next();
            book = new DefaultMutableTreeNode(simu);
            top.add(book);
        }

    }

    public void mySingleClick(int selRow, TreePath selPath) {
        System.out.println("Single Click on selRow = " + selRow + "; Path = " + selPath);
    }

    public void myDoubleClick(int selRow, TreePath selPath) {
        System.out.println("Double Click on selRow = " + selRow + "; Path = " + selPath);

        int count = selPath.getPathCount();

        if (count > 1) {
            TreeNode node = (TreeNode) selPath.getPath()[selPath.getPathCount() - 1];
            String name = node.toString();
            Simulation simu = (Simulation) ((DefaultMutableTreeNode) node).getUserObject();
            MainFrame.mainFrame.setSimulation(simu);
        }
    }

    public class ProjectTreeRenderer extends DefaultTreeCellRenderer {
        @Override
        public Component getTreeCellRendererComponent(
                JTree tree,
                Object value,
                boolean selected,
                boolean expanded,
                boolean leaf,
                int row,
                boolean hasFocus) {
            // Allow the original renderer to set up the label
            Component c = super.getTreeCellRendererComponent(
                    tree, value, selected,
                    expanded, leaf, row,
                    hasFocus);

            // Use a different foreground
            // color for leaf nodes.
            c.setBackground(Color.WHITE);

            Object obj = ((DefaultMutableTreeNode) value).getUserObject();
            if (obj instanceof Simulation) {
                Simulation simu = (Simulation) obj;

                System.out.println("Status = " + simu.getStatus());
                System.out.println("Status = " + simu.getRemoteJob().getStatus());
                System.out.println("Status = " + simu.getRemoteJob().getStatusString());

                if (simu.getStatus() == RemoteJob.RUNNING) {
                    c.setForeground(Color.BLUE);
                } else if (simu.getStatus() == RemoteJob.COMPLETED) {
                    c.setForeground(Color.BLACK);
                } else {
                    c.setForeground(Color.LIGHT_GRAY);
                }
            } else {
            }
            return c;
        }

        @Override
        public Color getBackground() {
            return null;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane6 = new javax.swing.JScrollPane();
        tasksTree = new javax.swing.JTree();
        jButton4 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Project Manager");

        tasksTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tasksTreeMousePressed(evt);
            }
        });
        jScrollPane6.setViewportView(tasksTree);

        jButton4.setText("New simulation");

        jButton7.setText("Rename simulation");

        jButton5.setText("Refresh status");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Delete job");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                    .addComponent(jScrollPane6)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tasksTreeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tasksTreeMousePressed
        int selRow = tasksTree.getRowForLocation(evt.getX(), evt.getY());
        TreePath selPath = tasksTree.getPathForLocation(evt.getX(), evt.getY());
        if (selRow != -1) {
            if (evt.getClickCount() == 1) {
                mySingleClick(selRow, selPath);
            } else if (evt.getClickCount() == 2) {
                myDoubleClick(selRow, selPath);
            }
        }
    }//GEN-LAST:event_tasksTreeMousePressed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        updateAllStatus();
    }//GEN-LAST:event_jButton5ActionPerformed

    public void updateAllStatus() {
        Iterator<Simulation> iter = project.iterator();

        while (iter.hasNext()) {
            Simulation simu = iter.next();
            System.out.println("simu = "+simu);
            System.out.println("simu = "+simu.getClass());
            simu.updateStatus();
            System.out.println("simu = "+simu.getRemoteJob().getStatusString());
        }
        tasksTree.repaint();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTree tasksTree;
    // End of variables declaration//GEN-END:variables
}
