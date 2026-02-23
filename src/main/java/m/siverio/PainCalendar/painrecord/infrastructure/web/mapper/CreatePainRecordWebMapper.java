package m.siverio.paincalendar.painrecord.infrastructure.web.mapper;

import java.util.Collections;
import java.util.List;
import m.siverio.paincalendar.painrecord.domain.port.in.CreatePainRecordCommand;
import m.siverio.paincalendar.painrecord.infrastructure.web.dto.CreatePainRecordRequest;

public final class CreatePainRecordWebMapper {

    private CreatePainRecordWebMapper() {
    }

    public static CreatePainRecordCommand toCommand(CreatePainRecordRequest request) {
        List<CreatePainRecordCommand.MedicationIntakeItem> medications = mapMedications(request.medications());

        return new CreatePainRecordCommand(
                request.userId(),
                request.date(),
                request.slot(),
                request.intensity(),
                request.location(),
                request.note(),
                medications
        );
    }

    private static List<CreatePainRecordCommand.MedicationIntakeItem> mapMedications(
            List<CreatePainRecordRequest.MedicationIntakeItemRequest> items
    ) {
        if (items == null || items.isEmpty()) {
            return Collections.emptyList();
        }

        return items.stream()
                .map(item -> new CreatePainRecordCommand.MedicationIntakeItem(
                        item.medicationId(),
                        item.quantity()
                ))
                .toList();
    }
}
