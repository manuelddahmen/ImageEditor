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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.camera2.interop.ExperimentalCamera2Interop;
import one.empty3.Run;
import one.empty3.feature20220726.PixM;

import java.io.*;
import java.util.Date;
import java.util.Properties;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

@ExperimentalCamera2Interop
public class ActivitySuperClass extends AppCompatActivity {
    public static final String TAG = "one.empty3.feature.app.maxSdk29.pro";
    public static final int MAXRES_DEFAULT = 500;
    protected static final String[] cordsConsts = new String[]{"x", "y", "z", "r", "g", "b", "a", "t", "u", "v"};
    private static final int ONSAVE_INSTANCE_STATE = 21516;
    private static final int ONRESTORE_INSTANCE_STATE = 51521;
    public final String filenameSaveState = "state.properties";
    public final String imageViewFilename = "imageView.jpg";
    public final String imageViewFilenameProperties = "imageView.properties";
    public String appDataPath = "/one.empty3.feature.app.maxSdk29.pro/";
    public String variableName;
    public String variable;
    protected ImageViewSelection imageView;
    protected File currentFile;
    protected String[] cords = new String[]{"x", "y", "z", "r", "g", "b", "a", "t", "u", "v"};
    protected Bitmap currentBitmap;
    private int maxRes = R.string.maxRes_1200;

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
        if (getIntent() != null) {
            getParameters(getIntent());
            if (currentFile == null && savedInstanceState != null) {
                try {
                    if (savedInstanceState.getString("currentFile") != null) {
                        currentFile = new File(savedInstanceState.getString("currentFile"));
                    }
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (imageView == null)
            imageView = findViewById(R.id.currentImageView);
        if(currentFile!=null) {
            testIfValidBitmap();
        } else
            loadInstanceState();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        /*requestPermissions(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES}, ONSAVE_INSTANCE_STATE);
*/
        saveInstanceState();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ONSAVE_INSTANCE_STATE && grantResults != null) {
            int g = 0;
            for (int granted : grantResults) {
                g = g + ((granted == PERMISSION_GRANTED) ? 1 : 0);
            }

            if (g > 0)
                saveInstanceState();
        }
        if (requestCode == ONRESTORE_INSTANCE_STATE && grantResults != null) {
            int g = 0;
            for (int granted : grantResults) {
                g = g + ((granted == PERMISSION_GRANTED) ? 1 : 0);
            }

            if (g > 0)
                restoreInstanceState();
        }
    }

    void restoreInstanceState() {
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
                if (imageViewPersistantFile.exists()) {
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
        if (currentFile == null)
            loadInstanceState();
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

    protected void loadInstanceState() {
        String currentFile1 = null;
        new Utils().loadImageState(this, false);
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(getImageViewPersistantPropertiesFile()));
        } catch (RuntimeException | IOException ex) {
            ex.printStackTrace();
        }
        try {
            for (int i = 0; i < cords.length; i++) {
                properties.setProperty(cordsConsts[i], cords[i]);
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
            try {
            String maxRes1 = properties.getProperty("maxRes", "" + maxRes);
            if(maxRes1!=null && maxRes1.length()>0) {
                try {
                    maxRes = (int)Double.parseDouble(maxRes1);
                } catch (RuntimeException ex1) {
                    ex1.printStackTrace();

                }
            }

            currentFile1 = properties.getProperty("currentFile", currentFile.getAbsolutePath());
            } catch (RuntimeException ex) {
                ex.printStackTrace();
            }

        try {
            File currentFile2 = null;
            if(currentFile1==null)
                currentFile2 = getImageViewPersistantFile();
            else
                currentFile2 = new File(currentFile1);
            if(currentFile2!=null&&currentFile2.exists()) {
                    currentFile = currentFile2;
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        if(currentFile==null)
            Toast.makeText(getApplicationContext(), "Cannot find current file (working copy)", Toast.LENGTH_SHORT)
                    .show();
    }

    protected void saveInstanceState() {
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
                if (currentFile != null)
                    new Utils().writeFile(this,
                            BitmapFactory.decodeStream(
                                    new FileInputStream(currentFile)),
                            getImageViewPersistantFile(), getImageViewPersistantFile(),
                            maxRes, true);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException | IOException ex) {
        }

        try {
            properties.store(new FileOutputStream(getImageViewPersistantPropertiesFile()), "#" + new Date().toString());
        } catch (IOException ignored) {
        }
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
        maxRes = utils.getMaxRes(this, null);
        utils.loadImageInImageView(this);
        utils.loadVarsMathImage(this, getIntent());
    }

    protected File getFilesFile(String s) {
        return new File("/storage/emulated/0/Android/data/one.empty3.feature.app.maxSdk29.pro/files/"  + s);
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
        saveInstanceState();
        try {
            currentBitmap = null;
            if (imageView == null)
                imageView = findViewById(R.id.imageViewSelection);
            if (imageView != null && currentFile != null)
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
        } else if (photo != null) {
            return photo;
        } else {
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        new Utils().saveImageState(this);
        saveInstanceState();

    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        requestPermissions(new String[]{
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.READ_MEDIA_IMAGES}, ONRESTORE_INSTANCE_STATE);
        new Utils().loadImageState(this, false);
        restoreInstanceState();
    }
}