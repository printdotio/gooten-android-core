package com.gooten.core.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    private final static char[] hexArray = "0123456789abcdef".toCharArray();
    private final static char[] hexChars = new char[32];
    private static MessageDigest md5;

    /**
     * Returns hash value of the supplied string.
     */
    public static String hashString(String plain) {
        synchronized (hexChars) {
            if (md5 == null) {
                try {
                    md5 = java.security.MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException e) {
                }
            }
            if (md5 != null) {
                char[] hexChars = Utils.hexChars, hexArray = Utils.hexArray;
                byte[] array = md5.digest(plain.getBytes());
                for (int i = 0, l = array.length; i < l; i++) {
                    int v = array[i] & 0xFF;
                    hexChars[i << 1] = hexArray[v >>> 4];
                    hexChars[(i << 1) + 1] = hexArray[v & 0x0F];
                }
                return new String(hexChars);
            }
        }
        return Integer.toString(plain.hashCode());
    }

    /**
     * Executes runnable object in new thread.
     *
     * @param runnable Object to execute.
     */
    public static Thread execute(Runnable runnable) {
        Thread thread = null;
        if (runnable != null) {
            thread = new Thread(runnable);
            thread.start();
        }
        return thread;
    }

}
