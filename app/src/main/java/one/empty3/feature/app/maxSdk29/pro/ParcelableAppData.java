/*
 * Copyright (c) 2023.
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

package one.empty3.feature.app.maxSdk29.pro;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ParcelableAppData implements Parcelable {
    private final String filename;
    private String[] cords = new String[]{"x", "y", "z", "r", "g", "b", "a", "t", "u", "v"};
    private String[] cordsValues = new String[]{"x", "y", "z", "r", "g", "b", "a", "t", "u", "v"};
    private Intent intent;
    private int maxRes = (int) Double.parseDouble(String.valueOf(R.string.maxRes_1200));

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public void setMaxRes(int maxRes) {
        this.maxRes = maxRes;
    }

    public String getFilename() {
        return filename;
    }

    public Intent getIntent() {
        return intent;
    }

    public String[] getCords() {
        return cords;
    }

    public String[] getCordsValues() {
        return cordsValues;
    }

    public int getMaxRes() {
        return maxRes;
    }

    protected ParcelableAppData(Parcel in) {
        maxRes = in.readInt();
        filename = in.readString();
        int i = 0;
        for (String s : cords) {
            cordsValues[i] = in.readString();
            i++;
        }
    }

    public static final Creator<ParcelableAppData> CREATOR = new Creator<ParcelableAppData>() {
        @Override
        public ParcelableAppData createFromParcel(Parcel in) {
            return new ParcelableAppData(in);
        }

        @Override
        public ParcelableAppData[] newArray(int size) {
            return new ParcelableAppData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(maxRes);
        parcel.writeString(filename);
        int j = 0;
        for (String cord : cords) {
            if (intent.getExtras() != null && intent.getStringExtra(cord) != null) {
                parcel.writeString(intent.getStringExtra(cord));
            } else {
                parcel.writeString(cord);
            }
            j++;
        }
    }
}
