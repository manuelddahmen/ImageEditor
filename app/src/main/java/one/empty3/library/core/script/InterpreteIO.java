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
package one.empty3.library.core.script;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/*__
 * @author Manuel DAHMEN
 * @date
 */
public class InterpreteIO {

    public static File getFile(String filename, String repertoire) throws FileNotFoundException {
        File f = new File(filename);
        if (f.exists()) {
            return f;
        }

        Properties config = new Properties();
        try {
            config.load(new FileInputStream(System.getProperty("user.home") + File.separator + java.util.ResourceBundle.getBundle("info/emptycanvas/one.empty3.library/gui/Bundle").getString("NOM DU FICHIER DE CONFIGURATION PROPERTIES")));
            f = new File(config.getProperty("folder.textures") + File.separator + filename);
        } catch (IOException ex) {
            Logger.getLogger(InterpreteIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (f != null && f.exists()) {
            return f;
        }

        f = new File(repertoire + File.separator + filename);
        if (f != null && f.exists()) {
            return f;
        }
        throw new FileNotFoundException(
                "Default Folders: \n\t" + repertoire
                        + (config == null ? "\n\t" : "\n\t" + File.pathSeparator
                        + config.getProperty("folder.textures"))
                        + "\nFile: \n\t" + filename);
    }
}
