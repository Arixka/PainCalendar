package m.siverio.paincalendar.painrecord.domain.port.in;

import java.util.UUID;

public interface CreatePainRecordUseCase {
    UUID createPainRecord(PainRecordRequest request);
}
