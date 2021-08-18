package com.pixcat.warehouseproductscanner.data.auth;

import com.pixcat.warehouseproductscanner.data.Result;
import com.pixcat.warehouseproductscanner.data.model.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class ActiveUserRepository {

    private static volatile ActiveUserRepository instance;

    private final AuthDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    private ActiveUserRepository(AuthDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static ActiveUserRepository getInstance(AuthDataSource dataSource) {
        if (instance == null) {
            instance = new ActiveUserRepository(dataSource);
        }
        return instance;
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String username, String password) {
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }
}
