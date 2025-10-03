package m.siverio.paincalendar.painrecord.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
                userId, date, slot, null, null, List.of()
        ));

        assertNotNull(createdId);
    }
}
