package com.thuytrinh.android.multiphotochooser.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.thuytrinh.android.multiphotochooser.controller.activity.PhotoChooserActivity;
import com.thuytrinh.android.multiphotochooser.model.Photo;

import java.util.ArrayList;

public class HomeActivity extends Activity {

  public static final int RC_CHOOSE_PHOTOS = 0;

  private ListView mChosenPhotoListView;

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_home, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.addMenuItem:
        startActivityForResult(PhotoChooserActivity.newIntent(this), RC_CHOOSE_PHOTOS);
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    mChosenPhotoListView = (ListView) findViewById(R.id.chosenPhotoListView);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == RC_CHOOSE_PHOTOS && resultCode == RESULT_OK) {
      ArrayList<Photo> chosenPhotoList = data.getParcelableArrayListExtra(PhotoChooserActivity.EXTRA_CHOSEN_PHOTO_LIST);
      mChosenPhotoListView.setAdapter(new ChosenPhotoListAdapter(this, chosenPhotoList));
    }
  }
}