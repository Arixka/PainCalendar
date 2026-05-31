package m.siverio.paincalendar.painrecord.infrastructure.web.mapper;

import java.util.List;
import java.util.UUID;

import m.siverio.paincalendar.painrecord.domain.port.in.UpdatePainRecordCommand;
import m.siverio.paincalendar.painrecord.infrastructure.web.dto.UpdatePainRecordRequest;

public final class UpdatePainRecordWebMapper {

    private UpdatePainRecordWebMapper() {
    }

    public static UpdatePainRecordCommand toCommand(UUID id, UpdatePainRecordRequest request) {
        List<UpdatePainRecordCommand.MedicationIntakeItem> medications = mapMedications(request.medications());

        return new UpdatePainRecordCommand(
                id,
                request.userId(),
                request.date(),
                request.slot(),
                request.intensity(),
                request.location(),
                request.note(),
                medications
        );
    }

    private static List<UpdatePainRecordCommand.MedicationIntakeItem> mapMedications(
            List<UpdatePainRecordRequest.MedicationIntakeItemRequest> items) {
        if (items == null) {
            return null;
        }

        return items.stream()
                .map(item -> new UpdatePainRecordCommand.MedicationIntakeItem(
                        item.medicationId(),
                        item.quantity()
                ))
                .toList();
    }
}
