package com.example.wrappercore;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.neurosky.AlgoSdk.NskAlgoSdk;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.*;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
  @Test
  public void useAppContext() {
    NskAlgoSdk nskAlgoSdk=new NskAlgoSdk();
    assertThat(nskAlgoSdk.toString()).isEqualTo("Test");
  }
}