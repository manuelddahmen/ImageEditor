package one.empty3.feature.app.maxSdk29.pro;

import androidx.camera.camera2.interop.ExperimentalCamera2Interop;

import one.empty3.feature20220726.GoogleFaceDetection;
import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;

public class GoogleMLKitVisionFacePolygonClipboard {
        private GoogleMLKitVisionFacePolygonClipboard instance = new GoogleMLKitVisionFacePolygonClipboard();
        private GoogleFaceDetection.FaceData.Surface source;
        private GoogleFaceDetection.FaceData.Surface destination;
        private StructureMatrix<Point3D> mapSource = new StructureMatrix<Point3D>(2, Point3D.class);
        private StructureMatrix<Point3D> mapDestination = new StructureMatrix<Point3D>(2, Point3D.class);
        public GoogleMLKitVisionFacePolygonClipboard(GoogleFaceDetection.FaceData.Surface source) {
                this.source = source;
        }

        private GoogleMLKitVisionFacePolygonClipboard() {
                if(instance==null)
                        instance = new GoogleMLKitVisionFacePolygonClipboard();
        }

        public GoogleMLKitVisionFacePolygonClipboard getInstance() {
                if(instance==null) new GoogleMLKitVisionFacePolygonClipboard();
                return instance;
        }
        public void simplePaste() {
                if(instance.source!=null && instance.destination!=null && instance.source.getSurfaceId()==instance.destination.getSurfaceId()) {
                        instance.destination.setFilledContours(instance.source.getFilledContours().resize(instance.destination.getFilledContours().getColumns(),
                                instance.destination.getFilledContours().getLines()));
                }
        }


}
