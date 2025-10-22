import ws.Employee;
import ws.EmployeeY;
import ws.ObjectService;
import ws.ObjectService_Service;
import java.util.Comparator;
import java.util.List;


public class Object {
    public static void main(String[] args) {
        String msv = "B22DCCN568";
        String qCode = "bFd6Eayn";
        ObjectService_Service service = new ObjectService_Service();
        ObjectService sv = service.getObjectServicePort();
        List<EmployeeY> list = sv.requestListEmployeeY(msv, qCode);
        System.out.println("Danh sách nhân viên nhận về từ server:");
        for(EmployeeY e : list) {
            System.out.println("Tên: " + e.getName() + ", Ngày bắt đầu: " + e.getStartDate());
        }
        Comparator<EmployeeY> comparator = new Comparator<EmployeeY>() {
            @Override
            public int compare(EmployeeY o1, EmployeeY o2) {
                return o1.getStartDate().compare(o2.getStartDate());
                
            }
        };
        list.sort(comparator);
         System.out.println("\nDanh sách nhân viên sau khi sắp xếp thâm niên:");
        for(EmployeeY e : list) {
            System.out.println("Tên: " + e.getName() + ", Ngày bắt đầu: " + e.getStartDate());
        }
        sv.submitListEmployeeY(msv, qCode, list);
         System.out.println("\nĐã gửi danh sách đã sắp xếp lên server!");
    } 
}