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
}
