package com.thuytrinh.android.multiphotochooser.controller.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.thuytrinh.android.multiphotochooser.controller.fragment.AlbumListFragment;
import com.thuytrinh.android.multiphotochooser.controller.loader.AlbumListLoader;
import com.thuytrinh.android.multiphotochooser.module.AppModule;
import com.thuytrinh.android.multiphotochooser.module.ObjectGraphContainer;

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
    new CountDownLatch(1).await();
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

    ObjectGraphContainer.setMockObjectGraph(ObjectGraph.create(
        new AppModule(appContext),
        new MockAppModule()
    ));

    // Launch the Activity.
    Intent mockIntent = PhotoChooserActivity.newIntent(mTargetContext);
    setActivityIntent(mockIntent);
    mActivity = getActivity();
  }

  @Module(
      injects = {AlbumListFragment.class},
      overrides = true,
      complete = false
  )
  public class MockAppModule {

    @Provides
    AlbumListLoader provideMockAlbumListLoader(Context context) {
      return new AlbumListLoader(context) {

        @Override
        protected Cursor onLoadInBackground() {
          Log.w("AwesomePicker", "Yes, I did it!");
          return super.onLoadInBackground();
        }
      };
    }
  }
}