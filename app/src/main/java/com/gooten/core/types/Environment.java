package com.gooten.core.types;

/**
 * Enumeration of all Gooten API environments.
 */
public enum Environment {

    LIVE("https://api.print.io/api/"),
    STAGING("https://staging-api.print.io/api/"),
    QA("http://qa-api.print.io/api/");

    private String apiUrl;

    private Environment(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getAPIUrl() {
        return apiUrl;
    }

}
