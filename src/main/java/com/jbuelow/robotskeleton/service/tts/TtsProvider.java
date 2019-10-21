package com.jbuelow.robotskeleton.service.tts;

import javax.sound.sampled.AudioInputStream;

public interface TtsProvider {

  AudioInputStream synthesize(String text);

}
