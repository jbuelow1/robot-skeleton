package com.jbuelow.robotskeleton.rest.control.cv;

import com.jbuelow.robotskeleton.hardware.camera.CameraDevice;
import com.jbuelow.robotskeleton.service.cv.ComputerVision;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestCVFaceDetectionController {

  private final CameraDevice cameraDevice;
  private final ComputerVision computerVision;

  public TestCVFaceDetectionController(CameraDevice cameraDevice, ComputerVision computerVision) {
    this.cameraDevice = cameraDevice;
    this.computerVision = computerVision;
  }

  @GetMapping("/control/camera/cv/showfaces")
  public @ResponseBody
  String showImage(@RequestParam(value = "refresh", defaultValue = "true") String refresh) {
    BufferedImage img = cameraDevice.getImage();

    int faces = computerVision.findFaces(img);

    ByteArrayOutputStream os = new ByteArrayOutputStream();
    String outB64 = "";

    try {
      ImageIO.write(img, "jpeg", os);
      outB64 = Base64.getEncoder().encodeToString(os.toByteArray());
    } catch (IOException e) {
      log.error("Exception occoured during base64 encoding of image", e);
    }

    return (refresh.equals("true")
        ?"<meta http-equiv=\"refresh\" content=\"0.5\">":"")+"<img src=\"data:image/jpeg;base64, " + outB64 + "\"/><br><h1>" + faces + " faces found</h1>";
  }

}
