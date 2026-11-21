package model;

/** Base class for people who log in to the system. */
public class User {
    protected String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() { return username; }

    public String getRole() { return "Guest"; }
}
