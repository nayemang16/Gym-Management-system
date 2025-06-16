/**
 * Child class that inherits data from GymMember parent class.
 * Represents a regular gym member with specific attributes and methods.
 * @author Nayem Raj Angdembay
 * @version March 5, 2025
 */
public class RegularMember extends GymMember {
    // Attributes specific to RegularMember
    private final int attendanceLimit;
    private boolean isEligibleForUpgrade;
    private String removalReason;
    private String referralSource;
    private String plan;
    private double price;

    // Constructor
    public RegularMember(int id, String name, String location, String phone, String email, String gender, String DOB, String membershipStartDate, String referralSource) {
        super(id, name, location, phone, email, gender, DOB, membershipStartDate);
        this.attendanceLimit = 3;
        this.isEligibleForUpgrade = false;
        this.removalReason = "";
        this.referralSource = referralSource;
        this.plan = "basic";
        this.price = 6500.0;
    }

    // Getter methods
    public int get_attendanceLimit() {
        return this.attendanceLimit;
    }

    public boolean get_isEligibleForUpgrade() {
        return this.isEligibleForUpgrade;
    }

    public String get_removalReason() {
        return this.removalReason;
    }

    public String get_referralSource() {
        return this.referralSource;
    }

    public String get_plan() {
        return this.plan;
    }

    public double get_price() {
        return this.price;
    }

    // Implemented abstract method
    public void markAttendance() {
        this.attendance++;
        this.loyaltyPoints += 5;
        if (attendance >= attendanceLimit) {
            isEligibleForUpgrade = true;
        }
        System.out.println(name + "'s attendance marked. Total attendance: " + attendance);
    }

    // Method to get the price of a plan
    public double getPlanPrice(String plan) {
        switch (plan.toLowerCase()) {
            case "basic":
                return 6500;
            case "standard":
                return 12500;
            case "deluxe":
                return 18500;
            default:
                System.out.println("Invalid plan.");
                return -1;
        }
    }

    // Method to upgrade the member's plan
    public String upgradePlan(String plan) {
        if (super.getattendance() >= attendanceLimit) {
            if (!this.plan.equalsIgnoreCase(plan)) {
                double newPrice = getPlanPrice(plan);
                if (newPrice != -1) {
                    this.plan = plan;
                    this.price = newPrice;
                    System.out.println("Congratulations for upgrading your plan changed successfully to " + plan + ".");
                    return "Plan successfully upgraded to " + plan + ".";
                } else {
                    return "Invalid plan name.";
                }
            } else {
                System.out.println("You are already subscribed to this plan.");
                return "Already subscribed to the " + plan + " plan.";
            }
        } else {
            System.out.println("You are not eligible for an upgrade.");
            return "You are currently not eligible to upgrade the plan. Attendance must be at least " + attendanceLimit + ".";
        }
    }

    // Method to revert the member to a regular member
    public void revertRegularMember(String removalReason) {
        super.resetMember();
        this.isEligibleForUpgrade = false;
        this.plan = "basic";
        this.price = 6500;
        this.removalReason = removalReason;
    }

    // Override displayInfo to include additional details
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Plan: " + plan);
        System.out.println("Price: " + price);
        if (!removalReason.isEmpty()) {
            System.out.println("Removal Reason: " + removalReason);
        }
    }
}