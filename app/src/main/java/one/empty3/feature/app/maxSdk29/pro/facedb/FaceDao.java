package one.empty3.feature.app.maxSdk29.pro.facedb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import one.empty3.feature20220726.GoogleFaceDetection;

@Dao
public interface FaceDao {

   @Insert
   void insert(GoogleFaceDetection googleFaceDetection);

   @Query("DELETE FROM face_table")
   void deleteAll();

   @Query("SELECT * from word_table ORDER BY word ASC")
   LiveData<List<Face>> getAllFace();
}