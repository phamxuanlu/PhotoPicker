package com.thuytrinh.android.multiphotochooser.module;

import dagger.ObjectGraph;

public class AppObjectGraph {

  private static AppObjectGraph sAppObjectGraph;
  private ObjectGraph mObjectGraph;

  private AppObjectGraph() { }

  public static ObjectGraph get() {
    if (sAppObjectGraph == null) {
      sAppObjectGraph = new AppObjectGraph();
      sAppObjectGraph.buildObjectGraph();
    }

    return sAppObjectGraph.mObjectGraph;
  }

  private void buildObjectGraph() {
    mObjectGraph = ObjectGraph.create();
  }
}