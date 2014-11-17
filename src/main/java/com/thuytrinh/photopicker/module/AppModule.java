package com.thuytrinh.photopicker.module;

import android.content.Context;

import com.squareup.picasso.Picasso;
import com.thuytrinh.photopicker.controller.ImageCursorMapper;
import com.thuytrinh.photopicker.controller.adapter.AlbumsAdapter;
import com.thuytrinh.photopicker.controller.adapter.PhotosAdapter;
import com.thuytrinh.photopicker.controller.fragment.AlbumsFragment;
import com.thuytrinh.photopicker.controller.fragment.PhotosFragment;
import com.thuytrinh.photopicker.controller.loader.AlbumsLoader;
import com.thuytrinh.photopicker.controller.loader.PhotosLoader;

import dagger.Module;
import dagger.Provides;

@Module(
    injects = {
        AlbumsFragment.class,
        AlbumsLoader.class,
        AlbumsAdapter.class,
        PhotosFragment.class,
        PhotosAdapter.class
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
  AlbumsLoader provideAlbumsLoader(Context context) {
    return new AlbumsLoader(context);
  }

  @Provides
  ImageCursorMapper provideImageCursorMapper() {
    return new ImageCursorMapper();
  }

  @Provides
  AlbumsAdapter provideAlbumsAdapter(Context context,
                                     ImageCursorMapper imageCursorMapper,
                                     Picasso picasso) {
    return new AlbumsAdapter(context, imageCursorMapper, picasso);
  }

  @Provides
  PhotosLoader providePhotosLoader(Context context) {
    return new PhotosLoader(context);
  }

  @Provides
  Picasso providePicasso(Context context) {
    return Picasso.with(context);
  }

  @Provides
  PhotosAdapter providePhotosAdapter(Context context,
                                     ImageCursorMapper imageCursorMapper,
                                     Picasso picasso) {
    return new PhotosAdapter(context, imageCursorMapper, picasso);
  }
}
