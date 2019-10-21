package com.jbuelow.robotskeleton.hardware.light.impl;

import com.jbuelow.robotskeleton.hardware.light.LedBus;
import lombok.Getter;

public class WS2812 implements LedBus {

  @Getter
  private final int length;

  public WS2812(int length) {
    this.length = length;
  }

  @Override
  public String getIdentifier() {
    return "WS2812";
  }

}
