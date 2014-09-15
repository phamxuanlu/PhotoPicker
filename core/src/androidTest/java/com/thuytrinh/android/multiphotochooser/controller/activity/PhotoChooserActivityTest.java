package com.thuytrinh.android.multiphotochooser.controller.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;

public class PhotoChooserActivityTest extends ActivityInstrumentationTestCase2<PhotoChooserActivity> {

  private PhotoChooserActivity mActivity;
  private Context mTargetContext;

  public PhotoChooserActivityTest() {
    super(PhotoChooserActivity.class);
  }

  public void testActivity() throws InterruptedException {
    new CountDownLatch(0).await();
  }

  public void testShouldCreateIntentProperly() {
    Intent intent = PhotoChooserActivity.newIntent(mTargetContext);
    assertNotNull(intent);

    ComponentName component = intent.getComponent();
    assertThat(component.getPackageName())
        .isEqualTo(mTargetContext.getPackageName());
    assertThat(component.getClassName())
        .isEqualTo(PhotoChooserActivity.class.getName());
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    mTargetContext = getInstrumentation().getTargetContext();
    Intent mockIntent = PhotoChooserActivity.newIntent(mTargetContext);
    setActivityIntent(mockIntent);

    mActivity = getActivity();
  }
}