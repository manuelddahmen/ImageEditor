/*
 *
 *  * Copyright (c) 2024. Manuel Daniel Dahmen
 *  *
 *  *
 *  *    Copyright 2024 Manuel Daniel Dahmen
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 *
 */

package one.empty3.feature20220726;
//import one.empty3.*;
import one.empty3.library.*;
/*

Sum [x+i, y+j, dist<sizeK] (I ij *
    gauss derivate ij) 

sobel. 3×3 ou plus. 1*2+1
|p1 -p2| (/ n/n)?


 Somme pondérée Gradient(i, j) * dérivée gauss(x-i, y-j) 
= M3 cols, lines, sigmas
 Map of vectors. 
*/
/*
public class GradientMapFilter extends FilterPixGMatrix {
    private M3 map;
    private PixM gaussMap;
    public double gaussDerivateX(int x, int y ) {
        return 0.0;
    } 
    public double gaussDerivateY(int x, int y ) {
        return 0.0;
    } 
    public double deltaIx(int x, int y) {
        return 0.0;
    } 
    public double deltaIy(int x, int y) {
        return 0.0;
    } 
    public double deltaGx(int x, int y) {
        return 0.0;
    } 

    public double deltaGy(int x, int y) {
        return 0.0;
    } 
    public GradientMapFilter(PixM img, int sigmas) {
       gradient = new PixM[sigmas] ;
       super(img.getColumns(), img.getLines()) ;
       this.img = img;
       for(int i=0; i<sigmas; i++) {
           GradientFilter gf = new GradientFilter((int ) (5.*i/sigmas), 0.6+0.5*sigmas);
           gradient[i] = img.applyFilter(gf);
      } 
    }

    public double filter(double x, double y) {
        return 0.0;
    } 
    
    
}
*/
