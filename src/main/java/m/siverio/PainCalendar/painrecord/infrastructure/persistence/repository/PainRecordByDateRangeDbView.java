package m.siverio.paincalendar.painrecord.infrastructure.persistence.repository;

import java.time.LocalDate;
import java.util.UUID;

public interface PainRecordByDateRangeDbView {
    UUID getId();
    LocalDate getDate();
    Integer getIntensity();
    String getLocation();
}
