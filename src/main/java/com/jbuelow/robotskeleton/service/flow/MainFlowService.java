package com.jbuelow.robotskeleton.service.flow;

import com.jbuelow.robotskeleton.hardware.camera.CameraDevice;
import com.jbuelow.robotskeleton.hardware.motor.servo.PwmDevice;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MainFlowService implements Flow {

  private final PwmDevice pwmDevice;
  private boolean running = false;

  private final CameraDevice webcam;

  public MainFlowService(CameraDevice webcam, PwmDevice pwmDevice) throws IOException {
    log.debug("Initializing flow service");
    this.webcam = webcam;
    this.pwmDevice = pwmDevice;
    pwmDevice.setPWMFreqency(50);
  }

  @Override
  public void start() {
    running = true;
    log.info("Starting MainFlow");
  }

  @Override
  public void stop() {
    running = false;
    log.info("Stopping MainFlow");
  }
}
