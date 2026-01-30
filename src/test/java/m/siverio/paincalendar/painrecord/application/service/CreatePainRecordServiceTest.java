package m.siverio.paincalendar.painrecord.application.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import m.siverio.paincalendar.painrecord.domain.model.Slot;
import m.siverio.paincalendar.painrecord.domain.port.in.PainRecordRequest;

public class CreatePainRecordServiceTest {

    @Test
    void returnsIdWhenCreatingRecord() {
        CreatePainRecordService service = new CreatePainRecordService();
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        Slot slot = Slot.MORNING;

        UUID createdId = service.createPainRecord(new PainRecordRequest(
                userId, date, slot, 1, null, List.of()));
        assertNotNull(createdId);
    }

    @Test
    void throwsExceptionWhenDateIsNull() {
        CreatePainRecordService service = new CreatePainRecordService();
        UUID userId = UUID.randomUUID();
        Slot slot = Slot.MORNING;

        // fecha null
        assertThrows(IllegalArgumentException.class, () -> service.createPainRecord(new PainRecordRequest(
                userId, null, slot, 5, null, List.of())));

    }

    @Test
    void throwsExceptionWhenUserIsNull() {
        CreatePainRecordService service = new CreatePainRecordService();
        LocalDate date = LocalDate.now();
        Slot slot = Slot.MORNING;

        // userId null
        assertThrows(IllegalArgumentException.class, () -> service.createPainRecord(new PainRecordRequest(
                null, date, slot, 5, null, List.of())));

    }

    @Test
    void throwsExceptionWhenSlotIsNull() {
        CreatePainRecordService service = new CreatePainRecordService();
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();

        // slot null
        assertThrows(IllegalArgumentException.class, () -> service.createPainRecord(new PainRecordRequest(
                userId, date, null, 1, null, List.of())));

    }

    @Test
    void throwsExceptionWhenIntensityIsOutOfRange() {
        CreatePainRecordService service = new CreatePainRecordService();
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        Slot slot = Slot.MORNING;

        // intensidad > 10
        assertThrows(IllegalArgumentException.class, () -> service.createPainRecord(new PainRecordRequest(
                userId, date, slot, 11, null, List.of())));

        // intensidad negativa
        assertThrows(IllegalArgumentException.class, () -> service.createPainRecord(new PainRecordRequest(
                userId, date, slot, -1, null, List.of())));
    }

    @Test
    void throwsExceptionWhenNoteIsOutOfMax() {
        CreatePainRecordService service = new CreatePainRecordService();
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.now();
        Slot slot = Slot.MORNING;
        String note = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.";

        assertThrows(IllegalArgumentException.class, () -> service.createPainRecord(new PainRecordRequest(
            userId, date, slot, 1, note, List.of())));
    }

}
