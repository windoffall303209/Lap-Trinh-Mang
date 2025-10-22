package RMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class euYzUv2R {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("203.162.10.109");
        ByteService byteService = (ByteService) registry.lookup("RMIByteService");
        byte[] receive = byteService.requestData("B22DCCN557", "euYzUv2R");
        ArrayList<Byte> arr = new ArrayList<>();
        for(int i = 0;i < receive.length;i++){
            System.out.print(receive[i] + " ");
        }
        System.out.println("");
        for(int i = 0;i < receive.length;i++){
            byte cur = receive[i];
            int curPos = i;
            while(i < receive.length && receive[i] == cur){
                i++;
            }
            arr.add(cur);
            arr.add((byte)(i - curPos));
            i--;
        }
        for(byte x : arr){
            System.out.print(x + " ");
        }
        byte[] send = new byte[arr.size()];
        for(int i = 0;i < arr.size();i++){
            send[i] = arr.get(i);
        }
        byteService.submitData("B22DCCN557", "euYzUv2R", send);
    }
}