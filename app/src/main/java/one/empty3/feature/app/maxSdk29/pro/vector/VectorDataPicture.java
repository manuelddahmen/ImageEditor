package one.empty3.feature.app.maxSdk29.pro.vector;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.camera.camera2.interop.ExperimentalCamera2Interop;

import java.util.ArrayList;
import java.util.List;

import one.empty3.library.Polygon;

@ExperimentalCamera2Interop public class VectorDataPicture implements Parcelable {
    List<Polygon> polygons = new ArrayList<>();

    protected VectorDataPicture(Parcel in) {
        Parcelable[] polygons1 = in.readParcelableArray(new MyClassLoaderCreator());
        for (int i = 0; i < polygons1.length; i++) {
            polygons.add((Polygon) polygons1[i]);
        }
    }

    public static final Creator<VectorDataPicture> CREATOR = new Creator<VectorDataPicture>() {
        @Override
        public VectorDataPicture createFromParcel(Parcel in) {
            return new VectorDataPicture(in);
        }

        @Override
        public VectorDataPicture[] newArray(int size) {
            return new VectorDataPicture[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        Polygon [] polygons1 = new Polygon[polygons.size()];
        for (int j = 0, polygonsSize = polygons.size(); j < polygonsSize; j++) {
            polygons1[j] =polygons.get(i);
        }
        parcel.writeParcelableArray(polygons1, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
    }
}
