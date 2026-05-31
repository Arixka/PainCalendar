package m.siverio.paincalendar.painrecord.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import m.siverio.paincalendar.painrecord.application.service.CreatePainRecordService;
import m.siverio.paincalendar.painrecord.application.service.GetPainRecordByIdService;
import m.siverio.paincalendar.painrecord.application.service.UpdatePainRecordService;
import m.siverio.paincalendar.painrecord.domain.port.in.CreatePainRecordUseCase;
import m.siverio.paincalendar.painrecord.domain.port.in.GetPainRecordByIdUseCase;
import m.siverio.paincalendar.painrecord.domain.port.in.UpdatePainRecordUseCase;
import m.siverio.paincalendar.painrecord.domain.port.out.PainRecordRepository;
import m.siverio.paincalendar.painrecord.domain.port.out.LoadMedicationPort;

@Configuration
public class PainRecordConfig {

    @Bean
    public CreatePainRecordUseCase createPainRecordUseCase(
            PainRecordRepository painRecordRepository,
            LoadMedicationPort loadMedicationPort) {
        return new CreatePainRecordService(painRecordRepository, loadMedicationPort);
    }

    @Bean
    public UpdatePainRecordUseCase updatePainRecordUseCase(
            PainRecordRepository painRecordRepository,
            LoadMedicationPort loadMedicationPort) {
        return new UpdatePainRecordService(painRecordRepository, loadMedicationPort);
    }

    @Bean
    public GetPainRecordByIdUseCase getPainRecordByIdUseCase(PainRecordRepository painRecordRepository) {
        return new GetPainRecordByIdService(painRecordRepository);
    }

}
