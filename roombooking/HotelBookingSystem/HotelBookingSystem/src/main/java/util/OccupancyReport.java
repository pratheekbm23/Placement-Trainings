package util;

import datastructures.WaitingListQueue;
import model.Booking;
import model.Room;
import service.BookingService;
import service.RoomService;

import java.util.List;

/** Prints the occupancy report. */
public class OccupancyReport {

    public static void print(RoomService roomService, BookingService bookingService,
                             WaitingListQueue waitingList) {
        List<Room> rooms = roomService.getRooms();
        int total = rooms.size();
        int occupied = 0;
        for (Room r : rooms) {
            if (r.isOccupied()) occupied++;
        }

        System.out.println("\n======= OCCUPANCY REPORT =======");
        System.out.println("Total rooms      : " + total);
        System.out.println("Occupied rooms   : " + occupied);
        System.out.println("Vacant rooms     : " + (total - occupied));
        System.out.printf("Occupancy rate   : %.1f%%%n", occupied * 100.0 / total);

        for (String type : RoomService.ROOM_TYPES) {
            int count = 0;
            int occ = 0;
            for (Room r : rooms) {
                if (r.getType().equals(type)) {
                    count++;
                    if (r.isOccupied()) occ++;
                }
            }
            System.out.printf("%-7s rooms    : %d/%d occupied%n", type, occ, count);
        }

        System.out.println("On waiting list  : " + waitingList.size());
        System.out.println("Check-outs done  : " + bookingService.countByStatus(Booking.Status.CHECKED_OUT));
        System.out.printf("Revenue earned   : Rs.%.2f%n", bookingService.getTotalRevenue());
        System.out.println("================================");
    }
}
