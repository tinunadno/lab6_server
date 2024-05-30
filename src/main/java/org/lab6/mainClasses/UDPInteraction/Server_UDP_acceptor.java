package org.lab6.mainClasses.UDPInteraction;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Server_UDP_acceptor implements Runnable {
    DatagramChannel channel;
    InetSocketAddress address;
    ByteBuffer buffer;
    ByteBuffer clientAcceptBuffer;
    InetSocketAddress clientAddress;

    public Server_UDP_acceptor(int port) {
        try {
            channel = DatagramChannel.open();
            address = new InetSocketAddress(port);
            channel.bind(address);
            buffer = ByteBuffer.allocate(10000);
            clientAcceptBuffer = ByteBuffer.allocate(10000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        send(userSendingAddress, sendingObject);
    }

    public void send(SocketAddress address, Object sendingObject) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(sendingObject);
            oos.close();
            ByteBuffer buffer = ByteBuffer.wrap(bos.toByteArray());
            channel.send(buffer, address);
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Object sendingObject;
    private SocketAddress userSendingAddress;

    public void setUserSendingAddress(SocketAddress address) {
        this.userSendingAddress = address;
    }

    public void setSendingObject(Object obj) {
        this.sendingObject = obj;
    }

    public Object get() {
        try {
            clientAddress = (InetSocketAddress) channel.receive(clientAcceptBuffer);
            clientAcceptBuffer.flip();
            int limits = clientAcceptBuffer.limit();
            byte bytes[] = new byte[limits];
            clientAcceptBuffer.get(bytes, 0, limits);
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInput in = new ObjectInputStream(bis);
            Object ret = in.readObject();
            clientAcceptBuffer.clear();
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public InetSocketAddress getUserAdress() {
        return clientAddress;
    }

    public void clearChannel() {
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}