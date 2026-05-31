package m.siverio.paincalendar.painrecord.infrastructure.web.dto;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record GetMonthlyPainRecordsRequest(
        @NotNull UUID userId,
        @NotNull @Min(1) Integer year,
        @NotNull @Min(1) @Max(12) Integer month
) {
}
