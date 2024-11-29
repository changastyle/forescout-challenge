package model;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPv6HostAddress implements HostAddress {
  private InetAddress address;

  public IPv6HostAddress(String ip) throws UnknownHostException {
    this.address = Inet6Address.getByName(ip);
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
    return "IPv6 Address: " + address.getHostAddress();
  }
}