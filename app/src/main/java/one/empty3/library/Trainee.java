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

import one.empty3.library.core.tribase.ApproximationFonction1D;

import java.util.ArrayList;

/*__
 * Created by manuel on 29-07-16.
 * <p>
 * Opacités
 * Lumières diffuse
 * Lumière ambiante
 * Diffraction
 * Réffraction
 * Réflexion
 * Ombres
 */
public class Trainee {
    private ArrayList<Point3D> traines;
    private ApproximationFonction1D taillesF;
    private ArrayList<Double> taille;
    /*__
     * Distance Unité : px
     */
    private Double distMax;
    /*__
     * Distance Unité : px
     */
    private Double distMin;
    /*__
     * double 0..1
     */
    private ArrayList<Double> opacites;
    /*__
     * Fonction définie sur [0,taille] à valeur dans [0,1]
     */
    private ApproximationFonction1D opacitesF;

    public Trainee() {

    }

    public ApproximationFonction1D getOpacitesF() {
        return opacitesF;
    }

    public void setOpacitesF(ApproximationFonction1D opacitesF) {
        this.opacitesF = opacitesF;
    }

    public ApproximationFonction1D getTaillesF() {
        return taillesF;
    }

    public void setTaillesF(ApproximationFonction1D taillesF) {
        this.taillesF = taillesF;
    }

    public ArrayList<Double> getOpacites() {
        return opacites;
    }

    public void setOpacites(ArrayList<Double> opacites) {
        this.opacites = opacites;
    }

    public ArrayList<Double> getTaille() {
        return taille;
    }

    public void setTaille(ArrayList<Double> taille) {
        this.taille = taille;
    }

    public ArrayList<Point3D> getTraines() {
        return traines;
    }

    public void setTraines(ArrayList<Point3D> traines) {
        this.traines = traines;
    }

    public Double getDistMax() {
        return distMax;
    }

    public void setDistMax(Double distMax) {
        this.distMax = distMax;
    }

    public Double getDistMin() {
        return distMin;
    }

    public void setDistMin(Double distMin) {
        this.distMin = distMin;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

}
