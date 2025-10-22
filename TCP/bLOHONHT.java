package TCP;
import java.io.*;
import java.net.*;
public class bLOHONHT {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("203.162.10.109", 2209); // Đổi localhost thành IP server nếu khác máy
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        // a. Gửi chuỗi mã sv;qCode dạng Object (String)
        String msg = "B22DCCN568;bLOHONHT";
        oos.writeObject(msg);
        oos.flush();
        // b. Nhận đối tượng TCP1.Product từ server
        Product product = (Product) ois.readObject();
        System.out.println("Nhận product từ server: " + product);
        // c. Tính discount
        int intPart = (int) product.price;
        int sum = 0;
        while (intPart > 0) {
            sum += intPart % 10;
            intPart /= 10;
        }
        product.discount = sum;
        System.out.println("TCP1.Product sau khi tính discount: " + product);
        // d. Gửi product đã update về server
        oos.writeObject(product);
        oos.flush();
        oos.close();
        ois.close();
        socket.close();
    }
}
