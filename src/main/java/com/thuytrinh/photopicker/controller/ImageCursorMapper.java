package com.thuytrinh.photopicker.controller;

import android.database.Cursor;
import android.util.SparseArray;

import com.thuytrinh.photopicker.model.Photo;

import java.io.File;

import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.Images.Media.BUCKET_DISPLAY_NAME;
import static android.provider.MediaStore.Images.Media.BUCKET_ID;
import static android.provider.MediaStore.Images.Media.DATA;

public class ImageCursorMapper {
  private final SparseArray<File> dataFileCache = new SparseArray<>();

  private int idIndex = -1;
  private int bucketIdIndex = -1;
  private int bucketDisplayNameIndex = -1;
  private int dataIndex = -1;

  private Cursor cursor;

  public ImageCursorMapper() {
    this(null);
  }

  public ImageCursorMapper(Cursor cursor) {
    this.cursor = cursor;
  }

  public void setCursor(Cursor cursor) {
    this.cursor = cursor;
    dataFileCache.clear();
  }

  public long getId() {
    if (idIndex == -1) {
      idIndex = cursor.getColumnIndex(_ID);
    }

    return cursor.getLong(idIndex);
  }

  public long getBucketId() {
    if (bucketIdIndex == -1) {
      bucketIdIndex = cursor.getColumnIndex(BUCKET_ID);
    }

    return cursor.getLong(bucketIdIndex);
  }

  public String getBucketDisplayName() {
    if (bucketDisplayNameIndex == -1) {
      bucketDisplayNameIndex = cursor.getColumnIndex(BUCKET_DISPLAY_NAME);
    }

    return cursor.getString(bucketDisplayNameIndex);
  }

  public String getData() {
    if (dataIndex == -1) {
      dataIndex = cursor.getColumnIndex(DATA);
    }

    return cursor.getString(dataIndex);
  }

  public File getDataFile() {
    int position = cursor.getPosition();
    File dataFile = dataFileCache.get(position);
    if (dataFile == null) {
      dataFile = new File(getData());
      dataFileCache.put(position, dataFile);
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
