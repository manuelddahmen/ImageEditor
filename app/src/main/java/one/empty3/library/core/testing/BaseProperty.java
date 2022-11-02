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

package one.empty3.library.core.testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*__
 * Created by manue on 08-02-20.
 */
public enum BaseProperty {
    setResx, setResy, setResolution, setMaxFrames, loop;

    private static HashMap<BaseProperty, List<Class>> parameters = new HashMap<>();

    private static void add(String name, Class... parametersTypes) {
        List<Class> classes;

        classes = new ArrayList<>();

        for (Class parametersType : parametersTypes) {
            classes.add(parametersType);
        }
        parameters.put(BaseProperty.valueOf(name), classes);
    }

    static {
        add("setResx", Integer.class);
        add("setResy", Integer.class);
        add("setResolution", Integer.class);
        add("setMaxFrames", Integer.class);
        add("loop", Boolean.class);
    }

    public String toString() {
        return name();
    }
}
