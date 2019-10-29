package com.jbuelow.robotskeleton.service.cv.impl;

import com.jbuelow.robotskeleton.service.cv.ComputerVision;
import java.awt.image.BufferedImage;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("noCV")
public class ComputerVisionFake implements ComputerVision {

  @Override
  public int findFaces(BufferedImage image) {
    return 0;
  }

}
