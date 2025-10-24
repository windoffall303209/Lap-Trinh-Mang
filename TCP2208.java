//sắp xếp các từ
import java.util.*;
import java.io.*;
import java.net.*;
public class Fz1XLIRG {
    public static void sortArr(String[] a){
        Integer[] b = new Integer[a.length];
        for(int i=0;i<a.length;i++){
            b[i] = i;
        }
        Arrays.sort(b, new Comparator<Integer>(){
            @Override
            public int compare(Integer i1, Integer i2){
                String s1 = a[i1];
                String s2 = a[i2];
                if(s1.length()!= s2.length()){
                    return s1.length() - s2.length();
                }
                return i1 - i2;
            };
        });
    }
    public static void main(String[] args) throws IOException{
        // Khai báo
        Socket socket = new Socket("203.162.10.109", 2208);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String ma  = "B22DCCN568;Fz1XLIRG";
        out.write(ma);
        out.newLine();
        out.flush();
        // Nhận dữ liệu từ server
        String s = in.readLine();
        String [] arr = s.trim().split("\\s+");
        Set<Integer> set= new TreeSet<>();
        for (String x : arr) {
            set.add(x.length());
        }
        StringBuilder res = new StringBuilder();
        for(Integer i : set){
            for(String x : arr){
                if(x.length() == i){
                    res.append(x).append(", ");
                }
            }
        }
        res.delete(res.length()-2, res.length());
        String res2 = res.toString();
        // Gửi dữ liệu về server
        out.write(res2);
        out.newLine();
        out.flush();
        System.out.println("Danh sách đã sắp xếp: " + res2);
        // Đóng kết nối
        in.close();
        out.close();
        socket.close();
    }
}
