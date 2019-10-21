package com.jbuelow.robotskeleton.hardware.motor.servo;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

//@Component //do not load. WIP.
@Slf4j
@Profile("pcaCluster")
public class PCA9685Cluster {

  private Map<Integer, PCA9685> masterMap = new HashMap<>();

  public PCA9685Cluster() {
    for (byte i = 0x00; i < 0x7F; i++) {
      if (i == 0x70) {
        //This is the all-call address.
        continue;
      }
    }
  }

  public PwmChannel getChannel() {
    return null;
  }
}
