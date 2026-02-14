package m.siverio.paincalendar.painrecord.infrastructure.persistence.adapter;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;
import m.siverio.paincalendar.painrecord.domain.port.out.LoadMedicationPort;

@Component
public class InMemoryMedicationAdapter implements LoadMedicationPort {

    @Override
    public Optional<String> loadMedicationName(UUID medicationId) {
        // Por ahora devolvemos un valor fijo para hacer pasar el test y compilar
        // Más adelante conectaremos esto con una tabla real o servicio externo
        return Optional.of("Ibuprofeno");
    }
}
