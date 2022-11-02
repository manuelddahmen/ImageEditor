package one.empty3.feature20220726.motion;

import android.graphics.Color;

import one.empty3.feature20220726.FeatureMatch;
import one.empty3.feature20220726.PixM;
import one.empty3.library.Lumiere;

import javaAnd.awt.*;
import javaAnd.awt.image.BufferedImage;

import java.util.List;

public class FeatureMotion extends Motion {
    @Override
    public javaAnd.awt.image.BufferedImage process(PixM frame1, PixM frame2) {
        FeatureMatch featureMatch = new FeatureMatch();

        List<double[]> match = featureMatch.match(frame1, frame2);

        BufferedImage bufferedImage = new javaAnd.awt.image.BufferedImage(frame1.getColumns(), frame1.getLines(), javaAnd.awt.image.BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < frame1.getColumns(); i++) {
            for (int j = 0; j < frame1.getLines(); j++) {
                for (int c = 0; c < frame1.getCompCount(); c++) {
                    bufferedImage.setRGB(i, j, Lumiere.getInt(frame1.getValues(i, j)));
                }
            }
        }
        for (int i = 0; i < match.size(); i++) {
            bufferedImage.setRGB((int) match.get(i)[0], (int) match.get(i)[1], Color.WHITE);
        }
        return bufferedImage;
    }
}
