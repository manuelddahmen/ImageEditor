package one.empty3.feature.app.maxSdk29.pro;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
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
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.google.mlkit.vision.face.internal.FaceDetectorImpl;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import one.empty3.feature20220726.PixM;

public class FaceOverlayView extends ImageViewSelection {
    private List<Face> mFaces;
    protected Bitmap mBitmap;
    private Canvas mCanvas;
    private Bitmap mCopy;

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
                            .build();

// Real-time contour detection
            FaceDetectorOptions realTimeOpts =
                    new FaceDetectorOptions.Builder()
                            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                            .build();

            InputImage inputImage = InputImage.fromBitmap(bitmap, 0);

            mCanvas = new Canvas(mCopy);

            FaceDetector client = FaceDetection.getClient(highAccuracyOpts);
            client.process(inputImage).addOnSuccessListener(faces ->  {
                mFaces = faces;


                mFaces.forEach(new Consumer<Face>() {
                    @Override
                    public void accept(Face face) {

                    }
                });

            });

            /*
            com.google.android.gms.vision.face.FaceDetector detector = new com.google.android.gms.vision.face.FaceDetector.Builder( getContext() )
                    .setTrackingEnabled(false)
                    .setLandmarkType(com.google.android.gms.vision.face.FaceDetector.ALL_LANDMARKS)
                    .setMode(com.google.android.gms.vision.face.FaceDetector.FAST_MODE)
                    .build();
                    */
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        setImageBitmap2(mCopy);

        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mCanvas = canvas;
        if(mCopy==null)
            setImageBitmap(mBitmap);
        if(mCopy!=null)
            updateImage(mCopy);
    }

    private double drawBitmap() {
        if(mCanvas==null)
            return 1.0;
        if(mBitmap==null)
            return 1.0;

        double viewWidth = mCanvas.getWidth();
        double viewHeight = mCanvas.getHeight();
        double imageWidth = mBitmap.getWidth();
        double imageHeight = mBitmap.getHeight();
        double scale = Math.min(viewWidth / imageWidth, viewHeight / imageHeight);
        Rect destBounds = new Rect(0, 0, (int) (imageWidth * scale), (int) (imageHeight * scale));
        mCanvas.drawBitmap(mBitmap, new Rect(0,0,mBitmap.getWidth(),mBitmap.getHeight()), destBounds, null);
        return scale;
    }


    public void updateImage(Bitmap bm) {
        super.setImageBitmap2(bm);
        new Handler(Looper.getMainLooper()).post(() -> {
            Log.d("ImageViewSelection::setImageBitmap",
                    "change image on UI thread");
            if(mFaces!=null && mCanvas!=null && bm!=null) {
                double scale = drawBitmap();
                Objects.requireNonNull(mFaces).forEach(face -> drawFaceBox(mCanvas, scale));
            }
        });

    }

    private void drawFaceBox(Canvas canvas, double scale) {
        //paint should be defined as a member variable rather than
        //being created on each onDraw request, but left here for
        //emphasis.
        if(canvas==null)
            return;
        if(mFaces==null)
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
            mCanvas.drawRect((int)(rect.left*scale), (int)(rect.top*scale),
                    (int)(rect.right*scale), (int)(rect.bottom*scale), paint);
        }
    }
}