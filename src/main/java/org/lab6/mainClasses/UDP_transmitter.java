package org.lab6.mainClasses;

import org.lab6.Main;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDP_transmitter {
    public static void send(int port, InetAddress host, Object object){
        DatagramSocket ds=null;
        try {
            ds = new DatagramSocket();
        }catch(SocketException e){
            e.printStackTrace();
        }
        //Serializing Object
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            oos.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        byte[] objBytes = bos.toByteArray();

        //sending serialized object info
        byte[] len={(byte)(objBytes.length-(int)(objBytes.length/128)*128), (byte)(objBytes.length/128)};
        DatagramPacket dp=new DatagramPacket(len, 2, host, port);
        try {
            ds.send(dp);
        }catch (IOException e){
            e.printStackTrace();
        }

        //sending object
        dp=new DatagramPacket(objBytes, objBytes.length, host, port);
        try {
            ds.send(dp);
        }catch (IOException e){
            e.printStackTrace();
        }
        ds.close();
    }

    public static<T> T get(int port){
        DatagramSocket ds=null;
        try {
            ds = new DatagramSocket(port);
        }catch(SocketException e){
            e.printStackTrace();
        }
        //ds.setSoTimeout(1000);
        //getting object info
        byte[] len=new byte[2];
        DatagramPacket dp = new DatagramPacket(len, 2);
        try {
            ds.receive(dp);
        }catch (IOException e){
            e.printStackTrace();
        }
        if(Main.getAdress()==null)
            Main.setInetAdress(dp.getAddress());
        //getting object
        byte[] arr = new byte[len[1]*128+len[0]];
        dp = new DatagramPacket(arr, len[1]*128+len[0]);
        try {
            ds.receive(dp);
        }catch (IOException e){
            e.printStackTrace();
        }
        ds.close();
        //deserializing object
        ByteArrayInputStream bis = new ByteArrayInputStream(arr);
        try {
            ObjectInput in = new ObjectInputStream(bis);
            return (T) in.readObject();
        }catch(IOException e){
            e.printStackTrace();

        }catch (ClassNotFoundException e){
            e.printStackTrace();

        }
        return null;
    }
}
