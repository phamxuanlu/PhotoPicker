package com.thuytrinh.android.multiphotochooser.controller;

import android.database.Cursor;
import android.util.SparseArray;

import com.thuytrinh.android.multiphotochooser.model.Photo;

import java.io.File;

import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.Images.Media.BUCKET_DISPLAY_NAME;
import static android.provider.MediaStore.Images.Media.BUCKET_ID;
import static android.provider.MediaStore.Images.Media.DATA;

public class ImageCursorMapper {

  private final SparseArray<File> mDataFileCache = new SparseArray<>();

  private int mIdIndex = -1;
  private int mBucketIdIndex = -1;
  private int mBucketDisplayNameIndex = -1;
  private int mDataIndex = -1;

  private Cursor mCursor;

  public ImageCursorMapper() {
    this(null);
  }

  public ImageCursorMapper(Cursor cursor) {
    mCursor = cursor;
  }

  public void setCursor(Cursor cursor) {
    mCursor = cursor;
    mDataFileCache.clear();
  }

  public long getId() {
    if (mIdIndex == -1) {
      mIdIndex = mCursor.getColumnIndex(_ID);
    }

    return mCursor.getLong(mIdIndex);
  }

  public long getBucketId() {
    if (mBucketIdIndex == -1) {
      mBucketIdIndex = mCursor.getColumnIndex(BUCKET_ID);
    }

    return mCursor.getLong(mBucketIdIndex);
  }

  public String getBucketDisplayName() {
    if (mBucketDisplayNameIndex == -1) {
      mBucketDisplayNameIndex = mCursor.getColumnIndex(BUCKET_DISPLAY_NAME);
    }

    return mCursor.getString(mBucketDisplayNameIndex);
  }

  public String getData() {
    if (mDataIndex == -1) {
      mDataIndex = mCursor.getColumnIndex(DATA);
    }

    return mCursor.getString(mDataIndex);
  }

  public File getDataFile() {
    int position = mCursor.getPosition();
    File dataFile = mDataFileCache.get(position);
    if (dataFile == null) {
      dataFile = new File(getData());
      mDataFileCache.put(position, dataFile);
    }

    return dataFile;
  }

  public Photo toPhoto() {
    Photo photo = new Photo();
    photo.setId(getId());
    photo.setAlbumId(getBucketId());
    photo.setPath(getData());
    return photo;
  }
}