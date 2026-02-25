package m.siverio.paincalendar.painrecord.domain.port.in;

import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

import m.siverio.paincalendar.painrecord.domain.model.PainRecordSummaryView;

public interface GetMonthlyPainRecordsUseCase {
    List<PainRecordSummaryView> getMonthlyPainRecords(UUID userId, YearMonth targetMonth);
}
