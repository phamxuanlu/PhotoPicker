package com.thuytrinh.android.multiphotochooser.controller.loader;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.provider.MediaStore;

import com.thuytrinh.android.multiphotochooser.controller.GroupByBucketIdFunc;

import javax.inject.Inject;

public class AlbumListLoader extends CursorLoader {

  @Inject
  public AlbumListLoader(Context context) {
    super(context);

    setUri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    setProjection(new String[] {
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.BUCKET_ID,
        MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
        MediaStore.Images.Media.DATA
    });
  }

  @Override
  public Cursor loadInBackground() {
    Cursor imageCursor = super.loadInBackground();
    if (imageCursor != null && imageCursor.getCount() > 0) {
      Cursor bucketCursor = new GroupByBucketIdFunc().call(imageCursor);
      imageCursor.close();
      return bucketCursor;
    } else {
      return imageCursor;
    }
  }
}