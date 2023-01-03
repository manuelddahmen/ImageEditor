/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package one.empty3.feature20220726;

/*
 *  // 1. amplitude and scale featyre
 *     Gauss
 *     2.1 pattern based amplification filter
 *     circle : (x, y, [xc, yc,] r) (radial)
 *     mutil circle tries
 *     2.2 linear : (x,y, xl, yl, d) (linear)
 *     other shapes
 *     3. local amplification : { (xi, yi) }
 *     kind of feature
 *     planes of colors or magnitude
 *     gradient == 0.0 ~+ 0.1
 *     surface { (xi, yi) }
 * Filtre sur toute l'image et plusieurs images.
 */
public class DaBandFilter {

    /*
     * Tracer toutes les lignes passant par Mxy et son voisinage, 1x1, 2x1, 1x2, 2x2, etc.
     * Une à n lignes, sans addition de couleurs (synthèse additive)
     *  . - ((résultat de Voisinage de GMatrix(vx, vy)) - max){comps}
     * Regarder les minima locaux à 2x2 pour commencer Mres
     * Mres = adapter la teinte de la couleur de ligne max pour RN(vx,v)-max -----vers min ou zéro.
     * relier les points adjacents si couleur similaire
     * //Retenter sur Mres{(x,y)} pour nxn avec n = n(step-1)+1
     * @param matrix matrice d'image à analyser
     */
    public DaBandFilter(PixM matrix) {

    }

}
