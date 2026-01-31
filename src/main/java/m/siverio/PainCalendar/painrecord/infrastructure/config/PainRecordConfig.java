package m.siverio.paincalendar.painrecord.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import m.siverio.paincalendar.painrecord.application.service.CreatePainRecordService;
import m.siverio.paincalendar.painrecord.domain.port.in.CreatePainRecordUseCase;
import m.siverio.paincalendar.painrecord.domain.port.out.PainRecordRepository;

@Configuration
public class PainRecordConfig {

    @Bean
    public CreatePainRecordUseCase createPainRecordUseCase(PainRecordRepository painRecordRepository) {
        return new CreatePainRecordService(painRecordRepository);
    }

}
