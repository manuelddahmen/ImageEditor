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

package one.empty3.library;

import java.util.List;
import java.util.function.Consumer;

/*__
 * Created by manue on 03-11-19.
 */
public class ArcBall {
    Representable representable;

    public int glhProjectf(float objx, float objy, float objz, float[] modelview, float[] projection, int[] viewport, Point3D a) {
        // Transformation vectors
        float fTempo[] = new float[8];
        // Modelview transform
        fTempo[0] = modelview[0] * objx + modelview[4] * objy + modelview[8] * objz + modelview[12]; // w is always 1
        fTempo[1] = modelview[1] * objx + modelview[5] * objy + modelview[9] * objz + modelview[13];
        fTempo[2] = modelview[2] * objx + modelview[6] * objy + modelview[10] * objz + modelview[14];
        fTempo[3] = modelview[3] * objx + modelview[7] * objy + modelview[11] * objz + modelview[15];
        // Projection transform, the final row of projection matrix is always [0 0 -1 0]
        // so we optimize for that.
        fTempo[4] = projection[0] * fTempo[0] + projection[4] * fTempo[1] + projection[8] * fTempo[2] + projection[12] * fTempo[3];
        fTempo[5] = projection[1] * fTempo[0] + projection[5] * fTempo[1] + projection[9] * fTempo[2] + projection[13] * fTempo[3];
        fTempo[6] = projection[2] * fTempo[0] + projection[6] * fTempo[1] + projection[10] * fTempo[2] + projection[14] * fTempo[3];
        fTempo[7] = -fTempo[2];
        // The result normalizes between -1 and 1
        if (fTempo[7] == 0.0) // The w value
            return 0;
        fTempo[7] = 1.0f / fTempo[7];
        // Perspective division
        fTempo[4] *= fTempo[7];
        fTempo[5] *= fTempo[7];
        fTempo[6] *= fTempo[7];
        // Window coordinates
        // Map x, y to range 0-1
        a.set(0, (double) (fTempo[4] * 0.5f + 0.5f) * viewport[2] + viewport[0]);
        a.set(1, (double) (fTempo[5] * 0.5f + 0.5f) * viewport[3] + viewport[1]);
        // This is only correct when glDepthRange(0.0, 1.0)
        a.set(2, (double) (1.0f + fTempo[6]) * 0.5f);    // Between 0 and 1
        return 1;
    }

    public int glhUnProjectf(float winx, float winy, float winz, float[] modelview, float[] projection, int[] viewport, float[] objectCoordinate) {
        // Transformation matrices
        float m[] = new float[16], A[] = new float[16];
        float in[] = new float[4], out[] = new float[4];
        // Calculation for inverting a matrix, compute projection x modelview
        // and store in A[16]
        MultiplyMatrices4by4OpenGL_FLOAT(A, projection, modelview);
        // Now compute the inverse of matrix A
        if (glhInvertMatrixf2(A, m) == 0)
            return 0;
        // Transformation of normalized coordinates between -1 and 1
        in[0] = (winx - (float) viewport[0]) / (float) viewport[2] * 2.0f - 1.0f;
        in[1] = (winy - (float) viewport[1]) / (float) viewport[3] * 2.0f - 1.0f;
        in[2] = 2.0f * winz - 1.0f;
        in[3] = 1.0f;
        // Objects coordinates
        MultiplyMatrixByVector4by4OpenGL_FLOAT(out, m, in);
        if (out[3] == 0.0)
            return 0;
        out[3] = 1.0f / out[3];
        objectCoordinate[0] = out[0] * out[3];
        objectCoordinate[1] = out[1] * out[3];
        objectCoordinate[2] = out[2] * out[3];
        return 1;
    }

    public void MultiplyMatrices4by4OpenGL_FLOAT(float[] result, float[] matrix1, float[] matrix2) {
        result[0] = matrix1[0] * matrix2[0] +
                matrix1[4] * matrix2[1] +
                matrix1[8] * matrix2[2] +
                matrix1[12] * matrix2[3];
        result[4] = matrix1[0] * matrix2[4] +
                matrix1[4] * matrix2[5] +
                matrix1[8] * matrix2[6] +
                matrix1[12] * matrix2[7];
        result[8] = matrix1[0] * matrix2[8] +
                matrix1[4] * matrix2[9] +
                matrix1[8] * matrix2[10] +
                matrix1[12] * matrix2[11];
        result[12] = matrix1[0] * matrix2[12] +
                matrix1[4] * matrix2[13] +
                matrix1[8] * matrix2[14] +
                matrix1[12] * matrix2[15];
        result[1] = matrix1[1] * matrix2[0] +
                matrix1[5] * matrix2[1] +
                matrix1[9] * matrix2[2] +
                matrix1[13] * matrix2[3];
        result[5] = matrix1[1] * matrix2[4] +
                matrix1[5] * matrix2[5] +
                matrix1[9] * matrix2[6] +
                matrix1[13] * matrix2[7];
        result[9] = matrix1[1] * matrix2[8] +
                matrix1[5] * matrix2[9] +
                matrix1[9] * matrix2[10] +
                matrix1[13] * matrix2[11];
        result[13] = matrix1[1] * matrix2[12] +
                matrix1[5] * matrix2[13] +
                matrix1[9] * matrix2[14] +
                matrix1[13] * matrix2[15];
        result[2] = matrix1[2] * matrix2[0] +
                matrix1[6] * matrix2[1] +
                matrix1[10] * matrix2[2] +
                matrix1[14] * matrix2[3];
        result[6] = matrix1[2] * matrix2[4] +
                matrix1[6] * matrix2[5] +
                matrix1[10] * matrix2[6] +
                matrix1[14] * matrix2[7];
        result[10] = matrix1[2] * matrix2[8] +
                matrix1[6] * matrix2[9] +
                matrix1[10] * matrix2[10] +
                matrix1[14] * matrix2[11];
        result[14] = matrix1[2] * matrix2[12] +
                matrix1[6] * matrix2[13] +
                matrix1[10] * matrix2[14] +
                matrix1[14] * matrix2[15];
        result[3] = matrix1[3] * matrix2[0] +
                matrix1[7] * matrix2[1] +
                matrix1[11] * matrix2[2] +
                matrix1[15] * matrix2[3];
        result[7] = matrix1[3] * matrix2[4] +
                matrix1[7] * matrix2[5] +
                matrix1[11] * matrix2[6] +
                matrix1[15] * matrix2[7];
        result[11] = matrix1[3] * matrix2[8] +
                matrix1[7] * matrix2[9] +
                matrix1[11] * matrix2[10] +
                matrix1[15] * matrix2[11];
        result[15] = matrix1[3] * matrix2[12] +
                matrix1[7] * matrix2[13] +
                matrix1[11] * matrix2[14] +
                matrix1[15] * matrix2[15];
    }


    public void MultiplyMatrixByVector4by4OpenGL_FLOAT(float[] resultvector, float[] matrix, float[] pvector) {
        resultvector[0] = matrix[0] * pvector[0] + matrix[4] * pvector[1] + matrix[8] * pvector[2] + matrix[12] * pvector[3];
        resultvector[1] = matrix[1] * pvector[0] + matrix[5] * pvector[1] + matrix[9] * pvector[2] + matrix[13] * pvector[3];
        resultvector[2] = matrix[2] * pvector[0] + matrix[6] * pvector[1] + matrix[10] * pvector[2] + matrix[14] * pvector[3];
        resultvector[3] = matrix[3] * pvector[0] + matrix[7] * pvector[1] + matrix[11] * pvector[2] + matrix[15] * pvector[3];
    }

    public void SWAP_ROWS_DOUBLE(double[] a, double[] b) {
        double[] _tmp = a;
        (a) = (b);
        (b) = _tmp;
    }

    public void SWAP_ROWS_FLOAT(float[] a, float[] b) {
        float[] _tmp = a;
        (a) = (b);
        (b) = _tmp;
    }

    public float MAT0(float[] m, int r, int c) {
        return (m)[(c) * 4 + (r)];
    }

    public void MAT(float[] m, int r, int c, float value) {
        (m)[(c) * 4 + (r)] = value;
    }


    // This code comes directly from GLU except that it is for float
    public int glhInvertMatrixf2(float[] m, float[] out) {
        float wtmp[][] = new float[4][8];
        float m0, m1, m2, m3, s;
        float[] r0;
        float[] r1;
        float[] r2;
        float[] r3;
        r0 = wtmp[0];
        r1 = wtmp[1];
        r2 = wtmp[2];
        r3 = wtmp[3];
        r0[0] = MAT0(m, 0, 0);
        r0[1] = MAT0(m, 0, 1);
        r0[2] = MAT0(m, 0, 2);
        r0[3] = MAT0(m, 0, 3);
        r0[4] = 1.0f;
        r0[5] = r0[6] = r0[7] = 0.0f;
        r1[0] = MAT0(m, 1, 0);
        r1[1] = MAT0(m, 1, 1);
        r1[2] = MAT0(m, 1, 2);
        r1[3] = MAT0(m, 1, 3);
        r1[5] = 1.0f;
        r1[4] = r1[6] = r1[7] = 0.0f;
        r2[0] = MAT0(m, 2, 0);
        r2[1] = MAT0(m, 2, 1);
        r2[2] = MAT0(m, 2, 2);
        r2[3] = MAT0(m, 2, 3);
        r2[6] = 1.0f;
        r2[4] = r2[5] = r2[7] = 0.0f;
        r3[0] = MAT0(m, 3, 0);
        r3[1] = MAT0(m, 3, 1);
        r3[2] = MAT0(m, 3, 2);
        r3[3] = MAT0(m, 3, 3);
        r3[7] = 1.0f;
        r3[4] = r3[5] = r3[6] = 0.0f;
        /* choose pivot - or die */
        if (Math.abs(r3[0]) > Math.abs(r2[0]))
            SWAP_ROWS_FLOAT(r3, r2);
        if (Math.abs(r2[0]) > Math.abs(r1[0]))
            SWAP_ROWS_FLOAT(r2, r1);
        if (Math.abs(r1[0]) > Math.abs(r0[0]))
            SWAP_ROWS_FLOAT(r1, r0);
        if (0.0 == r0[0])
            return 0;
        /* eliminate first variable */
        m1 = r1[0] / r0[0];
        m2 = r2[0] / r0[0];
        m3 = r3[0] / r0[0];
        s = r0[1];
        r1[1] -= m1 * s;
        r2[1] -= m2 * s;
        r3[1] -= m3 * s;
        s = r0[2];
        r1[2] -= m1 * s;
        r2[2] -= m2 * s;
        r3[2] -= m3 * s;
        s = r0[3];
        r1[3] -= m1 * s;
        r2[3] -= m2 * s;
        r3[3] -= m3 * s;
        s = r0[4];
        if (s != 0.0) {
            r1[4] -= m1 * s;
            r2[4] -= m2 * s;
            r3[4] -= m3 * s;
        }
        s = r0[5];
        if (s != 0.0) {
            r1[5] -= m1 * s;
            r2[5] -= m2 * s;
            r3[5] -= m3 * s;
        }
        s = r0[6];
        if (s != 0.0) {
            r1[6] -= m1 * s;
            r2[6] -= m2 * s;
            r3[6] -= m3 * s;
        }
        s = r0[7];
        if (s != 0.0) {
            r1[7] -= m1 * s;
            r2[7] -= m2 * s;
            r3[7] -= m3 * s;
        }
        /* choose pivot - or die */
        if (Math.abs(r3[1]) > Math.abs(r2[1]))
            SWAP_ROWS_FLOAT(r3, r2);
        if (Math.abs(r2[1]) > Math.abs(r1[1]))
            SWAP_ROWS_FLOAT(r2, r1);
        if (0.0 == r1[1])
            return 0;
        /* eliminate second variable */
        m2 = r2[1] / r1[1];
        m3 = r3[1] / r1[1];
        r2[2] -= m2 * r1[2];
        r3[2] -= m3 * r1[2];
        r2[3] -= m2 * r1[3];
        r3[3] -= m3 * r1[3];
        s = r1[4];
        if (0.0 != s) {
            r2[4] -= m2 * s;
            r3[4] -= m3 * s;
        }
        s = r1[5];
        if (0.0 != s) {
            r2[5] -= m2 * s;
            r3[5] -= m3 * s;
        }
        s = r1[6];
        if (0.0 != s) {
            r2[6] -= m2 * s;
            r3[6] -= m3 * s;
        }
        s = r1[7];
        if (0.0 != s) {
            r2[7] -= m2 * s;
            r3[7] -= m3 * s;
        }
        /* choose pivot - or die */
        if (Math.abs(r3[2]) > Math.abs(r2[2]))
            SWAP_ROWS_FLOAT(r3, r2);
        if (0.0 == r2[2])
            return 0;
        /* eliminate third variable */
        m3 = r3[2] / r2[2];
        r3[3] -= m3 * r2[3];
        r3[4] -= m3 * r2[4];
        r3[5] -= m3 * r2[5];
        r3[6] -= m3 * r2[6];
        r3[7] -= m3 * r2[7];
        /* last check */
        if (0.0 == r3[3])
            return 0;
        s = 1.0f / r3[3];        /* now back substitute row 3 */
        r3[4] *= s;
        r3[5] *= s;
        r3[6] *= s;
        r3[7] *= s;
        m2 = r2[3];            /* now back substitute row 2 */
        s = 1.0f / r2[2];
        r2[4] = s * (r2[4] - r3[4] * m2);
        r2[5] = s * (r2[5] - r3[5] * m2);
        r2[6] = s * (r2[6] - r3[6] * m2);
        r2[7] = s * (r2[7] - r3[7] * m2);
        m1 = r1[3];
        r1[4] -= r3[4] * m1;
        r1[5] -= r3[5] * m1;
        r1[6] -= r3[6] * m1;
        r1[7] -= r3[7] * m1;
        m0 = r0[3];
        r0[4] -= r3[4] * m0;
        r0[5] -= r3[5] * m0;
        r0[6] -= r3[6] * m0;
        r0[7] -= r3[7] * m0;
        m1 = r1[2];            /* now back substitute row 1 */
        s = 1.0f / r1[1];
        r1[4] = s * (r1[4] - r2[4] * m1);
        r1[5] = s * (r1[5] - r2[5] * m1);
        r1[6] = s * (r1[6] - r2[6] * m1);
        r1[7] = s * (r1[7] - r2[7] * m1);
        m0 = r0[2];
        r0[4] -= r2[4] * m0;
        r0[5] -= r2[5] * m0;
        r0[6] -= r2[6] * m0;
        r0[7] -= r2[7] * m0;
        m0 = r0[1];            /* now back substitute row 0 */
        s = 1.0f / r0[0];
        r0[4] = s * (r0[4] - r1[4] * m0);
        r0[5] = s * (r0[5] - r1[5] * m0);
        r0[6] = s * (r0[6] - r1[6] * m0);
        r0[7] = s * (r0[7] - r1[7] * m0);
        MAT(out, 0, 0, r0[4]);
        MAT(out, 0, 1, r0[5]);
        MAT(out, 0, 2, r0[6]);
        MAT(out, 0, 3, r0[7]);
        MAT(out, 1, 0, r1[4]);
        MAT(out, 1, 1, r1[5]);
        MAT(out, 1, 2, r1[6]);
        MAT(out, 1, 3, r1[7]);
        MAT(out, 2, 0, r2[4]);
        MAT(out, 2, 1, r2[5]);
        MAT(out, 2, 2, r2[6]);
        MAT(out, 2, 3, r2[7]);
        MAT(out, 3, 0, r3[4]);
        MAT(out, 3, 1, r3[5]);
        MAT(out, 3, 2, r3[6]);
        MAT(out, 3, 3, r3[7]);
        return 1;
    }

    Double ab_quat[] = {1., 0., 0., 0., 0., 1., 0., 0., 0., 0., 1., 0., 0., 0., 0., 1.};
    Double ab_last[] = {1., 0., 0., 0., 0., 1., 0., 0., 0., 0., 1., 0., 0., 0., 0., 1.};
    Double ab_next[] = {1., 0., 0., 0., 0., 1., 0., 0., 0., 0., 1., 0., 0., 0., 0., 1.};

    // the distance from the origin to the eye
    Double ab_zoom = 1.0;
    Double ab_zoom2 = 1.0;
    // the radius of the arcball
    Double ab_sphere = 10.0;
    Double ab_sphere2 = 10.0;
    // the distance from the origin of the plane that intersects
// the edge of the visible sphere (tangent to a ray from the eye)
    Double ab_edge = 1.0;
    // whether we are using a sphere or plane
    Boolean ab_planar = false;
    Double ab_planedist = 0.5;

    Point3D ab_start = new Point3D(0., 0., 1.);
    Point3D ab_curr = new Point3D(0., 0., 1.);
    Point3D ab_eye = new Point3D(0., 0., 1.);
    Point3D ab_eyedir = new Point3D(0., 0., 1.);
    Point3D ab_up = new Point3D(0., 1., 0.);
    Point3D ab_out = new Point3D(1., 0., 0.);

    float ab_glp[] = {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1};
    float ab_glm[] = {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1};
    int ab_glv[] = {0, 0, 640, 480};

    public void arcball_setzoom(double radius, Point3D eye, Point3D up) {
        ab_eye = eye; // store eye vector
        ab_zoom2 = ab_eye.prodScalaire(ab_eye);
        ab_zoom = Math.sqrt(ab_zoom2); // store eye distance
        ab_sphere = radius; // sphere radius
        ab_sphere2 = ab_sphere * ab_sphere;
        ab_eyedir = ab_eye.mult(1.0 / ab_zoom); // distance to eye
        ab_edge = ab_sphere2 / ab_zoom; // plane of visible edge

        if (ab_sphere <= 0.0) // trackball mode
        {
            ab_planar = true;
            ab_up = up;
            ab_out = (ab_eyedir.prodVect(ab_up));
            ab_planedist = (0.0 - ab_sphere) * ab_zoom;
        } else
            ab_planar = false;

    }


    // convert the quaternion into a rotation matrix
    public static void quaternion(Double[] q, Double x, Double y, Double z, Double w) {
        Double x2 = x * x;
        Double y2 = y * y;
        Double z2 = z * z;
        Double xy = x * y;
        Double xz = x * z;
        Double yz = y * z;
        Double wx = w * x;
        Double wy = w * y;
        Double wz = w * z;

        q[0] = 1 - 2 * y2 - 2 * z2;
        q[1] = 2 * xy + 2 * wz;
        q[2] = 2 * xz - 2 * wy;

        q[4] = 2 * xy - 2 * wz;
        q[5] = 1 - 2 * x2 - 2 * z2;
        q[6] = 2 * yz + 2 * wx;

        q[8] = 2 * xz + 2 * wy;
        q[9] = 2 * yz - 2 * wx;
        q[10] = 1 - 2 * x2 - 2 * y2;
    }

    // reset the rotation matrix
    public static void quatidentity(Double[] q) {
        q[0] = 1.;
        q[1] = 0.;
        q[2] = 0.;
        q[3] = 0.;
        q[4] = 0.;
        q[5] = 1.;
        q[6] = 0.;
        q[7] = 0.;
        q[8] = 0.;
        q[9] = 0.;
        q[10] = 1.;
        q[11] = 0.;
        q[12] = 0.;
        q[13] = 0.;
        q[14] = 0.;
        q[15] = 1.;
    }

    // copy a rotation matrix
    public static void quatcopy(Double[] dst, Double[] src) {
        dst[0] = src[0];
        dst[1] = src[1];
        dst[2] = src[2];
        dst[4] = src[4];
        dst[5] = src[5];
        dst[6] = src[6];
        dst[8] = src[8];
        dst[9] = src[9];
        dst[10] = src[10];
    }

    // multiply two rotation matrices
    public static void quatnext(Double[] dest, Double[] left, Double[] right) {
        dest[0] = left[0] * right[0] + left[1] * right[4] + left[2] * right[8];
        dest[1] = left[0] * right[1] + left[1] * right[5] + left[2] * right[9];
        dest[2] = left[0] * right[2] + left[1] * right[6] + left[2] * right[10];
        dest[4] = left[4] * right[0] + left[5] * right[4] + left[6] * right[8];
        dest[5] = left[4] * right[1] + left[5] * right[5] + left[6] * right[9];
        dest[6] = left[4] * right[2] + left[5] * right[6] + left[6] * right[10];
        dest[8] = left[8] * right[0] + left[9] * right[4] + left[10] * right[8];
        dest[9] = left[8] * right[1] + left[9] * right[5] + left[10] * right[9];
        dest[10] = left[8] * right[2] + left[9] * right[6] + left[10] * right[10];
    }

    // find the intersection with the plane through the visible edge
    public Point3D edge_coords(Point3D m) {
        // find the intersection of the edge plane and the ray
        Double t = (ab_edge - ab_zoom) / (ab_eyedir.prodScalaire(m));
        Point3D a = ab_eye.plus(m.mult(t));
        // find the direction of the eye-axis from that point
        // along the edge plane
        Point3D c = (ab_eyedir.mult(ab_edge).moins(a));

        // find the intersection of the sphere with the ray going from
        // the plane outside the sphere toward the eye-axis.
        Double ac = a.prodScalaire(c);
        Double c2 = (c.prodScalaire(c));
        Double q = (0.0 - ac - Math.sqrt(ac * ac - c2 * ((a.prodScalaire(a) - ab_sphere2))) / c2);

        return (a.plus(c.mult(q)).norme1());
    }

    // find the intersection with the sphere
    public Point3D sphere_coords(float mx, float my) {
        Point3D a = new Point3D();

        gluUnProject(mx, my, 0, ab_glm, ab_glp, ab_glv, a);
        Point3D m = a.moins(ab_eye);

        // mouse position represents ray: eye + t*m
        // intersecting with a sphere centered at the origin
        Double a2 = m.prodScalaire(m);
        Double b = (ab_eye.prodScalaire(m));
        Double root = (b * b) - a2 * (ab_zoom2 - ab_sphere2);
        if (root <= 0) return edge_coords(m);
        Double t = (0.0 - b - Math.sqrt(root)) / a2;
        return (ab_eye.plus((m.mult(t))).norme1());
    }

    // get intersection with plane for "trackball" style rotation
    public Point3D planar_coords(float mx, float my) {
        Point3D a = new Point3D();

        gluUnProject(mx, my, 0, ab_glm, ab_glp, ab_glv, a);
        Point3D m = a.moins(ab_eye);
        // intersect the point with the trackball plane
        Double t = (ab_planedist - ab_zoom) / (ab_eyedir.prodScalaire(m));
        Point3D d = ab_eye.plus(m.mult(t));

        return new Point3D(d.prodScalaire(ab_up), d.prodScalaire(ab_out), 0.0);
    }

    public int gluUnProject(float mx, float my, int mz, float[] ab_glm, float[] ab_glp, int[] ab_glv, Point3D a) {
        float x = (float) (double) a.getX();
        float y = (float) (double) a.getY();
        float z = (float) (double) a.getZ();
        float[] floats = {x, y, z};
        int i = glhUnProjectf(mx, my, mz, ab_glm, ab_glp, ab_glv, floats);
        a.set(0, (double) floats[0]);
        a.set(1, (double) floats[1]);
        a.set(2, (double) floats[2]);
        return i;
    }

    // reset the arcball
    public void arcball_init(Representable representable) {
        this.representable = representable;
        if (representable != null) {
            List<Double> d = representable.getRotation().getElem().getRot().getElem().getD().data1d;
            int size = d.size();
            System.out.println("List size rot = " + size);
            Double[] a = new Double[12];
            int[] i = new int[]{0};
            d.forEach(new Consumer<Double>() {
                @Override
                public void accept(Double aDouble) {
                    a[i[0]] = aDouble;
                    i[0]++;
                }
            });
            if (size == 9) {
                a[9] = 0.0;
                a[10] = 0.0;
                a[11] = 1.0;
            }
            quatcopy(ab_quat, a);
        }
    }

    // reset the arcball
    public void arcball_reset(Representable representable) {
        if (representable != null) {
            arcball_init(representable);
            quatidentity(ab_quat);
            quatidentity(ab_last);
        }
    }

    // begin arcball rotation
    public void arcball_start(int mx, int my) {
        if (representable != null) {
// saves a copy of the current rotation for comparison
            quatcopy(ab_last, ab_quat);
            if (ab_planar) ab_start = planar_coords(mx, my);
            else ab_start = sphere_coords(mx, my);
        }
    }

    // update current arcball rotation
    public void arcball_move(int mx, int my) {
        if (ab_planar) {
            ab_curr = planar_coords(mx, my);
            if (ab_curr.equals(ab_start)) return;

            // d is motion since the last position
            Point3D d = ab_curr.moins(ab_start);

            Double angle = 3 * 0.5;
            Double cosa = Math.cos(angle);
            Double sina = Math.sin(angle);
            // p is perpendicular to d
            Point3D p = ((ab_out.mult(d.getX())).moins(ab_up.mult(d.getY()))).norme1().mult(sina);

            quaternion(ab_next, p.getX(), p.getY(), p.getZ(), cosa);
            quatnext(ab_quat, ab_last, ab_next);
            // planar style only ever relates to the last point
            quatcopy(ab_last, ab_quat);
            ab_start = ab_curr;

        } else {

            ab_curr = sphere_coords(mx, my);
            if (ab_curr.equals(ab_start)) { // avoid potential rare divide by tiny
                quatcopy(ab_quat, ab_last);
                return;
            }

            // use a dot product to get the angle between them
            // use a cross product to get the vector to rotate around
            Double cos2a = ab_start.prodScalaire(ab_curr);
            Double sina = Math.sqrt((1.0 - cos2a) * 0.5);
            Double cosa = Math.sqrt((1.0 + cos2a) * 0.5);
            Point3D cross = (ab_start.prodVect(ab_curr)).norme1().mult(sina);
            quaternion(ab_next, cross.getX(), cross.getY(), cross.getZ(), cosa);

            // update the rotation matrix
            quatnext(ab_quat, ab_last, ab_next);
            ab_start = ab_curr;
        }

        representable.getRotation().getElem().getRot().getElem().setD(ab_last);
    }

}
