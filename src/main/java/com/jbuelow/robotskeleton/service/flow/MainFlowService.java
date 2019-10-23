package com.jbuelow.robotskeleton.service.flow;

import com.github.sarxos.webcam.WebcamDevice;
import com.jbuelow.robotskeleton.hardware.camera.CameraDevice;
import com.jbuelow.robotskeleton.hardware.camera.impl.WebcamCaptureDevice;
import com.jbuelow.robotskeleton.hardware.motor.servo.PwmDevice;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MainFlowService {

  private boolean running = false;

  private final CameraDevice webcam;
  private final PwmDevice pwmDevice;

  public MainFlowService(CameraDevice webcam, PwmDevice pwmDevice) throws IOException {
    log.debug("Initializing flow service");
    this.webcam = webcam;
    this.pwmDevice = pwmDevice;

    pwmDevice.setPWMFreqency(1000);
  }

}
