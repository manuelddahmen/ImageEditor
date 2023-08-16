package one.empty3.feature.app.maxSdk29.pro;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.camera2.interop.ExperimentalCamera2Interop;

public class PolygonDetailsImageView extends FaceOverlayView{
    public PolygonDetailsImageView(@NonNull Context context) {
        super(context);
    }

    public PolygonDetailsImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PolygonDetailsImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isDrawing()) {
            if (!isFinish()) {
                setDrawing(true);
                this.mCanvas = canvas;
                if (mCopy != null) updateImage(mCopy);
                setFinish(true);
                setDrawing( false);
            }
        }
    }


    public void updateImage(Bitmap bm) {

        new Handler(Looper.getMainLooper()).post(() -> {
                super.setImageBitmap3(bm.copy(Bitmap.Config.ARGB_8888, true));

        });
    }

}
