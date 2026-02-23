package m.siverio.paincalendar.painrecord.application.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import m.siverio.paincalendar.painrecord.domain.port.in.CreatePainRecordUseCase;
import m.siverio.paincalendar.painrecord.domain.port.out.PainRecordRepository;
import m.siverio.paincalendar.painrecord.domain.model.PainRecord;
import m.siverio.paincalendar.painrecord.domain.model.PainRecordId;
import m.siverio.paincalendar.painrecord.domain.port.in.CreatePainRecordCommand;

import java.util.List;
import m.siverio.paincalendar.painrecord.domain.port.out.LoadMedicationPort;
import m.siverio.paincalendar.painrecord.domain.model.MedicationIntake;

@RequiredArgsConstructor
@Slf4j
public class CreatePainRecordService implements CreatePainRecordUseCase {

        private final PainRecordRepository painRecordRepository;
        private final LoadMedicationPort loadMedicationPort;

        @Override
        public UUID createPainRecord(CreatePainRecordCommand request) {
                List<MedicationIntake> medications = request.getMedications().stream()
                                .map(item -> {
                                        String name = loadMedicationPort.loadMedicationName(item.getMedicationId())
                                                        .orElse("Unknown Medication"); // Fallback simple por ahora
                                        return new MedicationIntake(item.getMedicationId(), item.getQuantity(), name);
                                })
                                .toList();

                PainRecord painRecord = new PainRecord(
                                new PainRecordId(UUID.randomUUID()),
                                request.getUserId(),
                                request.getDate(),
                                request.getSlot(),
                                request.getIntensity(),
                                request.getLocation(),
                                request.getNote(),
                                medications);
                painRecordRepository.save(painRecord);
                log.info("Pain record created successfully with ID: {}", painRecord.getId().getId());
                return painRecord.getId().getId();// usamos PainRecordId.getId() para devolver un UUID en vez de
                                                  // PainRecordId y asi no equivocarnos al devolver por ejemplo el id
                                                  // del user
        }

}
