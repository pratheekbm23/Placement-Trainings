package service;

import exceptions.BookingNotFoundException;
import model.Booking;
import model.Guest;

import java.util.ArrayList;
import java.util.List;

/** Stores bookings and handles check-in and check-out. */
public class BookingService {

    private final List<Booking> bookings = new ArrayList<>();
    private final TariffService tariffService;
    private int nextId = 1;

    public BookingService(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    public Booking createBooking(Guest guest, String roomType, int nights) {
        Booking booking = new Booking(nextId++, guest, roomType, nights);
        bookings.add(booking);
        return booking;
    }

    public Booking findById(int id) throws BookingNotFoundException {
        for (Booking b : bookings) {
            if (b.getBookingId() == id) return b;
        }
        throw new BookingNotFoundException("Booking #" + id + " not found.");
    }

    /** Check-in: only allowed once a room has been allocated. */
    public void checkIn(int id) throws BookingNotFoundException {
        Booking b = findById(id);
        switch (b.getStatus()) {
            case ALLOCATED:
                b.setStatus(Booking.Status.CHECKED_IN);
                System.out.println("Check-in done. " + b.getGuest().getName()
                        + " is now in Room " + b.getRoom().getNumber() + ".");
                break;
            case WAITING:
                System.out.println("Cannot check in - booking is still on the waiting list.");
                break;
            default:
                System.out.println("Cannot check in - booking status is " + b.getStatus() + ".");
        }
    }

    /** Check-out: calculates the bill and frees the room. Returns null if not possible. */
    public Booking checkOut(int id, int nights) throws BookingNotFoundException {
        Booking b = findById(id);
        if (b.getStatus() != Booking.Status.CHECKED_IN) {
            System.out.println("Only checked-in guests can check out. Status is " + b.getStatus() + ".");
            return null;
        }
        double total = tariffService.generateBill(b, nights);
        b.setTotalBill(total);
        b.setStatus(Booking.Status.CHECKED_OUT);
        b.getRoom().setOccupied(false);          // room is free again
        return b;
    }

    public int countByStatus(Booking.Status status) {
        int count = 0;
        for (Booking b : bookings) {
            if (b.getStatus() == status) count++;
        }
        return count;
    }

    public double getTotalRevenue() {
        double sum = 0;
        for (Booking b : bookings) {
            if (b.getStatus() == Booking.Status.CHECKED_OUT) sum += b.getTotalBill();
        }
        return sum;
    }

    public void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings yet.");
            return;
        }
        System.out.println("\nID  Guest        Type    Room  Status");
        System.out.println("---------------------------------------------");
        for (Booking b : bookings) {
            String room = (b.getRoom() == null) ? "-" : String.valueOf(b.getRoom().getNumber());
            System.out.printf("%-3d %-12s %-7s %-5s %s%n",
                    b.getBookingId(), b.getGuest().getName(), b.getRoomType(), room, b.getStatus());
        }
    }
}
