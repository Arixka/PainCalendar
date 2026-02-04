package m.siverio.paincalendar.painrecord.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Data;

@Entity
@Table(name = "pain_record")
@Data
public class PainRecordEntity {
    @Id
    private UUID id;
    // TODO: Add other fields later
}
