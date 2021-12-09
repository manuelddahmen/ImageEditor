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

package one.empty3.library;

import javax.swing.*;
import java.awt.*;

/*__
 * Created by manuel on 25-01-17.
 */
public class Main2 extends JFrame {
    public Main2() {
        super("Application 2D/3D");
        JTextArea jTextArea = new JTextArea("# Variables: \n" +
                "# t : temps en secondes d'animation dans la vidéo\n" +
                "# xRes : résolution de la vidéo en coordArr\n" +
                "# yRes : résolution de la vidéo en y\n" +
                "# coordArr, y : coordonnées du point en (coordArr, y)\n" +
                "# tMax : fin de la vidéo en secondes\n");
        //jTextArea.setCursor(new Cursor(jTextArea.getLineCount()));
        JRootPane jRootPane = new JRootPane();
        jRootPane.setContentPane(jTextArea);
        add(jRootPane);
        this.setRootPane(jRootPane);
        this.setBounds(new Rectangle(0, 0, 800, 400));
    }

    public static void main(String[] args) {
        new Main2().setVisible(true);
    }
}
