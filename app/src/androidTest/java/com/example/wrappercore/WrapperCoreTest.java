package com.example.wrappercore;

import static org.assertj.core.api.Assertions.assertThat;

import ai.ModelController;
import org.junit.Test;

public class WrapperCoreTest {

  @Test
  public void testWrapperCore() {
    ModelController modelController = new ModelController("https://learny-v1.onrender.com/api/v1/downloadModel");
    WrapperCore wrapperCore = new WrapperCore();
    wrapperCore.getMindWaveMobile2().addEventListener(modelController);
    assertThat(wrapperCore.getMindWaveMobile2().containsListener(modelController)).isTrue();
  }
}
