package com.jbuelow.robotskeleton.rest.control.preset;

import com.jbuelow.robotskeleton.hardware.motor.servo.PwmDevice;
import com.jbuelow.robotskeleton.hardware.sound.AudioOutput;
import com.jbuelow.robotskeleton.rest.response.Response;
import com.jbuelow.robotskeleton.rest.response.impl.SuccessfulNoMoreInfo;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScarePresetController {

  private final PwmDevice pwmDevice;
  private final AudioOutput audioOutput;

  public ScarePresetController(PwmDevice pwmDevice,
      AudioOutput audioOutput) {
    this.pwmDevice = pwmDevice;
    this.audioOutput = audioOutput;
  }

  @RequestMapping("/control/preset/scare")
  public Response scarePreset() throws IOException, InterruptedException {
    pwmDevice.getChannel(0).setServoPulse(2f);
    pwmDevice.getChannel(2).setServoPulse(1f);
    pwmDevice.getChannel(3).setServoPulse(2f);
    Thread.sleep(3000);
    pwmDevice.getChannel(0).setServoPulse(1f);
    pwmDevice.getChannel(2).setServoPulse(1.5f);
    pwmDevice.getChannel(3).setServoPulse(1.5f);
    Thread.sleep(1000);
    pwmDevice.setAllPWM(0,0);
    return new SuccessfulNoMoreInfo();
  }

}
