package model;

/** One hotel room. */
public class Room {
    private final int number;
    private final String type;          // Single / Double / Suite
    private final double ratePerNight;
    private boolean occupied;

    public Room(int number, String type, double ratePerNight) {
        this.number = number;
        this.type = type;
        this.ratePerNight = ratePerNight;
        this.occupied = false;
    }

    public int getNumber()          { return number; }
    public String getType()         { return type; }
    public double getRatePerNight() { return ratePerNight; }
    public boolean isOccupied()     { return occupied; }
    public void setOccupied(boolean occupied) { this.occupied = occupied; }
}
