package com.thuytrinh.photopicker.model;

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

  private long id;
  private long albumId;
  private String path;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public long getAlbumId() {
    return albumId;
  }

  public void setAlbumId(long albumId) {
    this.albumId = albumId;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(id);
    dest.writeLong(albumId);
    dest.writeString(path);
  }
}
