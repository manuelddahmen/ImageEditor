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

import one.empty3.library.*;
import one.empty3.library.*;

import java.io.*;

public class ModeleIO {

    public static boolean sauvergarder(Representable o, File file) {
        boolean r = false;
        ObjectOutputStream dos = null;
        FileOutputStream fos = null;
        try {
            dos = new ObjectOutputStream(fos = new FileOutputStream(file));
            r = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dos.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dos.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return r;
    }

    public static boolean sauvergarder(Scene sc, File file) {
        boolean r = false;
        ObjectOutputStream dos = null;
        FileOutputStream fos = null;
        try {
            sc.dumpDATA();
            fos = new FileOutputStream(file);
            dos = new ObjectOutputStream(fos);
            r = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dos.writeObject(sc);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dos.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return r;
    }

    public static boolean sauvergarderTXT(Scene sc, File file) {
        String txt = sc.toString();
        boolean r = false;
        FileOutputStream dos = null;
        BufferedOutputStream pw = null;
        try {
            dos = new FileOutputStream(file);
            pw = new BufferedOutputStream(dos);
            pw.write(txt.getBytes(), 0, txt.length());
            pw.close();
            dos.close();
            r = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return r;
    }

    public Scene charger(Scene sc, File file) {
        boolean r = false;
        ObjectInputStream dos = null;
        try {
            dos = new ObjectInputStream(new FileInputStream(file));
            r = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return (Scene) dos.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
}
