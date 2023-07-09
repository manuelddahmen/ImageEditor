package one.empty3.feature20220726;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

public class GoogleFaceDetection {
    private List<FaceData> dataFaces;

    public static class FaceData {
        public static class Surface {
            private int colorFill;
            private int colorContours;
            private int colorTransparent;
            private int surfaceId;
            private List<PointF> polygon;
            private PixM contours;

            public Surface(int surfaceId, List<PointF> polygon, PixM contours, int colorFill, int colorContours, int colorTransparent) {
                this.surfaceId = surfaceId;
                this.polygon = polygon;
                this.contours = contours;
                this.colorFill = colorFill;
                this.colorContours = colorContours;
                this.colorTransparent = colorTransparent;
            }

            public int getSurfaceId() {
                return surfaceId;
            }

            public void setSurfaceId(int surfaceId) {
                this.surfaceId = surfaceId;
            }

            public List<PointF> getPolygon() {
                return polygon;
            }

            public void setPolygon(List<PointF> polygon) {
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
            if(faceSurfaces==null)
                this.faceSurfaces = new ArrayList<>();
        }
        public FaceData() {
            this.faceSurfaces = new ArrayList<>();
        }
    }
    public GoogleFaceDetection(List<FaceData> dataFaces) {
        this.dataFaces = dataFaces;
    }
    public GoogleFaceDetection() {
        dataFaces = new ArrayList<>();
    }

    public List<FaceData> getDataFaces() {
        return dataFaces;
    }

    public void setDataFaces(List<FaceData> dataFaces) {
        this.dataFaces = dataFaces;
    }
}
