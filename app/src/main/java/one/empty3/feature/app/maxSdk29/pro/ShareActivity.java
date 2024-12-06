package one.empty3.feature.app.maxSdk29.pro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import one.empty3.ImageIO;

public class ShareActivity extends ActivitySuperClass {
    private Button share;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.get_share_activity);
        share = findViewById(R.id.buttonWaitSharedImage);
        Intent intent = getIntent();
        String type = intent.getType();
        String action = intent.getAction();
        if (type != null && type.startsWith("image/")) {

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intentMain = new Intent(getApplicationContext(), MyCameraActivity.class);

                    passParameters(intentMain);

                }
            });
            if ("image/*".equals(type)) {
                share.setText((R.string.go));
                // Handle received image
                Uri imageUri = getIntent().getParcelableExtra(Intent.EXTRA_STREAM);
                handleSendImage(imageUri);
            }
        }

    }

    void handleSendImage(Uri imageUri) {
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inPremultiplied = false;
            // Works with content://, file://, or android.resource:// URIs
            InputStream inputStream =
                    getContentResolver().openInputStream(imageUri);
            bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            bitmap.setHasAlpha(false); ;
            File imageViewPersistantFile = getImageViewPersistantFile();
            one.empty3.ImageIO.write(bitmap, "jpg", imageViewPersistantFile);

            currentFile.add(new DataApp(imageViewPersistantFile));
            currentBitmap = bitmap;
            one.empty3.ImageIO.write(bitmap, "jpg", imageViewPersistantFile);
            saveInstanceState();
            Intent shareIntent = new Intent(getApplicationContext(), MyCameraActivity.class);
            passParameters(shareIntent);
        } catch (FileNotFoundException e) {
            // Inform the user that things have gone horribly wrong
        }

    }
}
