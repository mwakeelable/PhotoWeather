package com.wakeel.photoweather.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.wakeel.photoweather.model.Image;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MyImagePicker {
    private Context context = null;
    private String selectionMode = Constants.CAMERA;
    private Subscription cameraSubscription = null;
    private ImageView imageView = null;
    private OnOperationResult onOperationResult = null;
    private String choosenOption = Constants.CAMERA;
    private Bitmap imageCaptured;

    public MyImagePicker(Context context) {
        this.context = context;
    }

    public void setSelectionMode(String selectionMode) {
        this.selectionMode = selectionMode;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setOnOperationResult(OnOperationResult onOperationResult) {
        this.onOperationResult = onOperationResult;
    }

    public void chooseImage() {
        switch (selectionMode) {
            case Constants.CAMERA:
                choosenOption = Constants.CAMERA;
                openCamera();
                break;
            default:
                choosenOption = Constants.CAMERA;
                openCamera();
        }
    }

    private void openCamera() {
        subscribeForCamera();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ((AppCompatActivity) context).startActivityForResult(intent, Constants.CAMERA_REQ_CODE);
    }

    private void subscribeForCamera() {
        cameraSubscription = RxEventBus.getEventBus().getObservables()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o != null) processImage(o);
                    }
                });
    }

    private void processImage(Object o) {
        if (o instanceof Image) {
            Image image = (Image) o;
            if (image.getResultCode() == AppCompatActivity.RESULT_OK) {
                if (choosenOption.equals(Constants.CAMERA)) {
                    processCameraResult(image);
                }
            }
        }
        if (choosenOption.equalsIgnoreCase(Constants.CAMERA)) {
            unSubscribeCamera();
        }
    }

    private void processCameraResult(Image image) {
        Bitmap bitmap = (Bitmap) image.getIntent().getExtras().get("data");
        imageCaptured = bitmap;
        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
        }
        if (onOperationResult != null) {
            onOperationResult.onOperationResult(image);
        }
    }

    public Bitmap getImageCaptured() {
        return imageCaptured;
    }

    private void unSubscribeCamera() {
        if (cameraSubscription != null && !cameraSubscription.isUnsubscribed()) {
            cameraSubscription.unsubscribe();
        }
    }
}
