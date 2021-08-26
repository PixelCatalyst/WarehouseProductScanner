package com.pixcat.warehouseproductscanner.data.model;

import okhttp3.Credentials;

public final class ActiveUser {

    private final String username;
    private final String authToken;

    public static ActiveUser of(String username, String password) {
        return new ActiveUser(username, password);
    }

    private ActiveUser(String username, String password) {
        this.username = username;
        this.authToken = Credentials.basic(username, password);
    }

    public String getUsername() {
        return username;
    }

    public String getAuthToken() {
        return authToken;
    }
}
