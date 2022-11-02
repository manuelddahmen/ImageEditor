/*
 * Copyright (c) 2016. Tous les fichiers dans ce programme sont soumis à la License Publique Générale GNU créée par la Free Softxware Association, Boston.
 * La plupart des licenses de parties tièrces sont compatibles avec la license principale.
 * Les parties tierces peuvent être soumises à d'autres licenses.
 * Montemedia : Creative Commons
 * ECT : Tests à valeur artistique ou technique.
 * La partie RayTacer a été honteusement copiée sur le Net. Puis traduite en Java et améliorée.
 * Java est une marque de la société Oracle.
 *
 * Pour le moment le programme est entièrement accessible sans frais supplémentaire. Get the sources, build it, use it, like it, share it.
 */
/*
package one.empty3.library.core.physics;

import one.empty3.library.Point3D;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ListingTableau {
    private Move f;

    public static void main(String[] args) {
        OutputStream out = null;
        try {
            out = new FileOutputStream("listing-positions-1.txt");
        } catch (FileNotFoundException e) {
            System.exit(1);
        }

        Move f = new Move();


        ArrayList billes = billeConfig();

        f.configurer(billes);

        int frameMax = Integer.parseInt(args[0]);

        int frame = 1;
        while (frame < frameMax) {
            for (int i = 0; i < billes.size(); i++) {
                try {
                    out.write((
                            "" + frame
                                    + "\t" + frameMax +
                                    "\t" + i + "\t"
                                    + billes[i].position.get(0) + "\t"
                                    + billes[i].position.get(1) + "\t"
                                    + billes[i].position.get(2) + "\t"
                                    + "\n"
                    ).getBytes());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            f.calculer();

            System.out.println(frame);

            frame++;

        }
    }

    private static ArrayList<Bille> billeConfig() {
        int X, Y, Z;
        X = Y = Z = 5;
        ArrayList<Bille> billes = new ArrayList<Bille>(X * Y * Z);
        for(int i=0; i<X*Y*Z; i++)
            billes.add(new Bille());
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {
                for (int k = 0; k < Z; k++) {

                    billes.get(k * Y * X + j * X + i).position = new Point3D(
                            (i - X / 2) / 1f, (j - Y / 2) / 1f,
                            (k - Z / 2) / 1f);
                    billes.get(k * Y * X + j * X + i).vitesse = new Point3D(
                            (i - X / 2) / 1f, (j - Y / 2) / 1f,
                            (k - Z / 2) / 1f);
                    billes.get(k * Y * X + j * X + i).color = Color.valueOf(1.0f * i
                            / X, 1.0f * j / Y, 1.0f * k / Z);
                    billes.get(k * Y * X + j * X + i).masse = 1;
                    billes.get(k * Y * X + j * X + i).attraction = 1;
                    billes.get(k * Y * X + j * X + i).repulsion = 0.1;
                    billes.get(k * Y * X + j * X + i).amortissement = 0.2;
                }
            }
        }
        return billes;
    }

    public Move getForce() {
        return this.f;
    }

    public void setForce(Move f) {
        this.f = f;
    }
}*/