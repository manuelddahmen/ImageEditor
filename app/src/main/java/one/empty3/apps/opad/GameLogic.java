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

package one.empty3.apps.opad;

import one.empty3.library.Point3D;
import one.empty3.library.Representable;
import one.empty3.library.core.tribase.TRISphere;

import java.util.Iterator;

public class GameLogic {


 private double hauteur = 0.01;
 protected Point3D positionOrigine = Point3D.O0.plus(hauteur);
 protected Point3D position = positionOrigine;
 protected double positionIncrement = .01;
 protected double directionNorme = 1;
 protected Point3D directionOrigine = Point3D.Z;
 protected Point3D direction = directionOrigine;
 protected double angle = 0.0;
 private int score;
 private Bonus ennemi;
 protected static final int STATE_GAME_IN_PROGRESS = 1;
 private boolean gagne = false;

 public GameLogic() {
 }

 public void update() {
 }

 public void acc() {

 position = position.plus(direction.mult(positionIncrement));
 }

 public void dec() {
 position = position.moins(direction.mult(positionIncrement));
 }

 public int state() {
 return STATE_GAME_IN_PROGRESS;
 }

 public GameObject[] getObjects() {
 return null;
 }

 public Point3D position() {
 return position;
 }

 public Point3D direction() {
 return direction;
 }

 public void rotationGauche() {
 angle = angle + Math.PI * 2 / 360 * 10;
 direction =
 new Point3D(
 directionNorme * Math.sin(angle),
 hauteur,
 directionNorme * Math.cos(angle));
 }

 public void rotationDroite() {
 angle = angle - Math.PI * 2 / 360 * 10;
 direction =
 new Point3D(
 directionNorme * Math.sin(angle),
 hauteur,
 directionNorme * Math.cos(angle));

 }

 public void testCollision() {
 Iterator<Representable> it = ennemi.iterator();

 while (it.hasNext()) {
 Representable r = it.next();

 if (r instanceof TRISphere) {
 if (Point3D.distance(((TRISphere) r).getCentre(), position) < 0.1) {
 int points = 10;
 //System.out.println("POINTS" + points);

 score += points;

 System.out.println(score);

 if(ennemi.removeBonus(r))
 win();

 break;
 }
 }

 }

 }

 public void ennemi(Bonus e) {
 this.ennemi = e;
 }

 public int score() {
 return score;
 }

 private void win() {
 gagne = true;
 }

 public boolean estGagnant()
 {
 return gagne;
 }

}
