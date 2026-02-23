package m.siverio.paincalendar.painrecord.infrastructure.persistence.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import m.siverio.paincalendar.painrecord.domain.model.MedicationIntake;
import m.siverio.paincalendar.painrecord.domain.model.PainRecord;
import m.siverio.paincalendar.painrecord.domain.model.PainRecordId;
import m.siverio.paincalendar.painrecord.infrastructure.persistence.entity.MedicationIntakeEmbeddable;
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
                entity.setLocation(domain.getLocation());
                entity.setNote(domain.getNote());

                if (domain.getMedicationIds() != null) {
                        entity.setMedications(domain.getMedicationIds().stream()
                                        .map(m -> new MedicationIntakeEmbeddable(
                                                        m.getMedicationId(), m.getDose(), m.getMedicationName()))
                                        .collect(Collectors.toList()));
                }
                return entity;
        }

        public PainRecord toDomain(PainRecordEntity entity) {
                List<MedicationIntake> medications = Collections
                                .emptyList();

                if (entity.getMedications() != null) {
                        medications = entity.getMedications().stream()
                                        .map(m -> new MedicationIntake(
                                                        m.getMedicationId(), m.getDose(), m.getMedicationName()))
                                        .collect(Collectors.toList());
                }

                return new PainRecord(
                                new PainRecordId(entity.getId()),
                                entity.getUserId(),
                                entity.getDate(),
                                entity.getSlot(),
                                entity.getIntensity(),
                                entity.getLocation(),
                                entity.getNote(),
                                medications);
        }
}
