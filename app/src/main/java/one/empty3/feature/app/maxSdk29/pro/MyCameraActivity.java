package one.empty3.feature.app.maxSdk29.pro;

import static java.nio.file.Files.copy;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

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
import wseemann.media.BuildConfig;

public class MyCameraActivity extends Activity {
    private static final String TAG = "one.empty3.feature.app.maxSdk29.pro.MyCameraActivity";
    private static final Integer MAX_TARDINESS = 3000;
    private static final int MAX_RES_DEFAULT = 200;
    private final String appDataPath = "/one.empty3.feature.app.maxSdk29.pro/";
    private Activity thisActivity;
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
/*
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
                Bitmap photo = BitmapFactory.decodeStream(new FileInputStream(currentBitmap));
                System.err.println("Photo bitmap : " + currentBitmap.toURI() + "\nFile exists?" + currentBitmap.exists());
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
    */

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

    Bitmap loadImageFromPreferences() {
        Bitmap bitmap = null;

        SharedPreferences gm = getSharedPreferences("image", MODE_PRIVATE);
        if (gm != null) {
            String ot = gm.getString("workingImage", "");
            if (ot.length() > 0) {
                byte[] imageAsBytes = Base64.decode(ot.getBytes(), Base64.DEFAULT);
                ImageView image = (ImageView) this.findViewById(R.id.currentImageView);


                image.setImageBitmap(bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

                maxRes = Math.max(image.getWidth(), image.getHeight());
            }
        }


        return bitmap;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if ((savedInstanceState == null)) {
            maxRes = MAX_RES_DEFAULT;
        } else {
            maxRes = savedInstanceState.getInt("maxRes") > -1 ?
                    savedInstanceState.getInt("maxRes") : MAX_RES_DEFAULT;
        }
        setContentView(R.layout.main);
        imageView = (ImageView) this.findViewById(R.id.currentImageView);

        rectfs = new ArrayList<RectF>();

        loaded = false;

        Intent intent = getIntent();
        if (getIntent() != null && getIntent().getData() != null) {
            if (intent.getData() != null) {
                Uri data = intent.getData();
                currentFile = new File(data.getPath());
                System.err.println("File returned from effects' list = " + data);
                currentBitmap = currentFile;
                //var loadImageSmall = new LoadImage(currentFile, MAX_RES_DEFAULT).execute();
//                var loadImageNormal = new LoadImage(currentFile,
//                        getMaxRes() <= 0 ? MAX_RES_DEFAULT : maxRes).execute();
                loaded = true;

                Bitmap bitmap = ImageIO.read(currentFile).getBitmap();

                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                    saveImageState();
                    loaded = true;
                }
            }
        } else {
            System.err.println("intent data Main==null");
            Bitmap bitmap = loadImageFromPreferences();
            if (bitmap != null) {
                currentBitmap = currentFile = new Utils().writePhoto(this, bitmap, "prefs");
                loaded = true;
            }
        }


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
                imageView = findViewById(R.id.currentImageView);
                Intent intent = new Intent(Intent.ACTION_EDIT);
                System.err.println("Click on Effect button");
                if (currentFile != null || currentBitmap != null) {
                    if (currentFile == null) currentFile = currentBitmap.getAbsoluteFile();
                    try {
                        currentFile = currentBitmap = new Utils().writePhoto(thisActivity,
                                BitmapFactory.decodeStream(new FileInputStream(currentFile)),
                                "EffectOn");
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

            }
        });
        View fromFiles = findViewById(R.id.choosePhotoButton);

        fromFiles.setOnClickListener(v -> {
            startCreation();
        });
        View chooseMovie = findViewById(R.id.chooseMovie);
        chooseMovie.setOnClickListener(v -> {
            startCreationMovie();
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
                }
            }

        });
        shareView.setEnabled(true);
        View save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFile != null) {

                    saveImageState();

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
                    intentSave.putExtra(Intent.EXTRA_TITLE, "YOUR FILENAME.jpg");
                    //not needed, but maybe usefull
                    intentSave.setDataAndType(photoURI, "image/jpeg");
                    startActivityForResult(intentSave, REQUEST_CREATE_DOCUMENT_SAVE_IMAGE);


                }


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
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        imageView.setOnTouchListener((v, event) -> {
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
                        System.err.println("Selection drawn");
                    } else {
                        System.err.println("current PixM == null");

                    }
                }


            } else {
            }
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
                }
            }
        });

        View select = findViewById(R.id.selectRectangle);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFileZoomedBitmap != null) {
                    imageView.setImageBitmap(currentFileZoomedBitmap);
                    if (rectfs.size() > 0) {
                        RectF rectF = rectfs.get(rectfs.size() - 1);
                        drawPointA = new Point((int) rectF.left, (int) rectF.top);
                        drawPointB = new Point((int) rectF.right, (int) rectF.bottom);
                    }
                }
            }
        });

        if (!isLoaded()) {
            loadImageState();
        }
    }


    private boolean isLoaded() {
        return loaded;
    }

    public void saveImageState() {
        boolean file = true;

        imageView = findViewById(R.id.currentImageView);
        if (imageView == null) return;

        Drawable drawable = imageView.getDrawable();

        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) bitmap = ((BitmapDrawable) drawable).getBitmap();
        else if (drawable.getCurrent() instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable.getCurrent()).getBitmap();
        } else {
            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
            } else {
                if (getMaxRes() > 0) {
                    bitmap = Bitmap.createBitmap(
                            Math.min(drawable.getIntrinsicWidth(), getMaxRes()),
                            Math.min(drawable.getIntrinsicHeight(), getMaxRes()),
                            Bitmap.Config.ARGB_8888);
                } else {
                    bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                            Bitmap.Config.ARGB_8888);

                }
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }


        if (drawable == null) {

            return;
        }

        Bitmap bm = bitmap;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();

        String encoded = Base64.encodeToString(b, Base64.DEFAULT);
        SharedPreferences.Editor ed1 = getSharedPreferences("image", MODE_PRIVATE).edit();
        ed1.putString("workingImage", encoded);
        ed1.apply();
        OutputStream fos = null;
        try {
            fos = new FileOutputStream(getFilesFile("imageView.jpg"));
            bm.compress(Bitmap.CompressFormat.JPEG, 90, fos);

            System.err.println("Image updated");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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

    public void loadImageState() {
        boolean file = true;
        String ot = "";
        File imageFile = getFilesFile("imageView.jpg");

        if (file && imageFile.exists()) {
            try {
                Bitmap imageViewBitmap = BitmapFactory.decodeStream(new FileInputStream(imageFile));
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


        } else {

            SharedPreferences gm = getSharedPreferences("image", MODE_PRIVATE);
            ot = gm.getString("workingImage", "");
            if (ot.length() > 0) {
                byte[] imageAsBytes = Base64.decode(ot.getBytes(), Base64.DEFAULT);
                ImageView image = (ImageView) this.findViewById(R.id.currentImageView);


                image.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));


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
            PixM pixM = PixM.getPixM(ImageIO.read(currentFile), maxRes);

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
        saveImageState();
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
                saveImageState();
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
                saveImageState();
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


                saveImageState();


                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root + "/saved_images");
                myDir.mkdirs();

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + UUID.randomUUID();
                String fname = "Shutta_" + timeStamp + ".jpg";


                final Bitmap finalBitmap;
                try {
                    finalBitmap = BitmapFactory.decodeStream(new FileInputStream(currentFile));
                    File file = new File(myDir, fname);
                    if (file.exists()) file.delete();
                    FileOutputStream out = new FileOutputStream(file);
                    finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.flush();
                    out.close();
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
                saveImageState();
                System.err.println("SaveImageState");

                //createCurrentUniqueFile();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            saveImageState();
        } else if (requestCode == 10000 && resultCode == Activity.RESULT_OK) {

        }
        if (requestCode == REQUEST_CREATE_DOCUMENT_SAVE_IMAGE && resultCode == Activity.RESULT_OK) {
            try {
                Uri uri = data.getData(); //just as an example, I am writing a String to the Uri I received from the user: try {

                FileInputStream inputStream = new FileInputStream(currentFile);


                int byteRead = -1;

                OutputStream output = getApplicationContext().getContentResolver().openOutputStream(uri);

                while ((byteRead = inputStream.read()) != -1) {
                    output.write(byteRead);
                }

                output.flush();
                output.close();

                inputStream.close();

            } catch (IOException e) {

                e.printStackTrace();
            }


        }
        if (requestCode == FILESYSTEM_WRITE_PICTURE && resultCode == Activity.RESULT_OK) {

            if (currentFile != null) {
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
                FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()), BuildConfig.LIBRARY_PACKAGE_NAME + ".provider", currentFile);
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
                        uri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.LIBRARY_PACKAGE_NAME + ".provider", file);
                        Intent intent1 = new Intent(Intent.ACTION_SEND, uri);
                        startActivity(intent1);
                        //MediaStore.EXTRA_OUTPUT
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                saveImageState();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadImageState();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadImageState();
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
            saveImageState();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {


        loadImageState();

        try {
            if (savedInstanceState != null) {
                maxRes = savedInstanceState.getInt("maxRes") > -1 ?
                        savedInstanceState.getInt("maxRes") : 0;
                currentFile = new File((String) savedInstanceState.getString("currentFile"));
                currentBitmap = new File((String) savedInstanceState.getString("currentBitmap"));
                currentDir = new File((String) savedInstanceState.getString("currentDir"));
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
        saveImageState();
        if (outState != null) {
            outState.putString("currentFile", currentFile.getAbsolutePath());
            outState.putString("currentBitmap", currentBitmap.getAbsolutePath());
            outState.putString("currentDir", currentDir.getAbsolutePath());
            outState.putString("currentImageViewFile", currentFile.getAbsolutePath());
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
        saveImageState();
        super.onDestroy();
    }

    public void addText(View view) {
        try {
            Intent textIntent = new Intent(Intent.ACTION_VIEW);
            currentFile = currentBitmap = new Utils().writePhoto(thisActivity,
                    BitmapFactory.decodeStream(new FileInputStream(currentFile)),
                    "TextOn");

            textIntent.setDataAndType(Uri.fromFile(currentFile), "image/jpg");
            textIntent.setClass(getApplicationContext(), TextActivity.class);
            if (rectfs.size() > 0)
                textIntent.putExtra("rect", rectfs.size() > 0 ? rectfs.get(rectfs.size() - 1) : null);
            else textIntent.putExtra("rect", new Rect());
            startActivity(textIntent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

