package com.alimert.java.omdb.util;

import com.alimert.java.omdb.BuildConfig;

import java.util.HashMap;
import java.util.Map;

public class ApiQueryManager {

    private static ApiQueryManager instance;
    private static Map<String, String> apiQuery;

    public static ApiQueryManager getInstance() {
        if (instance == null) {
            instance = new ApiQueryManager();
        }
        return instance;
    }

    private ApiQueryManager() {}

    public static Map<String, String> addQueryParams(String key, String value) {
        apiQuery = new HashMap<>();
        apiQuery.put("apikey", BuildConfig.API_KEY);
        apiQuery.put("plot", "short");
        apiQuery.put("type", "movie");
        apiQuery.put("r", "json");
        apiQuery.put(key, value);
        return apiQuery;
    }
}
