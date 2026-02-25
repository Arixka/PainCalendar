package m.siverio.paincalendar.painrecord.application.service;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import m.siverio.paincalendar.painrecord.domain.model.PainRecordSummaryView;
import m.siverio.paincalendar.painrecord.domain.port.out.PainRecordRepository;


@ExtendWith(MockitoExtension.class)
public class GetMonthlyPainRecordsServiceTest {

    @Mock
    private PainRecordRepository painRecordRepository;

    @InjectMocks
    private GetMonthlyPainRecordsService service;
    
    @Test
    void returnsIdWhenCreatingRecord() {
        UUID userId = UUID.randomUUID();
        YearMonth targetMonth = YearMonth.of(2026, 2); 
        LocalDate expectedEndDate = LocalDate.of(2026, 2, 28);
        LocalDate expectedStartDate = LocalDate.of(2026, 2, 1);

        when(painRecordRepository.findByUserIdAndDateBetween(userId, expectedStartDate, expectedEndDate))
             .thenReturn(java.util.Collections.emptyList());
             
        List<PainRecordSummaryView> result = service.getMonthlyPainRecords(userId, targetMonth); 
        assertNotNull(result);
        verify(painRecordRepository).findByUserIdAndDateBetween(userId, expectedStartDate, expectedEndDate);
    }   
}
