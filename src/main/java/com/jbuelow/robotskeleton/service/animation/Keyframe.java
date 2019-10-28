package com.jbuelow.robotskeleton.service.animation;

import java.util.Map;

public abstract class Keyframe {

  public abstract Map<Integer, Integer> getPositions();

  public Integer getPosition(Integer channel) {
    return this.getPositions().get(channel);
  }

}
