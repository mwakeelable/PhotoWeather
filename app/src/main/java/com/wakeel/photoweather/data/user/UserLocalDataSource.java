package com.wakeel.photoweather.data.user;


import com.wakeel.photoweather.utils.SessionManager;

public class UserLocalDataSource implements UserDataSource {
    private static UserDataSource INSTANCE;
    private SessionManager sessionManager;

    private UserLocalDataSource(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public static UserDataSource getInstance(SessionManager sessionManager) {
        if (INSTANCE == null) {
            INSTANCE = new UserLocalDataSource(sessionManager);
        }
        return INSTANCE;
    }

    @Override
    public void checkUserLogged(SessionManager manager, CheckingCallback checkingCallback) {
        if (sessionManager.isLoggedIn()) {
            checkingCallback.userLoggedIn();
        } else {
            checkingCallback.anonymousUser();
        }
    }
}
