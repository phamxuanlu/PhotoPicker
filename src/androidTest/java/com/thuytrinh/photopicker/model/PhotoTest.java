package com.thuytrinh.photopicker.model;

import android.os.Parcel;

import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;

public class PhotoTest extends TestCase {
  public void testShouldParcelProperly() {
    Photo originalPhoto = new Photo();
    originalPhoto.setId(123L);
    originalPhoto.setAlbumId(456L);
    originalPhoto.setPath("Earth/Asia/AwesomePhotoChooser");

    Parcel parcel = Parcel.obtain();
    originalPhoto.writeToParcel(parcel, 0);
    parcel.setDataPosition(0);
    Photo parcelledPhoto = Photo.CREATOR.createFromParcel(parcel);

    assertThat(parcelledPhoto)
        .isNotNull()
        .isEqualToComparingFieldByField(originalPhoto);
  }
}