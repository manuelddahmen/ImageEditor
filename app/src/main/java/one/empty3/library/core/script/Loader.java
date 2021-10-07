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
package one.empty3.library.core.script;

import android.os.Build;

import androidx.annotation.RequiresApi;

import one.empty3.library.*;
import one.empty3.library.core.extra.AttracteurEtrange;
import one.empty3.library.core.extra.SimpleSphere;
import one.empty3.library.core.extra.SimpleSphereAvecTexture;
import one.empty3.library.core.extra.Tourbillon;
import one.empty3.library.core.nurbs.BSpline;
import one.empty3.library.core.nurbs.NurbsSurface;
import one.empty3.library.core.tribase.Plan3D;
import one.empty3.library.core.tribase.TRIEllipsoide;
import one.empty3.library.core.tribase.TRISphere;
import one.empty3.library.core.tribase.Tubulaire;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Loader implements SceneLoader {

    public static final Long TYPE_TEXT = 2l;
    public static final Long TYPE_BINA = 1l;
    private int pos;
    private String repertoire;
    private ID idO;

    void appelVersionSpecifiqueLoad(String version, File f)
            throws VersionNonSupporteeException {
    }

    void appelVersionSpecifiqueSave(String version, File f)
            throws VersionNonSupporteeException {
    }

    @Deprecated
    private void interprete(String t, Scene sc) {
        InterpreteListeTriangle ilf = new InterpreteListeTriangle();
        InterpreteBSpline ib = new InterpreteBSpline();
        TRIObject fo = null;
        BSpline b = null;
        try {
            fo = (TRIObject) ilf.interprete(t, 0);
            ilf.getPosition();
            sc.add(fo);
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                b = (BSpline) ib.interprete(t, 0);
                ib.getPosition();
                sc.add(b);
            } catch (Exception ex1) {
                ex1.printStackTrace();
            }

        }

        return;
    }

    public String[] liste(File dir) {
        if (!dir.exists() || !dir.isDirectory()) {
            return null;
        }
        return dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".mood") || name.endsWith(".moo");
            }
        });
    }

    public String[] listeTESTS() {
        String h = System.getProperty("user.home");
        String p = System.getProperty("file.separator");
        String txtCHEMIN = h + p + "PMMatrix.data" + p + "testscripts" + p;

        File dir = new File(txtCHEMIN);
        return liste(dir);
    }

    public Scene load(File file, Scene scene)
            throws VersionNonSupporteeException,
            ExtensionFichierIncorrecteException {
        if (file.getAbsolutePath().toLowerCase().endsWith("moo")
                || file.getAbsolutePath().toLowerCase().endsWith("mood")) {
            loadIF(file, scene);
            return scene;
        } else if (file.getAbsolutePath().toLowerCase().endsWith("bmoo")
                || file.getAbsolutePath().toLowerCase().endsWith("bmood")) {
            return loadBin(file);
        }
        return scene;
    }

    public Scene loadBin(File f) throws VersionNonSupporteeException,
            ExtensionFichierIncorrecteException {
        if (f.getAbsolutePath().toLowerCase().endsWith("bmood")
                || f.getAbsolutePath().toLowerCase().endsWith("bmoo"))
            ;
        else {
            System.err.println("Extension de fichier requise: .bmood ou bmoo");
            throw new ExtensionFichierIncorrecteException();
        }
        ObjectInputStream objectInputStream = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            objectInputStream = new ObjectInputStream(fis);
            Long type = (Long) objectInputStream.readObject();
            String version = (String) objectInputStream.readObject();
            appelVersionSpecifiqueLoad(version, f);
            if (type == TYPE_TEXT) {
                return null;
            }
            Scene sc = null;
            sc = (Scene) objectInputStream.readObject();
            return sc;
        } catch (IOException ex) {
            Logger.getLogger(Loader.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Loader.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                objectInputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return null;
    }

    public void loadData(File file, Scene sc) {
        try {
            FileInputStream is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @SuppressWarnings("deprecation")
    @Deprecated
    public void loadFObject(File file, Scene sc) throws Exception {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DataInputStream ds = new DataInputStream(fis);
        String text = "";
        String t = "";
        try {
            while ((text = ds.readLine()) != null) {
                t += text;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            fis.close();
        }
        interprete(t, sc);
    }

    @Deprecated
    public void loadFObject(String data, Scene sc) throws Exception {
        interprete(data, sc);
    }

    public boolean loadIF(File file, Scene sc) {
        String dir;
        if (file.getAbsolutePath().toLowerCase().endsWith("mood")
                || file.getAbsolutePath().toLowerCase().endsWith("moo"))
            ;
        else {
            System.err.println("Extension de fichier requise: .mood");
            // throw new ExtensionFichierIncorrecteException();
        }
        dir = file.getAbsolutePath().substring(0,
                file.getAbsolutePath().lastIndexOf(File.separator));
        System.out.println(dir);
        setRepertoire(dir);

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        DataInputStream ds = new DataInputStream(fis);
        String ligne = "";
        String texte = "";
        try {
            while ((ligne = ds.readLine()) != null) {
                texte += ligne;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return loadIF(texte, sc);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean loadIF(String t, Scene sc) {
        boolean failed = false;
        boolean end = false;
        boolean isContOpen = false;
        InterpreteFacade interpreteH = new InterpreteFacade(t, 0);

        interpreteH.setRepertoire(repertoire);

        Representable latest = null;

        RepresentableConteneur rc = null;
        String id = "";
        ID idO = null;
        boolean newIdO = false;

        while (interpreteH.getPosition() < t.length() && !end && !failed) {
            if (isContOpen && rc != null && (latest != null && !(latest instanceof RepresentableConteneur))) {
                rc.add(latest);
            }
            failed = true;
            if (interpreteH.parseEND().equals(")")) {
                end = true;
                failed = false;
                continue;
            }
            try {
                interpreteH.interpreteBlank();
                id = interpreteH.interpreteIdentifier();
                interpreteH.interpreteBlank();

                id = id.toLowerCase();

                pos = interpreteH.getPosition();
            } catch (InterpreteException e1) {
                Logger.getLogger(Loader.class.getName()).log(Level.SEVERE,
                        null, e1);
            }
            if (isContOpen && id.trim().length() == 0) {
                try {
                    interpreteH.interpreteParentheseFermante();
                    isContOpen = false;
                } catch (InterpreteException e2) {
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE,
                            null, e2);
                }
            } else {
                failed = true;
            }

            if ("conteneur".equals(id)) {
                try {
                    interpreteH.interpreteParentheseOuvrante();
                    rc = new RepresentableConteneur();
                    sc.add(rc);
                    isContOpen = true;
                } catch (InterpreteException ex) {
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if ("id".equals(id)) {
                interpreteH.interpreteBlank();
                try {
                    idO = interpreteH.interpreteId();
                    newIdO = true;

                } catch (InterpreteException e) {
                }

            }

            // Objects
            if ("scene".equals(id)) {
                try {
                    interpreteH.interpreteBlank();
                    interpreteH.interpreteParentheseOuvrante();
                    interpreteH.interpreteBlank();

                    latest = sc;

                    failed = false;
                    continue;
                } catch (Exception ex) {
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE,
                            null, ex);
                }
                continue;
            }
            if ("bezier".equals(id)) {
                BezierCubique bc = null;
                try {
                    bc = interpreteH.interpreteBezier();
                    sc.add(bc);

                    latest = bc;

                    failed = false;
                    continue;
                } catch (InterpreteException e) {
                    failed = true;
                    e.printStackTrace();
                }
                continue;
            }
            if ("p".equals(id)) {
                Point3D p = null;
                try {
                    p = interpreteH.interpretePoint3DAvecCouleur();
                    sc.add(p);

                    latest = p;

                    failed = false;
                    continue;
                } catch (InterpreteException e) {
                    failed = true;
                    e.printStackTrace();
                }
                continue;
            }
            if ("poly".equals(id)) {
                Polygon p = null;
                try {
                    p = interpreteH.interpretePolygone();
                    sc.add(p);

                    latest = p;

                    failed = false;
                    continue;
                } catch (InterpreteException e) {
                    failed = true;
                    e.printStackTrace();
                }
                continue;
            }
            if ("droite".equals(id)) {
                LineSegment p = null;
                try {
                    p = interpreteH.intepreteSegmentDroite();
                    sc.add(p);

                    latest = p;
                    failed = false;
                    continue;
                } catch (InterpreteException e) {
                    failed = true;
                    e.printStackTrace();
                }
                continue;
            }
            if ("bezier2d".equals(id)) {
                BezierCubique2D bc = null;
                try {
                    bc = interpreteH.interpreteBezier2d();
                    sc.add(bc);

                    latest = bc;

                    failed = false;
                    continue;
                } catch (InterpreteException e) {
                    failed = true;
                    e.printStackTrace();
                }
                continue;
            }
            if ("cube".equals(id)) {
                Cube c = null;
                try {
                    c = interpreteH.interpreteCube();
                    sc.add(c);

                    latest = c;

                    failed = false;
                    continue;
                } catch (InterpreteException e) {
                    failed = true;
                    e.printStackTrace();
                }
                continue;
            }
            if ("tris".equals(id)) {
                try {
                    TRIObject tris = interpreteH.interpreteTriangles();
                    sc.add(tris);

                    latest = tris;

                    failed = false;
                    continue;
                } catch (InterpreteException e) {
                    failed = true;
                    e.printStackTrace();
                }
                continue;
            }
            if ("bspline".equals(id)) {
                try {
                    BSpline b = interpreteH.interpreteBSpline();
                    sc.add(b);

                    latest = b;

                    failed = false;
                    continue;
                } catch (InterpreteException e) {
                    failed = true;
                    e.printStackTrace();
                }
                continue;
            }
            if ("tourbillon".equals(id)) {
                try {
                    Tourbillon to = interpreteH.intepreteTourbillon();
                    sc.add(to);

                    latest = to;

                    failed = false;
                    continue;
                } catch (InterpreteException ex) {
                    failed = true;
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE,
                            null, ex);
                }
            }
            if ("colline".equals(id)) {
                try {
                    Representable r = interpreteH.intepreteColline();
                    sc.add(r);
                    latest = r;
                    failed = false;
                } catch (InterpreteException e) {
                    failed = true;
                    e.printStackTrace();
                }
                continue;
            }
            if ("attracteuretrange".equals(id)) {
                try {
                    AttracteurEtrange ae = interpreteH
                            .intepreteAttracteurEtrange();
                    sc.add(ae);
                    latest = ae;

                    failed = false;
                } catch (InterpreteException ex) {
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE,
                            null, ex);
                    failed = true;
                }
                continue;
            }
            if ("tubulaire".equals(id)) {
                try {
                    Tubulaire ae = interpreteH.intepreteTubulaire();
                    sc.add(ae);
                    latest = ae;
                    failed = false;
                } catch (InterpreteException ex) {
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE,
                            null, ex);
                    failed = true;
                }
                continue;
            }
            if ("simplesphere".equals(id)) {
                try {
                    SimpleSphere ss = interpreteH.intepreteSimpleSphere();
                    sc.add(ss);
                    latest = ss;
                    failed = false;
                } catch (InterpreteException ex) {
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE,
                            null, ex);
                    failed = true;
                }
                continue;
            }
            if ("simplespheretexture".equals(id)
                    | "simplesphereavectexture".equals(id)) {
                try {
                    SimpleSphereAvecTexture ss = interpreteH
                            .interpreteSimpleSphereAvecTexture();
                    sc.add(ss);
                    latest = ss;
                    failed = false;
                } catch (InterpreteException ex) {
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE,
                            null, ex);
                    failed = true;
                }
                continue;
            }
            if ("tetraedre".equals(id)) {
                try {
                    Tetraedre t2 = interpreteH.interpreteTetraedre();
                    sc.add(t2);
                    latest = t2;
                    failed = false;

                } catch (InterpreteException ex) {
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE,
                            null, ex);
                }
                continue;
            }
            if ("plan".equals(id)) {
                try {
                    Plan3D t2 = interpreteH.interpretePlan3D();
                    sc.add(t2);
                    latest = t2;
                    failed = false;

                } catch (InterpreteException ex) {
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE,
                            null, ex);
                }
                continue;
            }
            if ("ellipsoide".equals(id)) {
                try {
                    TRIEllipsoide t2 = interpreteH.interpreteTRIEllipsoide();
                    sc.add(t2);
                    latest = t2;

                    failed = false;

                } catch (InterpreteException ex) {
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE,
                            null, ex);
                }
                continue;
            }/*
            if ("polymap".equals(id)) {
                try {
                    PolyMap pm = interpreteH.interpretePolyMapDef();
                    WireRepresentation wr = new WireRepresentation(pm.getMaillage());
                    sc.add(wr);
                    latest = wr;

                    failed = false;

                } catch (IllegalOperationException ex1) {
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex1);
                } catch (InterpreteException ex) {
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE,
                            null, ex);
                }
                continue;
            }*/
            if ("cameras".equals(id)) {
                StructureMatrix<Camera> cameras;
                try {
                    // TODO ADD POSITION INNIER
                    cameras = interpreteH.interpreteCameraCollection();
                    sc.setCameras(cameras);

                    failed = false;
                } catch (InterpreteException ex) {
                    failed = true;
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE,
                            null, ex);
                }
                continue;
            }
            if ("lumieres".equals(id)) {
                StructureMatrix<Lumiere> lumieres;
                try {
                    // TODO ADD POSITION INNIER
                    lumieres = interpreteH.interpreteLumiereCollection();
                    sc.setLumieres(lumieres);

                    failed = false;
                } catch (InterpreteException ex) {
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE,
                            null, ex);
                }
                continue;
            }
            if ("op".equals(id)) {
                try {
                    interpreteH.interpreteBlank();
                    String idOp = interpreteH.interpreteIdentifier();
                    if ("polyrot".equals(idOp)) {
                        Point3D axeA = interpreteH.interpretePoint3D();
                        Point3D axeB = interpreteH.interpretePoint3D();
                        int numRotations = interpreteH.interpreteInteger();

                        Representable da = sc.getDernierAjout();
                        /*
                         * if (da != null) { sc.rotationPolygone(da, axeA, axeB,
                         * numRotations); }
                         */
                    }
                } catch (InterpreteException ex) {
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE,
                            null, ex);
                    failed = true;
                }
                continue;
            }
            if ("sphere".equals(id)) {
                try {
                    TRISphere t2 = interpreteH.interpreteTRISphere();
                    sc.add(t2);
                    latest = t2;
                    failed = false;

                } catch (InterpreteException ex) {
                    failed = true;
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE,
                            null, ex);
                }
                continue;
            }
            if ("nurbs".equals(id)) {
                try {
                    NurbsSurface n = interpreteH.interpreteNurbs();
                    sc.add(n);
                    latest = n;
                    failed = false;

                } catch (InterpreteException ex) {
                    failed = true;
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE,
                            null, ex);
                }
                continue;
            }
            if ("quads".equals(id)) {
                try {
                    Quads q = interpreteH.interpreteQuads();
                    sc.add(q);
                    latest = q;
                    failed = false;

                } catch (InterpreteException ex) {
                    failed = true;
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE,
                            null, ex);
                }
                continue;
            }
            if ("is".equals(id)) {
                try {
                    interpreteH.interpreteBlank();
                    String ido = interpreteH.interpreteIdentifier();
                    interpreteH.interpreteBlank();
                    Representable r = latest;
                    /*if (r != null) {
                     r.setId(ido);
                     }*/
                    failed = false;

                } catch (InterpreteException ex) {
                    failed = true;
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE,
                            null, ex);
                }
                continue;
            }
            if ("tri".equals(id)) {
                try {
                    TRI poso = interpreteH.interpreteTriangle();
                    sc.add(poso);
                    latest = poso;
                    failed = false;
                } catch (InterpreteException ex) {
                    failed = true;
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE,
                            null, ex);
                }
                continue;
            }
            if ("texture".equals(id)) {
                try {
                    interpreteH.interpreteBlank();
                    String ido = interpreteH.interpreteIdentifier();
                    interpreteH.interpreteBlank();
                    ITexture tc = interpreteH.interpreteTColor();
                    interpreteH.interpreteBlank();

                    Representable r = sc.find(ido);
                    if (r != null && r.supporteTexture()) {
                        r.texture(tc);
                    }
                    failed = false;

                } catch (InterpreteException ex) {
                    failed = true;
                    Logger.getLogger(Loader.class.getName()).log(Level.SEVERE,
                            null, ex);
                }
            }

        }

        //sc.flushImports();
        return !failed;
    }

    /*__
     * @param string
     * @param s
     */
    public void loadTEST(String string, Scene s) {
        String h = System.getProperty("user.home");
        String p = System.getProperty("file.separator");
        String txtCHEMIN = h + p + "PMMatrix.data" + p + "testscripts" + p;

        this.loadIF(new File(txtCHEMIN + string), s);

    }

    public boolean saveBin(File f, Scene sc)
            throws VersionNonSupporteeException,
            ExtensionFichierIncorrecteException {
        if (f.getAbsolutePath().toLowerCase().endsWith("bmood")
                || f.getAbsolutePath().toLowerCase().endsWith("bmoo"))
            ;
        else {
            System.err.println("Extension de fichier requise: .bmood ou bmoo");
            throw new ExtensionFichierIncorrecteException();
        }
        ObjectOutputStream objectOutputStream = null;
        FileOutputStream fis = null;
        try {
            fis = new FileOutputStream(f);
            objectOutputStream = new ObjectOutputStream(fis);
            Long type = TYPE_BINA;
            String version = Scene.VERSION;
            objectOutputStream.writeObject(type);
            objectOutputStream.writeObject(version);
            appelVersionSpecifiqueSave(version, f);
            if (type == TYPE_TEXT) {
                return false;
            }
            objectOutputStream.writeObject(sc);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Loader.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                objectOutputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return false;
    }

    public void saveTxt(File fichier, Scene scene) {
        ModeleIO.sauvergarderTXT(scene, fichier);

    }

    public void setRepertoire(String dir) {
        this.repertoire = dir;
    }
}
