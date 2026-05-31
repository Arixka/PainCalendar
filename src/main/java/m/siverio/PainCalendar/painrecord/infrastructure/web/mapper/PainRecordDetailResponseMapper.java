package m.siverio.paincalendar.painrecord.infrastructure.web.mapper;

import m.siverio.paincalendar.painrecord.domain.model.PainRecord;
import m.siverio.paincalendar.painrecord.infrastructure.web.dto.PainRecordDetailResponse;

public final class PainRecordDetailResponseMapper {

    private PainRecordDetailResponseMapper() {
    }

    public static PainRecordDetailResponse toResponse(PainRecord painRecord) {
        return new PainRecordDetailResponse(
                painRecord.getId().getId(),
                painRecord.getDate(),
                painRecord.getSlot(),
                painRecord.getIntensity(),
                painRecord.getLocation(),
                painRecord.getNote()
        );
    }
}
