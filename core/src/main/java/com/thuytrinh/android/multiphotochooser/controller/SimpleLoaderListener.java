package com.thuytrinh.android.multiphotochooser.controller;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

public abstract class SimpleLoaderListener<D> implements LoaderManager.LoaderCallbacks<D> {

  @Override
  public Loader<D> onCreateLoader(int id, Bundle args) {
    return onCreateLoader();
  }

  @Override
  public void onLoadFinished(Loader<D> loader, D data) {
    onLoadFinished(data);
  }

  @Override
  public void onLoaderReset(Loader<D> loader) {
    onLoaderReset();
  }

  public abstract Loader<D> onCreateLoader();

  public void onLoadFinished(D data) { }

  public void onLoaderReset() { }
}