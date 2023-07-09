package one.empty3.feature.app.maxSdk29.pro;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.camera2.interop.ExperimentalCamera2Interop;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceContour;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.google.mlkit.vision.face.FaceLandmark;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Objects;

import one.empty3.feature20220726.GoogleFaceDetection;
import one.empty3.feature20220726.PixM;
import one.empty3.library.ColorTexture;
import one.empty3.library.Point3D;
import one.empty3.library.Polygon;
import one.empty3.library.StructureMatrix;

@ExperimentalCamera2Interop public class FaceOverlayView extends ImageViewSelection {
    private GoogleFaceDetection googleFaceDetection;
    private List<Face> mFaces;
    protected Bitmap mBitmap;
    private Canvas mCanvas;
    private Bitmap mCopy;
    private boolean isFinish = false;
    private ActivitySuperClass activity;
    private boolean isDrawing = false;

    public FaceOverlayView(@NonNull Context context) {
        super(context);
    }

    public FaceOverlayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FaceOverlayView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GoogleFaceDetection getGoogleFaceDetection() {
        return googleFaceDetection;
    }

    public void setGoogleFaceDetection(GoogleFaceDetection googleFaceDetection) {
        this.googleFaceDetection = googleFaceDetection;
    }

    public void setBitmap(Bitmap bitmap) {

        this.mBitmap = bitmap;
        mCopy = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        try {

            FaceDetectorOptions highAccuracyOpts =
                    new FaceDetectorOptions.Builder()
                            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_NONE)
                            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                            .build();

// Real-time contour detection
//            FaceDetectorOptions realTimeOpts =
//                    new FaceDetectorOptions.Builder()
//                            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
//                            .build();

            InputImage inputImage = InputImage.fromBitmap(bitmap, 0);

            mCanvas = new Canvas(mCopy);

            FaceDetector client = FaceDetection.getClient(highAccuracyOpts);
            client.process(inputImage).addOnSuccessListener(faces -> {
                mFaces = faces;


                //mFaces.forEach(this::action);
            });
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }


        setImageBitmap2(mCopy);
    }

    private void action(Face face, GoogleFaceDetection.FaceData faceData) {
        Rect bounds = face.getBoundingBox();
        float rotY = face.getHeadEulerAngleY();  // Head is rotated to the right rotY degrees
        float rotZ = face.getHeadEulerAngleZ();  // Head is tilted sideways rotZ degrees

        // If landmark detection was enabled (mouth, ears, eyes, cheeks, and
        // nose available):
        FaceLandmark leftEar = face.getLandmark(FaceLandmark.LEFT_EAR);

        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);


        PointF leftEarPos = null;
        if (leftEar != null) {
            leftEarPos = leftEar.getPosition();
        }
        // If landmark detection was enabled (mouth, ears, eyes, cheeks, and
        // nose available):
        PointF rightEarPos = null;
        FaceLandmark rightEar = face.getLandmark(FaceLandmark.RIGHT_EAR);
        if (rightEar != null) {
            rightEarPos = rightEar.getPosition();
        }

        // If contour detection was enabled:
        List<PointF> leftEyeContour = null;
        try {
            leftEyeContour =
                    face.getContour(FaceContour.LEFT_EYE).getPoints();
        } catch (NullPointerException ex) {
            //ex.printStackTrace();
        }
        List<PointF> rightEyeContour = null;
        try {
            // If contour detection was enabled:
            rightEyeContour =
                    face.getContour(FaceContour.RIGHT_EYE).getPoints();
        } catch (NullPointerException ex) {
            //ex.printStackTrace();
        }

        // If classification was enabled:
        float smileProb = 0f;
        if (face.getSmilingProbability() != null) {
            smileProb = face.getSmilingProbability();
        }
        float rightEyeOpenProb = 0f;
        if (face.getRightEyeOpenProbability() != null) {
            rightEyeOpenProb = face.getRightEyeOpenProbability();
        }

        // If face tracking was enabled:
        int id = -1;
        if (face.getTrackingId() != null) {
            id = face.getTrackingId();
        }

        paint.setColor(Color.RED);

        paint.setColor(Color.BLUE);
        if (leftEyeContour != null && leftEyeContour.size() >= 2) {
            for (int i = 0; i < leftEyeContour.size(); i++) {
//                drawLine(coordCanvas(leftEyeContour.get(i)), coordCanvas(leftEyeContour.get((i + 1) % leftEyeContour.size())));
            }
        }
        if (rightEyeContour != null && rightEyeContour.size() >= 2) {
            for (int i = 0; i < rightEyeContour.size(); i++) {
//                drawLine(coordCanvas(rightEyeContour.get(i)), coordCanvas(rightEyeContour.get((i + 1) % rightEyeContour.size())));
            }
        }
        int surfaceId = FaceContour.FACE;
        faceData.getFaceSurfaces().add(
                new GoogleFaceDetection.FaceData.Surface(
                        surfaceId, face.getContour(FaceContour.FACE).getPoints(), null,
                        Color.RED, Color.BLUE, Color.BLACK));
        surfaceId = FaceContour.LEFT_EYE;
        faceData.getFaceSurfaces().add(
                new GoogleFaceDetection.FaceData.Surface(
                        surfaceId, face.getContour(FaceContour.LEFT_EYE).getPoints(), null,
                        Color.BLUE, Color.RED, Color.BLACK));
        surfaceId =FaceContour.RIGHT_EYE;
        faceData.getFaceSurfaces().add(
                new GoogleFaceDetection.FaceData.Surface(
                        surfaceId, face.getContour(FaceContour.RIGHT_EYE).getPoints(), null,
                        Color.BLUE, Color.RED, Color.BLACK));
        surfaceId = FaceContour.NOSE_BOTTOM;
        faceData.getFaceSurfaces().add(
                new GoogleFaceDetection.FaceData.Surface(
                        surfaceId, face.getContour(FaceContour.NOSE_BOTTOM).getPoints(), null,
                        Color.BLUE, Color.RED, Color.BLACK));
        surfaceId = FaceContour.NOSE_BRIDGE;
        faceData.getFaceSurfaces().add(
                new GoogleFaceDetection.FaceData.Surface(
                        surfaceId, face.getContour(FaceContour.NOSE_BRIDGE).getPoints(), null,
                        Color.BLUE, Color.RED, Color.BLACK));
        surfaceId = FaceContour.LEFT_EYEBROW_BOTTOM;
        faceData.getFaceSurfaces().add(
                new GoogleFaceDetection.FaceData.Surface(
                        surfaceId, face.getContour(FaceContour.LEFT_EYEBROW_BOTTOM).getPoints(), null,
                        Color.BLUE, Color.RED, Color.BLACK));
        surfaceId = FaceContour.RIGHT_EYEBROW_BOTTOM;
        faceData.getFaceSurfaces().add(
                new GoogleFaceDetection.FaceData.Surface(
                        surfaceId, face.getContour(FaceContour.RIGHT_EYEBROW_BOTTOM).getPoints(), null,
                        Color.BLUE, Color.RED, Color.BLACK));
        surfaceId = FaceContour.LEFT_EYEBROW_TOP;
        faceData.getFaceSurfaces().add(
                new GoogleFaceDetection.FaceData.Surface(
                        surfaceId, face.getContour(FaceContour.LEFT_EYEBROW_TOP).getPoints(), null,
                        Color.BLUE, Color.RED, Color.BLACK));
        surfaceId = FaceContour.RIGHT_EYEBROW_TOP;
        faceData.getFaceSurfaces().add(
                new GoogleFaceDetection.FaceData.Surface(
                        surfaceId, face.getContour(FaceContour.RIGHT_EYEBROW_TOP).getPoints(), null,
                        Color.BLUE, Color.RED, Color.BLACK));
        surfaceId = FaceContour.UPPER_LIP_TOP;
        faceData.getFaceSurfaces().add(
                new GoogleFaceDetection.FaceData.Surface(
                        surfaceId, face.getContour(FaceContour.UPPER_LIP_TOP).getPoints(), null,
                        Color.BLUE, Color.RED, Color.BLACK));
        surfaceId = FaceContour.UPPER_LIP_BOTTOM;
        faceData.getFaceSurfaces().add(
                new GoogleFaceDetection.FaceData.Surface(
                        surfaceId, face.getContour(FaceContour.UPPER_LIP_BOTTOM).getPoints(), null,
                        Color.BLUE, Color.RED, Color.BLACK));
        surfaceId = FaceContour.LOWER_LIP_BOTTOM;
        faceData.getFaceSurfaces().add(
                new GoogleFaceDetection.FaceData.Surface(
                        surfaceId, face.getContour(FaceContour.LOWER_LIP_BOTTOM).getPoints(), null,
                        Color.BLUE, Color.RED, Color.BLACK));
        surfaceId = FaceContour.LOWER_LIP_TOP;
        faceData.getFaceSurfaces().add(
                new GoogleFaceDetection.FaceData.Surface(
                        surfaceId, face.getContour(FaceContour.LOWER_LIP_TOP).getPoints(), null,
                        Color.BLUE, Color.RED, Color.BLACK));
        surfaceId = FaceContour.UPPER_LIP_TOP;
        faceData.getFaceSurfaces().add(
                new GoogleFaceDetection.FaceData.Surface(
                        surfaceId, face.getContour(FaceContour.UPPER_LIP_TOP).getPoints(), null,
                        Color.BLUE, Color.RED, Color.BLACK));
        surfaceId = FaceContour.LEFT_CHEEK;
        faceData.getFaceSurfaces().add(
                new GoogleFaceDetection.FaceData.Surface(
                        surfaceId, face.getContour(FaceContour.LEFT_CHEEK).getPoints(), null,
                        Color.BLUE, Color.RED, Color.BLACK));
        surfaceId = FaceContour.RIGHT_CHEEK;
        faceData.getFaceSurfaces().add(
                new GoogleFaceDetection.FaceData.Surface(
                        surfaceId, face.getContour(FaceContour.RIGHT_CHEEK).getPoints(), null,
                        Color.BLUE, Color.RED, Color.BLACK));

        for (GoogleFaceDetection.FaceData.Surface faceSurface : faceData.getFaceSurfaces()) {
            fillPolygon(faceSurface, faceSurface.getPolygon(), faceSurface.getColorContours(), faceSurface.getColorFill());
        }
    }

    public void testSphere() {
/*
        Sphere sphere = new Sphere(new Axe(Point3D.Z, Point3D.Z.mult(-1)), 10.0);
        sphere.texture(new ColorTexture(Color.GREEN));

        ZBufferImpl zBuffer = new ZBufferImpl(mCopy.getWidth(), mCopy.getHeight());

        Scene scene = new Scene();

        Point3D center = Point3D.X.mult(mCopy.getWidth()/2.).plus(Point3D.Y.mult(mCopy.getHeight()/2.));

        double maxRes = Math.max(mCopy.getWidth(), mCopy.getHeight());

        one.empty3.library.Camera camera = new Camera(center.plus(Point3D.Z.mult(maxRes)), center, Point3D.Y);

        scene.cameraActive(camera);
        zBuffer.scene(scene);
        zBuffer.camera(camera);

        zBuffer.draw(scene);

        Bitmap bitmap = zBuffer.image2();

        mCanvas.drawBitmap(bitmap, 0f, 0f, paint);

 */
        paint.setColor(Color.GREEN);
        PointF pointF = coordCanvas(new PointF(mCopy.getWidth() / 2f, mCopy.getHeight() / 2f));
        //mCanvas.drawCircle(pointF.x, pointF.y, 20f, paint);
    }

    private void fillPolygon(GoogleFaceDetection.FaceData.Surface faceSurface, List<PointF> polygonContour, int contourColor, int inColor) {
        if (polygonContour != null) {
            paint.setColor(contourColor);
            for (int i = 0; i < polygonContour.size(); i++) {
                drawLine(coordCanvas(polygonContour.get(i)), coordCanvas(polygonContour.get((i + 1) % polygonContour.size())));
            }
            int size = polygonContour.size();
            Point3D[] point3DS = new Point3D[size];
            for (int i = 0; i < polygonContour.size(); i += 1) {
                PointF pointF = polygonContour.get(i);
                point3DS[i] = new Point3D(pointF.x * 1.0, pointF.y * 1.0, 0d);
            }
            Polygon polygon = new Polygon(point3DS, new ColorTexture(contourColor));

            StructureMatrix<Point3D> boundRect2d = polygon.getBoundRect2d();

            //System.out.println("Draw on canvas");

            PointF point0 = coordCanvas(new PointF(0f, 0f));

            PointF scale = getScale();

            {
                PixM pixM = polygon.fillPolygon2D(faceSurface, mCanvas, mCopy, Color.BLACK, 0.0, point0, scale.x);//ùCopy!
                if(pixM!=null && pixM.getLines()>0 && pixM.getColumns()>0) {
                    /*Bitmap bitmap = pixM.getBitmap();
                    if (bitmap != null) {
                        PointF p1 = coordCanvas(new PointF((float)(double)boundRect2d.getElem(0).get(0),
                                (float)(double)boundRect2d.getElem(0).get(1)));
                        PointF p2 = coordCanvas(new PointF((float)(double)boundRect2d.getElem(1).get(0),
                                (float)(double)boundRect2d.getElem(1).get(1)));

                        float x1 = p1.x;
                        float y1 = p1.y;
                        float x2 = p2.x;
                        float y2 = p2.y;

                        float w = x2 - x1;
                        float h = y2 - y1;


                        if(x1>=0 && x1<mCanvas.getWidth()&&y1>=0&&y1<mCanvas.getHeight())
                            ;//mCanvas.drawBitmap(bitmap.copy(Bitmap.Config.ARGB_8888, false), (int) x1, (int) y1, paint);
                    }*/
                }
            }
            //polygon.drawOnCanvas(mCanvas, mCopy, Color.BLACK, point0, scale);
        }
   }


    private void drawLine(PointF pointF, PointF pointF1) {
        mCanvas.drawLine(pointF.x, pointF.y, pointF1.x, pointF1.y, paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!isDrawing()) {
            if (!isFinish) {
                isDrawing = true;
                this.mCanvas = canvas;
                if (mCopy != null)
                    updateImage(mCopy);


                isDrawing = false;
            }
        }
    }

    private boolean isDrawing() {
        return isDrawing;
    }

    private boolean isFinish() {
        return isFinish;
    }

    private double drawBitmap() {
        if (mCanvas == null)
            return 1.0;
        if (mBitmap == null)
            return 1.0;

        double viewWidth = mCanvas.getWidth();
        double viewHeight = mCanvas.getHeight();
        double imageWidth = mBitmap.getWidth();
        double imageHeight = mBitmap.getHeight();
        double scale = Math.min(viewWidth / imageWidth, viewHeight / imageHeight);
        //Rect destBounds = new Rect(0, 0, (int) (imageWidth * scale), (int) (imageHeight * scale));
        return scale;
    }

    public PointF coordCanvas(PointF p) {
        if (mCanvas == null)
            return p;
        if (mBitmap == null)
            return p;

        double viewWidth = mCanvas.getWidth();
        double viewHeight = mCanvas.getHeight();
        double imageWidth = mBitmap.getWidth();
        double imageHeight = mBitmap.getHeight();
        double scale = Math.min(viewWidth / imageWidth, viewHeight / imageHeight);
        return new PointF((int) ((int) (-(imageWidth / 2) * scale) + mCanvas.getWidth() / 2 + p.x * scale),
                (int) ((int) (-(imageHeight / 2) * scale) + mCanvas.getHeight() / 2 + p.y * scale));
    }
    public static PointF coordCanvas(Canvas canvas, Bitmap bitmap, PointF p) {
        if (canvas == null)
            return p;
        if (bitmap == null)
            return p;

        double viewWidth = canvas.getWidth();
        double viewHeight = canvas.getHeight();
        double imageWidth = bitmap.getWidth();
        double imageHeight = bitmap.getHeight();
        double scale = Math.min(viewWidth / imageWidth, viewHeight / imageHeight);
        return new PointF((int) ((int) (-(imageWidth / 2) * scale) + canvas.getWidth() / 2 + p.x * scale),
                (int) ((int) (-(imageHeight / 2) * scale) + canvas.getHeight() / 2 + p.y * scale));
    }
    public PointF getScale() {
        if (mCanvas == null)
            return new PointF(0,0);
        if (mBitmap == null)
            return new PointF(0,0);

        double viewWidth = mCanvas.getWidth();
        double viewHeight = mCanvas.getHeight();
        double imageWidth = mBitmap.getWidth();
        double imageHeight = mBitmap.getHeight();
        double scaleMin = Math.min((float) (viewWidth / imageWidth), (float) (viewHeight / imageHeight));
        PointF scale = new PointF((float) scaleMin, (float) scaleMin);
        return scale;
    }

    public void updateImage(Bitmap bm) {

        new Handler(Looper.getMainLooper()).post(() -> {
            //Log.d("ImageViewSelection::setImageBitmap",
            //        "change image on UI thread");
            if (mFaces != null && mCanvas != null && bm != null) {
                googleFaceDetection = new GoogleFaceDetection();

                double scale = drawBitmap();

                double imageWidth = mBitmap.getWidth();
                double imageHeight = mBitmap.getHeight();
                PointF p1 = coordCanvas(new PointF(0, 0));
                PointF p2 = coordCanvas(new PointF((float) imageWidth, (float) imageHeight));
                Rect destBounds = new Rect((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
                //!!!mCanvas.drawBitmap(mBitmap, new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight()), destBounds, paint);
                //googleFaceDetection
                Objects.requireNonNull(mFaces).forEach(face -> drawFaceBoxes(mCanvas, scale));

                if (activity != null) {
                    File file = new Utils().writePhoto(activity, mCopy.copy(Bitmap.Config.ARGB_8888,
                            false), "face-");
                    this.activity.currentFile = file;
                }
            }
        });
        super.setImageBitmap3(mCopy.copy(Bitmap.Config.ARGB_8888, true));
    }
    public double getScaleImageX() {
        Rect destBounds = getDestBounds();
        return (destBounds.right-destBounds.left)*1.0/mBitmap.getWidth();
    }

    public double getScaleImageY() {
        Rect destBounds = getDestBounds();
        return (destBounds.bottom-destBounds.top)*1.0/mBitmap.getHeight();
    }

    public Rect getDestBounds() {
        double imageWidth = mBitmap.getWidth();
        double imageHeight = mBitmap.getHeight();
        PointF p1 = coordCanvas(new PointF(0, 0));
        PointF p2 = coordCanvas(new PointF((float) imageWidth, (float) imageHeight));
        return new Rect((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
    }
    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        isFinish = true;
        return super.onSaveInstanceState();
    }

    private void drawFaceBoxes(Canvas canvas, double scale) {
        if (canvas == null)
            return;
        if (mFaces == null)
            return;

        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        float left = 0;
        float top = 0;
        float right = 0;
        float bottom = 0;
        testSphere();
        for (int i = 0; i < mFaces.size(); i++) {
            Face face = mFaces.get(i);

            googleFaceDetection.getDataFaces().add(new GoogleFaceDetection.FaceData());

            GoogleFaceDetection.FaceData faceData = googleFaceDetection.getDataFaces().get(i);

            Rect rect = face.getBoundingBox();
            PointF a = coordCanvas(new PointF((int) (rect.left), (int) (rect.top)));
            PointF b = coordCanvas(new PointF((int) (rect.right), (int) (rect.bottom)));
            mCanvas.drawRect(new RectF(a.x, a.y, b.x, b.y), paint);
            action(face, faceData);
        }
    }

    public void setActivity(@NotNull ActivitySuperClass faceActivitySettings) {
        this.activity = faceActivitySettings;
    }

    public void setFinish(boolean b) {
        isFinish = b;
    }

    public void setDrawing(boolean b) {
        this.isDrawing = b;
    }
}