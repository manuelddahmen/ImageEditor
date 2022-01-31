///*
// * This file is part of Plants-Growth-2
// *     Plants-Growth-2 is free software: you can redistribute it and/or modify
// *     it under the terms of the GNU General Public License as published by
// *     the Free Software Foundation, either version 3 of the License, or
// *     (at your option) any later version.
// *
// *     Plants-Growth-2 is distributed in the hope that it will be useful,
// *     but WITHOUT ANY WARRANTY; without even the implied warranty of
// *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *     GNU General Public License for more details.
// *
// *     You should have received a copy of the GNU General Public License
// *     along with Plants-Growth-2.  If not, see <http://www.gnu.org/licenses/>.
// */
//
///*
// * This file is part of Plants-Growth-2
// *     Plants-Growth-2 is free software: you can redistribute it and/or modify
// *     it under the terms of the GNU General Public License as published by
// *     the Free Software Foundation, either version 3 of the License, or
// *     (at your option) any later version.
// *
// *     Plants-Growth-2 is distributed in the hope that it will be useful,
// *     but WITHOUT ANY WARRANTY; without even the implied warranty of
// *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *     GNU General Public License for more details.
// *
// *     You should have received a copy of the GNU General Public License
// *     along with Plants-Growth-2.  If not, see <http://www.gnu.org/licenses/>.
// */
//
///*
// * This file is part of Plants-Growth-2
// *     Foobar is free software: you can redistribute it and/or modify
// *     it under the terms of the GNU General Public License as published by
// *     the Free Software Foundation, either version 3 of the License, or
// *     (at your option) any later version.
// *
// *     Foobar is distributed in the hope that it will be useful,
// *     but WITHOUT ANY WARRANTY; without even the implied warranty of
// *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *     GNU General Public License for more details.
// *
// *     You should have received a copy of the GNU General Public License
// *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
// */
//
///*
// * This file is part of Plants-Growth-2
// *     Foobar is free software: you can redistribute it and/or modify
// *     it under the terms of the GNU General Public License as published by
// *     the Free Software Foundation, either version 3 of the License, or
// *     (at your option) any later version.
// *
// *     Foobar is distributed in the hope that it will be useful,
// *     but WITHOUT ANY WARRANTY; without even the implied warranty of
// *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *     GNU General Public License for more details.
// *
// *     You should have received a copy of the GNU General Public License
// *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
// */
//
///*
// * This file is part of Plants-Growth-2
// *     Foobar is free software: you can redistribute it and/or modify
// *     it under the terms of the GNU General Public License as published by
// *     the Free Software Foundation, either version 3 of the License, or
// *     (at your option) any later version.
// *
// *     Foobar is distributed in the hope that it will be useful,
// *     but WITHOUT ANY WARRANTY; without even the implied warranty of
// *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *     GNU General Public License for more details.
// *
// *     You should have received a copy of the GNU General Public License
// *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
// */
//
///*
// * This file is part of Plants-Growth-2
// *     Foobar is free software: you can redistribute it and/or modify
// *     it under the terms of the GNU General Public License as published by
// *     the Free Software Foundation, either version 3 of the License, or
// *     (at your option) any later version.
// *
// *     Foobar is distributed in the hope that it will be useful,
// *     but WITHOUT ANY WARRANTY; without even the implied warranty of
// *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *     GNU General Public License for more details.
// *
// *     You should have received a copy of the GNU General Public License
// *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
// */
//
///*
// * This file is part of Plants-Growth-2
// *     Foobar is free software: you can redistribute it and/or modify
// *     it under the terms of the GNU General Public License as published by
// *     the Free Software Foundation, either version 3 of the License, or
// *     (at your option) any later version.
// *
// *     Foobar is distributed in the hope that it will be useful,
// *     but WITHOUT ANY WARRANTY; without even the implied warranty of
// *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *     GNU General Public License for more details.
// *
// *     You should have received a copy of the GNU General Public License
// *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
// */
//
//package one.empty3.growth.graphics;
//
//import one.empty3.library.Matrix33;
//import one.empty3.library.Point3D;
//
//
//
//public class Turtle2D {
//    private Color zeColor = Color.GRAY;
//
//    private Matrix33 theRot(double angle) {
//        return new Matrix33(new double[]
//                {
//                        Math.cos(angle),
//                        Math.sin(angle),
//                        0.,
//                        Math.sin(angle),
//                        Math.cos(angle),
//                        0d,
//                        0d, 0d, 0d
//                }
//        );
//    }
//
//    private Point3D moveTo(double length) {
//        return new Point3D(Math.cos(rads), Math.sin(rads), 0.).mult(length);
//    }
//
//    private Rectangle dimImage;
//    private Image zeImage;
//    private Point3D position;
//    private double rads = 0;
//
//    public Turtle2D(Image image) {
//        zeImage = image;
//        position = new Point3D(image.getWidth(null) / 2., image.getHeight(null) / 2., 0.);
//    }
//
//    public Rectangle getDimImage() {
//        return dimImage;
//    }
//
//    public Color getZeColor() {
//        return zeColor;
//    }
//
//    public void setZeColor(Color zeColor) {
//        this.zeColor = zeColor;
//    }
//
//    public void setPosition(Point3D position) {
//        this.position = position;
//    }
//
//    public void setDimImage(Rectangle dimImage) {
//        this.dimImage = dimImage;
//    }
//
//    public Image getZeImage() {
//        return zeImage;
//    }
//
//    public void setZeImage(Image zeImage) {
//        this.zeImage = zeImage;
//    }
//
//    public Point3D getPosition() {
//        return position;
//    }
//
//
//    public double getRads() {
//        return rads;
//    }
//
//    public void setRads(double rads) {
//        this.rads = rads;
//    }
//
//    public void line(double length) {
//        Point3D extremite = position.plus(moveTo(length));
//        Graphics graphics = getZeImage().getGraphics();
//        graphics.setColor(zeColor);
//        graphics.drawLine((int)(double ) position.getX(),
//                (int)(double ) position.getY(),
//                (int)(double ) extremite.getX(),
//                (int)(double ) extremite.getY());
//        position = extremite;
//    }
//
//    public void move(double length) {
//        this.position = position.plus(moveTo(length));
//    }
//
//    public void right(double rads) {
//        setRads(this.getRads() - rads);
//    }
//
//    public void left(double rads) {
//        setRads(this.getRads() + rads);
//    }
//
//}
