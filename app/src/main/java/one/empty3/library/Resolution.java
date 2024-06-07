/*
 * Copyright (c) 2024.
 *
 *
 *  Copyright 2023 Manuel Daniel Dahmen
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

/*__
 * @author Se7en
 */
public class Resolution {
    public static final Resolution K4RESOLUTION = new Resolution(1920 * 2, 1080 * 2).makeFinal();
    public static final Resolution HD720RESOLUTION = new Resolution(1600, 720).makeFinal();
    public static final Resolution HD1080RESOLUTION = new Resolution(1920, 1080).makeFinal();
    public static final Resolution XVGARESOLUTION = new Resolution(640, 480).makeFinal();
    private int x;
    private int y;
    private int nbits = 32;
    private boolean aFinal = false;

    public Resolution(int xv, int yv) {
        this.x = xv;
        this.y = yv;

    }

    public void x(int v) {
        if (!isFinal())
            this.x = v;
    }

    public void y(int v) {
        if (!isFinal())
            this.y = v;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public boolean isFinal() {
        return aFinal;
    }

    protected Resolution makeFinal() {
        aFinal = true;
        return this;
    }
}
