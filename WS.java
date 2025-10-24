// Hệ cơ số 8 và 16
package ws;
import java.util.ArrayList;
import java.util.List;
import dataws.DataService;
import dataws.DataService_Service;
public class Data1 {
    public static void main(String[] args) throws Exception {
        String studentCode = "B22DCCN711"; 
        String qCode = "8kWyCeyY"; 
        DataService_Service service = new DataService_Service();
        DataService sv = service.getDataServicePort();
        List<Integer> receivedData = sv.getData(studentCode, qCode);
        List<String> resultList = new ArrayList<>();
        // b. Chuyển đổi cơ số và tạo danh sách chuỗi
        for (Integer number : receivedData) {
            if (number == null) continue;
            String octalString = Integer.toString(number, 8);
            String hexString = Integer.toString(number, 16).toUpperCase();
            String combinedString = octalString + "|" + hexString;
            resultList.add(combinedString);
        }
        sv.submitDataStringArray(studentCode, qCode, resultList);
    }
}
// Data nhị phân
package ws;
import dataws.DataService;
import dataws.DataService_Service;
import java.util.ArrayList;
import java.util.List;
public class main {
    public static void main(String[] args) {
        String msv = "B22DCCN557";
        String qCode = "zvMyZZas";
        DataService_Service service = new DataService_Service();
        DataService sv = service.getDataServicePort();
        List<Integer> list = sv.getData(msv, qCode);
        List<String> subList = new ArrayList<>();
        for(int x : list){
            subList.add(Integer.toString(x, 2));
        }
        sv.submitDataStringArray(msv, qCode, subList);
    }
    
}

//HelloWorldExample Character WS
package ws;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import vn.medianews.CharacterService;
import vn.medianews.CharacterService_Service;
public class character {
    private static String toTitleCase(String word) {
        if (word == null || word.isEmpty()) return "";
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
    private static List<String> getWords(String s) {
        if (s == null || s.isEmpty()) return new ArrayList<>();
        String normalizedString = s.replaceAll("[\\s_]+", " ").trim();
        if (normalizedString.isEmpty()) return new ArrayList<>();
        return Arrays.asList(normalizedString.split(" "));
    }
    public static void main(String[] args) {
        String studentCode = "B22DCCN568";
        String qCode = "JzbwHMD6";
        CharacterService_Service service = new CharacterService_Service();
        CharacterService sv = service.getCharacterServicePort();
        String receivedString = sv.requestString(studentCode, qCode);
        List<String> words = getWords(receivedString);
        List<String> resultList = new ArrayList<>();
        StringBuilder pascalSb = new StringBuilder();
        for (String word : words) {
            pascalSb.append(toTitleCase(word));
        }
        String pascalCase = pascalSb.toString();
        resultList.add(pascalCase);
        StringBuilder camelSb = new StringBuilder();
        if (!words.isEmpty()) {
            camelSb.append(words.get(0).toLowerCase());
            for (int i = 1; i < words.size(); i++) {
                camelSb.append(toTitleCase(words.get(i)));
            }
        }
        String camelCase = camelSb.toString();
        resultList.add(camelCase);
        StringBuilder snakeSb = new StringBuilder();
        for (int i = 0; i < words.size(); i++) {
            snakeSb.append(words.get(i).toLowerCase());
            if (i < words.size() - 1) {
                snakeSb.append("_");
            }
        }
        String snakeCase = snakeSb.toString();
        resultList.add(snakeCase);
        sv.submitCharacterStringArray(studentCode, qCode, resultList);
    }
}

//Object nhân viên
package ws;
import ObjectWS.Employee;
import ObjectWS.EmployeeY;
import ObjectWS.ObjectService;
import ObjectWS.ObjectService_Service;
import java.util.Comparator;
import java.util.List;

public class WSClientEmployeeY {
    public static void main(String[] args) {
        String msv = "B22DCCN568";
        String qCode = "bFd6Eayn";
        ObjectService_Service service = new ObjectService_Service();
        ObjectService sv = service.getObjectServicePort();
        List<EmployeeY> list = sv.requestListEmployeeY(msv, qCode);
        Comparator<EmployeeY> comparator = new Comparator<EmployeeY>() {
            @Override
            public int compare(EmployeeY o1, EmployeeY o2) {
                return o1.getStartDate().compare(o2.getStartDate());
                
            }
        };
        list.sort(comparator);
        sv.submitListEmployeeY(msv, qCode, list);
    } 
}

// Apple
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import vn.medianews.CharacterService;
import vn.medianews.CharacterService_Service;
public class Wb6Oq7lb {
    public static int cnt(String s){
        int res = 0;
        for(char x : s.toLowerCase().toCharArray()){
            if(x == 'a' || x == 'e' || x == 'i' || x == 'o' || x == 'u'){
                res++;
            }
        }
        return res;
    }
    public static void main(String[] args) {
        String msv = "B22DCCN557";
        String qCode = "Wb6Oq7lb";
        CharacterService_Service service = new CharacterService_Service();
        CharacterService sv = service.getCharacterServicePort();
        List<String> list = sv.requestStringArray(msv, qCode);
        Map<Integer, ArrayList<String>> map = new TreeMap<>();
        for(String x : list){
            int dem = cnt(x);
            if(!map.containsKey(dem)){
                ArrayList<String> arr = new ArrayList<>();
                arr.add(x);
                map.put(dem, arr);
            }
            else{
                ArrayList<String> oldArr = map.get(dem);
                oldArr.add(x);
                map.put(dem, oldArr);
            }
        }
        List<String> subList = new ArrayList<>();
        for(Map.Entry<Integer, ArrayList<String>> entry : map.entrySet()){
            ArrayList<String> tmp = new ArrayList<>();
            for(String s : entry.getValue()){
                tmp.add(s);
            }
            Collections.sort(tmp);
            String push = "";
            for(String t : tmp){
                push += t + ", ";
            }
            subList.add(push.substring(0, push.length() - 2));
        }
        sv.submitCharacterStringArray(msv, qCode, subList);
    }
}
