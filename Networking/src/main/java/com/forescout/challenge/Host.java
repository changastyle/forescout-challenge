package com.forescout.challenge;

import lombok.Getter;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Objects;

public class Host {

  @Getter
  private InetAddress address;
  @Getter
  private String hostName;
  private EnumIpVersion ipVersion;

  public Host(InetAddress address, String hostName) {
    setAddress(address);
    setHostName(hostName);
    ipVersion = calculateIpVersion();
  }

  public void setAddress(InetAddress  address) {
    this.address = Objects.requireNonNull(address);
  }

  public void setHostName(String hostName) {
    this.hostName = Objects.requireNonNull(hostName);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Host)) {
      return false;
    }
    Host host = (Host) o;
    return address.equals(host.address) && hostName.equalsIgnoreCase(host.hostName);
  }

  public EnumIpVersion calculateIpVersion() {
    if (address instanceof Inet4Address) {
      return EnumIpVersion.IPV4;
    } else if (address instanceof Inet6Address) {
      return EnumIpVersion.IPV6;
    } else {
      return EnumIpVersion.IPV4;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(address, hostName);
  }
}
