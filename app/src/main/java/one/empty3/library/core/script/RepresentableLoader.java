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

package one.empty3.library.core.script;

import one.empty3.library.Cube;
import one.empty3.library.Point3D;
import one.empty3.library.TRI;
import one.empty3.library.core.tribase.TRICylindre;
import one.empty3.library.core.tribase.TRISphere;

import java.util.HashMap;
import java.util.Map;

public class RepresentableLoader {

    public static Map<String, Class<?>> listObjectTypes() {
        Map<String, Class<?>> m = new HashMap<String, Class<?>>();

        m.put("p", Point3D.class);
        m.put("tri", TRI.class);
        m.put("sphere", TRISphere.class);
        m.put("cylindre", TRICylindre.class);
        m.put("cube", Cube.class);
        return m;

    }

    public Map<String, Object[]> classParametre(Class<Object> clazz) {

        return null;
    }

    public class ParametreObjet {

        public String nomCourt;
        public String nomComplet;
        public String caption;
        public String description;
        public Class<Object> clazz;
        public String defaultValue;
    }
}
