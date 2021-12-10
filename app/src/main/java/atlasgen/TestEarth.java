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

package atlasgen;

import java.io.File;

/*__
 * Created by manue on 28-04-19.
 */
public class TestEarth extends TestSoS {


    private CsvReader csvReader;

    public static void main(String[] args) {
        TestEarth testEarth = new TestEarth();
        testEarth.setResolution(800, 600);
        testEarth.setQuadrillage(false);
        //testEarth.addAudioFile(new File("res/mp3/Louis Armstrong - What a wonderful world  ( 1967 ).mp3"));
        new Thread(testEarth).start();
    }

    public void ginit() {
        super.ginit();
        setMaxFrames(360 * list.length);
        csvReader = new CsvReader(new File("allCountries/allCountries.txt"),
                "" + '\t', "" + '\n', false);
        csvReader.setAction(new DrawPerCountryActionSphere(this));


    }

    public void finit() {
        try {
            super.finit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testScene() {
        //csvReader.process();
        //System.out.println("rendering now!");
    }
}