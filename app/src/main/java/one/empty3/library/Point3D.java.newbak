package one.empty3.library;

public class Point3D extends vec {
public Point3D(int n) {
          super(n);
     }
     public Point3D(double x, double y, double z) {
          super(x,y,z);
     }

     public Point3D(double x, double y, double z,
         double t) {
          super(x,y,z, t);
     }


    /*__
     * *
     * Constructeur Point Origine
     */
    /*__
     * *
     *
     * @param x0 coordArr-coordonnée
     * @param y0 y-coordonnée
     * @param z0 z-coordonnée
     */
    public Point3D(Double x0, Double y0, Double z0) {
        super(x0, y0, z0);
        
 }
    /*__
     * *
     *
     * @param x0 coordArr-coordonnée
     * @param y0 y-coordonnée
     * @param z0 z-coordonnée
     */
    public Point3D(Double x0, Double y0, Double z0, ITexture t) {
        super(x0, y0, z0, t);
      
    }

    /*__
     * *
     * Initialise à partir d'un vecteur
     *
     * @param x0 coordonnées (>3)
     */
    public Point3D(Double... x0) {
        super(x0);
    }

    public Point3D(Double[] x0, ITexture t) {
        super(x0, t);
    }
    /*__
     *
     *
     * @param p0 point à copier
     */
    public Point3D(Point3D p0) {
        super(3);
        for(int i=0; i<n; i++)
            set(i, p0.get(i));
    }

    public Point3D(StructureMatrix<Double> coordArr) {
        super(coordArr);
    }

    public static Point3D n(Double a, Double b, Double c) {
        return new Point3D(a, b, c);
    }
}
