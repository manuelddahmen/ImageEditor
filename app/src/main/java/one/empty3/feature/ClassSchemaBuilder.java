/*
 * Created by JFormDesigner on Tue Aug 10 12:00:16 CEST 2021
 */

package one.empty3.feature;

import net.miginfocom.swing.MigLayout;

import one.empty3.feature.Classification;
import one.empty3.feature.CornerDetectProcess;
import one.empty3.feature.DBScanProcess;
import one.empty3.feature.DericheFilterProcess;
import one.empty3.feature.DiffEnergy;
import one.empty3.feature.Draw;
import one.empty3.feature.ExtremaProcess;
import one.empty3.feature.GaussFilterProcess;
import one.empty3.feature.GradProcess;
import one.empty3.feature.HarrisProcess;
import one.empty3.feature.Histogram2;
import one.empty3.feature.Histogram3;
import one.empty3.feature.HoughTransform;
import one.empty3.feature.HoughTransformCircle;
import one.empty3.feature.IdentNullProcess;
import one.empty3.feature.KMeans;
import one.empty3.feature.Lines;
import one.empty3.feature.Lines3;
import one.empty3.feature.Lines4;
import one.empty3.feature.Lines5;
import one.empty3.feature.Lines5colors;
import one.empty3.feature.LocalExtremaProcess;
import one.empty3.feature.MagnitudeProcess;
import one.empty3.feature.PixM;
import one.empty3.feature.ProxyValue;
import one.empty3.feature.ProxyValue2;
import one.empty3.feature.Transform1;
import one.empty3.feature.TrueHarrisProcess;
import one.empty3.feature.Voronoi;
import one.empty3.feature.gui.DirestEffect;
import one.empty3.feature.selection.HighlightFeatures;
import one.empty3.io.ProcessFile;
import one.empty3.library.Point2D;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import one.empty3.feature.app.replace.java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.*;
import java.util.function.Consumer;

/*
 * @author Manuel Dahmen
 */
public class ClassSchemaBuilder extends JFrame implements Serializable {

    public boolean processed = false;
    private PartElement selectedPart;
    private String directory = ".";
    private boolean selectAndClickClass = false;
    public List<File[]> files = new ArrayList<>();
    private boolean selectedActionDeleteClass = false;
    private boolean selectedActionDeleteLink;
    private DirestEffect direstEffect;
    private boolean cam;
    private int maxRes = 100;
    private String fileChooserDir = ".";
    private String tempDir;

    class DiagramElement implements Serializable {
        protected int x = getWidth() / 2;
        protected int y = getHeight() / 2;
        protected String label = "DIAGRAM ELEMENT";

        public DiagramElement() {

        }

        public void moveTo(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return "DiagramElement{" +
                    "x=" + x +
                    ", y=" + y +
                    ", label='" + label + '\'' +
                    '}';
        }
    }

    class ListFilesElement extends DiagramElement {
        File[] files;

        public File[] getFiles() {
            return files;
        }

        public void setFiles(File[] files) {
            this.files = files;
        }

        @Override
        public String toString() {
            return "ListFilesElement{" +
                    "x=" + x +
                    ", y=" + y +
                    ", label='" + label + '\'' +
                    ", files=" + Arrays.toString(files) +
                    '}';
        }
    }

    private DiagramElement selectedElement;
    private ClassElement selectedClassElement;
    private Then selectedArrow;

    class PartElement extends DiagramElement {
        DiagramElement referenceElement;
        DiagramElement element;//Nature de l'élément.
        int x, y;
        String label = "";

        public DiagramElement getReferenceElement() {
            return referenceElement;
        }

        public void setReferenceElement(DiagramElement referenceElement) {
            this.referenceElement = referenceElement;
        }

        public DiagramElement getElement() {
            return element;
        }

        public void setElement(DiagramElement element) {
            this.element = element;
        }

        @Override
        public int getX() {
            return x;
        }

        @Override
        public void setX(int x) {
            this.x = x;
        }

        @Override
        public int getY() {
            return y;
        }

        @Override
        public void setY(int y) {
            this.y = y;
        }

        @Override
        public String getLabel() {
            return label;
        }

        @Override
        public void setLabel(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return "PartElement{" +
                    "referenceElement=" + referenceElement +
                    ", element=" + element +
                    ", x=" + x +
                    ", y=" + y +
                    ", label='" + label + '\'' +
                    '}';
        }
    }

    class ClassElement extends DiagramElement {
        protected Class theClass;
        private PartElement partAfter;
        private File[] files;

        public ClassElement() {
            x = getWidth() / 2;
            y = getHeight() / 2;
            partAfter = new PartElement();
            partAfter.setReferenceElement(this);
            partElements.add(partAfter);
        }

        public Class getTheClass() {
            return theClass;
        }

        public void setTheClass(Class theClass) {
            this.theClass = theClass;
        }

        public PartElement getPartAfter() {
            return partAfter;
        }

        public void setPartAfter(PartElement partAfter) {
            this.partAfter = partAfter;
        }

        public void setInputFiles(File[] filesP) {
            this.files = filesP;
        }

        @Override
        public String toString() {
            return "ClassElement{" +
                    "theClass=" + theClass +
                    ", partAfter=" + partAfter +
                    ", files=" + Arrays.toString(files) +
                    '}';
        }
    }

    class Then extends DiagramElement {
        ClassElement a, b;

        public Then() {
            x = getWidth() / 2;
            y = getHeight() / 2;
        }

        public ClassElement getA() {
            return a;
        }

        public void setA(ClassElement a) {
            this.a = a;
        }

        public ClassElement getB() {
            return b;
        }

        public void setB(ClassElement b) {
            this.b = b;
        }


        @Override
        public String toString() {
            return "Then{" +
                    "x=" + x +
                    ", y=" + y +
                    ", label='" + label + '\'' +
                    ", a=" + a +
                    ", b=" + b +
                    '}';
        }
    }

    private List<DiagramElement> diagramElements = new ArrayList<DiagramElement>();
    private List<PartElement> partElements = new ArrayList<PartElement>();

    ArrayList<ProcessFile> listProcessClasses = new ArrayList<>();

    public ClassSchemaBuilder() {
        initComponents();

        addMouseMotionListener(new MouseMotionAdapter() {
            private Point point;
            private DiagramElement element;
            private Point source;
            private boolean isMouseDragged = false;

            @Override
            public void mouseDragged(MouseEvent e) {
                if (!isMouseDragged && e.getButton() == 0 && (selectedElement == selectFromPoint(e.getX(), e.getY())
                        || (selectedPart == selectPartFromPoint(e.getX(), e.getY()) || selectedPart == null))
                ) {
                    source = e.getPoint();
                    element = selectFromPoint(source.x, source.y);
                    if (element != null) {
                        labelStatus.setText("Drag element: " + element.label);
                        selectedElement = element;
                    }

                }
                point = e.getPoint();
                if (!isMouseDragged && e.getButton() == 0)
                    isMouseDragged = true;
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (isMouseDragged && element != null && point != null && e.getButton() == 0) {
                    element.moveTo(point.x, point.y);
                    PartElement partElement = selectPartFromPoint(e.getX(), e.getY());
                    isMouseDragged = false;
                    element = null;
                }
                isMouseDragged = false;
            }
        });

        addMouseListener(new MouseListener() {
            private static final int ADD_1 = 1;
            private static final int ADD_2 = 2;
            private int currentAction = 0;

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (selectAndClickClass) {
                        DiagramElement diagramElement = selectFromPoint(e.getX(), e.getY());
                        if (diagramElement instanceof ClassElement) {
                            ((ClassElement) diagramElement).setInputFiles(files.get(files.size() - 1));
                        }
                        selectAndClickClass = false;
                    } else if (selectedActionDeleteClass) {
                        DiagramElement diagramElement = selectFromPoint(e.getX(), e.getY());
                        partElements.remove(((ClassElement) diagramElement).partAfter);
                        ((ClassElement) diagramElement).partAfter = new PartElement();
                        diagramElements.remove(diagramElement);
                    } else if (selectedActionDeleteLink) {
                        DiagramElement diagramElement = selectFromPoint(e.getX(), e.getY());
                        PartElement partElement = selectPartFromPoint(e.getX(), e.getY());
                        if (diagramElement instanceof ClassElement && partElement != null && partElement instanceof PartElement) {
                            ((ClassElement) (diagramElement)).partAfter.referenceElement = null;
                            ((ClassElement) (diagramElement)).partAfter.element = null;
                        }
                    }
                    if (currentAction == 0) {

                        if (selectFromPoint(e.getX(), e.getY()) != null &&
                                selectFromPoint(e.getX(), e.getY()).getClass().equals(ClassElement.class)) {
                            selectedElement = selectFromPoint(e.getX(), e.getY());
                            selectedPart = selectPartFromPoint(e.getX(), e.getY());
                            currentAction = ADD_1;
                            labelStatus.setText("Selected element: " + selectedElement.label + " Mode: Select class2...");
                        }
                    } else if (currentAction == ADD_1) {
                        DiagramElement diagramElement = selectFromPoint(e.getX(), e.getY());
                        if (diagramElement instanceof ClassElement && selectedElement instanceof ClassElement) {
                            ((ClassElement) selectedElement).partAfter.referenceElement = diagramElement;
                            ((ClassElement) selectedElement).partAfter.element = diagramElement;
                            currentAction = 0;
                            labelStatus.setText("Selected element: " + selectedElement.label + " Class 2 element:" + diagramElement.label);
                        }


                    }
                } catch (NullPointerException ex) {
                    System.out.printf("Error : null");
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        Thread thread = new Thread(() -> {
            while (true) {
                drawAllElements();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


    private void buttonThenActionPerformed(ActionEvent e) {
        diagramElements.add(new Then());
    }

    private void buttonAddToActionPerformed(ActionEvent e) {
        ClassElement classElement = new ClassElement();
        classElement.theClass = comboBox1.getSelectedItem().getClass();
        diagramElements.add(classElement);
    }

    private void buttonFilesActionPerformed(ActionEvent e) {
        ListFilesElement listFilesElement = new ListFilesElement();
        FileDialog fileDialog = new FileDialog(this, "Select file to open/save", FileDialog.LOAD);
        //fileDialog.setLayout(new MigLayout());
        fileDialog.setMultipleMode(true);
        fileDialog.setVisible(true);
        if ((fileDialog.getFiles()) != null) {
            listFilesElement.setFiles(fileDialog.getFiles());
            files.add(fileDialog.getFiles());
        }
        diagramElements.add(listFilesElement);
        selectAndClickClass = true;
    }

    public void setMaxRes(int maxRes) {
        this.maxRes = maxRes;
    }

    public int getMaxRes() {
        return maxRes;
    }

    private void button3ActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    public void buttonGOActionPerformed(ActionEvent e) {
        processed = false;
        System.out.printf("Run processes\n");
        System.out.printf("Check stack of processes (linked)\n");
        System.out.printf("Check inputs (images files jpg mp4 decoder)\n");
        System.out.printf("Check outputs (folders overwrite/change folder)\n");
        System.out.printf("Run on images\n");

        // select class which have no predecessor and have files[] attached.
        List<ProcessFile> processes = new ArrayList<>();
        for (DiagramElement classElement : diagramElements) {
            if (classElement instanceof ClassElement) {
                ClassElement ce = (ClassElement) classElement;
                ClassElement first = ce;
                Class theClass = ce.getTheClass();
                if (ce.partAfter.element != null && ce.partAfter.element != ce) {
                    try {
                        System.out.printf("Start process stack\n");
                        int n = 1;
                        while (ce.partAfter.element != null && ce.partAfter.element != ce) {
                            ce = (ClassElement) ce.partAfter.element;
                            processes.add((ProcessFile) ce.theClass.newInstance());
                            processes.get(processes.size() - 1).setMaxRes(maxRes);
                        }
                    } catch (InstantiationException | IllegalAccessException instantiationException) {
                        instantiationException.printStackTrace();
                    }
                }
            }
        }
        //ResourceBundle globalSettings = ResourceBundle.getBundle("settings.properties");
        Properties globalSettings = new Properties();
        try {
            globalSettings.load(new FileInputStream("settings.properties"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        tempDir = globalSettings.getProperty("tempDir") + File.separator;
        if (tempDir == null)
            tempDir = "temp/";
        new File(tempDir).mkdirs();
        if (files == null) {
            System.out.printf("Error first.files==null");
        }

        direstEffect.threadEffectDisplay.setTempDir(tempDir);

        if (processes.size() > 0) {
            for (File[] files : this.files) {
                for (File f : files) {
                    int imageSource = 0;
                    if (f.getName().contains("webcam")) {
                        f = new File(tempDir + File.separator + "webcam.jpg");
                        imageSource = 1;
                    }
                    ProcessFile ce = processes.get(0);
                    int processNameOrder = listProcessClasses.indexOf(ce.getClass());
                    StringBuilder s = new StringBuilder();
                    s.append("-").append(listProcessClasses.indexOf(ce));
                    String s0 = "";
                    System.out.printf("Process %s \nfrom:  %s\n", processes.get(0).getClass().toString(), f.getAbsolutePath());
                    File fileOut = new File(tempDir + File.separator + f.getName() + s + ".jpg");
                    ce.process(f, fileOut);
                    for (int i = 1; i < processes.size(); i++) {
                        ce = processes.get(i);
                        s0 = s.toString();
                        s.append("-").append(listProcessClasses.indexOf(ce));
                        System.out.printf("Process %s \nfrom:  %s\n", ce.getClass().toString(), f.getName());
                        fileOut = new File(tempDir + File.separator + f.getName()
                                + s + ".jpg");
                        File fileIn = new File(tempDir + File.separator + f.getName() //+ (i - 1)
                                + s0 + ".jpg");
                        ce.addSource(f);//???
                        ce.addSource(fileIn);//???
                        ce.process(fileIn, fileOut);

                    }
                    if (imageSource == 1 && fileOut != f) {
                        File filePaste = new File(fileOut.getAbsolutePath() + "-paste.jpg");
                        try {
                            one.empty3.feature.IdentNullProcess identNullProcess = new one.empty3.feature.IdentNullProcess();
                            identNullProcess.setMaxRes(getMaxRes());
                            identNullProcess.process(f, filePaste);
                            one.empty3.feature.PixM original = new one.empty3.feature.PixM(ImageIO.read(filePaste));
                            original.colorsRegionWithMask(0, 0, original.getColumns(), original.getLines(),
                                    new one.empty3.feature.PixM(ImageIO.read(fileOut)), new PixM(ImageIO.read(fileOut)));
                            ImageIO.write(original.normalize(0, 1).getImage(), "jpg", fileOut);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        direstEffect.setFileIn(fileOut);
                        direstEffect.setBusy(false);
                    }
                }
            }
        } else {
            System.err.printf("ERROR Process array length = 0");
        }
        processed = true;

    }

    private void buttonDeleteClassActionPerformed(ActionEvent e) {
        selectedActionDeleteClass = ((JToggleButton) e.getSource()).isSelected();
    }

    private void buttonDeleteLinkActionPerformed(ActionEvent e) {
        selectedActionDeleteLink = ((JToggleButton) e.getSource()).isSelected();

    }

    private void buttonAddLinkActionPerformed(ActionEvent e) {
        labelStatus.setText("Method: Use click on class1, then class2 ");
    }

    private void buttonLoadActionPerformed(ActionEvent e) {
        String[] currentDirin = null;
        String server = "";
        int port = 0;
        String username = "";
        String password = "";
        String[] classnamesArr = null;
        JFileChooser jFileChooser = new JFileChooser(directory);

        jFileChooser.setMultiSelectionEnabled(false);
        if (JFileChooser.APPROVE_OPTION == jFileChooser.showOpenDialog(this)) {
            File selectedFile = jFileChooser.getSelectedFile();
            Properties appFile = new Properties();
            if (selectedFile.getName().endsWith(".properties")) {
                try {
                    appFile.load(new FileInputStream(selectedFile));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                currentDirin = (appFile.getProperty("in.directory")).split(",");
                if ("local".equals(appFile.getProperty("in.device"))) {
                    server = "file";
                    port = 0;
                    username = "";
                    password = "";
                } else {
                    server = (String) appFile.getProperty("host");
                    port = Integer.parseInt(appFile.getProperty("port"));
                    username = (String) appFile.getProperty("username");
                    password = (String) appFile.getProperty("password");
                }


                String maxFilesInDir0 = appFile.getProperty("maxFilesInDir");
                maxRes = -1;

                String maxResStr = appFile.getProperty("maxRes");
                if (maxResStr != null)
                    maxRes = Integer.parseInt(maxResStr);
                if (maxRes <= 0)
                    maxRes = 200;
                /* String*/
                String classnames = (String) appFile.getProperty("classname");
                String class0 = (String) appFile.getProperty("class0");
                String directoryOut = (String) appFile.getProperty("out.directory");


                String sep = "";
                int i = 0;
                if (class0 == null || class0.equals("")) {
                    sep = "";
                }
                classnamesArr = classnames.split(",");

                int j0 = diagramElements.size();
                int j = j0;
                for (i = 0; i < classnamesArr.length; i++) {
                    try {
                        ClassElement classElement = new ClassElement();
                        diagramElements.add(classElement);
                        classElement.theClass = Class.forName(classnamesArr[i]);
                        j++;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                for (i = j0; i < j0 + classnamesArr.length - 1; i++) {
                    ((ClassElement) (diagramElements.get(i))).partAfter.element = diagramElements.get(i + 1);
                    ((ClassElement) (diagramElements.get(i))).partAfter.referenceElement
                            = diagramElements.get(i);
                }
            } else {
                // Load Java Data from toString.
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(selectedFile));

                    Date date = (Date) objectInputStream.readObject();
                    ClassSchemaBuilder c = new ClassSchemaBuilder();
                    c.diagramElements = (List<DiagramElement>) objectInputStream.readObject();
                    c.files = (List<File[]>) objectInputStream.readObject();
                    c.partElements = (List<PartElement>) objectInputStream.readObject();
                    c.listProcessClasses = (ArrayList<ProcessFile>) objectInputStream.readObject();
                    c.setVisible(true);


                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    private void buttonSaveActionPerformed(ActionEvent e) {

        JFileChooser jFileChooser = new JFileChooser(directory);

        jFileChooser.setMultiSelectionEnabled(false);
        if (JFileChooser.APPROVE_OPTION == jFileChooser.showOpenDialog(this)) {
            File selectedFile = jFileChooser.getSelectedFile();
            ObjectOutputStream oos = null;
            try {
                FileOutputStream fichier = new FileOutputStream(selectedFile);
                oos = new ObjectOutputStream(fichier);
                oos.writeObject(new Date());
                oos.writeObject(this.diagramElements);
                oos.writeObject(this.files);
                oos.writeObject(this.partElements);
                oos.writeObject(this.listProcessClasses);

                oos.flush();
            } catch (final IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (oos != null) {
                        oos.flush();
                        oos.close();
                    }
                } catch (final IOException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }

    private void buttonCamActionPerformed(ActionEvent e) {
        cam = ((JToggleButton) e.getSource()).isSelected();
        if (direstEffect == null) {
            direstEffect = new DirestEffect();
        }
        direstEffect.setVisible(cam);
        direstEffect.setMainWindow(this);
        /*if (cam)
            if (!direstEffect.threadEffectDisplay.busy && direstEffect.threadEffectDisplay.isAlive())
                direstEffect.threadEffectDisplay.start();
                +/
         */
        files = new ArrayList<>();
        files.add(new File[]{new File("webcam")});
    }

    private void buttonPictureRecodeActionPerformed(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser(fileChooserDir);

        jFileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return Arrays.stream(ImageIO.getReaderFormatNames()).anyMatch(s -> f.toPath().endsWith(s.substring(s.lastIndexOf('.'))));
            }

            @Override
            public String getDescription() {
                return "All images known types";
            }
        });


        jFileChooser.setMultiSelectionEnabled(true);

        jFileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        jFileChooser.showOpenDialog(this);

        final File[] selectedFiles = jFileChooser.getSelectedFiles();

        if (selectedFiles != null) {
            //for (int i = 0; i < selectedFiles.length; i++) {
            //File selectedFile = selectedFiles[i];
            this.files.add(selectedFiles);
            buttonGOActionPerformed(null);
            //}
        } else {
            System.out.println("Nothing chosen");
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        comboBox1 = new JComboBox();
        button2 = new JButton();
        buttonAddLink = new JButton();
        buttonFiles = new JButton();
        buttonDeleteClass = new JToggleButton();
        buttonDeleteLink = new JToggleButton();
        buttonGO = new JButton();
        buttonLoad = new JButton();
        buttonSave = new JButton();
        panel1 = new JPanel();
        labelStatus = new JLabel();
        button1 = new JButton();
        buttonPictureRecode = new JButton();
        buttonCam = new JToggleButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Filter stack constructor");
        setVisible(true);
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
                "insets 0,hidemode 3,gap 5 5",
                // columns
                "[fill]" +
                        "[fill]" +
                        "[fill]",
                // rows
                "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[]"));

        //---- comboBox1 ----
        comboBox1.setMaximumRowCount(20);
        comboBox1.setAutoscrolls(true);
        comboBox1.setLightWeightPopupEnabled(false);
        comboBox1.setDoubleBuffered(true);
        contentPane.add(comboBox1, "cell 0 0");

        //---- button2 ----
        button2.setText("Add");
        button2.addActionListener(e -> buttonAddToActionPerformed(e));
        contentPane.add(button2, "cell 1 0");

        //---- buttonAddLink ----
        buttonAddLink.setText("Add link");
        buttonAddLink.addActionListener(e -> buttonAddLinkActionPerformed(e));
        contentPane.add(buttonAddLink, "cell 2 0");

        //---- buttonFiles ----
        buttonFiles.setText("Add input files");
        buttonFiles.addActionListener(e -> buttonFilesActionPerformed(e));
        contentPane.add(buttonFiles, "cell 2 0");

        //---- buttonDeleteClass ----
        buttonDeleteClass.setText("Delete class");
        buttonDeleteClass.addActionListener(e -> buttonDeleteClassActionPerformed(e));
        contentPane.add(buttonDeleteClass, "cell 2 0");

        //---- buttonDeleteLink ----
        buttonDeleteLink.setText("Delete link");
        buttonDeleteLink.addActionListener(e -> buttonDeleteLinkActionPerformed(e));
        contentPane.add(buttonDeleteLink, "cell 2 0");

        //---- buttonGO ----
        buttonGO.setText("Process GO");
        buttonGO.addActionListener(e -> {
            buttonThenActionPerformed(e);
            button3ActionPerformed(e);
            buttonGOActionPerformed(e);
            buttonGOActionPerformed(e);
        });
        contentPane.add(buttonGO, "cell 2 0");

        //---- buttonLoad ----
        buttonLoad.setText("Load");
        buttonLoad.addActionListener(e -> buttonLoadActionPerformed(e));
        contentPane.add(buttonLoad, "cell 2 0");

        //---- buttonSave ----
        buttonSave.setText("Save");
        buttonSave.addActionListener(e -> buttonSaveActionPerformed(e));
        contentPane.add(buttonSave, "cell 2 0");

        //======== panel1 ========
        {
            panel1.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "[fill]" +
                            "[fill]",
                    // rows
                    "[]" +
                            "[]" +
                            "[]"));
        }
        contentPane.add(panel1, "cell 0 1 3 2,dock center");

        //---- labelStatus ----
        labelStatus.setText("text");
        contentPane.add(labelStatus, "cell 0 3 2 1");

        //---- button1 ----
        button1.setText("Open movie/recode");
        contentPane.add(button1, "cell 2 3");

        //---- buttonPictureRecode ----
        buttonPictureRecode.setText("Open picture/recode");
        buttonPictureRecode.addActionListener(e -> buttonPictureRecodeActionPerformed(e));
        contentPane.add(buttonPictureRecode, "cell 2 3");

        //---- buttonCam ----
        buttonCam.setText("Open webcam");
        buttonCam.addActionListener(e -> buttonCamActionPerformed(e));
        contentPane.add(buttonCam, "cell 2 3");
        setSize(955, 355);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents

        try {
            listProcessClasses.add(Classification.class.newInstance());
            listProcessClasses.add(CornerDetectProcess.class.newInstance());
            listProcessClasses.add(CurveFitting.class.newInstance());
            listProcessClasses.add(DBScanProcess.class.newInstance());
            listProcessClasses.add(DericheFilterProcess.class.newInstance());
            listProcessClasses.add(DiffEnergy.class.newInstance());
            listProcessClasses.add(Draw.class.newInstance());
            listProcessClasses.add(ExtremaProcess.class.newInstance());
            listProcessClasses.add(GaussFilterProcess.class.newInstance());
            listProcessClasses.add(GradProcess.class.newInstance());
            listProcessClasses.add(HarrisProcess.class.newInstance());
            listProcessClasses.add(Histogram2.class.newInstance());
            listProcessClasses.add(Histogram3.class.newInstance());
            listProcessClasses.add(HoughTransform.class.newInstance());
            listProcessClasses.add(HoughTransformCircle.class.newInstance());
            listProcessClasses.add(IdentNullProcess.class.newInstance());
            listProcessClasses.add(IsleProcess.class.newInstance());
            listProcessClasses.add(KMeans.class.newInstance());
            listProcessClasses.add(Lines.class.newInstance());
            listProcessClasses.add(Lines3.class.newInstance());
            listProcessClasses.add(Lines4.class.newInstance());
            listProcessClasses.add(Lines5.class.newInstance());
            listProcessClasses.add(Lines5colors.class.newInstance());
            listProcessClasses.add(Lines6.class.newInstance());
            listProcessClasses.add(LocalExtremaProcess.class.newInstance());
            listProcessClasses.add(MagnitudeProcess.class.newInstance());
            listProcessClasses.add(ProxyValue.class.newInstance());
            listProcessClasses.add(ProxyValue2.class.newInstance());
            listProcessClasses.add(RegionLineCorner.class.newInstance());
            listProcessClasses.add(Transform1.class.newInstance());
            listProcessClasses.add(TrueHarrisProcess.class.newInstance());
            listProcessClasses.add(Voronoi.class.newInstance());
            listProcessClasses.add(HighlightFeatures.class.newInstance());

            listProcessClasses.forEach(new Consumer<ProcessFile>() {
                @Override
                public void accept(ProcessFile processFile) {
                    comboBox1.addItem(processFile);
                }
            });
            ;

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void drawElement(Graphics g, DiagramElement diagramElement) {
        g.drawString(diagramElement.label, diagramElement.x, diagramElement.y);
        if (diagramElement instanceof ClassElement) {

            g.drawString(((ClassElement) diagramElement).theClass.getName(), diagramElement.x, diagramElement.y + 32);

        }
        if (diagramElement instanceof ClassElement) {
            if (((ClassElement) diagramElement).partAfter.element != null) {
                int x = ((ClassElement) diagramElement).partAfter.element.x;
                int y = ((ClassElement) diagramElement).partAfter.element.y;
                g.setColor(Color.GRAY);
                g.drawLine(diagramElement.x, diagramElement.y, x, y);
            }
        }
    }

    public DiagramElement selectFromPoint(int x, int y) {
        double distance = 100000;
        DiagramElement diagramElementNear = null;
        for (DiagramElement diagramElement : diagramElements) {
            if (Point2D.dist(new Point2D(x, y), new Point2D(diagramElement.x, diagramElement.y)) < distance) {
                distance = Point2D.dist(new Point2D(x, y), new Point2D(diagramElement.x, diagramElement.y));
                diagramElementNear = diagramElement;
            }
        }
        return diagramElementNear;
    }


    private PartElement selectPartFromPoint(int x, int y) {
        double distance = 100000;
        DiagramElement diagramElementNear = selectFromPoint(x, y);
        if (selectedElement == diagramElementNear) {
            for (DiagramElement diagramElement : partElements) {
                if (diagramElement != null)
                    if (Point2D.dist(new Point2D(x, y), new Point2D(diagramElement.x, diagramElement.y)) < distance) {
                        for (PartElement partElement : partElements) {
                            DiagramElement diagramElement1 = ((PartElement) partElement).getReferenceElement();
                            if (diagramElement == diagramElement1) {
                                distance = Point2D.dist(new Point2D(x, y), new Point2D(diagramElement.x, diagramElement.y));
                                selectedElement = diagramElement;
                                selectedPart = partElement;
                            }
                        }
                    }
            }
        }
        return selectedPart;
    }

    public void drawAllElements() {
        BufferedImage bi = new BufferedImage(panel1.getWidth(), panel1.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, panel1.getWidth(), panel1.getHeight());

        Point location = MouseInfo.getPointerInfo().getLocation();
        DiagramElement selected = selectFromPoint(location.x, location.y);
        for (DiagramElement diagramElement : diagramElements) {
            g.setColor(Color.WHITE);
            if (diagramElement == selected)
                g.setColor(Color.GREEN);
            else if (selectedElement == diagramElement)
                g.setColor(Color.BLUE);
            drawElement(g, diagramElement);
        }
        Graphics graphics = panel1.getGraphics();
        graphics.drawImage(bi, 0, 0, panel1.getWidth(), panel1.getHeight(), this);
    }

    public void addClassToSchema() {
        ClassElement classElement = new ClassElement();
        diagramElements.add(classElement);
    }

    public void addThenToSchema() {
        Then thenElement = new Then();
        diagramElements.add(thenElement);
    }

    public static void main(String[] args) {
        new ClassSchemaBuilder().setVisible(true);
    }


    @Override
    public String toString() {
        List<File> f = new ArrayList<>();
        for (File[] files2 : files) {
            for (File file : files2) {
                f.add(file);
            }
        }
        return "ClassSchemaBuilder{" +
                "selectedPart=" + selectedPart +
                ", directory='" + directory + '\'' +
                ", files2=" + f +
                ", selectedElement=" + selectedElement +
                ", selectedArrow=" + selectedArrow +
                ", diagramElements=" + diagramElements +
                ", partElements=" + partElements +
                ", listProcessClasses=" + listProcessClasses +
                '}';
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JComboBox comboBox1;
    private JButton button2;
    private JButton buttonAddLink;
    private JButton buttonFiles;
    private JToggleButton buttonDeleteClass;
    private JToggleButton buttonDeleteLink;
    private JButton buttonGO;
    private JButton buttonLoad;
    private JButton buttonSave;
    private JPanel panel1;
    private JLabel labelStatus;
    private JButton button1;
    private JButton buttonPictureRecode;
    private JToggleButton buttonCam;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

}
