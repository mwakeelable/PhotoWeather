package com.wakeel.photoweather.data.gallery;

public class GalleryRepository implements GalleryDataSource {
    private static GalleryRepository INSTANCE = null;
    private static GalleryDataSource galleryDataSource;

    private GalleryRepository(GalleryDataSource galleryDataSource) {
        this.galleryDataSource = galleryDataSource;
    }

    public static GalleryRepository getInstance(GalleryDataSource galleryDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new GalleryRepository(galleryDataSource);
        }
        return INSTANCE;
    }

}
