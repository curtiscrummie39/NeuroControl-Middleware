package nexusedge.protocol;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the NexusEdgePortingProtocol class.
 */
public class NexusEdgePortingProtocolTest {

  private NexusEdgePortingProtocol protocol;
  private static final String VALID_PUBLIC_KEY = "bc1q4dpjsdcf8pwnduvqermyw5wrzyaqdpttjg3858";

  @Before
  public void setUp() {
    protocol = new NexusEdgePortingProtocol();
  }

  @Test
  public void testDefaultConstructor() {
    assertNotNull(protocol);
    assertNull(protocol.getAccountPublicKey());
    assertFalse(protocol.isConnected());
  }

  @Test
  public void testConstructorWithPublicKey() {
    NexusEdgePortingProtocol protocolWithKey = new NexusEdgePortingProtocol(VALID_PUBLIC_KEY);
    assertNotNull(protocolWithKey);
    assertEquals(VALID_PUBLIC_KEY, protocolWithKey.getAccountPublicKey());
    assertFalse(protocolWithKey.isConnected());
  }

  @Test
  public void testGetProtocolVersion() {
    assertEquals("SEP-1.0", protocol.getProtocolVersion());
  }

  @Test
  public void testSetAndGetAccountPublicKey() {
    protocol.setAccountPublicKey(VALID_PUBLIC_KEY);
    assertEquals(VALID_PUBLIC_KEY, protocol.getAccountPublicKey());
  }

  @Test
  public void testConnectWithValidKey() {
    protocol.setAccountPublicKey(VALID_PUBLIC_KEY);
    assertTrue(protocol.connect());
    assertTrue(protocol.isConnected());
  }

  @Test
  public void testConnectWithoutKey() {
    assertFalse(protocol.connect());
    assertFalse(protocol.isConnected());
  }

  @Test
  public void testConnectWithEmptyKey() {
    protocol.setAccountPublicKey("");
    assertFalse(protocol.connect());
    assertFalse(protocol.isConnected());
  }

  @Test
  public void testDisconnect() {
    protocol.setAccountPublicKey(VALID_PUBLIC_KEY);
    protocol.connect();
    assertTrue(protocol.isConnected());
    
    protocol.disconnect();
    assertFalse(protocol.isConnected());
  }

  @Test
  public void testValidatePublicKeyValid() {
    assertTrue(protocol.validatePublicKey(VALID_PUBLIC_KEY));
  }

  @Test
  public void testValidatePublicKeyNull() {
    assertFalse(protocol.validatePublicKey(null));
  }

  @Test
  public void testValidatePublicKeyEmpty() {
    assertFalse(protocol.validatePublicKey(""));
  }

  @Test
  public void testValidatePublicKeyWrongPrefix() {
    assertFalse(protocol.validatePublicKey("1q4dpjsdcf8pwnduvqermyw5wrzyaqdpttjg3858"));
  }

  @Test
  public void testValidatePublicKeyTooShort() {
    assertFalse(protocol.validatePublicKey("bc1q123"));
  }

  @Test
  public void testValidatePublicKeyTooLong() {
    String tooLong = "bc1q" + "a".repeat(100);
    assertFalse(protocol.validatePublicKey(tooLong));
  }

  @Test
  public void testMultipleConnectDisconnectCycles() {
    protocol.setAccountPublicKey(VALID_PUBLIC_KEY);
    
    for (int i = 0; i < 3; i++) {
      assertTrue(protocol.connect());
      assertTrue(protocol.isConnected());
      
      protocol.disconnect();
      assertFalse(protocol.isConnected());
    }
  }
}
