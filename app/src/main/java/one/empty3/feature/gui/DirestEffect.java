/*
 * Created by JFormDesigner on Fri Aug 20 16:53:54 CEST 2021
 */
/*
 * @author Manuel Dahmen
 */
package one.empty3.feature.gui;
/*
import com.github.sarxos.webcam.Webcam;
import net.miginfocom.swing.MigLayout;
import one.empty3.feature.*;
import one.empty3.feature.motion.DiffMotion;
import one.empty3.feature.motion.Motion;

import  one.empty3.feature.app.replace.javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class DirestEffect extends JFrame {
    public final ThreadEffectDisplay threadEffectDisplay;
    private ClassSchemaBuilder main;

    public DirestEffect() {
        initComponents();

        threadEffectDisplay
                = new ThreadEffectDisplay();
        threadEffectDisplay.setJpanel(panel1);
        threadEffectDisplay.motion = new DiffMotion();

        threadEffectDisplay.start();

        this.comboBoxDimenisions.setModel(new DefaultComboBoxModel(Webcam.getDefault().getViewSizes()));
    }

    private void panel1ComponentResized(ComponentEvent e) {
        main.setMaxRes(Math.max(getWidth(), getHeight()));
    }


    private void comboBoxMotionItemStateChanged(ItemEvent e) {
        if (comboBoxMotion.getSelectedIndex() == 0) {
            threadEffectDisplay.motion = null;
        }
        Object item = e.getItem();
        try {

            Class<?> aClass = DiffMotion.class;
            Object o = aClass.newInstance();
            if (o instanceof Motion) {
                this.threadEffectDisplay.motion = (Motion) o;
                threadEffectDisplay.setImageMotion(threadEffectDisplay.motion.process());
            }

            switch (comboBoxMotion.getSelectedIndex()) {
                case 0:
                    //Aucun mouvement, aucun effet
                    threadEffectDisplay.motion = new LastFrameMotion();
                    threadEffectDisplay.effect = new IdentNullProcess();
                    break;
                case 1:
                    //Aucun mouvement, effet
                    threadEffectDisplay.motion = new LastFrameMotion();
                    threadEffectDisplay.effect = new IdentNullProcess();
                    break;
                case 2:
                    //Mouvement diff, aucun effet
                    threadEffectDisplay.motion = new DiffMotion();
                    threadEffectDisplay.effect = null;
                    break;
                case 3:
                    //Mouvement diff, effet
                    threadEffectDisplay.motion = new DiffMotion();
                    threadEffectDisplay.effect = null;
                    break;
                case 4:
                    //Effet, mouvement diff
                    threadEffectDisplay.setRunEffect(new RunEffect() {
                        @Override
                        public BufferedImage runEffect(ArrayList<BufferedImage> imageList, ClassSchemaBuilder classSchemaBuilder) {
                            boolean b = imageList.size() > 1;
                            if (b) {
                                BufferedImage bufferedImage = imageList.get(0);
                                BufferedImage bufferedImage1 = imageList.get(1);
                                if (imageList.size() > Motion.BUFFER_MAX_FRAMES)
                                    imageList.remove(0);
                                classSchemaBuilder.files = new ArrayList<>();
                                try {
                                    ImageIO.write(bufferedImage, "jpg", new File("temp/webcam.jpg"));
                                    classSchemaBuilder.files.add(new File[]{new File("temp/webcam.jpg")});
                                    classSchemaBuilder.buttonGOActionPerformed(null);
                                    BufferedImage loaded0 = ImageIO.read(new File("temp/webcam.jpg"));
                                    classSchemaBuilder.files = new ArrayList<>();
                                    ImageIO.write(bufferedImage, "jpg", new File("temp/webcam.jpg"));
                                    classSchemaBuilder.files.add(new File[]{new File("temp/webcam.jpg")});
                                    classSchemaBuilder.buttonGOActionPerformed(null);
                                    BufferedImage loaded1 = ImageIO.read(new File("temp/webcam.jpg"));
                                    Linear linear = new Linear(loaded0, loaded1);
                                    linear.op2d2d(new char[]{'-'}, new int[][]{{0, 1}}, new int[]{2});
                                    PixM image = linear.getImages()[2];
                                    ImageIO.write(image.getImage(), "jpg", new File("temp/webcam.jpg"));
                                    setFileIn(new File("temp/webcam.jpg"));
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            return null;
                        }
                    });
                case 5:
                    //Aucun effet, mouvement diff
                    threadEffectDisplay.setRunEffect(new RunEffect() {
                        @Override
                        public BufferedImage runEffect(ArrayList<BufferedImage> imageList, ClassSchemaBuilder classSchemaBuilder) {
                            boolean b = imageList.size() > 1;
                            if (b) {
                                BufferedImage bufferedImage = imageList.get(0);
                                BufferedImage bufferedImage1 = imageList.get(1);
                                if (imageList.size() > Motion.BUFFER_MAX_FRAMES)
                                    imageList.remove(0);
                                classSchemaBuilder.files = new ArrayList<>();
                                try {
                                    Linear linear = new Linear(bufferedImage, bufferedImage1);
                                    linear.op2d2d(new char[]{'-'}, new int[][]{{0, 1}}, new int[]{2});
                                    PixM image = linear.getImages()[2];
                                    ImageIO.write(image.getImage(), "jpg", new File("temp/webcam.jpg"));
                                    classSchemaBuilder.buttonGOActionPerformed(null);
                                    setFileIn(new File("temp/webcam.jpg"));
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            return null;
                        }
                    });
                    break;
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    private void comboBoxDimenisionsActionPerformed(ActionEvent e) {
        Dimension selectedItem = (Dimension) comboBoxDimenisions.getSelectedItem();
        System.out.printf("Selected item = " + selectedItem);
        threadEffectDisplay.webcam.close();
        threadEffectDisplay.webcam = Webcam.getDefault();
        threadEffectDisplay.webcam.setViewSize(selectedItem);
        threadEffectDisplay.webcam.open();

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        comboBoxDimenisions = new JComboBox();
        scrollPane2 = new JScrollPane();
        comboBoxMotion = new JComboBox<>();
        scrollPane1 = new JScrollPane();
        panel1 = new JPanel();

        //======== this ========
        setTitle("Effect viewer");
        setMinimumSize(new Dimension(640, 480));
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
                "fill",
                // columns
                "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]",
                // rows
                "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]"));

        //---- comboBoxDimenisions ----
        comboBoxDimenisions.setDoubleBuffered(true);
        comboBoxDimenisions.addActionListener(e -> comboBoxDimenisionsActionPerformed(e));
        contentPane.add(comboBoxDimenisions, "cell 0 0 5 1");

        //======== scrollPane2 ========
        {
            scrollPane2.setDoubleBuffered(true);

            //---- comboBoxMotion ----
            comboBoxMotion.setModel(new DefaultComboBoxModel<>(new String[]{
                    "Aucun mouvement, aucun effet",
                    "Aucun mouvement, effet",
                    "Mouvement diff, aucun effet",
                    "Mouvement diff, effet",
                    "Effet, mouvement diff",
                    "Aucun effet, mouvement diff"
            }));
            comboBoxMotion.setDoubleBuffered(true);
            comboBoxMotion.addItemListener(e -> {
                comboBoxMotionItemStateChanged(e);
                comboBoxMotionItemStateChanged(e);
            });
            scrollPane2.setViewportView(comboBoxMotion);
        }
        contentPane.add(scrollPane2, "cell 0 0 5 1");

        //======== scrollPane1 ========
        {
            scrollPane1.setDoubleBuffered(true);

            //======== panel1 ========
            {
                panel1.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        panel1ComponentResized(e);
                    }
                });
                panel1.setLayout(new MigLayout(
                        "fill,hidemode 3",
                        // columns
                        "[fill]" +
                                "[fill]",
                        // rows
                        "[]" +
                                "[]" +
                                "[]"));
            }
            scrollPane1.setViewportView(panel1);
        }
        contentPane.add(scrollPane1, "cell 0 1 5 4,dock center");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JComboBox comboBoxDimenisions;
    private JScrollPane scrollPane2;
    private JComboBox<String> comboBoxMotion;
    private JScrollPane scrollPane1;
    private JPanel panel1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


    public static void main(String[] args) {
        DirestEffect direstEffect = new DirestEffect();
        direstEffect.setVisible(true);

    }

    public void setMainWindow(ClassSchemaBuilder classSchemaBuilder) {
        this.main = classSchemaBuilder;
        threadEffectDisplay.setMain(main);
        threadEffectDisplay.setDirectEffect(this);
        main.setMaxRes(100);
    }

    public void setFileIn(File fileOut) {
        try {
            threadEffectDisplay.setImageIn(ImageIO.read(fileOut));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBusy(boolean b) {
        threadEffectDisplay.setBusy(false);
    }
}
*/