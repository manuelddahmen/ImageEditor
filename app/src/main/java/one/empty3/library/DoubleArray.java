package one.empty3.library;

import java.util.*;

/**
 * mettre dans des espaces et les supprimer
 * quand ils sont vides.
 */
public class DoubleArray {
    private int[] stack = new int[5];
    public int current = 0;
    private int length = 1000 * 1000;
    List<Double[]> doubles;
    List<Integer[]> index;
    List<Integer[]> dimensions;
    int spaces;
    boolean[] freeSpace;
    int max = 0;

    public DoubleArray(int spaces, int spaceLength) {
        freeSpace = new boolean[spaces];
        this.spaces = spaces;
        doubles = new ArrayList<>();
        doubles.add(new Double[spaceLength]);
        index = new ArrayList<>();
        index.add(new Integer[spaceLength]);
        dimensions = new ArrayList<>();
        dimensions.add(new Integer[spaceLength]);
        //  location = new ArrayList<>()
    }

    public void clear() {
        for (int i = 0; i < index.size(); i++) {
            int j = 0;
            boolean isEmpty = true;
            while (j < index.get(i).length) {
                if (index.get(i)[j] != 0) {
                    isEmpty = false;
                    break;
                }
            }
            if (isEmpty) {
                freeSpace[i] = true;
            }
        }
    }

    public boolean clearInstanceCalculus(int indexesOrigin, int indexesIntermediates, int indexesFinalResult) {
        return false;
    }

    public Double getDouble(int index) {
        return doubles.get(0)[this.index.get(0)[index]];
    }

    public int addDouble(Double d) {
        int start = max;
        dimensions.get(0)[this.index.get(0)[start]] = 1;
        doubles.get(0)[this.index.get(0)[max] = max] = d;
        max++;
        return start;
    }

    public int addDoubles(Double... ds) {
        int start = max;
        dimensions.get(0)[this.index.get(0)[start]] = ds.length;
        for (int i = 0; i < ds.length; i++) {
            doubles.get(0)[this.index.get(0)[max] = max] = ds[i];
            dimensions.get(0)[this.index.get(0)[max]] = ds.length;
            max++;
        }
        return start;

    }

    public int setDoubles(int start, Double... ds) {
        for (int i = start; i < start + ds.length; i++) {
            doubles.get(0)[this.index.get(0)[i]] = ds[i - start];
            max++;
        }
        return start;

    }

    public int addDoubles(int n) {
        int start = max;
        for (int i = 0; i < n; i++) {
            doubles.get(0)[this.index.get(0)[max] = max] = 0.0;
            dimensions.get(0)[this.index.get(0)[max]] = n;
            max++;

        }
        return start;
    }

    public int current() {
        return stack[current];
    }

    public boolean addToStack() {
        stack[current++] = max;

        return current < stack.length;
    }

    public boolean removeFromStack() {
        current--;
        this.max = stack[current];
        return current >= 0;
    }

    // copy to free memory disallocated space.
    public void save(int start0) {
        int n = dimensions.get(0)[this.index.get(0)[start0]];
        for (int i = 0; i < n; i++) {
            doubles.get(0)[this.index.get(0)[max]]
                    = doubles.get(0)[this.index.get(0)[start0]];
            dimensions.get(0)[this.index.get(0)[max]] = n;
            start0++;
        }
    }
}
