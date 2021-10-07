package one.empty3.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProcessFile {
    public List<File> images = new ArrayList<>();
    protected int maxRes = 400;




    public boolean process(File in, File out) {return false;}

    public void setMaxRes(int maxRes) {
        this.maxRes = maxRes;
    }

    public void setStack(List<File> files1) {
        this.images = files1;

    }
    public File getStackItem(int index) {
        System.out.printf("STACK %d : %s", index, images.get(index));
        return images.get(index);
    }

}
