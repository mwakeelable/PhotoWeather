package com.wakeel.photoweather.home;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.wakeel.photoweather.R;
import com.wakeel.photoweather.model.Image;
import com.wakeel.photoweather.model.WeatherResponse;
import com.wakeel.photoweather.utils.Constants;
import com.wakeel.photoweather.utils.FactoryInjection;
import com.wakeel.photoweather.utils.GPSTracker;
import com.wakeel.photoweather.utils.MyImagePicker;
import com.wakeel.photoweather.utils.OnOperationResult;
import com.wakeel.photoweather.utils.UnitConverter;

public class HomeFragment extends Fragment implements HomeContract.HomeView, View.OnClickListener {
    private View mView;
    GPSTracker gps;
    HomePresenter homePresenter;
    ProgressDialog progressDialog;
    MyImagePicker myImagePicker;
    ShareDialog shareDialog;
    CallbackManager callbackManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        homePresenter = new HomePresenter(FactoryInjection.provideWeatherRepository(), this);
        find_views(mView);
        return mView;
    }

    private ImageView imageCaptured;
    private Button openCamera;
    private ImageButton shareImage;
    RelativeLayout imageContainer;
    TextView cityTxt, tempTxt, conditionTxt;

    private void find_views(View mView) {
        myImagePicker = new MyImagePicker(getActivity());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        imageCaptured = mView.findViewById(R.id.image_captured);
        openCamera = mView.findViewById(R.id.btn_open_camera);
        shareImage = mView.findViewById(R.id.btn_share);
        imageContainer = mView.findViewById(R.id.image_container);
        imageContainer.setVisibility(View.GONE);
        cityTxt = mView.findViewById(R.id.city_txt);
        tempTxt = mView.findViewById(R.id.temp_txt);
        conditionTxt = mView.findViewById(R.id.condition_txt);
        openCamera.setOnClickListener(this);
        shareImage.setOnClickListener(this);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // this part is optional
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open_camera:
                if (checkCameraPermission()) {
                    myImagePicker.setImageView(imageCaptured);
                    myImagePicker.setSelectionMode(Constants.CAMERA);
                    myImagePicker.setOnOperationResult(new OnOperationResult() {
                        @Override
                        public void onOperationResult(Image pojo) {
                            imageContainer.setVisibility(View.VISIBLE);
                            if (checkLocationPermission()) {
                                gps = new GPSTracker(getActivity());
                                if (gps.canGetLocation()) {
                                    double latitude = gps.getLatitude();
                                    double longitude = gps.getLongitude();
                                    //call weather web service
                                    homePresenter.getWeatherByCity(latitude, longitude);
                                } else {
                                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                requestLocationAccess();
                            }
                        }
                    });
                    myImagePicker.chooseImage();
                } else {
                    requestCameraPermission();
                }
                break;
            case R.id.btn_share:
                progressDialog.show();
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(myImagePicker.getImageCaptured())
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                ShareDialog.show(getActivity(), content);
        }
    }

    @Override
    public void onGetWeatherSuccess(WeatherResponse response) {
        cityTxt.setText(response.getName());
        float temp = (float) (response.getMain().getTemp());
        Float celsiusTemp = UnitConverter.convertTemperatureToCelsius(temp);
        tempTxt.setText(String.valueOf(celsiusTemp));
        conditionTxt.setText(response.getWeather().get(0).getDescription());
    }

    @Override
    public void onGetWeatherFailed(String message) {
        cityTxt.setText(message);
    }

    @Override
    public void setPresenter(HomeContract.HomePresenter presenter) {
        this.homePresenter = (HomePresenter) presenter;
    }

    @Override
    public void setLoadingIndicator(boolean showLoading) {
        if (showLoading) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        progressDialog.dismiss();
    }

    private static final int PERMISSION_CAMERA_REQUEST_CODE = 200;
    public static final int PERMISSION_LOCATION_REQUEST_CODE = 100;

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CAMERA_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    myImagePicker.setImageView(imageCaptured);
                    myImagePicker.setSelectionMode(Constants.CAMERA);
                    myImagePicker.setOnOperationResult(new OnOperationResult() {
                        @Override
                        public void onOperationResult(Image pojo) {
                            imageContainer.setVisibility(View.VISIBLE);
                            if (checkLocationPermission()) {
                                gps = new GPSTracker(getActivity());
                                if (gps.canGetLocation()) {
                                    double latitude = gps.getLatitude();
                                    double longitude = gps.getLongitude();
                                    Toast.makeText(getActivity(),
                                            "Your Location is - \nLat: " + latitude + "\nLong: " +
                                                    longitude, Toast.LENGTH_LONG).show();
                                    //call weather web service
                                    homePresenter.getWeatherByCity(latitude, longitude);
                                } else {
                                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                requestLocationAccess();
                            }
                        }
                    });
                    myImagePicker.chooseImage();
                } else {
                    Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestCameraPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
            case PERMISSION_LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        gps = new GPSTracker(getActivity());
                        if (gps.canGetLocation()) {
                            double latitude = gps.getLatitude();
                            double longitude = gps.getLongitude();
                            //call weather web service
                            homePresenter.getWeatherByCity(latitude, longitude);
                        } else {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestLocationAccess();
                                            }
                                        }
                                    });
                        }
                    }
                }
                return;
            }

        }
    }

    private void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA},
                PERMISSION_CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestLocationAccess() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_LOCATION_REQUEST_CODE);
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
