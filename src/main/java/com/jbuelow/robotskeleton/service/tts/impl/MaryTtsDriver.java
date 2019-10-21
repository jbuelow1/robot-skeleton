package com.jbuelow.robotskeleton.service.tts.impl;

import com.jbuelow.robotskeleton.hardware.sound.AudioDriver;
import com.jbuelow.robotskeleton.service.tts.TtsProvider;
import java.io.IOException;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import lombok.extern.slf4j.Slf4j;
import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.signalproc.effects.LpcWhisperiserEffect;
import marytts.signalproc.effects.RobotiserEffect;
import marytts.signalproc.effects.StadiumEffect;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MaryTtsDriver implements TtsProvider {

  private final MaryInterface mary;

  public MaryTtsDriver() throws MaryConfigurationException {
    try {
      this.mary = new LocalMaryInterface();
    } catch (MaryConfigurationException e) {
      log.error("Exception occurred while initializing maryTTS engine", e);
      throw e;
    }
  }

  @PostConstruct
  public void configureMary() {
    log.debug("Configuring maryTTS engine");
    log.debug("available voices: {}", mary.getAvailableVoices());
    mary.setVoice("cmu-bdl-hsmm");
    mary.setAudioEffects("Robot+Whisper(ammount:1)+Stadium(ammount:75)");
    log.debug("Mary locale is set to {}", mary.getLocale());
    log.debug("Mary voice is set to {}", mary.getVoice());
    log.debug("Mary effects are set to {}", mary.getAudioEffects());
  }

  @Override
  public AudioInputStream synthesize(String text) {
    AudioInputStream audio;
    try {
      audio = mary.generateAudio(text);
    } catch (SynthesisException e) {
      log.error("Exception occurred while synthesizing audio", e);
      throw new RuntimeException(e);
    }
    return audio;
  }
}
