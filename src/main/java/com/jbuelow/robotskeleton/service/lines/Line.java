package com.jbuelow.robotskeleton.service.lines;

import com.jbuelow.robotskeleton.service.animation.Keyframe;
import java.util.List;
import java.util.Map;

public interface Line {

  String getTtsText();

  List<LineParameter> getParameters();

  Map<Integer, Keyframe> getKeyframes();

}
