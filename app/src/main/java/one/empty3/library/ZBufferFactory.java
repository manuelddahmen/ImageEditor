/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

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

public class ZBufferFactory {

    private static ZBufferImplJan2023 insta = null;
    private static int la = -1, ha = -1;

    public static ZBufferImplJan2023 instance(int x, int y) {
        if (la == x && ha == y && insta != null) {
            return insta;
        }
        la = x;
        ha = y;
        insta = new ZBufferImplJan2023(x, y);
        return insta;
    }

    public static ZBufferImplJan2023 instance(int x, int y, boolean D3) {
        if (la == x && ha == y && insta != null)//&& (D3 && insta instanceof ZBuffer3D || !D3))
        {
            return insta;
        }
        la = x;
        ha = y;
        if (D3) {
            // insta = new ZBuffer3DImpl(coordArr, y);
        } else {
            insta = new ZBufferImplJan2023(x, y);
        }

        return insta;
    }

    public static ZBufferImplJan2023 instance(int x, int y, Scene s) {

        ZBufferImplJan2023 z = new ZBufferImplJan2023(x, y);
        z.scene(s);
        return z;
    }
}
