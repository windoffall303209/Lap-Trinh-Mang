// DataInputStream / DataOutputStream
DataOutputStream out = new DataOutputStream(socket.getOutputStream());
out.writeInt(1234);// Ghi số nguyên
out.writeDouble(12.34);// Ghi số thực
out.writeUTF("Hello");// Ghi chuỗi (UTF)
out.flush();
DataInputStream in = new DataInputStream(socket.getInputStream());
int n = in.readInt();// Đọc số nguyên
double d = in.readDouble();// Đọc số thực
String s = in.readUTF();// Đọc chuỗi (UTF)

// InputStream / OutputStream
OutputStream out = socket.getOutputStream();
String ma = "B22DCCN568,BopSnIpf";
String ans = String.valueOf(sum); // Chuyển kiểu dữ liệu từ int ->  string
out.write(ma.getBytes()); // ghi chuỗi mã lên server
out.flush();
InputStream in = socket.getInputStream();
byte[] buffer = new byte[1024];
int bytesRead = in.read(buffer);
String s = new String(buffer, 0, bytesRead).trim();
String [] t = s.split(","); // int num = Integer.parseInt(x); để chuyển ký tự trong mảng t thành số nguyên
// chuyển chuỗi kí tự S thành mảng Int 
int[] a = Arrays.stream(s.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
//Hàm chuyển mảng -> chuỗi
public static String arrayToString(int[] a){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i <a.length; i++){
            sb.append(a[i]);
            sb.append(",");
        }
        sb.delete(sb.length()-1,sb.length());
        return sb.toString();
    }

// BufferedReader / BufferedWriter
BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
bw.write(ma);
bw.newLine(); // Kết thúc dòng
bw.flush();
BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
String line = br.readLine(); // Đọc từng dòng (đến khi gặp ký tự xuống dòng)
String [] arr = s.trim().split(",\\s*");
String[] arr1 = s.split(",");// 1. Tách theo dấu phẩy (giữ khoảng trắng phía sau)
String[] arr2 = s.split(",\\s*");// 2. Tách theo dấu phẩy, bỏ khoảng trắng phía sau
String[] arr3 = s.split("\\s+");// 3. Tách theo khoảng trắng (1 hoặc nhiều space, tab, xuống dòng)
String[] arr4 = s.split("[,;\\s]+");// 4. Tách theo nhiều ký tự phân cách (ví dụ: dấu phẩy, chấm phẩy, khoảng trắng)
String[] arr5 = s.split("[,;\\s|.]+");// 5. Tách theo nhiều loại ký tự phân cách, cả dấu | và dấu chấm
String[] arr6 = s.split("");// 6. Tách từng ký tự (không thường dùng)
String[] arr7 = s.split("\\.");// Tách theo ký tự "." 
System.out.println(s.length());                       // Độ dài chuỗi
System.out.println(s.charAt(2));                      // Lấy ký tự tại vị trí 2 (tính từ 0)
System.out.println(s.trim());                         // Bỏ khoảng trắng đầu/cuối
System.out.println(s.toUpperCase());                  // Đổi toàn bộ thành chữ hoa
System.out.println(s.toLowerCase());                  // Đổi toàn bộ thành chữ thường
System.out.println(s.substring(2, 7));                // Cắt chuỗi con từ vị trí 2 đến 6
System.out.println(s.replace("Java", "String"));      // Thay "Java" thành "String"
System.out.println(s.replaceAll("\\s+", "-"));        // Thay các chuỗi khoảng trắng thành "-"
System.out.println(s.startsWith("  He"));             // Chuỗi có bắt đầu bằng "  He" không?
System.out.println(s.endsWith("!  "));                // Chuỗi có kết thúc bằng "!  " không?
System.out.println(s.contains("World"));              // Chuỗi có chứa "World" không?
System.out.println(s.isEmpty());                      // Chuỗi có rỗng không?

// ObjectInputStream / ObjectOutputStream
ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
oos.writeObject(myObject);
oos.flush();
ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
MyClass obj = (MyClass) ois.readObject();
//Ví dụ
package TCP;
import java.io.Serializable;
public class Product implements Serializable {
    private static final long serialVersionUID = 20231107L;
    public int id;
    public String name;
    public double price;
    public int discount;
    public Product(int id, String name, double price, int discount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = discount;
    }
    public String toString() {
        return String.format("TCP1.Product[id=%d, name=%s, price=%.2f, discount=%d]", id, name,
                price, discount);
    }
}
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





