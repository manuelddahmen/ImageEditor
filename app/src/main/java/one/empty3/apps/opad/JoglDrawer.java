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

import com.jogamp.nativewindow.AbstractGraphicsDevice;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.awt.TextureRenderer;
import one.empty3.apps.opad.help.PiloteAuto;
import one.empty3.library.*;
import one.empty3.library.Polygon;
import one.empty3.library.core.nurbs.CourbeParametriquePolynomiale;
import one.empty3.library.core.nurbs.ParametricCurve;
import one.empty3.library.core.nurbs.ParametricSurface;
import one.empty3.library.core.tribase.TRIObjetGenerateur;

import javax.swing.*;
import java.awt.*;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class JoglDrawer extends Drawer implements GLEventListener {
    private GLU glu;
    private final Frame component;
    private final Animator animator;
    double INCR_AA = 0.1;
    double DISTANCE_MIN = 100;
    Timer timer;
    private final double maximize = INCR_AA / 10;
    private final double minimize = INCR_AA;
    private PositionUpdate mover;
    private Terrain terrain;
    private Bonus bonus;
    private TextRenderer renderer;
    private Vaisseau vaisseau;
    private TextureRenderer textureRenderer;
    private boolean locked;
    private Circuit circuit;
    private int BUFSIZE;
    private Point2D pickPoint;
    private PiloteAuto piloteAuto;
    private Point3D del;
    private Point3D diff;
    private GL2 gl;
    private final GLCanvas glCanvas;
    private Plotter3D plotter3D;
    private long millis;
    private long millis0;
    private boolean wasAnimating = false;

    {
        Plasma.scale = 2;
        Plasma.t_factor = 0.000001;
    }

    {
    }

    public JoglDrawer(DarkFortressGUI darkFortressGUI) {

        //getting the capabilities object of GL2 profile

        GLProfile.initSingleton();

        final GLProfile profile = GLProfile.getDefault();

        AbstractGraphicsDevice defaultDevice = GLProfile.getDefaultDevice();
        defaultDevice.open();

        GLCapabilities capabilities = new GLCapabilities(profile);

        // The canvas
        glCanvas = new GLCanvas(capabilities);
        glCanvas.setSize(640, 480);
        glCanvas.setAutoSwapBufferMode(true);
        glCanvas.setGL(gl);
        glCanvas.addGLEventListener(this);



        // Create a animator that drives canvas' display() at the specified FPS.
        animator = new Animator(this.glCanvas);
        glCanvas.setAnimator(animator);
        mover = darkFortressGUI.mover;

        //textureRenderer = new TextureRenderer();
        initFrame(darkFortressGUI);

        this.component = darkFortressGUI;

        JPanel panel = new JPanel();

        panel.setMinimumSize(new Dimension(640, 480));
        panel.setSize(640, 480);
        panel.add(glCanvas);
        component.add(panel);

        timer = new Timer();
        timer.init();


        ((JFrame)component).getContentPane().removeAll();
        ((JFrame)component).getContentPane().add(glCanvas);
    }

    @Override
    public void display(GLAutoDrawable gLDrawable) {
        if (!wasAnimating){
            animator.start();
            wasAnimating = true;
        }

        millis = System.currentTimeMillis();
        System.out.println("FPS "+(millis-millis0));
        millis0 = millis;

        try {
            if (glu == null)
                glu = GLU.createGLU();

            gl = gLDrawable.getGL().getGL2();
            //glu = GLU.createGLU();
        } catch (Exception e) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return;
        }
        if(!component.isVisible()) {
            glCanvas.setGL(gl);
            component.setVisible(true);
        }
        // Change to projection matrix.
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        glu.gluPerspective(60, 1.33, 0.01, 10.0);
        gl.glLoadIdentity();


        Camera camera;
        if (mover.getPlotter3D()!=null&&mover.getPlotter3D().isActive())
            camera = mover.getPositionMobile().calcCameraMobile();
        else
            camera = mover.getPositionMobile().calcCamera();

        Point3D pos = camera.getEye();
        Point3D dir = camera.getLookat().moins(pos);
        diff = dir.moins(pos).norme1();

        /*
        if(del0!=null) {
            if (del.moins(del0).norme() > 0)
                diff = del.moins(del0).prodVect(del).norme1();
            if (diff.norme() == 0)
                diff = Point3D.Y;
            if (diff.prodScalaire(normale0) < 0)
                diff = diff.mult(-1);
        }
        */

        //Point3D normale = /*dir.prodVect(pos);*/getTerrain()
        //        .calcNormale(pos.getX(), pos.getY()).norme1();
        Point3D posCam = pos.moins(dir.norme1());
        Point3D vertical = camera.getVerticale().getElem().norme1();
        Point3D vert2 = vertical.prodVect(dir).mult(-1);
        glu.gluLookAt(pos.get(0), pos.get(1), pos.get(2),
                        dir.get(0), dir.get(1), dir.get(2),
                vert2.prodVect(dir).get(0), vert2.get(1), vert2.get(2));
        /*if(circuit==null)
         circuit = mover.getCircuit();
         if(circuit!=null)
         draw((TRIConteneur)circuit, glu, gl);
        */
        if (toggleMenu == null)
            return;
        if (toggleMenu.isDisplayBonus()) {
            bonus.getListRepresentable().forEach(representable -> {
                Point3D center = ((TRISphere2) representable).getCoords();
                ((TRISphere2) representable).getCircle().getAxis().getElem().setCenter(terrain.p3(center));
            });
            draw(bonus, glu, gl);
        }
        if (toggleMenu.isDisplaySky())
            draw(new Ciel().getBleu(), glu, gl);

//        if (mover.getPath().size() >= 2) {
//            path = new TubulaireN2<Lines>();
//            mover.setPath(new Path());
//            Lines lines = new Lines(getMover().getPath());
//            path.curve(lines);
//            path.nbrAnneaux(100);
//            path.nbrRotations(4);
//            path.diam(0.01);
//            path.generate();
//
//            // TODO draw2(path, glu, gl);
//        }

        if (toggleMenu.isDisplayGroundGrid())
          draw(terrain, glu, gl);
        if (toggleMenu.isDisplayGround()) {
            if (terrain.isDessineMurs()) {
                displayGround(glu, gl);
            }
        }
        if (toggleMenu.isDisplayArcs() && SolPlan.class.equals(getLevel())) {
            displayArcs(glu, gl);
        }
        if (toggleMenu.isDisplayCharacter()) {
            if (getPlotter3D()!=null&&getPlotter3D().isActive()) {
                CourbeParametriquePolynomiale courbeParametriquePolynomiale = null;
//                TubulaireN2<CourbeParametriquePolynomiale> segmentDroiteTubulaireN2 = new TubulaireN2<>();
//                segmentDroiteTubulaireN2.diam(0.01);
//                segmentDroiteTubulaireN2.curve(courbeParametriquePolynomiale = new CourbeParametriquePolynomiale(new Point3D[]{getMover().getPositionMobile().calcPosition2D(),
//                        getMover().getPositionMobile().calcDirection2D()}));
//                segmentDroiteTubulaireN2.generate();
//                draw(courbeParametriquePolynomiale, glu, gl);
            } else {
                Cube object = vaisseau.getObject();
                object.setPosition(mover.calcCposition());
                draw(object, glu, gl);
            }
        }

        if (toggleMenu.isDisplayScore())
            draw("Score :  " + mover.score(), Color.WHITE, glu, gl);
        if (toggleMenu.isDisplayEnergy())
            draw("Life :  " + mover.energy(), new Dimension(30, 10), Color.GREEN, glu, gl);


        drawToggleMenu(glu, gl);

        drawTrajectory(getPlotter3D(), glu, gl);

    }

    public PiloteAuto piloteAuto() {
        return piloteAuto;
    }

    public void piloteAuto(PiloteAuto pa) {
        piloteAuto = pa;
    }

    public void color(GL2 gl, Color c) {
        gl.glColor3f(
                c.getRed() / 255f,
                c.getGreen() / 255f, c.getBlue() / 255f);
    }

    private void draw(LineSegment segd, GLU glu, GL2 gl) {
        gl.glBegin(GL2.GL_LINES);
        Point3D p1 = getTerrain().p3(segd.getOrigine());
        Point3D p2 = getTerrain().p3(segd.getExtremite());
        color(gl, new Color(segd.texture().getColorAt(0.5, 0.5)));
        gl.glVertex3f((float) (double) p1.get(0), (float) (double) p1.get(1), (float) (double) p1.get(2));
        gl.glVertex3f((float) (double) p2.get(0), (float) (double) p2.get(1), (float) (double) p2.get(2));
        gl.glEnd();
        //System.out.print("SD");
        // System.out.println("L");
    }
    /*
     public void draw(Representable rep, GLU glu, GL2 gl)
     {
     throw new UnsupportedOperationException("Objet non supporte par "+getClass().getCanonicalName());
     }*/

    public void draw(TRI tri, GLU glu, GL2 gl) {
        color(gl, new Color(tri.texture().getColorAt(0.5, 0.5)));
        for (Point3D sommet : tri.getSommet().getData1d()) {
            Point3D p = getTerrain().p3(sommet);
            gl.glVertex3f((float) (double) p.get(0),
                    (float) (double) p.get(1),
                    (float) (double) p.get(2));
        }
    }

    public void draw2(TRI tri, GLU glu, GL2 gl, boolean guard) {
        if (!guard)
            gl.glBegin(GL2.GL_TRIANGLES);
        color(gl, new Color(tri.texture().getColorAt(0.5, 0.5)));
        for (Point3D sommet : tri.getSommet().getData1d()) {
            Point3D p = sommet;
            gl.glVertex3f((float) (double) p.get(0),
                    (float) (double) p.get(1),
                    (float) (double) p.get(2));
        }
        if (!guard)
            gl.glEnd();
    }

    public void draw(TRIObjetGenerateur s, GLU glu, GL2 gl) {
        gl.glBegin(GL2.GL_TRIANGLES);
        for (int i = 0; i < s.getMaxX(); i++) {
            for (int j = 0; j < s.getMaxY(); j++) {
                TRI[] tris = new TRI[2];
                Point3D INFINI = Point3D.INFINI;
                tris[0] = new TRI(INFINI, INFINI, INFINI);
                tris[1] = new TRI(INFINI, INFINI, INFINI);
                s.getTris(i, j, tris);
                draw(tris[0], glu, gl);
                draw(tris[1], glu, gl);
            }
        }
        gl.glEnd();
    }

    public void draw2(TRIObjetGenerateur s, GLU glu, GL2 gl) {
        gl.glBegin(GL2.GL_TRIANGLES);
        for (int i = 0; i < s.getMaxX(); i++) {
            for (int j = 0; j < s.getMaxY(); j++) {
                TRI[] tris = new TRI[2];
                Point3D INFINI = Point3D.INFINI;
                tris[0] = new TRI(INFINI, INFINI, INFINI);
                tris[1] = new TRI(INFINI, INFINI, INFINI);
                s.getTris(i, j, tris);
                draw(tris[0], glu, gl);
                draw(tris[1], glu, gl);
            }
        }
        gl.glEnd();
    }

    public void draw(TRIGenerable gen, GLU glu, GL2 gl) {
        draw(gen.generate(), glu, gl);
    }

    public void draw(TRIObject gen, GLU glu, GL2 gl) {
        gen.getTriangles().forEach((TRI t) -> {
            draw(t, glu, gl);
        });

    }

    public synchronized void draw(RepresentableConteneur rc, GLU glu, GL2 gl) {
        Iterator<Representable> it = rc.iterator();
        while (it.hasNext()) {
            Representable r = null;
            try {
                r = it.next();
                if (r instanceof TRI) {
                    draw((TRI) r, glu, gl);
                } else if (r instanceof LineSegment) {
                    draw((LineSegment) r, glu, gl);
                } else if(r instanceof ParametricSurface) {
                    draw((ParametricSurface) r, glu, gl);
                }
            } catch (ConcurrentModificationException ex) {
                ex.printStackTrace();
                break;
            }

        }
    }

    private void draw(ParametricSurface s, GLU glu, GL2 gl) {
        gl.glBegin(GL2.GL_TRIANGLES);
        for (double  i = 0; i < s.getIncrU(); i++) {
            for (double j = 0; j < s.getIncrV(); j++) {
                Polygon elementSurface = s.getElementSurface(i, s.getIncrU(), j, s.getIncrV());
                Point3D INFINI = Point3D.INFINI;
                draw2( new TRI(elementSurface.getPoints().getElem(0),
                        elementSurface.getPoints().getElem(1),
                        elementSurface.getPoints().getElem(2), s.texture()
                        ), glu, gl, true);
                draw2( new TRI(elementSurface.getPoints().getElem(2),
                        elementSurface.getPoints().getElem(3),
                        elementSurface.getPoints().getElem(0), s.texture()),
                        glu, gl, true);
            }
        }
        gl.glEnd();

    }
    private void draw(Terrain t, ParametricSurface s, GLU glu, GL2 gl) {
        gl.glBegin(GL2.GL_TRIANGLES);
        for (double  i = 0; i < s.getIncrU(); i++) {
            for (double j = 0; j < s.getIncrV(); j++) {
                Polygon elementSurface = s.getElementSurface(i, s.getIncrU(), j, s.getIncrV());
                Point3D INFINI = Point3D.INFINI;
                draw2( new TRI(t.p3( elementSurface.getPoints().getElem(0)),
                        t.p3( elementSurface.getPoints().getElem(1)),
                                t.p3( elementSurface.getPoints().getElem(2))), glu, gl, true);
                draw2( new TRI(t.p3( elementSurface.getPoints().getElem(2)),
                        t.p3( elementSurface.getPoints().getElem(3)),
                                t.p3( elementSurface.getPoints().getElem(0))), glu, gl, true);
            }
        }
        gl.glEnd();

    }


    public void draw(TRIConteneur con, GLU glu, GL2 gl) {
        /*if(con.getObj()==null && con instanceof TRIGenerable)
         {
         ((TRIGenerable)con).generate();
         }*/
        Iterable<TRI> iterable = con.iterable();
        iterable.forEach((TRI t) -> {
            draw(t, glu, gl);
        });

    }

    public void draw(Cube c, GLU glu, GL2 gl) {
        TRIObject generate = c.generate();
        draw(generate, glu, gl);
    }

    public void draw(String text, Color textColor, GLU glu, GL2 gl) {
        Dimension d = new Dimension(1, 1);
        if (component instanceof JFrame) {
            d = ((JFrame) component).getSize();
        }
        renderer.beginRendering((int) d.getWidth(), (int) d.getHeight());
        renderer.setColor(1.0f, 0.2f, 0.2f, 0.8f);
        renderer.draw(text, 10, 10);
        renderer.endRendering();
    }

    public void drawToggleMenu(GLU glu, GL2 gl) {
        if (toggleMenu.isDisplayMenu()) {
            Dimension d = new Dimension(1, 1);
            if (component instanceof JFrame) {
                d = ((JFrame) component).getSize();
            }
            renderer.beginRendering((int) d.getWidth(), (int) d.getHeight());
            String[] split = toggleMenu.toString().split("\\n");
            renderer.setColor(1.0f, 0.2f, 0.2f, 0.8f);
            renderer.draw(split[0], 0, (int) d.getHeight() - 50 - 0 * 20);
            for (int i = 1; i < split.length; i++) {
                if (i - 1 == toggleMenu.getIndex()) {
                    renderer.setColor(0.2f, 0.1f, 0.2f, 0.8f);
                } else {
                    renderer.setColor(1.0f, 0.2f, 0.2f, 0.8f);
                }
                renderer.draw(split[i], 0, (int) d.getHeight() - 50 - i * 30);
            }
            renderer.endRendering();
        }
    }


    public void draw(String text, Dimension place, Color textColor, GLU glu, GL2 gl) {
        Dimension d = new Dimension(1, 1);
        if (component instanceof JFrame) {
            d = ((JFrame) component).getSize();
        }
        renderer.beginRendering((int) d.getWidth(), (int) d.getHeight());
        renderer.setColor(1.0f, 0.2f, 0.2f, 0.8f);
        renderer.draw(text, (int) (d.getWidth() - 200), 10);
        renderer.endRendering();
    }
    /*public void drawCard(Card c, GLU glu, GL2 gl)
     {
     //Buffer buffer;
     Dimension d = new Dimension(1,1);
     if(component instanceof JFrame)
     {
     d = ((JFrame)component).getSize();
     }
     //gl.glDrawPixels(0, 0, d.getWidth(), d.getHeight(),  buffer);

     }*/


    private void displayArcs(GLU glu, GL2 gl) {
        Point3D[][] arc = new Point3D[][]{
                {
                        P.n(0.5, 0.5, 0),
                        P.n(0.5 + 0.25, 0.5 + 0.75, 0.5),
                        P.n(0.5 + 0.75, 0.5 + 0.25, 0.5),
                        P.n(0.5 + 1, 0.5, 0)},
                {
                        P.n(0.5 + 1, 0.5, 0),
                        P.n(0.5 + 1, 0.5 + 0.25, 0.5),
                        P.n(0.5 + 1, 0.5 + 0.75, 0.5),
                        P.n(0.5 + 1, 0.5 + 1, 0)}
        };

//        TubulaireN2<CourbeParametriquePolynomialeBezier> courbeParametriquePolynomialeBezierTubulaireN2 = new TubulaireN2<>();
//        courbeParametriquePolynomialeBezierTubulaireN2.curve(new CourbeParametriquePolynomialeBezier(arc[0]));
//        courbeParametriquePolynomialeBezierTubulaireN2.texture(new ColorTexture(Color.GREEN));
//
//
//        TubulaireN2<CourbeParametriquePolynomialeBezier> courbeParametriquePolynomialeBezierTubulaireN22 = new TubulaireN2<>();
//        courbeParametriquePolynomialeBezierTubulaireN22.curve(new CourbeParametriquePolynomialeBezier(arc[1]));
//        courbeParametriquePolynomialeBezierTubulaireN22.texture(new ColorTexture(Color.GREEN));

        // TODO draw(courbeParametriquePolynomialeBezierTubulaireN2, glu, gl);
        // TODO draw(courbeParametriquePolynomialeBezierTubulaireN22, glu, gl);

    }

    private void displayTerrain(GLU glu, GL2 gl) {
        draw((RepresentableConteneur) terrain, glu, gl);
    }


    private void displayGround(GLU glu, GL2 gl) {
        int nbrTriReduce = 0;
        double maxDistance = 0.01;
        gl.glBegin(GL2.GL_TRIANGLES);
        for (double i = 0; i <= 1; i += INCR_AA) {
            for (double j = 0; j <= 1; j += INCR_AA) {
                final Double[][][] faces = Cube.getData();
                TRI[] tris = new TRI[12];
                //tris[12] = new TRI(new Point3D(0, 1, 0), new Point3D(1, 1, 0), new Point3D(0, 0, 0));
                //tris[13] = new TRI(new Point3D(1, 0, 0), new Point3D(1, 1, 0), new Point3D(0, 0, 0));

                int index = 0;
                int a = 0;
                for (Double[][] triRaw : faces) {


                    tris[index] = new TRI();

                    Point3D p1 = new Point3D((triRaw[0][0] + 1) / 2, (triRaw[0][1] + 1) / 2, (triRaw[0][2] + 1) / 2);
                    Point3D p2 = new Point3D((triRaw[1][0] + 1) / 2, (triRaw[1][1] + 1) / 2, (triRaw[1][2] + 1) / 2);
                    Point3D p3 = new Point3D((triRaw[2][0] + 1) / 2, (triRaw[2][1] + 1) / 2, (triRaw[2][2] + 1) / 2);
                    tris[index] = new TRI(p1, p2, p3);
                    index++;
                }

                index = 0;
                for (TRI t : tris) {
                    /*
                    if(index>=12)
                    {
                        INCR_AA = 0.01;

                    }
                    else
                    {
                        INCR_AA = 0.1;
                    }
                    */
                    Point3D[] point3D = new Point3D[6];
                    for (int p : new int[]{0, 1, 2}) {
                        Point3D[] p3 = new Point3D[]{ t.getSommet().getElem(0),
                                t.getSommet().getElem(1), t.getSommet().getElem(2)};

                        for (int coord = 0; coord < 3; coord++) {
                            switch (coord) {
                                case 0:
                                    point3D[0] = new Point3D(p3[0].get(coord), (i), (j));
                                    point3D[1] = new Point3D(p3[0].get(coord), (i + INCR_AA), (j));
                                    point3D[2] = new Point3D(p3[0].get(coord), (i + INCR_AA), (j + INCR_AA));
                                    point3D[3] = new Point3D(p3[0].get(coord), (i), (j));
                                    point3D[4] = new Point3D(p3[0].get(coord), (i), (j + INCR_AA));
                                    point3D[5] = new Point3D(p3[0].get(coord), (i + INCR_AA), (j + INCR_AA));
                                    break;
                                case 1:
                                    point3D[0] = new Point3D((i) * 2, p3[0].get(coord), (j) * 2);
                                    point3D[1] = new Point3D((i + INCR_AA) * 2, p3[0].get(coord), (j) * 2);
                                    point3D[2] = new Point3D((i + INCR_AA) * 2, p3[0].get(coord), (j + INCR_AA) * 2);
                                    point3D[3] = new Point3D((i) * 2, p3[0].get(coord), (j) * 2);
                                    point3D[4] = new Point3D((i) * 2, p3[0].get(coord), (j + INCR_AA) * 2);
                                    point3D[5] = new Point3D((i + INCR_AA) * 2, p3[0].get(coord), (j + INCR_AA) * 2);
                                    break;
                                case 2:
                                    point3D[0] = new Point3D((i) * 2, (j) * 2, p3[0].get(coord));
                                    point3D[1] = new Point3D((i + INCR_AA) * 2, (j) * 2, p3[0].get(coord));
                                    point3D[2] = new Point3D((i + INCR_AA) * 2, (j + INCR_AA) * 2, p3[0].get(coord));
                                    point3D[3] = new Point3D((i) * 2, (j) * 2, p3[0].get(coord));
                                    point3D[4] = new Point3D((i) * 2, (j + INCR_AA) * 2, p3[0].get(coord));
                                    point3D[5] = new Point3D((i + INCR_AA) * 2, (j + INCR_AA) * 2, p3[0].get(coord));
                                    break;
                            }
                            nbrTriReduce++;


                            TRI toDraw = new TRI();
                            for (int g = 0; g < 3; g++) {
                                toDraw.getSommet().setElem(getTerrain().p3(point3D[g]), g);
                            }
                            toDraw.texture(new ColorTexture(Plasma.color(i + a, j + a, time())));

                            draw2(toDraw, glu, gl, true);

                            toDraw = new TRI();
                            for (int g = 0; g < 3; g++) {
                                toDraw.getSommet().setElem(getTerrain().p3(point3D[g+3]), g);
                            }
                            toDraw.texture(new ColorTexture(Plasma.color(i + a, j + a, time())));


                            toDraw.texture(new ColorTexture(Plasma.color(i + a, j + a, time())));
                            //if(isClose(maxDistance, toDraw))
                            draw2(toDraw, glu, gl, true);
                        }


                    }
                    index++;

                }
            }
        }
        gl.glEnd();
    }

    private boolean isClose(double maxDistance, TRI toDraw) {
        return Point3D.distance(getTerrain().p3(toDraw.getSommet().getElem(0)), mover.calcCposition()) < maxDistance;
    }


    private void drawTriLines(TRI triCourant, GLU glu, GL2 gl, boolean b) {
    }


    private void draw(ParametricCurve courbeParametriquePolynomiale, GLU glu, GL2 gl) {
        double d0 = courbeParametriquePolynomiale.start();
        for (double d = courbeParametriquePolynomiale.start(); d < courbeParametriquePolynomiale.endU(); d += courbeParametriquePolynomiale.getIncrU().getElem()) {
            draw(new LineSegment(courbeParametriquePolynomiale.calculerPoint3D(d0), courbeParametriquePolynomiale.calculerPoint3D(d)), glu, gl);
            d0 = d;
        }
    }

    private void drawTrajectory(Plotter3D plotter3D, GLU glu, GL2 gl) {
        if(plotter3D==null)
            return;
        Point3D impact = plotter3D.getImpact();
        draw(new CourbeParametriquePolynomiale(new Point3D[]
                        {
                                getMover().calcCposition(),

                                getTerrain().calcCposition(impact.getX(), impact.getY())
                        })
                , glu, gl);
    }

    public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged,
                               boolean deviceChanged) {
        reshape(gLDrawable, 0, 0, glCanvas.getWidth(), glCanvas.getHeight());
    }

    @Override
    public void init(GLAutoDrawable gLDrawable) {
        /*
         * System.out.println("init() called"); GL2 gl =
         * gLDrawable.getGL().getGL2(); gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
         * gl.glShadeModel(GL2.GL_FLAT);
         */

        gl = gLDrawable.getGL().getGL2();
        gLDrawable.setGL(new DebugGL2(gl));

        // Global settings.
        gl.glEnable(GL2.GL_DEPTH_TEST);

        /*
         gl.glDepthFunc(GL2.GL_LEQUAL);
         gl.glShadeModel(GL2.GL_SMOOTH);
         gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

         */
        gl.glClearColor(0f, 0f, 0f, 1f);

        // Start animator (which should be a field).
        renderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 36));
    }

    @Override
    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width,
                        int height) {
        if(glu==null)
            glu = new GLU();
        System.out.println("reshape() called: coordArr = " + x + ", y = " + y
                + ", width = " + width + ", height = " + height);
        final GL2 gl = gLDrawable.getGL().getGL2();

        if (height <= 0) // avoid a divide by zero error!
        {
            height = 1;
        }

        final float h = (float) width / (float) height;

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();


        glu.gluPerspective(60f, h, 0.01f, 200f);

        gl.glMatrixMode(GL2.GL_MODELVIEW);

    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        System.out.println("dispose() called");
    }

    @Override
    public void setLogic(PositionUpdate m) {
        this.mover = m;

        vaisseau = new Vaisseau(mover);
        terrain = mover.getTerrain();
        bonus = new Bonus();
        mover.ennemi(bonus);
    }

    private boolean locked() {
        return locked;
    }

    private double time() {
        return timer.getTimeEllapsed();
    }

    @Override
    public LineSegment click(Point2D p) {
        GLU glul = this.glu;

        /*
         double aspect = double(glcanvas.getWidth())/double(glcanvas.getHeight());
         glu.glMatrixMode( GL_PROJECTION );
         glLoadIdentity();
         glFrustum(-near_height * aspect,
         near_height * aspect,
         -near_height,
         near_height,
         zNear,
         zFar );
         int window_y = (window_height - mouse_y) - window_height/2;
         double norm_y = double(window_y)/double(window_height/2);
         int window_x = mouse_x - window_width/2;
         double norm_x = double(window_x)/double(window_width/2);
         float y = near_height * norm_y; float coordArr = near_height * aspect * norm_x;
         float ray_pnt[4] = {0.f, 0.f, 0.f, 1.f}; float ray_vec[4] = {coordArr, y, -near_distance, 0.f};

         GLuint buffer[BUF_SIZE]; glSelectBuffer (BUF_SIZE, buffer);
         GLint hits; glRenderMode(GL_SELECT);
         glRenderMode(GL_RENDER);
         */
        return null;
    }

    public GLU getGlu() {
        return glu;
    }

    public Object getComponent() {
        return component;
    }
    /*
     * sets up selection mode, name stack, and projection matrix for picking. Then
     * the objects are drawn.
     */

    public PositionUpdate getMover() {
        return mover;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Bonus getBonus() {
        return bonus;
    }

    public TextRenderer getRenderer() {
        return renderer;
    }

    public Vaisseau getVaisseau() {
        return vaisseau;
    }

    public boolean isLocked() {
        return locked;
    }

    private void setLocked(boolean l) {
        locked = l;
    }

    public Circuit getCircuit() {
        return circuit;
    }

    public Timer getTimer() {
        return timer;
    }

    public GLCanvas getGlcanvas() {
        return glCanvas;
    }

    public int getBUFSIZE() {
        return BUFSIZE;
    }

    public Point2D getPickPoint() {
        return pickPoint;
    }

    public PiloteAuto getPiloteAuto() {
        return piloteAuto;
    }

    public Plotter3D getPlotter3D() {
        return plotter3D;
    }

    public void setPlotter3D(Plotter3D plotter3D) {
        this.plotter3D = plotter3D;
    }

    public Animator getAnimator() {
        return animator;
    }

    public void start() {
        while(isRunning()) {
            display(glCanvas);
        }
    }

    private boolean isRunning() {
        return true;
    }
}
