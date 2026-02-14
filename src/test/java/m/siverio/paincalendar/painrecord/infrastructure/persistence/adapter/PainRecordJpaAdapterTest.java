package m.siverio.paincalendar.painrecord.infrastructure.persistence.adapter;

import m.siverio.paincalendar.painrecord.infrastructure.persistence.mapper.PainRecordMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.UUID;

import m.siverio.paincalendar.painrecord.domain.model.PainRecord;
import m.siverio.paincalendar.painrecord.domain.model.PainRecordId;
import m.siverio.paincalendar.painrecord.domain.model.Slot;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@DataJpaTest
@EntityScan("m.siverio.paincalendar.painrecord.infrastructure.persistence.entity")
@EnableJpaRepositories("m.siverio.paincalendar.painrecord.infrastructure.persistence.repository")
@Import({ PainRecordJpaAdapter.class, PainRecordMapper.class })
class PainRecordJpaAdapterTest {

    @Autowired
    private PainRecordJpaAdapter adapter;

    @Autowired
    private m.siverio.paincalendar.painrecord.infrastructure.persistence.repository.PainRecordJpaRepository jpaRepository;

    @Test
    void shouldSaveAndRetrievePainRecord() {
        PainRecord record = new PainRecord(
                new PainRecordId(UUID.randomUUID()),
                UUID.randomUUID(),
                LocalDate.now(),
                Slot.MORNING,
                5,
                "Pain note",
                java.util.List.of(new m.siverio.paincalendar.painrecord.domain.model.MedicationIntake(
                        UUID.randomUUID(), java.math.BigDecimal.TEN, "Ibuprofeno")));

        PainRecord saved = adapter.save(record);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isEqualTo(record.getId());

        // Verify simple internal persistence
        var entity = jpaRepository.findById(java.util.Objects.requireNonNull(record.getId().getId()));
        assertThat(entity).isPresent();
        assertThat(entity.get().getMedications()).hasSize(1);
        assertThat(entity.get().getMedications().get(0).getMedicationName()).isEqualTo("Ibuprofeno");
    }
}
