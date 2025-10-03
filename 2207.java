package UDP;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BADdSxly {
    public static void main(String[] args) {
        // Sử dụng try-with-resources để đảm bảo socket tự động được đóng sau khi hoàn tất.
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName("203.162.10.109"); // Địa chỉ server (localhost)
            int serverPort = 2207;
            // a. Gửi mã sinh viên và mã câu hỏi.
            String studentCode = "B22DCCN568"; //
            String qCode = "BADdSxly";
            String messageA = ";" + studentCode + ";" + qCode;
            byte[] sendDataA = messageA.getBytes();
            DatagramPacket sendPacketA = new DatagramPacket(sendDataA, sendDataA.length, serverAddress, serverPort);
            clientSocket.send(sendPacketA);
            System.out.println("Đã gửi: " + messageA);
            // b. Nhận chuỗi requestId và danh sách số từ server.
            byte[] receiveBuffer = new byte[4096];
            DatagramPacket receivePacketB = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            clientSocket.receive(receivePacketB);
            String responseB = new String(receivePacketB.getData(), 0, receivePacketB.getLength());
            System.out.println("Đã nhận: " + responseB);
            // c. Tách chuỗi, tìm giá trị lớn nhất, nhỏ nhất và gửi lại server.
            String[] parts = responseB.split(";");
            String requestId = parts[0];
            // Chuyển chuỗi số thành một danh sách các số nguyên
            int[] numbers = Arrays.stream(parts[1].split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
            Arrays.sort(numbers);
            int maxVal = numbers[numbers.length - 1];
            int minVal = numbers[0];
//            int maxVal = Arrays.stream(numbers).max().getAsInt();
//            int minVal = Arrays.stream(numbers).min().getAsInt();
            String messageC = requestId + ";" + maxVal + "," + minVal;
            byte[] sendDataC = messageC.getBytes();
            DatagramPacket sendPacketC = new DatagramPacket(sendDataC, sendDataC.length, serverAddress, serverPort);
            clientSocket.send(sendPacketC);
            System.out.println("Đã gửi: " + messageC);
        } catch (IOException e) {
            System.err.println("Lỗi I/O: " + e.getMessage());
            e.printStackTrace();
        }
        // d. Socket được tự động đóng bởi khối try-with-resources, chương trình kết thúc.
        System.out.println("Socket đã đóng. Chương trình kết thúc.");
    }
}
