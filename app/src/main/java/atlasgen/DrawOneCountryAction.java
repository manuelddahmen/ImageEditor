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

package atlasgen;

import one.empty3.library.ITexture;
import one.empty3.library.Point2D;
import one.empty3.library.core.lighting.Colors;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/*__
 * Created by Manuel Dagmen on 29-06-18.
 */
public class DrawOneCountryAction implements Action {
    private final ITexture tex;
    private SetMinMax.MyDim dim;
    private String country;
    private boolean firstPass = false;
    private Map<String, Color> colors = new HashMap<String, Color>();

    private Pixeler pixeler;

    public DrawOneCountryAction(Pixeler pixeler,
                                SetMinMax.MyDim dim,
                                ITexture tex) {
        this.pixeler = pixeler;
        this.dim = dim;
        this.country = dim.getCountryCode();
        this.tex = tex;

    }

    @Override
    public void init() {

    }

    @Override
    public void processLine(CsvLine csvLine) {
        int latitudeColumn = 4;
        int longitudeColumn = 5;
        int countryCodeColumn = 8;
        String[] lineArray = csvLine.getValue();
        String countryCode = lineArray[countryCodeColumn];
        if (country.equals(countryCode)) {
            colors.computeIfAbsent(countryCode, k -> Colors.random());
            double latitude = Double.parseDouble(lineArray[longitudeColumn]);
            double longitude = -Double.parseDouble(lineArray[latitudeColumn]);
            Dimension dimension = new Dimension(
                    pixeler.getImage().getWidth(),
                    pixeler.getImage().getHeight());
            Point2D p = dim.convert(dimension, longitude, latitude);

            Point2D pR = dim.getRatios(p);
            /*
            System.out.println("p" + Seriald.point2DtoString(p));
            System.out.println("pR" + Seriald.point2DtoString(pR));
            */
            pixeler.pixelize((int) p.x, (int) p.y,
                     one.empty3.feature.app.replace.java.awt.Color.Color(tex.getColorAt(pR.x, pR.y)));

        }
    }
}
