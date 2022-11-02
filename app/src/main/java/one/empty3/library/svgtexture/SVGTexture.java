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

package one.empty3.library.svgtexture;


import one.empty3.library.*;

import java.io.File;

/*__
 * Created by manue on 24-09-15.
 */
public class SVGTexture extends ITexture {

    public SVGTexture(File file) {
        // String parser = XMLResourceDescriptor.getXMLParserClassName();
        //SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
        String uri = file.toURI().toString();

    }

    @Override
    public void iterate() throws EOFVideoException {

    }

    @Override
    public int getColorAt(double x, double y) {
        return 0;
    }


    @Override
    public void timeNext() {
        // NOTHING TO DO HERE
    }

    @Override
    public void timeNext(long milli) {
        // NOTHING TO DO HERE
    }

    @Override
    public StructureMatrix getDeclaredProperty(String name) {
        return null;

    }

    @Override
    public MatrixPropertiesObject copy() throws CopyRepresentableError, IllegalAccessException, InstantiationException {
        return null;
    }
}
