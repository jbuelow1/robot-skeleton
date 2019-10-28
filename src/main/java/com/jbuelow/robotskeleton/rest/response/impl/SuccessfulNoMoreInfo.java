package com.jbuelow.robotskeleton.rest.response.impl;

import com.jbuelow.robotskeleton.rest.response.Response;
import java.time.Instant;
import lombok.Getter;

@Getter
public class SuccessfulNoMoreInfo extends Response {

  private final boolean success;

  public SuccessfulNoMoreInfo() {
    super();
    this.success = true;
  }

}
