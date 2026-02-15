package m.siverio.paincalendar.medication.domain.model;

import java.util.UUID;
import lombok.Value;

@Value
public class Medication {
    UUID id;
    String name;
    String description;
    MedicationCategory category;
}
