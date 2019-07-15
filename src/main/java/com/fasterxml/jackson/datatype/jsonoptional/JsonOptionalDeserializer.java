package com.fasterxml.jackson.datatype.jsonoptional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import java.io.IOException;

public class JsonOptionalDeserializer extends JsonDeserializer<JsonOptional<?>>
    implements ContextualDeserializer {

  private JavaType _valueType;

  @Override
  public JsonDeserializer<?> createContextual(
      final DeserializationContext ctxt, final BeanProperty property) throws JsonMappingException {
    final JavaType javaType = property.getType().containedType(0);
    final JsonOptionalDeserializer jsonOptionalDeserializer = new JsonOptionalDeserializer();
    jsonOptionalDeserializer._valueType = javaType;
    return jsonOptionalDeserializer;
  }

  @Override
  public JsonOptional<?> deserialize(final JsonParser p, final DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    final JsonDeserializer<Object> nonContextualValueDeserializer =
        ctxt.findNonContextualValueDeserializer(_valueType);

    final Object deserialize = nonContextualValueDeserializer.deserialize(p, ctxt);
    return JsonOptional.of(deserialize);
  }

  @Override
  public JsonOptional<?> getNullValue(final DeserializationContext ctxt)
      throws JsonMappingException {
    return JsonOptional.empty();
  }
}
