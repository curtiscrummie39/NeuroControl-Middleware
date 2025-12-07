package com.example.wrappercore.product;

/**
 * Represents a product in the NeuroControl Middleware system.
 * This could be a wheelchair model, headset model, or any other device
 * that can be controlled by the middleware.
 */
public class Product {

  private String id;
  private String name;
  private String description;
  private String type;
  private boolean active;
  private int deviceId;
  private int channelCount;
  private String networkGeneration;

  /**
   * Default constructor for Product.
   */
  public Product() {
    this.active = true;
    this.deviceId = 0;
    this.channelCount = 1;
    this.networkGeneration = null;
  }

  /**
   * Constructor with parameters.
   *
   * @param id          Unique identifier for the product
   * @param name        Name of the product
   * @param description Description of the product
   * @param type        Type of product (e.g., "WHEELCHAIR", "HEADSET", "PHONE", "PRINTER")
   */
  public Product(String id, String name, String description, String type) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.type = type;
    this.active = true;
    this.deviceId = 0;
    this.channelCount = 1;
    this.networkGeneration = null;
  }

  /**
   * Constructor with all parameters including deviceId and channelCount.
   *
   * @param id           Unique identifier for the product
   * @param name         Name of the product
   * @param description  Description of the product
   * @param type         Type of product (e.g., "WHEELCHAIR", "HEADSET", "PHONE", "PRINTER")
   * @param deviceId     Device identifier (must be non-negative, default is 0)
   * @param channelCount Number of channels for the device (must be positive)
   * @throws IllegalArgumentException if deviceId is negative or channelCount is not positive
   */
  public Product(String id, String name, String description, String type, int deviceId, int channelCount) {
    if (deviceId < 0) {
      throw new IllegalArgumentException("Device ID must be non-negative");
    }
    if (channelCount <= 0) {
      throw new IllegalArgumentException("Channel count must be positive");
    }
    this.id = id;
    this.name = name;
    this.description = description;
    this.type = type;
    this.active = true;
    this.deviceId = deviceId;
    this.channelCount = channelCount;
    this.networkGeneration = null;
  }

  /**
   * Constructor with all parameters including network generation support.
   *
   * @param id                Unique identifier for the product
   * @param name              Name of the product
   * @param description       Description of the product
   * @param type              Type of product (e.g., "PHONE", "PRINTER")
   * @param deviceId          Device identifier (must be non-negative, default is 0)
   * @param channelCount      Number of channels for the device (must be positive)
   * @param networkGeneration Network generation (e.g., "8G", "6G", "5G") for phones
   * @throws IllegalArgumentException if deviceId is negative or channelCount is not positive
   */
  public Product(String id, String name, String description, String type, int deviceId, int channelCount, String networkGeneration) {
    if (deviceId < 0) {
      throw new IllegalArgumentException("Device ID must be non-negative");
    }
    if (channelCount <= 0) {
      throw new IllegalArgumentException("Channel count must be positive");
    }
    this.id = id;
    this.name = name;
    this.description = description;
    this.type = type;
    this.active = true;
    this.deviceId = deviceId;
    this.channelCount = channelCount;
    this.networkGeneration = networkGeneration;
  }

  /**
   * Gets the product ID.
   *
   * @return Product ID
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the product ID.
   *
   * @param id Product ID
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Gets the product name.
   *
   * @return Product name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the product name.
   *
   * @param name Product name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the product description.
   *
   * @return Product description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the product description.
   *
   * @param description Product description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the product type.
   *
   * @return Product type
   */
  public String getType() {
    return type;
  }

  /**
   * Sets the product type.
   *
   * @param type Product type
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Checks if the product is active.
   *
   * @return true if active, false otherwise
   */
  public boolean isActive() {
    return active;
  }

  /**
   * Sets the product active status.
   *
   * @param active Active status
   */
  public void setActive(boolean active) {
    this.active = active;
  }

  /**
   * Gets the device ID.
   *
   * @return Device ID
   */
  public int getDeviceId() {
    return deviceId;
  }

  /**
   * Sets the device ID.
   *
   * @param deviceId Device ID (must be non-negative)
   * @throws IllegalArgumentException if deviceId is negative
   */
  public void setDeviceId(int deviceId) {
    if (deviceId < 0) {
      throw new IllegalArgumentException("Device ID must be non-negative");
    }
    this.deviceId = deviceId;
  }

  /**
   * Gets the channel count.
   *
   * @return Channel count
   */
  public int getChannelCount() {
    return channelCount;
  }

  /**
   * Sets the channel count.
   *
   * @param channelCount Channel count (must be positive)
   * @throws IllegalArgumentException if channelCount is not positive
   */
  public void setChannelCount(int channelCount) {
    if (channelCount <= 0) {
      throw new IllegalArgumentException("Channel count must be positive");
    }
    this.channelCount = channelCount;
  }

  /**
   * Gets the network generation.
   *
   * @return Network generation (e.g., "8G", "6G", "5G")
   */
  public String getNetworkGeneration() {
    return networkGeneration;
  }

  /**
   * Sets the network generation.
   *
   * @param networkGeneration Network generation (e.g., "8G", "6G", "5G")
   */
  public void setNetworkGeneration(String networkGeneration) {
    this.networkGeneration = networkGeneration;
  }

  @Override
  public String toString() {
    return "Product{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", type='" + type + '\'' +
        ", active=" + active +
        ", deviceId=" + deviceId +
        ", channelCount=" + channelCount +
        ", networkGeneration='" + networkGeneration + '\'' +
        '}';
  }
}
