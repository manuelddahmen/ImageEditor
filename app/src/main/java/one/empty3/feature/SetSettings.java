package one.empty3.feature;

import one.empty3.feature.DiffEnergy;
import one.empty3.feature.FTPProcessFiles;
import one.empty3.io.ProcessFile;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

public class SetSettings {
    private String settingsPropertiesPath;
    public HashMap<String, ProcessBean> processBeans = new HashMap<>();

    private String[] currentDirin;
    private int maxFilesInDir;
    private int maxRes;
    private String classnames;
    private String classname;
    private String currentDirout;
    private ProcessFile processInstance;
    private FTPClient ftpClient;
    private String directory;
    private String[] initialDirectories;

    public  Properties settings() {
        Properties p = new Properties();
        try {
            p.load(
                    new FileInputStream(settingsPropertiesPath +


                            "/settings.properties"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return p;
    }

    public void loadArgsProps(String propFile) {
    }

    public Properties defProcess(
            InputStream is) {
        Properties p = new Properties();
        try {
            p.load(is);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return p;
    }
    public SetSettings(String settingsFile) {
        settingsPropertiesPath = "sets/";
        if (settingsFile!=null) {
            settingsPropertiesPath =settingsFile;


            if (settingsFile.endsWith(".properties")) {
                loadArgsProps(settingsFile);
            }
            if (settingsFile.endsWith(".json")) {
                loadArgsJson(settingsFile);
            }
        } else {
            int i = 0;
            String[] sets = new File("sets").list();
            while (i < Objects.requireNonNull(sets).length) {
                settingsPropertiesPath = "sets/" + sets[i];
                i++;
            }

        }
    }


    public void loadArgsJson(String file) {


    }

    public  void parseAndSet(ProcessFile processInstance, List<Object> argCl) {
        if (argCl.size() % 3 == 0) {
            for (int i = 0; i < argCl.size(); i += 3) {
                //Class param = Class.forName(argCl.get(i));
                String param = (String) argCl.get(i);
                String propertyName = (String) argCl.get(i + 1);
                String argValue = (String) argCl.get(i + 2);
                try {
                    Method m = processInstance.getClass().getMethod("set" + propertyName, argValue.getClass());
                    m.invoke(processInstance, "set" + propertyName, argValue);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }
    }


    public Iterable<? extends File> getDirs() {
        return null;

    }

    public void config2() {
        try {
            DiffEnergy.pw = new PrintWriter("." + File.separator + "energies.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // if(path==null) {
        //      System.exit(-1);
        //  }

        // Properties set = defProcess(args[0]);

        Properties settings = settings();


        String server;
        int port;
        String username;
        String password;


        currentDirin = new String[1];
        if ("local".equals(settings.getProperty("in.device"))) {
            currentDirin = (settings.getProperty("in.directory")).split(",");
            server = "file";
            port = 0;
            username = "";
            password = "";
        } else {
            currentDirin = ((String) settings.getProperty("in.directory")).split(",");
            server = (String) settings.getProperty("host");
            port = Integer.parseInt(settings.getProperty("port"));
            username = (String) settings.getProperty("username");
            password = (String) settings.getProperty("password");
        }

        String maxFilesInDir0 = settings.getProperty("maxFilesInDir");
        maxFilesInDir = Integer.parseInt(maxFilesInDir0 == null ? "10000" : maxFilesInDir0);
        maxRes = Integer.parseInt(settings.getProperty("maxRes"));
        /* String*/
        classnames = (String) settings.getProperty("classname");
        String class0 = (String) settings.getProperty("class0");
        String directoryOut = (String) settings.getProperty("out.directory");


        String sep = "";
        int i = 0;
        //currentDirin = "";
        if (class0 == null || class0.equals("")) {
            sep = "";
        } else sep = ",";
/*
        classnames = (classnames != null ?
                classnames + sep : "")

                + (class0 == null ? "" : "," + classnames);
*/
        String[] classnamesArr = classnames.split(",");

//        for(String inputDir : currentDirin) {
        int index = 0;
        for (String classname2 : classnamesArr) {
            try {
                classname = classname2;
                if (i > 0)
                    currentDirin[index] = currentDirout;


                currentDirout = "" + directoryOut + "-" + i + "-" + classname + "/";
                Logger.getLogger(FTPProcessFiles.class.getName()).info("Process class name read " + classname);
                System.out.println(classname);
                Class classs = Class.forName(
                        classname
                );


                Logger.getLogger(FTPProcessFiles.class.getName()).info("Process Dir" + classname2);

                Object o = classs.newInstance();

                if (o instanceof ProcessFile)

                    processInstance = (ProcessFile) o;

                String arg = null;

                List<Object> argCl = new ArrayList();

                if ((arg = (String) (settings.getProperty(classname))) != null) {

                    String[] ar = arg.split(":");

                    argCl.addAll(Arrays.asList(ar));

                }

                parseAndSet(processInstance, argCl);


                if (i == 0) {


                    if (server.startsWith("ftp")) {


                        ftpClient = new FTPClient();
                        ftpClient.connect(server, port);
                        showServerReply(ftpClient);

                        int replyCode = ftpClient.getReplyCode();
                        if (!FTPReply.isPositiveCompletion(replyCode)) {
                            System.out.println("Connect failed");
                            return;
                        }
                        // reads settings.xml or prompts user/pass
                        boolean success = ftpClient.login(username, password);
                        showServerReply(ftpClient);

                        if (!success) {
                            System.out.println("Could not login to the server");
                            return;
                        }

                        ftpClient.enterLocalPassiveMode();


                        // Lists files and directories
                        ftpClient.changeWorkingDirectory(directory);
                        showServerReply(ftpClient);

                        FTPFile[] files1 = ftpClient.listFiles(directory);
                        showServerReply(ftpClient);


                        for (int index1 = 0; index1 < files1.length; index1++) {
                            processBeans.put(directory, new ProcessBean(files1[index1]) );
                        }
                    } else {
                        // local path
                        initialDirectories = currentDirin;
                        for (int d = 0; d < initialDirectories.length; d++)
                            if (new File(initialDirectories[d]).exists()) {
                                File[] files = new File(initialDirectories[d]).listFiles();
                                for (int index1 = 0; index1 <files .length; index1++) {
                                    processBeans.put(directory, new ProcessBean(files[index1]) );
                                }
                            }

                    }

                } else {
                    if (new File(currentDirin[index]).exists()) {
                        File[] files = new File(currentDirin[index]).listFiles();
                        for (int index1 = 0; index1 <files.length; index1++) {
                        List<ProcessBean> list1 = new ArrayList<>();
                        list1 = ProcessBean.processBeanList(files);
                        for (int i1 = 0; i1 < list1.size(); i1++) {
                            this.processBeans.put(directory, list1.get(i1));
                        }
                    }
                    }
                }


                i++;
            } catch (Exception ex) {
                System.out.println("Oops! Something wrong happened");
                ex.printStackTrace();
            } finally {
                // logs out and disconnects from server
                if (ftpClient != null) {
                    try {
                        if (ftpClient.isConnected()) {
                            ftpClient.logout();
                            ftpClient.disconnect();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
//            }
        }
 }

    private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            }
        }
    }
        }