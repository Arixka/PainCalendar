package m.siverio.paincalendar.painrecord.domain.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.Value;

@Value
public class PainRecord {
    PainRecordId id;
    UUID userId;
    LocalDate date;
    Slot slot;
    Integer intensity;
    String note;
    List<MedicationIntake> medicationIds;

    public PainRecord(PainRecordId id, UUID userId, LocalDate date, Slot slot, Integer intensity, String note,
            List<MedicationIntake> medicationIds) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId not be null");
        }
        if (slot == null) {
            throw new IllegalArgumentException("Slot not be null");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date not be null");
        }
        if (intensity == null || intensity < 0 || intensity > 10) {
            throw new IllegalArgumentException("Intensity must be between 0 and 10");
        }
        if (note != null && note.length() > 300) {
            throw new IllegalArgumentException("Note must be max 300 characters");
        }
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.slot = slot;
        this.intensity = intensity;
        this.note = note;
        this.medicationIds = medicationIds;
    }
}
