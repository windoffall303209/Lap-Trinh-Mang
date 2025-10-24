package RMI;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
public class TEst {
    //Nén nhị phân
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry("203.162.10.109");
        ByteService byteService = (ByteService) registry.lookup("RMIByteService");
        byte[] data = byteService.requestData("B22DCCN568", "euYzUv2R");
        ArrayList<Byte> encoded = new ArrayList<>();
        for (int i = 0; i < data.length;) {
            byte value = data[i];
            int count = 0;
            while (i < data.length && data[i] == value) {
                count++;
                i++;
            }
            encoded.add(value);
            encoded.add((byte) count);
        }
        byte[] result = new byte[encoded.size()];
        for (int i = 0; i < encoded.size(); i++) result[i] = encoded.get(i);
        byteService.submitData("B22DCCN557", "euYzUv2R", result);
    }
}

package RMI;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class RMIClient {
    // đổi mảng nhị phân
    public static void main(String[] args) {
        try {
            String host = "203.162.10.109";
            String studentCode = "B22DCCN568";
            String qCode = "POrw930v";
            Registry registry = LocateRegistry.getRegistry(host);
            ByteService service = (ByteService) registry.lookup("RMIByteService");
            byte[] data = service.requestData(studentCode, qCode);

            StringBuilder hex = new StringBuilder();
            for (byte b : data) {
                hex.append(String.format("%02x", b));
            }
            
            String hexStr = hex.toString();
            byte[] hexBytes = hexStr.getBytes();
            service.submitData(studentCode, qCode, hexBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package RMI;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedHashMap;
import java.util.Map;
public class RMICharacterClient {
    //Đếm tần suất xuất hiện
    public static void main(String[] args) {
        try {
            String host = "203.162.10.109";
            String studentCode = "B22DCCN568";
            String qCode = "kbvk2m2q";
            Registry registry = LocateRegistry.getRegistry(host);
            CharacterService service = (CharacterService) registry.lookup("RMICharacterService");
            String input = service.requestCharacter(studentCode, qCode);
            Map<Character, Integer> freq = new LinkedHashMap<>();
            for (char c : input.toCharArray()) {
                freq.put(c, freq.getOrDefault(c, 0) + 1);
            }
            StringBuilder result = new StringBuilder();
            for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
                result.append(entry.getKey()).append(entry.getValue());
            }
            service.submitCharacter(studentCode, qCode, result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
// ProductX.java
package RMI;
import java.io.Serializable;
public class ProductX implements Serializable {
    private static final long serialVersionUID = 20171107L;
    public String id;
    public String code;
    public String discountCode;
    public int discount;
    public ProductX(String id, String code, String discountCode, int discount) {
        this.id = id;
        this.code = code;
        this.discountCode = discountCode;
        this.discount = discount;
    }
}
package RMI;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.Serializable;
public class RMIObjectClient {
    public static void main(String[] args) {
        try {
            String host = "203.162.10.109";
            String studentCode = "B22DCCN568";
            String qAlias = "YNaDxTgp";
            Registry registry = LocateRegistry.getRegistry(host);
            ObjectService service = (ObjectService) registry.lookup("RMIObjectService");
            Serializable obj = service.requestObject(studentCode, qAlias);
            ProductX product = (ProductX) obj;
            int total = 0;
            for (char c : product.discountCode.toCharArray()) {
                if (Character.isDigit(c)) total += (c - '0');
            }
            product.discount = total;
            service.submitObject(studentCode, qAlias, product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
