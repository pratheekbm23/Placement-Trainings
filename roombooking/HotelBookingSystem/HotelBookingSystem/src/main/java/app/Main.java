package app;

import datastructures.WaitingListQueue;
import exceptions.BookingNotFoundException;
import exceptions.InvalidRoomException;
import model.Admin;
import model.Booking;
import model.Guest;
import model.User;
import service.BookingService;
import service.ReservationService;
import service.RoomService;
import service.TariffService;
import util.InputHelper;
import util.OccupancyReport;

import java.util.Scanner;

/** Command-line entry point of the Hotel Room Booking System. */
public class Main {

    static Scanner sc = new Scanner(System.in);
    static RoomService roomService = new RoomService();
    static TariffService tariffService = new TariffService();
    static BookingService bookingService = new BookingService(tariffService);
    static WaitingListQueue waitingList = new WaitingListQueue();
    static ReservationService reservationService =
            new ReservationService(roomService, bookingService, waitingList);

    public static void main(String[] args) {
        System.out.println("===== HOTEL ROOM BOOKING SYSTEM =====");
        boolean running = true;
        while (running) {
            System.out.println("\nLogin as:  1. Guest   2. Admin   3. Exit");
            int choice = InputHelper.readInt(sc, "Enter choice: ");
            if (choice == 1) {
                guestMenu(new User("guest"));
            } else if (choice == 2) {
                adminMenu(new Admin("admin"));
            } else if (choice == 3) {
                System.out.println("Thank you! Goodbye.");
                running = false;
            } else {
                System.out.println("Invalid choice, try again.");
            }
        }
    }

    // ---------------- Guest menu ----------------
    static void guestMenu(User user) {
        System.out.println("\nLogged in as " + user.getRole());
        int choice;
        do {
            System.out.println("\n--- GUEST MENU ---");
            System.out.println("1. View Rooms");
            System.out.println("2. Book Room (Room Allocation)");
            System.out.println("3. Check-in");
            System.out.println("4. Check-out & Bill");
            System.out.println("5. Back");
            choice = InputHelper.readInt(sc, "Enter choice: ");

            switch (choice) {
                case 1:
                    roomService.viewRooms();
                    break;
                case 2:
                    bookRoom();
                    break;
                case 3:
                    checkIn();
                    break;
                case 4:
                    checkOut();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        } while (choice != 5);
    }
//gor booking room
    static void bookRoom() {
        String name = InputHelper.readString(sc, "Guest name: ");
        String phone = InputHelper.readString(sc, "Phone: ");
        String type = InputHelper.readString(sc, "Room type (Single/Double/Suite): ");
        int nights = InputHelper.readInt(sc, "Number of nights planned: ");
        if (nights <= 0) {
            System.out.println("Nights must be at least 1.");
            return;
        }
        try {
            reservationService.reserve(new Guest(name, phone), type, nights);
        } catch (InvalidRoomException e) {
            System.out.println(e.getMessage());
        }
    }

    static void checkIn() {
        int id = InputHelper.readInt(sc, "Booking ID: ");
        try {
            bookingService.checkIn(id);
        } catch (BookingNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    static void checkOut() {
        int id = InputHelper.readInt(sc, "Booking ID: ");
        int nights = InputHelper.readInt(sc, "Number of nights stayed: ");
        if (nights <= 0) {
            System.out.println("Nights must be at least 1.");
            return;
        }
        try {
            Booking b = bookingService.checkOut(id, nights);
            if (b != null) {
                // room is free now - give it to the first guest waiting for this type
                reservationService.promoteFromWaitingList(b.getRoomType());
            }
        } catch (BookingNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // ---------------- Admin menu ----------------
    static void adminMenu(Admin admin) {
        System.out.println("\nLogged in as " + admin.getRole());
        int choice;
        do {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. View Rooms");
            System.out.println("2. View All Bookings");
            System.out.println("3. View Waiting List");
            System.out.println("4. Occupancy Report");
            System.out.println("5. Back");
            choice = InputHelper.readInt(sc, "Enter choice: ");

            switch (choice) {
                case 1:
                    roomService.viewRooms();
                    break;
                case 2:
                    bookingService.viewBookings();
                    break;
                case 3:
                    waitingList.display();
                    break;
                case 4:
                    OccupancyReport.print(roomService, bookingService, waitingList);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        } while (choice != 5);
    }
}
