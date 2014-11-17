package com.thuytrinh.photopicker.module;

import android.content.Context;

import dagger.ObjectGraph;

public class ObjectLocator {
  private static ObjectLocator objectLocator;
  private ObjectGraph graph;

  private ObjectLocator() { }

  public static ObjectGraph getGraph(Context appContext) {
    if (objectLocator == null) {
      objectLocator = new ObjectLocator();
      objectLocator.buildGraph(appContext);
    }

    return objectLocator.graph;
  }

  public static void setMockGraph(ObjectGraph mockGraph) {
    objectLocator = new ObjectLocator();
    objectLocator.graph = mockGraph;
  }

  private void buildGraph(Context appContext) {
    graph = ObjectGraph.create(new AppModule(appContext));
  }
}
