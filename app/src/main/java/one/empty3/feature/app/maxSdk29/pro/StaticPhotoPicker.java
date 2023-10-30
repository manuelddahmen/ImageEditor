package one.empty3.feature.app.maxSdk29.pro;

import android.app.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StaticPhotoPicker {
    public interface MyPickVisualMediaRequest  {

    }
    public static void pickUri(Activity activity) {
        ActivityResultLauncher<PickVisualMediaRequest> pickMedia = getPickVisualMediaRequestActivityResultLauncher();

// Include only one of the following calls to launch(), depending on the types
// of media that you want to let the user choose from.

// Launch the photo picker and let the user choose images and videos.
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageAndVideo.INSTANCE)
                .build());

// Launch the photo picker and let the user choose only images.
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());

// Launch the photo picker and let the user choose only videos.
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.VideoOnly.INSTANCE)
                .build());

// Launch the photo picker and let the user choose only images/videos of a
// specific MIME type, such as GIFs.
        String mimeType = "image/gif";
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(new ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeType))
                .build());
    }

    @Nullable
    private static ActivityResultLauncher<PickVisualMediaRequest> getPickVisualMediaRequestActivityResultLauncher() {
        MyPickVisualMediaRequest myPickVisualMediaRequest = new MyPickVisualMediaRequest() {
            @NonNull
            @Override
            public String toString() {
                return super.toString();
            }
        };
        // Registers a photo picker activity launcher in single-select mode.
        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(
                        new ActivityResultContracts.PickVisualMedia(), myPickVisualMediaRequest);
        return pickMedia;
    }

    private static ActivityResultLauncher<PickVisualMediaRequest> registerForActivityResult(ActivityResultContracts.PickVisualMedia pickVisualMedia, Object o) {
        return null;
    }
}
