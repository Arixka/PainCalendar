package m.siverio.paincalendar.painrecord.application.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import m.siverio.paincalendar.painrecord.domain.model.PainRecordSummaryView;
import m.siverio.paincalendar.painrecord.domain.port.in.GetMonthlyPainRecordsUseCase;
import m.siverio.paincalendar.painrecord.domain.port.out.PainRecordRepository;

@RequiredArgsConstructor
@Slf4j
public class GetMonthlyPainRecordsService implements GetMonthlyPainRecordsUseCase {
    
    private final PainRecordRepository painRecordRepository;

    public List<PainRecordSummaryView> getMonthlyPainRecords(UUID userId, YearMonth targetMonth) {
        LocalDate startDate = targetMonth.atDay(1);
        LocalDate endDate = targetMonth.atEndOfMonth();
        return painRecordRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }
}
