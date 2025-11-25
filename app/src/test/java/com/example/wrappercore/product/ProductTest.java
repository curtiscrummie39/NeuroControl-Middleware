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
    product = new Product("1", "Neurosky Mindwave2", "Brain-computer interface headset", "HEADSET", 0, 32);
  }

  @Test
  public void testProductConstructor() {
    assertNotNull(product);
    assertEquals("1", product.getId());
    assertEquals("Neurosky Mindwave2", product.getName());
    assertEquals("Brain-computer interface headset", product.getDescription());
    assertEquals("HEADSET", product.getType());
    assertTrue(product.isActive());
    assertEquals(0, product.getDeviceId());
    assertEquals(32, product.getChannelCount());
  }

  @Test
  public void testDefaultConstructor() {
    Product emptyProduct = new Product();
    assertNotNull(emptyProduct);
    assertNull(emptyProduct.getId());
    assertNull(emptyProduct.getName());
    assertTrue(emptyProduct.isActive());
    assertEquals(0, emptyProduct.getDeviceId());
    assertEquals(1, emptyProduct.getChannelCount());
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

    product.setDeviceId(1);
    assertEquals(1, product.getDeviceId());

    product.setChannelCount(64);
    assertEquals(64, product.getChannelCount());
  }

  @Test
  public void testToString() {
    String expected = "Product{id='1', name='Neurosky Mindwave2', description='Brain-computer interface headset', type='HEADSET', active=true, deviceId=0, channelCount=32}";
    assertEquals(expected, product.toString());
  }

  @Test
  public void testActiveStatusDefaultValue() {
    Product newProduct = new Product("3", "Test Product", "Test Description", "TEST");
    assertTrue(newProduct.isActive());
    assertEquals(0, newProduct.getDeviceId());
    assertEquals(1, newProduct.getChannelCount());
  }

  @Test
  public void testMindwaveWith32Channels() {
    Product mindwave = new Product("0", "Neurosky Mindwave", "EEG headset with 32 channels", "HEADSET", 0, 32);
    assertEquals("0", mindwave.getId());
    assertEquals(0, mindwave.getDeviceId());
    assertEquals(32, mindwave.getChannelCount());
    assertEquals("HEADSET", mindwave.getType());
  }
}
