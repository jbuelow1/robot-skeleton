package com.jbuelow.robotskeleton.hardware.sound.impl;

import com.jbuelow.robotskeleton.hardware.sound.AudioOutput;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("noHardware")
public class SimpleAudioOutputDriver implements AudioOutput {

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

}
