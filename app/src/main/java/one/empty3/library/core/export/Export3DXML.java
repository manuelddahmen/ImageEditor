/*
 *  This file is part of Empty3.
 *
 *     Empty3 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Empty3 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
 */

/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

/*__
 * *
 * Global license : * Microsoft Public Licence
 * <p>
 * author Manuel Dahmen _manuel.dahmen@gmx.com_
 * <p>
 * *
 */
package one.empty3.library.core.export;
/*
import one.empty3.library.*;
import com.sun.org.apache.xml.internal.utils.DOMBuilder;
import org.jdom.Attribute;
import org.jdom.Comment;
import org.jdom.Element;
import org.jdom.Text;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
public class Export3DXML {

    public DOMBuilder domBuilder;

    public static void main(String[] args) {
        Export3DXML e = new Export3DXML();
        try {
            e.save(new File("EMPTYCANVAS.3DXML"), new Scene(), false);
        } catch (IOException ex) {
            Logger.getLogger(Export3DXML.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void save(File file, Scene scene, boolean overwrite) throws IOException {
        Element scnXml = new Element("XML3D");
        scnXml.setAttribute(new Attribute("version", "4.3"));
        scnXml.setAttribute(new Attribute("namespace", "http://www.3ds.com/xsd/3DXML"));
        scnXml.addContent(new Comment("Not implemented yet"));
        Element RepresentationLinkType = new Element("RepresentationLinkType");
        RepresentationLinkType.addContent(new Text("urn:3DXML:Emptycanvas.simple_exemple001"));
        scnXml.addContent(RepresentationLinkType);

        XMLOutputter xmlOutputter = new org.jdom.output.XMLOutputter(Format.getPrettyFormat());

        if (!file.exists() || (file.exists() && overwrite)) {
            xmlOutputter.output(scnXml, new FileOutputStream(file));
        }
    }
}
*/