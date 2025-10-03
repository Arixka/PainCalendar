package m.siverio.paincalendar.painrecord.domain.usecase;

import java.util.UUID;

import m.siverio.paincalendar.painrecord.domain.port.in.CreatePainRecordUseCase;
import m.siverio.paincalendar.painrecord.domain.port.in.PainRecordRequest;

public class CreatePainRecordService implements CreatePainRecordUseCase{

    @Override
    public UUID createPainRecord(PainRecordRequest request) {
        return UUID.randomUUID();
    }
    
}
