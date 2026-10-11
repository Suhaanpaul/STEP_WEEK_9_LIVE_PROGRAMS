import java.util.Scanner;
interface Insurable {
    double calculateInsurance();
}
abstract class Parcel {
    protected double weight;
    protected double declaredValue;
    public Parcel(double weight, double declaredValue) {
        this.weight = weight;
        this.declaredValue = declaredValue;
    }
    public abstract double calculateCharge();
    public abstract String getParcelType();
}
class StandardParcel extends Parcel {
    public StandardParcel(double weight, double declaredValue) {
        super(weight, declaredValue);
    }
    @Override
    public double calculateCharge() {
        return 40.0 + (10.0 * weight);
    }
    @Override
    public String getParcelType() {
        return "STANDARD";
    }
}
class ExpressParcel extends Parcel implements Insurable {
    public ExpressParcel(double weight, double declaredValue) {
        super(weight, declaredValue);
    }
    @Override
    public double calculateCharge() {
        return 80.0 + (15.0 * weight);
    }
    @Override
    public double calculateInsurance() {
        return 0.02 * declaredValue;
    }
    @Override
    public String getParcelType() {
        return "EXPRESS";
    }
}
class FragileParcel extends Parcel implements Insurable {
    public FragileParcel(double weight, double declaredValue) {
        super(weight, declaredValue);
    }
    @Override
    public double calculateCharge() {
        // Standard charge plus handling fee of 50
        double standardCharge = 40.0 + (10.0 * weight);
        return standardCharge + 50.0;
    }
    @Override
    public double calculateInsurance() {
        return 0.02 * declaredValue;
    }
    @Override
    public String getParcelType() {
        return "FRAGILE";
    }
}
public class ParcelDesk {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();
        Parcel[] parcels = new Parcel[n];
        for (int i = 0; i < n; i++) {
            String type = scanner.next();
            double weight = scanner.nextDouble();
            double declaredValue = scanner.nextDouble();
            if (type.equals("STANDARD")) {
                parcels[i] = new StandardParcel(weight, declaredValue);
            } else if (type.equals("EXPRESS")) {
                parcels[i] = new ExpressParcel(weight, declaredValue);
            } else if (type.equals("FRAGILE")) {
                parcels[i] = new FragileParcel(weight, declaredValue);
            }
        }
        scanner.close();
        double grandTotal = 0;
        for (Parcel parcel : parcels) {
            double charge = parcel.calculateCharge();
            double insurance = 0.0;
            if (parcel instanceof Insurable) {
                insurance = ((Insurable) parcel).calculateInsurance();
            }
            double total = charge + insurance;
            grandTotal += total;
            System.out.printf("%s: Charge=%.2f Insurance=%.2f Total=%.2f\n", 
                parcel.getParcelType(), charge, insurance, total);
        }
        System.out.printf("Grand Total: %.2f\n", grandTotal);
    }
}