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

import java.io.File;
import java.io.IOException;
import org.jdom.*;
import org.jdom.input.*;

public class XMLConfigReader {

    org.jdom.Document document;
    Element root;

    public XMLConfigReader(String file2read) {
        SAXBuilder sxb = new SAXBuilder();
        try {
            document = sxb.build(new File(file2read));
            root = document.getRootElement();
        } catch (JDOMException | IOException e) {
            MainFrame.printERR(e.getMessage());
        }
    }

    public Element getRoot() {
        return root;
    }
}
