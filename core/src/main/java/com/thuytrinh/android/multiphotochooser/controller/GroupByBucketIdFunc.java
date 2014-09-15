package com.thuytrinh.android.multiphotochooser.controller;

import android.database.Cursor;
import android.database.MatrixCursor;

import java.util.HashSet;
import java.util.Set;

import rx.util.functions.Func1;

public class GroupByBucketIdFunc implements Func1<Cursor, Cursor> {

  @Override
  public Cursor call(Cursor imageCursor) {
    MatrixCursor bucketCursor = new MatrixCursor(
        imageCursor.getColumnNames(),
        imageCursor.getCount()
    );
    Set<Long> seenBucketIdSet = new HashSet<>();
    ImageCursorMapper imageCursorMapper = new ImageCursorMapper(imageCursor);

    long bucketId;
    while (imageCursor.moveToNext()) {
      bucketId = imageCursorMapper.getBucketId();

      if (!seenBucketIdSet.contains(bucketId)) {
        bucketCursor.addRow(new Object[] {
            imageCursorMapper.getId(),
            imageCursorMapper.getBucketId(),
            imageCursorMapper.getBucketDisplayName(),
            imageCursorMapper.getData()
        });

        seenBucketIdSet.add(bucketId);
      }
    }

    return bucketCursor;
  }
}