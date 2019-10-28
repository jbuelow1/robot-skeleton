package com.jbuelow.robotskeleton.rest.control.sound;

import com.jbuelow.robotskeleton.hardware.sound.AudioOutput;
import com.jbuelow.robotskeleton.hardware.sound.impl.SimpleAudioOutputDriver;
import com.jbuelow.robotskeleton.rest.response.Response;
import com.jbuelow.robotskeleton.rest.response.impl.SuccessfulNoMoreInfo;
import com.jbuelow.robotskeleton.service.tts.TtsProvider;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaySpeechController {

  private final TtsProvider tts;
  private final AudioOutput simpleAudioOutputDriver;

  public PlaySpeechController(TtsProvider tts, AudioOutput simpleAudioOutputDriver) {
    this.tts = tts;
    this.simpleAudioOutputDriver = simpleAudioOutputDriver;
  }

  @RequestMapping("/control/play/tts")
  public Response requestSpeech(@RequestParam String text)
      throws IOException, LineUnavailableException {
    AudioInputStream audio = tts.synthesize(text);
    simpleAudioOutputDriver.playSpeech(audio);

    return new SuccessfulNoMoreInfo();
  }
}
