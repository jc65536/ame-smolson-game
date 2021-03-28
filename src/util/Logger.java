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

    public static void log(String message) {
        message = String.format("[Timestamp: %s] [Thread: %s] %s\n", dateFormat.format(new Date()), Thread.currentThread().getName(), message);
        System.out.print(message);
        try {
            fileWriter.write(message);
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
