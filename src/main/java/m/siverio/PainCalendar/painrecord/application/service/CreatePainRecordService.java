package m.siverio.paincalendar.painrecord.application.service;

import java.util.UUID;

import m.siverio.paincalendar.painrecord.domain.port.in.CreatePainRecordUseCase;
import m.siverio.paincalendar.painrecord.domain.port.in.CreatePainRecordCommand;

public class CreatePainRecordService implements CreatePainRecordUseCase {

    @Override
    public UUID createPainRecord(CreatePainRecordCommand request) {
        validateRequest(request);
        return UUID.randomUUID();
    }

    private void validateRequest(CreatePainRecordCommand request) {
        Integer intensity = request.getIntensity();
        String note = request.getNote();
        if (null != note && note.length() > 300) {
            throw new IllegalArgumentException("Note must be max 300 characters");
        }
        if (request.getUserId() == null) {
            throw new IllegalArgumentException("UserId not be null");
        }
        if (request.getSlot() == null) {
            throw new IllegalArgumentException("Slot not be null");
        }
        if (request.getDate() == null) {
            throw new IllegalArgumentException("Date not be null");
        }
        if (intensity == null || (intensity > 10 || intensity < 0)) {
            throw new IllegalArgumentException("Intensity must be between 0 and 10");
        }
    }

}
