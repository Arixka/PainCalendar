package m.siverio.paincalendar.medication.application.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import m.siverio.paincalendar.medication.domain.model.Medication;
import m.siverio.paincalendar.medication.domain.model.MedicationCategory;
import m.siverio.paincalendar.medication.domain.port.in.CreateMedicationCommand;
import m.siverio.paincalendar.medication.domain.port.out.MedicationRepository;

@ExtendWith(MockitoExtension.class)
public class CreateMedicationServiceTest {

    @Mock
    private MedicationRepository medicationRepository;

    @InjectMocks
    private CreateMedicationService service;

    @Test
    void shouldCreateMedicationSuccessfully() {
        CreateMedicationCommand command = new CreateMedicationCommand(
                UUID.randomUUID(),
                "Ibuprofeno",
                "Para el dolor de cabeza",
                MedicationCategory.ANTIINFLAMMATORY);

        UUID id = service.createMedication(command);

        assertNotNull(id);
        verify(medicationRepository).save(any(Medication.class));
    }
}
