package com.thuytrinh.photopicker.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.thuytrinh.photopicker.R;

public class PhotoItemLayout extends RelativeLayout implements Checkable {

  private ImageView mPhotoView;
  private ImageView mCheckMarkView;

  private boolean mChecked;

  public PhotoItemLayout(Context context) {
    super(context);
    initLayout();
  }

  public ImageView getCheckMarkView() {
    return mCheckMarkView;
  }

  public ImageView getPhotoView() {
    return mPhotoView;
  }

  @Override
  public boolean isChecked() {
    return mChecked;
  }

  @Override
  public void setChecked(boolean checked) {
    mChecked = checked;

    int checkMarkVisibility = mChecked ? VISIBLE : GONE;
    mCheckMarkView.setVisibility(checkMarkVisibility);
  }

  @Override
  public void toggle() {
    setChecked(!mChecked);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    // Make it square.
    super.onMeasure(widthMeasureSpec, widthMeasureSpec);
  }

  private void initLayout() {
    LayoutInflater.from(getContext()).inflate(R.layout.view_photo_item, this, true);

    mPhotoView = (ImageView) findViewById(R.id.photoView);
    mCheckMarkView = (ImageView) findViewById(R.id.checkMarkView);

    setChecked(false);
  }
}