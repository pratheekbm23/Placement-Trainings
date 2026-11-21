package exceptions;
//during room booking, entering the wrong room type
public class InvalidRoomException extends Exception {
    public InvalidRoomException(String message) {
        super(message);
    }
}
