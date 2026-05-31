package m.siverio.paincalendar.painrecord.domain.port.in;

import java.util.UUID;

import m.siverio.paincalendar.painrecord.domain.model.PainRecord;

public interface GetPainRecordByIdUseCase {
    PainRecord getPainRecordById(UUID id);
}
