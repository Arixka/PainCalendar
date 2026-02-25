package m.siverio.paincalendar.painrecord.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Value;

@Value
public class PainRecordSummaryView {
     UUID id;
     LocalDate date;
     Integer intensity;
     String location;
}
