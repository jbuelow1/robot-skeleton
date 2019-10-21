package com.jbuelow.robotskeleton.service.flow;

import com.jbuelow.robotskeleton.hardware.camera.CameraController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MainFlowService {

  private boolean running = false;

  private final CameraController cameraController;

  public MainFlowService(CameraController cameraController) {
    log.debug("Initializing flow service");
    this.cameraController = cameraController;
  }

}
