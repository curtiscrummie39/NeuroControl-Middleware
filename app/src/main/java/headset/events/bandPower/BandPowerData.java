package headset.events.bandPower;


public record BandPowerData(float delta, float theta, float alpha, float beta, float gamma) {

  public BandPowerData {
    if (delta < 0 || theta < 0 || alpha < 0 || beta < 0
        || gamma < 0) {
      throw new IllegalArgumentException("Band power values cannot be negative");
    }
  }
}