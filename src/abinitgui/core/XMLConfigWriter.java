/*
 Copyright (c) 2009-2014 Flavio Miguel ABREU ARAUJO (flavio.abreuaraujo@uclouvain.be)
                         Yannick GILLET (yannick.gillet@uclouvain.be)

Universit� catholique de Louvain, Louvain-la-Neuve, Belgium
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
            // Il suffitde créer une instance de FileOutputStream avec en
            // argument le nom du fichier pour effectuer la sérialisation.
            sortie.output(document_, new FileOutputStream(fichier));
        } catch (java.io.IOException e) {
            MainFrame.printERR("class: XMLConfigWriter. method: save2file(): " + e);
        }
    }
}
