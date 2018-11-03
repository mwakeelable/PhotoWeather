package com.wakeel.photoweather.data.gallery;

public class GalleryLocalDataSource implements GalleryDataSource {
    private static GalleryDataSource INSTANCE;

    private GalleryLocalDataSource() {

    }

    public static GalleryDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GalleryLocalDataSource();
        }
        return INSTANCE;
    }
}
