package com.forescout.challenge;

import lombok.Getter;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Network {


  // USING HashSet IS NOT THREAD-SAFE IN CASE WE WANT TO HAVE  MANY THREADS ACCESING THE HASHSET
  // WE COULD GET AN RACE CONDITION SO ConcurrentHashMap.newKeySet()
  // HAS INCLUDED METHODS TO HANDLE RACE CONDITIONS - THATS WHY I CHANGE THE HASHSET TO ConcurrentHashMap.newKeySet();

  // 1 - CREATION OF ConcurrentHashMap.newKeySet() OF IP IN THE NETWORK:
  //  private final Set<Host> hosts = new HashSet<>();
  private final Set<Host> hosts = ConcurrentHashMap.newKeySet();

  @Getter
  private int amoutIPv4 = 0;
  @Getter
  private int amoutIPv6 = 0;

  public Host getHostByIp(InetAddress inet4Address) {
    return hosts.stream().filter(h -> h.getAddress().equals(inet4Address)).findFirst().orElse(null);
  }

  public Host getHostByName(String hostName) {
    return hosts.stream().filter(h -> h.getHostName().equals(hostName)).findFirst().orElse(null);
  }

  public boolean addHost(Host host) {
    Objects.requireNonNull(host);
    if (getHostByIp(host.getAddress()) != null) {
      throw new IllegalArgumentException("A host with the same IP address already exists");
    }
    if (getHostByName(host.getHostName()) != null) {
      throw new IllegalArgumentException("A host with the same name already exists");
    }

    if (host.calculateIpVersion() ==EnumIpVersion.IPV4) {
      amoutIPv4++;
    } else {
      amoutIPv6++;
    }

    return hosts.add(host);
  }
  public boolean removeHost(Host host) {
    Objects.requireNonNull(host);

    // UPDATE THE COUNTER OF IPV4 OR IPV6 BASSED ON THE HOST
    if (host.calculateIpVersion() == EnumIpVersion.IPV4) {
      amoutIPv4--;
    } else {
      amoutIPv6--;
    }

    return hosts.remove(host);
  }

  public boolean removeHostByIp(InetAddress inet4Address) {
    Host host = getHostByIp(inet4Address);
    if (host != null) {
      return removeHost(host);
    }
    return false;
  }
  // 5 - VERIFY IF THE NETWORK IS IPV4 OR IPV6 OR MIXED:
  public String getNetworkType() {

    // REDUCING COMPLEXITY:
    if (amoutIPv4 > 0 && amoutIPv6 > 0) {
      return "Mixed Network (" + EnumIpVersion.IPV4 + "/" + EnumIpVersion.IPV6 + ")";
    } else if (amoutIPv4 > 0) {
      return EnumIpVersion.IPV4 + " Network";
    } else {
      return  EnumIpVersion.IPV6 + " Network";
    }
  }

  public boolean removeHostByName(String hostName) {
    Host host = getHostByName(hostName);
    if (host != null) {
      return removeHost(host);
    }
    return false;
  }

  public int hostCount() {
    return hosts.size();
  }
}
