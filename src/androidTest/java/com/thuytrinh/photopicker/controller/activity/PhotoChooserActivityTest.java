package com.thuytrinh.photopicker.controller.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.thuytrinh.photopicker.controller.fragment.AlbumsFragment;
import com.thuytrinh.photopicker.controller.loader.AlbumsLoader;
import com.thuytrinh.photopicker.module.AppModule;
import com.thuytrinh.photopicker.module.ObjectLocator;

import java.util.concurrent.CountDownLatch;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;

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
    Context appContext = mTargetContext.getApplicationContext();
    assertNotNull(appContext);

    ObjectLocator.setMockGraph(ObjectGraph.create(
        new AppModule(appContext),
        new MockAppModule()
    ));

    // Launch the Activity.
    Intent mockIntent = PhotoChooserActivity.newIntent(mTargetContext);
    setActivityIntent(mockIntent);
    mActivity = getActivity();
  }

  @Module(
      injects = {AlbumsFragment.class},
      overrides = true,
      complete = false
  )
  public class MockAppModule {
    @Provides
    AlbumsLoader provideAlbumsLoader(Context context) {
      return new AlbumsLoader(context) {
        @Override
        protected Cursor onLoadInBackground() {
          Log.w("AwesomePicker", "Yes, I did it!");
          return super.onLoadInBackground();
        }
      };
    }
  }
}
