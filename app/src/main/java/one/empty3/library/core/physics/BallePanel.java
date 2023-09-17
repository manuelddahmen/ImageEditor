/*
 * Copyright (c) 2016. Tous les fichiers dans ce programme sont soumis à la License Publique Générale GNU créée par la Free Softxware Association, Boston.
 * La plupart des licenses de parties tièrces sont compatibles avec la license principale.
 * Les parties tierces peuvent être soumises à d'autres licenses.
 * Montemedia : Creative Commons
 * ECT : Tests à valeur artistique ou technique.
 * La partie RayTacer a été honteusement copiée sur le Net. Puis traduite en Java et améliorée.
 * Java est une marque de la société Oracle.
 *
 * Pour le moment le programme est entièrement accessible sans frais supplémentaire. Get the sources, build it, use it, like it, share it.
 */
/*
package one.empty3.library.core.physics;

import one.empty3.library.*;


import one.empty3.feature.app.replace.java.awt.event.MouseEvent;
import one.empty3.feature.app.replace.java.awt.event.MouseListener;
import one.empty3.feature.app.replace.java.awt.event.MouseMotionListener;
import android.graphics.Bitmap;

public class BallePanel extends DataPanel implements Runnable {
    private static final long serialVersionUID = 4876595577262483236L;
    Bille[] positions;
    BufferedImageAndroid bi;
    private int noBilles = 20;
    private boolean init;
    private double distanceSouris = 10;
    private double coefficient = 1;

    public BallePanel() {

        //setMinimumSize(new Dimension(RESX, RESY));

        //setSize(new Dimension(RESX, RESY));

        addMouseMotionListener(new MouseMotionListener() {


            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();

            }


            public void mouseDragged(MouseEvent e) {

            }
        });

        addMouseListener(new MouseListener() {

            private Bille activee;


            public void mouseReleased(MouseEvent arg0) {
                if (activee != null) {
                    Point3D v2 = new Point3D(arg0.getX(), arg0.getY(), 0).moins(activee.position).mult(coefficient);

                    activee.vitesse = activee.vitesse.plus(v2);

                }

            }


            public void mousePressed(MouseEvent e) {
                activee = getBille(e.getPoint());

            }


            public void mouseExited(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }


            public void mouseEntered(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }


            public void mouseClicked(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    public Bille getBille(Point p) {
        for (int i = 0; i < positions.length; i++)
            if (proche(new Point((int) positions[i].position.getX(),
                    (int) positions[i].position.getY()), p)) {
                return positions[i];
            }

        return null;
    }

    private boolean proche(Point point, Point p) {
        return p.distance(point) < distanceSouris;
    }

    public void init(int noBilles) {
        this.noBilles = noBilles;

        positions = new Bille[noBilles * 2];

        for (int c = 0; c < 2; c++) {
            for (int i = 0; i < noBilles / 5; i++) {
                for (int j = 0; j < 5; j++) {
                    positions[c * noBilles + (i * 5 + j)] = new Bille();
                    positions[c * noBilles + (i * 5 + j)].color = c == 0 ? Color.RED
                            : Color.BLUE;

                    int pasx = getWidth() / 4 / (noBilles + 1);
                    int pasy = getHeight() / (5 + 1);

                    positions[c * noBilles + (i * 5 + j)].position = c == 0 ? new Point3D(
                            10 + i * pasx, 10 + j * pasx, 0) : new Point3D(getWidth()
                            - 10 - i * pasx, 10 + j * pasx, 0);

                    positions[c * noBilles + (i * 5 + j)].vitesse = Point3D.X
                            .mult(10);
                }
            }
        }

        init = true;
    }

    public void afficher(BufferedImageAndroid bi) {

        Graphics g = bi.getGraphics();


        for (int i = 0; i < positions.length; i++) {
            if (positions[i] != null && positions[i].color != null
                    && positions[i].position != null) {


                Point p = new Point((int) positions[i].position.getX(),
                        (int) positions[i].position.getY());


                if (p.getX() < 0 || p.getX() > getWidth())
                    positions[i].vitesse.setX(positions[i].vitesse.getX() * -1);
                if (p.getY() < 0 || p.getY() > getHeight())

                    positions[i].vitesse.setY(positions[i].vitesse.getY() * -1);
                if (g != null) {

                    g.setColor(positions[i].color);
                    g.fillOval((int) (p.getX() - 5), (int) (p.getY() - 5), 10,
                            10);
                }
            }
        }
    }

    public void paint() {
        //afficher(bi);
    }

    public void run() {
        init(20);


        Move f = new Move();


        f.configurer(positions);


        bi = new BufferedImageAndroid(getWidth(), getHeight(), BufferedImageAndroid.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();


        while (true) {
            f.calculer();

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());


            afficher(bi);

            getGraphics().drawImage(bi, 0, 0, getWidth(), getHeight(), null);

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

}
*/