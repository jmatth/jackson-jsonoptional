package com.jmatth.jackson.jsonoptional;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class JsonOptionalSerializerTest {
  private static ObjectMapper mapperWithModule() {
    return new ObjectMapper().registerModule(new JsonOptionalModule());
  }

  @Test
  public void testPrimitiveSerialization_value() throws Exception {
    final ObjectMapper mapper = mapperWithModule();
    final String value = mapper.writeValueAsString(JsonOptional.of("1"));
    assertEquals("1", value);
  }

  @Test
  public void testPrimitiveSerialization_null() throws Exception {
    final ObjectMapper mapper = mapperWithModule();
    final String value = mapper.writeValueAsString(JsonOptional.empty());
    assertEquals("null", value);
  }

  @Test
  public void testPrimitiveSerialization_unset() throws Exception {
    final ObjectMapper mapper = mapperWithModule();
    final String value = mapper.writeValueAsString(JsonOptional.unset());
    assertEquals("null", value);
  }
}
