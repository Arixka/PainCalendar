package m.siverio.paincalendar.medication.infrastructure.persistence.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import m.siverio.paincalendar.medication.domain.model.MedicationCategory;

@Entity
@Data
@Table(name = "medication")
public class MedicationEntity {
    @Id
    private UUID id;
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private MedicationCategory category;
}
