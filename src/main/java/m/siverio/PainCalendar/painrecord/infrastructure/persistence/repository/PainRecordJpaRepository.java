package m.siverio.paincalendar.painrecord.infrastructure.persistence.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import m.siverio.paincalendar.painrecord.infrastructure.persistence.entity.PainRecordEntity;
import java.time.LocalDate;
import java.util.List;

public interface PainRecordJpaRepository extends JpaRepository<PainRecordEntity, UUID> {

    List<PainRecordByDateRangeDbView> findByUserIdAndDateBetween(UUID userId, LocalDate startDate, LocalDate endDate);

}
