/*
 * Copyright (c) 2023.
 *
 *
 */

package one.empty3.feature.app.maxSdk29.pro;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import one.empty3.feature20220726.PixM;
//import one.empty3.library.Rectangle2D;
import one.empty3.library.ColorTexture;
import one.empty3.library.Point3D;
import one.empty3.library.PolyLine;

public class ImageViewSelection extends androidx.appcompat.widget.AppCompatImageView {
    private final Paint paint =
            new Paint();
    private PixM pixels = null;

    {
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(10);
    }

    private RectF rect = null;
    private boolean isDrawingRect = false;

    public ImageViewSelection(@NonNull Context context) {
        super(context);

    }

    public ImageViewSelection(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewSelection(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDrawingRect(RectF rect) {
        this.rect = rect;
    }

    public void setDrawingRectState(boolean isDrawingRect) {
        this.isDrawingRect = isDrawingRect;
        if (isDrawingRect) {
            drawRect();
        }
    }

    private void drawRect() {
        if (isDrawingRect && pixels != null) {
            PolyLine polyLine = new PolyLine();
            polyLine.getPoints().add(new Point3D((double) rect.left, (double) rect.top, 0.0));
            polyLine.getPoints().add(new Point3D((double) rect.right, (double) rect.top, 0.0));
            polyLine.getPoints().add(new Point3D((double) rect.right, (double) rect.bottom, 0.0));
            polyLine.getPoints().add(new Point3D((double) rect.left, (double) rect.bottom, 0.0));
            pixels.plotCurve(polyLine, new ColorTexture(Color.YELLOW));
            setImageBitmap(pixels.getImage().getBitmap());
        }


    }

    @Override
    public void setWillNotDraw(boolean willNotDraw) {
        super.setWillNotDraw(willNotDraw);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //drawRect();
    }

    public RectF getDrawingRect() {
        return rect;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        this.pixels = new PixM(bm);
    }


}
