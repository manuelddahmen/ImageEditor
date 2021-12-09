package one.empty3.library;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class ImageContainer extends Representable implements ResourceLoader {
    private StructureMatrix<BufferedImage> image = new StructureMatrix<>(0, BufferedImage.class);
    private StructureMatrix<URL> url = new StructureMatrix<>(0, URL.class);
    private StructureMatrix<File> path = new StructureMatrix<>(0, File.class);
    private StructureMatrix<URL> videoUrl= new StructureMatrix<>(0, URL.class);
    private StructureMatrix<File> videoPath = new StructureMatrix<>(0, File.class);
     ;
    private URL oldUrl = null;
    private File oldPath = null;
    private boolean isMovie = false;
    private StructureMatrix<VideoDecoder> vd;
    private double nanos;
    private double oldNanos;


    public ImageContainer(File path, boolean isMovie) {
        this.isMovie = isMovie;
        this.path.setElem(path);
        declareProperties();
    }

    public ImageContainer(URL url) {
        this.isMovie = isMovie;
        this.url.setElem(url);
        if (url != null) {
            try {
                image.setElem(ImageIO.read(url));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        declareProperties();
    }

    public ImageContainer() {
        this.isMovie = isMovie;
        declareProperties();
    }

    public ImageContainer(BufferedImage image) {
        this.isMovie = isMovie;
        this.image.setElem(image);
        declareProperties();
    }

    @Override
    public void declareProperties() {
        super.declareProperties();
        getDeclaredDataStructure().put("image/Instance of BufferedImage", image);
        getDeclaredDataStructure().put("url/URL of BufferedImage", url);
        getDeclaredDataStructure().put("path/Local path or filesystem path of BufferedImage", path);
        getDeclaredDataStructure().put("videoUrl/URL of mp4/avi", videoUrl);
        getDeclaredDataStructure().put("videoPath/Local path or filesystem path of mp4/avi", videoPath);

        load();
    }

    private boolean hasChanged(File elem) {
        boolean c = oldPath != elem;
        oldPath = elem;
        return c;
    }

    private boolean hasChanged(URL elem) {
        boolean c = oldUrl != elem;
        oldUrl = elem;
        return c;
    }

    @Override
    public void load() {
        if (url!=null && hasChanged(url.getElem()) && url.getElem() != null) {
            loadImage(url.getElem());
            isMovie = false;
        } else if (path!=null &&hasChanged(path.getElem()) && path.getElem() != null) {
            loadImage(path.getElem());
            isMovie = false;
        } else if (videoUrl!=null&&hasChanged(videoUrl.getElem())) {
            loadVideo(videoUrl.getElem());
        } else if (videoPath!=null&&hasChanged(videoPath.getElem())) {
            loadVideo(videoPath.getElem());
        }
        if(isMovie) {
        nanos = System.nanoTime();
        if (oldNanos >= nanos && isMovie) {
            ECBufferedImage current = vd.getElem().current();
            image.setElem(current);
        }
        }
    }


    private void loadImage(File path) {
        if (path != null) {
            try {
                image.setElem(ImageIO.read(new FileInputStream(path)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void loadImage(URL url) {
        if (url != null) {
            try {
                image.setElem(ImageIO.read(url));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void loadVideo(File path) {
        if (path != null) {
/*            vd = new StructureMatrix<>(0, DecodeAndEncodeFrames.class);
            DecodeAndEncodeFrames vd2 = new DecodeAndEncodeFrames(path, new TextureMov());
            vd.setElem(vd2);
  */          isMovie = true;
            try {
                image.setElem(ImageIO.read(new FileInputStream(path)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void loadVideo(URL url) {
        if (url != null) {
            try {
                Object content = url.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }/*
            vd = new StructureMatrix<>(0, DecodeAndEncodeFrames.class);
            DecodeAndEncodeFrames vd2 = new DecodeAndEncodeFrames(new File(url.getFile()), new TextureMov());
            vd.setElem(vd2);
           */isMovie = true;
            try {
                image.setElem(ImageIO.read(new FileInputStream(new File(url.getFile()))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public StructureMatrix<BufferedImage> getImage() {
        return image;
    }

    public void setImage(StructureMatrix<BufferedImage> image) {
        this.image = image;
    }

    public StructureMatrix<URL> getUrl() {
        return url;
    }

    public void setUrl(StructureMatrix<URL> url) {
        this.url = url;
    }

    public StructureMatrix<File> getPath() {
        return path;
    }

    public void setPath(StructureMatrix<File> path) {
        this.path = path;
    }

    public StructureMatrix<VideoDecoder> getVd() {
        return vd;
    }

    public void setVd(StructureMatrix<VideoDecoder> vd) {
        this.vd = vd;
    }

    public StructureMatrix<URL> getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(StructureMatrix<URL> videoUrl) {
        this.videoUrl = videoUrl;
    }

    public StructureMatrix<File> getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(StructureMatrix<File> videoPath) {
        this.videoPath = videoPath;
    }
}

