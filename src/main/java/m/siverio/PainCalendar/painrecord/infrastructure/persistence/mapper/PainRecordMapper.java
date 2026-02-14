package m.siverio.paincalendar.painrecord.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import m.siverio.paincalendar.painrecord.domain.model.PainRecord;
import m.siverio.paincalendar.painrecord.infrastructure.persistence.entity.PainRecordEntity;

@Component
@RequiredArgsConstructor
public class PainRecordMapper {
    public PainRecordEntity toEntity(PainRecord domain) {
        PainRecordEntity entity = new PainRecordEntity();
        entity.setId(domain.getId().getId());
        entity.setUserId(domain.getUserId());
        entity.setDate(domain.getDate());
        entity.setSlot(domain.getSlot());
        entity.setIntensity(domain.getIntensity());
        entity.setNote(domain.getNote());

        if (domain.getMedicationIds() != null) {
            entity.setMedications(domain.getMedicationIds().stream()
                    .map(m -> new m.siverio.paincalendar.painrecord.infrastructure.persistence.entity.MedicationIntakeEmbeddable(
                            m.getMedicationId(), m.getDose(), m.getMedicationName()))
                    .collect(java.util.stream.Collectors.toList()));
        }
        return entity;
    }

    public PainRecord toDomain(PainRecordEntity entity) {
        java.util.List<m.siverio.paincalendar.painrecord.domain.model.MedicationIntake> medications = java.util.Collections
                .emptyList();

        if (entity.getMedications() != null) {
            medications = entity.getMedications().stream()
                    .map(m -> new m.siverio.paincalendar.painrecord.domain.model.MedicationIntake(
                            m.getMedicationId(), m.getDose(), m.getMedicationName()))
                    .collect(java.util.stream.Collectors.toList());
        }

        return new PainRecord(
                new m.siverio.paincalendar.painrecord.domain.model.PainRecordId(entity.getId()),
                entity.getUserId(),
                entity.getDate(),
                entity.getSlot(),
                entity.getIntensity(),
                entity.getNote(),
                medications);
    }
}
