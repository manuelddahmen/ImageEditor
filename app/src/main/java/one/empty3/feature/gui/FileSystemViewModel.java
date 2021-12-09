package one.empty3.feature.gui;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.io.File;
import java.util.*;
import java.util.function.Predicate;

public class FileSystemViewModel implements TreeModel {
    TreeMap<String, File> fileTreeMap;
    private final File file;

    public FileSystemViewModel(File file) {
        this.file = file;
    }

    @Override
    public Object getRoot() {
        return file;
    }

    @Override
    public Object getChild(Object parent, int index) {
        return fileTreeMap.get(parent).list()[index];
    }

    @Override
    public int getChildCount(Object parent) {
        return fileTreeMap.get((String)parent).list().length;
    }

    @Override
    public boolean isLeaf(Object node) {
        return fileTreeMap.get((String)node).list().length==0;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
        fileTreeMap.put(path.toString(), (File) newValue);
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        File file = fileTreeMap.get(parent);
        if(file.isDirectory()&& Arrays.stream(file.list()).anyMatch(s -> s.equals(child))) {
            Iterator<String> iterator = Arrays.stream(file.list()).iterator();
            int index = 0;
            while(iterator.hasNext()) {
                String next = iterator.next();
                if(child.equals(next)) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {

    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {

    }
}
