package m.siverio.paincalendar.painrecord.infrastructure.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import m.siverio.paincalendar.painrecord.domain.port.in.CreatePainRecordUseCase;

@WebMvcTest(PainRecordController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PainRecordControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private CreatePainRecordUseCase createPainRecordUseCase;

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
                                + "\"note\":\"Dolor fuerte al despertar\""
                                + "}";

                mockMvc.perform(post("/pain-records").contentType(
                                java.util.Objects.requireNonNull(MediaType.APPLICATION_JSON)).content(json))
                                .andExpect(status().isCreated())
                                .andExpect(header().string("Location", "/pain-records/" + painRecordId));
        }
}
