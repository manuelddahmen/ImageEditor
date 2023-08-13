package one.empty3.feature.app.maxSdk29.pro;

import androidx.camera.camera2.interop.ExperimentalCamera2Interop;

import one.empty3.feature20220726.GoogleFaceDetection;
import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;

@ExperimentalCamera2Interop public class GoogleMLKitVisionFacePolygonClipboard {
        private GoogleFaceDetection.FaceData.Surface source;
        private GoogleFaceDetection.FaceData.Surface destination;
        private StructureMatrix<Point3D> mapSource = new StructureMatrix<Point3D>(2, Point3D.class);
        private StructureMatrix<Point3D> mapDestination = new StructureMatrix<Point3D>(2, Point3D.class);
        public GoogleMLKitVisionFacePolygonClipboard(GoogleFaceDetection.FaceData.Surface source) {
                this.source = source;
        }

        public void simplePaste() {
                destination.setFilledContours(source.getFilledContours().copy());
        }


}
