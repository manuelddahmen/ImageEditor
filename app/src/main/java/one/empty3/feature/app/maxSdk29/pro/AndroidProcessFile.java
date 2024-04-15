package one.empty3.feature.app.maxSdk29.pro;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaFormat;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.linkedin.android.litr.MediaTransformer;
import com.linkedin.android.litr.TransformationListener;
import com.linkedin.android.litr.TransformationOptions;
import com.linkedin.android.litr.analytics.TrackTransformationInfo;
import com.linkedin.android.litr.filter.GlFilter;
import com.linkedin.android.litr.render.GlSingleFrameRenderer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import one.empty3.io.ProcessFile;


public class AndroidProcessFile extends ProcessFile {
    private Context context;
    private int numFrames = -1;
    public AndroidProcessFile(Context context) {
        init(context);
    }

    public void init(Context context) {
        this.context = context;
    }


    @Override
    public boolean process(File in, File out) {
        class TransformationListenerExt implements TransformationListener {
            @Override
            public void onStarted(@NonNull String id) {

            }

            @Override
            public void onProgress(@NonNull String id, float progress) {

            }

            @Override
            public void onCompleted(@NonNull String id, @Nullable List<TrackTransformationInfo> trackTransformationInfos) {

            }

            @Override
            public void onCancelled(@NonNull String id, @Nullable List<TrackTransformationInfo> trackTransformationInfos) {

            }

            @Override
            public void onError(@NonNull String id, @Nullable Throwable cause, @Nullable List<TrackTransformationInfo> trackTransformationInfos) {

            }
        }
        TransformationListener transformer = new TransformationListenerExt();



        boolean isImage = false;
        MediaTransformer mediaTransformer = new MediaTransformer(context);
        List<GlFilter> filters  = new ArrayList<>();
        int numFrames1 = numFrames;
        if(isImage(in)) {
            isImage = true;
            if(numFrames==-1) {
                numFrames1 = 150;
            }
            for (int i = 0; i < numFrames1; i++) {
                GlSingleFrameRenderer glSingleFrameRenderer = new GlSingleFrameRenderer(filters);
                Bitmap output = BitmapFactory.decodeFile(in.getAbsolutePath());
                Bitmap bitmap = glSingleFrameRenderer.renderFrame(output, numFrames1);

                glSingleFrameRenderer.release();
                mediaTransformer.transform(""+ UUID.randomUUID(),
                        Uri.fromFile(in), Uri.fromFile(out),
                        MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_MPEG4,
                                maxRes, maxRes), MediaFormat.createAudioFormat(MediaFormat.MIMETYPE_AUDIO_AAC,
                                3000, 1),
                        transformer,
                        new TransformationOptions.Builder().setVideoFilters(filters).build()
                );

            }
        }
        else {
            isImage = false;
            mediaTransformer.transform(""+ UUID.randomUUID(),
                    Uri.fromFile(in), Uri.fromFile(out),
                    MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_MPEG4,
                            maxRes, maxRes), MediaFormat.createAudioFormat(MediaFormat.MIMETYPE_AUDIO_AAC,
                            3000, 1),
                    transformer,
                    new TransformationOptions.Builder().setVideoFilters(filters).build()
            );
        }
        return false;
    }
}
