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

/*__
Global license : 

    CC Attribution

 author Manuel Dahmen _manuel.dahmen@gmx.com_

***/


package one.empty3.apps.opad;

import one.empty3.library.Cube;
import one.empty3.library.Point2D;
import one.empty3.library.Point3D;

/*__
 *
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public interface PositionUpdate extends Runnable
{

    void update();

    void
    acc(long timeMillis);

    void dec(long timeMillis);

    boolean plot(Point3D position, Point3D deplacement, Cube plottee);

    int state();



    void rotationGauche(long timeMillis);

    void rotationDroite(long timeMillis);

    void testCollision(PositionMobile positionMobile);

    void ennemi(Bonus e);

    int score();

    boolean estGagnant();

    Point3D calcCposition();

    Point3D calcDirection();

    Point3D calculerPositionAuSol(Point2D position2D);

    Point3D calculerPositionModule(Point2D position2D);



    int STATE_GAME_IN_PROGRESS();

    Circuit getCircuit();

    double energy();

    Terrain getTerrain();

    void setTerrain(Terrain t);

    double getAngle();

    Point2D directionY();

    void moveUp(long timeKeyPress);

    void moveDown(long timeKeyPress);

    Plotter3D getPlotter3D();

    void setPlotter3D(Plotter3D plotter3D);

    PositionMobile getPositionMobile();

    Point3D getVecDir2D();

    Path getPath();

    void setPath(Path path);

    void setMain(DarkFortressGUI darkFortressGUI);

    DarkFortressGUI getMain();
}
