package m.siverio.paincalendar.painrecord.infrastructure.web.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import m.siverio.paincalendar.painrecord.domain.port.in.CreatePainRecordCommand;
import m.siverio.paincalendar.painrecord.domain.port.in.CreatePainRecordUseCase;
import m.siverio.paincalendar.painrecord.infrastructure.web.dto.CreatePainRecordRequest;
import m.siverio.paincalendar.painrecord.infrastructure.web.mapper.CreatePainRecordWebMapper;

@RestController @RequestMapping("/pain-records") @RequiredArgsConstructor @Slf4j
public class PainRecordController {
    private final CreatePainRecordUseCase createPainRecordUseCase;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreatePainRecordRequest request) {
        log.info("Received request to create pain record for user: {}", request.userId());
        CreatePainRecordCommand command = CreatePainRecordWebMapper.toCommand(request);
        UUID id = createPainRecordUseCase.createPainRecord(command);
        URI location = URI.create("/pain-records/" + id);
        log.info("Pain record created successfully with ID: {}", id);
        return ResponseEntity.created(location).build();
    }
}
