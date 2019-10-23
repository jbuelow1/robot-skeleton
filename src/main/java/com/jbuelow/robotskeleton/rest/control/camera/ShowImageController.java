package com.jbuelow.robotskeleton.rest.control.camera;

import com.jbuelow.robotskeleton.hardware.camera.CameraDevice;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class ShowImageController {

  private final CameraDevice webcam;

  public ShowImageController(CameraDevice webcam) {
    this.webcam = webcam;
  }

  @GetMapping("/control/camera/showimage")
  public @ResponseBody String showImage(Model model) {
    BufferedImage img = webcam.getImage();

    ByteArrayOutputStream os = new ByteArrayOutputStream();
    String outB64 = "";

    try {
      ImageIO.write(img, "jpeg", os);
      outB64 = Base64.getEncoder().encodeToString(os.toByteArray());
    } catch (IOException e) {
      log.error("Exception occoured during base64 encoding of image", e);
    }

    return "<meta http-equiv=\"refresh\" content=\"0.5\"><img src=\"data:image/jpeg;base64, " + outB64 + "\"/>";
  }

}
