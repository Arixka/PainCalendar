package m.siverio.paincalendar.medication.infrastructure.persistence.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import m.siverio.paincalendar.medication.infrastructure.persistence.entity.MedicationEntity;

public interface MedicationJpaRepository extends JpaRepository<MedicationEntity, UUID> {

}
