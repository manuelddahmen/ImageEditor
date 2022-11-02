package one.empty3;

import java.util.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;

public class Pojo {
    public static boolean parseBoolean(String s) throws NumberFormatException {
        Boolean b = ((s != null) && s.equalsIgnoreCase("true"));
        if (b)
            return true;
        if (!b && ((s != null) && s.equalsIgnoreCase("false")))
            return false;
        throw new NumberFormatException("Boolean illegal string");
    }

    public static void setO(Object o, String propName, String value) {

        Double d;
        Integer i = 0;
        Boolean b = false;
        System.out.println("Pojo.before.setO. "
                + "\t" + o
                + "\t" + propName + "\t" + value);
        try {
            i = new Integer(value);
            System.out.println("int value: " + i);
            setProperty(o, propName, i, int.class);
            System.out.println("property " + propName + " is set to " + i);
            System.out.println(": " + i.getClass());

        } catch (NumberFormatException | InvocationTargetException | IllegalAccessException |
                 NoSuchMethodException ex) {
            System.out.println("integer not set/nreason " + ex.getClass());
            try {
                d = (double) Double.parseDouble(value);
                setProperty(o, propName, d, double.class);
                System.out.println(": " + d.getClass());

            } catch (NumberFormatException | InvocationTargetException | IllegalAccessException |
                     NoSuchMethodException ex1) {
                System.out.println("double not set");

                try {
                    b = (boolean) parseBoolean(value);
                    setProperty(o, propName, b, boolean.class);
                    System.out.println(": " + b.getClass());

                } catch (NumberFormatException | InvocationTargetException | IllegalAccessException |
                         NoSuchMethodException ex2) {
                    System.out.println("boolean not set");

                    try {
                        if (value != null && !"".equals(value)) {
                            setProperty(o, propName, value, String.class);
                            System.out.println(": " + value.getClass());
                        }
                    } catch (NumberFormatException | InvocationTargetException | IllegalAccessException |
                             NoSuchMethodException e1) {
                        System.out.println("string not set");
                        //e1.printStackTrace();
                    }
                    //ex2.printStackTrace();
                }
            }
            System.out.println("Pojo.after.setO. "
                    + "\t" + o
                    + "\t" + propName + "\t" + value);
        }

    }

    public static void setP(Object p, String propName,
                            String vType, String value) {
        try {
            Object o = null;
            Class c = Class.forName(vType);
            switch (vType) {
                case "double":
                case "Double":
                    o = Double.parseDouble(value);
                    break;
                case "int":
                case "Integer":
                    o = Integer.parseInt(value);
                    break;
                case "boolean":
                case "Boolean":
                    o = parseBoolean(value);
                    break;
                default:
                    break;
            }
            if (o != null) {

                setProperty(p, propName, o,
                        c);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static boolean setProperties(Object o, Properties p) {
        try {
            Iterator it = p.keySet().iterator();

            while (it.hasNext()) {
                String pr = it.next().toString();
                String value = p.getProperty(pr);
                if (!value.equals(""))
                    setO(o, pr, value);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /***
     * @param o Pojo object
     * @param p Properties list String (name), String (scalar value b|d|i)
     * @return properties getter list value
     */
    public static Properties getProperties(Object o, Properties p) {

        return null;
    }

    public static Class getPropertyType(Object o, String propertyName) throws NoSuchMethodException {
        Method propertyGetter = null;
        propertyGetter = o.getClass().getMethod("get" + ("" + propertyName.charAt(0)).toUpperCase() + (propertyName.length() > 1 ? propertyName.substring(1) : ""));
        return propertyGetter.getReturnType();
    }

    public static void setProperty(Object o, String propertyName, Object value
            , Class cl) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method propertySetter = null;
        try {
            propertySetter = o.getClass().getMethod("set" + ("" + propertyName.charAt(0)).toUpperCase() + (propertyName.substring(1)), cl);
        } catch (Exception ex) {
        }

        if (propertySetter == null) {
            String vType = cl.getName();

            switch (vType) {
                case "double":
                case "Double":
                    propertySetter = o.getClass().getMethod("set" + ("" + propertyName.charAt(0)).toUpperCase() + (propertyName.substring(1)), double.class);

                    break;
                case "int":
                case "Integer":
                    propertySetter = o.getClass().getMethod("set" + ("" + propertyName.charAt(0)).toUpperCase() + (propertyName.substring(1)), int.class);

                    break;
                case "boolean":
                case "Boolean":
                    propertySetter = o.getClass().getMethod("set" + ("" + propertyName.charAt(0)).toUpperCase() + (propertyName.substring(1)), boolean.class);

                    break;
                default:
                    break;
            }
        }
        propertySetter.invoke(o, value);
        System.out.println("type : " + o.getClass().getName() + " Property: " + propertyName + " New Value set " + getProperty(o, propertyName));
    }

    public static Object getProperty(Object o, String propertyName) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method propertySetter = null;
        propertySetter = o.getClass().getMethod("get" + ("" + propertyName.charAt(0)).toUpperCase() + propertyName.substring(1));
        return propertySetter.invoke(o);
    }

}
