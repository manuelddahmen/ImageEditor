package one.empty3.feature.app.maxSdk29.pro;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceContour;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.google.mlkit.vision.face.FaceLandmark;
import com.google.mlkit.vision.face.internal.FaceDetectorImpl;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import one.empty3.feature20220726.PixM;
import one.empty3.library.ColorTexture;
import one.empty3.library.Point3D;
import one.empty3.library.Polygon;
import one.empty3.library.Representable;

public class FaceOverlayView extends ImageViewSelection {
    private List<Face> mFaces;
    protected Bitmap mBitmap;
    private Canvas mCanvas;
    private Bitmap mCopy;
    private boolean isFinish = false;

    public FaceOverlayView(@NonNull Context context) {
        super(context);
    }

    public FaceOverlayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FaceOverlayView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setBitmap(Bitmap bitmap) {

        this.mBitmap = bitmap;
        mCopy = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        try {

            FaceDetectorOptions highAccuracyOpts =
                    new FaceDetectorOptions.Builder()
                            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
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


                mFaces.forEach(face -> action(face));
            });
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        setImageBitmap2(mCopy);

        //invalidate();
    }

    private void action(Face face) {
        Rect bounds = face.getBoundingBox();
        float rotY = face.getHeadEulerAngleY();  // Head is rotated to the right rotY degrees
        float rotZ = face.getHeadEulerAngleZ();  // Head is tilted sideways rotZ degrees

        // If landmark detection was enabled (mouth, ears, eyes, cheeks, and
        // nose available):
        FaceLandmark leftEar = face.getLandmark(FaceLandmark.LEFT_EAR);
        if (leftEar != null) {
            PointF leftEarPos = leftEar.getPosition();
        }
        // If landmark detection was enabled (mouth, ears, eyes, cheeks, and
        // nose available):
        FaceLandmark rightEar = face.getLandmark(FaceLandmark.RIGHT_EAR);
        if (rightEar != null) {
            PointF rightEarPos = rightEar.getPosition();
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
        List<PointF> upperLipTopContour = null;
        try {
            upperLipTopContour =
                    face.getContour(FaceContour.UPPER_LIP_TOP).getPoints();
        } catch (NullPointerException ex) {
            //ex.printStackTrace();
        }
        List<PointF> upperLipBottomContour = null;
        try {
            upperLipBottomContour =
                    face.getContour(FaceContour.UPPER_LIP_BOTTOM).getPoints();
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

        Paint paint = new Paint();
        paint.setColor(Color.RED);

        Paint paint2 = new Paint();
        paint2.setColor(Color.BLUE);
        if (leftEyeContour != null && leftEyeContour.size() >= 2) {
            for (int i = 0; i < leftEyeContour.size(); i++) {
                drawLine(coordCanvas(leftEyeContour.get(i)), coordCanvas(leftEyeContour.get((i + 1) % leftEyeContour.size())), paint);

            }
            fillPolygon(leftEyeContour, paint2);
        }
        if (rightEyeContour != null && rightEyeContour.size() >= 2) {
            for (int i = 0; i < rightEyeContour.size(); i++) {
                drawLine(coordCanvas(rightEyeContour.get(i)), coordCanvas(rightEyeContour.get((i + 1) % rightEyeContour.size())), paint);
            }
            fillPolygon(rightEyeContour, paint2);
        }
    }

    private void fillPolygon(List<PointF> rightEyeContour, Paint paint) {
        int size = rightEyeContour.size();
        Point3D[] point3DS = new Point3D[size];
        for (int i = 0; i < rightEyeContour.size(); i += 1) {
            point3DS[i] = new Point3D(rightEyeContour.get(i).x * 1.0, rightEyeContour.get(i).y * 1.0, 0d);
        }
        Polygon polygon = new Polygon(point3DS, new ColorTexture(Color.RED));
        polygon.drawOnCanvas(mCanvas, mCopy, Representable.FILL, 0);
    }


    private void drawLine(PointF pointF, PointF pointF1, Paint paint) {
        mCanvas.drawLine(pointF.x, pointF.y, pointF1.x, pointF1.y, paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isFinish())
            return;
        this.mCanvas = canvas;
        if (mCopy == null)
            setImageBitmap(mBitmap);
        if (mCopy != null)
            updateImage(mCopy);
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
        return new PointF((int) ((int) (-(imageWidth / 2) * scale) + mCanvas.getWidth() / 2 + p.x * scale), (int) (p.y * scale));
    }

    public void updateImage(Bitmap bm) {
        super.setImageBitmap2(bm);
        new Handler(Looper.getMainLooper()).post(() -> {
            //Log.d("ImageViewSelection::setImageBitmap",
            //        "change image on UI thread");
            if (mFaces != null && mCanvas != null && bm != null) {
                double scale = drawBitmap();

                double imageWidth = mBitmap.getWidth();
                double imageHeight = mBitmap.getHeight();
                Rect destBounds = new Rect((int) ((int) (-(imageWidth / 2) * scale) + mCanvas.getWidth() / 2), 0, (int) ((int) ((imageWidth / 2) * scale) + mCanvas.getWidth() / 2), (int) (imageHeight * scale));
                mCanvas.drawBitmap(mBitmap, new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight()), destBounds, null);
                Objects.requireNonNull(mFaces).forEach(face -> drawFaceBox(mCanvas, scale));

            }
        });

    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        isFinish = true;
        return super.onSaveInstanceState();
    }

    private void drawFaceBox(Canvas canvas, double scale) {
        //paint should be defined as a member variable rather than
        //being created on each onDraw request, but left here for
        //emphasis.
        if (canvas == null)
            return;
        if (mFaces == null)
            return;
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        float left = 0;
        float top = 0;
        float right = 0;
        float bottom = 0;
        for (int i = 0; i < mFaces.size(); i++) {
            Face face = mFaces.get(i);
            Rect rect = face.getBoundingBox();
            PointF a = coordCanvas(new PointF((int) (rect.left), (int) (rect.top)));
            PointF b = coordCanvas(new PointF((int) (rect.right), (int) (rect.bottom)));
            mCanvas.drawRect(new RectF(a.x, a.y, b.x, b.y), paint);
            action(face);
        }
    }
}