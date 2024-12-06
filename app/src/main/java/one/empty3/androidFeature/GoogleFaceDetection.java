package one.empty3.androidFeature;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import one.empty3.Polygon1;
import one.empty3.library.ColorTexture;
import one.empty3.library.Lumiere;
import one.empty3.library.Matrix33;
import matrix.PixM;
import one.empty3.library.Point3D;
import one.empty3.library.Serialisable;
import one.empty3.library.StructureMatrix;
import one.empty3.library1.tree.AlgebraicFormulaSyntaxException;
import one.empty3.library1.tree.AlgebraicTree;
import one.empty3.library1.tree.TreeNodeEvalException;
import one.empty3.libs.Image;

public class GoogleFaceDetection
        implements Parcelable, Serializable, Serialisable {
    static GoogleFaceDetection instance;
    private static GoogleFaceDetection instance2;
    private FaceData.Surface selectedSurface;
    public static double[] TRANSPARENT = Lumiere.getDoubles(Color.BLACK);
    private List<FaceData> dataFaces;
    @NotNull
    private Bitmap bitmap;
    private int versionFile = 5;

    public static GoogleFaceDetection getInstance(boolean newInstance, Bitmap bitmap) {
        if (instance == null || newInstance)
            instance = new GoogleFaceDetection(new Image(bitmap));
        return instance;
    }

    public static boolean isInstance() {
        if (instance != null)
            return true;
        else
            return false;
    }

    protected GoogleFaceDetection(Parcel in) {
        in.readTypedObject(new Creator<Object>() {
            @Override
            public Object createFromParcel(Parcel parcel) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    bitmap = parcel.readParcelable(ClassLoader.getSystemClassLoader());
                }
                GoogleFaceDetection googleFaceDetection = GoogleFaceDetection.getInstance(false,
                        bitmap);
                int numFaces = parcel.readInt();
                for (int face = 0; face < numFaces; face++) {
                    int id = parcel.readInt();

                    FaceData faceData = new FaceData();

                    int numPoly = parcel.readInt();

                    for (int i = 0; i < numPoly; i++) {
                        Polygon1 polygon = Polygon1.CREATOR.createFromParcel(parcel);
                        matrix.PixM contours = matrix.PixM.CREATOR.createFromParcel(parcel);
                        int colorFill = parcel.readInt();
                        int colorContours = parcel.readInt();
                        int colorTransparent = parcel.readInt();

                        faceData.getFaceSurfaces().add(new FaceData.Surface(id, polygon, contours, colorFill, colorContours,
                                colorTransparent, contours.copy(), false));

                    }
                    googleFaceDetection.getDataFaces().add(faceData);
                }
                return googleFaceDetection;
            }

            @Override
            public Object[] newArray(int i) {
                return new Object[0];
            }
        });
    }

    public static final Creator<GoogleFaceDetection> CREATOR = new Creator<GoogleFaceDetection>() {
        @Override
        public GoogleFaceDetection createFromParcel(Parcel in) {
            return new GoogleFaceDetection(in);
        }

        @Override
        public GoogleFaceDetection[] newArray(int size) {
            return new GoogleFaceDetection[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(getDataFaces().size());
        for (int face = 0; face < getDataFaces().size(); face++) {
            parcel.writeInt(getDataFaces().size());

            FaceData faceData = getDataFaces().get(face);

            parcel.writeInt(faceData.getFaceSurfaces().size());

            for (int j = 0; j < faceData.getFaceSurfaces().size(); j++) {
                FaceData.Surface surface = faceData.getFaceSurfaces().get(j);
                parcel.writeParcelable(surface.polygon, 1);
                parcel.writeParcelable(surface.contours, 1);
                parcel.writeParcelable(surface.actualDrawing, 1);
                parcel.writeInt(surface.colorFill);
                parcel.writeInt(surface.colorContours);
                parcel.writeInt(surface.colorTransparent);

            }
        }
    }

    public static void setInstance(@NotNull GoogleFaceDetection googleFaceDetection) {
        instance = googleFaceDetection;
    }

    public static void setInstance2(@NotNull GoogleFaceDetection googleFaceDetection) {
        instance2 = googleFaceDetection;
    }

    public static GoogleFaceDetection getInstance2() {
        return instance2;
    }

    public static boolean isInstance2() {
        return instance2 != null;
    }

    public static class FaceData implements Serializable, Serialisable {
        private matrix.PixM photo = new matrix.PixM(1, 1);

        @Override
        public Serialisable decode(DataInputStream in) {
            FaceData faceData = new FaceData();
            photo = (matrix.PixM) new matrix.PixM(1, 1).decode(in);
            if (photo == null)
                photo = new matrix.PixM(1, 1);
            return faceData;
        }

        public int encode(DataOutputStream out) {
            if (photo == null)
                photo = new matrix.PixM(1, 1);
            photo.encode(out);
            return 0;
        }

        @Override
        public int type() {
            return 0;
        }

        public matrix.PixM getPhoto() {
            return photo;
        }

        public void setPhoto(matrix.PixM photo) {
            this.photo = photo;
        }

        public static class Surface implements Serializable, Serialisable {
            private boolean drawOriginalImageContour;
            private int colorFill;
            private int colorContours;
            private int colorTransparent = -1;
            private int surfaceId;
            private Polygon1 polygon;
            private matrix.PixM contours;
            private matrix.PixM filledContours;
            @Nullable
            public matrix.PixM actualDrawing;

            public Surface() {

            }

            @Override
            public Serialisable decode(DataInputStream in) {
                try {
                    Surface surface = new Surface();
                    surface.colorFill = in.readInt();
                    surface.colorContours = in.readInt();
                    surface.colorTransparent = in.readInt();
                    surface.surfaceId = in.readInt();
                    surface.polygon = (Polygon1) new Polygon1().decode(in);
                    surface.contours = (matrix.PixM) new matrix.PixM(1, 1).decode(in);
                    surface.filledContours = (matrix.PixM) new matrix.PixM(1, 1).decode(in);
                    surface.drawOriginalImageContour = in.readBoolean();
                    surface.actualDrawing = (matrix.PixM) new matrix.PixM(1, 1).decode(in);
                    return surface;
                } catch (Exception exception) {
                    exception.printStackTrace();
                    throw new RuntimeException("Exception loading Surface" + exception.getLocalizedMessage());
                }
            }

            @Override
            public int encode(DataOutputStream out) {
                try {
                    out.writeInt(colorFill);
                    out.writeInt(colorContours);
                    out.writeInt(colorTransparent);
                    out.writeInt(surfaceId);
                    if (polygon == null)
                        polygon = new Polygon1();
                    polygon.encode(out);
                    if (contours == null)
                        contours = new matrix.PixM(1, 1);
                    contours.encode(out);
                    if (filledContours == null) {
                        filledContours = new matrix.PixM(1, 1);
                    }
                    filledContours.encode(out);
                    out.writeBoolean(drawOriginalImageContour);
                    if (actualDrawing == null)
                        actualDrawing = new matrix.PixM(1, 1);
                    actualDrawing.encode(out);

                } catch (Exception exception) {
                    exception.printStackTrace();
                    throw new RuntimeException(exception);
                }
                return 0;
            }

            @Override
            public int type() {
                return 5;
            }


            public Surface(int surfaceId, Polygon1 polygon, matrix.PixM contours,
                           int colorFill, int colorContours, int colorTransparent,
                           matrix.PixM filledContours, boolean drawOriginalImageContour) {
                this.surfaceId = surfaceId;
                this.polygon = polygon;
                this.contours = contours;
                this.colorFill = colorFill;
                this.colorContours = colorContours;
                this.colorTransparent = colorTransparent;
                this.filledContours = filledContours;
                this.drawOriginalImageContour = drawOriginalImageContour;
            }

            public int getSurfaceId() {
                return surfaceId;
            }

            public void setSurfaceId(int surfaceId) {
                this.surfaceId = surfaceId;
            }

            public Polygon1 getPolygon1() {
                return polygon;
            }

            public void setPolygon1(Polygon1 polygon) {
                this.polygon = polygon;
            }

            public PixM getContours() {
                return contours;
            }

            public void setContours(PixM contours) {
                this.contours = contours;
            }

            public int getColorFill() {
                return colorFill;
            }

            public void setColorFill(int colorFill) {
                this.colorFill = colorFill;
            }

            public int getColorContours() {
                return colorContours;
            }

            public void setColorContours(int colorContours) {
                this.colorContours = colorContours;
            }

            public int getColorTransparent() {
                return colorTransparent;
            }

            public void setColorTransparent(int colorTransparent) {
                this.colorTransparent = colorTransparent;
            }

            public void computeFilledSurface(PointF position, double scale) {
                polygon.texture(new ColorTexture(colorFill));
                filledContours = polygon.fillPolygon2D(this, null, contours.getImage(),
                        colorTransparent, 0, position, scale);
            }

            public boolean isContaning(Point pInPicture) {
                StructureMatrix<Point3D> boundRect2d = polygon.getBoundRect2d();
                double[] values = contours.getValues((int) (double) (pInPicture.x - boundRect2d.getElem(0).get(0)),
                        (int) (double) (pInPicture.y - boundRect2d.getElem(0).get(1)));
                return values.equals(TRANSPARENT);
            }

            @Override
            public String toString() {
                return "Surface{" +
                        "colorFill=" + colorFill +
                        ", colorContours=" + colorContours +
                        ", colorTransparent=" + colorTransparent +
                        ", surfaceId=" + surfaceId +
                        ", polygon=" + polygon +
                        ", contours=" + contours +
                        ", actualDrawing=" + actualDrawing +
                        '}';
            }

            public void setFilledContours(matrix.PixM filledContours) {
                this.filledContours = filledContours;
            }

            public PixM getFilledContours() {
                return filledContours;
            }

            @Nullable
            public PixM getActualDrawing() {
                return actualDrawing;
            }

            public void setActualDrawing(@Nullable matrix.PixM actualDrawing) {
                this.actualDrawing = actualDrawing;
            }

            public boolean isDrawOriginalImageContour() {
                return drawOriginalImageContour;
            }

            public void setDrawOriginalImageContour(boolean drawOriginalImageContour) {
                this.drawOriginalImageContour = drawOriginalImageContour;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Surface surface = (Surface) o;
                return getColorFill() == surface.getColorFill()
                        && getColorContours() == surface.getColorContours()
                        && getColorTransparent() == surface.getColorTransparent()
                        && getSurfaceId() == surface.getSurfaceId()
                        && Objects.equals(getPolygon1(), surface.getPolygon1())
                        && Objects.equals(getContours(), surface.getContours())
                        && Objects.equals(getFilledContours(), surface.getFilledContours())
                        && Objects.equals(getActualDrawing(), surface.getActualDrawing());
            }

            @Override
            public int hashCode() {
                return Objects.hash(getColorFill(), getColorContours(), getColorTransparent(), getSurfaceId(), getPolygon1(), getContours(), getFilledContours());
            }

            @NotNull
            public Point3D getPolygonAngles() {
                int size = polygon.getPoints().data1d.size();
                List<Point3D> data1d = polygon.getPoints().data1d;
                double xx = data1d.get(size / 4).getX() - data1d.get(3 * size / 4).getX();
                double xy = data1d.get(size / 4).getY() - data1d.get(3 * size / 4).getY();
                double yx = data1d.get(size / 2).getX() - data1d.get(0).getX();
                double yy = data1d.get(size / 2).getY() - data1d.get(0).getY();
                return new Point3D(Math.atan(xx / xy), Math.atan(yy / yx), 0.0);
            }

            public void rotate(@NotNull Surface from) {
                @NotNull Polygon1 polygonFrom = from.polygon;
                @NotNull Polygon1 polygonTo = this.polygon;
                @NotNull matrix.PixM actualDrawingFrom = from.actualDrawing;
                Point3D isocentreFrom = polygonFrom.getIsocentre();
                Point3D isocentreTo = polygonTo.getIsocentre();
                Point3D anglesFrom = from.getPolygonAngles();
                Point3D anglesTo = this.getPolygonAngles();


                Matrix33 rotation = rotatePoint(isocentreFrom, isocentreTo, anglesFrom, anglesTo);

                // 1 Rotated polygon -> destination size


                StructureMatrix<Point3D> boundRect2d = polygon.getBoundRect2d();
                // 2 Computed rotated matrix.PixM
                for (double i = boundRect2d.getElem(0).getX(); i < boundRect2d.getElem(1).getX(); i++) {
                    for (double j = boundRect2d.getElem(0).getY(); j < boundRect2d.getElem(1).getY(); j++) {
                        // Se replacer (i,j,0) dans le matrix.PixM d'origine, from
                        Point3D p = new Point3D(i, j, 0.0);
                        Point3D translated = p.plus(isocentreFrom).moins(isocentreTo);
                        Point3D dst = rotation.mult(translated);
                        for (int c = 0; c < 3; c++) {
                            actualDrawing.setCompNo(c);
                            from.actualDrawing.setCompNo(c);
                            actualDrawing.set((int) (double) i, (int) (double) j,
                                    from.actualDrawing.get((int) (double) dst.getX(), (int) (double) dst.getY()));
                        }
                    }
                }
            }

            private Matrix33 rotatePoint(Point3D isocentreFrom, Point3D isocentreTo, Point3D anglesFrom, Point3D anglesTo) {
                Matrix33 rotated = new Matrix33(new double[]{
                        Math.cos(anglesFrom.getX() - anglesTo.getX()), Math.sin(anglesFrom.getX() - anglesTo.getX()), 0.0,
                        Math.sin(anglesFrom.getX() - anglesTo.getX()), Math.cos(anglesFrom.getX() - anglesTo.getX()), 0.0,
                        0.0, 0.0, 1.0});
                return rotated;
            }
        }

        private List<Surface> faceSurfaces;

        public List<Surface> getFaceSurfaces() {
            return faceSurfaces;
        }

        public void setFaceSurfaces(List<Surface> faceSurfaces) {
            this.faceSurfaces = faceSurfaces;
        }

        public FaceData(List<Surface> faceSurfaces) {
            this.faceSurfaces = faceSurfaces;
            if (faceSurfaces == null)
                this.faceSurfaces = new ArrayList<>();
        }

        public FaceData() {
            this.faceSurfaces = new ArrayList<>();
        }
    }

    public GoogleFaceDetection(List<FaceData> dataFaces, Bitmap bitmap) {
        this(new Image(bitmap));
        this.dataFaces = dataFaces;
    }

    public GoogleFaceDetection(@NotNull Image bitmap) {
        dataFaces = new ArrayList<>();
        this.setBitmap(bitmap.getImage());
    }

    public void setBitmap(@NotNull Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public List<FaceData> getDataFaces() {
        return dataFaces;
    }

    public void setDataFaces(List<FaceData> dataFaces) {
        this.dataFaces = dataFaces;
    }

    public FaceData.Surface getSurface(Point pInPicture) {
        final FaceData.Surface[] surface = {null};
        for (FaceData dataFace : dataFaces) {
            dataFace.getFaceSurfaces().forEach(new Consumer<FaceData.Surface>() {
                @Override
                public void accept(FaceData.Surface surface1) {
                    if (surface1.isContaning(pInPicture))
                        surface[0] = surface1;
                }
            });
        }
        if (surface[0] != null)
            return surface[0];
        else
            return null;
    }

    public FaceData.Surface getSelectedSurface() {
        return selectedSurface;
    }

    public void setSelectedSurface(FaceData.Surface selectedSurface) {
        this.selectedSurface = selectedSurface;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public Serialisable decode(DataInputStream in) {
        try {
            versionFile = in.readInt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            matrix.PixM decode1 = (matrix.PixM) new matrix.PixM(1, 1).decode(in);
            bitmap = decode1.getBitmap();
        } catch (NullPointerException e1) {
            bitmap = new matrix.PixM(1, 1).getBitmap();
        }
        try {
            GoogleFaceDetection faceDetection = new GoogleFaceDetection(new Image(bitmap));
            int countFaces = in.readInt();
            System.out.println("Number of faces to read = " + countFaces);
            for (int c = 0; c < countFaces; c++) {
                FaceData faceData = (FaceData) new FaceData().decode(in);
                faceDetection.getDataFaces().add(faceData);
                int countSurfaces = in.readInt();
                for (int j = 0; j < countSurfaces; j++) {
                    FaceData.Surface decode = (FaceData.Surface) new FaceData.Surface().decode(in);
                    faceDetection.getDataFaces().get(c).getFaceSurfaces().add(decode);
                }
            }
            return faceDetection;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public int encode(DataOutputStream out) {
        try {
            out.writeInt(versionFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            if (getBitmap() != null) {
                new matrix.PixM(getBitmap()).encode(out);
            } else {
                PixM pixM = new PixM(1, 1);
                pixM.encode(out);
                setBitmap(pixM.getBitmap());
            }
            System.out.println("Number of face to save : " + dataFaces.size());
            out.writeInt(dataFaces.size());
            dataFaces.forEach(faceData -> {
                try {
                    faceData.encode(out);
                    out.writeInt(faceData.getFaceSurfaces().size());
                    if (!faceData.getFaceSurfaces().isEmpty()) {
                        System.out.println("Number of recorded surfaces : " + faceData.faceSurfaces.size());
                        faceData.faceSurfaces.forEach(surface -> {
                            surface.encode(out);
                        });
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (RuntimeException | IOException ex) {
            ex.printStackTrace();
            return -1;
        }

        return 0;
    }

    @Override
    public int type() {
        return 0;
    }

    public matrix.PixM getFunctionResult(String functionAbij, int idSurface) {
        if (functionAbij == null || functionAbij.isEmpty()) {
            functionAbij = "";
        }
        matrix.PixM a;
        List<FaceData.Surface> surfaces = new ArrayList<>();
        getDataFaces().forEach(new Consumer<FaceData>() {
            @Override
            public void accept(FaceData faceData) {
                faceData.getFaceSurfaces().forEach(new Consumer<FaceData.Surface>() {
                    @Override
                    public void accept(FaceData.Surface surface) {
                        if (surface.getSurfaceId() == idSurface) {
                            surfaces.add(surface);
                        }
                    }
                });
            }
        });
        FaceData.Surface surface = surfaces.get(0);
        if (surface != null) {
            AlgebraicTree algebraicTree = new AlgebraicTree(functionAbij);
            try {
                algebraicTree.construct();
                for (int i = 0; i < surface.getFilledContours().getColumns(); i++) {
                    for (int j = 0; i < surface.getFilledContours().getLines(); j++) {
                        algebraicTree.setParameter("i", (double) i);
                        algebraicTree.setParameter("j", (double) j);
                        StructureMatrix<Double> eval = algebraicTree.eval();
                        surface.actualDrawing.set(i, j, eval.getElem(0), eval.getElem(1), eval.getElem(2));
                    }
                }
            } catch (AlgebraicFormulaSyntaxException e) {
                throw new RuntimeException(e);
            } catch (TreeNodeEvalException e) {
                throw new RuntimeException(e);
            }

        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoogleFaceDetection that = (GoogleFaceDetection) o;
        if (getDataFaces().size() == ((GoogleFaceDetection) o).getDataFaces().size()) {
            int size = getDataFaces().size();
            for (int i = 0; i < size; i++) {
                if (!getDataFaces().get(i).equals(getDataFaces().get(i)))
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDataFaces());
    }
}
