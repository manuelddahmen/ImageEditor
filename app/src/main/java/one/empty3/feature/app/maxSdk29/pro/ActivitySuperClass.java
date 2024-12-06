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

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import matrix.PixM;
import one.empty3.libs.Image;


public class ActivitySuperClass extends AppCompatActivity {
    private PurchasesUpdatedListener purchasesUpdatedListener;

    private BillingClient billingClient;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;



    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    public static final String TAG = "one.empty3.feature.app.maxSdk29.pro";
    public static final int MAXRES_DEFAULT = 400;
    protected static final String[] cordsConsts = new String[]{"x", "y", "z", "r", "g", "b", "a", "t", "u", "v"};
    private static final int ONSAVE_INSTANCE_STATE = 21516;
    private static final int ONRETRIEVE_DEFAULT_CURRENTFILE = 242426;
    private static final int ONRESTORE_INSTANCE_STATE = 51521;
    public final String filenameSaveState = "state.properties";
    public final String imageViewFilename = "imageView.jpg";
    public final String imageViewFilenameProperties = "imageView.properties";
    public String appDataPath = "/one.empty3.feature.app.maxSdk29.pro/";
    public String variableName;
    public String variable;
    protected ImageViewSelection imageView;
    protected Undo currentFile = Undo.getUndo();
    protected String[] cords = new String[]{"x", "y", "z", "r", "g", "b", "a", "t", "u", "v"};
    protected Bitmap currentBitmap;
    protected int maxRes = R.string.maxRes_1200;
    private String userDisplayName;
    private String email;
    private Uri userPhotoUrl;
    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;
    private FirebaseAuth mAuth;

    public ImageViewSelection getImageView() {
        return imageView;
    }

    public void setImageView(ImageViewSelection imageView) {
        this.imageView = imageView;
    }

    public File getCurrentFile() {
        return currentFile.getCurrentFile();
    }

    public void setCurrentFile(File currentFile) {
        this.currentFile.add(new DataApp(currentFile));
    }

    public InputStream getPathInput(Uri uri) throws FileNotFoundException {
        InputStream input = getApplicationContext().getContentResolver().openInputStream(uri);
        return input;
    }

    protected File getRealPathFromIntentData(Intent file) {
        String realPathFromURIString = getRealPathFromURIString(file.getData());
        if (realPathFromURIString == null)
            realPathFromURIString = getRealPathFromURIString(file.getExtras().getParcelable(Intent.EXTRA_STREAM));
        return new File(realPathFromURIString);
    }

    protected InputStream getRealPathFromIntentData2(Intent file) {
        try {
            return getPathInput(file.getData());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected InputStream getRealPathFromURI(Uri uri) {
        try {
            return getPathInput(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        getParameters(intent);

        purchasesUpdatedListener = new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> purchases) {
                // To be implemented in a later section.
            }
        };

        billingClient = BillingClient.newBuilder(getApplicationContext())
                .setListener(purchasesUpdatedListener)
                // Configure other settings.
                .enablePendingPurchases()
                .build();

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() ==  BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                }
            }
            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });
        if (currentFile.getCurrentFile() == null) {
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
        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build())
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                // Automatically sign in when exactly one credential is retrieved.
                .setAutoSelectEnabled(true)
                .build();
//        new Utils().installReferrer(this);


        if (currentFile.getCurrentFile() == null && savedInstanceState != null) {
            try {
                if (savedInstanceState.getString("currentFile") != null) {
                    currentFile.add(new DataApp(new File(savedInstanceState.getString("currentFile"))));
                }
            } catch (RuntimeException ex) {
                ex.printStackTrace();
            }
        }
        if (imageView == null)
            imageView = findViewById(R.id.currentImageView);
        if (currentFile.getCurrentFile() != null) {
            testIfValidBitmap();
        } else
            loadInstanceState();

        try {
            if (currentFile.getCurrentFile() != null) {
                File currentFile1 = currentFile.getCurrentFile();
                if(currentFile1!=null && currentFile1.exists()) {
                    Image read = one.empty3.ImageIO.read(currentFile1);
                    if (read != null && read.getImage() != null) {
                        Bitmap bitmap = read.getImage();
                        currentFile.add(new DataApp(new Utils().writePhoto(
                                this, new Image(bitmap), "reload")));
                        loadImage(new FileInputStream(currentFile.getCurrentFile()), true);
                    }
                }
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>() {
                    @Override
                    public void onSuccess(BeginSignInResult result) {
                        try {
                            startIntentSenderForResult(
                                    result.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
                                    null, 0, 0, 0);
                        } catch (IntentSender.SendIntentException e) {
                            Log.e(TAG, "Couldn't start One Tap UI: " + e.getLocalizedMessage());
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // No saved credentials found. Launch the One Tap sign-up flow, or
                        // do nothing and continue presenting the signed-out UI.
                        Log.d(TAG, e.getLocalizedMessage());
                    }
                });
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            userDisplayName = user.getDisplayName();
            email = user.getEmail();
            userPhotoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        } else {

        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        /*requestPermissions(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES}, ONSAVE_INSTANCE_STATE);
*/
        saveInstanceState();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ONSAVE_INSTANCE_STATE && grantResults != null) {
            int g = 0;
            for (int granted : grantResults) {
                g = g + ((granted == PERMISSION_GRANTED) ? 1 : 0);
            }

            if (g > 0)
                saveInstanceState();
        }
        if (requestCode == ONRESTORE_INSTANCE_STATE && grantResults != null) {
            int g = 0;
            for (int granted : grantResults) {
                g = g + ((granted == PERMISSION_GRANTED) ? 1 : 0);
            }

            if (g > 0)
                restoreInstanceState();
        }

        if (requestCode == ONRETRIEVE_DEFAULT_CURRENTFILE && grantResults != null) {
            int g = 0;
            for (int granted : grantResults) {
                g = g + ((granted == PERMISSION_GRANTED) ? 1 : 0);
            }
            if (g > 0) ;

            retrieveCurrentFile();
        }
    }

    private void retrieveCurrentFile() {
        if (currentFile != null && currentFile.getCurrentFile()!=null && !currentFile.getCurrentFile().exists()) {
            //currentFile.addNull(null)
            /*Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),
                    R.drawable.apn512x512);
            if(drawable instanceof BitmapDrawable) {
                currentBitmap= ((BitmapDrawable) drawable).getBitmap();
            }*/
        }
        /*try {
            currentFile = getImageViewPersistantFile();
        } catch (RuntimeException zx){
            try {
                Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.apn512x512);
                if (drawable instanceof BitmapDrawable) {
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    try {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90,
                                new FileOutputStream(currentFile));
                        Toast.makeText(getApplicationContext(),
                                R.string.reference_to_file_that_does_t_exist_create_dummy_file,
                                Toast.LENGTH_LONG).show();
                    } catch (FileNotFoundException ignored) {
                        System.out.println("Error in retrieveCurrentfile cannot rewrite imageView.jpg");
                        Toast.makeText(getApplicationContext(),
                                R.string.reference_to_file_that_does_t_exist_create_dummy_file,
                                Toast.LENGTH_LONG).show();
                    }
                }
            } catch (RuntimeException exception) {
                System.out.println("Error in retrieveCurrentfile cannot rewrite imageView.jpg");
                Toast.makeText(getApplicationContext(),
                        R.string.reference_to_file_that_does_t_exist_create_dummy_file,
                        Toast.LENGTH_LONG).show();
            }

        }
*/

    }

    void restoreInstanceState() {
        new Utils().loadImageState(this, false);
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(getImageViewPersistantPropertiesFile()));
            for (int i = 0; i < cords.length; i++) {
                cords[i] = properties.getProperty(cordsConsts[i], cords[i]);
            }
            String maxRes1 = properties.getProperty("maxRes", "" + maxRes);
            if (maxRes1 != null) {
                try {
                    maxRes = Integer.parseInt(maxRes1);
                } catch (NumberFormatException ignored) {

                }
            }
            try {
                String currentFile1 = properties.getProperty("currentFile", currentFile.getCurrentFile().getAbsolutePath());
                currentFile.add(new DataApp(new File(currentFile1)));
                if (currentFile1 == null || currentFile1.length() == 0) {
                    File imageViewPersistantFile = getImageViewPersistantFile();
                    if (imageViewPersistantFile.exists()) {
                        currentFile.add(new DataApp(imageViewPersistantFile));
                    }
                }
            } catch (RuntimeException ex) {
                Toast.makeText(getApplicationContext(), "Error restoring currentFile", Toast.LENGTH_LONG)
                        .show();
            }
        } catch (IOException ignored) {

        }
    }

    public void testIfValidBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inPremultiplied = false;


        try {
        if (currentFile.getCurrentFile() == null)
            loadInstanceState();
        if (currentFile != null) {
            if (!currentFile.getCurrentFile().exists()) {
                return;
            }
                FileInputStream fileInputStream = new FileInputStream(currentFile.getCurrentFile());
                if (BitmapFactory.decodeStream(fileInputStream, null, options)
                        == null)
                    ;

        }

        } catch (FileNotFoundException e) {
            System.err.println("Error file:" + currentFile.getCurrentFile());
            //currentFile.addAtCurrentPlace(); = null;
        } catch (RuntimeException exception) {
            //currentFile.addNull(null)
        }
    }

    protected void loadInstanceState() {
        String currentFile1 = null;
        new Utils().loadImageState(this, false);
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(getImageViewPersistantPropertiesFile()));
        } catch (RuntimeException | IOException ex) {
            ex.printStackTrace();
        }
        try {
            for (int i = 0; i < cords.length; i++) {
                cords[i] = properties.getProperty(cordsConsts[i], cords[i]);
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        try {
            String maxRes1 = properties.getProperty("maxRes", "" + MAXRES_DEFAULT);
            if (maxRes1 != null && maxRes1.length() > 0) {
                try {
                    maxRes = (int) (Double.parseDouble(maxRes1));
                } catch (RuntimeException ex1) {
                    ex1.printStackTrace();

                }
            }
            currentFile1 = properties.getProperty("currentFile", (currentFile.getCurrentFile() == null ? null
                    : currentFile.getCurrentFile().getAbsolutePath()));
            if (currentFile1 != null)
                currentFile.add(new DataApp(new File(currentFile1)));
            else
                currentFile.add(new DataApp(getImageViewPersistantFile()));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }

        try {
            File currentFile2 = null;
            if (currentFile1 == null)
                currentFile2 = getImageViewPersistantFile();
            else
                currentFile2 = new File(currentFile1);
            if (currentFile2 != null && currentFile2.exists()) {
                currentFile.add(new DataApp(currentFile2));
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        if (currentFile.getCurrentFile() == null)
            Toast.makeText(getApplicationContext(), "Cannot find current file (working copy)", Toast.LENGTH_SHORT)
                    .show();
    }

    protected void saveInstanceState() {
        Properties properties = new Properties();
        File imageViewPersistantPropertiesFile = getImageViewPersistantPropertiesFile();
        try {
            properties.setProperty("maxRes", "" + maxRes);
            if (imageViewPersistantPropertiesFile.exists()) {
                try {
                    properties.load(new FileInputStream(imageViewPersistantPropertiesFile));
                } catch (IOException | RuntimeException ex) {
                    ex.printStackTrace();
                }
            } else {
            }

            for (int i = 0; i < cords.length; i++) {
                properties.setProperty(cordsConsts[i], cords[i]);
            }
            try {
                if (currentFile.getCurrentFile() != null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    options.inPremultiplied = false;
                    properties.setProperty("currentFile", currentFile.getCurrentFile().getAbsolutePath());
                    File file = new Utils().writeFile(this,
                            BitmapFactory.decodeStream(new FileInputStream(currentFile.getCurrentFile()), null, options),
                            getImageViewPersistantFile(), getImageViewPersistantFile(),
                            maxRes, true);
                    if (file != null)
                        currentFile.add(new DataApp(file));
                }
            } catch (FileNotFoundException ignored) {
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }


        try {
            properties.store(new FileOutputStream(getImageViewPersistantPropertiesFile()), "#" + new Date().toString());
        } catch (IOException ignored) {

        }
    }


    public void passParameters(Intent to) {

        if (currentFile.getCurrentFile() != null)
            to.setDataAndType(Uri.fromFile(currentFile.getCurrentFile()), "image/jpg");
        to.putExtra("maxRes", getMaxRes());
        new Utils().putExtra(to, cords, cordsConsts, variableName, variable);

        System.out.println("c className = " + this.getClass());
        System.out.println("m variableName = " + variableName);
        System.out.println("m variable =     " + variable);
        System.out.println("i variableName = " + to.getStringExtra("variableName"));
        System.out.println("i variable =     " + to.getStringExtra("variable"));
        System.out.println("i to.className = " + to.getComponent().getClassName());


        startActivity(to);
    }

    public void getParameters(Intent from) {
        Utils utils = new Utils();
        currentFile.add(new DataApp(utils.getCurrentFile(from, this)));
        maxRes = utils.getMaxRes(this);
        utils.loadImageInImageView(this);
        utils.loadVarsMathImage(this, getIntent());
        requestPermissions(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES
        }, ONRETRIEVE_DEFAULT_CURRENTFILE);
    }

    protected File getFilesFile(String s) {
        return new File("/storage/emulated/0/Android/data/one.empty3.feature.app.maxSdk29.pro/files/" + s);
    }

    @org.jetbrains.annotations.Nullable
    public File getImageViewPersistantFile() {
        return getFilesFile(imageViewFilename);
    }

    @org.jetbrains.annotations.Nullable
    public File getImageViewPersistantPropertiesFile() {
        return getFilesFile(imageViewFilenameProperties);
    }

    public boolean saveActivityProperties(Properties properties) {
        File filesFile = getFilesFile(this.getClass().getCanonicalName() + ".txt");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filesFile);
            properties.store(fileOutputStream, "Properties for activity: " + getClass());
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Properties loadActivityProperties(Properties properties) {
        File filesFile = getFilesFile(this.getClass().getCanonicalName() + ".txt");
        try {
            FileInputStream fileInputStream = new FileInputStream(filesFile);
            properties.load(fileInputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    void drawIfBitmap() {
        saveInstanceState();
        try {
            currentBitmap = null;
            if (imageView == null)
                imageView = findViewById(R.id.currentImageView);
            if (imageView != null && currentFile.getCurrentFile() != null)
                new Utils().setImageView2(this, imageView);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    public int getMaxRes() {
        return new Utils().getMaxRes(this);
    }

    public void setMaxRes(int maxRes) {
        this.maxRes = maxRes;
    }


    public Bitmap loadImage(InputStream choose_directoryData, boolean isCurrentFile) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inPremultiplied = false;
        imageView = findViewById(R.id.currentImageView);
        Bitmap photo = null;
        if (maxRes > 0) {
        System.err.println("FileInputStream" + choose_directoryData);
            photo = BitmapFactory.decodeStream(choose_directoryData, null, options);
            photo = matrix.PixM.getmatrix.PixM(photo, maxRes).getImage().getImage();
            System.err.println("Get file (bitmap) : " + photo);
        } else {
            System.err.println("FileInputStream" + choose_directoryData);
            photo = BitmapFactory.decodeStream(choose_directoryData, null, options);
            System.err.println("Get file (bitmap) : " + photo);
        }
        if (photo != null && isCurrentFile) {
            currentFile.add(new DataApp(new Utils().writePhoto(this, new Image(photo), "loaded_image-")));
            if (imageView != null)
                new Utils().setImageView(this, imageView);
            return photo;
        } else if (photo != null) {
            return photo;
        } else {
            System.err.println("file == null. Error.");
            throw new NullPointerException("File==null ActivitySuperClass, loadImage");
        }

    }

    public Bitmap loadImage(Bitmap photo, File choose_directoryData, boolean isCurrentFile) {
        try {
            return loadImage(new FileInputStream(choose_directoryData), isCurrentFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onDestroy() {
        saveInstanceState();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        new Utils().saveImageState(this);
        saveInstanceState();

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        requestPermissions(new String[]{
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.READ_MEDIA_IMAGES}, ONRESTORE_INSTANCE_STATE);
        new Utils().loadImageState(this, false);
        restoreInstanceState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case REQ_ONE_TAP:

                SignInCredential googleCredential = null;
                try {
                    googleCredential = oneTapClient.getSignInCredentialFromIntent(data);
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                }
                String idToken = googleCredential.getGoogleIdToken();
                if (idToken != null) {
                    // Got an ID token from Google. Use it to authenticate
                    // with Firebase.
                    AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
                    mAuth.signInWithCredential(firebaseCredential)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithCredential:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                                        updateUI(null);
                                    }
                                }
                            });
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


    private void updateUI(FirebaseUser user) {
        // No-op
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
        }
    }

    void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        //       InputStream realPathFromURI = getRealPathFromURI(imageUri);
        InputStream realPathFromURI = getRealPathFromURI(imageUri);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inPremultiplied = false;

        Bitmap bitmap = BitmapFactory.decodeStream(realPathFromURI, null, options);
        File file = new Utils().writePhoto(this, new Image(bitmap), "imported-" + UUID.randomUUID() + "--");
        if (file != null && file.exists()) {
            // Update UI to reflect image being shared
            setCurrentFile(file);
            try {
                InputStream realPathFromURIFile2 = new FileInputStream(file);
                loadImage(realPathFromURIFile2, true);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            for (Uri imageUri : imageUris) {
                if (imageUri != null) {
                    // Update UI to reflect image being shared
                    setCurrentFile(new File(imageUri.toString()));
                    try {
                        loadImage(new FileInputStream(new File(imageUri.toString())), true);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            // Update UI to reflect multiple images being shared

        }
    }

    public String getRealPathFromURIString(Uri contentURI) {
        String result;
        Cursor cursor = getApplicationContext().getContentResolver().query(contentURI, null,
                null, null, null);

        if (cursor == null) { // Source is Dropbox or other similar local file
            // path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            try {
                int idx = cursor
                        .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
            } catch (Exception e) {
               /* AppLog.handleException(ImageHelper.class.getName(), e);
                Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(
                        R.string.error_get_image), Toast.LENGTH_SHORT).show();
*/
                result = "";
            }
            cursor.close();
        }
        return result;
    }

}