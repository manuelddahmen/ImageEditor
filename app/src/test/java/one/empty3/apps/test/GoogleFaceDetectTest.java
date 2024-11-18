/*
 * Copyright (c) 2024.
 *
 *
 *  Copyright 2023 Manuel Daniel Dahmen
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

package one.empty3.apps.test;

import org.junit.Assert;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import one.empty3.libs.Image;
import one.empty3.androidFeature.GoogleFaceDetection;

public class GoogleFaceDetectTest {
    @Test
    public void loadSaveTest()   {
        Image image = new Image(1000, 1000);

        try {
            GoogleFaceDetection googleFaceDetection = new GoogleFaceDetection(image);
            FileInputStream fileOutputStream1 = new FileInputStream("C:\\Users\\manue\\AndroidStudioProjects\\FeatureApp3\\face-drawings.fac");
            GoogleFaceDetection decoded = (GoogleFaceDetection) googleFaceDetection.decode(new DataInputStream(fileOutputStream1));
            fileOutputStream1.close();

            FileOutputStream fileOutputStream = new FileOutputStream("text.fac");
            decoded.encode(new DataOutputStream(fileOutputStream));
            fileOutputStream.close();



            Assert.assertTrue(true);
        } catch (IOException e6) {
            e6.printStackTrace();
            Assert.fail();
        }
    }
}
