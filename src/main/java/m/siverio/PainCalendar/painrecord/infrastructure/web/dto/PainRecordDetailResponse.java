package m.siverio.paincalendar.painrecord.infrastructure.web.dto;

import java.time.LocalDate;
import java.util.UUID;

import m.siverio.paincalendar.painrecord.domain.model.Slot;

public record PainRecordDetailResponse(
        UUID id,
        LocalDate date,
        Slot slot,
        Integer intensity,
        String location,
        String note
) {
}
