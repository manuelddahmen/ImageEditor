package one.empty3.feature20220726;

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

import one.empty3.library.ColorTexture;
import one.empty3.library.Lumiere;
import one.empty3.library.Point3D;
import one.empty3.library.Polygon;
import one.empty3.library.Serialisable;
import one.empty3.library.StructureMatrix;

public class GoogleFaceDetection
        implements Parcelable, Serializable, Serialisable {
    static GoogleFaceDetection instance;
    private static GoogleFaceDetection instance2;
    private FaceData.Surface selectedSurface;
    public static double[] TRANSPARENT = Lumiere.getDoubles(Color.BLACK);
    private List<FaceData> dataFaces;
    @NotNull private Bitmap bitmap;
    private int versionFile = 4;

    public static GoogleFaceDetection getInstance(boolean newInstance, Bitmap bitmap) {
        if (instance == null || newInstance)
            instance = new GoogleFaceDetection(bitmap);
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
                        Polygon polygon = Polygon.CREATOR.createFromParcel(parcel);
                        PixM contours = PixM.CREATOR.createFromParcel(parcel);
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
        return instance2!=null;
    }

    public static class FaceData implements Serializable, Serialisable {
        private PixM photo = new PixM(1,1);
        @Override
        public Serialisable decode(DataInputStream in) {
            FaceData faceData = new FaceData();
            photo = (PixM) new PixM(1,1).decode(in);
            if(photo==null)
                photo = new PixM(1,1);
            return faceData;
        }

        public int encode(DataOutputStream out) {
            if(photo==null)
                photo = new PixM(1,1);
            photo.encode(out);
            return 0;
        }

        @Override
        public int type() {
            return 0;
        }

        public PixM getPhoto() {
            return photo;
        }

        public void setPhoto(PixM photo) {
            this.photo = photo;
        }

        public static class Surface implements Serializable, Serialisable {
            private boolean drawOriginalImageContour;
            private int colorFill;
            private int colorContours;
            private int colorTransparent;
            private int surfaceId;
            private Polygon polygon;
            private PixM contours;
            private PixM filledContours;
            @Nullable
            public PixM actualDrawing;

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
                    surface.polygon = (Polygon) new Polygon().decode(in);
                    surface.contours = (PixM) new PixM(1, 1).decode(in);
                    surface.filledContours = (PixM) new PixM(1, 1).decode(in);
                    surface.drawOriginalImageContour = in.readBoolean();
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
                        polygon = new Polygon();
                    polygon.encode(out);
                    if (contours == null)
                        contours = new PixM(1, 1);
                    contours.encode(out);
                    if (filledContours == null) {
                        filledContours = new PixM(1, 1);
                    }
                    filledContours.encode(out);
                    out.writeBoolean(drawOriginalImageContour);

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


            public Surface(int surfaceId, Polygon polygon, PixM contours,
                           int colorFill, int colorContours, int colorTransparent,
                           PixM filledContours, boolean drawOriginalImageContour) {
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

            public Polygon getPolygon() {
                return polygon;
            }

            public void setPolygon(Polygon polygon) {
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
                filledContours = polygon.fillPolygon2D(this, null, contours.getBitmap(),
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

            public void setFilledContours(PixM filledContours) {
                this.filledContours = filledContours;
            }

            public PixM getFilledContours() {
                return filledContours;
            }

            @Nullable
            public PixM getActualDrawing() {
                return actualDrawing;
            }

            public void setActualDrawing(@Nullable PixM actualDrawing) {
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
                        && Objects.equals(getPolygon(), surface.getPolygon())
                        && Objects.equals(getContours(), surface.getContours())
                        && Objects.equals(getFilledContours(), surface.getFilledContours());
            }

            @Override
            public int hashCode() {
                return Objects.hash(getColorFill(), getColorContours(), getColorTransparent(), getSurfaceId(), getPolygon(), getContours(), getFilledContours());
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
        this(bitmap);
        this.dataFaces = dataFaces;
    }

    public GoogleFaceDetection(@NotNull Bitmap bitmap) {
        dataFaces = new ArrayList<>();
        this.setBitmap(bitmap);
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

    public FaceData.Surface getSurface(android.graphics.Point pInPicture) {
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
            PixM decode1 = (PixM) new PixM(1, 1).decode(in);
            bitmap = decode1.getBitmap();
        } catch (NullPointerException e1) {
            bitmap = new PixM(1, 1).getBitmap();
        }
        try {
            GoogleFaceDetection faceDetection = new GoogleFaceDetection(bitmap);
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
            out.write(versionFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            if (getBitmap() != null) {
                new PixM(getBitmap()).encode(out);
            } else {
                new PixM(1, 1).encode(out);
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
