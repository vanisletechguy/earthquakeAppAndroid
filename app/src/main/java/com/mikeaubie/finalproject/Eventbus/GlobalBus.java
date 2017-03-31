package com.mikeaubie.finalproject.Eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Family on 3/30/2017.
 */

public class GlobalBus {
  private static EventBus sBus;

  public static EventBus getBus() {
    if (sBus == null)
      sBus = EventBus.getDefault();
    return sBus;
  }
}
