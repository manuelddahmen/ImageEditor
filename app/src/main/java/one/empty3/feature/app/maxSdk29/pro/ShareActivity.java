package one.empty3.feature.app.maxSdk29.pro;

import android.content.ContentProviderClient;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

import javaAnd.awt.image.imageio.ImageIO;

public class ShareActivity extends ActivitySuperClass {
    private Button share;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.get_share_activity);
        share = findViewById(R.id.buttonWaitSharedImage);
        Intent intent = getIntent();
        String type = intent.getType();
        String action = intent.getAction();
        if (type!=null &&type.startsWith("image/")) {

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intentMain = new Intent(getApplicationContext(), MyCameraActivity.class);

                    passParameters(intentMain);

                }
            });
            if (Intent.ACTION_SEND.equals(action)) {
                if ("image/*".equals(type)) {
                    // Handle received image
                    Uri imageUri = getIntent().getParcelableExtra(Intent.EXTRA_STREAM);
                    handleSendImage(imageUri);
                }
            }
        }

    }

    void handleSendImage(Uri imageUri) {

        ContentProviderClient r = getApplicationContext().getContentResolver().acquireContentProviderClient(imageUri);

        Intent shareIntent = new Intent(getApplicationContext(), MyCameraActivity.class);

            Bitmap bitmap = BitmapFactory.decodeStream( getRealPathFromURI(imageUri));

            File imageViewPersistantFile = getImageViewPersistantFile();
            ImageIO.write(bitmap, "jpg", imageViewPersistantFile);
            currentFile = imageViewPersistantFile;
            currentBitmap = bitmap;
            saveInstanceState();



        // Create an intent to share the image
        shareIntent.setDataAndType(imageUri, "image/jpg");
        // Add a subject to the sharing message (optional)
        share.setText(R.string.go);

        passParameters(shareIntent);
/*
        startActivity(Intent.createChooser(shareIntent, "Share Image via"));
        File currentFile1 = getIntent().getParcelableExtra(Intent.EXTRA_STREAM);
        if (currentFile != null || currentFile1!=null) {
            currentFile = currentFile1;
            currentFile =
                    new Utils().writeFile(this, BitmapFactory.decodeFile(currentFile1.getAbsolutePath()),
                            getImageViewPersistantFile(), currentFile1, 0, true);

            Intent intent2 = new Intent(getApplicationContext(), MyCameraActivity.class);
            intent2.putExtra("currentFile", currentFile);
            intent2.putExtra("data", currentFile );
            intent2.putExtra(Intent.EXTRA_STREAM, currentFile.getAbsolutePath());
            intent2.setDataAndType(Uri.fromFile(currentFile1), "image/jpg");
            startActivity(intent2);

            Toast.makeText(getApplicationContext(), "Go home and load image...", Toast.LENGTH_LONG).show();
        }
*/
    }
}
