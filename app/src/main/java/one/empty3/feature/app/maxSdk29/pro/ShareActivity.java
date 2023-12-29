package one.empty3.feature.app.maxSdk29.pro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Objects;

public class ShareActivity extends ActivitySuperClass {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            } else if (type.startsWith("image/")) {
                handleSendImage(intent); // Handle single image being sent
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendMultipleImages(intent); // Handle multiple images being sent
            }
        } else {
            // Handle other intents, such as being started from the home screen
        }
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            Intent text = new Intent(Intent.ACTION_PROCESS_TEXT);
            text.setClass(this.getApplicationContext(), TextActivity.class);
            Objects.requireNonNull(text.getExtras()).putCharArray("text", sharedText.toCharArray());
            passParameters(text);
        }
    }

    void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            Intent text = new Intent(Intent.ACTION_EDIT);
            text.setClass(this.getApplicationContext(), MyCameraActivity.class);
            passParameters(text);
        }
    }

    void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            Intent text = new Intent(Intent.ACTION_EDIT);
            text.setClass(this.getApplicationContext(), MyCameraActivity.class);
            passParameters(text);
        }
    }
}
