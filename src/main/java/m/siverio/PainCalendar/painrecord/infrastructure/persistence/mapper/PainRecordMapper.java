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
        return entity;
    }
}
