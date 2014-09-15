package com.thuytrinh.android.multiphotochooser.controller;

import android.content.Loader;

import junit.framework.TestCase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class SimpleLoaderListenerTest extends TestCase {

  public void testShouldCreateLoaderProperly() {
    final Loader<String> mockLoader = (Loader<String>) mock(Loader.class);
    SimpleLoaderListener<String> listener = new SimpleLoaderListener<String>() {

      @Override
      public Loader<String> onCreateLoader() {
        return mockLoader;
      }
    };

    assertThat(listener.onCreateLoader(0, null))
        .isEqualTo(mockLoader);
  }
}