/*
 * Copyright (c) 2024.
 *
 *
 *  Copyright 2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package one.empty3.feature.app.maxSdk29.pro;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import matrix.matrix.PixM;
import one.empty3.library.ColorTexture;
import one.empty3.library.Point3D;
import one.empty3.library.PolyLine;

public class ImageViewSelection extends androidx.appcompat.widget.AppCompatImageView {
    protected matrix.PixM pixels = null;
    protected final Paint paint = new Paint();
    private Context thisActivity = null;

    {
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(10);
    }
    private Class that;
    private RectF rect = null;
    private boolean isDrawingRect = false;

    public ImageViewSelection(@NonNull Context context) {
        super(context);
        this.thisActivity = context;
        that = this.getClass();
    }


    public ImageViewSelection(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.thisActivity = context;
        that = this.getClass();
    }

    public ImageViewSelection(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        that = this.getClass();
        this.thisActivity = context;
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
            setImageBitmap2(pixels.getImage().getImage());
        }
    }

    public RectF getDrawingRect() {
        return rect;
    }


    public void setImageBitmap2(Bitmap bm) {
        boolean post = new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                //Log.d("ImageViewSelection::setImageBitmap", "change image on UI thread");
                setImageBitmap(bm);
                if(that.equals(ImageViewSelection.class)) {
                    if(thisActivity!=null) {
                        pixels = matrix.PixM.getmatrix.PixM(bm, new Utils().getMaxRes(thisActivity));
                    } else {
                        pixels = new matrix.PixM(bm);
                    }
                }
            }
        });

    }
    public void setImageBitmap3(Bitmap bm) {
        boolean post = new Handler(Looper.getMainLooper()).post(() -> setImageBitmap(bm));

    }

    public void setPixels(@NotNull matrix.PixM matrix.PixM) {
        pixels = matrix.PixM;
    }
}
