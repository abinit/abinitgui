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

package abinitgui.pseudos;

import java.awt.event.ActionListener;
import java.util.Enumeration;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MendTabDialog extends JDialog {

    public MendTabDialog(JFrame parent, boolean modal, ActionListener al) {
        super(parent, modal);
        SwingUtilities.updateComponentTreeUI(this);
        initComponents();
        jB_H.addActionListener(al);
        jB_Sr.addActionListener(al);
        jB_Cm.addActionListener(al);
        jB_Am.addActionListener(al);
        jB_Eu.addActionListener(al);
        jB_Np.addActionListener(al);
        jB_Tb.addActionListener(al);
        jB_Cf.addActionListener(al);
        jB_Dy.addActionListener(al);
        jB_Gd.addActionListener(al);
        jB_Bk.addActionListener(al);
        jB_Ca.addActionListener(al);
        jB_Mg.addActionListener(al);
        jB_Be.addActionListener(al);
        jB_Sc.addActionListener(al);
        jB_Y.addActionListener(al);
        jB_Rf.addActionListener(al);
        jB_Zr.addActionListener(al);
        jB_Li.addActionListener(al);
        jB_Hf.addActionListener(al);
        jB_Ti.addActionListener(al);
        jB_Db.addActionListener(al);
        jB_Nb.addActionListener(al);
        jB_Ta.addActionListener(al);
        jB_V.addActionListener(al);
        jB_Sg.addActionListener(al);
        jB_Mo.addActionListener(al);
        jB_W.addActionListener(al);
        jB_Cr.addActionListener(al);
        jB_Na.addActionListener(al);
        jB_Tc.addActionListener(al);
        jB_Re.addActionListener(al);
        jB_Mn.addActionListener(al);
        jB_Fe.addActionListener(al);
        jB_Os.addActionListener(al);
        jB_Ru.addActionListener(al);
        jB_Co.addActionListener(al);
        jB_Ir.addActionListener(al);
        jB_Rh.addActionListener(al);
        jB_Ni.addActionListener(al);
        jB_K.addActionListener(al);
        jB_Pt.addActionListener(al);
        jB_Pd.addActionListener(al);
        jB_Cu.addActionListener(al);
        jB_Au.addActionListener(al);
        jB_Ag.addActionListener(al);
        jB_Zn.addActionListener(al);
        jB_Hg.addActionListener(al);
        jB_Cd.addActionListener(al);
        jB_B.addActionListener(al);
        jB_Ga.addActionListener(al);
        jB_Rb.addActionListener(al);
        jB_Al.addActionListener(al);
        jB_In.addActionListener(al);
        jB_Tl.addActionListener(al);
        jB_Sn.addActionListener(al);
        jB_Pb.addActionListener(al);
        jB_C.addActionListener(al);
        jB_Ge.addActionListener(al);
        jB_Si.addActionListener(al);
        jB_P.addActionListener(al);
        jB_As.addActionListener(al);
        jB_Cs.addActionListener(al);
        jB_N.addActionListener(al);
        jB_Sb.addActionListener(al);
        jB_Bi.addActionListener(al);
        jB_O.addActionListener(al);
        jB_Se.addActionListener(al);
        jB_S.addActionListener(al);
        jB_Po.addActionListener(al);
        jB_Te.addActionListener(al);
        jB_I.addActionListener(al);
        jB_At.addActionListener(al);
        jB_Fr.addActionListener(al);
        jB_F.addActionListener(al);
        jB_Br.addActionListener(al);
        jB_Cl.addActionListener(al);
        jB_La.addActionListener(al);
        jB_Rn.addActionListener(al);
        jB_Xe.addActionListener(al);
        jB_Kr.addActionListener(al);
        jB_Ar.addActionListener(al);
        jB_Ne.addActionListener(al);
        jB_He.addActionListener(al);
        jB_Ra.addActionListener(al);
        jB_Ac.addActionListener(al);
        jB_Ce.addActionListener(al);
        jB_Th.addActionListener(al);
        jB_Er.addActionListener(al);
        jB_Fm.addActionListener(al);
        jB_Ho.addActionListener(al);
        jB_Es.addActionListener(al);
        jB_Nd.addActionListener(al);
        jB_U.addActionListener(al);
        jB_Pr.addActionListener(al);
        jB_Ba.addActionListener(al);
        jB_Pa.addActionListener(al);
        jB_Yb.addActionListener(al);
        jB_Pm.addActionListener(al);
        jB_No.addActionListener(al);
        jB_Lr.addActionListener(al);
        jB_Lu.addActionListener(al);
        jB_Md.addActionListener(al);
        jB_Tm.addActionListener(al);
        jB_Sm.addActionListener(al);
        jB_Pu.addActionListener(al);

        jB_Bh.addActionListener(al);
        jB_Hs.addActionListener(al);

        jB_Uus.addActionListener(al);
        //jB_Uus.setVisible(false);
        jB_Uuo.addActionListener(al);
        //jB_Uuo.setVisible(false);

        jB_Mt.addActionListener(al);

        jB_Ds.addActionListener(al);
        //jB_Ds.setVisible(false);
        jB_Rg.addActionListener(al);
        //jB_Rg.setVisible(false);
        jB_Cn.addActionListener(al);
        //jB_Cn.setVisible(false);
        jB_Uut.addActionListener(al);
        //jB_Uut.setVisible(false);
        jB_Uuq.addActionListener(al);
        //jB_Uuq.setVisible(false);
        jB_Uup.addActionListener(al);
        //jB_Uup.setVisible(false);
        jB_Uuh.addActionListener(al);
        //jB_Uuh.setVisible(false);

        userPSPButton.addActionListener(al);
        GGA_FHI_CheckBox.addActionListener(al);
        GGA_HGH_CheckBox.addActionListener(al);
        LDA_Core_CheckBox.addActionListener(al);
        LDA_FHI_CheckBox.addActionListener(al);
        LDA_GTH_CheckBox.addActionListener(al);
        LDA_HGH_CheckBox.addActionListener(al);
        LDA_TM_CheckBox.addActionListener(al);
        LDA_Teter_CheckBox.addActionListener(al);

        /*Object[][] atomsDB = Atom.getAtomsBD();
        for (int i = 0; i < atomsDB.length; i++) {
            Border b = new EmptyBorder(3,3,3,3);
            getMendButton((String) atomsDB[i][0]).setBorder(b);
        }*/
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    //@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        jB_H = new javax.swing.JButton();
        jB_Li = new javax.swing.JButton();
        jB_Na = new javax.swing.JButton();
        jB_K = new javax.swing.JButton();
        jB_Rb = new javax.swing.JButton();
        jB_Cs = new javax.swing.JButton();
        jB_Fr = new javax.swing.JButton();
        jB_Ra = new javax.swing.JButton();
        jB_Ba = new javax.swing.JButton();
        jB_Sr = new javax.swing.JButton();
        jB_Ca = new javax.swing.JButton();
        jB_Mg = new javax.swing.JButton();
        jB_Be = new javax.swing.JButton();
        jB_Sc = new javax.swing.JButton();
        jB_Y = new javax.swing.JButton();
        jB_Rf = new javax.swing.JButton();
        jB_Zr = new javax.swing.JButton();
        jB_Hf = new javax.swing.JButton();
        jB_Ti = new javax.swing.JButton();
        jB_Db = new javax.swing.JButton();
        jB_Nb = new javax.swing.JButton();
        jB_Ta = new javax.swing.JButton();
        jB_V = new javax.swing.JButton();
        jB_Sg = new javax.swing.JButton();
        jB_Mo = new javax.swing.JButton();
        jB_W = new javax.swing.JButton();
        jB_Cr = new javax.swing.JButton();
        jB_Tc = new javax.swing.JButton();
        jB_Re = new javax.swing.JButton();
        jB_Mn = new javax.swing.JButton();
        jB_Fe = new javax.swing.JButton();
        jB_Os = new javax.swing.JButton();
        jB_Ru = new javax.swing.JButton();
        jB_Co = new javax.swing.JButton();
        jB_Ir = new javax.swing.JButton();
        jB_Rh = new javax.swing.JButton();
        jB_Ni = new javax.swing.JButton();
        jB_Pt = new javax.swing.JButton();
        jB_Pd = new javax.swing.JButton();
        jB_Cu = new javax.swing.JButton();
        jB_Au = new javax.swing.JButton();
        jB_Ag = new javax.swing.JButton();
        jB_Zn = new javax.swing.JButton();
        jB_Hg = new javax.swing.JButton();
        jB_Cd = new javax.swing.JButton();
        jB_B = new javax.swing.JButton();
        jB_Ga = new javax.swing.JButton();
        jB_Al = new javax.swing.JButton();
        jB_In = new javax.swing.JButton();
        jB_Tl = new javax.swing.JButton();
        jB_Sn = new javax.swing.JButton();
        jB_Pb = new javax.swing.JButton();
        jB_C = new javax.swing.JButton();
        jB_Ge = new javax.swing.JButton();
        jB_Si = new javax.swing.JButton();
        jB_P = new javax.swing.JButton();
        jB_As = new javax.swing.JButton();
        jB_N = new javax.swing.JButton();
        jB_Sb = new javax.swing.JButton();
        jB_Bi = new javax.swing.JButton();
        jB_O = new javax.swing.JButton();
        jB_Se = new javax.swing.JButton();
        jB_S = new javax.swing.JButton();
        jB_Po = new javax.swing.JButton();
        jB_Te = new javax.swing.JButton();
        jB_I = new javax.swing.JButton();
        jB_At = new javax.swing.JButton();
        jB_F = new javax.swing.JButton();
        jB_Br = new javax.swing.JButton();
        jB_Cl = new javax.swing.JButton();
        jB_Rn = new javax.swing.JButton();
        jB_Xe = new javax.swing.JButton();
        jB_Kr = new javax.swing.JButton();
        jB_Ar = new javax.swing.JButton();
        jB_Ne = new javax.swing.JButton();
        jB_He = new javax.swing.JButton();
        jB_La = new javax.swing.JButton();
        jB_Ac = new javax.swing.JButton();
        jB_Ce = new javax.swing.JButton();
        jB_Th = new javax.swing.JButton();
        jB_Er = new javax.swing.JButton();
        jB_Fm = new javax.swing.JButton();
        jB_Ho = new javax.swing.JButton();
        jB_Es = new javax.swing.JButton();
        jB_Nd = new javax.swing.JButton();
        jB_U = new javax.swing.JButton();
        jB_Pr = new javax.swing.JButton();
        jB_Pa = new javax.swing.JButton();
        jB_Yb = new javax.swing.JButton();
        jB_Pm = new javax.swing.JButton();
        jB_No = new javax.swing.JButton();
        jB_Lr = new javax.swing.JButton();
        jB_Lu = new javax.swing.JButton();
        jB_Md = new javax.swing.JButton();
        jB_Tm = new javax.swing.JButton();
        jB_Sm = new javax.swing.JButton();
        jB_Pu = new javax.swing.JButton();
        jB_Cm = new javax.swing.JButton();
        jB_Am = new javax.swing.JButton();
        jB_Eu = new javax.swing.JButton();
        jB_Np = new javax.swing.JButton();
        jB_Tb = new javax.swing.JButton();
        jB_Cf = new javax.swing.JButton();
        jB_Dy = new javax.swing.JButton();
        jB_Gd = new javax.swing.JButton();
        jB_Bk = new javax.swing.JButton();
        pspTypePanel = new javax.swing.JPanel();
        LDA_FHI_CheckBox = new javax.swing.JCheckBox();
        LDA_Core_CheckBox = new javax.swing.JCheckBox();
        LDA_TM_CheckBox = new javax.swing.JCheckBox();
        LDA_Teter_CheckBox = new javax.swing.JCheckBox();
        LDA_HGH_CheckBox = new javax.swing.JCheckBox();
        GGA_FHI_CheckBox = new javax.swing.JCheckBox();
        GGA_HGH_CheckBox = new javax.swing.JCheckBox();
        LDA_GTH_CheckBox = new javax.swing.JCheckBox();
        userPSPButton = new javax.swing.JButton();
        jB_Bh = new javax.swing.JButton();
        jB_Hs = new javax.swing.JButton();
        jB_Mt = new javax.swing.JButton();
        jB_Ds = new javax.swing.JButton();
        jB_Rg = new javax.swing.JButton();
        jB_Cn = new javax.swing.JButton();
        jB_Uut = new javax.swing.JButton();
        jB_Uuq = new javax.swing.JButton();
        jB_Uup = new javax.swing.JButton();
        jB_Uuh = new javax.swing.JButton();
        jB_Uus = new javax.swing.JButton();
        jB_Uuo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jB_H.setText("H");
        jB_H.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Li.setText("Li");
        jB_Li.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Na.setText("Na");
        jB_Na.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_K.setText("K");
        jB_K.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Rb.setText("Rb");
        jB_Rb.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Cs.setText("Cs");
        jB_Cs.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Fr.setText("Fr");
        jB_Fr.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Ra.setText("Ra");
        jB_Ra.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Ba.setText("Ba");
        jB_Ba.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Sr.setText("Sr");
        jB_Sr.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Ca.setText("Ca");
        jB_Ca.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Mg.setText("Mg");
        jB_Mg.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Be.setText("Be");
        jB_Be.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Sc.setText("Sc");
        jB_Sc.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Y.setText("Y");
        jB_Y.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Rf.setText("Rf");
        jB_Rf.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Zr.setText("Zr");
        jB_Zr.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Hf.setText("Hf");
        jB_Hf.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Ti.setText("Ti");
        jB_Ti.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Db.setText("Db");
        jB_Db.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Nb.setText("Nb");
        jB_Nb.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Ta.setText("Ta");
        jB_Ta.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_V.setText("V");
        jB_V.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Sg.setText("Sg");
        jB_Sg.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Mo.setText("Mo");
        jB_Mo.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_W.setText("W");
        jB_W.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Cr.setText("Cr");
        jB_Cr.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Tc.setText("Tc");
        jB_Tc.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Re.setText("Re");
        jB_Re.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Mn.setText("Mn");
        jB_Mn.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Fe.setText("Fe");
        jB_Fe.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Os.setText("Os");
        jB_Os.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Ru.setText("Ru");
        jB_Ru.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Co.setText("Co");
        jB_Co.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Ir.setText("Ir");
        jB_Ir.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Rh.setText("Rh");
        jB_Rh.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Ni.setText("Ni");
        jB_Ni.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Pt.setText("Pt");
        jB_Pt.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Pd.setText("Pd");
        jB_Pd.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Cu.setText("Cu");
        jB_Cu.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Au.setText("Au");
        jB_Au.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Ag.setText("Ag");
        jB_Ag.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Zn.setText("Zn");
        jB_Zn.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Hg.setText("Hg");
        jB_Hg.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Cd.setText("Cd");
        jB_Cd.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_B.setText("B");
        jB_B.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Ga.setText("Ga");
        jB_Ga.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Al.setText("Al");
        jB_Al.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_In.setText("In");
        jB_In.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Tl.setText("Tl");
        jB_Tl.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Sn.setText("Sn");
        jB_Sn.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Pb.setText("Pb");
        jB_Pb.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_C.setText("C");
        jB_C.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Ge.setText("Ge");
        jB_Ge.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Si.setText("Si");
        jB_Si.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_P.setText("P");
        jB_P.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_As.setText("As");
        jB_As.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_N.setText("N");
        jB_N.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Sb.setText("Sb");
        jB_Sb.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Bi.setText("Bi");
        jB_Bi.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_O.setText("O");
        jB_O.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Se.setText("Se");
        jB_Se.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_S.setText("S");
        jB_S.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Po.setText("Po");
        jB_Po.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Te.setText("Te");
        jB_Te.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_I.setText("I");
        jB_I.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_At.setText("At");
        jB_At.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_F.setText("F");
        jB_F.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Br.setText("Br");
        jB_Br.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Cl.setText("Cl");
        jB_Cl.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Rn.setText("Rn");
        jB_Rn.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Xe.setText("Xe");
        jB_Xe.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Kr.setText("Kr");
        jB_Kr.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Ar.setText("Ar");
        jB_Ar.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Ne.setText("Ne");
        jB_Ne.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_He.setText("He");
        jB_He.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_La.setText("La");
        jB_La.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Ac.setText("Ac");
        jB_Ac.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Ce.setText("Ce");
        jB_Ce.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Th.setText("Th");
        jB_Th.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Er.setText("Er");
        jB_Er.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Fm.setText("Fm");
        jB_Fm.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Ho.setText("Ho");
        jB_Ho.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Es.setText("Es");
        jB_Es.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Nd.setText("Nd");
        jB_Nd.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_U.setText("U");
        jB_U.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Pr.setText("Pr");
        jB_Pr.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Pa.setText("Pa");
        jB_Pa.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Yb.setText("Yb");
        jB_Yb.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Pm.setText("Pm");
        jB_Pm.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_No.setText("No");
        jB_No.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Lr.setText("Lr");
        jB_Lr.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Lu.setText("Lu");
        jB_Lu.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Md.setText("Md");
        jB_Md.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Tm.setText("Tm");
        jB_Tm.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Sm.setText("Sm");
        jB_Sm.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Pu.setText("Pu");
        jB_Pu.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Cm.setText("Cm");
        jB_Cm.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Am.setText("Am");
        jB_Am.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Eu.setText("Eu");
        jB_Eu.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Np.setText("Np");
        jB_Np.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Tb.setText("Tb");
        jB_Tb.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Cf.setText("Cf");
        jB_Cf.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Dy.setText("Dy");
        jB_Dy.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Gd.setText("Gd");
        jB_Gd.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Bk.setText("Bk");
        jB_Bk.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        pspTypePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        buttonGroup.add(LDA_FHI_CheckBox);
        LDA_FHI_CheckBox.setText("LDA FHI");
        LDA_FHI_CheckBox.setActionCommand("LDA_FHI");
        LDA_FHI_CheckBox.setName("LDA_FHI"); // NOI18N

        buttonGroup.add(LDA_Core_CheckBox);
        LDA_Core_CheckBox.setText("LDA Core");
        LDA_Core_CheckBox.setActionCommand("LDA_Core");
        LDA_Core_CheckBox.setName("LDA_Core"); // NOI18N

        buttonGroup.add(LDA_TM_CheckBox);
        LDA_TM_CheckBox.setSelected(true);
        LDA_TM_CheckBox.setText("LDA TM");
        LDA_TM_CheckBox.setActionCommand("LDA_TM");
        LDA_TM_CheckBox.setName("LDA_TM"); // NOI18N
        LDA_TM_CheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LDA_TM_CheckBoxActionPerformed(evt);
            }
        });

        buttonGroup.add(LDA_Teter_CheckBox);
        LDA_Teter_CheckBox.setText("LDA Teter");
        LDA_Teter_CheckBox.setActionCommand("LDA_Teter");
        LDA_Teter_CheckBox.setEnabled(false);
        LDA_Teter_CheckBox.setName("LDA_Teter"); // NOI18N

        buttonGroup.add(LDA_HGH_CheckBox);
        LDA_HGH_CheckBox.setText("LDA HGH");
        LDA_HGH_CheckBox.setActionCommand("LDA_HGH");
        LDA_HGH_CheckBox.setEnabled(false);
        LDA_HGH_CheckBox.setName("LDA_HGH"); // NOI18N

        buttonGroup.add(GGA_FHI_CheckBox);
        GGA_FHI_CheckBox.setText("GGA FHI");
        GGA_FHI_CheckBox.setActionCommand("GGA_FHI");
        GGA_FHI_CheckBox.setName("GGA_FHI"); // NOI18N

        buttonGroup.add(GGA_HGH_CheckBox);
        GGA_HGH_CheckBox.setText("GGA HGH");
        GGA_HGH_CheckBox.setActionCommand("GGA_HGH");
        GGA_HGH_CheckBox.setName("GGA_HGH"); // NOI18N

        buttonGroup.add(LDA_GTH_CheckBox);
        LDA_GTH_CheckBox.setText("LDA GTH");
        LDA_GTH_CheckBox.setActionCommand("LDA_GTH");
        LDA_GTH_CheckBox.setName("LDA_GTH"); // NOI18N

        javax.swing.GroupLayout pspTypePanelLayout = new javax.swing.GroupLayout(pspTypePanel);
        pspTypePanel.setLayout(pspTypePanelLayout);
        pspTypePanelLayout.setHorizontalGroup(
            pspTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pspTypePanelLayout.createSequentialGroup()
                .addGroup(pspTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LDA_TM_CheckBox)
                    .addComponent(LDA_GTH_CheckBox))
                .addGap(18, 18, 18)
                .addGroup(pspTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LDA_FHI_CheckBox)
                    .addComponent(GGA_FHI_CheckBox))
                .addGap(18, 18, 18)
                .addGroup(pspTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(GGA_HGH_CheckBox)
                    .addComponent(LDA_Core_CheckBox))
                .addGap(18, 18, 18)
                .addGroup(pspTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LDA_HGH_CheckBox)
                    .addComponent(LDA_Teter_CheckBox)))
        );
        pspTypePanelLayout.setVerticalGroup(
            pspTypePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pspTypePanelLayout.createSequentialGroup()
                .addComponent(LDA_FHI_CheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GGA_FHI_CheckBox))
            .addGroup(pspTypePanelLayout.createSequentialGroup()
                .addComponent(LDA_TM_CheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LDA_GTH_CheckBox))
            .addGroup(pspTypePanelLayout.createSequentialGroup()
                .addComponent(LDA_Core_CheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GGA_HGH_CheckBox))
            .addGroup(pspTypePanelLayout.createSequentialGroup()
                .addComponent(LDA_Teter_CheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LDA_HGH_CheckBox))
        );

        LDA_FHI_CheckBox.getAccessibleContext().setAccessibleName("LDA_FHI");
        LDA_Core_CheckBox.getAccessibleContext().setAccessibleName("LDA_Core");
        LDA_TM_CheckBox.getAccessibleContext().setAccessibleName("LDA_TM");
        LDA_Teter_CheckBox.getAccessibleContext().setAccessibleName("LDA_Teter");
        LDA_HGH_CheckBox.getAccessibleContext().setAccessibleName("LDA_HGH");
        GGA_FHI_CheckBox.getAccessibleContext().setAccessibleName("GGA_FHI");
        GGA_HGH_CheckBox.getAccessibleContext().setAccessibleName("GGA_HGH");
        LDA_GTH_CheckBox.getAccessibleContext().setAccessibleName("LDA_GTH");

        userPSPButton.setText("User PSP");
        userPSPButton.setActionCommand("UserPSP");

        jB_Bh.setText("Bh");
        jB_Bh.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Hs.setText("Hs");
        jB_Hs.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Mt.setText("Mt");
        jB_Mt.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Ds.setText("Ds");
        jB_Ds.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Rg.setText("Rg");
        jB_Rg.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Cn.setText("Cn");
        jB_Cn.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Uut.setText("Uut");
        jB_Uut.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Uuq.setText("Uuq");
        jB_Uuq.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Uup.setText("Uup");
        jB_Uup.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Uuh.setText("Uuh");
        jB_Uuh.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Uus.setText("Uus");
        jB_Uus.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        jB_Uuo.setText("Uuo");
        jB_Uuo.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pspTypePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userPSPButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jB_Li)
                                    .addComponent(jB_Na)
                                    .addComponent(jB_K)
                                    .addComponent(jB_Rb)
                                    .addComponent(jB_Cs)
                                    .addComponent(jB_Fr))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jB_Be)
                                    .addComponent(jB_Mg)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jB_Ca)
                                            .addComponent(jB_Sr)
                                            .addComponent(jB_Ba)
                                            .addComponent(jB_Ra))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jB_Sc)
                                            .addComponent(jB_Y))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jB_Ti)
                                            .addComponent(jB_Zr)
                                            .addComponent(jB_Hf)
                                            .addComponent(jB_Rf))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jB_V)
                                            .addComponent(jB_Nb)
                                            .addComponent(jB_Ta)
                                            .addComponent(jB_Db))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jB_Cr)
                                                    .addComponent(jB_Mo)
                                                    .addComponent(jB_W))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jB_Mn)
                                                    .addComponent(jB_Tc)
                                                    .addComponent(jB_Re))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jB_Fe)
                                                    .addComponent(jB_Ru)
                                                    .addComponent(jB_Os))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jB_Co)
                                                    .addComponent(jB_Rh)
                                                    .addComponent(jB_Ir))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jB_Ni)
                                                    .addComponent(jB_Pd)
                                                    .addComponent(jB_Pt))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jB_Cu)
                                                    .addComponent(jB_Ag)
                                                    .addComponent(jB_Au))
                                                .addGap(6, 6, 6)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jB_Zn)
                                                    .addComponent(jB_Cd)
                                                    .addComponent(jB_Hg)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jB_Sg)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jB_Bh)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jB_Hs)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jB_Mt)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jB_Ds)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jB_Rg)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jB_Cn))))))
                            .addComponent(jB_H))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jB_B)
                                    .addComponent(jB_Al)
                                    .addComponent(jB_Ga)
                                    .addComponent(jB_In)
                                    .addComponent(jB_Tl))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jB_C)
                                    .addComponent(jB_Si)
                                    .addComponent(jB_Ge)
                                    .addComponent(jB_Sn)
                                    .addComponent(jB_Pb))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jB_N)
                                    .addComponent(jB_P)
                                    .addComponent(jB_As)
                                    .addComponent(jB_Sb)
                                    .addComponent(jB_Bi))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jB_O)
                                    .addComponent(jB_S)
                                    .addComponent(jB_Se)
                                    .addComponent(jB_Te)
                                    .addComponent(jB_Po))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jB_F)
                                    .addComponent(jB_Cl)
                                    .addComponent(jB_Br)
                                    .addComponent(jB_I)
                                    .addComponent(jB_At))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jB_Ne)
                                    .addComponent(jB_Ar)
                                    .addComponent(jB_Kr)
                                    .addComponent(jB_Xe)
                                    .addComponent(jB_Rn)
                                    .addComponent(jB_He)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jB_Uut)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Uuq)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Uup)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Uuh)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Uus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Uuo))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jB_La)
                            .addComponent(jB_Ac))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jB_Ce)
                            .addComponent(jB_Th))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jB_Pr)
                            .addComponent(jB_Pa))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jB_Nd)
                            .addComponent(jB_U))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jB_Pm)
                            .addComponent(jB_Np))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jB_Sm)
                            .addComponent(jB_Pu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jB_Eu)
                            .addComponent(jB_Am))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jB_Gd)
                            .addComponent(jB_Cm))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jB_Tb)
                            .addComponent(jB_Bk))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jB_Dy)
                            .addComponent(jB_Cf))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jB_Ho)
                            .addComponent(jB_Es))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jB_Er)
                            .addComponent(jB_Fm))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jB_Tm)
                            .addComponent(jB_Md))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jB_Yb)
                            .addComponent(jB_No))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jB_Lu)
                            .addComponent(jB_Lr))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jB_Ac, jB_Ag, jB_Al, jB_Am, jB_Ar, jB_As, jB_At, jB_Au, jB_B, jB_Ba, jB_Be, jB_Bh, jB_Bi, jB_Bk, jB_Br, jB_C, jB_Ca, jB_Cd, jB_Ce, jB_Cf, jB_Cl, jB_Cm, jB_Cn, jB_Co, jB_Cr, jB_Cs, jB_Cu, jB_Db, jB_Ds, jB_Dy, jB_Er, jB_Es, jB_Eu, jB_F, jB_Fe, jB_Fm, jB_Fr, jB_Ga, jB_Gd, jB_Ge, jB_H, jB_He, jB_Hf, jB_Hg, jB_Ho, jB_Hs, jB_I, jB_In, jB_Ir, jB_K, jB_Kr, jB_La, jB_Li, jB_Lr, jB_Lu, jB_Md, jB_Mg, jB_Mn, jB_Mo, jB_Mt, jB_N, jB_Na, jB_Nb, jB_Nd, jB_Ne, jB_Ni, jB_No, jB_Np, jB_O, jB_Os, jB_P, jB_Pa, jB_Pb, jB_Pd, jB_Pm, jB_Po, jB_Pr, jB_Pt, jB_Pu, jB_Ra, jB_Rb, jB_Re, jB_Rf, jB_Rg, jB_Rh, jB_Rn, jB_Ru, jB_S, jB_Sb, jB_Sc, jB_Se, jB_Sg, jB_Si, jB_Sm, jB_Sn, jB_Sr, jB_Ta, jB_Tb, jB_Tc, jB_Te, jB_Th, jB_Ti, jB_Tl, jB_Tm, jB_U, jB_Uuh, jB_Uuo, jB_Uup, jB_Uuq, jB_Uus, jB_Uut, jB_V, jB_W, jB_Xe, jB_Y, jB_Yb, jB_Zn, jB_Zr});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jB_He)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jB_C)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Si)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Ge)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Sn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Pb))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jB_B)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Al)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Ga)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_In)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Tl))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jB_N)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_P)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_As)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Sb)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Bi))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jB_O)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_S)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Se)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Te)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Po))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jB_F)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Cl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Br)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_I)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_At))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jB_Ne)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Ar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Kr)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Xe)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Rn))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jB_H)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jB_Li)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Na)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_K)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Rb)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Cs)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Fr))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jB_Be)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jB_Mg)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jB_Sc)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jB_Y))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jB_Ca)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jB_Sr)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jB_Ba)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jB_Ra))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jB_Ti)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jB_Zr)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jB_Hf)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jB_Rf))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jB_V)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jB_Nb)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jB_Ta)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jB_Db))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jB_Fe)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jB_Ru)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jB_Os))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jB_Co)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jB_Rh)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jB_Ir))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jB_Ni)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jB_Pd)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jB_Pt))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jB_Cu)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jB_Ag)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jB_Au))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jB_Zn)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jB_Cd)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jB_Hg))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jB_Cr)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jB_Mo)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jB_W))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jB_Mn)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jB_Tc)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jB_Re)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jB_Sg)
                                            .addComponent(jB_Bh)
                                            .addComponent(jB_Hs)
                                            .addComponent(jB_Mt)
                                            .addComponent(jB_Ds)
                                            .addComponent(jB_Rg)
                                            .addComponent(jB_Cn)
                                            .addComponent(jB_Uut)
                                            .addComponent(jB_Uuq)
                                            .addComponent(jB_Uup)
                                            .addComponent(jB_Uuh)
                                            .addComponent(jB_Uus)
                                            .addComponent(jB_Uuo))))))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jB_Er)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jB_Fm))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jB_Ho)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jB_Es))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jB_Tm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jB_Md))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jB_Yb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jB_No))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jB_Lu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jB_Lr))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jB_La)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jB_Ac))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jB_Ce)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jB_Th))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jB_Pr)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jB_Pa))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jB_Sm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jB_Pu))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jB_Eu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jB_Am))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jB_Gd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jB_Cm))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jB_Tb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jB_Bk))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jB_Dy)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jB_Cf))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jB_Nd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jB_U))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jB_Pm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jB_Np)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pspTypePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(userPSPButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jB_Ac, jB_Ag, jB_Al, jB_Am, jB_Ar, jB_As, jB_At, jB_Au, jB_B, jB_Ba, jB_Be, jB_Bh, jB_Bi, jB_Bk, jB_Br, jB_C, jB_Ca, jB_Cd, jB_Ce, jB_Cf, jB_Cl, jB_Cm, jB_Cn, jB_Co, jB_Cr, jB_Cs, jB_Cu, jB_Db, jB_Ds, jB_Dy, jB_Er, jB_Es, jB_Eu, jB_F, jB_Fe, jB_Fm, jB_Fr, jB_Ga, jB_Gd, jB_Ge, jB_H, jB_He, jB_Hf, jB_Hg, jB_Ho, jB_Hs, jB_I, jB_In, jB_Ir, jB_K, jB_Kr, jB_La, jB_Li, jB_Lr, jB_Lu, jB_Md, jB_Mg, jB_Mn, jB_Mo, jB_Mt, jB_N, jB_Na, jB_Nb, jB_Nd, jB_Ne, jB_Ni, jB_No, jB_Np, jB_O, jB_Os, jB_P, jB_Pa, jB_Pb, jB_Pd, jB_Pm, jB_Po, jB_Pr, jB_Pt, jB_Pu, jB_Ra, jB_Rb, jB_Re, jB_Rf, jB_Rg, jB_Rh, jB_Rn, jB_Ru, jB_S, jB_Sb, jB_Sc, jB_Se, jB_Sg, jB_Si, jB_Sm, jB_Sn, jB_Sr, jB_Ta, jB_Tb, jB_Tc, jB_Te, jB_Th, jB_Ti, jB_Tl, jB_Tm, jB_U, jB_Uuh, jB_Uuo, jB_Uup, jB_Uuq, jB_Uus, jB_Uut, jB_V, jB_W, jB_Xe, jB_Y, jB_Yb, jB_Zn, jB_Zr});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LDA_TM_CheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LDA_TM_CheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LDA_TM_CheckBoxActionPerformed

    public String getPSPSelected() {
        Enumeration buttons = buttonGroup.getElements();
        while (buttons.hasMoreElements()) {
            JCheckBox cb = (JCheckBox) buttons.nextElement();
            if (cb.isSelected()) {
                return cb.getName();
            }
        }
        return null;
    }

    public javax.swing.JButton getMendButton(String atom) {
        switch (atom) {
            case "H":
                return jB_H;
            case "Sr":
                return jB_Sr;
            case "Cm":
                return jB_Cm;
            case "Am":
                return jB_Am;
            case "Eu":
                return jB_Eu;
            case "Np":
                return jB_Np;
            case "Tb":
                return jB_Tb;
            case "Cf":
                return jB_Cf;
            case "Dy":
                return jB_Dy;
            case "Gd":
                return jB_Gd;
            case "Bk":
                return jB_Bk;
            case "Ca":
                return jB_Ca;
            case "Mg":
                return jB_Mg;
            case "Be":
                return jB_Be;
            case "Sc":
                return jB_Sc;
            case "Y":
                return jB_Y;
            case "Rf":
                return jB_Rf;
            case "Zr":
                return jB_Zr;
            case "Li":
                return jB_Li;
            case "Hf":
                return jB_Hf;
            case "Ti":
                return jB_Ti;
            case "Db":
                return jB_Db;
            case "Nb":
                return jB_Nb;
            case "Ta":
                return jB_Ta;
            case "V":
                return jB_V;
            case "Sg":
                return jB_Sg;
            case "Mo":
                return jB_Mo;
            case "W":
                return jB_W;
            case "Cr":
                return jB_Cr;
            case "Na":
                return jB_Na;
            case "Tc":
                return jB_Tc;
            case "Re":
                return jB_Re;
            case "Mn":
                return jB_Mn;
            case "Fe":
                return jB_Fe;
            case "Os":
                return jB_Os;
            case "Ru":
                return jB_Ru;
            case "Co":
                return jB_Co;
            case "Ir":
                return jB_Ir;
            case "Rh":
                return jB_Rh;
            case "Ni":
                return jB_Ni;
            case "K":
                return jB_K;
            case "Pt":
                return jB_Pt;
            case "Pd":
                return jB_Pd;
            case "Cu":
                return jB_Cu;
            case "Au":
                return jB_Au;
            case "Ag":
                return jB_Ag;
            case "Zn":
                return jB_Zn;
            case "Hg":
                return jB_Hg;
            case "Cd":
                return jB_Cd;
            case "B":
                return jB_B;
            case "Ga":
                return jB_Ga;
            case "Rb":
                return jB_Rb;
            case "Al":
                return jB_Al;
            case "In":
                return jB_In;
            case "Tl":
                return jB_Tl;
            case "Sn":
                return jB_Sn;
            case "Pb":
                return jB_Pb;
            case "C":
                return jB_C;
            case "Ge":
                return jB_Ge;
            case "Si":
                return jB_Si;
            case "P":
                return jB_P;
            case "As":
                return jB_As;
            case "Cs":
                return jB_Cs;
            case "N":
                return jB_N;
            case "Sb":
                return jB_Sb;
            case "Bi":
                return jB_Bi;
            case "O":
                return jB_O;
            case "Se":
                return jB_Se;
            case "S":
                return jB_S;
            case "Po":
                return jB_Po;
            case "Te":
                return jB_Te;
            case "I":
                return jB_I;
            case "At":
                return jB_At;
            case "Fr":
                return jB_Fr;
            case "F":
                return jB_F;
            case "Br":
                return jB_Br;
            case "Cl":
                return jB_Cl;
            case "La":
                return jB_La;
            case "Rn":
                return jB_Rn;
            case "Xe":
                return jB_Xe;
            case "Kr":
                return jB_Kr;
            case "Ar":
                return jB_Ar;
            case "Ne":
                return jB_Ne;
            case "He":
                return jB_He;
            case "Ra":
                return jB_Ra;
            case "Ac":
                return jB_Ac;
            case "Ce":
                return jB_Ce;
            case "Th":
                return jB_Th;
            case "Er":
                return jB_Er;
            case "Fm":
                return jB_Fm;
            case "Ho":
                return jB_Ho;
            case "Es":
                return jB_Es;
            case "Nd":
                return jB_Nd;
            case "U":
                return jB_U;
            case "Pr":
                return jB_Pr;
            case "Ba":
                return jB_Ba;
            case "Pa":
                return jB_Pa;
            case "Yb":
                return jB_Yb;
            case "Pm":
                return jB_Pm;
            case "No":
                return jB_No;
            case "Lr":
                return jB_Lr;
            case "Lu":
                return jB_Lu;
            case "Md":
                return jB_Md;
            case "Tm":
                return jB_Tm;
            case "Sm":
                return jB_Sm;
            case "Pu":
                return jB_Pu;
            case "Bh":
                return jB_Bh;
            case "Hs":
                return jB_Hs;
            case "Uus":
                return jB_Uus;
            case "Uuo":
                return jB_Uuo;
            case "Mt":
                return jB_Mt;
            case "Ds":
                return jB_Ds;
            case "Rg":
                return jB_Rg;
            case "Cn":
                return jB_Cn;
            case "Uut":
                return jB_Uut;
            case "Uuq":
                return jB_Uuq;
            case "Uup":
                return jB_Uup;
            case "Uuh":
                return jB_Uuh;
            default:
                return null;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox GGA_FHI_CheckBox;
    private javax.swing.JCheckBox GGA_HGH_CheckBox;
    private javax.swing.JCheckBox LDA_Core_CheckBox;
    private javax.swing.JCheckBox LDA_FHI_CheckBox;
    private javax.swing.JCheckBox LDA_GTH_CheckBox;
    private javax.swing.JCheckBox LDA_HGH_CheckBox;
    private javax.swing.JCheckBox LDA_TM_CheckBox;
    private javax.swing.JCheckBox LDA_Teter_CheckBox;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JButton jB_Ac;
    private javax.swing.JButton jB_Ag;
    private javax.swing.JButton jB_Al;
    private javax.swing.JButton jB_Am;
    private javax.swing.JButton jB_Ar;
    private javax.swing.JButton jB_As;
    private javax.swing.JButton jB_At;
    private javax.swing.JButton jB_Au;
    private javax.swing.JButton jB_B;
    private javax.swing.JButton jB_Ba;
    private javax.swing.JButton jB_Be;
    private javax.swing.JButton jB_Bh;
    private javax.swing.JButton jB_Bi;
    private javax.swing.JButton jB_Bk;
    private javax.swing.JButton jB_Br;
    private javax.swing.JButton jB_C;
    private javax.swing.JButton jB_Ca;
    private javax.swing.JButton jB_Cd;
    private javax.swing.JButton jB_Ce;
    private javax.swing.JButton jB_Cf;
    private javax.swing.JButton jB_Cl;
    private javax.swing.JButton jB_Cm;
    private javax.swing.JButton jB_Cn;
    private javax.swing.JButton jB_Co;
    private javax.swing.JButton jB_Cr;
    private javax.swing.JButton jB_Cs;
    private javax.swing.JButton jB_Cu;
    private javax.swing.JButton jB_Db;
    private javax.swing.JButton jB_Ds;
    private javax.swing.JButton jB_Dy;
    private javax.swing.JButton jB_Er;
    private javax.swing.JButton jB_Es;
    private javax.swing.JButton jB_Eu;
    private javax.swing.JButton jB_F;
    private javax.swing.JButton jB_Fe;
    private javax.swing.JButton jB_Fm;
    private javax.swing.JButton jB_Fr;
    private javax.swing.JButton jB_Ga;
    private javax.swing.JButton jB_Gd;
    private javax.swing.JButton jB_Ge;
    private javax.swing.JButton jB_H;
    private javax.swing.JButton jB_He;
    private javax.swing.JButton jB_Hf;
    private javax.swing.JButton jB_Hg;
    private javax.swing.JButton jB_Ho;
    private javax.swing.JButton jB_Hs;
    private javax.swing.JButton jB_I;
    private javax.swing.JButton jB_In;
    private javax.swing.JButton jB_Ir;
    private javax.swing.JButton jB_K;
    private javax.swing.JButton jB_Kr;
    private javax.swing.JButton jB_La;
    private javax.swing.JButton jB_Li;
    private javax.swing.JButton jB_Lr;
    private javax.swing.JButton jB_Lu;
    private javax.swing.JButton jB_Md;
    private javax.swing.JButton jB_Mg;
    private javax.swing.JButton jB_Mn;
    private javax.swing.JButton jB_Mo;
    private javax.swing.JButton jB_Mt;
    private javax.swing.JButton jB_N;
    private javax.swing.JButton jB_Na;
    private javax.swing.JButton jB_Nb;
    private javax.swing.JButton jB_Nd;
    private javax.swing.JButton jB_Ne;
    private javax.swing.JButton jB_Ni;
    private javax.swing.JButton jB_No;
    private javax.swing.JButton jB_Np;
    private javax.swing.JButton jB_O;
    private javax.swing.JButton jB_Os;
    private javax.swing.JButton jB_P;
    private javax.swing.JButton jB_Pa;
    private javax.swing.JButton jB_Pb;
    private javax.swing.JButton jB_Pd;
    private javax.swing.JButton jB_Pm;
    private javax.swing.JButton jB_Po;
    private javax.swing.JButton jB_Pr;
    private javax.swing.JButton jB_Pt;
    private javax.swing.JButton jB_Pu;
    private javax.swing.JButton jB_Ra;
    private javax.swing.JButton jB_Rb;
    private javax.swing.JButton jB_Re;
    private javax.swing.JButton jB_Rf;
    private javax.swing.JButton jB_Rg;
    private javax.swing.JButton jB_Rh;
    private javax.swing.JButton jB_Rn;
    private javax.swing.JButton jB_Ru;
    private javax.swing.JButton jB_S;
    private javax.swing.JButton jB_Sb;
    private javax.swing.JButton jB_Sc;
    private javax.swing.JButton jB_Se;
    private javax.swing.JButton jB_Sg;
    private javax.swing.JButton jB_Si;
    private javax.swing.JButton jB_Sm;
    private javax.swing.JButton jB_Sn;
    private javax.swing.JButton jB_Sr;
    private javax.swing.JButton jB_Ta;
    private javax.swing.JButton jB_Tb;
    private javax.swing.JButton jB_Tc;
    private javax.swing.JButton jB_Te;
    private javax.swing.JButton jB_Th;
    private javax.swing.JButton jB_Ti;
    private javax.swing.JButton jB_Tl;
    private javax.swing.JButton jB_Tm;
    private javax.swing.JButton jB_U;
    private javax.swing.JButton jB_Uuh;
    private javax.swing.JButton jB_Uuo;
    private javax.swing.JButton jB_Uup;
    private javax.swing.JButton jB_Uuq;
    private javax.swing.JButton jB_Uus;
    private javax.swing.JButton jB_Uut;
    private javax.swing.JButton jB_V;
    private javax.swing.JButton jB_W;
    private javax.swing.JButton jB_Xe;
    private javax.swing.JButton jB_Y;
    private javax.swing.JButton jB_Yb;
    private javax.swing.JButton jB_Zn;
    private javax.swing.JButton jB_Zr;
    private javax.swing.JPanel pspTypePanel;
    private javax.swing.JButton userPSPButton;
    // End of variables declaration//GEN-END:variables
}
