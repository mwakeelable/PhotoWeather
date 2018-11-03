package com.wakeel.photoweather.data.user;

import com.wakeel.photoweather.utils.SessionManager;

import io.reactivex.annotations.NonNull;

public class UserRepository implements UserDataSource {
    private static UserRepository INSTANCE = null;
    private static UserDataSource userDataSource;

    private UserRepository(@NonNull UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    public static UserRepository getInstance(UserDataSource weatherDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository(weatherDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void checkUserLogged(SessionManager manager, CheckingCallback checkingCallback) {
        userDataSource.checkUserLogged(manager,checkingCallback);
    }
}
