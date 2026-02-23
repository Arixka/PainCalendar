package m.siverio.paincalendar.medication.infrastructure.persistence.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import m.siverio.paincalendar.medication.domain.model.Medication;
import m.siverio.paincalendar.medication.domain.model.MedicationCategory;
import m.siverio.paincalendar.medication.infrastructure.persistence.mapper.MedicationMapper;

import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@Import({ MedicationJpaAdapter.class, MedicationMapper.class })
@TestPropertySource(properties = "spring.sql.init.mode=never")
class MedicationJpaAdapterTest {

    @Autowired
    private MedicationJpaAdapter adapter;

    @Test
    void shouldSaveAndReturnMedication() {
        // GIVEN
        Medication medication = new Medication(
                UUID.randomUUID(),
                "Nolotil",
                "Pa los dolores",
                MedicationCategory.ANTIINFLAMMATORY);

        // WHEN
        Medication savedMedication = adapter.save(medication);

        // THEN
        assertThat(savedMedication).isNotNull();
        assertThat(savedMedication.getId()).isEqualTo(medication.getId());
        assertThat(savedMedication.getName()).isEqualTo("Nolotil");
    }
}
