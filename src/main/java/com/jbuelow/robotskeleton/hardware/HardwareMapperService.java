package com.jbuelow.robotskeleton.hardware;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HardwareMapperService {

  public HardwareMapperService() {
    log.debug("Initializing HardwareMapperService.");
  }

  public HardwareDevice getDevice(String identifier) {
    return null;
  }
}
