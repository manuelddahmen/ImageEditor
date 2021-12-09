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

/*__
 * Created by manuel Dahmen manuel.dahmen@gmail.com on 08-10-15.
 */

/*__
 * Destinée à concevoir des actions de dessin spécifiques à certaines formes d'un type général Representable.
 * <p/>
 * Par exemple : au lieu de tracer un maillage de surface (action par défaut) dessiner des points aléatoirement
 * sur la surface.
 */
public class Painter {
    private final ZBuffer z;
    private Scene scene;
    private PaintingAct pa;
    private Representable r;
    private Class c;

    public Painter(ZBuffer z, Scene scene) {
        this.z = z;

        this.scene = scene;
    }

    public Painter(ZBuffer z, Scene scene, Representable r) {
        this.z = z;
        this.r = r;
        this.c = r.getClass();
        this.scene = scene;
    }

    public Painter(ZBuffer z, Scene scene, Class<Representable> c) {
        this.z = z;
        this.c = c;
        this.scene = scene;
    }

    public void addAction(PaintingAct pa) {
        this.pa = pa;
        pa.setObjet(r);
        pa.setZBuffer(z);
        pa.setScene(scene);
    }

    public PaintingAct getPaintingAct() {
        return pa;
    }
}
