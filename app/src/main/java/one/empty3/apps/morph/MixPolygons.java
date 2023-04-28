///*
// * Copyright (c) 2023. Manuel Daniel Dahmen
// *
// *
// *    Copyright 2012-2023 Manuel Daniel Dahmen
// *
// *    Licensed under the Apache License, Version 2.0 (the "License");
// *    you may not use this file except in compliance with the License.
// *    You may obtain a copy of the License at
// *
// *        http://www.apache.org/licenses/LICENSE-2.0
// *
// *    Unless required by applicable law or agreed to in writing, software
// *    distributed under the License is distributed on an "AS IS" BASIS,
// *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *    See the License for the specific language governing permissions and
// *    limitations under the License.
// */
//
//package one.empty3.apps.morph;
//
//import one.empty3.library.ITexture;
//import one.empty3.library.Point3D;
//import one.empty3.library.Polygons;
//import one.empty3.library.TextureMorphing;
//import one.empty3.library.core.nurbs.ParametricSurface;
//
//public class MixPolygons extends ParametricSurface {
//    private final ParametricSurface sb;
//    private final ParametricSurface sa;
//    private double time;
//
//    public MixPolygons(ParametricSurface sa, ParametricSurface sb,
//                       ITexture texture1, ITexture texture2) {
//        this.sa = sa;
//        this.sb = sb;
//        texture(new TextureMorphing(texture1, texture2));
//    }
//    public void setTime(double t) {
//        this.time = t;
//        ((TextureMorphing)texture()).setT(t);
//    }
//    private double getTime() {
//        return time;
//    }
//    @Override
//    public Point3D calculerPoint3D(double u, double v) {
//        return sa.calculerPoint3D(u, v).plus(
//                sb.calculerPoint3D(u, v)
//                        .moins(sa.calculerPoint3D(u, v)).mult(getTime()));
//    }
//
//}
