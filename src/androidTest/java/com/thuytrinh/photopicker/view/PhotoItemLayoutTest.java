package com.thuytrinh.photopicker.view;

import android.test.AndroidTestCase;

import static org.assertj.android.api.Assertions.assertThat;

public class PhotoItemLayoutTest extends AndroidTestCase {

  public void testShouldInitializeLayoutProperly() {
    PhotoItemLayout itemLayout = new PhotoItemLayout(getContext());

    assertThat(itemLayout).hasChildCount(2);
    assertNotNull(itemLayout.getPhotoView());
    assertNotNull(itemLayout.getCheckMarkView());
    assertThat(itemLayout.getCheckMarkView()).isGone();
  }

  public void testShouldSetCheckedProperly() {
    PhotoItemLayout itemLayout = new PhotoItemLayout(getContext());

    itemLayout.setChecked(false);
    assertThat(itemLayout.getCheckMarkView()).isGone();

    itemLayout.setChecked(true);
    assertThat(itemLayout.getCheckMarkView()).isVisible();
  }

  public void testShouldToggleProperly() {
    PhotoItemLayout itemLayout = new PhotoItemLayout(getContext());

    itemLayout.toggle();
    assertThat(itemLayout.getCheckMarkView()).isVisible();

    itemLayout.toggle();
    assertThat(itemLayout.getCheckMarkView()).isGone();
  }
}