package com.gooten.core.utils;

import java.util.LinkedList;
import java.util.Queue;

import android.util.Log;

/**
 * Logger with class identifier.
 *
 * @author Vlado
 */
public class Logger {

    public static final String TAG = "GootenCoreSDK";

    private static final int MAX_QUEUE_LOG_SIZE = 200;
    private static final Queue<String> MESSAGE_LOGS = new LinkedList<String>();

    public synchronized static void clearLog() {
        MESSAGE_LOGS.clear();
    }

    public synchronized static void printLog(StringBuilder sb) {
        for (String s : MESSAGE_LOGS) {
            sb.append(s);
            sb.append("\n");
        }
    }

    private synchronized static void queueMessage(String message) {
		MESSAGE_LOGS.add(message);
		if (MESSAGE_LOGS.size() > MAX_QUEUE_LOG_SIZE) {
			MESSAGE_LOGS.remove();
		}
    }

    public static void d(Object o, String message) {
        String l = getObjectIdentifier(o) + nullSafe(message);
        queueMessage("D: " + l);
        Log.d(TAG, l);
    }

    public static void w(Object o, String message) {
        String l = getObjectIdentifier(o) + nullSafe(message);
        queueMessage("W: " + l);
        Log.w(TAG, l);
    }

    public static void e(Object o, String message) {
        String l = getObjectIdentifier(o) + nullSafe(message);
        queueMessage("E: " + l);
        Log.e(TAG, l);
    }

    public static void e(Object o, String message, Throwable throwable) {
        String l = getObjectIdentifier(o) + nullSafe(message) + '\n' + Log.getStackTraceString(throwable);
        queueMessage("E: " + l);
        Log.e(TAG, l);
    }

    public static void d(Class<?> clazz, String message) {
        String l = getClassIdentifier(clazz) + nullSafe(message);
        queueMessage("D: " + l);
        Log.d(TAG, l);
    }

    public static void w(Class<?> clazz, String message) {
        String l = getClassIdentifier(clazz) + nullSafe(message);
        queueMessage("W: " + l);
        Log.w(TAG, l);
    }

    public static void e(Class<?> clazz, String message) {
        String l = getClassIdentifier(clazz) + nullSafe(message);
        queueMessage("E: " + l);
        Log.e(TAG, l);
    }

    public static void e(Class<?> clazz, String message, Throwable throwable) {
        String l = getClassIdentifier(clazz) + nullSafe(message) + '\n' + Log.getStackTraceString(throwable);
        queueMessage("E: " + l);
        Log.e(TAG, l);
    }

    private static String getObjectIdentifier(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("Logger creation failed. You must provide not null object as log identifier.");
        }
        return getClassIdentifier(o.getClass());
    }

    private static String getClassIdentifier(Class<?>... clazz) {
        if (clazz != null && clazz.length > 0) {
            String classIdentifier = clazz[0].getSimpleName();
            for (int i = 1; i < clazz.length; i++) {
                classIdentifier += "." + clazz[i].getSimpleName();
            }
            return "[" + classIdentifier + "] ";
        } else {
            throw new IllegalArgumentException("Logger creation failed. You must provide at least one class.");
        }
    }

    private static String nullSafe(String str) {
        return str == null ? "null" : str;
    }

    private String classIdentifier;
    private boolean enabled;

    /**
     * Constructs new {@code Logger} with identifier for supplied class objects.
     *
     * @param clazz Class objects used to build identifier.
     */
    public Logger(Class<?>... clazz) {
        this(true, clazz);
    }

    /**
     * Constructs new {@code Logger} with identifier for supplied class objects.
     *
     * @param enabled Flag telling if logging is enabled for this {@code Logger}.
     * @param clazz   Class objects used to build identifier.
     */
    public Logger(boolean enabled, Class<?>... clazz) {
        this.enabled = enabled;
        this.classIdentifier = getClassIdentifier(clazz);
    }

    /**
     * Logs information message.
     */
    public void i(String message) {
        if (enabled) {
            String l = classIdentifier + nullSafe(message);
            queueMessage("I: " + l);
            Log.i(TAG, l);
        }
    }

    /**
     * Logs information message and exception stack trace.
     */
    public void i(String message, Throwable throwable) {
        i(nullSafe(message) + '\n' + Log.getStackTraceString(throwable));
    }

    /**
     * Logs warning message.
     */
    public void w(String message) {
        if (enabled) {
            String l = classIdentifier + nullSafe(message);
            queueMessage("W: " + l);
            Log.w(TAG, l);
        }
    }

    /**
     * Logs warning message and exception stack trace.
     */
    public void w(String message, Throwable throwable) {
        w(nullSafe(message) + '\n' + Log.getStackTraceString(throwable));
    }

    /**
     * Logs debugging message.
     */
    public void d(String message) {
        if (enabled) {
            String l = classIdentifier + nullSafe(message);
            queueMessage("D: " + l);
            Log.d(TAG, l);
        }
    }

    /**
     * Logs debugging message and exception stack trace.
     */
    public void d(String message, Throwable throwable) {
        d(nullSafe(message) + '\n' + Log.getStackTraceString(throwable));
    }

    /**
     * Logs error message.
     */
    public void e(String message) {
        if (enabled) {
            String l = classIdentifier + nullSafe(message);
            queueMessage("E: " + l);
            Log.e(TAG, l);
        }
    }

    /**
     * Logs error message and exception stack trace.
     */
    public void e(String message, Throwable throwable) {
        e(nullSafe(message) + '\n' + Log.getStackTraceString(throwable));
    }

}
