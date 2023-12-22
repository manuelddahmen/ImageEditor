package one.empty3.feature.app.maxSdk29.pro.data.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class URLContentTask  {

        public String loadUrlAsString(String... urls) {
            if (urls.length == 0)
                return null;

            String url = urls[0];
            try {
                URL urlObj = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder content = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }

                reader.close();
                connection.disconnect();

                return content.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void ConvertToInt(String result) {
            LoggedInUser fakeUser = null;
            try {
                if (Integer.parseInt(result.trim()) == 1) {
                    fakeUser = new LoggedInUser(UUID.randomUUID().toString(), "Test User");
                    LoggedInUser.setCurrentUser(fakeUser);
                    System.out.println("Ok Connect OK");
                    return;
                } else if (Integer.parseInt(result.trim()) == 0) {
                    System.err.println("Error logging User not connected == 0");
                    fakeUser = new LoggedInUser("0", "");
                    LoggedInUser.setCurrentUser(fakeUser);
                } else {
                    System.err.println("Error logging User not connected ...");
                    fakeUser = new LoggedInUser("0", "");
                    LoggedInUser.setCurrentUser(fakeUser);

                }
            } catch (NumberFormatException ex) {
                System.err.println("Error logging User not connected ...");
                fakeUser = new LoggedInUser("0", "");
                LoggedInUser.setCurrentUser(fakeUser);

            }
        }

    }
