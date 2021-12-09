package one.empty3.feature;

import one.empty3.io.ProcessFile;
import one.empty3.library.T;
import one.empty3.library.TextureMov;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import  one.empty3.feature.app.replace.javax.imageio.ImageIO;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.logging.Logger;
//import org.json.*;

public class ProcessFiles {

    public static void main(String[] args) {
        SetSettings setSettings = new SetSettings(args[0]);
        setSettings.config2();

    }

}
