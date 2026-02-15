package m.siverio.paincalendar.medication.infrastructure.persistence.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import m.siverio.paincalendar.medication.domain.model.Medication;
import m.siverio.paincalendar.medication.domain.port.out.MedicationRepository;
import m.siverio.paincalendar.medication.infrastructure.persistence.entity.MedicationEntity;
import java.util.Optional;
import java.util.UUID;

import m.siverio.paincalendar.medication.infrastructure.persistence.mapper.MedicationMapper;
import m.siverio.paincalendar.medication.infrastructure.persistence.repository.MedicationJpaRepository;
import m.siverio.paincalendar.painrecord.domain.port.out.LoadMedicationPort;

@Component
@RequiredArgsConstructor
public class MedicationJpaAdapter implements MedicationRepository, LoadMedicationPort {

    private final MedicationJpaRepository repository;
    private final MedicationMapper mapper;

    @Override
    public Medication save(Medication domain) {
        MedicationEntity entity = java.util.Objects.requireNonNull(mapper.toEntity(domain));

        MedicationEntity saveEntity = repository.save(entity);

        return mapper.toDomain(saveEntity);
    }

    @Override
    public Optional<String> loadMedicationName(UUID medicationId) {
        return repository.findById(medicationId)
                .map(entity -> entity.getName());
    }
}
