package one.empty3.testscopy.tests;

import one.empty3.library.Point3D;
import one.empty3.library.core.raytracer.tree.AlgebraicFormulaSyntaxException;
import one.empty3.library.core.raytracer.tree.AlgebricTree;
import one.empty3.library.core.raytracer.tree.TreeNodeEvalException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AngleMovment {
    AlgebricTree[] tree;
    List<AlgebricTree> fxs = new ArrayList();
    HashMap<String, Double> vars;

    public AngleMovment(int size) {
        tree = new AlgebricTree[size];
        vars = new HashMap<>(size);
    }

    public void var(String var, double val) {
        vars.put(var, val);
    }

    public void setFormula(int index, String chars) {

        tree[index] = new AlgebricTree(chars, vars);
        tree[index].setParametersValues(vars);
        try {
            tree[index].construct();
        } catch (AlgebraicFormulaSyntaxException e) {
            e.printStackTrace();
        }
    }

    public double calcculerAngle(int index) {
        try {
            tree[index].setParametersValues(vars);
            tree[index].construct();
            return (double) (tree[index].eval());
        } catch (TreeNodeEvalException | AlgebraicFormulaSyntaxException e) {
            e.printStackTrace();
        }
        return Double.NaN;
    }

    public void addFx(String formulaN) {
        try {
            fxs.add(new AlgebricTree(formulaN));

        }
        catch (AlgebraicFormulaSyntaxException ex) {
            ex.printStackTrace();
        }
    }

    public Point3D getPoint3D() {
        double [] values = new double[tree.length];
        for(int i=0; i<tree.length; i++) {
            try {
                values[i] = tree[i].eval();
            } catch(TreeNodeEvalException | AlgebraicFormulaSyntaxException ex){
                ex.printStackTrace();
            }


        }
        return new Point3D(values);
    }
}
