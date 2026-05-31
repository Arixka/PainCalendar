package m.siverio.paincalendar.painrecord.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import m.siverio.paincalendar.painrecord.domain.port.out.PainRecordRepository;

@ExtendWith(MockitoExtension.class)
class GetPainRecordByIdServiceTest {

    @Mock
    private PainRecordRepository painRecordRepository;

    @InjectMocks
    private GetPainRecordByIdService service;

    @Test
    void shouldReturnPainRecordWhenItExists() {
        UUID painRecordId = UUID.randomUUID();
        PainRecord painRecord = new PainRecord(
                new PainRecordId(painRecordId),
                UUID.randomUUID(),
                LocalDate.of(2026, 6, 1),
                Slot.EVENING,
                7,
                "Cabeza",
                "Dolor persistente",
                List.of());

        when(painRecordRepository.findById(new PainRecordId(painRecordId))).thenReturn(Optional.of(painRecord));

        PainRecord result = service.getPainRecordById(painRecordId);

        assertEquals(painRecord, result);
    }

    @Test
    void shouldThrowWhenPainRecordDoesNotExist() {
        UUID painRecordId = UUID.randomUUID();

        when(painRecordRepository.findById(new PainRecordId(painRecordId))).thenReturn(Optional.empty());

        assertThrows(PainRecordNotFoundException.class, () -> service.getPainRecordById(painRecordId));
    }
}
