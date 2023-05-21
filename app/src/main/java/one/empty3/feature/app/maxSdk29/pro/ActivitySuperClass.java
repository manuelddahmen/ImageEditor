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
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.camera2.interop.ExperimentalCamera2Interop;
import androidx.databinding.adapters.ToolbarBindingAdapter;
import one.empty3.feature20220726.PixM;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

@ExperimentalCamera2Interop public class ActivitySuperClass extends AppCompatActivity {
    public static final String TAG = "one.empty3.feature.app.maxSdk29.pro";
    private static final int ONSAVE_INSTANCE_STATE = 21516;
    private static final int ONRESTORE_INSTANCE_STATE = 51521;
    public static final int MAXRES_DEFAULT = 200;
    public String appDataPath = "/one.empty3.feature.app.maxSdk29.pro/";
    public final String filenameSaveState = "state.properties";
    public final String imageViewFilename = "imageView.jpg";
    public final String imageViewFilenameProperties = "imageView.properties";
    protected ImageViewSelection imageView;
    protected File currentFile;
    private int maxRes = R.string.maxRes_1200;
    protected static final String[] cordsConsts = new String[]{"x", "y", "z", "r", "g", "b", "a", "t", "u", "v"};

    protected String[] cords = new String[]{"x", "y", "z", "r", "g", "b", "a", "t", "u", "v"};
    protected Bitmap currentBitmap;
    public String variableName;
    public String variable;

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
        if(getIntent()!=null) {
            getParameters(getIntent());
        }
        currentFile = new Utils().getCurrentFile(getIntent());

        if (imageView == null)
            imageView = findViewById(R.id.currentImageView);

        new Utils().loadVarsMathImage(this, getIntent());


        testIfValidBitmap();


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        requestPermissions(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES}, ONSAVE_INSTANCE_STATE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ONSAVE_INSTANCE_STATE && grantResults != null) {
            int g = 0;
            for (int granted : grantResults) {
                g = g + ((granted == PERMISSION_GRANTED)?1:0);
            }

            if (g>=2)
                saveInstanceState();
        }
        if (requestCode == ONRESTORE_INSTANCE_STATE && grantResults != null) {
            int g = 0;
            for (int granted : grantResults) {
                g = g + ((granted == PERMISSION_GRANTED)?1:0);
            }

            if (g>=2)
                restoreInstanceState();
        }
    }

    private void restoreInstanceState() {
        new Utils().loadImageState(this, false);
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(getImageViewPersistantPropertiesFile()));
            for (int i = 0; i < cords.length; i++) {
                cords[i] = properties.getProperty(cordsConsts[i], cords[i]);
            }
            String maxRes1 = properties.getProperty("maxRes");
            if (maxRes1 != null) {
                try {
                    maxRes = Integer.parseInt(maxRes1);
                } catch (NumberFormatException ignored) {

                }
            }
            try {
                String currentFile1 = properties.getProperty("currentFile");
                currentFile = new File(currentFile1);

                File imageViewPersistantFile = getImageViewPersistantFile();
                if(imageViewPersistantFile.exists()) {
                    currentFile = imageViewPersistantFile;
                }
            } catch (RuntimeException ex) {
                Toast.makeText(getApplicationContext(), "Error restoring currentFile", Toast.LENGTH_LONG)
                        .show();
            }
        } catch (IOException ignored) {

        }
    }

    public void testIfValidBitmap() {
        if (currentFile != null) {
            if (!currentFile.exists()) {
                currentFile = null;
                return;
            }
            try {
                FileInputStream fileInputStream = new FileInputStream(currentFile);
                if (BitmapFactory.decodeStream(fileInputStream)
                        == null)
                    currentFile = null;


            } catch (FileNotFoundException e) {
                System.err.println("Error file:" + currentFile);
                currentFile = null;
            } catch (RuntimeException exception) {
                currentFile = null;
            }
        }
    }

    protected void saveInstanceState() {
        new Utils().saveImageState(this);
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(getImageViewPersistantPropertiesFile()));
            for (int i = 0; i < cords.length; i++) {
                properties.setProperty(cordsConsts[i], cords[i]);
            }
            properties.setProperty("maxRes", "" + maxRes);

            try {
                properties.setProperty("currentFile", currentFile.getAbsolutePath());
                File imageViewPersistantFile = currentFile;
                if(currentFile!=null)
                    new Utils().writeFile(this,
                            BitmapFactory.decodeStream(
                                    new FileInputStream(currentFile)),
                                    getImageViewPersistantFile(), getImageViewPersistantFile(),
                            maxRes, true);
                } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException | IOException ex) {
            Toast.makeText(getApplicationContext(), "Error saving currentFile", Toast.LENGTH_LONG)
                        .show();
            }

            try {
                properties.store(new FileOutputStream(getImageViewPersistantPropertiesFile()), "#"+new Date().toString());
            } catch (IOException ignored) {
            }
        }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        requestPermissions(new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES}, ONRESTORE_INSTANCE_STATE);

    }

    public void passParameters(Intent to) {

        if (currentFile != null)
            to.setDataAndType(Uri.fromFile(currentFile), "image/jpg");
        to.putExtra("maxRes", getMaxRes());
        new Utils().putExtra(to, cords, cordsConsts, variableName, variable);

        System.out.println("c className = " + this.getClass());
        System.out.println("m variableName = " + variableName);
        System.out.println("m variable =     " + variable);
        System.out.println("i variableName = " + getIntent().getStringExtra("variableName"));
        System.out.println("i variable =     " + getIntent().getStringExtra("variable"));
        System.out.println("c to.className = " + to.getType());


        startActivity(to);
    }

    public void getParameters(Intent from) {
        Utils utils = new Utils();
        currentFile = utils.getCurrentFile(from);
        cords = utils.getCordsValues();
        String v = from.getStringExtra(variableName);
        if(v!=null)
            variable = v;
        maxRes = utils.getMaxRes(this, null);

    }

    protected File getFilesFile(String s) {
        return new File("/storage/emulated/0/Android/data/one.empty3.feature.app.maxSdk29.pro/files" + File.separator + s);
    }

    @org.jetbrains.annotations.Nullable
    public File getImageViewPersistantFile() {
        return getFilesFile(imageViewFilename);
    }

    @org.jetbrains.annotations.Nullable
    public File getImageViewPersistantPropertiesFile() {
        return getFilesFile(imageViewFilenameProperties);
    }


    void drawIfBitmap() {
        restoreInstanceState();
        try {
            currentBitmap = null;
            if (imageView == null)
                imageView = findViewById(R.id.imageViewSelection);
            if (imageView != null && currentFile!=null)
                new Utils().setImageView(this, imageView);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    public int getMaxRes() {
        return new Utils().getMaxRes(this);
    }

    public void setMaxRes(int maxRes) {
        this.maxRes = maxRes;
    }


    public Bitmap loadImage(InputStream choose_directoryData, boolean isCurrentFile) {
        Bitmap photo = null;
        if (maxRes > 0) {
            System.err.println("FileInputStream" + choose_directoryData);
            photo = BitmapFactory.decodeStream(choose_directoryData);
            photo = PixM.getPixM(photo, maxRes).getImage().getBitmap();
            System.err.println("Get file (bitmap) : " + photo);
        } else {
            System.err.println("FileInputStream" + choose_directoryData);
            photo = BitmapFactory.decodeStream(choose_directoryData);
            System.err.println("Get file (bitmap) : " + photo);
        }
        if (photo != null && isCurrentFile) {
            currentFile = new Utils().writePhoto(this, photo, "loaded_image-");
            new Utils().setImageView(this, imageView);
            return photo;
        } else if(photo!=null) {
            return photo;
        }else {
            System.err.println("file == null. Error.");
            throw new NullPointerException("File==null ActivitySuperClass, loadImage");
        }

    }

    public Bitmap loadImage(Bitmap photo, File choose_directoryData, boolean isCurrentFile) {
        try {
            return loadImage(new FileInputStream(choose_directoryData), isCurrentFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onDestroy() {
        saveInstanceState();
        super.onDestroy();
    }
}