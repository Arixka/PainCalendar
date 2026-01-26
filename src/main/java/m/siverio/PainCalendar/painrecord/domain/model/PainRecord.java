package m.siverio.paincalendar.painrecord.domain.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.Value;
import m.siverio.paincalendar.painrecord.domain.port.in.MedicationIntakeRequest;

@Value
public class PainRecord {
    UUID id;
    UUID userId;
    LocalDate date;
    Slot slot;
    Integer intensity;
    String note;
    List<MedicationIntakeRequest> medicationIds;
}
