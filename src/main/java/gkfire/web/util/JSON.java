package gkfire.web.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.hibernate.proxy.HibernateProxy;

public class JSON {

    public static String convert(Object o)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        String json = "{";
        if (!o.getClass().isArray()) {
            if (!(o instanceof List)) {
                if (!(o instanceof HibernateProxy)) {
                    if (o.getClass().isEnum()) {
                        Method[] methods = o.getClass().getDeclaredMethods();
                        List<Method> avMethods = new ArrayList();
                        for (Method m : methods) {
                            if (m.getName().startsWith("get")) {
                                avMethods.add(m);
                            }
                        }
                        if (avMethods.isEmpty()) {
                            return "\"" + ((Enum) o).name() + "\"";
                        }
                        json = json + node("name", ((Enum) o).name());
                        for (Method m : avMethods) {
                            String name = m.getName().replaceFirst("get", "");
                            name = new StringBuilder().append(name.charAt(0)).append("").toString().toLowerCase() + name.substring(1, name.length());
                            json = json + node(name, m.invoke(o, new Object[0]));
                        }
                        if (json.charAt(json.length() - 1) == ',') {
                            json = json.substring(0, json.length() - 1);
                        }
                    } else {
                        Field[] fields = o.getClass().getDeclaredFields();
                        for (Field field : fields) {
                            field.setAccessible(true);
                            Object fieldValue = field.get(o);
                            field.setAccessible(false);
                            json = json + node(field.getName(), fieldValue);
                        }

                        if (json.charAt(json.length() - 1) == ',') {
                            json = json.substring(0, json.length() - 1);
                        }
                    }
                }
            }
        }
        json = json + "}";
        return json;
    }

    public static Object[] toArray(ScriptObjectMirror value) {
        Set<String> keys = value.keySet();
        List objects = new ArrayList();
        for (String key : keys) {
            objects.add(value.get(key));
        }
        return objects.toArray();
    }

    private static String node(String name, Object fieldValue) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (fieldValue == null) {
            return "";
        }
        if ((fieldValue instanceof String)) {
            return "\"" + name + "\" : \"" + fieldValue + "\",";
        }
        if ((fieldValue instanceof Date)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return "\"" + name + "\" : \"" + sdf.format(fieldValue) + "\",";
        }
        if ((fieldValue instanceof Number)) {
            return "\"" + name + "\" : " + fieldValue + ",";
        }
        if (!(fieldValue instanceof HibernateProxy)) {
            if ((fieldValue instanceof Boolean)) {
                return "\"" + name + "\" : " + fieldValue + ",";
            }
            if (fieldValue.getClass().isEnum()) {
                return "\"" + name + "\" : " + convert(fieldValue) + ",";
            }
            return "\"" + name + "\" : " + convert(fieldValue) + ",";
        }
        return "";
    }
}
