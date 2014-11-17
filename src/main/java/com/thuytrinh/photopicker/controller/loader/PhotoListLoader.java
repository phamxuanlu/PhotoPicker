package com.thuytrinh.photopicker.controller.loader;

import android.content.Context;
import android.content.CursorLoader;
import android.provider.MediaStore;

import javax.inject.Inject;

public class PhotoListLoader extends CursorLoader {

  @Inject
  public PhotoListLoader(Context context) {
    super(context);

    setUri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    setProjection(new String[] {
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.BUCKET_ID,
        MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
        MediaStore.Images.Media.DATA
    });
  }

  public void setAlbumId(long albumId) {
    String albumIdArg = Long.toString(albumId);
    setSelection(MediaStore.Images.Media.BUCKET_ID + " = ?");
    setSelectionArgs(new String[] {albumIdArg});
  }
}