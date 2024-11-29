package model;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPv4HostAddress implements HostAddress {
  private InetAddress address;

  public IPv4HostAddress(String ip) throws UnknownHostException {
    this.address = Inet4Address.getByName(ip);
  }

  @Override
  public InetAddress getAddress() {
    return address;
  }

  @Override
  public String getHostAddress() {
    return address.getHostAddress();
  }
  @Override
  public String toString() {
    return "IPv4 Address: " + address.getHostAddress();
  }
}