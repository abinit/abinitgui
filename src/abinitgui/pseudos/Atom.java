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

package abinitgui.pseudos;

public class Atom {
    private AbinitPseudo pseudo;

    public Atom() {
    }

    private String symbol = null;
    private String name = null;
    private int znucl = 0;
    private int typat = 0;
    private String PSPFileName = null;
    private String PSPPath = null;
    private String PSPType = null;

    public void setBySymbol(String symbol) {
        this.symbol = symbol;
        for (int i = 0; i < elements.length; i++) {
            if (((String) elements[i][0]).equals(symbol)) {
                this.name = (String) elements[i][1];
                this.znucl = (Integer) elements[i][2];
            }
        }
    }

    public void setByZNucl(int znucl) {
        this.znucl = znucl;
        for (int i = 0; i < elements.length; i++) {
            if (((Integer) elements[i][2]).equals(znucl)) {
                this.name = (String) elements[i][1];
                this.symbol = (String) elements[i][0];
            }
        }
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setName(String atom) {
        this.name = atom;
    }

    public String getName() {
        return name;
    }

    public void setZnucl(int znucl) {
        this.znucl = znucl;
    }

    public int getZnucl() {
        return znucl;
    }

    public void setTypat(int typat) {
        this.typat = typat;
    }

    public int getTypat() {
        return typat;
    }

    public void setPSPFileName(String PSPFileName) {
        this.PSPFileName = PSPFileName;
    }

    public String getPSPFileName() {
        return PSPFileName;
    }

    public void setPSPPath(String PSPPath) {
        this.PSPPath = PSPPath;
    }

    public String getPSPPath() {
        return PSPPath;
    }

    public void setPSPType(String PSPType) {
        this.PSPType = PSPType;
    }

    public String getPSPType() {
        return PSPType;
    }

    public static int getZnuclBySymbol(String symbol) {
        for (int i = 0; i < elements.length; i++) {
            if (((String) elements[i][0]).equals(symbol)) {
                return (Integer) elements[i][2];
            }
        }
        return 0;
    }
    
    public static String getSymbolByZnucl(int znucl) {
        for (int i = 0; i < elements.length; i++) {
            if ((Integer)elements[i][2] == znucl) {
                return (String) elements[i][0];
            }
        }
        return "";
    }

    public static Object[][] getAtomsBD() {
        return elements;
    }

    @Override
    public String toString() {
        return getSymbol();
    }
    private final static Object[][] elements = new Object[][]{
        {"H", "Hydrogen", new Integer(1)},
        {"He", "Hellium", new Integer(2)},
        {"Li", "", new Integer(3)},
        {"Be", "", new Integer(4)},
        {"B", "", new Integer(5)},
        {"C", "", new Integer(6)},
        {"N", "", new Integer(7)},
        {"O", "", new Integer(8)},
        {"F", "", new Integer(9)},
        {"Ne", "", new Integer(10)},
        {"Na", "", new Integer(11)},
        {"Mg", "", new Integer(12)},
        {"Al", "", new Integer(13)},
        {"Si", "", new Integer(14)},
        {"P", "", new Integer(15)},
        {"S", "", new Integer(16)},
        {"Cl", "", new Integer(17)},
        {"Ar", "", new Integer(18)},
        {"K", "", new Integer(19)},
        {"Ca", "", new Integer(20)},
        {"Sc", "", new Integer(21)},
        {"Ti", "", new Integer(22)},
        {"V", "", new Integer(23)},
        {"Cr", "", new Integer(24)},
        {"Mn", "", new Integer(25)},
        {"Fe", "", new Integer(26)},
        {"Co", "", new Integer(27)},
        {"Ni", "", new Integer(28)},
        {"Cu", "", new Integer(29)},
        {"Zn", "", new Integer(30)},
        {"Ga", "", new Integer(31)},
        {"Ge", "", new Integer(32)},
        {"As", "", new Integer(33)},
        {"Se", "", new Integer(34)},
        {"Br", "", new Integer(35)},
        {"Kr", "", new Integer(36)},
        {"Rb", "", new Integer(37)},
        {"Sr", "", new Integer(38)},
        {"Y", "", new Integer(39)},
        {"Zr", "", new Integer(40)},
        {"Nb", "", new Integer(41)},
        {"Mo", "", new Integer(42)},
        {"Tc", "", new Integer(43)},
        {"Ru", "", new Integer(44)},
        {"Rh", "", new Integer(45)},
        {"Pd", "", new Integer(46)},
        {"Ag", "", new Integer(47)},
        {"Cd", "", new Integer(48)},
        {"In", "", new Integer(49)},
        {"Sn", "", new Integer(50)},
        {"Sb", "", new Integer(51)},
        {"Te", "", new Integer(52)},
        {"I", "", new Integer(53)},
        {"Xe", "", new Integer(54)},
        {"Cs", "", new Integer(55)},
        {"Ba", "", new Integer(56)},
        {"La", "", new Integer(57)},
        {"Ce", "", new Integer(58)},
        {"Pr", "", new Integer(59)},
        {"Nd", "", new Integer(60)},
        {"Pm", "", new Integer(61)},
        {"Sm", "", new Integer(62)},
        {"Eu", "", new Integer(63)},
        {"Gd", "", new Integer(64)},
        {"Tb", "", new Integer(65)},
        {"Dy", "", new Integer(66)},
        {"Ho", "", new Integer(67)},
        {"Er", "", new Integer(68)},
        {"Tm", "", new Integer(69)},
        {"Yb", "", new Integer(70)},
        {"Lu", "", new Integer(71)},
        {"Hf", "", new Integer(72)},
        {"Ta", "", new Integer(73)},
        {"W", "", new Integer(74)},
        {"Re", "", new Integer(75)},
        {"Os", "", new Integer(76)},
        {"Ir", "", new Integer(77)},
        {"Pt", "", new Integer(78)},
        {"Au", "", new Integer(79)},
        {"Hg", "", new Integer(80)},
        {"Tl", "", new Integer(81)},
        {"Pb", "", new Integer(82)},
        {"Bi", "", new Integer(83)},
        {"Po", "", new Integer(84)},
        {"At", "", new Integer(85)},
        {"Rn", "", new Integer(86)},
        {"Fr", "", new Integer(87)},
        {"Ra", "", new Integer(88)},
        {"Ac", "", new Integer(89)},
        {"Th", "", new Integer(90)},
        {"Pa", "", new Integer(91)},
        {"U", "", new Integer(92)},
        {"Np", "", new Integer(93)},
        {"Pu", "", new Integer(94)},
        {"Am", "", new Integer(95)},
        {"Cm", "", new Integer(96)},
        {"Bk", "", new Integer(97)},
        {"Cf", "", new Integer(98)},
        {"Es", "", new Integer(99)},
        {"Fm", "", new Integer(100)},
        {"Md", "", new Integer(101)},
        {"No", "", new Integer(102)},
        {"Lr", "", new Integer(103)},
        //------------------------------------
        /*{"Unq", "", new Integer(104)},
        {"Unp", "", new Integer(105)},
        {"Unh", "", new Integer(106)},
        {"Uns", "", new Integer(107)},
        {"Uno", "", new Integer(108)},
        {"Une", "", new Integer(109)},
        {"Uun", "", new Integer(110)},
        {"Uuu", "", new Integer(111)},
        {"Uub", "", new Integer(112)},*/
        //------------------------------------
        {"Rf", "", new Integer(104)},
        {"Db", "", new Integer(105)},
        {"Sg", "", new Integer(106)},
        {"Bh", "", new Integer(107)},
        {"Hs", "", new Integer(108)},
        {"Mt", "", new Integer(109)},
        {"Ds", "", new Integer(110)},
        {"Rg", "", new Integer(111)},
        {"Cn", "", new Integer(112)},
        //------------------------------------
        {"Uut", "", new Integer(113)},
        {"Uuq", "", new Integer(114)},
        {"Uup", "", new Integer(115)},
        {"Uuh", "", new Integer(116)},
        {"Uus", "", new Integer(117)},
        {"Uuo", "", new Integer(118)}
    };

    public void setPseudo(AbinitPseudo pseudo) {
        this.pseudo = pseudo;
    }
}
