/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
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

import static androidx.navigation.Navigation.findNavController;
import static one.empty3.feature.app.maxSdk29.pro.MyCameraActivity.CAMERA_REQUEST;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import javaAnd.awt.Point;
import javaAnd.awt.image.BufferedImage;
import javaAnd.awt.image.imageio.ImageIO;
import one.empty3.feature20220726.PixM;

public class NavigationAndActionImplementation {
    private int maxRes;
    private ImageView imageView;
    private ArrayList<RectF> rectfs;
    private boolean loaded;
    private File currentFile;
    private File currentBitmap;
    private Activity thisActivity;
    private File currentDir;
    private int MY_CAMERA_PERMISSION_CODE;
    private Clipboard clipboard;
    private int REQUEST_CREATE_DOCUMENT_SAVE_IMAGE;
    private Point drawPointA;
    private Point drawPointB;
    private boolean currentFileZoomed;
    private PixM currentPixM;

    public void addButtonsListeners(Activity activity, AppData appData, Bundle savedInstanceState) {
        maxRes = new Utils().getMaxRes(activity, savedInstanceState);

        imageView = (ImageView) activity.findViewById(R.id.currentImageView);

        rectfs = new ArrayList<RectF>();

        loaded = true;


        currentFile = currentBitmap = new Utils().getCurrentFile(activity.getIntent());

        if (currentFile != null)
            if (new Utils().loadImageInImageView(currentFile, imageView)) loaded = true;


        thisActivity = activity;


        currentDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);


        Button takePhoto = activity.findViewById(R.id.takePhotoButton);

        if (takePhoto != null) {
            takePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    }
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    activity.startActivityForResult(cameraIntent, CAMERA_REQUEST);

                    Bundle bundle = new Bundle();

                    bundle.putString("currentFile", String.valueOf(currentFile));


                    findNavController(activity, R.id.currentFragmentViews)
                            .navigate(R.id.fragment_choose_effects, bundle);
                }

            });
        }


        Button effectsButton2 = activity.findViewById(R.id.effectsButtonNew);
        if (effectsButton2 != null) {
            effectsButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentFile != null) {
                        imageView = activity.findViewById(R.id.currentImageView);
                        Intent intent = new Intent(Intent.ACTION_EDIT);
                        System.err.println("Click on Effect button");
                        if (currentFile != null || currentBitmap != null) {
                            if (currentFile == null) currentFile = currentBitmap.getAbsoluteFile();
                            try {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                    currentFile = currentBitmap = new Utils().writePhoto(thisActivity, BitmapFactory.decodeStream(new FileInputStream(currentFile)), "EffectOn");
                                }
                                intent.setDataAndType(Uri.fromFile(currentFile), "image/jpg");
                                intent.setClass(imageView.getContext(), ChooseEffectsActivity2.class);
                                intent.putExtra("data", currentFile);
                                View viewById = activity.findViewById(R.id.editMaximiumResolution);
                                intent.putExtra("maxRes", (int) Double.parseDouble(((TextView) viewById).getText().toString()));
                                System.err.println("Start activity : EffectChoose");
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            activity.startActivity(intent);
                        } else {
                            System.err.println("No file assigned");
                            System.err.println("Can't Start activity : EffectChoose");
                        }
                    } else toastButtonDisabled(v);
                }
            });
        }

        View fromFiles = activity.findViewById(R.id.choosePhotoButton);
        if (fromFiles != null) {
            fromFiles.setOnClickListener(v -> {
                startCreation();
            });
        }
        View copy = activity.findViewById(R.id.copy);
        if (copy != null) {
            copy.setOnClickListener(v -> {
                if (clipboard != null) {
                    clipboard.copied = true;
                    copy.setBackgroundColor(Color.rgb(40, 255, 40));
                    Toast.makeText(activity.getApplicationContext(), "Subimage copied", Toast.LENGTH_SHORT).show();
                }
            });
        }
        View paste = activity.findViewById(R.id.paste);

        if (paste != null) {
            paste.setOnClickListener(v -> {
                clipboard = Clipboard.defaultClipboard;
                if (currentFile != null) {
                    if (clipboard == null && Clipboard.defaultClipboard != null)
                        clipboard = Clipboard.defaultClipboard;
                    if (clipboard != null && clipboard.copied && clipboard.getDestination() != null && clipboard.getSource() != null) {
                        PixM dest = PixM.getPixM(Objects.requireNonNull(ImageIO.read(currentFile)).bitmap);
                        if (rectfs.size() > 0)
                            clipboard.setDestination(rectfs.get(rectfs.size() - 1));
                        else {
                            return;
                        }
                        int x = Math.min((int) clipboard.getDestination().left, (int) clipboard.getDestination().right);
                        int y = Math.min((int) clipboard.getDestination().bottom, (int) clipboard.getDestination().top);
                        int w = Math.abs((int) clipboard.getDestination().right - (int) clipboard.getDestination().left);
                        int h = Math.abs((int) clipboard.getDestination().bottom - (int) clipboard.getDestination().top);
                        dest.pasteSubImage(clipboard.getSource(), x, y, w, h);
                        System.err.println("Destionation coord = " + clipboard.getDestination());
                        System.err.println("Theory Copied pixels = " + clipboard.getSource().getColumns() * clipboard.getSource().getLines());
                        Bitmap bitmap = dest.getBitmap();
                        currentBitmap = currentFile = new Utils().writePhoto(activity, bitmap, "copy_paste");
                        imageView.setImageBitmap(bitmap);
                        paste.setBackgroundColor(Color.rgb(40, 255, 40));
                        copy.setBackgroundColor(Color.rgb(40, 255, 40));
                        Toast.makeText(activity.getApplicationContext(), "Subimage pasted", Toast.LENGTH_SHORT).show();
                    }
                } else toastButtonDisabled(v);
            });
        }
        View about = activity.findViewById(R.id.About);
        if (about != null) {
            about.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openUserData(v);
                }
            });
        }
        View shareView = activity.findViewById(R.id.share);
        if (shareView != null) shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFile != null) {
                    Uri uri = Uri.fromFile(currentFile);
                    Uri photoURI = FileProvider.getUriForFile(activity.getApplicationContext(), activity.getApplicationContext().getPackageName() + ".provider", currentFile);
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, photoURI);
                    shareIntent.setDataAndType(photoURI, "image/jpeg");
                    shareIntent.putExtra("data", photoURI);
                    startActivity(shareIntent);
                } else toastButtonDisabled(v);
            }

        });
        if (shareView != null) shareView.setEnabled(true);
        View save = activity.findViewById(R.id.save);
        if (save != null) save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFile != null) {

                    saveImageState(isWorkingResolutionOriginal());

                    String[] permissionsStorage = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    int requestExternalStorage = 1;
                    int permission1 = ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                    int permission2 = ActivityCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (permission1 != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(thisActivity, permissionsStorage, requestExternalStorage);
                    }
                    if (permission2 != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(thisActivity, permissionsStorage, requestExternalStorage);
                    }

                    File picturesDirectory = new File(String.valueOf(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)));
                    Path target = null;

                    target = copy((currentFile).toPath(), new File(picturesDirectory.getAbsolutePath() + UUID.randomUUID() + ".jpg").toPath());

                    Uri photoURI = FileProvider.getUriForFile(activity.getApplicationContext(), activity.getApplicationContext().getPackageName() + ".provider", target == null ? currentFile : target.toFile());


                    Intent intentSave = new Intent(Intent.ACTION_CREATE_DOCUMENT);

                    //send an ACTION_CREATE_DOCUMENT intent to the system. It will open a dialog where the user can choose a location and a filename
                    intentSave.addCategory(Intent.CATEGORY_OPENABLE);
                    intentSave.putExtra(Intent.EXTRA_TITLE, "photo-" + UUID.randomUUID() + ".jpg");
                    intentSave.setDataAndType(photoURI, "image/jpeg");
                    activity.startActivityForResult(intentSave, REQUEST_CREATE_DOCUMENT_SAVE_IMAGE);


                } else toastButtonDisabled(v);


            }


        });


        //Draw activity (pass: rectangle, image, image view size.
        View draw_activity_button = activity.findViewById(R.id.draw_activity);
        //draw_activity_button.setEnabled(false);
        if (draw_activity_button != null) {
            draw_activity_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (drawPointA != null && drawPointB != null) {
                        Intent intentDraw = new Intent(Intent.ACTION_CHOOSER);
                        intentDraw.setClass(activity.getApplicationContext(), TextAndImages.class);
                        intentDraw.putExtra("drawRectangle", new Rect((int) drawPointA.x, (int) drawPointA.y, (int) drawPointB.x, (int) drawPointB.y));
                        intentDraw.putExtra("currentFile", currentFile);
                        intentDraw.putExtra("currentFileZoomed", currentFileZoomed);
                        startActivity(intentDraw);
                    }
                }
            });
        }
        Button computePixels = activity.findViewById(R.id.activity_compute_pixels);

        if (computePixels != null) {
            computePixels.setOnClickListener(v -> {
                if (currentFile != null) {
                    Uri uri = Uri.fromFile(currentFile);
                    //Uri photoURI = FileProvider.getUriForFile(activity.getApplicationContext(), getApplicationContext().getApplicationContext().getPackageName() + ".provider", currentFile);
                    Intent intentDraw = new Intent(Intent.ACTION_EDIT);
                    intentDraw.setDataAndType(uri, "image/jpeg");
                    intentDraw.putExtra("currentFile", currentFile);
                    intentDraw.putExtra("maxRes", new Utils().getMaxRes(activity, savedInstanceState));
                    intentDraw.putExtra("data", uri);
                    intentDraw.setClass(activity.getApplicationContext(), GraphicsActivity.class);
                    startActivity(intentDraw);
                } else toastButtonDisabled(v);
            });
        }
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                }
            });

            imageView.setOnTouchListener((View v, MotionEvent event) -> {
                imageView = activity.findViewById(R.id.currentImageView);
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
                        ImageViewSelection viewById = activity.findViewById(R.id.currentImageView);
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
                            if (clipboard == null && Clipboard.defaultClipboard == null) {
                                clipboard = Clipboard.defaultClipboard = new Clipboard(currentPixM);
                            } else if (Clipboard.defaultClipboard != null && clipboard != null) {
                                if (clipboard == null) clipboard = Clipboard.defaultClipboard;
                                BufferedImage read = ImageIO.read(currentFile);
                                clipboard.setDestination(viewById.getDrawingRect());
                                //rectfs.get(rectfs.size() - 1));
                            }
                            System.err.println("Selection drawn");
                        } else {
                            System.err.println("current PixM == null");

                        }
                    }


                } else toastButtonDisabled(v);
                return true;
            });
        }

        //Select rectangle toggle
        View unselect = activity.findViewById(R.id.unselect_rect);
        if (unselect != null) unselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFile != null) {
                    BufferedImage read = ImageIO.read(currentFile);
                    imageView.setImageBitmap(read.getBitmap());
                    drawPointA = null;
                    drawPointB = null;
                } else toastButtonDisabled(v);
            }
        });

        View addText = activity.findViewById(R.id.buttonAddText);
        if (addText != null) addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addText(view);
            }
        });

        View openNewUI = activity.findViewById(R.id.new_layout_app);

        if (openNewUI != null) openNewUI.setOnClickListener(view -> {
            Intent intent2 = new Intent();
            intent2.setClass(activity.getApplicationContext(), MainActivity.class);
            startActivity(intent2);
        });


        if (!isLoaded()) {
            loadImageState(isWorkingResolutionOriginal());
        }


    }

    private Path copy(Path toPath, Path toPath1) {
        return null;
    }

    private PixM getSelectedZone() {
        return null;
    }

    private boolean checkPointCordinates(Point p) {
        return false;
    }

    private void saveImageState(boolean workingResolutionOriginal) {

    }

    private void openUserData(View v) {
    }

    private void addText(View view) {
    }

    private void startActivity(Intent intent2) {
    }

    private void loadImageState(boolean workingResolutionOriginal) {

    }

    private boolean isWorkingResolutionOriginal() {
        return false;
    }

    private void startCreation() {

    }

    private boolean isLoaded() {
        return loaded;
    }

    private void toastButtonDisabled(View v) {
    }
}
