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

package one.empty3.library.core.Engine;

import one.empty3.library.*;

import java.lang.reflect.Method;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class RegisterObject {
    public class Entry {
        private Class<Representable> class3d;
        private boolean positionnable;
        private boolean rotatable;
        private boolean moveable;
        private boolean sizable;
        private Method positionnableSpecialMethod;
        private Method rotatableSpecialMethod;
        private Method moveableSpecialMethod;
        private Method sizableSpecialMethod;

        public Entry(Class<Representable> class3d, boolean positionnable, boolean rotatable, boolean moveable, boolean sizable, Method positionnableSpecialMethod, Method rotatableSpecialMethod, Method moveableSpecialMethod, Method sizableSpecialMethod) {
            this.class3d = class3d;
            this.positionnable = positionnable;
            this.rotatable = rotatable;
            this.moveable = moveable;
            this.sizable = sizable;
            this.positionnableSpecialMethod = positionnableSpecialMethod;
            this.rotatableSpecialMethod = rotatableSpecialMethod;
            this.moveableSpecialMethod = moveableSpecialMethod;
            this.sizableSpecialMethod = sizableSpecialMethod;
        }


        public Class<Representable> getClass3d() {
            return class3d;
        }

        public boolean isPositionnable() {
            return positionnable;
        }

        public boolean isRotatable() {
            return rotatable;
        }

        public boolean isMoveable() {
            return moveable;
        }

        public Method getPositionnableSpecialMethod() {
            return positionnableSpecialMethod;
        }

        public Method getRotatableSpecialMethod() {
            return rotatableSpecialMethod;
        }

        public Method getMoveableSpecialMethod() {
            return moveableSpecialMethod;
        }

        public Method getSizableSpecialMethod() {
            return sizableSpecialMethod;
        }


    }


}
