package com.jmatth.jackson.jsonoptional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

    @Test
    public void testNestedObject_value() throws Exception {
        final ObjectMapper mapper = mapperWithModule();
        final String json = "{ \"numbers\": [ 1, 2, 3 ] }";
        final NestedObject nestedObject = mapper.readValue(json, NestedObject.class);
        assertTrue(nestedObject.getNumbers().isPresent());
        assertEquals(Arrays.asList(1, 2, 3), nestedObject.getNumbers().get());
    }

    @Test
    public void testNestedObject_empty() throws Exception {
        final ObjectMapper mapper = mapperWithModule();
        final String json = "{ \"numbers\": [ ] }";
        final NestedObject nestedObject = mapper.readValue(json, NestedObject.class);
        assertTrue(nestedObject.getNumbers().isPresent());
        assertEquals(Collections.emptyList(), nestedObject.getNumbers().get());
    }

    @Test
    public void testNestedObject_null() throws Exception {
        final ObjectMapper mapper = mapperWithModule();
        final String json = "{ \"numbers\": null }";
        final NestedObject nestedObject = mapper.readValue(json, NestedObject.class);
        assertTrue(nestedObject.getNumbers().isSet());
        assertFalse(nestedObject.getNumbers().isPresent());
    }

    @Test
    public void testNestedObject_unset() throws Exception {
        final ObjectMapper mapper = mapperWithModule();
        final String json = "{ }";
        final NestedObject nestedObject = mapper.readValue(json, NestedObject.class);
        assertFalse(nestedObject.getNumbers().isSet());
    }

    private static class PrimitiveObject {
        @JsonProperty("number")
        private JsonOptional<Integer> number = JsonOptional.unset();

        JsonOptional<Integer> getNumber() {
            return number;
        }
    }

    private static class NestedObject {
        @JsonProperty("numbers")
        private JsonOptional<List<Integer>> numbers = JsonOptional.unset();

        JsonOptional<List<Integer>> getNumbers() {
            return numbers;
        }
    }
}
