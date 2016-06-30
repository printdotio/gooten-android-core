package com.gooten.core.utils;

import java.util.Collection;

import android.content.Context;

import com.gooten.core.GTN;
import com.gooten.core.GTNConfig;

/**
 * Gooten core validation utilities.
 *
 * @author Vlado
 */
public class Validate {

    public static void hasGTNConfig(Context context) {
        notNull(context, "Context");
        GTNConfig cfg = GTN.getRestoredConfig(context);
        if (cfg == null) {
            throw new NullPointerException("GTNConfig is not set");
        }
        if (StringUtils.isBlank(cfg.getRecipeId())) {
            throw new IllegalArgumentException("Argument 'GTNConfig.RecipeId' cannot be null or empty");
        }
        if (cfg.getEnvironment() == null) {
            throw new IllegalArgumentException("GTNConfig is not valid. Environment must be set.");
        }
    }

    public static void notNull(Object arg, String name) {
        if (arg == null) {
            throw new NullPointerException("Argument '" + name + "' cannot be null");
        }
    }

    public static void notEmpty(String arg, String name) {
        if (StringUtils.isEmpty(arg)) {
            throw new IllegalArgumentException("Argument '" + name + "' cannot be null or empty");
        }
    }

    public static void notNullOrEmpty(Collection<?> arg, String name) {
        if (arg == null || arg.isEmpty()) {
            throw new NullPointerException("Argument '" + name + "' cannot be null or empty");
        }
    }

}
