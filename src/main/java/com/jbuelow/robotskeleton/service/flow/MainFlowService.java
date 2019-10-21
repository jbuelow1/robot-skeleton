package com.jbuelow.robotskeleton.service.flow;

import com.github.sarxos.webcam.WebcamDevice;
import com.jbuelow.robotskeleton.hardware.camera.CameraDevice;
import com.jbuelow.robotskeleton.hardware.camera.impl.WebcamCaptureDevice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MainFlowService {

  private boolean running = false;

  private final CameraDevice webcam;

  public MainFlowService(CameraDevice webcam) {
    log.debug("Initializing flow service");
    this.webcam = webcam;
  }

}
