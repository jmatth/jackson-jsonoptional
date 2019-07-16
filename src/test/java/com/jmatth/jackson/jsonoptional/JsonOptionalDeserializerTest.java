package com.jmatth.jackson.jsonoptional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class JsonOptionalDeserializerTest {
  private static ObjectMapper mapperWithModule() {
    return new ObjectMapper().registerModule(new JsonOptionalModule());
  }

  @Test
  public void testPrimitiveDeserialization_value() throws Exception {
    final ObjectMapper mapper = mapperWithModule();
    final String json = "{ \"number\": 1 }";
    final PrimitiveObject primitiveObject = mapper.readValue(json, PrimitiveObject.class);
    assertTrue(primitiveObject.getNumber().isPresent());
    assertEquals(1, primitiveObject.getNumber().get().intValue());
  }

  @Test
  public void testPrimitiveDeserialization_null() throws Exception {
    final ObjectMapper mapper = mapperWithModule();
    final String json = "{ \"number\": null }";
    final PrimitiveObject primitiveObject = mapper.readValue(json, PrimitiveObject.class);
    assertTrue(primitiveObject.getNumber().isSet());
    assertFalse(primitiveObject.getNumber().isPresent());
  }

  @Test
  public void testPrimitiveDeserialization_unset() throws Exception {
    final ObjectMapper mapper = mapperWithModule();
    final String json = "{ }";
    final PrimitiveObject primitiveObject = mapper.readValue(json, PrimitiveObject.class);
    assertFalse(primitiveObject.getNumber().isSet());
  }

  private static class PrimitiveObject {
    @JsonProperty("number")
    private JsonOptional<Integer> number = JsonOptional.unset();

    public JsonOptional<Integer> getNumber() {
      return number;
    }
  }
}
