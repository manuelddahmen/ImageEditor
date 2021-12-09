/*
 *  This file is part of one.empty3.
 *
 *     Empty3 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Empty3 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with one.empty3.  If not, see <https://www.gnu.org/licenses/>. 2
 */

/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

/*
 * 2013 Manuel Dahmen
 */
package one.empty3.atlasgen;

import one.empty3.library.ECBufferedImage;
import one.empty3.library.core.testing.ImageContainer;
import one.empty3.library.core.testing.TestObjet;
import one.empty3.library.core.testing.TextAreaOutputStream;

import  one.empty3.feature.app.replace.javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/*__
 * @author Manuel DAHMEN
 */
public final class ShowTestResult extends JFrame implements Runnable {

    /*__
     *
     */
    private static final long serialVersionUID = -7844993762133687210L;
    private ECBufferedImage image = null;
    private ImageContainer biic;
    private boolean stop = false;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton5;
    private JButton jButtonDemarrerNouveauFilm;
    private JCheckBox jCheckBoxFilmRec;
    private JCheckBox jCheckBoxImagesRec;
    private JCheckBox jCheckBoxModeles;
    private JCheckBox jCheckBoxOGL;
    private JLabel jLabelFrame;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPaneMessage;
    private JSlider jSliderX;
    private JSlider jSliderXYZ;
    private JSlider jSliderY;
    private JSlider jSliderZ;
    private JSplitPane jSplitPane1;
    private JSplitPane jSplitPane2;
    private JTable jTableEquations;
    private JTextArea jTextAreaMessage;
    private JTextField jTextField1;
    private JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
    private TestObjet testRef;

    private Throwable throwable;
    private int movieNo = 1;
    private int frameNo = 1;

    /*__
     * Creates new form ShowTestResult
     */
    public ShowTestResult() {

        initComponents();

        jPanel1.setSize(jPanel1.getWidth(), 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// Now create a new TextAreaOutputStream to write to our JTextArea control and wrap a
// PrintStream around it to support the println/printf methods.
        PrintStream out = new PrintStream(new TextAreaOutputStream(jTextAreaMessage));

// redirect standard output stream to the TextAreaOutputStream
        System.setOut(out);

// redirect standard error stream to the TextAreaOutputStream
        System.setErr(out);

// now test the mechanism
        System.out.println("Hello World");

    }

    public ShowTestResult(BufferedImage ri) {
        initComponents();

        loadImage(ri);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public ShowTestResult(ECBufferedImage ri) {
        initComponents();

        image = ri;

        if (image != null) {
            setSize(new Dimension(image.getWidth(), image.getHeight()));
        }
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addKeyListener(new KeyListener() {

            private boolean stop;

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'P' || e.getKeyChar() == 'p') {
                    if (testRef != null && testRef instanceof Runnable) {
                        testRef.PAUSE();
                    }
                }
                if (e.getKeyChar() == 'S' || e.getKeyChar() == 's') {
                    if (testRef != null && testRef instanceof Runnable) {
                        testRef.STOP();
                        stop = true;
                        dispose();
                    }
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub

            }

        });
    }

    public void dessine() {
        if (biic != null && biic.getImage() != null) {
            image = new ECBufferedImage(biic.getImage());
            if (image != null) {
                if (jPanel1 != null) {
                    Graphics g = jPanel1.getGraphics();
                    if (g != null) {
                        jPanel1.getGraphics().drawImage(image, 0, 0,
                                jPanel1.getWidth(), jPanel1.getHeight(), 0, 0,
                                image.getWidth(), image.getHeight(), null);
                        // jPanel1.getGraphics().setColor(Color.red);
                        // jPanel1.getGraphics().drawRect(0, 0, 400, 200);
                        jPanel1.getGraphics().drawString(biic.getStr(), 10, 10);
                        jPanel1.getGraphics().drawString(" ? Pause ? " + testRef.isPause() + " ? Pause active ? " + testRef.isPauseActive(), 50, 10);
                        jLabelFrame.setText("f n°" + testRef.frame() + " / " + testRef.getMaxFrames());
                        jTextField1.setText("Frame no" + (testRef.frame() + 1));
                    }
                    //Graphics gg = jPanel4.getGraphics();
                    //gimballs.draw(gg, new Rectangle(jPanel4.getWidth()-30, jPanel4.getHeight()-30, jPanel4.getWidth()-1,jPanel4.getHeight()-1));

                }
            }
        }
    }

    public void exceptionReception(Exception t) {
        this.throwable = t;
        try {
            image = new ECBufferedImage(
                    ImageIO.read(
                            getClass().getResourceAsStream("one/core/testing/skull-cross-bones-evil.ico")
                    )
            );
        } catch (IOException e) {

        }

    }

    /*__
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new JSplitPane();
        jPanel1 = new JPanel();
        jSplitPane2 = new JSplitPane();
        jPanel3 = new JPanel();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jButton3 = new JButton();
        jLabelFrame = new JLabel();
        jSliderX = new JSlider();
        jSliderY = new JSlider();
        jSliderZ = new JSlider();
        jSliderXYZ = new JSlider();
        jScrollPaneMessage = new JScrollPane();
        jTextAreaMessage = new JTextArea();
        jPanel2 = new JPanel();
        jCheckBoxOGL = new JCheckBox();
        jCheckBoxModeles = new JCheckBox();
        jCheckBoxFilmRec = new JCheckBox();
        jCheckBoxImagesRec = new JCheckBox();
        jTextField1 = new JTextField();
        jTextField2 = new JTextField();
        jButtonDemarrerNouveauFilm = new JButton();
        jScrollPane2 = new JScrollPane();
        jTableEquations = new JTable();
        jButton5 = new JButton();

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new Color(204, 204, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);

        jPanel1.setBackground(new Color(204, 255, 204));
        jPanel1.setMinimumSize(new Dimension(200, 200));
        jPanel1.setLayout(new OverlayLayout(jPanel1));
        jSplitPane1.setLeftComponent(jPanel1);

        jSplitPane2.setMinimumSize(new Dimension(200, 200));

        jButton1.setText("Parcourir le dossier");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Pause");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Quitter");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabelFrame.setText("0");
        jLabelFrame.setHorizontalTextPosition(SwingConstants.CENTER);

        jSliderX.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSliderXPropertyChange(evt);
            }
        });

        jSliderY.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSliderYPropertyChange(evt);
            }
        });

        jSliderZ.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSliderZPropertyChange(evt);
            }
        });

        jSliderXYZ.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSliderXYZPropertyChange(evt);
            }
        });

        jTextAreaMessage.setColumns(20);
        jTextAreaMessage.setRows(5);
        jScrollPaneMessage.setViewportView(jTextAreaMessage);

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabelFrame, GroupLayout.PREFERRED_SIZE, 259, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jScrollPaneMessage, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jSliderXYZ, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(jButton3, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jButton1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                                                        .addComponent(jButton2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(jSliderX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jSliderY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jSliderZ, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(327, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jButton1)
                                        .addComponent(jSliderX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jButton2)
                                        .addComponent(jSliderY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(jButton3)
                                        .addComponent(jSliderZ, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabelFrame)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPaneMessage, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jSliderXYZ, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(140, Short.MAX_VALUE))
        );

        jSplitPane2.setRightComponent(jPanel3);

        jCheckBoxOGL.setText("Open GL");
        //jCheckBoxOGL.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/be/manudahmen/empty3/core/testing/RESULT_SUCCESS.jpg"))); // NOI18N
        jCheckBoxOGL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxOGLActionPerformed(evt);
            }
        });

        jCheckBoxModeles.setText("Modèles");
        //jCheckBoxModeles.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/be/manudahmen/empty3/core/testing/RESULT_SUCCESS.jpg"))); // NOI18N
        jCheckBoxModeles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxModelesActionPerformed(evt);
            }
        });

        jCheckBoxFilmRec.setText("Enregistrer film");
        //jCheckBoxFilmRec.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/be/manudahmen/empty3/core/testing/RESULT_SUCCESS.jpg"))); // NOI18N
        jCheckBoxFilmRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxFilmRecActionPerformed(evt);
            }
        });

        jCheckBoxImagesRec.setText("Enregistrer images");
        //jCheckBoxImagesRec.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/be/manudahmen/empty3/core/testing/RESULT_SUCCESS.jpg"))); // NOI18N
        jCheckBoxImagesRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxImagesRecActionPerformed(evt);
            }
        });

        jTextField1.setText("frame#no");

        jTextField2.setText("movie#no");

        jButtonDemarrerNouveauFilm.setText("(fermer et) créer nouveau");
        jButtonDemarrerNouveauFilm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDemarrerNouveauFilmActionPerformed(evt);
            }
        });

        jTableEquations.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {"coordArr", "u"},
                        {"y", "v"},
                        {"z", "0"},
                        {null, null}
                },
                new String[]{
                        "variable", "formula"
                }
        ));
        jTableEquations.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(jTableEquations);

        jButton5.setText("Créer");
        jButton5.setToolTipText("");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(jCheckBoxOGL)
                                                                        .addComponent(jCheckBoxModeles)
                                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                                .addComponent(jCheckBoxFilmRec)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                                .addComponent(jCheckBoxImagesRec)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(jTextField1)))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jButtonDemarrerNouveauFilm, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE))
                                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                                .addGap(26, 26, 26)))
                                                .addGap(468, 468, 468))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jButton5)
                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addComponent(jCheckBoxOGL)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBoxModeles)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextField2)
                                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(jCheckBoxFilmRec)
                                                .addComponent(jButtonDemarrerNouveauFilm)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jCheckBoxImagesRec)
                                        .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5)
                                .addGap(61, 61, 61))
        );

        jSplitPane2.setLeftComponent(jPanel2);

        jSplitPane1.setRightComponent(jSplitPane2);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jSplitPane1)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jSplitPane1, GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("explorer \"" + testRef.getSubfolder().getAbsolutePath() + "\"");
        } catch (IOException ex) {
            Logger.getLogger(one.empty3.library.core.testing.ShowTestResult.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        testRef.PAUSE();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        testRef.STOP();
        stop = true;
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    public void toggleTestOption(int GEN_OPT, boolean value) {
        testRef.setGenerate(testRef.getGenerate() | ((value ? 1 : 0) | GEN_OPT));
    }

    private void jCheckBoxOGLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxOGLActionPerformed

        toggleTestOption(TestObjet.GENERATE_OPENGL, jCheckBoxOGL.isSelected());
    }//GEN-LAST:event_jCheckBoxOGLActionPerformed

    private void jCheckBoxModelesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxModelesActionPerformed
        toggleTestOption(TestObjet.GENERATE_MODEL, jCheckBoxModeles.isSelected());
    }//GEN-LAST:event_jCheckBoxModelesActionPerformed

    private void jCheckBoxFilmRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxFilmRecActionPerformed
        toggleTestOption(TestObjet.GENERATE_MOVIE, jCheckBoxFilmRec.isSelected());
    }//GEN-LAST:event_jCheckBoxFilmRecActionPerformed

    private void jCheckBoxImagesRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxImagesRecActionPerformed
        toggleTestOption(TestObjet.GENERATE_IMAGE, jCheckBoxImagesRec.isSelected());
    }//GEN-LAST:event_jCheckBoxImagesRecActionPerformed

    private void jButtonDemarrerNouveauFilmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDemarrerNouveauFilmActionPerformed
        testRef.startNewMovie();
        jTextField2.setText("New movie" + ++movieNo);
    }//GEN-LAST:event_jButtonDemarrerNouveauFilmActionPerformed

    private void jSliderXPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSliderXPropertyChange
    }//GEN-LAST:event_jSliderXPropertyChange

    private void jSliderYPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSliderYPropertyChange
    }//GEN-LAST:event_jSliderYPropertyChange

    private void jSliderZPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSliderZPropertyChange
    }//GEN-LAST:event_jSliderZPropertyChange

    private void jSliderXYZPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSliderXYZPropertyChange
    }//GEN-LAST:event_jSliderXYZPropertyChange

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String sx = "0", sy = "0", sz = "0";
        sx = (String) jTableEquations.getCellEditor(0, 1).getCellEditorValue();
        sy = (String) jTableEquations.getCellEditor(1, 1).getCellEditorValue();
        sz = (String) jTableEquations.getCellEditor(2, 1).getCellEditorValue();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        testRef.STOP();
    }//GEN-LAST:event_formWindowClosing

    public double valuePC(int v) {
        double vv = 2 * Math.PI / 100 * v;
        return vv;
    }

    public void loadImage(BufferedImage ri) {
        this.image = new ECBufferedImage(ri);
        if (image != null) {
            setSize(new Dimension(image.getWidth(), image.getHeight()));
        }
    }

    @Override
    public void run() {
        while (true && !stop) {
            dessine();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(one.empty3.library.core.testing.ShowTestResult.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
    }

    public void setImageContainer(ImageContainer ic) {
        this.biic = ic;
    }

    public void setTestObjet(TestObjet testObjet) {
        this.testRef = testObjet;

        jCheckBoxImagesRec.setSelected(
                testRef.getGenerate(TestObjet.GENERATE_IMAGE));
        jCheckBoxFilmRec.setSelected(
                testRef.getGenerate(TestObjet.GENERATE_MOVIE));
        jCheckBoxModeles.setSelected(
                testRef.getGenerate(TestObjet.GENERATE_MODEL));
        //jCheckBoxOpenGl.setSelected(
        //toggleTestOption(testRef.GENERATE_OPENGL, testRef.getGenerate(testRef.GENERATE_IMAGE));
        setTitle(testObjet.getClass().getCanonicalName());

    }

    void stopThreads() {
        stop = true;
    }

    void setMessage(String message) {
        jTextAreaMessage.setText(jTextAreaMessage.getText() + "\n" + message);
    }

}

