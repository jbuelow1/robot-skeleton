package com.jbuelow.robotskeleton.rest.control.sound;

import com.jbuelow.robotskeleton.hardware.sound.AudioDriver;
import com.jbuelow.robotskeleton.rest.response.Response;
import com.jbuelow.robotskeleton.rest.response.impl.SuccessfulNoMoreInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

@RestController
public class PlaySampleController {

  private final AudioDriver audioDriver;

  public PlaySampleController(AudioDriver audioDriver) {
    this.audioDriver = audioDriver;
  }

  @RequestMapping("/control/play/sample")
  public Response playSample(
      @RequestParam String filename)
      throws IOException, LineUnavailableException, UnsupportedAudioFileException {

    audioDriver.playAudio(new File(filename));

    return new SuccessfulNoMoreInfo();
  }

}
