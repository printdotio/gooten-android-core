package com.gooten.core.types;

import org.json.JSONObject;

/**
 * Interface for classes whose instances can be written to and restored from a JSON.
 *
 * @author Vlado
 */
public interface JSONSerializable {

    /**
     * Flatten this object in to a JSON.
     */
    JSONObject toJSON();

    /**
     * Initializes this object from supplied JSON.
     */
    void fromJSON(JSONObject json);

}
