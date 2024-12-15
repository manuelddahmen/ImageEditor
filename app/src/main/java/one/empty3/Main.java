package one.empty3;

import java.util.ArrayList;

import one.empty3.androidFeature.Classification;
import one.empty3.androidFeature.DBScanProcess;
import one.empty3.androidFeature.DericheFilterProcess;
import one.empty3.androidFeature.Draw;
import one.empty3.androidFeature.ExtremaProcess;
import one.empty3.androidFeature.GaussFilterProcess;
import one.empty3.androidFeature.GradProcess;
import one.empty3.androidFeature.HarrisProcess;
import one.empty3.androidFeature.Histogram2;
import one.empty3.androidFeature.Histogram3;
import one.empty3.androidFeature.HoughTransform;
import one.empty3.androidFeature.IdentNullProcess;
import one.empty3.androidFeature.KMeans;
import one.empty3.androidFeature.Lines;
import one.empty3.androidFeature.Lines3;
import one.empty3.androidFeature.Lines4;
import one.empty3.androidFeature.Lines5;
import one.empty3.androidFeature.Lines5colors;
import one.empty3.androidFeature.MagnitudeProcess;
import one.empty3.androidFeature.ProxyValue;
import one.empty3.androidFeature.ProxyValue2;
import one.empty3.androidFeature.Transform1;
import one.empty3.androidFeature.TrueHarrisProcess;
import one.empty3.androidFeature.Voronoi;
import one.empty3.androidFeature.histograms.Histogram1;
import one.empty3.io.ProcessFile;

public class Main {
    public static ArrayList<ProcessFile> initListProcesses() {
        final ArrayList<ProcessFile> listProcessClasses = new ArrayList<>();
        try {

            listProcessClasses.add(Classification.class.getDeclaredConstructor().newInstance());
            //listProcessClasses.add(CornerDetectProcess.class.getDeclaredConstructor().newInstance());
            //listProcessClasses.add(CurveFitting.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(DBScanProcess.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(DericheFilterProcess.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(Draw.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(ExtremaProcess.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(GaussFilterProcess.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(GradProcess.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(HarrisProcess.class.getDeclaredConstructor().newInstance());
            //listProcessClasses.add(Histogram.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(Histogram1.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(Histogram2.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(Histogram3.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(HoughTransform.class.getDeclaredConstructor().newInstance());
            //listProcessClasses.add(HoughTransformCircle.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(IdentNullProcess.class.getDeclaredConstructor().newInstance());
            //listProcessClasses.add(IsleProcess.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(KMeans.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(Lines.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(Lines3.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(Lines4.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(Lines5.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(Lines5colors.class.getDeclaredConstructor().newInstance());
            //listProcessClasses.add(Lines6.class.getDeclaredConstructor().newInstance());
            //listProcessClasses.add(LocalExtremaProcess.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(MagnitudeProcess.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(ProxyValue.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(ProxyValue2.class.getDeclaredConstructor().newInstance());
            //listProcessClasses.add(RegionLineCorner.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(Transform1.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(TrueHarrisProcess.class.getDeclaredConstructor().newInstance());
            listProcessClasses.add(Voronoi.class.getDeclaredConstructor().newInstance());

            listProcessClasses.forEach(processFile -> {
            }/*comboBox1.addItem(processFile)*/);
            ;

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return listProcessClasses;
    }
}
