package one.empty3.feature.app.maxSdk29.pro

import android.Manifest
import android.R
import android.content.ContentValues
import android.content.pm.PackageManager
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraMetadata
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import androidx.camera.camera2.internal.annotation.CameraExecutor
import androidx.camera.camera2.interop.Camera2CameraInfo
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import androidx.camera.core.CameraProvider
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.FallbackStrategy
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Consumer
import com.google.common.util.concurrent.ListenableFuture
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor

@ExperimentalCamera2Interop class Camera {
    private val FILENAME_FORMAT: String? = "YYYY-mm-dd"
    val TAG = this.javaClass.canonicalName
    fun process(activitySuperClass: ActivitySuperClass) {
        val context = activitySuperClass.applicationContext
        val qualitySelector = QualitySelector.fromOrderedList(
            listOf(Quality.UHD, Quality.FHD, Quality.HD, Quality.SD),
            FallbackStrategy.lowerQualityOrHigherThan(Quality.SD))

        val cameraProvider : ListenableFuture<ProcessCameraProvider> = ProcessCameraProvider.getInstance(context)
        val cameraInfo = cameraProvider.get().availableCameraInfos.filter {
            Camera2CameraInfo
                .from(it)
                .getCameraCharacteristic(CameraCharacteristics.LENS_FACING) == CameraMetadata.LENS_FACING_BACK
        }

        val supportedQualities = QualitySelector.getSupportedQualities(cameraInfo[0])
        val filteredQualities = arrayListOf (Quality.UHD, Quality.FHD, Quality.HD, Quality.SD)
            .filter { supportedQualities.contains(it) }
/*
// Use a simple ListView with the id of simple_quality_list_view
        viewBinding.simpleQualityListView.apply {
            adapter = ArrayAdapter(context,
                R.layout.simple_list_item_1,
                filteredQualities.map { it.qualityToString() })

            // Set up the user interaction to manually show or hide the system UI.
            setOnItemClickListener { _, _, position, _ ->
                // Inside View.OnClickListener,
                // convert Quality.* constant to QualitySelector
                val qualitySelector = QualitySelector.from(filteredQualities[position])

                // Create a new Recorder/VideoCapture for the new quality
                // and bind to lifecycle
                val recorder = Recorder.Builder()
                    .setQualitySelector(qualitySelector).build()

                // ...
            }
        }
*/
        val cameraExecutor : Executor = Executor {  }

        val recorder = Recorder.Builder()
            .setExecutor(cameraExecutor).setQualitySelector(qualitySelector)
            .build()
        val videoCapture = VideoCapture.withOutput(recorder)

        try {
            // Bind use cases to camera
            cameraProvider.get().bindToLifecycle(
                activitySuperClass, CameraSelector.DEFAULT_BACK_CAMERA, videoCapture)
        } catch(exc: Exception) {
            val e = Log.e(TAG, "Use case binding failed", exc)
        }

// Create MediaStoreOutputOptions for our recorder
        val name = "CameraX-recording-" +
                SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                    .format(System.currentTimeMillis()) + ".mp4"
        val contentValues = ContentValues().apply {
            put(MediaStore.Video.Media.DISPLAY_NAME, name)
        }
        val mediaStoreOutput = MediaStoreOutputOptions.Builder(activitySuperClass.contentResolver,
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            .setContentValues(contentValues)
            .build()

// 2. Configure Recorder and Start recording to the mediaStoreOutput.
        val recording = if (ActivityCompat.checkSelfPermission(
                activitySuperClass.applicationContext,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        } else {

        }
        videoCapture.output
            .prepareRecording(activitySuperClass.applicationContext,
                mediaStoreOutput)
            .withAudioEnabled()
            .start(ContextCompat.getMainExecutor(activitySuperClass.applicationContext), Consumer<VideoRecordEvent> {

            })
    }


    // A helper function to translate Quality to a string
    fun qualityToString(quality: Quality) : String {
        return when (quality) {
            Quality.UHD -> return "UHD"
            Quality.FHD -> return "FHD"
            Quality.HD -> return "HD"
            Quality.SD -> return "SD"
            else -> throw IllegalArgumentException()
        }
    }


}