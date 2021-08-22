package com.pixcat.warehouseproductscanner.data.auth;

import com.pixcat.warehouseproductscanner.data.Result;
import com.pixcat.warehouseproductscanner.data.model.ActiveUser;

public class ActiveUserRepository {

    private static volatile ActiveUserRepository instance;

    private final AuthDataSource dataSource;

    private ActiveUser user = null;

    private ActiveUserRepository(AuthDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static ActiveUserRepository getInstance(AuthDataSource dataSource) {
        if (instance == null) {
            instance = new ActiveUserRepository(dataSource);
        }
        return instance;
    }

    public Result<ActiveUser> login(String username, String password) {
        Result<ActiveUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            this.user = ((Result.Success<ActiveUser>) result).getData();
        }
        return result;
    }
}
