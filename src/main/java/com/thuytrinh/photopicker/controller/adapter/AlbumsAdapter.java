package com.thuytrinh.photopicker.controller.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.squareup.picasso.Picasso;
import com.thuytrinh.photopicker.R;
import com.thuytrinh.photopicker.controller.ImageCursorMapper;
import com.thuytrinh.photopicker.view.AlbumView;

import java.io.File;

import javax.inject.Inject;

public class AlbumsAdapter extends CursorAdapter {
  private ImageCursorMapper imageCursorMapper;
  private Picasso picasso;

  @Inject
  public AlbumsAdapter(Context context,
                       ImageCursorMapper imageCursorMapper,
                       Picasso picasso) {
    super(context, null, 0);

    this.imageCursorMapper = imageCursorMapper;
    this.picasso = picasso;
  }

  @Override
  public long getItemId(int position) {
    getItem(position);
    return imageCursorMapper.getBucketId();
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    return new AlbumView(context);
  }

  @Override
  public void bindView(View view, Context context, Cursor cursor) {
    AlbumView albumView = (AlbumView) view;

    String albumName = imageCursorMapper.getBucketDisplayName();
    albumView.getNameView().setText(albumName);

    File photoFile = imageCursorMapper.getDataFile();
    picasso.load(photoFile)
        .resize(200, 200)
        .placeholder(R.color.placeholder)
        .centerCrop()
        .into(albumView.getThumbnailView());
  }

  @Override
  public Cursor swapCursor(Cursor newCursor) {
    imageCursorMapper.setCursor(newCursor);
    return super.swapCursor(newCursor);
  }
}
