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

/*
 * 2013 Manuel Dahmen
 */
package one.empty3.library.core.script;

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import one.empty3.library.*;
import one.empty3.library.Polygon;
import one.empty3.library.core.extra.*;
import one.empty3.library.core.nurbs.BSpline;
import one.empty3.library.core.nurbs.NurbsSurface;
import one.empty3.library.core.tribase.Plan3D;
import one.empty3.library.core.tribase.TRIEllipsoide;
import one.empty3.library.core.tribase.TRISphere;
import one.empty3.library.core.tribase.Tubulaire;


import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InterpreteFacade {

    public final int FILETYPE_MODEL_TO = 0;
    public final int BLANK = 0;
    public final int LEFTPARENTHESIS = 1;
    public final int RIGHTPARENTHESIS = 2;
    public final int LEFTSKETCH = 3;
    public final int RIGHTSKETCH = 4;
    public final int ALPHA_WORD = 5;
    public final int POINT3D = 100;
    public final int COLOR = 101;
    public final int INTEGER = 102;
    public final int DOUBLE = 103;
    public final int TRIANGLE = 104;
    public final int LIST_TRIANGLES = 105;
    public final int BSPLINE = 106;
    public final int BEZIER = 107;
    private String text;
    private int pos;
    private boolean okay;
    private String repertoire;

    public InterpreteFacade(String text, int pos) {
        super();
        this.text = text;
        this.pos = pos;
    }

    public Object getParsedObject() {
        return null;
    }

    public int getPosition() {
        return pos;
    }

    public void setPosition(int pos) {
        this.pos = pos;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AttracteurEtrange intepreteAttracteurEtrange()
            throws InterpreteException {
        InterpreteAttracteurEtrange interpreteH = new InterpreteAttracteurEtrange();
        interpreteH.setRepertoire(repertoire);
        AttracteurEtrange t = null;
        try {
            t = (AttracteurEtrange) interpreteH.interprete(text, pos);
            pos = interpreteH.getPosition();
        } catch (Exception e) {
            throw new InterpreteException(
                    java.util.ResourceBundle
                            .getBundle(
                                    "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                            .getString(
                                    "ATTRACTEUR ETRANGE ERRUER D'ANALYSE SYNTAXIQUE"));

        }
        return t;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    Representable intepreteColline() throws InterpreteException {
        InterpretesBase interpreteH = new InterpretesBase();
        ArrayList<Integer> pattern;
        pattern = new ArrayList<Integer>();
        pattern.add(interpreteH.BLANK);
        pattern.add(interpreteH.LEFTPARENTHESIS);
        pattern.add(interpreteH.BLANK);
        pattern.add(interpreteH.INTEGER);
        pattern.add(interpreteH.BLANK);
        pattern.add(interpreteH.RIGHTPARENTHESIS);
        interpreteH.compile(pattern);
        Integer type = (Integer) interpreteH.read(text, pos).get(3);
        this.pos = interpreteH.getPosition();

        switch (type) {
            case 1:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    return (Representable) new CollineModele1(1000);
                }
            case 2:
                return (Representable) new CollineModele2(1000);
            case 3:
                return (Representable) new CollineModele3(1000);
            default:
                return (Representable) new CollineModele1(1000);
        }
    }

    /*__
     * *
     *
     * @return segment de droite
     * @throws InterpreteException
     */
    public LineSegment intepreteSegmentDroite() throws InterpreteException {

        InterpreteSegment interpreteH = new InterpreteSegment();
        interpreteH.setRepertoire(repertoire);
        LineSegment t = null;
        try {
            t = (LineSegment) interpreteH.interprete(text, pos);
            pos = interpreteH.getPosition();
        } catch (Exception e) {
            throw new InterpreteException(e);

        }
        return t;

    }

    /*__
     * @return @throws InterpreteException
     */
    public SimpleSphere intepreteSimpleSphere() throws InterpreteException {
        InterpreteSimpleSphere interpreteH = new InterpreteSimpleSphere();
        interpreteH.setRepertoire(repertoire);
        SimpleSphere t = null;
        try {
            t = (SimpleSphere) interpreteH.interprete(text, pos);
            pos = interpreteH.getPosition();
        } catch (InterpreteException e) {
            throw new InterpreteException(
                    java.util.ResourceBundle
                            .getBundle(
                                    "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                            .getString(
                                    "SIMPLE SPHERE:  ERREUR D'ANALYSE SYNTAXIQUE"));

        }
        return t;

    }

    public Tourbillon intepreteTourbillon() throws InterpreteException {
        interpreteBlank();
        interpreteParentheseOuvrante();
        interpreteBlank();
        interpreteParentheseFermante();
        interpreteBlank();
        return new Tourbillon();

    }

    public Tubulaire intepreteTubulaire() throws InterpreteException {
        InterpreteTubulaire interpreteH = new InterpreteTubulaire();
        interpreteH.setRepertoire(repertoire);
        Tubulaire t = null;
        try {
            t = (Tubulaire) interpreteH.interprete(text, pos);
            pos = interpreteH.getPosition();
        } catch (Exception e) {
            throw new InterpreteException(e);

        }
        return t;

    }

    public BezierCubique interpreteBezier() throws InterpreteException {
        InterpreteBezier interpreteH = new InterpreteBezier();
        interpreteH.setRepertoire(repertoire);
        BezierCubique b = null;
        try {
            b = (BezierCubique) interpreteH.interprete(text, pos);
            pos = interpreteH.getPosition();
        } catch (Exception e) {
            throw new InterpreteException(
                    java.util.ResourceBundle
                            .getBundle(
                                    "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                            .getString("ERRUER"));

        }
        return b;
    }

    public BezierCubique2D interpreteBezier2d() throws InterpreteException {
        InterpreteBezier2D interpreteH = new InterpreteBezier2D();
        interpreteH.setRepertoire(repertoire);
        BezierCubique2D b = null;
        try {
            b = (BezierCubique2D) interpreteH.interprete(text, pos);
            pos = interpreteH.getPosition();
        } catch (Exception e) {
            throw new InterpreteException(
                    java.util.ResourceBundle
                            .getBundle(
                                    "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                            .getString("ERRUER"));

        }
        return b;
    }

    public String interpreteBlank() {
        InterpretesBase ib = new InterpretesBase();
        ArrayList<Integer> pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        try {
            ib.read(text, pos);
            pos = ib.getPosition();
        } catch (Exception e) {
            return "";
        }
        return java.util.ResourceBundle.getBundle(
                        "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                .getString(" ");
    }

    public BSpline interpreteBSpline() throws InterpreteException {
        InterpreteBSpline interpreteH = new InterpreteBSpline();
        interpreteH.setRepertoire(repertoire);
        BSpline b = null;
        try {
            b = (BSpline) interpreteH.interprete(text, pos);
            pos = interpreteH.getPosition();
        } catch (Exception e) {
            throw new InterpreteException(
                    java.util.ResourceBundle
                            .getBundle(
                                    "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                            .getString("ERRUER"));

        }
        return b;
    }

    public Camera interpreteCamera() throws InterpreteException {
        InterpreteCamera interpreteH = new InterpreteCamera();
        Camera c = null;
        try {

            c = (Camera) interpreteH.interprete(text, pos);
            pos = interpreteH.getPosition();
        } catch (InterpreteException ex) {
            throw new InterpreteException("CAMERA ???", ex);
        }
        return c;
    }

    public StructureMatrix<Camera> interpreteCameraCollection()
            throws InterpreteException {
        StructureMatrix<Camera> cameras = new StructureMatrix<Camera>(1, Camera.class);

        interpreteBlank();
        interpreteParentheseOuvrante();
        interpreteBlank();

        Camera c;
        try {
            while (true) {
                String id = interpreteIdentifier();
                System.out.println(id);
                if ("camera".equals(id == null ? "NULL" : id.toLowerCase())) {
                    interpreteBlank();
                    c = interpreteCamera();
                    cameras.add(1, c);
                    System.out.println(id);
                } else {
                    break;
                }
                interpreteBlank();
            }
        } catch (InterpreteException ex) {
        }

        interpreteBlank();
        interpreteParentheseFermante();
        interpreteBlank();

        return cameras;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Color interpreteColor() throws InterpreteException {
        InterpreteCouleur pc = new InterpreteCouleur();
        Color c = Color.valueOf(0, 0, 0);
        try {
            c = (Color) pc.interprete(text, pos);
            pos = pc.getPosition();
        } catch (Exception e) {
            throw new InterpreteException(
                    java.util.ResourceBundle
                            .getBundle(
                                    "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                            .getString("ERRUER"));

        }
        return c;
    }

    public Cube interpreteCube() throws InterpreteException {
        InterpreteCube interpreteH = new InterpreteCube();
        interpreteH.setRepertoire(repertoire);
        Cube c = null;
        try {
            c = (Cube) interpreteH.interprete(text, pos);
            pos = interpreteH.getPosition();
        } catch (Exception e) {
            throw new InterpreteException(
                    java.util.ResourceBundle
                            .getBundle(
                                    "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                            .getString("CUBE :  ERREUR D'ANALYSE SYNTAXIQUE"),
                    e);
        }
        return c;
    }

    public Double interpreteDouble() throws InterpreteException {
        InterpretesBase ib = new InterpretesBase();
        ArrayList<Integer> pattern = new ArrayList<Integer>();
        pattern.add(ib.DECIMAL);
        ib.compile(pattern);
        try {
            ib.read(text, pos);
            pos = ib.getPosition();
        } catch (Exception e) {
            throw new InterpreteException(
                    java.util.ResourceBundle
                            .getBundle(
                                    "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                            .getString("ERRUER"));

        }
        return (Double) ib.get().get(0);
    }

    public ID interpreteId() throws InterpreteException {
        interpreteBlank();
        interpreteParentheseOuvrante();
        interpreteBlank();
        String id = interpreteIdentifier();
        interpreteBlank();
        interpreteParentheseFermante();
        return new ID(id);
    }

    public String interpreteIdentifier() throws InterpreteException {
        InterpreteString is = new InterpreteString();
        String s = "";
        try {
            s = (String) is.interprete(text, pos);
            pos = is.getPosition();
        } catch (Exception e) {
            throw new InterpreteException(
                    java.util.ResourceBundle
                            .getBundle(
                                    "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                            .getString("ERRUER"));
        }/*
         * while (success) { interpreteBlank(); try { s += (String)
         * is.interprete(text, pos); pos = is.getPosition(); } catch (Exception
         * e) { throw new InterpreteException("Erruer"); } }
         */

        return s;
    }

    public Integer interpreteInteger() throws InterpreteException {
        InterpretesBase ib = new InterpretesBase();
        ArrayList<Integer> pattern = new ArrayList<Integer>();
        pattern.add(ib.INTEGER);
        ib.compile(pattern);
        try {
            ib.read(text, pos);
            pos = ib.getPosition();
        } catch (Exception e) {
            throw new InterpreteException(
                    java.util.ResourceBundle
                            .getBundle(
                                    "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                            .getString("ERRUER"));

        }
        return (Integer) ib.get().get(0);
    }

    StructureMatrix<Lumiere> interpreteLumiereCollection() throws InterpreteException {
        StructureMatrix<Lumiere> lumieres = new StructureMatrix<>(1, Lumiere.class);
        InterpretesBase ib = new InterpretesBase();
        ArrayList<Integer> pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.LEFTPARENTHESIS);
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ib.read(text, pos);
        setPosition(ib.getPosition());
        Lumiere c;
        try {
            while (true) {
                interpreteBlank();
                String id = interpreteIdentifier();
                interpreteBlank();
                if ("lumierepoint".equals(id == null ? "NULL" : id
                        .toLowerCase())) {
                    lumieres.add(1, interpreteLumierePoint());
                }
                interpreteBlank();
            }
        } catch (InterpreteException ex) {
        }

        ib = new InterpretesBase();
        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.RIGHTPARENTHESIS);
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ArrayList<Object> read = ib.read(text, pos);
        setPosition(pos);

        return lumieres;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LumierePointSimple interpreteLumierePoint() throws InterpreteException {
        InterpretesBase ib = new InterpretesBase();
        ArrayList<Integer> pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.LEFTPARENTHESIS);
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ib.read(text, pos);
        setPosition(ib.getPosition());

        // Point de lumiere
        Point3D pl = interpretePoint3D();
        // Intensite au point (*)
        double intensite = 1.0;
        Color c = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            c = interpreteColor();
        }

        LumierePointSimple lps = new LumierePointSimple(c, pl, intensite);

        ib = new InterpretesBase();
        pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.RIGHTPARENTHESIS);
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ib.read(text, pos);
        setPosition(ib.getPosition());

        return lps;

    }

    public void interpreteMODHomothetie(Representable r) {
    }

    public void interpreteMODRotation(Representable r) {
    }

    public void interpreteMODTranslation(Representable r) {
    }

    public NurbsSurface interpreteNurbs() throws InterpreteException {
        NurbsSurface n = null;
        InterpreteNurbs iq = new InterpreteNurbs();
        n = (NurbsSurface) iq.interprete(text, pos);
        return n;
    }

    public void interpreteParentheseFermante() throws InterpreteException {
        InterpretesBase ib = new InterpretesBase();
        ArrayList<Integer> pattern = new ArrayList<Integer>();
        pattern.add(ib.RIGHTPARENTHESIS);
        ib.compile(pattern);
        ib.read(text, pos);
        setPosition(ib.getPosition());
    }

    public void interpreteParentheseOuvrante() throws InterpreteException {
        InterpretesBase ib = new InterpretesBase();
        ArrayList<Integer> pattern = new ArrayList<Integer>();
        pattern.add(ib.LEFTPARENTHESIS);
        ib.compile(pattern);
        ib.read(text, pos);
        setPosition(ib.getPosition());
    }

    public Plan3D interpretePlan3D() throws InterpreteException {
        InterpretePlan3D interpreteH = new InterpretePlan3D();
        interpreteH.setRepertoire(repertoire);
        Plan3D c = null;
        try {
            c = (Plan3D) interpreteH.interprete(text, pos);
            pos = interpreteH.getPosition();
        } catch (Exception e) {
            throw new InterpreteException(
                    java.util.ResourceBundle
                            .getBundle(
                                    "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                            .getString("PLAN3D :  ERREUR D'ANALYSE SYNTAXIQUE "),
                    e);
        }
        return c;
    }

    public Point3D interpretePoint3D() throws InterpreteException {
        InterpretePoint3D pp = new InterpretePoint3D();
        Point3D c = new Point3D();
        try {
            c = (Point3D) pp.interprete(text, pos);
            pos = pp.getPosition();
        } catch (Exception e) {
            throw new InterpreteException(
                    java.util.ResourceBundle
                            .getBundle(
                                    "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                            .getString("ERRUER"));

        }
        return c;
    }

    public Point3D interpretePoint3DAvecCouleur() throws InterpreteException {
        InterpretePoint3DCouleur ipp = new InterpretePoint3DCouleur();
        Point3D p = null;
        try {
            p = (Point3D) ipp.interprete(text, pos);
            pos = ipp.getPosition();
        } catch (Exception e) {
            throw new InterpreteException(
                    java.util.ResourceBundle
                            .getBundle(
                                    "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                            .getString("ERRUER"));

        }
        return p;

    }

    public Polygon interpretePolygone() throws InterpreteException {
        InterpretePolygone interpreteH = new InterpretePolygone();
        interpreteH.setRepertoire(repertoire);
        Polygon s = null;
        try {
            s = (Polygon) interpreteH.interprete(text, pos);
            pos = interpreteH.getPosition();
        } catch (InterpreteException ex) {
            throw new InterpreteException(
                    java.util.ResourceBundle
                            .getBundle(
                                    "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                            .getString(
                                    "TRISPHERE :  ERREUR D'ANALYSE SYNTAXIQUE "),
                    ex);
        }
        return s;
    }

    /*
        public PolyMap interpretePolyMapDef() throws InterpreteException {
            InterpretePolyMapDef interpreteH;
            interpreteH = new InterpretePolyMapDef();
            interpreteH.setRepertoire(repertoire);
            PolyMap pm = null;
            try {
                pm = (PolyMap) interpreteH.interprete(text, pos);
                pos = interpreteH.getPosition();
            } catch (InterpreteException ex) {
                throw new InterpreteException(
                        java.util.ResourceBundle
                                .getBundle(
                                        "info.emptycanvas.one.empty3.library/scripts/InterpretePolyMap")
                                .getString(
                                        "POLYMAP:  ERREUR D'ANALYSE SYNTAXIQUE "),
                        ex);
            }
            return pm;
        }
    */
    public Barycentre interpretePosition() throws InterpreteException {
        InterpretePosition interpreteH = new InterpretePosition();
        interpreteH.setRepertoire(repertoire);
        Barycentre poso = new Barycentre();
        try {
            poso = (Barycentre) interpreteH.interprete(text, pos);
            pos = interpreteH.getPosition();
        } catch (InterpreteException ex) {
            throw new InterpreteException(
                    java.util.ResourceBundle
                            .getBundle(
                                    "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                            .getString(
                                    "Position : erreur d'analyse"),
                    ex);
        }
        return poso;
    }

    public Quads interpreteQuads() throws InterpreteException {
        Quads q = null;
        InterpreteQuads iq = new InterpreteQuads();
        q = (Quads) iq.interprete(text, pos);
        return q;
    }

    public SimpleSphereAvecTexture interpreteSimpleSphereAvecTexture()
            throws InterpreteException {
        InterpreteSimpleSphereTexture interpreteH = new InterpreteSimpleSphereTexture();
        interpreteH.setRepertoire(repertoire);
        SimpleSphereAvecTexture t = null;
        try {
            t = (SimpleSphereAvecTexture) interpreteH.interprete(text, pos);
            pos = interpreteH.getPosition();
        } catch (Exception e) {
            throw new InterpreteException(e);

        }
        return t;

    }

    public ITexture interpreteTColor() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /*__
     * @return @throws InterpreteException
     */
    public Tetraedre interpreteTetraedre() throws InterpreteException {
        InterpreteTetraedre interpreteH = new InterpreteTetraedre();
        interpreteH.setRepertoire(repertoire);
        Tetraedre t = null;
        try {
            t = (Tetraedre) interpreteH.interprete(text, pos);
            pos = interpreteH.getPosition();
        } catch (Exception e) {
            throw new InterpreteException(
                    java.util.ResourceBundle
                            .getBundle(
                                    "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                            .getString(
                                    "TETRAEDRE :  ERREUR D'ANALYSE SYNTAXIQUE"),
                    e);

        }
        return t;

    }

    public TRI interpreteTriangle() throws InterpreteException {
        InterpreteTriangle interpreteH = new InterpreteTriangle();
        TRI t = null;
        try {
            t = (TRI) interpreteH.interprete(text, pos);
            interpreteH.setRepertoire(repertoire);
            pos = interpreteH.getPosition();
        } catch (Exception e) {
            Logger.getLogger(InterpreteFacade.class.getName()).log(Level.SEVERE, "", e);

            throw new InterpreteException(
                    java.util.ResourceBundle
                            .getBundle(
                                    "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                            .getString("ERRUER"));

        }
        return t;
    }

    public TRIObject interpreteTriangles() throws InterpreteException {
        InterpreteListeTriangle interpreteH = new InterpreteListeTriangle();
        interpreteH.setRepertoire(repertoire);
        TRIObject fo = null;
        try {
            fo = (TRIObject) interpreteH.interprete(text, pos);
            pos = interpreteH.getPosition();
        } catch (Exception e) {
            throw new InterpreteException(
                    java.util.ResourceBundle
                            .getBundle(
                                    "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                            .getString("ERREUR"));

        }
        return fo;
    }

    public TRIEllipsoide interpreteTRIEllipsoide() throws InterpreteException {
        InterpreteTRIEllipsoide interpreteH = new InterpreteTRIEllipsoide();
        interpreteH.setRepertoire(repertoire);
        TRIEllipsoide e = new TRIEllipsoide(new Point3D(0d, 0d, 0d), 1d, 2d, 3d);
        try {
            e = (TRIEllipsoide) interpreteH.interprete(text, pos);
            pos = interpreteH.getPosition();
        } catch (InterpreteException ex) {
            throw new InterpreteException(
                    java.util.ResourceBundle
                            .getBundle(
                                    "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                            .getString(
                                    "TRIELLIPSOIDE :  ERREUR D'ANALYSE SYNTAXIQUE "),
                    ex);
        }
        return e;
    }

    public TRISphere interpreteTRISphere() throws InterpreteException {
        InterpreteTRISphere interpreteH = new InterpreteTRISphere();
        interpreteH.setRepertoire(repertoire);
        TRISphere s = new TRISphere(new Point3D(0d, 0d, 0d), 1.0);
        try {
            s = (TRISphere) interpreteH.interprete(text, pos);
            pos = interpreteH.getPosition();
        } catch (InterpreteException ex) {
            throw new InterpreteException(
                    java.util.ResourceBundle
                            .getBundle(
                                    "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                            .getString(
                                    "TRISPHERE :  ERREUR D'ANALYSE SYNTAXIQUE "),
                    ex);
        }
        return s;
    }

    public Tubulaire interpreteTubulaire() throws InterpreteException {
        InterpreteBezier2D interpreteH = new InterpreteBezier2D();
        interpreteH.setRepertoire(repertoire);
        Tubulaire t = null;
        try {
            t = (Tubulaire) interpreteH.interprete(text, pos);
            pos = interpreteH.getPosition();
        } catch (Exception e) {
            throw new InterpreteException(
                    java.util.ResourceBundle
                            .getBundle(
                                    "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                            .getString("ERRUER"));

        }
        return t;

    }

    public boolean isFailed() {
        return true;
    }

    public boolean isOkay() {
        return okay;
    }

    public void setOkay(boolean okay) {
        this.okay = okay;
    }

    public void parse(int filetype) {
    }

    @Deprecated
    public String parseEND() {
        InterpretesBase ib = new InterpretesBase();
        ArrayList<Integer> pattern = new ArrayList<Integer>();
        pattern.add(ib.RIGHTPARENTHESIS);
        ib.compile(pattern);
        try {
            ib.read(text, pos);
            pos = ib.getPosition();
        } catch (Exception e) {
            return "";
        }
        return java.util.ResourceBundle.getBundle(
                        "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                .getString(")");
    }

    @Deprecated
    public String parseWord() throws InterpreteException {
        InterpreteString is = new InterpreteString();
        String s = "";
        try {
            s = (String) is.interprete(text, pos);
            pos = is.getPosition();
        } catch (Exception e) {
            throw new InterpreteException(
                    java.util.ResourceBundle
                            .getBundle(
                                    "info.emptycanvas.one.empty3.library/scripts/InterpreteLangage")
                            .getString("ERRUER"));
        }
        return s;
    }

    public void setRepertoire(String repertoire) {
        this.repertoire = repertoire;
    }

}
