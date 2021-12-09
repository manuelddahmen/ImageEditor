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


import one.empty3.apps.opad.menu.ToggleMenu;
import one.empty3.library.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;
import java.util.ResourceBundle;


/*__
 * Created by manuel on 07-06-17.
 */

public class Plotter3D implements KeyListener, Runnable {
    private final ResourceBundle bundle;

    public double rotationYParNano;
    public double rotationXParNano;
    protected boolean release_up = true;
    protected boolean release_down = true;
    protected boolean release_left = true;
    protected boolean release_right = true;
    DistanceCount distanceCount;
    ToggleMenu toggleMenu;
    private PositionUpdate positionUpdate;
    private boolean active;
    private Point2D impact;
    private boolean droite = false;
    private boolean gauche = false;
    private boolean haut = false;
    private boolean bas = false;
    private long timeEllapsed = 0;

    public Plotter3D(PositionUpdate positionUpdate) {

        this.positionUpdate = positionUpdate;
        distanceCount = new DistanceCount();
        bundle = ResourceBundle.getBundle("one.empty3.apps.opad/Bundle"); // NOI18N
        rotationYParNano = Double.parseDouble(bundle.getString("rotationYPerNanos"));
        rotationXParNano = Double.parseDouble(bundle.getString("rotationXPerNanos"));
    }

    @Override
    public void run() {
        long timeBefore = System.nanoTime();
        long timeAfter = timeBefore;
        while (true) {
            timeBefore = System.nanoTime();


            cont(timeAfter - timeBefore);


            timeAfter = System.nanoTime();

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public Point3D getImpact() {

        return distanceCount.getDistance();
    }

    public Point2D getImpactY() {
        return distanceCount.dirY;
    }

    public Terrain getTerrain() {
        return positionUpdate.getTerrain();
    }

    public Collection<Representable> getScene() {
        return getTerrain().getListRepresentable();
    }

    public boolean isActive() {
        return active;
    }

    public Point2D getPosition() {
        return positionUpdate.getPositionMobile().getPositionSol().to2DwoZ();
    }

    public boolean plot(Point3D position, Point3D deplacement, Cube plottee)

    {
        Point3D point3D = getTerrain().hauteur(position.getX() + deplacement.getX(), position.getY() + deplacement.getY(), position.getZ());
        plottee.setPosition(point3D);
        getScene().add(plottee);
        return true;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            active = true;
            distanceCount = new DistanceCount();
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (isActive()) {

                distanceCount.countPressed();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            plot(distanceCount.dir.plus(getTerrain().calcCposition(getPosition().getX(), getPosition().getY())),
                    distanceCount.dir.plus(getTerrain().calcCposition(getPosition().getX(), getPosition().getY()))
                    , new Cube(0.1, Point3D.O0, new TextureCol(Color.WHITE)));
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            release_up = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            release_down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            release_left = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            release_right = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            active = false;
            distanceCount = new DistanceCount();
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            distanceCount.countRelease();
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            release_up = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            release_down = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            release_left = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            release_right = true;
        }
    }

    private void cont(long timeKeyPress) {
        if (isActive()) {
            System.out.println("Plotter is active");
            if (!release_up) {
                distanceCount.rotationHaut(timeKeyPress);
                //System.out.println("Acc");
            }
            if (!release_down) {
                distanceCount.rotationBas(timeKeyPress);
                //System.out.println("Dec");
            }
            if (!release_left) {
                distanceCount.rotationGauche(timeKeyPress);
                //System.out.println("Left");
            }
            if (!release_right) {
                distanceCount.rotationDroite(timeKeyPress);
                //System.out.println("Right");
            }
        }
    }


    class DistanceCount {
        ResourceBundle bundle = ResourceBundle.getBundle("one.empty3.apps.opad.Bundle");
        private double distance = 1;
        private Point3D dir;
        private long timeMiliisStart;
        private long timeElapsedMiliis;
        private boolean distanceNull;
        private boolean isReleased = true;
        private double acc = Double.parseDouble(bundle.getString("plotter.speed"));
        private double angle = 0;
        private double angleY = Math.PI / 2;
        private Point2D dirY;

        public DistanceCount() {
            this.dir = positionUpdate.calcDirection();
            if (dir == null) {
                dir = Point3D.O0;
            }
            angle = positionUpdate.getAngle();
        }

        public void countPressed() {
            if (isReleased) {
                timeMiliisStart = System.currentTimeMillis();
                timeElapsedMiliis = 0;
                acc = Double.parseDouble(bundle.getString("plotter.speed"));
            } else {
                distance += distance * timeElapsedMiliis * acc;
                timeElapsedMiliis = System.currentTimeMillis() - timeMiliisStart;

            }
        }

        public void countRelease() {
            timeElapsedMiliis = System.currentTimeMillis() - timeMiliisStart;
            isReleased = true;
            Bullet bullet = new Bullet(positionUpdate.calcCposition(), positionUpdate.calcDirection(), 0.0001, null, System.nanoTime());
            getTerrain().add(bullet);
        }

        public Point3D getDistance() {
            return dir.mult(distance);
        }

        public void setDir(Point3D dir) {
            this.dir = dir;
        }


        public void rotationGauche(long timeMillis) {
            angle -= Math.PI * 2 * rotationXParNano * timeMillis;
            dir
                    = new Point3D(
                    Math.sin(angle) * Math.cos(angleY),
                    Math.cos(angle) * Math.cos(angleY),
                    dir.getZ());
            positionUpdate.getPositionMobile().setAngleVueMobile(dir);
            System.out.println("RG");
        }

        public void rotationDroite(long timeMillis) {
            angle += Math.PI * 2 * rotationXParNano * timeMillis;
            dir
                    = new Point3D(
                    Math.sin(angle) * Math.cos(angleY),
                    Math.cos(angle) * Math.cos(angleY),
                    dir.getZ());
            positionUpdate.getPositionMobile().setAngleVueMobile(dir);
            System.out.println("RD");

        }

        public void rotationHaut(long timeMillis) {
            angleY -= Math.PI * 2 * rotationYParNano * timeMillis;
            dir
                    = new Point3D(
                    Math.sin(angle) * Math.cos(angleY),
                    Math.cos(angle) * Math.cos(angleY),
                    Math.sin(angleY));
            positionUpdate.getPositionMobile().setAngleVueMobile(dir);
        }

        public void rotationBas(long timeMillis) {
            angleY += Math.PI * 2 * rotationYParNano * timeMillis;
            dir
                    = new Point3D(
                    Math.sin(angle) * Math.cos(angleY),
                    Math.cos(angle) * Math.cos(angleY),
                    Math.sin(angleY));

            positionUpdate.getPositionMobile().setAngleVueMobile(dir);
        }

        public Point2D getDirY() {
            return dirY;
        }
    }
}

