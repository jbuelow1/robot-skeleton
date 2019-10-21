package com.jbuelow.robotskeleton.rest.response.impl;

import java.awt.image.BufferedImage;
import lombok.Getter;

@Getter
public class SimpleImageResponse extends SuccessfulNoMoreInfo {

  private final String image;

  public SimpleImageResponse(String image) {
    super();
    this.image = image;
  }

}
