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
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ActivitySuperClass extends AppCompatActivity {
    public static final String TAG = "one.empty3.feature.app.maxSdk29.pro";
    public String appDataPath = "/one.empty3.feature.app.maxSdk29.pro/";
    public final String filenameSaveState = "state.properties";
    public final String imageViewFilename = "imageView.jpg";
    protected ImageViewSelection imageView;
    protected File currentFile;
    protected int maxRes = 1280;
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

    public InputStream getPathInput(Uri uri) throws FileNotFoundException {
        InputStream input = getApplicationContext().getContentResolver().openInputStream(uri);
        return input;
    }

    protected InputStream getRealPathFromIntentData(Intent file) {
        try {
            return getPathInput(file.getData());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected InputStream getRealPathFromURI(Uri uri) {
        try {
            return getPathInput(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
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

        imageView = findViewById(R.id.currentImageViewSelection);

        new Utils().loadImageInImageView(this);

        if(currentFile==null) {
            new Utils().loadImageInImageView(this);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        new Utils().saveImageState(this);
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(getImageViewPersistantFile()));
        } catch (IOException e) {
//            e.printStackTrace();
        }
        for (int i = 0; i < cords.length; i++) {
            properties.setProperty(cordsConsts[i], cords[i]);
        }
        properties.setProperty("maxRes", "" + maxRes);

        try {
            properties.store(new FileOutputStream(getImageViewPersistantFile()), "");
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
            properties.load(new FileInputStream(getImageViewPersistantFile()));
        } catch (IOException e) {
//            e.printStackTrace();
        }
        for (int i = 0; i < cords.length; i++) {
            cords[i] = properties.getProperty(cordsConsts[i], cords[i]);
        }
        maxRes = Integer.parseInt(properties.getProperty("maxRes"));

        if (currentFile != null) {
            if (imageView == null || imageView.getDrawable() == null) {
                imageView = findViewById(R.id.currentImageViewSelection);
                if (imageView != null) {
                    new Utils().setImageView(this, imageView);
                }
            }
        }
    }

    public void passParameters(Intent to) {

    }

    public void getParameters(Intent from) {

    }

    protected File getFilesFile(String s) {
        return new File("/storage/emulated/0/Android/data/one.empty3.feature.app.maxSdk29.pro/files/" + File.separator + s);
    }

    @org.jetbrains.annotations.Nullable
    public File getImageViewPersistantFile() {
        return getFilesFile(imageViewFilename);
    }
}
