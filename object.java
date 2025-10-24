//Lenovo
package UDP;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
class Product implements Serializable {
    private static final long serialVersionUID = 20161107L;
    public String id;
    public String code;
    public String name;
    public int quantity;
    public Product(String id, String code, String name, int quantity) {
        this.id = id; this.code = code; this.name = name; this.quantity = quantity;
    }
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
    private static final String STUDENT_CODE = "B22DCCN568"; 
    private static final String QCODE = "PWZy1Ru1";
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            String hello = ";" + STUDENT_CODE + ";" + QCODE;
            byte[] sendBuffer = hello.getBytes(StandardCharsets.UTF_8);
            socket.send(new DatagramPacket(sendBuffer, sendBuffer.length, InetAddress.getByName(HOST), PORT));
            System.out.println("1. Gửi yêu cầu: " + hello);
            byte[] buf = new byte[65507]; 
            DatagramPacket recv = new DatagramPacket(buf, buf.length);
            socket.receive(recv);
            String requestId = new String(buf, 0, 8, StandardCharsets.UTF_8).trim();
            System.out.println("2. Nhận requestId: " + requestId);
            int objectLength = recv.getLength() - 8;
            Product p = (Product) new ObjectInputStream(new ByteArrayInputStream(buf, 8, objectLength)).readObject();
            System.out.println("   Dữ liệu bị lỗi (Gốc): " + p);
            String restoredName = restoreFirstLastWords(p.name);
            p.setName(restoredName);
            int restoredQuantity = reverseDigits(p.quantity);
            p.setQuantity(restoredQuantity);
            System.out.println("3. Dữ liệu đã sửa : " + p);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bos.write(requestId.getBytes(StandardCharsets.UTF_8));
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(p);
            oos.flush();
            byte[] sendBack = bos.toByteArray();
            socket.send(new DatagramPacket(sendBack, sendBack.length,
                    InetAddress.getByName(HOST), PORT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String restoreFirstLastWords(String name) {
        String[] w = name.trim().split("\\s+");    
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
//Chuẩn hóa khách hàng
package UDP;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
// Định nghĩa class Customer theo đúng yêu cầu đề
class Customer implements Serializable {
    private static final long serialVersionUID = 20151107L;
    public String id;
    public String code;
    public String name;
    public String dayOfBirth;
    public String userName;
    public Customer(String id, String code, String name, String dayOfBirth, String userName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.dayOfBirth = dayOfBirth;
        this.userName = userName;
    }
    @Override
    public String toString() {
        return String.format("Customer[id=%s, code=%s, name=%s, dob=%s, userName=%s]",
                id, code, name, dayOfBirth, userName);
    }
}
public class kjuyht {
    private static final String HOST = "203.162.10.109";
    private static final int PORT = 2209;
    private static final String STUDENT_CODE = "B22DCCN568"; 
    private static final String QCODE = "xXRF175e";
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            // Bước 1: Gửi yêu cầu
            String hello = ";" + STUDENT_CODE + ";" + QCODE;
            byte[] sendBuf = hello.getBytes(StandardCharsets.UTF_8);
            socket.send(new DatagramPacket(sendBuf, sendBuf.length, InetAddress.getByName(HOST), PORT));
            System.out.println("1. Đã gửi yêu cầu: " + hello);
            // Bước 2: Nhận dữ liệu (requestId + object Customer)
            byte[] buf = new byte[4096];
            DatagramPacket recv = new DatagramPacket(buf, buf.length);
            socket.receive(recv);
            String requestId = new String(buf, 0, 8, StandardCharsets.UTF_8).trim();
            // Deserialize object Customer (bắt đầu từ byte 8)
            int objLen = recv.getLength() - 8;
            Customer cus = (Customer) new ObjectInputStream(new ByteArrayInputStream(buf, 8, objLen)).readObject();
            System.out.println("2. Đã nhận: " + cus);
            // Bước 3: Xử lý chuẩn hóa dữ liệu
            cus.name = formatName(cus.name);
            cus.dayOfBirth = convertDate(cus.dayOfBirth);
            cus.userName = makeUsername(cus.name);
            System.out.println("3. Sau xử lý: " + cus);
            // Bước 4: Serialize lại object và gửi lại kèm requestId
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bos.write(requestId.getBytes(StandardCharsets.UTF_8));
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(cus);
            oos.flush();
            byte[] sendBack = bos.toByteArray();
            socket.send(new DatagramPacket(sendBack, sendBack.length, InetAddress.getByName(HOST), PORT));
            System.out.println("4. Đã gửi lại object hoàn chỉnh!");

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Chương trình kết thúc.");
    }
    // DUONG, Nguyen Van Hai
    private static String formatName(String raw) {
        String[] arr = raw.trim().toLowerCase().split("\\s+");
        if (arr.length == 0) return "";
        String lastName = arr[arr.length - 1].toUpperCase();
        StringBuilder mid = new StringBuilder();
        for (int i = 0; i < arr.length - 1; i++) {
            mid.append(Character.toUpperCase(arr[i].charAt(0)));
            if (arr[i].length() > 1) mid.append(arr[i].substring(1));
            if (i < arr.length - 2) mid.append(" ");
        }
        return lastName + (mid.length() > 0 ? ", " + mid : "");
    }
    // mm-dd-yyyy -> dd/mm/yyyy
    private static String convertDate(String dob) {
        String[] parts = dob.split("-");
        if (parts.length != 3) return dob;
        return parts[1] + "/" + parts[0] + "/" + parts[2];
    }
    // nvhduong (từ tên gốc, chưa chuẩn hóa)
    private static String makeUsername(String formattedName) {
        // formattedName: DUONG, Nguyen Van Hai
        // Lấy lại tên gốc bằng cách đảo lại dấu phẩy, tách tên
        String[] mainParts = formattedName.split(",");
        if (mainParts.length != 2) return "";
        String[] arr = (mainParts[1].trim() + " " + mainParts[0].trim().toLowerCase()).toLowerCase().split("\\s+");
        if (arr.length == 0) return "";
        StringBuilder username = new StringBuilder();
        for (int i = 0; i < arr.length - 1; i++) {
            username.append(arr[i].charAt(0));
        }
        username.append(arr[arr.length - 1]);
        return username.toString();
    }
}
// Trao đổi client
package UDP;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
// Định nghĩa class Student đúng như yêu cầu đề
class Student implements Serializable {
    private static final long serialVersionUID = 20171107L;
    public String id;
    public String code;
    public String name;
    public String email;
    public Student(String id, String code, String name, String email) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.email = email;
    }
    public Student(String code) {
        this.code = code;
    }
    @Override
    public String toString() {
        return String.format("Student[id=%s, code=%s, name=%s, email=%s]", id, code, name, email);
    }
}
public class _4elPYvXf {
    private static final String HOST = "203.162.10.109";
    private static final int PORT = 2209;
    private static final String STUDENT_CODE = "B22DCCN568";
    private static final String QCODE = "4elPYvXf";
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            // Bước 1: Gửi yêu cầu
            String hello = ";" + STUDENT_CODE + ";" + QCODE;
            byte[] sendBuf = hello.getBytes(StandardCharsets.UTF_8);
            socket.send(new DatagramPacket(sendBuf, sendBuf.length, InetAddress.getByName(HOST), PORT));
            System.out.println("1. Đã gửi yêu cầu: " + hello);
            // Bước 2: Nhận dữ liệu (requestId + object Student)
            byte[] buf = new byte[4096];
            DatagramPacket recv = new DatagramPacket(buf, buf.length);
            socket.receive(recv);
            String requestId = new String(buf, 0, 8, StandardCharsets.UTF_8).trim();
            // Deserialize object Student (bắt đầu từ byte 8)
            int objLen = recv.getLength() - 8;
            Student stu = (Student) new ObjectInputStream(new ByteArrayInputStream(buf, 8, objLen)).readObject();
            System.out.println("2. Đã nhận: " + stu);
            // Bước 3: Xử lý chuẩn hóa tên và email
            stu.name = capitalizeName(stu.name);
            stu.email = makeEmail(stu.name);
            System.out.println("3. Sau xử lý: " + stu);
            // Bước 4: Serialize lại object và gửi lại kèm requestId
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bos.write(requestId.getBytes(StandardCharsets.UTF_8));
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(stu);
            oos.flush();
            byte[] sendBack = bos.toByteArray();
            socket.send(new DatagramPacket(sendBack, sendBack.length, InetAddress.getByName(HOST), PORT));
            System.out.println("4. Đã gửi lại object hoàn chỉnh!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Chương trình kết thúc.");
    }
    // "nguyen van tuan nam" -> "Nguyen Van Tuan Nam"
    private static String capitalizeName(String raw) {
        String[] arr = raw.trim().toLowerCase().split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String word : arr) {
            if (word.length() > 0) {
                result.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1)
                    result.append(word.substring(1));
                result.append(" ");
            }
        }
        return result.toString().trim();
    }
    // "Nguyen Van Tuan Nam" -> "namnvt@ptit.edu.vn"
    private static String makeEmail(String name) {
        String[] arr = name.trim().toLowerCase().split("\\s+");
        if (arr.length == 0) return "@ptit.edu.vn";
        String lastName = arr[arr.length - 1]; 
        StringBuilder sb = new StringBuilder(lastName);
        for (int i = 0; i < arr.length - 1; i++) {
            sb.append(arr[i].charAt(0));
        }
        sb.append("@ptit.edu.vn");
        return sb.toString();
    }
}
// UDP Book
package UDP;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
// Định nghĩa class Book đúng như đề
class Book implements Serializable {
    private static final long serialVersionUID = 20251107L;
    public String id;
    public String title;
    public String author;
    public String isbn;
    public String publishDate;
    public Book(String id, String title, String author, String isbn, String publishDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishDate = publishDate;
    }
    @Override
    public String toString() {
        return String.format("Book[id=%s, title=%s, author=%s, isbn=%s, publishDate=%s]",
                id, title, author, isbn, publishDate);
    }
}
public class aw3MPqDo {
    private static final String HOST = "203.162.10.109";
    private static final int PORT = 2209;
    private static final String STUDENT_CODE = "B22DCCN568";
    private static final String QCODE = "aw3MPqDo";
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            String hello = ";" + STUDENT_CODE + ";" + QCODE;
            byte[] sendBuf = hello.getBytes(StandardCharsets.UTF_8);
            socket.send(new DatagramPacket(sendBuf, sendBuf.length, InetAddress.getByName(HOST), PORT));
            System.out.println("1. Đã gửi yêu cầu: " + hello);
            byte[] buf = new byte[4096];
            DatagramPacket recv = new DatagramPacket(buf, buf.length);
            socket.receive(recv);
            String requestId = new String(buf, 0, 8, StandardCharsets.UTF_8).trim();
            int objLen = recv.getLength() - 8;
            Book book = (Book) new ObjectInputStream(new ByteArrayInputStream(buf, 8, objLen)).readObject();
            System.out.println("2. Đã nhận: " + book);
            book.title = capitalizeWords(book.title);
            book.author = normalizeAuthor(book.author);
            book.isbn = normalizeISBN(book.isbn);
            book.publishDate = convertDate(book.publishDate);
            System.out.println("3. Sau chuẩn hóa: " + book);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bos.write(requestId.getBytes(StandardCharsets.UTF_8));
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(book);
            oos.flush();
            byte[] sendBack = bos.toByteArray();
            socket.send(new DatagramPacket(sendBack, sendBack.length, InetAddress.getByName(HOST), PORT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Chương trình kết thúc.");
    }
    // Viết hoa chữ cái đầu mỗi từ
    private static String capitalizeWords(String str) {
        String[] arr = str.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : arr) {
            if (word.length() > 0) {
                sb.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1)
                    sb.append(word.substring(1));
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }
    // "nguyen van tuan" -> "nguyen, Tuan Van"
    private static String normalizeAuthor(String author) {
        String[] arr = author.trim().toLowerCase().split("\\s+");
        if (arr.length == 0) return author;
        StringBuilder firstNames = new StringBuilder();
        // Tên (last) đầu tiên, phần còn lại (họ, đệm) sau dấu phẩy
        String lastName = arr[arr.length-1];
        for (int i = 0; i < arr.length-1; i++) {
            firstNames.append(Character.toUpperCase(arr[i].charAt(0)));
            if (arr[i].length() > 1)
                firstNames.append(arr[i].substring(1));
            if (i < arr.length-2) firstNames.append(" ");
        }
        // VD: "nguyen van tuan" -> "Tuan, Nguyen Van"
        // Nhưng đề yêu cầu "Họ, Tên"
        // --> "Nguyen, Tuan Van"
        StringBuilder given = new StringBuilder();
        for (int i = 1; i < arr.length; i++) {
            given.append(Character.toUpperCase(arr[i].charAt(0)));
            if (arr[i].length() > 1)
                given.append(arr[i].substring(1));
            if (i < arr.length-1) given.append(" ");
        }
        return capitalizeFirst(arr[0]) + ", " + given.toString();
    }
    private static String capitalizeFirst(String s) {
        if (s == null || s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + (s.length() > 1 ? s.substring(1) : "");
    }
    private static String normalizeISBN(String isbn) {
        String digits = isbn.replaceAll("[^0-9X]", "");
        if (digits.length() == 13) {
            return String.format("%s-%s-%s-%s-%s",
                    digits.substring(0, 3),  
                    digits.substring(3, 4),  
                    digits.substring(4, 6),  
                    digits.substring(6, 12), 
                    digits.substring(12));   
        }
        return isbn;
    }
    private static String convertDate(String pub) {
        String[] parts = pub.split("-");
        if (parts.length != 3) return pub;
        return parts[1] + "/" + parts[0];
    }
}
//UDP Student
package UDP;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
// Định nghĩa class Student đúng yêu cầu đề
class Student implements Serializable {
    private static final long serialVersionUID = 20171107L;
    public String id;
    public String code;
    public String name;
    public String email;
    public Student(String id, String code, String name, String email) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.email = email;
    }
    public Student(String code) {
        this.code = code;
    }
    @Override
    public String toString() {
        return String.format("Student[id=%s, code=%s, name=%s, email=%s]", id, code, name, email);
    }
}
public class _4elPYvXf {
    private static final String HOST = "203.162.10.109";
    private static final int PORT = 2209;
    private static final String STUDENT_CODE = "B22DCCN568"; // Đổi thành MSSV của bạn
    private static final String QCODE = "4elPYvXf";
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            // 1. Gửi thông điệp yêu cầu
            String hello = ";" + STUDENT_CODE + ";" + QCODE;
            byte[] sendBuf = hello.getBytes(StandardCharsets.UTF_8);
            socket.send(new DatagramPacket(sendBuf, sendBuf.length, InetAddress.getByName(HOST), PORT));
            System.out.println("1. Đã gửi yêu cầu: " + hello);
            // 2. Nhận dữ liệu (requestId + object Student)
            byte[] buf = new byte[4096];
            DatagramPacket recv = new DatagramPacket(buf, buf.length);
            socket.receive(recv);
            String requestId = new String(buf, 0, 8, StandardCharsets.UTF_8).trim();
            // Deserialize object Student (bắt đầu từ byte 8)
            int objLen = recv.getLength() - 8;
            Student stu = (Student) new ObjectInputStream(new ByteArrayInputStream(buf, 8, objLen)).readObject();
            System.out.println("2. Đã nhận: " + stu);
            // 3. Chuẩn hóa name & tạo email
            stu.name = capitalizeName(stu.name);
            stu.email = makeEmail(stu.name);
            System.out.println("3. Sau xử lý: " + stu);
            // 4. Serialize lại object và gửi lại kèm requestId
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bos.write(requestId.getBytes(StandardCharsets.UTF_8));
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(stu);
            oos.flush();
            byte[] sendBack = bos.toByteArray();
            socket.send(new DatagramPacket(sendBack, sendBack.length, InetAddress.getByName(HOST), PORT));
            System.out.println("4. Đã gửi lại object hoàn chỉnh!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Chương trình kết thúc.");
    }
    // "nguyen van tuan nam" -> "Nguyen Van Tuan Nam"
    private static String capitalizeName(String raw) {
        String[] arr = raw.trim().toLowerCase().split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String word : arr) {
            if (word.length() > 0) {
                result.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1)
                    result.append(word.substring(1));
                result.append(" ");
            }
        }
        return result.toString().trim();
    }
    // "Nguyen Van Tuan Nam" -> "namnvt@ptit.edu.vn"
    private static String makeEmail(String name) {
        String[] arr = name.trim().toLowerCase().split("\\s+");
        if (arr.length == 0) return "@ptit.edu.vn";
        String lastName = arr[arr.length - 1];
        StringBuilder sb = new StringBuilder(lastName);
        for (int i = 0; i < arr.length - 1; i++) {
            sb.append(arr[i].charAt(0));
        }
        sb.append("@ptit.edu.vn");
        return sb.toString();
    }
}
//TCP Bài Laptop 
package TCP;
import java.io.Serializable;
public class Laptop implements Serializable {
    private static final long serialVersionUID = 20150711L;
    private int id;
    private String code;
    private String name;
    private int quantity;
    public Laptop(int id, String code, String name, int quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    @Override
    public String toString() {
        return "Laptop [id=" + id + ", code=" + code + ", name='" + name + "', quantity=" + quantity + "]";
    }
}
package TCP;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class LaptopClient {
    private static final int SERVER_PORT = 2209;
    private static final String SERVER_ADDRESS = "203.162.10.109";
    public static void main(String[] args) {
        String studentCode = "B22DCCN568";
        String qCode = "7TQsOLYR";
        String initialData = studentCode + ";" + qCode;
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            oos.writeObject(initialData);
            oos.flush();
            System.out.println("1. Sent initial data: " + initialData);
            Laptop receivedLaptop = (Laptop) ois.readObject();
            System.out.println("2. Received corrupted data: " + receivedLaptop);
            String originalName = receivedLaptop.getName();
            String correctedName = correctName(originalName);
            receivedLaptop.setName(correctedName);
            int originalQuantity = receivedLaptop.getQuantity();
            int correctedQuantity = correctQuantity(originalQuantity);
            receivedLaptop.setQuantity(correctedQuantity);
            oos.writeObject(receivedLaptop);
            oos.flush();
            System.out.println("3. Sent corrected data: " + receivedLaptop);
            System.out.println("4. Socket closed. Program terminated.");
        } catch (Exception e) {
            System.err.println("Client Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static String correctName(String corruptedName) {
        String[] words = corruptedName.split(" ");
        if (words.length < 2) {
            return corruptedName;
        }
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        words[0] = lastWord;
        words[words.length - 1] = firstWord;
        return String.join(" ", words);
    }
    private static int correctQuantity(int corruptedQuantity) {
        String s = String.valueOf(corruptedQuantity);
        StringBuilder sb = new StringBuilder(s);
        String reversedString = sb.reverse().toString();
        return Integer.parseInt(reversedString);
    }
}
// Chuẩn hóa địa chỉ khách hàng TCP
package TCP;
import java.io.Serializable;
public class Address implements Serializable {
    private static final long serialVersionUID = 20180801L;
    private int id;
    private String code;
    private String addressLine;
    private String city;
    private String postalCode;
    public Address(int id, String code, String addressLine, String city, String postalCode) {
        this.id = id;
        this.code = code;
        this.addressLine = addressLine;
        this.city = city;
        this.postalCode = postalCode;
    }
    public String getAddressLine() {
        return addressLine;
    }
    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public int getId() { return id; }
    public String getCode() { return code; }
    public String getCity() { return city; }
    @Override
    public String toString() {
        return "Address [id=" + id + ", code='" + code + "', addressLine='" + addressLine + "', city='" + city + "', postalCode='" + postalCode + "']";
    }
}
package TCP;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
public class AddressClient {
    private static final int SERVER_PORT = 2209;
    private static final String SERVER_ADDRESS = "127.0.0.1";
    public static void main(String[] args) {
        String studentCode = "B22DCCN568";
        String qCode = "hBaw3Nqi";
        String initialData = studentCode + ";" + qCode;
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            oos.writeObject(initialData);
            oos.flush();
            System.out.println("1. Sent initial data: " + initialData);
            Address receivedAddress = (Address) ois.readObject();
            System.out.println("2. Received unnormalized data: " + receivedAddress);
            String originalAddressLine = receivedAddress.getAddressLine();
            String correctedAddressLine = normalizeAddressLine(originalAddressLine);
            receivedAddress.setAddressLine(correctedAddressLine);
            String originalPostalCode = receivedAddress.getPostalCode();
            String correctedPostalCode = normalizePostalCode(originalPostalCode);
            receivedAddress.setPostalCode(correctedPostalCode);
            oos.writeObject(receivedAddress);
            oos.flush();
            System.out.println("3. Sent normalized data: " + receivedAddress);
            System.out.println("4. Socket closed. Program terminated.");
        } catch (Exception e) {
            System.err.println("Client Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static String normalizeAddressLine(String addressLine) {
        if (addressLine == null || addressLine.trim().isEmpty()) {
            return "";
        }
        String cleaned = addressLine.replaceAll("[^a-zA-Z0-9\\s]", " ");
        String normalizedSpaces = cleaned.trim().replaceAll("\\s+", " ");
        StringBuilder result = new StringBuilder();
        String[] words = normalizedSpaces.split(" ");
        for (String word : words) {
            if (word.isEmpty()) {
                continue;
            }
            String capitalizedWord = word.substring(0, 1).toUpperCase() + 
                                     word.substring(1).toLowerCase();
            
            result.append(capitalizedWord).append(" ");
        }
        return result.toString().trim();
    }
    private static String normalizePostalCode(String postalCode) {
        if (postalCode == null) {
            return "";
        }
        return postalCode.replaceAll("[^0-9\\-]", "");
    }
}
