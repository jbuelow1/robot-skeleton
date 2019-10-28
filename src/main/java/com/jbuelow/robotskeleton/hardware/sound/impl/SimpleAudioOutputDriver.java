package com.jbuelow.robotskeleton.hardware.sound.impl;

import com.jbuelow.robotskeleton.hardware.sound.AudioOutput;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer.Info;
import javax.sound.sampled.UnsupportedAudioFileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("noHardware")
public class SimpleAudioOutputDriver implements AudioOutput {

  @PostConstruct
  public void announceDriver() {
    log.info("Loaded simple audio driver");
  }

  @Override
  public void playSpeech(AudioInputStream stream)
      throws IOException, LineUnavailableException {
    playAudioInputStream(stream);
  }

  @Override
  public void playAudioFile(File in)
      throws IOException, LineUnavailableException, UnsupportedAudioFileException {
    playAudioInputStream(AudioSystem.getAudioInputStream(in));
  }

  static void playAudioInputStream(AudioInputStream ais)
      throws LineUnavailableException, IOException {
    Clip clip = AudioSystem.getClip();
    clip.open(ais);
    clip.start();
  }

  static Clip getWorkingClipMixer() {
    Info[] mixers = AudioSystem.getMixerInfo();
    Clip outMixer = null;
    for (Info mixer:mixers) {
      try {
        log.debug("Trying mixer {}", mixer);
        outMixer = AudioSystem.getClip(mixer);
        break;
      } catch (LineUnavailableException e) {
        log.debug("Mixer {} is unavailable.", mixer);
      }
    }
    if (Objects.nonNull(outMixer)) {
      return outMixer;
    }
    log.error("Could not find a usable mixer");
    throw new RuntimeException("No mixers are working");
  }

}
