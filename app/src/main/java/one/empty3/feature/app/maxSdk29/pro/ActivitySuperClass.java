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

import static android.content.pm.PackageManager.*;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.PermissionRequest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.PackageManagerCompat;
import androidx.core.content.PermissionChecker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Permission;
import java.util.Arrays;
import java.util.Properties;

public class ActivitySuperClass extends AppCompatActivity {
    public static final String TAG = "one.empty3.feature.app.maxSdk29.pro";
    private static final int ONSAVE_INSTANCE_STATE = 21516;
    private static final int ONRESTORE_INSTANCE_STATE = 51521;
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


        imageView = findViewById(R.id.imageView);
        if(imageView==null)
            imageView = findViewById(R.id.currentImageView);


        new Utils().loadImageInImageView(this);

        if(currentFile==null) {
            new Utils().loadImageInImageView(this);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        requestPermissions(new String[] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES}, ONSAVE_INSTANCE_STATE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==ONSAVE_INSTANCE_STATE&&grantResults!=null) {
            boolean g = true;
            for (int granted: grantResults)
                g = g & (granted==PERMISSION_GRANTED);

            if(g)
                saveInstanceState();
        }
        if(requestCode==ONRESTORE_INSTANCE_STATE&&grantResults!=null) {
            boolean g = true;
            for (int granted: grantResults)
                g = g & (granted==PERMISSION_GRANTED);
            if(g)
                restoreInstanceState();
        }
    }

    private void restoreInstanceState() {
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
        String maxRes1 = properties.getProperty("maxRes");
        if(maxRes1!=null) {
            try {
                maxRes = Integer.parseInt(maxRes1);
            } catch (NumberFormatException ex) {

            }
        }
        if (currentFile != null) {
            if (imageView == null || imageView.getDrawable() == null) {
                imageView = findViewById(R.id.currentImageView);
                if (imageView != null) {
                    new Utils().setImageView(this, imageView);
                }
            }
        }
    }

    protected void saveInstanceState() {
        new Utils().saveImageState(this);
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(getImageViewPersistantFile()));
            for (int i = 0; i < cords.length; i++) {
                properties.setProperty(cordsConsts[i], cords[i]);
            }
            properties.setProperty("maxRes", "" + maxRes);

            try {
                properties.store(new FileOutputStream(getImageViewPersistantFile()), "");
            } catch (IOException ignored) {
            }
        } catch (IOException ignored) {
        }

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        requestPermissions(new String[] {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES}, ONRESTORE_INSTANCE_STATE);

    }

    public void passParameters(Intent to) {


        to.setDataAndType(Uri.fromFile(this.currentFile), "image/jpg");

        new Utils().putExtra(to, cords, currentCord);
        new Utils().addCurrentFileToIntent(to, this, currentFile);

        startActivity(to);
    }

    public void getParameters(Intent from) {
        Utils utils = new Utils();
        currentFile = utils.getCurrentFile(from);
        cords = utils.getCordsValues();
        maxRes = utils.getMaxRes(this, null);

    }

    protected File getFilesFile(String s) {
        return new File("/storage/emulated/0/Android/data/one.empty3.feature.app.maxSdk29.pro/files/" + File.separator + s);
    }

    @org.jetbrains.annotations.Nullable
    public File getImageViewPersistantFile() {
        return getFilesFile(imageViewFilename);
    }
}
