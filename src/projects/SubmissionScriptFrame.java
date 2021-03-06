/*
 Copyright (c) 2009-2013 Flavio Miguel ABREU ARAUJO (flavio.abreuaraujo@uclouvain.be)
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

package projects;

import core.MainFrame;
import projects.SGEScript;
import projects.SLURMScript;

public class SubmissionScriptFrame extends javax.swing.JFrame {

    private MainFrame mf;
    private SubmissionScript script;

    /**
     * Creates new form SubmissionScriptFrame
     */
    public SubmissionScriptFrame(MainFrame mf) {
        this();

        setMainFrame(mf);
    }
    
    public SubmissionScriptFrame() {
        initComponents();
        typeChooser.setSelectedItem("Frontend");
        parallelBox.setSelected(true);
        parallelBoxActionPerformed(null);
    }
    
    public void setMainFrame(MainFrame mf)
    {
        this.mf = mf;
    }

    public void setScript(SubmissionScript script) {
        this.script = script;

        typeChooser.setSelectedItem(script.getSystem());
        mailField.setText(script.getEmail());
        memField.setText(script.getMemoryMax());
        nbProcsField.setText(script.getNbProcs());
        parallelBox.setSelected(script.isParallel());
        preProcessArea.setText(script.getPreProcessPart());
        postProcessArea.setText(script.getPostProcessPart());
        timeField.setText(script.getTimeLimit());
    }

    public SubmissionScript getScript() {
        return script;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        typeChooser = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nbProcsField = new javax.swing.JTextField();
        memField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        timeField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        mailField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        parallelBox = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        preProcessArea = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        postProcessArea = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        validButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Submission script details");
        setResizable(false);

        typeChooser.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Frontend", "SGE", "SLURM" }));
        typeChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeChooserActionPerformed(evt);
            }
        });

        jLabel1.setText("Submission system :");

        jLabel2.setText("Number of processors :");

        nbProcsField.setText("1");

        memField.setText("2000");

        jLabel3.setText("Memory limit (MB) :");

        timeField.setText("23:59:59");

        jLabel4.setText("Time limit :");

        mailField.setText("yanikou19@gmail.com");

        jLabel5.setText("Email :");

        parallelBox.setText("Parallel");
        parallelBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parallelBoxActionPerformed(evt);
            }
        });

        preProcessArea.setColumns(20);
        preProcessArea.setRows(5);
        jScrollPane1.setViewportView(preProcessArea);

        jLabel6.setText("Pre-processing commands :");

        postProcessArea.setColumns(20);
        postProcessArea.setRows(5);
        jScrollPane2.setViewportView(postProcessArea);

        jLabel7.setText("Post-processing commands :");

        validButton.setText("Validate");
        validButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                validButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(typeChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(parallelBox, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(timeField, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(memField, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(nbProcsField, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(mailField, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(validButton)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nbProcsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(memField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(parallelBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(validButton)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void typeChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeChooserActionPerformed
        // Save to script instance !
        int index = typeChooser.getSelectedIndex();
        if (index == -1) {
            mf.printERR("Please choose a type !");
        }
        String type = (String) typeChooser.getSelectedItem();

        if (type.equals("Frontend")) {
            EnableSubmitFields(false);
        } else {
            EnableSubmitFields(true);
        }
    }//GEN-LAST:event_typeChooserActionPerformed

    public void EnableSubmitFields(boolean isEnabled) {
        mailField.setEnabled(isEnabled);
        memField.setEnabled(isEnabled);
        timeField.setEnabled(isEnabled);
    }

    private void validButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_validButtonActionPerformed
        // Save to script instance !
        int index = typeChooser.getSelectedIndex();
        if (index == -1) {
            mf.printERR("Please choose a type !");
        }
        String type = (String) typeChooser.getSelectedItem();
        if (!type.equals(script.getSystem())) {
            // Rebuilt the script !
            switch (type) {
                case "SGE":
                    script = new SGEScript(mf);
                    break;
                case "Frontend":
                    script = new FrontendScript(mf);
                    break;
                case "SLURM":
                    script = new SLURMScript(mf);
                    break;
                default:
                    mf.printERR("The type "+type+" is not yet implemented");
                    break;
            }
        }
        script.setEmail(mailField.getText());
        script.setMemoryMax(memField.getText());
        script.setNbProcs(nbProcsField.getText());
        script.setParallel(parallelBox.isSelected());
        script.setPreProcessPart(preProcessArea.getText());
        script.setPostProcessPart(postProcessArea.getText());
        script.setTimeLimit(timeField.getText());
        mf.getSimulation().getRemoteJob().setScript(script);
        this.setVisible(false);
    }//GEN-LAST:event_validButtonActionPerformed

    private void parallelBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parallelBoxActionPerformed
        nbProcsField.setEnabled(parallelBox.isSelected());
    }//GEN-LAST:event_parallelBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField mailField;
    private javax.swing.JTextField memField;
    private javax.swing.JTextField nbProcsField;
    private javax.swing.JCheckBox parallelBox;
    private javax.swing.JTextArea postProcessArea;
    private javax.swing.JTextArea preProcessArea;
    private javax.swing.JTextField timeField;
    private javax.swing.JComboBox typeChooser;
    private javax.swing.JButton validButton;
    // End of variables declaration//GEN-END:variables
}
