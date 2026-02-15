package m.siverio.paincalendar.medication.domain.port.in;

import java.util.UUID;

public interface CreateMedicationUseCase {
    UUID createMedication(CreateMedicationCommand request);
}
