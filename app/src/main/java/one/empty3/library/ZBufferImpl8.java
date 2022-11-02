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
 *//*


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
 *//*


 */
/*__
 * *
 * Global license  GNU GPL v2
 * author Manuel Dahmen _manuel.dahmen@gmx.com_
 *//*

package one.empty3.library;

import one.empty3.library.core.nurbs.*;
import one.empty3.pointset.PCont;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Picture;
import android.graphics.Point;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

*/
/**
 * * Classe de rendu graphique
 * <p>
 * P2 écran 2 int
 * P3 x y z
 * Tgt x y z
 * N x y z
 * Tex u v w TextId [12 double 1 int
 * (1 double)imeid 1 ref. ]
 * T
 * Representable container
 * <p>
 * <p>
 * Point x y z t x y z n x y z t u v w
 * Int textId int4 out rgba
 * Representable surface line point3d
 * <p>
 * <p>
 * Draw buffer with data
 * Iterate on objects
 * <p>
 * Textures and lights
 *
 * @return image null
 *//*

class Data {

    */
/**
 * Point x y z t x y z n x y z t u v w
 * Int textId int4 out rgba
 * Representable surface line point3d
 *//*

    Double[][][] dataP;
    Representable[][] container;
    ZBufferImpl8 zBuffer;
    int la;
    int ha;

    public Data(int w, int h, ZBufferImpl8 zBuffer) {
        dataP = new Double[16][h][w];

        container = new Representable[h][w];
        this.zBuffer = zBuffer;
        this.la = w;

        this.ha = h;


        System.out.println("la,ha " + la + ", " + ha);


    }

    public void clear() {


        for (int j = 0; j < ha; j++)
            for (int i = 0; i < la; i++) {
                dataP[13][j][i] = 9999999999.9; //Double.MAX_VALUE;
                container[j][i] = ZBufferImpl8.INFINI;
                dataP[9][j][i] = 1.0 * i / la;
                dataP[10][j][i] = 1.0 * j / ha;

            }


    }

    public boolean addData(Double px, Double py, Double pz,
                           Double tx, Double ty, Double tz,
                           Double nx, Double ny, Double nz,
                           Double u, Double v, Double w,
                           Representable r) {
        Point p = null;
        if ((p = testP(px, py, pz)) != null) {
            int x = (int) p.x;
            int y = (int) p.y;
            dataP[0][y][x] = px;
            dataP[1][y][x] = py;
            dataP[2][y][x] = pz;
            dataP[3][y][x] = tx;
            dataP[4][y][x] = ty;
            dataP[5][y][x] = tz;
            dataP[6][y][x] = nx;
            dataP[7][y][x] = ny;
            dataP[8][y][x] = nz;
            dataP[9][y][x] = u;
            dataP[10][y][x] = v;
            dataP[11][y][x] = w;
             */
/*if(r==null){
                 r = zBuffer;
                        }(*//*

            container[y][x] = r;
            //System.out.println("x,y "+x+", "+y+" u,v : " + u+" "+v+" r " +r.getClass());
            return true;
        }
        return false;
    }

    public Point testP(Double px, Double py, Double pz) {
        if (px == null || py == null || pz == null)
            return null;
        return testDeep(new Point3D(px, py, pz));
    }


    public Point testDeep(Point3D x3d) {
        if (x3d == null)
            return null;
        Camera cam = zBuffer.scene().camera();
        Point ce = cam.coordonneesPoint2D(
                cam.calculerPointDansRepere(x3d),
                zBuffer);
        if (ce == null)
            return null;
        double deep = zBuffer.camera().distanceCamera(x3d);


        int x = (int) ce.x;
        int y = (int) ce.y;
        if ((x >= 0) & (x < la) & (y >= 0) & (y < ha)
                && (deep < dataP[13][y][x])) {
            dataP[13][y][x] = deep;
            return ce;

        }
        return null;
    }

    public Bitmap getBitmap() {
        Bitmap bi = Bitmap.createBitmap(la, ha, Bitmap.Config.RGB_565);

        int[] c = new int[4];
        for (int j = 0; j < ha; j++)
            for (int i = 0; i < la; i++)
                if (dataP[9][j][i] != null && dataP[10][j][i] != null) {
                    c[0] = container[j][i].texture().getColorAt(
                            dataP[9][j][i], dataP[10][j][i]);
                    bi.setPixel(i, j, c[0]);
                } else
                    System.out.println("error texture null Data.getBitmap");


        return bi;

    }

}

public class ZBufferImpl8 extends ZBufferImpl {


    public static final int DISPLAY_ALL = 0;
    public static final int SURFACE_DISPLAY_TEXT_QUADS = 1;
    private static final int SURFACE_DISPLAY_TEXT_TRI = 2;
    public static final int SURFACE_DISPLAY_COL_QUADS = 3;
    public static final int SURFACE_DISPLAY_COL_TRI = 4;
    public static final int SURFACE_DISPLAY_LINES = 5;
    public static final int SURFACE_DISPLAY_POINTS = 6;
    */
/*__
 * Couleur de fond (texture: couleur, image, vidéo, ...
 *//*

    // DEFINITIONS
    public static double INFINI_PROF = Double.MAX_VALUE;
    protected Point3D planproj = null;
    protected Point3D camera = null;
    protected boolean colorationActive = false;
    protected boolean experimental = true;
    protected double angleX = Math.PI / 3;
    protected double angleY = Math.PI / 3;
    protected Picture bi;
    protected ImageMap ime;
    public static Point3D INFINI = new Point3D(0d, 0d, INFINI_PROF);
    private Camera cameraC = new Camera();
    // PARAMETRES
    private float zoom = 1.05f;
    private boolean locked = false;
    private boolean firstRun = true;
    // VARIABLES
    private long idImg = 1;
    private int dimx;
    private int dimy;
    private Point3D[][] Scordinate;
    private int[] Scolor;
    private long[][] Simeid;
    private double[][] Simeprof;
    private Scene currentScene;
    public Box2D box;
    private Point3D activeLight = new Point3D(-10d, 0d, 100d);
    private int displayType = DISPLAY_ALL;
    ZBufferImpl8 that;
    Data data1;

    public ZBufferImpl8() {

        that = this;
        texture(new TextureCol(Color.BLACK));
    }


    public ZBufferImpl8(int l, int h) {
        this();


        la = l;
        ha = h;
        dimx = la;
        dimy = ha;
        //this.ime = new ImageMap(la, ha);
        data1 = new Data(la, ha, this);

        //Point3D.start();
    }

    public ZBufferImpl8(Resolution resolution) {


        this(resolution.x(), resolution.y());
    }

    protected long idImg() {
        return idImg;
    }

    public Point3D activeLight() {
        return activeLight;
    }

    public void activeLight(Point3D l) {
        activeLight = l;
    }

    public Camera camera() {
        return this.scene().cameraActive();
    }

    public void camera(Camera c) {
        this.cameraC = c;
        this.scene().cameraActive(c);
    }

    */
/***
 *
 * Draw buffer with data
 * Iterate on objects
 *//*

    public void predraw() {
        draw();
    }

    */
/**
 * Textures and lights
 * @return image null
 *//*

    public BufferedImageAndroid finishDraw() {
        return null;

    }

    public void draw() {

        data1.clear();
        scene().lumieres().clear();
        for (int i = 0; i < scene().getObjets().data1d.size(); i++)
            if (scene().getObjets().getElem(i).getClass().isAssignableFrom(Lumiere.class))
                scene().lumieres().add((Lumiere) scene().getObjets().getElem(i));
        if (firstRun && ime == null) {
            ime = new ImageMap(la, ha);
            firstRun = false;
        }

        draw(scene());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void draw(Representable r) {
        Point3D.start();
        */
/*
 * if (r instanceof RepresentableType) { try { ((RepresentableType)
 * r).draw(this); } catch (Exception ex) { ex.printStackTrace(); } return; }
 *
 * if (r.getPainter() != null) { try { r.paint(); } catch (Exception ex) {
 * ex.printStackTrace(); } }
 *//*

        // COLLECTION
        r.texture().timeNext();

        Iterator<Representable> it;
        if (r instanceof Scene) {
            Scene scene = (Scene) r;

            this.setTexture(scene.texture() == null ? this.texture() : scene.texture());

            it = scene.iterator();
            while (it.hasNext()) {
                draw(it.next());
            }


        } else if (r instanceof RepresentableConteneur) {
            RepresentableConteneur rc = (RepresentableConteneur) r;
            it = rc.iterator();
            while (it.hasNext()) {
                draw(it.next());
            }

        } else

            */
/* OBJECTS *//*

            if (r instanceof Point3D) {
                Point3D p = (Point3D) r;
                add(p.get(0), p.get(1), p.get(2), null, null, null, null, null, null, 0.0, 0.0, 0.0, p);
            } else if (r instanceof ThickSurface) {
                // System.out.println("Surface");
                ThickSurface n = (ThickSurface) r;
                // TODO Dessiner les bords

                for (double u = n.getStartU(); u <= n.getEndU(); u += n.getIncrU()) {
                    // System.out.println("(u,v) = ("+u+","+")");
                    for (double v = n.getStartU(); v <= n.getEndV(); v += n.getIncrV()) {
                        Point3D p1, p2, p3, p4;

                        p1 = rotate(n.calculerPoint3D(u, v), n);
                        p2 = rotate(n.calculerPoint3D(u + n.getIncrU(), v), n);
                        p3 = rotate(n.calculerPoint3D(u + n.getIncrU(), v + n.getIncrV()), n);
                        p4 = rotate(n.calculerPoint3D(u, v + n.getIncrV()), n);
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
                                        u,
                                        v, u + n.getIncrU(), v + n.getEndV());
                                tracerTriangle(n.calculerPoint3D(u, v),
                                        n.calculerPoint3D(u, v + n.getIncrV()),
                                        n.calculerPoint3D(u + n.getIncrU(),
                                                v + n.getIncrV()),
                                        n.texture(),
                                        u,
                                        v, u + n.getIncrU(), v + n.getEndV());

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
                TRI tri = (TRI) r;
                switch (displayType) {
                    case SURFACE_DISPLAY_LINES:
                        for (int i = 0; i < 3; i++)
                            line(rotate(tri.getSommet().getElem(i), r),
                                    rotate(tri.getSommet().getElem((i + 1) % 3), r)
                                    , tri.texture);
                        break;
                    case SURFACE_DISPLAY_POINTS:
                        for (int i = 0; i < 3; i++)
                            ime.testDeep(rotate(tri.getSommet().getElem(i), r)
                                    , tri.texture);
                        break;
                    default:
                        tracerTriangle(rotate(tri.getSommet().getElem(0), r),
                                rotate(tri.getSommet().getElem(1), r),
                                rotate(tri.getSommet().getElem(2), r)
                                , tri.texture());
                        break;

                }
            } else
                // GENERATORS
                if (r instanceof ParametricSurface) {
                    // System.out.println("Surface");
                    ParametricSurface n = (ParametricSurface) r;
                    // TODO Dessiner les bords
                    for (double u = n.getStartU(); u <= n.getEndU() - n.getIncrU(); u += n.getIncrU()) {
                        // System.out.println("(u,v) = ("+u+","+")");
                        for (double v = n.getStartV(); v <= n.getEndV() - n.getIncrV(); v += n.getIncrV()) {
                            */
/*
 * draw(new TRI(n.calculerPoint3D(u, v), n.calculerPoint3D(u + n.getIncrU(), v),
 * n.calculerPoint3D(u + n.getIncrU(), v + n.getIncrV()), n.texture()), n);
 * draw(new TRI(n.calculerPoint3D(u, v), n.calculerPoint3D(u, v + n.getIncrV()),
 * n. calculerPoint3D(u + n.getIncrU(), v + n.getIncrV()), n.texture()), n);
 *//*

 */
/*
 * tracerTriangle(n.calculerPoint3D(u, v), n.calculerPoint3D(u + n.getIncrU(),
 * v), n.calculerPoint3D(u + n.getIncrU(), v + n.getIncrV()), new
 * Color(n.texture().getColorAt(0.5,0.5))); tracerTriangle(n.calculerPoint3D(u,
 * v), n.calculerPoint3D(u, v + n.getIncrV()), n.calculerPoint3D(u +
 * n.getIncrU(), v + n.getIncrV()), Color.valueOf(n.texture().getColorAt(0.5,0.5)));
 *//*
 */
/*
 * tracerTriangle(n.calculerPoint3D(u, v), n.calculerPoint3D(u + n.getIncrU(),
 * v), n.calculerPoint3D(u + n.getIncrU(), v + n.getIncrV()), n.texture());
 * tracerTriangle(n.calculerPoint3D(u, v), n.calculerPoint3D(u, v +
 * n.getIncrV()), n.calculerPoint3D(u + n.getIncrU(), v + n.getIncrV()),
 * n.texture());
 *
 *//*

 */
/*
 * Point3D[][] point3DS = {{n.calculerPoint3D(u, v), n.calculerPoint3D(u +
 * n.getIncrU(), v)}, {n.calculerPoint3D(u + n.getIncrU(), v + n.getIncrV()),
 * n.calculerPoint3D(u, v + n.getIncrV())}};
 *
 * SurfaceParametricPolygonalBezier surfaceParametriquePolynomialeBezier = new
 * SurfaceParametricPolygonalBezier(point3DS);
 * draw(surfaceParametriquePolynomialeBezier, n);
 *//*

                            Point3D p1, p2, p3, p4;
                            p1 = n.calculerPoint3D(u, v);
                            p2 = n.calculerPoint3D(u + n.getIncrU(), v);
                            p3 = n.calculerPoint3D(u + n.getIncrU(), v + n.getIncrV());
                            p4 = n.calculerPoint3D(u, v + n.getIncrV());
                            if (n instanceof HeightMapSurface) {
                                Point3D n1, n2, n3, n4;
                                HeightMapSurface h = (HeightMapSurface) n;
                                n1 = n.calculerNormale3D(u, v);
                                n2 = n.calculerNormale3D(u + n.getIncrU(), v);
                                n3 = n.calculerNormale3D(u + n.getIncrU(), v + n.getIncrV());
                                n4 = n.calculerNormale3D(u, v + n.getIncrV());
                                p1 = p1.plus(n1.norme1().mult(h.height(u, v)));
                                p2 = p2.plus(n2.norme1().mult(h.height(u + n.getIncrU(), v)));
                                p3 = p3.plus(n3.norme1().mult(h.height(u + n.getIncrU(), v + n.getIncrV())));
                                p4 = p4.plus(n4.norme1().mult(h.height(u, v + n.getIncrV())));
                            }
                            if (displayType == SURFACE_DISPLAY_LINES) {
                                tracerLines(p1
                                        , p2,
                                        p3,
                                        p4,
                                        n.texture(), u, u + n.getIncrU(), v, v + n.getIncrV(), n);
                                break;
                            } else if (displayType == SURFACE_DISPLAY_POINTS) {
                                ime.testDeep(rotate(p1, r));
                                ime.testDeep(rotate(p2, r));
                                ime.testDeep(rotate(p3, r));
                                ime.testDeep(rotate(p4, r));
                            } else {
                                System.out.println("Surface" + n.getClass() + " u,v,u1,v1 = " + u + "," + v + " u1,v1 "
                                        + (u + n.getIncrU()) + " " + (v + n.getIncrV()));
                                tracerQuad(rotate(p1, n), rotate(p2, n),
                                        rotate(p3, n), rotate(p4, n),
                                        n.texture(), u, u + n.getIncrU(), v, v + n.getIncrV(), n);

                            }

                            */
/*
 * line(n.calculerPoint3D(u, v), n.calculerPoint3D(u + n.getIncrU(), v),
 * n.texture, u); line( n.calculerPoint3D(u + n.getIncrU(), v),
 * n.calculerPoint3D(u + n.getIncrU(), v + n.getIncrV()), n.texture, v); line(
 * n.calculerPoint3D(u + n.getIncrU(), v + n.getIncrV()), n.calculerPoint3D(u, v
 * + n.getIncrV()), n.texture, u); line( n.calculerPoint3D(u, v + n.getIncrV()),
 * n.calculerPoint3D(u, v), n.texture, v);
 *//*


                            //
                            //
                            // draw(new TRI(n.calculerPoint3D(u, v),
                            // n.calculerPoint3D(u + n.getIncrU(), v),
                            // n.calculerPoint3D(u + n.getIncrU(), v + n.getIncrV()),
                            // n.texture()), n, u, v);
                            // draw(new TRI(n.calculerPoint3D(u, v),
                            // n.calculerPoint3D(u, v + n.getIncrV()),
                            // n.calculerPoint3D(u + n.getIncrU(), v + n.getIncrV()),
                            // n.texture()), n, u, v);
                            //
                        }

                    }
                } else if (r instanceof TRIGenerable) {
                    r = ((TRIGenerable) r).generate();

                } else if (r instanceof PGenerator) {
                    r = ((PGenerator) r).generatePO();
                } else if (r instanceof TRIConteneur) {
                    r = ((TRIConteneur) r).getObj();
                } else

                    // OBJETS
                    if (r instanceof TRIObject) {
                        TRIObject o = (TRIObject) r;
                        for (TRI t : o.getTriangles()) {
                            // System.out.println("Triangle suivant");

                            draw(t);

                        }
                    } else if (r instanceof Point3DS) {
                        Point3D p = ((Point3DS) r).calculerPoint3D(0);
                        ime.testDeep(rotate(p, r), r.texture());
                    } else if (r instanceof LineSegment) {
                        LineSegment s = (LineSegment) r;
                        Point3D pO = s.getOrigine();
                        Point3D pE = s.getExtremite();
                        line(rotate(pO, r), rotate(pE, r), s.texture());
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
                                draw(new Polygon(new Point3D[]{
                                        rotate(b.calculerPoint3D((i - 1 < 0 ? 0 : i - 1) * 1d / i1, (j) * 1d / i2), r),
                                        rotate(b.calculerPoint3D((i) * 1d / i1, (j) * 1d / i2), r),
                                        rotate(b.calculerPoint3D((i) * 1d / i1, (j - 1 < 0 ? 0 : j - 1) * 1d / i2), r),
                                        rotate(b.calculerPoint3D((i - 1 < 0 ? 0 : i - 1) * 1d / i1, (j - 1 < 0 ? 0 : j - 1) * 1d / i2), r)},
                                        b.texture()));
                            }
                        }
                    } else if (r instanceof PCont) {
                        PCont b = (PCont) r;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            b.getPoints().forEach(new Consumer() {
                                @Override
                                public void accept(Object o) {
                                    ime.testDeep(rotate((Point3D) o, b)
                                            , ((Point3D) o).texture().getColorAt(0, 0));
                                }
                            });
                        }
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
                        double incr = n.getIncrU().getData0d();
                        for (double u = n.start(); u <= n.endU(); u += incr) {
                            if (n.isConnected() && displayType != SURFACE_DISPLAY_POINTS) {
                                line(
                                        n.calculerPoint3D(u),
                                        n.calculerPoint3D(u + incr),
                                        n.texture(), u, u + incr, n);
                            } else {
                                ime.testDeep(rotate(n.calculerPoint3D(u), r)
                                        , n.texture().getColorAt(0.5, 0.5));
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
                                line(rotate(points.get((i % length)), p), rotate(points.get((i + 1) % length), p), p.texture);
                            }
                        }
                    }
        Point3D.end();

    }


    private void tracerLines(Point3D p1, Point3D p2, Point3D p3, Point3D p4, ITexture texture, double u, double v,
                             double u1, double v1, ParametricSurface n) {
        line(p1, p2, texture, u, v, u + u1, v, n);
        line(p2, p3, texture, u + u1, v, u + u1, v + v1, n);
        line(p3, p4, texture, u + u1, v, u, v + v1, n);
        line(p4, p1, texture, u, v + v1, u, v, n);
    }


    public double echelleEcran() {
        return box.echelleEcran();
    }

    public int getColorAt(Point p) {
        if (ime.getIME().getElementProf((int) p.x, (int) p.y) >= INFINI_PROF) {
            return ime.getIME().getElementCouleur((int) p.x, (int) p.y);
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

    */
/*__
 * @return hauteur du zbuffer
 *//*

    public int hauteur() {
        return ha;
    }

    @Override
    public void setDimension(int width, int height) {
        la = width;
        ha = height;
    }

    public Bitmap image() {
               */
/*
        ECBufferedImage bi2 = new ECBufferedImage(la, ha, ECBufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < la; i++) {
            for (int j = 0; j < ha; j++) {
                int elementCouleur = ime.ime.getElementCouleur(i, j);
                bi2.setRGB(i, j, elementCouleur);

            }
        }
        this.bi = bi2;
        return bi2;
        *//*

        return image2();

    }

    public Picture image2() {
        return bi;


    }

    public boolean isLocked() {
        return locked;
    }

    public void isobox(boolean isBox) {
    }


    */
/*__
 * @return largeur du zbuffer
 *//*

    public int largeur() {
        return la;
    }

    @Override
    */
/*__
 * @param p1 first point
 * @param p2 second point
 * @param t  colour of de la line
 *//*

    public void line(Point3D p1, Point3D p2, ITexture t) {
        Point x1 = camera().coordonneesPoint2D(p1, this);
        Point x2 = camera().coordonneesPoint2D(p2, this);
        if (x1 == null || x2 == null) {
            return;
        }
        Point3D n = p1.moins(p2).norme1();
        double itere = Math.max(Math.abs(x1.x - x2.x), Math.abs(x1.y - x2.y)) * 4 + 1;
        for (int i = 0; i < itere; i++) {
            Point3D p = p1.plus(p2.moins(p1).mult(i / itere));
            p.texture(t);
            add(p.get(0), p.get(1), p.get(2), null, null, null,
                    null, null, null, i / itere, 0.0, 0.0, p);
        }

    }

    public void line(Point3D p1, Point3D p2, ITexture t, double u, double u1, ParametricCurve curve) {
        Point x1 = camera().coordonneesPoint2D(p1, this);
        Point x2 = camera().coordonneesPoint2D(p2, this);
        if (x1 == null || x2 == null) {
            return;
        }
        Point3D n = p1.moins(p2).norme1();
        double itere = Math.max(Math.abs(x1.x - x2.x), Math.abs(x1.y - x2.y)) * 4 + 1;
        for (int i = 0; i < itere; i++) {
            Point3D p = p1.plus(p2.moins(p1).mult(i / itere));
            if (curve != null)
                p = rotate(curve.calculerPoint3D(u + i / itere * (u1 - u)), curve);


            add(p.get(0), p.get(1), p.get(2), null, null, null,
                    null, null, null, i / itere, 0.0, 0.0, curve);
        }
    }

    public void line(Point3D p1, Point3D p2, ITexture texture, double u, double v, double u1, double v1,
                     ParametricSurface surface) {
        // TODO Check
        Point x1 = camera().coordonneesPoint2D(p1, this);
        Point x2 = camera().coordonneesPoint2D(p2, this);
        if (x1 == null || x2 == null) {
            return;
        }
        Point3D n = p1.moins(p2).norme1();
        double itereU = Math.max(Math.abs(x1.x - x2.x), Math.abs(x1.y - x2.y)) * 4 + 1;
        double itereV = Math.max(Math.abs(x1.x - x2.x), Math.abs(x1.y - x2.y)) * 4 + 1;
        for (int i = 0; i < itereU; i++) {
            Point3D p = p1.plus(p2.moins(p1).mult(i / itereU));
            //p.addData("u", u);
            //p.addData("v", v);
            if (surface != null) {
                p = surface.calculerPoint3D(u + i / itereU * (u1 - u), v + i / itereV * (v1 - v));

            }
            add(p.get(0), p.get(1), p.get(2), null, null, null,
                    null, null, null, u + i / itereU * (u1 - u), v + i / itereV * (v1 - v), 0.0, surface);
        }
    }


    public boolean lock() {
        if (locked) {
            return false;
        }
        locked = true;
        return true;
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
                    Map[i][j] = INFINI_PROF;
                }
            }
        }
        return Map;

    }
*/
/*
    private double maxDistance(Point p1, Point p2, Point p3) {
        return Math.max(Math.max(Point.distance(p1.x, p1.y, p2.x, p2.y), Point.distance(p2.x, p2.y, p3.x, p3.y)),
                Point.distance(p3.x, p3.y, p1.x, p1.y));
    }

    private double maxDistance(Point p1, Point p2, Point p3, Point p4) {
        return Math
                .max(Math.max(Math.max(Point.distance(p1.x, p1.y, p2.x, p2.y), Point.distance(p2.x, p2.y, p3.x, p3.y)),
                        Point.distance(p3.x, p3.y, p4.x, p4.y)), Point.distance(p4.x, p4.y, p1.x, p1.y));
    }

*//*

    public void plotPoint(Color color, Point3D p) {
        if (p != null && color != null) {
            ime.testDeep(p, color.toArgb());
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

    */
/*
        public void next() {
            if (texture() instanceof TextureMov) {
                ((TextureMov) texture()).nextFrame();
            }
            idImg++;
        }
    *//*

    @Override
    public void testDeep(Point3D p, Color c) {

        ime.testDeep(p, c);
    }

    @Override
    public void testDeep(Point3D p, int c) {
        ime.testDeep(p, c);
        ime.testDeep(p, c);

    }

    public void testDeep(Point3D p) {
        if (p != null && p.texture() != null) {
            ime.testDeep(p, p.texture());
        }
    }

    public void testPoint(Point3D p, Color c) {
        int cc = c.toArgb();

        if (scene().lumiereActive() != null) {
            cc = scene().lumiereActive().getCouleur(c.toArgb(), p, p.getNormale());
        }
        ime.testDeep(p, cc);
    }

    private void tracerAretes(Point3D point3d, Point3D point3d2, Color c) {
        Point p1 = camera().coordonneesPoint2D(point3d, this);
        Point p2 = camera().coordonneesPoint2D(point3d2, this);
        if (p1 == null || p2 == null) {
            return;
        }
        double iteres = Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y + 1);
        for (double a = 0; a < 1.0; a += 1 / iteres) {
            Point pp = new Point(p1);
            Point3D p = point3d.mult(a).plus(point3d2.mult(1 - a));
            pp.set(p1.x + (int) (a * (p2.x - p1.x)),
                    p1.y + (int) (a * (p2.y - p1.y)));
            ime.testDeep(p, c.toArgb());

        }

    }

    public boolean add(Double px, Double py, Double pz, Double
            tx, Double ty, Double tz, Double nx, Double ny, Double nz,
                       Double u, Double v, Double w, Representable r) {
        //  rotate(p);
        // ime.testDeep(new Point3D(px, py, pz), new Point3D(nx, ny, nz), r.texture().getColorAt(u, v));
        if (data1.addData(px, py, pz,
                tx, ty, tz, nx, ny, nz, u, v, w, r)) {
            //System.out.println(":");
            return true;
        }
        return false;
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

    public void tracerTriangle(Point3D pp1, Point3D pp2, Point3D pp3, ITexture t, double u0, double u1, double v0, double v1) {
        Point p1 = camera().coordonneesPoint2D(pp1, this);
        Point p2 = camera().coordonneesPoint2D(pp2, this);
        Point p3 = camera().coordonneesPoint2D(pp3, this);
        if (p1 == null || p2 == null || p3 == null) {
            return;
        }

        int col = t.getColorAt(u0, v0);

        double iteres1 = 1.0 / (Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y));
        for (double a = 0; a < 1.0; a += iteres1) {
            Point3D p3d = pp1.plus(pp1.mult(-1d).plus(pp2).mult(a));
            Point pp = camera().coordonneesPoint2D(p3d, this);
            if (pp != null) {
                double iteres2 = 1.0 / (Math.abs(pp.x - p3.x) + Math.abs(pp.y - p3.y));
                for (double b = 0; b < 1.0; b += iteres2) {
                    Point3D p = p3d.plus(p3d.mult(-1d).plus(pp3).mult(b));
                    p.texture(t);
                    // Point p22 = coordonneesPoint2D(p);
                    if (displayType <= SURFACE_DISPLAY_TEXT_TRI) {
                        add(p.get(0), p.get(1), p.get(2), null, null, null, null, null, null, u0 + a * (u1 - u0), v0 + b * (v1 - v0), 0.0, p);
                    } else if (displayType == SURFACE_DISPLAY_COL_TRI)
                        add(p.get(0), p.get(1), p.get(2), null, null, null, null, null, null, u0 + a * (u1 - u0), v0 + b * (v1 - v0), 0.0, p);

                    else ;
                    // LINES, POINTS;
                }
            }
        }
    }

    public void tracerQuad(Point3D pp1, Point3D pp2, Point3D pp3, Point3D pp4, ITexture texture, double u0, double u1,
                           double v0, double v1, ParametricSurface n) {
        Point p1, p2, p3, p4;
        p1 = camera().coordonneesPoint2D(pp1, this);
        p2 = camera().coordonneesPoint2D(pp2, this);
        p3 = camera().coordonneesPoint2D(pp3, this);
        p4 = camera().coordonneesPoint2D(pp4, this);

        int col = texture.getColorAt(u0, v0);

        if (p1 == null || p2 == null || p3 == null || p4 == null)
            return;
        TRI triBas = new TRI(pp1, pp2, pp3, texture);
        Point3D normale = triBas.normale();
        double inter = 1 / (maxDistance(p1, p2, p3, p4) + 1) / 3;
        for (double a = 0; a < 1.0; a += inter) {
            Point3D.start();
            Point3D pElevation1 = pp1.plus(pp1.mult(-1d).plus(pp2).mult(a));
            Point3D pElevation2 = pp4.plus(pp4.mult(-1d).plus(pp3).mult(a));
            for (double b = 0; b < 1.0; b += inter) {
                Point3D.start();
                Point3D pFinal = (pElevation1.plus(pElevation1.mult(-1d).plus(pElevation2).mult(b)));
                pFinal.setNormale(normale);
                pFinal.texture(texture);
                if (n != null) {
                    if (displayType == DISPLAY_ALL)

                        pFinal = n.calculerPoint3D(u0 + (u1 - u0) * a, v0 + (v1 - v0) * b);
                    else {
                        pFinal = pFinal;

                    }
                }
                if (displayType <= SURFACE_DISPLAY_TEXT_QUADS) {
                    Point3D no = n.calculerNormale3D(u0 + (u1 - u0) * a, v0 + (v1 - v0) * b);
                    add(pFinal.get(0), pFinal.get(1), pFinal.get(2), null, null, null, no.get(0), no.get(1), no.get(2), u0 + (u1 - u0) * a, v0 + (v1 - v0) * b, 0.0, n);
                } else {

                    add(pFinal.get(0), pFinal.get(1), pFinal.get(2), null, null, null, normale.get(0), normale.get(1), normale.get(2), u0 + (u1 - u0) * a, v0 + (v1 - v0) * b, 0.0, n);

                }

                Point3D.end();
                //Point3D.save(pFinal);
            }
            Point3D.end();
        }

    }

    public void tracerTriangle(Point3D pp1, Point3D pp2, Point3D pp3, ITexture c) {
        Point p1, p2, p3;
        p1 = camera().coordonneesPoint2D(pp1, this);
        p2 = camera().coordonneesPoint2D(pp2, this);
        p3 = camera().coordonneesPoint2D(pp3, this);

        Point3D n = (pp3.moins(pp1)).prodVect(pp2.moins(pp1)).norme1();

        if (p1 == null || p2 == null || p3 == null) {
            return;
        }
        double iteres1 = 1.0 / (maxDistance(p1, p2, p3) + 1) / 3;
        for (double a = 0; a < 1.0; a += iteres1) {
            Point3D p3d = pp1.plus(pp1.mult(-1d).plus(pp2).mult(a));
            double iteres2 = 1.0 / maxDistance(p1, p2, p3) / 3;
            for (double b = 0; b < 1.0; b += iteres2) {
                Point3D p = p3d.plus(p3d.mult(-1d).plus(pp3).mult(b));
                p.setNormale(n);
                p.texture(c);
                add(p.get(0), p.get(1), p.get(2), null, null, null, n.get(0), n.get(1), n.get(2), a, b, 0.0, p);

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

    public class Box2D {

        private double minx = 1000000;
        private double miny = 1000000;
        private double minz = 1000000;
        private double maxx = -1000000;
        private double maxy = -1000000;
        private double maxz = -1000000;
        private boolean notests = false;


        */
/*
           P3 x y z
           Tgt x y z
           N x y z 
           Tex u v w TextId 
           T
           
        *//*

        public Box2D() {
            SceneCadre cadre = currentScene.getCadre();
            if (cadre.isCadre()) {
                for (int i = 0; i < 4; i++) {
                    if (cadre.get(i) != null) {
                        test(cadre.get(i));
                    }
                }
                */
/*
 * if (!cadre.isExterieur()) { notests = true; }
 *//*

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
                    } */
/* else if (r instanceof BSpline) {
                        BSpline b = (BSpline) r;
                        Iterator<Point3D> ts = b.iterator();
                        while (ts.hasNext()) {
                            Point3D p = ts.next();
                            test(p);
                        }
                    } *//*
 else if (r instanceof BezierCubique) {
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

        */
/*__
 * @param scene
 *//*

        public Box2DPerspective(Scene scene) {
        }
    }

    public void preprocessor() {
        if (data1 == null)
            data1 = new Data(la, ha, this);
        predraw();
        finishDraw();

    }


    public class ImageMap {

        protected ImageMapElement ime;

        public ImageMap(int x, int y) {
            dimx = x;
            dimy = y;
            ime = new ImageMapElement();
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    ime.setElementID(x, y, idImg);
                    ime.setElementPoint(x, y, INFINI);
                    ime.setElementCouleur(x, y, 0);
                }
            }
        }

        public void dessine(Point3D x3d, Color c) {
            Point ce = camera().coordonneesPoint2D(x3d, that);
            if (ce == null) {
                return;
            }
            double prof = -1000;
            int x = (int) ce.x;
            int y = (int) ce.y;
            if (x >= 0 & x < la & y >= 0 & y < ha && c.alpha() == 255) {
                ime.setElementID(x, y, idImg);
                ime.setElementPoint(x, y, x3d);
                ime.setElementCouleur(x, y, c.toArgb());
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

        */
/*
 * private boolean checkCoordinates(int coordArr, int y) { if (coordArr >= 0 & coordArr < la & y >= 0
 * & y < ha) { return true; } return false; }
 *//*

        public void setIME(int x, int y) {
            ime.setElementID(x, y, idImg);
        }

        public void testDeep(Point3D x3d, int c) {
            if (x3d == null)
                return;
            int cc = c;
            Point ce = camera().coordonneesPoint2D(x3d, that);
            if (ce == null)
                return;
            double deep = camera().distanceCamera(x3d);

            int x = (int) ce.x;
            int y = (int) ce.y;
            if (x >= 0 & x < la & y >= 0 & y < ha
                    && (deep < ime.getElementProf(x, y) */
/*
 * || ime.getElementID(coordArr, y) != idImg
 *//*
) */
/* && (((cc>>24)&0xff) == 0) *//*
) {
                if (scene().lumiereActive() != null) {
                    cc = scene().lumiereTotaleCouleur(c, x3d, x3d.getNormale());

                }
                ime.setElementID(x, y, idImg);
                ime.setElementCouleur(x, y, cc);
                ime.setDeep(x, y, deep);
                ime.setElementPoint(x, y, x3d);
            }
        }

        public void testDeep(Point3D p, Point3D n, Color c) {
            // Color cc = c.getCouleur();
            p.setNormale(n);
            testDeep(p, c.toArgb());
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void testDeep(Point3D p, Point3D n, int c) {
            testDeep(p, n, Color.valueOf(c));
        }

        public void testDeep(Point3D p, ITexture texture) {
            testDeep(p, texture.getColorAt(0.5, 0.5));

        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void dessine(Point3D p, ITexture texture) {
            dessine(p, Color.valueOf(texture.getColorAt(0.5, 0.5)));

        }

        public void testDeep(Point3D p) {
            testDeep(p, (p != null && p.texture() != null) ? p.texture() : CFAST);//WTF
        }

        public void testDeep(Point3D p, Color c) {
            testDeep(p, p.getNormale(), c);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void testDeep(Point3D pFinal, Point3D point3D, int colorAt, Representable n) {
            testDeep(pFinal, point3D, colorAt);
            Point point = camera().coordonneesPoint2D(pFinal, that);
            if (ime != null && point != null) {
                ime.setElementRepresentable((int) point.x, (int) point.y, n);
            }
        }

        public void testDeep(Point3D pFinal, int colorAt, Representable n) {
            testDeep(pFinal, pFinal.getNormale(), colorAt, n);
        }
    }

    public void dessine(Point3D p, ITexture texture) {
        ime.dessine(p, Color.valueOf(texture.getColorAt(0.5, 0.5)));
    }

    public class ImageMapElement {

        protected int couleur_fond_int = -1;
        private ImageMapElement instance;
        private Representable[][] Simerepresentable;

        public ImageMapElement() {
            Scordinate = new Point3D[la][ha];
            Scolor = new int[la * ha];
            Simeid = new long[la][ha];
            Simeprof = new double[la][ha];
            Simerepresentable = new Representable[la][ha];

            for (int i = 0; i < la; i++) {
                for (int j = 0; j < ha; j++) {
                    Simeprof[i][j] = INFINI.getZ();
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
            if (checkCordinates(x, y)) {
                Simerepresentable[x][y] = representable;
            }
        }

        public boolean checkCordinates(int x, int y) {
            return x >= 0 && x < resX() && y >= 0 && y < resY();
        }

        public int COULEUR_FOND_INT(int x, int y) {
            couleur_fond_int = texture().getColorAt(1.0 * x / largeur(), 1.0 * y / hauteur());
            return couleur_fond_int;
        }

        public int getElementCouleur(int x, int y) {
            if (checkCordinates(x, y) && Simeid[x][y] == idImg() && Simeprof[x][y] < INFINI.getZ()) {
                return getRGBInt(Scolor, x, y);
            } else {
                return COULEUR_FOND_INT(x, y);
            }
        }

        public long getElementID(int x, int y) {
            if (checkCordinates(x, y)) {
                return Simeid[x][y];
            } else {
                return -1;
            }
        }

        public Point3D getElementPoint(int x, int y) {
            if (checkCordinates(x, y) && Scordinate[x][y] != null) {
                return Scordinate[x][y];
            } else {
                return INFINI;
            }
        }

        private double getElementProf(int x, int y) {
            if (checkCordinates(x, y) && Simeid[x][y] == idImg) {
                return Simeprof[x][y];
            } else {
                return INFINI_PROF;
            }
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

            if (checkCordinates(x, y)) {
                setElementID(x, y, idImg);
                setRGBInts(pc, Scolor, x, y);
            }
        }

        public void setElementID(int x, int y, long id) {
            if (checkCordinates(x, y)) {
                Simeid[x][y] = idImg;
            }
        }

        public void setElementPoint(int x, int y, Point3D px) {
            if (checkCordinates(x, y)) {
                setElementID(x, y, idImg);
                Scordinate[x][y] = px;
            }
        }

        private void setDeep(int x, int y, double d) {
            if (checkCordinates(x, y)) {
                Simeprof[x][y] = (float) d;
            }
        }

        private void setRGBInts(int rgb, int[] sc, int x, int y) {
            sc[x + y * la] = rgb;
        }

    }

    public Point3D clickAt(double x, double y) {
        return clickAt((int) (x * largeur()), (int) y * hauteur());
    }

    */
/*__
 *
 * @param x Coordonnees de l'image ds ZBuffer
 * @param y Coordonnees de l'image ds ZBuffer
 * @return Point3D avec texture. Si vide Point3D.INFINI
 *//*

    public Point3D clickAt(int x, int y) {
        Point3D p = ime.getIME().getElementPoint(x, y);
        p.texture(new TextureCol(ime.getIME().getElementCouleur(x, y)));
        return p;
    }

    */
/*__
 *
 * @param x Coordonnees de l'image ds ZBuffer
 * @param y Coordonnees de l'image ds ZBuffer
 * @return Point3D avec texture. Si vide Point3D.INFINI
 *//*

    public Representable representableAt(int x, int y) {
        Representable p = ime.getIME().getElementRepresentable(x, y);
        return p;
    }

    */
/*__
 *
 * @param x Coordonnées dans le composant
 * @param y Coordonnées dans le composant
 * @param orig point 3D à inverser dans le repère de la caméra
 * @param camera Caméra où calculer. null="this.camera()"
 * @return axe p1: camera.axe, p3 à dist.
 *//*

    public Point3D invert(int x, int y, Point3D orig, Camera camera) {
        x = (x - largeur() / 2);
        y = (y - hauteur() / 2);
        double dist = orig.moins(camera.getEye()).norme();

        double pX = Math.cos(camera.getAngleX()) * dist;
        double pY = Math.cos(camera.getAngleY()) * dist;

        return camera.getMatrice().tild().mult(new Point3D(
                pX * 2.0 * x / largeur(),
                -pY * 2.0 * y / hauteur(), dist
        ));
    }


    public int getDisplayType() {
        return displayType;
    }

    public void setDisplayType(int displayType) {
        this.displayType = displayType;
    }


}
*/
