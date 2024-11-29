package main;

import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class NetworkTest {

  @Test
  public void testAddAddressIPv4() throws UnknownHostException {
    String expected = "192.168.0.1";

    Network network = new Network();
    network.addAddress("192.168.0.1");

    String actual = network.getAddress("192.168.0.1").getHostAddress();
    assertEquals(expected,actual);
  }

  @Test
  public void testAddAddressIPv6() throws UnknownHostException {
    InetAddress expectedAddress = InetAddress.getByName("2001:0db8:85a3:0000:0000:8a2e:0370:7334");

    Network network = new Network();
    network.addAddress("2001:0db8:85a3:0000:0000:8a2e:0370:7334");

    InetAddress actualAddress = network.getAddress("2001:0db8:85a3:0000:0000:8a2e:0370:7334").getAddress();
    assertEquals(expectedAddress, actualAddress);
  }

  @Test
  public void testNetworkType() throws UnknownHostException {

    String expected = "Mixed Network (IPv4/IPv6)";

    Network network = new Network();
    network.addAddress("192.168.0.1");
    network.addAddress("2001:0db8:85a3:0000:0000:8a2e:0370:7334");

    String actual = network.getNetworkType();
    assertEquals(expected, actual);
  }
  @Test
  public void testNetworkTypeIPV4() throws UnknownHostException {
    String expected = "IPv4 Network";

    Network network = new Network();
    network.addAddress("192.168.0.1");
    network.addAddress("192.168.0.2");
    network.addAddress("192.168.0.3");

    String actual = network.getNetworkType();
    assertEquals(expected,actual);
  }
  @Test
  public void testNetworkTypeIPV6() throws UnknownHostException {
    String expected = "IPv6 Network";

    Network network = new Network();
    network.addAddress("2001:0db8:85a3:0000:0000:8a2e:0370:7334");
    network.addAddress("2001:0db8:85a3:0000:0000:8a2e:0370:7335");
    network.addAddress("2001:0db8:85a3:0000:0000:8a2e:0370:7336");

    String actual = network.getNetworkType();
    assertEquals(expected,actual);
  }
  @Test
  public void testAddAndRemoveDevicesFromTheNetwork() throws UnknownHostException {
    String expected = "IPv6 Network";

    Network network = new Network();

    // ADD SOME ADDRESS TO THE NETWORK:
    network.addAddress("192.168.0.1");
    network.addAddress("192.168.0.2");
    network.addAddress("192.168.0.3");
    network.addAddress("192.168.0.4");
    network.addAddress("192.168.0.5");
    network.addAddress("2001:0db8:85a3:0000:0000:8a2e:0370:7334");
    network.addAddress("2001:0db8:85a3:0000:0000:8a2e:0370:7335");
    network.addAddress("2001:0db8:85a3:0000:0000:8a2e:0370:7336");

    // REMOVE 2 ADDRESS:
    network.removeAddress("192.168.0.3");
    network.removeAddress("2001:0db8:85a3:0000:0000:8a2e:0370:7335");

    int expectedAmountOfIPV4 = 4;
    int expectedAmountOfIPV6 = 2;
    assertEquals(expectedAmountOfIPV4,  network.getAmoutIPv4() );
    assertEquals(expectedAmountOfIPV6,  network.getAmoutIPv6() );
  }
}