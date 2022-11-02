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

package one.empty3.library.core.nurbs;

public class NurbsRewrite {
    /*  Subroutine to generate B-spline basis functions for open knot vectors

     C code for An Introduction to NURBS
     by David F. Rogers. Copyright (C) 2000 David F. Rogers,
     All rights reserved.
	
     Name: basis.c
     Language: C
     Subroutines called: none
     Book reference: p. 279

     c        = order of the B-spline basis function
     d        = first term of the basis function recursion relation
     e        = second term of the basis function recursion relation
     npts     = number of defining polygon vertices
     n[]      = array containing the basis functions
     n[1] contains the basis function associated with B1 etc.
     nplusc   = constant -- npts + c -- maximum number of knot values
     t        = parameter value
     temp[]   = temporary array
     coordArr[]      = knot vector
     */

    public static void basis(int c, double t, int npts, int[] x, double[] n) {
        int nplusc;
        int i, k;
        double d, e;
        double[] temp = new double[36];

        nplusc = npts + c;

        /*		printf("knot vector is \n");
         for (i = 1; i <= nplusc; i++){
         printf(" %d %d \n", i,coordArr[i]);
         }
         printf("t is %f \n", t);
         */

        /* calculate the first order basis functions n[i][1]	*/
        for (i = 0; i < nplusc - 1; i++) {
            if ((t >= x[i]) && (t < x[i + 1])) {
                temp[i] = 1;
            } else {
                temp[i] = 0;
            }
        }

        /* calculate the higher order basis functions */
        for (k = 1; k < c; k++) {
            for (i = 0; i < nplusc - k; i++) {
                if (temp[i] != 0) /* if the lower order basis function is zero skip the calculation */ {
                    d = ((t - x[i]) * temp[i]) / (x[i + k - 1] - x[i]);
                } else {
                    d = 0;
                }

                if (temp[i + 1] != 0) /* if the lower order basis function is zero skip the calculation */ {
                    e = ((x[i + k] - t) * temp[i + 1]) / (x[i + k] - x[i + 1]);
                } else {
                    e = 0;
                }

                temp[i] = d + e;
            }
        }

        if (t == (double) x[nplusc]) {        /*    pick up last point	*/

            temp[npts] = 1;
        }

        /* put in n array	*/
        for (i = 0; i < npts; i++) {
            n[i] = temp[i];
        }
    }
    /*
     Subroutine to generate a B-spline open knot vector with multiplicity
     equal to the order at the ends.
	
     c            = order of the basis function
     n            = the number of defining polygon vertices
     nplus2       = index of coordArr() for the first occurence of the maximum knot vector value
     nplusc       = maximum value of the knot vector -- $n + c$
     coordArr()          = array containing the knot vector
     */

    public static void knot(int n, int c, int[] x) {
        int nplusc, nplus2, i;

        nplusc = n + c;
        nplus2 = n + 2;

        x[1] = 0;
        for (i = 1; i < nplusc; i++) {
            if ((i > c) && (i < nplus2)) {
                x[i] = x[i - 1] + 1;
            } else {
                x[i] = x[i - 1];
            }
        }
    }
    /*  Subroutine to calculate a Cartesian product rational B-spline
     surface using open uniform knot vectors (see Eq. (7.1)).

     C code for An Introduction to NURBS
     by David F. Rogers. Copyright (C) 2000 David F. Rogers,
     All rights reserved.
	
     Name: rbspsurf.c
     Language: C
     Subroutines called: knot.c, basis.c sumrbas.c
     Book reference: Chapter 7, Section 7.1, Alg. p. 308

     b[]         = array containing the polygon net points
     b[1] = coordArr-component
     b[2] = y-component
     b[3] = z-component
     b[4] = h-component
     Note: Bi,j = b[] has dimensions of n*m*3 with j varying fastest
     The polygon net is n coordArr m
     k           = order in the u direction
     l           = order in the w direction
     mbasis[]    = array containing the nonrational basis functions for one value of w (see Eq. (3.2))
     mpts        = the number of polygon vertices in w direction
     nbasis[]    = array containing the nonrational basis functions for one value of u (see Eq. (3.2))
     npts        = the number of polygon vertices in u direction
     p1          = number of parametric lines in the u direction
     p2          = number of parametric lines in the w direction
     q[]         = array containing the resulting surface
     q[1] = coordArr-component
     q[2] = y-component
     q[3] = z-component
     for a fixed value of u the next m elements contain
     the values for the curve q[u[sub i],w] q has dimensions
     of p1*p2*3. The display surface is p1 coordArr p2
     */

    public static void rbspsurf(
            int k, int l,
            int npts, int mpts,
            int p1, int p2,
            double[] b, double[] q) {

        /*   allows for 20 data points with basis function of order 5 */
        int i, j, j1, jbas;
        int icount;
        int uinc, winc;
        int nplusc, mplusc;
        double pbasis;
        double h;
        double sum;
        double u, w;
        double stepu, stepw;

        /*  printf("in bsplsurf.c \n"); */

        /*    zero and redimension the arrays */
        nplusc = npts + k;
        mplusc = mpts + l;

        int[] x = new int[nplusc];
        int[] y = new int[mplusc];
        int temp;

        double[] nbasis = new double[npts];
        double[] mbasis = new double[mpts];

        for (i = 0; i < nplusc; i++) {
            x[i] = 0;
        }
        for (i = 0; i < mplusc; i++) {
            y[i] = 0;
        }
        for (i = 0; i < npts; i++) {
            nbasis[i] = 0.;
        }
        for (i = 0; i < mpts; i++) {
            mbasis[i] = 0.;
        }

        temp = 3 * (p1 * p2);

        for (i = 0; i < 3 * p1 * p2; i++) {
            q[i] = 0.;
        }

        /*   generate the open uniform knot vectors */
        knot(npts, k, x);       /*  calculate u knot vector */

        knot(mpts, l, y);       /*  calculate w knot vector */

        icount = 1;

        /*    calculate the points on the B-spline surface */
        stepu = (double) x[nplusc] / (double) (p1 - 1);
        stepw = (double) y[mplusc] / (double) (p2 - 1);
        u = 0.;
        for (uinc = 0; uinc < p1; uinc++) {
            if ((double) x[nplusc] - u < 5e-6) {
                u = (double) x[nplusc];
            }
            basis(k, u, npts, x, nbasis);    /* basis function for this value of u */

            w = 0.;
            for (winc = 0; winc < p2; winc++) {
                if ((double) y[mplusc] - w < 5e-6) {
                    w = (double) y[mplusc];
                }
                basis(l, w, mpts, y, mbasis);    /* basis function for this value of w */

                sum = sumrbas(b, nbasis, mbasis, npts, mpts);
                /*        	printf("in rbspsurf u,w,sum = %f %f %f \n",u,w,sum);*/
                for (i = 0; i < npts; i++) {
                    if (nbasis[i] != 0.) {
                        jbas = 4 * mpts * (i - 1);
                        for (j = 0; j < mpts; j++) {
                            if (mbasis[j] != 0.) {
                                j1 = jbas + 4 * (j - 1) + 1;
                                pbasis = b[j1 + 3] * nbasis[i] * mbasis[j] / sum;
                                q[icount] = q[icount] + b[j1] * pbasis;  /* calculate surface point */

                                q[icount + 1] = q[icount + 1] + b[j1 + 1] * pbasis;
                                q[icount + 2] = q[icount + 2] + b[j1 + 2] * pbasis;
                            }
                        }
                    }
                }
                icount = icount + 3;
                w = w + stepw;
            }
            u = u + stepu;
        }
    }
    /*  Subroutine to calculate the sum of the nonrational basis functions (see \eq {6--87}).

     C code for An Introduction to NURBS
     by David F. Rogers. Copyright (C) 2000 David F. Rogers,
     All rights reserved.
	
     Name: sumrbas.c
     Language: C
     Subroutines called: none
     Book reference: Chapter 7, Section 7.1, Alg. p.309
	
     b[]         = array containing the polygon net points
     b[1] = coordArr-component
     b[2] = y-component
     b[3] = z-component
     b[4] = homogeneous coordinate weighting factor        
     Note: Bi,j = b[] has dimensions of n*m coordArr 4 with j varying fastest
     The polygon net is n coordArr m
     mbasis[,]  = array containing the nonrational basis functions for one value of w
     mpts       = the number of polygon vertices in w direction
     nbasis[,]  = array containing the nonrational basis functions for one value of u
     npts       = the number of polygon vertices in u direction
     sum        = sum of the basis functions
     */

    public static double sumrbas(
            double b[], double[] nbasis, double[] mbasis,
            int npts, int mpts) {

        int i, j, jbas, j1;
        double sum;

        /* calculate the sum */
        sum = 0;
        /*	printf("npts,mpts %d %d \n", npts,mpts);*/

        for (i = 0; i < npts; i++) {
            if (nbasis[i] != 0.) {
                jbas = 4 * mpts * (i - 1);
                for (j = 0; j < mpts; j++) {
                    /*				printf("i,j,jbas %d %d %d \n",i,j,jbas);*/
                    if (mbasis[j] != 0.) {
                        j1 = jbas + 4 * (j - 1) + 4;
                        /*					printf("in sumrbas j1 = %d \n",j1);*/
                        sum = sum + b[j1] * nbasis[i] * mbasis[j];
                    }
                }
            }
        }
        return (sum);
    }

    /*
     Test program for C code from An Introduction to NURBS
     by David F. Rogers. Copyright (C) 2000 David F. Rogers,
     All rights reserved.
	
     Name: trbsurf.c
     Purpose: Test rational B-spline surface generator
     Language: C
     Subroutines called: rbspsurf.c
     Book reference: Chapter 7, Section 7.1, Alg. p 308
     */
    public static void main(String[] args) {

        int i;
        int npts, mpts;
        int k, l;
        int p1, p2;

        double[] b = new double[66];
        double[] q = new double[701];

        char[] header = new char[80];
        int hdrlen;
        /*
         Data for the standard test control net.
         Comment out to use file input.
         */
        npts = 4;
        mpts = 4;
        k = 3;
        l = 3;

        p1 = 5;
        p2 = 5;

        /*	printf("k,l,npts,mpts,p1,p2 = %d %d %d %d %d %d \n",k,l,npts,mpts,p1,p2);*/
        for (i = 0; i <= 4 * npts; i++) {
            b[i] = 0.;
        }

        for (i = 0; i <= 3 * p1 * p2; i++) {
            q[i] = 0.;
        }

        /*
         This is a standard test control polygon from Ex. 6.1, p. 184
         Comment out to use file input <filename>.rnp. Data is in the
         form coordArr=b[1], y=b[2], z=b[3], h=b[4], etc. All h are 1.0.
         Thus, this is a nonrational B-spline surface and the results
         should be the same as for tbsurf.c
         */
        b[1] = -15.;
        b[2] = 0.;
        b[3] = 15.;
        b[4] = 1;
        b[5] = -15.;
        b[6] = 5.;
        b[7] = 5.;
        b[8] = 1;
        b[9] = -15.;
        b[10] = 5.;
        b[11] = -5.;
        b[12] = 1;
        b[13] = -15.;
        b[14] = 0.;
        b[15] = -15.;
        b[16] = 1;

        b[17] = -5.;
        b[18] = 5.;
        b[19] = 15.;
        b[20] = 1;
        b[21] = -5.;
        b[22] = 10.;
        b[23] = 5.;
        b[24] = 1;
        b[25] = -5.;
        b[26] = 10.;
        b[27] = -5.;
        b[28] = 1;
        b[29] = -5.;
        b[30] = 5.;
        b[31] = -15.;
        b[32] = 1;

        b[33] = 5.;
        b[34] = 5.;
        b[35] = 15.;
        b[36] = 1;
        b[37] = 5.;
        b[38] = 10.;
        b[39] = 5.;
        b[40] = 1;
        b[41] = 5.;
        b[42] = 10.;
        b[43] = -5.;
        b[44] = 1;
        b[45] = 5.;
        b[46] = 0.;
        b[47] = -15.;
        b[48] = 1;

        b[49] = 15.;
        b[50] = 0.;
        b[51] = 15.;
        b[52] = 1;
        b[53] = 15.;
        b[54] = 5.;
        b[55] = 5.;
        b[56] = 1;
        b[57] = 15.;
        b[58] = 5.;
        b[59] = -5.;
        b[60] = 1;
        b[61] = 15.;
        b[62] = 0.;
        b[63] = -15.;
        b[64] = 1;

        /*
         Uncomment the file input statement below and comment out the standard
         control net data above to use file input. Also needs to be compiled
         with the file rdrnp.c
         */
        /*  get polygon netpoint file */
        /*    rdrnp(header,&hdrlen,&k,&l,&npts,&mpts,b); */
        rbspsurf(k, l, npts, mpts, p1, p2, b, q);

        /*	Use the next two lines for getting performance timings
         and comment out the line above.
         */
        /*	for (i = 1; i<=1000; i++){ 
         rbspsurf(b,k,l,npts,mpts,p1,p2,q);
         }
         */
        System.out.printf("\nPolygon points\n\n");

        for (i = 0; i < 4 * npts * mpts; i = i + 4) {
            System.out.printf(" %f %f %f %f\n", b[i], b[i + 1], b[i + 2], b[i + 3]);
        }

        System.out.printf("\nSurface points\n\n");

        for (i = 1; i <= 3 * p1 * p2; i = i + 3) {
            System.out.printf("%f, %f, %f \n", q[i], q[i + 1], q[i + 2]);
        }

        /*	To get output in the format of an sgf file
         remove the comment and compile with sgfout.c
         */
        /*
         sgfout(p1,p2,q);
         */
    }

}
