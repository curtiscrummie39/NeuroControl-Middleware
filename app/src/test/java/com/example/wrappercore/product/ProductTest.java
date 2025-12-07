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
    String expected = "Product{id='1', name='Neurosky Mindwave2', description='Brain-computer interface headset', type='HEADSET', active=true, deviceId=0, channelCount=32, networkGeneration='null'}";
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

  @Test(expected = IllegalArgumentException.class)
  public void testSetNegativeDeviceId() {
    product.setDeviceId(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetZeroChannelCount() {
    product.setChannelCount(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetNegativeChannelCount() {
    product.setChannelCount(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNegativeDeviceId() {
    new Product("1", "Test", "Test", "TEST", -1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithZeroChannelCount() {
    new Product("1", "Test", "Test", "TEST", 0, 0);
  }

  @Test
  public void test8GPhoneProduct() {
    Product phone8g = new Product(
        "3",
        "NeuroPhone 8G",
        "8G-enabled phone with magical abilities",
        "PHONE",
        1,
        1,
        "8G"
    );
    assertEquals("3", phone8g.getId());
    assertEquals("NeuroPhone 8G", phone8g.getName());
    assertEquals("8G-enabled phone with magical abilities", phone8g.getDescription());
    assertEquals("PHONE", phone8g.getType());
    assertTrue(phone8g.isActive());
    assertEquals(1, phone8g.getDeviceId());
    assertEquals(1, phone8g.getChannelCount());
    assertEquals("8G", phone8g.getNetworkGeneration());
  }

  @Test
  public void testPrinterProduct() {
    Product printer = new Product(
        "4",
        "NeuroPrinter Pro",
        "Magical printer controlled by brain signals",
        "PRINTER"
    );
    assertEquals("4", printer.getId());
    assertEquals("NeuroPrinter Pro", printer.getName());
    assertEquals("Magical printer controlled by brain signals", printer.getDescription());
    assertEquals("PRINTER", printer.getType());
    assertTrue(printer.isActive());
    assertEquals(0, printer.getDeviceId());
    assertEquals(1, printer.getChannelCount());
    assertNull(printer.getNetworkGeneration());
  }

  @Test
  public void testNetworkGenerationSetterGetter() {
    Product phone = new Product("5", "Test Phone", "Test", "PHONE");
    assertNull(phone.getNetworkGeneration());
    
    phone.setNetworkGeneration("8G");
    assertEquals("8G", phone.getNetworkGeneration());
    
    phone.setNetworkGeneration("6G");
    assertEquals("6G", phone.getNetworkGeneration());
  }

  @Test
  public void testToStringWithNetworkGeneration() {
    Product phone8g = new Product(
        "3",
        "NeuroPhone 8G",
        "8G-enabled phone",
        "PHONE",
        1,
        1,
        "8G"
    );
    String expected = "Product{id='3', name='NeuroPhone 8G', description='8G-enabled phone', type='PHONE', active=true, deviceId=1, channelCount=1, networkGeneration='8G'}";
    assertEquals(expected, phone8g.toString());
  }
}
