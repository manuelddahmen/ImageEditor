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

/*
 *  This file is part of Empty3.
 *
 *     Empty3 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Empty3 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
 */

/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

/*
 * 2013 Manuel Dahmen
 */
/*__
 * @author Manuel
 */
package one.empty3.library;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

import one.empty3.feature0.GoogleFaceDetection;
import one.empty3.feature0.PixM;
import one.empty3.library.core.nurbs.ParametricCurve;
import one.empty3.library.core.nurbs.SurfaceElem;


public class Polygon extends Representable implements SurfaceElem, ClosedCurve, Parcelable {

    /*__
     *
     */
    private StructureMatrix<Point3D> points = new StructureMatrix<>(1, Point3D.class);

    public Polygon() {
        super();
        declareProperties();
    }

    public Polygon(Color c) {
        this();
        texture(new TextureCol(c));
    }

    public Polygon(ITexture c) {
        this();
        texture(c);
    }

    public Polygon(Point3D[] list, Color c) {
        this(list, new TextureCol(c));
    }

    public Polygon(Point3D[] list, ITexture c) {
        this();
        this.texture = c;
        points.setAll(list);
    }

    protected Polygon(Parcel in) {
        int size = in.readInt();
        double[] pointsListXyz = new double[size * 3];
        in.readDoubleArray(pointsListXyz);
        Point3D[] point3Ds = new Point3D[size];
        for (int k = 0; k < pointsListXyz.length; k++) {
            point3Ds[k] = new Point3D(pointsListXyz[k * 3],
                    pointsListXyz[k * 3 + 1],
                    pointsListXyz[k * 3 + 2]);
        }
        setPoints(point3Ds);
    }

    public static final Creator<Polygon> CREATOR = new Creator<Polygon>() {
        @Override
        public Polygon createFromParcel(Parcel in) {
            return new Polygon(in);
        }

        @Override
        public Polygon[] newArray(int size) {
            return new Polygon[size];
        }
    };

    public void add(Point3D point3D) {
        int newLength;
        if (points == null) {
            points = new StructureMatrix<>(1, Point3D.class);
        } else {
            newLength = points.getData1d().size() + 1;
            java.util.List<Point3D> tmp = points.getData1d();
            points = new StructureMatrix<>(1, Point3D.class);
            for (int i = 0; i < tmp.size(); i++) {
                points.setElem(tmp.get(i), i);
            }
            points.setElem(point3D, newLength - 1);
        }
        declareProperties();
    }


    public StructureMatrix<Point3D> getPoints() {
        return points;
    }

    public void setPoints(Point3D[] points) {
        this.points.setAll(points);
        declareProperties();
    }


    @Override
    public String toString() {
        String t = "poly (\n\t(";
        for (Point3D p : points.getData1d()) {
            t += "\n\t\t" + (p == null ? "null" : p.toString());
        }
        t += "\n\t)\n\t" + (texture == null ? "" : texture.toString()) + "\n)\n\n";
        return t;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Polygon polygone = (Polygon) o;

        return getPoints() != null ? getPoints().equals(polygone.getPoints()) : polygone.getPoints() == null;

    }

    @Override
    public int hashCode() {
        return getPoints() != null ? getPoints().hashCode() : 0;
    }

    public Point3D getIsocentre() {
        Point3D p = Point3D.O0;

        for (Point3D p0 : points.getData1d()) {
            p = p.plus(p0);
        }
        return p.mult(1. / points.getData1d().size());
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("points/point 0 à N du Polygone", points);

    }

    public StructureMatrix<Point3D> getBoundRect2d() {
        StructureMatrix<Point3D> boundRect2d = new StructureMatrix<>(1, Point3D.class);
        boundRect2d.setElem(new Point3D(10000d, 10000d, 0d), 0);
        boundRect2d.setElem(new Point3D(-10000d, -10000d, 0d), 1);
        for (Point3D point3D : getPoints().getData1d()) {
            //System.out.println("currentPoint: point3D"+point3D);
            //System.out.println(point3D);
            if (point3D.get(0) <= boundRect2d.getElem(0).get(0))
                boundRect2d.getElem(0).set(0, (double) point3D.get(0));
            if (point3D.get(1) <= boundRect2d.getElem(0).get(1))
                boundRect2d.getElem(0).set(1, (double) point3D.get(1));
            if (point3D.get(0) >= boundRect2d.getElem(1).get(0))
                boundRect2d.getElem(1).set(0, (double) point3D.get(0));
            if (point3D.get(1) >= boundRect2d.getElem(1).get(1))
                boundRect2d.getElem(1).set(1, (double) point3D.get(1));
        }
        //System.out.println("Polygon ("+getPoints().getData1d().size()+")bounds: "+boundRect2d);

        return boundRect2d;
    }

    private Point3D getPosition(Point3D p, double scale, PointF position) {
        return new Point3D(p.get(0) * scale + position.x, p.get(1) * scale + position.y, 0.0);
    }

    private Point3D getPositionOnPicture(Point3D pCanvas, double scale, PointF position) {
        return new Point3D((pCanvas.get(0) - position.x) / scale,
                (pCanvas.get(1) - position.y) / scale, 0.0);
    }

    public boolean leftToRightScanPixM(PixM pixM, int x, int y, boolean[] columnLeft, boolean[] columnRight) {
        boolean foundLeft = columnLeft[y];
        boolean foundRight = columnRight[y];
        if (x >= 0 && x < pixM.getColumns()) {
            for (int i = 0; i < x && !foundLeft; i++) {
                if (!pixM.getP(i, y).equals(Point3D.O0)) {
                    foundLeft = true;
                }
            }
            for (int i = x; i < pixM.getColumns() && !foundRight; i++) {
                if (!pixM.getP(i, y).equals(Point3D.O0)) {
                    foundRight = true;
                }
            }
        }
        columnLeft[y] = foundLeft;
        columnRight[y] = foundRight;
        return !(foundLeft && !foundRight) || (foundLeft && foundRight) && !(!foundLeft && !foundRight);
    }

    public boolean leftToRightScanPixM(@NonNull PixM pixM, int x, int y, boolean isDrawingOnImage) {
        if(pixM==null)
            return false;
        boolean foundLeft = false;
        boolean foundRight = false;
        if (x >= 0 && x < pixM.getColumns()) {
            for (int i = 0; i < x && !foundLeft; i++) {
                if (!pixM.getP(i, y).equals(Point3D.O0)) {
                    foundLeft = true;
                }
            }
            for (int i = x; i < pixM.getColumns() && !foundRight; i++) {
                if (!pixM.getP(i, y).equals(Point3D.O0)) {
                    foundRight = true;
                }
            }
        }
        return !(foundLeft && !foundRight) || (foundLeft && foundRight) && !(!foundLeft && !foundRight);
    }

    public PixM fillPolygon2D(GoogleFaceDetection.FaceData.Surface faceSurface, Canvas canvas, Bitmap bitmap, int transparent, double deep, PointF position, double scale) {
        int TRANSPARENT = -1;
        boolean isDrawingOnImage = true;
        int pixels = 0;


        int colorTemp = this.texture().getColorAt(0.5, 0.5);

        StructureMatrix<Point3D> boundRect2d = this.getBoundRect2d();

        faceSurface.setPolygon(this);//!!!???

        if (!isDrawingOnImage) {
            boundRect2d.setElem(getPosition(boundRect2d.getElem(0), scale, position), 0);
            boundRect2d.setElem(getPosition(boundRect2d.getElem(1), scale, position), 1);
        } else {

        }
        double left = (boundRect2d.getElem(0).get(0) - 1);
        double top = (boundRect2d.getElem(0).get(1) - 1);
        double right = (boundRect2d.getElem(1).get(0) + 1);
        double bottom = (boundRect2d.getElem(1).get(1) + 1);
        double widthBox = right - left;
        double heightBox = bottom - top;

        if (!(widthBox > 0 && heightBox > 0))
            return null;

        PixM pixM = new PixM((int) (widthBox), (int) (heightBox));

        faceSurface.setContours(pixM);
        if(faceSurface.getActualDrawing()==null)
            faceSurface.setActualDrawing(pixM.copy());
        int count = 0;

        int[] currentColor = new int[(int) (heightBox + 1)];
        Arrays.fill(currentColor, transparent);

        int size = this.getPoints().getData1d().size();
        for (int i = 0; i < size; i++) {
            Point3D p1 = null;
            Point3D p2 = null;

            if (!isDrawingOnImage) {
                p1 = getPosition(this.getPoints().getData1d().get((i + size) % size), scale, position);
                p2 = getPosition(this.getPoints().getData1d().get((i + 1 + size) % size), scale, position);
            } else {
                p1 = this.getPoints().getData1d().get((i + size) % size);
                p2 = this.getPoints().getData1d().get((i + 1 + size) % size);
            }

            ParametricCurve curve = new LineSegment(new Point3D(p1.get(0) - left, p1.get(1) - top, 0.0), new Point3D(p2.get(0) - left, p2.get(1) - top, 0.0));
            curve.texture(new ColorTexture(colorTemp));
            curve.setIncrU(0.1);
            pixM.plotCurve(curve, new ColorTexture(colorTemp));
        }

        //paint = new Paint();

        double[] fillColorArrayPolygon = Lumiere.getDoubles(colorTemp);
        //paint.setColor(colorTemp);
        //paint.setAntiAlias(true);
        //paint.setStrokeWidth(2);
        int paintColor = colorTemp;
        System.out.println("filLPolygon2D: (" + (right - left) + ", " + (bottom - top) + ")s");

        double[] color1 = new double[3];
        for (double i = left; i < right; i++) {
            for (double j = top; j < bottom; j++) {
                int xMap = (int) (i - left);
                int yMap = (int) (j - top);

                double[] color = pixM.getValues(xMap, yMap);

                double[] colorP1 = pixM.getValues(xMap + 1, yMap);

                int imageColor = Lumiere.getInt(color);
                int imageColorP1 = Lumiere.getInt(colorP1);

                int polygonColor = this.texture().getColorAt(1.0 * xMap / (right - left), 1.0 * yMap / (bottom - top));

                if (yMap >= currentColor.length) {
                    continue;
                }


                if (imageColor == colorTemp && currentColor[(int) yMap] == colorTemp && imageColorP1 != colorTemp) {
                    currentColor[(int) yMap] = transparent;
                } else if (imageColor == colorTemp && currentColor[(int) yMap] == transparent && imageColorP1 != colorTemp) {
                    currentColor[(int) yMap] = colorTemp;
                } else if (currentColor[(int) yMap] == colorTemp &&
                        leftToRightScanPixM(pixM, xMap, yMap, isDrawingOnImage)) {

                    if (!isDrawingOnImage && i < canvas.getWidth() && i >= 0 && j < canvas.getHeight() && j >= 0) {
                        //Point3D positionOnPicture = new Point3D((double) i, (double) j, 0.0);
                        //canvas.drawPoint((int) (double) positionOnPicture.get(0),
                        //        (int) (double) positionOnPicture.get(1), colorTemp);
                        //pixels++;
                    } else if (isDrawingOnImage && i < bitmap.getWidth() && i >= 0 && j < bitmap.getHeight() && j >= 0) {
                        if(faceSurface.isDrawOriginalImageContour()) {
                            int x2 = (int) (1.0 * (i-left) * faceSurface.actualDrawing.getColumns()/(right-left));
                            int y2 = (int) (1.0 * (j-top)   * faceSurface.actualDrawing.getLines()/(bottom - top));
                            faceSurface.getActualDrawing().getColor(x2, y2, color1);
                            bitmap.setPixel((int) i, (int) j, Lumiere.getInt(color1));
                            pixM.setValues(xMap, yMap, color1);
                        } else {
                            bitmap.setPixel((int) i, (int) j, colorTemp);
                            pixM.setValues(xMap, yMap, fillColorArrayPolygon);
                        }
                        pixels++;
                    }
                }


                count++;
            }
        }
        System.out.println("Points count : " + count + " | Points drawn : " + pixels + "fillPolygon");

        return pixM;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        int size = getPoints().getData1d().size();
        double[] pointsListXyz = new double[size * 3];
        for (int k = 0; k < size; k++) {
            for (int j = 0; j < 3; j++) {
                pointsListXyz[j + k * 3] = getPoints().getElem(k).get(j);
            }
        }
        parcel.writeInt(size);
        parcel.writeDoubleArray(pointsListXyz);
    }

    public void fillPolygon2DFromData(@NonNull GoogleFaceDetection.FaceData.Surface surface,
                                      @NonNull Bitmap mCopy, int black, boolean drawOriginalImageContour) {
        int pixels = 0;


        StructureMatrix<Point3D> boundRect2d = this.getBoundRect2d();

        double leftImg = boundRect2d.getElem(0).get(0);
        double rightImg = boundRect2d.getElem(1).get(0);

        double left = (boundRect2d.getElem(0).get(0) - 1);
        double top = (boundRect2d.getElem(0).get(1) - 1);
        double right = (boundRect2d.getElem(1).get(0) + 1);
        double bottom = (boundRect2d.getElem(1).get(1) + 1);
        double widthBox = right - left;
        double heightBox = bottom - top;

        if (!(widthBox > 0 && heightBox > 0))
            return;
        PixM pixMilled, pixMori;

        pixMori = surface.getActualDrawing();
        pixMilled = surface.getFilledContours();

        int count = 0;

        System.out.println("filLPolygon2D: (" + (right - left) + ", " + (bottom - top) + ")");

        int transparent = surface.getColorTransparent();


        final double[] floats = new double[3];
        for (double i = left; i < right; i++) {
            for (double j = top; j < bottom; j++) {
                int xMap = (int) (i - left);
                int yMap = (int) (j - top);


                if (pixMilled != null && mCopy!=null) {
                    double[] color = pixMilled.getValues(xMap, yMap);
                    int colorToDraw = Lumiere.getInt(color);
                    if (transparent != colorToDraw
                            && i >= 0 && i < mCopy.getWidth() && j >= 0 && j < mCopy.getHeight()) {
                        if (drawOriginalImageContour) {
                            int a = (int) (xMap / (right - left) * pixMori.getColumns());
                            int b = (int) (yMap / (bottom - top) * pixMori.getLines());
                            if (a >= 0 && b >= 0 && pixMori!=null&&a < pixMori.getColumns() && b < pixMori.getLines()) {
                                    int anInt1 = Lumiere.getInt(pixMori.getValues(a, b));
                                    mCopy.setPixel((int) i, (int) j, anInt1);
                            }
                        } else {
                            mCopy.setPixel((int) i, (int) j, colorToDraw);
                        }
                    }
                    pixels++;


                    count++;
                }
            }
        }
        System.out.println("Points count : " + count + " | Points drawn : " + pixels + "fillPolygon");

        return;
    }

    @Override
    public Serialisable decode(DataInputStream in) {
        Polygon p = new Polygon();
        try {
            int points = in.readInt();
            for (int i = 0; i < points; i++) {
                Point3D p1 = new Point3D();
                for (int j = 0; j <3; j++) {

                    p1.set(j ,in.readDouble());
                }
                p.getPoints().setElem(p1, i);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return p;
    }

    @Override
    public int encode(DataOutputStream out) {
        try {
            out.writeInt(getPoints().data1d.size());
            for (int i = 0; i < getPoints().data1d.size(); i++) {
                for (int j = 0; j <3; j++) {
                    out.writeDouble(getPoints().getElem(i).get(j));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
