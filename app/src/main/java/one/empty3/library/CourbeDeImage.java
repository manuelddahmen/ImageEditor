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

/*

 Vous êtes libre de :

 */
package one.empty3.library;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Hashtable;
import java.util.Set;

public class CourbeDeImage {

    private Bitmap image;
    private Hashtable<Point2D, Color> points;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public CourbeDeImage(Bitmap image) {
        super();
        this.image = image;
        this.points = new Hashtable<Point2D, Color>();

        anayliserImage();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void anayliserImage() {
        for (int i = 0; i < image.getWidth(); i++) {
            int y0 = -1;
            int y1 = -1;
            for (int j = 0; j < image.getHeight(); j++) {
                if (!Color.valueOf(image.getPixel(i, j)).equals(Color.WHITE)) {
                    y0 = y1;
                    y1 = j;
                    if (y0 == -1 || (y1 > y0 + 1)) {
                        points.put(new Point2D(i, j), Color.valueOf(image.getPixel(i, j)));
                        break;
                    }
                }

            }
        }
    }

    public void classerPoints() {
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Hashtable<Point2D, Color> getPoints() {
        return points;
    }

    public void setPoints(Hashtable<Point2D, Color> points) {
        this.points = points;
    }

    public Set<Point2D> getPointsList() {
        return points.keySet();
    }
}
