package one.empty3.feature.jviolajones;


import java.util.LinkedList;
import java.util.List;

import one.empty3.feature.jviolajones.Tree;

public class Stage {
    List<one.empty3.feature.jviolajones.Tree> trees;
    float threshold;

    public Stage(float threshold) {
        this.threshold = threshold;
        trees = new LinkedList<one.empty3.feature.jviolajones.Tree>();
//features = new LinkedList<Feature>();
    }

    public void addTree(one.empty3.feature.jviolajones.Tree t) {
        trees.add(t);
    }

    public boolean pass(int[][] grayImage, int[][] squares, int i, int j, float scale) {
        float sum = 0;
        for (Tree t : trees) {

            //System.out.println("Returned value :"+t.getVal(grayImage, squares,i, j, scale));

            sum += t.getVal(grayImage, squares, i, j, scale);
        }
        //System.out.println(sum+" "+threshold);
        return sum > threshold;
    }

}
