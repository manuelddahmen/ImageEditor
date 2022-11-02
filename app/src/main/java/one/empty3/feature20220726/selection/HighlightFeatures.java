package one.empty3.feature20220726.selection;

import one.empty3.feature20220726.PixM;
import one.empty3.io.ProcessFile;
import one.empty3.library.ITexture;
import one.empty3.library.Lumiere;
import one.empty3.library.Point3D;
import one.empty3.library.Scene;
import one.empty3.library.core.nurbs.ParametricCurve;

import javaAnd.awt.image.imageio.ImageIO;
import javaAnd.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HighlightFeatures extends ProcessFile {

    /*
     *
     * @param points Sélection de points
     * @param img Image sur laquelle dessiner
     * @param col Couleur ou texture de dessin
     */
    public void pasteList(List<Point3D> points, PixM img, ITexture col) {

        for (int i = 0; i < points.size(); i++) {

            int x = (int) (double) points.get(i).getX();
            int y = (int) (double) points.get(i).getY();

            int rgb = col.getColorAt(
                    points.get(i).getX() / img.getColumns(),
                    points.get(i).getY() / img.getLines());

            double[] rgbD = Lumiere.getDoubles(rgb);
            for (int i1 = 0; i1 < 3; i1++) {
                img.setCompNo(i1);
                img.set(x, y, rgbD[i1]);
            }

        }

    }

    /*
     *
     * @param points Sélection de points
     * @param img Image sur laquelle dessiner
     * @param objets Objets à dessiner sur l'image (3d-2d)
     */
    public void pasteList(List<Point3D> points, PixM img, Scene objets) {

        for (int i = 0; i < points.size(); i++) {

            int x = (int) (double) points.get(i).getX();
            int y = (int) (double) points.get(i).getY();

            objets.getObjets().getData1d().forEach(representable -> {
                if (representable instanceof ParametricCurve)
                    img.plotCurve((ParametricCurve) representable, representable.texture());///Clip curve for moving, rotating, scaling
                else if (representable instanceof Point3D) {
                    img.setValues((int) (double) ((Point3D) representable).getX(),
                            (int) (double) ((Point3D) representable).getY(),
                            Lumiere.getDoubles(representable.texture().getColorAt(0.5, 0.5)));
                }
            });

        }

    }

    public void openCsv() {

    }

    public void writeCsv(String filename, int xPercent, int yPercent, double... vector) {
    /*
        Misérables oeufs mignolet

        Te voilà jeté en freestyle
        Comme un steak à l 'ail
        J'aime les vaches
        et les viandes
        J'aime les femmes
        Et les comptes
        En banques que
        Je découpe tous
        à la hache
        Ma kekette
        Et mes boulets
        Au bouquet
        Des bollets
        Dans l'bosquet
        Des balais
        Déblayent
        Les lendemains
        De la fête
        A saints de putains
        Qui mont'ses hein
        Dans le purin
        Police politik
        Potiche pissin'
        Ca c'est drôle
        Drole de drame
        Dans l'émoi
        Des piques
        De Véronique
        La putain du diable
        Ses vérolaises
        Ah que
        Hackerdôme
        Dos au mur
        Eh c'est sûr
        Et sertis
        De pistolet
        Un anchois
        En chocolat
    */
    }

    public void closenCsv() {

    }

    @Override
    public boolean process(File in, File out) {
        BufferedImage read = ImageIO.read(in);
        PixM pixM = PixM.getPixM(read.bitmap);
        File stackItem = getStackItem(1);
        PixM original = new PixM(ImageIO.read(stackItem));
        int cadre = (int) Math.min((pixM.getColumns() + pixM.getLines()) / 2., 10.);
        for (int i = 0; i < pixM.getColumns(); i++)
            for (int j = 0; j < pixM.getLines(); j++) {
                double luminance = pixM.luminance(i, j);
                if (luminance > 0.1) {
                    for (int x = i - cadre; x < i + cadre; x++)
                        for (int y = j - cadre; y < j + cadre; y++) {
                            if ((x == i - cadre) || (y == j - cadre) || x == i + cadre - 1 || y == j + cadre - 1)
                                original.setValues(x, y, 1, 1, 0);
                        }
                }
            }
        try {
            ImageIO.write(original.normalize(0., 1., 0., 1.).getImage(), "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
