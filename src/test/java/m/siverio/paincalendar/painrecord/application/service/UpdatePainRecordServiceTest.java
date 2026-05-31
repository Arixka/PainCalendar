package m.siverio.paincalendar.painrecord.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import m.siverio.paincalendar.painrecord.domain.exception.PainRecordNotFoundException;
import m.siverio.paincalendar.painrecord.domain.model.PainRecord;
import m.siverio.paincalendar.painrecord.domain.model.PainRecordId;
import m.siverio.paincalendar.painrecord.domain.model.Slot;
import m.siverio.paincalendar.painrecord.domain.port.in.UpdatePainRecordCommand;
import m.siverio.paincalendar.painrecord.domain.port.out.LoadMedicationPort;
import m.siverio.paincalendar.painrecord.domain.port.out.PainRecordRepository;

@ExtendWith(MockitoExtension.class)
class UpdatePainRecordServiceTest {

    @Mock
    private PainRecordRepository painRecordRepository;

    @Mock
    private LoadMedicationPort loadMedicationPort;

    @InjectMocks
    private UpdatePainRecordService service;

    @Test
    void shouldUpdateExistingPainRecord() {
        UUID recordId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        PainRecord existingRecord = new PainRecord(
                new PainRecordId(recordId),
                userId,
                LocalDate.of(2026, 6, 1),
                Slot.MORNING,
                4,
                "Cabeza",
                "Nota inicial",
                List.of());

        UpdatePainRecordCommand command = new UpdatePainRecordCommand(
                recordId,
                userId,
                LocalDate.of(2026, 6, 1),
                Slot.NIGHT,
                8,
                "Cuello",
                "Dolor actualizado",
                List.of());

        when(painRecordRepository.findById(new PainRecordId(recordId))).thenReturn(Optional.of(existingRecord));

        service.updatePainRecord(command);

        verify(painRecordRepository).save(argThat(record ->
                record.getId().getId().equals(recordId) &&
                        record.getUserId().equals(userId) &&
                        record.getSlot().equals(Slot.NIGHT) &&
                        record.getIntensity().equals(8) &&
                        "Cuello".equals(record.getLocation()) &&
                        "Dolor actualizado".equals(record.getNote())));
    }

    @Test
    void shouldThrowWhenPainRecordDoesNotExist() {
        UUID recordId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        UpdatePainRecordCommand command = new UpdatePainRecordCommand(
                recordId,
                userId,
                LocalDate.of(2026, 6, 1),
                Slot.NIGHT,
                8,
                "Cuello",
                "Dolor actualizado",
                List.of());

        when(painRecordRepository.findById(new PainRecordId(recordId))).thenReturn(Optional.empty());

        assertThrows(PainRecordNotFoundException.class, () -> service.updatePainRecord(command));
    }
}
