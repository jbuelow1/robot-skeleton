package com.jbuelow.robotskeleton.rest.response;

import java.time.Instant;
import lombok.Getter;

public abstract class Response {

  @Getter
  private final Instant timestamp;

  public Response() {
    this.timestamp = Instant.now();
  }

}
