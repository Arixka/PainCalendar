package m.siverio.paincalendar.painrecord.infrastructure.persistence.adapter;

import m.siverio.paincalendar.painrecord.infrastructure.persistence.mapper.PainRecordMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collections;
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

    @Test
    void shouldSaveAndRetrievePainRecord() {
        PainRecord record = new PainRecord(
                new PainRecordId(UUID.randomUUID()),
                UUID.randomUUID(),
                LocalDate.now(),
                Slot.MORNING,
                5,
                "Pain note",
                Collections.emptyList());

        PainRecord saved = adapter.save(record);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isEqualTo(record.getId());
    }
}
