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
package one.empty3.library.core.tribase;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map.Entry;

@Deprecated
public abstract class BaseGenerator {

    protected BufferedImage buffer;
    protected Component comp;
    protected BufferedImage image;
    private int x;
    private int y;

    public BaseGenerator(int dx, int dy, Component c) {
        x = dx;
        y = dy;
        comp = c;
    }

    public void computeFrame() {
        // TODO Auto-generated method stub

    }

    public Image getBUFFER() {
        return buffer;
    }

    public Graphics getGraphicsDisque() {
        return image.getGraphics();
    }

    public Graphics getGraphicsEcran() {
        return comp.getGraphics();
    }

    public void initFrame() {
        // TODO Auto-generated method stub

    }

    public void paint() {
        comp.getGraphics().drawImage(buffer, 0, 0, x, y, null);
        image.getGraphics().drawImage(buffer, 0, 0, x, y, null);
    }

    public void renew() {
        image = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
    }

    public void save() {
    }

    public void setConfig(Config params) {
        for (Entry<String, Object> entry : params.entrySet()) {
            if (entry.getKey().startsWith("global") | entry.getKey().startsWith(this.getClass().getSimpleName())) {
                setField(entry.getKey().substring(
                        entry.getKey().indexOf(".") + 1,
                        entry.getKey().indexOf("=")),
                        entry.getKey().substring(
                                entry.getKey().indexOf("="))
                );
            }
        }

    }

    private void setField(String key, String stringvalue) {

    }

    public void setParams(Params params) {
        // TODO Auto-generated method stub

    }

    public void showFrame() {
        // TODO Auto-generated method stub

    }
}
