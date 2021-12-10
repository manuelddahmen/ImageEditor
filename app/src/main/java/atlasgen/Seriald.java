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

import javax.swing.text.NumberFormatter;
import java.io.File;
import java.text.DecimalFormat;

/*__
 * Created by Win on 06-07-18.
 */
public class Seriald {
    public static NumberFormatter numberFormatter;

    static {
        DecimalFormat decimalFormat = new DecimalFormat("0000000");
        numberFormatter
                = new NumberFormatter();
        numberFormatter.setFormat(decimalFormat);


    }

    public static String newSerial() {
        return numberFormatter.getFormat().format((int) (Math.random() * 10000000));
    }

    public static File newOutputFile(String classname) {
        File file = new File(".\\generated_Maps\\" + classname + "\\map-" + Seriald.newSerial() + ".jpg");
        file.mkdirs();
        return file;
    }

    public static String point2DtoString(Point2D p) {
        return "Point2D(" + p.getX() + ", " + p.getY() + " )";
    }

    public static String newOutputFileMulti(String classname, String countryCode) {
        String fileStr = "" + classname + "\\" + countryCode + "\\map-" + Seriald.newSerial() + ".jpg";
        File file = new File(fileStr);
        file.mkdirs();
        return fileStr
                ;
    }
}
