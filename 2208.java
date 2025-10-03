//Sắp xếp ngược
package UDP;
import java.net.*;
import java.util.*;
public class TVlMUe2 {
    private static final String SERVER_URI = "203.162.10.109"; // Thay đổi nếu cần
    private static final int SERVER_PORT = 2208;
    private static final String STUDENT_CODE = "B22DCCN568"; // THAY BẰNG MÃ SINH VIÊN CỦA BẠN
    private static final String Q_CODE = "9TVlMUe2";
    public static void main(String[] args) throws Exception {
        // 1. Khởi tạo DatagramSocket và địa chỉ Server
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName(SERVER_URI);
        // A. Gửi thông điệp khởi tạo
        String initialMessage = ";" + STUDENT_CODE + ";" + Q_CODE;
        byte[] sendData = initialMessage.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
        clientSocket.send(sendPacket);
        System.out.println("Gửi đi: " + initialMessage);
        // B. Nhận thông điệp từ Server
        byte[] receiveBuffer = new byte[4096];
        DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
        // Chờ nhận gói tin (Blocking call)
        clientSocket.receive(receivePacket);
        String receivedMessage = new String(receiveBuffer, 0, receivePacket.getLength()).trim();
        System.out.println("Nhận về: " + receivedMessage);
        // C. Xử lý thông điệp
        // Tách requestId và data
        String[] parts = receivedMessage.split(";", 2);
        if (parts.length != 2) {
            System.err.println("Lỗi: Thông điệp nhận về không đúng định dạng.");
            clientSocket.close();
            return;
        }
        String requestId = parts[0].trim();
        String data = parts[1].trim();
        // Tách chuỗi data thành mảng từ (phân cách bằng dấu cách)
        String[] words = data.split("\\s+");
        // Sắp xếp các từ theo thứ tự từ điển ngược (Z đến A), case-sensitive
        // Dùng Collections.reverseOrder() để sắp xếp ngược lại
        Arrays.sort(words, Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));
//        Arrays.sort(words, Collections.reverseOrder());
        // Nối các từ đã sắp xếp bằng dấu phẩy (,)
        StringJoiner sj = new StringJoiner(",");
        for (String word : words) {
            sj.add(word);
        }
        String sortedData = sj.toString();
        // Tạo thông điệp phản hồi theo định dạng "requestId;word1,word2,...,wordN"
        String replyMessage = requestId + ";" + sortedData;
        System.out.println("Phản hồi: " + replyMessage);
        // C. Gửi thông điệp phản hồi
        byte[] replyData = replyMessage.getBytes();
        DatagramPacket replyPacket = new DatagramPacket(replyData, replyData.length, serverAddress, SERVER_PORT);
        clientSocket.send(replyPacket);
        clientSocket.close();
        System.out.println("Đã đóng socket và kết thúc chương trình.");
    }
}
