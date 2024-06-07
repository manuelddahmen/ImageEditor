package one.empty3.feature.app.maxSdk29.pro;

import android.content.Context;

import java.io.File;

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
        return false;
    }
    /*
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
    }*/
}
