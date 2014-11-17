package com.thuytrinh.photopicker.controller.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.squareup.picasso.Picasso;
import com.thuytrinh.photopicker.R;
import com.thuytrinh.photopicker.controller.ImageCursorMapper;
import com.thuytrinh.photopicker.view.PhotoItemLayout;

import java.io.File;

import javax.inject.Inject;

public class PhotosAdapter extends CursorAdapter {
  private ImageCursorMapper imageCursorMapper;
  private Picasso picasso;
  private SparseArray<Long> checkedItems;

  @Inject
  public PhotosAdapter(Context context,
                       ImageCursorMapper imageCursorMapper,
                       Picasso picasso) {
    super(context, null, 0);

    this.imageCursorMapper = imageCursorMapper;
    this.picasso = picasso;
  }

  @Override
  public Object getItem(int position) {
    super.getItem(position);
    return imageCursorMapper.toPhoto();
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    return new PhotoItemLayout(context);
  }

  @Override
  public void bindView(View view, Context context, Cursor cursor) {
    PhotoItemLayout itemView = (PhotoItemLayout) view;

    int position = cursor.getPosition();
    itemView.setChecked(checkedItems.get(position) != null);

    File photoFile = imageCursorMapper.getDataFile();
    picasso.load(photoFile)
        .resize(200, 200)
        .placeholder(R.color.placeholder)
        .centerCrop()
        .into(itemView.getPhotoView());
  }

  @Override
  public Cursor swapCursor(Cursor newCursor) {
    imageCursorMapper.setCursor(newCursor);
    return super.swapCursor(newCursor);
  }

  public void setCheckedItems(SparseArray<Long> checkedItems) {
    this.checkedItems = checkedItems;
  }
}