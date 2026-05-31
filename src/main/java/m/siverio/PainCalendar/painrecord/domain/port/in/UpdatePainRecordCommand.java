package m.siverio.paincalendar.painrecord.domain.port.in;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.Value;
import m.siverio.paincalendar.painrecord.domain.model.Slot;

@Value
public class UpdatePainRecordCommand {
    UUID id;
    UUID userId;
    LocalDate date;
    Slot slot;
    Integer intensity;
    String location;
    String note;
    List<MedicationIntakeItem> medications;

    @Value
    public static class MedicationIntakeItem {
        UUID medicationId;
        BigDecimal quantity;
    }
}
