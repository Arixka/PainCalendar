package m.siverio.paincalendar.painrecord.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import m.siverio.paincalendar.painrecord.domain.model.PainRecord;
import m.siverio.paincalendar.painrecord.infrastructure.persistence.entity.PainRecordEntity;

@Component @RequiredArgsConstructor
public class PainRecordMapper {
    public PainRecordEntity toEntity(PainRecord domain) {
        PainRecordEntity entity = new PainRecordEntity();
        entity.setId(domain.getId().getId());
        entity.setUserId(domain.getUserId());
        entity.setDate(domain.getDate());
        entity.setSlot(domain.getSlot());
        entity.setIntensity(domain.getIntensity());
        entity.setNote(domain.getNote());
        return entity;
    }
}
