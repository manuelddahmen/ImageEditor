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
//package one.empty3.apps.morph;
//
//import javaAnd.awt.image.BufferedImage;
//import one.empty3.library.*;
//import one.empty3.library.core.nurbs.ParametricSurface;
//import one.empty3.library.core.tribase.Plan3D;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
//import java.awt.image.BufferedImage;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class ImageControls implements Runnable {
//    private final BufferedImage image;
//    boolean moving = false;
//    boolean dropped = false;
//    boolean clicked = false;
//    boolean drags = false;
//    RepresentableConteneur rc;
//    int resX = 400;//imageRead1.getWidth();
//    int resY = 400;//imageRead1.getHeight();
//    private StructureMatrix<Point3D> grid;
//    private ITexture texture;
//    private PanelPoint3DUVGridIJ point3Dedit;
//    private StructureMatrix<Point3D> gridUv;
//    private boolean isSelected;
//    private boolean isPressed;
//    private double RADIUS = 5;
//    private Point3D selectedPoint;
//    private int xGrid;
//    private int yGrid;
//    private boolean running = false;
//    private Scene scene;
//    private Camera camera;
//    private boolean displaying;
//    private ZBuffer zBuffer;
//    private int loopIndex;
//    private MorphUI morphUI;
//    private int index = 0;
//
//    public ImageControls(JFrame jframe,
//                         StructureMatrix<Point3D> grid, StructureMatrix<Point3D> gridUv, BufferedImage image,
//                         JPanel panelDisplay, ITexture texture, PanelPoint3DUVGridIJ point3Dedit) {
//        this.jframe = jframe;
//        this.grid = grid;
//        this.gridUv = gridUv;
//        this.image = image;
//        this.panelDisplay = panelDisplay;
//        this.texture = texture;
//        this.point3Dedit = point3Dedit;
//        point3Dedit.setImageControls(this);
//        setRunning(true);
//        panelDisplay.addMouseListener(new MouseListener() {
//
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                System.out.println("Clicked = " + (clicked = true));
//                clicked();
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//                if (grid.getData2d().size() > xGrid && grid.getData2d().get(xGrid).size() > yGrid) {
//                    System.out.println("Pressed : " + (isPressed = true));
//                    isSelected = selectPoint(e.getX(), e.getY());
//                    if (grid.getData2d().size() > xGrid && grid.getData2d().get(xGrid).size() > yGrid) {
//                        System.out.println("Selected point: " + grid.getElem(xGrid, yGrid));
//                        moving = true;
//                        drags();
//                    } else {
//                        isSelected = false;
//                    }
//                } else {
//                    isSelected = false;
//                }
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                System.out.println("Pressed : " + (isPressed = false));
//                moving = false;
//                dropped = true;
//                dragged(e.getX(), e.getY());
//
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//            }
//        });
//        panelDisplay.addMouseMotionListener(new MouseMotionListener() {
//            @Override
//            public void mouseDragged(MouseEvent e) {
//            }
//
//            @Override
//            public void mouseMoved(MouseEvent e) {
//            }
//        });
//    }
//
//
//    public void setScene(Scene scene) {
//        this.scene = scene;
//    }
//
//    public StructureMatrix<Point3D> getMatrice() {
//        return grid;
//    }
//
//    public void initBools() {
//        moving = false;
//        dropped = false;
//        clicked = false;
//        drags = false;
//        isSelected = false;
//        isPressed = false;
//    }
//
//    private void clicked() {
//        //select();
////        System.out.println("::select a point");
////        isSelected = true;
//    }
//
//    private void drags() {
//        //move if selected
//        moving = true;
//        if (isPressed) {
//            //System.out.println("::move a point");
//        }
//    }
//
//    private void dragged(int x, int y) {
//        // drops if moved
//        if (!moving && !isPressed && isSelected) {
//            System.out.println("::update a point position");
//            System.out.println(selectedPoint);
//            Point point = camera.coordonneesPoint2D(
//                    new Point3D(1.0 * x, 1.0 * y, 0d), zBuffer);
//            grid.setElem(new Point3D(resX - 1.0 * point.x, 1.0 * point.y, 0d),
//                    this.xGrid, this.yGrid);
//            if (getPointView().getCheckBoxUv().isSelected()) {
//                getGridUv().setElem(selectedPoint.multDot(
//                        new Point3D(1. / resX, 1. / resY, 0d)), xGrid, yGrid);
//            }
//        }
//        initBools();
//
//    }
//
//    public boolean isNear(Point3D candidate, Point3D gridPoint) {
//        if (candidate.getX() - RADIUS / 2 >= gridPoint.getX() &&
//                candidate.getX() + RADIUS / 2 <= gridPoint.getX()
//                && candidate.getY() - RADIUS / 2 >= gridPoint.getY() &&
//                candidate.getY() + RADIUS / 2 <= gridPoint.getY())
//            return true;
//        else
//            return false;
//    }
//
//    private boolean selectPoint(int x, int y) {
//        selectedPoint = null;
//        xGrid = 0;
//        yGrid = 0;
//        double minDist = Double.MAX_VALUE;
//        Point3D point3D;
//        if ((camera == null) || (zBuffer == null)) {
//            point3D = convertScreenCordToSceneCord(new Point3D((double) x, (double) y, 0d));
//        } else {
//            point3D = convertScreenCordToSceneCord(new Point3D((double) x, (double) y, 0d));
//        }
//        for (int i = 0; i < grid.data2d.size(); i++) {
//            for (int j = 0; j < grid.data2d.get(i).size(); j++) {
//                Point3D pointGrid = grid.data2d.get(i).get(j);
//                double distanceActuelle = Point3D.distance(point3D, pointGrid);
//                if (distanceActuelle < minDist) {
//                    selectedPoint = pointGrid;
//                    this.xGrid = i;
//                    this.yGrid = j;
//                    minDist = distanceActuelle;
//                }
//            }
//        }
//
//        if (selectedPoint != null) {
//            if (point3Dedit.getDataPoint() == null ||
//                    selectedPoint != point3Dedit.getDataPoint().point) {
//                point3Dedit.loadDataPoint();
//            }
//            point3Dedit.getTextFieldI().setText("" + xGrid);
//            point3Dedit.getTextFieldJ().setText("" + yGrid);
//            point3Dedit.getTextFieldX().setText("" + (grid.getData2d().get(xGrid).get(yGrid).getX()));
//            point3Dedit.getTextFieldY().setText("" + (grid.getData2d().get(xGrid).get(yGrid).getY()));
//            point3Dedit.getTextFieldU().setText("" + (gridUv.getData2d().get(xGrid).get(yGrid).getX()));
//            point3Dedit.getTextFieldV().setText("" + (gridUv.getData2d().get(xGrid).get(yGrid).getY()));
//        }
//        return selectedPoint != null;
//    }
//
//
//    public void run() {
//        //displayGrid();
//        while (isRunning()) {
//            display();
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    private void displayGrid() {
//        Thread thread = new Thread(() -> {
//            ZBufferImplJan2023 zBuffer1 = new ZBufferImplJan2023(resX, resY);
//            while (isRunning()) {
//
//                Scene scene1 = new Scene();
//                addToScene(scene1);
//                //drawPolygons(zBuffer1, scene1);
//            }
//        });
//        thread.start();
//
//    }
//
//    private boolean isRunning() {
//        return running;
//    }
//
//    public void setRunning(boolean isRunning) {
//        this.running = isRunning;
//    }
//
//    public Point3D convertSceneCordToScreenCord(Point3D pScene) {
//        double x = pScene.getX() * panelDisplay.getWidth() / image.getWidth();
//        double y = pScene.getY() * panelDisplay.getHeight() / image.getHeight();
//
//
//        return new Point3D(x, y, 0d);
//
//    }
//
//    public Point3D convertScreenCordToSceneCord(Point3D pScreen) {
//        double x = pScreen.getX();/// panelDisplay.getWidth() * image.getWidth();
//        double y = pScreen.getY();/// panelDisplay.getHeight() * image.getHeight();
//
//        return new Point3D(x, y, 0d);
//
//    }
//    public ParametricSurface computeShapeT() {
//        if (getPointView().getCheckBoxNoDeformation().isSelected()) {
//            Plan3D polygons = new Plan3D();
//            polygons.getP0().setElem(Point3D.O0);
//            polygons.getvX().setElem(Point3D.X.mult(resX));
//            polygons.getvY().setElem(Point3D.Y.mult(resY));
//            polygons.texture(texture);
//            scene.add(polygons);
//            return polygons;
//        } else {
//            if (getPointView().getCheckboxMorphing()) {
//                Polygons polygons = new Polygons();
//                polygons.setCoefficients(grid);
//                polygons.texture(texture);
//                scene.add(polygons);
//                return polygons;
//            } else {
//                PolygonsDistinctUV polygons = new PolygonsDistinctUV();
//                polygons.setCoefficients(grid);
//                polygons.texture(texture);
//                if (polygons instanceof PolygonsDistinctUV) {
//                    polygons.setUvMap(gridUv);
//                    polygons.setTexture2(texture);
//                }
//                scene.add(polygons);
//                return polygons;
//            }
//        }
//
//    }
//    private void display() {
//        while (isDisplaying()) {
//
//            try {
//                Thread.sleep(80);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        int time = 10000;
//        loopIndex++;
//
//        displaying = true;
//
//        if (texture == null) {
//            Logger.getLogger(this.getClass().getSimpleName()).log(Level.SEVERE, "Error texture Movie or Image is null");
//            displaying = false;
//            return;
//        }
//        scene = new Scene();
//
////
//        computeShapeT();
//        Point3D plus = Point3D.X.mult(
//                resX / 2.).plus(Point3D.Y.mult(resY / 2.));
//
//        camera = new Camera(Point3D.Z.mult(
//                -Math.max(resX, resY)).plus(plus), plus);
//        camera.declareProperties();
//        camera.calculerMatrice(Point3D.Y);
//
//        addToScene(scene);
//
//        if (zBuffer == null)
//            zBuffer = new ZBufferImplJan2023(resX, resY);
//        else {
//            zBuffer.idzpp();
//        }
//
//        drawPolygons(scene);
//
//
//        displaying = false;
//    }
//
//    private void drawPolygons(Scene scene) {
//
//
//        zBuffer.scene(scene);
//        scene.cameraActive(camera);
//
//        zBuffer.draw(scene);
//
//        drawSceneOnScreen(zBuffer);
//
//    }
//
//    private void drawSceneOnScreen(ZBuffer zBuffer) {
//        if (zBuffer != null) {
//           /* BufferedImage image = new BufferedImage(resX, resY, BufferedImage.TYPE_INT_ARGB);
//
//            Graphics2D graphics = image.createGraphics();
//            Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, .5f);
//            graphics.setComposite(c);
//            graphics.drawImage(zBuffer.imageInvX(), 0, 0,  resX, resY, null);
//            */
//            ImageIcon imageIcon = new ImageIcon(zBuffer.imageInvX());
//
//            JLabel jLabelResult = new JLabel(imageIcon);
//
//            if (panelDisplay.getComponents().length > 0) {
//                panelDisplay.remove(0);
//            }
//            panelDisplay.add(jLabelResult);
//
//            jframe.pack();
//        }
//    }
//
//    public void addToScene(Scene scene) {
//        rc = new RepresentableConteneur();
//        for (int i = 0; i < grid.getData2d().size(); i++) {
//            for (int j = 0; j < grid.getData2d().get(i).size(); j++) {
//                Point3D point3D = grid.getData2d().get(i).get(j);
//                Sphere sphere = new Sphere(new Axe(point3D.plus(Point3D.Y.mult(RADIUS / 2.)),
//                        point3D.moins(Point3D.Y.mult(RADIUS / 2.))),
//                        RADIUS);
//                if (point3D.equals(selectedPoint)) {
//                    sphere.texture(new ColorTexture(Color.RED));
//                } else {
//                    sphere.texture(new ColorTexture(Color.BLUE));
//                }
//                if (grid.inBounds(i+1, j)) {
//                    rc.add(new LineSegment(point3D, grid.getElem(i + 1, j), new ColorTexture(Color.GREEN)));
//                }
//                if(grid.inBounds(i, j+1)) {
//                    rc.add(new LineSegment(point3D, grid.getElem(i, j + 1), new ColorTexture(Color.GREEN)));
//                }
//                sphere.setIncrU(0.4);
//                sphere.setIncrV(0.4);
//                rc.add(sphere);
//            }
//        }
//        scene.add(rc);
//    }
//
//    public boolean isDisplaying() {
//        return displaying;
//    }
//
//    public void setDisplaying(boolean displaying) {
//        this.displaying = displaying;
//    }
//
//    public PanelPoint3DUVGridIJ getPointView() {
//        return point3Dedit;
//    }
//
//    public void setPointView(PanelPoint3DUVGridIJ point3Dedit) {
//        this.point3Dedit = point3Dedit;
//    }
//
//    public StructureMatrix<Point3D> getGrid() {
//        return grid;
//    }
//
//    public void setGrid(StructureMatrix<Point3D> o) {
//        this.grid = o;
//    }
//
//    public int getXgrid() {
//        return xGrid;
//    }
//
//    public void setXgrid(int x) {
//        this.xGrid = x;
//    }
//
//    public int getYgrid() {
//        return yGrid;
//    }
//
//    public void setYgrid(int y) {
//        this.yGrid = y;
//    }
//
//    public StructureMatrix<Point3D> getGridUv() {
//        return gridUv;
//    }
//
//    public void setGridUv(StructureMatrix<Point3D> o) {
//        this.gridUv = o;
//    }
//
//    public int getResX() {
//        return resY;
//    }
//
//    public void setResX(int resX) {
//        this.resX = resX;
//    }
//
//    public int getResY() {
//        return resY;
//    }
//
//    public void setResY(int resY) {
//        this.resY = resY;
//    }
//
//    public MorphUI getMorphUI() {
//        return this.morphUI;
//    }
//
//    public void setMorphUI(MorphUI morphUI) {
//        this.morphUI = morphUI;
//    }
//
//    public BufferedImage getImage() {
//        return image;
//    }
//
//    public void setXyUv(boolean b) {
//        point3Dedit.getCheckBoxUv().setSelected(b);
//    }
//
//    public void setMorphing(boolean b) {
//        point3Dedit.getCheckBoxMorphing().setSelected(b);
//    }
//
//    public void setModelIndex(int selectedIndex) {
//        this.index = selectedIndex;
//    }
//    public int getModelIndex() {
//        return index;
//    }
//}
