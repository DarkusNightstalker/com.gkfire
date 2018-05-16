package gkfire.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gkfire.web.util.BeanUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Serializable;
import java.net.URL;
import javax.faces.context.FacesContext;

public class ManejoArchivo implements Serializable {

    public static <T> T setFileJsonToClass(Class<T> type, String path)
            throws Exception {
        Reader reader = new InputStreamReader(BeanUtil.getResourceAsStream(path), "UTF-8");
        Gson gson = new GsonBuilder().create();
        T pee = gson.fromJson(reader, type);

        return pee;
    }

    public static void setClassToFileJson(Object ob, String path)
            throws Exception {
        Gson gson = new Gson();
        String gSonString = gson.toJson(ob);

        URL url = FacesContext.getCurrentInstance().getExternalContext().getResource(path);
        File file = new File(url.getPath());
        FileWriter writer = new FileWriter(file);
        writer.write(gSonString);

        writer.close();
    }

    public static String getTextoOfFile(String nombreArchivo) {
        File f = new File(nombreArchivo);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (Exception localException) {
            }
        }
        String texto = "";
        FileReader archivo = null;
        String linea = "";
        try {
            archivo = new FileReader(nombreArchivo);
            BufferedReader lector = new BufferedReader(archivo);
            System.out.println(nombreArchivo);
            while ((linea = lector.readLine()) != null) {
                texto = texto + linea + "\n";
            }
            System.out.println(texto);

            return texto;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (archivo != null) {
                try {
                    archivo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void escribirTextoArchivo(String nombreArchivo, String texto) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(nombreArchivo, false);
            pw = new PrintWriter(fichero);

            pw.println(texto);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
