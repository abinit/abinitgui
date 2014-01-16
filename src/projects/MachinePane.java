/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projects;

import core.MainFrame;
import core.Password;
import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author yannick
 */
public class MachinePane extends javax.swing.JPanel {

    private MainFrame mf;
    private Machine machine;
    
    public MachinePane()
    {
        this.machine = null;
        initComponents();
        remoteGatewayRadioButton.doClick();
    }
    
    public void setMainFrame(MainFrame mf)
    {
        this.mf = mf;
        submissionScriptPanel1.setMainFrame(mf);
    }
    
    public void refresh()
    {
        Machine curMach = (Machine)machineList.getSelectedValue();
        
        MachineDatabase ddb = this.mf.getMachineDatabase();
        DefaultListModel<Machine> model = new DefaultListModel<>();
        for(Machine mach : ddb)
        {
            model.addElement(mach);
        }
        machineList.setModel(model);
        
        if(curMach != null)
        {
            machineList.setSelectedValue(curMach, true);
        }
    }
    
    public void fillFieldsFromMachine(Machine machine)
    {
        this.machine = machine;
        setEmptyFields();
        if(machine != null)
        {
            if(machine.getName() != null)
                this.nameField.setText(machine.getName());
            
            if(machine.getAbinitPath() != null)
                this.abinitPathTextField.setText(machine.getAbinitPath());
            
            if(machine.getSimulationPath() != null)
                this.mySimulationsTextField.setText(machine.getSimulationPath());
            
            if(machine.getType() == Machine.REMOTE_MACHINE || 
                    machine.getType() == Machine.GATEWAY_MACHINE)
            {
                if(machine.getRemoteConnect() != null)
                {
                    if(machine.getRemoteConnect().getHost() != null)
                        this.hostTextField.setText(machine.getRemoteConnect().getHost());
                    
                    if(machine.getRemoteConnect().getLogin() != null)
                        this.loginTextField.setText(machine.getRemoteConnect().getLogin());
                    
                    if(machine.getRemoteConnect().getPassword() != null)
                        this.pwdPasswordField.setText(machine.getRemoteConnect().getPassword().toString());
                    
                    this.jCB_useKey1.setSelected(machine.getRemoteConnect().isUseKey());
                    activateKey(jCB_useKey1, jTF_key1, pwdLabel);
                    
                    jTF_key1.setText(machine.getRemoteConnect().getKeyPath());
                }
                
                if(machine.getType() == Machine.GATEWAY_MACHINE)
                {
                    if(machine.getGatewayConnect().getHost() != null)
                        this.gatewayHostTextField.setText(machine.getGatewayConnect().getHost());
                    
                    if(machine.getGatewayConnect().getLogin() != null)
                        this.gatewayLoginTextField.setText(machine.getGatewayConnect().getLogin());
                    
                    if(machine.getGatewayConnect().getPassword() != null)
                        this.gatewayPasswordField.setText(machine.getGatewayConnect().getPassword().toString());
                    
                    this.jCB_useKey2.setSelected(machine.getGatewayConnect().isUseKey());
                    activateKey(jCB_useKey2, jTF_key2, pwdBFELabel);
                    
                    jTF_key2.setText(machine.getGatewayConnect().getKeyPath());
                }
            }
        
            if(machine.getType() == Machine.GATEWAY_MACHINE)
            {
                this.remoteGatewayRadioButton.setSelected(true);
            }
            else if(machine.getType() == Machine.REMOTE_MACHINE)
            {
                this.remoteAbinitRadioButton.setSelected(true);
            }
            else if(machine.getType() == Machine.LOCAL_MACHINE)
            {
                this.localAbinitRadioButton.setSelected(true);
            }
            else
            {
                System.err.println("Another type was discovered");
            }
            
            activateType();
            
            this.submissionScriptPanel1.setScript(machine.getSubmissionScript());
        }
    }
    
    public void createNewMachine()
    {
        this.machine = new LocalMachine();
        this.machine.setName("New machine");
        
        this.mf.getMachineDatabase().addMachine(this.machine);
        
        refresh();
        
        setEmptyFields();
        
        mf.refreshMachines();
    }
    
    private void setEmptyFields()
    {
        this.gatewayHostTextField.setText("");
        this.gatewayLoginTextField.setText("");
        this.gatewayPasswordField.setText("");
        this.jCB_useKey2.setSelected(false);
        this.hostTextField.setText("");
        this.loginTextField.setText("");
        this.pwdPasswordField.setText("");
        this.jCB_useKey1.setSelected(false);
        this.abinitPathTextField.setText("");
        this.mySimulationsTextField.setText("");
        this.nameField.setText("");
    }
    
    public void saveMachineFromFields()
    {
        Machine mach = (Machine)machineList.getSelectedValue();
        
        if(mach != null)
        {
            int type = -1;

            if(remoteAbinitRadioButton.isSelected())
                type = Machine.REMOTE_MACHINE;
            else if(remoteGatewayRadioButton.isSelected())
                type = Machine.GATEWAY_MACHINE;
            else if(localAbinitRadioButton.isSelected())
                type = Machine.LOCAL_MACHINE;

            String oldName = null;
            if(machine != null)
            {
                oldName = machine.getName();
            }

            boolean changeType = false;
            if (machine == null || type != machine.getType()) 
            {
                changeType = true;
                // Rebuilt the script !
                switch (type) {
                    case Machine.REMOTE_MACHINE:
                        machine = new RemoteMachine();
                        System.out.println("Machine is Remote !");
                        break;
                    case Machine.GATEWAY_MACHINE:
                        machine = new GatewayMachine();
                        System.out.println("Machine is Gateway !");
                        break;
                    case Machine.LOCAL_MACHINE:
                        machine = new LocalMachine();
                        System.out.println("Machine is Local !");
                        break;
                    default:
                        mf.printERR("Please select the type of the machine");
                        machine = null;
                        break;
                }
            }

            if(this.machine != null)
            {
                this.machine.setAbinitPath(this.abinitPathTextField.getText());
                this.machine.setSimulationPath(this.mySimulationsTextField.getText());
                this.machine.setName(this.nameField.getText());

                if(remoteAbinitRadioButton.isSelected() || remoteGatewayRadioButton.isSelected())
                {
                    ConnectionInfo infos = new ConnectionInfo();
                    infos.setHost(this.hostTextField.getText());
                    infos.setLogin(this.loginTextField.getText());
                    infos.setPassword(new Password(new String(this.pwdPasswordField.getPassword())));
                    infos.setUseKey(this.jCB_useKey1.isSelected());
                    infos.setKeyPath(this.jTF_key1.getText());
                    this.machine.setRemoteConnect(infos);

                    if(remoteGatewayRadioButton.isSelected())
                    {
                        System.out.println("Is Gateway : "+machine);
                        ConnectionInfo infosgw = new ConnectionInfo();
                        infosgw.setHost(this.gatewayHostTextField.getText());
                        infosgw.setLogin(this.gatewayLoginTextField.getText());
                        infosgw.setPassword(new Password(new String(this.gatewayPasswordField.getPassword())));
                        infosgw.setUseKey(this.jCB_useKey2.isSelected());
                        infosgw.setKeyPath(this.jTF_key2.getText());
                        this.machine.setGatewayConnect(infosgw);
                    }
                }

                if(changeType)
                {
                    if(oldName != null)
                    {
                        mf.getMachineDatabase().removeMachineWithName(oldName);
                    }
                    mf.getMachineDatabase().addMachine(machine);
                }

                this.machine.setSubmissionScript(this.submissionScriptPanel1.getScript());

                try {
                    mf.getMachineDatabase().saveToFile("machines.yml");
                } catch (IOException ex) {
                    Logger.getLogger(MachinePane.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            mf.refreshMachines();
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

        whereIsAbinitbuttonGroup = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        infosPanel = new javax.swing.JPanel();
        whereIsAbinitLabel = new javax.swing.JLabel();
        localAbinitRadioButton = new javax.swing.JRadioButton();
        remoteAbinitRadioButton = new javax.swing.JRadioButton();
        remoteGatewayRadioButton = new javax.swing.JRadioButton();
        loginPanel = new javax.swing.JPanel();
        hostTextField = new javax.swing.JTextField();
        hostLabel = new javax.swing.JLabel();
        loginLabel = new javax.swing.JLabel();
        loginTextField = new javax.swing.JTextField();
        pwdPasswordField = new javax.swing.JPasswordField();
        pwdLabel = new javax.swing.JLabel();
        jCB_useKey1 = new javax.swing.JCheckBox();
        jTF_key1 = new javax.swing.JTextField();
        gatewayLoginPanel = new javax.swing.JPanel();
        gatewayHostTextField = new javax.swing.JTextField();
        hostBFELabel = new javax.swing.JLabel();
        loginBFELabel = new javax.swing.JLabel();
        gatewayLoginTextField = new javax.swing.JTextField();
        gatewayPasswordField = new javax.swing.JPasswordField();
        pwdBFELabel = new javax.swing.JLabel();
        jCB_useKey2 = new javax.swing.JCheckBox();
        jTF_key2 = new javax.swing.JTextField();
        mySimulationsLabel = new javax.swing.JLabel();
        mySimulationsTextField = new javax.swing.JTextField();
        abinitPathPathLabel = new javax.swing.JLabel();
        abinitPathTextField = new javax.swing.JTextField();
        nameField = new javax.swing.JTextField();
        nameLabel = new javax.swing.JLabel();
        submissionScriptPanel1 = new projects.SubmissionScriptPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        machineList = new javax.swing.JList();
        newButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();

        whereIsAbinitLabel.setForeground(java.awt.Color.red);
        whereIsAbinitLabel.setText("ABINIT host location ?");

        whereIsAbinitbuttonGroup.add(localAbinitRadioButton);
        localAbinitRadioButton.setForeground(java.awt.Color.blue);
        localAbinitRadioButton.setText("Local (only for Linux hosts)");
        localAbinitRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                localAbinitRadioButtonActionPerformed(evt);
            }
        });

        whereIsAbinitbuttonGroup.add(remoteAbinitRadioButton);
        remoteAbinitRadioButton.setForeground(java.awt.Color.blue);
        remoteAbinitRadioButton.setText("Remote");
        remoteAbinitRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remoteAbinitRadioButtonActionPerformed(evt);
            }
        });

        whereIsAbinitbuttonGroup.add(remoteGatewayRadioButton);
        remoteGatewayRadioButton.setForeground(java.awt.Color.red);
        remoteGatewayRadioButton.setText("Remote (behind a gateway)");
        remoteGatewayRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remoteGatewayRadioButtonActionPerformed(evt);
            }
        });

        loginPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Remote Abinithost login", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 3, 14), java.awt.Color.darkGray)); // NOI18N

        hostLabel.setText("Hostname or IP");

        loginLabel.setText("Login");

        pwdLabel.setText("Password");

        jCB_useKey1.setText("use key");
        jCB_useKey1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_useKey1ActionPerformed(evt);
            }
        });

        jTF_key1.setEnabled(false);

        javax.swing.GroupLayout loginPanelLayout = new javax.swing.GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout.setHorizontalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hostTextField)
                            .addGroup(loginPanelLayout.createSequentialGroup()
                                .addComponent(hostLabel)
                                .addGap(0, 160, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(loginTextField)
                            .addComponent(loginLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pwdLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pwdPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addComponent(jCB_useKey1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTF_key1)))
                .addContainerGap())
        );
        loginPanelLayout.setVerticalGroup(
            loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hostLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pwdLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hostTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pwdPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(loginPanelLayout.createSequentialGroup()
                        .addComponent(loginLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loginTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(loginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCB_useKey1)
                    .addComponent(jTF_key1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gatewayLoginPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Gateway login", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 3, 14), java.awt.Color.darkGray)); // NOI18N

        hostBFELabel.setText("Hostname or IP");

        loginBFELabel.setText("Login");

        pwdBFELabel.setText("Password");

        jCB_useKey2.setText("use key");
        jCB_useKey2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCB_useKey2ActionPerformed(evt);
            }
        });

        jTF_key2.setEnabled(false);

        javax.swing.GroupLayout gatewayLoginPanelLayout = new javax.swing.GroupLayout(gatewayLoginPanel);
        gatewayLoginPanel.setLayout(gatewayLoginPanelLayout);
        gatewayLoginPanelLayout.setHorizontalGroup(
            gatewayLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gatewayLoginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gatewayLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gatewayLoginPanelLayout.createSequentialGroup()
                        .addComponent(jCB_useKey2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTF_key2))
                    .addGroup(gatewayLoginPanelLayout.createSequentialGroup()
                        .addGroup(gatewayLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gatewayHostTextField)
                            .addComponent(hostBFELabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(gatewayLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(gatewayLoginTextField)
                            .addComponent(loginBFELabel, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(gatewayLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pwdBFELabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(gatewayPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        gatewayLoginPanelLayout.setVerticalGroup(
            gatewayLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gatewayLoginPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gatewayLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(gatewayLoginPanelLayout.createSequentialGroup()
                        .addGroup(gatewayLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hostBFELabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pwdBFELabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(gatewayLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(gatewayHostTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gatewayPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(gatewayLoginPanelLayout.createSequentialGroup()
                        .addComponent(loginBFELabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gatewayLoginTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(gatewayLoginPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCB_useKey2)
                    .addComponent(jTF_key2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mySimulationsLabel.setText("Path where to create the simulations filetree");
        mySimulationsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mySimulationsLabelMouseClicked(evt);
            }
        });

        abinitPathPathLabel.setText("Path to the abinit program (At abinit server !)");
        abinitPathPathLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                abinitPathPathLabelMouseClicked(evt);
            }
        });

        nameLabel.setText("Name of the machine: ");

        javax.swing.GroupLayout infosPanelLayout = new javax.swing.GroupLayout(infosPanel);
        infosPanel.setLayout(infosPanelLayout);
        infosPanelLayout.setHorizontalGroup(
            infosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mySimulationsLabel)
                    .addComponent(mySimulationsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(abinitPathPathLabel)
                    .addComponent(abinitPathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(infosPanelLayout.createSequentialGroup()
                        .addComponent(localAbinitRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(remoteAbinitRadioButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(remoteGatewayRadioButton))
                    .addComponent(whereIsAbinitLabel)
                    .addGroup(infosPanelLayout.createSequentialGroup()
                        .addComponent(nameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(infosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(loginPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(gatewayLoginPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        infosPanelLayout.setVerticalGroup(
            infosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infosPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(whereIsAbinitLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(infosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(localAbinitRadioButton)
                    .addComponent(remoteAbinitRadioButton)
                    .addComponent(remoteGatewayRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gatewayLoginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loginPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mySimulationsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mySimulationsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(abinitPathPathLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(abinitPathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Connections", infosPanel);
        jTabbedPane1.addTab("Script default", submissionScriptPanel1);

        machineList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        machineList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                machineListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(machineList);

        newButton.setText("New");
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(newButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(newButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void localAbinitRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_localAbinitRadioButtonActionPerformed
        activateType();
    }//GEN-LAST:event_localAbinitRadioButtonActionPerformed

    private void activateType()
    {
        if(localAbinitRadioButton.isSelected())
        {
            localAbinitRadioButton.setForeground(Color.red);
            remoteGatewayRadioButton.setForeground(Color.blue);
            remoteAbinitRadioButton.setForeground(Color.blue);

            gatewayLoginPanel.setVisible(false);
            loginPanel.setVisible(false);
            loginPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)),
            "Local Abinithost login", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
            javax.swing.border.TitledBorder.DEFAULT_POSITION,
            new java.awt.Font("Arial", 3, 14), java.awt.Color.darkGray));
        }
        else if(remoteGatewayRadioButton.isSelected())
        {
            localAbinitRadioButton.setForeground(Color.blue);
            remoteGatewayRadioButton.setForeground(Color.red);
            remoteAbinitRadioButton.setForeground(Color.blue);

            gatewayLoginPanel.setVisible(true);
            loginPanel.setVisible(true);
            loginPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)),
                "Remote Abinithost login", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Arial", 3, 14), java.awt.Color.darkGray));
        }
        else if(remoteAbinitRadioButton.isSelected())
        {
            localAbinitRadioButton.setForeground(Color.blue);
            remoteGatewayRadioButton.setForeground(Color.blue);
            remoteAbinitRadioButton.setForeground(Color.red);

            gatewayLoginPanel.setVisible(false);
            loginPanel.setVisible(true);
            loginPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)),
                "Remote Abinithost login", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Arial", 3, 14), java.awt.Color.darkGray));
        }
    }
    
    private void remoteAbinitRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remoteAbinitRadioButtonActionPerformed
        activateType();
    }//GEN-LAST:event_remoteAbinitRadioButtonActionPerformed

    private void remoteGatewayRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remoteGatewayRadioButtonActionPerformed
        activateType();
    }//GEN-LAST:event_remoteGatewayRadioButtonActionPerformed

    private void jCB_useKey1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_useKey1ActionPerformed
        activateKey(jCB_useKey1, jTF_key1, pwdLabel);
    }//GEN-LAST:event_jCB_useKey1ActionPerformed

    private void jCB_useKey2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCB_useKey2ActionPerformed
        activateKey(jCB_useKey2, jTF_key2, pwdBFELabel);
    }//GEN-LAST:event_jCB_useKey2ActionPerformed

    private void activateKey(JCheckBox checkbox, JTextField field, JLabel label)
    {
        if(checkbox.isSelected())
        {
            label.setText("Passphrase");
            field.setEnabled(true);
        }
        else
        {
            label.setText("Password");
            field.setEnabled(false);
        }
    }
    private void mySimulationsLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mySimulationsLabelMouseClicked
        mf.printGEN("--- HINT ------------------------------------------", Color.BLACK, false, true);
        mf.printGEN("You have to start your path with ./ and give a folder name where"
            + " to create the abinit filetree\n", Color.RED, false, true);
        mf.printGEN("Example: ./MySimulations\n", new Color(0, 100, 0), false, true);
        mf.printGEN("The filetree will be created in your local computer and at the"
            + " Abinit server side when using remote Abinit servers", Color.DARK_GRAY, false, true);
        mf.printGEN("---------------------------------------------------", Color.BLACK, false, true);
    }//GEN-LAST:event_mySimulationsLabelMouseClicked

    private void abinitPathPathLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_abinitPathPathLabelMouseClicked
        mf.printGEN("--- HINT ------------------------------------------", Color.BLACK, false, true);
        mf.printGEN("Remote path where to find the abinit program\n", Color.RED, false, true);
        mf.printGEN("Example: /Users/me/Abinit6.7.2/bin\n", new Color(0, 100, 0), false, true);
        mf.printGEN("---------------------------------------------------", Color.BLACK, false, true);
    }//GEN-LAST:event_abinitPathPathLabelMouseClicked

    private void machineListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_machineListValueChanged
        Machine mach = (Machine)(machineList.getSelectedValue());
        fillFieldsFromMachine(mach);
    }//GEN-LAST:event_machineListValueChanged

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        saveMachineFromFields();
        refresh();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        createNewMachine();
        refresh();
    }//GEN-LAST:event_newButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        deleteCurrentMachine();
        refresh();
    }//GEN-LAST:event_deleteButtonActionPerformed
    
    private void deleteCurrentMachine()
    {
        Machine mach = (Machine)machineList.getSelectedValue();
        
        if(mach != null)
        {
            mf.getMachineDatabase().removeMachine(mach);
            mf.refreshMachines();
        }
        else
        {
            mf.printERR("Please select a machine first");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel abinitPathPathLabel;
    private javax.swing.JTextField abinitPathTextField;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField gatewayHostTextField;
    private javax.swing.JPanel gatewayLoginPanel;
    private javax.swing.JTextField gatewayLoginTextField;
    private javax.swing.JPasswordField gatewayPasswordField;
    private javax.swing.JLabel hostBFELabel;
    private javax.swing.JLabel hostLabel;
    private javax.swing.JTextField hostTextField;
    private javax.swing.JPanel infosPanel;
    private javax.swing.JCheckBox jCB_useKey1;
    private javax.swing.JCheckBox jCB_useKey2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTF_key1;
    private javax.swing.JTextField jTF_key2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton localAbinitRadioButton;
    private javax.swing.JLabel loginBFELabel;
    private javax.swing.JLabel loginLabel;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JTextField loginTextField;
    private javax.swing.JList machineList;
    private javax.swing.JLabel mySimulationsLabel;
    private javax.swing.JTextField mySimulationsTextField;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton newButton;
    private javax.swing.JLabel pwdBFELabel;
    private javax.swing.JLabel pwdLabel;
    private javax.swing.JPasswordField pwdPasswordField;
    private javax.swing.JRadioButton remoteAbinitRadioButton;
    private javax.swing.JRadioButton remoteGatewayRadioButton;
    private javax.swing.JButton saveButton;
    private projects.SubmissionScriptPanel submissionScriptPanel1;
    private javax.swing.JLabel whereIsAbinitLabel;
    private javax.swing.ButtonGroup whereIsAbinitbuttonGroup;
    // End of variables declaration//GEN-END:variables
}
