package one.empty3.feature.app;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilderFactory;

import one.empty3.feature.app.replace.javax.imageio.ImageIO;

public class MyCameraActivity extends Activity {
    private static final int CAMERA_REQUEST = 1888;
    private static final int PICK_REQUEST_CODE = 0;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private File currentFile = null;
    private Gallery gallery;
    private File currentDir;

    private String getRealPathFromURI(Intent file) {
        /*
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }*/
        String original = file.getDataString();
        System.out.println("Original : "+original);
        String replace = original.replace("content:/com.android.externalstorage.documents/document/primary",
                "/Android/0");
        //replace.replace()
        System.out.println("replace:" + replace);
        return replace;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        currentDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        this.imageView = (ImageView) this.findViewById(R.id.currentImageView);
        Button photoButton = (Button) this.findViewById(R.id.takePhotoButton);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }

        });

        Button effectsButton = (Button) this.findViewById(R.id.effectsButton);
        effectsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFile != null) {
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    System.err.println("Cick on Effect button");
                    intent.setDataAndType(Uri.fromFile(currentFile),
                            "image/jpg");
                    intent.setClass(imageView.getContext(),
                            one.empty3.feature.app.ChooseEffectsActivity.class);
                    intent.putExtra("data", currentFile);
                    startActivity(intent);
                }
            }
        });
        if (getIntent() != null && getIntent().getData() != null) {
            currentFile = new File(String.valueOf(intent.getExtras().get("data")));
            Bitmap photo = ImageIO.read(currentFile);
            imageView.setImageBitmap(photo);

        }
        Gallery gallery = findViewById(R.id.imageTakenPreviewGallery);

        Button fromFiles = findViewById(R.id.choosePhotoButton);

        fromFiles.setOnClickListener(v -> {
            startCreation();
        });
    }

    private void startCreation() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*.*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "video/*"});
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        Intent intent2 = Intent.createChooser(intent, "Choose a file");
        System.out.println(intent2);
        startActivityForResult(intent2, 9999);
    }

    public void fillGallery(Bitmap photo, Intent data) {
        String src = getRealPathFromURI(data);
        System.out.println("Replaced : "+src);
        File file = new File(src);
        File[] allFiles = new File[]{file};
        ArrayList<View> views = new ArrayList<>();

        for (int i = 0; i < Objects.requireNonNull(allFiles).length; i++) {
            //views.add(imageView);
            Bitmap read = ImageIO.read(allFiles[i]);
            imageView.setImageBitmap(read);
            currentFile = allFiles[i];
        }
        gallery = findViewById(R.id.imageTakenPreviewGallery);
        gallery.addTouchables(views);

        File myFilePicture = writePhoto(photo, "MyFilePicture");

        currentFile = myFilePicture;
        imageView.setImageBitmap(photo);

        Toast.makeText(this, "File " + myFilePicture.toString() + " added", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    public File writePhoto(@NotNull Bitmap bitmap, String name) {

        Intent camera = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        int n = 1;
        //Folder is already created
        String dirName1 = "", dirName2 = "";
        do {
            dirName1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath()
                    + "/FeatureApp/data/" + name + "_" + n + ".jpg";
            dirName2 = "/storage/emulated/0/Android/media/one.empty3.feature.app/"//+getFilesDir()
                    + "/data/" + name + "_" + n + ".jpg";
            n++;
        } while (new File(dirName1).exists() || new File(dirName2).exists());


        //startActivityForResult(camera, 1);
        File dir1 = new File(dirName1.substring(0, dirName1.lastIndexOf(File.separator)));
        File file1 = new File(dirName1);
        File dir2 = new File(dirName2.substring(0, dirName2.lastIndexOf(File.separator)));
        File file2 = new File(dirName2);

        Uri uriSavedImage = Uri.fromFile(file2);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

        try {
            // Make sure the Pictures directory exists.
            if (!dir1.exists())
                if (!dir1.mkdirs()) {
                    System.err.println("Error creating dir = " + dir1.getAbsolutePath());
                    return null;
                }
            if (!dir2.exists())
                if (!dir2.mkdirs()) {
                    System.err.println("Error creating dir = " + dir2.getAbsolutePath());
                    return null;
                }
            ImageIO.write(bitmap, "jpg", file1);
            ImageIO.write(bitmap, "jpg", file2);
            System.out.println("File (photo) " + file1.getAbsolutePath());
            System.out.println("File (photo) " + file2.getAbsolutePath());
            return file2;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getExtras() != null && data.getExtras().get("data") != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(photo);
                File f = writePhoto(photo, "MyImage");
                if (f == null) {
                    System.err.println("Can't write file");

                } else {
                    currentFile = f;
                }
            }
        } else if (requestCode == 9999 && resultCode == Activity.RESULT_OK) {
            String choose_directoryData = data.getDataString();
            Bitmap photo = null;
            try {
                System.out.println(choose_directoryData);
                photo = BitmapFactory.decodeStream(new FileInputStream(choose_directoryData));
                currentFile = new File(choose_directoryData);
                fillGallery(photo, data);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Error request " + requestCode, Toast.LENGTH_LONG).show();
        }
    }
}
