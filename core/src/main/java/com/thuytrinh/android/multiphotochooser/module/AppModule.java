package com.thuytrinh.android.multiphotochooser.module;

import android.content.Context;

import com.squareup.picasso.Picasso;
import com.thuytrinh.android.multiphotochooser.controller.ImageCursorMapper;
import com.thuytrinh.android.multiphotochooser.controller.adapter.AlbumListAdapter;
import com.thuytrinh.android.multiphotochooser.controller.adapter.PhotoListAdapter;
import com.thuytrinh.android.multiphotochooser.controller.fragment.AlbumListFragment;
import com.thuytrinh.android.multiphotochooser.controller.fragment.PhotoListFragment;
import com.thuytrinh.android.multiphotochooser.controller.loader.AlbumListLoader;
import com.thuytrinh.android.multiphotochooser.controller.loader.PhotoListLoader;

import dagger.Module;
import dagger.Provides;

@Module(
    injects = {
        AlbumListFragment.class,
        AlbumListLoader.class,
        AlbumListAdapter.class,
        PhotoListFragment.class,
        PhotoListAdapter.class
    },
    library = true
)
public class AppModule {

  private Context mContext;

  public AppModule(Context context) {
    mContext = context;
  }

  @Provides
  Context provideContext() {
    return mContext;
  }

  @Provides
  AlbumListLoader provideAlbumListLoader(Context context) {
    return new AlbumListLoader(context);
  }

  @Provides
  ImageCursorMapper provideImageCursorMapper() {
    return new ImageCursorMapper();
  }

  @Provides
  AlbumListAdapter provideAlbumListAdapter(Context context,
                                           ImageCursorMapper imageCursorMapper,
                                           Picasso picasso) {
    return new AlbumListAdapter(context, imageCursorMapper, picasso);
  }

  @Provides
  PhotoListLoader providePhotoListLoader(Context context) {
    return new PhotoListLoader(context);
  }

  @Provides
  Picasso providePicasso(Context context) {
    return Picasso.with(context);
  }

  @Provides
  PhotoListAdapter providePhotoListAdapter(Context context,
                                           ImageCursorMapper imageCursorMapper,
                                           Picasso picasso) {
    return new PhotoListAdapter(context, imageCursorMapper, picasso);
  }
}