package one.empty3.feature_arr_style;

import one.empty3.library.Representable;

public class FeatureDetector {
    Representable wanted;

    public Representable getWanted() {
        return wanted;
    }

    public FeatureDetector(Representable r, FeatureDescriptor feature) {
        this.wanted = r;
    }

    public void detect(Region region) {

    }
}
