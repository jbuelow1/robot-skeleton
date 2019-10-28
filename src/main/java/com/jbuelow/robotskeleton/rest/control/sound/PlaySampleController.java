package com.jbuelow.robotskeleton.rest.control.sound;

import com.jbuelow.robotskeleton.hardware.sound.AudioOutput;
import com.jbuelow.robotskeleton.hardware.sound.impl.SimpleAudioOutputDriver;
import com.jbuelow.robotskeleton.rest.response.Response;
import com.jbuelow.robotskeleton.rest.response.impl.SuccessfulNoMoreInfo;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaySampleController {

  private final AudioOutput simpleAudioOutputDriver;

  public PlaySampleController(AudioOutput simpleAudioOutputDriver) {
    this.simpleAudioOutputDriver = simpleAudioOutputDriver;
  }

  @RequestMapping("/control/play/sample")
  public Response playSample(
      @RequestParam String filename)
      throws IOException, LineUnavailableException, UnsupportedAudioFileException {

    simpleAudioOutputDriver.playAudioFile(new File(filename));

    return new SuccessfulNoMoreInfo();
  }

}
