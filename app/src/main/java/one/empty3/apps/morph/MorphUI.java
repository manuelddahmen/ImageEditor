///*
// * Copyright (c) 2022-2023. Manuel Daniel Dahmen
// *
// *
// *    Copyright 2012-2023 Manuel Daniel Dahmen
// *
// *    Licensed under the Apache License, Version 2.0 (the "License");
// *    you may not use this file except in compliance with the License.
// *    You may obtain a copy of the License at
// *
// *        http://www.apache.org/licenses/LICENSE-2.0
// *
// *    Unless required by applicable law or agreed to in writing, software
// *    distributed under the License is distributed on an "AS IS" BASIS,
// *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *    See the License for the specific language governing permissions and
// *    limitations under the License.
// */
//
///*
// * Created by JFormDesigner on Sat Dec 10 12:00:49 CET 2022
// */
//
//package one.empty3.apps.morph;
//
//import javaAnd.awt.image.BufferedImage;
//import one.empty3.feature.app.maxSdk29.pro.ActivitySuperClass;
//import one.empty3.library.*;
//import one.empty3.library.core.nurbs.ParametricSurface;
//import one.empty3.library.core.nurbs.SurfaceParametriquePolynomiale;
//import one.empty3.library.core.tribase.Plan3D;
//import javaAnd.awt.*;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.UUID;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// * @author Manuel Dahmen
// */
//public class MorphUI extends ActivitySuperClass {
//    private final StructureMatrix<Point3D> gridUV1;
//    private final StructureMatrix<Point3D> gridUV2;
//    private final StructureMatrix<Point3D> grid1;
//    private final StructureMatrix<Point3D> grid2;
//    ImageControls imageControl1;
//    ImageControls imageControl2;
//    private UUID uuid;
//    private boolean computing = false;
//    private File currentDirectory;
//    private File image1;
//    private File image2;
//    private BufferedImage imageRead1;
//    private BufferedImage imageRead2;
//    private ZBuffer zBuffer1;
//    private ZBuffer zBuffer2;
//    private ZBufferImpl zBufferComputing;
//    private ITexture text1;
//    private ITexture text2;
//    private String vid1;
//    private String vid2;
//    private VideoDecoder instance1;
//    private VideoDecoder instance2;
//    private int finalResY;
//    private int finalResX;
//    private File saveFile;
//    private File currentProjectDirectory;
//    private int shapeType;
//
//    public MorphUI() {
////
//
//        initComponents();
//
//        grid2 = new StructureMatrix<Point3D>(2, Point3D.class);
//        grid1 = new StructureMatrix<Point3D>(2, Point3D.class);
//        gridUV2 = new StructureMatrix<Point3D>(2, Point3D.class);
//        gridUV1 = new StructureMatrix<Point3D>(2, Point3D.class);
//    }
//
//
//    private void initGrids(StructureMatrix<Point3D> grid, StructureMatrix<Point3D> gridUV,
//                           BufferedImage imageRead) {
//        grid.reset();
//        gridUV.reset();
//
//        try {
//            if (imageRead != null) {
//                int resX = imageRead.getWidth();
//                int resY = imageRead.getHeight();
//
//                int i1 = Integer.parseInt(textFieldResX.getText());
//                int j1 = Integer.parseInt(textFieldResY.getText());
//                for (int i = 0; i <= i1; i++)
//                    for (int j = 0; j <= j1; j++) {
//                        grid.setElem(Point3D.n(1.0 * i / i1 * resX, 1.0 * j / j1 * resY, 0d), i, j);
//                        gridUV.setElem(Point3D.n(1.0 * i / i1, 1.0 * j / j1, 0d), i, j);
//                    }
//            }
//        } catch (NumberFormatException ex) {
//            ex.printStackTrace();
//        } catch (NullPointerException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private void buttonLoadImage1(ActionEvent e) {
//        boolean isLoaded = false;
//        JFileChooser jFileChooser = new JFileChooser();
//        jFileChooser.setFileFilter(new FileFilter() {
//            @Override
//            public boolean accept(File f) {
//                return f.getName().toLowerCase().endsWith(".jpg") || f.getName().toLowerCase().endsWith(".png") ||
//                        f.getName().toLowerCase().endsWith(".mp4") || f.getName().toLowerCase().endsWith(".m4a")
//                        || f.getName().toLowerCase().endsWith(".avi") || f.isDirectory();
//            }
//
//            @Override
//            public String getDescription() {
//                return "image/jpg,png";
//            }
//        });
//        jFileChooser.addChoosableFileFilter(new FileFilter() {
//            @Override
//            public boolean accept(File f) {
//                return f.getName().toLowerCase().endsWith(".jpg") || f.getName().toLowerCase().endsWith(".png") ||
//                        f.getName().toLowerCase().endsWith(".mp4") || f.getName().toLowerCase().endsWith(".m4a")
//                        || f.getName().toLowerCase().endsWith(".avi");
//            }
//
//            @Override
//            public String getDescription() {
//                return "video/mp4,m4a,avi";
//            }
//
//        });
//        if (currentDirectory != null && currentDirectory.exists()) {
//            jFileChooser.setCurrentDirectory(currentDirectory);
//        }
//
//        if (jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
//            chooseFile1(jFileChooser.getSelectedFile());
//
//        }
//    }
//
//
//    public void chooseFile1(File selectedFile) {
//        this.image1 = selectedFile;
//        try {
//            imageRead1 = ImageIO.read(image1);
//        } catch (Exception ex) {
//            vid1 = image1.getAbsolutePath();
//            imageRead1 = null;
//            throw new NullPointerException("Image1==null");
//        }
//
//        currentDirectory = selectedFile.getParentFile();
//
//        initialization();
//
//
//        initGrids(grid1, gridUV1, imageRead1, panel1);
//        if (imageControl1 != null) {
//            imageControl1.setDisplaying(false);
//            imageControl1.setRunning(false);
//        }
//        imageControl1 = new ImageControls(this, grid1, gridUV1,
//                imageRead1, panel1, text1, panelPoint3DUVGridIJ1);
//        imageControl1.setMorphUI(this);
//        //imageControl1.initUv(gridUV1);
//        new Thread(imageControl1).start();
//    }
//
//
//    private void buttonLoadImage2(ActionEvent e) {
//        boolean isLoaded = false;
//        JFileChooser jFileChooser = new JFileChooser();
//        jFileChooser.setFileFilter(new FileFilter() {
//            @Override
//            public boolean accept(File f) {
//                return f.getName().toLowerCase().endsWith(".jpg") || f.getName().toLowerCase().endsWith(".png") ||
//                        f.getName().toLowerCase().endsWith(".mp4") || f.getName().toLowerCase().endsWith(".m4a")
//                        || f.getName().toLowerCase().endsWith(".avi") || f.isDirectory();
//            }
//
//            @Override
//            public String getDescription() {
//                return "image/jpg,png {- video/mp4,m4a,avi";
//            }
//        });
//        jFileChooser.addChoosableFileFilter(new FileFilter() {
//            @Override
//            public boolean accept(File f) {
//                return f.getName().toLowerCase().endsWith(".jpg") || f.getName().toLowerCase().endsWith(".png") ||
//                        f.getName().toLowerCase().endsWith(".mp4") || f.getName().toLowerCase().endsWith(".m4a")
//                        || f.getName().toLowerCase().endsWith(".avi");
//            }
//
//            @Override
//            public String getDescription() {
//                return "video/mp4,m4a,avi";
//            }
//        });
//        if (currentDirectory != null && currentDirectory.exists()) {
//            jFileChooser.setCurrentDirectory(currentDirectory);
//        }
//
//        if (jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
//            chooseFile2(jFileChooser.getSelectedFile());
//        }
//
//
//    }
//
//    public void chooseFile2(File selectedFile) {
//        this.image2 = selectedFile;
//
//        try {
//            imageRead2 = ImageIO.read(image2);
//        } catch (Exception ex) {
//            vid2 = image2.getAbsolutePath();
//            imageRead2 = null;
//            throw new NullPointerException("image2==null");
//        }
//        currentDirectory = selectedFile.getParentFile();
//
//
//        initialization();
//        initGrids(grid2, gridUV2, imageRead2, panel2);
//        if (imageControl2 != null) {
//            imageControl2.setDisplaying(false);
//            imageControl2.setRunning(false);
//        }
//        imageControl2 = new ImageControls(this, grid2, gridUV2, imageRead2,
//                panel2, text2, panelPoint3DUVGridIJ2);
//        imageControl2.setMorphUI(this);
//        //imageControl2.initUv(gridUV2);
//        new Thread(imageControl2).start();
//    }
//
//
//    private void initialization() {
//        if (imageRead1 != null)
//            text1 = new ImageTexture(new ECBufferedImage(imageRead1));
//        else if (vid1 != null) {
//            text1 = new TextureMov(vid1);
//            instance1 = VideoDecoderFactory.createInstance(image1, (TextureMov) text1);
//        } else {
//            text1 = null;
//            imageRead1 = null;
//            vid1 = null;
//        }
//        if (imageRead2 != null)
//            text2 = new ImageTexture(new ECBufferedImage(imageRead2));
//        else if (vid2 != null) {
//            text2 = new TextureMov(vid2);
//            instance2 = VideoDecoderFactory.createInstance(image2, (TextureMov) text2);
//        } else {
//            text2 = null;
//            imageRead2 = null;
//            vid2 = null;
//        }
//    }
//
//
//
//    private BufferedImage computeFrame(int frameNo) {
//        while (isComputing()) {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        BufferedImage image = null;
//        try {
//            int seconds = getSeconds();
//            int fps = getFps();
//
//            double t = 1f * frameNo / seconds / fps;
//
//            TextureMorphing textureMorphing = null;
//
//            if (imageRead1 != null && imageRead2 != null && grid1 != null
//                    && grid2 != null && text1 != null && text2 != null) {
//
//                Scene scene = new Scene();
//
//
//                if (text1 instanceof TextureMov) {
//                    new Thread(instance1).start();
//                }
//                if (text2 instanceof TextureMov) {
//                    new Thread(instance2).start();
//                }
//
//                StructureMatrix<Point3D> copy = grid1.copy();
//
//                if (copy != null) {
//                    setComputing(true);
//
//                    double r = 1.0 * frameNo / (getFps() * getSeconds());
//
//                    textureMorphing = new TextureMorphing(text1, text2);
//                    textureMorphing.setT(r);
//
//
//                    for (int x = 0; x < copy.getData2d().size(); x++)
//                        for (int y = 0; y < copy.getData2d().get(x).size(); y++) {
//                            copy.setElem(transitionPoint(grid1.getElem(x, y), grid2.getElem(x, y), r), x, y);
//                        }
//
//                    int resX = getFinalResX();//imageRead1.getWidth();
//                    int resY = getFinalResY();//imageRead1.getHeight();
//
//                    Representable polygons = null;
//
//                    MixPolygons mixPolygons = null;
//
//
//                    if(shapeType==0) {
//
//                    if (caseShape()==1) {
//
//                        ImageControls[] imageControls = new ImageControls[]
//                                {imageControl1, imageControl2};
//                        ParametricSurface[] surfaces = new ParametricSurface[2];
//                        int i = 0;
//                        for (ImageControls ic : imageControls) {
//                            switch (ic.getModelIndex()) {
//                                case 0:
//                                    surfaces[i] = new Polygons();
//                                    ((SurfaceParametriquePolynomiale) (surfaces[i]))
//                                            .setCoefficients(ic.getGrid());
//                                    break;
//                                case 1:
//                                    surfaces[i] = new PolygonsDistinctUV();
//                                    ((PolygonsDistinctUV) (surfaces[i]))
//                                            .setCoefficients(ic.getGrid());
//                                    ((PolygonsDistinctUV) (surfaces[i]))
//                                            .setUvMap(ic.getGridUv());
//                                    break;
//                                case 2:
//                                case 3:
//                                    surfaces[i] = new Plan3D();
//                                    ((Plan3D) surfaces[i]).getP0().setElem(Point3D.O0);
//                                    ((Plan3D) surfaces[i]).getvX().setElem(Point3D.X.mult(resX));
//                                    ((Plan3D) surfaces[i]).getvY().setElem(Point3D.Y.mult(resY));
//                                    break;
//                            }
//                            i++;
//
//                        }
//                        mixPolygons = new MixPolygons(surfaces[0], surfaces[1], text1, text2);
//                    } else if (imageControl1.getPointView().getCheckBoxMorphing().isSelected()
//                            && imageControl2.getPointView().getCheckBoxMorphing().isSelected()
//                            && !imageControl1.getPointView().getCheckBoxUv().isSelected()
//                            && !imageControl1.getPointView().getCheckBoxUv().isSelected()) {
//                        polygons = new Polygons();
//                        ((Polygons) polygons).setCoefficients(copy);
//                    } else if (imageControl1.getPointView().getCheckBoxMorphing().isSelected()
//                            && imageControl2.getPointView().getCheckBoxMorphing().isSelected()
//                            && imageControl1.getPointView().getCheckBoxUv().isSelected()
//                            && imageControl1.getPointView().getCheckBoxUv().isSelected()) {
//                        polygons = new ShapeMorph1(text1, text2, grid1, grid2);
//                        ((ShapeMorph1) polygons).setT(t);
//                    } else {
//                        polygons = new PolygonsDistinctUV();
//                        ((PolygonsDistinctUV) polygons).setUvMap(imageControl1.getGridUv());
//                        ((PolygonsDistinctUV) polygons).setCoefficients(copy);
//                        ((PolygonsDistinctUV) polygons).setTexture2(textureMorphing);
//                    }
//
//                    //polygons.texture(textureMorphing);
//                    //scene.add(polygons);
//
//                    if (mixPolygons != null) {
//                        mixPolygons.texture(textureMorphing);
//                        mixPolygons.setTime(t);
//                        scene.add(mixPolygons);
//                    } else if (polygons != null) {
//                        polygons.texture(textureMorphing);
//                        scene.add(polygons);
//                    } else {
//                        Logger.getAnonymousLogger().log(Level.SEVERE, "Polygons==null && mixPolygons==null");
//                    }
//
//
//                    /*
//                    if (polygons instanceof ShapeMorph1) {
//                        ((ShapeMorph1) polygons).setT(1.0 * frameNo / fps / seconds);
//                    }
//*/
//                    textureMorphing.setFrameNo(frameNo);
//                    } else {
//                        polygons = new ShapeMorph1(text1, text2, grid1, grid2);
//                        ((ShapeMorph1)polygons).setT(t);
//                    }
//
//                        Point3D plus = Point3D.X.mult(
//                            resX / 2.).plus(Point3D.Y.mult(resY / 2.));
//
//                    Camera camera = new Camera(Point3D.Z.mult(
//                            -Math.max(resX, resY)).plus(plus), plus);
//                    camera.declareProperties();
//                    camera.calculerMatrice(Point3D.Y);
//
//                    scene.cameraActive(camera);
//
//                    if (zBufferComputing == null)
//                        zBufferComputing = new ZBufferImpl(resX, resY);
//                    else
//                        zBufferComputing.idzpp();
//
//                    //zBufferComputing.setDisplayType(ZBufferImpl.SURFACE_DISPLAY_TEXT_QUADS);
//
//                    zBufferComputing.scene(scene);
//                    zBufferComputing.camera(camera);
//
//                    if (zBufferComputing != null) {
//
//                        zBufferComputing.draw();
//
//                        image = zBufferComputing.imageInvX();
//
//                        ImageIcon imageIcon = new ImageIcon(image);
//
//
//                        JLabel jLabelResult = new JLabel(imageIcon);
//
//                        if (panelResult.getComponents().length > 0) {
//                            panelResult.remove(0);
//                        }
//
//                        panelResult.add(jLabelResult);
//
//                        pack();
//
//
//                        Logger.getLogger(this.getClass().getSimpleName())
//                                .log(Level.INFO, "Image " + frameNo + "\tEvolution: " + r);
//                        setComputing(false);
//                    }
//                }
//            }
//        } catch (IllegalAccessException | CopyRepresentableError | InstantiationException e) {
//            setComputing(false);
//            throw new RuntimeException(e);
//        }
//
//        return image;
//    }
//
//    public int getFps() {
//        return Integer.parseInt(textFieldFps.getText());
//
//    }
//
//    public int getSeconds() {
//        return Integer.parseInt(textFieldSeconds.getText());
//
//    }
//
//    private void buttonSave(ActionEvent e) {
//
//        new Thread(() -> {
//            try {
//                FileChannelWrapper out = writableFileChannel(new File("Video--" + UUID.randomUUID()).getAbsolutePath());
//                AWTSequenceEncoder encoder = new AWTSequenceEncoder(out, Rational.R(25, 1));
//                for (int frame = 0; frame < getFps() * getSeconds(); frame++) {
//                    Logger.getAnonymousLogger()
//                            .log(Level.FINE, "FrameNo" + frame);
//                    BufferedImage i = computeFrame(frame);
//                    encoder.encodeImage(i);
//                }
//                encoder.finish();
//                out.close();
//            } catch (FileNotFoundException ex) {
//                throw new RuntimeException(ex);
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//        }).start();
//    }
//
//    public boolean isComputing() {
//        return computing;
//    }
//
//    public void setComputing(boolean computing) {
//        this.computing = computing;
//    }
//
//    public int getFinalResY() {
//        return Integer.parseInt(textFieldFinalResY.getText());
//    }
//
//    public void setFinalResY(int finalResY) {
//        textFieldFinalResY.setText("" + finalResY);
//    }
//
//    public int getFinalResX() {
//        return Integer.parseInt(textFieldFinalResX.getText());
//    }
//
//    public void setFinalResX(int finalResX) {
//        textFieldFinalResX.setText("" + finalResX);
//    }
//
//    private void panel1ComponentResized(ComponentEvent e) {
//        if (grid1 != null && imageRead1 != null && panel1 != null)
//            initGrids(grid1, gridUV1, imageRead1, panel1);
//        if (grid2 != null && imageRead2 != null && panel2 != null)
//            initGrids(grid2, gridUV2, imageRead2, panel2);
//    }
//
//    private void panel2ComponentResized(ComponentEvent e) {
//        if (grid1 != null && imageRead1 != null && panel1 != null)
//            initGrids(grid1, gridUV1, imageRead1, panel1);
//        if (grid2 != null && imageRead2 != null && panel2 != null)
//            initGrids(grid2, gridUV2, imageRead2, panel2);
//    }
//
//    private void loadPoint(ActionEvent e) {
//        // TODO add your code here
//    }
//
//    private void savePoint(ActionEvent e) {
//        // TODO add your code here
//    }
//
//    private void method(ActionEvent e) {
//        if (imageControl1 != null && imageControl2 != null)
//            switch (getComboBoxMethod().getSelectedIndex()) {
//                case 0:
//                    imageControl1.setMorphing(true);
//                    imageControl2.setMorphing(true);
//                    imageControl1.setXyUv(true);
//                    imageControl2.setXyUv(true);
//                    break;
//                case 1:
//                    imageControl1.setMorphing(false);
//                    imageControl2.setMorphing(false);
//                    imageControl1.setXyUv(true);
//                    imageControl2.setXyUv(true);
//                    break;
//                case 2:
//                    imageControl1.setMorphing(false);
//                    imageControl2.setMorphing(false);
//                    imageControl1.setXyUv(false);
//                    imageControl2.setXyUv(false);
//                    break;
//            }
//    }
//
//    public JComboBox<String> getComboBoxMethod() {
//        return comboBoxMethod;
//    }
//
//    private void open(ActionEvent e) {
//        JFileChooser jFileChooser = new JFileChooser(currentProjectDirectory == null ? currentDirectory : currentProjectDirectory);
//        jFileChooser.setFileFilter(new FileFilter() {
//            @Override
//            public boolean accept(File f) {
//                return f.getName().toLowerCase().endsWith(".morph");
//            }
//
//            @Override
//            public String getDescription() {
//                return "Fichiers de Morphing empty3";
//            }
//        });
//        jFileChooser.showOpenDialog(this);
//        File f = null;
//
//        if ((f = jFileChooser.getSelectedFile()) != null) {
//            File finalF = f;
//            new Thread(() -> {
//                Logger.getAnonymousLogger().log(Level.INFO,
//                        "Create new GUI with loaded model = " + finalF.getAbsolutePath());
//                MorphUI morphUI = new MorphUI();
//                try {
//                    DataModel dataModel = new DataModel(morphUI);
//                    dataModel.load(finalF);
//                    pack();
//                    Logger.getAnonymousLogger().log(Level.INFO,
//                            "Model created");
//
//                    currentProjectDirectory = finalF.getParentFile();
//                } catch (IOException | ClassNotFoundException ex) {
//                    morphUI.dispose();
//                    ex.printStackTrace();
//                }
//                morphUI.setVisible(true);
//            }).start();
//        }
//
//    }
//
//    public void dispose() {
//        super.dispose();
//        imageControl1.setDisplaying(false);
//        imageControl2.setDisplaying(false);
//    }
//
//    private void save(ActionEvent e) {
//        DataModel dataModel = new DataModel(this);
//        saveFile = dataModel.file;
//        if (saveFile == null) {
//            JFileChooser jFileChooser = new JFileChooser(currentProjectDirectory == null ? currentDirectory : currentProjectDirectory);
//            jFileChooser.setFileFilter(new FileFilter() {
//                @Override
//                public boolean accept(File f) {
//                    return f.getName().toLowerCase().endsWith(".morph");
//                }
//
//                @Override
//                public String getDescription() {
//                    return "Morph project file by Empty3";
//                }
//            });
//            jFileChooser.showSaveDialog(this);
//
//            if (jFileChooser.getSelectedFile() != null) {
//                saveFile = jFileChooser.getSelectedFile();
//                dataModel.setFile(saveFile);
//            }
//        }
//        if (saveFile != null) {
//            dataModel.save(saveFile);
//            currentProjectDirectory = saveFile.getParentFile();
//        }
//    }
//
//    private void shapeType(ActionEvent e) {
//        switch (((JComboBox)(e.getSource())).getSelectedIndex()) {
//            case 0:
//                shapeType = 0;
//                break;
//            case 1:
//                shapeType = 1;
//                break;
//            default:
//                throw new IllegalStateException("Unexpected value: " + ((JComboBox) (e.getSource())));
//        }
//    }
//
//    private void initComponents() {
//        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
//        // Generated using JFormDesigner non-commercial license
//        DefaultComponentFactory compFactory = DefaultComponentFactory.getInstance();
//        menuBar1 = new JMenuBar();
//        menu1 = new JMenu();
//        menuItemNew = new JMenuItem();
//        menuItemOpen = new JMenuItem();
//        menuItemSave = new JMenuItem();
//        panel5 = new JPanel();
//        comboBoxShapeType = new JComboBox<>();
//        comboBoxMethod = new JComboBox<>();
//        label7 = new JLabel();
//        textFieldResX = new JTextField();
//        label8 = new JLabel();
//        textFieldResY = new JTextField();
//        panel3 = new JPanel();
//        labelFinalResX = new JLabel();
//        textFieldFinalResX = new JTextField();
//        labelFinalResY = new JLabel();
//        textFieldFinalResY = new JTextField();
//        button4 = new JButton();
//        panel1 = new JPanel();
//        panel2 = new JPanel();
//        panelResult = new JPanel();
//        label1 = new JLabel();
//        textFieldSeconds = new JTextField();
//        label3 = new JLabel();
//        textFieldFps = new JTextField();
//        button3 = new JButton();
//        button1 = new JButton();
//        button2 = new JButton();
//        slider1 = new JSlider();
//        panelPoint3DUVGridIJ1 = new PanelPoint3DUVGridIJ();
//        panelPoint3DUVGridIJ2 = new PanelPoint3DUVGridIJ();
//        panel4 = new JPanel();
//        button5 = new JButton();
//        button6 = new JButton();
//        label4 = new JButton();
//        label5 = new JButton();
//        textFieldAddCol = new JTextField();
//        textFieldDelCol = new JTextField();
//        textFieldAddRow = new JTextField();
//        textFieldDelRow = new JTextField();
//        label6 = compFactory.createLabel("Bottom bar");
//
//        //======== this ========
//        var contentPane = getContentPane();
//        contentPane.setLayout(new MigLayout(
//            "fill,hidemode 3",
//            // columns
//            "[fill]" +
//            "[fill]" +
//            "[fill]" +
//            "[fill]" +
//            "[fill]",
//            // rows
//            "[]" +
//            "[]" +
//            "[]" +
//            "[]" +
//            "[]" +
//            "[]" +
//            "[]" +
//            "[]"));
//
//        //======== menuBar1 ========
//        {
//
//            //======== menu1 ========
//            {
//                menu1.setText("File");
//
//                //---- menuItemNew ----
//                menuItemNew.setText("New");
//                menu1.add(menuItemNew);
//
//                //---- menuItemOpen ----
//                menuItemOpen.setText("Open");
//                menuItemOpen.addActionListener(e -> open(e));
//                menu1.add(menuItemOpen);
//
//                //---- menuItemSave ----
//                menuItemSave.setText("Save");
//                menuItemSave.addActionListener(e -> save(e));
//                menu1.add(menuItemSave);
//            }
//            menuBar1.add(menu1);
//        }
//        setJMenuBar(menuBar1);
//
//        //======== panel5 ========
//        {
//            panel5.setLayout(new MigLayout(
//                "hidemode 3",
//                // columns
//                "[fill]" +
//                "[fill]" +
//                "[fill]" +
//                "[fill]" +
//                "[fill]",
//                // rows
//                "[]" +
//                "[]"));
//
//            //---- comboBoxShapeType ----
//            comboBoxShapeType.setModel(new DefaultComboBoxModel<>(new String[] {
//                "Polygons",
//                "ShapeMorph1"
//            }));
//            panel5.add(comboBoxShapeType, "cell 0 0");
//
//            //---- comboBoxMethod ----
//            comboBoxMethod.setModel(new DefaultComboBoxModel<>(new String[] {
//                "Morphing",
//                "Tirer les points",
//                "Tirer et Stitch U,V"
//            }));
//            comboBoxMethod.addActionListener(e -> {
//			method(e);
//			shapeType(e);
//		});
//            panel5.add(comboBoxMethod, "cell 0 0");
//
//            //---- label7 ----
//            label7.setText("Grid X");
//            panel5.add(label7, "cell 0 0");
//
//            //---- textFieldResX ----
//            textFieldResX.setText("4");
//            panel5.add(textFieldResX, "cell 1 0");
//
//            //---- label8 ----
//            label8.setText("Grid Y");
//            panel5.add(label8, "cell 2 0");
//
//            //---- textFieldResY ----
//            textFieldResY.setText("4");
//            panel5.add(textFieldResY, "cell 3 0");
//
//            //======== panel3 ========
//            {
//                panel3.setLayout(new MigLayout(
//                    "hidemode 3",
//                    // columns
//                    "[fill]" +
//                    "[fill]" +
//                    "[fill]" +
//                    "[fill]",
//                    // rows
//                    "[]" +
//                    "[]" +
//                    "[]"));
//
//                //---- labelFinalResX ----
//                labelFinalResX.setText("Final Res X");
//                panel3.add(labelFinalResX, "cell 0 1");
//
//                //---- textFieldFinalResX ----
//                textFieldFinalResX.setText("400");
//                panel3.add(textFieldFinalResX, "cell 1 1");
//
//                //---- labelFinalResY ----
//                labelFinalResY.setText("Final Res Y");
//                panel3.add(labelFinalResY, "cell 2 1");
//
//                //---- textFieldFinalResY ----
//                textFieldFinalResY.setText("400");
//                panel3.add(textFieldFinalResY, "cell 3 1");
//            }
//            panel5.add(panel3, "cell 4 0");
//        }
//        contentPane.add(panel5, "cell 0 0 2 1");
//
//        //---- button4 ----
//        button4.setText("GO");
//        button4.addActionListener(e -> buttonGo(e));
//        contentPane.add(button4, "cell 2 0");
//
//        //======== panel1 ========
//        {
//            panel1.setMinimumSize(new Dimension(400, 400));
//            panel1.setMaximumSize(new Dimension(400, 400));
//            panel1.addComponentListener(new ComponentAdapter() {
//                @Override
//                public void componentResized(ComponentEvent e) {
//                    panel1ComponentResized(e);
//                }
//            });
//            panel1.setLayout(new MigLayout(
//                "hidemode 3",
//                // columns
//                "[fill]" +
//                "[fill]",
//                // rows
//                "[]" +
//                "[]" +
//                "[]"));
//        }
//        contentPane.add(panel1, "cell 0 1 1 3");
//
//        //======== panel2 ========
//        {
//            panel2.setMaximumSize(new Dimension(400, 400));
//            panel2.setMinimumSize(new Dimension(400, 400));
//            panel2.addComponentListener(new ComponentAdapter() {
//                @Override
//                public void componentResized(ComponentEvent e) {
//                    panel2ComponentResized(e);
//                }
//            });
//            panel2.setLayout(new MigLayout(
//                "hidemode 3",
//                // columns
//                "[fill]" +
//                "[fill]",
//                // rows
//                "[]" +
//                "[]" +
//                "[]"));
//        }
//        contentPane.add(panel2, "cell 1 1 1 3");
//
//        //======== panelResult ========
//        {
//            panelResult.setMaximumSize(new Dimension(400, 400));
//            panelResult.setMinimumSize(new Dimension(400, 400));
//            panelResult.setLayout(new MigLayout(
//                "fill,hidemode 3",
//                // columns
//                "[fill]" +
//                "[fill]",
//                // rows
//                "[]" +
//                "[]" +
//                "[]"));
//        }
//        contentPane.add(panelResult, "cell 2 1 1 3");
//
//        //---- label1 ----
//        label1.setText("Seconds");
//        contentPane.add(label1, "cell 3 1");
//
//        //---- textFieldSeconds ----
//        textFieldSeconds.setText("10");
//        contentPane.add(textFieldSeconds, "cell 4 1");
//
//        //---- label3 ----
//        label3.setText("FPS");
//        contentPane.add(label3, "cell 3 2");
//
//        //---- textFieldFps ----
//        textFieldFps.setText("25");
//        contentPane.add(textFieldFps, "cell 4 2");
//
//        //---- button3 ----
//        button3.setText("Save as");
//        button3.addActionListener(e -> buttonSave(e));
//        contentPane.add(button3, "cell 3 3 2 1");
//
//        //---- button1 ----
//        button1.setText("Load 1");
//        button1.addActionListener(e -> buttonLoadImage1(e));
//        contentPane.add(button1, "cell 0 4");
//
//        //---- button2 ----
//        button2.setText("Load 2");
//        button2.addActionListener(e -> buttonLoadImage2(e));
//        contentPane.add(button2, "cell 1 4");
//
//        //---- slider1 ----
//        slider1.addChangeListener(e -> slider1StateChanged(e));
//        contentPane.add(slider1, "cell 2 4");
//        contentPane.add(panelPoint3DUVGridIJ1, "cell 0 5");
//        contentPane.add(panelPoint3DUVGridIJ2, "cell 1 5");
//
//        //======== panel4 ========
//        {
//            panel4.setLayout(new MigLayout(
//                "fill,hidemode 3",
//                // columns
//                "[fill]" +
//                "[fill]" +
//                "[fill]" +
//                "[fill]",
//                // rows
//                "[]" +
//                "[]"));
//
//            //---- button5 ----
//            button5.setText("Add col");
//            panel4.add(button5, "cell 0 0");
//
//            //---- button6 ----
//            button6.setText("Del col");
//            panel4.add(button6, "cell 1 0");
//
//            //---- label4 ----
//            label4.setText("Add row");
//            panel4.add(label4, "cell 2 0");
//
//            //---- label5 ----
//            label5.setText("Del row");
//            panel4.add(label5, "cell 3 0");
//
//            //---- textFieldAddCol ----
//            textFieldAddCol.setText("1");
//            panel4.add(textFieldAddCol, "cell 0 1");
//
//            //---- textFieldDelCol ----
//            textFieldDelCol.setText("1");
//            panel4.add(textFieldDelCol, "cell 1 1");
//
//            //---- textFieldAddRow ----
//            textFieldAddRow.setText("1");
//            panel4.add(textFieldAddRow, "cell 2 1");
//
//            //---- textFieldDelRow ----
//            textFieldDelRow.setText("1");
//            panel4.add(textFieldDelRow, "cell 3 1");
//        }
//        contentPane.add(panel4, "cell 2 5");
//        contentPane.add(label6, "cell 0 6 5 1");
//        pack();
//        setLocationRelativeTo(getOwner());
//        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
//    }
//
//    public ImageControls getImageControls1() {
//        return imageControl1;
//    }
//
//    public ImageControls getImageControls2() {
//        return imageControl2;
//    }
//}
