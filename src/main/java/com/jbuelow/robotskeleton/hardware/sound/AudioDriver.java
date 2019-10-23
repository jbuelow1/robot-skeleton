package com.jbuelow.robotskeleton.hardware.sound;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.springframework.stereotype.Component;

@Component
public class AudioDriver {

  public void playAudio(AudioInputStream stream)
      throws IOException, LineUnavailableException {
    Clip clip = AudioSystem.getClip();
    clip.open(stream);
    clip.start();
  }

  public void playAudio(File in)
      throws IOException, LineUnavailableException, UnsupportedAudioFileException {
    Clip clip = AudioSystem.getClip();
    clip.open(AudioSystem.getAudioInputStream(in));
    clip.start();
  }
}
