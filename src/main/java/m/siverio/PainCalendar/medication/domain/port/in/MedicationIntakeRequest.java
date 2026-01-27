package m.siverio.paincalendar.medication.domain.port.in;

import java.util.UUID;

import lombok.Value;

@Value
public class MedicationIntakeRequest {
    UUID medicationId;
    int quantity;
}
