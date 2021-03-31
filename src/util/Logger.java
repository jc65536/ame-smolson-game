package util;

import java.util.*;
import java.io.*;
import java.text.*;

public class Logger {

    private static BufferedWriter fileWriter = null;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:SS");

    static {
        try {
            fileWriter = new BufferedWriter(new FileWriter("log.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void log(Object message) {
        message = String.format("[Timestamp: %s] [Thread: %s]\n%s\n--", dateFormat.format(new Date()), Thread.currentThread().getName(), message);
        System.out.println(message);
        try {
            fileWriter.write(message.toString());
            fileWriter.newLine();
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logf(String message, Object... args) {
        log(String.format(message, args));
    }

}
