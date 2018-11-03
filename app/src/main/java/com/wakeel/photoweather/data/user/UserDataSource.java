package com.wakeel.photoweather.data.user;

import com.wakeel.photoweather.utils.SessionManager;

public interface UserDataSource {
    void checkUserLogged(SessionManager manager, CheckingCallback checkingCallback);

    interface CheckingCallback {
        void userLoggedIn();

        void anonymousUser();
    }
}
