package m.siverio.paincalendar.medication.domain.port.in;

import java.util.UUID;

import lombok.Value;
import m.siverio.paincalendar.medication.domain.model.MedicationCategory;

@Value
public class CreateMedicationCommand {
    private UUID id;
    private String name;
    private String description;
    private MedicationCategory category;
}
