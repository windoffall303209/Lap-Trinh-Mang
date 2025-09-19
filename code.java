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

