/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
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
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import one.empty3.feature20220726.PixM;
import one.empty3.library.ColorTexture;
import one.empty3.library.Point3D;
import one.empty3.library.PolyLine;

public class ImageViewSelection extends androidx.appcompat.widget.AppCompatImageView {
    protected PixM pixels = null;
    private final Paint paint =
            new Paint();
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
            setImageBitmap2(pixels.getImage().getBitmap());
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
                pixels = new PixM(bm);
            }
        });

    }

    public void setPixels(@NotNull PixM pixM) {
        pixels = pixM;
    }
}
