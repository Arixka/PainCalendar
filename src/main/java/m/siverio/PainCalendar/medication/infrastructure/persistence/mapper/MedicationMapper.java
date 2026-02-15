package m.siverio.paincalendar.medication.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import m.siverio.paincalendar.medication.domain.model.Medication;
import m.siverio.paincalendar.medication.infrastructure.persistence.entity.MedicationEntity;

@Component
@RequiredArgsConstructor
public class MedicationMapper {
    public MedicationEntity toEntity(Medication domain) {
        MedicationEntity entity = new MedicationEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setCategory(domain.getCategory());
        return entity;
    }

    public Medication toDomain(MedicationEntity entity) {
        return new Medication(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCategory());
    }
}
