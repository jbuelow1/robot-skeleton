package com.jbuelow.robotskeleton.hardware.camera.impl;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryEvent;
import com.github.sarxos.webcam.WebcamDiscoveryListener;
import com.github.sarxos.webcam.ds.v4l4j.V4l4jDriver;
import com.jbuelow.robotskeleton.hardware.camera.CameraDevice;
import java.awt.image.BufferedImage;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@Profile("!noCamera")
public class WebcamCaptureDevice implements CameraDevice {

  private Webcam webcam;

  @Getter
  private volatile BufferedImage image;

  public WebcamCaptureDevice() {
    log.debug("Initializing camera controller module");
    setLibraryOptions();
    webcam = Webcam.getDefault();
    Webcam.addDiscoveryListener(new listener());
    if (webcam != null) {
      initializeCamera();
    }
  }

  private void setLibraryOptions() {
    log.debug("Setting camera lib options");
    String osName = System.getProperty("os.name");
    boolean nix = Pattern.matches("^.*(nux)|(nix)|(aix).*$", osName);
    if (nix) {
      log.debug("This is a *nix system. Using v4l driver");
      Webcam.setDriver(new V4l4jDriver());
    }
  }

  private void initializeCamera() {
    //Set webcam options

    //Open webcam
    webcam.open();
  }

  @Scheduled(fixedRate = 40)
  public void fetchImage() {
    if (webcam != null) {
      this.image = webcam.getImage();
    }
  }

  private class listener implements WebcamDiscoveryListener {

    @Override
    public void webcamFound(WebcamDiscoveryEvent webcamDiscoveryEvent) {
      log.info("New webcam found");
      if (webcam == null) {
        webcam = webcamDiscoveryEvent.getWebcam();
        initializeCamera();
      }
    }

    @Override
    public void webcamGone(WebcamDiscoveryEvent webcamDiscoveryEvent) {
      log.info("Webcam disconnected");
      if (webcam == webcamDiscoveryEvent.getWebcam()) {
        webcam = null;
      }
    }
  }

}
