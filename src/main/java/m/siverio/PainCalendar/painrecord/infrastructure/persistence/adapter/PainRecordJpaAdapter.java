package m.siverio.paincalendar.painrecord.infrastructure.persistence.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import m.siverio.paincalendar.painrecord.domain.port.out.PainRecordRepository;
import m.siverio.paincalendar.painrecord.domain.model.PainRecord;
import m.siverio.paincalendar.painrecord.infrastructure.persistence.repository.PainRecordJpaRepository;
import m.siverio.paincalendar.painrecord.infrastructure.persistence.mapper.PainRecordMapper;
import m.siverio.paincalendar.painrecord.infrastructure.persistence.entity.PainRecordEntity;

@Component
@RequiredArgsConstructor
public class PainRecordJpaAdapter implements PainRecordRepository {
    private final PainRecordJpaRepository repository;
    private final PainRecordMapper mapper;

    @Override
    public PainRecord save(PainRecord domain) {
        PainRecordEntity entity = mapper.toEntity(domain);

        PainRecordEntity savedEntity = repository.save(entity);

        return domain;
    }
}
