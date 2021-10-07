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

package one.empty3.library.core.raytracer;

/*__
 * Created by manuel on 30-07-16.
 */
public class RtMatiere {/*! \class CMaterial
 *  \brief Cette classe repr�sente les materiaux ainsi que les o�prations de base sur ces derniers.
 *  \author Beno�t Lemaire (aka DaRkWoLf)
 *  \date   19/04/2002
 */

    private String mName;
    private RtColor mSpecularColor;
    private RtColor mDiffuseColor;
    private RtColor mAmbientColor;
    private RtColor mSelfIllumColor;
    private RtColor reflectionColor;
    private RtColor refractionColor;

    private double mShininess;
    private double mShinestrength;
    private double mTransmittivity;
    private double mReflectivity;
    private boolean mPermanent;
    private double reflectionAmount;
    private double refractionAmount;

    // constructor and destructor
    public RtMatiere() {
        mName = "EMPTY MAT";
        mSpecularColor = new RtColor(0.0f, 0.0f, 0.0f);
        mDiffuseColor = new RtColor(0.0f, 0.0f, 0.0f);
        mAmbientColor = new RtColor(0.0f, 0.0f, 0.0f);
        mSelfIllumColor = new RtColor(0.0f, 0.0f, 0.0f);
        mShininess = 0.0f;
        mShinestrength = 0.0f;
        mTransmittivity = 0.0f;
        mReflectivity = 0.0f;
        mPermanent = true;
    }

    public RtMatiere(String name, RtColor specular, RtColor diffuse, RtColor ambient, RtColor selfillum) {
        this(name, specular, diffuse, ambient, selfillum, 0, 0, 0, 0, true);
    }

    public RtMatiere(String name, RtColor specular, RtColor diffuse, RtColor ambient, RtColor selfillum, double transmittivity, double reflectivity) {
        this(name, specular, diffuse, ambient, selfillum, transmittivity, reflectivity, 0, 0, true);
    }

    public RtMatiere(String name, RtColor specular, RtColor diffuse, RtColor ambient, RtColor selfillum, double transmittivity, double reflectivity, double shininess, double shinestrength, boolean permanent) {
        mName = name;
        mSpecularColor = specular;
        mDiffuseColor = diffuse;
        mAmbientColor = ambient;
        mSelfIllumColor = selfillum;
        mShininess = shininess;

        mShinestrength = shinestrength;
        mTransmittivity = transmittivity;
        mReflectivity = reflectivity;
        mPermanent = permanent;
    }


    // get
    public String GetName() {
        return mName;
    }

    public RtColor GetDiffuse() {
        return mDiffuseColor;
    }

    public RtColor GetSpecular() {
        return mSpecularColor;
    }

    public RtColor GetAmbient() {
        return mAmbientColor;
    }

    public RtColor GetSelfIllum() {
        return mSelfIllumColor;
    }

    public double GetShininess() {
        return mShininess;
    }

    public double GetShinestStrength() {
        return mShinestrength;
    }

    public double GetTransmittivity() {
        return mTransmittivity;
    }

    public double GetReflectivity() {
        return mReflectivity;
    }

    public boolean GetPermanency() {
        return mPermanent;
    }

    // set
    public void SetName(String name) {
        mName = name;
    }

    public void SetSpecular(RtColor specular) {
        mSpecularColor = specular;
    }

    public void SetDiffuse(RtColor diffuse) {
        mDiffuseColor = diffuse;
    }

    public void SetAmbient(RtColor ambient) {
        mAmbientColor = ambient;
    }

    public void SetSelfIllum(RtColor selfIllum) {
        mSelfIllumColor = selfIllum;
    }

    public void SetShininess(float shininess) {
        mShininess = shininess;
    }

    public void SetShinesStrenght(float sStrength) {
        mShinestrength = sStrength;
    }

    public void SetTransparency(float transmittivity) {
        mTransmittivity = transmittivity;
    }

    public void SetReflectivity(float reflectivity) {
        mReflectivity = reflectivity;
    }

    public void SetPermanency(boolean permanent) {
        mPermanent = permanent;
    }

/*
        // operators
         public static RtMatiere mult(RtMatiere mat, float multiple)
        { return RtMatiere( "COMPOSITE MATERIAL",
                mat.mSpecularColor*multiple,
                mat.mDiffuseColor*multiple,
                mat.mAmbientColor*multiple,
                mat.mSelfIllumColor*multiple,
                mat.mTransmittivity*multiple,
                mat.mReflectivity*multiple,
                mat.mShininess*multiple,
                mat.mShinestrength*multiple ); }

         friend CMaterial operator+ (const CMaterial& mat1, const CMaterial& mat2)
        { return CMaterial( "COMPOSITE MATERIAL",
                mat1.mSpecularColor+mat2.mSpecularColor,
                mat1.mDiffuseColor+mat2.mDiffuseColor,
                mat1.mAmbientColor+mat2.mAmbientColor,
                mat1.mSelfIllumColor+mat2.mSelfIllumColor,
                mat1.mTransmittivity+mat2.mTransmittivity,
                mat1.mReflectivity+mat2.mReflectivity,
                mat1.mShininess+mat2.mShininess,
                mat1.mShinestrength+mat2.mShinestrength ); }

         friend void operator+= (CMaterial& mat1, const CMaterial& mat2)
        {	mat1.mName = "COMPOSITE MATERIAL";
            mat1.mSpecularColor += mat2.mSpecularColor;
            mat1.mDiffuseColor += mat2.mDiffuseColor;
            mat1.mAmbientColor += mat2.mAmbientColor;
            mat1.mSelfIllumColor += mat2.mSelfIllumColor;
            mat1.mTransmittivity += mat2.mTransmittivity;
            mat1.mReflectivity += mat2.mReflectivity;
            mat1.mShininess += mat2.mShininess;
            mat1.mShinestrength += mat2.mShinestrength;
        }

        // misc functions
         CMaterial InterpolateMaterials(const CMaterial& mat1, const CMaterial& mat2) const
        { return CMaterial(	"COMPOSITE MATERIAL",
                (mat1.mSpecularColor+mat2.mSpecularColor)/2.0f,
                (mat1.mDiffuseColor+mat2.mDiffuseColor)/2.0f,
                (mat1.mAmbientColor+mat2.mAmbientColor)/2.0f,
                (mat1.mSelfIllumColor+mat2.mSelfIllumColor)/2.0f,
                (mat1.mTransmittivity+mat2.mTransmittivity)/2.0f,
                (mat1.mReflectivity+mat2.mReflectivity)/2.0f,
                (mat1.mShininess+mat2.mShininess)/2.0f,
                (mat1.mShinestrength+mat2.mShinestrength)/2.0f	); }
    };

*/

    public RtColor getRefractionColor() {
        return refractionColor;
    }

    public void setRefractionColor(RtColor refractionColor) {
        this.refractionColor = refractionColor;
    }

    public RtColor getReflectionColor() {
        return reflectionColor;
    }

    public void setReflectionColor(RtColor reflectionColor) {
        this.reflectionColor = reflectionColor;
    }

    public double getReflectionAmount() {
        return reflectionAmount;
    }

    public void setReflectionAmount(double reflectionAmount) {
        this.reflectionAmount = reflectionAmount;
    }

    public double getRefractionAmount() {
        return refractionAmount;
    }

    public void setRefractionAmount(double refractionAmount) {
        this.refractionAmount = refractionAmount;
    }
}
