package com.thuytrinh.photopicker.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.thuytrinh.photopicker.R;

public class PhotoItemLayout extends RelativeLayout implements Checkable {
  private ImageView photoView;
  private ImageView checkMarkView;

  private boolean isChecked;

  public PhotoItemLayout(Context context) {
    super(context);
    initLayout();
  }

  public ImageView getCheckMarkView() {
    return checkMarkView;
  }

  public ImageView getPhotoView() {
    return photoView;
  }

  @Override
  public boolean isChecked() {
    return isChecked;
  }

  @Override
  public void setChecked(boolean checked) {
    isChecked = checked;

    int checkMarkVisibility = isChecked ? VISIBLE : GONE;
    checkMarkView.setVisibility(checkMarkVisibility);
  }

  @Override
  public void toggle() {
    setChecked(!isChecked);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    // Make it square.
    super.onMeasure(widthMeasureSpec, widthMeasureSpec);
  }

  private void initLayout() {
    LayoutInflater.from(getContext()).inflate(R.layout.view_photo_item, this, true);

    photoView = (ImageView) findViewById(R.id.photoView);
    checkMarkView = (ImageView) findViewById(R.id.checkMarkView);

    setChecked(false);
  }
}
