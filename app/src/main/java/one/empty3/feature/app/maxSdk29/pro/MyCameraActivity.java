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

import static java.nio.file.Files.copy;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.multidex.BuildConfig;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javaAnd.awt.Point;
import javaAnd.awt.image.BufferedImage;
import javaAnd.awt.image.imageio.ImageIO;
import one.empty3.feature20220726.PixM;

public class MyCameraActivity extends AppCompatActivity {
    private static final String TAG = "one.empty3.feature.app.maxSdk29.pro.MyCameraActivity";
    private static final Integer MAX_TARDINESS = 3000;
    static final int MAX_RES_DEFAULT = 200;
    public static final String IMAGE_VIEW_ORIGINAL_JPG = "imageViewOriginal.jpg";
    public static final String IMAGE_VIEW_JPG = "imageView.jpg";
    private final String appDataPath = "/one.empty3.feature.app.maxSdk29.pro/";
    private AppCompatActivity thisActivity;
    private static final int REQUEST_CREATE_DOCUMENT_SAVE_IMAGE = 4072040;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int ONCLICK_STARTACTIVITY_CODE_VIDEO_CHOOSER = 9998;
    private static final int ONCLICK_STARTACTIVITY_CODE_PHOTO_CHOOSER = 9999;
    private static final int FILESYSTEM_WRITE_PICTURE = 1111;
    private static final int MY_EXTERNAL_STORAGE_PERMISSION_CODE = 7777;
    private ImageView imageView;
    private File currentDir;
    private File currentBitmap;
    private File currentFileOriginalResolution;
    private File currentFile;
    private File currentFileZoomed;
    private boolean beta = false;
    private Point drawPointA = null;
    private Point drawPointB = null;
    private List<RectF> rectfs = new ArrayList<RectF>();
    private Bitmap currentFileZoomedBitmap;
    private PixM currentPixM = null;
    private boolean loaded;
    private int maxRes = 200;
    private boolean workingResolutionOriginal = false;
    private Clipboard clipboard;
    private boolean copied;


    class LoadImage extends AsyncTask {

        private final File file;
        private final int resolution;

        public LoadImage(File file, int resolution) {
            super();
            this.file = file;
            this.resolution = resolution;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                Bitmap photo = BitmapFactory.decodeStream(new FileInputStream(file));
                System.err.println("Photo bitmap : " + file.toURI() + "\nFile exists?" + file.exists());
                imageView.setImageBitmap(photo);
                //imageView.setBackground(Drawable.createFromStream(new FileInputStream(currentBitmap), "chosenImage"));
                System.err.println("Image main intent loaded");
                //saveImageState();
                maxRes = resolution;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }
    }


    public InputStream getPathInput(Uri uri) throws FileNotFoundException {
        InputStream input = getContentResolver().openInputStream(uri);
        return input;
    }

    public OutputStream getPathOutput(Uri uri) throws FileNotFoundException {
        OutputStream output = getContentResolver().openOutputStream(uri);
        return output;
    }

    private InputStream getRealPathFromURI(Intent file) {
        try {
            return getPathInput(file.getData());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void toastButtonDisabled(View button) {
        if (currentFile == null) {
            String text = "Pas de fichier image choisis.<br/>" +
                    "Choisisssez une image dans les fichiers de<br/>" +
                    getString(R.string.button_current_file_is_null) +
                    "le copier/coller. La fonction draw ne fonctionne pas<br/>" +
                    "actuellement.<br/>";
            Integer duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
/*
        FragmentNavigation fragmentNavigation = new FragmentNavigation();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.stubNavigation,
                        fragmentNavigation)
                .commit();
*/
        Undo dataWithUndo = Undo.getUndo();
        dataWithUndo.doStep(new DataApp(getMaxRes(), currentFile, currentFile, isWorkingResolutionOriginal()));
        maxRes = new Utils().getMaxRes(this, savedInstanceState);

        imageView = (ImageView) this.findViewById(R.id.currentImageView);

        rectfs = new ArrayList<RectF>();

        loaded = true;


        currentFile = currentBitmap = new Utils().getCurrentFile(getIntent());

        if (new Utils().loadImageInImageView(currentFile, imageView))
            loaded = true;


        thisActivity = this;


        currentDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);


        Button takePhoto = findViewById(R.id.takePhotoButton);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);


            }

        });

/*        Button effectsButton = this.findViewById(R.id.effectsButton);
        effectsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView = findViewById(R.id.currentImageView);
                Intent intent = new Intent(Intent.ACTION_EDIT);
                System.err.println("Click on Effect button");
                if (currentFile != null || currentBitmap != null) {
                    if (currentFile == null)
                        currentFile = currentBitmap.getAbsoluteFile();
                    intent.setDataAndType(Uri.fromFile(currentFile),
                            "image/jpg");
                    intent.setClass(imageView.getContext(),
                            ChooseEffectsActivity.class);
                    intent.putExtra("data", currentFile);
                    View viewById = findViewById(R.id.editMaximiumResolution);
                    intent.putExtra("maxRes", (int) Double.parseDouble(((TextView) viewById).getText().toString()));
                    System.err.println("Start activity : EffectChoose");
                    startActivity(intent);
                } else {
                    System.err.println("No file assigned");
                    System.err.println("Can't Start activity : EffectChoose");
                }

            }
        });*/
        Button effectsButton2 = this.findViewById(R.id.effectsButtonNew);
        effectsButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFile != null) {
                    imageView = findViewById(R.id.currentImageView);
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    System.err.println("Click on Effect button");
                    if (currentFile != null || currentBitmap != null) {
                        if (currentFile == null) currentFile = currentBitmap.getAbsoluteFile();
                        try {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                currentFile = currentBitmap = new Utils().writePhoto(thisActivity,
                                        BitmapFactory.decodeStream(new FileInputStream(currentFile)),
                                        "EffectOn");
                            }
                            intent.setDataAndType(Uri.fromFile(currentFile), "image/jpg");
                            intent.setClass(imageView.getContext(), ChooseEffectsActivity2.class);
                            intent.putExtra("data", currentFile);
                            View viewById = findViewById(R.id.editMaximiumResolution);
                            intent.putExtra("maxRes", (int) Double.parseDouble(((TextView) viewById).getText().toString()));
                            System.err.println("Start activity : EffectChoose");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        startActivity(intent);
                    } else {
                        System.err.println("No file assigned");
                        System.err.println("Can't Start activity : EffectChoose");
                    }
                } else toastButtonDisabled(v);
            }
        });
        View fromFiles = findViewById(R.id.choosePhotoButton);

        fromFiles.setOnClickListener(v -> {
            startCreation();
        });

        View copy = findViewById(R.id.copy);
        copy.setOnClickListener(v -> {
            if (clipboard != null) {
                clipboard.copied = true;
                copy.setBackgroundColor(Color.rgb(40, 255, 40));
                Toast.makeText(getApplicationContext(), "Subimage copied", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        View paste = findViewById(R.id.paste);
        paste.setOnClickListener(v -> {
            clipboard = Clipboard.defaultClipboard;
            if (currentFile != null) {
                if (clipboard == null && Clipboard.defaultClipboard != null)
                    clipboard = Clipboard.defaultClipboard;
                if (clipboard != null && clipboard.copied && clipboard.getDestination() != null
                        && clipboard.getSource() != null) {
                    PixM dest = PixM.getPixM(Objects.requireNonNull(ImageIO.read(currentFile)).bitmap);
                    if (rectfs.size() > 0)
                        clipboard.setDestination(rectfs.get(rectfs.size() - 1));
                    else {
                        return;
                    }
                    int x = Math.min((int) clipboard.getDestination().left, (int) clipboard.getDestination().right);
                    int y = Math.min((int) clipboard.getDestination().bottom, (int) clipboard.getDestination().top);
                    int w = Math.abs((int) clipboard.getDestination().right - (int) clipboard.getDestination().left);
                    int h = Math.abs((int) clipboard.getDestination().bottom - (int) clipboard.getDestination().top);
                    dest.pasteSubImage(clipboard.getSource(), x, y, w, h);
                    System.err.println("Destionation coord = " + clipboard.getDestination());
                    System.err.println("Theory Copied pixels = " + clipboard.getSource().getColumns() * clipboard.getSource().getLines());
                    Bitmap bitmap = dest.getBitmap();
                    currentBitmap = currentFile
                            = new Utils().writePhoto(this, bitmap, "copy_paste");
                    imageView.setImageBitmap(bitmap);
                    paste.setBackgroundColor(Color.rgb(40, 255, 40));
                    copy.setBackgroundColor(Color.rgb(40, 255, 40));
                    Toast.makeText(getApplicationContext(), "Subimage pasted", Toast.LENGTH_SHORT)
                            .show();
                }
            } else toastButtonDisabled(v);
        });
        View about = findViewById(R.id.About);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserData(v);
            }
        });

        View shareView = findViewById(R.id.share);
        shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFile != null) {
                    Uri uri = Uri.fromFile(currentFile);
                    Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getApplicationContext().getPackageName() + ".provider", currentFile);
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, photoURI);
                    shareIntent.setDataAndType(photoURI, "image/jpeg");
                    shareIntent.putExtra("data", photoURI);
                    startActivity(shareIntent);
                } else toastButtonDisabled(v);
            }

        });
        shareView.setEnabled(true);
        View save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFile != null) {

                    saveImageState(isWorkingResolutionOriginal());

                    String[] permissionsStorage = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    int requestExternalStorage = 1;
                    int permission1 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                    int permission2 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (permission1 != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(thisActivity, permissionsStorage, requestExternalStorage);
                    }
                    if (permission2 != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(thisActivity, permissionsStorage, requestExternalStorage);
                    }

                    File picturesDirectory = new File(String.valueOf(getExternalFilesDir(Environment.DIRECTORY_PICTURES)));
                    Path target = null;

                    try {
                        target = copy((currentFile).toPath(), new File(picturesDirectory.getAbsolutePath() + UUID.randomUUID() + ".jpg").toPath());
                    } catch (IOException e) {
                        e.printStackTrace();

                        return;
                    }


                    Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getApplicationContext().getPackageName() + ".provider", target == null ? currentFile : target.toFile());


                    Intent intentSave = new Intent(Intent.ACTION_CREATE_DOCUMENT);

                    //send an ACTION_CREATE_DOCUMENT intent to the system. It will open a dialog where the user can choose a location and a filename
                    intentSave.addCategory(Intent.CATEGORY_OPENABLE);
                    intentSave.putExtra(Intent.EXTRA_TITLE, "photo-" + UUID.randomUUID() + ".jpg");
                    intentSave.setDataAndType(photoURI, "image/jpeg");
                    startActivityForResult(intentSave, REQUEST_CREATE_DOCUMENT_SAVE_IMAGE);


                } else toastButtonDisabled(v);


            }


        });


        //Draw activity (pass: rectangle, image, image view size.
        View draw_activity_button = findViewById(R.id.draw_activity);
        //draw_activity_button.setEnabled(false);
        draw_activity_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawPointA != null && drawPointB != null) {
                    Intent intentDraw = new Intent(Intent.ACTION_CHOOSER);
                    intentDraw.setClass(getApplicationContext(), TextAndImages.class);
                    intentDraw.putExtra("drawRectangle", new Rect((int) drawPointA.x, (int) drawPointA.y, (int) drawPointB.x, (int) drawPointB.y));
                    intentDraw.putExtra("currentFile", currentFile);
                    intentDraw.putExtra("currentFileZoomed", currentFileZoomed);
                    startActivity(intentDraw);
                }
            }
        });
        Button computePixels = findViewById(R.id.activity_compute_pixels);
        computePixels.setOnClickListener(v -> {
            if (currentFile != null) {
                Uri uri = Uri.fromFile(currentFile);
                //Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getApplicationContext().getPackageName() + ".provider", currentFile);
                Intent intentDraw = new Intent(Intent.ACTION_EDIT);
                intentDraw.setDataAndType(uri, "image/jpeg");
                intentDraw.putExtra("currentFile", currentFile);
                intentDraw.putExtra("maxRes", getMaxRes());
                intentDraw.putExtra("data", uri);
                intentDraw.setClass(getApplicationContext(), GraphicsActivity.class);
                startActivity(intentDraw);
            } else toastButtonDisabled(v);
        });
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        imageView.setOnTouchListener((View v, MotionEvent event) -> {
            imageView = findViewById(R.id.currentImageView);
            if (currentFile != null) {
                int[] location = new int[2];
                v.getLocationOnScreen(location);
                int viewX = location[0];
                int viewY = location[1];
                location = new int[]{0, 0};
                float x = event.getRawX() - viewX;
                float y = event.getRawY() - viewY;

                Point p = new Point((int) x, (int) y);

                if (drawPointA == null && drawPointB == null) {
                    System.err.println("Sélection du point A: ");
                    if (checkPointCordinates(p)) {
                        drawPointA = p;
                        System.err.println("Draw point A = (" + drawPointA.getX() + ", " + drawPointA.getY() + ") ");
                    } else System.err.println("Error cordinates A");
                    // Sélectionner A
                } else {
                    System.err.println("Sélection du point B: ");
                    // Sélectionner B
                    if (checkPointCordinates(p)) {
                        drawPointB = p;
                        System.err.println("Draw point B = (" + drawPointB.getX() + ", " + drawPointB.getY() + ") ");
                    } else System.err.println("Error cordinates B");
                    //drawPointA = new Point(, Touch.getY());
                }
                if (drawPointA != null && drawPointB != null && drawPointA.getX() != drawPointB.getX() && drawPointA.getY() != drawPointB.getY()) {
                    System.err.println("2 points sélectionnés A et B");
                    ImageViewSelection viewById = findViewById(R.id.currentImageView);
                    viewById.setDrawingRect(new RectF((float) drawPointA.getX(), (float) drawPointA.getY(), (float) drawPointB.getX(), (float) drawPointB.getY()));
                    viewById.setDrawingRectState(true);
                    System.err.println(viewById.getDrawingRect().toString());
                    //viewById.draw(new Canvas());
                    // Désélectionner A&B
                    //drawPointA = null;


                    // PixM zone
                    currentPixM = getSelectedZone();

                    if (currentPixM != null) {
                        System.err.println("Draw Selection");
                        imageView.setImageBitmap(currentPixM.getImage().bitmap);
                        if (clipboard == null && Clipboard.defaultClipboard == null) {
                            clipboard = Clipboard.defaultClipboard
                                    = new Clipboard(currentPixM);
                        } else if (Clipboard.defaultClipboard != null && clipboard != null) {
                            if (clipboard == null)
                                clipboard = Clipboard.defaultClipboard;
                            BufferedImage read = ImageIO.read(currentFile);
                            clipboard.setDestination(viewById.getDrawingRect());
                            //rectfs.get(rectfs.size() - 1));
                        }
                        System.err.println("Selection drawn");
                    } else {
                        System.err.println("current PixM == null");

                    }
                }


            } else toastButtonDisabled(v);
            return true;
        });

        //Select rectangle toggle
        View unselect = findViewById(R.id.unselect_rect);
        unselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFile != null) {
                    BufferedImage read = ImageIO.read(currentFile);
                    imageView.setImageBitmap(read.getBitmap());
                    drawPointA = null;
                    drawPointB = null;
                } else toastButtonDisabled(v);
            }
        });

        View addText = findViewById(R.id.buttonAddText);
        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addText(view);
            }
        });


        if (!isLoaded()) {
            loadImageState(isWorkingResolutionOriginal());
        }

    }


    private boolean isLoaded() {
        return loaded;
    }

    public void saveImageState(boolean imageViewOriginal) {
        boolean file = true;

        imageView = findViewById(R.id.currentImageView);
        if (imageView == null) return;

        Drawable drawable = imageView.getDrawable();

        Bitmap bitmapOriginal = null;
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable)
            bitmapOriginal = ((BitmapDrawable) drawable).getBitmap();
        else if (drawable.getCurrent() instanceof BitmapDrawable) {
            if (isWorkingResolutionOriginal()) {
                bitmapOriginal = ((BitmapDrawable) drawable.getCurrent()).getBitmap();
            }
            bitmap = PixM.getPixM(bitmap, getMaxRes()).getBitmap();
        } else {
            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
            } else {
                // ???
                if (isWorkingResolutionOriginal()) {
                    bitmapOriginal = PixM.getPixM(bitmap, 0).getBitmap();
                }
                bitmap = bitmapOriginal = PixM.getPixM(bitmap, getMaxRes()).getBitmap();
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, getMaxRes() == 0 ? canvas.getWidth() : getMaxRes(),
                    getMaxRes() == 0 ? canvas.getHeight() : getMaxRes());
            drawable.draw(canvas);
        }

        Bitmap bm = null;
        if (bitmap != null) {
            bm = bitmap;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 90, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();

            String encoded = Base64.encodeToString(b, Base64.DEFAULT);
            OutputStream fos = null;
            try {
                File filesFile = getFilesFile(IMAGE_VIEW_JPG);
                fos = new FileOutputStream(filesFile);
                bm.compress(Bitmap.CompressFormat.JPEG, 90, fos);

                System.err.println("Image updated");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (imageViewOriginal && bitmapOriginal != null) {
            bm = bitmapOriginal;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 90, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();

            String encoded = Base64.encodeToString(b, Base64.DEFAULT);
            OutputStream fos = null;
            File filesFile = getFilesFile(IMAGE_VIEW_ORIGINAL_JPG);
            try {
                fos = new FileOutputStream(filesFile);
                bm.compress(Bitmap.CompressFormat.JPEG, 90, fos);

                System.err.println("Image updated");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


    }

    int getImageRatio(Bitmap bitmap) {
        return bitmap.getWidth() / bitmap.getHeight();
    }

    Point setMaxResImage(Bitmap bitmap) {
        int imageRatio = getImageRatio(bitmap);
        Point point = new Point(getMaxRes() / imageRatio,
                getMaxRes() * imageRatio);
        return point;
    }

    private int getMaxRes() {
        EditText maxResText = findViewById(R.id.editMaximiumResolution);
        maxRes = (int) Double.parseDouble(maxResText.getText().toString());
        return maxRes;
    }

    public void loadImageState(boolean originalImage) {
        boolean file = true;
        String ot = "";
        File imageFile = getFilesFile("imageViewOriginal.jpg");
        File imageFileLow = getFilesFile(IMAGE_VIEW_JPG);

        if (file && imageFile.exists()) {
            try {
                Bitmap imageViewBitmap = null;
                if (isWorkingResolutionOriginal()) {
                    imageViewBitmap = BitmapFactory.decodeStream(new FileInputStream(imageFile));
                } else {
                    imageViewBitmap = BitmapFactory.decodeStream(new FileInputStream(imageFileLow));
                }

                if (imageViewBitmap != null) {
                    imageView = (ImageView) this.findViewById(R.id.currentImageView);
                    if (imageView != null) {
                        imageView.setImageBitmap(imageViewBitmap);
                        currentFile = imageFile;
                        currentBitmap = imageFile;

                        System.err.println("Image reloaded");

                        //createCurrentUniqueFile();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    private void createCurrentUniqueFile() {

        try {
            if (currentFile != null) {
                Bitmap photo = BitmapFactory.decodeStream(new FileInputStream(currentFile));
                System.err.println("Get file (bitmap) : " + photo);

                File myPhotoV2022 = writePhoto(photo, "MyPhotoV2022" + UUID.randomUUID());
                System.err.println("Written copy : " + myPhotoV2022.getAbsolutePath());
                //photo.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(currentFile));
                fillGallery(photo, new FileInputStream(myPhotoV2022));
                System.err.println("Set in ImageView : " + myPhotoV2022.getAbsolutePath());

                currentFile = myPhotoV2022;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private File getFilesFile(String s) {
        return new File("/storage/emulated/0/Android/data/one.empty3.feature.app.maxSdk29.pro/files/" + File.separator + s);
    }


    private PixM getSelectedZone() {
        if (currentFile != null) {
            PixM pixM = PixM.getPixM(Objects.requireNonNull(ImageIO.read(currentFile)), maxRes);

            if (drawPointA == null || drawPointB == null) {
                return null;
            }

            double xr = 1.0 / imageView.getWidth() * pixM.getColumns();
            double yr = 1.0 / imageView.getHeight() * pixM.getLines();

            int x1 = (int) Math.min(drawPointA.getX() * xr, drawPointB.getX() * xr);
            int x2 = (int) Math.max(drawPointA.getX() * xr, drawPointB.getX() * xr);
            int y1 = (int) Math.min(drawPointA.getY() * yr, drawPointB.getY() * yr);
            int y2 = (int) Math.max(drawPointA.getY() * yr, drawPointB.getY() * yr);
            PixM copy = pixM.copySubImage(x1, y1, x2 - x1, y2 - y1);

            System.err.printf("Copied rect = (%d, %d, %d, %d)\n", x1, y1, x2 - x1, y2 - y1);

            if (copy != null) {
                // currentFileZoomedBitmap = copy.getImage().getBitmap();
            }
            RectF rectF = new RectF(x1, y1, x2, y2);

            rectfs.add(rectF);

            RectF localRectIn = getLocalRectIn(null);

            if (localRectIn != null) {

                System.err.printf("Local rect = (%f, %f, %f, %f)\n", localRectIn.left, localRectIn.top, localRectIn.right, localRectIn.bottom);
                drawPointB = null;
                drawPointA = null;
                return copy;
            } else {
                System.err.println("Error getLocalRectIn : returns null");

            }
        }
        return null;
    }

    public RectF getLocalRectIn(RectF current) {
        RectF originalComponentView = new RectF(0, 0, imageView.getWidth(), imageView.getHeight());
        //RectF destinationComponentView = originalComponentView;
        BufferedImage read = ImageIO.read(currentFile);
        if (rectfs.size() == 0) return current;
        int i = 0;

        current = originalComponentView;

        while (i < rectfs.size()) {
            //RectF originalImageRect = new RectF(0, 0, read.getWidth(), read.getHeight());
            //RectF newImageRect = rectfs.get(rectfs.size() - 1);
            RectF currentSubImage = rectfs.get(i);
            current = new RectF(current.left + currentSubImage.left / currentSubImage.width() * (current.width()), current.top + currentSubImage.top / currentSubImage.height() * (current.height()), current.right + currentSubImage.right / currentSubImage.width() * (current.width()), current.bottom + currentSubImage.bottom / currentSubImage.height() * (current.height()));
            i++;
        }
        return current;
    }

    private boolean checkPointCordinates(Point a) {
        int x = (int) a.getX();
        int y = (int) a.getY();
        if (x >= 0 && x < imageView.getWidth() && y >= 0 && y < imageView.getHeight()) {
            return true;
        } else {
            return false;
        }
    }

    private void save(Bitmap toSave) {
    }

    private void openUserData(View view) {
        saveImageState(isWorkingResolutionOriginal());
        Intent intent = new Intent(view.getContext(), LicenceUserData.class);
        startActivity(intent);
    }

    private void startCreation() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*.*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "video/*"});
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        Intent intent2 = Intent.createChooser(intent, "Choose a file");
        System.err.println(intent2);
        startActivityForResult(intent2, ONCLICK_STARTACTIVITY_CODE_PHOTO_CHOOSER);
    }

    private void startCreationMovie() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*.*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"video/*"});
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        Intent intent2 = Intent.createChooser(intent, "Choose a file");
        System.err.println(intent2);
        startActivityForResult(intent2, ONCLICK_STARTACTIVITY_CODE_VIDEO_CHOOSER);

    }

    public void fillGallery(Bitmap photo, InputStream fileInputStream) throws FileNotFoundException {
        if (photo == null) {
            photo = BitmapFactory.decodeStream(fileInputStream);
        }

        imageView = findViewById(R.id.currentImageView);

        imageView.setImageBitmap(photo);

        System.out.println("Image set in ImageView 4/4");

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {

            }
        }
    }

    /***
     * Write copy of original file in data folder
     * @param bitmap
     * @param name
     * @return
     */
    public File writePhoto(@NotNull Bitmap bitmap, String name) {

        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        int n = 1;
        //Folder is already created

        name = name + "-photo-" + UUID.randomUUID().toString();

        String dirName1 = "", dirName2 = "";
        dirName1 = Environment.getDataDirectory().getPath();
        dirName2 = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        n++;


        //startActivityForResult(camera, 1);
        File dir1 = new File(dirName1);
        File file1 = new File(dirName1 + File.separator + name + ".jpg");
        File dir2 = new File(dirName2);
        File file2 = new File(dirName2 + File.separator + name + ".jpg");

        Uri uriSavedImage = Uri.fromFile(file2);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

        if (!dir1.exists()) if (!dir1.mkdirs()) {
            System.err.print("Dir not created $dir1");
        }
        if (!dir2.exists()) if (!dir2.mkdirs()) {
            System.err.println("Dir not created $dir2");
        }
        try {
            if (!file1.exists()) {
                ImageIO.write(new BufferedImage(bitmap), "jpg", file1);
                System.err.print("Image written 1/2 " + file1 + " return");
                saveImageState(isWorkingResolutionOriginal());
                //System.err.println("File (photo) " + file1.getAbsolutePath());
                return file1;
            }
        } catch (Exception ex) {
            Log.e("SAVE FILE", "writePhoto: erreur file 1/2");
        }
        try {
            if (!file2.exists()) {
                ImageIO.write(new BufferedImage(bitmap), "jpg", file2);
                System.err.print("Image written 2/2 " + file2 + " return");
                //System.err.println("File (photo) " + file2.getAbsolutePath());
                saveImageState(isWorkingResolutionOriginal());
                return file2;
            }
        } catch (Exception ex) {
            Log.e("SAVE FILE", "writePhoto: erreur file 2/2");
        }
        return file1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getExtras() != null && data.getExtras().get("data") != null) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                Bitmap photo = bitmap;
                imageView = findViewById(R.id.currentImageView);
                imageView.setImageBitmap(photo);
                System.err.printf("Image set 4/4");
                File f = writePhoto(photo, "MyImage");

//                try {
//                    imageView.setBackground(Drawable.createFromStream(new FileInputStream(f), "chosenFile"));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
                if (f == null) {
                    System.err.println("Can't write copy image file from camera ");

                } else {
                    currentFile = f;
                    try {
                        fillGallery(bitmap, new FileInputStream(f));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }


                saveImageState(isWorkingResolutionOriginal());


                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root + "/saved_images");
                myDir.mkdirs();

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + UUID.randomUUID();
                String fname = "Shutta_" + timeStamp + ".jpg";


                final Bitmap finalBitmap;
                try {
                    finalBitmap = BitmapFactory.decodeStream(new FileInputStream(currentFile));
                    File file = new File(myDir, fname);
                    ImageIO.write(new BufferedImage(finalBitmap), "", file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == ONCLICK_STARTACTIVITY_CODE_VIDEO_CHOOSER && resultCode == Activity.RESULT_OK) {
            InputStream choose_directoryData = null;
            choose_directoryData = getRealPathFromURI(data);

            Drawable fromStream = AnimatedImageDrawable.createFromStream(choose_directoryData, "file" + UUID.randomUUID());

            Drawable current = fromStream.getCurrent();


            File videoFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/screenshots/", "myvideo.mp4");

            Uri videoFileUri = Uri.parse(videoFile.toString());

            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(videoFile.getAbsolutePath());
            ArrayList<Bitmap> rev = new ArrayList<Bitmap>();

            //Create a new Media Player
            MediaPlayer mp = MediaPlayer.create(getBaseContext(), videoFileUri);

            int millis = mp.getDuration();
            Bitmap bitmap;
            for (int i = 0; i < 10000000; i += 1) {
                bitmap = retriever.getFrameAtIndex(i);

                if (bitmap == null) break;
                rev.add(bitmap);
                try {
                    fillGallery(bitmap, choose_directoryData);
                    rev.remove(0);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        if (requestCode == ONCLICK_STARTACTIVITY_CODE_PHOTO_CHOOSER && resultCode == Activity.RESULT_OK) {
            InputStream choose_directoryData = null;
            choose_directoryData = getRealPathFromURI(data);
            Bitmap photo = null;
            System.err.println(choose_directoryData);
            ;
            photo = BitmapFactory.decodeStream(choose_directoryData);
            try {
                System.err.println("Get file (bitmap) : " + photo);

                File myPhotoV2022 = writePhoto(photo, "MyPhotoV2022");
                System.err.println("Written copy : " + myPhotoV2022.getAbsolutePath());
                //photo.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(currentFile));
                fillGallery(photo, new FileInputStream(myPhotoV2022));
                System.err.println("Set in ImageView : " + myPhotoV2022.getAbsolutePath());

                currentFile = myPhotoV2022;
                System.err.println("Set as class member");
                saveImageState(isWorkingResolutionOriginal());
                System.err.println("SaveImageState");

                //createCurrentUniqueFile();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            saveImageState(isWorkingResolutionOriginal());
        } else if (requestCode == 10000 && resultCode == Activity.RESULT_OK) {

        }
        if (requestCode == REQUEST_CREATE_DOCUMENT_SAVE_IMAGE && resultCode == Activity.RESULT_OK) {
            try {
                Uri uri = data.getData();

//                if (!currentFile.exists()) {

                FileInputStream inputStream = new FileInputStream(currentFile);


                int byteRead = -1;


                OutputStream output = getApplicationContext().getContentResolver().openOutputStream(uri);

                while ((byteRead = inputStream.read()) != -1) {
                    output.write(byteRead);
                }

                output.flush();
                output.close();

                inputStream.close();

//                }
//            else {
//                    Toast.makeText(getApplicationContext(), "Le fichier existe déjà", Toast.LENGTH_SHORT).show();
//                    System.out.println("Le fichier existe déjà");
//                }

            } catch (IOException e) {

                e.printStackTrace();
            }


        }
        if (requestCode == FILESYSTEM_WRITE_PICTURE && resultCode == Activity.RESULT_OK) {

            if (currentFile != null) {
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
                FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()), BuildConfig.APPLICATION_ID + ".provider", currentFile);
                Path myPath = Paths.get(path, "" + UUID.randomUUID() + currentFile.getName());
                String fileStr = currentFile.getName();
                if (myPath.toFile().exists()) {


                } else {
                    File dir = new File(currentDir + File.separator + "FeatureApp" + File.separator);
                    File file = new File(currentDir + File.separator + "FeatureApp" + File.separator + fileStr);

                    if (myPath.toFile().exists() && myPath.toFile().isDirectory()) {

                    }
                    if (new File(myPath.toFile().getParent()).isDirectory() && !new File(myPath.toFile().getParent()).exists()) {
                        File file1 = new File(myPath.toFile().getParent());
                        file1.mkdirs();
                    }
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    String ext = currentFile.getName().substring(currentFile.getName().lastIndexOf(".") + 1);
                    String type = mime.getMimeTypeFromExtension(ext);
                    file = myPath.toFile();
                    try {
                        copy(currentFile.toPath(), myPath);
                        Uri uri = Uri.fromFile(file);
                        uri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", file);
                        Intent intent1 = new Intent(Intent.ACTION_SEND, uri);
                        startActivity(intent1);
                        //MediaStore.EXTRA_OUTPUT
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                saveImageState(isWorkingResolutionOriginal());
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadImageState(isWorkingResolutionOriginal());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadImageState(isWorkingResolutionOriginal());
    }

    public void fillFromStorageState(File data) {
        InputStream choose_directoryData = null;
        try {
            choose_directoryData = new FileInputStream(data);
            Bitmap photo = null;
            photo = BitmapFactory.decodeStream(choose_directoryData);
            try {
                System.err.println("Get file (bitmap) : " + photo);

                File myPhotoV2022 = writePhoto(photo, "MyPhotoV2022");
                System.err.println("Written copy : " + myPhotoV2022.getAbsolutePath());
                //photo.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(currentFile));
                fillGallery(photo, new FileInputStream(myPhotoV2022));
                System.err.println("Set in ImageView : " + myPhotoV2022.getAbsolutePath());

                currentFile = myPhotoV2022;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            saveImageState(isWorkingResolutionOriginal());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {


        loadImageState(isWorkingResolutionOriginal());

        try {
            if (savedInstanceState.containsKey("maxRes")) {
                maxRes = savedInstanceState.getInt("maxRes") > -1 ?
                        savedInstanceState.getInt("maxRes") : MAX_RES_DEFAULT;
                //currentFile = new File((String) savedInstanceState.getString("currentFile"));
                //currentBitmap = new File((String) savedInstanceState.getString("currentBitmap"));
                //currentDir = new File((String) savedInstanceState.getString("currentDir"));
                if (currentBitmap != null) {
                    File bitmap = new File(currentFile.getAbsolutePath());
                    try {
                        Bitmap bitmap1 = BitmapFactory.decodeStream(new FileInputStream(bitmap));
//                try {
//                    imageView.setBackground(Drawable.createFromStream(new FileInputStream(bitmap), "chosenFile"));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
                        imageView.setImageBitmap(bitmap1);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                imageView = findViewById(R.id.currentImageView);
            }
        } catch (Exception e) {
            Log.i("MyCameraActivity", "Error in OnRestoreState");
        }


        this.imageView = (ImageView) this.findViewById(R.id.currentImageView);
        thisActivity = this;

        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        saveImageState(isWorkingResolutionOriginal());
        if (outState != null) {
            //outState.putString("currentFile", currentFile.getAbsolutePath());
            //outState.putString("currentBitmap", currentBitmap.getAbsolutePath());
            //outState.putString("currentDir", currentDir.getAbsolutePath());
            //outState.putString("currentImageViewFile", currentFile.getAbsolutePath());
            outState.putInt("maxRes", maxRes);
            this.imageView = (ImageView) this.findViewById(R.id.currentImageView);
        }
        super.onSaveInstanceState(outState, outPersistentState);

    }

    public void unselectA(View view) {
        drawPointA = null;
        drawPointB = null;
        ImageViewSelection imageView = findViewById(R.id.currentImageView);
        if (rectfs.size() >= 1) rectfs.remove(rectfs.size() - 1);
        imageView.setDrawingRectState(false);
    }

    public void drawActivity(View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), TextAndImages.class);
        Intent selection = intent.putExtra("selection", new Rect((int) drawPointA.getX(), (int) drawPointA.getY(), (int) drawPointB.getX(), (int) drawPointB.getY()));
    }

    @Override
    protected void onDestroy() {
        saveImageState(isWorkingResolutionOriginal());
        super.onDestroy();
    }

    public void addText(View view) {
        if (currentFile != null) {
            Intent textIntent = new Intent(Intent.ACTION_VIEW);
            textIntent.setDataAndType(Uri.fromFile(currentFile), "image/jpg");
            textIntent.setClass(getApplicationContext(), TextActivity.class);
            if (rectfs.size() > 0)
                textIntent.putExtra("rect", rectfs.size() > 0 ? rectfs.get(rectfs.size() - 1) : null);
            else
                textIntent.putExtra("rect", new Rect());
            startActivity(textIntent);
        }
    }

    public boolean isWorkingResolutionOriginal() {
        return workingResolutionOriginal;
    }

    public void setWorkingResolutionOriginal(boolean workingResolutionOriginal) {
        this.workingResolutionOriginal = workingResolutionOriginal;
    }
}

