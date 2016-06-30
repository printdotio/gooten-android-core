package com.gooten.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import android.content.Context;

public class FileUtils {

    /**
     * Class logger.
     */
    private static final Logger LOG = new Logger(FileUtils.class);

    /**
     * Checks if a file exist on the file system.
     *
     * @param filename Absolute path and name of the file to check.
     * @return <code>true</code> if the file exists; else <code>false</code>.
     */
    public static boolean fileExists(String filename) {
        boolean fileExists = false;
        try {
            File fileToCheck = new File(filename);
            fileExists = fileToCheck.exists();
        } catch (Exception e) {
            // NOP
        }
        return fileExists;
    }

    /**
     * Deletes a file from file system.
     *
     * @param filename Absolute path and name of the file to delete.
     * @return <code>true</code> if the file was successfully deleted; else
     * <code>false</code>.
     */
    public static boolean deleteFile(String filename) {
        boolean fileDeleted = false;
        if (StringUtils.isNotBlank(filename)) {
            try {
                File fileToDelete = new File(filename);
                fileDeleted = fileToDelete.delete();
            } catch (Exception e) {
                // NOP
            }
        }
        return fileDeleted;
    }

    public static void cleanDir(Context context, String dirName) {
        cleanDir(context.getExternalFilesDir(dirName));
    }

    public static void cleanDir(File rootDir) {
        if (rootDir != null) {
            File[] files = rootDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        cleanDir(file);
                        file.delete();
                    } else {
                        file.delete();
                    }
                }
            }
        }
    }

    public static final void copy(File source, File destination) throws IOException {
        if (source.exists()) {
            if (source.isDirectory()) {
                copyDirectory(source, destination);
            } else {
                copyFile(source, destination);
            }
        }
    }

    private static final void copyDirectory(File source, File destination) throws IOException {
        destination.mkdirs();
        for (File file : source.listFiles()) {
            if (file.isDirectory()) {
                copyDirectory(file, new File(destination, file.getName()));
            } else {
                copyFile(file, new File(destination, file.getName()));
            }
        }
    }

    private static final void copyFile(File source, File destination) throws IOException {
        @SuppressWarnings("resource")
        FileChannel sourceChannel = new FileInputStream(source).getChannel();
        @SuppressWarnings("resource")
        FileChannel targetChannel = new FileOutputStream(destination).getChannel();
        sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
        sourceChannel.close();
        targetChannel.close();
    }

    public static boolean saveStringToFile(Context context, String fileName, String string) {
        if (!fileName.startsWith("GTN")) {
            throw new IllegalArgumentException("File name must have \"GTN\" prefix.");
        }

        boolean success = false;
        OutputStreamWriter osw = null;
        try {
            osw = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            osw.write(string == null ? "" : string);
            osw.flush();
            success = true;
        } catch (Exception e) {
            LOG.d("Failed saving string to file.", e);
        } finally {
            try {
                osw.close();
            } catch (Exception e) {
            }
        }
        return success;
    }

    public static String loadStringFromFile(Context context, String fileName) {
        if (!fileName.startsWith("GTN")) {
            throw new IllegalArgumentException("File name must have \"GTN\" prefix.");
        }

        String result = null;
        try {
            result = readStreamAsString(context.openFileInput(fileName));
        } catch (Exception e) {
            LOG.d("Failed loading string from file.", e);
        }
        return result;
    }

    public static String readStreamAsString(InputStream is) {
        return readStreamAsString(is, Charset.defaultCharset().name());
    }

    public static String readStreamAsString(InputStream is, String encoding) {
        String result = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is, encoding));
            StringBuilder sb = new StringBuilder();

            String receiveString = null;
            while ((receiveString = br.readLine()) != null) {
                sb.append(receiveString);
            }

            result = sb.toString();
        } catch (IOException e) {
            LOG.d("Failed reading string from stream.", e);
        } finally {
            try {
                br.close();
            } catch (Exception e) {
            }
            try {
                is.close();
            } catch (Exception e) {
            }
        }
        return result;
    }

}
