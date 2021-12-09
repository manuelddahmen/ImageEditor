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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package one.empty3.library.core.testing;

import one.empty3.library.core.script.ExtensionFichierIncorrecteException;
import one.empty3.library.core.script.Loader;
import one.empty3.library.core.script.VersionNonSupporteeException;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/*__
 * @author Manuel DAHMEN
 */
public class TestCollection {

    private final ArrayList<TestObjet> tests = new ArrayList<TestObjet>();
    private boolean dr;

    public void add(final File fichier) {
        TestObjet to = new TestObjetSub() {


            @Override
            public void ginit() {
                try {
                    new Loader().load(fichier, scene());
                } catch (VersionNonSupporteeException ex) {
                    Logger.getLogger(TestCollection.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExtensionFichierIncorrecteException ex) {
                    Logger.getLogger(TestCollection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        };
        add(to);

    }

    public void add(File[] fichiers) {
        for (File fichier : fichiers) {
            add(fichier);
        }
    }

    public void add(TestObjet to) {
        tests.add(to);
    }

    public void displayResult(boolean b) {
        this.dr = b;

    }

    public void run() {
        Iterator<TestObjet> it = tests.iterator();
        while (it.hasNext()) {
            TestObjet next = it.next();
            next.publishResult(dr);
            next.run();
        }
    }

    public void testCollection() {
        Iterator<TestObjet> it = tests.iterator();
        while (it.hasNext()) {
            it.next().run();
        }
    }
}
