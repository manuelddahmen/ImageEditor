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
// * Created by JFormDesigner on Wed Dec 28 14:10:32 CET 2022
// */
//
//package one.empty3.apps.morph;
//
//import net.miginfocom.swing.MigLayout;
//import one.empty3.library.CopyRepresentableError;
//import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;
//import one.empty3.library.StructureMatrix;
//
//import javax.swing.*;
//import javaAnd.awt.*;
//import java.awt.event.ActionEvent;
//import java.beans.PropertyChangeEvent;
//
///**
// * @author manuel
// */
//public class PanelPoint3DUVGridIJ extends JPanel {
//    private ImageControls imageControls;
//    private DataPoint dataPoint;
//    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
//    // Generated using JFormDesigner non-commercial license
//    private JLabel label1;
//    private JTextField textFieldI;
//    private JLabel label3;
//    private JTextField textFieldU;
//    private JLabel label5;
//    private JTextField textFieldX;
//    private JComboBox<String> comboBox1;
//    private JLabel label2;
//    private JTextField textFieldJ;
//    private JLabel label4;
//    private JTextField textFieldV;
//    private JLabel label7;
//    private JTextField textFieldY;
//    private JCheckBox checkBoxUv;
//    private JCheckBox checkBoxMorphing;
//    private JCheckBox checkBoxNoDeformation;
//    private JComboBox<String> comboBoxShape;
//    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
//
//    public PanelPoint3DUVGridIJ() {
//        initComponents();
//    }
//
//    public DataPoint getDataPoint() {
//        return dataPoint;
//    }
//
//    public void loadDataPoint() {
//        if (dataPoint == null)
//            dataPoint = new DataPoint();
//        getDataPoint().point = imageControls.getGrid().getData2d()
//                .get(imageControls.getXgrid()).get(imageControls.getYgrid());
//        getDataPoint().uv = new Point3D(imageControls.getGridUv().getData2d().get(
//                imageControls.getXgrid()).get(imageControls.getYgrid()));
//        getDataPoint().i = imageControls.getXgrid();
//        getDataPoint().j = imageControls.getYgrid();
//
//    }
//
//    public boolean getCheckboxMorphing() {
//        return checkBoxMorphing.isSelected();
//    }
//
//    public void setImageControls(ImageControls imageControls) {
//        this.imageControls = imageControls;
//    }
//
//    public JLabel getLabel1() {
//        return label1;
//    }
//
//    public JTextField getTextFieldI() {
//        return textFieldI;
//    }
//
//    public JLabel getLabel3() {
//        return label3;
//    }
//
//    public JTextField getTextFieldU() {
//        return textFieldU;
//    }
//
//    public JLabel getLabel5() {
//        return label5;
//    }
//
//    public JTextField getTextFieldX() {
//        return textFieldX;
//    }
//
//    public JComboBox<String> getComboBox1() {
//        return comboBox1;
//    }
//
//    public JLabel getLabel2() {
//        return label2;
//    }
//
//    public JTextField getTextFieldJ() {
//        return textFieldJ;
//    }
//
//    public JLabel getLabel4() {
//        return label4;
//    }
//
//    public JTextField getTextFieldV() {
//        return textFieldV;
//    }
//
//    public JLabel getLabel7() {
//        return label7;
//    }
//
//    public JTextField getTextFieldY() {
//        return textFieldY;
//    }
//
//    private void textFieldIPropertyChange(PropertyChangeEvent e) {
//        getTextFieldI().setText("" + Double.parseDouble("" + e.getOldValue()));
//        saveDataPoint();
//        getTextFieldI().setText("" + Double.parseDouble("" + e.getNewValue()));
//        loadDataPoint();
//    }
//
//    private void textFieldJPropertyChange(PropertyChangeEvent e) {
//        getTextFieldJ().setText("" + Double.parseDouble("" + e.getOldValue()));
//        saveDataPoint();
//        getTextFieldJ().setText("" + Double.parseDouble("" + e.getNewValue()));
//        loadDataPoint();
//    }
//
//    private void saveDataPoint() {
//        if (dataPoint != null) {
//            /*
//            imageControls.getGrid().setElem(dataPoint.point, dataPoint.i, dataPoint.j);
//            imageControls.getGridUv().setElem(dataPoint.uv, dataPoint.i, dataPoint.j);
//        */
//            imageControls.getGrid().getData2d().get(dataPoint.i).set(dataPoint.j, dataPoint.point);
//            imageControls.getGridUv().getData2d().get(dataPoint.i).set(dataPoint.j, dataPoint.uv);
//            try {
//                if (checkBoxUv.isSelected()) {
//                    imageControls.getGridUv().getData2d().get(dataPoint.i)
//                            .set(dataPoint.j,
//                                    ((Point3D) dataPoint.point.copy()).multDot(
//                                            new Point3D((double) imageControls.getResX(),
//                                                    (double) imageControls.getResY(), 0d)));
//                }
//            } catch (CopyRepresentableError e) {
//                throw new RuntimeException(e);
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException(e);
//            } catch (InstantiationException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    private void textFieldUPropertyChange(PropertyChangeEvent e) {
//        try {
//            dataPoint.uv = new Point3D((double) Double.parseDouble(getTextFieldU().getText()),
//                    dataPoint.uv.getY(), dataPoint.uv.getZ());
//            saveDataPoint();
//        } catch (NumberFormatException ex) {
//
//        }
//    }
//
//    private void textFieldVPropertyChange(PropertyChangeEvent e) {
//        try {
//            dataPoint.uv = new Point3D(dataPoint.uv.getX(),
//                    (double) Double.parseDouble(getTextFieldU().getText()), dataPoint.uv.getZ());
//            saveDataPoint();
//        } catch (NumberFormatException ex) {
//
//        }
//
//    }
//
//    private void textFieldXPropertyChange(PropertyChangeEvent e) {
//        try {
//            dataPoint.point = new Point3D((double) Double.parseDouble(getTextFieldX().getText()),
//                    dataPoint.point.getY(), dataPoint.point.getZ());
//            saveDataPoint();
//        } catch (NumberFormatException ex) {
//
//        }
//
//    }
//
//    private void textFieldYPropertyChange(PropertyChangeEvent e) {
//        try {
//            dataPoint.point = new Point3D(dataPoint.point.getX(),
//                    (double) Double.parseDouble(getTextFieldY().getText()), dataPoint.point.getZ());
//            saveDataPoint();
//        } catch (NumberFormatException ex) {
//
//        }
//
//    }
//    private void shape(ActionEvent event) {
//        switch (((JComboBox) (event.getSource())).getSelectedIndex()) {
//            case 0:
//                System.out.println("Model 1");
//                break;
//            case 1:
//                System.out.println("Model 2");
//                break;
//            case 2:
//                System.out.println("Model 3");
//                break;
//            case 3:
//                System.out.println("Model 3");
//                break;
//        }
//        imageControls.setModelIndex((((JComboBox) (event.getSource())).getSelectedIndex()));
//    }
//
//    private void comboBoxAction(ActionEvent e) {
//        ImageControls imageControls1 = imageControls.getMorphUI().getImageControls1();
//        ImageControls imageControls2 = imageControls.getMorphUI().getImageControls2();
//        StructureMatrix<Point3D>[] imageControlsArr = new StructureMatrix[]{
//                imageControls1.getGrid(),imageControls1.getGridUv(),
//                imageControls2.getGrid(), imageControls2.getGridUv()};
//        switch (((JComboBox) (e.getSource())).getSelectedIndex()) {
//            //Update
//            case 0:
//                saveDataPoint();
//                break;
////            Delete row
//            case 1:
//                for (StructureMatrix<Point3D> grid : imageControlsArr) {
//                    grid.delete(
//                            imageControls.getYgrid(), StructureMatrix.INSERT_ROW);
//                    grid.delete(
//                            imageControls.getYgrid(), StructureMatrix.INSERT_ROW);
//                    imageControls1.setYgrid(imageControls.getYgrid() - 1);
//                    imageControls2.setYgrid(imageControls.getYgrid() - 1);
//                }
//                break;
////            Delete column
//            case 2:
//                for (StructureMatrix<Point3D> grid : imageControlsArr) {
//                    grid.delete(
//                            imageControls.getXgrid(), StructureMatrix.INSERT_COL);
//                }
//                imageControls1.setXgrid(imageControls.getXgrid() - 1);
//                imageControls2.setXgrid(imageControls.getXgrid() - 1);
//                break;
//            case 3:
////            Insert col at
//                try {
//                    for (StructureMatrix<Point3D> grid : imageControlsArr) {
//                        grid.insert(
//                                imageControls.getXgrid(), StructureMatrix.INSERT_COL, Point3D.O0);
//                        int x = imageControls.getXgrid();
//                        for (int y = 0; y < grid.getData2d().get(x).size(); y++) {
//                            Point3D p1;
//                            Point3D p2;
//                            if (x == 0) {
//                                p1 = grid.getElem(x + 1, y);
//                                p2 = grid.getElem(x + 1, y);
//                            } else if (x == grid.getData2d().size() - 1) {
//                                p1 = grid.getElem(x - 1, y);
//                                p2 = grid.getElem(x - 1, y);
//                            } else {
//                                p1 = grid.getElem(x - 1, y);
//                                p2 = grid.getElem(x + 1, y);
//                            }
//                            Point3D p = p1.plus(p2).mult(0.5);
//                            grid.setElem(p, x, y);
//                        }
//                    }
//                } catch (IndexOutOfBoundsException ex) {
//                    ex.printStackTrace();
//                }
//                break;
//            case 4:
////            Insert row at
//
//                try {
//                    for (StructureMatrix<Point3D> grid : imageControlsArr) {
//                        grid.insert(imageControls.getYgrid(), StructureMatrix.INSERT_ROW, Point3D.O0);
//                        int y = imageControls.getYgrid();
//                        for (int x = 0; x < grid.getData2d().size(); x++) {
//                            Point3D p1;
//                            Point3D p2;
//                            if (y == 0) {
//                                p1 = grid.getElem(x, y + 1);
//                                p2 = grid.getElem(x, y + 1);
//                            } else if (y == grid.getData2d().get(x).size() - 1) {
//                                p1 = grid.getElem(x, y - 1);
//                                p2 = grid.getElem(x, y - 1);
//                            } else {
//                                p1 = grid.getElem(x, y - 1);
//                                p2 = grid.getElem(x, y + 1);
//                            }
//                            Point3D p = p1.plus(p2).mult(0.5);
//                            grid.setElem(p, x, y);
//                        }
//                    }
//                } catch (IndexOutOfBoundsException ex) {
//                    ex.printStackTrace();
//                }
//
//                break;
//        }
//        loadDataPoint();
//    }
//
//    public JCheckBox getCheckBoxUv() {
//        return checkBoxUv;
//    }
//
//    public JCheckBox getCheckBoxMorphing() {
//        return checkBoxMorphing;
//    }
//
//    public JCheckBox getCheckBoxNoDeformation() {
//        return checkBoxNoDeformation;
//    }
//
//    private void noDeformation(ActionEvent e) {
//        if (getCheckBoxNoDeformation().isSelected()) {
//            getCheckBoxMorphing().setSelected(false);
//            getCheckBoxUv().setSelected(false);
//        } else {
//            getCheckBoxMorphing().setSelected(true);
//            getCheckBoxUv().setSelected(true);
//        }
//    }
//
//    public JComboBox<String> getComboBoxShape() {
//        return comboBoxShape;
//    }
//
//
//
//    private void initComponents() {
//        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
//        // Generated using JFormDesigner non-commercial license
//        label1 = new JLabel();
//        textFieldI = new JTextField();
//        label3 = new JLabel();
//        textFieldU = new JTextField();
//        label5 = new JLabel();
//        textFieldX = new JTextField();
//        comboBox1 = new JComboBox<>();
//        label2 = new JLabel();
//        textFieldJ = new JTextField();
//        label4 = new JLabel();
//        textFieldV = new JTextField();
//        label7 = new JLabel();
//        textFieldY = new JTextField();
//        checkBoxUv = new JCheckBox();
//        checkBoxMorphing = new JCheckBox();
//        checkBoxNoDeformation = new JCheckBox();
//        comboBoxShape = new JComboBox<>();
//
//        //======== this ========
//        setLayout(new MigLayout(
//            "hidemode 3",
//            // columns
//            "[fill]" +
//            "[fill]" +
//            "[fill]" +
//            "[fill]" +
//            "[fill]" +
//            "[fill]" +
//            "[fill]" +
//            "[fill]" +
//            "[fill]" +
//            "[fill]" +
//            "[fill]",
//            // rows
//            "[]" +
//            "[]"));
//
//        //---- label1 ----
//        label1.setText("i");
//        add(label1, "cell 0 0");
//
//        //---- textFieldI ----
//        textFieldI.addPropertyChangeListener("changedI", e -> textFieldIPropertyChange(e));
//        add(textFieldI, "cell 1 0");
//
//        //---- label3 ----
//        label3.setText("u");
//        add(label3, "cell 2 0");
//
//        //---- textFieldU ----
//        textFieldU.addPropertyChangeListener("changedU", e -> textFieldUPropertyChange(e));
//        add(textFieldU, "cell 3 0");
//
//        //---- label5 ----
//        label5.setText("x");
//        add(label5, "cell 4 0");
//
//        //---- textFieldX ----
//        textFieldX.addPropertyChangeListener("changedX", e -> textFieldXPropertyChange(e));
//        add(textFieldX, "cell 5 0");
//
//        //---- comboBox1 ----
//        comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
//            "Update",
//            "Delete row",
//            "Delete column",
//            "Insert column",
//            "Insert row"
//        }));
//        comboBox1.addActionListener(e -> comboBoxAction(e));
//        add(comboBox1, "cell 7 0");
//
//        //---- label2 ----
//        label2.setText("j");
//        add(label2, "cell 0 1");
//
//        //---- textFieldJ ----
//        textFieldJ.addPropertyChangeListener("changedJ", e -> textFieldJPropertyChange(e));
//        add(textFieldJ, "cell 1 1");
//
//        //---- label4 ----
//        label4.setText("v");
//        add(label4, "cell 2 1");
//
//        //---- textFieldV ----
//        textFieldV.addPropertyChangeListener("changedV", e -> textFieldVPropertyChange(e));
//        add(textFieldV, "cell 3 1");
//
//        //---- label7 ----
//        label7.setText("y");
//        add(label7, "cell 4 1");
//
//        //---- textFieldY ----
//        textFieldY.addPropertyChangeListener("changedY", e -> textFieldYPropertyChange(e));
//        add(textFieldY, "cell 5 1");
//
//        //---- checkBoxUv ----
//        checkBoxUv.setText("Text (u,v)");
//        add(checkBoxUv, "cell 7 1");
//
//        //---- checkBoxMorphing ----
//        checkBoxMorphing.setText("Morphing");
//        add(checkBoxMorphing, "cell 7 1");
//
//        //---- checkBoxNoDeformation ----
//        checkBoxNoDeformation.setText("Don't deform");
//        checkBoxNoDeformation.setSelected(true);
//        checkBoxNoDeformation.addActionListener(e -> noDeformation(e));
//        add(checkBoxNoDeformation, "cell 7 1");
//
//        //---- comboBoxShape ----
//        comboBoxShape.setModel(new DefaultComboBoxModel<>(new String[] {
//            "1",
//            "2",
//            "3",
//            "4"
//        }));
//        comboBoxShape.addActionListener(e -> shape(e));
//        add(comboBoxShape, "cell 7 1");
//        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
//    }
//
//    class DataPoint {
//        int i, j;
//        Point3D point, uv;
//
//    }
//}
