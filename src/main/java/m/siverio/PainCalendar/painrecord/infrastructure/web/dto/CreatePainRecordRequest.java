package m.siverio.paincalendar.painrecord.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import m.siverio.paincalendar.painrecord.domain.model.Slot;

public record CreatePainRecordRequest(
        @NotNull UUID userId,
        @NotNull LocalDate date,
        @NotNull Slot slot,
        @NotNull @Min(0) @Max(10) Integer intensity,
        @Size(max = 300) String note,
        @Valid List<MedicationIntakeItemRequest> medications
) {
    public record MedicationIntakeItemRequest(
            @NotNull UUID medicationId,
            @NotNull @Positive BigDecimal quantity
    ) {
    }
}
