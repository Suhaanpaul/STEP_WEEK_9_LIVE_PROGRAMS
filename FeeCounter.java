import java.util.Scanner;
interface TransportUser {
    double getTransportFee();
}
abstract class Student {
    protected String name;
    public Student(String name) {
        this.name = name;
    }
    public abstract double calculateTuition();
}
class DayScholar extends Student implements TransportUser {
    private static final double TRANSPORT_FEE = 12000.0;
    public DayScholar(String name) {
        super(name);
    }
    @Override
    public double calculateTuition() {
        return 40000.0;
    }
    @Override
    public double getTransportFee() {
        return TRANSPORT_FEE;
    }
}
class Hosteller extends Student {
    public Hosteller(String name) {
        super(name);
    }
    @Override
    public double calculateTuition() {
        return 40000.0 + 60000.0; // tuition + hostel fee
    }
}
class ScholarshipStudent extends Student implements TransportUser {
    private static final double TRANSPORT_FEE = 12000.0;
    public ScholarshipStudent(String name) {
        super(name);
    }
    @Override
    public double calculateTuition() {
        return 20000.0; // half of normal tuition
    }
    @Override
    public double getTransportFee() {
        return TRANSPORT_FEE;
    }
}
public class FeeCounter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();
        Student[] students = new Student[n];
        for (int i = 0; i < n; i++) {
            String token1 = scanner.next();
            String type = token1;
            if (token1.equals("DAY")) {
                String token2 = scanner.next(); // SCHOLAR
                type = "DAY_SCHOLAR";
            }
            String name = scanner.next();
            if (type.equals("DAY_SCHOLAR")) {
                students[i] = new DayScholar(name);
            } else if (type.equals("HOSTELLER")) {
                students[i] = new Hosteller(name);
            } else if (type.equals("SCHOLAR")) {
                students[i] = new ScholarshipStudent(name);
            }
        }
        scanner.close();
        double totalCollected = 0;
        for (Student student : students) {
            double fee = student.calculateTuition();
            if (student instanceof TransportUser) {
                fee += ((TransportUser) student).getTransportFee();
            }
            totalCollected += fee;
            System.out.printf("%s: %.2f\n", student.name, fee);
        }
        System.out.printf("Total Collected: %.2f\n", totalCollected);
    }
}
