package com.thuytrinh.photopicker.controller.fragment;

import android.app.ActionBar;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.thuytrinh.photopicker.R;
import com.thuytrinh.photopicker.controller.SimpleLoaderListener;
import com.thuytrinh.photopicker.controller.adapter.AlbumListAdapter;
import com.thuytrinh.photopicker.controller.loader.AlbumListLoader;
import com.thuytrinh.photopicker.module.ObjectLocator;

import javax.inject.Inject;
import javax.inject.Provider;

import rx.subjects.PublishSubject;

public class AlbumListFragment extends BaseFragment {

  @Inject AlbumListAdapter mAlbumListAdapter;
  @Inject Provider<AlbumListLoader> mAlbumListLoaderProvider;

  private PublishSubject<Long> mWhenAlbumSelected;

  public AlbumListFragment() {
    mWhenAlbumSelected = PublishSubject.create();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setLayoutId(R.layout.fragment_album_list);

    ObjectLocator.getGraph(getActivity().getApplicationContext())
        .inject(this);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    getLoaderManager().initLoader(0, null, new SimpleLoaderListener<Cursor>() {

      @Override
      public Loader<Cursor> onCreateLoader() {
        return mAlbumListLoaderProvider.get();
      }

      @Override
      public void onLoadFinished(Cursor data) {
        mAlbumListAdapter.swapCursor(data);
      }

      @Override
      public void onLoaderReset() {
        mAlbumListAdapter.swapCursor(null);
      }
    });

    ActionBar actionBar = getActivity().getActionBar();
    if (actionBar != null) {
      actionBar.setTitle(R.string.albums);
    }
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    GridView albumGridView = (GridView) view.findViewById(R.id.albumGridView);
    albumGridView.setAdapter(mAlbumListAdapter);
    albumGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long albumId = mAlbumListAdapter.getItemId(position);

        // Emit selected album to subscribers.
        mWhenAlbumSelected.onNext(albumId);
      }
    });
  }

  public PublishSubject<Long> whenAlbumSelected() {
    return mWhenAlbumSelected;
  }
}