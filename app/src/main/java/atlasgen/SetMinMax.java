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


import one.empty3.library.Point2D;
import one.empty3.library.Representable;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/*__
 * Created by Maauel Dahmen on 30-06-18.
 */
public class SetMinMax implements Action {
    private HashMap<String, MyDim> colors = new HashMap<>();

    public HashMap<String, MyDim> getMyDims() {
        return colors;
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

        double latitude = Double.parseDouble(lineArray[longitudeColumn]);
        double longitude = Double.parseDouble(lineArray[latitudeColumn]);


        Point2D p1 = new Point2D(longitude, latitude);
        MyDim myDim;
        if (colors.get(countryCode) == null) {
            colors.put(countryCode, new MyDim(countryCode));
        }
        myDim = colors.get(countryCode);
        myDim.add(p1);

    }

    public Map<String, MyDim> getMyDim() {
        return colors;
    }

    class MyDim extends Representable {
        Point2D pA = new Point2D(Double.POSITIVE_INFINITY,
                Double.POSITIVE_INFINITY);
        Point2D pB = new Point2D(Double.NEGATIVE_INFINITY,
                Double.NEGATIVE_INFINITY);

        String countryCode;

        MyDim(String countryCode) {
            this.countryCode = countryCode;

            System.out.println("New dim" + countryCode);
        }

        void setMin(Point2D newPoint, Point2D ref) {
            if (newPoint.getX() < ref.getX()) {
                ref.setX(newPoint.getX());
            }
            if (newPoint.getY() < ref.getY()) {
                ref.setY(newPoint.getY());
            }
        }

        void setMax(Point2D newPoint, Point2D ref) {
            if (newPoint.getX() > ref.getX()) {
                ref.setX(newPoint.getX());
            }
            if (newPoint.getY() > ref.getY()) {
                ref.setY(newPoint.getY());
            }
        }

        void add(Point2D p) {
            setMin(p, pA);
            setMax(p, pB);
        }

        public Point2D convert(Dimension imageDimension, double longitude, double latitude) {

            return
                    new Point2D(imageDimension.getWidth() * ((pB.getX() - pA.getX()) /
                            (longitude - pA.getX())),
                            imageDimension.getHeight() * (
                                    (pB.getY() - pA.getY()) /
                                            (latitude - pA.getY())));

        }

        public Point2D getRatios(Point2D p) {
            double longitude = p.getX();
            double latitude = p.getY();
            return
                    new Point2D(((1 / (pB.getX() - pA.getX()) *
                            (longitude - pA.getX()) / 180) + 1) / 2,
                            ((1 /
                                    (pB.getY() - pA.getY()) *
                                    (latitude - pA.getY()) / 90) + 1) / 2);
        }

        public double latitudeExtend() {
            return pB.getY() - pA.getY();
        }

        public double longitudeExtend() {
            return pB.getX() - pA.getX();


        }

        public String toString() {
            String s = "";
            s += "pA              : " + Seriald.point2DtoString(pA) + "\n";
            s += "pB              : " + Seriald.point2DtoString(pB) + "\n";
            s += "latitude extend : " + latitudeExtend() + "\n";
            s += "longitud extend : " + latitudeExtend() + "\n";
            return s;
        }

        public String getCountryCode() {
            return countryCode;
        }
    }
}
