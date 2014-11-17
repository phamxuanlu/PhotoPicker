package com.thuytrinh.photopicker.module;

import android.content.Context;

import dagger.ObjectGraph;

public class ObjectLocator {

  private static ObjectLocator sObjectLocator;
  private ObjectGraph mGraph;

  private ObjectLocator() { }

  public static ObjectGraph getGraph(Context appContext) {
    if (sObjectLocator == null) {
      sObjectLocator = new ObjectLocator();
      sObjectLocator.buildGraph(appContext);
    }

    return sObjectLocator.mGraph;
  }

  public static void setMockGraph(ObjectGraph mockGraph) {
    sObjectLocator = new ObjectLocator();
    sObjectLocator.mGraph = mockGraph;
  }

  private void buildGraph(Context appContext) {
    mGraph = ObjectGraph.create(new AppModule(appContext));
  }
}