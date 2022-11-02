package one.empty3.feature20220726;


import java.util.*;

import one.empty3.io.ProcessFile;
import one.empty3.Pojo;
/*
public class ParseJSon {
    public List<ProcessFile> parse(String jsonString) {
        try {
            List<ProcessFile> o = new ArrayList<>();
            //assign your JSON String here
            JSONObject obj = new JSONObject(jsonString);
            String classname = "";
            JSONArray arr = obj.getJSONArray("filters");
            for (int i = 0; i < arr.length(); i++) {
                classname = arr.getJSONObject(i).getString("classname");

                Class c = Class.forName(classname);
                ProcessFile pf = (ProcessFile)
                        (c.newInstance());

                o.add(pf);

                String properties = arr.getJSONObject(i).getString("properties");
                JSONObject propertiesA = new JSONObject(properties);
                Set<String> keys = propertiesA.keySet();
                for (String key : keys) {
                    String value = propertiesA.getString(key);
                    if (key.equals("classname"))
                        classname = value;
                    else if (value.split(":").length == 2) {
                        String[] array = value.split(":");
                        String vType = array[0];
                        String vValue = array[1];

                        Pojo.setP((Object) pf, key, classname, vValue);


                    }
                }
            }
            return o;
        } catch (InstantiationException
                | ClassNotFoundException
                | IllegalAccessException
                ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
*/