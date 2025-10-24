package com.example.wrappercore.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages products in the NeuroControl Middleware system.
 * Provides CRUD operations for products.
 */
public class ProductManager {

  private final Map<String, Product> products;

  /**
   * Constructor for ProductManager.
   */
  public ProductManager() {
    this.products = new HashMap<>();
  }

  /**
   * Creates a new product and adds it to the manager.
   *
   * @param product Product to create
   * @return true if product was created successfully, false if product with same ID exists
   */
  public boolean createProduct(Product product) {
    if (product == null || product.getId() == null) {
      return false;
    }
    if (products.containsKey(product.getId())) {
      return false;
    }
    products.put(product.getId(), product);
    return true;
  }

  /**
   * Gets a product by ID.
   *
   * @param id Product ID
   * @return Product if found, null otherwise
   */
  public Product getProduct(String id) {
    return products.get(id);
  }

  /**
   * Gets all products.
   *
   * @return List of all products
   */
  public List<Product> getAllProducts() {
    return new ArrayList<>(products.values());
  }

  /**
   * Gets all active products.
   *
   * @return List of active products
   */
  public List<Product> getActiveProducts() {
    List<Product> activeProducts = new ArrayList<>();
    for (Product product : products.values()) {
      if (product.isActive()) {
        activeProducts.add(product);
      }
    }
    return activeProducts;
  }

  /**
   * Updates an existing product.
   *
   * @param product Product with updated information
   * @return true if product was updated successfully, false if product doesn't exist
   */
  public boolean updateProduct(Product product) {
    if (product == null || product.getId() == null) {
      return false;
    }
    if (!products.containsKey(product.getId())) {
      return false;
    }
    products.put(product.getId(), product);
    return true;
  }

  /**
   * Deletes a product by ID.
   *
   * @param id Product ID
   * @return true if product was deleted successfully, false if product doesn't exist
   */
  public boolean deleteProduct(String id) {
    if (id == null || !products.containsKey(id)) {
      return false;
    }
    products.remove(id);
    return true;
  }

  /**
   * Gets the total number of products.
   *
   * @return Number of products
   */
  public int getProductCount() {
    return products.size();
  }

  /**
   * Checks if a product exists.
   *
   * @param id Product ID
   * @return true if product exists, false otherwise
   */
  public boolean productExists(String id) {
    return products.containsKey(id);
  }
}
