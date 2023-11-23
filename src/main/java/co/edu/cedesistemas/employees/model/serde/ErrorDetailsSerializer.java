package co.edu.cedesistemas.employees.model.serde;

import co.edu.cedesistemas.employees.model.error.ErrorDetails;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ErrorDetailsSerializer extends JsonSerializer<ErrorDetails> {
    @Override
    public void serialize(ErrorDetails value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("code", value.getErrorCode().toString());
        gen.writeStringField("message", value.getErrorMessage());
        gen.writeStringField("reference", value.getReferenceUrl());
        gen.writeEndObject();
    }
}
