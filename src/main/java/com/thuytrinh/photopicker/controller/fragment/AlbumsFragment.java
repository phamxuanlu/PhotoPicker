package com.thuytrinh.photopicker.controller.fragment;

import android.app.ActionBar;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.reactiveviewmodel.core.BaseFragment;
import com.thuytrinh.photopicker.R;
import com.thuytrinh.photopicker.controller.SimpleLoaderListener;
import com.thuytrinh.photopicker.controller.adapter.AlbumsAdapter;
import com.thuytrinh.photopicker.controller.loader.AlbumsLoader;
import com.thuytrinh.photopicker.module.ObjectLocator;

import javax.inject.Inject;
import javax.inject.Provider;

import rx.subjects.PublishSubject;

public class AlbumsFragment extends BaseFragment {
  @Inject AlbumsAdapter albumsAdapter;
  @Inject Provider<AlbumsLoader> albumsLoaderProvider;

  private PublishSubject<Long> whenAlbumSelected;

  public AlbumsFragment() {
    whenAlbumSelected = PublishSubject.create();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setLayoutId(R.layout.fragment_albums);

    ObjectLocator.getGraph(getActivity().getApplicationContext())
        .inject(this);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    getLoaderManager().initLoader(0, null, new SimpleLoaderListener<Cursor>() {
      @Override
      public Loader<Cursor> onCreateLoader() {
        return albumsLoaderProvider.get();
      }

      @Override
      public void onLoadFinished(Cursor data) {
        albumsAdapter.swapCursor(data);
      }

      @Override
      public void onLoaderReset() {
        albumsAdapter.swapCursor(null);
      }
    });

    ActionBar actionBar = getActivity().getActionBar();
    if (actionBar != null) {
      actionBar.setTitle(R.string.albums);
    }
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    GridView albumsView = (GridView) view.findViewById(R.id.albumsView);
    albumsView.setAdapter(albumsAdapter);
    albumsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long albumId = albumsAdapter.getItemId(position);

        // Emit selected album to subscribers.
        whenAlbumSelected.onNext(albumId);
      }
    });
  }

  public PublishSubject<Long> whenAlbumSelected() {
    return whenAlbumSelected;
  }
}
