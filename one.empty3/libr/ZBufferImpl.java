/*
 * Copyright (c) 2024. Manuel Daniel Dahmen
 *
 *
 *    Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

/*__
 * *
 * Global license  GNU GPL v2
 * author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
package one.empty3.library;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.Image;

import androidx.constraintlayout.core.widgets.Rectangle;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import one.empty3.libs.Color;
import javaAnd.awt.Point;
import one.empty3.library.core.nurbs.ParametricCurve;
import one.empty3.library.core.nurbs.ParametricSurface;
import one.empty3.library.core.nurbs.ParametricVolume;
import one.empty3.library.core.nurbs.Point3DS;
import one.empty3.library.core.nurbs.RPv;
import one.empty3.library.core.nurbs.ThickSurface;
import one.empty3.pointset.PCont;
/*__
 * * Classe de rendu graphique
 */
public class ZBufferImpl extends Representable implements ZBuffer {

    public static final int DISPLAY_ALL = 0;
    public static final int SURFACE_DISPLAY_TEXT_QUADS = 1;
    public static final int SURFACE_DISPLAY_TEXT_TRI = 2;
    public static final int SURFACE_DISPLAY_COL_QUADS = 4;
    public static final int SURFACE_DISPLAY_COL_TRI = 8;
    public static final int SURFACE_DISPLAY_LINES = 16;
    public static final int SURFACE_DISPLAY_POINTS = 32;
    public static final int SURFACE_DISPLAY_POINTS_DEEP = 64;
    public static final int SURFACE_DISPLAY_POINTS_LARGE = 128;
    public static int CURVES_MAX_SIZE = 10000;
    public static int SURFAS_MAX_SIZE = 1000000;
    public static int CURVES_MAX_DEEP = 10;
    public static int SURFAS_MAX_DEEP = 10;
    // DEFINITIONS
    public static double INFINITY_DEEP = -1E10;
    public static Point3D INFINITY = new Point3D(0d, 0d, INFINITY_DEEP);
    public ImageMap ime;
    public Box2D box;
    protected boolean colorationActive = false;
    protected double angleX = Math.PI / 3;
    protected double angleY = Math.PI / 3;
    protected ECImage bi;
    protected int ha;
    protected int la;
    ZBufferImpl that;
    // PARAMETRES
    private float zoom = 1.05f;
    private boolean locked = false;
    // VARIABLES
    private int idImg = 0;
    private int dimx;
    private int dimy;
    private Point3D[][] Scordinate;
    private int[] Scolor;
    private long[][] Simeid;
    private double[][] Simeprof;
    private Scene currentScene;
    private int displayType = SURFACE_DISPLAY_TEXT_QUADS;

    private boolean FORCE_POSITIVE_NORMALS = true;
    private int transparent = -1;
    private Paint paint = new Paint();

    public ZBufferImpl() {
        that = this;
        scene = new Scene();
        texture(new TextureCol(-1));
    }

    public ZBufferImpl(int l, int h) {
        this();
        la = l;
        ha = h;
        dimx = la;
        dimy = ha;
        Logger.getAnonymousLogger().log(Level.INFO, "width,height(" + la + ", " + ha + ")");
        this.ime = new ImageMap(la, ha);
    }


    public ZBufferImpl(Resolution resolution) {


        this(resolution.x(), resolution.y());
    }

    public void copyResourceFiles(File destDirectory) {
    }

    protected long idImg() {
        return idImg;
    }


    public Camera camera() {

        return scene().cameraActive();
    }

    public void camera(Camera c) {

        this.scene().cameraActive(c);
    }

    public synchronized void draw() {
        draw(scene());
    }

    public Point3D rotate(Point3D p0, Representable ref) {
        return p0;
        //return camera().calculerPointDansRepere(super.rotate(p0, ref));
    }

    public synchronized void draw(Collection<Object> collection) {
        collection.forEach(new Consumer() {
            @Override
            public void accept(Object o) {
                if (o instanceof Representable) {
                    draw((Representable) o);
                } else if (o instanceof Collection)
                    draw((Collection) o);
            }
        });
    }

    public synchronized void draw(Representable r) {
        camera().ratioHorizontalAngle(dimx, dimy);
        if (r == null) {
            Logger.getAnonymousLogger().log(Level.INFO, "r is null return");
            return;
        }
        if (r.texture() != null)
            r.texture().timeNext();

        if (r instanceof Scene) {
            Scene scene = (Scene) r;
            scene.getObjets().getData1d().forEach(representable -> draw(representable));
            return;
        } else if (r instanceof RepresentableConteneur) {
            ((RepresentableConteneur) r).getListRepresentable().forEach(representable -> draw(representable));
            return;
        }

        /* OBJECTS */
        if (r instanceof Point3D) {
            Point3D p = (Point3D) r;
            ime.testDeep(p);
        } else if (r instanceof ThickSurface) {
            // Logger.getAnonymousLogger().log(Level.INFO, "Surface");
            ThickSurface n = (ThickSurface) r;
            // TODO Dessiner les bords

            for (double u = n.getStartU(); u <= n.getEndU(); u += n.getIncrU()) {
                // Logger.getAnonymousLogger().log(Level.INFO, "(u,v) = ("+u+","+")");
                for (double v = n.getStartU(); v <= n.getEndV(); v += n.getIncrV()) {
                    Point3D p1, p2, p3, p4;

                    p1 = n.calculerPoint3D(u, v);
                    p2 = n.calculerPoint3D(u + n.getIncrU(), v);
                    p3 = n.calculerPoint3D(u + n.getIncrU(), v + n.getIncrV());
                    p4 = n.calculerPoint3D(u, v + n.getIncrV());
                    switch (displayType) {
                        case DISPLAY_ALL:
                        case SURFACE_DISPLAY_COL_QUADS:
                        case SURFACE_DISPLAY_TEXT_QUADS:
                            tracerQuad(p1, p2, p3, p4, n.texture(), u, u + n.getIncrU(), v, v + n.getIncrV(), n);
                            break;
                        case SURFACE_DISPLAY_COL_TRI:
                        case SURFACE_DISPLAY_TEXT_TRI:
                            tracerTriangle(
                                    n.calculerPoint3D(u, v),
                                    n.calculerPoint3D(u + n.getIncrU(), v),
                                    n.calculerPoint3D(u + n.getIncrU(),
                                            v + n.getIncrV()),
                                    n.texture(),
                                    u, v, u + n.getIncrU(), v + n.getEndV());
                            tracerTriangle(
                                    n.calculerPoint3D(u + n.getIncrU(),
                                            v + n.getIncrV()),
                                    n.calculerPoint3D(u, v + n.getIncrV()),
                                    n.calculerPoint3D(u, v),
                                    n.texture(),
                                    u + n.getIncrU(), v + n.getEndV(),
                                    u, v + n.getIncrV()
                            );
                            break;
                        case SURFACE_DISPLAY_LINES:
                            tracerLines(p1, p2, p3, p4, n.texture(), u, u + n.getIncrU(), v, v + n.getIncrV(), n);
                            break;
                        case SURFACE_DISPLAY_POINTS:
                            ime.testDeep(p1);
                            ime.testDeep(p2);
                            ime.testDeep(p3);
                            ime.testDeep(p4);
                            break;
                    }
                }
            }
        } else if (r instanceof TRI) {
            //System.out.print("Draw TRI");
            TRI tri = (TRI) r;
            if (displayType == SURFACE_DISPLAY_LINES) {
                for (int i = 0; i < 3; i++)
                    line(rotate(tri.getSommet().getElem(i), r),
                            rotate(tri.getSommet().getElem((i + 1) % 3), r)
                            , tri.texture);
            } else if (displayType == SURFACE_DISPLAY_POINTS) {
                for (int i = 0; i < 3; i++)
                    ime.testDeep(rotate(tri.getSommet().getElem(i), r)
                            , tri.texture);
            } else {
                tracerTriangle(rotate(tri.getSommet().getElem(0), r),
                        rotate(tri.getSommet().getElem(1), r),
                        rotate(tri.getSommet().getElem(2), r)
                        , tri.texture());
                //System.out.print("Triangle");
            }
        } else if (r instanceof ParametricSurface) {
            ParametricSurface n = (ParametricSurface) r;
            // Logger.getAnonymousLogger().log(Level.INFO, "Surface");
            //Logger.getAnonymousLogger().log(Level.INFO, "class" + n.getClass());
            // TODO Dessiner les bords
            for (double u = n.getStartU(); u + n.getIncrU() <= n.getEndU(); u += n.getIncrU()) {
                // Logger.getAnonymousLogger().log(Level.INFO, "(u,v) = ("+u+","+")");
                for (double v = n.getStartV(); v + n.getIncrV() <= n.getEndV(); v += n.getIncrV()) {
                    Point3D p1, p2, p3, p4;
                    double u2 = u + n.getIncrU(), v2 = v + n.getIncrV();
                    if (u > n.getEndU() - n.getIncrU()) {
                        Point3D point3D = new Point3D(u2, v2, 0.0);
                        point3D = n.getTerminalU().data0d.result(point3D);
                        u2 = point3D.get(0);
                        v2 = point3D.get(1);
                    }
                    if (v > n.getEndV() - n.getIncrV()) {
                        Point3D point3D = new Point3D(u2, v2, 0.0);
                        point3D = n.getTerminalU().data0d.result(point3D);
                        u2 = point3D.get(0);
                        v2 = point3D.get(1);
                    }

                    p1 = n.calculerPoint3D(u, v);
                    p2 = n.calculerPoint3D(u2, v);
                    p3 = n.calculerPoint3D(u2, v2);
                    p4 = n.calculerPoint3D(u, v2);
                    if (n instanceof HeightMapSurface) {
                        Point3D n1, n2, n3, n4;
                        HeightMapSurface h = (HeightMapSurface) n;
                        n1 = n.calculerNormale3D(u, v);
                        n2 = n.calculerNormale3D(u2, v);
                        n3 = n.calculerNormale3D(u2, v2);
                        n4 = n.calculerNormale3D(u, v2);
                        p1 = p1.plus(n1.norme1().mult(h.height(u, v)));
                        p2 = p2.plus(n2.norme1().mult(h.height(u2, v)));
                        p3 = p3.plus(n3.norme1().mult(h.height(u2, v2)));
                        p4 = p4.plus(n4.norme1().mult(h.height(u, v2)));
                    }
                    if (displayType == SURFACE_DISPLAY_POINTS || displayType == SURFACE_DISPLAY_POINTS_DEEP) {
                        ime.testDeep(p1, n.texture(), u, v, n);
                        if (displayType == SURFACE_DISPLAY_POINTS_DEEP) {
                            double v1p = maxDistance(camera().coordonneesPoint2D(p1, this), camera().coordonneesPoint2D(p2, this),
                                    camera().coordonneesPoint2D(p3, this), camera().coordonneesPoint2D(p4, this));
                            if (v1p > 1 && v1p < la && v1p < ha) {
                                int i = 0;
                                int j = 0;
                                for (i = 0; i < v1p; i += 1)
                                    for (j = 0; j < v1p; j += 1) {
                                        double u2p = u2 / (1 + v1p) * i;
                                        double v2p = v2 / (1 + v1p) * j;
                                        ime.testDeep(n.calculerPoint3D(u2, v2),
                                                n.texture(), u2, v2, n);
                                    }
                            }
                        } else if (displayType == SURFACE_DISPLAY_POINTS_LARGE) {
                            tracerQuad(p1, p2, p3, p4, n.texture(), u, u2, v, v2, n);
                        }
                    } else if (displayType == SURFACE_DISPLAY_LINES) {
                        tracerLines(p1, p2, p3, p4, n.texture(), u, u2, v, v2, n);
                    } else if (displayType == SURFACE_DISPLAY_COL_TRI ||
                            displayType == SURFACE_DISPLAY_TEXT_TRI) {
                        tracerTriangle(p1, p2, p3, n.texture(), u, v, u2, v2);
                        tracerTriangle(p3, p4, p1, n.texture(), u2, v2, u, v);


                    } else {
                        if (p1 != null && p2 != null && p3 != null && p4 != null) {
                            tracerQuad(p1, p2, p3, p4, n.texture(), u, u2, v, v2, n);
                        }
                    }
                }

            }
        } else if (r instanceof TRIGenerable) {
            r = ((TRIGenerable) r).generate();
            draw(r);

        } else if (r instanceof PGenerator) {
            r = ((PGenerator) r).generatePO();
            draw(r);
        } else if (r instanceof TRIConteneur) {
            r = ((TRIConteneur) r).getObj();
            draw(r);
        } else
            // OBJETS
            if (r instanceof TRIObject) {
                TRIObject o = (TRIObject) r;
                Logger.getAnonymousLogger().log(Level.INFO, "Objets triangle n°" + ((TRIObject) r).getTriangles().size());
                for (TRI t : o.getTriangles()) {

                    draw(t);

                }
            } else if (r instanceof Point3DS) {
                Point3D p = ((Point3DS) r).calculerPoint3D(0);
                ime.testDeep(rotate(p, r), r.texture());
            } else if (r instanceof LineSegment) {
                LineSegment s = (LineSegment) r;
                Point3D pO = s.getOrigine();
                Point3D pE = s.getExtremite();
                line(pO, pE, s.texture());
            } else if (r instanceof BezierCubique) {
                BezierCubique b = (BezierCubique) r;
                int nt = largeur() / 10;
                Point3D p0 = b.calculerPoint3D(0.0);
                for (double t = 0; t < 1.0; t += 1.0 / nt) {
                    try {
                        Point3D p1 = b.calculerPoint3D(t);
                        line(rotate(p0, r), rotate(p1, r), b.texture());
                        p0 = p1;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else if (r instanceof BezierCubique2D) {
                BezierCubique2D b = (BezierCubique2D) r;
                int i1 = BezierCubique2D.DIM1, i2 = BezierCubique2D.DIM2;
                for (int i = 0; i < i1; i++) {
                    for (int j = 0; j < i2; j++) {
                        double tx = (Math.max(i - 1, 0)) * 1d / i1;
                        double ty = (Math.max(j - 1, 0)) * 1d / i2;
                        draw(new one.empty3.library.Polygon(new Point3D[]{
                                rotate(b.calculerPoint3D(tx, (j) * 1d / i2), r),
                                rotate(b.calculerPoint3D((i) * 1d / i1, (j) * 1d / i2), r),
                                rotate(b.calculerPoint3D((i) * 1d / i1, ty), r),
                                rotate(b.calculerPoint3D(tx, ty), r)},
                                b.texture()));
                    }
                }
            } else if (r instanceof PCont) {
                PCont b = (PCont) r;
                b.getPoints().forEach(o -> ime.testDeep(rotate((Point3D) o, b)
                        , ((Point3D) o).texture().getColorAt(0, 0)));
            } else if (r instanceof POConteneur) {
                POConteneur c = (POConteneur) r;
                for (Point3D p : c.iterable()) {
                    {
                        ime.testDeep(rotate(p, r), p.texture());
                    }
                }
            } else if (r instanceof TRIConteneur) {
                for (TRI t : ((TRIConteneur) r).iterable()) {
                    {
                        draw(t);
                    }
                }

            } else if (r instanceof ParametricCurve) {
                ParametricCurve n = (ParametricCurve) r;
                double incr = n.getIncrU().getElem();
                for (double u = n.start(); u <= n.endU(); u += incr) {
                    if (n.isConnected() && displayType != SURFACE_DISPLAY_POINTS) {
                        line(
                                n.calculerPoint3D(u),
                                n.calculerPoint3D(u + incr),
                                n.texture(), u, u + incr, n);
                    } else {
                        ime.testDeep(n.calculerPoint3D(u), n.texture().getColorAt(0.5, 0.5));
                    }
                }

            } else if (r instanceof Polygon) {
                Polygon p = (Polygon) r;
                List<Point3D> points = p.getPoints().getData1d();
                int length = points.size();
                Point3D centre = Point3D.O0;
                for (int i = 0; i < points.size(); i++)
                    centre = centre.plus(points.get(i));
                centre = centre.mult(1.0 / points.size());
                for (int i = 0; i < length; i++) {
                    if (getDisplayType() <= SURFACE_DISPLAY_COL_TRI) {
                        draw(new TRI(points.get(i), points.get((i + 1) % points.size()), centre, p.texture()));
                    } else {
                        line(points.get((i % length)), points.get((i + 1) % length), p.texture);
                    }
                }
            } else if (r instanceof RPv) {
                drawElementVolume(((RPv) r).getRepresentable(), (RPv) r);
            }

    }

    public int getColorAt(android.graphics.Point p) {
        return 0;
    }


    private void tracerLines(Point3D p1, Point3D p2, Point3D p3, Point3D p4, ITexture texture, double u1, double v1,
                             double u2, double v2, ParametricSurface n) {
        line(p1, p2, texture, u1, v1, u2, v1, n);
        line(p2, p3, texture, u2, v1, u2, v2, n);
        line(p3, p4, texture, u2, v2, u1, v2, n);
        line(p4, p1, texture, u1, v2, u1, v1, n);
    }


    public double echelleEcran() {
        return box.echelleEcran();
    }

    public int getColorAt(Point p) {
        if (ime.getIME().getElementProf((int) p.getX(), (int) p.getY()) <= INFINITY_DEEP) {
            return ime.getIME().getElementCouleur((int) p.getX(), (int) p.getY());
        } else {
            return Color.TRANSPARENT;
        }
    }

    public int[] getData() {
        return Scolor;
    }

    public ZBuffer getInstance(int x, int y) {
        return new ZBufferImpl(x, y);
    }

    /*__
     * @return hauteur du zbuffer
     */
    public int hauteur() {
        return ha;
    }

    public boolean checkScreen(android.graphics.Point p1) {
        return false;
    }

    @Override
    public void setDimension(int width, int height) {
        la = width;
        ha = height;
    }

    public Bitmap image() {
        bi = null;
        final AtomicReference<Bitmap> bi2 = new AtomicReference<>(
                Bitmap.createBitmap(la, ha, Bitmap.Config.ARGB_8888, true)

        );
        while (bi2.get() == null) {
            bi2.set(Bitmap.createBitmap(la, ha, Bitmap.Config.ARGB_8888, true));

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (bi2.get() != null) {

            for (int i = 0; i < la; i++) {
                for (int j = 0; j < ha; j++) {
                    int elementCouleur = ime.getIME().getElementCouleur(i, j);
                    bi2.get().setPixel(i, j, elementCouleur);

                }
            }
            this.bi = new ECImage(bi2.get());
            return bi.getBitmap();
        } else throw new UnsupportedOperationException("ZBufferImpl::image() :  Unexpected result.");
    }

    public Bitmap imageInverseX() {
        bi = null;
        final AtomicReference<Bitmap> bi2 = new AtomicReference<>(
                Bitmap.createBitmap(la, ha, Bitmap.Config.ARGB_8888, true)

        );
        while (bi2.get() == null) {
            bi2.set(Bitmap.createBitmap(la, ha, Bitmap.Config.ARGB_8888, true));

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (bi2.get() != null) {

            for (int i = 0; i < la; i++) {
                for (int j = 0; j < ha; j++) {
                    int elementCouleur = ime.getIME().getElementCouleur(i, j);
                    bi2.get().setPixel(i, j, elementCouleur);

                }
            }
            this.bi = new ECImage(bi2.get());
            return bi.getBitmap();
        } else throw new UnsupportedOperationException("ZBufferImpl::image() :  Unexpected result.");
    }

    public ECImage imageInvX() {
        ECImage bi2 = new ECImage(la, ha);
        for (int i = 0; i < la; i++) {
            for (int j = 0; j < ha; j++) {
                int elementCouleur = ime.ime.getElementCouleur(i, j);
                if (ime.getIME() != null && ime.getIME().getElementPoint(i, j).equals(INFINITY)) {
                    //elementCouleur = Color.TRANSLUCENT;
                }
                bi2.setRGB(la - i - 1, j, elementCouleur);

            }
        }
        this.bi = bi2;
        return bi2;
    }

    //??
    public Bitmap image2() {
        //return image2();

//        Image bi = new Image(la, ha, Image.TYPE_INT_RGB);
//        bi.setRGB(0, 0, la, ha, getData(), 0, la);
//        return new ECImage(bi);
        return image();

    }

    public boolean isLocked() {
        return locked;
    }

    public void isobox(boolean isBox) {
    }


    /*__
     * @return largeur du zbuffer
     */
    public int largeur() {
        return la;
    }

    @Override
    /*__
     * @param p1 first point
     * @param p2 second point
     * @param t  colour of de la line
     */
    public void line(Point3D p1, Point3D p2, ITexture t) {
        Point x1 = camera().coordonneesPoint2D(p1, this);
        Point x2 = camera().coordonneesPoint2D(p2, this);
        if (x1 == null || x2 == null || (!checkScreen(x1) || !checkScreen(x2))) {
            return;
        }
        Point3D n = p1.moins(p2).norme1();
        double itere = Math.max(Math.abs(x1.getX() - x2.getX()), Math.abs(x1.getY() - x2.getY())) * 4 + 1;
        for (int i = 0; i < itere; i++) {
            Point3D p = p1.plus(p2.moins(p1).mult(i / itere));
            ime.testDeep(p, t.getColorAt(0.5, 0.5));
        }

    }

    public void line(Point3D p1, Point3D p2, ITexture t, double u, double u1, ParametricCurve curve) {
        Point x1 = camera().coordonneesPoint2D(p1, this);
        Point x2 = camera().coordonneesPoint2D(p2, this);
        if (x1 == null && x2 == null || (!checkScreen(x1) || !checkScreen(x2))) {
            return;
        }
        Point3D n = p1.moins(p2).norme1();
        double iterate = Math.max(Math.abs(x1.getX() - x2.getX()), Math.abs(x1.getY() - x2.getY())) * 4 + 1;
        for (int i = 0; i < iterate; i++) {
            Point3D p = p1.plus(p2.moins(p1).mult(i / iterate));
            double u0 = i / iterate;
            if (curve != null)
                p = curve.calculerPoint3D(u0);
            ime.testDeep(p, t, u, 0.0, curve);
        }

    }

    public void line(Point3D p1, Point3D p2, ITexture texture, double u1, double v1, double u2, double v2,
                     ParametricSurface surface) {
        // TODO Check
        Point x1 = camera().coordonneesPoint2D(p1, this);
        Point x2 = camera().coordonneesPoint2D(p2, this);
        if (x1 == null && x2 == null || (!checkScreen(x1) || !checkScreen(x2))) {
            return;
        }
        Point3D n = p2.moins(p1).norme1();
        double itere = Math.sqrt((x1.getX() - x2.getX()) * (x1.getX() - x2.getX())
                + (x1.getY() - x2.getY()) * (x1.getY() - x2.getY())) + 1;
        for (int i = 0; i < itere; i++) {
            Point3D p = p1.plus(n.mult(i / itere));
            double u = u1 + i / itere * (u2 - u1);
            double v = v1 + i / itere * (v2 - v1);
            if (surface != null) {
                //p = surface.calculerPoint3D(u2, v2);
                ime.testDeep(p, texture, u, v, surface);
            } else {
                ime.testDeep(p, texture.getColorAt(u, v));
            }
        }
    }


    public boolean lock() {
        if (locked) {
            return false;
        }
        locked = true;
        return true;
    }

    @Override
    public void plotPoint(Point3D p, android.graphics.Color c) {

    }

    public Lumiere lumiereActive() {
        return currentScene.lumiereActive();
    }

    public double[][] map() {
        double[][] Map = new double[la][ha];

        for (int i = 0; i < la; i++) {
            for (int j = 0; j < ha; j++) {
                if (ime.getIME().getElementPoint(i, j) != null) {
                    Map[i][j] = ime.getIME().getElementPoint(i, j).getZ();
                } else {
                    Map[i][j] = INFINITY_DEEP;
                }
            }
        }
        return Map;

    }

    private double maxDistance(Point p1, Point p2, Point p3) {
        double max = Math.max(Math.max(Point.distance(p1.x, p1.y, p2.x, p2.y), Point.distance(p2.x, p2.y, p3.x, p3.y)),
                Point.distance(p3.x, p3.y, p1.x, p1.y));
        return max;
    }


    public double maxDistance(Point p1, Point p2, Point p3, Point p4) {
        return Math
                .max(Math.max(Math.max(Point.distance(p1.x, p1.y, p2.x, p2.y), Point.distance(p2.x, p2.y, p3.x, p3.y)),
                        Point.distance(p3.x, p3.y, p4.x, p4.y)), Point.distance(p4.x, p4.y, p1.x, p1.y));
    }

    public void testDeep(Point3D pFinal, ITexture texture, double u, double v, ParametricSurface n) {
        ime.testDeep(pFinal, texture, u, v, n);
    }

    public int la() {
        return la;
    }

    public int ha() {
        return ha;
    }


    public void itereMaxDist(List<Double> points, ParametricCurve pc, double pStart, double pEnd, ParametricVolume v) {
        Point3D p2start = v.calculerPoint3D(pc.calculerPoint3D(pStart));
        Point3D p2End = v.calculerPoint3D(pc.calculerPoint3D(pEnd));
        double dist = Point2D.dist(
                new Point2D(camera().coordonneesPoint2D(p2start, this)),
                new Point2D(camera().coordonneesPoint2D(p2End, this)));
        if (dist <= 1.0) {
            points.add(pStart);
            points.add(pEnd);
            ;
        } else {
            for (int i = 0; i < 10; i++) {
                itereMaxDist(points, pc, pStart + (pEnd - pStart) * i / 10.0, pStart + (pEnd - pStart) * (i + 1) / 10.0, v);
            }
        }
    }

    public void itereMaxDist(List<Double[]> polygons, ParametricSurface ps,
                             double u0, double u1, double v0, double v1, ParametricVolume v) {
        Point3D p1 = v.calculerPoint3D(ps.calculerPoint3D(u0, v0));
        Point3D p2 = v.calculerPoint3D(ps.calculerPoint3D(u1, v0));
        Point3D p3 = v.calculerPoint3D(ps.calculerPoint3D(u1, v1));
        Point3D p4 = v.calculerPoint3D(ps.calculerPoint3D(u0, v1));
        double dist = maxDistance(camera().coordonneesPoint2D(p1, this), camera().coordonneesPoint2D(p2, this),
                camera().coordonneesPoint2D(p3, this), camera().coordonneesPoint2D(p4, this)
        );
        if (dist <= 1.0) {
            polygons.add(new Double[]{u0, u1, v0, v1});
        } else {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    itereMaxDist(polygons, ps,
                            u0 + (u1 - u0) * i / 10.0, u0 + (u1 - u0) * (i + 1) / 10.0, v0 + (v1 - v0) * j / 10.0, v0 + (v1 - v0) * (j + 1) / 10.0, v);
                }
            }
        }
    }

    public void plotPoint(Color color, Point3D p) {
        if (p != null && color != null) {
            ime.testDeep(p, color);
        }

    }

    public void plotPoint(Point3D p) {
        if (p != null && p.texture() != null) {
            ime.dessine(p, p.texture());
        }
    }

    public void plotPoint(Point3D p, Color c) {
        if (p != null && c != null) {
            ime.dessine(p, c);
        }
    }

    public Image rendu() {
        return null;
    }

    public int resX() {
        return largeur();
    }

    public int resY() {
        return hauteur();
    }

    public Scene scene() {
        return currentScene;
    }

    public void scene(Scene s) {
        this.currentScene = s;
        this.texture(s.texture());

    }

    public void setAngles(double angleXRad, double angleYRad) {
        this.angleX = angleXRad;
        this.angleY = angleYRad;
    }

    @Deprecated
    public void setColoration(boolean a) {
        this.colorationActive = a;
    }

    public void next() {
        if (texture() instanceof TextureMov) {
            ((TextureMov) texture()).nextFrame();
        }
        idImg++;
    }

    public void testDeep(Point3D p, Color c) {
        ime.testDeep(p, c);
        ime.testDeep(p, c);
    }

    @Override
    public void testDeep(Point3D p, int c) {
        ime.testDeep(p, c);
        ime.testDeep(p, c);

    }

    public void testDeep(Point3D p) {
        if (p != null && p.texture() != null) {
            ime.testDeep(p, p.texture().getColorAt(0., 0.));
        }
    }

    @Override
    public void testDeep(Point3D p, android.graphics.Color c) {

    }

    public void testPoint(Point3D p, Color c) {
        int cc = c.getRGB();
        cc = scene().lumiereActive().getCouleur(c.getRGB(), p, p.getNormale());
        ime.testDeep(p, cc);
        ime.testDeep(p, cc);
    }

    private void tracerAretes(Point3D point3d, Point3D point3d2, Color c) {
        Point p1 = camera().coordonneesPoint2D(point3d, this);
        Point p2 = camera().coordonneesPoint2D(point3d2, this);
        if (p1 == null || p2 == null || (!checkScreen(p1) || !checkScreen(p2))) {
            return;
        }
        double iteres = Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY() + 1);
        for (double a = 0; a < 1.0; a += 1 / iteres) {
            Point pp = new Point (p1);
            Point3D p = point3d.mult(a).plus(point3d2.mult(1 - a));
            pp.setLocation(p1.getX() + (int) (a * (p2.getX() - p1.getX())),
                    p1.getY() + (int) (a * (p2.getY() - p1.getY())));
            ime.testDeep(p, c.getRGB());

        }

    }


    public void tracerLumineux() {
        throw new UnsupportedOperationException("Not supported yet."); // To
        // change
        // body
        // of
        // generated
        // methods,
        // choose
        // Tools
        // |
        // Templates.
    }

    public double mathUtilPow2(Point p1, Point p2) {
        return Math.sqrt(
                ((p1.getX() - p2.getX()) * (p1.getX() - p2.getX())) +
                        ((p1.getY() - p2.getY()) * (p1.getY() - p2.getY()))
        );
    }

    public void tracerTriangle(Point3D pp1, Point3D pp2, Point3D pp3,
                               ITexture t,
                               double u0, double v0, double u1, double v1) {
        if (camera() == null || pp1 == null || pp2 == null || pp3 == null)
            return;
        Point p1 = camera().coordonneesPoint2D(pp1, this);
        Point p2 = camera().coordonneesPoint2D(pp2, this);
        Point p3 = camera().coordonneesPoint2D(pp3, this);
        if (!checkScreen(p1) || !checkScreen(p2) || !checkScreen(p3)) {
            return;
        }
        Point3D[] uvs = new Point3D[]
                {Point3D.n(u0, v0, 0.0), Point3D.n(u1, v0, 0.0), Point3D.n(u1, v1, 0.0),
                        Point3D.n(u0, v1, 0.0)};
        Point3D n = pp1.moins(pp2).prodVect(pp3.moins(pp2)).norme1();
        int col = t.getColorAt(u0, v0);

        double iteres1 = 1.0 / (1 + mathUtilPow2(p1, p2));
        for (double a = 0; a < 1.0; a += iteres1) {
            Point3D p3a = pp1.plus(pp2.moins(pp1).mult(a));
            Point3D uv3a = uvs[0].plus(uvs[1].moins(uvs[0]).mult(a));
            Point pp = camera().coordonneesPoint2D(p3a, this);
            if (pp != null) {
                double iteres2 = 1.0 / (1 + mathUtilPow2(p3, pp));
                Point3D p3ab;
                for (double b = 0; b <= 1.0/*Math.sqrt(p3a.moins(pp3).norme()*p3a.moins(pp3).norme()
                        -pp2.moins(pp1).norme()*pp2.moins(pp1).norme())>=b*/; b += iteres2) {
                    p3ab = p3a.plus(pp3.moins(p3a).mult(b));
                    Point3D uv3ab = uv3a.plus(uvs[2].moins(uv3a).mult(b));
                    // Corriger la méthode.
                    p3ab.setNormale(n);
                    // Point p22 = coordonneesPoint2D(p);
                    if (displayType <= SURFACE_DISPLAY_TEXT_TRI) {
                        //ime.testDeep(p3ab, t.getColorAt(u0 + a * (u1 - u0), v0 + b * (v1 - v0)));
                        ime.testDeep(p3ab, t.getColorAt(uv3ab.getX(), uv3ab.getY()));
                    } else if (displayType >= SURFACE_DISPLAY_COL_TRI)
                        ime.testDeep(p3ab, col);
                    // LINES, POINTS;
                }
            }
        }
    }

    public boolean checkScreen(Point p1) {
        if (p1 != null && p1.getX() >= 0d && p1.getY() < la
                && p1.getY() >= 0d && p1.getY() < ha)
            return true;
        return false;

    }

    public void tracerQuad(Point3D pp1, Point3D pp2, Point3D pp3, Point3D pp4, ITexture texture, double u0, double u1,
                           double v0, double v1, ParametricSurface n) {


        Point p1, p2, p3, p4;
        p1 = camera().coordonneesPoint2D(pp1, this);
        p2 = camera().coordonneesPoint2D(pp2, this);
        p3 = camera().coordonneesPoint2D(pp3, this);
        p4 = camera().coordonneesPoint2D(pp4, this);

        int checked = 0;
        if (!checkScreen(p1))
            checked++;
        if (!checkScreen(p2))
            checked++;
        if (!checkScreen(p3))
            checked++;
        if (!checkScreen(p4))
            checked++;
        if (p1 == null || p2 == null || p3 == null || p4 == null || checked > 0)
            return;


        int col = texture.getColorAt(u0, v0);

        TRI triBas = new TRI(pp1, pp2, pp3, texture);
        Point3D normale = triBas.normale();
        double inter = 1 / (maxDistance(p1, p2, p3, p4) + 1) / 3;
        for (double a = 0; a < 1.0; a += inter) {
            Point3D pElevation1 = pp1.plus(pp1.mult(-1d).plus(pp2).mult(a));
            Point3D pElevation2 = pp4.plus(pp4.mult(-1d).plus(pp3).mult(a));
            for (double b = 0; b < 1.0; b += inter) {
                Point3D pFinal = (pElevation1.plus(pElevation1.mult(-1d).plus(pElevation2).mult(b)));
                pFinal.setNormale(normale);
                pFinal.texture(texture);
                pFinal.setNormale(n.calculerNormale3D(u0 + (u1 - u0) * a, v0 + (v1 - v0) * b));
                if (n != null) {
                    if (displayType == DISPLAY_ALL) {
                        pFinal = n.calculerPoint3D(u0 + (u1 - u0) * a, v0 + (v1 - v0) * b);
                        ;
                        pFinal.texture(texture);
                    } else {
                        pFinal.setNormale(normale);
                        pFinal.texture(texture);

                    }
                }
                if (displayType == SURFACE_DISPLAY_POINTS_LARGE) {
                    double u = u0 + (u1 - u0) * a;
                    double v = v0 + (v1 - v0) * b;
                    ime.testDeep(pFinal, n.texture().getColorAt(u, v));
                } else if (displayType <= SURFACE_DISPLAY_TEXT_QUADS) {
                    double u = u0 + (u1 - u0) * a;
                    double v = v0 + (v1 - v0) * b;
                    ime.testDeep(pFinal, n.texture(),
                            u, v, n);
                } else {
                    ime.testDeep(pFinal, col);

                }
            }
        }

    }

    public void tracerTriangle(Point3D pp1, Point3D pp2, Point3D pp3, ITexture c) {
        Point p1, p2, p3;
        p1 = camera().coordonneesPoint2D(pp1, this);
        p2 = camera().coordonneesPoint2D(pp2, this);
        p3 = camera().coordonneesPoint2D(pp3, this);
        if (p1 == null || p2 == null || p3 == null) {
            return;
        }

        Point3D n = (pp3.moins(pp1)).prodVect(pp2.moins(pp1)).norme1();

        int checked = 0;
        if (!checkScreen(p1))
            checked++;
        if (!checkScreen(p2))
            checked++;
        if (!checkScreen(p3))
            checked++;
        if (checked > 0)
            return;
        double iteres1 = 1.0 / (maxDistance(p1, p2, p3) + 1) / 3;
        for (double a = 0; a < 1.0; a += iteres1) {
            Point3D p11 = pp1.plus(pp1.mult(-1d).plus(pp2).mult(a));
            double iteres2 = 1.0 / maxDistance(p1, p2, p3) / 3;
            for (double b = 0; b < 1.0; b += iteres2) {
                Point3D p21 = p11.plus(p11.mult(-1d).plus(pp3).mult(b));
                p21.setNormale(n);
                p21.texture(c);
                ime.testDeep(p21);
            }
        }
    }

    public boolean unlock() {
        if (!locked) {
            return false;
        }
        locked = false;
        return true;
    }

    public void zoom(float z) {
        if (z > 0) {
            zoom = z;
        }
    }

    @Override
    public ITexture backgroundTexture() {
        return texture();
    }

    public void couleurDeFond(ITexture couleurFond) {
    }

    public void backgroundTexture(ITexture texture) {
        if (texture != null) {
            texture(texture);
            applyTex();
        }
    }

    public void applyTex() {
        if (texture instanceof TextureMov) {
            for (int i = 0; i < la; i++) {
                for (int j = 0; j < ha; j++) {
                    ime.ime.setElementCouleur(i, j, texture().getColorAt(1.0 * i / la, 1.0 * j / ha));
                }
            }
        }
    }

    public void dessine(Point3D p, ITexture texture) {
        ime.dessine(p, texture.getColorAt(0.5, 0.5));
    }

    public Point3D clickAt(double x, double y) {
        return clickAt((int) (x * largeur()), (int) y * hauteur());
    }

    /*__
     *
     * @param x Coordonnees de l'image ds ZBuffer
     * @param y Coordonnees de l'image ds ZBuffer
     * @return Point3D avec texture. Si vide Point3D.INFINI
     */
    public Point3D clickAt(int x, int y) {
        Point3D p = ime.getIME().getElementPoint(x, y);
        p.texture(new TextureCol(ime.getIME().getElementCouleur(x, y)));
        return p;
    }

    /*__
     *
     * @param x Coordonnees de l'image ds ZBuffer
     * @param y Coordonnees de l'image ds ZBuffer
     * @return Point3D avec texture. Si vide Point3D.INFINI
     */
    public Representable representableAt(int x, int y) {
        Representable p = ime.getIME().getElementRepresentable(x, y);
        return p;
    }

    /*__
     *
     * @param p point 3D à inverser dans le repère de la caméra
     * @param camera Caméra null="this.camera()"
     * @return point3d inversion
     *
     * P = M(P-E)
     * MT p' = P-E
     * P = MT p' + E
     */
    public Point3D invert(Point3D p, Camera camera, double returnedDist) {
        p = new Point3D(p);
        p.setX(p.getX() * returnedDist);
        p.setY(p.getY() * returnedDist);
        //p.setX(p.getX()-0.5);
        //p.setY(-p.getY()-0.5);
        p.setZ(returnedDist);
        return camera.getMatrice().tild()
                .mult(p).plus(camera.getEye());
    }

    public int getDisplayType() {
        return displayType;
    }

    public void setDisplayType(int displayType) {
        this.displayType = displayType;
    }

    public int idz() {
        return idImg;
    }

    public int isTranparent() {
        return transparent;
    }

    public void setTransparent(int mTransparent) {
        this.transparent = mTransparent;
    }

    public void drawElementVolume(Representable representable, ParametricVolume volume) {
        if (representable instanceof ParametricSurface) {
            ParametricSurface ps = (ParametricSurface) representable;
            List<Double[]> doubles = new ArrayList<>();
            itereMaxDist(doubles, ps, 0., 1., 0., 1., volume);


            // Tracer les points
            doubles.forEach(new Consumer<Double[]>() {
                @Override
                public void accept(Double[] doubles) {
                    tracerQuad(
                            ps.calculerPoint3D(doubles[0], doubles[2]),
                            ps.calculerPoint3D(doubles[1], doubles[2]),
                            ps.calculerPoint3D(doubles[1], doubles[3]),
                            ps.calculerPoint3D(doubles[0], doubles[3]),
                            ps.texture(),
                            doubles[0], doubles[1], doubles[2], doubles[3], ps
                    );
                }
            });

        } else if (representable instanceof ParametricCurve) {
            ParametricCurve pc = (ParametricCurve) representable;
            List<Double> doubles = new ArrayList<>();
            itereMaxDist(doubles, pc, 0., 1., volume);


            double start = doubles.get(0);
            double end = doubles.get(1);
            for (int i = 0; i < doubles.size(); i++) {
                line(pc.calculerPoint3D(start), pc.calculerPoint3D(end), pc.texture(), start, end, pc);
                start = end;
                end += doubles.get(i + 1);
            }


        } else if (representable instanceof RepresentableConteneur) {
            ((RepresentableConteneur) representable).getListRepresentable().forEach(new Consumer<Representable>() {
                @Override
                public void accept(Representable representable) {
                    drawElementVolume(representable, volume);
                }
            });
        } else if (representable instanceof Point3D) {
            draw(volume.calculerPoint3D((Point3D) representable));
        } else if (representable instanceof TRI) {
            TRI t = (TRI) representable;
            tracerTriangle(t.getSommet().getElem(0), t.getSommet().getElem(1), t.getSommet().getElem(2), t.texture());
        } else if (representable instanceof Polygon) {
            Polygon t = (Polygon) representable;
            for (int i = 0; i < t.getPoints().getData1d().size(); i++)
                tracerTriangle(t.getPoints().getElem(0), t.getPoints().getElem((i + 1) % t.getPoints().getData1d().size()),
                        t.getIsocentre(), t.texture());
        }

    }

    public void idzpp() {
        idImg++;
        ime = new ImageMap(la, ha);
/*        for(int i=0;i<la; i++)
            for(int j=0;j<ha; j++) {
                Scolor[j * la + i] = texture().getColorAt(1. * i / la, 1. * j / ha);
                ime.ime.setElementPoint(i, j, Point3D.INFINI);
                ime.ime.setDeep(i, j, INFINITY_DEEP);
                ime.ime.setElementID(i, j, idImg());
                ime.ime.setElementCouleur(i, j, Scolor[j*la+i]);
                ime.ime.setElementProf(i, j, INFINITY_DEEP);
                ime = new ImageMap(la, ha);
            }
  */
    }


    public class Box2D {

        private double minx = 1000000;
        private double miny = 1000000;
        private double minz = 1000000;
        private double maxx = -1000000;
        private double maxy = -1000000;
        private double maxz = -1000000;
        private boolean notests = false;

        public Box2D() {
            SceneCadre cadre = currentScene.getCadre();
            if (cadre.isCadre()) {
                for (int i = 0; i < 4; i++) {
                    if (cadre.get(i) != null) {
                        test(cadre.get(i));
                    }
                }
                /*
                 * if (!cadre.isExterieur()) { notests = true; }
                 */
            }

            if (!notests) {
                Iterator<Representable> it = currentScene.iterator();
                while (it.hasNext()) {
                    Representable r = it.next();
                    // GENERATORS
                    if (r instanceof TRIGenerable) {
                        r = ((TRIGenerable) r).generate();
                    } else if (r instanceof PGenerator) {
                        r = ((PGenerator) r).generatePO();
                    } else if (r instanceof TRIConteneur) {
                        r = ((TRIConteneur) r).getObj();
                    }
                    // OBJETS
                    if (r instanceof TRIObject) {
                        TRIObject o = (TRIObject) r;
                        Iterator<TRI> ts = o.iterator();
                        while (ts.hasNext()) {
                            TRI t = ts.next();
                            for (Point3D p : t.getSommet().getData1d()) {
                                test(p);
                            }
                        }
                    } else if (r instanceof Point3D) {
                        Point3D p = (Point3D) r;
                        test(p);
                    } else if (r instanceof LineSegment) {
                        LineSegment p = (LineSegment) r;
                        test(p.getOrigine());
                        test(p.getExtremite());
                    } else if (r instanceof TRI) {
                        TRI t = (TRI) r;
                        test(t.getSommet().getElem(0));
                        test(t.getSommet().getElem(1));
                        test(t.getSommet().getElem(2));
                    } /*else if (r instanceof BSpline) {
                        BSpline b = (BSpline) r;
                        Iterator<Point3D> ts = b.iterator();
                        while (ts.hasNext()) {
                            Point3D p = ts.next();
                            test(p);
                        }
                    }(*/ else if (r instanceof BezierCubique) {
                        BezierCubique b = (BezierCubique) r;
                        Iterator<Point3D> ts = b.iterator();
                        while (ts.hasNext()) {
                            Point3D p = ts.next();
                            test(p);
                        }
                    } else if (r instanceof BezierCubique2D) {
                        BezierCubique2D b = (BezierCubique2D) r;
                        for (int i = 0; i < 4; i++) {
                            for (int j = 0; j < 4; j++) {
                                Point3D p = b.getControle(i, j);
                                test(p);
                            }
                        }
                    } else if (r instanceof POConteneur) {
                        for (Point3D p : ((POConteneur) r).iterable()) {
                            test(p);
                        }

                    } else if (r instanceof PObjet) {
                        for (Point3D p : ((PObjet) r).iterable()) {
                            test(p);
                        }

                    } else if (r instanceof RepresentableConteneur) {
                        throw new UnsupportedOperationException("Conteneur non supporté");
                    }
                }
            }
            // Adapter en fonction du ratio largeur/hauteur
            double ratioEcran = 1.0 * la / ha;
            double ratioBox = (maxx - minx) / (maxy - miny);
            double minx2 = minx, miny2 = miny, maxx2 = maxx, maxy2 = maxy;
            if (ratioEcran > ratioBox) {
                // Ajouter des points en coordArr
                minx2 = minx - (1 / ratioBox * ratioEcran / 2) * (maxx - minx);
                maxx2 = maxx + (1 / ratioBox * ratioEcran / 2) * (maxx - minx);
            } else if (ratioEcran < ratioBox) {
                // Ajouter des points en y
                miny2 = miny - (ratioBox / ratioEcran / 2) * (maxy - miny);
                maxy2 = maxy + (ratioBox / ratioEcran / 2) * (maxy - miny);

            }
            minx = minx2;
            miny = miny2;
            maxx = maxx2;
            maxy = maxy2;

            double ajuste = zoom - 1.0;
            minx2 = minx - ajuste * (maxx - minx);
            miny2 = miny - ajuste * (maxy - miny);
            maxx2 = maxx + ajuste * (maxx - minx);
            maxy2 = maxy + ajuste * (maxy - miny);
            minx = minx2;
            miny = miny2;
            maxx = maxx2;
            maxy = maxy2;

        }

        public boolean checkPoint(Point3D p) {
            return p.getX() > minx & p.getX() < maxx & p.getY() > miny & p.getY() < maxy;
        }

        public double echelleEcran() {
            return (box.getMaxx() - box.getMinx()) / la;
        }

        public double getMaxx() {
            return maxx;
        }

        public void setMaxx(double maxx) {
            this.maxx = maxx;
        }

        public double getMaxy() {
            return maxy;
        }

        public void setMaxy(double maxy) {
            this.maxy = maxy;
        }

        public double getMaxz() {
            return maxz;
        }

        public void setMaxz(double maxz) {
            this.maxz = maxz;
        }

        public double getMinx() {
            return minx;
        }

        public void setMinx(double minx) {
            this.minx = minx;
        }

        public double getMiny() {
            return miny;
        }

        public void setMiny(double miny) {
            this.miny = miny;
        }

        public double getMinz() {
            return minz;
        }

        public void setMinz(double minz) {
            this.minz = minz;
        }

        public Rectangle rectangle() {
            return new Rectangle((int) minx, (int) miny, (int) maxx, (int) maxy);
        }

        private void test(Point3D p) {
            if (p.getX() < minx) {
                minx = p.getX();
            }
            if (p.getY() < miny) {
                miny = p.getY();
            }
            if (p.getZ() < minz) {
                minz = p.getZ();
            }
            if (p.getX() > maxx) {
                maxx = p.getX();
            }
            if (p.getY() > maxy) {
                maxy = p.getY();
            }
            if (p.getZ() > maxz) {
                maxz = p.getZ();
            }

        }
    }

    public class Box2DPerspective {

        public float d = -10.0f;
        public float w = 10.0f;
        public float h = w * la / ha;

        /*__
         * @param scene
         */
        public Box2DPerspective(Scene scene) {
        }
    }

    public class ImageMap {

        protected ImageMapElement ime;

        public ImageMap(int x, int y) {
            dimx = x;
            dimy = y;
            ime = new ImageMapElement();
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    ime.setElementID(i, j, idImg);
                    ime.setElementPoint(i, j, INFINITY);
                    ime.setElementCouleur(i, j, texture().getColorAt(1. * i / la, 1. * j / ha));
                }
            }
        }

        public void dessine(Point3D x3d, Color c) {
            Point ce = camera().coordonneesPoint2D(x3d, that);
            if (ce == null) {
                return;
            }
            double prof = -1000;
            int x = (int) ce.getX();
            int y = (int) ce.getY();
            if (x >= 0 & x < la & y >= 0 & y < ha && c.getAlpha() == 255) {
                ime.setElementID(x, y, idImg);
                ime.setElementPoint(x, y, x3d);
                ime.setElementCouleur(x, y, c.getRGB());
                ime.setDeep(x, y, prof);
            } else if (checkScreen(ce)) {
                int elementCouleur = ime.getElementCouleur(x, y);
                double[] nc = Lumiere.getRgb(c);
                double[] ac = Lumiere.getDoubles(elementCouleur);
                double[] b = new double[3];
                for (int i = 0; i < 3; i++) {
                    b[i] = nc[i] * c.getAlpha() / 255. + (1 - c.getAlpha() / 255.) * ac[i];
                }
                int anInt = Lumiere.getInt(b);
                ime.setElementID(x, y, idImg);
                ime.setElementPoint(x, y, x3d);
                ime.setElementCouleur(x, y, anInt);
                ime.setDeep(x, y, prof);

            }
        }

        public int getDimx() {
            return dimx;
        }

        public int getDimy() {
            return dimy;
        }

        public ImageMapElement getIME() {
            return ime;
        }

        public void reinit() {
            idImg++;
        }

        /*
         * private boolean checkCoordinates(int coordArr, int y) { if (coordArr >= 0 & coordArr < la & y >= 0
         * & y < ha) { return true; } return false; }
         */
        public void setIME(int x, int y) {
            ime.setElementID(x, y, idImg);
        }

        public boolean testDeep(Point3D x3d, int c) {
            if (x3d == null)
                return false;
            //x3d = camera().calculerPointDansRepere(x3d);
            if (x3d == null)
                return false;
            int cc = c;
            Point ce = camera().coordonneesPoint2D(x3d, that);
            if (ce == null)
                return false;
            int x = (int) ce.getX();
            int y = (int) ce.getY();
            double deep = camera().distanceCamera(x3d);
            if (x >= 0 & x < la & y >= 0 & y < ha
                    && (deep >= ime.getElementProf(x, y))) {
                Point3D n = x3d.getNormale();
                // Vérifier : n.eye>0 sinon n = -n Avoir toutes les normales
                // dans la même direction par rapport à la caméra.
                if (n == null || n.norme() == 0)
                    n = x3d.moins(camera().getEye());
                else if (FORCE_POSITIVE_NORMALS && n.norme1().dot(scene().cameraActive().getEye().norme1()) < 0)
                    n = n.mult(-1);
                cc = scene().lumiereTotaleCouleur(c, x3d, n);
                ime.setElementID(x, y, idImg);
                ime.setElementCouleur(x, y, cc);
                ime.setDeep(x, y, deep);
                ime.setElementPoint(x, y, x3d);
                return true;
            }
            return false;
        }

        public void testDeep(Point3D p, Point3D n, Color c) {
            // Color cc = c.getCouleur();
            p.setNormale(n);
            testDeep(p, c.getRgb());
        }

        public void testDeep(Point3D p, Point3D n, int c) {
            testDeep(p, n, (Color) Color.valueOf(c));
        }

        public boolean testDeep(Point3D p, ITexture texture, double u, double v, Representable representable) {
            if (testDeep(p, texture.getColorAt(u, v))) {
                Point point = camera().coordonneesPoint2D(p, that);
                double x = point.x;
                double y = point.y;
                ime.getuMap()[(int) x][(int) y] = u;
                ime.getvMap()[(int) x][(int) y] = v;
                ime.getrMap()[(int) x][(int) y] = representable;
                return true;
            }
            return false;
        }

        public boolean testDeep(Point3D p, ITexture texture) {
            if (testDeep(p, texture.getColorAt(0.5, 0.5)))
                return true;
            return false;
        }

        public void dessine(Point3D p, ITexture texture) {
            dessine(p, (Color) Color.valueOf(texture.getColorAt(0.5, 0.5)));

        }

        public void testDeep(Point3D p) {
            testDeep(p, (p != null && p.texture() != null) ? p.texture() : CFAST);//WTF
        }

        public void testDeep(Point3D p, Color c) {
            testDeep(p, p.getNormale(), c);
        }

        public void testDeep(Point3D pFinal, Point3D point3D, int colorAt, Representable n) {
            testDeep(pFinal, point3D, colorAt);
            Point point = camera().coordonneesPoint2D(pFinal, that);
            if (ime != null && point != null) {
                ime.setElementRepresentable((int) point.getX(), (int) point.getY(), n);
            }
        }

        public void testDeep(Point3D pFinal, int colorAt, Representable n) {
            testDeep(pFinal, pFinal.getNormale(), colorAt, n);
        }

        public void dessine(Point3D p, int colorAt) {
            dessine(p, (Color) Color.valueOf(colorAt));
        }
    }
//
//    public void drawElementVolume(RPv rPv) {
//        Representable representable = rPv.getRepresentable();
//        if (representable instanceof ParametricVolume) {
//            for (double u = 0.0; u <= 1.0; u += ParametricVolume.incrU()) {
//                for (double v = 0.0; v <= 1.0; v += ParametricVolume.incrV()) {
//                    for (double w = 0.0; w <= 1.0; w += ParametricVolume.incrW()) {
//                        Point3D point3D = ((ParametricVolume) representable).calculerPoint3D(P.n(u, v, w));
//                        if (point3D == null) return;
//                        testDeep(point3D);
//                    }
//                }
//            }
//        } else if (representable instanceof ParametricSurface) {
//            ParametricSurface s = (ParametricSurface) representable;
//            for (double u = 0.0; u <= 1.0; u += ParametricSurface.getGlobals().getIncrU()) {
//                for (double v = 0.0; v <= 1.0; v += ParametricSurface.getGlobals().getIncrV()) {
//                    Point3D point3D = ((ParametricSurface) representable).calculerPoint3D(u, v);
//                    if (point3D == null) return;
//                    /*tracerQuad(s.calculerPoint3D(u, v), s.calculerPoint3D(u+s.getIncrU(), v),
//                            s.calculerPoint3D(u+s.getIncrU(), v+s.getIncrV()), s.calculerPoint3D(u, v+s.getIncrV()),
//                              s.texture(), u, u+s.getIncrU(), v, v+s.getIncrV(), s);
//                            );
//                    */
//                    testDeep(point3D);
//                }
//            }
//
//        } else if (representable instanceof ParametricCurve) {
//            for (double u = 0.0; u <= 1.0; u += ParametricCurve.getGlobals().getIncrU()) {
//                Point3D point3D = ((ParametricCurve) representable).calculerPoint3D(u);
//                if (point3D == null) return;
//                testDeep(point3D);
//            }
//        }
//    }

    public class ImageMapElement {

        protected int couleur_fond_int = -1;
        private ImageMapElement instance;
        private Representable[][] Simerepresentable;
        private double[][] uMap;
        private double[][] vMap;
        private Representable[][] rMap;

        public ImageMapElement() {
            Scordinate = new Point3D[la][ha];
            Scolor = new int[la * ha];
            Simeid = new long[la][ha];
            Simeprof = new double[la][ha];
            Simerepresentable = new Representable[la][ha];
            uMap = new double[la][ha];
            vMap = new double[la][ha];
            rMap = new Representable[la][ha];
            for (int i = 0; i < la; i++) {
                for (int j = 0; j < ha; j++) {
                    Simeprof[i][j] = INFINITY.getZ();
                    Simeid[i][j] = idImg;
                    Scolor[j * la + i] = COULEUR_FOND_INT(i, j);
                    Simerepresentable[i][j] = null;
                }
            }
        }

        public Representable getElementRepresentable(int x, int y) {
            if (checkCordinates(x, y)) {
                return Simerepresentable[x][y];
            }
            return null;
        }

        public void setElementRepresentable(int x, int y, Representable representable) {
            Simerepresentable[x][y] = representable;
        }

        public boolean checkCordinates(int x, int y) {
            return x >= 0 && x < la && y >= 0 && y < ha;
        }

        public int COULEUR_FOND_INT(int x, int y) {
            couleur_fond_int = texture().getColorAt(1.0 * x / largeur(), 1.0 * y / hauteur());
            return couleur_fond_int;
        }

        public int getElementCouleur(int x, int y) {
            if (checkCordinates(x, y)
                    && Simeid[x][y] == idImg
                    && Simeprof[x][y] > INFINITY.getZ()) {
                return getRGBInt(Scolor, x, y);
            } else {
                return COULEUR_FOND_INT(x, y);
            }
        }

        public long getElementID(int x, int y) {
            return Simeid[x][y];
        }

        public Point3D getElementPoint(int x, int y) {
            return Scordinate[x][y];
        }

        private double getElementProf(int x, int y) {
            return Simeprof[x][y];
        }

        public ImageMapElement getInstance(int x, int y) {
            if (instance == null) {
                instance = new ImageMapElement();
            }
            return instance;
        }

        private int getRGBInt(int[] sc, int x, int y) {
            return sc[x + y * la];

        }

        public void setElementCouleur(int x, int y, int pc) {
            setElementID(x, y, idImg);
            setRGBInts(pc, Scolor, x, y);
        }

        public void setElementID(int x, int y, long id) {
            Simeid[x][y] = idImg;
        }

        public void setElementPoint(int x, int y, Point3D px) {
            setElementID(x, y, idImg);
            Scordinate[x][y] = px;
        }

        public double[][] getuMap() {
            return uMap;
        }

        public void setuMap(double[][] uMap) {
            this.uMap = uMap;
        }

        public double[][] getvMap() {
            return vMap;
        }

        public void setvMap(double[][] vMap) {
            this.vMap = vMap;
        }

        public Representable[][] getrMap() {
            return rMap;
        }

        public void setrMap(Representable[][] rMap) {
            this.rMap = rMap;
        }

        private void setDeep(int x, int y, double d) {
            if (checkCordinates(x, y)) {
                Simeprof[x][y] = (float) d;
            }

        }

        private void setRGBInts(int rgb, int[] sc, int x, int y) {
            sc[x + y * la] = rgb;
        }

        public void setElementProf(int i, int j, double pr) {
            Simeprof[i][j] = pr;
        }
    }

    public boolean isFORCE_POSITIVE_NORMALS() {
        return FORCE_POSITIVE_NORMALS;
    }

    public void setFORCE_POSITIVE_NORMALS(boolean FORCE_POSITIVE_NORMALS) {
        this.FORCE_POSITIVE_NORMALS = FORCE_POSITIVE_NORMALS;
    }

    public void ratioVerticalAngle() {
        this.angleY = 1.0 * dimy / dimx * angleX;
    }


    public void drawOnImage(Bitmap bitmap, Bitmap renderedImage, Canvas mCanvas,
                            StructureMatrix<Point3D> inBounds, PointF pointF, PointF scale) {

        paint.setColor(android.graphics.Color.BLUE);

        double left = inBounds.getElem(0).get(0);
        double top = inBounds.getElem(0).get(1);
        double right = inBounds.getElem(1).get(0);
        double bottom = inBounds.getElem(1).get(1);
        double widthBox = right - left;
        double heightBox = bottom - top;

        int pixels = 0;
/*
        PointF pointF1 = FaceOverlayView.coordCanvas(mCanvas, bitmap, new PointF((float) left, (float) top));
        PointF pointF2 = FaceOverlayView.coordCanvas(mCanvas, bitmap, new PointF((float) right, (float) bottom));

        left   = pointF1.x;
        top    = pointF1.y;
        right  = pointF2.x;
        bottom = pointF2.y;
*/
        //System.out.println("drawOnImage");
        for (int i = (int) left; i <= right; i++) {
            for (int j = (int) top; j <= bottom; j++) {
                if (i < bitmap.getWidth() && j < bitmap.getHeight()) {
                    double width = right - left;
                    double height = bottom - top;

                    double rX = (i - left) / width;
                    double rY = (j - top) / height;

                    int xOrigin = (int) (rX * (renderedImage.getWidth()));
                    int yOrigin = (int) (rY * (renderedImage.getHeight()));

                    if (xOrigin>=0 && xOrigin<renderedImage.getWidth()
                        &&yOrigin>=0 && yOrigin<renderedImage.getHeight() &&
                            !ime.getIME().getElementPoint(xOrigin, yOrigin).equals(INFINITY)) {
                        int color = renderedImage.getPixel(xOrigin, yOrigin);


                        if (color != isTranparent()) {
                            int i1 =(int)( pointF.x+i*scale.x);
                            int j1 =(int)( pointF.y+j*scale.y);

                            paint.setColor(color);
                            mCanvas.drawLine(i1, j1, i1+1, j1+1, paint);
                            pixels++;
                        }
                    }
                }
            }
        }

        //System.out.println("rect points : " + pixels);

    }

}
