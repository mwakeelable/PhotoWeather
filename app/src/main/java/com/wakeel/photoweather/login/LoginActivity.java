package com.wakeel.photoweather.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.wakeel.photoweather.R;
import com.wakeel.photoweather.base.BaseActivity;
import com.wakeel.photoweather.home.HomeActivity;
import com.wakeel.photoweather.utils.SessionManager;

import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends BaseActivity {
    ArrayList permissions;
    CallbackManager callbackManager;
    private LoginButton fbLoginButton;
    private AccessToken accessToken;
    SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        fbLoginButton = findViewById(R.id.login_button);
        loginUsingFB();
        sessionManager = new SessionManager(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    private void loginUsingFB() {
        permissions = new ArrayList();
        permissions.add("public_profile");
        permissions.add("email");
        permissions.add("user_friends");
        fbLoginButton.setReadPermissions(permissions);
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = loginResult.getAccessToken();
                GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.e("LoginResult", object.toString());
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();

                Profile profile = Profile.getCurrentProfile();
                if (profile != null) {
                    sessionManager.createLoginSession(profile.getName());
                    Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                }

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, error.getMessage());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
