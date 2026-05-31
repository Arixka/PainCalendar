package m.siverio.paincalendar.contract;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;

class OpenApiSpecificationValidationTest {

    @Test
    void shouldValidateOpenApiSpecification() {
        String specificationUri = Path.of("src/main/resources/pain-calendar.yaml").toUri().toString();

        SwaggerParseResult result = new OpenAPIParser().readLocation(specificationUri, null, null);

        assertNotNull(result.getOpenAPI(), () -> "OpenAPI document could not be parsed. Messages: " + String.join("\n", result.getMessages()));
        assertTrue(result.getMessages().isEmpty(), () -> "OpenAPI validation errors:\n" + String.join("\n", result.getMessages()));
    }
}
