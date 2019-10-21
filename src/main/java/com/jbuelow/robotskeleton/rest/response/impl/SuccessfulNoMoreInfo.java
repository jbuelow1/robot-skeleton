package com.jbuelow.robotskeleton.rest.response.impl;

import com.jbuelow.robotskeleton.rest.response.Response;
import java.time.Instant;
import lombok.Getter;

@Getter
public class SuccessfulNoMoreInfo implements Response {

  private final Instant time;
  private final boolean success;

  public SuccessfulNoMoreInfo() {
    this.time = Instant.now();
    this.success = true;
  }

}
