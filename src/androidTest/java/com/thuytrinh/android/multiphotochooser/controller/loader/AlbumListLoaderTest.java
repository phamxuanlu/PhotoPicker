package com.thuytrinh.android.multiphotochooser.controller.loader;

import android.database.Cursor;
import android.provider.MediaStore;
import android.test.AndroidTestCase;

import com.thuytrinh.android.multiphotochooser.module.ObjectLocator;

import org.assertj.android.api.Assertions;

import dagger.ObjectGraph;

import static org.assertj.core.api.Assertions.assertThat;

public class AlbumListLoaderTest extends AndroidTestCase {

  private AlbumListLoader mAlbumListLoader;

  public void testShouldInitializeProperly() {
    Assertions.assertThat(mAlbumListLoader)
        .hasProjection(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATA)
        .hasUri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
  }

  public void testShouldLoadProperly() {
    Cursor cursor = mAlbumListLoader.loadInBackground();

    assertThat(cursor.getCount())
        .isGreaterThan(0);
    Assertions.assertThat(cursor)
        .hasColumns(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATA)
        .isBeforeFirst();
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    ObjectGraph objectGraph = ObjectLocator.getGraph(getContext());
    mAlbumListLoader = objectGraph.get(AlbumListLoader.class);
  }
}