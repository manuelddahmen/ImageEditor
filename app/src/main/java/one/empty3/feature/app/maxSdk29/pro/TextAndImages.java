/*
 * Copyright (c) 2023.
 *
 *
 */

package one.empty3.feature.app.maxSdk29.pro;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class TextAndImages extends Activity {
    private static final int ONCLICK_STARTACTIVITY_CODE_PHOTO_CHOOSER = 897987;
    private Bitmap photo = null;
    private File currentFile;
    private Bitmap workingBitmap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);


        setContentView(R.layout.text_image_chooser);

        if (savedInstanceState.getString("text") != null) {
            String text = savedInstanceState.getString("text");
        }
        if (savedInstanceState.getParcelable("bitmap") != null) {
            photo = savedInstanceState.getParcelable("bitmap");
        }
        if (savedInstanceState.getParcelable("workingBitmap") != null) {
            workingBitmap = savedInstanceState.getParcelable("workingBitmap");
        }

        findViewById(R.id.ok_button_add_element).setOnClickListener(new View.OnClickListener() {

                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            Intent intent = new Intent();
                                                                            intent.setClassName("one.empty3.feature.app.sdk29.pro",
                                                                                    "MyCameraActivity");
                                                                            intent.putExtra("bitmapToPaste", photo);
                                                                            intent.putExtra("textToPaste", ((TextView) findViewById(R.id.chooseText)).getText());
                                                                            startActivity(intent);
                                                                        }
                                                                    }
        );
        findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("one.empty3.feature.app.sdk29.pro",
                        "MyCameraActivity");
                //intent.putExtra("bitmapToPaste", photo);
                //intent.putExtra("textToPaste",((TextView)findViewById(R.id.chooseText)).getText());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        photo = savedInstanceState.getParcelable("bitmap");
        ((TextView) (findViewById(R.id.chooseText))).setText(savedInstanceState.getString("text"));
        workingBitmap = savedInstanceState.getParcelable("workingBitmap");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("text", ((TextView) (findViewById(R.id.chooseText))).getText().toString());
        outState.putParcelable("bitmap", photo);
        outState.getParcelable("workingBitmap");

    }

    private void startCreation() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*.*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "video/*"});
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        Intent intent2 = Intent.createChooser(intent, "Choose a file");
        System.out.println(intent2);
        startActivityForResult(intent2, ONCLICK_STARTACTIVITY_CODE_PHOTO_CHOOSER);
    }

    private InputStream getRealPathFromURI(Intent file) {
        try {
            return getPathInput(file.getData());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public InputStream getPathInput(Uri uri) throws FileNotFoundException {
        InputStream input = getContentResolver().openInputStream(uri);
        return input;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ONCLICK_STARTACTIVITY_CODE_PHOTO_CHOOSER && resultCode == Activity.RESULT_OK) {
            InputStream choose_directoryData = null;
            choose_directoryData = getRealPathFromURI(data);
            photo = null;
            System.out.println(choose_directoryData);
            photo = BitmapFactory.decodeStream(choose_directoryData);
        }
    }
}
