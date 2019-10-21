package com.jbuelow.robotskeleton.hardware.camera.impl;

import com.jbuelow.robotskeleton.hardware.camera.CameraDevice;
import java.awt.image.BufferedImage;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("noCamera")
public class FakeCameraDevice implements CameraDevice {

  @Override
  public BufferedImage getImage() {
    return new BufferedImage(300, 300, BufferedImage.TYPE_3BYTE_BGR);
  }

}
