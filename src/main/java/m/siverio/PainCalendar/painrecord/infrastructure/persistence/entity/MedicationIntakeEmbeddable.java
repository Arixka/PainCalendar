package m.siverio.paincalendar.painrecord.infrastructure.persistence.entity;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicationIntakeEmbeddable {
    private UUID medicationId;
    private BigDecimal dose;
    private String medicationName;
}
