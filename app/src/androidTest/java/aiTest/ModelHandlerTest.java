package aiTest;

import ai.Model;
import org.junit.Test;

public class ModelHandlerTest {

  Model modelHandler = new Model();

  @Test
  public void testLoadModel() {
    // Arrange
    String modelPath = "D:\\projects\\Grad\\Wrapper\\WrapperCore\\app\\src\\main\\ml\\sum.tflite";
    // Act
    modelHandler.loadModel(modelPath);
    // Assert
//    assertThat(modelHandler.tflite).isNotNull();
    // ArrangeS
    float[] inputData = new float[]{1, 2};
    // Act
//    float[] result = modelHandler.runInference(inputData);
    // Assert
//    assertThat(result).isEqualTo(new float[]{3});
  }
}