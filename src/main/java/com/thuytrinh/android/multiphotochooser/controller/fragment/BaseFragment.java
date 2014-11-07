package com.thuytrinh.android.multiphotochooser.controller.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment {

  private int mLayoutId;

  public void setLayoutId(@LayoutRes int layoutId) {
    mLayoutId = layoutId;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(mLayoutId, container, false);
  }
}