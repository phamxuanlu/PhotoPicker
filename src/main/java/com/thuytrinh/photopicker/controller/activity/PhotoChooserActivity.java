package com.thuytrinh.photopicker.controller.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.thuytrinh.photopicker.R;
import com.thuytrinh.photopicker.controller.fragment.AlbumsFragment;
import com.thuytrinh.photopicker.controller.fragment.PhotosFragment;
import com.thuytrinh.photopicker.model.Photo;

import java.util.ArrayList;

import rx.functions.Action1;

public class PhotoChooserActivity extends Activity {
  public static final String EXTRA_CHOSEN_PHOTO_LIST = "chosenPhotoList";

  private final String mPhotoListTag = "photoList";
  private final String mAlbumListTag = "albumList";

  public static Intent newIntent(Context context) {
    return new Intent(context, PhotoChooserActivity.class);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int itemId = item.getItemId();
    if (itemId == android.R.id.home) {
      onBackPressed();
      return true;
    } else {
      return super.onOptionsItemSelected(item);
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_photo_chooser);

    if (getActionBar() != null) {
      getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    AlbumsFragment albumsFragment;
    if (savedInstanceState == null) {
      albumsFragment = new AlbumsFragment();

      getFragmentManager().beginTransaction()
          .add(R.id.container, albumsFragment, mAlbumListTag)
          .commit();
    } else {
      albumsFragment = (AlbumsFragment) getFragmentManager()
          .findFragmentByTag(mAlbumListTag);
    }

    albumsFragment.whenAlbumSelected().subscribe(new Action1<Long>() {
      @Override
      public void call(Long selectedAlbumId) {
        PhotosFragment photosFragment = PhotosFragment.newInstance(selectedAlbumId);
        photosFragment.whenChoicesChanged().subscribe(new Action1<Integer>() {
          @Override
          public void call(Integer choiceCount) {
            if (getActionBar() != null) {
              getActionBar().setTitle(choiceCount.toString());
            }
          }
        });
        photosFragment.whenChoicesDone().subscribe(new Action1<ArrayList<Photo>>() {
          @Override
          public void call(ArrayList<Photo> chosenPhotoList) {
            // Prepare data.
            Intent data = new Intent();
            data.putParcelableArrayListExtra(EXTRA_CHOSEN_PHOTO_LIST, chosenPhotoList);

            // Push result back.
            setResult(RESULT_OK, data);
            finish();
          }
        });

        getFragmentManager().beginTransaction()
            .replace(R.id.container, photosFragment, mPhotoListTag)
            .addToBackStack(mPhotoListTag)
            .commit();
      }
    });
  }
}
