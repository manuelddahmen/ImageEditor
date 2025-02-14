package one.empty3.feature.app.maxSdk29.pro.vector;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


import java.util.ArrayList;
import java.util.List;

import one.empty3.Polygon1;
import one.empty3.library.Polygon;

public class VectorDataPicture implements Parcelable {
    List<Polygon1> polygons = new ArrayList<>();

    protected VectorDataPicture(Parcel in) {
        Parcelable[] polygons1 = in.readParcelableArray(new MyClassLoaderCreator());
        for (int i = 0; i < polygons1.length; i++) {
            polygons.add((Polygon1) polygons1[i]);
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
        Polygon1[] polygons1 = new Polygon1[polygons.size()];
        for (int j = 0, polygonsSize = polygons.size(); j < polygonsSize; j++) {
            polygons1[j] =polygons.get(i);
        }
        parcel.writeParcelableArray(polygons1, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
    }
}
