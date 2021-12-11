package one.empty3.atlasgen;

import one.empty3.feature.app.replace.java.awt.Color;
import one.empty3.library.core.lighting.Colors;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
/*__
 * Created by Manuel Dahmen on 29-06-18.
 */
public class DrawPerCountryAction implements Action {
    private final boolean firstPass = false;
    private final Map<String, Color> colors = new HashMap<String, Color>();

    private final Pixeler pixeler;

    public DrawPerCountryAction(Pixeler pixeler) {
        this.pixeler = pixeler;
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
        colors.computeIfAbsent(countryCode, k -> Colors.random());

        pixeler.pixelize(
                (int) ((Double.parseDouble(lineArray[longitudeColumn]) / 180 + 1) / 2 * pixeler.getImage().getWidth()),
                (int) ((-Double.parseDouble(lineArray[latitudeColumn]) / 90 + 1) / 2 * pixeler.getImage().getHeight()),
                Objects.requireNonNull(colors.get(countryCode))
        );
    }
}
