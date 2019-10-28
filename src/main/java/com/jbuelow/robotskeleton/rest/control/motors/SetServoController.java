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

  @RequestMapping("/control/servo/set")
  public Response setServo(
      @RequestParam(value = "servo") int servo,
      @RequestParam(value = "value") int value) throws IOException {

    float floatingPoint = (float) value / 1000;

    pwmDevice.getChannel(servo).setServoPulse(floatingPoint);
    log.info("Servo #{} pulse length has been set to {}ms via a manual REST request.", servo, floatingPoint);

    return new SuccessfulNoMoreInfo();
  }

  @RequestMapping("/control/servo/release")
  public Response releaseServo(
      @RequestParam(value = "servo", defaultValue = "-1") int servo
  ) throws IOException {
    if (servo != -1) {
      pwmDevice.getChannel(servo).setPWM(0,0);
    } else {
      pwmDevice.setAllPWM(0,0);
    }
    return new SuccessfulNoMoreInfo();
  }
}
