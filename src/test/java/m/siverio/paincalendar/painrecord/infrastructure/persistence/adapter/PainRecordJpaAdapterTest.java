package m.siverio.paincalendar.painrecord.infrastructure.persistence.adapter;

import m.siverio.paincalendar.painrecord.infrastructure.persistence.mapper.PainRecordMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import m.siverio.paincalendar.painrecord.domain.model.MedicationIntake;
import m.siverio.paincalendar.painrecord.domain.model.PainRecord;
import m.siverio.paincalendar.painrecord.domain.model.PainRecordSummaryView;
import m.siverio.paincalendar.painrecord.domain.model.PainRecordId;
import m.siverio.paincalendar.painrecord.domain.model.Slot;
import m.siverio.paincalendar.painrecord.infrastructure.persistence.repository.PainRecordJpaRepository;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@EntityScan("m.siverio.paincalendar.painrecord.infrastructure.persistence.entity")
@EnableJpaRepositories("m.siverio.paincalendar.painrecord.infrastructure.persistence.repository")
@Import({ PainRecordJpaAdapter.class, PainRecordMapper.class })
@TestPropertySource(properties = "spring.sql.init.mode=never")
class PainRecordJpaAdapterTest {

    @Autowired
    private PainRecordJpaAdapter adapter;

    @Autowired
    private PainRecordJpaRepository jpaRepository;

    @Test
    void shouldSaveAndRetrievePainRecord() {
        PainRecord record = new PainRecord(
                new PainRecordId(UUID.randomUUID()),
                UUID.randomUUID(),
                LocalDate.now(),
                Slot.MORNING,
                5,
                "Brazo",
                "Pain note",
                List.of(new MedicationIntake(
                        UUID.randomUUID(), BigDecimal.TEN, "Ibuprofeno")));

        PainRecord saved = adapter.save(record);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isEqualTo(record.getId());

        var entity = jpaRepository.findById(Objects.requireNonNull(record.getId().getId()));
        assertThat(entity).isPresent();
        assertThat(entity.get().getMedications()).hasSize(1);
        assertThat(entity.get().getMedications().get(0).getMedicationName()).isEqualTo("Ibuprofeno");
        assertThat(entity.get().getLocation()).isEqualTo("Brazo");
    }

    @Test
    void shouldRetrieveMonthlyPainRecords() {
        UUID userId = UUID.randomUUID();

        PainRecord record1 = new PainRecord(
                new PainRecordId(UUID.randomUUID()), userId, LocalDate.of(2026, 1, 10),
                Slot.MORNING, 5, "Brazo", "Dolor leve", List.of());
        PainRecord record2 = new PainRecord(
                new PainRecordId(UUID.randomUUID()), userId, LocalDate.of(2026, 1, 20),
                Slot.NIGHT, 8, "Cabeza", "Migraña", List.of());
        PainRecord record3 = new PainRecord(
                new PainRecordId(UUID.randomUUID()), userId, LocalDate.of(2026, 2, 5),
                Slot.AFTERNOON, 3, "Espalda", "Molestia leve", List.of());

        adapter.save(record1);
        adapter.save(record2);
        adapter.save(record3);

        LocalDate startDate = LocalDate.of(2026, 1, 1);
        LocalDate endDate = LocalDate.of(2026, 1, 31);

        List<PainRecordSummaryView> monthlyRecords = adapter.findByUserIdAndDateBetween(userId, startDate, endDate);

        assertThat(monthlyRecords).hasSize(2);
        assertThat(monthlyRecords)
                .extracting(PainRecordSummaryView::getDate)
                .containsExactlyInAnyOrder(LocalDate.of(2026, 1, 10), LocalDate.of(2026, 1, 20));
    }
}
