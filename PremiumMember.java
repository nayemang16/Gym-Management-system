/**
 * Child class that inherits data from GymMember parent class.
 * Represents a premium gym member with additional attributes and methods.
 * @author Nayem Raj Angdembay
 * @version March 6, 2025
 */
public class PremiumMember extends GymMember {

    private final double PREMIUM_CHARGE;
    private String personalTrainer;
    private boolean isFullPayment;
    private double paidAmount;
    private double discountAmount;

    // Constructor
    public PremiumMember(int id, String name, String location, String phone, String email,
                         String gender, String DOB, String membershipStartDate, String personalTrainer) {
        super(id, name, location, phone, email, gender, DOB, membershipStartDate);
        this.personalTrainer = personalTrainer;
        this.isFullPayment = false;
        this.PREMIUM_CHARGE = 50000.0;
        this.paidAmount = 0.0;
        this.discountAmount = 0.0;
    }

    // Getters
    public double getPremiumCharge() {
        return this.PREMIUM_CHARGE;
    }

    public String getPersonalTrainer() {
        return this.personalTrainer;
    }

    public boolean isFullPayment() {
        return this.isFullPayment;
    }

    public double getPaidAmount() {
        return this.paidAmount;
    }

    public double getDiscountAmount() {
        return this.discountAmount;
    }

    public String getPlan() {
        return "Premium"; // Assuming all premium members have the same plan
    }

    // Method to pay the due amount
    public String payDueAmount(double paidAmount) {
        double remainingAmount = PREMIUM_CHARGE - this.paidAmount;

        if (paidAmount == this.PREMIUM_CHARGE) {
            this.isFullPayment = true;
            this.paidAmount = paidAmount;
            System.out.println("Your payment has been cleared. The remaining balance is: " + remainingAmount);
            return "Your payment is cleared and the remaining balance is:" + remainingAmount;
        }

        if (this.isFullPayment == true) {
            System.out.println("Thank You. Your payment is cleared!");
            return "Your payment is cleared. Thank you!";
        }

        else if (paidAmount > remainingAmount) {
            return "The amount paid exceeds the remaining amount. Your remaining balance is " + remainingAmount;
        }

        else {
            this.paidAmount += paidAmount;
            remainingAmount = PREMIUM_CHARGE - this.paidAmount;

            if (this.paidAmount == PREMIUM_CHARGE) {
                this.isFullPayment = true;
            }
            return "Successfully Paid. Your Remaining Amount is " + remainingAmount;
        }
    }

    // Implemented abstract method to mark attendance
    public void markAttendance() {
        attendance++;
        loyaltyPoints += 10;
        System.out.println(name + "'s attendance marked. Total attendance:" + attendance);
    }

    // Method to calculate discount based on attendance
    public double calculateDiscount() {
        // Apply 15% discount if attendance is 10 or more
        if (getattendance() >= 10) {
            discountAmount = PREMIUM_CHARGE * 0.15;
        } else {
            discountAmount = 0;
        }
        return discountAmount;
    }

    // Method to revert the premium membership
    public void revertPremiumMember() {
        super.resetMember();
        personalTrainer = "";
        isFullPayment = false;
        paidAmount = 0;
        discountAmount = 0;
        System.out.println(super.name + "(" + super.id + ") premium membership has been reverted");
    }

    // Override displayInfo to include additional details
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Personal Trainer: " + personalTrainer);
        System.out.println("Paid Amount: " + paidAmount);
        System.out.println("Payment Status: " + (this.isFullPayment() ? "Paid" : "Remaining"));
        System.out.println("Remaining Amount: " + (PREMIUM_CHARGE - this.paidAmount));
        if (this.isFullPayment == true) {
            System.out.println("Discount Amount: " + calculateDiscount());
        }
    }
}