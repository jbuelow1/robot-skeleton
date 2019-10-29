package com.jbuelow.robotskeleton.hardware.sound.impl;

import static com.jbuelow.robotskeleton.hardware.sound.impl.SimpleAudioOutputDriver.getWorkingClipMixer;

import com.jbuelow.robotskeleton.hardware.motor.servo.PwmChannel;
import com.jbuelow.robotskeleton.hardware.motor.servo.PwmDevice;
import com.jbuelow.robotskeleton.hardware.sound.AudioOutput;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer.Info;
import javax.sound.sampled.UnsupportedAudioFileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("!noHardware")
public class ArticulatedJawAudioOutput implements AudioOutput {

  private final PwmDevice pwmDevice;
  private Boolean playing = false;
  Clip clip = null;

  public ArticulatedJawAudioOutput(PwmDevice pwmDevice) {
    this.pwmDevice = pwmDevice;
  }

  @Override
  public void playSpeech(AudioInputStream inStream) throws IOException, LineUnavailableException {
    log.debug("Playing speech sample that is {} frames long.", inStream.getFrameLength());

    LineListener ll = new LineListener() {
      @Override
      public void update(LineEvent event) {
        if (event.getType() == Type.STOP) {
          playing = false;
          clip.close();
        }
      }
    };

    byte[] sampleByteArray = new byte[(int)inStream.getFrameLength()*inStream.getFormat().getSampleSizeInBits()/8];
    inStream.read(sampleByteArray, 0, (int)inStream.getFrameLength()*inStream.getFormat().getSampleSizeInBits()/8);
    short[] sampleShortArray = toShortArray(sampleByteArray);

    log.debug("Raw data is {} bytes long.", sampleByteArray.length);
    log.debug("Audio type is {}", inStream.getFormat());
    log.debug("Avaliable audio mixers: {}", AudioSystem.getMixerInfo());
    log.debug("There are {} frames in our raw pcm audio array.", sampleShortArray.length);
    AudioInputStream ais = new AudioInputStream(new ByteArrayInputStream(sampleByteArray), inStream.getFormat(), inStream.getFrameLength());

    Info mixer = getWorkingClipMixer();
    clip = AudioSystem.getClip(mixer);
    clip.open(ais);
    clip.addLineListener(ll);
    clip.start();
    playing = true;

    PwmChannel pwmChannel = pwmDevice.getChannel(0);

    while (playing) {
      log.trace("Clip is still playing...");
      float level = getLevel(clip, sampleShortArray);
      level = Math.min(level, 0.7f);
      level = Math.max(level, 0f);
      if (!Float.isNaN(level)) {
        pwmChannel.setServoPulse(level+1.3f);
      }
      log.trace("Current line level is {}", level);
      try {
        Thread.sleep(20);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    pwmChannel.setServoPulse(1.3f);
  }

  private float getLevel(Clip clip, short[] inputSamples) {
    int position = clip.getFramePosition();
    short[] sampleData = new short[position+100<inputSamples.length?100:inputSamples.length-position];
    for (int i = 0; i < sampleData.length; i++) {
      sampleData[i] = inputSamples[position+i];
    }
    return (float) rootMeanSquare(sampleData);
  }

  @Override
  public void playAudioFile(File in)
      throws IOException, LineUnavailableException, UnsupportedAudioFileException {
    SimpleAudioOutputDriver.playAudioInputStream(AudioSystem.getAudioInputStream(in));
  }

  public static short[] toShortArray(byte[] byteArray){
    int times = Short.SIZE / Byte.SIZE;
    short[] doubles = new short[byteArray.length / times];
    for(int i=0;i<doubles.length;i++){
      doubles[i] = ByteBuffer.wrap(byteArray, i*times, times).getShort();
    }
    return doubles;
  }

  public static double rootMeanSquare(short... nums) {
    double sum = 0;
    for (short num : nums)
      sum += num * num;
    return Math.sqrt(sum / nums.length) / Short.MAX_VALUE;
  }
}
