package one.empty3.feature;

public class LocalPattern extends FilterMatPixM {
    private M3 sr;
    public static String formulaXvLogical = "count(x==v)==columns()*lines()/2";

    /*
   
        Ligne 1 searchPattern value = x [0,1] 
           - 1 : ignore 
        Ligne 2 replacePattern value x' [0,1] 
        comp4 => rgb' = rgb *(1-opacity) si x ! =-1
       
    */
    public LocalPattern(M3 searchReplace) {
        // super(searchReplace.columns, searchReplace.lines, 1,1) ;
        this.sr = searchReplace;

    }

    /***
     */
    public M3 filter(

            M3 original
    ) {
        M3 copy = new M3(original.columns, original.lines,

                1, 1);

        for (int i = 0; i < original.columns; i++) {

            for (int j = 0; j < original.lines; j++) {

                //copy.setCompNo(c);

                //boolean isMaximum = true;
                double v = 0.;
                double maxLocal = original.getIntensity(i, j, 0, 0);
//v = maxLocal;
                int countOut = 0;

                int countIn = 0;

                for (int s = 0; s < sr.columnsIn; s++) {


                    for (int ii = -sr.columns / 2; ii <= sr.columns; ii++) {

                        for (int ij = -sr.lines / 2; ij <= sr.lines / 2; ij++) {

                            v = original.get(i + ii, j + ij, 0, 0);

                            if (sr.get(ii + sr.columns / 2, ij + sr.lines / 2, 0, s)
                                    == v) {

                                countIn++;

                            }

                        }

                    }


                    if (countIn == sr.lines * sr.columns / 2.0) {

                        copy.setCompNo(0);

                        copy.set(i, j, 0, 0, 1.0);//1 au lieu value

                        copy.setCompNo(1);

                        copy.set(i, j, 0, 0, 1.0);//1 au lieu value

                        copy.setCompNo(2);

                        copy.set(i, j, 0, 0, 1.0);//1 au lieu value
                    }
                }

            }

        }

        return copy;


    }


    @Override
    public void element(M3 source, M3 copy, int i, int j, int ii, int ij) {

    }

    @Override
    public M3 norm(M3 m3, M3 copy) {
        return m3.copy();
    }
} 
