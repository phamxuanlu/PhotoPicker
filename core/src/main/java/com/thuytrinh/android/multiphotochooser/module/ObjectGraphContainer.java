package com.thuytrinh.android.multiphotochooser.module;

import android.content.Context;

import dagger.ObjectGraph;

public class ObjectGraphContainer {

  private static ObjectGraphContainer sObjectGraphContainer;
  private ObjectGraph mObjectGraph;

  private ObjectGraphContainer() { }

  public static ObjectGraph getObjectGraph(Context appContext) {
    if (sObjectGraphContainer == null) {
      sObjectGraphContainer = new ObjectGraphContainer();
      sObjectGraphContainer.buildObjectGraph(appContext);
    }

    return sObjectGraphContainer.mObjectGraph;
  }

  public static void setMockObjectGraph(ObjectGraph mockObjectGraph) {
    sObjectGraphContainer = new ObjectGraphContainer();
    sObjectGraphContainer.mObjectGraph = mockObjectGraph;
  }

  private void buildObjectGraph(Context appContext) {
    mObjectGraph = ObjectGraph.create(new AppModule(appContext));
  }
}