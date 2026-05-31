package m.siverio.paincalendar.painrecord.domain.exception;

import java.util.UUID;

public class PainRecordNotFoundException extends RuntimeException {

    public PainRecordNotFoundException(UUID painRecordId) {
        super("Pain record not found: " + painRecordId);
    }
}
