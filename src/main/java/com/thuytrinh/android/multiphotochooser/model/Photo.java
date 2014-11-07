package com.thuytrinh.android.multiphotochooser.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Photo implements Parcelable {

  public static final Creator<Photo> CREATOR = new Creator<Photo>() {

    @Override
    public Photo createFromParcel(Parcel source) {
      Photo photo = new Photo();
      photo.setId(source.readLong());
      photo.setAlbumId(source.readLong());
      photo.setPath(source.readString());
      return photo;
    }

    @Override
    public Photo[] newArray(int size) {
      return new Photo[size];
    }
  };

  private long mId;
  private long mAlbumId;
  private String mPath;

  public long getId() {
    return mId;
  }

  public void setId(long id) {
    mId = id;
  }

  public String getPath() {
    return mPath;
  }

  public void setPath(String path) {
    mPath = path;
  }

  public long getAlbumId() {
    return mAlbumId;
  }

  public void setAlbumId(long albumId) {
    mAlbumId = albumId;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(mId);
    dest.writeLong(mAlbumId);
    dest.writeString(mPath);
  }
}