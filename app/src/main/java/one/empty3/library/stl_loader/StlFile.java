/*
 *  This file is part of Empty3.
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
 *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
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

package one.empty3.library.stl_loader;

import one.empty3.library.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.StringTokenizer;

// New from JDK 1.4 for endian related problems

/*__
 * Title: STL Loader Description: STL files loader (Supports ASCII and binary
 * files) for Java3D Needs JDK 1.4 due to endian problems Company: Universidad
 * del Pais Vasco (UPV/EHU)
 *
 * @author: Carlos Pedrinaci Godoy
 * @version: 1.0
 * <p>
 * Contact : xenicp@yahoo.es
 * <p>
 * <p>
 * Things TO-DO: 1.-We can't read binary files over the net. 2.-For binary files
 * if size is lower than expected (calculated with the number of faces) the
 * program will block. 3.-Improve the way for detecting the kind of stl file?
 * Can give us problems if the comment of the binary file begins by "solid"
 */
public class StlFile {

    private static final int DEBUG = 0;     // Sets mode to Debug: outputs every action done

    // Maximum length (in chars) of basePath
    private static final int MAX_PATH_LENGTH = 1024;

    // Global variables
    private int flag;                         // Needed cause implements Loader

    private URL baseUrl = null;               // Reading files over Internet
    private String basePath = null;           // For local files

    private boolean fromUrl = false;          // Usefull for binary files
    private boolean Ascii = true;             // File type Ascii -> true o binary -> false
    private String fileName = null;

    // Arrays with coordinates and normals
    // Needed for reading ASCII files because its size is unknown until the end
    private ArrayList<Point3D> coordList;        // Holds Point3f
    private ArrayList<Point3D> normList;        // Holds Vector3f

    // GeometryInfo needs Arrays
    private Point3D[] coordArray = null;
    private Point3D[] normArray = null;

    // Needed because TRIANGLE_STRIP_ARRAY
    // As the number of strips = the number of faces it's filled in objectToVectorArray
    private int[] stripCounts = null;

    // Default = Not available
    private String objectName = "Not available";

    /*__
     * Constructor
     */
    public StlFile() {
    }

    public boolean getAscii() {
        return this.Ascii;
    }

    public void setAscii(boolean tipo) {
        this.Ascii = tipo;
    }

    public String getBasePath() {
        return basePath;
    }

    /*__
     * Set the path where files associated with this .stl file are located. Only
     * needs to be called to set it to a different directory from that
     * containing the .stl file.
     *
     * @param pathName The new Path to the file
     */
    public void setBasePath(String pathName) {
        basePath = pathName;
        if (basePath == null || "".equals(basePath)) {
            basePath = "." + java.io.File.separator;
        }
        basePath = basePath.replace('/', java.io.File.separatorChar);
        basePath = basePath.replace('\\', java.io.File.separatorChar);
        if (!basePath.endsWith(java.io.File.separator)) {
            basePath = basePath + java.io.File.separator;
        }
    } // End of setBasePath

    public URL getBaseUrl() {
        return baseUrl;
    }

    /*__
     * Modifier for baseUrl, if accessing internet.
     *
     * @param url The new url
     */
    public void setBaseUrl(URL url) {
        baseUrl = url;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String filename) {
        this.fileName = filename;
    }

    public int getFlags() {
        return flag;
    }

    public void setFlags(int parm) {
        this.flag = parm;
    }

    public String getObjectName() {
        return this.objectName;
    }

    public void setObjectName(String name) {
        this.objectName = name;
    }

    /*__
     * The Stl File is loaded from the already opened file. To attach the model
     * to your scene, call getSceneGroup() on the Scene object passed back, and
     * attach the returned BranchGroup to your scene graph. For an example, see
     * $J3D/programs/examples/ObjLoad/ObjLoad.java.
     *
     * @param reader The reader to read the object from
     * @return Scene The scene with the object loaded.
     * @throws FileNotFoundException
     * @throws IncorrectFormatException
     * @throws ParsingErrorException
     */
    public Scene load(Reader reader) throws FileNotFoundException,
            IncorrectFormatException,
            ParsingErrorException {
        // That method calls the method that loads the file for real..
        // Even if the Stl format is not complicated I've decided to use
        // a parser as in the Obj's loader included in Java3D

        StlFileParser st = new StlFileParser(reader);

        // Initialize data
        coordList = new ArrayList();
        normList = new ArrayList();
        setAscii(true);      // Default ascii

        readFile(st);
        return makeScene();
    }

    /*__
     * The Stl File is loaded from the .stl file specified by the filename. To
     * attach the model to your scene, call getSceneGroup() on the Scene object
     * passed back, and attach the returned BranchGroup to your scene graph. For
     * an example, see $J3D/programs/examples/ObjLoad/ObjLoad.java.
     *
     * @param filename The name of the file with the object to load
     * @return Scene The scene with the object loaded.
     * @throws FileNotFoundException
     * @throws IncorrectFormatException
     * @throws ParsingErrorException
     */
    public Scene load(String filename) throws FileNotFoundException,
            IncorrectFormatException,
            ParsingErrorException {
        setBasePathFromFilename(filename);
        setFileName(filename);     // For binary files

        Reader reader = new BufferedReader(new FileReader(filename));
        return load(reader);
    } // End of load(String)

    /*__
     * The Stl file is loaded off of the web. To attach the model to your scene,
     * call getSceneGroup() on the Scene object passed back, and attach the
     * returned BranchGroup to your scene graph. For an example, see
     * $J3D/programs/examples/ObjLoad/ObjLoad.java.
     *
     * @param url The url to load the onject from
     * @return Scene The scene with the object loaded.
     * @throws FileNotFoundException
     * @throws IncorrectFormatException
     * @throws ParsingErrorException
     */
    public Scene load(URL url) throws FileNotFoundException,
            IncorrectFormatException,
            ParsingErrorException {
        BufferedReader reader;

        setBaseUrlFromUrl(url);

        try {
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
        fromUrl = true;
        return load(reader);
    } // End of load(URL)

    /*__
     * Method that creates the SceneBase with the stl file info
     *
     * @return SceneBase The scene
     */
    private Scene makeScene() {
        // Create Scene to pass back
        Scene scene = new Scene();
        RepresentableConteneur RC = new RepresentableConteneur();
        scene.add(RC);

        // Convert ArrayLists to arrays: only needed if file was not binary
        if (this.Ascii) {
            coordArray = objectToPoint3Array(coordList);
            normArray = objectToVectorArray(normList);
        }

        for (int i = 0; i < coordList.size() / 3; i += 3) {
            TRI tri = new TRI();
            tri.setSommet(
                    new Point3D[]{
                            coordList.get(i * 3 + 0),
                            coordList.get(i * 3 + 1),
                            coordList.get(i * 3 + 2)
                    }
            );
            RC.add(tri);
        }

        return scene;
    } // end of makeScene

    /*__
     * Method that takes the info from an ArrayList of Point3f and returns a
     * Point3f[]. Needed for ASCII files as we don't know the number of facets
     * until the end
     *
     * @param inList The list to transform into Point3f[]
     * @return Point3D[] The result.
     */
    private Point3D[] objectToPoint3Array(ArrayList inList) {
        Point3D outList[] = new Point3D[inList.size()];

        for (int i = 0; i < inList.size(); i++) {
            outList[i] = (Point3D) inList.get(i);
        }
        return outList;
    } // End of objectToPoint3Array

    /////////////////////// Accessors and Modifiers ///////////////////////////

    /*__
     * Method that takes the info from an ArrayList of Vector3f and returns a
     * Vector3f[]. Needed for ASCII files as we don't know the number of facets
     * until the end
     * <p>
     * TO-DO: 1.- Here we fill stripCounts... Find a better place to do it?
     *
     * @param inList The list to transform into Point3f[]
     * @return Point3D[] The result.
     */
    private Point3D[] objectToVectorArray(ArrayList inList) {
        Point3D outList[] = new Point3D[inList.size()];

        if (DEBUG == 1) {
            System.out.println("Number of facets of the object=" + inList.size());
        }

        // To-do
        stripCounts = new int[inList.size()];
        for (int i = 0; i < inList.size(); i++) {
            outList[i] = (Point3D) inList.get(i);
            // To-do
            stripCounts[i] = 3;
        }
        return outList;
    } // End of objectToVectorArray

    /*__
     * Method for reading binary files Execution is completly different It uses
     * ByteBuffer for reading data and ByteOrder for retrieving the machine's
     * endian (Needs JDK 1.4)
     * <p>
     * TO-DO: 1.-Be able to read files over Internet 2.-If the amount of data
     * expected is bigger than what is on the file then the program will block
     * forever
     *
     * @param file The name of the file
     * @throws IOException
     * @throws IncorrectFormatException
     */
    private void readBinaryFile(String file) throws IOException, IncorrectFormatException {
        FileInputStream data;                 // For reading the file
        ByteBuffer dataBuffer;                // For reading in the correct endian
        byte[] Info = new byte[80];             // Header data
        byte[] Array_number = new byte[4];     // Holds the number of faces
        byte[] Temp_Info;                     // Intermediate array

        int Number_faces; // First info (after the header) on the file

        if (DEBUG == 1) {
            System.out.println("Machine's endian: " + ByteOrder.nativeOrder());
        }

        // Get file's name
        if (fromUrl) {
            // FileInputStream can only read local files!?
            System.out.println("This version doesn't support reading binary files from internet");
        } else { // It's a local file
            data = new FileInputStream(file);

            // First 80 bytes aren't important
            if (80 != data.read(Info)) { // File is incorrect
                //System.out.println("Format Error: 80 bytes expected");
                throw new IncorrectFormatException();
            } else { // We must first read the number of faces -> 4 bytes int
                // It depends on the endian so..

                data.read(Array_number);                      // We get the 4 bytes
                dataBuffer = ByteBuffer.wrap(Array_number);   // ByteBuffer for reading correctly the int
                dataBuffer.order(ByteOrder.nativeOrder());    // Set the right order
                Number_faces = dataBuffer.getInt();

                Temp_Info = new byte[50 * Number_faces];        // Each face has 50 bytes of data

                data.read(Temp_Info);                         // We get the rest of the file

                dataBuffer = ByteBuffer.wrap(Temp_Info);      // Now we have all the data in this ByteBuffer
                dataBuffer.order(ByteOrder.nativeOrder());

                if (DEBUG == 1) {
                    System.out.println("Number of faces= " + Number_faces);
                }

                // We can create that array directly as we know how big it's going to be
                coordArray = new Point3D[Number_faces * 3]; // Each face has 3 vertex
                normArray = new Point3D[Number_faces];
                stripCounts = new int[Number_faces];

                for (int i = 0; i < Number_faces; i++) {
                    stripCounts[i] = 3;
                    try {
                        readFacetB(dataBuffer, i);
                        // After each facet there are 2 bytes without information
                        // In the last iteration we dont have to skip those bytes..
                        if (i != Number_faces - 1) {
                            dataBuffer.get();
                            dataBuffer.get();
                        }
                    } catch (IOException e) {
                        // Quitar
                        System.out.println("Format Error: iteration number " + i);
                        throw new IncorrectFormatException();
                    }
                }//End for
            }// End file reading
        }// End else
    }// End of readBinaryFile

    /*__
     * Method that reads "endfacet" then EOL
     *
     * @param parser The file parser. An instance of StlFileParser.
     */
    private void readEndFacet(StlFileParser parser) {
        if (!(parser.ttype == StlFileParser.TT_WORD && parser.sval.equals("endfacet"))) {
            System.err.println("Format Error:expecting 'endfacet' on line " + parser.lineno());
        } else {
            readEOL(parser);
        }
    }//End of readEndFacet

    /*__
     * Method that reads "endloop" then EOL
     *
     * @param parser The file parser. An instance of StlFileParser.
     */
    private void readEndLoop(StlFileParser parser) {
        if (!(parser.ttype == StlFileParser.TT_WORD && parser.sval.equals("endloop"))) {
            System.err.println("Format Error:expecting 'endloop' on line " + parser.lineno());
        } else {
            readEOL(parser);
        }
    }//End of readEndLoop

    /*__
     * Method that reads the EOL Needed for verifying that the file has a
     * correct format
     *
     * @param parser The file parser. An instance of StlFileParser.
     */
    private void readEOL(StlFileParser parser) {
        try {
            parser.nextToken();
        } catch (IOException e) {
            System.err.println("IO Error on line " + parser.lineno() + ": " + e.getMessage());
        }
        if (parser.ttype != StlFileParser.TT_EOL) {
            System.err.println("Format Error:expecting End Of Line on line " + parser.lineno());
        }
    }

    /*__
     * Method that reads a face of the object (Cares about the format)
     *
     * @param parser The file parser. An instance of StlFileParser.
     */
    private void readFacet(StlFileParser parser) {
        if (!(parser.ttype == StlFileParser.TT_WORD && parser.sval.equals("facet"))) {
            System.err.println("Format Error:expecting 'facet' on line " + parser.lineno());
        } else {
            try {
                parser.nextToken();
                readNormal(parser);

                parser.nextToken();
                readLoop(parser);

                parser.nextToken();
                readVertex(parser);

                parser.nextToken();
                readVertex(parser);

                parser.nextToken();
                readVertex(parser);

                parser.nextToken();
                readEndLoop(parser);

                parser.nextToken();
                readEndFacet(parser);
            } catch (IOException e) {
                System.err.println("IO Error on line " + parser.lineno() + ": " + e.getMessage());
            }
        }
    }// End of readFacet

    /*__
     * Method that reads a face in binary files All binary versions of the
     * methods end by 'B' As in binary files we can read the number of faces, we
     * don't need to use coordArray and normArray (reading binary files should
     * be faster)
     *
     * @param in    The ByteBuffer with the data of the object.
     * @param index The facet index
     * @throws IOException
     */
    private void readFacetB(ByteBuffer in, int index) throws IOException {
        //File structure: Normal Vertex1 Vertex2 Vertex3
        Point3D normal = new Point3D();
        Point3D vertex = new Point3D();

        if (DEBUG == 1) {
            System.out.println("Reading face number " + index);
        }

        // Read the Normal
        normArray[index] = new Point3D();
        normArray[index].setX(in.getDouble());
        normArray[index].setY(in.getDouble());
        normArray[index].setZ(in.getDouble());

        if (DEBUG == 1) {
            System.out.println("Normal: X=" + normArray[index].getX() + " Y=" + normArray[index].getY() + " Z=" + normArray[index].getZ());
        }

        // Read vertex1
        coordArray[index * 3] = new Point3D();
        coordArray[index * 3].setX(in.getDouble());
        coordArray[index * 3].setY(in.getDouble());
        coordArray[index * 3].setZ(in.getDouble());

        if (DEBUG == 1) {
            System.out.println("Vertex 1: X=" + coordArray[index * 3].getX() + " Y=" + coordArray[index * 3].getY() + " Z=" + coordArray[index * 3].getZ());
        }

        // Read vertex2
        coordArray[index * 3 + 1] = new Point3D();
        coordArray[index * 3 + 1].setX(in.getDouble());
        coordArray[index * 3 + 1].setY(in.getDouble());
        coordArray[index * 3 + 1].setZ(in.getDouble());

        if (DEBUG == 1) {
            System.out.println("Vertex 2: X=" + coordArray[index * 3 + 1].getX() + " Y=" + coordArray[index * 3 + 1].getY() + " Z=" + coordArray[index * 3 + 1].getZ());
        }

        // Read vertex3
        coordArray[index * 3 + 2] = new Point3D();
        coordArray[index * 3 + 2].setX(in.getDouble());
        coordArray[index * 3 + 2].setY(in.getDouble());
        coordArray[index * 3 + 2].setZ(in.getDouble());

        if (DEBUG == 1) {
            System.out.println("Vertex 3: X=" + coordArray[index * 3 + 2].getX() + " Y=" + coordArray[index * 3 + 2].getY() + " Z=" + coordArray[index * 3 + 2].getZ());
        }

    }// End of readFacetB

    /*__
     * Method that reads ASCII files Uses StlFileParser for correct reading and
     * format checking The beggining of that method is common to binary and
     * ASCII files We try to detect what king of file it is
     * <p>
     * TO-DO: 1.- Find a best way to decide what kind of file it is 2.- Is that
     * return (first catch) the best thing to do?
     *
     * @param parser The file parser. An instance of StlFileParser.
     */
    private void readFile(StlFileParser parser) throws IncorrectFormatException {
        int t;

        try {
            parser.nextToken();
        } catch (IOException e) {
            System.err.println("IO Error on line " + parser.lineno() + ": " + e.getMessage());
            System.err.println("File seems to be empty");
            return;         // ????? Throw ?????
        }

        // Here we try to detect what kind of file it is (see readSolid)
        readSolid(parser);

        if (getAscii()) { // Ascii file
            try {
                parser.nextToken();
            } catch (IOException e) {
                System.err.println("IO Error on line " + parser.lineno() + ": " + e.getMessage());
                return;
            }

            // Read all the facets of the object
            while (parser.ttype != StlFileParser.TT_EOF
                    && !"endsolid".equals(parser.sval)) {
                readFacet(parser);
                try {
                    parser.nextToken();
                } catch (IOException e) {
                    System.err.println("IO Error on line " + parser.lineno() + ": " + e.getMessage());
                }
            }// End while

            // Why are we out of the while?: EOF or endsolid
            if (parser.ttype == StlFileParser.TT_EOF) {
                System.err.println("Format Error:expecting 'endsolid', line " + parser.lineno());
            } else {
                if (DEBUG == 1) {
                    System.out.println("File readed");
                }
            }
        }//End of Ascii reading
        else { // Binary file
            try {
                readBinaryFile(getFileName());
            } catch (IOException e) {
                System.err.println("Format Error: reading the binary file");
            }
        }// End of binary file
    }//End of readFile

    /*__
     * Method that reads "outer loop" and then EOL
     *
     * @param parser The file parser. An instance of StlFileParser.
     */
    private void readLoop(StlFileParser parser) {
        if (!(parser.ttype == StlFileParser.TT_WORD && parser.sval.equals("outer"))) {
            System.err.println("Format Error:expecting 'outer' on line " + parser.lineno());
        } else {
            try {
                parser.nextToken();
            } catch (IOException e) {
                System.err.println("IO error on line " + parser.lineno() + ": " + e.getMessage());
            }
            if (!(parser.ttype == StlFileParser.TT_WORD && parser.sval.equals("loop"))) {
                System.err.println("Format Error:expecting 'loop' on line " + parser.lineno());
            } else {
                readEOL(parser);
            }
        }
    }//End of readLoop

    /*__
     * Method that reads a normal
     *
     * @param parser The file parser. An instance of StlFileParser.
     */
    private void readNormal(StlFileParser parser) {
        Point3D v = new Point3D();

        if (!(parser.ttype == StlFileParser.TT_WORD && parser.sval.equals("normal"))) {
            System.err.println("Format Error:expecting 'normal' on line " + parser.lineno());
        } else {
            if (parser.getNumber()) {
                v.setX(parser.nval);

                if (DEBUG == 1) {
                    System.out.println("Normal:");
                    System.out.print("X=" + v.getX() + " ");
                }

                if (parser.getNumber()) {
                    v.setY(parser.nval);
                    if (DEBUG == 1) {
                        System.out.print("Y=" + v.getY() + " ");
                    }

                    if (parser.getNumber()) {
                        v.setZ(parser.nval);
                        if (DEBUG == 1) {
                            System.out.println("Z=" + v.getZ());
                        }

                        // We add that vector to the Normal's array
                        this.normList.add(v);
                        this.readEOL(parser);
                    } else {
                        System.err.println("Format Error:expecting coordinate on line " + parser.lineno());
                    }
                } else {
                    System.err.println("Format Error:expecting coordinate on line " + parser.lineno());
                }
            } else {
                System.err.println("Format Error:expecting coordinate on line " + parser.lineno());
            }
        }
    }// End of Read Normal

    /*__
     * Method that reads the word "solid" and stores the object name. It also
     * detects what kind of file it is TO-DO: 1.- Better way control of
     * exceptions? 2.- Better way to decide between Ascii and Binary?
     *
     * @param parser The file parser. An instance of StlFileParser.
     */
    private void readSolid(StlFileParser parser) {
        if (!parser.sval.equals("solid")) {
            System.out.println("Expecting solid on line " + parser.lineno());
            // If the first word is not "solid" then we consider the file is binary
            // Can give us problems if the comment of the binary file begins by "solid"
            this.setAscii(false);
        } else // It's an ASCII file
        {
            try {
                parser.nextToken();
            } catch (IOException e) {
                System.err.println("IO Error on line " + parser.lineno() + ": " + e.getMessage());
            }
            if (parser.ttype != StlFileParser.TT_WORD) {
                // Is the object name always provided???
                System.err.println("Format Error:expecting the object name on line " + parser.lineno());
            } else { // Store the object Name
                this.setObjectName(parser.sval);
                if (DEBUG == 1) {
                    System.out.println("Object Name:" + this.getObjectName().toString());
                }
                this.readEOL(parser);
            }
        }
    }//End of readSolid

    /*__
     * Method that reads the coordinates of a vector
     *
     * @param parser The file parser. An instance of StlFileParser.
     */
    private void readVertex(StlFileParser parser) {
        Point3D p = new Point3D();

        if (!(parser.ttype == StlFileParser.TT_WORD && parser.sval.equals("vertex"))) {
            System.err.println("Format Error:expecting 'vertex' on line " + parser.lineno());
        } else {
            if (parser.getNumber()) {
                p.setX(parser.nval);

                if (DEBUG == 1) {
                    System.out.println("Vertex:");
                    System.out.print("X=" + p.getX() + " ");
                }

                if (parser.getNumber()) {
                    p.setY(parser.nval);
                    if (DEBUG == 1) {
                        System.out.print("Y=" + p.getY() + " ");
                    }

                    if (parser.getNumber()) {
                        p.setZ(parser.nval);
                        if (DEBUG == 1) {
                            System.out.println("Z=" + p.getZ());
                        }

                        // We add that vertex to the array of vertex
                        coordList.add(p);
                        readEOL(parser);
                    } else {
                        System.err.println("Format Error: expecting coordinate on line " + parser.lineno());
                    }
                } else {
                    System.err.println("Format Error: expecting coordinate on line " + parser.lineno());
                }
            } else {
                System.err.println("Format Error: expecting coordinate on line " + parser.lineno());
            }
        }
    }//End of read vertex

    /*
     * Takes a file name and sets the base path to the directory
     * containing that file.
     */
    private void setBasePathFromFilename(String fileName) {
        // Get ready to parse the file name
        StringTokenizer stok
                = new StringTokenizer(fileName, java.io.File.separator);

        //  Get memory in which to put the path
        StringBuilder sb = new StringBuilder(MAX_PATH_LENGTH);

        // Check for initial slash
        if (fileName != null && fileName.startsWith(java.io.File.separator)) {
            sb.append(java.io.File.separator);
        }

        // Copy everything into path except the file name
        for (int i = stok.countTokens() - 1; i > 0; i--) {
            String a = stok.nextToken();
            sb.append(a);
            sb.append(java.io.File.separator);
        }
        setBasePath(sb.toString());
    } // End of setBasePathFromFilename

    private void setBaseUrlFromUrl(URL url) {
        StringTokenizer stok
                = new StringTokenizer(url.toString(), "/\\", true);
        int tocount = stok.countTokens() - 1;
        StringBuilder sb = new StringBuilder(MAX_PATH_LENGTH);
        for (int i = 0; i < tocount; i++) {
            String a = stok.nextToken();
            sb.append(a);
        }
        try {
            baseUrl = new URL(sb.toString());
        } catch (MalformedURLException e) {
            System.err.println("Error setting base URL: " + e.getMessage());
        }
    } // End of setBaseUrlFromUrl

} // End of package stl_loader
