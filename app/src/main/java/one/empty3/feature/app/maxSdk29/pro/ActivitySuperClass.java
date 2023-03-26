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

package one.empty3.feature.app.maxSdk29.pro;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ActivitySuperClass extends AppCompatActivity {
    private static final String TAG = "one.empty3.feature.app.maxSdk29.pro";
    public final String filenameSaveState = "state.properties";
    protected ImageViewSelection imageView;
    protected File currentFile;
    protected int maxRes;
    protected static final String[] cordsConsts = new String[]{"x", "y", "z", "r", "g", "b", "a", "t", "u", "v"};

    protected String[] cords = new String[]{"x", "y", "z", "r", "g", "b", "a", "t", "u", "v"};
    protected String currentCord = cords[0];

    public ImageViewSelection getImageView() {
        return imageView;
    }

    public void setImageView(ImageViewSelection imageView) {
        this.imageView = imageView;
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null && getIntent().getExtras() != null)
            for (int i = 0; i < cordsConsts.length; i++) {
                if (getIntent().getStringExtra(cordsConsts[i]) != null)
                    cords[i] = getIntent().getStringExtra(cordsConsts[i]);
            }
        currentFile = new Utils().getCurrentFile(getIntent());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        new Utils().saveImageState(this, false);
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(filenameSaveState));
        } catch (IOException e) {
//            e.printStackTrace();
        }
        for (int i = 0; i < cords.length; i++) {
            properties.setProperty(cordsConsts[i], cords[i]);
        }
        properties.setProperty("maxRes", "" + maxRes);

        try {
            properties.store(new FileOutputStream(filenameSaveState), "");
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        new Utils().loadImageState(this, false);
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(filenameSaveState));
        } catch (IOException e) {
//            e.printStackTrace();
        }
        for (int i = 0; i < cords.length; i++) {
            cords[i] = properties.getProperty(cordsConsts[i], cords[i]);
        }
        maxRes = Integer.parseInt(properties.getProperty("maxRes"));
    }

    public void passParameters(Intent to) {

    }

    public void getParameters(Intent from) {

    }

}
