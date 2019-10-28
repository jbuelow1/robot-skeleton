package com.jbuelow.robotskeleton.hardware.sound;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public interface AudioOutput {

  void playSpeech(AudioInputStream stream)
      throws IOException, LineUnavailableException;

  void playAudioFile(File in)
      throws IOException, LineUnavailableException, UnsupportedAudioFileException;

}
