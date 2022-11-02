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
 * 2013-2020 Manuel Dahmen
 */
package one.empty3.library;

import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;

import one.empty3.library.core.TemporalComputedObject3D;
import one.empty3.library.core.lighting.Colors;
import one.empty3.library.core.raytracer.RtIntersectInfo;
import one.empty3.library.core.raytracer.RtMatiere;
import one.empty3.library.core.raytracer.RtRay;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;

public class Representable /*extends RepresentableT*/ implements Serializable, Comparable, XmlRepresentable, MatrixPropertiesObject, TemporalComputedObject3D {


    public static final int DISPLAY_ALL = 0;
    public static final int SURFACE_DISPLAY_TEXT_QUADS = 1;
    private static final int SURFACE_DISPLAY_TEXT_TRI = 2;
    public static final int SURFACE_DISPLAY_COL_QUADS = 3;
    public static final int SURFACE_DISPLAY_COL_TRI = 4;
    public static final int SURFACE_DISPLAY_LINES = 5;
    public static final int SURFACE_DISPLAY_POINTS = 6;
    private static final String[] displayTypes = {"All", "Textured Quad", "SURFACE_DISPLAY_TEXT_TRI", "SURFACE_DISPLAY_COL_QUADS", "SURFACE_DISPLAY_COL_TRI", "SURFACE_DISPLAY_LINES", "SURFACE_DISPLAY_POINTS"};
    private int displayType = 0; //SURFACE_DISPLAY_TEXT_QUADS;
    public static Point3D SCALE1;
    public static final ITexture DEFAULT_TEXTURE = new TextureCol(Colors.random());
    protected static ArrayList<Painter> classPainters = new ArrayList<Painter>();
    public StructureMatrix<Rotation> rotation = new StructureMatrix<>(0, Rotation.class);
    protected double NFAST = 100;
    protected RtMatiere materiau;
    protected ITexture CFAST = DEFAULT_TEXTURE;
    // protected Barycentre bc = new Barycentre();
    protected Representable parent;
    protected Scene scene;
    protected ITexture texture = DEFAULT_TEXTURE;
    private String id;
    private Painter painter = null;
    private int RENDERING_DEFAULT = 0;
    protected Render render; //= Render.getInstance(0, -1);
    protected Point3D scale;
    protected StructureMatrix<T> T; // = new StructureMatrix<T>(0, one.empty3.library.T.class);
    protected static HashMap<String, StructureMatrix> defaultHashMapData = new HashMap<String, StructureMatrix>();

    public Representable() {
        if (!(this instanceof Matrix33 || this instanceof Point3D || this instanceof Camera)) {
            rotation.setElem(new Rotation());
            //scale = new Point3D(1d, 1d, 1d);
            //texture = new TextureCol(Colors.random());

        }
    }

    public static void setPaintingActForClass(ZBuffer z, Scene s, PaintingAct pa) {
        Painter p = null;
        classPainters().add(new Painter(z, s, Representable.class));
        p.addAction(pa);
    }

    private static ArrayList<Painter> classPainters() {
        return classPainters;
    }

    public StructureMatrix<Rotation> getRotation() {
        return rotation;
    }

    public void setRotation(StructureMatrix<Rotation> rotation) {
        this.rotation = rotation;
    }

    public Point3D rotate(Point3D p0, Representable ref) {
        if (p0 == null) {
            return Point3D.O0;
        } else if (ref != null && ref.getRotation() != null && ref.getRotation().getElem() != null)
            return ref.getRotation().getElem().rotation(p0);
        else
            return p0;
    }


    public String id() {
        return id;
    }

    public void id(String id) {
        this.id = id;
    }

    public void informer(Representable parent) {
        this.parent = parent;
    }


    public void replace(String moo) {
        throw new UnsupportedOperationException("Operation non supportee");
    }
/*
    public void scene(Scene scene) {
        this.scene = scene;

    }
*/

    public boolean supporteTexture() {
        return false;
    }

    public ITexture texture(Bitmap img) {
        return this.texture;
    }

    public void texture(ITexture tc) {
        this.texture = tc;
    }


    /*__
     * DOn't call ZBuffer dessiine methods here: it would loop.
     *
     * @param z ZBuffer use plot or dessine(P) or tracerTriangle(TRI, Itexture)
     */
    public void drawStructureDrawFast(ZBuffer z) {
        throw new UnsupportedOperationException("No genral method for drawing objects");
    }

    public boolean ISdrawStructureDrawFastIMPLEMENTED(ZBuffer z) {
        return false;
    }

    /*__
     * When correctly initialized, PaintingAct action method is called while
     * the shape is rendered.
     *
     * @param z  the actual ZBuffer in which the action should occurs
     * @param s  the scene in which the actions can access to other objects properties.
     *           Optional parameter
     * @param pa The "painting act" (term referring to history of arts).
     */
    public void setPaintingAct(ZBuffer z, Scene s, PaintingAct pa) {
        this.painter = new Painter(z, s, this);
        pa.setObjet(this);
        pa.setScene(s);
        pa.setZBuffer(z);
        painter.addAction(pa);
    }

    public Painter getPainter() {
        return painter;
    }

    public void setPainter(Painter painter) {
        this.painter = painter;
    }

    public void paint() {
        if (getPainter() != null) {
            getPainter().getPaintingAct().paint();
        }
    }

    public Intersects.Intersection intersects(RtRay ray, RtIntersectInfo cii) {
        // TODO Implements
        return null;
    }

    public Representable intersects(Representable r2) {
        throw new UnsupportedOperationException("Pas implémenté  en cours" +
                "");
    }

    public void become(Representable r) {
        if (this.getClass().equals(r.getClass())) {
            set(r);
        }
    }

    private void set(Representable r) {
    }

    /*__
     *
     * @param o
     * @return ???
     */
    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Representable))
            return 0;
        else
            return 1;

    }

    public void draw(ZBufferImpl zBuffer) {
    }

    @Override
    public StructureMatrix getDeclaredProperty(String name) {

        return getDeclaredDataStructure().get(name);
    }


    private Map<String, StructureMatrix> declaredDataStructure;// = Collections.synchronizedMap(new HashMap());

    public Map<String, StructureMatrix> getDeclaredDataStructure() {
        if ((!(this instanceof Point3D)) && (declaredDataStructure == null))
            declaredDataStructure = Collections.synchronizedMap(new HashMap());
        else declaredDataStructure = defaultHashMapData;

        return declaredDataStructure;
    }

    private Map<String, StructureMatrix> declaredLists;//= new HashMap<>();

    public Map<String, StructureMatrix> getDeclaredLists() {
        return declaredLists;
    }

    public ITexture getTexture() {
        return texture;
    }

    public void setTexture(ITexture texture) {
        this.texture = texture;
    }

    public Class getPropertyType(String propertyName) throws NoSuchMethodException {
        Method propertyGetter = null;
        propertyGetter = this.getClass().getMethod("get" + ("" + propertyName.charAt(0)).toUpperCase() + (propertyName.length() > 1 ? propertyName.substring(1) : ""));
        return propertyGetter.getReturnType();
    }

    public void setProperty(String propertyName, Object value) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method propertySetter = null;

        propertySetter = this.getClass().getMethod("set" + ("" + propertyName.charAt(0)).toUpperCase() + (propertyName.substring(1)), value.getClass());
        propertySetter.invoke(this, value);
        System.out.println("RType : " + this.getClass().getName() + " Property: " + propertyName + " New Value set " + getProperty(propertyName));
    }

    public Object getProperty(String propertyName) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method propertySetter = null;
        propertySetter = this.getClass().getMethod("get" + ("" + propertyName.charAt(0)).toUpperCase() + propertyName.substring(1));
        return propertySetter.invoke(this);
    }

    public String toString() {
        return "Representable()";
    }


    public void declareProperties() {
        getDeclaredDataStructure().clear();
        if (getRotation() != null && getRotation().getElem() != null) {
            getDeclaredDataStructure().put("rotation/Rotation", rotation);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public Map<String, StructureMatrix> declarations() {
        declareProperties();
        Map<String, StructureMatrix> dec = Collections.synchronizedMap(new HashMap<>());
        getDeclaredDataStructure().forEach((s, structureMatrix) -> dec.put(s, structureMatrix));
        return dec;
    }


    public ITexture getCFAST() {
        return CFAST;
    }

    public void setCFAST(ITexture CFAST) {
        this.CFAST = CFAST;
    }

    public Point3D getScale() {
        return scale;
    }

    public void setScale(Point3D scale) {
        this.scale = scale;
    }


    public void xmlRepresentation(String filesPath, StringBuilder stringBuilder, Double o) {
        stringBuilder.append("<Double class=\"" + o.getClass() + "\">" + o + "</Double>");
    }

    public void xmlRepresentation(String filesPath, StringBuilder stringBuilder, Boolean o) {
        stringBuilder.append("<Boolean class=\"" + o.getClass() + "\">" + o + "</Boolean>");
    }

    public void xmlRepresentation(String filesPath, StringBuilder stringBuilder, Integer o) {
        stringBuilder.append("<Integer class=\"" + o.getClass() + "\">" + o + "</Integer>");
    }

    public void xmlRepresentation(String filesPath, StringBuilder stringBuilder, String o) {
        stringBuilder.append("<String class=\"" + o.getClass() + "\"> <![CDATA[ " + o + " ]]> </String>");
    }

    public void xmlRepresentation(String filesPath, StringBuilder stringBuilder, File o) {
        stringBuilder.append("<String filename=\"" + o.getName() + "\"class=\"" + o.getClass() + "\"> <![CDATA[ " + o.getName() + " ]]> </String>");
        try {
            FileInputStream fileInputStream = new FileInputStream(o);
            File copy = new File(filesPath + "/" + o.getName());
            copy.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(copy);
            int read = -1;
            byte[] bytes = new byte[4096];
            while (read != 0) {

                read = fileInputStream.read(bytes);
                fileOutputStream.write(bytes, 0, read);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void xmlRepresentation(String filesPath, MatrixPropertiesObject parent, StringBuilder stringBuilder, ArrayList o) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void xmlRepresentation(String filesPath, StringBuilder stringBuilder, Object o) {
        if (o == null) return;
        if (o instanceof StructureMatrix) {
            xmlRepresentation(filesPath, stringBuilder, (StructureMatrix) o);
        } else if (o instanceof Representable) {
            xmlRepresentation(filesPath, stringBuilder, ((Representable) o));
        } else {
            switch (o.getClass().getName()) {
                case "java.lang.Boolean":
                    xmlRepresentation(filesPath, stringBuilder, ((Boolean) o));
                    break;
                case "java.lang.Double":
                    xmlRepresentation(filesPath, stringBuilder, ((Double) o));
                    break;
                case "java.lang.Integer":
                    xmlRepresentation(filesPath, stringBuilder, ((Integer) o));
                    break;
                case "java.lang.String":
                    xmlRepresentation(filesPath, stringBuilder, ((String) o));
                    break;
                case "java.util.ArrayList":
                    //xmlRepresentation(filesPath, (XmlRepresentable)parent, stringBuilder, ((ArrayList) o));
                    break;
                case "java.io.File":
                    xmlRepresentation(filesPath, stringBuilder, ((File) o));
                    break;
            }

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void xmlRepresentation(String filesPath, StringBuilder stringBuilder, Representable is) {
        if (stringBuilder.toString().length() == 0) {
            stringBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        }
        stringBuilder.append("<Representable class=\"" + is.getClass().getName() + "\">\n");
        is.declareProperties();
        is.declarations().forEach((s, o) -> {
            if (o instanceof StructureMatrix) {
                StructureMatrix b = (StructureMatrix) o;
                xmlRepresentation(filesPath, s, stringBuilder, o);
            }

        });
        stringBuilder.append("</Representable>\n");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void xmlRepresentation(String filesPath, MatrixPropertiesObject parent, StringBuilder stringBuilder) {
        xmlRepresentation(filesPath, stringBuilder, (Representable) parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void xmlRepresentation(String filesPath, String name, StringBuilder stringBuilder, StructureMatrix is) {
        stringBuilder.append("<StructureMatrix name=\"" + name + "\" dim=\"" + is.getDim() + "\" class=\"" + is.getClass().getName() + "\" typeClass=\"" + is.getClassType().getName() + "\">");
        switch (is.getDim()) {
            case 0:
                stringBuilder.append("<Data>");
                stringBuilder.append("<Cell l=\"0\" c=\"0\">");
                xmlRepresentation(filesPath, stringBuilder, is.getElem());
                stringBuilder.append("</Cell>");
                stringBuilder.append("</Data>");
                break;
            case 1:
                stringBuilder.append("<Data>");
                int[] i1 = new int[]{0, 0};
 /*               is.data1d.forEach(new Consumer() {
                    @Override
                    public void accept(Object o) {
                        stringBuilder.append("<Cell l=\"" + i1[0] + "\" c=\"" + i1[1] + "\">");
                        xmlRepresentation(filesPath, stringBuilder, o);
                        stringBuilder.append("</Cell>");
                        i1[1]++;

                    }

                });
   */
                stringBuilder.append("</Data>");
                break;
            case 2:
                stringBuilder.append("<Data>");
                int[] i2 = new int[]{0, 0};
                is.data2d.forEach(new Consumer<List>() {
                    @Override
                    public void accept(List ts) {
                        i2[1] = 0;
     /*                   ts.forEach(new Consumer() {
                            @Override
                            public void accept(Object o) {
                                stringBuilder.append("<Cell l=\"" + i2[0] + "\" c=\"" + i2[1] + "\">");
                                xmlRepresentation(filesPath, stringBuilder, o);
                                i2[1]++;
                                stringBuilder.append("</Cell>");
                            }
                        });
*/
                        i2[0]++;
                    }
                });
                stringBuilder.append("</Data>");
                break;


        }
        stringBuilder.append("</StructureMatrix>");
    }

    /*
        Map<String, Double> data = new HashMap<>();
        public void addData(String key, double number) {
            data.put(key, number);
        }
        public double getData(String key)
        {
            return data.get(key);
        }
    */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public MatrixPropertiesObject copy() throws CopyRepresentableError, IllegalAccessException, InstantiationException {
        Class<? extends Representable> aClass = this.getClass();
        try {
            Representable representable = aClass.newInstance();
            representable.declareProperties();
            declarations().forEach((s, structureMatrix) -> {
                try {
                    try {
                        representable.setProperty(s.split("/")[0], structureMatrix.copy());
                    } catch (CopyRepresentableError copyRepresentableError) {
                        copyRepresentableError.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            });
            return representable;
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        return null;
    }

    public int getDisplayType() {
        return displayType;
    }

    public void setDisplayType(int displayType) {
        this.displayType = displayType;
    }

    public static String[] getDisplayTypes() {
        return displayTypes;
    }


    @Override
    public double T(T t) {
        this.T.setElem(t);
        return t.getT();
    }

    @Override
    public Point3D calculerPointT(double t) {
        return null;
    }

    @Override
    public Point3D calculerCurveT(double u, double t) {
        return null;
    }

    @Override
    public Point3D calculerSurfaceT(double u, double v, double t) {
        return null;
    }

    public void setPosition(Point3D calcCposition) {
        getRotation().getElem().getCentreRot().setElem(calcCposition);
    }

    public ITexture texture() {
        return texture;
    }
}


