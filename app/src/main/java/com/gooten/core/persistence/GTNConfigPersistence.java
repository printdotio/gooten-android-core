package com.gooten.core.persistence;

import android.content.Context;

import com.gooten.core.GTNConfig;
import com.gooten.core.utils.FileUtils;
import com.gooten.core.utils.JsonUtils;
import com.gooten.core.utils.Logger;
import com.gooten.core.utils.Validate;

/**
 * Encapsulates persistence mechanism for {@code GTNConfig} structure.
 */
public class GTNConfigPersistence {

    private static final String GTN_CONFIG_DATA_FILENAME = "GTNState.bin";

    public static GTNConfig load(Context context) {
        Validate.notNull(context, "Context");

        GTNConfig config = null;
        synchronized (GTNConfigPersistence.class) {
            String gtnConfigJson = FileUtils.loadStringFromFile(context, GTN_CONFIG_DATA_FILENAME);
            config = JsonUtils.fromJsonString(GTNConfig.class, gtnConfigJson);
        }
        return config;
    }

    public static boolean store(Context context, GTNConfig config) {
        Validate.notNull(context, "Context");

        boolean result = false;
        synchronized (GTNConfigPersistence.class) {
            String gtnConfigStr = config != null ? config.toJSON().toString() : null;
            result = FileUtils.saveStringToFile(context, GTN_CONFIG_DATA_FILENAME, gtnConfigStr);
        }
        Logger.d(GTNConfigPersistence.class, "GTN config stored: " + (result ? "successfully" : "unsuccessfully"));
        return result;
    }

}
