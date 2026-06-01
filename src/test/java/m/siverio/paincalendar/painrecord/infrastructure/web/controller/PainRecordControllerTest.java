package m.siverio.paincalendar.painrecord.infrastructure.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.atlassian.oai.validator.mockmvc.OpenApiValidationMatchers;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import m.siverio.paincalendar.painrecord.domain.exception.PainRecordNotFoundException;
import m.siverio.paincalendar.painrecord.domain.model.PainRecordSummaryView;
import m.siverio.paincalendar.painrecord.domain.port.in.CreatePainRecordUseCase;
import m.siverio.paincalendar.painrecord.domain.port.in.GetPainRecordByIdUseCase;
import m.siverio.paincalendar.painrecord.domain.port.in.GetMonthlyPainRecordsUseCase;
import m.siverio.paincalendar.painrecord.domain.port.in.UpdatePainRecordUseCase;

@WebMvcTest(PainRecordController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PainRecordControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private CreatePainRecordUseCase createPainRecordUseCase;

        @MockBean
        private GetMonthlyPainRecordsUseCase getMonthlyPainRecordsUseCase;

        @MockBean
        private UpdatePainRecordUseCase updatePainRecordUseCase;

        @MockBean
        private GetPainRecordByIdUseCase getPainRecordByIdUseCase;

        @Test
        void shouldCreatePainRecord() throws Exception {
                UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");
                UUID painRecordId = UUID.fromString("22222222-2222-2222-2222-222222222222");
                when(createPainRecordUseCase.createPainRecord(any())).thenReturn(painRecordId);

                String json = "{"
                                + "\"userId\":\"" + userId + "\","
                                + "\"date\":\"2026-02-01\","
                                + "\"slot\":\"MORNING\","
                                + "\"intensity\":7,"
                                + "\"location\":\"Cabeza\","
                                + "\"note\":\"Dolor fuerte al despertar\""
                                + "}";

                mockMvc.perform(post("/pain-records").contentType(
                                java.util.Objects.requireNonNull(MediaType.APPLICATION_JSON)).content(json))
                                .andExpect(status().isCreated())
                                .andExpect(header().string("Location", "/pain-records/" + painRecordId))
                                .andExpect(OpenApiValidationMatchers.openApi().isValid("pain-calendar.yaml"));
        }

        @Test
        void shouldUpdatePainRecord() throws Exception {
                UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");
                UUID painRecordId = UUID.fromString("22222222-2222-2222-2222-222222222222");

                String json = "{"
                                + "\"userId\":\"" + userId + "\","
                                + "\"date\":\"2026-02-01\","
                                + "\"slot\":\"NIGHT\","
                                + "\"intensity\":8,"
                                + "\"location\":\"Cuello\","
                                + "\"note\":\"Dolor actualizado\""
                                + "}";

                mockMvc.perform(put("/pain-records/{id}", painRecordId)
                                .contentType(java.util.Objects.requireNonNull(MediaType.APPLICATION_JSON))
                                .content(json))
                                .andExpect(status().isNoContent())
                                .andExpect(OpenApiValidationMatchers.openApi().isValid("pain-calendar.yaml"));
        }

        @Test
        void shouldReturnPainRecordById() throws Exception {
                UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");
                UUID painRecordId = UUID.fromString("22222222-2222-2222-2222-222222222222");

                when(getPainRecordByIdUseCase.getPainRecordById(painRecordId)).thenReturn(
                                new m.siverio.paincalendar.painrecord.domain.model.PainRecord(
                                                new m.siverio.paincalendar.painrecord.domain.model.PainRecordId(painRecordId),
                                                userId,
                                                LocalDate.of(2026, 2, 1),
                                                m.siverio.paincalendar.painrecord.domain.model.Slot.NIGHT,
                                                8,
                                                "Cuello",
                                                "Dolor actualizado",
                                                List.of()));

                mockMvc.perform(get("/pain-records/{id}", painRecordId))
                                .andExpect(status().isOk())
                                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.id").value("22222222-2222-2222-2222-222222222222"))
                                .andExpect(jsonPath("$.date").value("2026-02-01"))
                                .andExpect(jsonPath("$.slot").value("NIGHT"))
                                .andExpect(jsonPath("$.intensity").value(8))
                                .andExpect(jsonPath("$.location").value("Cuello"))
                                .andExpect(jsonPath("$.note").value("Dolor actualizado"))
                                .andExpect(OpenApiValidationMatchers.openApi().isValid("pain-calendar.yaml"));
        }

        @Test
        void shouldReturnNotFoundWhenUpdatingMissingPainRecord() throws Exception {
                UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");
                UUID painRecordId = UUID.fromString("22222222-2222-2222-2222-222222222222");

                doThrow(new PainRecordNotFoundException(painRecordId))
                                .when(updatePainRecordUseCase)
                                .updatePainRecord(any());

                String json = "{"
                                + "\"userId\":\"" + userId + "\","
                                + "\"date\":\"2026-02-01\","
                                + "\"slot\":\"NIGHT\","
                                + "\"intensity\":8,"
                                + "\"location\":\"Cuello\","
                                + "\"note\":\"Dolor actualizado\""
                                + "}";

                mockMvc.perform(put("/pain-records/{id}", painRecordId)
                                .contentType(java.util.Objects.requireNonNull(MediaType.APPLICATION_JSON))
                                .content(json))
                                .andExpect(status().isNotFound())
                                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.message").value("Pain record not found: " + painRecordId))
                                .andExpect(jsonPath("$.code").value("PAIN_RECORD_NOT_FOUND"))
                                .andExpect(OpenApiValidationMatchers.openApi().isValid("pain-calendar.yaml"));
        }

        @Test
        void shouldReturnNotFoundWhenPainRecordByIdDoesNotExist() throws Exception {
                UUID painRecordId = UUID.fromString("22222222-2222-2222-2222-222222222222");

                doThrow(new PainRecordNotFoundException(painRecordId))
                                .when(getPainRecordByIdUseCase)
                                .getPainRecordById(painRecordId);

                mockMvc.perform(get("/pain-records/{id}", painRecordId))
                                .andExpect(status().isNotFound())
                                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.message").value("Pain record not found: " + painRecordId))
                                .andExpect(jsonPath("$.code").value("PAIN_RECORD_NOT_FOUND"))
                                .andExpect(OpenApiValidationMatchers.openApi().isValid("pain-calendar.yaml"));
        }

        @Test
        void shouldRejectInvalidPainRecordPayload() throws Exception {
                UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");

                String json = "{"
                                + "\"userId\":\"" + userId + "\","
                                + "\"date\":\"2026-02-01\","
                                + "\"slot\":\"MORNING\","
                                + "\"intensity\":11"
                                + "}";

                mockMvc.perform(post("/pain-records")
                                .contentType(java.util.Objects.requireNonNull(MediaType.APPLICATION_JSON))
                                .content(json))
                                .andExpect(status().isBadRequest())
                                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.message").value("intensity: must be less than or equal to 10"))
                                .andExpect(jsonPath("$.code").value("INVALID_REQUEST"));
        }

        @Test
        void shouldRejectPainRecordPayloadWithoutRequiredField() throws Exception {
                UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");

                String json = "{"
                                + "\"userId\":\"" + userId + "\","
                                + "\"date\":\"2026-02-01\","
                                + "\"intensity\":7"
                                + "}";

                mockMvc.perform(post("/pain-records")
                                .contentType(java.util.Objects.requireNonNull(MediaType.APPLICATION_JSON))
                                .content(json))
                                .andExpect(status().isBadRequest())
                                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.message").value("slot: must not be null"))
                                .andExpect(jsonPath("$.code").value("INVALID_REQUEST"));
        }

        @Test
        void shouldReturnMonthlyPainRecords() throws Exception {
                UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");
                List<PainRecordSummaryView> records = List.of(
                                new PainRecordSummaryView(
                                                UUID.fromString("22222222-2222-2222-2222-222222222222"),
                                                LocalDate.of(2026, 2, 1),
                                                7,
                                                "Cabeza"),
                                new PainRecordSummaryView(
                                                UUID.fromString("33333333-3333-3333-3333-333333333333"),
                                                LocalDate.of(2026, 2, 15),
                                                4,
                                                null));

                when(getMonthlyPainRecordsUseCase.getMonthlyPainRecords(eq(userId), eq(YearMonth.of(2026, 2))))
                                .thenReturn(records);

                mockMvc.perform(get("/pain-records")
                                .queryParam("userId", userId.toString())
                                .queryParam("year", "2026")
                                .queryParam("month", "2"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$[0].id").value("22222222-2222-2222-2222-222222222222"))
                                .andExpect(jsonPath("$[0].date").value("2026-02-01"))
                                .andExpect(jsonPath("$[0].intensity").value(7))
                                .andExpect(jsonPath("$[0].location").value("Cabeza"))
                                .andExpect(jsonPath("$[1].id").value("33333333-3333-3333-3333-333333333333"))
                                .andExpect(jsonPath("$[1].date").value("2026-02-15"))
                                .andExpect(jsonPath("$[1].intensity").value(4))
                                .andExpect(jsonPath("$[1].location").isEmpty())
                                .andExpect(OpenApiValidationMatchers.openApi().isValid("pain-calendar.yaml"));
        }

        @Test
        void shouldRejectMonthlyPainRecordsRequestWithoutRequiredParameter() throws Exception {
                UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");

                mockMvc.perform(get("/pain-records")
                                .queryParam("userId", userId.toString())
                                .queryParam("year", "2026"))
                                .andExpect(status().isBadRequest())
                                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.message").value("month: must not be null"))
                                .andExpect(jsonPath("$.code").value("INVALID_REQUEST"));
        }

        @Test
        void shouldRejectMonthlyPainRecordsRequestWithInvalidMonth() throws Exception {
                UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");

                mockMvc.perform(get("/pain-records")
                                .queryParam("userId", userId.toString())
                                .queryParam("year", "2026")
                                .queryParam("month", "13"))
                                .andExpect(status().isBadRequest())
                                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.message").value("month: must be less than or equal to 12"))
                                .andExpect(jsonPath("$.code").value("INVALID_REQUEST"));
        }
}
