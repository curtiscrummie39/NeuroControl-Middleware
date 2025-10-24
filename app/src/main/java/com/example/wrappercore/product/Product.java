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

  /**
   * Default constructor for Product.
   */
  public Product() {
    this.active = true;
  }

  /**
   * Constructor with parameters.
   *
   * @param id          Unique identifier for the product
   * @param name        Name of the product
   * @param description Description of the product
   * @param type        Type of product (e.g., "WHEELCHAIR", "HEADSET")
   */
  public Product(String id, String name, String description, String type) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.type = type;
    this.active = true;
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

  @Override
  public String toString() {
    return "Product{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", type='" + type + '\'' +
        ", active=" + active +
        '}';
  }
}
