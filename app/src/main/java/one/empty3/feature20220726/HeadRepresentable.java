package one.empty3.feature20220726;

import one.empty3.library.Representable;
import one.empty3.library.RepresentableConteneur;
import one.empty3.library.Scene;
import one.empty3.library.core.export.STLExport;
import one.empty3.library.core.script.ExtensionFichierIncorrecteException;
import one.empty3.library.core.script.Loader;
import one.empty3.library.core.script.SceneLoader;
import one.empty3.library.core.script.VersionNonSupporteeException;

import java.io.*;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class HeadRepresentable extends Representable {
    public HeadRepresentable() {
        StringBuilder sb = new StringBuilder();
        Properties headRb = new Properties();
        try {
            File properties = new File("resources/stl/head.properties");

            File sceneStl = new File("resources/stl/head.stl");
            File sceneXml = new File("resources/stl/head.xml");
            File txt = new File("resources/stl/head.moo");
            headRb.load(new BufferedReader(new InputStreamReader(new FileInputStream(properties))));

            headRb.keySet().forEach(o -> {
                        sb.append(headRb.getProperty((String) o));
                        System.out.println(o + " = " + headRb.getProperty((String) o));
                    }

            );

            //sb.append(')');


            String s = sb.toString();

            new OutputStreamWriter(new FileOutputStream(txt)).write(s);


            Scene load = new Scene();

            if ((load = new Loader().load(txt, load)) != null) {


                //File sceneL = new File("resources/stl/headRewrite.moo");
                //new PrintWriter(sceneL).println(load);
                StringBuilder stringBuilder = new StringBuilder();
                load.xmlRepresentation(null, stringBuilder, load);
                new PrintWriter(sceneXml).println(stringBuilder);
                STLExport.save(sceneStl, load, true);
                new PrintWriter(sceneStl).println(sb.toString());

                System.out.println(load);
            } else
                System.out.println("Model moo not loaded");

        } catch (IOException | ExtensionFichierIncorrecteException | VersionNonSupporteeException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HeadRepresentable headRepresentable = new HeadRepresentable();
    }
}
