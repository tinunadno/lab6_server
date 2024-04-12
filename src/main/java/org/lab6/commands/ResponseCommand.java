package org.lab6.commands;

import java.net.InetAddress;

public interface ResponseCommand {
    void setPort(int port);
    void setAddress(InetAddress address);
}
