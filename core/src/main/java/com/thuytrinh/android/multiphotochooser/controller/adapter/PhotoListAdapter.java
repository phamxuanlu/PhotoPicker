package com.thuytrinh.android.multiphotochooser.controller.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.squareup.picasso.Picasso;
import com.thuytrinh.android.multiphotochooser.R;
import com.thuytrinh.android.multiphotochooser.controller.ImageCursorMapper;
import com.thuytrinh.android.multiphotochooser.view.PhotoItemLayout;

import java.io.File;

import javax.inject.Inject;

public class PhotoListAdapter extends CursorAdapter {

  private ImageCursorMapper mImageCursorMapper;
  private Picasso mPicasso;

  @Inject
  public PhotoListAdapter(Context context,
      ImageCursorMapper imageCursorMapper,
      Picasso picasso) {
    super(context, null, 0);

    mImageCursorMapper = imageCursorMapper;
    mPicasso = picasso;
  }

  @Override
  public Object getItem(int position) {
    super.getItem(position);
    return mImageCursorMapper.toPhoto();
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    return new PhotoItemLayout(context);
  }

  @Override
  public void bindView(View view, Context context, Cursor cursor) {
    PhotoItemLayout itemView = (PhotoItemLayout) view;

    File photoFile = mImageCursorMapper.getDataFile();
    mPicasso.load(photoFile)
        .resize(200, 200)
        .placeholder(R.color.placeholder)
        .centerCrop()
        .into(itemView.getPhotoView());
  }

  @Override
  public Cursor swapCursor(Cursor newCursor) {
    mImageCursorMapper.setCursor(newCursor);
    return super.swapCursor(newCursor);
  }
}