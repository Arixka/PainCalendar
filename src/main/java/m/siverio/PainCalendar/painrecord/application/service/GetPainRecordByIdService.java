package m.siverio.paincalendar.painrecord.application.service;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import m.siverio.paincalendar.painrecord.domain.exception.PainRecordNotFoundException;
import m.siverio.paincalendar.painrecord.domain.model.PainRecord;
import m.siverio.paincalendar.painrecord.domain.model.PainRecordId;
import m.siverio.paincalendar.painrecord.domain.port.in.GetPainRecordByIdUseCase;
import m.siverio.paincalendar.painrecord.domain.port.out.PainRecordRepository;

@RequiredArgsConstructor
public class GetPainRecordByIdService implements GetPainRecordByIdUseCase {

    private final PainRecordRepository painRecordRepository;

    @Override
    public PainRecord getPainRecordById(UUID id) {
        return painRecordRepository.findById(new PainRecordId(id))
                .orElseThrow(() -> new PainRecordNotFoundException(id));
    }
}
