package service;

import exceptions.InvalidRoomException;
import exceptions.RoomNotAvailableException;
import model.Room;

import java.util.ArrayList;
import java.util.List;

/** Keeps the list of rooms and finds vacant ones. */
public class RoomService {

    public static final String[] ROOM_TYPES = {"Single", "Double", "Suite"};

    private final List<Room> rooms = new ArrayList<>();

    public RoomService() {
        for (int i = 101; i <= 104; i++) rooms.add(new Room(i, "Single", 1500));
        for (int i = 201; i <= 204; i++) rooms.add(new Room(i, "Double", 2500));
        for (int i = 301; i <= 302; i++) rooms.add(new Room(i, "Suite", 5000));
    }

    public List<Room> getRooms() { return rooms; }

    /** Returns the properly spelled type, or throws if the type does not exist. */
    public String validateType(String type) throws InvalidRoomException {
        for (String t : ROOM_TYPES) {
            if (t.equalsIgnoreCase(type)) return t;
        }
        throw new InvalidRoomException("Invalid room type '" + type + "'. Choose Single, Double or Suite.");
    }

    /** Room allocation: give the first vacant room of this type. */
    public Room allocateRoom(String type) throws RoomNotAvailableException {
        for (Room r : rooms) {
            if (!r.isOccupied() && r.getType().equalsIgnoreCase(type)) {
                r.setOccupied(true);
                return r;
            }
        }
        throw new RoomNotAvailableException("No vacant " + type + " room available.");
    }

    public void viewRooms() {
        System.out.println("\nRoom  Type     Rate/Night  Status");
        System.out.println("-----------------------------------");
        for (Room r : rooms) {
            System.out.printf("%-5d %-8s Rs.%-8.0f %s%n",
                    r.getNumber(), r.getType(), r.getRatePerNight(),
                    r.isOccupied() ? "Occupied" : "Vacant");
        }
    }
}
