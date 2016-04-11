package at.sheldor5.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Michael Palata on 11.04.2016.
 */
public class FileUtils {

    private static String help = null;

    public static final void serialize(final Object o, final String filePath) {
        try {
            final File f = new File(filePath);
            if (f.exists()) {
                System.out.println("Nicht gespeichert, Datei existiert bereits: " + f.getAbsolutePath());
                return;
            } else if (!f.createNewFile()) {
                System.out.println("Nicht gespeichert, Datei konnte nicht erstellt werden!");
                return;
            }
            FileOutputStream fileOut = new FileOutputStream(f);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(o);
            out.close();
            fileOut.close();
            System.out.println("Gespeichert in: " + filePath);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static final Object deserialize(final String filePath) {
        Object result;
        try {
            final File f = new File(filePath);
            if (!f.exists()) {
                System.out.println("Fehlgeschlagen, Datei existiert nicht: " + f.getAbsolutePath());
                result = null;
            } else {
                FileInputStream fileIn = new FileInputStream(filePath);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                result = in.readObject();
                in.close();
                fileIn.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
            result = null;
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    public static String getHelp() {
        if (help != null) {
            return help;
        }

        try {
            BufferedReader txtReader = new BufferedReader(new InputStreamReader(FileUtils.class.getResourceAsStream("/help.txt")));
            help = new String();
            String line;
            while ((line = txtReader.readLine()) != null) {
                help += line + "\n";
            }
            help += "\n";
        } catch (IOException e) {
            e.printStackTrace();
            help = "";
        }
        return help;
    }
}
