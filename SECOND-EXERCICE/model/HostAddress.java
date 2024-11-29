package model;

import java.net.InetAddress;

public interface HostAddress {
  InetAddress getAddress();
  String getHostAddress();
}
