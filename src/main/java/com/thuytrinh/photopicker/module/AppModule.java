package com.thuytrinh.photopicker.module;

import android.content.Context;

import com.squareup.picasso.Picasso;
import com.thuytrinh.photopicker.controller.ImageCursorMapper;
import com.thuytrinh.photopicker.controller.adapter.AlbumListAdapter;
import com.thuytrinh.photopicker.controller.adapter.PhotoListAdapter;
import com.thuytrinh.photopicker.controller.fragment.AlbumListFragment;
import com.thuytrinh.photopicker.controller.fragment.PhotoListFragment;
import com.thuytrinh.photopicker.controller.loader.AlbumListLoader;
import com.thuytrinh.photopicker.controller.loader.PhotoListLoader;

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
  private Context context;

  public AppModule(Context context) {
    this.context = context;
  }

  @Provides
  Context provideContext() {
    return context;
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
