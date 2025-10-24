
import java.io.*;
import java.net.*;
import java.util.*;
public class YqQK4zTR {
    public static void main(String[] args) throws IOException{
        // Khai báo
        Socket socket = new Socket("203.162.10.109", 2206);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        String ma = "B22DCCN568;HqFLCUMJ";
        out.write(ma.getBytes());
        out.flush();
        // Nhận dữ liệu từ server
        byte [] buffer = new byte[1024];
        int bytesRead = in.read(buffer);
        String s = new String(buffer, 0 , bytesRead).trim();
        int n = Integer.parseInt(s);
        // Xử lý dữ liệu
        int count = 1;
        String ans = "";
        while (n != 1){
            ans += n + " ";
            count++;
            if(n%2==0){
                n = n/2;
            } else {
                n = 3*n + 1;
            }
        }
        ans += "1; " + count ;
        out.write(ans.getBytes());
        // Gửi dữ liệu về server
        out.flush();
        // Đóng kết nối
        in.close();
        out.close();
        socket.close();
    }

}
