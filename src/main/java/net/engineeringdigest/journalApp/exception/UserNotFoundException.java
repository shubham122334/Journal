package net.engineeringdigest.journalApp.exception;

import org.bson.types.ObjectId;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(ObjectId id) {
        super("User not found with id:"+id);
    }
    public UserNotFoundException(String username) {
        super(username);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
