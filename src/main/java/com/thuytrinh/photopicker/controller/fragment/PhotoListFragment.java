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

import com.thuytrinh.photopicker.R;
import com.thuytrinh.photopicker.controller.SimpleLoaderListener;
import com.thuytrinh.photopicker.controller.adapter.PhotoListAdapter;
import com.thuytrinh.photopicker.controller.loader.PhotoListLoader;
import com.thuytrinh.photopicker.model.Photo;
import com.thuytrinh.photopicker.module.ObjectLocator;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Provider;

import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

public class PhotoListFragment extends BaseFragment {
  private static final String EXTRA_ALBUM_ID = "albumId";

  @Inject PhotoListAdapter mPhotoListAdapter;
  @Inject Provider<PhotoListLoader> mPhotoListLoaderProvider;

  private PublishSubject<String> mWhenTitleReady;
  private PublishSubject<ArrayList<Photo>> mWhenChoicesDone;
  private BehaviorSubject<Integer> mWhenChoicesChanged;

  private SparseArray<Long> mCheckedItemMap;
  private long mAlbumId;

  public PhotoListFragment() {
    mCheckedItemMap = new SparseArray<>();

    mWhenTitleReady = PublishSubject.create();
    mWhenChoicesDone = PublishSubject.create();
    mWhenChoicesChanged = BehaviorSubject.create(mCheckedItemMap.size());
  }

  public static PhotoListFragment newInstance(long albumId) {
    Bundle args = new Bundle();
    args.putLong(EXTRA_ALBUM_ID, albumId);

    PhotoListFragment fragment = new PhotoListFragment();
    fragment.setArguments(args);
    return fragment;
  }

  public PublishSubject<String> whenTitleReady() {
    return mWhenTitleReady;
  }

  public PublishSubject<ArrayList<Photo>> whenChoicesDone() {
    return mWhenChoicesDone;
  }

  public BehaviorSubject<Integer> whenChoicesChanged() {
    return mWhenChoicesChanged;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    setLayoutId(R.layout.fragment_photo_list);

    ObjectLocator.getGraph(getActivity().getApplicationContext())
        .inject(this);

    mAlbumId = getArguments().getLong(EXTRA_ALBUM_ID);
    mPhotoListAdapter.setCheckedItemMap(mCheckedItemMap);
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
        PhotoListLoader photoListLoader = mPhotoListLoaderProvider.get();
        photoListLoader.setAlbumId(mAlbumId);
        return photoListLoader;
      }

      @Override
      public void onLoadFinished(Cursor data) {
        mPhotoListAdapter.swapCursor(data);
      }

      @Override
      public void onLoaderReset() {
        mPhotoListAdapter.swapCursor(null);
      }
    });

    mWhenTitleReady.onNext(getString(R.string.photos));
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    GridView photoGridView = (GridView) view.findViewById(R.id.photoGridView);
    photoGridView.setAdapter(mPhotoListAdapter);
    photoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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
    if (mCheckedItemMap.get(position) == null) {
      mCheckedItemMap.put(position, id);
      isChecked = true;
    } else {
      mCheckedItemMap.remove(position);
    }

    // Emit the change to subscribers.
    mWhenChoicesChanged.onNext(mCheckedItemMap.size());
    return isChecked;
  }

  private void emitChoices() {
    ArrayList<Photo> selectedPhotoList = new ArrayList<>();
    for (int i = 0, size = mCheckedItemMap.size(); i < size; i++) {
      int selectedPosition = mCheckedItemMap.keyAt(i);
      Photo selectedPhoto = (Photo) mPhotoListAdapter.getItem(selectedPosition);
      selectedPhotoList.add(selectedPhoto);
    }

    mWhenChoicesDone.onNext(selectedPhotoList);
  }
}
