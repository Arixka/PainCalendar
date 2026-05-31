package m.siverio.paincalendar.shared.error;

public record ApiErrorResponse(
        String message,
        String code
) {
}
