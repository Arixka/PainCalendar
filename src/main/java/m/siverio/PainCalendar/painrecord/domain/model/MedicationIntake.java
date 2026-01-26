package m.siverio.paincalendar.painrecord.domain.model;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Value;

@Value
public class MedicationIntake {
    UUID medicationId;
    BigDecimal dose;
}
