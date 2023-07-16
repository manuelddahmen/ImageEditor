package one.empty3;

import java.util.ArrayList;

import one.empty3.feature20220726.Classification;
import one.empty3.feature20220726.DBScanProcess;
import one.empty3.feature20220726.DericheFilterProcess;
import one.empty3.feature20220726.DiffEnergy;
import one.empty3.feature20220726.Draw;
import one.empty3.feature20220726.ExtremaProcess;
import one.empty3.feature20220726.GaussFilterProcess;
import one.empty3.feature20220726.GradProcess;
import one.empty3.feature20220726.HarrisProcess;
import one.empty3.feature20220726.Histogram2;
import one.empty3.feature20220726.Histogram3;
import one.empty3.feature20220726.HoughTransform;
import one.empty3.feature20220726.IdentNullProcess;
import one.empty3.feature20220726.KMeans;
import one.empty3.feature20220726.Lines;
import one.empty3.feature20220726.Lines3;
import one.empty3.feature20220726.Lines4;
import one.empty3.feature20220726.Lines5;
import one.empty3.feature20220726.Lines5colors;
import one.empty3.feature20220726.MagnitudeProcess;
import one.empty3.feature20220726.ProxyValue;
import one.empty3.feature20220726.ProxyValue2;
import one.empty3.feature20220726.Transform1;
import one.empty3.feature20220726.TrueHarrisProcess;
import one.empty3.feature20220726.Voronoi;
import one.empty3.feature20220726.histograms.Histogram1;
import one.empty3.io.ProcessFile;

public class Main {
    public static ArrayList<ProcessFile> initListProcesses() {
        final ArrayList<ProcessFile> listProcessClasses = new ArrayList<>();
        try {

            listProcessClasses.add(Classification.class.newInstance());
            //listProcessClasses.add(CornerDetectProcess.class.newInstance());
            //listProcessClasses.add(CurveFitting.class.newInstance());
            listProcessClasses.add(DBScanProcess.class.newInstance());
            listProcessClasses.add(DericheFilterProcess.class.newInstance());
            listProcessClasses.add(DiffEnergy.class.newInstance());
            listProcessClasses.add(Draw.class.newInstance());
            listProcessClasses.add(ExtremaProcess.class.newInstance());
            listProcessClasses.add(GaussFilterProcess.class.newInstance());
            listProcessClasses.add(GradProcess.class.newInstance());
            listProcessClasses.add(HarrisProcess.class.newInstance());
            //listProcessClasses.add(Histogram.class.newInstance());
            listProcessClasses.add(Histogram1.class.newInstance());
            listProcessClasses.add(Histogram2.class.newInstance());
            listProcessClasses.add(Histogram3.class.newInstance());
            listProcessClasses.add(HoughTransform.class.newInstance());
            //listProcessClasses.add(HoughTransformCircle.class.newInstance());
            listProcessClasses.add(IdentNullProcess.class.newInstance());
            //listProcessClasses.add(IsleProcess.class.newInstance());
            listProcessClasses.add(KMeans.class.newInstance());
            listProcessClasses.add(Lines.class.newInstance());
            listProcessClasses.add(Lines3.class.newInstance());
            listProcessClasses.add(Lines4.class.newInstance());
            listProcessClasses.add(Lines5.class.newInstance());
            listProcessClasses.add(Lines5colors.class.newInstance());
            //listProcessClasses.add(Lines6.class.newInstance());
            //listProcessClasses.add(LocalExtremaProcess.class.newInstance());
            listProcessClasses.add(MagnitudeProcess.class.newInstance());
            listProcessClasses.add(ProxyValue.class.newInstance());
            listProcessClasses.add(ProxyValue2.class.newInstance());
            //listProcessClasses.add(RegionLineCorner.class.newInstance());
            listProcessClasses.add(Transform1.class.newInstance());
            listProcessClasses.add(TrueHarrisProcess.class.newInstance());
            listProcessClasses.add(Voronoi.class.newInstance());

            listProcessClasses.forEach(processFile -> {
            }/*comboBox1.addItem(processFile)*/);
            ;

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return listProcessClasses;
    }
}
