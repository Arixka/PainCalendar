package m.siverio.paincalendar.painrecord.infrastructure.persistence.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import m.siverio.paincalendar.painrecord.infrastructure.persistence.entity.PainRecordEntity;

public interface PainRecordJpaRepository extends JpaRepository<PainRecordEntity, UUID> {

}
