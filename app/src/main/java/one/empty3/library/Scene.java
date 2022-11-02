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
 * 2013 2021 Manuel Dahmen
 */
package one.empty3.library;

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import one.empty3.library.core.animation.Animation;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Scene extends Representable implements Serializable {

    public static final String VERSION = "2019.1";
    /*__
     *
     */
    public String author;
    public String date;
    public String description;
    public StructureMatrix<Camera> cameraActive = new StructureMatrix<>(0, Camera.class);
    private StructureMatrix<Representable> objets = new StructureMatrix<>(1, Representable.class);
    private StructureMatrix<Animation> animations = new StructureMatrix<>(1, Animation.class);
    private StructureMatrix<Camera> cameras = new StructureMatrix<>(1, Camera.class);
    private StructureMatrix<ITexture> colors = new StructureMatrix<>(1, ITexture.class);
    private StructureMatrix<Lumiere> lumieres = new StructureMatrix<>(1, Lumiere.class);
    private SceneCadre cadre = new SceneCadre();
    private GTime gt = new GTime();
    private Lumiere lumiereActive;
    private String DESCRIPTION;
    private Time time;
    private List<Move> moves;

    {

    }

    // FOR LOADER
    private Representable dernierAjout;

    public Scene() {

        super();
        Camera camera = new Camera();
        cameras.setElem(camera, 0);
        cameraActive.setElem(camera);
    }


    public boolean add(Representable add) {

        this.dernierAjout = add;

        //add.scene(this);

        objets.add(1, add);

        return true;

    }

    public boolean add(Representable add, ZBuffer zBuffer) {

        this.dernierAjout = add;


        //add.scene(this);

        add.setPainter(new Painter(zBuffer, this));

        objets.add(1, add);

        return true;

    }

    @Deprecated
    public Camera camera() {
        return cameraActive();
    }

    @Deprecated
    public void camera(Camera c) {
        cameraActive.setElem(c);
    }

    public Camera cameraActive() {
        if (cameraActive != null) {
            return cameraActive.getElem();
        } else if (cameras.data1d.size() > 0) {
            return cameras.getElem(0);
        }
        return Camera.PARDEFAULT;
    }

    public void cameraActive(Camera c) {
        this.cameraActive.setElem(c);
        if (!cameras.getData1d().contains(c)) {
            cameras.getData1d().add(0, c);
        }
    }

    public List<Camera> cameras() {
        return this.cameras.getData1d();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    protected int colorAdd(int[] cs) {
        float[] compArray = new float[4];
        float[] compArray3 = new float[4];

        int l = cs.length;
        for (int c = 0; c < l; c++) {
            for (int i = 0; i < 3; i++) {
                compArray3 = Color.valueOf(cs[i]).getComponents(compArray);

                compArray3[i] += compArray[i] / l;
            }
        }
        int res = Color.valueOf(compArray3[0], compArray3[1], compArray3[2], compArray3[3]).toArgb();

        return res;
    }

    public void dumpDATA() {
        this.setDESCRIPTION(toString());
    }

    public Representable find(String ido) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void flushImports() {
        dernierAjout = null;
    }

    public SceneCadre getCadre() {
        return cadre;
    }

    public void setCadre(SceneCadre cadre) {
        this.cadre = cadre;
    }

    public Representable getDernierAjout() {
        return dernierAjout;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String dESCRIPTION) {
        DESCRIPTION = dESCRIPTION;
    }

    public GTime getGt() {
        return gt;
    }

    public Iterator<Representable> iterator() {
        return objets.getData1d().iterator();
    }

    public Object[] liste() {
        Iterator<Representable> ir = iterator();

        Object[] liste = new Object[size()];
        int i = 0;
        while (ir.hasNext()) {
            liste[i] = ir.next().toString();
            liste[i] = ((String) liste[i]).length() >= 100 ? ((String) liste[i]).substring(0, 100) : liste[i];
            i++;
        }
        return liste;
    }

    public Lumiere lumiereActive() {
        if (lumiereActive != null) {
            return lumiereActive;
        } else if (lumieres.getData1d().size() > 0) {
            return lumieres.getData1d().get(0);
        }
        return LumierePointSimple.PARDEFAUT;
    }

    public List<Lumiere> lumieres() {
        return lumieres.getData1d();
    }

    public int lumiereTotaleCouleur(int c, Point3D p, Point3D n) {
        if (n == null || Point3D.INFINI.equals(p) || lumieres.getData1d().isEmpty()) {
            return c;
        }

        double[] t = new double[]{0., 0., 0., 0.};

        int cpt = 0;

        for (Lumiere l : lumieres.getData1d()) {


            int cP = l.getCouleur(c, p, n);


            double[] tadd = Lumiere.getDoubles(cP);
            for (int j = 0; j < 3; j++)
                t[j] += tadd[j];

            cpt++;
        }

        for (int j = 0; j < 3; j++) {
            t[j] /= cpt;
        }

        return Lumiere.getInt(t);
    }

    public Representable place(MODObjet aThis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public boolean remove(Representable rem) {
        return objets.getData1d().remove(rem);
    }

    public int size() {
        return objets.getData1d().size();
    }

    @Override
    public boolean supporteTexture() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void texture(ITexture c) {
        colors.getData1d().add(c);
    }

    public ArrayList<ITexture> textures() {
        return (ArrayList<ITexture>) colors.getData1d()
                ;
    }

    @Override
    public String toString() {
        String str = "scene \n(\n\n";

        Iterator<Representable> it = iterator();
        while (it.hasNext()) {
            Representable r = it.next();
            if (r instanceof Point3D) {
                str += ((Point3D) r).toLongString();
            } else {
                str += r.toString();
            }
        }
        str += "cameras (\n\t";
        if (cameras.getData1d().isEmpty()) {
            str += "\n\t" + cameraActive().toString() + "\n";
        }
        Iterator<Camera> itC = cameras.getData1d().iterator();
        while (itC.hasNext()) {
            str += "\n\t" + itC.next().toString() + "\n";
        }
        str += "\n)";

        str += "\n\n)\n";
        return str;
    }

    public void updateFromText(Representable selectedComponent, String text) {
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    /*__
     * @return
     */
    public boolean updateTime() {
        return false;
    }

    public Time getTime() {
        return time;
    }

    public List<Move> getMoves() {
        return moves;
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("objets/Objets à peindre", objets);
        getDeclaredDataStructure().put("animations/Animation (pas implémenté maintenant jamais", animations);
        getDeclaredDataStructure().put("cameras/Caméras de la scène. cameraActive caméra en cours", cameras);
        getDeclaredDataStructure().put("lumieres/Lumières additionnelles", lumieres);
        getDeclaredDataStructure().put("cameraActive/cameraActive", this.cameraActive);
    }

    public static String getVERSION() {
        return VERSION;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Camera getCameraActive() {
        return cameraActive.getElem();
    }

    public void setCameraActive(Camera cameraActive) {
        this.cameraActive.setElem(cameraActive);
    }


    public void setCameraActive(StructureMatrix<Camera> cameraActive) {
        this.cameraActive = cameraActive;
    }

    public StructureMatrix<Representable> getObjets() {
        return objets;
    }

    public void setObjets(StructureMatrix<Representable> objets) {
        this.objets = objets;
    }

    public StructureMatrix<Animation> getAnimations() {
        return animations;
    }

    public void setAnimations(StructureMatrix<Animation> animations) {
        this.animations = animations;
    }

    public StructureMatrix<Camera> getCameras() {
        return cameras;
    }

    public void setCameras(StructureMatrix<Camera> cameras) {
        this.cameras = cameras;
    }

    public StructureMatrix<ITexture> getColors() {
        return colors;
    }

    public void setColors(StructureMatrix<ITexture> colors) {
        this.colors = colors;
    }

    public StructureMatrix<Lumiere> getLumieres() {
        return lumieres;
    }

    public void setLumieres(StructureMatrix<Lumiere> lumieres) {
        this.lumieres = lumieres;
    }

    public void setGt(GTime gt) {
        this.gt = gt;
    }

    public Lumiere getLumiereActive() {
        return lumiereActive;
    }

    public void setLumiereActive(Lumiere lumiereActive) {
        this.lumiereActive = lumiereActive;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public void setDernierAjout(Representable dernierAjout) {
        this.dernierAjout = dernierAjout;
    }

    public void clear() {
        objets.reset();
    }
}
