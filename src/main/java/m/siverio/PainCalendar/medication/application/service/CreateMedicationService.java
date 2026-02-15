package m.siverio.paincalendar.medication.application.service;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import m.siverio.paincalendar.medication.domain.model.Medication;
import m.siverio.paincalendar.medication.domain.port.in.CreateMedicationCommand;
import m.siverio.paincalendar.medication.domain.port.in.CreateMedicationUseCase;
import m.siverio.paincalendar.medication.domain.port.out.MedicationRepository;

@Slf4j
public class CreateMedicationService implements CreateMedicationUseCase {

    private final MedicationRepository medicationRepository;

    public CreateMedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    @Override
    public UUID createMedication(CreateMedicationCommand command) {
        Medication medication = new Medication(
                command.getId(),
                command.getName(),
                command.getDescription(),
                command.getCategory());
        medicationRepository.save(medication);
        log.info("Medication created: {}", medication.getId());
        return medication.getId();
    }
}
