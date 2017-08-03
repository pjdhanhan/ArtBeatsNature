package com.liuzhen.mylibrary.Utils.drawableview.gestures.creator;


import com.liuzhen.mylibrary.Utils.drawableview.draw.SerializablePath;

public interface GestureCreatorListener {
  void onGestureCreated(SerializablePath serializablePath);

  void onCurrentGestureChanged(SerializablePath currentDrawingPath);
}
