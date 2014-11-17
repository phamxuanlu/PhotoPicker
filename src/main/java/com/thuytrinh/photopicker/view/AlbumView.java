package com.thuytrinh.photopicker.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thuytrinh.photopicker.R;

public class AlbumView extends RelativeLayout {
  private TextView nameView;
  private ImageView thumbnailView;

  public AlbumView(Context context) {
    super(context);
    initLayout();
  }

  public TextView getNameView() {
    return nameView;
  }

  public ImageView getThumbnailView() {
    return thumbnailView;
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    // Make it square.
    super.onMeasure(widthMeasureSpec, widthMeasureSpec);
  }

  private void initLayout() {
    LayoutInflater.from(getContext()).inflate(R.layout.view_album, this, true);

    nameView = (TextView) findViewById(R.id.nameView);
    thumbnailView = (ImageView) findViewById(R.id.thumbnailView);
  }
}
