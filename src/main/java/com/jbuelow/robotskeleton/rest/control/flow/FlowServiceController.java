package com.jbuelow.robotskeleton.rest.control.flow;

import com.jbuelow.robotskeleton.rest.response.Response;
import com.jbuelow.robotskeleton.rest.response.impl.SuccessfulNoMoreInfo;
import com.jbuelow.robotskeleton.service.flow.Flow;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlowServiceController {

  private final Flow flow;

  public FlowServiceController(Flow flow) {
    this.flow = flow;
  }

  @RequestMapping("/control/flow/start")
  public Response startFlowService() {
    flow.start();
    return new SuccessfulNoMoreInfo();
  }

  @RequestMapping("/control/flow/stop")
  public Response stopFlowService() {
    flow.stop();
    return new SuccessfulNoMoreInfo();
  }

}
