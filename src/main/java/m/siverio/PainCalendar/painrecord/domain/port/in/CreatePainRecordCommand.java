package m.siverio.paincalendar.painrecord.domain.port.in;

import lombok.Value;
import m.siverio.paincalendar.painrecord.domain.model.Slot;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Value
public class CreatePainRecordCommand {
    UUID userId;
    LocalDate date;
    Slot slot;
    Integer intensity;
    String note;
    List<MedicationIntakeItem> medications;

    @Value
    public class MedicationIntakeItem {
        UUID medicationId;
        BigDecimal quantity;
    }

}