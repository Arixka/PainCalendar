package m.siverio.paincalendar.painrecord.domain.port.in;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Value;

@Value
public class MedicationIntakeRequest {
    UUID medicationId;
    BigDecimal quantity;
}
