package com.thuytrinh.photopicker.controller.loader;

import android.provider.MediaStore;
import android.test.AndroidTestCase;

import org.assertj.android.api.Assertions;

public class PhotosLoaderTest extends AndroidTestCase {
  public void testShouldInitializeProperly() {
    PhotosLoader photosLoader = new PhotosLoader(getContext());
    long mockAlbumId = 12345L;
    photosLoader.setAlbumId(mockAlbumId);

    Assertions.assertThat(photosLoader)
        .hasProjection(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATA)
        .hasUri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        .hasSelection(MediaStore.Images.Media.BUCKET_ID + " = ?")
        .hasSelectionArgs(Long.toString(mockAlbumId));
  }
}