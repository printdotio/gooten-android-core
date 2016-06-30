package com.gooten.core;

import android.content.Context;

import com.gooten.core.persistence.GTNConfigPersistence;
import com.gooten.core.types.Environment;
import com.gooten.core.utils.Logger;
import com.gooten.core.utils.StringUtils;

/**
 * Gooten Core SDK entry point.
 */
public class GTN {

    private GTN() {
        // NOP
    }

    /**
     * @return SDK version in format MAJOR.MINOR.PATCH
     */
    public static String getVersion() {
        return Version.BUILD_VERSION;
    }

    /**
     * Holds SDK configuration.
     */
    private static GTNConfig config;

    /**
     * Returns current {@code GTNConfig} instance.
     * Reference could change in runtime if application gets restarted, in which case the {@link GTNConfig} is restored from permanent storage.
     *
     * @return {@link GTNConfig} instance, or null if current application instance was killed and restarted.
     */
    public static GTNConfig getConfig() {
        return config;
    }

    /**
     * Returns restored {@link GTNConfig} if needed.
     */
    public static GTNConfig getRestoredConfig(Context context) {
        if (config == null) {
            config = GTNConfigPersistence.load(context);
        }
        return config;
    }

    /**
     * Sets the Gooten SDK configuration without validation.
     *
     * @param context The {@code Context} of your application.
     * @param config  The {@code GTNConfig} holding the configuration for Gooten SDK.
     */
    public static void setConfigNoValidation(Context context, GTNConfig config) {
        if (context == null) {
            throw new IllegalArgumentException("Failed to set Gooten SDK Config. Context can not be null.");
        }

        GTN.config = config;
        GTNConfigPersistence.store(context, config);
    }

    /**
     * Sets the Gooten SDK configuration.
     *
     * @param context The {@code Context} of your application.
     * @param config  The {@code GTNConfig} holding the configuration for Gooten SDK.
     * @throws GTNException If the configuration is invalid.
     */
    public static void setConfig(Context context, GTNConfig config) throws GTNException {
        if (context == null) {
            throw new GTNException("Failed to set Gooten SDK Config. Context can not be null.");
        }
        if (config == null) {
            throw new GTNException("Failed to set Gooten SDK Config. GTNConfig can not be null.");
        }

        // Validate configuration
        GTNException validationException = validateConfig(config);
        if (validationException != null) {
            GTN.config = null;
            Logger.e(GTN.class, "Failed to set Gooten SDK Config. GTNConfig is not valid.");
            throw validationException;
        }

        // Set and store GTN config
        GTN.config = config;
        GTNConfigPersistence.store(context, config);
    }

    /**
     * Performs sanity checks to supplied {@code GTNConfig} object.
     *
     * @param config {@link GTNConfig} object to validate
     * @return {@link GTNException} describing the issue in configuration, or null if {@link GTNConfig} is valid.
     */
    private static GTNException validateConfig(GTNConfig config) {
        if (StringUtils.isBlank(config.getRecipeId())) {
            return new GTNException("GTNConfig is not valid. RecipeId must be set.");
        }
        if (config.getEnvironment() == null) {
            config.setEnvironment(Environment.LIVE);
            Logger.e(GTN.class, "Environment not set. Using LIVE environment...");
        }
        return null;
    }

}
