package com.thuytrinh.photopicker.controller.fragment;

import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.GridView;

import com.reactiveviewmodel.core.BaseFragment;
import com.thuytrinh.photopicker.R;
import com.thuytrinh.photopicker.controller.SimpleLoaderListener;
import com.thuytrinh.photopicker.controller.adapter.PhotosAdapter;
import com.thuytrinh.photopicker.controller.loader.PhotosLoader;
import com.thuytrinh.photopicker.model.Photo;
import com.thuytrinh.photopicker.module.ObjectLocator;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Provider;

import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

public class PhotosFragment extends BaseFragment {
  private static final String EXTRA_ALBUM_ID = "albumId";

  @Inject PhotosAdapter photosAdapter;
  @Inject Provider<PhotosLoader> photosLoaderProvider;

  private PublishSubject<String> whenTitleReady;
  private PublishSubject<ArrayList<Photo>> whenChoicesDone;
  private BehaviorSubject<Integer> whenChoicesChanged;

  private SparseArray<Long> checkedItems;
  private long albumId;

  public PhotosFragment() {
    checkedItems = new SparseArray<>();

    whenTitleReady = PublishSubject.create();
    whenChoicesDone = PublishSubject.create();
    whenChoicesChanged = BehaviorSubject.create(checkedItems.size());
  }

  public static PhotosFragment newInstance(long albumId) {
    Bundle args = new Bundle();
    args.putLong(EXTRA_ALBUM_ID, albumId);

    PhotosFragment fragment = new PhotosFragment();
    fragment.setArguments(args);
    return fragment;
  }

  public PublishSubject<String> whenTitleReady() {
    return whenTitleReady;
  }

  public PublishSubject<ArrayList<Photo>> whenChoicesDone() {
    return whenChoicesDone;
  }

  public BehaviorSubject<Integer> whenChoicesChanged() {
    return whenChoicesChanged;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    setLayoutId(R.layout.fragment_photos);

    ObjectLocator.getGraph(getActivity().getApplicationContext())
        .inject(this);

    albumId = getArguments().getLong(EXTRA_ALBUM_ID);
    photosAdapter.setCheckedItems(checkedItems);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_photo_list, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int itemId = item.getItemId();
    if (itemId == R.id.doneMenuItem) {
      emitChoices();
      return true;
    } else {
      return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    getLoaderManager().initLoader(0, null, new SimpleLoaderListener<Cursor>() {
      @Override
      public Loader<Cursor> onCreateLoader() {
        PhotosLoader photosLoader = photosLoaderProvider.get();
        photosLoader.setAlbumId(albumId);
        return photosLoader;
      }

      @Override
      public void onLoadFinished(Cursor data) {
        photosAdapter.swapCursor(data);
      }

      @Override
      public void onLoaderReset() {
        photosAdapter.swapCursor(null);
      }
    });

    whenTitleReady.onNext(getString(R.string.photos));
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    GridView photosView = (GridView) view.findViewById(R.id.photosView);
    photosView.setAdapter(photosAdapter);
    photosView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        boolean isChecked = toggleChoice(position, id);
        Checkable itemView = (Checkable) view;
        itemView.setChecked(isChecked);
      }
    });
  }

  private boolean toggleChoice(int position, long id) {
    boolean isChecked = false;
    if (checkedItems.get(position) == null) {
      checkedItems.put(position, id);
      isChecked = true;
    } else {
      checkedItems.remove(position);
    }

    // Emit the change to subscribers.
    whenChoicesChanged.onNext(checkedItems.size());
    return isChecked;
  }

  private void emitChoices() {
    ArrayList<Photo> selectedPhotoList = new ArrayList<>();
    for (int i = 0, size = checkedItems.size(); i < size; i++) {
      int selectedPosition = checkedItems.keyAt(i);
      Photo selectedPhoto = (Photo) photosAdapter.getItem(selectedPosition);
      selectedPhotoList.add(selectedPhoto);
    }

    whenChoicesDone.onNext(selectedPhotoList);
  }
}
