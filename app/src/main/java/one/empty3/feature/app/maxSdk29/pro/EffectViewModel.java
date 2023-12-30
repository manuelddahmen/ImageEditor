package one.empty3.feature.app.maxSdk29.pro;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.io.File;

import one.empty3.io.ProcessFile;

public class EffectViewModel extends ViewModel {
    private WorkManager mWorkManager;
    // New instance variable for the WorkInfo
    private Uri mOutputUri;

    // Add a getter and setter for mOutputUri
    void setOutputUri(String outputImageUri) {
        mOutputUri = uriOrNull(outputImageUri);
    }

    private Uri uriOrNull(String outputImageUri) {
        return Uri.parse(outputImageUri);
    }

    Uri getOutputUri() { return mOutputUri; }
    private int maxRes;
    private java.util.HashMap<String, ProcessFile> listEffects;
    private  ChooseEffectsActivity2 activity;
    private File currentFile;
    public EffectViewModel(@NonNull Application application) {
        mWorkManager = WorkManager.getInstance(application);
    }
    void passVariables(ChooseEffectsActivity2 effectActivity) {
        maxRes= effectActivity.maxRes;
        listEffects = effectActivity.getListEffects();
        activity= effectActivity;
        currentFile= effectActivity.currentFile;

    }
    void applyBlur(int blurLevel) {

        mWorkManager.enqueue(OneTimeWorkRequest.from(EffectWorker.class));
    }
}

