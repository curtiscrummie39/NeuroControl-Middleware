package com.example.wrappercore.product;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for the ProductManager class.
 */
public class ProductManagerTest {

  private ProductManager productManager;
  private Product product1;
  private Product product2;

  @Before
  public void setUp() {
    productManager = new ProductManager();
    product1 = new Product("1", "Neurosky Mindwave2", "Brain-computer interface headset", "HEADSET", 0, 32);
    product2 = new Product("2", "Wheelchair Model X", "Advanced electric wheelchair", "WHEELCHAIR");
  }

  @Test
  public void testCreateProduct() {
    assertTrue(productManager.createProduct(product1));
    assertEquals(1, productManager.getProductCount());
    assertTrue(productManager.productExists("1"));
  }

  @Test
  public void testCreateDuplicateProduct() {
    assertTrue(productManager.createProduct(product1));
    assertFalse(productManager.createProduct(product1));
    assertEquals(1, productManager.getProductCount());
  }

  @Test
  public void testCreateNullProduct() {
    assertFalse(productManager.createProduct(null));
    assertEquals(0, productManager.getProductCount());
  }

  @Test
  public void testCreateProductWithNullId() {
    Product productWithNullId = new Product();
    assertFalse(productManager.createProduct(productWithNullId));
    assertEquals(0, productManager.getProductCount());
  }

  @Test
  public void testGetProduct() {
    productManager.createProduct(product1);
    Product retrieved = productManager.getProduct("1");
    assertNotNull(retrieved);
    assertEquals("1", retrieved.getId());
    assertEquals("Neurosky Mindwave2", retrieved.getName());
  }

  @Test
  public void testGetNonExistentProduct() {
    assertNull(productManager.getProduct("999"));
  }

  @Test
  public void testGetAllProducts() {
    productManager.createProduct(product1);
    productManager.createProduct(product2);
    List<Product> products = productManager.getAllProducts();
    assertEquals(2, products.size());
  }

  @Test
  public void testGetActiveProducts() {
    productManager.createProduct(product1);
    productManager.createProduct(product2);
    product2.setActive(false);
    productManager.updateProduct(product2);

    List<Product> activeProducts = productManager.getActiveProducts();
    assertEquals(1, activeProducts.size());
    assertEquals("1", activeProducts.get(0).getId());
  }

  @Test
  public void testUpdateProduct() {
    productManager.createProduct(product1);
    product1.setName("Updated Name");
    assertTrue(productManager.updateProduct(product1));

    Product retrieved = productManager.getProduct("1");
    assertEquals("Updated Name", retrieved.getName());
  }

  @Test
  public void testUpdateNonExistentProduct() {
    assertFalse(productManager.updateProduct(product1));
  }

  @Test
  public void testUpdateNullProduct() {
    assertFalse(productManager.updateProduct(null));
  }

  @Test
  public void testDeleteProduct() {
    productManager.createProduct(product1);
    assertTrue(productManager.deleteProduct("1"));
    assertEquals(0, productManager.getProductCount());
    assertFalse(productManager.productExists("1"));
  }

  @Test
  public void testDeleteNonExistentProduct() {
    assertFalse(productManager.deleteProduct("999"));
  }

  @Test
  public void testDeleteNullId() {
    assertFalse(productManager.deleteProduct(null));
  }

  @Test
  public void testProductExists() {
    assertFalse(productManager.productExists("1"));
    productManager.createProduct(product1);
    assertTrue(productManager.productExists("1"));
  }

  @Test
  public void testGetProductCount() {
    assertEquals(0, productManager.getProductCount());
    productManager.createProduct(product1);
    assertEquals(1, productManager.getProductCount());
    productManager.createProduct(product2);
    assertEquals(2, productManager.getProductCount());
  }
}
