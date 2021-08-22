package com.pixcat.warehouseproductscanner.ui.auth;

class ActiveUserView {

    private final String displayName;

    ActiveUserView(String displayName) {
        this.displayName = displayName;
    }

    String getDisplayName() {
        return displayName;
    }
}
