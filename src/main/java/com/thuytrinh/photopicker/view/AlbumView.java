package com.thuytrinh.photopicker.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thuytrinh.photopicker.R;

public class AlbumView extends RelativeLayout {
  private TextView nameTextView;
  private ImageView thumbnailImageView;

  public AlbumView(Context context) {
    super(context);
    initLayout();
  }

  public TextView getNameTextView() {
    return nameTextView;
  }

  public ImageView getThumbnailImageView() {
    return thumbnailImageView;
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    // Make it square.
    super.onMeasure(widthMeasureSpec, widthMeasureSpec);
  }

  private void initLayout() {
    LayoutInflater.from(getContext()).inflate(R.layout.view_album, this, true);

    nameTextView = (TextView) findViewById(R.id.nameTextView);
    thumbnailImageView = (ImageView) findViewById(R.id.thumbnailImageView);
  }
}