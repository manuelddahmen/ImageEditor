package one.empty3.feature;

import java.util.Arrays;
import java.util.function.Consumer;

import one.empty3.feature.FilterPixM;
import one.empty3.feature.M;
import one.empty3.feature.M3;
import one.empty3.feature.PixM;

/*
 * Multi-Image Matching using Multi-Scale Oriented Patches
 */
public class MIMmops {
    public static one.empty3.feature.PixM applyMultipleFilters(one.empty3.feature.PixM pixM, int level, FilterPixM... filter) {

        final one.empty3.feature.PixM[] res = {pixM};
        for (int i = 0; i < level; i++) {
            // Hl(x, y) = ∇σd Pl(x, y)∇σd Pl(x, y)T∗ gσi(x, y)
            // g      -> Gauss filter
            //  Sigma -> filter size
            //  level -> filter iterations
            // i      -> Iteration value of sigma (end condition?)
            // ∇σd Pl     -> Picture "derivative" (iteration x (gradient(image)))
            //               at level l and at sigma
            // ^T ??? -> Transpose smoothed matrix ?

            // La dérivée et le filtre ne sont pas les mêmes. sommeMatrice(e-..)   et
            // (get(x+1)-2*get(x)+get(x-1) + get(y+1)+2*get(y)-get(y))/4/4 ou /1/1 ??
            Arrays.stream(filter).sequential().forEach(new Consumer<FilterPixM>() {
                @Override
                public void accept(FilterPixM filterPixM) {
                    res[0] = res[0].applyFilter(filterPixM);


                }
            });

        }


        return res[0];
    }


    public one.empty3.feature.M matGrad(PixM image, one.empty3.feature.M3 gradientX, M3 gradientY) {
        one.empty3.feature.M matGrad = null;
        // image :  smoothes
        // image : gradientX M3(w, h, 2, 1)
        // image : gradientY M3(w, h, 1, 2)

        // ?mode radar?

        // image of matrix matGrad. outer product of vectors gradient.


        // image of angles gradient orientation atan(y/x)
        return matGrad;
    }

    /*
     * fHM(x, y) = det Hl(x, y)
     * tr Hl(x, y)
     * =
     * λ1λ2
     * λ1 + λ2
     */
    public void cornerStrength(M matGrad) {

    }
}
