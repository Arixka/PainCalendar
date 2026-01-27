package m.siverio.paincalendar.medication.domain.model;

import java.util.UUID;
import lombok.Value;

@Value
public class Medication {
    UUID Id;
    MedicationCategory category;
    String name;
}
