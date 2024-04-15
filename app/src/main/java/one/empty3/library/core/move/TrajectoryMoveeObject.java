/*
 * Copyright (c) 2023.
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

package one.empty3.library.core.move;

import one.empty3.library.MoveeObject;
import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;
import one.empty3.library.core.nurbs.ParametricCurve;

/*__
 * Created by manuel on 29-06-17.
 */
public abstract class TrajectoryMoveeObject extends SimpleTrajectory implements MoveeObject {
    private final ParametricCurve parametricCurve;
    private Point3D origin;
    private double timeScale;

    public TrajectoryMoveeObject(ParametricCurve parametricCurve) {
        this.parametricCurve = parametricCurve;
        this.origin = parametricCurve.calculerPoint3D(0.0);
    }

    @Override
    public void setPositionAtTime(Point3D position, long nanoTime) {
        this.nanoTime = nanoTime;
    }

    public void setTimeScale(double timeScale) {
        this.timeScale = timeScale;
    }

    @Override
    public Point3D getCurrentPosition() {
        return calculerPoint3D(System.nanoTime() - nanoTime);
    }

    @Override
    public Point3D calculerPoint3D(double timeEllapsedNano) {
        return parametricCurve.calculerPoint3D(timeScale * timeEllapsedNano);
    }

}
