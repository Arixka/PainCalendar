package m.siverio.paincalendar.painrecord.application.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import m.siverio.paincalendar.painrecord.domain.model.Slot;
import m.siverio.paincalendar.painrecord.domain.port.in.CreatePainRecordCommand;
import m.siverio.paincalendar.painrecord.domain.port.out.PainRecordRepository;

@ExtendWith(MockitoExtension.class)
public class CreatePainRecordServiceTest {

    @Mock
    private PainRecordRepository painRecordRepository;

    @InjectMocks
    private CreatePainRecordService service;

    @Test
    void returnsIdWhenCreatingRecord() {
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        Slot slot = Slot.MORNING;
        // when(painRecordRepository.save(any(PainRecord.class))).thenReturn(new
        // PainRecordId(UUID.randomUUID()));
        UUID createdId = service.createPainRecord(new CreatePainRecordCommand(
                userId, date, slot, 1, null, List.of()));
        assertNotNull(createdId);
    }

    @Test
    void throwsExceptionWhenDateIsNull() {
        UUID userId = UUID.randomUUID();
        Slot slot = Slot.MORNING;
        assertThrows(IllegalArgumentException.class, () -> service.createPainRecord(new CreatePainRecordCommand(
                userId, null, slot, 5, null, List.of())));
    }

    @Test
    void throwsExceptionWhenUserIsNull() {
        LocalDate date = LocalDate.now();
        Slot slot = Slot.MORNING;
        assertThrows(IllegalArgumentException.class, () -> service.createPainRecord(new CreatePainRecordCommand(
                null, date, slot, 5, null, List.of())));
    }

    @Test
    void throwsExceptionWhenSlotIsNull() {
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        assertThrows(IllegalArgumentException.class, () -> service.createPainRecord(new CreatePainRecordCommand(
                userId, date, null, 1, null, List.of())));
    }

    @Test
    void throwsExceptionWhenIntensityIsOutOfRange() {
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        Slot slot = Slot.MORNING;
        assertThrows(IllegalArgumentException.class, () -> service.createPainRecord(new CreatePainRecordCommand(
                userId, date, slot, 11, null, List.of())));
        assertThrows(IllegalArgumentException.class, () -> service.createPainRecord(new CreatePainRecordCommand(
                userId, date, slot, -1, null, List.of())));
    }

    @Test
    void throwsExceptionWhenNoteIsOutOfMax() {
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        Slot slot = Slot.MORNING;
        String note = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.";
        assertThrows(IllegalArgumentException.class, () -> service.createPainRecord(new CreatePainRecordCommand(
                userId, date, slot, 1, note, List.of())));
    }

}
