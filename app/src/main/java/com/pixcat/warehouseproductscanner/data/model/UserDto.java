package com.pixcat.warehouseproductscanner.data.model;

import java.util.List;

public class UserDto {

    private final String username;
    private final String password;
    private final List<String> roles;

    public UserDto(String username, String password, List<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles;
    }
}
