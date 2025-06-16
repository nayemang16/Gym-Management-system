/**
 * Abstract class GymMember - parent class for different types of gym memberships
 *
 * @author (Nayem Raj Angdembay)
 * @version (March 4, 2025)
 */
public abstract class GymMember {
    // Instance variables
    protected int id;
    protected String name;
    protected String location;
    protected String phone;
    protected String email;
    protected String gender;
    protected String DOB;
    protected String membershipStartDate;
    protected int attendance;
    protected int loyaltyPoints;
    protected boolean active;
    
    /**
     * Constructor for GymMember
     */
    public GymMember(int id, String name, String location, String phone, String email, 
                     String gender, String DOB, String membershipStartDate) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.DOB = DOB;
        this.membershipStartDate = membershipStartDate;
        this.attendance = 0;
        this.loyaltyPoints = 0;
        this.active = true; // Members are active by default
    }
    
    // Getter methods
    public int getid() {
        return this.id;
    }
    
    public String getname() {
        return this.name;
    }
    
    public String getlocation() {
        return this.location;
    }
    
    public String getphone() {
        return this.phone;
    }
    
    public String getemail() {
        return this.email;
    }
    
    public String getgender() {
        return this.gender;
    }
    
    public String getDOB() {
        return this.DOB;
    }
    
    public String getmembershipStartDate() {
        return this.membershipStartDate;
    }
    
    public int getattendance() {
        return this.attendance;
    }
    
    public int getLoyaltyPoints() {
        return this.loyaltyPoints;
    }
    
    public boolean activeStatus() {
        return this.active;
    }
    
    // Abstract method that must be implemented by child classes
    public abstract void markAttendance();
    
    /**
     * Activate the member's membership
     */
    public void activateMember() {
        this.active = true;
        System.out.println(name + "'s membership has been activated.");
    }
    
    /**
     * Deactivate the member's membership
     */
    public void deactivateMember() {
        this.active = false;
        System.out.println(name + "'s membership has been deactivated.");
    }
    
    /**
     * Reset member's data (used when reverting membership type)
     */
    protected void resetMember() {
        this.attendance = 0;
        this.loyaltyPoints = 0;
        this.active = true;
    }
    
    /**
     * Display member information
     */
    public void displayInfo() {
        System.out.println("Member ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Location: " + location);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Gender: " + gender);
        System.out.println("Date of Birth: " + DOB);
        System.out.println("Membership Start Date: " + membershipStartDate);
        System.out.println("Attendance: " + attendance);
        System.out.println("Loyalty Points: " + loyaltyPoints);
        System.out.println("Status: " + (active ? "Active" : "Inactive"));
    }
   
    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Status: " + (active ? "Active" : "Inactive");
    }
}