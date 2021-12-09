/*
 * Copyright (c) 2016. Tous les fichiers dans ce programme sont soumis à la License Publique Générale GNU créée par la Free Softxware Association, Boston.
 * La plupart des licenses de parties tièrces sont compatibles avec la license principale.
 * Les parties tierces peuvent être soumises à d'autres licenses.
 * Montemedia : Creative Commons
 * ECT : Tests à valeur artistique ou technique.
 * La partie RayTacer a été honteusement copiée sur le Net. Puis traduite en Java et améliorée.
 * Java est une marque de la société Oracle.
 *
 * Pour le moment le programme est entièrement accessible sans frais supplémentaire. Get the sources, build it, use it, like it, share it.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package one.empty3.library.core.script;
//
//import one.empty3.library.Point3D;
//import one.empty3.library.PolyMap;
//
//import java.util.ArrayList;
//
///*__
// * @author Se7en
// */
//public class InterpretePolyMapDef implements Interprete {
//
//    private String rep;
//    private int position;
//
//    public InterpretePolyMapDef() {
//    }
//
//    public InterpreteConstants constant() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    public int getPosition() {
//        return position;
//    }
//
//    public Object interprete(String text, int pos) throws InterpreteException {
//        InterpretesBase ib;
//        ArrayList<Integer> pattern;
//        ib = new InterpretesBase();
//
//        pattern = new ArrayList<Integer>();
//        pattern.add(ib.BLANK);
//        pattern.add(ib.LEFTPARENTHESIS);
//        pattern.add(ib.BLANK);
//        pattern.add(ib.DECIMAL);
//        pattern.add(ib.BLANK);
//        pattern.add(ib.DECIMAL);
//        pattern.add(ib.BLANK);
//        ib.compile(pattern);
//        ArrayList<Object> read = ib.read(text, pos);
//        pos = ib.getPosition();
//
//        Integer width = (Integer) read.get(3);
//        Integer height = (Integer) read.get(5);
//
//        PolyMap pm = new PolyMap(width);
//
//        InterpreteListePoints ilp = new InterpreteListePoints();
//        ArrayList<Point3D> interprete = (ArrayList<Point3D>) ilp.interprete(text, pos);
//        pos = ilp.getPosition();
//
//        for (int coordArr = 0; coordArr < width; coordArr++) {
//
//            for (int y = 0; y < height; y++) {
//
//                pm.addPoint(y, interprete.get(coordArr + y * width));
//
//            }
//
//        }
//
//        pattern = new ArrayList<Integer>();
//        pattern.add(ib.BLANK);
//        pattern.add(ib.LEFTPARENTHESIS);
//        pattern.add(ib.BLANK);
//        ib.compile(pattern);
//        ib.read(text, pos);
//        pos = ib.getPosition();
//
//        this.position = pos;
//
//        return pm;
//    }
//
//    public void setConstant(InterpreteConstants c) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    public void setRepertoire(String r) {
//        this.rep = r;
//    }
//
//}
