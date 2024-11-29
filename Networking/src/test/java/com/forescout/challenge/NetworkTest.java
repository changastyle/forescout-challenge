package com.forescout.challenge;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NetworkTest {

  private final Network network = new Network();

  @AfterEach
  public void afterEach() {
    assertEquals(0, network.hostCount());
  }

  private Host addHost(String addressStr) throws UnknownHostException {
    Inet4Address address = (Inet4Address) Inet4Address.getByName(addressStr);
    // Generate a unique host name based on the address
    String hostName = "host-" + addressStr.replaceAll("\\.", "-");
    Host host = new Host(address, hostName);
    assertTrue(network.addHost(host));
    // Test that the same host cannot be added twice
    assertThrows(IllegalArgumentException.class, () -> network.addHost(host));
    // Test that a host with the same name cannot be added
    assertThrows(IllegalArgumentException.class, () -> network.addHost(
        new Host((Inet4Address) Inet4Address.getByName("1.0.0.1"), hostName)));
    // Test that a host with the same address cannot be added
    assertThrows(IllegalArgumentException.class,
        () -> network.addHost(new Host(address, hostName + "-test")));
    return host;
  }

  private void removeHost(Host host) {
    assertTrue(network.removeHost(host));
    // cannot be removed twice
    assertFalse(network.removeHost(host));
    // Put it back
    assertTrue(network.addHost(host));
    // And remove it by address
    assertTrue(network.removeHostByIp(host.getAddress()));
    // cannot be removed twice
    assertFalse(network.removeHostByIp(host.getAddress()));
    // Put it back
    assertTrue(network.addHost(host));
    // And remove it by name
    assertTrue(network.removeHostByName(host.getHostName()));
    // cannot be removed twice
    assertFalse(network.removeHostByName(host.getHostName()));
  }

  @ParameterizedTest
  @ValueSource(strings = {"1.1.1.1", "1.1.1.2", "1.2.1.1", "1.2.1.2"})
  void testAddAndRemove(String addressStr) throws Exception {
    Host host = addHost(addressStr);
    removeHost(host);
  }



  @Test
  public void testAddAddressIPv4() throws UnknownHostException {

    Network network = new Network();
    String hostname = "hostname-1";
    String hostname2 = "hostname-2";

    String expected = hostname;

    InetAddress ip = Inet4Address.getByName("192.168.0.1");
    InetAddress ip2 = Inet4Address.getByName("192.168.0.2");

    network.addHost(new Host( ip, hostname ));
    network.addHost(new Host( ip2, hostname2 ));

    String actual = network.getHostByIp(ip).getHostName();
    assertEquals(expected,actual);
  }

  @Test
  public void testAddAddressIPv6() throws UnknownHostException {
    InetAddress expectedAddress = InetAddress.getByName("2001:0db8:85a3:0000:0000:8a2e:0370:7334");
    String hostname = "hostname-1";
    String hostname2 = "hostname-2";
    String expected = hostname;

    Network network = new Network();
    InetAddress ip = Inet6Address.getByName("2001:0db8:85a3:0000:0000:8a2e:0370:7334");
    InetAddress ip2 = Inet6Address.getByName("2001:0db8:85a3:0000:0000:8a2e:0370:7335");
    network.addHost(new Host(ip,hostname));
    network.addHost(new Host(ip2,hostname2));

    InetAddress actualAddress = network.getHostByIp(ip).getAddress();
    assertEquals(expectedAddress, actualAddress);
  }

//  @Test
//  public void testNetworkType() throws UnknownHostException {
//
//    String expected = "Mixed Network (IPv4/IPv6)";
//
//    Network network = new Network();
//    network.addHost("192.168.0.1");
//    network.addHost("2001:0db8:85a3:0000:0000:8a2e:0370:7334");
//
//    String actual = network.get();
//    assertEquals(expected, actual);
//  }
//  @Test
//  public void testNetworkTypeIPV4() throws UnknownHostException {
//    String expected = "IPv4 Network";
//
//    Network network = new Network();
//    network.addAddress("192.168.0.1");
//    network.addAddress("192.168.0.2");
//    network.addAddress("192.168.0.3");
//
//    String actual = network.getNetworkType();
//    assertEquals(expected,actual);
//  }
  @Test
  public void testNetworkTypeIPV6() throws UnknownHostException {
    String expected = EnumIpVersion.IPV6+" Network";

    Network network = new Network();

    Host host6 = new Host(Inet6Address.getByName("2001:0db8:85a3:0000:0000:8a2e:0370:7334"),"hostname-1");
    Host host7 = new Host(Inet6Address.getByName("2001:0db8:85a3:0000:0000:8a2e:0370:7335"),"hostname-2");
    Host host8 = new Host(Inet6Address.getByName("2001:0db8:85a3:0000:0000:8a2e:0370:7336"),"hostname-3");
    network.addHost(host6);
    network.addHost(host7);
    network.addHost(host8);


    String actual = network.getNetworkType();
    assertEquals(expected,actual);
  }
  @Test
  public void testAddAndRemoveDevicesFromTheNetwork() throws UnknownHostException {
    Network network = new Network();

    // ADD SOME ADDRESS TO THE NETWORK:
    Host host1 = new Host(Inet4Address.getByName("192.168.0.1"),"hostname-1");
    Host host2 = new Host(Inet4Address.getByName("192.168.0.2"),"hostname-2");
    Host host3 = new Host(Inet4Address.getByName("192.168.0.3"),"hostname-3");
    Host host4 = new Host(Inet4Address.getByName("192.168.0.4"),"hostname-4");
    Host host5 = new Host(Inet4Address.getByName("192.168.0.5"),"hostname-5");
    network.addHost(host1);
    network.addHost(host2);
    network.addHost(host3);
    network.addHost(host4);
    network.addHost(host5);
    Host host6 = new Host(Inet6Address.getByName("2001:0db8:85a3:0000:0000:8a2e:0370:7336"),"hostname-6");
    Host host7 = new Host(Inet6Address.getByName("2001:0db8:85a3:0000:0000:8a2e:0370:7337"),"hostname-7");
    Host host8 = new Host(Inet6Address.getByName("2001:0db8:85a3:0000:0000:8a2e:0370:7338"),"hostname-8");
    network.addHost(host6);
    network.addHost(host7);
    network.addHost(host8);

    // REMOVE 2 ADDRESS:
    network.removeHost(host3);
    network.removeHost(host7);

    int expectedAmountOfIPV4 = 4;
    int expectedAmountOfIPV6 = 2;
    assertEquals(expectedAmountOfIPV4,  network.getAmoutIPv4() );
    assertEquals(expectedAmountOfIPV6,  network.getAmoutIPv6() );
  }
}