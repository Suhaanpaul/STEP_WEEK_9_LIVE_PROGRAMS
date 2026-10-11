import java.util.Scanner;
interface SaverModeSupported {
    // marker interface for saver mode capability
}
abstract class Appliance {
    protected double hours;
    protected boolean requestedSaver;
    protected static final double RATE_PER_UNIT = 8.0;
    public Appliance(double hours, boolean requestedSaver) {
        this.hours = hours;
        this.requestedSaver = requestedSaver;
    }
    public abstract double getPowerRating(); // in Watts
    public abstract String getApplianceName();
    public abstract boolean supportsSaverMode();
    public void generateReport() {
        if (requestedSaver && !supportsSaverMode()) {
            System.out.println(getApplianceName() + ": saver mode not supported");
            return;
        }
        double units = (getPowerRating() * hours) / 1000.0;
        if (requestedSaver && supportsSaverMode()) {
            units *= 0.75; // reduced by 25%
        }
        double cost = units * RATE_PER_UNIT;
        System.out.printf("%s: Units=%.2f Cost=%.2f\n", getApplianceName(), units, 
cost);
    }
    public double getCostValue() {
        if (requestedSaver && !supportsSaverMode()) {
            return -1.0;
        }
        double units = (getPowerRating() * hours) / 1000.0;
        if (requestedSaver && supportsSaverMode()) {
            units *= 0.75;
        }
        return units * RATE_PER_UNIT;
    }
}
class Fridge extends Appliance {
    public Fridge(double hours, boolean requestedSaver) {
        super(hours, requestedSaver);
    }
    @Override
    public double getPowerRating() {
        return 150.0;
    }
    @Override
    public String getApplianceName() {
        return "FRIDGE";
    }
    @Override
    public boolean supportsSaverMode() {
        return false;
    }
}
class AirConditioner extends Appliance implements SaverModeSupported {
    public AirConditioner(double hours, boolean requestedSaver) {
        super(hours, requestedSaver);
    }
    @Override
    public double getPowerRating() {
        return 1500.0;
    }
    @Override
    public String getApplianceName() {
        return "AC";
    }
    @Override
    public boolean supportsSaverMode() {
        return true;
    }
}
class TV extends Appliance {
    public TV(double hours, boolean requestedSaver) {
        super(hours, requestedSaver);
    }
    @Override
    public double getPowerRating() {
        return 100.0;
    }
    @Override
    public String getApplianceName() {
        return "TV";
    }
    @Override
    public boolean supportsSaverMode() {
        return false;
    }
}
class WashingMachine extends Appliance implements SaverModeSupported {
    public WashingMachine(double hours, boolean requestedSaver) {
        super(hours, requestedSaver);
    }
    @Override
    public double getPowerRating() {
        return 500.0;
    }
    @Override
    public String getApplianceName() {
        return "WASHER";
    }
    @Override
    public boolean supportsSaverMode() {
        return true;
    }
}
public class Appliance_Energy {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();
        Appliance[] appliances = new Appliance[n];
        for (int i = 0; i < n; i++) {
            String type = scanner.next();
            double hours = scanner.nextDouble();
            boolean saver = false;
            if (scanner.hasNext("SAVER")) {
                scanner.next();
                saver = true;
            }
            if (type.equals("FRIDGE")) {
                appliances[i] = new Fridge(hours, saver);
            } else if (type.equals("AC")) {
                appliances[i] = new AirConditioner(hours, saver);
            } else if (type.equals("TV")) {
                appliances[i] = new TV(hours, saver);
            } else if (type.equals("WASHER")) {
                appliances[i] = new WashingMachine(hours, saver);
            }
        }
        scanner.close();
        double totalCost = 0;
        for (Appliance app : appliances) {
            double costVal = app.getCostValue();
            app.generateReport();
            if (costVal >= 0) {
                totalCost += costVal;
            }
        }
        System.out.printf("Total Cost: %.2f\n", totalCost);
    }
}