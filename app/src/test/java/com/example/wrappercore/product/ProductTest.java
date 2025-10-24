package com.example.wrappercore.product;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the Product class.
 */
public class ProductTest {

  private Product product;

  @Before
  public void setUp() {
    product = new Product("1", "Neurosky Mindwave2", "Brain-computer interface headset", "HEADSET");
  }

  @Test
  public void testProductConstructor() {
    assertNotNull(product);
    assertEquals("1", product.getId());
    assertEquals("Neurosky Mindwave2", product.getName());
    assertEquals("Brain-computer interface headset", product.getDescription());
    assertEquals("HEADSET", product.getType());
    assertTrue(product.isActive());
  }

  @Test
  public void testDefaultConstructor() {
    Product emptyProduct = new Product();
    assertNotNull(emptyProduct);
    assertNull(emptyProduct.getId());
    assertNull(emptyProduct.getName());
    assertTrue(emptyProduct.isActive());
  }

  @Test
  public void testSettersAndGetters() {
    product.setId("2");
    assertEquals("2", product.getId());

    product.setName("Wheelchair Model X");
    assertEquals("Wheelchair Model X", product.getName());

    product.setDescription("Advanced electric wheelchair");
    assertEquals("Advanced electric wheelchair", product.getDescription());

    product.setType("WHEELCHAIR");
    assertEquals("WHEELCHAIR", product.getType());

    product.setActive(false);
    assertFalse(product.isActive());
  }

  @Test
  public void testToString() {
    String expected = "Product{id='1', name='Neurosky Mindwave2', description='Brain-computer interface headset', type='HEADSET', active=true}";
    assertEquals(expected, product.toString());
  }

  @Test
  public void testActiveStatusDefaultValue() {
    Product newProduct = new Product("3", "Test Product", "Test Description", "TEST");
    assertTrue(newProduct.isActive());
  }
}
