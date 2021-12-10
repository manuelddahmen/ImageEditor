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

import one.empty3.library.ColorTexture;
import one.empty3.library.Point3D;
import one.empty3.library.core.lighting.Colors;
import one.empty3.library.core.nurbs.PcOnPs;
import one.empty3.library.core.nurbs.Point3DS;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/*__
 * Created by Manuel Dagmen on 29-06-18.
 */
public class DrawPerCountryActionSphere implements Action {
    int countLine = 0;
    private final TestEarth testEarth;
    private boolean firstPass = false;
    private Map<String, Color> colors = new HashMap<String, Color>();

    private Pixeler pixeler;

    public DrawPerCountryActionSphere(TestEarth testEarth) {
        this.testEarth = testEarth;
    }


    @Override
    public void init() {

    }

    @Override
    public void processLine(CsvLine csvLine) {
        //if (countLine % 1000 == 0) {
        int lattitudeColumn = 4;
        int longitudeColumn = 5;
        int countryCodeColumn = 8;
        String[] lineArray = csvLine.getValue();
        String countryCode = lineArray[countryCodeColumn];
        colors.computeIfAbsent(countryCode, k -> Colors.random());
        double lng = (Double.parseDouble(lineArray[longitudeColumn]) / 180 + 1) / 2;
        double lat = Double.parseDouble(lineArray[lattitudeColumn]) / 90;
        Point3DS d = new Point3DS(new Point3D(
                lng, lat, 0d));
        d.texture(new ColorTexture(colors.get(countryCode)));
        PcOnPs pcOnPs = new PcOnPs(testEarth.getSphere(), d);
        pcOnPs.texture(new ColorTexture(colors.get(countryCode)));
        pcOnPs.getParameters().setIncrU(1.0);
        pcOnPs.getParameters().setEndU(0.1);
        pcOnPs.setConnected(false);
        testEarth.getZ().draw(pcOnPs);
        if (countLine % 100000 == 0) {
            System.out.println("Lines" + countLine);
            System.out.println(d.calculerPoint3D(0.0));
        }
        //}
        countLine++;
    }
}
