//Data Stream
import java.util.*;
import java.io.*;
import java.net.*;
public class h9wUSsFlX {
    public static String arrayToString(int[] a){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i <a.length; i++){
            sb.append(a[i]);
            sb.append(",");
        }
        sb.delete(sb.length()-1,sb.length());
        return sb.toString();
    }
    public static void CatMang(int [] a, int k){
        int n = a.length;
        for(int i = 0; i < n; i+=k){
            int left = i;
            int right = Math.min(i+k-1,n-1);
            while(left < right){
                int temp = a[left];
                a[left] = a[right];
                a[right] = temp;
                left++;
                right--;
            }
        }
    }
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("203.162.10.109", 2207);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        String ma = "B22DCCN568;9wUSsFlX";
        out.writeUTF(ma);
        out.flush();
        int k = in.readInt();
        String arr = in.readUTF();
        System.out.println("Số cần cắt" +k);

        int[] a = Arrays.stream(arr.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
        System.out.println("Mảng a là" +Arrays.toString(a));
        CatMang(a, k);
        String res = arrayToString(a);
        System.out.println("Kết quả" + res);
        out.writeUTF(res);
        out.flush();
        in.close();
        out.close();
        socket.close();
    }
}
//Character Stream
import java.io.*;
import java.net.*;
public class Check {
    // Đảo ngược từ
    public static String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }
    // RLE cho 1 từ
    public static String RLE(String s) {
        StringBuilder sb = new StringBuilder();
        int n = s.length();
        for (int i = 0; i < n; ) {
            char c = s.charAt(i);
            int count = 1;
            int j = i + 1;
            while (j < n && s.charAt(j) == c) {
                count++;
                j++;
            }
            sb.append(c);
            if (count > 1) sb.append(count);
            i = j;
        }
        return sb.toString();
    }
    public static void main(String[] args) throws IOException {
        String host = "203.162.10.109";
        int port = 2208;
        String studentCode = "B22DCCN568";
        String qCode = "rSLVtWie";
        Socket socket = new Socket(host, port);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        // a. Gửi chuỗi mã sinh viên và mã câu hỏi
        out.write(studentCode + ";" + qCode);
        out.newLine(); // Cực kỳ quan trọng!
        out.flush();
        // b. Nhận chuỗi từ server
        String line = in.readLine();
        System.out.println("Received: " + line);
        // c. Đảo ngược và RLE từng từ
        String[] words = line.trim().split("\\s+");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String rev = reverse(words[i]);
            String encoded = RLE(rev);
            result.append(encoded);
            if (i != words.length - 1) result.append(" ");
        }
        // Gửi chuỗi đã xử lý lên server
        out.write(result.toString());
        out.newLine();
        out.flush();
        // Đóng kết nối
        in.close();
        out.close();
        socket.close();
    }
}
// Byte Stream
import java.util.*;
import java.io.*;
import java.net.*;
public class h9wUSsFlX {
    public static String arrayToString(int[] a){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i <a.length; i++){
            sb.append(a[i]);
            sb.append(",");
        }
        sb.delete(sb.length()-1,sb.length());
        return sb.toString();
    }
    public static void CatMang(int [] a, int k){
        int n = a.length;
        for(int i = 0; i < n; i+=k){
            int left = i;
            int right = Math.min(i+k-1,n-1);
            while(left < right){
                int temp = a[left];
                a[left] = a[right];
                a[right] = temp;
                left++;
                right--;
            }
        }
    }
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("203.162.10.109", 2207);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        String ma = "B22DCCN568;9wUSsFlX";
        out.writeUTF(ma);
        out.flush();
        int k = in.readInt();
        String arr = in.readUTF();
        System.out.println("Số cần cắt" +k);
        int[] a = Arrays.stream(arr.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
        System.out.println("Mảng a là" +Arrays.toString(a));
        CatMang(a, k);
        String res = arrayToString(a);
        System.out.println("Kết quả" + res);
        out.writeUTF(res);
        out.flush();
        in.close();
        out.close();
        socket.close();
    }
}
// Object Stream File class
package TCP;
import java.io.Serializable;
// Lớp Address phải implements Serializable để có thể gửi qua luồng đối tượng
public class Address implements Serializable {
    // serialVersionUID phải khớp với server để đảm bảo tương thích
    private static final long serialVersionUID = 20180801L;
    private int id;
    private String code;
    private String addressLine;
    private String city;
    private String postalCode;
    // Constructor, getters và setters
    public Address(int id, String code, String addressLine, String city, String postalCode) {
        this.id = id;
        this.code = code;
        this.addressLine = addressLine;
        this.city = city;
        this.postalCode = postalCode;
    }
    // Getters
    public int getId() { return id; }
    public String getCode() { return code; }
    public String getAddressLine() { return addressLine; }
    public String getCity() { return city; }
    public String getPostalCode() { return postalCode; }
    // Setters
    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", addressLine='" + addressLine + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
} //File main
package TCP; // Import lớp Address từ package TCP
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
public class Client_iYU7RXAZ {
    public static void main(String[] args) {
        final String SERVER_IP = "203.162.10.109";
        final int SERVER_PORT = 2209;
        final int TIMEOUT = 5000;
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT)) {
            socket.setSoTimeout(TIMEOUT);
            // Quan trọng: Khởi tạo ObjectOutputStream TRƯỚC ObjectInputStream
            // để tránh deadlock khi cả client và server cùng chờ nhau.
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("✅ Đã kết nối tới server: " + SERVER_IP + " tại cổng " + SERVER_PORT);
            // a. Gửi đối tượng chuỗi chứa mã sinh viên và mã câu hỏi
            String studentCode = "B22DCCN568"; // <<<--- THAY MÃ SINH VIÊN CỦA BẠN VÀO ĐÂY
            String qCode = "iYU7RXAZ";
            String initialObject = studentCode + ";" + qCode;
            oos.writeObject(initialObject);
            oos.flush();
            System.out.println("-> Đã gửi object: \"" + initialObject + "\"");
            // b. Nhận đối tượng Address và thực hiện chuẩn hóa
            Address receivedAddress = (Address) ois.readObject();
            System.out.println("<- Đã nhận object: " + receivedAddress);
            // Chuẩn hóa addressLine
            String rawAddressLine = receivedAddress.getAddressLine();
            String normalizedAddressLine = normalizeAddressLine(rawAddressLine);
            receivedAddress.setAddressLine(normalizedAddressLine);
            // Chuẩn hóa postalCode
            String rawPostalCode = receivedAddress.getPostalCode();
            String normalizedPostalCode = rawPostalCode.replaceAll("[^0-9-]", "");
            receivedAddress.setPostalCode(normalizedPostalCode);
            System.out.println("🔄 Đã chuẩn hóa object: " + receivedAddress);
            // c. Gửi đối tượng đã được chuẩn hóa lên server
            oos.writeObject(receivedAddress);
            oos.flush();
            System.out.println("-> Đã gửi object đã chuẩn hóa.");
            System.out.println("✨ Hoàn thành tác vụ.");
            // d. Đóng kết nối (try-with-resources sẽ tự động đóng oos, ois và socket)
        } catch (UnknownHostException e) {
            System.err.println(" Lỗi: Không tìm thấy server tại địa chỉ " + SERVER_IP);
        } catch (IOException e) {
            System.err.println(" Lỗi I/O: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println(" Lỗi: Không tìm thấy class 'TCP.Address' khi nhận đối tượng.");
            e.printStackTrace();
        } finally {
            System.out.println("🔌 Đã đóng kết nối và kết thúc chương trình.");
        }
    }
    public static String normalizeAddressLine(String address) {
        if (address == null || address.trim().isEmpty()) {
            return "";
        }
        // 1. Loại bỏ các ký tự không phải là chữ, số hoặc khoảng trắng
        String cleaned = address.replaceAll("[^a-zA-Z0-9\\s]", "");
        // 2. Loại bỏ khoảng trắng thừa ở đầu/cuối và giữa các từ
        String trimmed = cleaned.trim().replaceAll("\\s+", " ");

        String[] words = trimmed.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                // Viết hoa chữ cái đầu tiên và viết thường phần còn lại
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        // Xóa khoảng trắng cuối cùng
        return result.toString().trim();
    }
}
