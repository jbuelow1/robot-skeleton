package com.jbuelow.robotskeleton.rest.control.camera;

import com.jbuelow.robotskeleton.hardware.camera.CameraController;
import com.jbuelow.robotskeleton.rest.response.impl.SimpleImageResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class GetCameraImageController {

  private final CameraController cameraController;

  public GetCameraImageController(CameraController cameraController) {
    this.cameraController = cameraController;
  }

  @RequestMapping("/control/camera/getsnapshot")
  public SimpleImageResponse getImageSnapshot() {
    BufferedImage img = cameraController.getImage();

    ByteArrayOutputStream os = new ByteArrayOutputStream();
    String outB64 = "";

    try {
      ImageIO.write(img, "png", os);
      outB64 = Base64.getEncoder().encodeToString(os.toByteArray());
    } catch (IOException e) {
      log.error("Exception occoured during base64 encoding of image", e);
    }

    return new SimpleImageResponse(outB64);
  }

}
