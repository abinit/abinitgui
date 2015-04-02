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

package abinitgui.core;

import java.io.*;
import org.jdom.*;
import org.jdom.output.*;

public class XMLConfigWriter {

    Element root_;
    org.jdom.Document document_;

    public XMLConfigWriter(String root) {
        root_ = new Element(root);
        document_ = new Document(root_);
    }

    public Element add2root(String elem) {
        Element elem_ = new Element(elem);
        root_.addContent(elem_);
        return elem_;
    }

    public void setAttr(Element parent, String child, String attr, String value) {
        Element elem = new Element(child);
        elem.setAttribute(new Attribute(attr, value));
        parent.addContent(elem);
    }

    public void setAttr(Element parent, String child, String[] attrs, String[] values) {
        Element elem = new Element(child);
        if (values.length != attrs.length) {
            MainFrame.printERR("class: XMLConfigWriter. method: setAttr()");
            return;
        }
        for (int i = 0; i < values.length; i++) {
            elem.setAttribute(new Attribute(attrs[i], values[i]));
        }
        parent.addContent(elem);
    }

    public void display() {
        try {
            //On utilise ici un affichage classique avec getPrettyFormat()
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(document_, System.out);
        } catch (java.io.IOException e) {
            MainFrame.printERR("class: XMLConfigWriter. method: display(): " + e);
        }
    }

    public void save2file(String fichier) {
        try {
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            // Il suffitde crÃÂ©er une instance de FileOutputStream avec en
            // argument le nom du fichier pour effectuer la sÃÂ©rialisation.
            sortie.output(document_, new FileOutputStream(fichier));
        } catch (java.io.IOException e) {
            MainFrame.printERR("class: XMLConfigWriter. method: save2file(): " + e);
        }
    }
}
