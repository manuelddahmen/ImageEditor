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

package javaAnd.awt;

public class Point {

    public double x;
    public double y;

    public Point(int x2, int y2) {
        this.x = x2;
        this.y = y2;
    }

    public Point(Point p1) {
        x = p1.x;
        y = p1.y;
    }

    public static double distance(double x, double y, double x1, double y1) {
        return Math.sqrt((x-x1)*(x-x1)+(y-y1)*(y-y1));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Point(" +
                "x=" + x +
                ", y=" + y +
                ')';
    }

    public void setLocation(double v, double v1) {
        this.x = v;
        this.y = v1;
    }
}
