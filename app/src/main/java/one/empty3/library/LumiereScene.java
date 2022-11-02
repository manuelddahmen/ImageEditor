/*
 *  This file is part of Empty3.
 *
 *     Empty3 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Empty3 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
 */

/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

/*

 Vous êtes libre de :

 */
package one.empty3.library;

/*__
 * *
 * <p>
 * PACKAGE be.ibiiztera.md.pmatrix.pushmatrix OWNER DAHMEN MANUEL
 */


import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class LumiereScene {/*
     public class Lumiere
     {
     private double ratio = 1.0;
     private Color baseCouleur = Color.WHITE;
     private Point3D vecteur = new Point3D(0,0,1);
     public void ratio(double r)
     {
     ratio = r;
     }
     public double ratio()
     {
     return ratio;
     }
     public void vecteur(Point3D v)
     {
     vecteur=v;
     }
     public Point3D vecteur()
     {
     return vecteur;
     }
     public void couleur(Color c)
     {
     baseCouleur = c;
     }
     public Color couleur()
     {
     return baseCouleur;
     }
     public double facteurAngulaire(double a)
     {
     return Math.exp(-a*a);
     }
     }

     private ArrayList<Lumiere> lumieres = new ArrayList<Lumiere>();
     public Color calculer(Point3D n, Color co)
     {
     double ratio = 0.0;
     double a = 0.0;
     double [] c  = new double[] {co.red(), co.green(), co.blue()};
     for(int i=0; i<lumieres.size(); i++)
     ratio += lumieres.get(i).ratio();
     for(int i=0; i<lumieres.size(); i++)
     {
     Lumiere l  = lumieres.get(i);
     a = Math.acos(l.vecteur().prodScalaire(n));
     for(int comp = 0; comp<3; comp++)
     {
     double compVal = 0;
     switch(comp)
     {
     case 0:
     compVal = l.couleur().red() - c[comp];
     break;
     case 1:
     compVal = l.couleur().green() - c[comp];
     break;
     case 2:
     compVal = l.couleur().blue() - c[comp];
     break;
     }
     c[comp] += 
     compVal
     * l.ratio()/ratio
     * l.facteurAngulaire(a);
     }
     }
     for(int comp = 0; comp<3;comp++)
     {
     if(c[comp]>255)
     c[comp] = 255;
     if(c[comp]<0)
     c[comp] = 0;

     }
     return Color.valueOf((int)c[0], (int)c[1], (int)c[2]);
     }
     */


    @RequiresApi(api = Build.VERSION_CODES.O)
    public ITexture calculer(ArrayList<Lumiere> ls, Point3D p) {
        Color synthese = Color.valueOf(p.getTexture().getColorAt(0.5, 0.5));

        /* Calcul */
        if (ls.size() > 0) {

            float[] ratio = new float[]{1};

            Color[] colors = new Color[]{synthese};

            float[] f = synthese(ratio, colors);

            synthese = Color.valueOf(f[0], f[1], f[2]);
        }

        return new TextureCol(synthese);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private float[] synthese(float[] ratio, Color[] colors) {

        float[] c = new float[]{0, 0, 0};
        float[] incr;

        for (int i = 0; i < ratio.length; i++) {
            incr = colors[i].getComponents();
            for (int j = 0; j < 3; j++) {
                c[j] += incr[j] / ratio.length;
            }
        }

        return c;
    }
}
