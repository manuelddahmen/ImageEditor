package one.empty3.feature.app.maxSdk29.pro;

import android.os.Bundle;

import one.empty3.library.Point3D;
import one.empty3.library.StructureMatrix;

public class ReshapeActivity extends ActivitySuperClass {
    private int lines = 10;
    private int columns = 10;
    private StructureMatrix<Point3D> points = new StructureMatrix<>(2, Point3D.class);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reshape);




    }
}