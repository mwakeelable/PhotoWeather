package com.wakeel.photoweather.model;

import android.content.Intent;

public class Image {
    private Intent intent;
    private int resultCode;

    public Image(Intent intent, int resultCode) {
        this.intent = intent;
        this.resultCode = resultCode;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}
