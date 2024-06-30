/*
 * Copyright (c) 2024.
 *
 *
 *  Copyright 2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package one.empty3.feature0.jviolajones;


import java.util.LinkedList;
import java.util.List;

public class Stage {
    List<Tree> trees;
    float threshold;

    public Stage(float threshold) {
        this.threshold = threshold;
        trees = new LinkedList<Tree>();
//features = new LinkedList<Feature>();
    }

    public void addTree(Tree t) {
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
