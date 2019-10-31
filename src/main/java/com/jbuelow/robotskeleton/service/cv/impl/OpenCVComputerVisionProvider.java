package com.jbuelow.robotskeleton.service.cv.impl;

import com.jbuelow.robotskeleton.hardware.camera.CameraDevice;
import com.jbuelow.robotskeleton.service.cv.ComputerVision;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("!noCV")
public class OpenCVComputerVisionProvider implements ComputerVision {

  private final CameraDevice cameraDevice;
  private final CascadeClassifier faceDetector;

  @Value("classpath:cascades/haarcascade_frontalface_alt.xml")
  private Resource res;

  public OpenCVComputerVisionProvider(CameraDevice cameraDevice) {
    this.cameraDevice = cameraDevice;
    OpenCV.loadShared();
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    faceDetector = new CascadeClassifier();
  }

  @PostConstruct
  public void loadOpenCV() throws IOException {
    log.debug("Loading Cascade");
    String tempDir="tmp";
    Path tmp = Files.createTempFile(tempDir, "cv");
    Files.copy(res.getInputStream(), tmp,
        StandardCopyOption.REPLACE_EXISTING);
    faceDetector.load(tmp.toString());

    if (faceDetector.empty()) {
      throw new RuntimeException("Cascade is empty!");
    }
  }

  @Override
  public int findFaces(BufferedImage image) {
    log.debug("Starting opencv face cascade");
    Mat imgMat = bufferedImageToMat(image);
    Imgproc.cvtColor(imgMat, imgMat, Imgproc.COLOR_RGB2GRAY);

    if (faceDetector.empty()) {
      throw new RuntimeException("Cascade is empty!");
    }

    MatOfRect detections = new MatOfRect();
    faceDetector.detectMultiScale(imgMat, detections);
    return detections.toArray().length;
  }

  public static Mat bufferedImageToMat(BufferedImage bi) {
    Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
    byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
    mat.put(0, 0, data);
    return mat;
  }
}
