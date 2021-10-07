//package one.empty3;
//
//import java.io.IOException;
//import org.apache.commons.net.ftp.FTPClient;
//import org.apache.commons.net.ftp.FTPReply;
//
//public class FTPConnectAndLogin {
//    private static void showServerReply(FTPClient ftp) {
//        String[] replies = ftp.getReplyStrings();
//        if (replies != null && replies.length > 0) {
//        for (String aReply : replies) {
//            System.out.println("SERVER: " + aReply);
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        // SET THESE TO MATCH YOUR FTP SERVER //
//        String server = "www.server.com";
//        int port = 21;
//        String user = "username";
//        String pass = "password";
//
//        FTPClient ftp = new FTPClient();
//        try {
//            ftp.connect(server, port);
//            showServerReply(ftp);
//            int replyCode = ftp.getReplyCode();
//            if (!FTPReply.isPositiveCompletion(replyCode)) {
//                System.out.println("Operation failed. Server reply code: " + replyCode);
//                return;
//            }
//            boolean success = ftp.login(user, pass);
//            showServerReply(ftp);
//            if (!success) {
//                System.out.println("Failed to log into the server");
//            } else {
//                System.out.println("LOGGED IN SERVER");
//            }
//        } catch (IOException ex) {
//            System.out.println("Oops! Something went wrong.");
//            ex.printStackTrace();
//        }
//    }
//}
//
