package m.siverio.paincalendar.painrecord.domain.port.in;

import lombok.Value;
import m.siverio.paincalendar.painrecord.domain.model.Slot;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Value
public class PainRecordRequest {
    UUID userId;
    LocalDate date;
    Slot slot;
    Integer intensity;
    String note;
    List<MedicationIntakeRequest> medicationIds;
}