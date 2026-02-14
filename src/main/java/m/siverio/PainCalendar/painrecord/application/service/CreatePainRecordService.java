package m.siverio.paincalendar.painrecord.application.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import m.siverio.paincalendar.painrecord.domain.port.in.CreatePainRecordUseCase;
import m.siverio.paincalendar.painrecord.domain.port.out.PainRecordRepository;
import m.siverio.paincalendar.painrecord.domain.model.PainRecord;
import m.siverio.paincalendar.painrecord.domain.model.PainRecordId;
import m.siverio.paincalendar.painrecord.domain.port.in.CreatePainRecordCommand;

@RequiredArgsConstructor @Slf4j
public class CreatePainRecordService implements CreatePainRecordUseCase {

    private final PainRecordRepository painRecordRepository;

    @Override
    public UUID createPainRecord(CreatePainRecordCommand request) {
        PainRecord painRecord = new PainRecord(
                new PainRecordId(UUID.randomUUID()),
                request.getUserId(),
                request.getDate(),
                request.getSlot(),
                request.getIntensity(),
                request.getNote(),
                null // Medication logic is not yet implemented in command
        );
        painRecordRepository.save(painRecord);
        log.info("Pain record created successfully with ID: {}", painRecord.getId().getId());
        return painRecord.getId().getId();
    }

}
