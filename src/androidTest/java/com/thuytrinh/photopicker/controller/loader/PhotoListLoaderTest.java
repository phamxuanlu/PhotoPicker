package com.thuytrinh.photopicker.controller.loader;

import android.provider.MediaStore;
import android.test.AndroidTestCase;

import org.assertj.android.api.Assertions;

public class PhotoListLoaderTest extends AndroidTestCase {
  public void testShouldInitializeProperly() {
    PhotoListLoader photoListLoader = new PhotoListLoader(getContext());
    long mockAlbumId = 12345L;
    photoListLoader.setAlbumId(mockAlbumId);

    Assertions.assertThat(photoListLoader)
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