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

package one.empty3.buildutil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.logging.Logger;

/*__
 * Created by manue on 12-08-19.
 */
public class WriteManifest {

    public static void main(String[] args) {
        String dir;
        String mainClass;
        try {
            if (args.length > 0) {
                dir = args[0];
            } else {
                dir = "./lib";
            }
            if (args.length > 1) {
                mainClass = args[1];
            } else {
                mainClass = null;
            }
            File manifest = new File("META-INF/MANIFEST.MF");
            if (manifest.exists()) {
                manifest.renameTo(new File(manifest.getAbsolutePath() + ".tmp" + System.nanoTime()));
            } else {
                new File("META-INF").mkdir();
            }
            //File launch = new File("LAUNCH.BAT");
            FileOutputStream fileOutputStream = new FileOutputStream(manifest);
            PrintWriter pw = new PrintWriter(fileOutputStream);
            pw.println("Manifest-Version: 1.0");
            if (mainClass != null)
                pw.println("Main-Class: " + mainClass);
            StringBuilder stringBuilder = new StringBuilder("Class-Path: ");
            recurse(dir, stringBuilder);
            pw.println(stringBuilder.toString());
            //Main-Class: be.manudahmen.empty3.apps.pad.PanelGraphics
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    private static void recurse(String s, StringBuilder stringBuilder) {
        File file = new File(s);
        if (file.exists()) {
            if (file.isDirectory()) {
                String[] list = file.list();
                for (String fn : list) {
                    recurse(s + "/" + fn, stringBuilder);
                }
            } else {
                if (file.getName().endsWith(".jar")) {
                    stringBuilder.append(s + " ");
                }
            }
        } else {
            Logger.getAnonymousLogger().info("Error file not exists = " + s);
        }

    }
}
