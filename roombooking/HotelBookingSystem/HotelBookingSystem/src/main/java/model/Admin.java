package model;

/** Admin (hotel manager) - can see reports and all bookings. */
public class Admin extends User {

    public Admin(String username) {
        super(username);
    }

    @Override
    public String getRole() { return "Admin"; }
}
