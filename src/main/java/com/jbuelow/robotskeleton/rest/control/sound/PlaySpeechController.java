package com.jbuelow.robotskeleton.rest.control.sound;

import com.jbuelow.robotskeleton.hardware.sound.AudioDriver;
import com.jbuelow.robotskeleton.rest.response.Response;
import com.jbuelow.robotskeleton.rest.response.impl.SuccessfulNoMoreInfo;
import com.jbuelow.robotskeleton.service.tts.TtsProvider;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.audio.AudioStream;

@RestController
public class PlaySpeechController {

  private final TtsProvider tts;
  private final AudioDriver audioDriver;

  public PlaySpeechController(TtsProvider tts, AudioDriver audioDriver) {
    this.tts = tts;
    this.audioDriver = audioDriver;
  }

  @RequestMapping("/control/play/tts")
  public Response requestSpeech(@RequestParam String text)
      throws IOException, LineUnavailableException {
    AudioInputStream audio = tts.synthesize(text);
    audioDriver.playAudio(audio);

    return new SuccessfulNoMoreInfo();
  }
}
