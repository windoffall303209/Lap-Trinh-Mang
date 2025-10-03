package UDP;

import java.net.*;
import java.io.*;
import java.util.*;

public class btFdvFTJ {
    public static String chuanhoa(String s){
        StringBuilder sb = new StringBuilder();
        String words[] = s.trim().toLowerCase().split("\\s+");
        for(int i=0; i<words.length; i++){
            sb.append(Character.toUpperCase(words[i].charAt(0))).append(words[i].substring(1));
            if(i < words.length - 1) sb.append(" ");
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        try(DatagramSocket socket = new DatagramSocket()) {
//          thông tin server
            String qcode = "btFdvFTJ";
            String msv = "B22DCCN568";
            String serverAddress = "203.162.10.109";
            int serverPort = 2208;
            InetAddress server = InetAddress.getByName(serverAddress);
//          gửi thông điệp
            String messageToSend = ";" + msv + ";" + qcode;
            byte[] sendData = messageToSend.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, server, serverPort);
            socket.send(sendPacket);
            System.out.println("Sent to server: " + messageToSend);
//          nhận thông điệp
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            String response = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
            System.out.println("Received from server: " + response);
//          xử lý thông điệp
            String parts[] = response.split(";");
            String requestId = parts[0];
            String hoten = chuanhoa(parts[1]);
//          gửi kết quả về server
            String messageToReply = requestId + ";" + hoten;
            byte[] replyData = messageToReply.getBytes();
            DatagramPacket replyPacket = new DatagramPacket(replyData, replyData.length, server, serverPort);
            socket.send(replyPacket);
            System.out.println("Sent to server: " + messageToReply);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
