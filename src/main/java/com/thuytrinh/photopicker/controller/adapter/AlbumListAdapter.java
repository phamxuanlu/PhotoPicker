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

public class AlbumListAdapter extends CursorAdapter {

  private ImageCursorMapper mImageCursorMapper;
  private Picasso mPicasso;

  @Inject
  public AlbumListAdapter(Context context,
                          ImageCursorMapper imageCursorMapper,
                          Picasso picasso) {
    super(context, null, 0);

    mImageCursorMapper = imageCursorMapper;
    mPicasso = picasso;
  }

  @Override
  public long getItemId(int position) {
    getItem(position);
    return mImageCursorMapper.getBucketId();
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    return new AlbumView(context);
  }

  @Override
  public void bindView(View view, Context context, Cursor cursor) {
    AlbumView albumView = (AlbumView) view;

    String albumName = mImageCursorMapper.getBucketDisplayName();
    albumView.getNameTextView().setText(albumName);

    File photoFile = mImageCursorMapper.getDataFile();
    mPicasso.load(photoFile)
        .resize(200, 200)
        .placeholder(R.color.placeholder)
        .centerCrop()
        .into(albumView.getThumbnailImageView());
  }

  @Override
  public Cursor swapCursor(Cursor newCursor) {
    mImageCursorMapper.setCursor(newCursor);
    return super.swapCursor(newCursor);
  }
}