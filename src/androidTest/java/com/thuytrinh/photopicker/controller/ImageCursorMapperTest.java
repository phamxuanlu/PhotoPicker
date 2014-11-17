package com.thuytrinh.photopicker.controller;

import android.database.Cursor;
import android.test.AndroidTestCase;

import com.thuytrinh.photopicker.model.Photo;

import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.Images.Media.BUCKET_DISPLAY_NAME;
import static android.provider.MediaStore.Images.Media.BUCKET_ID;
import static android.provider.MediaStore.Images.Media.DATA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ImageCursorMapperTest extends AndroidTestCase {

  public void testShouldGetIdProperly() {
    Cursor mockImageCursor = mock(Cursor.class);

    int mockIdIndex = 2;
    when(mockImageCursor.getColumnIndex(_ID))
        .thenReturn(mockIdIndex);

    long mockId = 12345L;
    when(mockImageCursor.getLong(mockIdIndex))
        .thenReturn(mockId);

    ImageCursorMapper imageCursorMapper = new ImageCursorMapper(mockImageCursor);

    assertThat(imageCursorMapper.getId())
        .isEqualTo(mockId);
  }

  public void testShouldGetBucketIdProperly() {
    Cursor mockImageCursor = mock(Cursor.class);

    int mockBucketIdIndex = 2;
    when(mockImageCursor.getColumnIndex(BUCKET_ID))
        .thenReturn(mockBucketIdIndex);

    long mockBucketId = 12345L;
    when(mockImageCursor.getLong(mockBucketIdIndex))
        .thenReturn(mockBucketId);

    ImageCursorMapper imageCursorMapper = new ImageCursorMapper(mockImageCursor);

    assertThat(imageCursorMapper.getBucketId())
        .isEqualTo(mockBucketId);
  }

  public void testShouldGetBucketDisplayNameProperly() {
    Cursor mockImageCursor = mock(Cursor.class);

    int mockBucketDisplayNameIndex = 2;
    when(mockImageCursor.getColumnIndex(BUCKET_DISPLAY_NAME))
        .thenReturn(mockBucketDisplayNameIndex);

    String mockBucketDisplayName = "Awesome album";
    when(mockImageCursor.getString(mockBucketDisplayNameIndex))
        .thenReturn(mockBucketDisplayName);

    ImageCursorMapper imageCursorMapper = new ImageCursorMapper(mockImageCursor);

    assertThat(imageCursorMapper.getBucketDisplayName())
        .isEqualTo(mockBucketDisplayName);
  }

  public void testShouldGetDataProperly() {
    Cursor mockImageCursor = mock(Cursor.class);

    int mockDataIndex = 2;
    when(mockImageCursor.getColumnIndex(DATA))
        .thenReturn(mockDataIndex);

    String mockData = "Matrix/secret.png";
    when(mockImageCursor.getString(mockDataIndex))
        .thenReturn(mockData);

    ImageCursorMapper imageCursorMapper = new ImageCursorMapper(mockImageCursor);

    assertThat(imageCursorMapper.getData())
        .isEqualTo(mockData);
  }

  public void testShouldConvertToPhotoProperly() {
    Cursor mockImageCursor = mock(Cursor.class);

    int mockIdIndex = 1;
    when(mockImageCursor.getColumnIndex(_ID))
        .thenReturn(mockIdIndex);

    long mockId = 123L;
    when(mockImageCursor.getLong(mockIdIndex))
        .thenReturn(mockId);

    int mockBucketIdIndex = 2;
    when(mockImageCursor.getColumnIndex(BUCKET_ID))
        .thenReturn(mockBucketIdIndex);

    long mockBucketId = 456L;
    when(mockImageCursor.getLong(mockBucketIdIndex))
        .thenReturn(mockBucketId);

    int mockDataIndex = 3;
    when(mockImageCursor.getColumnIndex(DATA))
        .thenReturn(mockDataIndex);

    String mockData = "Matrix/secret.png";
    when(mockImageCursor.getString(mockDataIndex))
        .thenReturn(mockData);

    ImageCursorMapper imageCursorMapper = new ImageCursorMapper(mockImageCursor);
    Photo photo = imageCursorMapper.toPhoto();

    assertNotNull(photo);
    assertThat(photo.getId()).isEqualTo(mockId);
    assertThat(photo.getAlbumId()).isEqualTo(mockBucketId);
    assertThat(photo.getPath()).isEqualTo(mockData);
  }
}