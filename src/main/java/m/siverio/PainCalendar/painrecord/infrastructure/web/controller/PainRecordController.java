package m.siverio.paincalendar.painrecord.infrastructure.web.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import m.siverio.paincalendar.painrecord.domain.port.in.CreatePainRecordCommand;
import m.siverio.paincalendar.painrecord.domain.port.in.CreatePainRecordUseCase;

@RestController
@RequestMapping("/pain-records")
@RequiredArgsConstructor
public class PainRecordController {
    private final CreatePainRecordUseCase createPainRecordUseCase;
        
    @PostMapping("/pain-records")
    public ResponseEntity<Void> create(@RequestBody CreatePainRecordCommand command) {
        UUID id = createPainRecordUseCase.createPainRecord(command);
        URI location = URI.create("/pain-records/" + id);
        return ResponseEntity.created(location).build();
    }
}
