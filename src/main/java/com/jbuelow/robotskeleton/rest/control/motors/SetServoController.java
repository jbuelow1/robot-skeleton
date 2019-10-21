package com.jbuelow.robotskeleton.rest.control.motors;

import com.jbuelow.robotskeleton.hardware.motor.servo.PwmDevice;
import com.jbuelow.robotskeleton.rest.response.Response;
import com.jbuelow.robotskeleton.rest.response.impl.SuccessfulNoMoreInfo;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SetServoController {

  private final PwmDevice pwmDevice;

  public SetServoController(PwmDevice pwmDevice) {
    this.pwmDevice = pwmDevice;
  }

  @RequestMapping("/control/setservo")
  public Response setServo(
      @RequestParam(value = "servo") int servo,
      @RequestParam(value = "value") int value) throws IOException {

    pwmDevice.getChannel(servo).setServoPulse(value);
    log.info("servo {} has been set to {} via a manual REST request.", servo, value);

    return new SuccessfulNoMoreInfo();
  }
}
