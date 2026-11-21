package model;

/** A booking record. Life cycle: WAITING -> ALLOCATED -> CHECKED_IN -> CHECKED_OUT */
public class Booking {

    public enum Status { WAITING, ALLOCATED, CHECKED_IN, CHECKED_OUT }

    private final int bookingId;
    private final Guest guest;
    private final String roomType;
    private final int plannedNights;
    private Room room;                  // null while on the waiting list
    private Status status;
    private double totalBill;

    public Booking(int bookingId, Guest guest, String roomType, int plannedNights) {
        this.bookingId = bookingId;
        this.guest = guest;
        this.roomType = roomType;
        this.plannedNights = plannedNights;
        this.status = Status.WAITING;   // until a room is allocated
    }

    /** Room allocation: attach a room to this booking. */
    public void assignRoom(Room room) {
        this.room = room;
        this.status = Status.ALLOCATED;
    }

    public int getBookingId()      { return bookingId; }
    public Guest getGuest()        { return guest; }
    public String getRoomType()    { return roomType; }
    public int getPlannedNights()  { return plannedNights; }
    public Room getRoom()          { return room; }
    public Status getStatus()      { return status; }
    public double getTotalBill()   { return totalBill; }

    public void setStatus(Status status)   { this.status = status; }
    public void setTotalBill(double bill)  { this.totalBill = bill; }
}
