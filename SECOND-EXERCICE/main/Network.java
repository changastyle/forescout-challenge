package main;

import lombok.Data;
import lombok.Getter;
import model.HostAddress;
import model.IPv4HostAddress;
import model.IPv6HostAddress;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Network {

  @Getter
  private int amoutIPv4 = 0;
  @Getter
  private int amoutIPv6 = 0;

  // FORMAT OF A IPV4: 192.168.1.1 - 4 GROUPS OF 3 DIGITS DECIMAL
  // FORMAT OF A IPV6: 2001:0db8:85a3:0000:0000:8a2e:0370:7334 - 8 GROUPS OF 4 DIGITS HEXA
  // FORMAT OF A MAC ADRESS : 00:14:22:01:23:45 - 6 GROUPS OF 2 DIGITS HEXA


  // USING ConcurrentHashMap MAKE THIS CLASS NETWORK THREAD-SAFE IN CASE WE HAVE MANY THREADS
  // ACCESING THE MAP WE COULD GET AN RACE CONDITION SO ConcurrentHashMap HAS INCLUDED
  // METHODS TO HANDLE RACE CONDITIONS..

  // 1 - CREATION OF CONCURRENT HASHMAP OF IP RELATED TO AN MAC ADDRESS:
  private Map<String, HostAddress> ipToHostAddressMap = new ConcurrentHashMap<>();

  // 2 - ATTTACH NEW IP TO A MAC ADDRESS
  public void addAddress(String ip) throws UnknownHostException {
    HostAddress address;
    if (ip.contains(":")) {
      address = new IPv6HostAddress(ip);
      amoutIPv6++;
    } else {
      address = new IPv4HostAddress(ip);
      amoutIPv4++;
    }
    ipToHostAddressMap.put(ip, address);
  }

  // 3 - REMOVE AN ADDRESS FROM THE NETWORK AND DISCOUNT THE COUNTERS..
  public boolean removeAddress(String ip) {
    boolean rm = false;
    HostAddress address = ipToHostAddressMap.remove(ip);
    if (address != null) {
      if (address instanceof IPv4HostAddress) {
        amoutIPv4--;
      } else if (address instanceof IPv6HostAddress) {
        amoutIPv6--;
      }
      rm = true;
    }
    return rm;
  }

  // 4 - GET A MAC-ADDRESS BY IP:
  public HostAddress getAddress(String ip) {
    return ipToHostAddressMap.get(ip);
  }

  // 5 - VERIFY IF THE NETWORK IS IPV4 OR IPV6 OR MIXED:
  public String getNetworkType() {

    // REDUCING COMPLEXITY:
    if (amoutIPv4 > 0 && amoutIPv6 > 0) {
      return "Mixed Network (IPv4/IPv6)";
    } else if (amoutIPv4 > 0) {
      return "IPv4 Network";
    } else {
      return "IPv6 Network";
    }
    //    boolean hasIPv4 = false;
    //    boolean hasIPv6 = false;
    //    for (HostAddress address : ipToHostAddressMap.values()) {
    //      if (address instanceof IPv4HostAddress) {
    //        hasIPv4 = true;
    //      } else if (address instanceof IPv6HostAddress) {
    //        hasIPv6 = true;
    //      }
    //    }
    //
    //    if (hasIPv4 && hasIPv6) {
    //      return "Mixed Network (IPv4/IPv6)";
    //    } else if (hasIPv4) {
    //      return "IPv4 Network";
    //    } else {
    //      return "IPv6 Network";
    //    }
  }
}
