package UDP;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class BADdSxly {
    public static void main(String[] args) {
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName("203.162.10.109");
            int serverPort = 2207;
            String studentCode = "B22DCCN568";
            String qCode = "BADdSxly";
            String messageA = ";" + studentCode + ";" + qCode;
            byte[] sendDataA = messageA.getBytes();
            DatagramPacket sendPacketA = new DatagramPacket(sendDataA, sendDataA.length, serverAddress, serverPort);
            clientSocket.send(sendPacketA);

            byte[] receiveBuffer = new byte[4096];
            DatagramPacket receivePacketB = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            clientSocket.receive(receivePacketB);
            String responseB = new String(receivePacketB.getData(), 0, receivePacketB.getLength());

            String[] parts = responseB.split(";");
            String requestId = parts[0];
            int[] numbers = Arrays.stream(parts[1].split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
            Arrays.sort(numbers);
            int maxVal = numbers[numbers.length - 1];
            int minVal = numbers[0];
            String messageC = requestId + ";" + maxVal + "," + minVal;
            byte[] sendDataC = messageC.getBytes();
            DatagramPacket sendPacketC = new DatagramPacket(sendDataC, sendDataC.length, serverAddress, serverPort);
            clientSocket.send(sendPacketC);
        } catch (IOException e) {
            System.err.println("Lỗi I/O: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Socket đã đóng. Chương trình kết thúc.");
    }
}
