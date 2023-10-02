package one.empty3.feature.app.maxSdk29.pro.facedb;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import one.empty3.feature20220726.GoogleFaceDetection;

@Entity(tableName = "face_table")
public class Face {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "face")
    private GoogleFaceDetection mFace;

    public Face(@NonNull GoogleFaceDetection face) {this.mFace = face;}

    public GoogleFaceDetection getFace(){return this.mFace;}
}