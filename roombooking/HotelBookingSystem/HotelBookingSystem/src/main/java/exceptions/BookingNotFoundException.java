package exceptions;
// exception if id not avalable during checkin
public class BookingNotFoundException extends Exception {
    public BookingNotFoundException(String message) {
        super(message);
    }
}
