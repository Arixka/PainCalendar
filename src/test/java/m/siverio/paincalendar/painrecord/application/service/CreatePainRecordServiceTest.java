package m.siverio.paincalendar.painrecord.application.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import m.siverio.paincalendar.painrecord.domain.model.Slot;
import m.siverio.paincalendar.painrecord.domain.port.in.CreatePainRecordCommand;
import m.siverio.paincalendar.painrecord.domain.port.out.LoadMedicationPort;
import m.siverio.paincalendar.painrecord.domain.port.out.PainRecordRepository;

@ExtendWith(MockitoExtension.class)
public class CreatePainRecordServiceTest {

    @Mock
    private PainRecordRepository painRecordRepository;

    @Mock
    private LoadMedicationPort loadMedicationPort;

    @InjectMocks
    private CreatePainRecordService service;

    @Test
    void returnsIdWhenCreatingRecord() {
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        Slot slot = Slot.MORNING;

        UUID createdId = service.createPainRecord(new CreatePainRecordCommand(
                userId, date, slot, 1, "Cabeza", null, List.of()));
        assertNotNull(createdId);
    }

    @Test
    void shouldLookUpMedicationNameWhenCreatingRecord() {
        UUID userId = UUID.randomUUID();
        UUID medId = UUID.randomUUID();

        CreatePainRecordCommand command = new CreatePainRecordCommand(
                userId, LocalDate.now(), Slot.MORNING, 5, "Cuello", null,
                List.of(new CreatePainRecordCommand.MedicationIntakeItem(medId, BigDecimal.ONE)));

        when(loadMedicationPort.loadMedicationName(medId))
                .thenReturn(Optional.of("Ibuprofeno"));

        service.createPainRecord(command);

        verify(loadMedicationPort).loadMedicationName(medId);
        verify(painRecordRepository)
                .save(argThat(record -> "Cuello".equals(record.getLocation()) &&
                        record.getMedicationIds().size() == 1 &&
                        record.getMedicationIds().get(0).getMedicationName().equals("Ibuprofeno")));
    }

    @Test
    void throwsExceptionWhenDateIsNull() {
        UUID userId = UUID.randomUUID();
        Slot slot = Slot.MORNING;
        assertThrows(IllegalArgumentException.class, () -> service.createPainRecord(new CreatePainRecordCommand(
                userId, null, slot, 5, null, null, List.of())));
    }

    @Test
    void throwsExceptionWhenUserIsNull() {
        LocalDate date = LocalDate.now();
        Slot slot = Slot.MORNING;
        assertThrows(IllegalArgumentException.class, () -> service.createPainRecord(new CreatePainRecordCommand(
                null, date, slot, 5, null, null, List.of())));
    }

    @Test
    void throwsExceptionWhenSlotIsNull() {
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        assertThrows(IllegalArgumentException.class, () -> service.createPainRecord(new CreatePainRecordCommand(
                userId, date, null, 1, null, null, List.of())));
    }

    @Test
    void throwsExceptionWhenIntensityIsOutOfRange() {
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        Slot slot = Slot.MORNING;
        assertThrows(IllegalArgumentException.class, () -> service.createPainRecord(new CreatePainRecordCommand(
                userId, date, slot, 11, null, null, List.of())));
        assertThrows(IllegalArgumentException.class, () -> service.createPainRecord(new CreatePainRecordCommand(
                userId, date, slot, -1, null, null, List.of())));
    }

    @Test
    void throwsExceptionWhenNoteIsOutOfMax() {
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        Slot slot = Slot.MORNING;
        String note = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.";
        assertThrows(IllegalArgumentException.class, () -> service.createPainRecord(new CreatePainRecordCommand(
                userId, date, slot, 1, null, note, List.of())));
    }

}
