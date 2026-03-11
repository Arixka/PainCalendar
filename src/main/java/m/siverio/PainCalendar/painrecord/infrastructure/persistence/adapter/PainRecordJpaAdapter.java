package m.siverio.paincalendar.painrecord.infrastructure.persistence.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import m.siverio.paincalendar.painrecord.domain.port.out.PainRecordRepository;
import m.siverio.paincalendar.painrecord.domain.model.PainRecord;
import m.siverio.paincalendar.painrecord.domain.model.PainRecordSummaryView;
import m.siverio.paincalendar.painrecord.infrastructure.persistence.repository.PainRecordJpaRepository;
import m.siverio.paincalendar.painrecord.infrastructure.persistence.repository.PainRecordByDateRangeDbView;
import m.siverio.paincalendar.painrecord.infrastructure.persistence.mapper.PainRecordMapper;
import m.siverio.paincalendar.painrecord.infrastructure.persistence.entity.PainRecordEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PainRecordJpaAdapter implements PainRecordRepository {
    private final PainRecordJpaRepository repository;
    private final PainRecordMapper mapper;

    @Override
    public PainRecord save(PainRecord domain) {
        PainRecordEntity entity = java.util.Objects.requireNonNull(mapper.toEntity(domain));

        PainRecordEntity savedEntity = repository.save(entity);

        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<PainRecordSummaryView> findByUserIdAndDateBetween(UUID userId, LocalDate startDate, LocalDate endDate) {
        List<PainRecordByDateRangeDbView> listaDesdeBbdd = repository.findByUserIdAndDateBetween(userId, startDate, endDate);
        
        return listaDesdeBbdd.stream()
                .map(dbView -> new PainRecordSummaryView(
                        dbView.getId(),
                        dbView.getDate(),
                        dbView.getIntensity(),
                        dbView.getLocation()
                ))
                .toList();
    }
}
