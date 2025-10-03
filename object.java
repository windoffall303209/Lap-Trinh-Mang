package UDP;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
// Định nghĩa lớp Product (cần thiết để Client có thể Deserialize đối tượng)
class Product implements Serializable {
    private static final long serialVersionUID = 20161107L;
    // Đặt thuộc tính là public để dễ dàng thao tác như trong code mẫu ban đầu
    public String id;
    public String code;
    public String name;
    public int quantity;
    public Product(String id, String code, String name, int quantity) {
        this.id = id; this.code = code; this.name = name; this.quantity = quantity;
    }
    // Các setters (cần thiết cho việc sửa đổi dữ liệu)
    public void setName(String name) { this.name = name; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    @Override
    public String toString() {
        return String.format("Product[id=%s, code=%s, name=%s, quantity=%d]", id, code, name, quantity);
    }
}
public class fghs {
    private static final String HOST = "203.162.10.109";
    private static final int PORT = 2209;
    private static final String STUDENT_CODE = "B22DCCN568"; // Thay bằng MSSV của bạn
    private static final String QCODE = "PWZy1Ru1";
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            // --- BƯỚC 1: GỬI THÔNG ĐIỆP YÊU CẦU ---
            String hello = ";" + STUDENT_CODE + ";" + QCODE;
            byte[] sendBuffer = hello.getBytes(StandardCharsets.UTF_8);
            socket.send(new DatagramPacket(sendBuffer, sendBuffer.length, InetAddress.getByName(HOST), PORT));
            System.out.println("1. Gửi yêu cầu: " + hello);
            // --- BƯỚC 2: NHẬN DỮ LIỆU TỪ SERVER ---
            byte[] buf = new byte[65507]; // Kích thước tối đa cho gói tin UDP
            DatagramPacket recv = new DatagramPacket(buf, buf.length);
            socket.receive(recv);
            // Trích xuất requestId (8 byte đầu)
            String requestId = new String(buf, 0, 8, StandardCharsets.UTF_8).trim();
            System.out.println("2. Nhận requestId: " + requestId);
            // Trích xuất đối tượng Product (từ byte thứ 8 trở đi)
            int objectLength = recv.getLength() - 8;
            Product p = (Product) new ObjectInputStream(new ByteArrayInputStream(buf, 8, objectLength)).readObject();
            System.out.println("   Dữ liệu bị lỗi (Gốc): " + p);
            // --- BƯỚC 3: SỬA TÊN VÀ SỐ LƯỢNG (Hoàn nguyên) ---
            // a. Sửa tên: Đảo ngược từ đầu tiên và từ cuối cùng
            String restoredName = restoreFirstLastWords(p.name);
            p.setName(restoredName);
            // b. Sửa số lượng: Đảo ngược các chữ số
            int restoredQuantity = reverseDigits(p.quantity);
            p.setQuantity(restoredQuantity);
            System.out.println("3. Dữ liệu đã sửa (Hoàn nguyên): " + p);
            // --- BƯỚC 4: GỬI LẠI ĐỐI TƯỢNG ĐÃ SỬA ĐỔI ---
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            // Ghi 8 byte requestId đầu tiên
            bos.write(requestId.getBytes(StandardCharsets.UTF_8));
            // Ghi đối tượng Product đã sửa đổi
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(p);
            oos.flush();
            byte[] sendBack = bos.toByteArray();
            socket.send(new DatagramPacket(sendBack, sendBack.length,
                    InetAddress.getByName(HOST), PORT));
            System.out.println("4. Gửi lại đối tượng đã sửa đổi thành công.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String restoreFirstLastWords(String name) {
        String[] w = name.trim().split("\\s+");    // tách theo mọi khoảng trắng
        if (w.length > 1) {
            // Chỉ đảo (swap) từ đầu tiên và từ cuối cùng
            String temp = w[0];
            w[0] = w[w.length - 1];
            w[w.length - 1] = temp;
        }
        return String.join(" ", w);
    }
    private static int reverseDigits(int n) {
        // Chuyển số sang String, đảo ngược, rồi chuyển lại thành int
        return Integer.parseInt(new StringBuilder("" + n).reverse().toString());
    }
}
