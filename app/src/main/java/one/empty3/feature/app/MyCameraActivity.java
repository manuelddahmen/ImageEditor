package one.empty3.feature.app;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import one.empty3.feature.app.replace.javax.imageio.ImageIO;

public class MyCameraActivity extends Activity {
    private static final int CAMERA_REQUEST = 1888;
    private static final int PICK_REQUEST_CODE = 0;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private File currentFile = null;
    private View gallery;
    private File currentDir = getFilesDir();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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
    private void startCreation(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);

        Uri startDir = Uri.fromFile(currentDir==null?new File("/sdcard"):currentDir);

        intent.setDataAndType(startDir,
                "vnd.android.cursor.dir/lysesoft.andexplorer.file");
        intent.putExtra("browser_filter_extension_whitelist", "*.csv");
        intent.putExtra("explorer_title", getText(R.string.andex_file_selection_title));
        intent.putExtra("browser_title_background_color",
                getText(R.string.browser_title_background_color));
        intent.putExtra("browser_title_foreground_color",
                getText(R.string.browser_title_foreground_color));
        intent.putExtra("browser_list_background_color",
                getText(R.string.browser_list_background_color));
        intent.putExtra("browser_list_fontscale", "120%");
        intent.putExtra("browser_list_layout", "2");

        startActivityForResult(intent, PICK_REQUEST_CODE);
    }

    public void fillGallery() {
        File folder = new File(Environment.getExternalStorageDirectory().getPath() + "/aaaa/");
        File[] allFiles = folder.listFiles();
        ArrayList<View> views = new ArrayList<>();
        for (int i = 0; i < allFiles.length; i++) {
            ImageView imageView = new ImageView(this);
            views.add(imageView);
            imageView.setImageBitmap(ImageIO.read(allFiles[i]));
        }
        gallery = findViewById(R.id.imageTakenPreviewGallery);
        gallery.addTouchables(views);
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

    public File writePhoto(Bitmap bitmap, String name) {

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
            if(!dir1.exists())
                if(!dir1.mkdirs()) {
                    System.err.println("Error creating dir = " + dir1.getAbsolutePath());
                    return null;
                }
            if(!dir2.exists())
                if(!dir2.mkdirs()) {
                    System.err.println("Error creating dir = " + dir2.getAbsolutePath());
                    return null;
                }
            ImageIO.write(bitmap, "jpg", file1);
            ImageIO.write(bitmap, "jpg", file2);
            System.out.println("File (photo) " + file1.getAbsolutePath());
            System.out.println("File (photo) " + file2.getAbsolutePath());
            return file2;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
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
        } else if (requestCode == PICK_REQUEST_CODE && resultCode==Activity.RESULT_OK) {
            fillGallery();
        }
    }
}