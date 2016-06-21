package com.nathansass.todoapp;

import org.json.JSONArray;

/**
 * Created by nathansass on 6/20/16.
 */
public interface GetImageUrlsCallback {
    void done(JSONArray returnedUrls);
}
