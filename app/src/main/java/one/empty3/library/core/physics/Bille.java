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

package one.empty3.library.core.physics;

import android.graphics.Color;

import one.empty3.library.*;


public class Bille implements IPoint {
    public Color color;
    public String nom;
    public Point3D position;
    public Point3D vitesse = new Point3D(0d, 0d, 0d);
    public double attraction;
    public double repulsion;
    public double masse = 1;
    public double amortissement = 1;


    public Bille(Color color, int statut, String nom, Point3D position,
                 Point3D vitesse, double attraction, double repulsion, double masse,
                 double amortissement) {
        super();
        this.color = color;
        this.nom = nom;
        this.position = position;
        this.vitesse = vitesse;
        this.attraction = attraction;
        this.repulsion = repulsion;
        this.masse = masse;
        this.amortissement = amortissement;
    }

    public Bille(Bille b) {
        super();
        this.color = b.color;
        this.nom = b.nom;
        this.position = b.position;
        this.vitesse = b.vitesse;
        this.attraction = b.attraction;
        this.repulsion = b.repulsion;
        this.masse = b.masse;
        this.amortissement = b.amortissement;
    }

    public Bille() {
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public Point3D getVitesse() {
        return vitesse;
    }

    public void setVitesse(Point3D vitesse) {
        this.vitesse = vitesse;
    }

    public double getAttraction() {
        return attraction;
    }

    public void setAttraction(double attraction) {
        this.attraction = attraction;
    }

    public double getRepulsion() {
        return repulsion;
    }

    public void setRepulsion(double repulsion) {
        this.repulsion = repulsion;
    }

    public double getMasse() {
        return masse;
    }

    public void setMasse(double masse) {
        this.masse = masse;
    }

    public double getAmortissement() {
        return amortissement;
    }

    public void setAmortissement(double amortissement) {
        this.amortissement = amortissement;
    }

}
