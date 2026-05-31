package m.siverio.paincalendar.painrecord.infrastructure.web.controller;

import java.net.URI;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import m.siverio.paincalendar.painrecord.domain.model.PainRecordSummaryView;
import m.siverio.paincalendar.painrecord.domain.port.in.CreatePainRecordCommand;
import m.siverio.paincalendar.painrecord.domain.port.in.CreatePainRecordUseCase;
import m.siverio.paincalendar.painrecord.domain.port.in.GetPainRecordByIdUseCase;
import m.siverio.paincalendar.painrecord.domain.port.in.GetMonthlyPainRecordsUseCase;
import m.siverio.paincalendar.painrecord.domain.port.in.UpdatePainRecordCommand;
import m.siverio.paincalendar.painrecord.domain.port.in.UpdatePainRecordUseCase;
import m.siverio.paincalendar.painrecord.infrastructure.web.dto.CreatePainRecordRequest;
import m.siverio.paincalendar.painrecord.infrastructure.web.dto.GetMonthlyPainRecordsRequest;
import m.siverio.paincalendar.painrecord.infrastructure.web.dto.PainRecordDetailResponse;
import m.siverio.paincalendar.painrecord.infrastructure.web.dto.UpdatePainRecordRequest;
import m.siverio.paincalendar.painrecord.infrastructure.web.mapper.CreatePainRecordWebMapper;
import m.siverio.paincalendar.painrecord.infrastructure.web.mapper.PainRecordDetailResponseMapper;
import m.siverio.paincalendar.painrecord.infrastructure.web.mapper.UpdatePainRecordWebMapper;

@RestController
@RequestMapping("/pain-records")
@Validated
@RequiredArgsConstructor
@Slf4j
public class PainRecordController {
    private final CreatePainRecordUseCase createPainRecordUseCase;
    private final GetMonthlyPainRecordsUseCase getMonthlyPainRecordsUseCase;
    private final UpdatePainRecordUseCase updatePainRecordUseCase;
    private final GetPainRecordByIdUseCase getPainRecordByIdUseCase;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreatePainRecordRequest request) {
        CreatePainRecordCommand command = CreatePainRecordWebMapper.toCommand(request);
        UUID id = createPainRecordUseCase.createPainRecord(command);
        URI location = URI.create("/pain-records/" + id);
        log.info("Pain record created successfully with ID: {}", id);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @Valid @RequestBody UpdatePainRecordRequest request) {
        UpdatePainRecordCommand command = UpdatePainRecordWebMapper.toCommand(id, request);
        updatePainRecordUseCase.updatePainRecord(command);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PainRecordDetailResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(
                PainRecordDetailResponseMapper.toResponse(
                        getPainRecordByIdUseCase.getPainRecordById(id)));
    }

    @GetMapping
    public ResponseEntity<List<PainRecordSummaryView>> getMonthlyRecords(
            @Valid @ModelAttribute GetMonthlyPainRecordsRequest request) {
        
        YearMonth targetMonth = YearMonth.of(request.year(), request.month());
        List<PainRecordSummaryView> records = getMonthlyPainRecordsUseCase.getMonthlyPainRecords(request.userId(), targetMonth);
        return ResponseEntity.ok(records);
    }
}
