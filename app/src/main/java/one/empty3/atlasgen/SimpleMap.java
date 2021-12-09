/*
 *  This file is part of one.empty3.
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
 *     along with one.empty3.  If not, see <https://www.gnu.org/licenses/>. 2
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

package one.empty3.atlasgen;

import javax.imageio.ImageIO;

import one.empty3.feature.app.replace.java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SimpleMap {

    public static void main(String[] args) {
        System.out.println(
                "One color Map"
        );

        Color color = Color.WHITE;
        Pixeler pixeler = new Pixeler(new BufferedImage(1800, 1600, BufferedImage.TYPE_INT_RGB));
        CsvReader csvReader = new CsvReader(new File("allCountries/allCountries.txt"),
                "" + '\t', "" + '\n', false);
        csvReader.setAction(new DrawAction(pixeler, color));
        csvReader.process();
        try {
            File file = Seriald.newOutputFile("SimpleMap");
            file.mkdirs();
            ImageIO.write(pixeler.getImage(), "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
