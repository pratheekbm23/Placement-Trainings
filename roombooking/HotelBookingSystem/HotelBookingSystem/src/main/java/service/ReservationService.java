package service;

import datastructures.WaitingListQueue;
import exceptions.InvalidRoomException;
import exceptions.RoomNotAvailableException;
import model.Booking;
import model.Guest;
import model.Room;

/** Room allocation: books a room, or puts the guest on the waiting list. */
public class ReservationService {

    private final RoomService roomService;
    private final BookingService bookingService;
    private final WaitingListQueue waitingList;

    public ReservationService(RoomService roomService, BookingService bookingService,
                              WaitingListQueue waitingList) {
        this.roomService = roomService;
        this.bookingService = bookingService;
        this.waitingList = waitingList;
    }

    public Booking reserve(Guest guest, String type, int nights) throws InvalidRoomException {
        String roomType = roomService.validateType(type);
        Booking booking = bookingService.createBooking(guest, roomType, nights);
        try {
            Room room = roomService.allocateRoom(roomType);
            booking.assignRoom(room);
            System.out.println("Room allocated! Booking #" + booking.getBookingId()
                    + " -> Room " + room.getNumber() + " (" + roomType + ").");
        } catch (RoomNotAvailableException e) {
            waitingList.enqueue(booking);
            System.out.println(e.getMessage() + " Booking #" + booking.getBookingId()
                    + " added to waiting list (position " + waitingList.size() + ").");
        }
        return booking;
    }

    /** After a check-out, give the freed room to the first guest waiting for that type. */
    public void promoteFromWaitingList(String type) {
        Booking waiting = waitingList.removeFirstOfType(type);
        if (waiting == null) return;
        try {
            Room room = roomService.allocateRoom(type);
            waiting.assignRoom(room);
            System.out.println("Waiting list update: Booking #" + waiting.getBookingId() + " ("
                    + waiting.getGuest().getName() + ") now allocated Room " + room.getNumber() + ".");
        } catch (RoomNotAvailableException e) {
            waitingList.enqueue(waiting);        // should not happen, kept for safety
        }
    }
}
