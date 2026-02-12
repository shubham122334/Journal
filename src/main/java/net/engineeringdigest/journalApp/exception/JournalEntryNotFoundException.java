package net.engineeringdigest.journalApp.exception;

public class JournalEntryNotFoundException extends RuntimeException {

    public JournalEntryNotFoundException(String id) {
        super("Journal entry not found with id:"+id);
    }

    public JournalEntryNotFoundException(String message,String id) {
        super(message.concat(id));
    }

    public JournalEntryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
