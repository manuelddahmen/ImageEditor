package one.empty3.feature.gui;

import org.apache.commons.net.ftp.FTPFile;


import java.io.File;

public class FDEntity {
    public enum type {
        FTPFile, FTPFolder, File, Folder, BufferedImage
    }

    public BufferedImage load(FTPFile ftpFile) {
        return null;
    }

    public BufferedImage load(File file) {
        return null;
    }

    public BufferedImage load(BufferedImage originalImage) {
        return null;
    }

}
