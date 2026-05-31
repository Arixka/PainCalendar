package m.siverio.paincalendar.painrecord.application.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import m.siverio.paincalendar.painrecord.domain.exception.PainRecordNotFoundException;
import m.siverio.paincalendar.painrecord.domain.model.MedicationIntake;
import m.siverio.paincalendar.painrecord.domain.model.PainRecord;
import m.siverio.paincalendar.painrecord.domain.model.PainRecordId;
import m.siverio.paincalendar.painrecord.domain.port.in.UpdatePainRecordCommand;
import m.siverio.paincalendar.painrecord.domain.port.in.UpdatePainRecordUseCase;
import m.siverio.paincalendar.painrecord.domain.port.out.LoadMedicationPort;
import m.siverio.paincalendar.painrecord.domain.port.out.PainRecordRepository;

@RequiredArgsConstructor
public class UpdatePainRecordService implements UpdatePainRecordUseCase {

    private final PainRecordRepository painRecordRepository;
    private final LoadMedicationPort loadMedicationPort;

    @Override
    public void updatePainRecord(UpdatePainRecordCommand request) {
        PainRecordId painRecordId = new PainRecordId(request.getId());
        PainRecord existingPainRecord = painRecordRepository.findById(painRecordId)
                .orElseThrow(() -> new PainRecordNotFoundException(request.getId()));

        List<MedicationIntake> medications = resolveMedications(request, existingPainRecord);

        PainRecord updatedPainRecord = new PainRecord(
                painRecordId,
                request.getUserId(),
                request.getDate(),
                request.getSlot(),
                request.getIntensity(),
                request.getLocation(),
                request.getNote(),
                medications);

        painRecordRepository.save(updatedPainRecord);
    }

    private List<MedicationIntake> resolveMedications(UpdatePainRecordCommand request, PainRecord existingPainRecord) {
        if (request.getMedications() == null) {
            return existingPainRecord.getMedicationIds();
        }

        return request.getMedications().stream()
                .map(item -> {
                    String name = loadMedicationPort.loadMedicationName(item.getMedicationId())
                            .orElse("Unknown Medication");
                    return new MedicationIntake(item.getMedicationId(), item.getQuantity(), name);
                })
                .toList();
    }
}
