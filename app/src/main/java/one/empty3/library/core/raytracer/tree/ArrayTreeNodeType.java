package one.empty3.library.core.raytracer.tree;

import one.empty3.library.*;

public class ArrayTreeNodeType extends TreeNodeType {
    private StructureMatrix list;

    // todo des que tu as un separateur tu dois recommener avant quil soit pas la
    public ArrayTreeNodeType(Class t) {
        super();
        list = new StructureMatrix(1, t);
    }

    public ArrayTreeNodeType() {
        this(Double.class);
    }

    public void add(Object o) {
        list.add(1, o);
    }

    @Override
    public Double eval() {
        return 0.0;
    }
} 
