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

package one.empty3.feature.app.maxSdk29.pro;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;
import static androidx.navigation.ui.NavigationUI.setupWithNavController;
import static com.google.firebase.database.collection.BuildConfig.APPLICATION_ID;
import static java.nio.file.Files.copy;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;

import one.empty3.ImageIO;
import matrix.PixM;
import one.empty3.libs.Color;
import one.empty3.library.Point;
import one.empty3.libs.Image;
import one.empty3.feature.app.maxSdk29.pro.ui.login.LoginActivity2;


public class MyCameraActivity extends ActivitySuperClass {
    private static final int INT_READ_MEDIA_IMAGES = 445165;
    public static final int TRANSPARENT = -1;
    Properties properties = new Properties();

    private static final String TAG = "one.empty3.feature.app.maxSdk29.pro.MyCameraActivity";
    static final int MAX_RES_DEFAULT = 200;
    public static final String IMAGE_VIEW_ORIGINAL_JPG = "imageViewOriginal.jpg";
    public static final String IMAGE_VIEW_JPG = "imageView.jpg";
    private final String appDataPath = "/one.empty3.feature.app.maxSdk29.pro/";
    protected ActivitySuperClass thisActivity;
    private static final int REQUEST_CREATE_DOCUMENT_SAVE_IMAGE = 4072040;
    static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int ONCLICK_STARTACTIVITY_CODE_VIDEO_CHOOSER = 9998;
    private static final int ONCLICK_STARTACTIVITY_CODE_PHOTO_CHOOSER = 9999;
    private static final int FILESYSTEM_WRITE_PICTURE = 1111;
    private static final int MY_EXTERNAL_STORAGE_PERMISSION_CODE = 7777;
    private File currentDir;
    private File currentFileOriginalResolution;

    private File currentFileZoomed;
    private boolean beta = false;
    public Point drawPointA = null;
    public Point drawPointB = null;
    public List<RectF> rectfs = new ArrayList<RectF>();
    private Bitmap currentFileZoomedBitmap;
    private matrix.PixM currentPixM = null;
    private boolean loaded;
    private boolean workingResolutionOriginal = false;
    private Clipboard clipboard;
    private boolean copied;
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.main);
        getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS;
        View view1 = findViewById(R.id.main_layout_xml);
        ViewCompat.setOnApplyWindowInsetsListener(view1, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures());
            // Apply the insets as padding to the view. Here, set all the dimensions
            // as appropriate to your layout. You can also update the view's margin if
            // more appropriate.
            view1.setPadding(insets.left, insets.top, insets.right, insets.bottom);

            // Return CONSUMED if you don't want the window insets to keep passing down
            // to descendant views.
            return WindowInsetsCompat.CONSUMED;
        });

/*
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        setupActionBarWithNavController(this,  navController, appBarConfiguration);
        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration);
*/
        String type = getIntent().getType();

        maxRes = new Utils().getMaxRes(this);

        imageView = this.findViewById(R.id.currentImageView);

        rectfs = new ArrayList<RectF>();

        loaded = true;


        thisActivity = this;


        currentDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);


        Button takePhoto = findViewById(R.id.takePhotoButton);

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = ContextCompat.checkSelfPermission(thisActivity.getApplicationContext(),
                        Manifest.permission.CAMERA);
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                if (checkSelfPermission(Manifest.permission.USE_FULL_SCREEN_INTENT) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.USE_FULL_SCREEN_INTENT}, MY_CAMERA_PERMISSION_CODE);
                }
                if (checkSelfPermission(Manifest.permission.USE_FULL_SCREEN_INTENT) != PackageManager.PERMISSION_GRANTED &&
                        checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }

        });

        Button effectsButton2 = this.findViewById(R.id.effectsButtonNew);
        effectsButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFile.getCurrentFile() != null) {
                    imageView = findViewById(R.id.currentImageView);
                    Intent intent1 = new Intent(getApplicationContext(), ChooseEffectsActivity2.class);

                    if (currentPixM != null) {
                        intent1.putExtra("zoom", currentPixM.getBitmap().getBitmap());
                    }
                    passParameters(intent1);

                }
            }
        });
// Put in menu
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
            if (currentFile.getCurrentFile() != null) {
                if (clipboard != null && clipboard.copied && clipboard.getDestination() != null
                        && clipboard.getSource() != null) {
                    matrix.PixM dest = new PixM(Objects.requireNonNull(ImageIO.read(currentFile.getCurrentFile())).getImage());

                    int x = (int) Math.min(clipboard.getDestination().right, clipboard.getDestination().left);
                    int y = (int) Math.min(clipboard.getDestination().bottom, clipboard.getDestination().top);
                    int w = (int) Math.abs(clipboard.getDestination().right - clipboard.getDestination().left);
                    int h = (int) Math.abs(clipboard.getDestination().bottom - clipboard.getDestination().top);
                    dest.pasteSubImage(clipboard.getSource(), x, y, w, h);
                    Bitmap bitmap = dest.getBitmap().getBitmap();
                    currentFile.add(new DataApp(new Utils().writePhoto(this, new Image(bitmap), "copy_paste")));
                    new Utils().setImageView(imageView, bitmap);
                    paste.setBackgroundColor(Color.rgb(40, 255, 40));
                    copy.setBackgroundColor(Color.rgb(40, 255, 40));
                    Toast.makeText(getApplicationContext(), R.string.subimage_pasted, Toast.LENGTH_SHORT)
                            .show();

                    System.err.println("Image copiée: " + clipboard.getSource().getColumns() + " " + clipboard.getSource().getLines());
                    System.err.println("Zone de collage: " + "x:" + x + ", y:" + y + " w:" + w + " h:" + h);


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
                if (currentFile != null && currentFile.getCurrentFile() != null && currentFile.getCurrentFile().exists()) {
                    Uri uri = Uri.fromFile(currentFile.getCurrentFile());
                    Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", currentFile.getCurrentFile());
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                    sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    sendIntent.putExtra(Intent.EXTRA_STREAM, photoURI);
                    sendIntent.setDataAndType(photoURI, "image/jpeg");
                    sendIntent.putExtra("data", photoURI);

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);
                } else toastButtonDisabled(v);
            }

        });
        shareView.setEnabled(true);
        View save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (currentFile != null && currentFile.getCurrentFile() != null && currentFile.getCurrentFile().exists()) {

                    String[] permissionsStorage = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_MEDIA_IMAGES};
                    int requestExternalStorage = 1;
                    int permission1 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                    int permission2 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    int permission3 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_MEDIA_IMAGES);
                    if (permission1 != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(thisActivity, permissionsStorage, requestExternalStorage);
                    }
                    if (permission2 != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(thisActivity, permissionsStorage, requestExternalStorage);
                    }
                    if (permission3 != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(thisActivity, permissionsStorage, requestExternalStorage);
                    }

                    File picturesDirectory = new File(String.valueOf(getExternalFilesDir(Environment.DIRECTORY_PICTURES)));
                    Path target = null;

                    try {
                        target = copy((currentFile.getCurrentFile()).toPath(), new File(picturesDirectory.getAbsolutePath() + File.separator + UUID.randomUUID() + ".jpg").toPath());
                    } catch (IOException e) {
                        e.printStackTrace();

                        return;
                    }


                    Uri photoURI = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        photoURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", (target == null) ? currentFile.getCurrentFile() : target.toFile());
                    }


                    //ActivityResultContracts.CreateDocument createDocument = new ActivityResultContracts.CreateDocument("image/jpeg");
                    Intent intentSave = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                    //        createDocument.createIntent(getApplicationContext(), "Save as new image");


                    //send an ACTION_CREATE_DOCUMENT intent to the system. It will open a dialog where the user can choose a location and a filename
                    intentSave.addCategory(Intent.CATEGORY_OPENABLE);
                    intentSave.putExtra(Intent.EXTRA_TITLE, "photo-" + UUID.randomUUID() + ".jpg");
                    intentSave.setDataAndType(photoURI, "image/jpeg");
                    //createDocument.parseResult(new ²);
                    startActivityForResult(intentSave, REQUEST_CREATE_DOCUMENT_SAVE_IMAGE);

                    saveImageState(true);


                } else toastButtonDisabled(v);


            }


        });

        //Draw activity (pass: rectangle, image, image view size.
        View face = findViewById(R.id.buttonFace);

        face.setOnClickListener(view -> {
            if (currentFile != null) {

                Intent faceIntent = new Intent(Intent.ACTION_VIEW);

                faceIntent.setClass(getApplicationContext(), FaceActivity.class);

                if (currentPixM != null) {
                    faceIntent.putExtra("zoom", currentPixM.getBitmap().getBitmap());
                }

                passParameters(faceIntent);

            }
        });
        Button computePixels = findViewById(R.id.activity_compute_pixels);
        computePixels.setOnClickListener(v -> {
            if (currentFile.getCurrentFile() != null) {
                Uri uri = Uri.fromFile(currentFile.getCurrentFile());
                //Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getApplicationContext().getPackageName() + ".provider", currentFile);
                Intent intentDraw = new Intent(getApplicationContext(), GraphicsActivity.class);
                intentDraw.setDataAndType(uri, "image/jpeg");
                intentDraw.putExtra("data", uri);
                passParameters(intentDraw);
            } else toastButtonDisabled(v);
        });

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        imageView.setOnTouchListener((View v, MotionEvent event) -> {
            imageView = findViewById(R.id.currentImageView);
            if(imageView==null)
                return false;
            if (currentFile != null) {
                int[] location = new int[2];
                v.getLocationOnScreen(location);
                int viewX = location[0];
                int viewY = location[1];
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
                    if(viewById==null)
                        return false;
                    File currentFile1 = currentFile.getCurrentFile();
                    if (currentFile1 == null || !currentFile1.exists())
                        return false;
                    Image read = one.empty3.ImageIO.read(currentFile1);
                    if (read == null)
                        return false;
                    Bitmap bitmap = read.getImage();
                    if(bitmap==null)
                        return false;
                    RectF rectF = getSelectedCordsImgToView(bitmap, viewById);
                    viewById.setDrawingRect(rectF);
                    viewById.setDrawingRectState(true);
                    System.err.println(viewById.getDrawingRect().toString());
                    if (rectF != null) {
                        currentPixM = getSelectedZone(getSelectedCordsImgToView(bitmap, viewById));

                        if (currentPixM != null) {
                            System.err.println("Draw Selection");
                            new Utils().setImageView(imageView, currentPixM.getImage().getImage());
                            if (clipboard == null && Clipboard.defaultClipboard == null) {
                                clipboard = Clipboard.defaultClipboard
                                        = new Clipboard(currentPixM);

                            }
                            if (Clipboard.defaultClipboard != null && clipboard != null) {
                                if (clipboard.copied) {
                                    clipboard.setDestination(rectF);
                                    drawPointA = null;
                                    drawPointB = null;
                                } else {
                                    clipboard.setSource(currentPixM);
                                }
                            }
                            System.err.println("Selection drawn");
                        } else {
                            System.err.println("current matrix.PixM == null");

                        }
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
                unselectReloadCurrentFile();
            }
        });
        View addText = findViewById(R.id.buttonAddText);
        addText.setOnClickListener(this::addText);
        View settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsScrollingActivity.class);
                passParameters(settingsIntent);
            }
        });
        /*Button loginButton = findViewById(R.id.myButton);
        loginButton.setOnClickListener(view -> {
            Intent login = new Intent();
            login.setClass(getApplicationContext(), LoginActivity2.class);
            startActivity(login);
        });*/
        Button undo = findViewById(R.id.undo);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Undo.getUndo().back();
                unselectReloadCurrentFile();
            }
        });
        Button redo = findViewById(R.id.redo);
        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Undo.getUndo().next();
                unselectReloadCurrentFile();
            }
        });


        unselectReloadCurrentFile();
    }

    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private RectF getSelectedCordsImgToView(Bitmap bitmap, ImageView imageView) {
        if (currentFile != null) {
            PixM pixM = new PixM(Objects.requireNonNull(ImageIO.read(currentFile.getCurrentFile())).getImage());

            if (drawPointA == null || drawPointB == null) {
                return null;
            }

            double xr = 1.0 / imageView.getWidth() * pixM.getColumns();
            double yr = 1.0 / imageView.getHeight() * pixM.getLines();

            int x1 = (int) Math.min(drawPointA.getX() * xr, drawPointB.getX() * xr);
            int x2 = (int) Math.max(drawPointA.getX() * xr, drawPointB.getX() * xr);
            int y1 = (int) Math.min(drawPointA.getY() * yr, drawPointB.getY() * yr);
            int y2 = (int) Math.max(drawPointA.getY() * yr, drawPointB.getY() * yr);

            return new RectF(x1, y1, x2, y2);

        }
        return null;
    }

    private RectF getSelectedCordsViewToImg(ImageView imageView, RectF rectF) {
        if (currentFile.getCurrentFile() != null) {
            int x1 = (int) Math.min(rectF.left, rectF.right);
            int x2 = (int) Math.max(rectF.left, rectF.right);
            int y1 = (int) Math.min(rectF.bottom, rectF.top);
            int y2 = (int) Math.max(rectF.bottom, rectF.top);

            double xr = 1.0 / (1.0 * imageView.getWidth() / (x2 - x1));
            double yr = 1.0 / (1.0 * imageView.getHeight() / y2 - y1);


            return new RectF(x1, y1, x2 - x1, y2 - y1);

        }
        return null;
    }

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
                if (file != null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    options.inPremultiplied = false;
                    Bitmap photo = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
                    System.err.println("Photo bitmap : " + file.toURI() + "\nFile exists?" + file.exists());
                    new Utils().setImageView(imageView, photo);
                    //imageView.setBackground(Drawable.createFromStream(new FileInputStream(currentBitmap), "chosenImage"));
                    System.err.println("Image main intent loaded");
                    //saveImageState();
                    var maxRes = resolution;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public OutputStream getPathOutput(Uri uri) throws FileNotFoundException {
        OutputStream output = getApplicationContext().getContentResolver().openOutputStream(uri);
        return output;
    }

//
//    protected InputStream getRealPathFromIntentData(Intent file) {
//        try {
//            String realPathFromURIString = getRealPathFromURIString(file.getData());
//            File file1 = new File(realPathFromURIString);
//            return new FileInputStream(file1);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public void toastButtonDisabled(View button) {
        if (currentFile.getCurrentFile() == null) {
            String text = getString(R.string.button_current_file_is_null);
            Integer duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();
        }
    }

    private boolean isLoaded() {
        return loaded;
    }

    public void saveImageState(boolean imageViewOriginal) {
        boolean file = true;

        if (imageView == null) return;

        Drawable drawable = imageView.getDrawable();

        Bitmap bitmapOriginal = null;
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            bitmapOriginal = ((BitmapDrawable) drawable).getBitmap();
            bitmap = bitmapOriginal;
        } else if (drawable.getCurrent() instanceof BitmapDrawable) {
            if (isWorkingResolutionOriginal()) {
                bitmapOriginal = ((BitmapDrawable) drawable.getCurrent()).getBitmap();
                bitmap = PixM.getPixM(bitmapOriginal, getMaxRes()).getBitmap().getBitmap();
            }
        } else {
            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
            } else {
                // ???
                if (isWorkingResolutionOriginal()) {
                    bitmapOriginal = PixM.getPixM(bitmap, 0).getBitmap().getBitmap();
                }
                if (bitmap != null) {
                    bitmap = bitmapOriginal = PixM.getPixM(bitmap, getMaxRes()).getBitmap().getBitmap();
                }
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
                File filesFile = getImageViewPersistantFile();
                fos = new FileOutputStream(filesFile);
                bm.compress(Bitmap.CompressFormat.JPEG, 90, fos);

                System.err.println("Image updated");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (RuntimeException e) {
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

    int getImageRatio(@NonNull Bitmap bitmap) {
        return bitmap.getWidth() / bitmap.getHeight();
    }

    Point setMaxResImage(Bitmap bitmap) {
        int imageRatio = getImageRatio(bitmap);
        Point point = new Point(getMaxRes() / imageRatio,
                getMaxRes() * imageRatio);
        return point;
    }


    public void loadImageState(boolean originalImage) {
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES}, 0);

        boolean file = true;
        String ot = "";
        File imageFile = getFilesFile("imageViewOriginal.jpg");
        File imageFileLow = getFilesFile(IMAGE_VIEW_JPG);

        if (file && imageFile != null && imageFile.exists()) {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                options.inPremultiplied = false;
                Bitmap imageViewBitmap = null;
                if (isWorkingResolutionOriginal()) {

                    imageViewBitmap = BitmapFactory.decodeStream(new FileInputStream(imageFile), null, options);
                } else if(imageFileLow!=null && imageFileLow.exists()){
                    imageViewBitmap = BitmapFactory.decodeStream(new FileInputStream(imageFileLow), null, options);
                } else
                    return;

                if (imageViewBitmap != null) {
                    if (imageView != null) {

                        new Utils().setImageView(imageView, imageViewBitmap);
                        currentFile.add(new DataApp(imageFile));
                        System.err.println("Image reloaded");

                        currentFile.add(new DataApp(new Utils().createCurrentUniqueFile(this)));
                    }
                }
            } catch (FileNotFoundException | NullPointerException e) {
                e.printStackTrace();
            }


        }
    }


    @Nullable
    private matrix.PixM getSelectedZone(RectF selectedCords) {
        if (currentFile != null) {
            try {
                PixM pixM = new PixM(Objects.requireNonNull(one.empty3.ImageIO.read(currentFile.getCurrentFile())));

                return pixM.copySubImage((int) (selectedCords.left), (int) (selectedCords.top),
                        (int) (selectedCords.right - selectedCords.left),
                        (int) (selectedCords.bottom - selectedCords.top));
            } catch (RuntimeException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public RectF getLocalRectIn(RectF current) {
        RectF originalComponentView = new RectF(0, 0, imageView.getWidth(), imageView.getHeight());
        //RectF destinationComponentView = originalComponentView;
        Image read = one.empty3.ImageIO.read(currentFile.getCurrentFile());
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

    private void openUserData(@NonNull View view) {
        //saveImageState(isWorkingResolutionOriginal());
        Intent intent = new Intent(Intent.ACTION_SHOW_APP_INFO);
         intent.setClass(view.getContext(), LicenceUserData2.class);

        passParameters(intent);
    }

    /***Dépendances des modules
     Ce module DocumentsUI dépend de l'autorisation MANAGE_DOCUMENTS protégée par l'autorisation de signature ; une classe d'autorisation supplémentaire garantit qu'une seule application sur l'appareil dispose de l'autorisation MANAGE_DOCUMENTS .
     *
     */

    private void startCreation() {


        requireWriteTempFilePermission();


            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*"});
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
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inPremultiplied = false;
            photo = BitmapFactory.decodeStream(fileInputStream, null, options);
        }
        final Bitmap p2 = photo;

        new Utils().setImageView(imageView, p2);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults!=null && grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

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
    public File writePhoto(@NonNull Bitmap bitmap, String name) {


        boolean shouldOverwrite = true;

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

        if (dir1 != null && !dir1.exists()) if (!dir1.mkdirs()) {
            System.err.print("Dir not created $dir1");
        }
        if (dir2 != null && !dir2.exists()) if (!dir2.mkdirs()) {
            System.err.println("Dir not created $dir2");
        }
        try {
            if (file1 != null && !file1.exists()) {
                one.empty3.ImageIO.write(new Image(bitmap), "jpg", file1, shouldOverwrite);
                System.err.print("Image written 1/2 " + file1 + " return");
                saveImageState(isWorkingResolutionOriginal());
                //System.err.println("File (photo) " + file1.getAbsolutePath());
                return file1;
            }
        } catch (Exception ex) {
            Log.e("SAVE FILE", "writePhoto: erreur file 1/2");
        }
        try {
            if (file2 != null && !file2.exists()) {
                one.empty3.ImageIO.write(new Image(bitmap), "jpg", file2, shouldOverwrite);
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

    /*
        @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
        public boolean requestPermissionAppStorage() {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_IMAGES
            )
                    != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_MEDIA_IMAGES
                    )
                            != PackageManager.PERMISSION_GRANTED
            ) {

                requestPermissions(
                        new String[]{
                                Manifest.permission.READ_MEDIA_IMAGES}, INT_READ_MEDIA_IMAGES);

                return (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_MEDIA_IMAGES
                )
                        != PackageManager.PERMISSION_GRANTED);
            } else return true;
        }
    */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getExtras() != null && data.getExtras().get("data") != null) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                Bitmap photo = bitmap;

                imageView = findViewById(R.id.currentImageView);

                if (photo != null && imageView != null) {

                    new Utils().setImageView(imageView, photo);

                    System.err.printf("Image set 4/4");


                    currentFile.add(new DataApp(new Utils().writePhoto(this, new Image(photo), "camera-")));

                }
            }


        }
        if (requestCode == ONCLICK_STARTACTIVITY_CODE_VIDEO_CHOOSER && resultCode == Activity.RESULT_OK) {
            InputStream choose_directoryData = null;
            try {
                choose_directoryData = new FileInputStream(getRealPathFromURIString(data.getData()));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

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
            Bitmap bitmap = null;
            for (int i = 0; i < 100; i += 1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    bitmap = retriever.getFrameAtIndex(i);
                }

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
            imageView = findViewById(R.id.currentImageView);


            //DownloadImageTask downloadImageTask = new DownloadImageTask((ImageViewSelection) findViewById(R.id.currentImageView));

            //AsyncTask<String, Void, Bitmap> execute = downloadImageTask.execute(getRealPathFromURI(data).toString());

            InputStream choose_directoryData = null;
            choose_directoryData = getRealPathFromIntentData2(data);
            if (choose_directoryData == null) {
                try {
                    String choose_directoryDataFile = getRealPathFromURIString(data.getData());
                    choose_directoryData = new FileInputStream(choose_directoryDataFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return;
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                    return;
                }
            }


            loadImage(choose_directoryData, true);

        } else if (requestCode == 10000 && resultCode == Activity.RESULT_OK) {

        }
        if (requestCode == REQUEST_CREATE_DOCUMENT_SAVE_IMAGE && resultCode == Activity.RESULT_OK) {
            try {
                Uri uri = data.getData();

//                if (!currentFile.exists()) {

                FileInputStream inputStream = new FileInputStream(currentFile.getCurrentFile());


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

            if (currentFile.getCurrentFile() != null) {
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
                FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()), APPLICATION_ID + ".provider", currentFile.getCurrentFile());
                Path myPath = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    myPath = Paths.get(path, "" + UUID.randomUUID() + currentFile.getCurrentFile().getName());
                }
                String fileStr = currentFile.getCurrentFile().getName();
                if (myPath != null && myPath.toFile() != null && myPath.toFile().exists()) {


                } else {
                    File dir = new File(currentDir + File.separator + "FeatureApp" + File.separator);
                    File file = new File(currentDir + File.separator + "FeatureApp" + File.separator + fileStr);

                    if (myPath.toFile() != null && myPath.toFile().exists() && myPath.toFile().isDirectory()) {

                    }
                    if (new File(myPath.toFile().getParent()).isDirectory() && !new File(myPath.toFile().getParent()).exists()) {
                        File file1 = new File(myPath.toFile().getParent());
                        file1.mkdirs();
                    }
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    String ext = currentFile.getCurrentFile().getName().substring(currentFile.getCurrentFile().getName().lastIndexOf(".") + 1);
                    String type = mime.getMimeTypeFromExtension(ext);
                    file = myPath.toFile();
                    try {
                        copy(currentFile.getCurrentFile().toPath(), myPath);
                        Uri uri = Uri.fromFile(file);
                        uri = FileProvider.getUriForFile(getApplicationContext(), APPLICATION_ID + ".provider", file);
                        Intent intent1 = new Intent(Intent.ACTION_SEND, uri).setClassName(/* TODO: provide the application ID. For example: */ getPackageName(), "one.empty3.feature.app.maxSdk29.pro.VisualFaceEffects");
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

    private boolean requireWriteTempFilePermission() {
            requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, 2621621);
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2621621);
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED&&
                 checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED)
            return true;
        else
            return false;
    }

    public void fillFromStorageState(File data) {
        InputStream choose_directoryData = null;
        try {
            choose_directoryData = new FileInputStream(data);
            Bitmap photo = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inPremultiplied = false;
            photo = BitmapFactory.decodeStream(choose_directoryData, null, options);
            try {
                System.err.println("Get file (bitmap) : " + photo);

                File myPhotoV2022 = writePhoto(photo, "MyPhotoV2022");
                System.err.println("Written copy : " + myPhotoV2022.getAbsolutePath());
                //photo.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(currentFile));
                fillGallery(photo, new FileInputStream(myPhotoV2022));
                System.err.println("Set in ImageView : " + myPhotoV2022.getAbsolutePath());

                currentFile.add(new DataApp(myPhotoV2022));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            saveImageState(isWorkingResolutionOriginal());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void unselectA(View view) {
        drawPointA = null;
        drawPointB = null;
        ImageViewSelection imageView = findViewById(R.id.currentImageView);
        if (rectfs.size() >= 1) rectfs.remove(rectfs.size() - 1);
        imageView.setDrawingRectState(false);
    }


    public void addText(View view) {
        if (currentFile != null && imageView != null && currentFile.getCurrentFile() != null && currentFile.getCurrentFile().exists()) {
            Intent textIntent = new Intent(Intent.ACTION_VIEW);
            textIntent.setDataAndType(Uri.fromFile(currentFile.getCurrentFile()), "image/jpg");
            textIntent.setClass(getApplicationContext(), TextActivity.class);
            textIntent.putExtra("currentFile", currentFile.getCurrentFile());
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

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (currentFile != null)
            saveInstanceState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentFile == null)
            loadInstanceState();
    }


    private void unselectReloadCurrentFile() {
        try {
            if (currentFile.getCurrentFile() != null && currentFile.getCurrentFile().exists()) {
                Image read = one.empty3.ImageIO.read(currentFile.getCurrentFile());
                if (read != null && read.getImage() != null) {
                    new Utils().setImageView(imageView, read.getImage());
                }
                drawPointA = null;
                drawPointB = null;
            } else toastButtonDisabled(null);
        } catch (RuntimeException ex) {
            currentFile.addNull(null);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return navController.navigateUp()
                || super.onSupportNavigateUp();
    }}


