import java.util.Scanner;
interface NightServiceable {
    // marker or method for night service support
}
abstract class Cab {
    protected double distance;
    protected String time;
    protected static final double MIN_FARE = 100.0;
    public Cab(double distance, String time) {
        this.distance = distance;
        this.time = time;
    }
    public abstract double calculateRatePerKm();
    public abstract String getCabName();
    public abstract boolean supportsNightService();
    public void processTrip() {
        if (time.equals("NIGHT") && !supportsNightService()) {
            System.out.println(getCabName() + ": night service not available");
            return;
        }
        double baseFare = distance * calculateRatePerKm();
        double fare = Math.max(baseFare, MIN_FARE);
        if (time.equals("NIGHT") && supportsNightService()) {
            fare *= 1.20;
        }
        System.out.printf("%s: %.2f\n", getCabName(), fare);
    }
    public double getTripFareValue() {
        if (time.equals("NIGHT") && !supportsNightService()) {
            return -1.0; // rejected
        }
        double baseFare = distance * calculateRatePerKm();
        double fare = Math.max(baseFare, MIN_FARE);
        if (time.equals("NIGHT") && supportsNightService()) {
            fare *= 1.20;
        }
        return fare;
    }
}
class MiniCab extends Cab {
    public MiniCab(double distance, String time) {
        super(distance, time);
    }
    @Override
    public double calculateRatePerKm() {
        return 10.0;
    }
    @Override
    public String getCabName() {
        return "MINI";
    }
    @Override
    public boolean supportsNightService() {
        return false;
    }
}
class SedanCab extends Cab implements NightServiceable {
    public SedanCab(double distance, String time) {
        super(distance, time);
    }
    @Override
    public double calculateRatePerKm() {
        return 14.0;
    }
    @Override
    public String getCabName() {
        return "SEDAN";
    }
    @Override
    public boolean supportsNightService() {
        return true;
    }
}
class SuvCab extends Cab implements NightServiceable {
    public SuvCab(double distance, String time) {
        super(distance, time);
    }
    @Override
    public double calculateRatePerKm() {
        return 18.0;
    }
    @Override
    public String getCabName() {
        return "SUV";
    }
    @Override
    public boolean supportsNightService() {
        return true;
    }
}
public class CityCab {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();
        Cab[] cabs = new Cab[n];
        for (int i = 0; i < n; i++) {
            String type = scanner.next();
            double distance = scanner.nextDouble();
            String time = scanner.next();
            if (type.equals("MINI")) {
                cabs[i] = new MiniCab(distance, time);
            } else if (type.equals("SEDAN")) {
                cabs[i] = new SedanCab(distance, time);
            } else if (type.equals("SUV")) {
                cabs[i] = new SuvCab(distance, time);
            }
        }
        scanner.close();
        double total = 0;
        for (Cab cab : cabs) {
            double val = cab.getTripFareValue();
            cab.processTrip();
            if (val > 0) {
                total += val;
            }
        }
        System.out.printf("Total: %.2f\n", total);
    }
}
