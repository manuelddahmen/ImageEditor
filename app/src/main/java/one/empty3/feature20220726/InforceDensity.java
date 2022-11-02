package one.empty3.feature20220726;


/*
 * Prendre pour points d'intérêt les zones denses en intensité
 * Prendre les mesures entre elles et avec les zones moins denses
 *
 */
public class InforceDensity {
    private double rarityRatio = 0.0;

    public InforceDensity(PixM m, double rarityRatio) {
        this.rarityRatio = rarityRatio;
        Histogram h = new Histogram(m, 10, 0.3, m.getColumns() / 10, 0.1);
        // circle r grands I faible
        // circle r petits ou grands. I haute. 

    }
}
