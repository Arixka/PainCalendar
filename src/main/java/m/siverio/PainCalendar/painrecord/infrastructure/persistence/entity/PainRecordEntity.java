package m.siverio.paincalendar.painrecord.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDate;
import java.util.UUID;
import m.siverio.paincalendar.painrecord.domain.model.Slot;
import lombok.Data;

@Entity @Table(name = "pain_record") @Data
public class PainRecordEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private Slot slot;

    @Column(nullable = false)
    private Integer intensity;

    @Column(length = 300)
    private String note;
}
