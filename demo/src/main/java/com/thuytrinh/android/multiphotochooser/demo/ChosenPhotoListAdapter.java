package com.thuytrinh.android.multiphotochooser.demo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.thuytrinh.android.multiphotochooser.model.Photo;

import java.io.File;
import java.util.List;

public class ChosenPhotoListAdapter extends BaseAdapter {

  private List<Photo> mChosenPhotoList;
  private Context mContext;

  public ChosenPhotoListAdapter(Context context, List<Photo> chosenPhotoList) {
    mContext = context;
    mChosenPhotoList = chosenPhotoList;
  }

  @Override
  public int getCount() {
    return mChosenPhotoList.size();
  }

  @Override
  public Object getItem(int position) {
    return null;
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ImageView photoView = new ImageView(parent.getContext());

    Photo photo = mChosenPhotoList.get(position);
    File photoFile = new File(photo.getPath());
    Picasso.with(mContext)
        .load(photoFile)
        .resize(400, 400)
        .centerCrop()
        .placeholder(R.color.placeholder)
        .into(photoView);

    return photoView;
  }
}