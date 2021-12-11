package one.empty3.atlasgen;

import one.empty3.feature.app.replace.java.awt.Color;
import one.empty3.feature.app.replace.java.awt.Dimension;
import one.empty3.library.ITexture;
import one.empty3.library.Point2D;
import one.empty3.library.core.lighting.Colors;

import java.util.HashMap;
import java.util.Map;

/*__
 * Created by Manuel Dahmen on 29-06-18.
 */
public class DrawOneCountryAction implements Action {
    private final ITexture tex;
    private SetMinMax.MyDim dim;
    private String country;
    private boolean firstPass = false;
    private Map<String, Color> colors = new HashMap<String, Color>();

    private Pixeler pixeler;

    public DrawOneCountryAction(Pixeler pixeler,
                                SetMinMax.MyDim dim,
                                ITexture tex) {
        this.pixeler = pixeler;
        this.dim = dim;
        this.country = dim.getCountryCode();
        this.tex = tex;

    }

    @Override
    public void init() {

    }

    @Override
    public void processLine(CsvLine csvLine) {
        int latitudeColumn = 4;
        int longitudeColumn = 5;
        int countryCodeColumn = 8;
        String[] lineArray = csvLine.getValue();
        String countryCode = lineArray[countryCodeColumn];
        if (country.equals(countryCode)) {
            Color color = colors.computeIfAbsent(countryCode, k -> Colors.random());
            double latitude = Double.parseDouble(lineArray[longitudeColumn]);
            double longitude = -Double.parseDouble(lineArray[latitudeColumn]);
            Dimension dimension = new Dimension(
                    pixeler.getImage().getWidth(),
                    pixeler.getImage().getHeight());
            Point2D p = dim.convert(dimension, longitude, latitude);

            Point2D pR = dim.getRatios(p);
            /*
            System.out.println("p" + Seriald.point2DtoString(p));
            System.out.println("pR" + Seriald.point2DtoString(pR));
            */
            pixeler.pixelize((int) p.x, (int) p.y,
                    (Color) Color.Color(
                            tex.getColorAt(pR.x, pR.y)));

        }
    }
}
